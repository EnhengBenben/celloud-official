package com.celloud.manager.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.manager.constants.ConstantsData;
import com.celloud.manager.constants.UserRole;
import com.celloud.manager.model.User;
import com.celloud.manager.service.DataService;
import com.celloud.manager.utils.PropertiesUtil;

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
        Long dataSize = null;
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
    
    @ResponseBody
    @RequestMapping(value="data/dataMon/{companyId}",method=RequestMethod.POST)
    public List<Map<String,Object>> getDataByMon(@PathVariable Integer companyId){
        User user=ConstantsData.getLoginUser();
        List<Map<String,Object>> dataMon=null;
        if(user!=null){
            Integer role=user.getRole();
            if(UserRole.ADMINISTRATOR.equals(role)&&companyId!=null){//超级管理员
                dataMon=dataService.getBigCustomerDataCountByMon(companyId,"asc");
            }
            
        }
        return dataMon;
    }
    
    @RequestMapping(value="data/dataMon/{companyId}",method=RequestMethod.GET)
    public ModelAndView countDataByMon(@PathVariable Integer companyId){
        ModelAndView mv=new ModelAndView("data/data_each_bigCustomer");
        User user=ConstantsData.getLoginUser();
        List<Map<String,Object>> dataMon=null;
        if(user!=null){
            Integer role=user.getRole();
            if(UserRole.ADMINISTRATOR.equals(role)&&companyId!=null){//超级管理员
                dataMon=dataService.getBigCustomerDataCountByMon(companyId,"desc");
            }
            
        }
        mv.addObject("dataMon", dataMon);
        return mv;
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
    
    @RequestMapping("bigCustomerDataCount")
    public ModelAndView bigCustomerDataCount(){
        ModelAndView mv=new ModelAndView("data/data_bigCustomer");
        User user=ConstantsData.getLoginUser();
        List<Map<String,Object>> bigCustomerDataCount=null;
        if(user!=null){
            Integer role=user.getRole();
            mv.addObject("userRole", role);
            if(UserRole.ADMINISTRATOR.equals(role)){//超级管理员
                bigCustomerDataCount=dataService.getBigCustomerDataCount();
            }
        }
        mv.addObject("bigCustomerDataCount", bigCustomerDataCount);
        return mv;
    }
    
    @ResponseBody
    @RequestMapping("data/dataBigCustomer")
    public List<Map<String,Object>> getBigCustomerData(){
        User user=ConstantsData.getLoginUser();
        List<Map<String,Object>> bigCustomerDataCount=null;
        if(user!=null){
            Integer role=user.getRole();
            if(UserRole.ADMINISTRATOR.equals(role)){//超级管理员
                bigCustomerDataCount=dataService.getBigCustomerDataCount();
            }
        }
        return bigCustomerDataCount;
    }
    @RequestMapping("dataExport")
    public ModelAndView dataExport(){
        ModelAndView mv=new ModelAndView("data/data_export");
        User user=ConstantsData.getLoginUser();
        List<User> userList=null;
        if(user!=null){
            Integer role=user.getRole();
            mv.addObject("userRole", role);
            if(UserRole.ADMINISTRATOR.equals(role)){//超级管理员
                userList=dataService.getUser(null);
            }
            if(UserRole.BIG_CUSTOMER.equals(role)){//大客户
                userList=dataService.getUser(user.getCompanyId());
            }
        }
        mv.addObject("userList", userList);
        return mv;
    }
    
    @RequestMapping("data/export")
    public ResponseEntity<byte[]> download(String userIds,String start,String end) throws Exception {
        List<Map<String,Object>> list=dataService.getUserData(userIds, start, end);
        StringBuffer sb = new StringBuffer(
                "user_id\tusername\tdata_key\tfile_name\tcreate_date\tpath\tapp\n");
        for (Map<String, Object> data : list) {
            sb.append(data.get("user_id")).append("\t").append(data.get("username")).append("\t")
                    .append(data.get("data_key")).append("\t").append(data.get("file_name")).append("\t")
                    .append(data.get("create_date").toString().substring(0, 10)).append("\t").append(data.get("path"))
                    .append("\t").append(data.get("app_name")).append("\n");
        }
        String fileName = new Date().getTime() + ".xls";
        String path = PropertiesUtil.outputPath + fileName;
        File file = new File(path);
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bufferWritter = new BufferedWriter(fw);
        bufferWritter.write(sb.toString());
        bufferWritter.close();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
    
}
