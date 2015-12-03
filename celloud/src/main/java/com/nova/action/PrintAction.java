package com.nova.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.nova.sdo.Company;
import com.nova.sdo.Dept;
import com.nova.service.ICompanyService;
import com.nova.service.IDataService;
import com.nova.service.IDeptService;
import com.nova.service.IReportService;
import com.nova.service.ISoftwareService;
import com.nova.utils.PropertiesUtil;

@ParentPackage("celloud-default")
@Action("print")
@Results({ @Result(name = "pgs", location = "../../pages/print/printPGS.jsp"),
	@Result(name = "nipt", location = "../../pages/print/NIPT.jsp"),
	@Result(name = "VSP", location = "../../pages/print/printVSP.jsp"),
	@Result(name = "CMP", location = "../../pages/print/printCMP.jsp"),
	@Result(name = "hbv", location = "../../pages/print/printHBV.jsp") })
public class PrintAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    @Inject
    private ICompanyService companyService;
    @Inject
    private IDeptService deptService;
    @Inject
    private IDataService dataService;
    @Inject
    private IReportService reportService;
    @Inject
    private ISoftwareService iss;
    private String outPath = PropertiesUtil.toolsOutPath + "upload";
    private String pagePath;
    private String miniPng;
    private String txt;
    private Company company;
    private Dept dept;
    private String context;
    private int userId;
    private int appId;
    private int fileId;
    private String dataKey;
    private int flag;
    private String pageTxt;
    // 打印
    private String snpType;
    private String sensitive;
    private String imgHtml;
    private List<String> imgList;
    private String fileName;
    private Map<String, Object> resultMap;
    private String peakFigure;
    private String allPic;
    private String seq;
    private String result;
    private String table;

    public String printCMP() {
	String[] temp = StringUtils.splitByWholeSeparatorPreserveAllTokens(
		context, ",");
	resultMap = new HashMap<String, Object>();
	resultMap.put("runDate", temp[0]);
	resultMap.put("allFragment", temp[1]);
	resultMap.put("avgQuality", temp[2]);
	resultMap.put("avgGC", temp[3]);
	resultMap.put("useFragment", temp[4]);
	resultMap.put("undetectGene", temp[5]);
	resultMap.put("detectGene", temp[6]);
	resultMap.put("avgCoverage", temp[7]);
	resultMap.put("snp_tbody1", temp[8]);
	resultMap.put("snp_tbody2", temp[9]);
	resultMap.put("fastqc_data", temp[10]);
	resultMap.put("per_base_quality", temp[11]);
	resultMap.put("per_base_seq_content", temp[12]);
	resultMap.put("f2", temp[13]);
	resultMap.put("q2", temp[14]);
	resultMap.put("s2", temp[15]);
	resultMap.put("fileName", temp[16]);
	return "CMP";
    }

    public String printVSP() {
	return "VSP";
    }

    public String printHBV() {
        Integer deptId = (Integer) super.session.get("deptId");
        dept = deptService.getDept(deptId);
        company = companyService.getCompany(dept.getCompanyId());
        fileId = dataService.getDataByKey(dataKey).getFileId();
        pageTxt = reportService.getPrintContext(userId, appId, fileId, 0);
        // 首先检索该报告是否保存过，若保存过，则直接将保存内容返回
        if (!StringUtils.isEmpty(pageTxt)) {
            return "hbv";
        }
        txt = iss.getSoftware(appId).getSoftwareName();
        if (imgHtml != null) {
            String[] imgArr = imgHtml.split(",");
            imgList = new ArrayList<String>();
            for (String s : imgArr) {
                imgList.add(s);
            }
        }
        return "hbv";
    }

    public String printPGS() {
	String info[] = pagePath.split("/");
	userId = Integer.parseInt(info[0]);
	appId = Integer.parseInt(info[1]);
	fileId = dataService.getDataByKey(info[2]).getFileId();
	context = reportService.getPrintContext(userId, appId, fileId, 0);
	// 首先检索该报告是否保存过，若没有，则查询该用户所属的医院
	if (context == null || context.isEmpty()) {
	    dept = deptService.getDept((Integer) super.session.get("deptId"));
	    company = companyService.getCompany(dept.getCompanyId());
	}
	return "pgs";
    }

    public String printNIPT() {
	String info[] = pagePath.split("/");
	userId = Integer.parseInt(info[0]);
	appId = Integer.parseInt(info[1]);
	fileId = dataService.getDataByKey(info[2]).getFileId();
	context = reportService.getPrintContext(userId, appId, fileId, 0);
	return "nipt";
    }

    public String getOutPath() {
	return outPath;
    }

    public void setOutPath(String outPath) {
	this.outPath = outPath;
    }

    public String getPagePath() {
	return pagePath;
    }

    public void setPagePath(String pagePath) {
	this.pagePath = pagePath;
    }

    public String getMiniPng() {
	return miniPng;
    }

    public void setMiniPng(String miniPng) {
	this.miniPng = miniPng;
    }

    public String getTxt() {
	return txt;
    }

    public void setTxt(String txt) {
	this.txt = txt;
    }

    public String getContext() {
	return context;
    }

    public void setContext(String context) {
	this.context = context;
    }

    public int getUserId() {
	return userId;
    }

    public void setUserId(int userId) {
	this.userId = userId;
    }

    public int getAppId() {
	return appId;
    }

    public void setAppId(int appId) {
	this.appId = appId;
    }

    public int getFileId() {
	return fileId;
    }

    public void setFileId(int fileId) {
	this.fileId = fileId;
    }

    public int getFlag() {
	return flag;
    }

    public void setFlag(int flag) {
	this.flag = flag;
    }

    public Company getCompany() {
	return company;
    }

    public void setCompany(Company company) {
	this.company = company;
    }

    public Dept getDept() {
	return dept;
    }

    public void setDept(Dept dept) {
	this.dept = dept;
    }

    public String getSnpType() {
	return snpType;
    }

    public void setSnpType(String snpType) {
	this.snpType = snpType;
    }

    public String getSensitive() {
	return sensitive;
    }

    public void setSensitive(String sensitive) {
	this.sensitive = sensitive;
    }

    public String getImgHtml() {
	return imgHtml;
    }

    public void setImgHtml(String imgHtml) {
	this.imgHtml = imgHtml;
    }

    public List<String> getImgList() {
	return imgList;
    }

    public void setImgList(List<String> imgList) {
	this.imgList = imgList;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public Map<String, Object> getResultMap() {
	return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
	this.resultMap = resultMap;
    }

    public String getDataKey() {
	return dataKey;
    }

    public void setDataKey(String dataKey) {
	this.dataKey = dataKey;
    }

    public String getPageTxt() {
	return pageTxt;
    }

    public void setPageTxt(String pageTxt) {
	this.pageTxt = pageTxt;
    }

    public String getPeakFigure() {
	return peakFigure;
    }

    public void setPeakFigure(String peakFigure) {
	this.peakFigure = peakFigure;
    }

    public String getAllPic() {
	return allPic;
    }

    public void setAllPic(String allPic) {
	this.allPic = allPic;
    }

    public String getSeq() {
	return seq;
    }

    public void setSeq(String seq) {
	this.seq = seq;
    }

    public String getResult() {
	return result;
    }

    public void setResult(String result) {
	this.result = result;
    }

    public String getTable() {
	return table;
    }

    public void setTable(String table) {
	this.table = table;
    }

}
