#include <node.h>
#include "jsobject.h"

using namespace v8;

void CreateObject(const FunctionCallbackInfo<Value>& args) {
  JsObject::NewInstance(args);
}

void InitAll(Handle<Object> exports, Handle<Object> module) {
  JsObject::Init(exports->GetIsolate());
  //module.exports 导出对象
  NODE_SET_METHOD(module, "exports", CreateObject);
}

//函数外 宏定义不加分号
NODE_MODULE(addon, InitAll)