package com.celloud.box.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.celloud.box.constants.Response;
import com.celloud.box.utils.UploadPath;

@RestController
@RequestMapping("box")
public class BoxController {
	private Logger logger = LoggerFactory.getLogger(BoxController.class);

	@RequestMapping("upload")
	public Response upload(@RequestParam("file") CommonsMultipartFile file, Integer userId, Integer chunk,
			Integer chunks, String name, Date lastModifiedDate, long size, Integer tagId, String batch,
			Integer needSplit) {
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
		logger.info("【{}】 chunk={}\t{}", name, chunk, getLoaded(userId, uniqueName));
		if (chunks == null || chunks == 0 || chunk == chunks - 1) {
			logger.info("文件上传完成【{}】", name);
			File tempFile = chunkFile.getParentFile();
			int total = chunks == null ? 0 : chunks.intValue();
			File f = new File(tempFile.getParentFile().getAbsolutePath() + File.separatorChar + uniqueName);
			try {
				for (int i = 0; i < total; i++) {// 有可能是性能瓶颈
					FileUtils.writeByteArrayToFile(f, FileUtils.readFileToByteArray(
							new File(tempFile.getAbsolutePath() + File.separatorChar + i)), i != 0);
				}
				FileUtils.forceDelete(tempFile);
			} catch (IOException e) {
				logger.error("合并文件失败！", e);
				return new Response("文件上传失败，服务器异常！");
			}
		}
		return Response.SUCCESS;
	}

	@RequestMapping("checkBreakpoints")
	public Response checkBreakpoints(Integer userId, String name, Date lastModifiedDate, long size) {
		String uniqueName = UploadPath.getUniqueName(userId, name, lastModifiedDate, size);
		return Response.SUCCESS.setData(getLoaded(userId, uniqueName));
	}

	private long getLoaded(Integer userId, String uniqueName) {
		String folder = UploadPath.getPath(userId);
		File file = new File(folder + File.separatorChar + uniqueName);
		logger.info(file.getAbsolutePath());
		long loaded = 0L;
		if (!file.exists() || file.isFile()) {
			return loaded;
		}
		File[] files = file.listFiles();
		if (files == null || files.length == 0) {
			return loaded;
		}
		for (int i = 0; i <= files.length; i++) {
			File f = new File(folder + File.separatorChar + uniqueName + File.separatorChar + i);
			if (!f.exists()) {
				break;
			}
			loaded += f.length();
		}
		return loaded;
	}
}
