/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_easyprjava_jni_EasyPR */

#ifndef _Included_org_easyprjava_jni_EasyPR
#define _Included_org_easyprjava_jni_EasyPR
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_easyprjava_jni_EasyPR
 * Method:    plateRecognize
 * Signature: (I[B)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_easyprjava_jni_EasyPR_plateRecognize
  (JNIEnv *, jobject, jint, jbyteArray);

/*
 * Class:     org_easyprjava_jni_EasyPR
 * Method:    initPath
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_easyprjava_jni_EasyPR_initPath
  (JNIEnv *, jobject, jstring);

/*
 * Class:     org_easyprjava_jni_EasyPR
 * Method:    init
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_easyprjava_jni_EasyPR_init
  (JNIEnv *, jobject);

/*
 * Class:     org_easyprjava_jni_EasyPR
 * Method:    delete
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_easyprjava_jni_EasyPR_delete
  (JNIEnv *, jobject, jint);

#ifdef __cplusplus
}
#endif
#endif
