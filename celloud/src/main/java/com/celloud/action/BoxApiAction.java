package com.celloud.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.celloud.constants.ConstantsData;
import com.celloud.constants.DataState;
import com.celloud.constants.FileFormat;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Experiment;
import com.celloud.service.DataService;
import com.celloud.service.ExperimentService;
import com.celloud.utils.CheckFileTypeUtil;
import com.celloud.utils.DataUtil;
import com.celloud.utils.DateUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.MD5Util;
import com.celloud.utils.OSSUtils;
import com.celloud.utils.PerlUtils;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.Response;

@RestController
@RequestMapping("api/box")
public class BoxApiAction {
	private static Logger logger = LoggerFactory.getLogger(BoxApiAction.class);
	CheckFileTypeUtil checkFileType = new CheckFileTypeUtil();
	@Resource
	private DataService dataService;
	@Resource
	private ExperimentService expService;
	private String realPath = PropertiesUtil.bigFilePath;

	@RequestMapping("newfile")
	public Response newfile(Integer userId, String name, String md5, long size) {
		System.out.println(name);
		Map<String, Object> values = new HashMap<>();
		int dataId = addFileInfo(userId, name);
		String fileDataKey = DataUtil.getNewDataKey(dataId);
		String ext = FileTools.getExtName(name);
		DataFile data = new DataFile();
		data.setFileId(dataId);
		data.setDataKey(fileDataKey);
		data.setMd5(md5);
		data.setSize(size);
		dataService.updateByPrimaryKeySelective(data);
		values.put("dataKey", fileDataKey);
		values.put("fileId", dataId);
		values.put("ext", ext);
		values.put("newName", fileDataKey + ext);
		return Response.SUCCESS.setData(values);
	}

	@RequestMapping("updatefile")
	public Response updatefile(String objectKey, Integer fileId, Integer tagId, String batch, Integer needSplit,
			HttpServletRequest request) {
		// dataService.updateDataInfoByFileIdAndTagId(data, tagId);
		DataFile file = dataService.getDataById(fileId);
		String newName = file.getDataKey() + FileTools.getExtName(file.getFileName());
		Integer userId = file.getUserId();
		String today = DateUtil.getDateToString("yyyyMMdd");
		String perlPath = request.getSession().getServletContext().getRealPath("/resources") + "/plugins/getAliases.pl";
		String outPath = request.getSession().getServletContext().getRealPath("/temp") + "/" + file.getDataKey();
		String folderByDay = realPath + userId + File.separator + today;
		updateUploadState(fileId, objectKey);
		OSSUtils.download(objectKey, folderByDay + File.separator + newName);
		int fileFormat = checkFileType.checkFileType(newName, folderByDay);
		updateFileInfo(fileId, file.getDataKey(), newName, perlPath, outPath, folderByDay, batch, fileFormat, tagId);
		// TODO 自动运行：这里没有用户进行登录，也就没有subject，判断hasRole不好判断，暂时先不考虑自动运行
		// if (sub.hasRole("bsier")) {
		// logger.info("{}拥有百菌探权限", userId);
		// return bsierCheckRun(tagId, batch, dataId, fileDataKey, needSplit,
		// newName, originalName, userId,
		// fileFormat);
		// }
		return Response.SUCCESS;
	}

	private void updateUploadState(Integer fileId, String objectKey) {
		DataFile data = new DataFile();
		data.setFileId(fileId);
		data.setOssPath(objectKey);
		data.setUploadState(1);
		dataService.updateByPrimaryKeySelective(data);
	}

	private int updateFileInfo(Integer dataId, String dataKey, String newName, String perlPath, String outPath,
			String folderByDay, String batch, Integer fileFormat, Integer tagId) {
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
		return dataService.updateDataInfoByFileIdAndTagId(data, tagId);
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

	/**
	 * 将上传文件添加到数据库中
	 * 
	 * @return
	 */
	private int addFileInfo(Integer userId, String fileName) {
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
}
