package com.nova.tools.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.celloud.mongo.sdo.CmpGeneDetectionDetail;
import com.celloud.mongo.sdo.CmpReport;
import com.celloud.mongo.service.ReportService;
import com.celloud.mongo.service.ReportServiceImpl;
import com.nova.tools.constant.AppNameIDConstant;
import com.nova.tools.itext.utils.MergePdf;
import com.nova.tools.service.ReadReportService;
import com.nova.tools.service.RunAppService;
import com.nova.tools.utils.FileTools;
import com.nova.tools.utils.PropertiesUtils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @Description:流程运行 Action
 * @author lin
 * @date 2013-7-29 下午8:17:48
 */
@ParentPackage("json-default")
@Action("Procedure")
@Results({
		@Result(name = "success", type = "json", params = { "root", "flag" }),
		@Result(name = "resultMap", type = "json", params = { "root",
				"resultMap" }),
		@Result(name = "CMP_199", location = "../../pages/view/ajax/CMP.jsp"),
		@Result(name = "CMP", location = "../../pages/view/ajax/CMP.jsp"),
		@Result(name = "printCMP", location = "../../pages/view/ajax/printDetailCMP.jsp"),
		@Result(name = "percent", location = "../../pages/view/ajax/ProcessPercent.jsp"),
		@Result(name = "step", location = "../../pages/view/ajax/ProcessStep.jsp"),
		@Result(name = "SNPData", location = "../../pages/view/ajax/snpResult.jsp"),
		@Result(name = "HBV_SNP2Data", location = "../../pages/view/ajax/HBV_SNP2Data.jsp"),
		@Result(name = "ExomeSNVReport", location = "../../pages/view/ajax/hsnvReport.jsp"),
		@Result(name = "EST", location = "../../pages/view/ajax/estReport.jsp"),
		@Result(name = "miRNAData", location = "../../pages/view/ajax/microRNAReport.jsp"),
		@Result(name = "HBVTree", location = "../../pages/view/ajax/treeResult.jsp"),
		@Result(name = "miRNADiff", location = "../../pages/view/ajax/miRNADiffResult.jsp"),
		@Result(name = "DGE", location = "../../pages/view/ajax/DGEResult.jsp"),
		@Result(name = "RNADeNovo", location = "../../pages/view/ajax/RNADeNovoResult.jsp"),
		@Result(name = "DeNovoDiff", location = "../../pages/view/ajax/DeNovoDiff.jsp"),
		@Result(name = "RNAseq", location = "../../pages/view/ajax/RNAseq.jsp"),
		@Result(name = "HCV", location = "../../pages/view/ajax/HCV.jsp"),
		@Result(name = "egfr", location = "../../pages/view/ajax/EGFR.jsp"),
		@Result(name = "DPD", location = "../../pages/view/ajax/DPD.jsp"),
		@Result(name = "BRAF", location = "../../pages/view/ajax/BRAF.jsp"),
		@Result(name = "UGT", location = "../../pages/view/ajax/UGT.jsp"),
		@Result(name = "TBINH", location = "../../pages/view/ajax/TBINH.jsp"),
		@Result(name = "TB", location = "../../pages/view/ajax/TB.jsp"),
		@Result(name = "gDNA_HRData", location = "../../pages/view/ajax/gDNA_HRData.jsp"),
		@Result(name = "translate", location = "../../pages/view/ajax/gadgets.jsp"),
		@Result(name = "projectTable", location = "../../pages/view/ajax/projectTable.jsp"),
		@Result(name = "NIPT", location = "../../pages/view/ajax/NIPT.jsp"),
		@Result(name = "VSP", location = "../../pages/view/ajax/VSP.jsp"),
		@Result(name = "_16S", location = "../../pages/view/ajax/16S.jsp") })
public class ProcedureAction extends ActionSupport {
	Logger log = Logger.getLogger(ProcedureAction.class);

	private static Map<String, String> dataMap = new HashMap<String, String>();
	private static Map<String, String> projectMap = new HashMap<String, String>();

