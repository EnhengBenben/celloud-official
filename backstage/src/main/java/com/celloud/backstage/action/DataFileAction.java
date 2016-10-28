package com.celloud.backstage.action;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.backstage.constants.DataState;
import com.celloud.backstage.constants.FileFormat;
import com.celloud.backstage.model.DataFile;
import com.celloud.backstage.model.User;
import com.celloud.backstage.model.Week;
import com.celloud.backstage.service.DataService;
import com.celloud.backstage.service.UserService;
import com.celloud.backstage.service.WeekService;
import com.celloud.backstage.utils.CheckFileTypeUtil;
import com.celloud.backstage.utils.DataUtil;
import com.celloud.backstage.utils.FileTools;
import com.celloud.backstage.utils.MD5Util;
import com.celloud.backstage.utils.PerlUtils;
import com.celloud.backstage.utils.PropertiesUtil;

/**
 * 
 *
 * @author han
 * @date 2016年2月18日 上午10:41:25
 */
@Controller
public class DataFileAction {
    Logger logger=LoggerFactory.getLogger(DataFileAction.class);
    
    @Resource
    private UserService userService;
    
    @Resource
    private DataService dataService;
    @Resource
    private WeekService weekService;
    
    private static String realPath=PropertiesUtil.bigFilePath;
    /**
     * 用于判断数据格式
     */
    CheckFileTypeUtil checkFileType = new CheckFileTypeUtil();
    
    
    @RequestMapping("dataFile/toDataFileUpload")
    public ModelAndView toDataFileUpload(){
         ModelAndView mv=new ModelAndView("data/data_upload");
         return mv;
     }
    @RequestMapping("dataFile/toDataClean")
    public ModelAndView toDataClean(){
        ModelAndView mv=new ModelAndView("data/data_clean");
        String[] testAccountIds=PropertiesUtil.testAccountIds.split(",");
        List<User> userList=new ArrayList<User>();
        for(String id:testAccountIds){
            User user=userService.selectUserById(Integer.parseInt(id));
            if(user!=null){
                userList.add(user);
            }
        }
        mv.addObject("userList", userList);
        return mv;
    }
    @ResponseBody
    @RequestMapping("dataFile/cleanData")
    public boolean cleanData(Integer userId){
        return dataService.removeData(userId);
    }
    
    @RequestMapping("dataFile/sumitAmount")
    public ModelAndView sumitAmount(@RequestParam("amount") int amount){
         ModelAndView mv=new ModelAndView("data/data_upload_set");
         List<User> userList=userService.getAllUserList();
         mv.addObject("amount", amount);
         mv.addObject("userList", userList);
         return mv;
     }
    
