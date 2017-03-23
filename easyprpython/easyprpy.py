# -*- coding: utf-8 -*-
import platform
import ctypes
import os

def suffix():
    sysstr=platform.system()
    if(sysstr == "Windows"):
        return ".dll"
    elif(sysstr == "Darwin"):
        return ".dylib"
    else:
        return ".so"

def initeasyprpy():
    pathdll=os.path.split(os.path.realpath(__file__))[0]
    Suffix=suffix()
    easypr=ctypes.CDLL(os.path.join(pathdll+"/easyprexport"+Suffix))
    easypr.plateRecognize.restype=ctypes.c_char_p
    return easypr

class EasyPR:
    Geasypr=initeasyprpy()
    def __init__(self, modelpath):
        self.ptr=EasyPR.Geasypr.init(os.path.join(modelpath))

    def plateRecognize(self,data,lendata):
        return  EasyPR.Geasypr.plateRecognize(self.ptr,data,lendata)

    def __del__(self):
        EasyPR.Geasypr.deleteptr(self.ptr)