	static {
		dataMap.put(AppNameIDConstant.SNP, "SNPData");
		dataMap.put(AppNameIDConstant.HBV_SNP2, "HBV_SNP2Data");
		dataMap.put(AppNameIDConstant.PGS, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.gDNA, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.gDNA_Chimeric, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.MDA_Chimeric, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.MDA_HR, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.gDNA_MR, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.gDNA_HR, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.MalBac, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.MDA_MR, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.SurePlex, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.miRNA, "miRNAData");
		dataMap.put(AppNameIDConstant.Tree, "HBVTree");
		dataMap.put(AppNameIDConstant.EST, "EST");
		dataMap.put(AppNameIDConstant.QC, "projectTable");
		dataMap.put(AppNameIDConstant.ExomeSNV, "ExomeSNVReport");
		dataMap.put(AppNameIDConstant.HCV, "HCV");
		dataMap.put(AppNameIDConstant.EGFR, "egfr");
		dataMap.put(AppNameIDConstant.KRAS, "egfr");
		dataMap.put(AppNameIDConstant.TB, "TB");
		dataMap.put(AppNameIDConstant.TRANSLATE, "translate");
		dataMap.put(AppNameIDConstant._16S, "_16S");
		dataMap.put(AppNameIDConstant.NIPT, "NIPT");
		dataMap.put(AppNameIDConstant.gDNA_MR_v1, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.gDNA_Chimeric_v1, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.gDNA_HR_v1, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.MDA_Chimeric_v1, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.MDA_HR_v1, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.MDA_MR_v1, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.SurePlex_v1, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.Sureplex_HR, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.MalBac_v1, "gDNA_HRData");
		dataMap.put(AppNameIDConstant.TB_INH, "TBINH");
		dataMap.put(AppNameIDConstant.DPD, "DPD");
		dataMap.put(AppNameIDConstant.BRAF, "BRAF");
		dataMap.put(AppNameIDConstant.UGT, "UGT");
		dataMap.put(AppNameIDConstant.CMP, "CMP");
		dataMap.put(AppNameIDConstant.VSP, "VSP");
		dataMap.put(AppNameIDConstant.CMP_199, "CMP_199");

		projectMap.put(AppNameIDConstant.Tree, "HBVTree");
	}

	private static final long serialVersionUID = 1L;
	private String userId;
	private String appId;
	private String appName;
	// 格式为 Celloud ， Celloud.ab1 ， X351.ab1 , anotherName ；...
	private String dataKeyList;
	private String sampleList;// 格式为 样本名称:数据名称1,数据名称2; ...
	private String diffList;// DIFF1:treatment1[sample1,sample1...]#control1[s1,s2...];DIFF2:...
	private String projectId;// 项目ID
	private String sampleName;// 样本名称
	private String projectName;
	private String email;
	private String dataKey;// 用于传递 dataKey
	private String fileName;//

	private String ada3;// adaptor，接头
	private String ada5;// adaptor，接头
	private String sp;// 物种
	private String cpu;// cpu 数量

	private Map<String, String> resultMap;
	private String flag;
	private String anotherName;
	private String dataInfos;
	private String company;
	private String user;
	private CmpReport cmpReport;
	private ReportService reportService = new ReportServiceImpl();

	private final String basePath = ServletActionContext.getServletContext()
			.getRealPath("/upload");

	/**
	 * 运行App
	 * 
	 * @return
	 * @throws IOException
	 */
	public String runApp() throws IOException {
		ServletActionContext.getResponse().setHeader(
				"Access-Control-Allow-Origin", "*");
		log.info("用户" + userId + "使用appId=" + appId + "运行projectId="
				+ projectId);
		final RunAppService app = new RunAppService();
		// 需要对datakeylist进行排序
		dataKeyList = FileTools.dataListSort(dataKeyList);
		new Thread(new Runnable() {
			@Override
			public void run() {
				app.runProject(basePath, userId, appId, appName, projectId,
						dataKeyList, email, projectName, sampleList, ada3,
						ada5, sp, cpu, diffList, fileName, dataInfos, company,
						user);
			}
		}).start();
		return SUCCESS;
	}

