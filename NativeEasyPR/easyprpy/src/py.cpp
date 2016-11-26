#include<cstring>
#include"process.h"
#include"py.h"

EXPORT Process* init(char * modelpath){
	Process * ptr = new Process(modelpath);
	return ptr;
}

EXPORT char * plateRecognize(Process * ptr, char *img,int len){
	string str = ptr->process(img, len);
	char *buf = new char[str.length()+1];
	strcpy(buf, str.c_str());
	return buf;
}

EXPORT void deleteptr(Process * ptr){
	delete ptr;
}