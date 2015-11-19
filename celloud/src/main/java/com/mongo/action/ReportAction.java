package com.mongo.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.mongo.sdo.HBV;
import com.mongo.sdo.HCV;
import com.mongo.sdo.NIPT;
import com.mongo.sdo.Pgs;
import com.mongo.service.ReportService;
import com.nova.action.BaseAction;
import com.nova.utils.ExcelUtil;
import com.nova.utils.FileTools;
import com.nova.utils.PropertiesUtil;

/**
 * @author lin
 * @date 2015-10-28 上午10:51:44
 * @description：操作 mongodb 工具
 */
@ParentPackage("celloud-default")
@Action("report3")
@Results({
        @Result(name = "niptReport", location = "../../pages/report/niptReport.jsp"),
        @Result(name = "pgsReport", location = "../../pages/report/pgsReport.jsp"),
        @Result(name = "hcvReport", location = "../../pages/report/hcvReport.jsp"),
        @Result(name = "hbvReport", location = "../../pages/report/hbvReport.jsp"),
        @Result(name = "toHBVCount", location = "../../pages/count/hbvCount.jsp"),
        @Result(name = "toPgsCount", location = "../../pages/count/pgsCount.jsp")})
public class ReportAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(ReportAction.class);
    @Inject
    private ReportService reportService;
    private List<HBV> hbvList;
    private List<Pgs> pgsList;
    private String fileName;
    private HBV hbv;
    private HCV hcv;
    private Pgs pgs;
    private NIPT nipt;
    private String dataKey;
    private Integer proId;
    private Integer appId;

    // TODO 配置Tools外网路径，考虑优化
    private String path = PropertiesUtil.toolsOutPath + "upload";
    // TODO 下载路径
    private String down = PropertiesUtil.toolsOutPath
            + "Procedure!miRNADownload?userId=";

    // TODO 写死的路径，需优化
    public void download() {
        if (fileName != null) {
            FileTools.fileDownLoad(ServletActionContext.getResponse(),
                    "/share/data/output/" + fileName);
        }
    }
    
    public String toPgsCount() {
        log.info("查看用户" + pgs.getUsername() + "的数据报告统计");
        pgsList = reportService.getPgsList(pgs.getUserId());
        return "toPgsCount";
    }

    public String toHBVCount() {
        Integer userId = (Integer) super.session.get("userId");
        // TODO 不应该在结果中去重，应该在查询时候去重
        // 去重规则是，每个datakey只保留最近运行的那一次
        // 1. 查询
        hbvList = reportService.getHBVList(userId);
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
        // TODO 写死的路径，考虑前台下载时 js导出excel
        long l = new Date().getTime();
        String txt = String.valueOf(l + ".txt");
        fileName = String.valueOf(l + ".xls");
        String path = "/share/data/output/" + txt;
        String excelpath = "/share/data/output/" + fileName;
        FileTools.createFile(path);
        FileTools
                .appendWrite(
                        path,
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
            FileTools.appendWrite(path, line.toString());
        }
        ExcelUtil.simpleTxtToExcel(path, excelpath, "count");
        return "toHBVCount";
    }

    /**
     * 单查HBV的数据报告
     * 
     * @return
     */
    public String getHBVReport() {
        hbv = reportService.getDataReport(HBV.class, dataKey, proId, appId);
        // 其他突变位点排序
        Map<String, String> map = hbv.getOther();
        System.out.println(map == null);
        if(map!=null){
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
        return "hbvReport";
    }

    /**
     * 获取HCV报告
     * 
     * @return
     */
    public String getHCVReport() {
        hcv = reportService.getDataReport(HCV.class, dataKey, proId, appId);
        return "hcvReport";
    }
    
    /**
     * 获取PGS报告
     * 
     * @return
     */
    public String getPgsReport() {
        pgs = reportService.getDataReport(Pgs.class, dataKey, proId, appId);
        return "pgsReport";
    }
    
    /**
     * 获取NIPT报告
     * 
     * @return
     */
    public String getNIPTReport() {
        nipt = reportService.getDataReport(NIPT.class, dataKey, proId, appId);
        return "niptReport";
    }

    public List<HBV> getHbvList() {
        return hbvList;
    }

    public void setHbvList(List<HBV> hbvList) {
        this.hbvList = hbvList;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public HBV getHbv() {
        return hbv;
    }

    public void setHbv(HBV hbv) {
        this.hbv = hbv;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getPath() {
        return path;
    }

    public String getDown() {
        return down;
    }

    public HCV getHcv() {
        return hcv;
    }

    public void setHcv(HCV hcv) {
        this.hcv = hcv;
    }

    public Pgs getPgs() {
        return pgs;
    }

    public void setPgs(Pgs pgs) {
        this.pgs = pgs;
    }

    public List<Pgs> getPgsList() {
        return pgsList;
    }

    public void setPgsList(List<Pgs> pgsList) {
        this.pgsList = pgsList;
    }

    public NIPT getNipt() {
        return nipt;
    }

    public void setNipt(NIPT nipt) {
        this.nipt = nipt;
    }

}
