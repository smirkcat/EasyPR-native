#**************************************************************************************************
# cMake的公共配置
#**************************************************************************************************
cmake_minimum_required(VERSION 3.1.0)

project(easypr)

#**************************************************************************************************
# Preprocessor definitions 一些预定义设置
#**************************************************************************************************
if (CMAKE_CL_64)
 add_definitions(-DWIN64)
 set(PLATFORM x64)
else (CMAKE_CL_64)
 set(PLATFORM x86)
endif (CMAKE_CL_64)

if(WIN32)
  ADD_DEFINITIONS(-DIGTB_EXPORTS)
  ADD_DEFINITIONS(-D_WIN32_WINNT=0x0501)
endif(WIN32)

# 查找 OpenCV
find_package(OpenCV 3.1.0 REQUIRED)

# opencv头文件依赖可以不写
if(OpenCV_FOUND)
  include_directories(${OpenCV_INCLUDE_DIRS})
endif()

#根目录设置
SET(EASYPR_BASE_DIR ${CMAKE_CURRENT_SOURCE_DIR})

#源文件设置
AUX_SOURCE_DIRECTORY(${EASYPR_BASE_DIR}/esayprjni/src OBJ_JNI)

# 源文件 由于core下面多了一个plate.cpp所以不能用上面的
set(OBJ_CORE
        ${EASYPR_BASE_DIR}/../EasyPR/src/core/core_func.cpp
        ${EASYPR_BASE_DIR}/../EasyPR/src/core/chars_identify.cpp
        ${EASYPR_BASE_DIR}/../EasyPR/src/core/chars_recognise.cpp
        ${EASYPR_BASE_DIR}/../EasyPR/src/core/chars_segment.cpp
        ${EASYPR_BASE_DIR}/../EasyPR/src/core/feature.cpp
        ${EASYPR_BASE_DIR}/../EasyPR/src/core/plate_detect.cpp
        ${EASYPR_BASE_DIR}/../EasyPR/src/core/plate_judge.cpp
        ${EASYPR_BASE_DIR}/../EasyPR/src/core/plate_locate.cpp
        ${EASYPR_BASE_DIR}/../EasyPR/src/core/plate_recognize.cpp
        ${EASYPR_BASE_DIR}/../EasyPR/src/core/params.cpp
        
        ${EASYPR_BASE_DIR}/../EasyPR/thirdparty/LBP/lbp.cpp
        ${EASYPR_BASE_DIR}/../EasyPR/thirdparty/LBP/helper.cpp

        ${EASYPR_BASE_DIR}/../EasyPR/thirdparty/mser/mser2.cpp

        ${EASYPR_BASE_DIR}/../EasyPR/thirdparty/textDetect/erfilter.cpp
        )

#头文件包含目录
include_directories(${EASYPR_BASE_DIR}/../EasyPR/include)
include_directories(${EASYPR_BASE_DIR})
include_directories(${EASYPR_BASE_DIR}/easyprjni/include)

#用$ENV{JAVA_HOME}获取 用于加载jni头文件 前提配置好JAVA_HOME
INCLUDE_DIRECTORIES($ENV{JAVA_HOME}/include)
if(WIN32)
    INCLUDE_DIRECTORIES($ENV{JAVA_HOME}/include/win32)
ELSEIF(APPLE)
    INCLUDE_DIRECTORIES($ENV{JAVA_HOME}/include/darwin)
ELSE() 
    INCLUDE_DIRECTORIES($ENV{JAVA_HOME}/include/linux)
endif()


#生成easyprcore静态库
set(CORE_LIB easyprcore)
add_library(${CORE_LIB} STATIC ${OBJ_CORE})
target_link_libraries(${CORE_LIB} ${OpenCV_LIBS})

#生成easyprjni动态库
set(JNI_LIB easyprjni)
add_library(${JNI_LIB} SHARED ${OBJ_JNI})
target_link_libraries(${JNI_LIB} ${CORE_LIB} ${OpenCV_LIBS})

