#include <string>
#include "tojava.h"
// char* To jstring  
jstring stringTojstring(JNIEnv* env, const char* pat)

{
	jclass strClass = env->FindClass("Ljava/lang/String;");
	jmethodID ctorID = env->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
	jbyteArray bytes = env->NewByteArray(strlen(pat));
	env->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte*)pat);
	//此处用GB2312是因为char*中文这个方式保存，如果换成UTF-8认为char*是UTF-8显然不对,linux也测试成功

	jstring encoding = env->NewStringUTF("GB2312");

	return (jstring)env->NewObject(strClass, ctorID, bytes, encoding);
}

// jstring To char*  

char* jstringTostring(JNIEnv* env, jstring jstr)

{
	char* rtn = NULL;
	jclass clsstring = env->FindClass("java/lang/String");
	jstring strencode = env->NewStringUTF("GB2312");
	jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
	jbyteArray barr = (jbyteArray)env->CallObjectMethod(jstr, mid, strencode);
	jsize alen = env->GetArrayLength(barr);
	jbyte* ba = env->GetByteArrayElements(barr, JNI_FALSE);
	if (alen > 0)
	{
		rtn = (char*)malloc(alen + 1);
		memcpy(rtn, ba, alen);
		rtn[alen] = 0;
	}
	env->ReleaseByteArrayElements(barr, ba, 0);
	return rtn;
}

JNIEXPORT jstring JNICALL plateRecognize
(JNIEnv *env, jobject obj, Process *ptr, jbyteArray img){
	jboolean isCopy = JNI_FALSE;
	int size = env->GetArrayLength(img);
	jbyte * imagebuffer = env->GetByteArrayElements(img, &isCopy);
	if (NULL == imagebuffer)
	{
		return NULL;
	}
	//此处前面通用
	//此处只是测试 编写测试程序请修改process函数

	std::string result = ptr->process((char*)imagebuffer, size);

	//此处后面通用

	env->ReleaseByteArrayElements(img, imagebuffer, JNI_COMMIT);

	jstring  jresult = stringTojstring(env, result.c_str());
	return jresult;
}


JNIEXPORT Process * JNICALL initPath
(JNIEnv *env, jobject object, jstring path){
	string modelpath = jstringTostring(env, path);
	Process * ptr = new Process(modelpath);
	return ptr;
}

JNIEXPORT Process * JNICALL init
(JNIEnv *env, jobject object){
	Process * ptr = new Process();
	return ptr;
}

JNIEXPORT void JNICALL deleteptr
(JNIEnv *env, jobject object, Process * ptr){
	delete ptr;
}


static int registerNativeMethods(JNIEnv* env, const char* className,
	JNINativeMethod* gMethods, int numMethods){
	
	jclass clazz;
	clazz = env->FindClass(className);
	if (clazz == NULL) {
		return JNI_FALSE;
	}
	if (env->RegisterNatives(clazz, gMethods, numMethods) < 0) {
		return JNI_FALSE;
	}
	return JNI_TRUE;
}

static int registerNatives(JNIEnv* env){

	if (!registerNativeMethods(env, classPathName,
		methods, sizeof(methods) / sizeof(methods[0]))) {
		return JNI_FALSE;
	}
	return JNI_TRUE;
}

/* This function will be call when the library first be loaded */
JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM *vm, void *reserved){
	
	UnionJNIEnvToVoid uenv;
	JNIEnv* env = NULL;
	jint result = -1;
	if (vm->GetEnv((void**)&uenv.venv, JNI_VERSION_1_6) != JNI_OK) {
		goto bail;
	}
	env = uenv.env;
	if (registerNatives(env) != JNI_TRUE) {
		goto bail;
	}
	result = JNI_VERSION_1_6;
bail:
	return result;
}