package com.nova.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.nova.constants.DataState;
import com.nova.constants.FileFormat;
import com.nova.dao.IUploadDao;
import com.nova.sdo.Client;
import com.nova.sdo.Data;
import com.nova.sdo.Report;
import com.nova.service.IClientService;
import com.nova.service.IDataService;
import com.nova.service.IReportService;
import com.nova.service.ISoftwareService;
import com.nova.utils.CheckFileTypeUtil;
import com.nova.utils.DataUtil;
import com.nova.utils.FileTools;
import com.nova.utils.PerlUtils;
import com.nova.utils.PropertiesUtil;

/**
 * 多文件上传
 * 
 * @author <a href="mailto:liuqingxiao@novacloud.com">liuqx</a>
 * @date 2013-5-7下午12:41:39
 * @version Revision: 1.0
 */
@ParentPackage("json-default")
@Action("file")
@Results({
        @Result(name = "client", type = "json", params = { "root", "flag" }),
        @Result(name = "readBigFile", type = "json", params = { "root",
                "message" }),
        @Result(name = "imageUpload", params = { "root", "fileFileName",
                "contentType", "text/html" }, type = "json") })
public class ManyFileUploadAction extends BaseAction {
    Logger log = Logger.getLogger(ManyFileUploadAction.class);
    private static final int BUFFER_SIZE = 2 * 1024;
    private static final long serialVersionUID = 1L;
    @Inject
    private ISoftwareService softwareService;
    @Inject
    private IReportService reportService;
    @Inject
    private IDataService dataService;
    @Inject
    private IClientService clientService;
    @Inject
    private IUploadDao iud;
    private List<File> uploadImages;
    private List<String> uploadImagesFileName;
    private List<String> uploadImagesContentType;
    private String fileDataKey;// 数据编号
    private int userId;// 提交者编号
    private int fileId;// 上传完成后的文件号
    private String realPath = PropertiesUtil.bigFilePath;
    private String newName = "";
    private String resultData;
    private String downName;// 下载文件的文件名

    private Client client;

    private Integer flag;
    // ====================以下为后台上传文件======================================
    private String dataList;
    private String uploadImageFileName;
    private Long fileSize;
    private int fileType;
    private String message;
    private String path;

    // =====================以下是plupload参数====================
    private File file;// 对应前台的 file_data_name 属性
    private String fileFileName;// 分块上传时，此值固定为 blob ，没有什么用处
    private String fileContentType;
    private int chunk;// 当前上传的总块数中的第几块
    private int chunks;// 每个文件切割的总块数
    // 前台 unique_names 属性为 false 时，此变量接收文件原本的名字
    // 前台 unique_names 属性为 true 时，此变量接收唯一标示
    private String name;
    // 前台 unique_names 属性为 true 时，此变量接收文件原本的名字
    private String originalName;
    // 断点续传时的唯一名字
    private String onlyName;
    private String md5;

    /**
     * 将本地磁盘中的大数据拷贝到指定目录下
     * 
     * @return
     */
    public String readBigFile() {
        String extName = null;
        StringBuffer sb = new StringBuffer();
        for (String fileItem : dataList.split(";")) {
            //切割参数
            uploadImageFileName = fileItem.split(",")[0];
            userId = Integer.parseInt(fileItem.split(",")[1]);
            //获取后缀
            extName = FileTools.getExtName(uploadImageFileName);
            int dataId = this.addFileInfo(uploadImageFileName);
            fileDataKey = DataUtil.getNewDataKey(dataId);
            //构造新文件名
            String newName = fileDataKey + extName;
            //构造新路径
            path = PropertiesUtil.bigFilePath + newName;
            //旧路径
            String old = PropertiesUtil.bigFilePath + uploadImageFileName;
            if (new File(old).exists()) {
                FileTools.renameFile(PropertiesUtil.bigFilePath, uploadImageFileName, newName);
                fileSize = new File(path).length();
                fileType = checkFileType.checkFileType(newName);
                this.updateFileInfo(dataId, fileDataKey, newName);
                // this.addFileInfo1(path);
            } else {
                sb.append(uploadImageFileName).append(",");
            }
        }
        message = sb.toString();
        return "readBigFile";
    }

