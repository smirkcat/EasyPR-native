package org.smirkcat.easypr.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.smirkcat.easypr.service.IImageProcessing;
import org.smirkcat.easypr.util.ToImageByte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/handle")
public class ImageDemoController {
	
	@Autowired
	IImageProcessing handle;
	
	@RequestMapping(value="/carPlateDectect",produces="text/html;charset=UTF-8")
    public  String findFormFile(@RequestParam("inputimage") MultipartFile image) throws IOException{
		byte[] imagebyte=ToImageByte.input2byte(image.getInputStream());
		return handle.plate(imagebyte);
	}
	@RequestMapping(value="/carPlateDectect",produces="text/html;charset=UTF-8",method = RequestMethod.GET)
    public  String idNumIdentify(@RequestParam("url") String url) throws IOException{
		byte[] imagebyte=ToImageByte.urlTo(url);
		return handle.plate(imagebyte);
	}
	@RequestMapping(value="/carPlateDectect",produces="text/html;charset=UTF-8",method = RequestMethod.POST)
    public  String idNumIdentify(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String img=request.getParameter("img");
		byte[] imagebyte = ToImageByte.StringBase64UrlTo(img);
		return handle.plate(imagebyte);
	}
}
