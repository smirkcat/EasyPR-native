#include<cstring>
#include"process.h"
#include"py.h"
#include<string>
using namespace std;

EXPORT uint init(char * modelpath){
	Process * ptr = new Process(modelpath);
	unsigned int re = (unsigned int)ptr;
	return re;
}

EXPORT char * plateRecognize(uint ptr, char *img,int len){
	Process *p = (Process *)ptr;
	string str = p->process(img, len);
	char *buf = new char[str.length()+1];
	strcpy(buf, str.c_str());
	return buf;
}

EXPORT void deleteptr(uint ptr){
	Process *p = (Process *)ptr;
	delete p;
}