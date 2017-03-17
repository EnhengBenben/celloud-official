package com.celloud.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.celloud.constants.BoxUploadState;
import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.DataState;
import com.celloud.constants.FileFormat;
import com.celloud.constants.ReportPeriod;
import com.celloud.constants.ReportType;
import com.celloud.mapper.DataFileMapper;
import com.celloud.mapper.SampleMapper;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Sample;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.AppService;
import com.celloud.service.BoxApiService;
import com.celloud.service.DataService;
import com.celloud.service.ExpensesService;
import com.celloud.service.ExperimentService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.service.RunService;
import com.celloud.service.TaskService;
import com.celloud.utils.CheckFileTypeUtil;
import com.celloud.utils.DataUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.OSSUtils;
import com.celloud.utils.PerlUtils;
import com.celloud.utils.UploadPathUtils;

/**
 * 数据管理服务实现类
 * 
 * @author han
 * @date 2015年12月23日 下午6:20:22
 */
@Service("dataService")
public class DataServiceImpl implements DataService {
	private static Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);
	@Resource
	DataFileMapper dataFileMapper;
	@Resource
	private AppService appService;
	@Resource
	private ProjectService projectService;
	@Resource
	private ReportService reportService;
	@Resource
	private TaskService taskService;
	@Resource
	ExpensesService expenseService;
	@Resource
	ExperimentService expService;
	@Resource
	private RunService runService;
	@Resource
	private BoxApiService boxApiService;
	@Resource
	private SampleMapper sampleMapper;

	@Override
	public Integer countData(Integer userId) {
		return dataFileMapper.countData(userId, DataState.ACTIVE);
	}

	@Override
	public Long sumData(Integer userId) {
		return dataFileMapper.sumData(userId, DataState.ACTIVE);
	}

	@Override
	public List<Map<String, String>> countData(Integer userId, String time) {
		return dataFileMapper.countDataByTime(userId, time, DataState.ACTIVE);
	}

	@Override
	public int addDataInfo(DataFile data) {
		dataFileMapper.addDataInfo(data);
		return data.getFileId();
	}

	@Override
	public int updateDataInfoByFileId(DataFile data) {
		return dataFileMapper.updateDataInfoByFileId(data);
	}

	@Override
	public int updateDataInfoByFileIdAndTagId(DataFile data, Integer tagId) {
		Integer result = dataFileMapper.selectTagRelat(data.getFileId(), tagId);
		if (result == null || result.intValue() == 0) {
			dataFileMapper.insertFileTagRelat(data.getFileId(), tagId);
		}
		DataFile data_s = dataFileMapper.selectByPrimaryKey(data.getFileId());
		Sample sample = null;
		if (tagId.intValue() == 2) {
			sample = sampleMapper.getSampleByExperName(StringUtils.splitByWholeSeparator(data_s.getFileName(), "_")[0],
					DataState.ACTIVE);
		} else {
			if (data_s.getFileName().contains(".")) {
				sample = sampleMapper.getSampleByExperName(
						StringUtils.splitByWholeSeparator(data_s.getFileName(), ".")[0], DataState.ACTIVE);
			}
		}
		if (sample != null) {
			Integer count = dataFileMapper.getFileSampleCount(data.getFileId(), sample.getSampleId());
			if (count != null && count.intValue() <= 0) {
				dataFileMapper.addFileSampleRelat(data.getFileId(), sample.getSampleId());
			}
		}
		int updateDataInfoByFileId = dataFileMapper.updateDataInfoByFileId(data);
		return updateDataInfoByFileId;
	}

	@Override
	public List<Map<String, String>> sumData(Integer userId, String time) {
		return dataFileMapper.sumDataByTime(userId, time, DataState.ACTIVE);
	}

	@Override
	public PageList<DataFile> dataAllList(Page page, Integer userId) {
		List<DataFile> lists = dataFileMapper.findAllDataLists(page, userId, DataState.ACTIVE, ReportType.DATA,
				ReportPeriod.COMPLETE);
		return new PageList<>(page, lists);
	}

	@Override
	public PageList<DataFile> dataListByAppId(Page page, Integer userId, Integer appId, String condition, Integer sort,
			String sortDate, String sortName, String sortBatch) {
		List<DataFile> lists = dataFileMapper.findDataListsByAppId(page, userId, DataState.ACTIVE, appId, condition,
				sort, sortDate, sortName, sortBatch);
		return new PageList<>(page, lists);
	}

	@Override
	public PageList<DataFile> dataLists(Page page, Integer userId, String condition, int sort, String sortDateType,
			String sortNameType, String sortAnotherName, String sortRun) {
		List<DataFile> lists = dataFileMapper.findDataLists(page, userId, condition, sort, sortDateType, sortNameType,
				DataState.ACTIVE, ReportType.DATA, ReportPeriod.COMPLETE, sortAnotherName, sortRun);
		return new PageList<>(page, lists);
	}

	@Override
	public PageList<DataFile> dataLists(Page page, Integer userId, String condition, int sort, String sortDate,
			String sortBatch, String sortName) {
		List<DataFile> lists = dataFileMapper.findDataListsSortMore(page, userId, condition, sort, sortDate, sortBatch,
				sortName, DataState.ACTIVE, ReportType.DATA, ReportPeriod.COMPLETE);
		return new PageList<>(page, lists);
	}

	@Override
	public Integer getFormatByIds(String dataIds) {
		Map<String, Object> map = dataFileMapper.findFormatByIds(dataIds);
		Integer result = null;
		if (map.get("formatNum") != null && (Long) map.get("formatNum") > 1) {
			result = -1;
		} else {
			result = (Integer) map.get("fileFormat");
		}
		return result;
	}

	@Override
	public List<Integer> findRunningAppData(String dataIds, Integer appId) {
		return dataFileMapper.findRunningAppData(dataIds, appId, DataState.ACTIVE, ReportPeriod.COMPLETE);
	}

	@Override
	public String queryFileSize(String dataIds) {
		return dataFileMapper.queryFileSize(dataIds);
	}

	@Override
	public List<DataFile> findDatasById(String dataIds) {
		// String[] dataIdArr = dataIds.split(",");
		return dataFileMapper.findDatasById(dataIds);
	}

	@Override
	public Integer dataRunning(String appIds) {
		return dataFileMapper.queryDataRunning(appIds, ReportPeriod.NOT_RUN, DataState.ACTIVE, ReportType.DATA);
	}

	@Override
	public Integer delete(String dataIds) {
		String[] dataIdArr = dataIds.split(",");
		int index = 0;
		for (String dataId : dataIdArr) {
			DataFile data = new DataFile();
			data.setFileId(Integer.parseInt(dataId));
			data.setState(DataState.DEELTED);
			index += dataFileMapper.updateByPrimaryKeySelective(data);
		}
		return index;
	}

	@Override
	public DataFile getDataById(Integer dataId) {
		return dataFileMapper.selectByPrimaryKey(dataId);
	}

	@Override
	public List<Map<String, String>> getStrainList(Integer userId) {
		List<Map<String, String>> mlist = new ArrayList<>();
		List<String> list = dataFileMapper.queryStrainList(userId);
		for (String s : list) {
			Map<String, String> map = new HashMap<>();
			map.put("id", s);
			map.put("text", s);
			mlist.add(map);
		}
		return mlist;
	}

	@Override
	public Integer updateDataByIds(String dataIds, DataFile data) {
		return dataFileMapper.updateDataByIds(dataIds, data.getStrain(), data.getSample(), new Date(),
				data.getAnotherName(), data.getDataTags());
	}

	@Override
	public Integer updateDatas(List<DataFile> dataList) {
		Integer index = 0;
		for (DataFile d : dataList) {
			index += dataFileMapper.updateByPrimaryKeySelective(d);
		}
		return index;
	}

	@Override
	public Map<String, String> countUserRunFileNum(Integer userId) {
		return dataFileMapper.countFileNumByUserId(userId);
	}

	@Override
	public List<DataFile> getDatasInProject(Integer projectId) {
		return dataFileMapper.getDatasInProject(projectId);
	}

	@Override
	public List<Map<String, Object>> getDatasMapInProject(Integer projectId) {
		return dataFileMapper.getDatasMapInProject(projectId);
	}

	@Override
	public List<Map<String, String>> countDataFile(Integer userId) {
		return null;
	}

	@Override
	public DataFile getDataByKey(String dataKey) {
		return dataFileMapper.selectByDataKey(dataKey);
	}

	@Override
	public List<DataFile> selectDataByKeys(String dataKeys) {
		return dataFileMapper.selectByDataKeys(dataKeys);
	}

	@Override
	public int updateByPrimaryKeySelective(DataFile record) {
		return dataFileMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateDataAndTag(DataFile record) {
		// 1.清除历史标签
		dataFileMapper.deleteDataTag(record.getFileId());
		// 2.插入新的标签
		dataFileMapper.insertDataTag(record);
		// 3.修改数据信息
		return dataFileMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<DataFile> getDataByAnotherName(Integer userId, String anotherName) {
		return dataFileMapper.getDataByAnotherName(userId, anotherName, DataState.ACTIVE);
	}

	@Override
	public List<DataFile> getDataByBatchAndFileName(Integer userId, String batch, String fileName) {
		return dataFileMapper.getDataByBatchAndFileName(userId, batch, fileName, DataState.ACTIVE);
	}

	@Override
	public List<String> getBatchList(Integer userId) {
		return dataFileMapper.getBatchList(userId, DataState.ACTIVE);
	}

	@Override
	public List<String> getBatchListByAppId(Integer userId, Integer appId) {
		return dataFileMapper.getBatchListByAppId(userId, DataState.ACTIVE, appId);
	}

	@Override
	public List<String> getBsiBatchList(Integer userId, Integer appId) {
		return dataFileMapper.getBsiBatchList(userId, DataState.ACTIVE, appId);
	}

	@Override
	public PageList<Map<String, Object>> filterRockyList(Page page, String sample, String condition, String sidx,
			String sord) {
		List<Map<String, Object>> lists = dataFileMapper.filterRockyList(page, ConstantsData.getLoginUserId(),
				DataState.ACTIVE, ReportType.DATA, ReportPeriod.COMPLETE, sample, condition, sidx, sord);
		return new PageList<>(page, lists);
	}

	@Override
	public String getAnotherName(HttpServletRequest request, String filePath, String fileDataKey) {
		ServletContext sc = request.getSession().getServletContext();
		// TODO 写死的路径
		String perlPath = sc.getRealPath("/resources") + "/plugins/getAliases.pl";
		String outPath = sc.getRealPath("/temp") + "/" + fileDataKey;
		StringBuffer command = new StringBuffer();
		command.append("perl ").append(perlPath).append(" ").append(filePath).append(" ").append(outPath);
		PerlUtils.excutePerl(command.toString());
		return FileTools.getFirstLine(outPath);
	}

	@Override
	public String getAnotherName(String perlPath, String filePath, String outPath) {
		perlPath = !StringUtils.isBlank(perlPath) ? perlPath : ConstantsData.getAnotherNamePerlPath(null);
		outPath = !StringUtils.isBlank(outPath) ? outPath : filePath + ".txt";
		StringBuffer command = new StringBuffer();
		command.append("perl ").append(perlPath).append(" ").append(filePath).append(" ").append(outPath);
		PerlUtils.excutePerl(command.toString());
		String anothername = FileTools.getFirstLine(outPath);
		File file = new File(outPath);
		if (file.exists()) {
			file.delete();
		}
		return anothername;
	}

	@Override
	public int updateFileInfo(Integer dataId, String dataKey, String filePath, String batch, Integer fileFormat,
			String md5, String anotherName, Integer tagId) {
		DataFile data = new DataFile();
		data.setFileId(dataId);
		data.setDataKey(dataKey);
		data.setPath(filePath);
		if (filePath != null) {
			data.setSize(FileTools.getFileSize(filePath));
		} else {
			data.setSize(0L);
		}
		data.setBatch(batch);
		data.setFileFormat(fileFormat);
		data.setMd5(md5);
		data.setState(DataState.ACTIVE);
		data.setUploadState(BoxUploadState.ON_CELLOUD);
		if (fileFormat == FileFormat.BAM) {
			data.setAnotherName(anotherName);
		}
		if (tagId == null) {
			return updateDataInfoByFileId(data);
		} else {
			return updateDataInfoByFileIdAndTagId(data, tagId);
		}
	}

	@Override
	public void updateUploadState(Integer fileId, String objectKey, int state) {
		DataFile data = new DataFile();
		data.setFileId(fileId);
		data.setOssPath(objectKey);
		data.setUploadState(state);
		updateByPrimaryKeySelective(data);
	}

	@Override
	public Integer addFileInfo(Integer userId, String fileName) {
		DataFile data = new DataFile();
		data.setUserId(userId);
		data.setIsRun(0);
		data.setUploadState(3);
		// 只允许字母和数字
		String regEx = "[^\\w\\.\\_\\-\u4e00-\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(fileName);
		// replaceAll()将中文标号替换成英文标号
		data.setFileName(m.replaceAll("").trim());
		data.setState(DataState.DEELTED);
		return addDataInfo(data);
	}

	@Override
	public List<Map<String, Object>> getDataFileFromTbTask(Integer projectId) {
		return this.dataFileMapper.getDataFileFromTbTask(projectId);
	}

	@Override
	public Integer addAndRunFile(Integer userId, String objectKey) {
		logger.info("创建web直传oss的文件({})：{}", userId, objectKey);
		Map<String, String> metadata = OSSUtils.getMetadata(objectKey);
		long size = Long.parseLong(metadata.get("size"));
		int tagId = Integer.parseInt(metadata.get("tagid"));
		String name = metadata.get("name");
		int dataId = addFileInfo(userId, name);
		String fileDataKey = DataUtil.getNewDataKey(dataId);
		DataFile data = new DataFile();
		data.setFileId(dataId);
		data.setDataKey(fileDataKey);
		data.setSize(size);
		String batch = metadata.get("batch");
		data.setBatch(batch);
		data.setMd5(metadata.get("etag").toLowerCase());
		data.setFileName(name);
		data.setState(DataState.ACTIVE);
		data.setCreateDate(new Date());
		data.setOssPath(objectKey);
		data.setUserId(userId);
		updateDataInfoByFileIdAndTagId(data, tagId);
		String newObjectKey = UploadPathUtils.getObjectKey(userId, fileDataKey, FileTools.getExtName(name));
		long time = System.currentTimeMillis();
		OSSUtils.moveObject(objectKey, newObjectKey, metadata);
		logger.info("移动oss文件用时：{}", System.currentTimeMillis() - time);
		String path = ConstantsData.getOfsPath() + newObjectKey;
		logger.info("文件：name={},path={}", data.getFileName(), path);
		time = System.currentTimeMillis();
		int fileFormat = new CheckFileTypeUtil().checkFileType(new File(path).getName(),
				new File(path).getParentFile().getAbsolutePath());
		logger.info("获取文件类型用时：{}", System.currentTimeMillis() - time);
		if (fileFormat == FileFormat.BAM) {
			time = System.currentTimeMillis();
			data.setAnotherName(getAnotherName("", path, ""));
			logger.info("获取anotherName用时：{}", System.currentTimeMillis() - time);
		}
		data.setFileFormat(fileFormat);
		data.setOssPath(newObjectKey);
		dataFileMapper.updateByPrimaryKeySelective(data);

		data = dataFileMapper.selectByPrimaryKey(dataId);
		if (Constants.bsiTags.containsKey(tagId)) {
			runService.bsiCheckRun(batch, dataId, fileDataKey, name, userId, fileFormat);
		} else if (tagId == 2) {
			runService.rockyCheckRun(123, data);
		}
		return dataId;
	}

	@Override
	public Integer getSampleIdByDataKey(String dataKey) {
		Long id = dataFileMapper.getSampleIdByDataKey(dataKey);
		return id == null ? null : id.intValue();
	}

	@Override
	public String getBatchByDataKey(String dataKey) {
		return dataFileMapper.selectByDataKey(dataKey).getBatch();
	}

}
