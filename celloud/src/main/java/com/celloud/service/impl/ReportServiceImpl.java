package com.celloud.service.impl;

import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.stereotype.Service;

import com.celloud.constants.DataState;
import com.celloud.constants.ReportPeriod;
import com.celloud.constants.ReportType;
import com.celloud.constants.TaskPeriod;
import com.celloud.constants.TimeState;
import com.celloud.dao.ReportDao;
import com.celloud.mapper.AppMapper;
import com.celloud.mapper.DataFileMapper;
import com.celloud.mapper.PriceMapper;
import com.celloud.mapper.ReportMapper;
import com.celloud.mapper.TaskMapper;
import com.celloud.model.mongo.ABINJ;
import com.celloud.model.mongo.AccuGddDiseaseDict;
import com.celloud.model.mongo.AccuSeqα2;
import com.celloud.model.mongo.AccuSeqα2Fill;
import com.celloud.model.mongo.BRAF;
import com.celloud.model.mongo.BSI;
import com.celloud.model.mongo.CmpFilling;
import com.celloud.model.mongo.CmpGeneDetectionDetail;
import com.celloud.model.mongo.CmpGeneSnpResult;
import com.celloud.model.mongo.CmpReport;
import com.celloud.model.mongo.DPD;
import com.celloud.model.mongo.EGFR;
import com.celloud.model.mongo.EGFRCount;
import com.celloud.model.mongo.FSocg;
import com.celloud.model.mongo.GddDiseaseDict;
import com.celloud.model.mongo.GeneDetectionResult;
import com.celloud.model.mongo.HBV;
import com.celloud.model.mongo.HCV;
import com.celloud.model.mongo.HCVCount;
import com.celloud.model.mongo.KRAS;
import com.celloud.model.mongo.KRASCount;
import com.celloud.model.mongo.MIB;
import com.celloud.model.mongo.Oncogene;
import com.celloud.model.mongo.Pgs;
import com.celloud.model.mongo.Rocky;
import com.celloud.model.mongo.S16;
import com.celloud.model.mongo.Split;
import com.celloud.model.mongo.TBINH;
import com.celloud.model.mongo.TBRifampicin;
import com.celloud.model.mongo.TBRifampicinCount;
import com.celloud.model.mongo.TaskQueue;
import com.celloud.model.mongo.Translate;
import com.celloud.model.mongo.UGT;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Report;
import com.celloud.model.mysql.Task;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.ReportService;
import com.celloud.utils.CustomStringUtils;
import com.celloud.utils.DateUtil;
import com.celloud.utils.ExcelUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.PropertiesUtil;

import net.sf.json.JSONObject;

/**
 * 报告接口 实现类
 * 
 * @author han
 * @date 2015年12月25日 下午3:50:02
 */
@Service("reportService")
public class ReportServiceImpl implements ReportService {

	@Resource
	ReportMapper reportMapper;
	@Resource
	ReportDao reportDao;
	@Resource
	DataFileMapper dataMapper;
	@Resource
	AppMapper appMapper;
	@Resource
	PriceMapper priceMapper;
	@Resource
	private TaskMapper taskMapper;

	Logger log = Logger.getLogger(this.getClass());

	@Override
	public Integer countReport(Integer userId) {
		return reportMapper.countReport(userId, DataState.ACTIVE, ReportType.PROJECT, ReportPeriod.COMPLETE);
	}

	@Override
	public List<Map<String, String>> countReport(Integer userId, String time) {
		return reportMapper.countReportByTime(userId, time, DataState.ACTIVE, ReportType.PROJECT,
				ReportPeriod.COMPLETE);
	}

	@Override
	public PageList<Map<String, Object>> getReportPageList(Integer userId, Page pager, String condition, String start,
			String end, Integer appId, Integer belongs) {
		List<Map<String, Object>> list = reportMapper.getReportList(userId, pager, condition, start, end, appId,
				ReportType.PROJECT, belongs);
		return new PageList<>(pager, list);
	}

	@Override
	public List<Map<String, String>> countAppRunNum(Integer userId) {
		return reportMapper.countAppRunNumByUserId(userId);
	}

	@Override
	public HBV getHBVReport(String dataKey, Integer projectId, Integer appId) {
		HBV hbv = reportDao.getDataReport(HBV.class, dataKey, projectId, appId);
		if (hbv == null)
			return null;
		// 其他突变位点排序
		Map<String, String> map = hbv.getOther();
		if (map != null) {
			String[] array = new String[map.size()];
			int i = 0;
			for (Map.Entry<String, String> m : map.entrySet()) {
				array[i] = m.getKey();
				i++;
			}
			Arrays.sort(array);
			StringBuffer sb = new StringBuffer();
			for (String s : array) {
				sb.append(map.get(s)).append(",");
			}
			String img = sb.toString();
			if (img.length() > 1) {
				hbv.setImgString(img.substring(0, img.length() - 1));
			} else {
				hbv.setImgString("");
			}
		}
		return hbv;
	}

	@Override
	public FSocg getFSocgReport(String dataKey, Integer projectId, Integer appId) {
		return reportDao.getDataReport(FSocg.class, dataKey, projectId, appId);
	}

	@Override
	public Integer getTBINHisWildByGeneNameAndUserId(Integer userId, String simpleGeneName, Integer isWild) {
		return reportDao.getTBINHisWild(userId, simpleGeneName, isWild);
	}

	@Override
	public Pgs getPgsReport(String dataKey, Integer projectId, Integer appId) {
		return reportDao.getDataReport(Pgs.class, dataKey, projectId, appId);
	}

	@Override
	public List<Pgs> getPgsProjectReport(Integer projectId) {
		return reportDao.getDataByProjectId(Pgs.class, projectId);
	}

	@Override
	public Oncogene getOncogeneReport(String dataKey, Integer projectId, Integer appId) {
		return reportDao.getDataReport(Oncogene.class, dataKey, projectId, appId);
	}

	@Override
	public HCV getHCVReport(String dataKey, Integer projectId, Integer appId) {
		return reportDao.getDataReport(HCV.class, dataKey, projectId, appId);
	}

