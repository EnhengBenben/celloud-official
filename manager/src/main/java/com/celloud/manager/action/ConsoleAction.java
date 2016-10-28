package com.celloud.manager.action;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.manager.constants.ConstantsData;
import com.celloud.manager.constants.UserRole;
import com.celloud.manager.model.User;
import com.celloud.manager.service.ConsoleService;

/**
 * 
 *
 * @author han
 * @date 2016年3月10日 上午10:52:21
 */
@Controller
public class ConsoleAction {
    Logger logger=LoggerFactory.getLogger(ConsoleAction.class);
    @Resource
    private ConsoleService consoleService;
    
    @RequestMapping("console")
    public ModelAndView console(){
        ModelAndView mv=new ModelAndView("console/console");
        User user=ConstantsData.getLoginUser();
        Map<String,Object> map=null;
        if(user!=null){
            Integer role=user.getRole();
            if(UserRole.ADMINISTRATOR.equals(role)){//超级管理员
                map=consoleService.totalStatistics(null);
            }
            if(UserRole.BIG_CUSTOMER.equals(role)){//大客户
                map=consoleService.totalStatistics(user.getCompanyId());
            }
        }
        mv.addObject("browserData", consoleService.getBrowserData());
        mv.addObject("resultMap", map);
        return mv;
    }
    
    @ResponseBody
    @RequestMapping("console/data")
    public Map<String,Object> getStatisticsData(){
        User user=ConstantsData.getLoginUser();
        if(user!=null){
            Integer role=user.getRole();
            if(UserRole.ADMINISTRATOR.equals(role)){//超级管理员
                return consoleService.getStatisticsData(null);
            }
            if(UserRole.BIG_CUSTOMER.equals(role)){//大客户
                return consoleService.getStatisticsData(user.getCompanyId());
            }
        }
        return null;
    }
}
