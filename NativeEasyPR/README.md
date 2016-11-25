克隆代码之后初始化更新一下子模块 git submodule update --init --recursive

### 配置opencv环境(windows版)
+ 添加环境变量jdk的根目录%JAVA_HOME%，以及opencv3.1.0的bulid根目录%OPENCV310%
+ 替换EasyPR文件，替换所需文件在[EasyPR-change](../EasyPR-change)下，一共三个文件,同名替换，也可以参考修改即可
  + 替换[EasyPR/include/easypr/core](../EasyPR/include/easypr/core)下的[chars_identify.h](../EasyPR/include/easypr/core/chars_identify.h)头文件
  + 替换[EasyPR/src/core](../EasyPR/src/core)下的[chars_identify.cpp](../EasyPR/src/core/chars_identify.cpp)源文件
  + 替换[EasyPR/src/core](../EasyPR/src/core)下的[plate_judge.cpp](../EasyPR/src/core/plate_judge.cpp)源文件
+ 然后用vs2013打开[NativeEasyPR.sln](NativeEasyPR.sln)即可

###　测试效果如下图
![easyprtes-cpp效果图](easyprtest/shows.png)