	@Override
	public Translate getTranslateReport(String dataKey, Integer projectId, Integer appId) {
		return reportDao.getDataReport(Translate.class, dataKey, projectId, appId);
	}

	@Override
	public EGFR getEGFRReport(String dataKey, Integer projectId, Integer appId) {
		return reportDao.getDataReport(EGFR.class, dataKey, projectId, appId);
	}

	@Override
	public KRAS getKRASReport(String dataKey, Integer projectId, Integer appId) {
		return reportDao.getDataReport(KRAS.class, dataKey, projectId, appId);
	}

	@Override
	public UGT getUGTReport(String dataKey, Integer projectId, Integer appId) {
		return reportDao.getDataReport(UGT.class, dataKey, projectId, appId);
	}

	@Override
	public BRAF getBRAFReport(String dataKey, Integer projectId, Integer appId) {
		return reportDao.getDataReport(BRAF.class, dataKey, projectId, appId);
	}

	@Override
	public TBINH getTBINHReport(String dataKey, Integer projectId, Integer appId) {
		return reportDao.getDataReport(TBINH.class, dataKey, projectId, appId);
	}

	@Override
	public TBRifampicin getTBRifampicinReport(String dataKey, Integer projectId, Integer appId) {
		return reportDao.getDataReport(TBRifampicin.class, dataKey, projectId, appId);
	}

	@Override
	public DPD getDPDReport(String dataKey, Integer projectId, Integer appId) {
		return reportDao.getDataReport(DPD.class, dataKey, projectId, appId);
	}

	@Override
	public Map<String, Object> systemCount(Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		/*** 按月统计的每月上传的:数据量\数据大小\报告数量\APP运行次数 ***/
		List<Map<String, String>> monthData = dataMapper.countDataByTime(userId, TimeState.MONTH, DataState.ACTIVE);
		List<Map<String, String>> monthSize = dataMapper.sumDataByTime(userId, TimeState.MONTH, DataState.ACTIVE);

		List<Map<String, String>> monthReport = reportMapper.countReportMonthByUserId(userId);

		List<Map<String, String>> monthApp = appMapper.countMyAppByTime(userId, TimeState.MONTH, DataState.ACTIVE,
				DataState.ACTIVE);
		/**** 按周统计每周上传:数据量\数据大小\报告数量\APP数量 *****/
		List<Map<String, String>> weekData = dataMapper.countDataFileWeek(userId);
		List<Map<String, String>> weekReport = reportMapper.countReportWeekByUserId(userId);
		List<Map<String, String>> weekApp = appMapper.countWeekByUserId(userId);

		// 用户上传数据
		Long size = dataMapper.sumData(userId, DataState.ACTIVE);
		// 已运行、未运行
		Map<String, String> fileNum = dataMapper.countFileNumByUserId(userId);
		// 已添加App使用次数
		List<Map<String, String>> appRum = reportMapper.countAppRunNumByUserId(userId);

		map.put("monthData", monthData);
		map.put("weekData", weekData);
		map.put("monthSize", monthSize);

		map.put("monthReport", monthReport);
		map.put("monthApp", monthApp);
		map.put("appRum", appRum);
		map.put("size", size);
		map.put("weekReport", weekReport);
		map.put("weekApp", weekApp);
		map.put("fileNum", fileNum);
		log.info("fileNum" + fileNum);
		log.info("size" + size);

		/*
		 * log.info("monthData"+monthData.size());
		 * log.info("monthSize"+monthSize.size());
		 * log.info("monthReport"+monthReport);
		 * log.info("monthApp"+monthApp.size());
		 * log.info("appRum"+appRum.size()); log.info("size"+size);
		 * log.info("fileNum"+fileNum.size());
		 * log.info("weekData"+weekData.size());
		 * log.info("weekReport"+weekReport.size());
		 * log.info("weekApp"+weekApp.size());
		 */
		return map;
	}

	@Override
	public Integer insertProReport(Report report, Integer dataId) {
		report.setFlag(ReportType.PROJECT);
		report.setCreateDate(new Date());
		reportMapper.insertSelective(report);
		report.setFlag(ReportType.DATA);
		report.setCreateDate(new Date());
		report.setFileId(dataId);
		return reportMapper.insertSelectiveReturnKey(report);
	}

	@Override
	public List<Integer> insertMultipleProReport(Report report, Map<Integer, Integer> appProId, String[] dataIds) {
		List<Integer> failAppId = new ArrayList<>();
		report.setCreateDate(new Date());
		for (Entry<Integer, Integer> entry : appProId.entrySet()) {
			Integer appId = entry.getKey();
			Integer proId = entry.getValue();
			report.setReportId(null);
			report.setProjectId(proId);
			report.setAppId(appId);
			report.setFlag(ReportType.PROJECT);
			int pindex = reportMapper.insertSelective(report);
			// 创建数据报告
			report.setFlag(ReportType.DATA);
			int dindex = 0;
			for (String dataId : dataIds) {
				report.setFileId(Integer.parseInt(dataId));
				dindex += reportMapper.insertSelective(report);
			}
			if (pindex == 0 || dindex == 0) {
				failAppId.add(appId);
			}
		}
		return failAppId;
	}

	@Override
	public boolean insertProReport(Report report, List<DataFile> dataList) {
		report.setReportId(null);
		report.setCreateDate(new Date());
		// 创建项目报告
		report.setFlag(ReportType.PROJECT);
		int success = reportMapper.insertSelective(report);
		// 创建数据报告
		report.setFlag(ReportType.DATA);
		for (DataFile dataFile : dataList) {
			report.setFileId(dataFile.getFileId());
			success += reportMapper.insertSelective(report);
		}
		return success == dataList.size() + 1;
	}

