package com.celloud.manager.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.celloud.manager.constants.ConstantsData;
/**
 * 主页
 *
 * @author han
 * @date 2016年3月8日 上午10:36:00
 */
@Controller
public class HomeAction {
    @RequestMapping("index")
    public String index(){
        if(ConstantsData.isLoggedIn()){
            return "index";
        }
        return "redirect:login";
    }
}
