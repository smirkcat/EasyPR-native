### 说明
+ jni写法例子参考我的另外两个项目[ImgJni][1],[scalacpptest][2]
+ 加载动态库路径解决参考项目[loaddll][3]
+ 测试前请安装opencv3.1.0，配置方法见NativeEasyPR

#### windows
+ 把NativeEasyPR 生成的库 easyprjni.dll 复制到[src/main/resources/dll](src/main/resources/dll)目录下

#### linux 
+ 把NativeEasyPR 生成的库 easyprjni.dll 复制到[src/main/resources/dll](src/main/resources/dll)目录下，改名easyprjni.so

#### 运行
+ 此项目是maven工程项目，导入用最新版eclipse或者intellij-idea即可
+ 测试请运行src/test下org.easypr.jni包下面的EasyPRTest类
+ 命令行运行
```
$ java org.easyprjava.jni.EasyPRTest
```

### 运行结果如下图
![EasyPRTest.java效果图](shows.png)

[1]: https://git.oschina.net/smirkcat/ImgJni.git
[2]: https://git.oschina.net/smirkcat/scalacpptest.git
[3]: https://git.oschina.net/smirkcat/loaddll.git