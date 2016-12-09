package main

import "C"

import (
	"fmt"
	"io/ioutil"
	"syscall"
	"unsafe"
)

func IntPtr(n int) uintptr {
	return uintptr(n)
}
func UIntPtr(n uint) uintptr {
	return uintptr(n)
}

func StrPtr(s string) uintptr {
	return uintptr(unsafe.Pointer(syscall.StringBytePtr(s)))
}

func BytePtr(b []byte) uintptr {
	return uintptr(unsafe.Pointer(&b[0]))
}

func main() {
	lib := syscall.NewLazyDLL("./easyprpy.dll")
	err := lib.Load()
	if err != nil {
		fmt.Println(err)
		return
	}
	init := lib.NewProc("init")
	plateRecognize := lib.NewProc("plateRecognize")
	deleteptr := lib.NewProc("deleteptr")
	gostr := "../../../model"
	//modepath := C.CString(gostr)
	ptr, _, _ := init.Call(StrPtr(gostr))
	file, err := ioutil.ReadFile("test.jpg")
	if err != nil {
		fmt.Println(err)
		return
	}

	goptr := uint(ptr)
	lenfile := len(file)
	platestr, _, _ := plateRecognize.Call(UIntPtr(goptr), BytePtr(file), IntPtr(lenfile))
	cre := (*C.char)(unsafe.Pointer(platestr))
	result := C.GoString(cre)
	fmt.Println(result)
	deleteptr.Call(UIntPtr(goptr))
}
