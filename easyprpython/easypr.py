# -*- coding: utf-8 -*-
from easyprpy import * 

if __name__ == '__main__':
    fp = open('test.jpg','rb')
    data = fp.read()
    datalen=len(data)
    easypr= EasyPR("easyprpy.dll","../model")
    st=easypr.plateRecognize(data,datalen)
    print st