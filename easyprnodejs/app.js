"use strict";
var os = require('os')
var platform = os.platform();
var easypr=null
console.log(platform);
if (platform == 'win32') {
    easypr = require('./node_mode/windows/easyprnode')
} else if (platform == ';inux'){
    easypr = require('./node_mode/linux/easyprnode')
} else {
    throw new Error("not support this platform " +platform);
}
var iconv = require('iconv-lite');

var rf = require("fs");
var teststr = rf.readFileSync("test.jpg");
var len = Buffer.byteLength(teststr, "binary")
var dir = __dirname + "/../model";
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
var re = obj.plateRecognize(teststr)
var reutf = ""
if (platform == 'win32') {
    reutf = iconv.decode(re, "gb2312")
}else{
    reutf = iconv.decode(re, "utf-8")
}
console.log(reutf);