	/**
	 * 阅读报告
	 * 
	 * @return
	 * @throws IOException
	 */
	public String readReport() throws IOException {
		ServletActionContext.getResponse().setHeader(
				"Access-Control-Allow-Origin", "*");
		long start = new Date().getTime();
		ReadReportService report = new ReadReportService();
		if (projectId == null || "".equals(projectId)) {
			if (appId.equals("110")) {
				cmpReport = reportService.getCmpReport(dataKey, userId);
				resultMap = new HashMap<String, String>();
				resultMap.put("appId", appId);
				resultMap.put("fileName", fileName);
			} else {
				// 查看数据报告
				resultMap = report.readDataReport(basePath, userId, appId,
						dataKey, fileName, anotherName);
				if (resultMap != null) {
					resultMap.put("outProject", PropertiesUtils.outProject);
				}
			}
			long end = new Date().getTime();
			log.info("用户" + userId + "访问app：" + appId + "下DataKey=" + dataKey
					+ "的数据报告，用时" + (end - start) + "ms");
			return dataMap.get(appId);
		} else {
			// 查看项目报告
			resultMap = report.readProjectReport(basePath, userId, appId,
					projectId, sampleList);
			resultMap.put("outProject", PropertiesUtils.outProject);
			return projectMap.get(appId);
		}
	}

	public String printReport() {
		ServletActionContext.getResponse().setHeader(
				"Access-Control-Allow-Origin", "*");
		long start = new Date().getTime();
		long end = new Date().getTime();
		log.info("用户" + userId + "打印app：" + appId + "下DataKey=" + dataKey
				+ "的数据报告，用时" + (end - start) + "ms");
		if (appId.equals(AppNameIDConstant.CMP)
				|| appId.equals(AppNameIDConstant.CMP_199)) {
			cmpReport = reportService.getCmpReport(dataKey, userId);
			Map<String, CmpGeneDetectionDetail> gdd = cmpReport
					.getGeneDetectionDetail();
			System.out.println(gdd);
			return "printCMP";
		} else {
			ReadReportService report = new ReadReportService();
			resultMap = report.printReport(basePath, userId, appId, dataKey);
			return "";
		}
	}

	public String downPDF() throws IOException {
		ServletActionContext.getResponse().setHeader(
				"Access-Control-Allow-Origin", "*");
		String data[] = dataKeyList.split(";");
		String downPath = basePath + "/" + data[0] + "/" + data[1] + "/";
		String projectFolder = downPath+data[2];
		String proPDF = FileTools.fileExist(projectFolder, ".pdf", "endsWith");
		if(!proPDF.equals("")){
			flag = data[0] + "/" + data[1] + "/" + data[2] + "/"+proPDF;
			return SUCCESS;
		}
		String projectPath = downPath + data[2] + "/project.pdf";
		String keys[] = data[3].split(",");
		List<String> list = new ArrayList<String>();
		String dk = null;
		for (int i = 0; i < keys.length; i++) {
			if (data[1].equals("95")) {
				dk = downPath + keys[i] + "/Result";
			} else {
				dk = downPath + keys[i];
			}
			String pdf = FileTools.fileExist(dk, ".pdf", "endWith");
			if (!pdf.equals("")) {
				list.add(dk + "/" + pdf);
			}
		}
		flag = "";
		if (list.size() > 0) {
			FileTools.createFile(projectPath);
			MergePdf.merge(list.toArray(new String[list.size()]), projectPath);
			flag = data[0] + "/" + data[1] + "/" + data[2] + "/project.pdf";
		}
		return SUCCESS;
	}

