#include<iostream>
#include "process.h"
#include "easypr/core/plate_recognize.h"
#include <opencv2/opencv.hpp>
using namespace cv;
using namespace easypr;
using namespace std;
int main(){
	string path = "D:/JAVA/workspaceneo/easyprjava-parent/easyprjava/src/test/resources/model";
	Process pr(path);
	
	Mat src = imread("D:/Users/Administrator/Desktop/test.jpg");
	string plate=pr.process(src);
	cout << plate << endl;
	getchar();
	return 0;

}