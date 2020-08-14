package com.mrkj.ygl.controller;


import com.mrkj.ygl.service.MainPageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Controller
public class MainPageController {
    @RequestMapping("/saveUeditorContent")
    public ModelAndView saveUEditor(HttpServletRequest request, String content, String mainTitle){
        ModelAndView mav = new ModelAndView();
        String mainCreateUser = request.getRemoteAddr();
        int result = mps.saveMainContent(content, mainTitle, mainCreateUser);
        if(result == 1){
            mav.setViewName("redirect:/goMainPage");
        }else{
            mav.setViewName("myJSP/error");
        }
        return mav;
    }

    @Resource
    MainPageService mps;
    @RequestMapping("/goMainPage")
    public ModelAndView goMainPage(HttpServletRequest request,
                                   @RequestParam(name="page",required = false, defaultValue = "1") Integer page,
                                   @RequestParam(name="row",required = false, defaultValue = "40") Integer row){
        ModelAndView mav = new ModelAndView("myJSP/mainPage");
        List<Map<String, Object>> mainContents = mps.getMainPage((page-1) * row, row);
        mav.addObject("main", mainContents);
        long count = mps.getMainCount();
        String pageHtml = mps.getPage(count, page, row);
        mav.addObject("pageHtml", pageHtml);

        return mav;
    }
}
