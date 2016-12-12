package main

import "C"

import (
	"bytes"
	"fmt"
	"io/ioutil"
	"syscall"
	"unsafe"

	"golang.org/x/text/encoding/simplifiedchinese"
	"golang.org/x/text/transform"
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
	ptr, _, _ := init.Call(StrPtr(gostr))
	file, err := ioutil.ReadFile("test.jpg")
	if err != nil {
		fmt.Println(err)
		return
	}

	goptr := uint(ptr)
	lenfile := len(file)
	platestr, _, _ := plateRecognize.Call(UIntPtr(goptr), BytePtr(file), IntPtr(lenfile))
	deleteptr.Call(UIntPtr(goptr))
	cre := (*C.char)(unsafe.Pointer(platestr))
	result := C.GoString(cre)
	//fmt.Println(result) 乱码
	rebyte := C.GoBytes(unsafe.Pointer(platestr), C.int(len(result)))
	utfrebyte, err := GbkToUtf8(rebyte)
	if err != nil {
		fmt.Println(err)
		return
	}
	fmt.Println(string(utfrebyte))

}

func GbkToUtf8(s []byte) ([]byte, error) {
	reader := transform.NewReader(bytes.NewReader(s), simplifiedchinese.GBK.NewDecoder())
	d, e := ioutil.ReadAll(reader)
	if e != nil {
		return nil, e
	}
	return d, nil
}
