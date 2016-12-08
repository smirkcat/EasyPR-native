"use strict";
const easypr = require('./build/Release/easyprnode');

var rf = require("fs");
var teststr = rf.readFileSync("test.jpg", 'binary');
var v1 = Uint8Array.from(teststr);
var len = Buffer.byteLength(teststr, "binary")
console.log(len);
var dir = __dirname + "/../model";
console.log(dir);
//加载dir文件夹下的配置文件
var obj = easypr(dir);

//可以使用new方式创建，等价
//var obj = new easypr(dir);

//obj.getInitFlag() 获取easypr初始化标志，1表示成功 负数初始化失败，可能是配置文件未读取
var initflag = obj.getInitFlag()
if (initflag < 1) {
    console.log("Init Faild the initflag=" + initflag);
    return
}
var re = obj.plateRecognize(v1)
console.log(re);