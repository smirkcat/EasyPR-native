
#include "process.h"
#include<string>
using namespace std;

#ifdef __cplusplus
extern "C" {
#endif
	__declspec(dllexport) Process* init(char * modelpath);
	__declspec(dllexport) const char * plateRecognize(Process * ptr, char *img);
	__declspec(dllexport) void deleteptr(Process * ptr);

#ifdef __cplusplus
}
#endif