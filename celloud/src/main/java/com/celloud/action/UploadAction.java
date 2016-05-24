package com.celloud.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.celloud.constants.ConstantsData;
import com.celloud.constants.DataState;
import com.celloud.constants.FileFormat;
import com.celloud.constants.TaskPeriod;
import com.celloud.model.mysql.App;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Experiment;
import com.celloud.model.mysql.Task;
import com.celloud.service.AppService;
import com.celloud.service.DataService;
import com.celloud.service.ExperimentService;
import com.celloud.service.ReportService;
import com.celloud.service.TaskService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.CheckFileTypeUtil;
import com.celloud.utils.DataUtil;
import com.celloud.utils.DateUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.MD5Util;
import com.celloud.utils.PerlUtils;
import com.celloud.utils.PropertiesUtil;

/**
 * 多文件上传
 *
 * @author han
 * @date 2015年12月28日 下午3:12:11
 */
@Controller
@RequestMapping("upload")
public class UploadAction {
    Logger logger = LoggerFactory.getLogger(UploadAction.class);
    private static final int BUFFER_SIZE = 2 * 1024;
    @Resource
    private AppService appService;
    @Resource
    private ReportService reportService;
    @Resource
    private DataService dataService;
    @Resource
    private ExperimentService expService;
    @Resource
    private TaskService taskService;
    private String realPath = PropertiesUtil.bigFilePath;

    /**
     * 用于判断数据格式
     */
    CheckFileTypeUtil checkFileType = new CheckFileTypeUtil();