	@Override
	public String hbvCompare(Integer appId, String path) {
		List<String> list = FileTools.fileSearch(path, String.valueOf(appId), "startWith");
		StringBuffer sb = new StringBuffer();
		StringBuffer type = new StringBuffer();
		String[] sl = new String[list.size()];
		list.toArray(sl);
		Arrays.sort(sl);
		for (int i = 0; i < sl.length; i++) {
			for (int j = sl.length - 1; j > i; j--) {
				if ((sl[i].length() > sl[j].length()) && (sl[i].length() > 4) && (sl[j].length() > 4)) {
					String temp = sl[i];
					sl[i] = sl[j];
					sl[j] = temp;
				}
			}
		}
		// 数组排序完成后再写回到列表中
		ListIterator<String> l = list.listIterator();
		for (int j = 0; j < sl.length; j++) {
			l.next();
			l.set(sl[j]);
		}
		int[] hbvType = new int[10];
		for (int i = 0; i < list.size(); i++) {
			String name = list.get(i);
			String column = name.substring(name.indexOf("_") + 1, name.length());
			String val = FileTools.getFirstLine(path + name);
			int val_i = Integer.valueOf(val);
			if (name.equals("82_A")) {
				hbvType[0] = val_i;
			} else if (name.equals("82_B")) {
				hbvType[1] = val_i;
			} else if (name.equals("82_C")) {
				hbvType[2] = val_i;
			} else if (name.equals("82_D")) {
				hbvType[3] = val_i;
			} else if (name.equals("82_E")) {
				hbvType[4] = val_i;
			} else if (name.equals("82_F")) {
				hbvType[5] = val_i;
			} else if (name.equals("82_G")) {
				hbvType[6] = val_i;
			} else if (name.equals("82_H")) {
				hbvType[7] = val_i;
			} else if (name.equals("82_no match")) {
				hbvType[8] = val_i;
			} else if (name.equals("82_")) {
				hbvType[9] = val_i;
			}
			if (name.length() == 4 || name.endsWith("no match")) {
				type.append(column).append(",").append(val).append(";");
			} else if (name.equals("82_")) {
				type.append("none").append(",").append(val).append(";");
			} else if (name.length() > 4 && !name.endsWith("yes") && !name.endsWith("no")) {
				sb.append(column).append(",").append(val).append(";");
			} else if (name.equals("82")) {
				sb.append("none").append(",").append(val).append(";");
			}
		}
		return sb.toString() + "@" + Arrays.toString(hbvType);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String krasCompare(Integer length) {
		Iterable list = reportDao.getEGFROrKRASCompare(KRASCount.class, length);
		Iterator it = list.iterator();
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			JSONObject i = JSONObject.fromObject(it.next());
			Integer count = Integer.parseInt(i.get("count").toString());
			Integer site = Integer.parseInt(JSONObject.fromObject(i.get("_id")).get("site").toString());
			sb.append(site).append("\t").append(count).append("\n");
		}
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String egfrCompare(Integer length) {
		Iterable list = reportDao.getEGFROrKRASCompare(EGFRCount.class, length);
		Iterator it = list.iterator();
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			JSONObject i = JSONObject.fromObject(it.next());
			Integer count = Integer.parseInt(i.get("count").toString());
			Integer site = Integer.parseInt(JSONObject.fromObject(i.get("_id")).get("site").toString());
			sb.append(site).append("\t").append(count).append("\n");
		}
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String tbrifampicinCompare() {
		Iterable list = reportDao.getTBRifampicinCompare(TBRifampicinCount.class);
		Iterator it = list.iterator();
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			JSONObject i = JSONObject.fromObject(it.next());
			Integer count = Integer.parseInt(i.get("count").toString());
			Integer site = Integer.parseInt(JSONObject.fromObject(i.get("_id")).get("site").toString());
			sb.append(site).append("\t").append(count).append("\n");
		}
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String hcvCompare() {
		Iterable list = reportDao.getHCVCompare(HCVCount.class);
		Iterator it = list.iterator();
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			JSONObject i = JSONObject.fromObject(it.next());
			Integer count = Integer.parseInt(i.get("count").toString());
			String subtype = JSONObject.fromObject(i.get("_id")).get("subtype").toString();
			sb.append(subtype + "," + count + ";");
		}
		return sb.toString();

	}

