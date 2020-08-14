package com.mrkj.ygl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class Test02Controller {

    @RequestMapping("/gotest02")
    public ModelAndView Test02(){
        ModelAndView mav = new ModelAndView("myJSP/test02");
        return mav;
    }


}
