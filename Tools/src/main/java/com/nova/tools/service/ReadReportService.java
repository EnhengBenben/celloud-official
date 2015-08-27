package com.nova.tools.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.nova.tools.constant.AppNameIDConstant;
import com.nova.tools.service.impl.ReadReportServiceImpl;
import com.nova.tools.utils.FileTools;
import com.nova.tools.utils.PropertiesUtils;
import com.nova.tools.utils.TableUtil;

/**
 * @Description: app 报告读取的 service
 * @author lin
 * @date 2013-7-29 下午8:51:49
 */
public class ReadReportService {
    /**
     * 读取数据报告
     * 
     * @param userId
     * @param appId
     * @param dataId
     * @return
     * @throws IOException
     */
    public Map<String, String> readDataReport(String basePath, String userId,
	    String appId, String dataKey, String fileName, String anotherName)
	    throws IOException {
	Map<String, String> reportMap = new HashMap<String, String>();
	ReadReportServiceImpl readReport = new ReadReportServiceImpl();

	if (AppNameIDConstant.VSP.equals(appId)) {
	    reportMap.put("datakey", dataKey);
	    reportMap.put("fileName",
		    ("null".equals(anotherName) || anotherName == null || ""
			    .equals(anotherName)) ? fileName : anotherName);
	    // 取1000个gene名称,可以考虑放在页面上
	    String gene = basePath.replace("upload", "")
		    + "/resource/txt/gene_list.txt";
	    String geneTable = TableUtil.simpleTable(gene, 5);
	    reportMap.put("geneTable", geneTable);

	    String result = basePath + "/" + userId + "/" + appId + "/"
		    + dataKey;
	    List<String> xlss = FileTools.fileSearch(result, "tsv", "endWith");
	    String datalist = FileUtils.readFileToString(new File(result
		    + "/datalist.txt"));
	    String[] line = datalist.split("\n");
	    Map<String, String> key = new HashMap<>();
	    for (String s : line) {
		key.put(s.split("\t")[0], s.split("\t")[1]);
	    }
	    for (int i = 0; i < xlss.size(); i++) {
		reportMap.put("source" + i, key.get(xlss.get(i)));
	    }
	    for (String xls : xlss) {
		String re = result + "/" + xls;
		if (FileTools.getFirstLine(re).split("\t").length == 17) {// gene
		    List<Integer> list = new ArrayList<>();
		    list.add(0);
		    list.add(7);
		    list.add(2);
		    String title = "<tr><th>编号</th><th>基因</th><th>染色体位置</th><th>突变位点数</th></tr>";
		    String geneResult = TableUtil.simpleTable(re, list, 1,
			    title, true);
		    reportMap.put("geneResult", geneResult);

		    List<Integer> list2 = new ArrayList<>();
		    list2.add(0);
		    list2.add(15);
		    list2.add(16);
		    String des = TableUtil.txtGetComumns(re, list2);
		    reportMap.put("des", des);
		} else if (FileTools.getFirstLine(re).split("\t").length == 71) {// Variants
		    List<Integer> list = new ArrayList<>();
		    list.add(0);
		    list.add(1);
		    list.add(2);
		    list.add(6);
		    list.add(7);
		    list.add(12);
		    list.add(13);
		    list.add(21);
		    list.add(35);
		    String title = "<tr><th>Gene</th><th>Variant</th><th>Chr</th><th>Genotype</th><th>Exonic</th><th>Mut_base%</th><th>depth</th><th>Mut type</th><th>dbsnp ID</th></tr>";
		    String variant = TableUtil.simpleTable(re, list, 1, title,
			    false);
		    reportMap.put("variant", variant);
		}
	    }
	    return reportMap;
	}

	if (AppNameIDConstant._16S.equals(appId)) {
	    reportMap.put("datakey", dataKey);
	    reportMap.put("fileName",
		    ("null".equals(anotherName) || anotherName == null || ""
			    .equals(anotherName)) ? fileName : anotherName);
	    String path = basePath + "/" + userId + "/" + appId + "/" + dataKey
		    + "/result.txt";
	    reportMap.put("table", TableUtil.simpleTable(path, true));
	    return reportMap;
	}

	// Translate
	if (AppNameIDConstant.TRANSLATE.equals(appId)) {
	    String translate = basePath + "/" + userId + "/" + appId + "/"
		    + dataKey + "/" + dataKey + ".txt";
	    String dataPath = PropertiesUtils.dataPath;
	    if (new File(translate).exists()) {
		reportMap.put(
			"source",
			FileTools.readAppoint(dataPath
				+ dataKey
				+ fileName.substring(fileName.lastIndexOf("."),
					fileName.length())));
		reportMap.put("result", FileTools.readAppoint(translate));
	    }
	    reportMap.put("fileName",
		    ("null".equals(anotherName) || anotherName == null || ""
			    .equals(anotherName)) ? fileName : anotherName);
	    return reportMap;
	}

	// HCV
	if (AppNameIDConstant.HCV.equals(appId)) {
	    String HCVPath = basePath + "/" + userId + "/" + appId + "/"
		    + dataKey;
	    reportMap = readReport.HCVReport(HCVPath, userId, appId, dataKey);
	    reportMap.put("datakey", dataKey);
	    reportMap.put("pagePath", userId + "/" + appId + "/" + dataKey);
	    reportMap.put("fileName",
		    ("null".equals(anotherName) || anotherName == null || ""
			    .equals(anotherName)) ? fileName : anotherName);
	    return reportMap;
	}

	// gDNA_HR
	if (AppNameIDConstant.gDNA_HR.equals(appId)
		|| AppNameIDConstant.MalBac.equals(appId)
		|| AppNameIDConstant.gDNA.equals(appId)
		|| AppNameIDConstant.gDNA_MR.equals(appId)
		|| AppNameIDConstant.gDNA_MR_v1.equals(appId)
		|| AppNameIDConstant.gDNA_Chimeric_v1.equals(appId)
		|| AppNameIDConstant.gDNA_HR_v1.equals(appId)
		|| AppNameIDConstant.MDA_Chimeric_v1.equals(appId)
		|| AppNameIDConstant.MDA_HR_v1.equals(appId)
		|| AppNameIDConstant.MDA_MR_v1.equals(appId)
		|| AppNameIDConstant.MalBac_v1.equals(appId)
		|| AppNameIDConstant.SurePlex_v1.equals(appId)
		|| AppNameIDConstant.Sureplex_HR.equals(appId)
		|| AppNameIDConstant.PGS.equals(appId)
		|| AppNameIDConstant.MDA_MR.equals(appId)
		|| AppNameIDConstant.MDA_HR.equals(appId)
		|| AppNameIDConstant.MDA_Chimeric.equals(appId)
		|| AppNameIDConstant.gDNA_Chimeric.equals(appId)
		|| AppNameIDConstant.SurePlex.equals(appId)) {
	    String snpPath = basePath + "/" + userId + "/" + appId + "/"
		    + dataKey;
	    reportMap = readReport.gDNA_HRDataReport(snpPath, userId + "/"
		    + appId + "/" + dataKey);
	    reportMap.put("pagePath", userId + "/" + appId + "/" + dataKey);
	    reportMap.put("fileName",
		    ("null".equals(anotherName) || anotherName == null || ""
			    .equals(anotherName)) ? fileName : anotherName);
	    reportMap.put("appId", appId);
	    return reportMap;
	}

	// NIPT
	if (AppNameIDConstant.NIPT.equals(appId)) {
	    String snpPath = basePath + "/" + userId + "/" + appId + "/"
		    + dataKey + "/Result";
	    reportMap = readReport.getNIPTDataReport(snpPath);
	    reportMap.put("pagePath", userId + "/" + appId + "/" + dataKey);
	    reportMap.put("fileName",
		    ("null".equals(anotherName) || anotherName == null || ""
			    .equals(anotherName)) ? fileName : anotherName);
	    return reportMap;
	}

	// HBV_SNP数据报告
	if (AppNameIDConstant.SNP.equals(appId)) {
	    String snpPath = basePath + "/" + userId + "/" + appId + "/"
		    + dataKey + "/SVG";
	    reportMap = readReport.HBVSNPDataReport(snpPath);
	    reportMap.put("pagePath", userId + "/" + appId + "/" + dataKey
		    + "/SVG");
	    reportMap.put("fileName",
		    ("null".equals(anotherName) || anotherName == null || ""
			    .equals(anotherName)) ? fileName : anotherName);
	    return reportMap;
	}

	// DPD ||BRAF || UGT 数据报告
	if (AppNameIDConstant.DPD.equals(appId)
		|| AppNameIDConstant.BRAF.equals(appId)
		|| AppNameIDConstant.UGT.equals(appId)) {
	    String path = basePath + "/" + userId + "/" + appId + "/" + dataKey;
	    reportMap = readReport.DPDDataReport(path);
	    reportMap.put("pagePath", userId + "/" + appId + "/" + dataKey
		    + "/SVG");
	    reportMap.put("fileName",
		    ("null".equals(anotherName) || anotherName == null || ""
			    .equals(anotherName)) ? fileName : anotherName);
	    return reportMap;
	}

	// EGFR 数据报告
	if (AppNameIDConstant.EGFR.equals(appId)) {
	    String path = basePath + "/" + userId + "/" + appId + "/" + dataKey;
	    reportMap = readReport.EGFRDataReport(path);
	    reportMap.put("pagePath", userId + "/" + appId + "/" + dataKey
		    + "/SVG");
	    if (FileTools.checkPath(path + "/EGFR.pdf")) {
		String pdfPath = PropertiesUtils.outProject
			+ "/Procedure!miRNADownload?userId=" + userId + "/"
			+ appId + "/" + dataKey + "/EGFR.pdf";
		reportMap.put("pdf", pdfPath);
	    } else {
		reportMap.put("pdf", "false");
	    }
	    reportMap.put("fileName",
		    ("null".equals(anotherName) || anotherName == null || ""
			    .equals(anotherName)) ? fileName : anotherName);
	    return reportMap;
	}

	// TB_INH 数据报告
	if (AppNameIDConstant.TB_INH.equals(appId)) {
	    String path = basePath + "/" + userId + "/" + appId + "/" + dataKey;
	    reportMap = readReport.TBINHReport(path);
	    String countTxt = basePath + "/" + userId + "/" + appId + "/"
		    + reportMap.get("type") + ".txt";
	    reportMap.put("Mutant", FileTools.getFirstLine(countTxt));
	    reportMap.put("Wild", FileTools.getLineByNum(countTxt, 2));
	    reportMap.put("pagePath", userId + "/" + appId + "/" + dataKey
		    + "/SVG");
	    reportMap.put("fileName",
		    ("null".equals(anotherName) || anotherName == null || ""
			    .equals(anotherName)) ? fileName : anotherName);
	    return reportMap;
	}

	// TB 数据报告
	if (AppNameIDConstant.TB.equals(appId)) {
	    String path = basePath + "/" + userId + "/" + appId + "/" + dataKey;
	    reportMap = readReport.EGFRDataReport(path);
	    reportMap.put("pagePath", userId + "/" + appId + "/" + dataKey
		    + "/SVG");
	    if (FileTools.checkPath(path + "/TB_Rifampicin.pdf")) {
		String pdfPath = PropertiesUtils.outProject
			+ "/Procedure!miRNADownload?userId=" + userId + "/"
			+ appId + "/" + dataKey + "/TB_Rifampicin.pdf";
		reportMap.put("pdf", pdfPath);
	    } else {
		reportMap.put("pdf", "false");
	    }
	    reportMap.put("fileName",
		    ("null".equals(anotherName) || anotherName == null || ""
			    .equals(anotherName)) ? fileName : anotherName);
	    return reportMap;
	}

	// KRAS
	if (AppNameIDConstant.KRAS.equals(appId)) {
	    String path = basePath + "/" + userId + "/" + appId + "/" + dataKey;
	    reportMap = readReport.KRASDataReport(path);
	    reportMap.put("pagePath", userId + "/" + appId + "/" + dataKey
		    + "/SVG");
	    if (FileTools.checkPath(path + "/KRAS.pdf")) {
		String pdfPath = PropertiesUtils.outProject
			+ "/Procedure!miRNADownload?userId=" + userId + "/"
			+ appId + "/" + dataKey + "/KRAS.pdf";
		reportMap.put("pdf", pdfPath);
	    } else {
		reportMap.put("pdf", "false");
	    }
	    reportMap.put("fileName",
		    ("null".equals(anotherName) || anotherName == null || ""
			    .equals(anotherName)) ? fileName : anotherName);
	    return reportMap;
	}

	// HBV_SNP2 数据报告
	if (AppNameIDConstant.HBV_SNP2.equals(appId)) {
	    String snpPath = basePath + "/" + userId + "/" + appId + "/"
		    + dataKey + "/SVG";
	    reportMap = readReport.HBV_SNP2DataReport(snpPath);
	    reportMap.put("pagePath", userId + "/" + appId + "/" + dataKey
		    + "/SVG");
	    reportMap.put("zip", userId + "/" + appId + "/" + dataKey
		    + "/HBV_SNP.zip");
	    reportMap.put("pdf", userId + "/" + appId + "/" + dataKey
		    + "/HBV_SNP.pdf");
	    reportMap.put("fileName",
		    ("null".equals(anotherName) || anotherName == null || ""
			    .equals(anotherName)) ? fileName : anotherName);
	    return reportMap;
	}
	return null;
    };

    /**
     * 读取项目报告
     * 
     * @param userId
     * @param appId
     * @param projectId
     * @return
     */
    public Map<String, String> readProjectReport(String basePath,
	    String userId, String appId, String projectId, String sampleList) {
	Map<String, String> reportMap = new HashMap<String, String>();
	// HBV_Tree 项目报告
	if (AppNameIDConstant.Tree.equals(appId)) {
	    String treePath = basePath + "/" + userId + "/" + appId + "/"
		    + projectId;
	    reportMap.put("imgName",
		    FileTools.fileExist(treePath, ".png", "endsWith"));
	    reportMap.put("outPath", PropertiesUtils.outProject + "/upload");
	    reportMap.put("pagePath", userId + "/" + appId + "/" + projectId);
	    return reportMap;
	}
	return null;
    }
}