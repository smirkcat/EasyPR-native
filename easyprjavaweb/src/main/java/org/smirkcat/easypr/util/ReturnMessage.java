package org.smirkcat.easypr.util;

import java.util.HashMap;
import java.util.Map;


public class ReturnMessage {
	
	public static Map<String,Object> imgMessage(String message,String content){
		Map<String,Object> mapData=new HashMap<String,Object>();
		if(message==null || "".equals(message) || "no_image".equals(message)){
			mapData.put("message", "no_image");
		}
		else {
			mapData.put("message", message);
			mapData.put("content", content);
		}
		return mapData;
	}
}
