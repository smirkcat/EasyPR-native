package org.smirkcat.easypr.util;

import org.easyprjava.jni.EasyPR;
import org.smirkcat.loaddll.JarDllJava;

public class EasyPRInstance {
	//封装为静态函数调用
	
	private static EasyPR func;
	static{
		String path=JarDllJava.rootPath(EasyPRInstance.class)+"model";
		func=new EasyPR(path);
	}
	
	public static String processing(byte[] img){
		return func.plateRecognize(img);
	}
	
	//something else add
	//single 
	
}
