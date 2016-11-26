#include"process.h"

__declspec(dllexport) Process* init(char * modelpath){
	Process * ptr = new Process(modelpath);
	return ptr;
}

__declspec(dllexport) const char * plateRecognize(Process * ptr, char *img){
	string str = ptr->process(img, strlen(img));
	return str.c_str();
}

__declspec(dllexport) void deleteptr(Process * ptr){
	delete ptr;
}