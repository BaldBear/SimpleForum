package com.mrkj.ygl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyFirstController {

    @RequestMapping("/mytest")
    public ModelAndView myTest(){
        ModelAndView mav = new ModelAndView("myJSP/test01");
        return mav;
    }
}
