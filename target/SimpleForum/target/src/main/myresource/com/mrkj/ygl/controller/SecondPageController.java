package com.mrkj.ygl.controller;

import com.mrkj.ygl.service.SecondPageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class SecondPageController {
    @Resource
    SecondPageService sps;

    @RequestMapping("/goSecondPage")
    public ModelAndView goSecondPage(String mainId,
                                     @RequestParam(name="page", required = false, defaultValue = "1") Integer page,
                                     @RequestParam(name="row", required = false, defaultValue = "40") Integer row ){
        ModelAndView mav = new ModelAndView("myJSP/secondPage");
        Map<String, Object> mainAndSeconds = sps.getMainAndSeconds(mainId, (page-1)*row, row);
        mav.addObject("mainAndSeconds", mainAndSeconds);
        return mav;
    }

    @RequestMapping("/saveSecondPage")
    public ModelAndView saveSecondPage(String mainId, HttpServletRequest request, String content){
        ModelAndView mav = new ModelAndView();
        String mainCreateuser = request.getRemoteAddr();
        int result = sps.saveSecondPage(mainId, content, mainCreateuser);
        if(result == 1){
            mav.setViewName("redirect:/secondPageContent?mainId="+mainId);
        }else{
            mav.setViewName("404");
        }
        return mav;
    }
}
