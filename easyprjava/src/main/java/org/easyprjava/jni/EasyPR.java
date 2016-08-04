package org.easyprjava.jni;

import org.smirkcat.loaddll.JarDllJava;

public class EasyPR {

	// 函数指针地址，需要init函数初始化
	protected int ptrNative;

	public EasyPR() {
		ptrNative = init();
	}

	public EasyPR(String path) {
		ptrNative = initPath(path);
	}

	/**
	 * 实现图片的传图，返回相关信息,私有函数
	 * @param ptrNative
	 * @param img
	 * @return
	 */
	protected native String plateRecognize(int ptrNative, byte[] img);

	/**
	 * 初始化类的模型文件
	 * 
	 * @param path
	 *            模型文件顶级目录
	 * @return
	 */
	protected native int initPath(String path);

	/**
	 * 预留函数，初始化类
	 * 
	 * @return
	 */
	protected native int init();

	/**
	 * 删除c++类，删除前确信不在调用，不然需重新执行init函数给ptrNative赋值 只能执行一次，两次出错，内存释放只有一次
	 * @param ptrNative
	 * @return
	 */
	protected native void delete(int ptrNative);

	// 静态调用只执行一次，加载本地库 ,编译好的动态放到src/main/resources下面
	static {
		try {
			JarDllJava.loadLib("easyprjni", "/dll/", EasyPR.class);
			System.out.println("加载动态库easyprjni完成");
		} catch (Exception e) {
			System.err.println("加载动态库easyprjni失败，错误日志:" + e.getMessage());
		}
	}
	/**
	 * 提供对外处理的接口
	 * @param img
	 * @return
	 */
	public String plateRecognize(byte[] img){
		return plateRecognize(ptrNative, img);
	}
	/**
	 * 对外提供的释放c++内存函数
	 */
	public void delete(){
		delete(ptrNative);
		ptrNative=0;
	}

}
