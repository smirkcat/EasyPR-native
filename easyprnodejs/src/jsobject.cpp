#include <node.h>
#include<node_buffer.h>
#include <string>
#include "jsobject.h"

using namespace v8;

Persistent<Function> JsObject::constructor;


JsObject::JsObject(const char* dir) {
	initFlag = 0;
	ptr = new Process(dir);
	initFlag = 1;
}

JsObject::JsObject() {
	initFlag = -1;
}

JsObject::~JsObject() {
	delete ptr;
}

void JsObject::Init(v8::Isolate* isolate) {
	// Prepare constructor template
	Local<FunctionTemplate> tpl = FunctionTemplate::New(isolate, New);
	tpl->SetClassName(v8::String::NewFromUtf8(isolate, "JsObject"));
	tpl->InstanceTemplate()->SetInternalFieldCount(1);

	// Prototype
	NODE_SET_PROTOTYPE_METHOD(tpl, "plateRecognize", plateRecognize);
	NODE_SET_PROTOTYPE_METHOD(tpl, "getInitFlag", GetInitFlag);

	constructor.Reset(isolate, tpl->GetFunction());
}

void JsObject::New(const FunctionCallbackInfo<Value>& args) {
	Isolate* isolate = args.GetIsolate();

	if (args.IsConstructCall()) {
		// Invoked as constructor: `new MyObject(...)`
		const char *dir = NULL;
		if (args.Length() >= 1 && args[0]->IsString()) {
			v8::String::Utf8Value result(args[0]->ToString());
			dir = std::string(*result, result.length()).c_str();
		}
		JsObject* obj = new JsObject(dir);
		obj->Wrap(args.This());
		args.GetReturnValue().Set(args.This());
	}
	else {
		//// Invoked as plain function `MyObject(...)`, turn into construct call.
		// v6/7.x
		//const int argc = 1;
		//Local<Value> argv[argc] = { args[0] };
		//Local<Context> context = isolate->GetCurrentContext();
		//Local<Function> cons = Local<Function>::New(isolate, constructor);
		//Local<Object> result =cons->NewInstance(context, argc, argv).ToLocalChecked();
		//args.GetReturnValue().Set(result);
		// v4.x
		const int argc = 1;
		Local<Value> argv[argc] = { args[0] };
		Local<Function> cons = Local<Function>::New(isolate, constructor);
		args.GetReturnValue().Set(cons->NewInstance(argc, argv));
	}
}

void JsObject::NewInstance(const FunctionCallbackInfo<Value>& args) {
	Isolate* isolate = args.GetIsolate();
	// v6/7.x
	/*const unsigned argc = 1;
	Local<Value> argv[argc] = { args[0] };
	Local<Function> cons = Local<Function>::New(isolate, constructor);
	Local<Context> context = isolate->GetCurrentContext();
	Local<Object> instance =cons->NewInstance(context, argc, argv).ToLocalChecked();*/
	// v4.x
	const unsigned argc = 1;
	Local<Value> argv[argc] = { args[0] };
	Local<Function> cons = Local<Function>::New(isolate, constructor);
	Local<Object> instance = cons->NewInstance(argc, argv);

	args.GetReturnValue().Set(instance);
}

void JsObject::plateRecognize(const FunctionCallbackInfo<Value>& args) {
	Isolate* isolate = args.GetIsolate();
	JsObject* obj = ObjectWrap::Unwrap<JsObject>(args.Holder());
	
	if (obj->initFlag <= 0) {
		isolate->ThrowException(v8::Exception::TypeError(v8::String::NewFromUtf8(isolate, "initFlag<0,pelese check the ini file dir")));
		return;
	}
	else if (args.Length() < 1 || !node::Buffer::HasInstance(args[0])) {
		isolate->ThrowException(v8::Exception::TypeError(v8::String::NewFromUtf8(isolate, "Wrong arguments,should image buffer")));
		return;
	}
	if (obj->ptr == NULL)
		return;
	int len = node::Buffer::Length(args[0]);
	char * img = node::Buffer::Data(args[0]);
	std::string result=obj->ptr->process(img,len);
	char *buf = new char[result.length() + 1];
	strcpy(buf, result.c_str());
	MaybeLocal<Object> rebuffer = node::Buffer::New(isolate, buf, result.length());
	Local<Object> re= Object::New(isolate);
	rebuffer.ToLocal(&re);
	args.GetReturnValue().Set(re);
}

void JsObject::GetInitFlag(const FunctionCallbackInfo<Value>& args) {
	Isolate* isolate = args.GetIsolate();
	JsObject* obj = ObjectWrap::Unwrap<JsObject>(args.Holder());
	args.GetReturnValue().Set(Int32::New(isolate, obj->initFlag));
}