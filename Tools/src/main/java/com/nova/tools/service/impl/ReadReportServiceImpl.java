package com.nova.tools.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.nova.tools.utils.FileTools;
import com.nova.tools.utils.PropertiesUtils;
import com.nova.tools.utils.TableUtil;

public class ReadReportServiceImpl {

	public Map<String, String> printCMPReport(String path, String outPath,
			String dataKey) {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("dataKey", dataKey);
		String logPath = path + "/LOG.txt";
		String statisPath = path + "/result/statistic.xls";
		String avgPath = path + "/result/average.info";
		String snpPath = path + "/result/snp_num.info";
		String[] snpArr = { "ABL1", "EGFR", "GNAQ", "KRAS", "PTPN11", "AKT1",
				"ERBB2", "GNAS", "MET", "RB1", "ALK", "ERBB4", "HNF1A", "MLH1",
				"RET", "APC", "EZH2", "HRAS", "MPL", "SMAD4", "ATM", "FBXW7",
				"IDH1", "NOTCH1", "SMARCB1", "BRAF", "FGFR1", "IDH2", "NPM1",
				"SMO", "CDH1", "FGFR2", "JAK2", "NRAS", "SRC", "CDKN2A",
				"FGFR3", "JAK3", "PDGFRA", "STK11", "CSF1R", "FLT3", "KDR",
				"PIK3CA", "TP53", "CTNNB1", "GNA11", "KIT", "PTEN", "VHL" };
		String allSnp = path + "/result/all.snp";
		String qualityPath = PropertiesUtils.outProject+"/upload/"+outPath + "/QC/" + dataKey
				+ "_fastqc/Images/per_base_quality.png";
		String seqContentPath = PropertiesUtils.outProject+"/upload/"+outPath  + "/QC/" + dataKey
				+ "_fastqc/Images/per_base_sequence_content.png";
		String fastqcDataPath = path + "/QC/" + dataKey
				+ "_fastqc/fastqc_data.txt";
		HashSet<String> folder = FileTools.getFolders(path+"/QC");
		Iterator<String> fol= folder.iterator();
		while(fol.hasNext()){
			String f = fol.next();
			if(!f.startsWith(dataKey)){
				String q2 = PropertiesUtils.outProject+"/upload/"+outPath + "/QC/" + f
						+ "/Images/per_base_quality.png";
				String s2 = PropertiesUtils.outProject+"/upload/"+outPath  + "/QC/" + f
						+ "/Images/per_base_sequence_content.png";
				String f2 = path + "/QC/" + f + "/fastqc_data.txt";
				resultMap.put("q2", q2);
				resultMap.put("s2", s2);
				List<String> list = FileTools.getLineByNum(f2, 3, 10);
				resultMap.put("f2", TableUtil.listToTableHasTbody(list, true));
			}
		}
		if (new File(logPath).exists()) {
			resultMap.put("runDate",
					FileTools.getFirstLine(logPath).substring(1, 11));
		}
		if (new File(statisPath).exists()) {
			List<String> list = FileTools.readLinestoString(statisPath);
			resultMap.put("allFragment", FileTools.listIsNull(list, 0));
			resultMap.put("avgQuality", FileTools.listIsNull(list, 1));
			resultMap.put("avgGC", FileTools.listIsNull(list, 2));
			resultMap.put("useFragment", FileTools.listIsNull(list, 3));
			resultMap.put("undetectGene", FileTools.listIsNull(list, 4));
			resultMap.put("detectGene", FileTools.listIsNull(list, 5));
		}
		if (new File(avgPath).exists()) {
			resultMap.put("avgCoverage", FileTools.getFirstLine(avgPath));
		}
		if (new File(snpPath).exists()) {
			List<String> list = FileTools.readLinestoString(snpPath);
			StringBuffer tbody1 = new StringBuffer();
			StringBuffer tbody2 = new StringBuffer();
			if (list != null) {
				tbody1.append("<tbody>");
				if (list.size() > 4) {
					tbody2.append("<tbody>");
					int begin = 0;
					int end = 0;
					if (list.size() % 2 == 0) {
						end = list.size() / 2 - 1;
						begin = list.size() / 2;
					} else {
						end = list.size() / 2;
						begin = list.size() / 2 + 1;
					}
					tbody1 = cmpToTbody(list, 0, end, -1);
					tbody2 = cmpToTbody(list, begin, list.size() - 1, end);
					tbody2.append("</tbody>");
				} else {
					tbody1 = cmpToTbody(list, 0, list.size() - 1, -1);
				}
				tbody1.append("</tbody>");
			}
			resultMap.put("snp_tbody1", tbody1.toString());
			resultMap.put("snp_tbody2", tbody2.toString());
		}
		for (String snpName : snpArr) {
			Map<String, String> snpMap = printCMPsnp(path, snpName);
			resultMap.putAll(snpMap);
		}
		if (new File(allSnp).exists()) {
			StringBuffer allSnpTable = new StringBuffer();
			List<String> list = FileTools.readLinestoString(allSnp);
			if (list.size() == 1 && list.get(0) == "") {
				resultMap.put("allSnp", list.get(0));
			} else {
				for (int i = 0; i < list.size(); i++) {
					String[] line_i = list.get(i).split("\t");
					String td = "";
					int length = line_i.length - 1;
					if (length < 6) {
						length = line_i.length;
					}
					for (int j = 0; j < length; j++) {
						String s = line_i[j];
						if (line_i[j].length() > 12) {
							s = line_i[j].substring(0, 13) + "...";
						}
						td += TableUtil.sepLine(s, "td");
					}
					allSnpTable.append("<tr>").append(td).append("</tr>");
				}
				resultMap.put("allSnp", allSnpTable.toString());
			}
		} else {
			resultMap.put("allSnp", null);
		}
		resultMap.put("per_base_quality", qualityPath);
		resultMap.put("per_base_seq_content", seqContentPath);
		List<String> list = FileTools.getLineByNum(fastqcDataPath, 3, 10);
		resultMap.put("fastqc_data", TableUtil.listToTableHasTbody(list, true));
		return resultMap;
	}

