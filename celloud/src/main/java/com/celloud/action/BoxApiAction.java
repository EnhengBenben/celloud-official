package com.celloud.action;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.celloud.constants.DataState;
import com.celloud.model.mysql.DataFile;
import com.celloud.service.BoxApiService;
import com.celloud.service.DataService;
import com.celloud.utils.DataUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.Response;

@RestController
@RequestMapping("api/box")
public class BoxApiAction {
	private static Logger logger = LoggerFactory.getLogger(BoxApiAction.class);

	@Resource
	private DataService dataService;
	@Resource
	private BoxApiService apiService;

	@RequestMapping("newfile")
	public Response newfile(Integer userId, String name, String md5, long size, Integer tagId, String batch) {
		logger.info("user {} new file : {}", userId, name);
		Map<String, Object> values = new HashMap<>();
		int dataId = addFileInfo(userId, name);
		String fileDataKey = DataUtil.getNewDataKey(dataId);
		String ext = FileTools.getExtName(name);
		DataFile data = new DataFile();
		data.setFileId(dataId);
		data.setDataKey(fileDataKey);
		data.setMd5(md5);
		data.setSize(size);
		data.setBatch(batch);
		data.setState(DataState.ACTIVE);
		dataService.updateDataInfoByFileIdAndTagId(data, tagId);
		values.put("dataKey", fileDataKey);
		values.put("fileId", dataId);
		values.put("ext", ext);
		values.put("newName", fileDataKey + ext);
		return Response.SUCCESS.setData(values);
	}

	@RequestMapping("updatefile")
	public Response updatefile(String objectKey, Integer fileId, Integer tagId, String batch, Integer needSplit,
			HttpServletRequest request) {
		logger.info("updating file : {}", objectKey);
		apiService.updateUploadState(fileId, objectKey, 1);
		apiService.updatefile(objectKey, fileId, tagId, batch, needSplit);
		return Response.SUCCESS;
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
