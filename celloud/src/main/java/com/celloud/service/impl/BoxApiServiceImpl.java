package com.celloud.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.celloud.constants.ConstantsData;
import com.celloud.constants.DataState;
import com.celloud.constants.FileFormat;
import com.celloud.constants.TaskPeriod;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Experiment;
import com.celloud.model.mysql.Task;
import com.celloud.service.BoxApiService;
import com.celloud.service.DataService;
import com.celloud.service.ExperimentService;
import com.celloud.service.TaskService;
import com.celloud.utils.CheckFileTypeUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.MD5Util;
import com.celloud.utils.OSSUtils;
import com.celloud.utils.PerlUtils;

@Service
public class BoxApiServiceImpl implements BoxApiService {
	private static Logger logger = LoggerFactory.getLogger(BoxApiServiceImpl.class);
	@Resource
	private DataService dataService;
	@Resource
	private ExperimentService expService;
	@Resource
	private TaskService taskService;
	CheckFileTypeUtil checkFileType = new CheckFileTypeUtil();

	@Async
	@Override
	public void updatefile(String objectKey, Integer fileId, Integer tagId, String batch, Integer needSplit,
			String newName, String folderByDay) {
		DataFile file = dataService.getDataById(fileId);
		Integer userId = file.getUserId();
		boolean isDownloaded = false;
		String path = folderByDay + File.separator + newName;
		for (int i = 0; i < 3; i++) {
			String md5 = OSSUtils.download(objectKey, path);
			if (file.getMd5().equals(md5)) {
				isDownloaded = true;
				break;
			} else {
				logger.info("从oss下载文件错误，进行第{}次重试：{}", i + 1, objectKey);
			}
		}
		if (!isDownloaded) {
			updateUploadState(fileId, objectKey, 3, path);
			return;
		}
		int fileFormat = checkFileType.checkFileType(newName, folderByDay);
		// TODO fileFormat 是空
		updateFileInfo(fileId, file.getDataKey(), newName, null, null, folderByDay, batch, fileFormat, tagId);
		if (tagId != null && tagId.intValue() == 1) {
			// TODO 保险起见，这里还应该校验用户是否已经添加app
			String result = updateBSIerCheckRun(batch, fileId, file.getDataKey(), needSplit, file.getFileName(), userId,
					fileFormat);
			logger.debug("bsi check run result: {}", result);
		}
	}

	@Override
	public void updateUploadState(Integer fileId, String objectKey, int state, String path) {
		DataFile data = new DataFile();
		data.setFileId(fileId);
		data.setOssPath(objectKey);
		data.setUploadState(state);
		data.setPath(path);
		dataService.updateByPrimaryKeySelective(data);
	}

	@Override
	public int updateFileInfo(Integer dataId, String dataKey, String newName, String perlPath, String outPath,
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
		return dataService.updateDataInfoByFileId(data);
	}

	@Override
	public String updateBSIerCheckRun(String batch, Integer dataId, String dataKey, Integer needSplit,
			String originalName, Integer userId, Integer fileFormat) {
		logger.info("判断是否数据{}上传完即刻运行", originalName);
		Integer appId;
		if (needSplit == null) {
			appId = 118;
		} else {
			appId = 113;
		}
		String pubName = "";
		List<Integer> dataIds;
		if (fileFormat == FileFormat.FQ || originalName.contains(".txt") || originalName.contains(".lis")) {
			Boolean isR1 = false;
			if (originalName.contains("R1")) {
				pubName = originalName.substring(0, originalName.lastIndexOf("R1"));
				isR1 = true;
			} else if (originalName.contains("R2")) {
				pubName = originalName.substring(0, originalName.lastIndexOf("R2"));
			} else if (originalName.contains(".txt") || originalName.contains(".lis")) {
				pubName = originalName.substring(0, originalName.lastIndexOf("."));
			}
			Pattern p = Pattern.compile("\\_|\\%");
			Matcher m = p.matcher(pubName);
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String rep = "\\\\" + m.group(0);
				m.appendReplacement(sb, rep);
			}
			m.appendTail(sb);
			List<DataFile> dlist = dataService.getDataByBatchAndFileName(userId, batch, sb.toString());
			boolean hasR1 = false;
			boolean hasR2 = false;
			boolean hasIndex = false;
			dataIds = new ArrayList<>();
			for (DataFile d : dlist) {
				String name_tmp = d.getFileName();
				if (d.getPath() == null) {
					return "1";
				}
				if (name_tmp.contains("R1")) {
					hasR1 = true;
				} else if (name_tmp.contains("R2")) {
					hasR2 = true;
				} else if (name_tmp.contains(".txt") || name_tmp.contains(".lis")) {
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
				dataService.updateToRun(userId, StringUtils.join(dataIds.toArray(), ","), appId + "");
				return "{\"dataIds\":\"" + StringUtils.join(dataIds.toArray(), ",") + "\",\"appIds\":\"" + appId
						+ "\"}";
			} else if (needSplit != 1 && hasR1 && hasR2) {
				task.setAppId(appId);
				taskService.addOrUpdateUploadTaskByParam(task, isR1);
				dataService.updateToRun(userId, StringUtils.join(dataIds.toArray(), ","), appId + "");
				return "{\"dataIds\":\"" + StringUtils.join(dataIds.toArray(), ",") + "\",\"appIds\":\"" + appId
						+ "\"}";
			}
		} else if (fileFormat == FileFormat.YASUO && needSplit == null) {
			dataService.updateToRun(userId, dataId + "", appId + "");
			return "{\"dataIds\":" + dataId + ",\"appIds\":\"" + appId + "\"}";
		}
		return "1";
	}

	@Override
	public String getAnotherName(String filePath, String fileDataKey, String perlPath, String outPath) {
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
