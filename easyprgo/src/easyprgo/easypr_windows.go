package easyprgo

import "C"

import (
	"bytes"
	"fmt"
	"io/ioutil"
	"syscall"
	"unsafe"

	"os"

	"golang.org/x/text/encoding/simplifiedchinese"
	"golang.org/x/text/transform"
)

var lib *syscall.LazyDLL
var inito *syscall.LazyProc
var plateRecognize *syscall.LazyProc
var deleteptr *syscall.LazyProc

type EasyPR struct {
	Ptr uint64
}

func IntPtr(n int) uintptr {
	return uintptr(n)
}
func UIntPtr(n uint64) uintptr {
	return uintptr(n)
}

func StrPtr(s string) uintptr {
	return uintptr(unsafe.Pointer(syscall.StringBytePtr(s)))
}

func BytePtr(b []byte) uintptr {
	return uintptr(unsafe.Pointer(&b[0]))
}

func GbkToUtf8(s []byte) ([]byte, error) {
	reader := transform.NewReader(bytes.NewReader(s), simplifiedchinese.GBK.NewDecoder())
	d, e := ioutil.ReadAll(reader)
	if e != nil {
		return nil, e
	}
	return d, nil
}

func init() {
	lib = syscall.NewLazyDLL(GetCurrPath() + "/../lib/easyprexport.dll")
	err := lib.Load()
	if err != nil {
		fmt.Println(err)
		os.Exit(1)
	}
	inito = lib.NewProc("init")
	plateRecognize = lib.NewProc("plateRecognize")
	deleteptr = lib.NewProc("deleteptr")
}

func (er *EasyPR) Init(str string) {
	ptr, _, _ := inito.Call(StrPtr(str))
	er.Ptr = uint64(ptr)
}

func (er *EasyPR) Close() {
	deleteptr.Call(UIntPtr(er.Ptr))
	er.Ptr = 0
}

func (er *EasyPR) PlateRecognize(b []byte) string {
	platestr, _, _ := plateRecognize.Call(UIntPtr(er.Ptr), BytePtr(b), IntPtr(len(b)))
	cre := (*C.char)(unsafe.Pointer(platestr))
	result := C.GoString(cre)
	utfrebyte, err := GbkToUtf8([]byte(result))
	if err != nil {
		fmt.Println(err)
		return ""
	}
	return string(utfrebyte)
}

func newEasyPR() IEasyPR {
	return &EasyPR{}
}
