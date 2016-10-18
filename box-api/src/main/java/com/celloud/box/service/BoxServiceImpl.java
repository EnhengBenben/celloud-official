package com.celloud.box.service;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.celloud.box.model.DataFile;
import com.celloud.box.model.Newfile;
import com.celloud.box.utils.MD5Util;
import com.celloud.box.utils.OSSProgressListener;
import com.celloud.box.utils.ThreadUtil;
import com.celloud.box.utils.UploadPath;

@Service
public class BoxServiceImpl implements BoxService {
	private static Logger logger = LoggerFactory.getLogger(BoxServiceImpl.class);
	@Resource
	private OSSService ossService;
	@Resource
	private ApiService apiService;
	@Resource
	private FileUploadQueue queue;

	@Async
	@Override
	public void finish(Integer userId, String name, Integer tagId, String batch, Integer needSplit, File file) {
		String md5 = MD5Util.getFileMD5(file);
		// 通知celloud有新文件上传
		Newfile newfile = null;
		for (int i = 0; i < 3; i++) {// 失败需重试
			newfile = apiService.newfile(userId, name, file.length(), md5, tagId, batch);
			if (newfile != null) {// 成功则退出
				logger.info("文件创建成功：userId={},fileName={},fileId={}", userId, name, newfile.getFileId());
				break;
			}
			ThreadUtil.sleep(5000);// 5秒之后再重试
		}
		if (newfile == null) {
			// TODO 这里需要处理
			logger.info("文件创建失败：userId={},fileName={},fileId={}", userId, name);
			return;
		}
		// 将文件上传到oss
		String objectKey = UploadPath.getObjectKey(userId, newfile.getDataKey(), newfile.getExt());
		String location = null;
		for (int i = 0; i < 10; i++) {
			location = ossService.upload(objectKey, file, new OSSProgressListener(userId, name, newfile.getDataKey()));
			if (location != null) {
				break;
			}
		}
		// 通知celloud文件已经上传到oss
		boolean result = false;
		for (int i = 0; i < 3; i++) {// 失败需重试
			result = apiService.updatefile(objectKey, newfile.getFileId(), tagId, batch, needSplit);
			if (result) {// 成功则退出
				logger.info("文件更新成功：userId={},fileName={},fileId={}", userId, name, newfile.getFileId());
				break;
			}
			ThreadUtil.sleep(5000);// 5秒之后再重试
		}
		if (!result) {
			logger.info("文件更新失败：userId={},fileName={}", userId, name);
		}
	}

	@Override
	public DataFile save(Integer userId, String name, Integer tagId, String batch, Integer needSplit, File f) {
		DataFile file = new DataFile();
		file.setPath(f.getAbsolutePath());
		file.createFile();
		file.setBatch(batch);
		file.setUserId(userId);
		file.setFilename(name);
		file.setNeedSplit(needSplit);
		file.setTagId(tagId);
		String md5 = MD5Util.getFileMD5(f);
		file.setMd5(md5);
		file.setFileSize(f.length());
		return file.serialize() ? file : null;
	}

	@Override
	public DataFile newfile(DataFile file) {
		Newfile newfile = null;
		for (int i = 0; i < 3; i++) {// 失败需重试
			newfile = apiService.newfile(file.getUserId(), file.getFilename(), file.getFileSize(), file.getMd5(),
					file.getTagId(), file.getBatch());
			if (newfile != null) {// 成功则退出
				logger.info("文件创建成功：userId={},fileName={},fileId={}", file.getUserId(), file.getFilename(),
						newfile.getFileId());
				file.setDataKey(newfile.getDataKey());
				file.setExt(newfile.getExt());
				file.setNewName(newfile.getNewName());
				file.setFileId(newfile.getFileId());
				file.setObjectKey(UploadPath.getObjectKey(file.getUserId(), newfile.getDataKey(), newfile.getExt()));
				file.serialize();
				return file;
			}
			ThreadUtil.sleep(5000);// 5秒之后再重试
		}
		return null;
	}

	@Override
	public DataFile updatefile(DataFile file) {
		// 通知celloud文件已经上传到oss
		boolean result = false;
		if (file == null) {
			logger.info("文件更新失败，从json未加载到数据。。。");
			return null;
		}
		for (int i = 0; i < 3; i++) {// 失败需重试
			result = apiService.updatefile(file.getObjectKey(), file.getFileId(), file.getTagId(), file.getBatch(),
					file.getNeedSplit());
			if (result) {// 成功则退出
				logger.info("文件更新成功：userId={},fileId={},fileName={}", file.getUserId(), file.getFileId(),
						file.getFilename());
				break;
			}
			ThreadUtil.sleep(5000);// 5秒之后再重试
		}
		if (!result) {
			logger.info("文件更新失败：userId={},fileName={}", file.getUserId(), file.getFilename());
		}
		return null;
	}

	@Override
	public boolean uploaded(DataFile file) {
		if (file == null) {
			return false;
		}
		try {
			FileUtils.moveFileToDirectory(new File(file.getPath()),
					new File(UploadPath.getUploadedPath(file.getUserId())), true);
			FileUtils.moveFileToDirectory(new File(file.getPath() + ".json"),
					new File(UploadPath.getUploadedPath(file.getUserId())), true);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	@Override
	public void loadUnUploadedFiles() {
		TreeMap<Long, String> uploadingFiles = listFiles(new File(UploadPath.getUploadingPath()));
		for (String file : uploadingFiles.values()) {
			queue.add(file);
		}
	}

	private TreeMap<Long, String> listFiles(File file) {
		TreeMap<Long, String> result = new TreeMap<>();
		if (file == null || !file.exists()) {
			return result;
		}
		if (file.isFile()) {
			if (new File(file.getAbsolutePath() + ".json").exists()) {
				result.put(file.lastModified(), file.getAbsolutePath());
			}
			return result;
		}
		File[] children = file.listFiles();
		if (children == null || children.length == 0) {
			return result;
		}
		for (File child : children) {
			result.putAll(listFiles(child));
		}
		return result;
	}

}
