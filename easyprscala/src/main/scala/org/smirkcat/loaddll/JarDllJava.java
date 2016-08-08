package org.smirkcat.loaddll;

import java.io.File;

//这个类是为了提供给scala删除windows临时文件
public class JarDllJava {

	// windows删除tempDir所有文件
	public static void main(String[] args) throws InterruptedException  {
		File tmpdir = new File(System.getProperty("java.io.tmpdir"));
		File tempDir = new File(args[0]);
		if (!tmpdir.equals(tempDir.getParentFile()) || !tempDir.getName().startsWith("dll")) {
			return;
		}
		for (File file : tempDir.listFiles()) {
			while (file.exists() && !file.delete()) {
				Thread.sleep(100);
			}
		}
		tempDir.delete();
	}




}
