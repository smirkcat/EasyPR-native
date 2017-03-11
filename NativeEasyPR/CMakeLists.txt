#**************************************************************************************************
# cMake的公共配置
#**************************************************************************************************
# cmake最低要求版本
cmake_minimum_required(VERSION 3.1.0)

# 工程名
project(easypr)

#**************************************************************************************************
# Preprocessor definitions 一些预定义设置
#**************************************************************************************************
# c++11 required cmake 3.1.0以上生效
set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED ON)


SET(CMAKE_C_FLAGS  "-fPIC")  
SET(CMAKE_CXX_FLAGS "-fPIC") 


if (CMAKE_SYSTEM_NAME MATCHES "Darwin")
  set(CMAKE_PREFIX_PATH ${CMAKE_PREFIX_PATH} "/usr/local/opt/opencv3")
endif()


# 查找 OpenCV
find_package(OpenCV 3.1.0 REQUIRED)

# opencv头文件依赖可以不写
if(OpenCV_FOUND)
    include_directories(${OpenCV_INCLUDE_DIRS})
else()
    MESSAGE(FATAL_ERROR "opencv 3.1.0 not found")
endif()

#根目录设置
SET(EASYPR_BASE_DIR ${CMAKE_CURRENT_SOURCE_DIR})

#源文件设置
AUX_SOURCE_DIRECTORY(${EASYPR_BASE_DIR}/esayprjni/src OBJ_JNI)

AUX_SOURCE_DIRECTORY(${EASYPR_BASE_DIR}/src OBJ_EXPORT)

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

        ${EASYPR_BASE_DIR}/../EasyPR/src/util/util.cpp
	    ${EASYPR_BASE_DIR}/../EasyPR/src/util/program_options.cpp
	    ${EASYPR_BASE_DIR}/../EasyPR/src/util/kv.cpp
        )

set(EXE_SRC ${EASYPR_BASE_DIR}/easyprtest/main.cpp)

#头文件包含目录
include_directories(${EASYPR_BASE_DIR}/../EasyPR)
include_directories(${EASYPR_BASE_DIR}/../EasyPR/include)
include_directories(${EASYPR_BASE_DIR}/include)
# include_directories(${EASYPR_BASE_DIR}/easyprjni/include)

# #用$ENV{JAVA_HOME}获取 用于加载jni头文件 前提配置好JAVA_HOME
# INCLUDE_DIRECTORIES($ENV{JAVA_HOME}/include)
# if(WIN32)
#     INCLUDE_DIRECTORIES($ENV{JAVA_HOME}/include/win32)
# ELSEIF(APPLE)
#     INCLUDE_DIRECTORIES($ENV{JAVA_HOME}/include/darwin)
# ELSE() 
#     INCLUDE_DIRECTORIES($ENV{JAVA_HOME}/include/linux)
# endif()

link_directories(./)
#生成easyprcore静态库
set(CORE_LIB easyprcore)
add_library(${CORE_LIB} STATIC ${OBJ_CORE})
target_link_libraries(${CORE_LIB} ${OpenCV_LIBS})

#生成easyprjni动态库
set(JNI_LIB easyprjni)
add_library(${JNI_LIB} SHARED ${OBJ_JNI} ${OBJ_EXPORT})
target_link_libraries(${JNI_LIB} ${CORE_LIB} ${OpenCV_LIBS})

#生成可执行文件
add_executable(easypr ${OBJ_EXPORT} ${EXE_SRC})
target_link_libraries(easypr ${CORE_LIB} ${OpenCV_LIBS})