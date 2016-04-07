package com.celloud.backstage.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
import com.celloud.backstage.service.DataService;
import com.celloud.backstage.service.UserService;
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

}
