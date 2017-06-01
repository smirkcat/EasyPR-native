### 使用了node addon c++ object

### node环境安装
- 去官网下载node安装包，[下载地址](https://nodejs.org/en/download)
- 安装时需要选着npm

### 编译环境准备
```bash
$ npm install node-gyp -g
$ npm install iconv-lite
```

### 编译
- 编译前需要依赖NativeEasyPR的编译的包
- linux需要依赖pkg-config
- gyp file参考node-opencv编写
```shell
node-gyp configure # windows 生成可能是MTD 不是MD  
                   # 需要手动打开sln文件 修改对应的编译参数 目前gyp参数设置了没生效
                   # linux还未测试
node-gyp build 
```

### 运行
```bash
$ node app.js
```
得到结果 *蓝牌:苏EUK722*

### 运行结构如下图
![app.js效果图](shows.png)