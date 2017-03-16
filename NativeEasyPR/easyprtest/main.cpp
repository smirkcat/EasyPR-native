#include <iostream>
#include <string>
#include "process.h"
#include "easypr/core/plate_recognize.h"
#include <opencv2/opencv.hpp>
using namespace cv;
using namespace easypr;
using namespace std;
int main(){
	//模型文件model 和province_mapping需放到一个文件夹下面，这里已经提取出来放到model文件下
	//源程序没有处理加载失败情况，很是糟糕,path为自己的model文件路径
	string path = "D:/workspace/git/EasyPR-native/model";
	//string path = "/root/ccccc/EasyPR-native/model";
	Process *pr=new Process(path);
	
	Mat src = imread("D:/workspace/git/EasyPR-native/EasyPR/resources/image/test.jpg");
	//Mat src = imread("/root/ccccc/EasyPR-native/EasyPR/resources/image/test.jpg");
	string plate=pr->process(src);
	cout << plate << endl;
	getchar();
	delete pr;
	return 0;
}