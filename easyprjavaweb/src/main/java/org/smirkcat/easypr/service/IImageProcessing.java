package org.smirkcat.easypr.service;

/**
 * 数字图像处理服务接口
 * @author smirklijie    2016年6月22日15:39:23
 *
 */
public interface IImageProcessing {
	/**
	 * 车牌识别识别
	 * @param imagebyte
	 * @return 返回json格式字符串类型
	 */
	public String plate(byte[] imagebyte);
	
	
}
