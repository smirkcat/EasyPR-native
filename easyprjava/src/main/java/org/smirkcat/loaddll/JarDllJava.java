package org.smirkcat.loaddll;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @see https://git.oschina.net/smirkcat/loaddll
 * @author smirklijie
 *
 */
public class JarDllJava {

	// 动态库解压位置文件属性，用File应该是方便跨平台获取绝对路径以及创建
	static File tempDir = null;
	// 系统平台动态库后缀名
	static String systemType = null;
	// 动态库扩展名
	static String libExtension = null;

	/**
	 * 此处代码来源 https://github.com/bytedeco/javacpp/blob/master/src/main/java/org/
	 * bytedeco/javacpp/Loader.java#L393-L407 public static File getTempDir()
	 * //392行 我提取出来是为了方便，二是框架有点大，直接用掌握不了
	 * 
	 * @param tempDir
	 * @return 如果tempDir存在则返回原值，不存在则在临时文件夹下创建与时间相关的问价夹
	 */
	public static File getTempDir(File tempDir) {
		if (tempDir == null) {
			File tmpdir = new File(System.getProperty("java.io.tmpdir"));
			File f = null;
			for (int i = 0; i < 1000; i++) {
				f = new File(tmpdir, "dll" + System.nanoTime());
				if (f.mkdir()) {
					tempDir = f;
					tempDir.deleteOnExit();
					break;
				}
			}
		}
		return tempDir;
	}

	static {
		// https://github.com/bytedeco/javacpp/blob/master/src/main/java/org/bytedeco/javacpp/Loader.java#L68-L96
		systemType = System.getProperty("os.name");
		String osName = systemType.toLowerCase();
		if (osName.indexOf("win") != -1) {
			libExtension = ".dll";
		} else if (osName.indexOf("mac") != -1) {
			libExtension = ".dylib";
		} else {
			libExtension = ".so";
		}

		// shut down hook 
		// 虚拟机关闭之前执行的钩子
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (tempDir == null) {
					return;
				}
				if (tempDir.exists()) {
					// windows测试不会删除，javacpp这么说，我也测试过确实删不掉
					// 所以采用java命令删除。调用下面的main函数删除，不得不佩服谷歌大神
					if (libExtension == ".dll") {
						try {
							// ... to launch a separate process ...
							List<String> command = new ArrayList<String>();
							command.add(System.getProperty("java.home") + "/bin/java");
							command.add("-classpath");
							command.add((new File(
									JarDllJava.class.getProtectionDomain().getCodeSource().getLocation().toURI()))
											.toString());
							command.add(JarDllJava.class.getName());
							command.add(tempDir.getAbsolutePath());//args[0]
							new ProcessBuilder(command).start();
						} catch (IOException e) {
							throw new RuntimeException(e);
						} catch (URISyntaxException e) {
							throw new RuntimeException(e);
						}
					}

				}
			}
		});
	}

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

	/**
	 * classpath路径获取
	 * 
	 * @param cls
	 * @return
	 */
	public static String rootPath(Class<?> cls) {
		String rootPath = cls.getResource("/").getFile().toString();
		// 特别注意rootPath返回有斜杠，linux和mac下不需要去掉，windows需要去掉
		//返回文件名末尾也有斜杠/
		if ((systemType.toLowerCase().indexOf("win") != -1)) {
			// windows下去掉斜杠
			rootPath = rootPath.substring(1, rootPath.length());
		}
		return rootPath;
	}

	/**
	 * 加载dllpath下的libName库文件，windows libName.dll linux libName.so mac
	 * libName.dylib 会把动态库问价解压到jar包同级目录下
	 * 
	 * @param libName
	 *            库文件名没有后缀
	 * @param dllpath
	 *            库文件所在文件夹 /dll/ 相对于jar包里根目录
	 * @param cls
	 *            动态库所在包的类
	 * @throws IOException
	 *             抛出异常
	 */
	public static void loadLib(String libName, String dllpath, Class<?> cls) throws IOException {

		String libFullName = dllpath + libName + libExtension;
		InputStream in = cls.getResourceAsStream(libFullName);
		if(in==null){
			return;
		}
		BufferedInputStream reader = new BufferedInputStream(in);
		tempDir = getTempDir(tempDir);
		String filepath = tempDir.getAbsolutePath() + "/" + libName + libExtension;
		FileOutputStream writer = null;
		File extractedLibFile = new File(filepath);
		if (!extractedLibFile.exists()) {
			try {
				writer = new FileOutputStream(extractedLibFile);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, len);
				}
				in.close();
				reader.close();
				writer.close();
				System.load(extractedLibFile.toString());
			} catch (IOException e) {
				if (extractedLibFile.exists()) {
					extractedLibFile.delete();
				}
				throw e;
			}finally {
				in.close();
				reader.close();
				if(writer!=null)
					writer.close();
				// 此行代码windows是无法执行的，linux和os x已经测试，可以删除
				if(extractedLibFile.exists())
					extractedLibFile.deleteOnExit();
			}
		}
	}
}
