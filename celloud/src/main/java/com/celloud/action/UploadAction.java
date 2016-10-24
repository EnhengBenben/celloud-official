package com.celloud.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.celloud.constants.ConstantsData;
import com.celloud.constants.FileFormat;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Tag;
import com.celloud.service.DataService;
import com.celloud.service.RunService;
import com.celloud.service.TagService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.CheckFileTypeUtil;
import com.celloud.utils.DataUtil;
import com.celloud.utils.DateUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.MD5Util;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.UploadUtil;

/**
 * 多文件上传
 *
 * @author han
 * @date 2015年12月28日 下午3:12:11
 */
@Controller
@RequestMapping("uploadFile")
public class UploadAction {
	Logger logger = LoggerFactory.getLogger(UploadAction.class);
	private static final int BUFFER_SIZE = 2 * 1024;
	@Resource
	private DataService dataService;
	@Resource
	private TagService tagService;
	@Resource
	private RunService runService;
	private String realPath = PropertiesUtil.bigFilePath;
	/**
	 * 用于判断数据格式
	 */
    private CheckFileTypeUtil checkFileType = new CheckFileTypeUtil();

	@RequestMapping("getProductTag")
	@ResponseBody
	public List<Tag> getProductTag() {
		return tagService.findProductTags(ConstantsData.getLoginUserId());
	}

    // @RequestMapping("rocky")
    // @ResponseBody
    // public Map<String, String> uploadRockyFile(@RequestParam("file")
    // CommonsMultipartFile file, Integer chunk,
    // Integer chunks, String name, String uniqueName, Integer tagId, String
    // batch, Integer needSplit,
    // HttpServletRequest request) {
    // // TODO 3.3.6后可以删除该方法
    // Map<String, String> model = new HashMap<>();
    // model.put("run", "false");
    // int dataId = handleUpload(file, name, uniqueName, chunk, chunks, batch,
    // request);
    // if (dataId != 0) {
    // DataFile data = dataService.getDataById(dataId);
    // //TODO 写死的华木兰标签信息和APPID
    // data.setTagId(2);
    // data.setTagName("华木兰");
    // dataService.updateDataAndTag(data);
    // runService.rockyCheckRun(123, data);
    // }
    // return model;
    // }

	/**
	 * 处理上传的文件
	 * 
	 * @param file
	 * @param chunk
	 * @param chunks
	 * @param batch
	 * @param request
	 * @return 文件id，如果不是最后一个chunk，则为0
	 */
    // private int handleUpload(CommonsMultipartFile file, String name, String
    // uniqueName, Integer chunk, Integer chunks,
    // String batch, HttpServletRequest request) {
    // File f = new File(PropertiesUtil.bigFilePath);
    // if (!f.exists()) {
    // boolean isTrue = f.mkdir();
    // if (!isTrue) {
    // logger.error("路径创建失败：{}", realPath);
    // }
    // }
    // String fileName = realPath + File.separatorChar + uniqueName;
    // File localFile = new File(fileName);
    // this.copy(file, localFile);
    // if (chunk.equals(chunks) || chunk.equals(chunks - 1)) {
    // Integer userId = ConstantsData.getLoginUserId();
    // Integer dataId = dataService.addFileInfo(userId, name);
    // String fileDataKey = DataUtil.getNewDataKey(dataId);
    // String newName = fileDataKey + FileTools.getExtName(name);
    // String today = DateUtil.getDateToString("yyyyMMdd");
    // String folderByDay = realPath + userId + File.separator + today;
    // File pf = new File(folderByDay);
    // if (!pf.exists()) {
    // pf.mkdirs();
    // }
    // FileTools.mvFile(realPath, uniqueName, folderByDay, newName);
    // int fileFormat = checkFileType.checkFileType(newName, folderByDay);
    // String filePath = folderByDay + File.separator + newName;
    // String md5 = MD5Util.getFileMD5(filePath);
    // dataService.updateFileInfo(dataId, fileDataKey, filePath, batch,
    // fileFormat, md5, null, null);
    // return dataId;
    // }
    // return 0;
    // }

