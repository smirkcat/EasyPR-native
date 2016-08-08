### 说明
+ 此项目是提供车牌识别的java接口，不做源码的任何修改，只在上面增加相关接口
+ EasyPR([github][5])([oschina][1])为此工程的子模块，没有做任何修改
+ [EasyPR-change](EasyPR-change)替换EasyPR中的对应同名的源代码文件，不然无法加载自己定义的路径的模型文件，源代码中有bug
+ [NativeEasyPR](NativeEasyPR)提供c++层的native代码,目前只提供jni
+ [easyprjava](easyprjava)提供EasyPR的java接口
+ [easyprscala](easyprscala)提供EasyPR的scala接口
+ [easyprjavaweb](easyprjavaweb)提供EasyPR的javaweb接口以及页面展示
+ git clone代码之后记得git submodule update -f(强制更新一下子模块,然后按照文档替换相关源文件)
+ 或者下载zip包之后去[EasyPR][5]下载最新代码，如果使用我编译好的dll(x64_vc12)，则无需下载和更新EasyPR

### 本项目地址
+ [github][2]
+ [coding][4]
+ [oschina][3]

### NativeEasyPR中easyprtest-c++测试效果如下
![easyprtes-cpp效果图](NativeEasyPR/easyprtest/shows.png)

具体使用请参见[NativeEasyPR](NativeEasyPR)

### easyprjava EasyPRTest.java测试效果如下(scala类似)
![EasyPRTest.java效果图](easyprjava/shows.png)

具体使用请参见[easyprjava](easyprjava)或者[easyprscala](easyprscala)

### java-web展示效果图如下
![imageDemo效果图](easyprjavaweb/shows.jpg)

具体使用请参见[easyprjavaweb](easyprjavaweb)

[1]: https://git.oschina.net/easypr/EasyPR.git
[2]: https://github.com/smirkcat/EasyPR-native.git
[3]: https://git.oschina.net/smirkcat/EasyPR-native.git
[4]: https://git.coding.net/smirkcat/EasyPR-native.git
[5]: https://github.com/liuruoze/EasyPR.git