package com.celloud.backstage.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.celloud.backstage.constants.ConstantsData;
/**
 * 
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年1月18日 上午11:41:43
 */
@Controller
public class HomeAction {
    @RequestMapping("index")
    public String index() {
        if(ConstantsData.isLoggedIn()){
            return "index";
        }
        return "redirect:login";
    }
}
