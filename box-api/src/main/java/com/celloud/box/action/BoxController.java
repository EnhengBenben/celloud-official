package com.celloud.box.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.celloud.box.constants.Response;
import com.celloud.box.model.DataFile;
import com.celloud.box.service.ApiService;
import com.celloud.box.service.BoxService;
import com.celloud.box.service.FileUploadQueue;
import com.celloud.box.utils.UploadPath;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("box")
@CrossOrigin(origins = { "http://localhost:8080", "http://127.0.0.1:8080", "https://www.celloud.cn",
		"https://celloud.cn", "https://www.celloud.cc", "https://celloud.cc", "https://www.genecode.cn",
		"https://genecode.cn", "http://www.celloud.cn", "http://celloud.cn", "http://www.celloud.cc",
		"http://celloud.cc", "http://www.genecode.cn",
		"http://genecode.cn" }, methods = { RequestMethod.POST, RequestMethod.GET }, allowedHeaders = { "*" })
@Api(value="box-controller",description="盒子上传数据的接口",produces="application/json;charset=utf-8")
public class BoxController {
	private static Logger logger = LoggerFactory.getLogger(BoxController.class);
	@Resource
	private BoxService boxService;
	@Resource
	private FileUploadQueue queue;
	@Resource
	private ApiService apiService;

	@RequestMapping(value = "alive", method = RequestMethod.GET)
	@ApiOperation(value = "检测盒子是否存活", produces = "application/json;charset=utf-8")
	public Response alive() {
		logger.info("checking alive...");
		return Response.SUCCESS;
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ApiOperation(value = "上传文件", produces = "application/json;charset=utf-8")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Integer", required = true),
			@ApiImplicitParam(name = "chunk", value = "正在上传的文件分片编号", dataType = "Integer", required = true),
			@ApiImplicitParam(name = "chunks", value = "文件分片总片数", dataType = "Integer", required = true),
			@ApiImplicitParam(name = "name", value = "文件名", dataType = "String", required = true),
			@ApiImplicitParam(name = "lastModifiedDate", value = "文件最后修改时间", dataType = "Date", required = true),
			@ApiImplicitParam(name = "size", value = "文件大小", dataType = "Long", required = true),
			@ApiImplicitParam(name = "tagId", value = "文件数据标签", dataType = "Integer", required = true),
			@ApiImplicitParam(name = "batch", value = "文件批次", dataType = "String", required = true),
			@ApiImplicitParam(name = "needSplit", value = "是否需要先运行split", dataType = "Integer", required = true) })
	public Response upload(@RequestParam(name = "file", value = "file", required = true) MultipartFile file,
			Integer userId, Integer chunk, Integer chunks, String name, Date lastModifiedDate, long size, Integer tagId,
			String batch, Integer needSplit) {
		if (file == null || file.isEmpty()) {
			return new Response("没有要上传的文件！");
		}
		tagId = tagId == null ? 118 : tagId;
		logger.debug("name={}\tsize={}\tlastModifiedDate={}", name, size,
				new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(lastModifiedDate));
		String folder = UploadPath.getUploadingPath(userId);
		String uniqueName = UploadPath.getUniqueName(userId, name, lastModifiedDate.getTime(), size);
		File chunkFile = new File(folder + uniqueName + "_chunks" + File.separatorChar + chunk);
		chunkFile.getParentFile().mkdirs();
		try {
			file.transferTo(chunkFile);
		} catch (IllegalStateException | IOException e) {
			logger.error("文件上传失败！", e);
			return new Response("文件上传失败，服务器异常！");
		}
		logger.debug("【{}】 chunk={} {}", name, chunk, getLoaded(userId, uniqueName));
		if (chunks == null || chunks == 0 || chunk == chunks - 1) {
			logger.info("文件上传完成【{}】", name);
			File f = finish(chunkFile.getParentFile(), chunks == null ? 0 : chunks.intValue(), uniqueName);
			if (f == null) {
				return new Response("文件上传失败，服务器异常！");
			}
			DataFile dataFile = boxService.save(userId, name, null, tagId, batch, needSplit, f);
			dataFile = boxService.newfile(dataFile);
			if (dataFile != null) {
				queue.add(f);
				boxService.checkRunSplit(dataFile);
			}
		}
		return Response.SUCCESS;
	}

	@RequestMapping(value = "checkBreakpoints", method = RequestMethod.GET)
	@ApiOperation(value = "获取文件上传上传的断点位置", produces = "application/json;charset=utf-8")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Integer", required = true),
			@ApiImplicitParam(name = "name", value = "文件名", dataType = "String", required = true),
			@ApiImplicitParam(name = "size", value = "文件大小", dataType = "Long", required = true),
			@ApiImplicitParam(name = "lastModifiedDate", value = "文件最后修改时间", dataType = "Date", required = true) })
	public Response checkBreakpoints(Integer userId, String name, Date lastModifiedDate, long size) {
		String uniqueName = UploadPath.getUniqueName(userId, name, lastModifiedDate.getTime(), size);
		return Response.SUCCESS.setData(getLoaded(userId, uniqueName));
	}

	private File finish(File tempFile, int total, String uniqueName) {
		File f = new File(
				tempFile.getParentFile().getAbsolutePath() + File.separatorChar + UploadPath.getRandomName(uniqueName));
		while (f.exists()) {
			f = new File(tempFile.getParentFile().getAbsolutePath() + File.separatorChar
					+ UploadPath.getRandomName(uniqueName));
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

	private long getLoaded(Integer userId, String uniqueName) {
		String folder = UploadPath.getUploadingPath(userId);
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
