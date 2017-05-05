package easyprgo

// #include <stdio.h>
// #include <stdlib.h>
/*
#cgo CFLAGS : -I${SRCDIR}/../include
#cgo LDFLAGS: -L${SRCDIR}/../lib -leasyprexport

#include "py.h"
*/
import "C"

import (
	"unsafe"
)

type EasyPR struct {
	Ptr C.long
}

// 只调用一次
func (er *EasyPR) Init(str string) {
	modepath := C.CString(str)
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
	return result
}

func newEasyPR() IEasyPR {
	return &EasyPR{}
}