	/**
	 * 
	 * 数据上传
	 * 
	 * @return
	 * @date 2015年12月28日 下午3:22:38
	 */
    // @ResponseBody
    // @RequestMapping("uploadManyFile_bak")
    // public String uploadManyFile_bak(String name, String onlyName, String
    // md5, String originalName, Integer chunk,
    // Integer chunks, HttpServletRequest request, Integer tagId, String batch,
    // Integer needSplit) {
    // // TODO 3.3.6后可以删除该方法
    // File f = new File(realPath);
    // if (!f.exists()) {
    // boolean isTrue = f.mkdir();
    // if (!isTrue) {
    // logger.error("路径创建失败：{}", realPath);
    // }
    // }
    // CommonsMultipartResolver multipartResolver = new
    // CommonsMultipartResolver(
    // request.getSession().getServletContext());
    // Integer dataId = 0;
    // if (multipartResolver.isMultipart(request)) {
    // MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)
    // request;
    // Iterator<String> iter = multiRequest.getFileNames();
    // while (iter.hasNext()) {
    // MultipartFile file = multiRequest.getFile(iter.next());
    // if (file != null) {
    // String myFileName = file.getOriginalFilename();
    // if (!"".equals(myFileName.trim())) {
    // String fileName = realPath + File.separatorChar + name;
    // File localFile = new File(fileName);
    // try {
    // this.copy(file, localFile);
    // if (chunk.equals(chunks) || chunk.equals(chunks - 1)) {
    // Integer userId = ConstantsData.getLoginUserId();
    // dataId = dataService.addFileInfo(userId, originalName);
    // String fileDataKey = DataUtil.getNewDataKey(dataId);
    // String newName = fileDataKey + FileTools.getExtName(originalName);
    // String today = DateUtil.getDateToString("yyyyMMdd");
    // String folderByDay = realPath + userId + File.separator + today;
    // File pf = new File(folderByDay);
    // if (!pf.exists()) {
    // pf.mkdirs();
    // }
    // FileTools.mvFile(realPath, name, folderByDay, newName);
    // int fileFormat = checkFileType.checkFileType(newName, folderByDay);
    // String filePath = folderByDay + File.separator + newName;
    // String anotherName = null;
    // if (fileFormat == FileFormat.BAM) {
    // anotherName = dataService.getAnotherName(request, filePath, fileDataKey);
    // }
    // md5 = MD5Util.getFileMD5(filePath);
    // dataService.updateFileInfo(dataId, fileDataKey, filePath, batch,
    // fileFormat, md5,
    // anotherName, tagId);
    // // TODO 写死的百菌探自动运行
    // if (tagId == 1) {
    // logger.info("{}拥有百菌探权限", userId);
    // runService.bsiCheckRun(batch, dataId, fileDataKey, needSplit,
    // originalName, userId,
    // fileFormat);
    // }
    // }
    // } catch (Exception e) {
    // logger.error(e.getMessage());
    // }
    // }
    // }
    // }
    // }
    // return "1";
    // }