    public String addFileInfo1(String filePath) {
        Data data = new Data();
        data.setUserId(userId);
        data.setSize(fileSize);
        data.setFileName(uploadImageFileName);
        data.setDataKey(fileDataKey);
        data.setFileFormat(fileType);
        if (fileType == FileFormat.BAM) {
            String anotherName = getAnotherName(filePath, fileDataKey);
            data.setAnotherName(anotherName);
        }
        data.setPath(path);
        dataService.addDataInfo(data);
        return "addFileInfo1";
    }
    
    private static String getAnotherName(String filePath,String fileDataKey){
        String anotherName = null;
        StringBuffer command = new StringBuffer();
        String perlPath = ServletActionContext.getServletContext()
                .getRealPath("/plugins") + "/getAliases.pl";
        String outPath = ServletActionContext.getServletContext()
                .getRealPath("/temp") + "/" + fileDataKey;
        command.append("perl ").append(perlPath).append(" ")
                .append(filePath).append(" ").append(outPath);
        PerlUtils.excutePerl(command.toString());
        String anothername = FileTools.getFirstLine(outPath);
        if(anothername!=null){
            anothername = anothername.replace(" ", "_").replace("\t", "_");
            String regEx1 = "[^\\w+$]";
            Pattern p1 = Pattern.compile(regEx1);
            Matcher m1 = p1.matcher(anothername);
            anotherName = m1.replaceAll("").trim();
            new File(outPath).delete();
        }
        return anotherName;
    }

    // ====================以上为后台上传文件======================================

