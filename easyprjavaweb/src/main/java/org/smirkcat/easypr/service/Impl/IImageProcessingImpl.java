package org.smirkcat.easypr.service.Impl;

import org.smirkcat.easypr.service.IImageProcessing;
import org.smirkcat.easypr.util.EasyPRInstance;
import org.smirkcat.easypr.util.ReturnMessage;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;


@Service
public class IImageProcessingImpl implements IImageProcessing{

	public String plate(byte[] imagebyte) {
		JSONObject dataJson;
		if (imagebyte == null) {
			dataJson = new JSONObject(ReturnMessage.imgMessage("no_image", null));
			return dataJson.toString();
		}
		String result=EasyPRInstance.processing(imagebyte);
		if (result== null) {
			dataJson = new JSONObject(ReturnMessage.imgMessage("no_image", null));
			
		}
		else{
			dataJson = new JSONObject(ReturnMessage.imgMessage("yes",result ));
		}
		return dataJson.toString();
	}

}