	/**
	 * 
	 * @description 分块上传,并且每一块单独存储,最后合并到一起.
	 * @author miaoqi
	 * @date 2016年10月17日上午10:54:49
	 *
	 * @param name
	 * @param originalName 文件原始名称
	 * @param chunk 当前第几块(0开始)
	 * @param chunks 总块数
	 * @param file 文件对象
	 * @param tagId 标签id
	 * @param batch 批次
	 * @param needSplit 是否需要分割
	 * @return 
	 *
	 */
    @ResponseBody
    @RequestMapping("uploadManyFile")
    public String uploadManyFile(String name, String originalName, Integer chunk,
            Integer chunks, MultipartFile file, HttpServletRequest request, Integer tagId, String batch,
            Integer needSplit, Long size, Date lastModifiedDate) {
        /**
         * 在用户文件夹下为每一个上传的文件创建一个块的存储目录用来存储该文件的每一块,最后将该目录下的每个文件都写到用户真实的存储目录下
         */
        if (file != null) {
            Integer userId = ConstantsData.getLoginUserId();
            // /share/data/file/23
            String userFolder = realPath + ConstantsData.getLoginUserId();
            // 通过userId,块名称,最后修改时间,大小获取文件唯一名字
            String uniqueName = UploadUtil.getUniqueName(userId, name, lastModifiedDate, size);
            if (logger.isDebugEnabled()) {
                logger.debug("获取uniqueName, userId: {}, name: {}, lastModifiedData: {}, size: {}", userId, name,
                    lastModifiedDate, size);
            }
            // 获取块文件对象
            // /share/data/file/23/uniqueName_chunks/0(1,2...)
            File chunkFile = new File(userFolder + File.separator + uniqueName + "_chunks" + File.separator + chunk);
            if (!chunkFile.getParentFile().exists()) {
                boolean isTrue = chunkFile.getParentFile().mkdirs();
                if (!isTrue) {
                    logger.error("路径创建失败：{}", realPath);
                }
            }
            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("用户 {} 正在上传文件, chunk = {}", userId, chunk);
                }
                file.transferTo(chunkFile);
                if (chunk.equals(chunks) || chunk.equals(chunks - 1)) {
                    Integer dataId = dataService.addFileInfo(userId, originalName);
                    // 文件datakey
                    String fileDataKey = DataUtil.getNewDataKey(dataId);
                    // 文件名称(datakey.原始扩展名)
                    String newName = fileDataKey + FileTools.getExtName(originalName);
                    // 今天的日期
                    String today = DateUtil.getDateToString("yyyyMMdd");
                    // /share/data/file/23/20161017
                    String folderByDay = realPath + userId + File.separator + today;
                    // 合并块数据到真实存储路径
                    // 1. 获取目标文件对象
                    String dest = folderByDay + File.separator + newName;
                    // 2. 获取块文件父目录
                    String srcFolder = chunkFile.getParent();
                    logger.info("用户 {} 上传块文件完毕开始合并", userId);
                    Boolean flag = UploadUtil.mergeBlock(dest, srcFolder, chunks);
                    if (flag) {
                        logger.info("用户 {} 合并文件成功", userId);
                        int fileFormat = checkFileType.checkFileType(newName, folderByDay);
                        String filePath = folderByDay + File.separator + newName;
                        String anotherName = null;
                        if (fileFormat == FileFormat.BAM) {
                            anotherName = dataService.getAnotherName(request, filePath, fileDataKey);
                        }
                        String md5 = MD5Util.getFileMD5(filePath);
                        dataService.updateFileInfo(dataId, fileDataKey, filePath, batch, fileFormat, md5, anotherName,
                                tagId);
                        // TODO 写死的百菌探自动运行
                        if (tagId == 1) {
                            logger.info("{}拥有百菌探权限", userId);
                            runService.bsiCheckRun(batch, dataId, fileDataKey, needSplit, originalName, userId,
                                    fileFormat);
                        } else if (tagId == 2) {
                            logger.info("{}拥有华木兰权限", userId);
                            DataFile data = dataService.getDataById(dataId);
                            // TODO 写死的华木兰标签信息和APPID
                            data.setTagId(2);
                            data.setTagName("华木兰");
                            dataService.updateDataAndTag(data);
                            runService.rockyCheckRun(123, data);
                        }
                    } else {
                        logger.info("用户 {} 合并文件出错", userId);
                    }
                }
            } catch (IllegalStateException | IOException e) {
                logger.error("文件上传失败！", e);
            }
        }
        return "1";
    }

    /**
     * 
     *
     * @param file原始文件
     * @param dst
     *            目标文件
     * @author han
     * @date 2016年1月13日 上午10:04:06
     */
    // @ActionLog(value = "将缓存的数据读取到共享存储", button = "开始上传")
    // private void copy(MultipartFile file, File dst) {
    // InputStream in = null;
    // OutputStream out = null;
    // try {
    // if (dst.exists()) {
    // out = new BufferedOutputStream(new FileOutputStream(dst, true),
    // BUFFER_SIZE);
    // in = new BufferedInputStream(file.getInputStream(), BUFFER_SIZE);
    // byte[] buffer = new byte[BUFFER_SIZE];
    // int len = 0;
    // while ((len = in.read(buffer)) > 0) {
    // out.write(buffer, 0, len);
    // }
    // out.flush();
    // } else {
    // file.transferTo(dst);
    // }
    // } catch (Exception e) {
    // logger.error("将文件写入磁盘出错！", e);
    // } finally {
    // if (null != in) {
    // try {
    // in.close();
    // } catch (IOException e) {
    // logger.error("上传文件关闭输入流异常！", e);
    // }
    // }
    // if (null != out) {
    // try {
    // out.close();
    // } catch (IOException e) {
    // logger.error("上传文件关闭输出流异常！", e);
    // }
    // }
    // }
    // }

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

}
