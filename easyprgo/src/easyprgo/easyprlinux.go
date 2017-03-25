package main

// #include <stdio.h>
// #include <stdlib.h>
/*
#cgo CFLAGS : -I${SRCDIR}/../include
#cgo LDFLAGS: -L${SRCDIR}/../lib -leasyprexport

#include "py.h"
*/
import "C"

import (
	"bytes"
	"fmt"
	"io/ioutil"
	"unsafe"

	"golang.org/x/text/encoding/simplifiedchinese"
	"golang.org/x/text/transform"
)

func main() {
	easypr := &EasyPR{ModePath: `../../../model`}
	easypr.Init()
	file, err := ioutil.ReadFile("test.jpg")
	if err != nil {
		fmt.Println(err)
		return
	}
	re := easypr.PlateRecognize(file)
	fmt.Println(re)
	easypr.Close()
}

type EasyPR struct {
	ModePath string //模型文件路径
	Ptr      C.long
}

// 只调用一次
func (er *EasyPR) Init() {
	modepath := C.CString(er.ModePath)
	er.Ptr = C.init(modepath)
	C.free(unsafe.Pointer(modepath))
}

func (er *EasyPR) Close() {
	C.deleteptr(er.Ptr)
	er.Ptr = C.long(0)
}

func (er *EasyPR) PlateRecognize(b []byte) string {
	re := C.plateRecognize(er.Ptr, (*C.char)(unsafe.Pointer(&b[0])), C.int(len(b)))
	result := C.GoString(re)
	C.free(unsafe.Pointer(re))
	//utfrebyte, _ := GbkToUtf8([]byte(result))
	return result
}

func GbkToUtf8(s []byte) ([]byte, error) {
	reader := transform.NewReader(bytes.NewReader(s), simplifiedchinese.GBK.NewDecoder())
	d, e := ioutil.ReadAll(reader)
	if e != nil {
		return nil, e
	}
	return d, nil
}
