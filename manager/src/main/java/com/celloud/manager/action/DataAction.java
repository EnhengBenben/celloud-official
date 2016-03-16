package com.celloud.manager.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.manager.constants.ConstantsData;
import com.celloud.manager.constants.UserRole;
import com.celloud.manager.model.User;
import com.celloud.manager.service.DataService;

/**
 * 
 *
 * @author han
 * @date 2016年3月14日 下午4:18:07
 */
@Controller
public class DataAction {
    @Resource
    private DataService dataService;

    @RequestMapping("dataCount")
    public ModelAndView dataCount(){
        ModelAndView mv=new ModelAndView("data/data_count");
        User user=ConstantsData.getLoginUser();
        Integer dataNum=null;
        Long dataSize=null;
        List<Map<String,Object>> monData=null;
        if(user!=null){
            Integer role=user.getRole();
            mv.addObject("userRole", role);
            if(UserRole.ADMINISTRATOR.equals(role)){//超级管理员
                dataNum=dataService.totalDataNum(null);
                dataSize=dataService.totalDataSize(null);
                monData=dataService.getBigCustomerDataCountByMon(null,"desc");
            }
            if(UserRole.BIG_CUSTOMER.equals(role)){//大客户
                dataNum=dataService.totalDataNum(user.getCompanyId());
                dataSize=dataService.totalDataSize(user.getCompanyId());
                monData=dataService.getBigCustomerDataCountByMon(user.getCompanyId(),"desc");
            }
        }
        if(dataSize!=null){
            if ((dataSize >> 30) > 0) {
                dataSize=dataSize / (1024 * 1024 * 1024);
                mv.addObject("unit", "(GB)");
            } else if ((dataSize >> 20) > 0) {
                dataSize=dataSize / (1024 * 1024);
                mv.addObject("unit", "(MB)");
            } else if ((dataSize >> 20) > 0) {
                dataSize=dataSize / (1024);
                mv.addObject("unit", "(KB)");
            }
        }
        mv.addObject("monData", monData);
        mv.addObject("dataNum", dataNum);
        mv.addObject("dataSize", dataSize);
        return mv;
    }
    
    @ResponseBody
    @RequestMapping("data/dataMon")
    public Object getDataByMon(){
        User user=ConstantsData.getLoginUser();
        Map<String,Object> resultMap=null;
        if(user!=null){
            resultMap=new HashMap<String,Object>();
            Integer role=user.getRole();
            if(UserRole.ADMINISTRATOR.equals(role)){//超级管理员
                resultMap.put("adminData", dataService.getBigCustomerDataCountByMon());
                resultMap.put("dataMon", dataService.getBigCustomerDataCountByMon(null,"asc"));
            }
            if(UserRole.BIG_CUSTOMER.equals(role)){//大客户
                resultMap.put("dataMon", dataService.getBigCustomerDataCountByMon(user.getCompanyId(),"asc"));
            }
        }
        return resultMap;
    }
    
    @RequestMapping("userDataCount")
    public ModelAndView userDataCount(){
        ModelAndView mv=new ModelAndView("data/data_user");
        User user=ConstantsData.getLoginUser();
        List<Map<String,Object>> userDataCount=null;
        if(user!=null){
            Integer role=user.getRole();
            mv.addObject("userRole", role);
            if(UserRole.ADMINISTRATOR.equals(role)){//超级管理员
                userDataCount=dataService.getBigCustomerDataCountByUser(null);
            }
            if(UserRole.BIG_CUSTOMER.equals(role)){//大客户
                userDataCount=dataService.getBigCustomerDataCountByUser(user.getCompanyId());
            }
        }
        mv.addObject("userDataCount", userDataCount);
        return mv;
    }
    
    @ResponseBody
    @RequestMapping("data/dataUser")
    public List<Map<String,Object>> getDataByUser(){
        User user=ConstantsData.getLoginUser();
        List<Map<String,Object>> userDataCount=null;
        if(user!=null){
            Integer role=user.getRole();
            if(UserRole.ADMINISTRATOR.equals(role)){//超级管理员
                userDataCount=dataService.getBigCustomerDataCountByUser(null);
            }
            if(UserRole.BIG_CUSTOMER.equals(role)){//大客户
                userDataCount=dataService.getBigCustomerDataCountByUser(user.getCompanyId());
            }
        }
        return userDataCount;
    }
    
    @RequestMapping("companyDataCount")
    public ModelAndView companyDataCount(){
        ModelAndView mv=new ModelAndView("data/data_company");
        User user=ConstantsData.getLoginUser();
        List<Map<String,Object>> companyDataCount=null;
        if(user!=null){
            Integer role=user.getRole();
            mv.addObject("userRole", role);
            if(UserRole.ADMINISTRATOR.equals(role)){//超级管理员
                companyDataCount=dataService.getBigCustomerDataCountByCompany(null);
            }
            if(UserRole.BIG_CUSTOMER.equals(role)){//大客户
                companyDataCount=dataService.getBigCustomerDataCountByCompany(user.getCompanyId());
            }
        }
        mv.addObject("companyDataCount", companyDataCount);
        return mv;
    }
    
    @ResponseBody
    @RequestMapping("data/dataCompany")
    public List<Map<String,Object>> getDataByCompany(){
        User user=ConstantsData.getLoginUser();
        List<Map<String,Object>> companyDataCount=null;
        if(user!=null){
            Integer role=user.getRole();
            if(UserRole.ADMINISTRATOR.equals(role)){//超级管理员
                companyDataCount=dataService.getBigCustomerDataCountByCompany(null);
            }
            if(UserRole.BIG_CUSTOMER.equals(role)){//大客户
                companyDataCount=dataService.getBigCustomerDataCountByCompany(user.getCompanyId());
            }
        }
        return companyDataCount;
    }
}
