package com.celloud.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.celloud.constants.ConstantsData;
import com.celloud.constants.DataState;
import com.celloud.constants.FileFormat;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Tag;
import com.celloud.service.AppService;
import com.celloud.service.DataService;
import com.celloud.service.ExperimentService;
import com.celloud.service.ReportService;
import com.celloud.service.RunService;
import com.celloud.service.TagService;
import com.celloud.service.TaskService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.CheckFileTypeUtil;
import com.celloud.utils.DataUtil;
import com.celloud.utils.DateUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.MD5Util;
import com.celloud.utils.PropertiesUtil;

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
	private AppService appService;
	@Resource
	private ReportService reportService;
	@Resource
	private DataService dataService;
	@Resource
	private ExperimentService expService;
	@Resource
	private TaskService taskService;
	@Resource
	private TagService tagService;
	@Resource
	private RunService runService;
	private String realPath = PropertiesUtil.bigFilePath;
	/**
	 * 用于判断数据格式
	 */
	CheckFileTypeUtil checkFileType = new CheckFileTypeUtil();

	@RequestMapping("getProductTag")
	@ResponseBody
	public List<Tag> getProductTag() {
		return tagService.findProductTags(ConstantsData.getLoginUserId());
	}

	@RequestMapping("rocky")
	@ResponseBody
	public Map<String, String> uploadRockyFile(@RequestParam("file") CommonsMultipartFile file, Integer chunk,
			Integer chunks, String name, String uniqueName, Integer tagId, String batch, Integer needSplit,
			HttpServletRequest request) {
		Map<String, String> model = new HashMap<>();
		model.put("run", "false");
		int dataId = handleUpload(file, name, uniqueName, chunk, chunks, batch, request);
		if (dataId != 0) {
			DataFile data = dataService.getDataById(dataId);
			//TODO 写死的华木兰标签信息和APPID
			data.setTagId(2);
			data.setTagName("华木兰");
			dataService.updateDataAndTag(data);
			runService.rockyCheckRun(123, data);
		}
		return model;
	}

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
	private int handleUpload(CommonsMultipartFile file, String name, String uniqueName, Integer chunk, Integer chunks,
			String batch, HttpServletRequest request) {
		File f = new File(PropertiesUtil.bigFilePath);
		if (!f.exists()) {
			boolean isTrue = f.mkdir();
			if (!isTrue) {
				logger.error("路径创建失败：{}", realPath);
			}
		}
		String fileName = realPath + File.separatorChar + uniqueName;
		File localFile = new File(fileName);
		this.copy(file, localFile);
		if (chunk.equals(chunks) || chunk.equals(chunks - 1)) {
			Integer userId = ConstantsData.getLoginUserId();
			Integer dataId = dataService.addFileInfo(userId, name);
			String fileDataKey = DataUtil.getNewDataKey(dataId);
			String newName = fileDataKey + FileTools.getExtName(name);
			String today = DateUtil.getDateToString("yyyyMMdd");
			String folderByDay = realPath + userId + File.separator + today;
			File pf = new File(folderByDay);
			if (!pf.exists()) {
				pf.mkdirs();
			}
			FileTools.mvFile(realPath, uniqueName, folderByDay, newName);
			// TODO 固定值，可以抽取处理
			String perlPath = request.getSession().getServletContext().getRealPath("/resources")
					+ "/plugins/getAliases.pl";
			// TODO 固定值，可以抽取处理
			String outPath = request.getSession().getServletContext().getRealPath("/temp") + "/" + fileDataKey;
			int fileFormat = checkFileType.checkFileType(newName, folderByDay);
			updateFileInfo(dataId, fileDataKey, newName, perlPath, outPath, folderByDay, batch, fileFormat);
			return dataId;
		}
		return 0;
	}

	/**
	 * 
	 * 数据上传
	 * 
	 * @return
	 * @date 2015年12月28日 下午3:22:38
	 */
	@ResponseBody
	@RequestMapping("uploadManyFile")
	public String uploadManyFile(String name, String onlyName, String md5, String originalName, Integer chunk,
			Integer chunks, HttpServletRequest request, Integer tagId, String batch, Integer needSplit) {
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
							if (chunk.equals(chunks) || chunk.equals(chunks - 1)) {
								Integer userId = ConstantsData.getLoginUserId();
								dataId = dataService.addFileInfo(userId, originalName);
								String fileDataKey = DataUtil.getNewDataKey(dataId);
								String newName = fileDataKey + FileTools.getExtName(originalName);
								String today = DateUtil.getDateToString("yyyyMMdd");
								String folderByDay = realPath + userId + File.separator + today;
								File pf = new File(folderByDay);
								if (!pf.exists()) {
									pf.mkdirs();
								}
								FileTools.mvFile(realPath, name, folderByDay, newName);
								String perlPath = request.getSession().getServletContext().getRealPath("/resources")
										+ "/plugins/getAliases.pl";
								String outPath = request.getSession().getServletContext().getRealPath("/temp") + "/"
										+ fileDataKey;
								int fileFormat = checkFileType.checkFileType(newName, folderByDay);
								updateFileInfo(dataId, fileDataKey, newName, perlPath, outPath, folderByDay,
										batch, fileFormat, tagId);
								Subject sub = SecurityUtils.getSubject();
								// MessageUtils.get()
								// .on(Constants.MESSAGE_USER_CHANNEL).send(NoticeConstants.createMessage("upload",
								// "文件上传完成", "您的文件【" + originalName +
								// "】已经上传完成。"))
								// .to(sub.getPrincipal().toString());
								if (sub.hasRole("bsier")) {
									logger.info("{}拥有百菌探权限", userId);
									runService.bsiCheckRun(batch, dataId, fileDataKey, needSplit, originalName, userId,
											fileFormat);
								}
							}
						} catch (Exception e) {
							logger.error(e.getMessage());
						}
					}
				}
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
	@ActionLog(value = "将缓存的数据读取到共享存储", button = "开始上传")
	private void copy(MultipartFile file, File dst) {
		InputStream in = null;
		OutputStream out = null;
		try {
			if (dst.exists()) {
				out = new BufferedOutputStream(new FileOutputStream(dst, true), BUFFER_SIZE);
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
			logger.error("将文件写入磁盘出错！", e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("上传文件关闭输入流异常！", e);
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("上传文件关闭输出流异常！", e);
				}
			}
		}
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

	//TODO 以下两个方法是垃圾，要删除
    private int updateFileInfo(int dataId, String dataKey, String newName, String perlPath, String outPath,
            String folderByDay, String batch, int fileFormat, int tagId) {
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
			String anotherName = dataService.getAnotherName(filePath, dataKey, perlPath, outPath);
            data.setAnotherName(anotherName);
        }
        data.setState(DataState.ACTIVE);
        return dataService.updateDataInfoByFileIdAndTagId(data, tagId);
    }

	private int updateFileInfo(Integer dataId, String dataKey, String newName, String perlPath, String outPath,
			String folderByDay, String batch, Integer fileFormat) {
		DataFile data = new DataFile();
		data.setFileId(dataId);
		String filePath = folderByDay + File.separator + newName;
		data.setSize(FileTools.getFileSize(filePath));
		data.setDataKey(dataKey);
		data.setPath(filePath);
		data.setMd5(MD5Util.getFileMD5(filePath));
		data.setBatch(batch);
		data.setFileFormat(fileFormat);
		data.setState(DataState.ACTIVE);
		return dataService.updateDataInfoByFileId(data);
	}

}