    /**
     * 
     * 数据上传
     * 
     * @return
     * @date 2015年12月28日 下午3:22:38
     */
    @ActionLog(value = "上传数据", button = "开始上传")
    @ResponseBody
    @RequestMapping("uploadManyFile")
    public String uploadManyFile(String name, String onlyName, String md5,
            String originalName, Integer chunk, Integer chunks,
            HttpServletRequest request, Integer tagId, String batch,
            Integer needSplit) {
        File f = new File(realPath);
        if (!f.exists()) {
            boolean isTrue = f.mkdir();
            if (!isTrue) {
                logger.error("路径创建失败：{}", realPath);
            }
        }
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        Integer dataId = 0;
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    String myFileName = file.getOriginalFilename();
                    if (!"".equals(myFileName.trim())) {
                        String fileName = realPath + File.separatorChar + name;
                        File localFile = new File(fileName);
                        try {
                            this.copy(file, localFile);
                            if (chunk.equals(chunks)
                                    || chunk.equals(chunks - 1)) {
                                dataId = addFileInfo(originalName);
								String fileDataKey = DataUtil.getNewDataKey(dataId);
								String newName = fileDataKey + FileTools.getExtName(originalName);
								Integer userId = ConstantsData.getLoginUserId();
								String today = DateUtil.getDateToString("yyyyMMdd");
								String folderByDay = realPath + userId + File.separator + today;
								File pf = new File(folderByDay);
								if(!pf.exists()){
									pf.mkdirs();
								}
								FileTools.mvFile(realPath, name, folderByDay, newName);
								String perlPath = request.getSession().getServletContext().getRealPath("/resources")
										+ "/plugins/getAliases.pl";
								String outPath = request.getSession().getServletContext().getRealPath("/temp") + "/"
										+ fileDataKey;
                                int fileFormat = checkFileType
                                        .checkFileType(newName, folderByDay);
                                updateFileInfo(dataId, fileDataKey, newName,
                                        perlPath, outPath, folderByDay, batch,
                                        fileFormat);
                                Subject sub = SecurityUtils.getSubject();
                                if (sub.hasRole("bsier")) {
                                    logger.info("{}拥有百菌探权限", userId);
                                    return bsierCheckRun(tagId, batch, dataId,
                                            fileDataKey,
                                            needSplit, newName, folderByDay,
                                            originalName, userId, fileFormat);
                                }
							}
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }
                }
                // 记录上传该文件后的时间

            }
        }
        return "1";
    }

    /**
     * 判断是否上传完即刻运行
     * 
     * @param tagId
     * @param batch
     * @param dataId
     * @param needSplit
     * @param newName
     * @param folderByDay
     * @param originalName
     * @param userId
     * @param fileFormat
     * @return
     * @author leamo
     * @date 2016年5月10日 下午3:41:08
     */
    @ActionLog(value = "判断是否上传完即刻运行", button = "上传完即刻运行")
    private String bsierCheckRun(Integer tagId, String batch, Integer dataId,
            String dataKey,
            Integer needSplit, String newName, String folderByDay,
            String originalName, Integer userId, Integer fileFormat) {
        logger.info("判断是否数据{}上传完即刻运行", originalName);
        App app = appService.findAppsByTag(tagId);
        Integer appId = needSplit == 1 ? 113 : app.getAppId();
        String pubName = "";
        List<Integer> dataIds;
        if (fileFormat == FileFormat.FQ || originalName.contains(".txt")
                || originalName.contains(".lis")) {
            Boolean isR1 = false;
            if (originalName.contains("R1")) {
                pubName = originalName.substring(0,
                        originalName.lastIndexOf("R1"));
                isR1 = true;
            } else if (originalName.contains("R2")) {
                pubName = originalName.substring(0,
                        originalName.lastIndexOf("R2"));
            } else if (originalName.contains(".txt")
                    || originalName.contains(".lis")) {
                pubName = originalName.substring(0,
                        originalName.lastIndexOf("."));
            }
            List<DataFile> dlist = dataService.getDataByBatchAndFileName(userId,
                    batch, pubName);
            boolean hasR1 = false;
            boolean hasR2 = false;
            boolean hasIndex = false;
            dataIds = new ArrayList<>();
            for (DataFile d : dlist) {
                String name_tmp = d.getFileName();
                if (name_tmp.contains("R1")) {
                    hasR1 = true;
                } else if (name_tmp.contains("R2")) {
                    hasR2 = true;
                } else if (name_tmp.contains(".txt")
                        || name_tmp.contains(".lis")) {
                    hasIndex = true;
                }
                dataIds.add(d.getFileId());
            }
            Task task = new Task();
            task.setUserId(userId);
            task.setDataKey(dataKey);
            task.setPeriod(TaskPeriod.UPLOADING);
            task.setParams(pubName);
            task.setAppId(appId);
            taskService.addOrUpdateUploadTaskByParam(task, isR1);
            if (needSplit == 1 && hasR1 && hasR2 && hasIndex) {
                return "{\"dataIds\":\""
                        + StringUtils.join(dataIds.toArray(), ",")
                        + "\",\"appIds\":\"" + appId + "\"}";
            } else if (needSplit != 1 && hasR1 && hasR2) {
                task.setAppId(appId);
                taskService.addOrUpdateUploadTaskByParam(task, isR1);
                return "{\"dataIds\":\""
                        + StringUtils.join(dataIds.toArray(), ",")
                        + "\",\"appIds\":\"" + appId + "\"}";
            }
        } else if (fileFormat == FileFormat.YASUO) {
            return "\"dataIds\":" + dataId + ",\"appIds\":" + appId;
        }
        return "1";
    }

    /**
     * 
     *
     * @param file原始文件
     * @param dst 目标文件
     * @author han
     * @date 2016年1月13日 上午10:04:06
     */
    @ActionLog(value = "将缓存的数据读取到共享存储", button = "开始上传")
    private void copy(MultipartFile file, File dst) {
        InputStream in = null;
        OutputStream out = null;
        try {
            if (dst.exists()) {
                out = new BufferedOutputStream(new FileOutputStream(dst, true),
                        BUFFER_SIZE);
                in = new BufferedInputStream(file.getInputStream(), BUFFER_SIZE);
                byte[] buffer = new byte[BUFFER_SIZE];
                int len = 0;
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                out.flush();
            } else {
                file.transferTo(dst);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    
    /**
     * 将上传文件添加到数据库中
     * 
     * @return
     */
    @ActionLog(value = "上传完成的数据保存数据库", button = "开始上传")
    private int addFileInfo(String fileName) {
        Integer userId = ConstantsData.getLoginUserId();
        DataFile data = new DataFile();
        data.setUserId(userId);
        // 只允许字母和数字
        String regEx = "[^\\w\\.\\_\\-\u4e00-\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(fileName);
        // replaceAll()将中文标号替换成英文标号
        data.setFileName(m.replaceAll("").trim());
        data.setState(DataState.DEELTED);
        return dataService.addDataInfo(data);
    }

    /**
     * 检查管理员页面是否超时
     * 
     * @return
     */
    @ActionLog(value = "检查上传页面是否超时", button = "开始上传")
    @ResponseBody
    @RequestMapping("checkAdminSessionTimeOut")
    public String checkAdminSessionTimeOut() {
        return ConstantsData.getLoginUserName();
    }

    /**
     * 修改文件信息
     * 
     * @param dataId
     * @param dataKey
     * @param newName
     * @return
     */
    @ActionLog(value = "修改文件详细信息", button = "开始上传")
    private int updateFileInfo(int dataId, String dataKey, String newName,
            String perlPath, String outPath, String folderByDay, String batch,
            int fileFormat) {
        DataFile data = new DataFile();
        data.setFileId(dataId);
		String filePath = folderByDay + File.separator + newName;
        data.setSize(FileTools.getFileSize(filePath));
        data.setDataKey(dataKey);
        data.setPath(filePath);
        data.setMd5(MD5Util.getFileMD5(filePath));
        data.setBatch(batch);
        data.setFileFormat(fileFormat);
        if (fileFormat == FileFormat.BAM) {
            String anotherName = getAnotherName(filePath, dataKey, perlPath, outPath);
            data.setAnotherName(anotherName);
			// 绑定实验流程
			if (!StringUtils.isBlank(anotherName)) {
				Integer userId = ConstantsData.getLoginUserId();
				List<Experiment> expList = expService.getUnRelatList(userId, anotherName);
				if (expList != null && expList.size() == 1) {
					Experiment exp = expList.get(0);
					exp.setFileId(dataId);
					exp.setDataKey(dataKey);
					expService.updateByPrimaryKeySelective(exp);
					logger.info("用户{}数据{}自动绑定成功", userId, dataId);
				} else {
					logger.error("用户{}数据{}自动绑定失败", userId, dataId);
				}
			}
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
