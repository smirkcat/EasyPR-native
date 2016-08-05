package org.smirkcat.easypr.controller;

import javax.servlet.http.HttpServletRequest; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
  
@RestController 
@RequestMapping(value="/demo")
public class DemoController{  

    @RequestMapping(value="/processing")
    public  ModelAndView faceDetectPage(HttpServletRequest request){
    	ModelAndView mv = new ModelAndView("imagedemo");
		return mv;
	}
}  
