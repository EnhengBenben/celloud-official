package com.celloud.box.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.celloud.box.config.BoxConfig;
import com.celloud.box.model.DataFile;
import com.celloud.box.model.Newfile;
import com.celloud.box.model.SplitFile;
import com.celloud.box.utils.MD5Util;
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
	private FileUploadQueue fileUploadQueue;
	@Resource
	private SplitQueue splitQueue;
	@Resource
	private BoxConfig config;
	@Resource
	private FileUploadQueue fileQueue;

	@Override
	public DataFile save(Integer userId, String name, String anotherName, Integer tagId, String batch,
			Integer needSplit, File f) {
		DataFile file = new DataFile();
		file.setPath(f.getAbsolutePath());
		file.createFile();
		file.setBatch(batch);
		file.setUserId(userId);
		file.setFilename(name);
		file.setAnotherName(anotherName);
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
			newfile = apiService.newfile(file.getUserId(), file.getFilename(), file.getAnotherName(),
					file.getFileSize(), file.getMd5(), file.getTagId(), file.getBatch());
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
					file.getNeedSplit(), file.getNeedSplit() != null);
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
	public boolean finish(DataFile file) {
		if (file == null) {
			return false;
		}
		try {
			if (file.isSplited() && file.isUploaded()) {
				String uploadedPath = UploadPath.getUploadedPath(file.getUserId());
				logger.info("moving file : {}", file.getPath());
				File uploading = new File(file.getPath());
				FileUtils.moveFileToDirectory(uploading, new File(uploadedPath), true);
				new File(file.getPath() + ".json").delete();
				file.setPath(UploadPath.getUploadedPath(file.getUserId()) + uploading.getName());
				file.serialize();
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	@Async
	@Override
	public void loadUnFinishedFiles() {
		TreeMap<Long, String> uploadingFiles = listFiles(new File(UploadPath.getUploadingPath()));
		for (String file : uploadingFiles.values()) {
			DataFile dataFile = DataFile.load(file + ".json");
			if (!dataFile.isUploaded()) {
				fileUploadQueue.add(file);
			}
			if (!dataFile.isSplited()) {
				checkRunSplit(dataFile);
			}
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

	@Async
	@Override
	public void checkRunSplit(DataFile dataFile) {
		if (dataFile == null || dataFile.getNeedSplit() == null) {
			return;
		}
		String name = null;
		String filename = dataFile.getFilename();
		int index = -1;
		int fileType = 0;
		if ((index = filename.indexOf("R1")) != -1) {
			fileType = 1;
			name = filename.substring(0, index);
		} else if ((index = filename.indexOf("R2")) != -1) {
			fileType = 2;
			name = filename.substring(0, index);
		}
		if (name == null) {
			return;
		}
		String path = UploadPath.getSplitCheckingPath(dataFile.getUserId(), dataFile.getBatch(), name);
		SplitFile splitFile = SplitFile.load(path);
		if (splitFile == null) {
			splitFile = new SplitFile();
			splitFile.setListPath(UploadPath.getSplitListPath(dataFile.getUserId(), dataFile.getBatch(), name));
			splitFile.setOutPath(UploadPath.getSplitOutputPath(dataFile.getUserId(), dataFile.getBatch(), name));
		}
		splitFile.setUserId(dataFile.getUserId());
		splitFile.setTagId(dataFile.getTagId());
		splitFile.setName(name);
		splitFile.setBatch(dataFile.getBatch());
		switch (fileType) {
		case 1:
			splitFile.setR1Path(dataFile.getPath());
			break;
		case 2:
			splitFile.setR2Path(dataFile.getPath());
			break;
		default:
			break;
		}
		if (splitFile.isRunning()) {
			splitFile.toFile();
			logger.info("split正在运行:{}", splitFile.toJSON());
			return;
		}
		if (!splitFile.check()) {
			splitFile.toFile();
			logger.info("不能运行split:{}", splitFile.toJSON());
			return;
		}
		// 检查是否包含r1和r2, 如果包含, 根据storageName向celloud请求.txt文件信息
		String pubName = splitFile.getName();
		String storageName = StringUtils.splitByWholeSeparator(splitFile.getName(), "_")[0];
		String txtName = pubName + ".txt";
		// 获取file信息, 在盒子内创建.txt文件
		Map<String, Object> result = apiService.splittxt(dataFile.getUserId(), pubName, storageName,
		        dataFile.getBatch());
		// 没有绑定成功.txt文件
		if (result == null) {
            logger.info("绑定.txt文件失败, pubName = {}", pubName);
			return;
		}
		String folder = UploadPath.getUploadingPath(dataFile.getUserId());
		String uniqueName = UploadPath.getUniqueName(dataFile.getUserId(), txtName, new Date().getTime(),
		        Long.parseLong(result.get("size").toString()));
		File txtFile = new File(folder + UploadPath.getRandomName(uniqueName));
		try {
			FileUtils.write(txtFile, result.get("content").toString());
		} catch (IOException e) {
			logger.info("创建txt文件失败");
			return;
		}
		// 创建txt文件信息
		DataFile file = new DataFile();
		file.setPath(txtFile.getAbsolutePath());
		file.setBatch(dataFile.getBatch());
		file.setUserId(dataFile.getUserId());
		file.setFilename(txtName);
		file.setAnotherName(null);
		file.setNeedSplit(0);
		file.setTagId(null);
		String md5 = MD5Util.getFileMD5(file.getPath());
		file.setMd5(md5);
		file.setFileSize(Long.parseLong(result.get("size").toString()));
		file.setDataKey(result.get("dataKey").toString());
		file.setExt(result.get("ext").toString());
		file.setNewName(result.get("newName").toString());
		file.setFileId(Integer.parseInt(result.get("fileId").toString()));
		file.setObjectKey(UploadPath.getObjectKey(file.getUserId(), result.get("dataKey").toString(), result.get("ext").toString()));
		file.setUploaded(true);
		file.serialize();
		if (!file.serialize()) {
			return;
		}
        logger.info("存储.txt信息成功");
		// 移动文件
		finish(file);
		// 运行split
		splitFile.setTxtPath(file.getPath());
		splitFile.toFile();
		splitQueue.add(splitFile);
	}

	@Async
	@Override
	public void splitRunOver(Integer userId, String name, String anotherName, Integer tagId, String batch,
			Integer needSplit, File f) {
		DataFile file = save(userId, name, anotherName, tagId, batch, needSplit, f);
		logger.info("anotherName={}", anotherName);
		file = newfile(file);
		if (file != null) {
			fileQueue.add(f);
		}
	}
}
