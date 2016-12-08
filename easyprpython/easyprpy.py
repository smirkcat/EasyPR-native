# -*- coding: utf-8 -*-
import ctypes
import os

class EasyPR:

    def __init__(self, dllfile, modelpath):
        self.easypr=ctypes.CDLL(os.path.join(dllfile))
        self.ptr=self.easypr.init(os.path.join(modelpath))
        self.easypr.plateRecognize.restype=ctypes.c_char_p

    def plateRecognize(self,data,lendata):
        return self.easypr.plateRecognize(self.ptr,data,lendata)

    def __del__(self):
        self.easypr.deleteptr(self.ptr)