	public Map<String, String> CMPReport(String path, String outPath,
			String dataKey) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String statisPath = path + "/result/statistic.xls";
		String avgPath = path + "/result/average.info";
		String snpPath = path + "/result/snp_num.info";
		String logPath = path + "/LOG.txt";
		String qualityPath = PropertiesUtils.outProject + "/upload/" + outPath
				+ "/QC/" + dataKey + "_fastqc/Images/per_base_quality.png";
		String seqContentPath = PropertiesUtils.outProject + "/upload/"
				+ outPath + "/QC/" + dataKey
				+ "_fastqc/Images/per_base_sequence_content.png";
		String fastqcDataPath = path + "/QC/" + dataKey
				+ "_fastqc/fastqc_data.txt";
		HashSet<String> folder = FileTools.getFolders(path + "/QC");
		Iterator<String> fol = folder.iterator();
		while (fol.hasNext()) {
			String f = fol.next();
			if (!f.startsWith(dataKey)) {
				String q2 = PropertiesUtils.outProject + "/upload/" + outPath
						+ "/QC/" + f + "/Images/per_base_quality.png";
				String s2 = PropertiesUtils.outProject + "/upload/" + outPath
						+ "/QC/" + f + "/Images/per_base_sequence_content.png";
				String f2 = path + "/QC/" + f + "/fastqc_data.txt";
				resultMap.put("q2", q2);
				resultMap.put("s2", s2);
				List<String> list = FileTools.getLineByNum(f2, 3, 10);
				resultMap.put("f2", TableUtil.listToTableHasTbody(list, true));
			}
		}
		String detectGene;
		if (new File(logPath).exists()) {
			resultMap.put("runDate",
					FileTools.getFirstLine(logPath).substring(1, 11));
		}
		if (new File(statisPath).exists()) {
			List<String> list = FileTools.readLinestoString(statisPath);
			resultMap.put("allFragment", FileTools.listIsNull(list, 0));
			resultMap.put("avgQuality", FileTools.listIsNull(list, 1));
			resultMap.put("avgGC", FileTools.listIsNull(list, 2));
			resultMap.put("useFragment", FileTools.listIsNull(list, 3));
			resultMap.put("undetectGene", FileTools.listIsNull(list, 4));
			detectGene = FileTools.listIsNull(list, 5);
			resultMap.put("detectGene", detectGene);
		}
		if (new File(avgPath).exists()) {
			resultMap.put("avgCoverage", FileTools.getFirstLine(avgPath));
		}
		if (new File(snpPath).exists()) {
			List<String> list = FileTools.readLinestoString(snpPath);
			StringBuffer tbody1 = new StringBuffer();
			StringBuffer tbody2 = new StringBuffer();
			if (list != null) {
				tbody1.append("<tbody>");
				if (list.size() > 4) {
					tbody2.append("<tbody>");
					int begin = 0;
					int end = 0;
					if (list.size() % 2 == 0) {
						end = list.size() / 2 - 1;
						begin = list.size() / 2;
					} else {
						end = list.size() / 2;
						begin = list.size() / 2 + 1;
					}
					tbody1 = cmpToTbody(list, 0, end, -1);
					tbody2 = cmpToTbody(list, begin, list.size() - 1, end);
					tbody2.append("</tbody>");
				} else {
					tbody1 = cmpToTbody(list, 0, list.size() - 1, -1);
				}
				tbody1.append("</tbody>");
			}
			resultMap.put("snp_tbody1", tbody1.toString());
			resultMap.put("snp_tbody2", tbody2.toString());
		}
		resultMap.put("per_base_quality", qualityPath);
		resultMap.put("per_base_seq_content", seqContentPath);
		List<String> list = FileTools.getLineByNum(fastqcDataPath, 3, 10);
		resultMap.put("fastqc_data", TableUtil.listToTableHasTbody(list, true));
		return resultMap;
	}

	public StringBuffer cmpToTbody(List<String> list, int begin, int end,
			int length) {
		StringBuffer tbody = new StringBuffer();
		for (int i = begin; i <= end; i++) {
			String[] line_i = list.get(i).split("\t");
			if (getArray(line_i, 2) != null
					&& Integer.parseInt(getArray(line_i, 2)) < 50) {
				tbody.append(
						"<tr onclick='showGeneDetail('" + getArray(line_i, 0)
								+ "')'>").append(
						"<td><span style='background-color:#feaa20'>");
			} else {
				tbody.append("<tr><td><span>");
			}
			tbody.append(getArray(line_i, 0)).append("</span></td><td>")
					.append(getArray(line_i, 1))
					.append("</td><td>").append(getArray(line_i, 2))
					.append("</td></tr>");
		}
		if (length != -1 && end - begin < length) {
			tbody.append("<tr><td>&nbsp;</td><td></td><td></td></tr>");
		}
		return tbody;
	}

	/**
	 * HCV 报告读取
	 * 
	 * @param HCVPath
	 * @param projectId
	 * @param appId
	 * @param userId
	 * @return
	 */
	public Map<String, String> HCVReport(String HCVPath, String userId,
			String appId, String projectId) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String dataFile = HCVPath + "/Result.txt";
		StringBuffer sb = new StringBuffer();
		if (FileTools.checkPath(dataFile)) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(dataFile));
				String line = "";
				sb.append("<table class='table table-bordered table-condensed' id='hcvTable'><thead>");
				int countLine = 0;
				int maxLength = 0;
				while ((line = br.readLine()) != null) {
					countLine++;
					if ("".equals(line.trim())) {
						continue;
					}
					String detail[] = line.split("\t");
					sb.append("<tr>");
					for (int i = 0; i < detail.length; i++) {
						if (countLine == 1) {// 表头
							maxLength = detail.length;
							sb.append("<th>" + detail[i] + "</th>");
						} else {
							sb.append("<td>" + detail[i] + "</td>");
						}
					}
					if (maxLength > detail.length) {
						for (int i = 0; i < maxLength - detail.length; i++) {
							sb.append("<td></td>");
						}
					}
					sb.append("</tr>");
					if (countLine == 1) {
						sb.append("</thead><tbody>");
					}
				}
				sb.append("</tbody></table><br/>");
				String name = FileTools.fileExist(HCVPath + "/Fasta/", ".ab1",
						"endWith");
				String filePath = HCVPath + "/Fasta/" + name;
				if (new File(filePath).exists() && new File(filePath).isFile()) {
					String context = FileTools.readAppoint(filePath);
					sb.append("<div>" + context + "</div>");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		resultMap.put("table", sb.toString());
		return resultMap;
	}

	public Map<String, String> gDNA_HRDataReport(String snpPath, String imgPath) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String finalPng = FileTools.fileExist(snpPath, "final.png", "endsWith");
		if ("".equals(finalPng)) {
			String xls = snpPath + "/no_enough_reads.xls";
			String txt = snpPath + "/no_enough_reads.txt";
			String error = FileTools.checkPath(xls) ? xls : (FileTools
					.checkPath(txt) ? txt : null);
			if (error != null) {
				if (new File(error).exists()) {
					try {
						resultMap.put("info",
								FileUtils.readFileToString(new File(error)));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				resultMap.put("info", "运行出错");
			}
		} else {
			String miniPng = FileTools.fileExist(snpPath, "mini.png",
					"endsWith");
			String testPng = FileTools.fileExist(snpPath, "test.png",
					"endsWith");
			String split = FileTools.fileExist(snpPath, "test.split.png",
					"endsWith");
			String rawPng = FileTools.fileExist(snpPath, "Raw.png", "endsWith");
			String normalPng = FileTools.fileExist(snpPath, "Normalized.png",
					"endsWith");
			String pdf = FileTools.fileExist(snpPath, ".pdf", "endsWith");
			resultMap.put("finalPng", finalPng);
			resultMap.put("miniPng", miniPng);
			resultMap.put("testPng", testPng);
			resultMap.put("split", split);
			resultMap.put("rawPng", rawPng);
			resultMap.put("normalPng", normalPng);
			resultMap.put("outPath", PropertiesUtils.outProject + "/upload");

			if (new File(snpPath + "/report.txt").exists()) {
				try {
					String txt = FileUtils.readFileToString(new File(snpPath
							+ "/report.txt"));
					txt = txt.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
					resultMap.put("txt", txt);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			if (new File(snpPath + "/report.xls").exists()) {
				String xls =TableUtil.simpleTable(snpPath + "/report.xls", true);
				resultMap.put("xls", xls);
			}

			String downloadPath = PropertiesUtils.outProject
					+ "/Procedure!miRNADownload?userId=" + imgPath + "/"
					+ finalPng;
			resultMap.put("down", downloadPath);

			if (FileTools.checkPath(snpPath + "/" + pdf)) {
				String pdfPath = PropertiesUtils.outProject
						+ "/Procedure!miRNADownload?userId=" + imgPath + "/"
						+ pdf;
				resultMap.put("pdf", pdfPath);
			} else {
				resultMap.put("pdf", "false");
			}
			List<String> xlslist = FileTools.fileSearch(snpPath, ".xls", "endsWith");
			String xls = null;
			for (String s : xlslist) {
				if(!"report.xls".equals(s)){
					xls=s;
				}
			}
			String context[] = null;
			if (new File(snpPath + "/" + xls).exists()) {
				try {
					context = FileUtils.readFileToString(
							new File(snpPath + "/" + xls)).split("\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			StringBuffer sb = new StringBuffer(
					"<table class='table table-bordered table-condensed'><thead><tr><th>");
			sb.append(context[1].replace("\t", "</th><th>"));
			sb.append("</th></tr></thead><tbody><tr><td>");
			sb.append(context[2].replace("\t", "</td><td>"));
			sb.append("</td></tr></tbody></table>");
			resultMap.put("table", sb.toString());
			if (context.length > 3) {
				resultMap.put("notes", context[3]);
			}
		}
		return resultMap;
	}

	public Map<String, String> getNIPTDataReport(String snpPath) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String report = FileTools.fileExist(snpPath, "Data_report.txt",
				"endsWith");
		String png = FileTools.fileExist(snpPath, "Pic.txt.mini.png", "endsWith");
		String zscore = FileTools.fileExist(snpPath, "Zscore.txt", "endsWith");
		if (!"".equals(report)) {
			String r1 = TableUtil.simpleTable(snpPath + "/" + report, true);
			resultMap.put("r1", r1);
		}
		if (new File(snpPath + "/" + png).exists()) {
			resultMap.put("png", png);
		}
		if (!"".equals(zscore)) {
			String r2 = TableUtil.simpleTable(snpPath + "/" + zscore, true);
			resultMap.put("r2", r2);
		}
		resultMap.put("outPath", PropertiesUtils.outProject + "/upload");
		return resultMap;
	}

	/**
	 * DPD 数据报告
	 * 
	 * @param path
	 * @return
	 */
	public Map<String, String> DPDDataReport(String path) {
		Map<String, String> resultMap = new HashMap<String, String>();
		// 位点图片
		String number[] = { "1", "2", "3", "4", "5" };
		for (String num : number) {
			resultMap.put("listAll" + num, FileTools.fileExist(path + "/SVG",
					num + "_all.png", "endsWith"));
		}
		if (new File(path + "/report.txt.wz.1").exists()) {
			String result = FileTools.readAppoint(path + "/report.txt.wz.1");
			resultMap.put("result1", result);
		}
		if (new File(path + "/report.txt.wz.2").exists()) {
			String result = FileTools.readAppoint(path + "/report.txt.wz.2");
			if (result != null) {
				String context[] = result.split("<br />");
				StringBuffer sb = new StringBuffer("<table>");
				for (String st : context) {
					sb.append("<tr><td>" + st + "</td></tr>");
				}
				sb.append("</table>");
				resultMap.put("result2", sb.toString());
			}
		}
		if (new File(path + "/SVG/Report.txt.seq.txt").exists()) {
			try {
				String seq = FileUtils.readFileToString(new File(path
						+ "/SVG/Report.txt.seq.txt"));
				resultMap.put("seq", seq);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		resultMap.put("outPath", PropertiesUtils.outProject + "/upload");
		return resultMap;
	}

	/**
	 * EGFR 数据报告
	 * 
	 * @param path
	 * @return
	 */
	public Map<String, String> EGFRDataReport(String path) {
		Map<String, String> resultMap = new HashMap<String, String>();
		// 位点图片
		String number[] = { "1", "2", "3", "4", "5" };
		for (String num : number) {
			resultMap.put("listAll" + num, FileTools.fileExist(path + "/SVG",
					num + "_all.png", "endsWith"));
		}

		String result = null;
		if (new File(path + "/report.txt").exists()) {
			String first = FileTools.getFirstLine(path + "/report.txt");
			String r = first == null ? "0" : first.replace(
					"EGFR exon number is ", "");
			resultMap.put("length", r);
			try {
				result = FileUtils.readFileToString(new File(path
						+ "/report.txt"), "GBK");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (result != null) {
			String context[] = result.split("\n");
			StringBuffer sb = new StringBuffer("<table>");
			for (String st : context) {
				sb.append("<tr><td>" + st + "</td></tr>");
			}
			sb.append("</table>");
			resultMap.put("result", sb.toString());
		}
		resultMap.put("outPath", PropertiesUtils.outProject + "/upload");

		if (new File(path + "/SVG/Report.txt.seq.txt").exists()) {
			try {
				String seq = FileUtils.readFileToString(new File(path
						+ "/SVG/Report.txt.seq.txt"));
				resultMap.put("seq", seq);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultMap;
	}

	/**
	 * TBINH 数据报告
	 * 
	 * @param path
	 * @return
	 */
	public Map<String, String> TBINHReport(String path) {
		Map<String, String> resultMap = new HashMap<String, String>();
		// 位点图片
		String number[] = { "1", "2", "3", "4", "5" };
		for (String num : number) {
			resultMap.put("listAll" + num, FileTools.fileExist(path + "/SVG",
					num + "_all.png", "endsWith"));
		}

		String result1 = path + "/report.txt.1";
		if (new File(result1).exists()) {
			try {
				result1 = FileUtils.readFileToString(new File(result1), "GBK");
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (result1 != null) {
				String context[] = result1.split("\n");
				StringBuffer sb = new StringBuffer("<table>");
				for (String st : context) {
					sb.append("<tr><td>" + st + "</td></tr>");
				}
				sb.append("</table>");
				resultMap.put("result1", sb.toString());
			}
		}

		String result = path + "/report.txt";
		if (new File(result).exists()) {
			String first = FileTools.getFirstLine(result);
			if (first == null) {
				resultMap.put("result", "no result");
			} else {
				resultMap.put("result", first);
				resultMap.put("type", first.replace("INH gene is ", ""));
			}
		}

		String result2 = path + "/report.txt.2";
		if (new File(result2).exists()) {
			try {
				result2 = FileUtils.readFileToString(new File(result2), "GBK");
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (result2 != null) {
				String context[] = result2.split("\n");
				StringBuffer sb = new StringBuffer("<table>");
				for (String st : context) {
					sb.append("<tr><td>" + st + "</td></tr>");
				}
				sb.append("</table>");
				resultMap.put("result2", sb.toString());
			}
		}

		resultMap.put("outPath", PropertiesUtils.outProject + "/upload");

		if (new File(path + "/SVG/Report.txt.seq.txt").exists()) {
			try {
				String seq = FileUtils.readFileToString(new File(path
						+ "/SVG/Report.txt.seq.txt"));
				resultMap.put("seq", seq);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultMap;
	}

	/**
	 * KRAS 数据报告
	 * 
	 * @param path
	 * @return
	 */
	public Map<String, String> KRASDataReport(String path) {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("listAll1", "1_all.png");
		resultMap.put("listAll2", "2_all.png");
		resultMap.put("listAll3", "3_all.png");
		resultMap.put("listAll4", "4_all.png");
		resultMap.put("listAll5", "5_all.png");
		String result = null;
		if (new File(path + "/report.txt").exists()) {
			String first = FileTools.getFirstLine(path + "/report.txt");
			String r = first == null ? "0" : first.replace(
					"KRAS exon number is ", "");
			resultMap.put("length", r);
			try {
				result = FileUtils.readFileToString(new File(path
						+ "/report.txt"), "GBK");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (result != null) {
			String context[] = result.split("\n");
			StringBuffer sb = new StringBuffer("<table>");
			for (String st : context) {
				sb.append("<tr><td>" + st + "</td></tr>");
			}
			sb.append("</table>");
			resultMap.put("result", sb.toString());
		}
		resultMap.put("outPath", PropertiesUtils.outProject + "/upload");
		if (new File(path + "/SVG/Report.txt.seq.txt").exists()) {
			try {
				String seq = FileUtils.readFileToString(new File(path
						+ "/SVG/Report.txt.seq.txt"));
				resultMap.put("seq", seq);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultMap;
	}

	/**
	 * HBV_SNP 的数据报告
	 * 
	 * @param snpPath
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> HBVSNPDataReport(String snpPath)
			throws IOException {
		Map<String, String> resultMap = new HashMap<String, String>();
		String svg173 = FileTools.fileExist(snpPath, "173.png", "endsWith");
		String svg180 = FileTools.fileExist(snpPath, "180.png", "endsWith");
		String svg181 = FileTools.fileExist(snpPath, "181.png", "endsWith");
		String svg184 = FileTools.fileExist(snpPath, "184.png", "endsWith");
		String svg202 = FileTools.fileExist(snpPath, "202.png", "endsWith");
		String svg204 = FileTools.fileExist(snpPath, "204.png", "endsWith");
		String svg207 = FileTools.fileExist(snpPath, "207.png", "endsWith");
		String svg213 = FileTools.fileExist(snpPath, "213.png", "endsWith");
		String svg214 = FileTools.fileExist(snpPath, "214.png", "endsWith");
		String svg215 = FileTools.fileExist(snpPath, "215.png", "endsWith");
		String svg236 = FileTools.fileExist(snpPath, "236.png", "endsWith");
		String svg237 = FileTools.fileExist(snpPath, "237.png", "endsWith");
		String svg238 = FileTools.fileExist(snpPath, "238.png", "endsWith");
		String svg250 = FileTools.fileExist(snpPath, "250.png", "endsWith");
		List<String> listNew = FileTools.fileSearch(snpPath, "new.png",
				"endWith");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < listNew.size(); i++) {
			sb.append(listNew.get(i) + ";");
		}
		String all1 = FileTools.fileExist(snpPath, "1_all.png", "endsWith");
		String all2 = FileTools.fileExist(snpPath, "2_all.png", "endsWith");
		String all3 = FileTools.fileExist(snpPath, "3_all.png", "endsWith");
		String all4 = FileTools.fileExist(snpPath, "4_all.png", "endsWith");

		List<String> listAll = new ArrayList<String>();
		listAll.add(all1);
		listAll.add(all2);
		listAll.add(all3);
		listAll.add(all4);

		String result = FileTools.readAppoint(snpPath + "/Report.txt");
		result = result.split("Other")[0];
		String snpType = FileTools.readAppoint(snpPath + "/type.txt");

		resultMap.put("svg173", svg173);
		resultMap.put("svg180", svg180);
		resultMap.put("svg181", svg181);
		resultMap.put("svg184", svg184);
		resultMap.put("svg202", svg202);
		resultMap.put("svg204", svg204);
		resultMap.put("svg207", svg207);
		resultMap.put("svg213", svg213);
		resultMap.put("svg214", svg214);
		resultMap.put("svg215", svg215);
		resultMap.put("svg236", svg236);
		resultMap.put("svg237", svg237);
		resultMap.put("svg238", svg238);
		resultMap.put("svg250", svg250);
		resultMap.put("listNew", sb.toString());
		resultMap.put("listAll1", all1);
		resultMap.put("listAll2", all2);
		resultMap.put("listAll3", all3);
		resultMap.put("listAll4", all4);
		resultMap.put("result", result);
		resultMap.put("outPath", PropertiesUtils.outProject + "/upload");
		resultMap.put("snpType", snpType);
		return resultMap;
	}

	public Map<String, String> HBV_SNP2DataReport(String snpPath)
			throws IOException {
		Map<String, String> resultMap = new HashMap<String, String>();
		String r204 = snpPath + "/Report.txt.204";
		if (new File(r204).exists()) {
			String clinicalPath = snpPath.replace("SVG", "") + "clinical.txt";
			if (new File(clinicalPath).exists()) {
				String clinical = TableUtil.SNPTable(clinicalPath);
				resultMap.put("clinical", clinical);
			}
			String[] info = FileUtils.readFileToString(new File(r204)).split(
					"\n");
			resultMap.put("title", info[0]);
			if (info.length > 1) {
				String context = "";
				for (int i = 1; i < info.length; i++) {
					context += info[i] + "<br/>";
				}
				context = context.replace("\t",
						"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				resultMap.put("context", context);
				String y[] = { "LAM", "FTC", "LDT", "ETV" };
				for (int i = 0; i < y.length; i++) {
					if (context.contains(y[i])) {
						resultMap.put(y[i], "true");
					} else {
						resultMap.put(y[i], "false");
					}
				}
			}
		}
		// 位点图片
		String number[] = { "169", "173", "180", "181", "184", "194", "202",
				"204", "236", "250" };
		for (String num : number) {
			resultMap.put("svg" + num,
					FileTools.fileExist(snpPath, num + ".png", "endsWith"));
		}
		for (String num : number) {
			resultMap.put("svg10" + num,
					FileTools.fileExist(snpPath, num + ".10.png", "endsWith"));
		}
		// 其他
		String other = FileTools.fileSearch(snpPath, "new.png", "endWith")
				.toString();
		resultMap.put("other",
				other.substring(other.indexOf("[") + 1, other.indexOf("]")));
		// 所有检索结果（大图）
		String all1 = FileTools.fileExist(snpPath, "1_all.png", "endsWith");
		String all2 = FileTools.fileExist(snpPath, "2_all.png", "endsWith");
		String all3 = FileTools.fileExist(snpPath, "3_all.png", "endsWith");
		String all4 = FileTools.fileExist(snpPath, "4_all.png", "endsWith");
		String all5 = FileTools.fileExist(snpPath, "5_all.png", "endsWith");
		resultMap.put("listAll1", all1);
		resultMap.put("listAll2", all2);
		resultMap.put("listAll3", all3);
		resultMap.put("listAll4", all4);
		resultMap.put("listAll5", all5);

		String result = FileTools.readAppoint(snpPath + "/Report.txt");
		result = result.split("Other")[0];
		resultMap.put("result", result);
		String y[] = { "LAM", "FTC", "LDT", "ETV", "TDF", "ADV" };
		String[] r = result.split("<br />");
		for (int i = 0; i < y.length; i++) {
			for (String s : r) {
				if (s.contains(y[i])) {
					if (s.contains("未检测到")) {
						resultMap.put("is" + y[i], "true");
					} else {
						resultMap.put("is" + y[i], "false");
					}
				}
			}
		}
		String snpType = FileTools.readAppoint(snpPath + "/type.txt");
		resultMap.put("snpType", snpType);
		if (new File(snpPath + "/Report.txt.seq.txt").exists()) {
			String seq = FileTools.readAppoint(snpPath + "/Report.txt.seq.txt");
			resultMap.put("seq", seq);
		}
		resultMap.put("outPath", PropertiesUtils.outProject + "/upload");
		resultMap.put("down", PropertiesUtils.outProject
				+ "/Procedure!miRNADownload?userId=");
		return resultMap;
	}

	private static String getArray(String[] n, int num) {
		return n == null ? null : (n.length > num ? n[num] : null);
	}

	private static Map<String, String> printCMPsnp(String path, String snpName) {
		String snpPath = path + "/result/" + snpName + ".snp";
		Map<String, String> snpMap = new HashMap<String, String>();
		if (new File(snpPath).exists()) {
			List<String> list = FileTools.readLinestoString(snpPath);
			StringBuffer snptable = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				if (i == 0) {
					snpMap.put(snpName + "Coverage", list.get(i));
				} else {
					String[] line_i = list.get(i).split("\t");
					String td = "";
					int length = line_i.length - 1;
					if (length < 6) {
						length = line_i.length;
					}
					for (int j = 0; j < length; j++) {
						String s = line_i[j];
						if (line_i[j].length() > 12) {
							s = line_i[j].substring(0, 13) + "...";
						}
						td += TableUtil.sepLine(s, "td");
					}
					snptable.append("<tr>").append(td).append("</tr>");
				}
			}
			snpMap.put(snpName + "Table", snptable.toString());
		} else {
			snpMap.put(snpName + "Coverage", null);
			snpMap.put(snpName + "Table", null);
		}
		return snpMap;
	}
}