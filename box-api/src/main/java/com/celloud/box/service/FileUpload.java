package com.celloud.box.service;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.celloud.box.config.BoxConfig;
import com.celloud.box.model.DataFile;
import com.celloud.box.utils.OSSProgressListener;

@Component
public class FileUpload {
	private static Logger logger = LoggerFactory.getLogger(FileUpload.class);
	@Resource
    private OSSService ossService;
	@Resource
	private BoxConfig config;
    @Resource
    private BoxService boxService;

    public void upload(ExecutorService fixedThreadPool, String file) {
		DataFile dataFile = DataFile.load(file + ".json");
		boolean result = false;
		if (dataFile != null) {
			String location = null;
			for (int i = 0; i <= config.getMaxRetry(); i++) {
                location = ossService.upload(dataFile.getObjectKey(), new File(file),
						new OSSProgressListener(dataFile.getUserId(), dataFile.getFilename(), dataFile.getDataKey()));
				if (location != null) {
					result = true;
					break;
				}
			}
		} else {
			result = true;
		}
        if (result) {
            logger.info("文件上传成功, {}", file);
            dataFile = DataFile.load(new File(file + ".json"));
            dataFile.setUploaded(Boolean.TRUE);
            dataFile.serialize();
            boxService.finish(dataFile);
            boxService.updatefile(dataFile);
        } else {
            logger.info("文件上传失败，重新上传：{}", file);
            fixedThreadPool.execute(() -> {
                this.upload(fixedThreadPool, file);
            });
        }
	}

	public List<String> getAll() {
        // return new ArrayList<String>(queue);
        return null;
	}

	public void printAll() {

        // logger.info("##################uploading##################");
        // for (String file : uploading) {
        // logger.info(file);
        // }
        // if (uploading.size() <= 0) {
        // logger.info("没有需要上传的文件......");
        // }
        // if (queue.size() > 0) {
        // logger.info("################## waiting ##################");
        // Iterator<String> it = queue.iterator();
        // while (it.hasNext()) {
        // logger.info(it.next());
        // }
        // }
        // logger.info("#############################################");
	}
}
