### 使用了rust libloading方式调用 目前语言不稳定

### 运行
#### windows 测试两者共享有错误 c++类中含有指针时找不到位置 暂时无法解决
- 把NativeEasyPR 生成的库 easyprexport.dll 复制到当前目录
- 运行
```bash
$ cargo run 
```

#### linux 暂未测试
- 把NativeEasyPR 生成的库 libeasyprexport.so 复制到当前目录 改名easyprexport.so
- 运行
```bash
$ cargo run
```