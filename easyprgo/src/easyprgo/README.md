### 使用了go syscall方式调用

### 运行
#### windows
- 把NativeEasyPR 生成的库 easyprexport.dll 复制到当前目录
- 运行 
```
$ go build easyprwin.go
$ easyprwin.exe
```

#### linux 
- 设置 环境变量LD_LIBRARY_PATH  export LD_LIBRARY_PATH=/root/utils/src/lib 这里为放置libeasyprexport.so的路径
- 运行
```
$ go build easyprlinux.go
$ ./easyprlinux
EUK722;蓝牌:苏
```

得到结果 *蓝牌:苏EUK722*

### windows运行结构如下图
![easypr.go效果图](shows.png)

### linux运行结构如下图
![easypr.go效果图](linuxshow.png)