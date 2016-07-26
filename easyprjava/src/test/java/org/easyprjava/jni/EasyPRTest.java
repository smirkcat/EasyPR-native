package org.easyprjava.jni;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.smirkcat.loaddll.JarDllJava;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class EasyPRTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public EasyPRTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( EasyPRTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
        String path=JarDllJava.rootPath(EasyPR.class);
        String modelpath=path+"../../../EasyPR/resources/model";
        System.out.println(modelpath);
        EasyPR fun=new EasyPR(modelpath);
        
        System.out.println("继续");
        byte[] img=setImageToByteArray(JarDllJava.rootPath(EasyPR.class)+"test.jpg");
        String result=fun.plateRecognize(fun.ptrNative, img);
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
