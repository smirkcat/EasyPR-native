#include <string>
#include "easypr/core/plate_recognize.h"
#include <opencv2/opencv.hpp>
using namespace easypr;
using namespace std;
#ifndef _Included_Process
#define _Included_Process

class Process{
	CPlateRecognize pr;
public:
	~Process(){
	}
	Process(string);
	Process();
	string process(char*, int);
};
#endif