	@Override
	public String pgsCompare(Integer appId, String columns) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("totalReads", "Total_Reads");
		map.put("duplicate", "Duplicate(%)");
		map.put("gcCount", "GC_Count(%)");
		map.put("sd", "*SD");
		// 对比列为null
		if (columns == null) {
			return null;
		}
		String queryColumns = columns.replace("Total_Reads", "totalReads").replace("Duplicate(%)", "duplicate")
				.replace("GC_Count(%)", "gcCount").replace("*SD", "sd");
		String[] queryColumn = queryColumns.split(",");
		// 分割对比列[totalReads,duplicate,gcCount]
		String column[] = columns.split(",");
		// 拼接最终返回的字符串:
		// ;Total_Reads:477319,470293,410200,;Duplicate(%):3.50,0.52,0.14,;GC_Count(%):40.02,36.26,39.90,
		StringBuffer sb = new StringBuffer();
		// 根据appId查询某些列的字段
		List<Pgs> pgs = reportDao.getDataFieldsByAppId(Pgs.class, appId, queryColumn);
		if (pgs != null && pgs.size() > 0) {
			for (int i = 0; i < column.length; i++) {
				// 拼接方法名, 根绝field
				StringBuilder methodName = new StringBuilder();
				methodName.append("get");
				methodName.append(column[i].substring(0, 1).toUpperCase());
				methodName.append(column[i].substring(1));
				// 开始拼接
				sb.append(";" + map.get(column[i]) + ":");
				try {
					// 获取getXxx方法:p.getTotalReads();p.getSd();p.getGcCount();p.getDuplicate();
					Method getMethod = pgs.get(0).getClass().getMethod(methodName.toString(), (Class<?>[]) null);
					// 可暴力访问
					getMethod.setAccessible(true);
					// 遍历每一个Pgs对象
					for (Pgs p : pgs) {
						// 用当前的对象执行getXxx方法获取值
						String value = (String) getMethod.invoke(p, (Object[]) null);
						// 新老数据的字段有可能不一致, 所以判断非空
						if (value != null && !"".equals(value)) {
							value = value.trim();
							// 拼接到sb中
							try {
								Float.parseFloat(value);
								sb.append(value + ",");
							} catch (Exception e) {
								continue;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return sb.toString();
		} else {
			return null;
		}
	}

	@Override
	public Map<String, List<List<Float>>> splitCompare(ObjectId id) {
		Map<String, List<List<Float>>> map = new HashMap<>();
		String[] retrievedFields = { "resultList", "totalReads", "avgQuality" };
		List<Split> slist = reportDao.getAllAppList(Split.class, retrievedFields);
		Split split = reportDao.getDataById(Split.class, id);
		List<List<Float>> totalSourceList = new ArrayList<>();
		List<List<Float>> totalSampleList = new ArrayList<>();
		List<List<Float>> thisSourceList = new ArrayList<>();
		List<List<Float>> thisSampleList = new ArrayList<>();
		if (split.getAvgQuality() != null && split.getTotalReads() != null) {
			List<Float> source = new ArrayList<>();
			source.add(Float.valueOf(split.getAvgQuality().split("\n")[0]));
			source.add(Float.valueOf(split.getTotalReads().split("\n")[0]));
			thisSourceList.add(source);
		}
		if (split.getResultList() != null) {
			for (Map<String, String> result : split.getResultList()) {
				List<Float> sample = new ArrayList<>();
				if (result.get("avgQuality") != null) {
					sample.add(Float.valueOf(result.get("avgQuality")));
				} else {
					sample.add(new Float(0));
				}
				if (result.get("number") != null) {
					sample.add(Float.valueOf(result.get("number")));
				} else {
					sample.add(new Float(0));
				}
				thisSampleList.add(sample);
			}
		}
		for (Split s : slist) {
			if (s.getAvgQuality() != null && s.getTotalReads() != null) {
				List<Float> source = new ArrayList<>();
				source.add(Float.valueOf(s.getAvgQuality().split("\n")[0]));
				source.add(Float.valueOf(s.getTotalReads().split("\n")[0]));
				totalSourceList.add(source);
			}
			if (s.getResultList() != null) {
				for (Map<String, String> result : s.getResultList()) {
					List<Float> sample = new ArrayList<>();
					if (result.get("avgQuality") != null) {
						sample.add(Float.valueOf(result.get("avgQuality")));
					} else {
						sample.add(new Float(0));
					}
					if (result.get("number") != null) {
						sample.add(Float.valueOf(result.get("number")));
					} else {
						sample.add(new Float(0));
					}
					totalSampleList.add(sample);
				}
			}
		}
		map.put("totalSource", totalSourceList);
		map.put("totalSample", totalSampleList);
		map.put("thisSource", thisSourceList);
		map.put("thisSample", thisSampleList);
		return map;
	}

	@Override
	public Map<String, Object> hbvCount(Integer userId) {
		Map<String, Object> result = new HashMap<>();
		// TODO 不应该在结果中去重，应该在查询时候去重
		// 去重规则是，每个datakey只保留最近运行的那一次
		// 1. 查询
		List<HBV> hbvList = reportDao.getAppList(HBV.class, userId);
		if (hbvList == null) {
			result.put("data", hbvList);
			return result;
		}
		// 2.筛选
		Map<String, HBV> map = new HashMap<String, HBV>();
		for (int i = 0; i < hbvList.size(); i++) {
			HBV hbv = hbvList.get(i);
			String dataKey = hbv.getDataKey();
			if (map.containsKey(dataKey)) {
				HBV before = map.get(dataKey);
				if (before.getCreateDate().getTime() < hbv.getCreateDate().getTime()) {
					map.put(dataKey, hbv);
				}
			} else {
				map.put(dataKey, hbv);
			}
		}
		// 3. 排序
		Map<Long, HBV> sort = new HashMap<>();
		Long time[] = new Long[map.size()];
		int count = 0;
		for (Entry<String, HBV> hbv : map.entrySet()) {
			long e = hbv.getValue().getCreateDate().getTime()
					+ Long.parseLong((Math.random() * 1000 + "").split("\\.")[0]);
			time[count] = e;
			count++;
			sort.put(e, hbv.getValue());
		}
		Arrays.sort(time);
		// 4.按照排好的序列倒序取值
		hbvList = new ArrayList<>();
		for (int i = time.length - 1; i > -1; i--) {
			hbvList.add(sort.get(time[i]));
		}
		// TODO 考虑前台下载时 js导出excel
		long l = new Date().getTime();
		String txt = String.valueOf(l + ".txt");
		String fileName = String.valueOf(l + ".xls");
		String path = PropertiesUtil.outputPath + txt;
		String excelpath = PropertiesUtil.outputPath + fileName;
		result.put("fileName", fileName);
		result.put("data", hbvList);
		FileTools.createFile(path);
		FileTools.appendWrite(path,
				"文件名\tI169T\tV173L\tL180M\tA181V/T\tT184A/G/S/I/L/F\tA194T\tS202G/I\tM204V\tN236T\tM250V/L/I\t序列\n");
		StringBuffer line = new StringBuffer();
		for (HBV hbv : hbvList) {
			line.append(hbv.getFileName()).append("\t");
			if (hbv.getSite() == null) {
				line.append("由于分析流程的升级，2015年8月1日之前的分析结果无法提取到该信息，若需要请重新运行。");
				for (int i = 0; i < 10; i++) {
					line.append("\t");
				}
			} else {
				int site[] = { 169, 173, 180, 181, 184, 194, 202, 204, 236, 250 };
				for (int i : site) {
					String w = hbv.getSite().get(i + "_wild");
					String m = hbv.getSite().get(i + "_mutation");
					if (m.contains("未检测到")) {
						line.append(w.substring(0, 1));
					} else {
						line.append(m.substring(0, 1));
					}
					line.append("\t");
				}
			}
			line.append(hbv.getSeq()).append("\n");
		}
		FileTools.appendWrite(path, line.toString());
		ExcelUtil.simpleTxtToExcel(path, excelpath, "count");
		return result;
	}

	@Override
	public Map<String, Object> pgsCount(Integer userId) {
		List<Pgs> pgsList = reportDao.getAppList(Pgs.class, userId);
		// TODO 将mongodb中查询出来的日期回退8小时
		// 要彻底解决此问题，需要
		// 1.python插入时时间做处理
		// 2.历史数据时间全部做处理
		if (pgsList != null && pgsList.size() > 0) {
			Calendar c = Calendar.getInstance();
			for (Pgs p : pgsList) {
				c.setTime(p.getUploadDate());
				c.add(Calendar.HOUR_OF_DAY, -8);
				p.setUploadDate(c.getTime());
			}
		}
		Map<String, Object> result = new HashMap<>();
		// TODO 考虑前台下载时 js导出excel
		long l = new Date().getTime();
		String txt = String.valueOf(l + ".txt");
		String fileName = String.valueOf(l + ".xls");
		String path = PropertiesUtil.outputPath + txt;
		String excelpath = PropertiesUtil.outputPath + fileName;
		result.put("fileName", fileName);
		result.put("data", pgsList);
		FileTools.createFile(path);
		FileTools.appendWrite(path,
				"数据编号\tBarcode\t数据别名\t上传日期\tAPP\tTotal_Reads\tMap_Reads\tMap_Ratio(%)\tDuplicate\tGC_Count(%)\t*SD\n");
		if (pgsList != null && pgsList.size() > 0) {
			StringBuffer line = new StringBuffer();
			for (Pgs pgs : pgsList) {
				line.append(pgs.getDataKey()).append("\t").append(CustomStringUtils.getBarcode(pgs.getFileName()))
						.append("\t").append(pgs.getAnotherName()).append("\t")
						.append(DateUtil.getDateToString(pgs.getUploadDate(), "yyyy-MM-dd")).append("\t")
						.append(pgs.getAppName()).append("\t").append(pgs.getTotalReads()).append("\t")
						.append(pgs.getMapReads()).append("\t").append(pgs.getMapRatio()).append("\t")
						.append(pgs.getDuplicate()).append("\t").append(pgs.getGcCount()).append("\t")
						.append(pgs.getSd()).append("\n");
			}
			FileTools.appendWrite(path, line.toString());
		}
		ExcelUtil.simpleTxtToExcel(path, excelpath, "count");
		return result;
	}

	@Override
	public Map<String, Object> cmpCount(Integer userId) {
		Map<String, Object> result = new HashMap<>();
		List<CmpReport> cmpList = reportDao.getAppList(CmpReport.class, userId);
		Map<String, CmpReport> map = new HashMap<>();
		for (int i = 0; i < cmpList.size(); i++) {
			CmpReport cmp = cmpList.get(i);
			String dataKey = cmp.getDataKey();
			if (map.containsKey(dataKey)) {
				CmpReport before = map.get(dataKey);
				if (before.getCreateDate().getTime() < cmp.getCreateDate().getTime()) {
					map.put(dataKey, cmp);
				}
			} else {
				map.put(dataKey, cmp);
			}
		}
		Map<Long, CmpReport> sort = new HashMap<>();
		Long time[] = new Long[map.size()];
		int count = 0;
		for (Entry<String, CmpReport> cmp : map.entrySet()) {
			long e = cmp.getValue().getCreateDate().getTime()
					+ Long.parseLong((Math.random() * 1000 + "").split("\\.")[0]);
			time[count] = e;
			count++;
			sort.put(e, cmp.getValue());
		}
		Arrays.sort(time);
		cmpList = new ArrayList<>();
		for (int i = time.length - 1; i > -1; i--) {
			cmpList.add(sort.get(time[i]));
		}
		// TODO 考虑前台下载时 js导出excel
		long l = new Date().getTime();
		String txt = String.valueOf(l + ".txt");
		String fileName = String.valueOf(l + ".xls");
		String path = PropertiesUtil.outputPath + txt;
		String excelpath = PropertiesUtil.outputPath + fileName;
		result.put("fileName", fileName);
		result.put("data", cmpList);
		FileTools.createFile(path);
		FileTools.appendWrite(path, "数据编号\t原始文件名1\t原始文件名2\t应用名称\t共获得有效片段\t可用片段\t平均测序深度\t基因检测结果\n");
		StringBuffer line = new StringBuffer();
		for (CmpReport cmp : cmpList) {
			line.append(cmp.getDataKey());
			List<DataFile> dataList = cmp.getData();
			if (dataList == null) {
				continue;
			}
			for (DataFile d : dataList) {
				line.append(d.getFileName()).append("(").append(d.getDataKey()).append(")").append("\t");
			}
			line.append(cmp.getAppName() == null ? "" : cmp.getAppName()).append("\t");
			line.append(cmp.getAllFragment() == null ? "" : cmp.getAllFragment().replaceAll("\n", "")).append("\t");
			line.append(cmp.getUsableFragment() == null ? "" : cmp.getUsableFragment().replaceAll("\n", ""))
					.append("\t");
			line.append(cmp.getAvgCoverage() == null ? "" : cmp.getAvgCoverage().replaceAll("\n", "")).append("\t");
			if (cmp.getCmpGeneResult() != null) {
				for (GeneDetectionResult gene : cmp.getCmpGeneResult()) {
					if (gene.getKnownMSNum() != null && Integer.valueOf(gene.getKnownMSNum()) > 0) {
						line.append(gene.getGeneName()).append(":").append(gene.getKnownMSNum()).append(";");
					}
				}
			}
			line.append("\n");
		}
		FileTools.appendWrite(path, line.toString());
		ExcelUtil.simpleTxtToExcel(path, excelpath, "count");
		return result;
	}

	@Override
	public MIB getMIBReport(String dataKey, Integer projectId, Integer appId) {
		return reportDao.getDataReport(MIB.class, dataKey, projectId, appId);
	}

	@Override
	public Integer updateMIBFilling(MIB mib) {
		UpdateResults ur = reportDao.editData(MIB.class, mib.getId(), "baseInfo", mib.getBaseInfo());
		return ur != null ? 1 : 0;
	}

	@Override
	public Integer updateBSIFilling(BSI bsi) {
		UpdateResults ur = reportDao.editData(BSI.class, bsi.getId(), "baseInfo", bsi.getBaseInfo());
		return ur != null ? 1 : 0;
	}

	@Override
	public Integer updateRockyFilling(Rocky rocky) {
		UpdateResults ur = reportDao.editData(Rocky.class, rocky.getId(), "baseInfo", rocky.getBaseInfo());
		return ur != null ? 1 : 0;
	}

	@Override
	public Split getSplitReport(String dataKey, Integer projectId, Integer appId) {
		return reportDao.getDataReport(Split.class, dataKey, projectId, appId);
	}

	@Override
	public CmpReport getCMPReport(String dataKey, Integer projectId, Integer appId) {
		return reportDao.getDataReport(CmpReport.class, dataKey, projectId, appId);
	}

    @Override
    public AccuSeqα2 getAccuSeqα2Report(String dataKey, Integer projectId,
            Integer appId) {
        return reportDao.getDataReport(AccuSeqα2.class, dataKey, projectId,
                appId);
    }

	@Override
	public void updateCmpFilling(ObjectId id, CmpFilling cmpFill) {
		reportDao.editData(CmpReport.class, id, "cmpFilling", cmpFill);
	}

    @Override
    public void updateAccuSeqα2Fill(ObjectId id, AccuSeqα2Fill accuSeqFill) {
        reportDao.editData(AccuSeqα2.class, id, "accuSeqFill", accuSeqFill);
    }

	@Override
	public List<GddDiseaseDict> getGddDiseaseDictNormal(String[] fields, Map<String, List<String>> conditionMap,
            String sortField) {
		return reportDao.getDataFieldInAndOrder(GddDiseaseDict.class, fields, conditionMap, sortField);
	}

    @Override
    public List<AccuGddDiseaseDict> getAccuGddDiseaseDictNormal(String[] fields, Map<String, List<String>> conditionMap,
            String sortField) {
        return reportDao.getDataFieldInAndOrder(AccuGddDiseaseDict.class, fields, conditionMap, sortField);
    }
	@Override
	public List<CmpGeneSnpResult> getGddResult(String dataKey, Integer projectId, Integer appId) {
		List<CmpGeneSnpResult> resultList = new ArrayList<>();
		String[] retrievedFields = { "geneDetectionDetail" };
		CmpReport cr = reportDao.getDataFileds(CmpReport.class, dataKey, projectId, appId, retrievedFields);
		Map<String, CmpGeneDetectionDetail> map_gene = cr.getGeneDetectionDetail();
		if (map_gene != null) {
			CmpGeneDetectionDetail gdd = map_gene.get("all");
			if (gdd != null) {
				List<CmpGeneSnpResult> list = gdd.getResult();
				List<CmpGeneSnpResult> list_tmp = new ArrayList<CmpGeneSnpResult>();
				// 只允许字母和数字
				String regEx = "[^\\w\\.\\_\\-\u4e00-\u9fa5]";
				Pattern p = Pattern.compile(regEx);
				for (CmpGeneSnpResult gsr : list) {
					CmpGeneSnpResult gsr_tmp = new CmpGeneSnpResult();
					gsr_tmp.setDiseaseName(gsr.getDiseaseName());
					gsr_tmp.setDiseaseEngName(p.matcher(gsr.getDiseaseEngName()).replaceAll("").trim());
					gsr_tmp.setGene(gsr.getGene());
					list_tmp.add(gsr_tmp);
				}
				for (int i = 0; i < list_tmp.size(); i++) {
					int num = 1;
					for (int j = list_tmp.size() - 1; j > i; j--) {
						if (list_tmp.get(j).getDiseaseEngName().equals(list_tmp.get(i).getDiseaseEngName())
								&& list_tmp.get(j).getGene().equals(list_tmp.get(i).getGene())) {
							list_tmp.remove(j);
							num++;
						}
					}
					CmpGeneSnpResult gsr_tmp = new CmpGeneSnpResult();
					gsr_tmp.setDiseaseName(list_tmp.get(i).getDiseaseName());
					gsr_tmp.setDiseaseEngName(list_tmp.get(i).getDiseaseEngName());
					gsr_tmp.setGene(list_tmp.get(i).getGene());
					gsr_tmp.setMutNum(num);
					resultList.add(gsr_tmp);
				}
				// 将结果根据疾病类型排序
				Collections.sort(resultList, new Comparator<CmpGeneSnpResult>() {
					@Override
					public int compare(CmpGeneSnpResult gsr1, CmpGeneSnpResult gsr2) {
						return gsr1.getDiseaseName().compareTo(gsr2.getDiseaseName());
					}
				});
			}
		}
		return resultList;
	}

	@Override
	public Integer reportCompeleteByProId(Integer projectId, String dataKey, String context) {
		Date endDate = new Date();
		// 修改任务状态
		Task task = taskMapper.findTaskByProData(projectId, dataKey);
		task.setEndDate(endDate);
		task.setUpdateDate(endDate);
		task.setPeriod(TaskPeriod.DONE);
		taskMapper.updateByPrimaryKeySelective(task);
		Report report = new Report();
		report.setProjectId(projectId);
		report.setFlag(ReportType.DATA);
		report.setPeriod(ReportPeriod.COMPLETE);
		report.setState(DataState.ACTIVE);
		report.setEndDate(endDate);
		DataFile data = dataMapper.selectByDataKey(dataKey);
		report.setFileId(data.getFileId());
		reportMapper.updateReportPeriod(report);
		report.setFlag(ReportType.PROJECT);
		report.setContext(context);
		report.setFileId(null);
		int result = reportMapper.updateReportPeriod(report);
		return result;
	}

	@Override
	public Report getReport(Integer userId, Integer appId, Integer projectId, Integer fileId, Integer flag) {
		return reportMapper.getReport(userId, appId, projectId, fileId, flag);
	}

	@Override
	public int updateReport(Report report) {
		return reportMapper.updateReport(report);
	}

	@Override
	public void saveTask(TaskQueue tq) {
		reportDao.saveData(tq);
	}

	@Override
	public TaskQueue getTaskQueue(Integer projectId) {
		return reportDao.getDataReport(TaskQueue.class, "", projectId, 0);
	}

	@Override
	public BSI getBSIReport(String dataKey, Integer projectId, Integer appId) {
		return reportDao.getDataReport(BSI.class, dataKey, projectId, appId);
	}

	@Override
	public ABINJ getABINJReport(String dataKey, Integer projectId, Integer appId) {
		return reportDao.getDataReport(ABINJ.class, dataKey, projectId, appId);
	}

	@Override
	public S16 get16SReport(String dataKey, Integer projectId, Integer appId) {
		return reportDao.getDataReport(S16.class, dataKey, projectId, appId);
	}

	@Override
	public Report getReportByProjectId(Integer projectId) {
		return reportMapper.getReportByProjectId(projectId, ReportType.PROJECT);
	}

	@Override
	public Integer updateSplitReport(Split split) {
		UpdateResults ur = reportDao.editData(Split.class, split.getId(), "baseInfo", split.getBaseInfo());
		return ur != null ? 1 : 0;
	}

	@Override
	public void deleteBSIReport(String dataKey, Integer projectId, Integer appId) {
		reportDao.delete(BSI.class, dataKey, projectId, appId);
	}

	@Override
	public Rocky getRockyReport(String dataKey, Integer projectId, Integer appId) {
		Rocky rocky = reportDao.getDataReport(Rocky.class, dataKey, projectId, appId);
        if (rocky == null) {
            return rocky;
        }
		Map<String, String> baseInfo = rocky.getBaseInfo();
		if (baseInfo == null) {
			baseInfo = new HashMap<>();
		}
		Date date = rocky.getCreateDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, -8);
		String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		baseInfo.put("createTime", dateStr);
		rocky.setBaseInfo(baseInfo);
		return rocky;
	}

	@Override
	public String getDataKey(Integer fileId) {
		return reportMapper.getDataKey(fileId);
	}

	@Override
	public List<Report> getAllPgsReport() {
		return reportMapper.getAllPgsReport();
	}

	@Override
	public Integer updatePgsFilling(Pgs pgs) {
		UpdateResults ur = null;
		if (pgs.getBaseInfo() == null) {
			ur = reportDao.editData(Pgs.class, pgs.getId(), "baseInfo", new HashMap<String, String>());
		} else {
			ur = reportDao.editData(Pgs.class, pgs.getId(), "baseInfo", pgs.getBaseInfo());
		}
		return ur != null ? 1 : 0;
	}

	@Override
	public List<Report> getAllHcvReport() {
		return reportMapper.getAllHcvReport();
	}

	@Override
	public Integer updateHcvFilling(HCV hcv) {
		UpdateResults ur = null;
		if (hcv.getBaseInfo() == null) {
			ur = reportDao.editData(HCV.class, hcv.getId(), "baseInfo", new HashMap<String, String>());
		} else {
			ur = reportDao.editData(HCV.class, hcv.getId(), "baseInfo", hcv.getBaseInfo());
		}
		return ur != null ? 1 : 0;
	}

	@Override
	public List<Report> getAllEgfrReport() {
		return reportMapper.getAllEgfrReport();
	}

	@Override
	public Integer updateEgfrFilling(EGFR egfr) {
		UpdateResults ur = null;
		if (egfr.getBaseInfo() == null) {
			ur = reportDao.editData(EGFR.class, egfr.getId(), "baseInfo", new HashMap<String, String>());
		} else {
			ur = reportDao.editData(EGFR.class, egfr.getId(), "baseInfo", egfr.getBaseInfo());
		}
		return ur != null ? 1 : 0;
	}

	@Override
	public List<Report> getAllKrasReport() {
		return reportMapper.getAllKrasReport();
	}

	@Override
	public Integer updateKrasFilling(KRAS kras) {
		UpdateResults ur = null;
		if (kras.getBaseInfo() == null) {
			ur = reportDao.editData(KRAS.class, kras.getId(), "baseInfo", new HashMap<String, String>());
		} else {
			ur = reportDao.editData(KRAS.class, kras.getId(), "baseInfo", kras.getBaseInfo());
		}
		return ur != null ? 1 : 0;
	}

	@Override
	public List<Report> getAllTBRifampicinReport() {
		return reportMapper.getAllTBRifampicinReport();
	}

	@Override
	public Integer updateTBRifampicinFilling(TBRifampicin tbRifampicin) {
		UpdateResults ur = null;
		if (tbRifampicin.getBaseInfo() == null) {
			ur = reportDao.editData(TBRifampicin.class, tbRifampicin.getId(), "baseInfo",
					new HashMap<String, String>());
		} else {
			ur = reportDao.editData(TBRifampicin.class, tbRifampicin.getId(), "baseInfo", tbRifampicin.getBaseInfo());
		}
		return ur != null ? 1 : 0;
	}

	@Override
	public List<Report> getAllHbvBriefReport() {
		return reportMapper.getAllHbvBriefReport();
	}

	@Override
	public Integer updateHbvBriefFilling(HBV hbv) {
		UpdateResults ur = null;
		if (hbv.getBriefBaseInfo() == null) {
			ur = reportDao.editData(HBV.class, hbv.getId(), "briefBaseInfo", new HashMap<String, String>());
		} else {
			ur = reportDao.editData(HBV.class, hbv.getId(), "briefBaseInfo", hbv.getBriefBaseInfo());
		}
		return ur != null ? 1 : 0;
	}

	@Override
	public Integer updateFSocgFilling(FSocg fsocg) {
		UpdateResults ur = null;
		if (fsocg.getBaseInfo() == null) {
			ur = reportDao.editData(FSocg.class, fsocg.getId(), "baseInfo", new HashMap<String, String>());
		} else {
			ur = reportDao.editData(FSocg.class, fsocg.getId(), "baseInfo", fsocg.getBaseInfo());
		}
		return ur != null ? 1 : 0;
	}

	@Override
	public List<Report> getAllHbvDetailReport() {
		return reportMapper.getAllHbvDetailReport();
	}

	@Override
	public Integer updateHbvDetailFilling(HBV hbv) {
		UpdateResults ur = null;
		if (hbv.getDetailBaseInfo() == null) {
			ur = reportDao.editData(HBV.class, hbv.getId(), "detailBaseInfo", new HashMap<String, String>());
		} else {
			ur = reportDao.editData(HBV.class, hbv.getId(), "detailBaseInfo", hbv.getDetailBaseInfo());
		}
		return ur != null ? 1 : 0;
	}

    @Override
    public Pgs getPgsProjectInfo(Integer projectId) {
        return reportDao.getProjectByProjectId(Pgs.class, projectId);
    }

    @Override
    public <T> Key<T> save(T t) {
        return reportDao.saveObj(t);
    }

    @Override
    public Integer updatePgsProjectilling(Pgs pgs) {
        UpdateResults ur = null;
        if (pgs.getProjectInfo() == null) {
            ur = reportDao.editData(Pgs.class, pgs.getId(), "projectInfo", new HashMap<String, String>());
        } else {
            ur = reportDao.editData(Pgs.class, pgs.getId(), "projectInfo", pgs.getProjectInfo());
        }
        return ur != null ? 1 : 0;
    }

    @Override
    public Integer getProjectPeriod(Integer projectId) {
        return reportMapper.selectPeriodByFlag(projectId, 1);
    }

    @Override
    public List<Map.Entry<String, Map<String, String>>> getHBVOtherSiteByUserId(Integer userId, Integer appId) {

        // 结果Map
        // {"206":{"count":"20","percent":"10%"},"207":{"count":"30","percent":"30%}}
        Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
        // 封装过滤条件
        Map<String, Object> filters = new HashMap<String, Object>();
        filters.put("userId", userId);
        // 该用户下所有的hbv报告
        List<HBV> hbvs = reportDao.queryByFilters(HBV.class, filters, new String[] { "other", "fileId", "dataKey" });

        // 保存datakey, 用于去除重复运行
        Map<String, Object> dataKeyMap = new HashMap<String, Object>();
        // 保存md5, 用于去除重复文件
        Map<String, Object> md5Map = new HashMap<String, Object>();
        // hbv对应的文件map集合{"21":{"md5":"xfdsghfsgh"},"22":{"md5":"dgdfsgsdgf"},...}
        Map<Integer, Map<String, String>> md5FileIdMap = dataMapper.getMd5FileIdMap(userId, appId);
        // 遍历hbv数据报告, 去除重复运行, 去除重复文件, 将有用信息封装map
        Float totalSite = 0.0f; // 总的位点数量用于统计半分比
        NumberFormat nt = NumberFormat.getPercentInstance(); // 数字格式化
        nt.setMinimumFractionDigits(2);
        for (HBV hbv : hbvs) {
            if (hbv.getOther() != null && hbv.getOther().size() > 0) { // 其他位点存指才去统计
                if (dataKeyMap.containsKey(hbv.getDataKey())) { // map中已经存在该datakey代表已经存在该文件(去除重复运行)
                    continue;
                } else {
                    // 判断是否包含在md5Map中(去除重复文件)
                    if (md5FileIdMap.containsKey(hbv.getFileId())) { // 代表该文件属于md5有重复的文件
                        // 判断是否已经添加过了
                        if (md5Map.get(md5FileIdMap.get(hbv.getFileId()).get("md5")) != null) { // 代表保存md5的map中已经存在该MD5(去除重复文件)
                            continue;
                        } else {
                            md5Map.put(md5FileIdMap.get(hbv.getFileId()).get("md5"), new Object());
                        }
                    }
                    dataKeyMap.put(hbv.getDataKey(), new Object()); // 代表第一次读取到该datakey作为键存入map中
                    // 该hbv数据既不属于重复运行, 也不属于重复文件, 读取该数据的新的突变位点, 封装map
                    // 其他突变位点统计
                    Map<String, String> map = hbv.getOther();
                    for (String site : map.keySet()) {
                        totalSite++;
                        site = site.split("_")[0]; // 获取位点值
                        if(result.containsKey(site)){ // 结果map中已经统计过该位点, 则取出该位点的Map
                            Map<String,String> siteMap = result.get(site);
                            siteMap.put("count", String.valueOf(Integer.parseInt(siteMap.get("count")) + 1));// 数量+1
                            siteMap.put("percent", Integer.parseInt(siteMap.get("count")) / totalSite + ""); // 重新计算百分比
                        } else {
                            // 构造一个结果map放到result中
                            Map<String, String> siteMap = new HashMap<String, String>();
                            siteMap.put("count", "1");
                            siteMap.put("percent", 1 / totalSite + "");
                            result.put(site, siteMap);
                        }
                    }
                    for (Map.Entry<String, Map<String, String>> entry : result.entrySet()) {
                        entry.getValue().put("percent",
                                nt.format(Float.parseFloat(entry.getValue().get("count")) / totalSite));
                    }
                }
            }
        }
        List<Map.Entry<String, Map<String, String>>> resultList = new ArrayList<Map.Entry<String, Map<String, String>>>(
                result.entrySet());
        Collections.sort(resultList, new Comparator<Map.Entry<String, Map<String, String>>>() {
            @Override
            public int compare(Entry<String, Map<String, String>> o1, Entry<String, Map<String, String>> o2) {
                return Integer.parseInt(o2.getValue().get("count")) - Integer.parseInt(o1.getValue().get("count"));
            }

        });
        return resultList;
    }

	@Override
	public Report getLastDataReport(String dataKey, Integer userId, Integer appId) {
		return reportMapper.getLastDataReport(dataKey, DataState.ACTIVE, ReportType.DATA, userId, appId);
	}

}
