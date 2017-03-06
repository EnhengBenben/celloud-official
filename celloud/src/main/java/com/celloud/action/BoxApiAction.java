package com.celloud.action;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.celloud.constants.BoxUploadState;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.DataState;
import com.celloud.constants.FileFormat;
import com.celloud.model.BoxFile;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.OSSConfig;
import com.celloud.model.mysql.Sample;
import com.celloud.service.BoxApiService;
import com.celloud.service.BoxConfigService;
import com.celloud.service.DataService;
import com.celloud.service.OSSConfigService;
import com.celloud.service.SampleService;
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
	@Resource
	private OSSConfigService ossService;
	@Autowired
	private SampleService sampleService;

    /**
     * 
     * @description 根据r1, r2的id修改tb_file的状态
     * @author miaoqi
     * @date 2017年3月2日 上午11:21:18
     * @param r1Id
     * @param r2Id
     * @return
     */
    @RequestMapping("fileRunOver")
    public Response fileRunOver(Integer r1Id, Integer r2Id) {
        try {
            logger.info("盒子内运行split完成, 修改tb_file的isRun状态 r1Id = {}, r2Id = {}", r1Id, r2Id);
            String dataIds = r1Id + "," + r2Id;
            List<DataFile> dataFiles = dataService.findDatasById(dataIds);
            dataFiles.forEach(dataFile -> {
                dataFile.setIsRun(0);
                dataService.updateByPrimaryKeySelective(dataFile);
            });
        } catch (Exception e) {
            return Response.FAIL();
        }
        return Response.SUCCESS();
    }

	/**
	 * 
	 * @author miaoqi
	 * @date 2017年1月20日下午2:13:39
	 * @description 根据storageName获取运行split所需的.txt文件
	 *
	 * @return
	 */
	@RequestMapping("splittxt")
	public Response splittxt(Integer userId, String pubName, String storageName,
	        String batch) {
		logger.info("盒子请求运行split所需的txt文件");
		List<Sample> sampleList = sampleService.getSamplesByStorageName(storageName);
		if (sampleList != null && sampleList.size() > 0) {
            logger.info("文库 {} 下样本列表长度 {} ", storageName, sampleList.size());
			DataFile data = new DataFile();
			data.setFileName(pubName + ".txt");
			data.setUserId(userId);
			data.setCreateDate(new Date());
			data.setFileFormat(FileFormat.NONE);
            data.setState(DataState.DEELTED);
			data.setBatch(batch);
            Integer id = dataService.addDataInfo(data);
			String index_dataKey = DataUtil.getNewDataKey(id);
            data.setFileId(id);
			data.setDataKey(index_dataKey);
			String path = new StringBuffer().append(PropertiesUtil.bigFilePath).append(userId).append("/")
			        .append(DateUtil.getDateToString("yyyyMMdd")).append("/").append(index_dataKey + ".txt").toString();
			StringBuilder slist = new StringBuilder();
			for (Sample s : sampleList) {
				slist.append(s.getExperSampleName())
				        .append(":").append(s.getSindex().contains(":")
				                ? StringUtils.splitByWholeSeparator(s.getSindex(), ":")[1] : s.getSindex())
				        .append("\n");
			}
			FileTools.appendWrite(path, slist.toString());
			data.setPath(path);
			data.setSize(FileUtils.sizeOf(new File(path)));
			dataService.updateByPrimaryKeySelective(data);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dataKey", data.getDataKey());
			map.put("fileId", data.getFileId());
			map.put("ext", ".txt");
			map.put("newName", data.getDataKey() + ".txt");
			map.put("content", slist.toString());
			map.put("size", data.getSize());
			return Response.SUCCESS(map);
		}
		return null;
	}

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
	public Response newfile(Integer userId, String name, String anotherName, String md5, long size, Integer tagId,
			String batch) {
		logger.info("user {} new file : {}", userId, name);
		Map<String, Object> values = new HashMap<>();
		int dataId = dataService.addFileInfo(userId, name);
		String fileDataKey = DataUtil.getNewDataKey(dataId);
		String ext = FileTools.getExtName(name);
		DataFile data = new DataFile();
		data.setFileId(dataId);
		data.setDataKey(fileDataKey);
		data.setAnotherName(anotherName);
		data.setMd5(md5);
		data.setSize(size);
		data.setBatch(batch);
        if (tagId == 1 || tagId == 40 || tagId == 41 || tagId == 42 || tagId == 43 || tagId == 44) {
            data.setIsRun(1);
        } else {
            data.setIsRun(0);
        }
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
			boolean splited, HttpServletRequest request) {
		ConstantsData.getAnotherNamePerlPath(request);
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
		boxFile.setAnotherName(file.getAnotherName());
		boxFile.setDataKey(file.getDataKey());
		boxFile.setMd5(file.getMd5());
		boxFile.setSplited(splited);
		boxFile.setNeedSplit(needSplit);
		boxFile.setObjectKey(objectKey);
		boxFile.setPath(folderByDay + File.separator + newName);
		boxFile.setTagId(tagId);
		boxFile.setUserId(file.getUserId());
		dataService.updateUploadState(fileId, objectKey, BoxUploadState.IN_OSS);
		// apiService.downloadFromOSS(boxFile);
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
		if (extranet.startsWith("192.168.22")) {
			extranet = "127.0.0.1";
		}
		logger.info("更新盒子状态：serialNumber={},version={},intranet={},extranet={},port={}", serialNumber, version, ip,
				extranet, port);
		boolean result = configService.updateBoxHealth(serialNumber, version, ip, extranet, port);
		return result ? Response.SUCCESS() : Response.FAIL();
	}

	@RequestMapping("ossConfig")
	public Response ossConfig(HttpServletRequest request, String serialNumber, String version, String ip,
			Integer port) {
		String extranet = UserAgentUtil.getIp(request);
		Response response = Response.FAIL();
		boolean result = configService.checkConfig(serialNumber, version, ip, extranet, port);
		OSSConfig config = null;
		if (!result) {
			logger.info("获取oos配置的参数不合法：serialNumber={},version={},intranet={},extranet={},port={}", serialNumber,
					version, ip, extranet, port);
			response.setMessage("参数不合法！");
			return response;
		}
		config = ossService.getLatest();
		if (config == null) {
			response.setMessage("没有可用的oss配置");
		} else {
			response = Response.SUCCESS().setData(config);
		}
		return response;
	}

}