    /**
     * 将本地磁盘中的大数据拷贝到指定目录下
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("dataFile/save")
    public String save(HttpServletRequest request,@RequestParam("fileName")String fileNames,@RequestParam("userId")String userIds) {
       String extName = null;
       StringBuffer sb = new StringBuffer();
       String[]fileName=fileNames.split(",");
       String[]userId=userIds.split(",");
        for (int i=0;i<fileName.length;i++) {
            // 插入数据库
            DataFile data = new DataFile();
            data.setUserId(Integer.parseInt(userId[i]));
            data.setFileName(fileName[i]);
            data.setState(DataState.DEELTED);
            // 旧路径
            String old = realPath + fileName[i];
            if (new File(old).exists()) {
                int dataId = dataService.addDataInfo(data);
                // 构造datakey
                String fileDataKey = DataUtil.getNewDataKey(dataId);
                // 获取后缀
                extName = FileTools.getExtName(fileName[i]);
                // 构造新文件名
                String newName = fileDataKey + extName;
                // 构造新路径
                String path = realPath + newName;
                FileTools.renameFile(realPath, fileName[i], newName);
                @SuppressWarnings("unused")
                long fileSize = new File(path).length();
                @SuppressWarnings("unused")
                int fileType = checkFileType.checkFileType(newName);
                String perlPath = request.getSession().getServletContext().getRealPath("/resources")
                        + "/plugins/getAliases.pl";
                String outPath = request.getSession().getServletContext().getRealPath("/temp") + "/"
                        + fileDataKey;
                this.updateFileInfo(dataId, fileDataKey, newName,perlPath, outPath);
            } else {
                sb.append(fileName[i]).append(",");
            }
        }
        return sb.toString();
    }
    
    
    /**
     * 修改文件信息
     * 
     * @param dataId
     * @param dataKey
     * @param newName
     * @return
     */
    private int updateFileInfo(int dataId, String dataKey, String newName, String perlPath, String outPath) {
        DataFile data = new DataFile();
        data.setFileId(dataId);
        String filePath = realPath + newName;
        data.setSize(FileTools.getFileSize(filePath));
        data.setDataKey(dataKey);
        data.setPath(filePath);
        data.setMd5(MD5Util.getFileMD5(filePath));
        int fileFormat = checkFileType.checkFileType(newName);
        data.setFileFormat(fileFormat);
        if (fileFormat == FileFormat.BAM) {
            String anotherName = getAnotherName(filePath, dataKey, perlPath, outPath);
            data.setAnotherName(anotherName);
        }
        data.setState(DataState.ACTIVE);
        return dataService.updateDataInfoByFileId(data);
    }

    private String getAnotherName(String filePath, String fileDataKey, String perlPath, String outPath) {
        String anotherName = null;
        StringBuffer command = new StringBuffer();
        command.append("perl ").append(perlPath).append(" ").append(filePath).append(" ").append(outPath);
        PerlUtils.excutePerl(command.toString());
        String anothername = FileTools.getFirstLine(outPath);
        if (anothername != null) {
            anothername = anothername.replace(" ", "_").replace("\t", "_");
            String regEx1 = "[^\\w+$]";
            Pattern p1 = Pattern.compile(regEx1);
            Matcher m1 = p1.matcher(anothername);
            anotherName = m1.replaceAll("").trim();
            new File(outPath).delete();
        }
        return anotherName;
    }

    @RequestMapping("/dataFile/updateHistoryToTbWeek")
    public void updateHistoryToTbWeek() {
        Integer companyId = 0;
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
            listMap.put("historyWeekUserLogin", historyWeekUserLogin.get(i).get("start_date").equals(startDate)
                    ? historyWeekUserLogin.get(i).get("log_count") : 0);
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
        for (Map<String, Object> map : historyWeekData) {
            try {
                Week week = new Week();
                week.setStartDate(map.get("historyDate") != null
                        ? new SimpleDateFormat("yyyy-MM-dd").parse(map.get("historyDate").toString()) : null);
                week.setEndDate(map.get("endDate") != null
                        ? new SimpleDateFormat("yyyy-MM-dd").parse(map.get("endDate").toString()) : null);
                week.setLoginCount(Integer.parseInt(
                        map.get("historyWeekUserLogin") != null ? map.get("historyWeekUserLogin").toString() : "0"));
                week.setActiveUser(Integer.parseInt(
                        map.get("historyWeekActiveUser") != null ? map.get("historyWeekActiveUser").toString() : "0"));
                week.setAppRun(Integer.parseInt(
                        map.get("historyWeekAppRun") != null ? map.get("historyWeekAppRun").toString() : "0"));
                week.setActiveApp(Integer.parseInt(
                        map.get("historyWeekAppActive") != null ? map.get("historyWeekAppActive").toString() : "0"));
                week.setDataSize(FileTools.formatFullFileSize(Float.parseFloat(
                        map.get("historyWeekDataSize") != null ? map.get("historyWeekDataSize").toString() : "0")));
                week.setFileCount(Integer.parseInt(
                        map.get("historyWeekFileCount") != null ? map.get("historyWeekFileCount").toString() : "0"));
                weekService.add(week);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