	/**
	 * 下载通用方法
	 * 
	 * @throws IOException
	 */
	public void download() throws IOException {
		ServletActionContext.getResponse().setHeader(
				"Access-Control-Allow-Origin", "*");
		String downPath = "";
		String fileName = "";
		if (dataKey == null || "".equals(dataKey)) {
			downPath = basePath + "/" + userId + "/" + appId + "/" + projectId;
			fileName = FileTools.fileExist(downPath, ".zip", "endsWith");
		} else if ("T".equals(dataKey)) {
			downPath = basePath + "/" + userId;
			fileName = FileTools.fileExist(downPath, flag, "startsWith");
		} else {
			downPath = basePath + "/" + userId + "/" + appId + "/" + dataKey;
			fileName = FileTools.fileExist(downPath, ".zip", "endsWith");
		}
		FileTools.fileDownLoad(ServletActionContext.getResponse(), downPath
				+ "/" + fileName);
	}

	/**
	 * miRNA 下载方法
	 * 
	 * @throws IOException
	 */
	public void miRNADownload() throws IOException {
		ServletActionContext.getResponse().setHeader(
				"Access-Control-Allow-Origin", "*");
		FileTools.fileDownLoad(ServletActionContext.getResponse(), basePath
				+ "/" + userId);
	}

	public String getRNADeNovo() {
		ServletActionContext.getResponse().setHeader(
				"Access-Control-Allow-Origin", "*");

		String projectPath = basePath + "/" + userId + "/"
				+ AppNameIDConstant.RNA_DeNovo + "/" + projectId + "/";
		String downFile = projectPath
				+ FileTools.fileExist(projectPath, "Assembly_Sta.txt",
						"endsWith");
		String total = "";
		try {
			total = FileUtils.readFileToString(new File(downFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String detail[] = total.split("\n");
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < detail.length; i++) {
			String line = detail[i];
			if (!line.startsWith("*")) {
				sb.append(line).append("\n");
			}
		}
		flag = sb.toString().replace("\n", ";").replace("\t", ",");
		return SUCCESS;
	}

	/**
	 * 删除报告接口
	 */
	public void rmReport() {
		ServletActionContext.getResponse().setHeader(
				"Access-Control-Allow-Origin", "*");
		log.info("删除报告：" + userId + "/" + projectId + "|" + dataKey);
		String folder = (projectId == null || "".equals(projectId)) ? dataKey
				: projectId;
		String[] detail = folder.split(";");
		File rmUser = new File(basePath + "/" + userId);
		if (rmUser.exists()) {
			File[] list = rmUser.listFiles();
			for (int i = 0; i < list.length; i++) {
				for (int j = 0; j < detail.length; j++) {
					File rmPath = new File(list[i] + "/" + detail[j]);
					if (rmPath.exists()) {
						try {
							FileUtils.deleteDirectory(rmPath);
							log.info("删除报告:" + rmPath + "成功");
						} catch (IOException e) {
							log.info("删除报告:" + rmPath + "失败，错误" + e);
						}
					}
				}
			}
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getDataKeyList() {
		return dataKeyList;
	}

	public void setDataKeyList(String dataKeyList) {
		this.dataKeyList = dataKeyList;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Map<String, String> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, String> resultMap) {
		this.resultMap = resultMap;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSampleList() {
		return sampleList;
	}

	public void setSampleList(String sampleList) {
		this.sampleList = sampleList;
	}

	public String getAda3() {
		return ada3;
	}

	public void setAda3(String ada3) {
		this.ada3 = ada3;
	}

	public String getAda5() {
		return ada5;
	}

	public void setAda5(String ada5) {
		this.ada5 = ada5;
	}

	public String getSp() {
		return sp;
	}

	public void setSp(String sp) {
		this.sp = sp;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public String getDiffList() {
		return diffList;
	}

	public void setDiffList(String diffList) {
		this.diffList = diffList;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAnotherName() {
		return anotherName;
	}

	public void setAnotherName(String anotherName) {
		this.anotherName = anotherName;
	}

	public CmpReport getCmpReport() {
		return cmpReport;
	}

	public void setCmpReport(CmpReport cmpReport) {
		this.cmpReport = cmpReport;
	}

	public String getDataInfos() {
		return dataInfos;
	}

	public void setDataInfos(String dataInfos) {
		this.dataInfos = dataInfos;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}