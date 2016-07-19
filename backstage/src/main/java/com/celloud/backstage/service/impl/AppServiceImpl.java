package com.celloud.backstage.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.celloud.backstage.constants.AppConstants;
import com.celloud.backstage.constants.AppOffline;
import com.celloud.backstage.constants.AppPermission;
import com.celloud.backstage.mapper.AppMapper;
import com.celloud.backstage.mapper.ScreenMapper;
import com.celloud.backstage.model.App;
import com.celloud.backstage.model.Screen;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.AppService;

@Service("appService")
public class AppServiceImpl implements AppService{
    Logger logger=LoggerFactory.getLogger(AppServiceImpl.class);
    @Resource
    private AppMapper appMapper;
    
    @Resource
    private ScreenMapper screenMapper;

    @Override
    public List<App> getAppListByCompany(Integer companyId) {
        if(companyId==null){
            return null;
        }
        return appMapper.getAppListByCompany(companyId,AppPermission.PRIVATE, AppOffline.ON);
    }
        

    @Override
    public List<App> getAppListPulbicAdded() {
        return appMapper.getAppListAdded(AppPermission.PUBLIC, AppOffline.ON);
    }


    @Override
    public PageList<App> getAppByPage(Page page) {
        List<App> list=appMapper.getAppListByPage(page);
        return new PageList<>(page, list);
    }


    @Override
    public int updateAppOn(Integer appId) {
        if(appId!=null){
            return appMapper.updateAppOffline(AppOffline.ON, appId);
        }
        return 0;
    }


    @Override
    public int updateAppOff(Integer appId) {
        if(appId!=null){
            return appMapper.updateAppOffline(AppOffline.OFF, appId);
        }
        return 0;
    }


    @Override
    public int addApp(App app, String[] screenNames,Integer[] formatIds,Integer[] calssifyIds) {
        int flag=0;
        try {
            app.setOffLine(AppOffline.ON);
            appMapper.insertApp(app);
            Integer appId=app.getAppId();
            if(appId!=null){
                String pictureName=app.getPictureName();
                if(StringUtils.isNotBlank(pictureName)){
                        FileUtils.moveFile(new File(AppConstants.getAppTempPath() + File.separator + pictureName),
                                new File(AppConstants.geAppPicturePath(pictureName)));
                }
                if(screenNames!=null&&screenNames.length>0){
                    for(String screenName:screenNames){
                        Screen screen=new Screen();
                        screen.setAppId(appId);
                        screen.setScreenName(screenName);
                        int result=screenMapper.insertScreen(screen);
                        if(result>0){
                                FileUtils.moveFile(new File(AppConstants.getAppTempPath() + File.separator + screenName),
                                        new File(AppConstants.geAppScreenPath(screenName)));
                        }
                    }
                    
                }
                if(formatIds!=null&&formatIds.length>0){
                    appMapper.insertAppFileFormatBatch(appId, formatIds);
                }
                if(calssifyIds!=null&&calssifyIds.length>0){
                    appMapper.insertAppClassifyBatch(appId, calssifyIds);
                }
                cleanAppTemp();
               flag=1;
            }
            
        } catch (Exception e) {
            logger.error("新增App异常：{}",e.getMessage());
        }
        return flag;
    }


    @Override
    public int updateApp(App app,String[]screenNames,String[] delScreenNames,Integer[] formatIds,Integer[] calssifyIds) {
        if(app==null||app.getAppId()==null){
            return 0;
        }
        int flag=0;
        try {
            App a=appMapper.selectByPrimaryKey(app.getAppId());
            
            String oldPictureName=a.getPictureName();
            String newPictureName=app.getPictureName();
            
            a.setAddress(app.getAddress());
            a.setAppDoc(app.getAppDoc());
            a.setAppName(app.getAppName());
            a.setAttribute(app.getAttribute());
            a.setCommand(app.getCommand());
            a.setCompanyId(app.getCompanyId());
            a.setDataNum(app.getDataNum());
            a.setDescription(app.getDescription());
            a.setEnglishName(app.getEnglishName());
            a.setFlag(app.getFlag());
            a.setIntro(app.getIntro());
            a.setMaxTask(app.getMaxTask());
            a.setMethod(app.getMethod());
            a.setParam(app.getParam());
            a.setPictureName(app.getPictureName());
            a.setRunData(app.getRunData());
            a.setRunType(app.getRunType());
            a.setTitle(app.getTitle());
            @SuppressWarnings("unused")
            int result=appMapper.updateApp(a);
            if(StringUtils.isNotBlank(oldPictureName)&&!oldPictureName.equals(newPictureName)){
                File delFile=new File(AppConstants.geAppPicturePath(oldPictureName));
                if(delFile.exists()){
                    FileUtils.deleteQuietly(delFile);
                }
           }
           if(StringUtils.isNotBlank(newPictureName)&&!newPictureName.equals(oldPictureName)){
               try {
                   FileUtils.moveFile(new File(AppConstants.getAppTempPath() + File.separator + newPictureName),
                           new File(AppConstants.geAppPicturePath(newPictureName)));
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           appMapper.deleteAppClassify(a.getAppId());
           if(calssifyIds!=null&&calssifyIds.length>0){
               appMapper.insertAppClassifyBatch(a.getAppId(), calssifyIds);
           }
           appMapper.deleteAppFileFormat(a.getAppId());
           if(formatIds!=null&&formatIds.length>0){
               appMapper.insertAppFileFormatBatch(a.getAppId(), formatIds);
           }
           if(delScreenNames!=null && delScreenNames.length>0){
               for(String del:delScreenNames){
                   screenMapper.deleteByAppId(a.getAppId(), del);
               }
           }
           if(screenNames!=null&&screenNames.length>0){
               for(String screenName:screenNames){
                   Screen screen=new Screen();
                   screen.setAppId(a.getAppId());
                   screen.setScreenName(screenName);
                   int r=screenMapper.insertScreen(screen);
                   if(r>0){
                           FileUtils.moveFile(new File(AppConstants.getAppTempPath() + File.separator + screenName),
                                   new File(AppConstants.geAppScreenPath(screenName)));
                   }
               }
               
           }
           cleanAppTemp();
           flag=1;
        } catch (Exception e) {
            logger.error("更新App异常：{}",e.getMessage());
        }
        return flag;
    }
    
    private void cleanAppTemp(){
        String path = AppConstants.getAppTempPath();
        File tempDir = new File(path);
        if (tempDir == null || !tempDir.exists()) {
            return;
        }
        if (tempDir.isFile()) {
            tempDir.delete();
            return;
        }
        File[] tempFiles = tempDir.listFiles();
        if(tempFiles==null||tempFiles.length==0){
            return ;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date date = calendar.getTime();
        for (File file : tempFiles) {
            ObjectId id = new ObjectId(file.getName().substring(0, file.getName().indexOf(".")));
            if (date.after(id.getDate())) {
                file.delete();
            }
        }
    }


    @Override
    public int appNameExist(Integer appId,String appName) {
        return appMapper.appNameExist(appId,appName);
    }


    @Override
    public App getAppById(Integer appId) {
        if(appId==null){
            return null;
        }
        return appMapper.getAppById(appId);
    }

    @Override
    public List<App> getAllApp() {
        return appMapper.getAllApp(AppOffline.ON);
    }

    @Override
    public Map<String, Object> getAppMapById(Integer appId) {
        return appMapper.getAppMapById(appId);
    }
    
}
