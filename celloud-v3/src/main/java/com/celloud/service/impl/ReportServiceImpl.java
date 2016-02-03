package com.celloud.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.celloud.constants.DataState;
import com.celloud.constants.ReportPeriod;
import com.celloud.constants.ReportType;
import com.celloud.constants.TimeState;
import com.celloud.dao.ReportDao;
import com.celloud.mapper.AppMapper;
import com.celloud.mapper.DataFileMapper;
import com.celloud.mapper.ReportMapper;
import com.celloud.model.CmpFilling;
import com.celloud.model.CmpGeneDetectionDetail;
import com.celloud.model.CmpGeneSnpResult;
import com.celloud.model.CmpReport;
import com.celloud.model.DataFile;
import com.celloud.model.GddDiseaseDict;
import com.celloud.model.GeneDetectionResult;
import com.celloud.model.HBV;
import com.celloud.model.MIB;
import com.celloud.model.Oncogene;
import com.celloud.model.Pgs;
import com.celloud.model.Report;
import com.celloud.model.Split;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.ReportService;
import com.celloud.utils.Base64Util;
import com.celloud.utils.ExcelUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.PropertiesUtil;

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

    Logger log = Logger.getLogger(this.getClass());

    @Override
    public Integer countReport(Integer userId) {
        return reportMapper.countReport(userId, DataState.ACTIVE,
                ReportType.DATA,ReportPeriod.COMPLETE);
    }

    @Override
    public List<Map<String, String>> countReport(Integer userId, String time) {
        return reportMapper.countReportByTime(userId, time, DataState.ACTIVE,
                ReportType.DATA, ReportPeriod.COMPLETE);
    }

    @Override
    public PageList<Map<String, Object>> getReportPageList(Integer userId,
            Page pager, String condition, String start, String end,
            Integer appId) {
        List<Map<String, Object>> list = reportMapper.getReportList(userId,
                pager, condition, start, end, appId, ReportType.PROJECT);
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
        // jstl 处理 \n 很困难，就在 java 端处理
        hbv.setReporttxt(hbv.getReporttxt().replace("\n", "<br/>"));
        return hbv;
    }

    @Override
    public Pgs getPgsReport(String dataKey, Integer projectId, Integer appId) {
        return reportDao.getDataReport(Pgs.class, dataKey, projectId, appId);
    }

    @Override
    public Oncogene getOncogeneReport(String dataKey, Integer projectId,
            Integer appId) {
        return reportDao.getDataReport(Oncogene.class, dataKey, projectId,
                appId);
    }

    @Override
    public Map<String, Object> systemCount(Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        /*** 按月统计的每月上传的:数据量\数据大小\报告数量\APP运行次数 ***/
        List<Map<String, String>> monthData = dataMapper.countDataByTime(userId,
                TimeState.MONTH, DataState.ACTIVE);
        List<Map<String, String>> monthSize = dataMapper.sumDataByTime(userId,
                TimeState.MONTH, DataState.ACTIVE);

        List<Map<String, String>> monthReport = reportMapper
                .countReportMonthByUserId(userId);

        List<Map<String, String>> monthApp = appMapper.countMyAppByTime(userId,
                TimeState.MONTH, DataState.ACTIVE, DataState.ACTIVE);
        /**** 按周统计每周上传:数据量\数据大小\报告数量\APP数量 *****/
        List<Map<String, String>> weekData = dataMapper
                .countDataFileWeek(userId);
        List<Map<String, String>> weekReport = reportMapper
                .countReportWeekByUserId(userId);
        List<Map<String, String>> weekApp = appMapper.countWeekByUserId(userId);

        // 用户上传数据
        Long size = dataMapper.sumData(userId, DataState.ACTIVE);
        // 已运行、未运行
        Map<String, String> fileNum = dataMapper.countFileNumByUserId(userId);
        // 已添加App使用次数
        List<Map<String, String>> appRum = reportMapper
                .countAppRunNumByUserId(userId);

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
    public Integer insertProReport(Report report) {
        report.setFlag(ReportType.PROJECT);
        report.setCreateDate(new Date());
        return reportMapper.insertSelectiveReturnKey(report);
    }

    @Override
    public List<Integer> insertMultipleProReport(Report report,
            Map<Integer, Integer> appProId, String[] dataIds) {
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
    public Integer insertDataReport(Report report, String[] dataIds) {
        int index = 0;
        report.setFlag(ReportType.DATA);
        report.setCreateDate(new Date());
        for (String dataId : dataIds) {
            report.setFileId(Integer.parseInt(dataId));
            index += reportMapper.insertSelective(report);
        }
        return index;
    }

    @Override
    public String hbvCompare(Integer appId, String path) {
        List<String> list = FileTools.fileSearch(path, String.valueOf(appId),
                "startWith");
        StringBuffer sb = new StringBuffer();
        StringBuffer type = new StringBuffer();
        String[] sl = new String[list.size()];
        list.toArray(sl);
        Arrays.sort(sl);
        for (int i = 0; i < sl.length; i++) {
            for (int j = sl.length - 1; j > i; j--) {
                if ((sl[i].length() > sl[j].length()) && (sl[i].length() > 4)
                        && (sl[j].length() > 4)) {
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
            String column = name.substring(name.indexOf("_") + 1,
                    name.length());
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
            } else if (name.length() > 4 && !name.endsWith("yes")
                    && !name.endsWith("no")) {
                sb.append(column).append(",").append(val).append(";");
            } else if (name.equals("82")) {
                sb.append("none").append(",").append(val).append(";");
            }
        }
        return sb.toString() + "@" + Arrays.toString(hbvType);
    }

    @Override
    public String egfrCompare(Integer appId, String path, String length) {
        path = path + appId + "_" + length;
        if (FileTools.checkPath(path)) {
            return FileTools.getLimitLines(path, 1, 10);
        }
        return null;
    }

    @Override
    public String hcvCompare(Integer appId, String path) {
        List<String> list = FileTools.fileSearch(path, String.valueOf(appId),
                "startWith");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i);
            String val = FileTools.getFirstLine(path + name);
            sb.append(name.substring(name.lastIndexOf("_") + 1) + "," + val
                    + ";");
        }
        return sb.toString();
    }

    @Override
    public String pgsCompare(Integer appId, String path, String columns) {
        if (columns == null) {
            return null;
        }
        String column[] = columns.split(",");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < column.length; i++) {
            String fileName = path + appId + "_" + column[i];
            File file = new File(fileName);
            if (file.exists()) {
                sb.append(";" + column[i] + ":");
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                String line = null;
                try {
                    while ((line = br.readLine()) != null) {
                        sb.append(line + ",");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (br != null) {
                            br.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sb.toString();
    }

    @Override
    public Map<String, List<List<Float>>> splitCompare(ObjectId id) {
        Map<String, List<List<Float>>> map = new HashMap<>();
        String[] retrievedFields = { "resultList", "totalReads", "avgQuality" };
        List<Split> slist = reportDao.getAllAppList(Split.class,
                retrievedFields);
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
        // 2.筛选
        Map<String, HBV> map = new HashMap<String, HBV>();
        for (int i = 0; i < hbvList.size(); i++) {
            HBV hbv = hbvList.get(i);
            String dataKey = hbv.getDataKey();
            if (map.containsKey(dataKey)) {
                HBV before = map.get(dataKey);
                if (before.getCreateDate().getTime() < hbv.getCreateDate()
                        .getTime()) {
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
            long e = hbv.getValue().getCreateDate().getTime() + Long
                    .parseLong((Math.random() * 1000 + "").split("\\.")[0]);
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
        for (HBV hbv : hbvList) {
            StringBuffer line = new StringBuffer(hbv.getFileName())
                    .append("\t");
            if (hbv.getSite() == null) {
                line.append("由于分析流程的升级，八月一日之前的分析结果无法提取到该信息，若需要请重新运行。");
                for (int i = 0; i < 10; i++) {
                    line.append("\t");
                }
            } else {
                int site[] = { 169, 173, 180, 181, 184, 194, 202, 204, 236,
                        250 };
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
            FileTools.appendWrite(path, line.toString());
        }
        ExcelUtil.simpleTxtToExcel(path, excelpath, "count");
        return result;
    }

    @Override
    public List<Pgs> pgsCount(Integer userId) {
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
        return pgsList;
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
                if (before.getCreateDate().getTime() < cmp.getCreateDate()
                        .getTime()) {
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
            long e = cmp.getValue().getCreateDate().getTime() + Long
                    .parseLong((Math.random() * 1000 + "").split("\\.")[0]);
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
        FileTools.appendWrite(path,
                "数据编号\t原始文件名1\t原始文件名2\t共获得有效片段\t可用片段\t平均测序深度\t基因检测结果\n");
        for (CmpReport cmp : cmpList) {
            StringBuffer line = new StringBuffer(cmp.getDataKey()).append("\t");
            List<DataFile> dataList = cmp.getData();
            for (DataFile d : dataList) {
                line.append(d.getFileName()).append("(").append(d.getDataKey())
                        .append(")").append("\t");
            }
            line.append(cmp.getAllFragment() == null ? ""
                    : cmp.getAllFragment().replaceAll("\n", "")).append("\t");
            line.append(cmp.getUsableFragment() == null ? ""
                    : cmp.getUsableFragment().replaceAll("\n", ""))
                    .append("\t");
            line.append(cmp.getAvgCoverage() == null ? ""
                    : cmp.getAvgCoverage().replaceAll("\n", "")).append("\t");
            if (cmp.getCmpGeneResult() != null) {
                for (GeneDetectionResult gene : cmp.getCmpGeneResult()) {
                    line.append(gene.getGeneName()).append(":")
                            .append(gene.getSequencingDepth()).append(";");
                }
            }
            line.append("\n");
            FileTools.appendWrite(path, line.toString());
        }
        ExcelUtil.simpleTxtToExcel(path, excelpath, "count");
        return result;
    }

    @Override
    public MIB getMIBReport(String dataKey, Integer projectId, Integer appId) {
        return reportDao.getDataReport(MIB.class, dataKey, projectId, appId);
    }

    @Override
    public Split getSplitReport(String dataKey, Integer projectId,
            Integer appId) {
        return reportDao.getDataReport(Split.class, dataKey, projectId, appId);
    }

    @Override
    public CmpReport getCMPReport(String dataKey, Integer projectId,
            Integer appId) {
        return reportDao.getDataReport(CmpReport.class, dataKey, projectId,
                appId);
    }

    @Override
    public void updateCmpFilling(ObjectId id, CmpFilling cmpFill) {
        reportDao.editData(CmpReport.class, id, "cmpFilling", cmpFill);
    }

    @Override
    public List<GddDiseaseDict> getGddDiseaseDictNormal(String[] fields,
            Map<String, List<String>> conditionMap, String sortField) {
        return reportDao.getDataFieldInAndOrder(GddDiseaseDict.class, fields,
                conditionMap, sortField);
    }

    @Override
    public List<CmpGeneSnpResult> getGddResult(String dataKey,
            Integer projectId, Integer appId) {
        List<CmpGeneSnpResult> resultList = new ArrayList<>();
        String[] retrievedFields = { "geneDetectionDetail" };
        CmpReport cr = reportDao.getDataFileds(CmpReport.class, dataKey,
                projectId, appId, retrievedFields);
        Map<String, CmpGeneDetectionDetail> map_gene = cr
                .getGeneDetectionDetail();
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
                    gsr_tmp.setDiseaseEngName(p.matcher(gsr.getDiseaseEngName())
                            .replaceAll("").trim());
                    gsr_tmp.setGene(gsr.getGene());
                    list_tmp.add(gsr_tmp);
                }
                for (int i = 0; i < list_tmp.size(); i++) {
                    int num = 1;
                    for (int j = list_tmp.size() - 1; j > i; j--) {
                        if (list_tmp.get(j).getDiseaseEngName()
                                .equals(list_tmp.get(i).getDiseaseEngName())
                                && list_tmp.get(j).getGene()
                                        .equals(list_tmp.get(i).getGene())) {
                            list_tmp.remove(j);
                            num++;
                        }
                    }
                    CmpGeneSnpResult gsr_tmp = new CmpGeneSnpResult();
                    gsr_tmp.setDiseaseName(list_tmp.get(i).getDiseaseName());
                    gsr_tmp.setDiseaseEngName(
                            list_tmp.get(i).getDiseaseEngName());
                    gsr_tmp.setGene(list_tmp.get(i).getGene());
                    gsr_tmp.setMutNum(num);
                    resultList.add(gsr_tmp);
                }
                // 将结果根据疾病类型排序
                Collections.sort(resultList,
                        new Comparator<CmpGeneSnpResult>() {
                            @Override
                            public int compare(CmpGeneSnpResult gsr1,
                                    CmpGeneSnpResult gsr2) {
                                return gsr1.getDiseaseName()
                                        .compareTo(gsr2.getDiseaseName());
                            }
                        });
            }
        }
        return resultList;
    }

    @Override
    public Integer reportCompeleteByProId(Integer projectId, String context) {
        Report report = new Report();
        report.setProjectId(projectId);
        report.setFlag(ReportType.DATA);
        report.setPeriod(ReportPeriod.COMPLETE);
        report.setState(DataState.ACTIVE);
        report.setEndDate(new Date());
        reportMapper.updateReportPeriod(report);
        report.setFlag(ReportType.PROJECT);
        report.setContext(context);
        return reportMapper.updateReportPeriod(report);
    }

    @Override
    public Report getReport(Integer userId, Integer appId, Integer projectId,
            Integer fileId, Integer flag) {
        return reportMapper.getReport(userId, appId, projectId, fileId, flag);
    }

    @Override
    public int updateReport(Report report) {
        return reportMapper.updateReport(report);
    }

    @Override
    public Integer updateReportStateToTools(Integer userId, Integer appId,
            Integer projectId, Integer period, String context) {
        if (context != null) {
            context = context.replaceAll(" ", "+");
            context = Base64Util.decrypt(context);
        }
        Report report = new Report();
        report.setUserId(userId);
        report.setAppId(appId);
        report.setProjectId(projectId);
        report.setPeriod(period);
        List<DataFile> list = dataMapper.getDatasInProject(projectId);
        for (DataFile data : list) {
            report.setFileId(data.getFileId());
            report.setFlag(ReportType.DATA);
            report.setEndDate(new Date());
            reportMapper.updateReport(report);
        }
        report.setFlag(ReportType.PROJECT);
        report.setContext(context);
        report.setEndDate(new Date());
        return reportMapper.updateReport(report);
    }
}
