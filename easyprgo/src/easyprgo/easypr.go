package easyprgo

import (
	"os"
	"path/filepath"
)

// GetCurrPath 当前运行的绝对路径
func GetCurrPath() string {
	dir, _ := filepath.Abs(filepath.Dir(os.Args[0]))
	return dir
}

// IEasyPR 车牌调用接口
type IEasyPR interface {
	PlateRecognize([]byte) string
	Init(string)
	Close()
}

// NewEasypr 返回车牌调用接口
func NewEasypr() IEasyPR {
	return newEasyPR()
}
