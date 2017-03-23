# -*- coding: utf-8 -*-
import ctypes
import os
import platform

def suffix():
    sysstr=platform.system()
    if(sysstr == "Windows"):
        return ".dll"
    elif(sysstr == "Darwin"):
        return ".dylib"
    else:
        return ".so"

Gsuffix=suffix()

if __name__ == '__main__':
    fp = open('test.jpg','rb')
    data = fp.read()
    datalen=len(data)
    easypr=ctypes.CDLL("./easyprexport"+Gsuffix)
    modelpath=os.path.join("../model")
    ptr=easypr.init(modelpath)
    easypr.plateRecognize.restype=ctypes.c_char_p
    st=easypr.plateRecognize(ptr,data,datalen)
    easypr.deleteptr(ptr)
    print st