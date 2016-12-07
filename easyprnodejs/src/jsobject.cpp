#include <node.h>
#include <string>
#include "jsobject.h"

using namespace v8;

Persistent<Function> JsObject::constructor;


JsObject::JsObject(const char* dir) {
	initFlag = 1;
	ptr = new Process(dir);
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
	//v8::ArrayBufferView
	if (obj->initFlag <= 0) {
		isolate->ThrowException(v8::Exception::TypeError(v8::String::NewFromUtf8(isolate, "initFlag<0,pelese check the ini file dir")));
		return;
	}
	else if (args.Length() < 2 || !args[0]->IsString() || !args[1]->IsInt32()) {
		isolate->ThrowException(v8::Exception::TypeError(v8::String::NewFromUtf8(isolate, "Wrong arguments,should string and int")));
		return;
	}
	Local<Object> reobj = Object::New(isolate);
	v8::String::Utf8Value result(args[0]->ToString());
	std::string buffer = std::string(*result, result.length());
	int policy = args[1]->Int32Value();
	int cnum = 0, bnum = 0;
	std::string cstr, bstr;
	int n = card_search(buffer.c_str(), buffer.length(), policy, bnum, cnum, bstr, cstr);

	Local<Number> numbank = Int32::New(isolate, bnum);
	reobj->Set(v8::String::NewFromUtf8(isolate, "numBank"), numbank);

	Local<Number> numidcard = Int32::New(isolate, cnum);
	reobj->Set(v8::String::NewFromUtf8(isolate, "numIdCard"), numidcard);

	Local<Number> numtotal = Int32::New(isolate, n);
	reobj->Set(v8::String::NewFromUtf8(isolate, "numTotal"), numtotal);

	reobj->Set(v8::String::NewFromUtf8(isolate, "strBank"), v8::String::NewFromUtf8(isolate, bstr.c_str()));

	reobj->Set(v8::String::NewFromUtf8(isolate, "strIdCard"), v8::String::NewFromUtf8(isolate, cstr.c_str()));

	args.GetReturnValue().Set(reobj);
}

void JsObject::GetInitFlag(const FunctionCallbackInfo<Value>& args) {
	Isolate* isolate = args.GetIsolate();
	JsObject* obj = ObjectWrap::Unwrap<JsObject>(args.Holder());
	args.GetReturnValue().Set(Int32::New(isolate, obj->initFlag));
}