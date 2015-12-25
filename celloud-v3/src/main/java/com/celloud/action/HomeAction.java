package com.celloud.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页相关的action
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月23日 下午1:40:56
 */
@Controller
public class HomeAction {
    @RequestMapping("service.html")
    public String service() {
        return "service";
    }

    @RequestMapping("feedBack.html")
    public String feedBack() {
        return "feedBack";
    }
    @RequestMapping("home.html")
    public String home() {
        return "home";
    }
    @RequestMapping("index")
    public String index(){
        return "index";
    }
    @RequestMapping("download.html")
    public String download(){
        return "download";
    }
    @RequestMapping("joinUs.html")
    public String joinUs(){
        return "joinUs";
    }
}
