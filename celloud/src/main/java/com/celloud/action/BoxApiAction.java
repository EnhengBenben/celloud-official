package com.celloud.action;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
import com.celloud.utils.DateUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.Response;

@RestController
@RequestMapping("api/box")
public class BoxApiAction {
	private static Logger logger = LoggerFactory.getLogger(BoxApiAction.class);
	private String realPath = PropertiesUtil.bigFilePath;

	@Resource
	private DataService dataService;
	@Resource
	private BoxApiService apiService;

	@RequestMapping("newfile")
	public Response newfile(Integer userId, String name, String md5, long size, Integer tagId, String batch) {
		logger.info("user {} new file : {}", userId, name);
		Map<String, Object> values = new HashMap<>();
		int dataId = dataService.addFileInfo(userId, name);
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
		return Response.SUCCESS(values);
	}

	@RequestMapping("updatefile")
	public Response updatefile(String objectKey, Integer fileId, Integer tagId, String batch, Integer needSplit,
			HttpServletRequest request) {
		logger.info("updating file : {}", objectKey);
		DataFile file = dataService.getDataById(fileId);
		Integer userId = file.getUserId();
		String today = DateUtil.getDateToString("yyyyMMdd");
		String folderByDay = realPath + userId + File.separator + today;
		String newName = file.getDataKey() + FileTools.getExtName(file.getFileName());
		String path = folderByDay + File.separator + newName;
		dataService.updateUploadState(fileId, objectKey, 1, path);
		apiService.finishfile(objectKey, fileId, tagId, batch, needSplit, newName, folderByDay);
		return Response.SUCCESS();
	}
}
