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

    /**
     * 
     * @description 检测用户的角色
     * @author miaoqi
     * @date 2017年2月5日 下午1:12:39
     * @return
     */
    @RequestMapping("checkRole")
    @ResponseBody
    public String checkRole() {
        Integer role = ConstantsData.getLoginUser().getRole();
        if (role.intValue() == 5) {
            return "0";
        }
        return "1";
    }

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
        if ("0".equals(this.checkRole())) {
            return "0";
        }
        // 如果是百菌探, 先检查同一批次下, 是否包含相同文件名的文件, 如果有 则直接返回
        if (tagId.intValue() == 1) {
            if (chunk.intValue() == 0) {
                logger.info("用户 {} 上传文件, 检查批次 {} 下是否含有文件名 {} 相同的文件, 仅第一块文件检查", ConstantsData.getLoginUserId(), batch,
                        originalName);
            }
            List<DataFile> dataFiles = dataService.getDataByBatchAndFileName(ConstantsData.getLoginUserId(), batch,
                    originalName);
            if (dataFiles.size() > 0) {
                if (chunk.intValue() == 0) {
                    logger.info("批次 {} 下包含文件名为 {} 的相同文件, 直接返回", batch, originalName);
                }
                if (chunk.intValue() == chunks.intValue() - 1) {
                    logger.info("批次 {} 下文件名为 {} 的相同文件上传完毕", batch, originalName);
                }
                return "1";
            } else {
                if (chunk.intValue() == 0) {
                    logger.info("批次 {} 下不包含文件名为 {} 的相同文件, 继续执行正常的上传功能", batch, originalName);
                }
            }
        }
        /**
         * 在用户文件夹下为每一个上传的文件创建一个块的存储目录用来存储该文件的每一块,最后将该目录下的每个文件都写到用户真实的存储目录下
         */
        if (file != null) {
            Integer userId = ConstantsData.getLoginUserId();
            // /share/data/file/23
            String userFolder = realPath + ConstantsData.getLoginUserId();
            // 通过userId,块名称,最后修改时间,大小获取文件唯一名字
            if (logger.isDebugEnabled()) {
                logger.debug("获取uniqueName, userId: {}, name: {}, lastModifiedData: {}, size: {}", userId, name,
                    lastModifiedDate, size);
            }
            String uniqueName = UploadUtil.getUniqueName(userId, name, lastModifiedDate, size);
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
                        if (tagId == 1 || tagId == 40 || tagId == 41
                                || tagId == 42 || tagId == 43 || tagId == 44) {
                            logger.info("{}拥有百菌探权限", userId);
                            runService.bsiCheckRun(batch, dataId, fileDataKey,
                                    originalName, userId,
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
