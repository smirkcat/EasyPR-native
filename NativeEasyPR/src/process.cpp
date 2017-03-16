#include "process.h"
#include "easypr/core/plate_recognize.h"
#include <opencv2/opencv.hpp>
using namespace easypr;
using namespace cv;
string Process::process(Mat &src){
	string plate = "";
	if (!src.data)
		return plate;
	vector<CPlate> plateVec;
	int result = pr->plateRecognize(src, plateVec);
	if (result == 0) {
		size_t num = plateVec.size();
		if (num<=0){
			return plate;
		}
		plate = plateVec[0].getPlateStr();
		for (size_t i = 1; i < num; i++) {
			plate = plate + "," + plateVec[i].getPlateStr();
		}
	}
	return plate;
}

string Process::process(char* imagebuffer, int size){
	Mat src = imdecode(Mat(1, size, CV_8U, imagebuffer), IMREAD_COLOR);
	if (!src.data)
		return "";
	return this->process(src);
}
Process::Process(){
	pr = new CPlateRecognize();
	pr->setLifemode(true);
	pr->setDebug(false);
	pr->setMaxPlates(4);
	//pr.setDetectType(PR_DETECT_COLOR | PR_DETECT_SOBEL);
	pr->setDetectType(easypr::PR_DETECT_CMSER);
}
Process::Process(string path){
	pr = new CPlateRecognize();
	pr->setLifemode(true);
	pr->setDebug(false);
	pr->setResultShow(false);
	//pr.setDetectType(PR_DETECT_COLOR | PR_DETECT_SOBEL|PR_DETECT_CMSER);
	pr->setDetectType(PR_DETECT_COLOR | PR_DETECT_CMSER);

	//kv_ 属性需设置成公有属性
	pr->LoadSVM((path + "/svm.xml"));
	pr->LoadANN(path + "/ann.xml");
	pr->LoadChineseANN(path + "/ann_chinese.xml");
	CharsIdentify::instance()->kv_ = std::shared_ptr<Kv>(new Kv);
	CharsIdentify::instance()->kv_->load(path + "/province_mapping");
}