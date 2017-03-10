package com.celloud.box.listener;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.celloud.box.event.SplitRunOverEvent;
import com.celloud.box.model.DataFile;
import com.celloud.box.model.SplitFile;
import com.celloud.box.service.ApiService;
import com.celloud.box.service.BoxService;
import com.celloud.box.utils.UploadPath;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * split队列监听器，监听split运行结束的事件，某个文件运行split结束后，触发一下个文件的split运行
 * 
 * @author <a href="mailto:sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年10月25日上午11:27:54
 * @version Revision: 1.0
 */
@Component
public class SplitRunOverListener implements ApplicationListener<SplitRunOverEvent> {
	@Resource
	private BoxService boxService;
    @Autowired
    private ApiService apiService;

	private static Logger logger = LoggerFactory.getLogger(SplitRunOverListener.class);

	@Async
	@Override
	public void onApplicationEvent(SplitRunOverEvent event) {
		String path = (String) event.getSource();
		// 标记运行完成
		SplitFile splitFile = SplitFile.load(path);
		splitFile.setRunning(Boolean.FALSE);
		splitFile.toFile();
        // 读取r1, r2, 通知celloud修改r1, r2的运行状态
        try {
            logger.info("split运行完成");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode r1Tree = objectMapper.readTree(splitFile.getR1Path());
            logger.info("r1Path = {}", splitFile.getR1Path());
            JsonNode r2Tree = objectMapper.readTree(splitFile.getR2Path());
            logger.info("r2Path = {}", splitFile.getR2Path());
            Integer r1Id = Integer.parseInt(String.valueOf(r1Tree.get("fileId")));
            logger.info("r1Id = {}", r1Id);
            Integer r2Id = Integer.parseInt(String.valueOf(r2Tree.get("fileId")));
            logger.info("r2id = {}", r2Id);
            Boolean flag = apiService.fileRunOver(r1Id, r2Id);
            logger.info("flag = {}", flag);
            if (flag) {
                logger.info("修改数据运行状态成功");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        // 分别处理三个文件
        DataFile r1 = setSplited(splitFile.getR1Path());
        boxService.finish(r1);
        DataFile r2 = setSplited(splitFile.getR2Path());
        boxService.finish(r2);

		DataFile txt = setSplited(splitFile.getTxtPath());
		boxService.finish(txt);
		// 读取split结果
		String temp = new File(r1.getPath()).getName().replaceAll(r1.getExt(), "");
		String resultPath = UploadPath.getSplitOutputPath(splitFile.getUserId(), splitFile.getBatch(),
				splitFile.getName()) + File.separatorChar + temp + File.separatorChar + "result" + File.separatorChar
				+ "split" + File.separatorChar;
		File resultFile = new File(resultPath);
		File[] results = resultFile.listFiles();
		if (results == null || results.length == 0) {
			logger.error("split运行结束，但是没有找到结果文件：{}\n{}", resultPath, splitFile.toJSON());
			return;
		}
		// 分别处理结果文件
		String folder = UploadPath.getUploadingPath(splitFile.getUserId());
		for (File result : results) {
			if (result.isDirectory()) {
				continue;
			}
			String filename = result.getName();
			String uniqueName = UploadPath.getUniqueName(splitFile.getUserId(), result.getName(), result.lastModified(),
					result.length());
			String randomName = UploadPath.getRandomName(uniqueName);
			File newPath = new File(folder + randomName);
			try {
				FileUtils.moveFile(result, newPath);// 文件移动到等待上传的目录下
			} catch (IOException e) {
				logger.error("移动文件失败：{}", result.getAbsolutePath(), e);
				continue;
			}
			boxService.splitRunOver(splitFile.getUserId(), filename, splitFile.getName(), splitFile.getTagId(),
					splitFile.getBatch(), null, newPath);
		}
		// clean...
		File file = resultFile.getParentFile().getParentFile().getParentFile();
		try {
			FileUtils.deleteDirectory(file);
		} catch (IOException e) {
			logger.error("删除目录失败：{}", file.getAbsolutePath(), e);
		}
		// 列举上级目录下的所有的文件夹
		File[] children = file.getParentFile().listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory();
			}
		});
		// 如果上级目录下不存在其他文件夹，则连同上级目录一起删除
		if (children == null || children.length == 0) {
			try {
				FileUtils.deleteDirectory(file.getParentFile());
			} catch (IOException e) {
				logger.error("删除目录失败：{}", file.getParentFile().getAbsolutePath(), e);
			}
		}
		new File(splitFile.getListPath()).delete();// 删除生成的list文件
		new File(path).delete();// 删除split的校验文件
	}

	/**
	 * 将上传的文件置为split已完成
	 * 
	 * @param path
	 * @return
	 */
	public DataFile setSplited(String path) {
		DataFile temp = DataFile.load(path + ".json");
		if (temp == null) {
			logger.info("没有加载到文件的信息:{}", path);
			return null;
		}
		temp.setSplited(Boolean.TRUE);
		temp.serialize();
		return temp;
	}

}
