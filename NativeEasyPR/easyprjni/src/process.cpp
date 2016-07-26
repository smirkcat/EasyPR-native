#include "process.h"
#include "easypr/core/plate_recognize.h"
#include <opencv2/opencv.hpp>
using namespace easypr;
using namespace cv;
string Process::process(char* imagebuffer, int size){
	Mat src = imdecode(Mat(1, size, CV_8U, imagebuffer), IMREAD_COLOR);
	string plate="";
	if (!src.data)
		return plate;
	vector<string> result;
	pr.plateRecognize(src, result);
	int platesize = result.size();
	if (platesize>0){
		plate = result[0];
	}
	for (int i = 1; i < platesize;i++){
		plate = plate + "," + result[i];
	}
	return plate;
}
Process::Process(){
	pr.setLifemode(true);
	pr.setDebug(false);
	pr.setMaxPlates(4);
	//pr.setDetectType(PR_DETECT_COLOR | PR_DETECT_SOBEL);
	pr.setDetectType(easypr::PR_DETECT_CMSER);
}
Process::Process(string path){
	Process();
	pr.LoadANN(path + "/ann.xml");
	pr.LoadChineseANN(path + "/ann_chinese.xml");
	pr.LoadSVM(path + "/svm.xml");
}