package com.celloud.box.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.celloud.box.config.OSSConfig;
import com.celloud.box.constants.Response;
import com.celloud.box.model.Newfile;
import com.celloud.box.service.ApiService;
import com.celloud.box.service.BoxService;
import com.celloud.box.utils.UploadPath;

@RestController
@RequestMapping("box")
@CrossOrigin(origins = { "http://localhost:8080", "https://www.celloud.cn", "https://celloud.cn",
		"https://www.celloud.cc", "https://celloud.cc", "https://www.genecode.cn",
		"https://genecode.cn" }, methods = { RequestMethod.POST, RequestMethod.GET }, allowedHeaders = { "*" })
public class BoxController {
	private Logger logger = LoggerFactory.getLogger(BoxController.class);
	@Resource
	private BoxService boxService;
	@Resource
	private ApiService apiService;

	@RequestMapping("upload")
	public Response upload(@RequestParam("file") MultipartFile file, Integer userId, Integer chunk, Integer chunks,
			String name, Date lastModifiedDate, long size, Integer tagId, String batch, Integer needSplit) {
		if (file == null || file.isEmpty()) {
			return new Response("没有要上传的文件！");
		}
		logger.info("name={}\tsize={}\tlastModifiedDate={}", name, size,
				new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(lastModifiedDate));
		String folder = UploadPath.getPath(userId);
		String uniqueName = UploadPath.getUniqueName(userId, name, lastModifiedDate, size);
		File chunkFile = new File(folder + uniqueName + "_chunks" + File.separatorChar + chunk);
		chunkFile.getParentFile().mkdirs();
		try {
			file.transferTo(chunkFile);
		} catch (IllegalStateException | IOException e) {
			logger.error("文件上传失败！", e);
			return new Response("文件上传失败，服务器异常！");
		}
		logger.info("【{}】 chunk={} {}", name, chunk, getLoaded(userId, uniqueName));
		if (chunks == null || chunks == 0 || chunk == chunks - 1) {
			logger.info("文件上传完成【{}】", name);
			File f = finish(chunkFile.getParentFile(), chunks == null ? 0 : chunks.intValue(), uniqueName);
			if (f == null) {
				return new Response("文件上传失败，服务器异常！");
			}
			// TODO 通知celloud有新文件上传
			Newfile newfile = apiService.newfile(userId, name, size, tagId, batch, needSplit);
			// TODO 将文件上传到oss
			boxService.upload2oss(userId, newfile.getDataKey(), newfile.getExt(), f);
			logger.info("finish....................");
			// TODO 通知celloud文件已经上传到oss
		}
		return Response.SUCCESS;
	}

	@RequestMapping("checkBreakpoints")
	public Response checkBreakpoints(Integer userId, String name, Date lastModifiedDate, long size) {
		String uniqueName = UploadPath.getUniqueName(userId, name, lastModifiedDate, size);
		return Response.SUCCESS.setData(getLoaded(userId, uniqueName));
	}

	private File finish(File tempFile, int total, String uniqueName) {
		File f = new File(tempFile.getParentFile().getAbsolutePath() + File.separatorChar + getRandomName(uniqueName));
		while (f.exists()) {
			f = new File(tempFile.getParentFile().getAbsolutePath() + File.separatorChar + getRandomName(uniqueName));
		}
		try {
			for (int i = 0; i < total; i++) {// 有可能是性能瓶颈
				FileUtils.writeByteArrayToFile(f,
						FileUtils.readFileToByteArray(new File(tempFile.getAbsolutePath() + File.separatorChar + i)),
						i != 0);
			}
			FileUtils.forceDelete(tempFile);
		} catch (IOException e) {
			logger.error("合并文件失败！", e);
			return null;
		}
		return f;
	}

	private String getRandomName(String uniqueName) {
		return (new Random().nextInt(9000) + 1000) + "_" + uniqueName;
	}

	private long getLoaded(Integer userId, String uniqueName) {
		String folder = UploadPath.getPath(userId);
		String path = folder + File.separatorChar + uniqueName + "_chunks";
		File file = new File(path);
		long loaded = 0L;
		if (!file.exists() || file.isFile()) {
			return loaded;
		}
		File[] files = file.listFiles();
		if (files == null || files.length == 0) {
			return loaded;
		}
		for (int i = 0; i <= files.length; i++) {
			File f = new File(path + File.separatorChar + i);
			if (!f.exists()) {
				break;
			}
			loaded += f.length();
		}
		return loaded;
	}
}
