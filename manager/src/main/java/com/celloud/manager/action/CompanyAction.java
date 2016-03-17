package com.celloud.manager.action;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.manager.constants.ConstantsData;
import com.celloud.manager.constants.UserRole;
import com.celloud.manager.model.User;
import com.celloud.manager.service.CompanyService;

/**
 * 医院统计
 *
 * @author han
 * @date 2016年3月16日 下午3:07:02
 */
@Controller
public class CompanyAction {
    @Resource
    private CompanyService companyService;
    
    @RequestMapping("company/guide")
    public ModelAndView companyGuideCount(){
        ModelAndView mv=new ModelAndView("company/company_guide");
        User user=ConstantsData.getLoginUser();
        Map<String,Object> map=null;
        if(user!=null){
            Integer role=user.getRole();
            if(UserRole.ADMINISTRATOR.equals(role)){//超级管理员
                map=companyService.companyGuideCount(null);
            }
            if(UserRole.BIG_CUSTOMER.equals(role)){//大客户
                map=companyService.companyGuideCount(user.getCompanyId());
            }
        }
        mv.addObject("resultMap", map);
        return mv;
    }
    @ResponseBody
    @RequestMapping("company/guideData")
    public Object getCompanyGuideCount(){
        User user=ConstantsData.getLoginUser();
        Map<String,Object> resultMap=null;
        if(user!=null){
            Integer role=user.getRole();
            if(UserRole.ADMINISTRATOR.equals(role)){//超级管理员
                resultMap=companyService.getCompanyGuideData(null);
                resultMap.put("adminData", companyService.getBigCustomerUserCountByMon());
            }
            if(UserRole.BIG_CUSTOMER.equals(role)){//大客户
                resultMap=companyService.getCompanyGuideData(user.getCompanyId());
            }
        }
        return resultMap;
    }
}