    /**
     * 上传图标和屏幕截图
     * 
     * @return
     */
    public String imageUpload() {
        log.info("imageUpload");
        String newFilePath = null;
        if (flag == 0) {// app图标
            newFilePath = ServletActionContext.getServletContext().getRealPath(
                    "/images/app")
                    + "/" + fileFileName;
        } else {// app截图
            newFilePath = ServletActionContext.getServletContext().getRealPath(
                    "/images/screenshot")
                    + "/" + fileFileName;
        }
        try {
            FileUtils.copyFile(file, new File(newFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "imageUpload";
    }

    /**
     * 医院logo和部门logo上传
     * 
     * @return
     */
    public String hospitalImage() {
        log.info("hospitalImage");
        String newFilePath = null;
        if (flag == 0) {// 医院 logo
            newFilePath = ServletActionContext.getServletContext().getRealPath(
                    "/images/hospitalIcon")
                    + "/" + fileFileName;
        } else {// 科室logo
            newFilePath = ServletActionContext.getServletContext().getRealPath(
                    "/images/deptIcon")
                    + "/" + fileFileName;
        }
        try {
            FileUtils.copyFile(file, new File(newFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "imageUpload";
    }

    /**
     * 上传客户端
     * 
     * @return
     */
    public String addClient() {
        String path = ServletActionContext.getServletContext().getRealPath(
                "/client");
        int size = fileFileName.lastIndexOf(".");
        String name = fileFileName.substring(0, size) + client.getVersion()
                + fileFileName.substring(size, fileFileName.length());
        client.setName(name);
        FileTools.createFile(path + "/" + name);
        try {
            FileUtils.copyFile(file, new File(path + "/" + name));
            flag = clientService.add(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "client";
    }

    /**
     * 用于判断数据格式
     */
    CheckFileTypeUtil checkFileType = new CheckFileTypeUtil();

    public String uploadManyFile() {
        File f = new File(realPath);
        if (!f.exists()) {
            boolean isTrue = f.mkdir();
            if (!isTrue) {
                log.error("路径创建失败：" + realPath);
            }
        }
        if (file != null) {
            copy(file, new File(realPath, name));
            // 分块上传时，html5使用前面的判断，flash使用后面的判断，因为flash不支持分块，其实就一块
            if ((chunk == chunks - 1) || chunk == chunks) {
                int dataId = addFileInfo(originalName);
                fileDataKey = DataUtil.getNewDataKey(dataId);
                newName = fileDataKey + FileTools.getExtName(originalName);
                FileTools.renameFile(realPath, name, newName);
                resultData = dataId + "," + originalName;
                updateFileInfo(dataId, fileDataKey, newName);
            }
        } else {
            log.error("没有获取到文件");
        }
        return "uploadMSuc";
    }

    public String initUpload() {
        flag = iud.getInfo(onlyName);
        return "client";
    }

    /**
     * @param src
     *            ：原始文件
     * @param dst
     *            ：目标文件
     */
    private void copy(File src, File dst) {
        InputStream in = null;
        OutputStream out = null;
        try {
            if (dst.exists()) {
                out = new BufferedOutputStream(new FileOutputStream(dst, true),
                        BUFFER_SIZE);
            } else {
                out = new BufferedOutputStream(new FileOutputStream(dst),
                        BUFFER_SIZE);
            }
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
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
    public int addFileInfo(String fileName) {
        userId = (Integer) super.session.get("userId");
        Data data = new Data();
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
     * 修改文件信息
     * 
     * @param dataId
     * @param dataKey
     * @param newName
     * @return
     */
    public int updateFileInfo(int dataId, String dataKey, String newName) {
        Data data = new Data();
        data.setFileId(dataId);
        String filePath = realPath + newName;
        data.setSize(FileTools.getFileSize(filePath));
        data.setDataKey(fileDataKey);
        data.setPath(filePath);
        int fileFormat = checkFileType.checkFileType(newName);
        data.setFileFormat(fileFormat);
        if (fileFormat == FileFormat.BAM) {
            String anotherName = getAnotherName(filePath, fileDataKey);
            data.setAnotherName(anotherName);
        }
        data.setState(DataState.ACTIVE);
        return dataService.updateDataInfoByFileId(data);
    }

    public void addReportInfo() {
        log.info("为上传的文件添加数据报告");
        Report report = new Report();
        report.setFileId(fileId);
        report.setUserId(userId);
        if (checkFileType.checkFileType(newName) == 1) {
            int softwareId = softwareService.getSoftIdByName("SNP");
            report.setSoftwareId(softwareId);
            reportService.addReportInfo(report);
        } else if (checkFileType.checkFileType(newName) == 4) {
            int softwareId = softwareService.getSoftIdByName("mRNA");
            report.setSoftwareId(softwareId);
            reportService.addReportInfo(report);
        }
    }

    /**
     * 文件下载方法，需要传入参数newName
     * 
     * @throws IOException
     */
    public void download() throws IOException {
        ServletActionContext.getResponse().setHeader(
                "Access-Control-Allow-Origin", "*");
        String path = null;
        if (downName != null) {
            if (downName.endsWith(".msi")) {
                path = ServletActionContext.getServletContext()
                        .getRealPath("/");
            } else if (downName.endsWith(".txt")) {
                path = "/share/data/output";
            }
            FileTools.fileDownLoad(ServletActionContext.getResponse(), path
                    + "/" + downName);
        }
    }

    public List<File> getUploadImages() {
        return uploadImages;
    }

    public void setUploadImages(List<File> uploadImages) {
        this.uploadImages = uploadImages;
    }

    public List<String> getUploadImagesFileName() {
        return uploadImagesFileName;
    }

    public void setUploadImagesFileName(List<String> uploadImagesFileName) {
        this.uploadImagesFileName = uploadImagesFileName;
    }

    public List<String> getUploadImagesContentType() {
        return uploadImagesContentType;
    }

    public void setUploadImagesContentType(List<String> uploadImagesContentType) {
        this.uploadImagesContentType = uploadImagesContentType;
    }

    public String getFileDataKey() {
        return fileDataKey;
    }

    public void setFileDataKey(String fileDataKey) {
        this.fileDataKey = fileDataKey;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public int getUserId() {
        return userId;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }

    public String getDownName() {
        return downName;
    }

    public void setDownName(String downName) {
        this.downName = downName;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getDataList() {
        return dataList;
    }

    public void setDataList(String dataList) {
        this.dataList = dataList;
    }

    public String getUploadImageFileName() {
        return uploadImageFileName;
    }

    public void setUploadImageFileName(String uploadImageFileName) {
        this.uploadImageFileName = uploadImageFileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getChunk() {
        return chunk;
    }

    public void setChunk(int chunk) {
        this.chunk = chunk;
    }

    public int getChunks() {
        return chunks;
    }

    public void setChunks(int chunks) {
        this.chunks = chunks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOnlyName() {
        return onlyName;
    }

    public void setOnlyName(String onlyName) {
        this.onlyName = onlyName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
