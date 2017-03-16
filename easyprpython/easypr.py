# -*- coding: utf-8 -*-
import easyprpy

if __name__ == '__main__':
    fp = open('test.jpg','rb')
    data = fp.read()
    datalen=len(data)
    easypr= easyprpy.EasyPR("../model")
    st=easypr.plateRecognize(data,datalen)
    print st