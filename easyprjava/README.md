### 说明
+ jni写法例子参考我的另外两个项目[ImgJni][1],[scalacpptest][2]
+ 加载动态库路径解决参考项目[loaddll][3]
+ 测试前请安装opencv3.1.0，配置方法见后面

### opencv环境变量设置（暂时只有windows_x64版本）
去官网下载opencv3.1.0之后，假设opencv根目录为xxx, 则需把xxx\build\x64\vc12\bin加进系统path环境变量下

### 运行
+ 此项目是maven工程项目，依赖junit,导入用最新版eclipse或者intellij-idea即可
+ 测试请运行org.easypr.jni包下面的EasyPRTest类，集成环境下右键runas->Junit test(后面改为main函数执行)

### 运行结构如下图
![EasyPRTest.java效果图](shows.png)

[1]: https://git.oschina.net/smirkcat/ImgJni.git
[2]: https://git.oschina.net/smirkcat/scalacpptest.git
[3]: https://git.oschina.net/smirkcat/loaddll.git