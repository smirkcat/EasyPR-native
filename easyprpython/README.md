### 使用了python ctypes方式调用

### 运行
#### windows
+ 把NativeEasyPR 生成的库 easyprexport.dll 复制到当前目录
+ 运行
```
$ chcp 936
$ python py.py
$ python easypr.py
```

#### linux 
+ 把NativeEasyPR 生成的库 libeasyprexport.so 复制到当前目录 改名easyprexport.so
+ 运行
```
$ python py.py
```

得到结果 *蓝牌:苏EUK722*

### 运行结构如下图
![py.py效果图](shows.png)