#include<cstring>
#include"process.h"
#include"py.h"
#include<string>
using namespace std;

EXPORT long init(char * modelpath){
	Process * ptr = new Process(modelpath);
	long re = (long)ptr;
	return re;
}

EXPORT char * plateRecognize(long ptr, char *img, int len){
	Process *p = (Process *)ptr;
	string str = p->process(img, len);
	char *buf = new char[str.length()+1];
	strcpy(buf, str.c_str());
	return buf;
}

EXPORT void deleteptr(long ptr){
	Process *p = (Process *)ptr;
	delete p;
}