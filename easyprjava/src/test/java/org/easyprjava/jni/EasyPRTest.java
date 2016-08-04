package org.easyprjava.jni;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.smirkcat.loaddll.JarDllJava;

public class EasyPRTest 
{
   
    public static void main(String[] args)
    {
    	//获取当前类所在的根目录
        String path=JarDllJava.rootPath(EasyPR.class);
        EasyPR fun=new EasyPR(path+"model");
        
        byte[] img=setImageToByteArray(path+"test.jpg");
        if(img==null)
        	return;
        String result=fun.plateRecognize( img);
        fun.delete();
        System.out.println(result);
        
    }
    
    /**
	 * 图片文件目录转化为二进制
	 * 
	 * @param fileName
	 * @return byte[]
	 */
	public static byte[] setImageToByteArray(String fileName) {
		File file = new File(fileName);
		return setImageToByteArray(file);
	}

	/**
	 * 图片文件转化为二进制
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] setImageToByteArray(File file) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			byte[] filebuff = new byte[fis.available()];
			fis.read(filebuff);
			fis.close();
			return filebuff;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return null;
	}
}
