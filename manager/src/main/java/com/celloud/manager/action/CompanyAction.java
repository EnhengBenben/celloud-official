package com.celloud.manager.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.manager.constants.ConstantsData;
import com.celloud.manager.constants.UserRole;
import com.celloud.manager.model.App;
import com.celloud.manager.model.Company;
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
    
    @RequestMapping("company/baseInfo")
    public ModelAndView companyBaseInfo(){
        ModelAndView mv=new ModelAndView("company/company_baseInfo");
        User user=ConstantsData.getLoginUser();
        List<Company> list=null;
        if(user!=null){
            Integer role=user.getRole();
            if(UserRole.ADMINISTRATOR.equals(role)){//超级管理员
                list=companyService.getCompany(null);
            }
            if(UserRole.BIG_CUSTOMER.equals(role)){//大客户
                list=companyService.getCompany(user.getCompanyId());
            }
        }
        mv.addObject("companyList", list);
        return mv;
    }
    @RequestMapping("company/reportCount")
    public ModelAndView companyReportCount(){
        ModelAndView mv=new ModelAndView("company/company_report");
        User user=ConstantsData.getLoginUser();
        List<App> list=null;
        List<Map<String, Object>> dataList=null;
        if(user!=null){
            Integer role=user.getRole();
            if(UserRole.ADMINISTRATOR.equals(role)){//超级管理员
                list=companyService.getAppOfBigCustomer(null);
                dataList=companyService.getCompanyReport(null);
            }
            if(UserRole.BIG_CUSTOMER.equals(role)){//大客户
                list=companyService.getAppOfBigCustomer(user.getCompanyId());
                dataList=companyService.getCompanyReport(null);
            }
        }
        mv.addObject("appList", list);
        mv.addObject("dataList", dataList);
        return mv;
    }
    
    @RequestMapping("company/bigCustomer")
    public ModelAndView companyBigCustomerCount(){
        ModelAndView mv=new ModelAndView("company/company_bigCustomer");
        User user=ConstantsData.getLoginUser();
        List<Map<String, Object>> dataList=null;
        if(user!=null){
            Integer role=user.getRole();
            if(UserRole.ADMINISTRATOR.equals(role)){//超级管理员
                dataList=companyService.bigCustomerDataCount();
            }
        }
        mv.addObject("dataList", dataList);
        return mv;
    }
    
    @ResponseBody
    @RequestMapping("company/companyNum")
    public Object getCompanyNumCount(){
        User user=ConstantsData.getLoginUser();
        List<Map<String,Object>> resultMap=null;
        if(user!=null){
            Integer role=user.getRole();
            if(UserRole.ADMINISTRATOR.equals(role)){//超级管理员
                resultMap=companyService.getCompanyNumCount();
            }
        }
        return resultMap;
    }
}
