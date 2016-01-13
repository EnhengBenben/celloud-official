package com.celloud.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.celloud.model.DataFile;
import com.celloud.service.AppService;
import com.celloud.service.DataService;
import com.celloud.service.ReportService;
import com.celloud.utils.CheckFileTypeUtil;
import com.celloud.utils.DataUtil;
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
    private static final long serialVersionUID = 1L;
    @Resource
    private AppService appService;
    @Resource
    private ReportService reportService;
    @Resource
    private DataService dataService;
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
    @ResponseBody
    @RequestMapping("uploadManyFile")
    public String uploadManyFile(String name, String onlyName, String md5, String originalName, int chunk, int chunks,
            HttpServletRequest request) {
        File f = new File(realPath);
        if (!f.exists()) {
            boolean isTrue = f.mkdir();
            if (!isTrue) {
                logger.error("路径创建失败：{}", realPath);
            }
        }
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
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
                            if ((chunk == chunks - 1) || chunk == chunks) {
                                int dataId = addFileInfo(originalName);
                                String fileDataKey = DataUtil.getNewDataKey(dataId);
                                String newName = fileDataKey + FileTools.getExtName(originalName);
                                FileTools.renameFile(realPath, name, newName);
                                String resultData = dataId + "," + originalName;
                                String perlPath = request.getSession().getServletContext().getRealPath("/plugins")
                                        + "/getAliases.pl";
                                String outPath = request.getSession().getServletContext().getRealPath("/temp") + "/"
                                        + fileDataKey;
                                updateFileInfo(dataId, fileDataKey, newName, perlPath, outPath);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                // 记录上传该文件后的时间

            }
        }
        return "uploadMSuc";
    }

    /**
     * 
     *
     * @param file原始文件
     * @param dst 目标文件
     * @author han
     * @date 2016年1月13日 上午10:04:06
     */
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
            } else {
                file.transferTo(dst);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    
    /**
     * 将上传文件添加到数据库中
     * 
     * @return
     */
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
