#ifndef _EASYPRPY_H_
#define _EASYPRPY_H_
#include "process.h"
#include<string>
using namespace std;

#if _WIN32
#define EXPORT __declspec(dllexport)
#define IMPORT __declspec(dllimport)
#else
#if (defined(__GNUC__) && ((__GNUC__ > 4) || (__GNUC__ == 4) && (__GNUC_MINOR__ > 2))) || __has_attribute(visibility)
#define EXPORT     __attribute__((visibility("default")))
#define IMPORT     __attribute__((visibility("default")))
#else
#define EXPORT
#define IMPORT
#endif
#endif


#ifdef __cplusplus
extern "C" {
#endif
	EXPORT Process* init(char * modelpath);
	EXPORT char * plateRecognize(Process * ptr, char *img,int len);
	EXPORT void deleteptr(Process * ptr);

#ifdef __cplusplus
}
#endif
#endif