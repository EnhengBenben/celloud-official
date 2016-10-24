package com.celloud.manager.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
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
import com.celloud.manager.service.WeekService;
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
    @Resource
    private WeekService weekService;

    @RequestMapping("getSiteInfo")
    public ModelAndView getSiteInfo(Integer site) {

        ModelAndView mv = new ModelAndView("data/data_siteInfo");
        User user = ConstantsData.getLoginUser();
        if (user != null) {
            Map<Integer, Map<String, String>> siteInfo = null;
            Integer role = user.getRole();
            if (UserRole.ADMINISTRATOR.equals(role)) {// 超级管理员
                siteInfo = this.dataService.getSiteInfo(null, site);
            }
            if (UserRole.BIG_CUSTOMER.equals(role)) {// 大客户
                siteInfo = this.dataService.getSiteInfo(user.getCompanyId(), site);
            }
            mv.addObject("siteInfo", siteInfo);
        }
        return mv;
    }

    @RequestMapping("otherSiteCount")
    public ModelAndView otherSiteCount() {
        ModelAndView mv = new ModelAndView("data/data_otherSite");
        User user = ConstantsData.getLoginUser();
        Map<String, Map<String, String>> otherSiteCount = null;
        if (user != null) {
            Integer role = user.getRole();
            mv.addObject("userRole", role);
            if (UserRole.ADMINISTRATOR.equals(role)) {// 超级管理员
                otherSiteCount = dataService.getHBVOtherSiteByUserId(null);
            }
            if (UserRole.BIG_CUSTOMER.equals(role)) {// 大客户
                otherSiteCount = dataService.getHBVOtherSiteByUserId(user.getCompanyId());
            }
        }
        mv.addObject("othserSiteCount", otherSiteCount);
        return mv;
    }

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
        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), "gbk");
        BufferedWriter bufferWritter = new BufferedWriter(write);
        bufferWritter.write(sb.toString());
        bufferWritter.close();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
    
    @RequestMapping("weekDataCount")
    public ModelAndView weekDataCount() {
        ModelAndView mv = new ModelAndView("data/data_week");
        Integer companyId = ConstantsData.getLoginUser().getCompanyId();
        // 获取本周top10
        List<Map<String, Object>> weekUserLogin = dataService.getWeekUserLogin(companyId);
        List<Map<String, Object>> weekAppRun = dataService.getWeekAppRun(companyId);
        List<Map<String, Object>> weekDataSize = dataService.getWeekDataSize(companyId);
        List<Map<String, Object>> weekData = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < weekUserLogin.size(); i++) {
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("logUsername", weekUserLogin.size() > i ? weekUserLogin.get(i).get("username") : "无");
            temp.put("logCount", weekUserLogin.size() > i ? weekUserLogin.get(i).get("log_count") : 0);
            temp.put("appName", weekAppRun.size() > i ? weekAppRun.get(i).get("app_name") : "无");
            temp.put("appCount", weekAppRun.size() > i ? weekAppRun.get(i).get("app_count") : 0);
            temp.put("sizeUsername", weekDataSize.size() > i ? weekDataSize.get(i).get("username") : "无");
            temp.put("sizeSum", weekDataSize.size() > i ? weekDataSize.get(i).get("size_sum") : 0);
            temp.put("fileCount", weekDataSize.size() > i ? weekDataSize.get(i).get("file_count") : 0);
            weekData.add(i, temp);
        }
        // 获取历史周统计
        List<Map<String, Object>> historyWeekUserLogin = dataService.getHistoryWeekUserLogin(companyId);
        List<Map<String, Object>> historyWeekActiveUser = dataService.getHistoryWeekActiveUser(companyId);
        List<Map<String, Object>> historyWeekAppRun = dataService.getHistoryWeekAppRun(companyId);
        List<Map<String, Object>> historyWeekAppActive = dataService.getHistoryWeekAppActive(companyId);
        List<Map<String, Object>> historyWeekDataSize = dataService.getHistoryWeekDataSize(companyId);
        List<Map<String, Object>> historyWeekData = new ArrayList<Map<String, Object>>();
        // 构造临时map追加数据key为日期,value为list中的map
        Map<String, Map<String, Object>> appendMap = new HashMap<String, Map<String, Object>>();
        for (int i = historyWeekUserLogin.size() - 1; i >= 0; i--) {
            String startDate = (String) historyWeekUserLogin.get(i).get("start_date");
            String endDate = (String) historyWeekUserLogin.get(i).get("end_date");
            Map<String, Object> listMap = new HashMap<String, Object>();
            listMap.put("historyDate", startDate);
            listMap.put("endDate", endDate);
            listMap.put("historyWeekUserLogin",
                    historyWeekUserLogin.get(i).get("start_date").equals(startDate)
                            ? historyWeekUserLogin.get(i).get("log_count")
                            : 0);
            if (historyWeekActiveUser.size() > i) {
                if (historyWeekActiveUser.get(i).get("start_date").equals(startDate)) {
                    listMap.put("historyWeekActiveUser", historyWeekActiveUser.get(i).get("active_user"));
                } else {
                    if (appendMap.get(historyWeekActiveUser.get(i).get("start_date")) == null) {
                        Map<String, Object> newMap = new HashMap<String, Object>();
                        newMap.put("historyDate", (String) historyWeekActiveUser.get(i).get("start_date"));
                        newMap.put("endDate", (String) historyWeekActiveUser.get(i).get("end_date"));
                        newMap.put("historyWeekActiveUser", historyWeekActiveUser.get(i).get("active_user"));
                        appendMap.put((String) historyWeekActiveUser.get(i).get("start_date"), newMap);
                    } else {
                        appendMap.get(historyWeekActiveUser.get(i).get("start_date")).put("historyWeekAppRun",
                                historyWeekActiveUser.get(i).get("active_user"));
                    }
                    listMap.put("historyWeekActiveUser", 0);
                }
            } else {
                listMap.put("historyWeekActiveUser", 0);
            }
            
            if (historyWeekAppRun.size() > i) {
                if (historyWeekAppRun.get(i).get("start_date").equals(startDate)) {
                    listMap.put("historyWeekAppRun", historyWeekAppRun.get(i).get("run_app"));
                } else {
                    if (appendMap.get(historyWeekAppRun.get(i).get("start_date")) == null) {
                        Map<String, Object> newMap = new HashMap<String, Object>();
                        newMap.put("historyDate", (String) historyWeekAppRun.get(i).get("start_date"));
                        newMap.put("endDate", (String) historyWeekAppRun.get(i).get("end_date"));
                        newMap.put("historyWeekAppActive", historyWeekAppRun.get(i).get("run_app"));
                        appendMap.put((String) historyWeekAppRun.get(i).get("start_date"), newMap);
                    } else {
                        appendMap.get(historyWeekAppRun.get(i).get("start_date")).put("historyWeekAppRun",
                                historyWeekAppRun.get(i).get("run_app"));
                    }
                    listMap.put("historyWeekAppRun", 0);
                }
            } else {
                listMap.put("historyWeekAppRun", 0);
            }

            if (historyWeekAppActive.size() > i) {
                if (historyWeekAppActive.get(i).get("start_date").equals(startDate)) {
                    listMap.put("historyWeekAppActive", historyWeekAppActive.get(i).get("active_app"));
                } else {
                    if (appendMap.get(historyWeekAppActive.get(i).get("start_date")) == null) {
                        Map<String, Object> newMap = new HashMap<String, Object>();
                        newMap.put("historyDate", (String) historyWeekAppActive.get(i).get("start_date"));
                        newMap.put("endDate", (String) historyWeekAppActive.get(i).get("end_date"));
                        newMap.put("historyWeekAppActive", historyWeekAppActive.get(i).get("active_app"));
                        appendMap.put((String) historyWeekAppActive.get(i).get("start_date"), newMap);
                    } else {
                        appendMap.get(historyWeekAppActive.get(i).get("start_date")).put("historyWeekAppActive",
                                historyWeekAppActive.get(i).get("active_app"));
                    }
                    listMap.put("historyWeekAppActive", 0);
                }
            } else {
                listMap.put("historyWeekAppActive", 0);
            }

            if (historyWeekDataSize.size() > i) {
                if (historyWeekDataSize.get(i).get("start_date").equals(startDate)) {
                    listMap.put("historyWeekDataSize", historyWeekDataSize.get(i).get("size_sum"));
                    listMap.put("historyWeekFileCount", historyWeekDataSize.get(i).get("file_count"));
                } else {
                    if (appendMap.get(historyWeekDataSize.get(i).get("start_date")) == null) {
                        Map<String, Object> newMap = new HashMap<String, Object>();
                        newMap.put("historyDate", (String) historyWeekDataSize.get(i).get("start_date"));
                        newMap.put("endDate", (String) historyWeekDataSize.get(i).get("end_date"));
                        newMap.put("historyWeekDataSize", historyWeekDataSize.get(i).get("size_sum"));
                        newMap.put("historyWeekFileCount", historyWeekDataSize.get(i).get("file_count"));
                        appendMap.put((String) historyWeekDataSize.get(i).get("start_date"), newMap);
                    } else {
                        appendMap.get(historyWeekDataSize.get(i).get("start_date")).put("historyWeekDataSize",
                                historyWeekDataSize.get(i).get("size_sum"));
                        appendMap.get(historyWeekDataSize.get(i).get("start_date")).put("historyWeekFileCount",
                                historyWeekDataSize.get(i).get("file_count"));
                    }
                    listMap.put("historyWeekDataSize", 0);
                    listMap.put("historyWeekFileCount", 0);
                }
            } else {
                listMap.put("historyWeekDataSize", 0);
                listMap.put("historyWeekFileCount", 0);
            }
            appendMap.put(startDate, listMap);
        }

        int i = 0;
        for (Map.Entry<String, Map<String, Object>> entry : appendMap.entrySet()) {
            historyWeekData.add(i++, entry.getValue());
        }

        mv.addObject("weekData", weekData);
        mv.addObject("historyWeekData", historyWeekData);
        return mv;
    }
    


    @ResponseBody
    @RequestMapping("data/topUserLogin")
    public List<Map<String, Object>> topUserLogin() {
        Integer companyId = ConstantsData.getLoginUser().getCompanyId();
        return dataService.getWeekUserLogin(companyId);
    }
    
    @ResponseBody
    @RequestMapping("data/topAppRun")
    public List<Map<String, Object>> topAppRun() {
        Integer companyId = ConstantsData.getLoginUser().getCompanyId();
        return dataService.getWeekAppRun(companyId);
    }

    @ResponseBody
    @RequestMapping("data/topDataSize")
    public List<Map<String, Object>> topDataSize() {
        Integer companyId = ConstantsData.getLoginUser().getCompanyId();
        return dataService.getWeekDataSize(companyId);
    }

    @ResponseBody
    @RequestMapping("data/historyUserLogin")
    public List<Map<String, Object>> historyUserLogin() {
        Integer companyId = ConstantsData.getLoginUser().getCompanyId();
        return dataService.getHistoryWeekUserLogin(companyId);
    }

    @ResponseBody
    @RequestMapping("data/historyUserActive")
    public List<Map<String, Object>> historyUserActive() {
        Integer companyId = ConstantsData.getLoginUser().getCompanyId();
        return dataService.getHistoryWeekActiveUser(companyId);
    }

    @ResponseBody
    @RequestMapping("data/historyAppRun")
    public List<Map<String, Object>> historyAppRun() {
        Integer companyId = ConstantsData.getLoginUser().getCompanyId();
        return dataService.getHistoryWeekAppRun(companyId);
    }

    @ResponseBody
    @RequestMapping("data/historyAppActive")
    public List<Map<String, Object>> historyAppActive() {
        Integer companyId = ConstantsData.getLoginUser().getCompanyId();
        return dataService.getHistoryWeekAppActive(companyId);
    }

    @ResponseBody
    @RequestMapping("data/historyDataSize")
    public List<Map<String, Object>> historyDataSize() {
        Integer companyId = ConstantsData.getLoginUser().getCompanyId();
        return dataService.getHistoryWeekDataSize(companyId);
    }

}
