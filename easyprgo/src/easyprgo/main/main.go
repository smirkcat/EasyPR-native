package main

import (
	"easyprgo"
	"fmt"
	"io/ioutil"
)

func main() {
	easypr := easyprgo.NewEasypr()
	easypr.Init(`../../../model`)
	file, err := ioutil.ReadFile("test.jpg")
	if err != nil {
		fmt.Println(err)
		return
	}
	re := easypr.PlateRecognize(file)
	fmt.Println(re)
	easypr.Close()
}
