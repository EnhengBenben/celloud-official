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

import com.celloud.constants.BoxUploadState;
import com.celloud.constants.DataState;
import com.celloud.model.BoxFile;
import com.celloud.model.mysql.DataFile;
import com.celloud.service.BoxApiService;
import com.celloud.service.BoxConfigService;
import com.celloud.service.DataService;
import com.celloud.utils.DataUtil;
import com.celloud.utils.DateUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.Response;
import com.celloud.utils.UserAgentUtil;

@RestController
@RequestMapping("api/box")
public class BoxApiAction {
	private static Logger logger = LoggerFactory.getLogger(BoxApiAction.class);
	private String realPath = PropertiesUtil.bigFilePath;

	@Resource
	private DataService dataService;
	@Resource
	private BoxApiService apiService;
	@Resource
	private BoxConfigService configService;

	/**
	 * 盒子端创建文件
	 * 
	 * @param userId
	 * @param name
	 * @param md5
	 * @param size
	 * @param tagId
	 * @param batch
	 * @return
	 */
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

	/**
	 * 盒子端将文件上传到OSS后，更新根据的状态
	 * 
	 * @param objectKey
	 * @param fileId
	 * @param tagId
	 * @param batch
	 * @param needSplit
	 * @param request
	 * @return
	 */
	@RequestMapping("updatefile")
	public Response updatefile(String objectKey, Integer fileId, Integer tagId, String batch, Integer needSplit,
			HttpServletRequest request) {
		logger.info("updating file : {}", objectKey);
		DataFile file = dataService.getDataById(fileId);
		Integer userId = file.getUserId();
		String today = DateUtil.getDateToString("yyyyMMdd");
		String folderByDay = realPath + userId + File.separator + today;
		String newName = file.getDataKey() + FileTools.getExtName(file.getFileName());
		BoxFile boxFile = new BoxFile();
		boxFile.setFileId(fileId);
		boxFile.setBatch(batch);
		boxFile.setFileName(file.getFileName());
		boxFile.setDataKey(file.getDataKey());
		boxFile.setMd5(file.getMd5());
		boxFile.setNeedSplit(needSplit);
		boxFile.setObjectKey(objectKey);
		boxFile.setPath(folderByDay + File.separator + newName);
		boxFile.setTagId(tagId);
		boxFile.setUserId(file.getUserId());
		dataService.updateUploadState(fileId, objectKey, BoxUploadState.IN_OSS);
		apiService.downloadFromOSS(boxFile);
		return Response.SUCCESS();
	}

	/**
	 * 盒子端主动上报盒子的健康状态
	 * 
	 * @param request
	 * @param serialNumber
	 * @param version
	 * @param ip
	 * @return
	 */
	@RequestMapping("health")
	public Response health(HttpServletRequest request, String serialNumber, String version, String ip, Integer port) {
		String extranet = UserAgentUtil.getIp(request);
		logger.info("更新盒子状态：serialNumber={},version={},intranet={},extranet={},port={}", serialNumber, version, ip,
				extranet, port);
		boolean result = configService.updateBoxHealth(serialNumber, version, ip, extranet, port);
		return result ? Response.SUCCESS() : Response.FAIL();
	}
}
