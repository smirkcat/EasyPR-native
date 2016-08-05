package org.smirkcat.easypr.util;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
/*Base64解码。相关测试性能最好。在jdk1.6之后有(隐藏类，没有提供api实现)，如果报错请用最新版jdk8+*/
import java.util.Base64;


import javax.imageio.ImageIO;

/**
 * 
 * @author smirklijie
 * 功能类，用于各种图片来源转化为二进制byte[]
 */
public class ToImageByte {
	/**
	 * 图片文件base64位文件转化为byte[]
	 * @param imageBase64
	 * @return
	 */
	public static final byte[] StringBase64UrlTo(String imageBase64){
		if(imageBase64==null || "".equals(imageBase64))
			return null;
		//是否有逗号分隔，js解析会有前缀
		String[] img=imageBase64.split(",");
		byte[] imagebyte;
		if(img.length<2){
			imagebyte = Base64.getDecoder().decode(imageBase64);
		}else{
			imagebyte = Base64.getDecoder().decode(img[1]);
		}
		return imagebyte;
		
	}
	
	/**
	 * 图片url转化为二进制，没有返回空
	 * @param urlImage
	 * @return
	 */
	public static final byte[] urlTo(String urlImage) {
		//判断进来的图片
		if(urlImage==null || "".equals(urlImage))
			return null;
		try {
			URL url=new URL(urlImage);
			//此处一定要用BufferedImage读入，不能用直接用InputStream
			BufferedImage image = ImageIO.read(url);
			//假如没有图片直接返回空，不然服务器会宕掉
			if(image==null){
				return null;
			}
			ByteArrayOutputStream os = new ByteArrayOutputStream(); 
			ImageIO.write(image, "jpeg", os);
			byte[] is=os.toByteArray();
			os.close();
			return is;
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return null;
	}
	/**
	 * 输入流转化为二进制
	 * @param inStream
	 * @return
	 * @throws IOException
	 */
	public static final byte[] input2byte(InputStream inStream)  
            throws IOException {  
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
        	swapStream.write(buffer, 0, len);  
        }  
        return  swapStream.toByteArray();
    }
}
