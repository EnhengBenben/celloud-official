package com.celloud.backstage.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.backstage.constants.ConstantsData;
import com.celloud.backstage.constants.IconConstants;
import com.celloud.backstage.model.App;
import com.celloud.backstage.model.Classify;
import com.celloud.backstage.model.Company;
import com.celloud.backstage.model.FileFormat;
import com.celloud.backstage.model.User;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.AppService;
import com.celloud.backstage.service.ClassifyService;
import com.celloud.backstage.service.CompanyService;
import com.celloud.backstage.service.FileFormatService;
import com.celloud.backstage.service.UserService;
import com.celloud.backstage.utils.FileTools;
import com.celloud.backstage.utils.PropertiesUtil;

@Controller
public class AppAction {
    Logger logger=LoggerFactory.getLogger(AppAction.class);
    @Resource
    private AppService appService;
    @Resource
    private CompanyService companyService;
    @Resource
    private ClassifyService classifyService;
    @Resource
    private FileFormatService fileFormatService;
	@Resource
	private UserService userService;
    
	@RequestMapping("app/getUsersByApp")
	public ModelAndView getUsersByApp(Integer appId) {
		ModelAndView mv = new ModelAndView("app/to_show_app_right");
		List<User> list = userService.getUserByAppId(appId);
		String path = createEmailFile(list);
		mv.addObject("userList", list).addObject("path", path);
		return mv;
	}

	@RequestMapping("app/downEmailFile")
	@ResponseBody
	public void downEmailFile(String path) {
		FileTools.fileDownLoad(ConstantsData.getResponse(), path);
	}

	private String createEmailFile(List<User> list) {
		String path = PropertiesUtil.weeklyReportPath + "email_" + new Date().getTime() + ".txt";
		StringBuffer sb = new StringBuffer();
		for (User user : list) {
			sb.append(user.getEmail()).append(";");
		}
		FileTools.createFile(path);
		FileTools.appendWrite(path, sb.toString());
		return path;
	}

    @RequestMapping("app/appList")
    public ModelAndView getAppListByPage(@RequestParam(defaultValue="1") int currentPage,@RequestParam(defaultValue="10") int pageSize){
        ModelAndView mv=new ModelAndView("app/app_list");
        Page page=new Page(currentPage, pageSize);
        PageList<App> pageList=appService.getAppByPage(page);
        mv.addObject("pageList", pageList);
        return mv;
    }

	@RequestMapping("app/appRight")
	public ModelAndView getAppRight(@RequestParam(defaultValue = "1") int currentPage,
			@RequestParam(defaultValue = "10") int pageSize) {
		ModelAndView mv = new ModelAndView("app/app_right");
		Page page = new Page(currentPage, pageSize);
		PageList<App> pageList = appService.getAppByPage(page);
		mv.addObject("pageList", pageList);
		return mv;
	}
    
    @ResponseBody
    @RequestMapping("app/on")
    public int appOn(@RequestParam("appId") Integer appId){
        return appService.updateAppOn(appId);
    }
    
    @ResponseBody
    @RequestMapping("app/off")
    public int appOff(@RequestParam("appId") Integer appId){
        return appService.updateAppOff(appId);
    }
    
    @RequestMapping("app/toEditApp")
    public ModelAndView toEditApp(HttpServletRequest request){
        ModelAndView mv=new ModelAndView("app/app_edit");
        String appId=request.getParameter("appId");
        if(StringUtils.isNotBlank(appId)){
            Map<String, Object> appMap = appService
                    .getAppMapById(Integer.parseInt(appId));
            if (appMap != null) {
                // app.setClassifyIds(";" + app.getClassifyIds() + ";");
                // app.setFileFormatIds(";"+app.getFileFormatIds()+";");

                mv.addObject("app", appMap);
            }
        }
        List<Company> companyList=companyService.getAllCompany();
        List<Classify> classifyList=classifyService.getLeaf();
        List<FileFormat> fileFormatList=fileFormatService.getFileFormatList();
        mv.addObject("companyList", companyList);
        mv.addObject("classifyList", classifyList);
        mv.addObject("fileFormatList", fileFormatList);
        return mv;
    }
    
    
    @RequestMapping(value = "app/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") CommonsMultipartFile file, HttpSession session) {
        String fileName = file.getOriginalFilename();
        String type = fileName.substring(fileName.lastIndexOf("."));
		File targetFile = new File(IconConstants.getTempPath(new ObjectId().toString() + type));
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            logger.error("图片上传失败：{}", fileName, e);
        }
        return targetFile.getName();
    }
    
    /**
     * 获取已上传未保存的临时图片
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "app/icon/temp", method = RequestMethod.GET)
    public ResponseEntity<byte[]> pictureTemp(String file) throws IOException {
		String path = IconConstants.getTempPath(file);
        File targetFile = new File(path);
        logger.info("app图片临时目录的绝对路径{}",targetFile.getAbsolutePath());
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }
    
    /**
     * 获取已保存app图标
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "app/icon", method = RequestMethod.GET)
    public ResponseEntity<byte[]> appIcon(String file) throws IOException {
		String path = IconConstants.getAppPath(file);
        File targetFile = new File(path);
        logger.info("app图标的绝对路径{}",targetFile.getAbsolutePath());
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }
    
    /**
     * 获取已保存app截图
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "app/screen", method = RequestMethod.GET)
    public ResponseEntity<byte[]> appScreen(String file) throws IOException {
		String path = IconConstants.getScreenPath(file);
        File targetFile = new File(path);
        logger.info("app截图的绝对路径{}",targetFile.getAbsolutePath());
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }
    
    @ResponseBody
    @RequestMapping(value = "app/save", method = RequestMethod.POST)
    public int save(App app,Integer[]classifyIds,Integer[]fileFormatIds,String[]screenName,String[] delScreenName){

        if(app.getAppId()!=null){
            logger.info("修改appId={}",app.getAppId());
            return appService.updateApp(app, screenName, delScreenName, fileFormatIds, classifyIds);
        }else{
            logger.info("新增app");
            return appService.addApp(app, screenName, fileFormatIds, classifyIds);  
        }
        
    }
    
    @ResponseBody
    @RequestMapping("app/appNameExist")
    public int appNameExist(@RequestParam("appName")String appName,@RequestParam("appId")Integer appId){
        return appService.appNameExist(appId,appName);
    }
}
