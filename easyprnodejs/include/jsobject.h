#ifndef MYOBJECT_H
#define MYOBJECT_H

#include <node.h>
#include <node_object_wrap.h>
#include "process.h"
using namespace v8;

class JsObject : public node::ObjectWrap {
public:
	static void Init(v8::Isolate* isolate);
	static void NewInstance(const v8::FunctionCallbackInfo<v8::Value>& args);

private:
	explicit JsObject(const char * dir);
	JsObject();
	~JsObject();
	Process * ptr;
	int initFlag;
	static void GetInitFlag(const v8::FunctionCallbackInfo<v8::Value>& args);
	static void New(const v8::FunctionCallbackInfo<v8::Value>& args);
	static void plateRecognize(const v8::FunctionCallbackInfo<v8::Value>& args);
	static v8::Persistent<v8::Function> constructor;
};

#endif