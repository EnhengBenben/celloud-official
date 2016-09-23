package com.celloud.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.itext.PGSProjectPDF;
import com.celloud.model.mysql.DataFile;

/**
 * 
 * @author lin
 * @date 2015-11-2 下午2:39:11
 * @description :项目运行结束后的后续操作
 */
public class RunOverUtil {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * TBINH流程运行结束后的数据处理
	 * 
	 * @param reportPath
	 * @param dataKey
	 * @param appTitle
	 * @param projectFile
	 * @param projectId
	 * @param dataList
	 * @return
	 * @author MQ
	 */
	public boolean TBINH(String reportPath, String dataKey, String appTitle, String projectFile, String projectId,
			List<DataFile> dataList) {
		// 1. 追加表头
		StringBuffer resultArray = new StringBuffer();
		resultArray.append(appTitle);
		// 2. 遍历数据列表
		for (DataFile data : dataList) {
			String finalPath = reportPath + data.getDataKey();

			String report = finalPath + "/report.txt";
			int lines = FileTools.countLines(report);
			String gene = "";
			if (lines == 0) {// 文件为空
				gene = "no result";
			} else {
				// 第一行最后就是gene值
				String first = FileTools.getFirstLine(report);
				String genes[] = first.split(" ");
				gene = genes[genes.length - 1];
			}
			boolean isKatG = gene.equals("KatG");
			String position = ScreeningUtil.unitColumn(finalPath + "/report.txt.1", isKatG);
			resultArray.append(data.getDataKey() + "\t" + data.getFileName() + "\t" + gene + "\t" + position + "\n");
		}
		FileTools.appendWrite(projectFile, resultArray.toString());
		return true;
	}

	/**
	 * TBRifampicin流程运行结束后的数据处理
	 * 
	 * @param reportPath
	 * @param dataKey
	 * @param appTitle
	 * @param projectFile
	 * @param projectId
	 * @param dataList
	 * @return
	 * @author MQ
	 */
	public boolean TBRifampicin(String reportPath, String dataKey, String appTitle, String projectFile,
			String projectId, List<DataFile> dataList) {
		// 1. 追加表头
		StringBuffer resultArray = new StringBuffer();
		resultArray.append(appTitle);
		// 2. 遍历数据列表
		for (DataFile data : dataList) {
			String finalPath = reportPath + data.getDataKey();
			resultArray.append(data.getDataKey() + "\t" + data.getFileName() + "\t"
					+ ScreeningUtil.screen(finalPath + "/report.txt") + "\n");
		}
		FileTools.appendWrite(projectFile, resultArray.toString());
		return true;
	}

	/**
	 * Translate流程运行结束后的数据处理
	 * 
	 * @param reportPath
	 * @param dataKey
	 * @param appTitle
	 * @param projectFile
	 * @param projectId
	 * @param dataList
	 * @return
	 * @author lin
	 * @date 2016年4月19日下午5:28:16
	 */
	public boolean Translate(String reportPath, String dataKey, String appTitle, String projectFile, String projectId,
			List<DataFile> dataList) {
		// 1. 追加表头
		StringBuffer resultArray = new StringBuffer();
		resultArray.append(appTitle);
		// 2. 遍历数据列表
		for (DataFile data : dataList) {
			String resultPath = reportPath + data.getDataKey() + File.separatorChar + "result.txt";
			String result = FileTools.getFirstLine(resultPath);
			if (StringUtils.isNotBlank(result) && result.length() > 50) {
				result = result.substring(0, 50);
			}
			resultArray.append(data.getDataKey() + "\t" + data.getFileName() + "\t" + result + "\n");
		}
		FileTools.appendWrite(projectFile, resultArray.toString());
		return true;
	}

	/**
	 * 16S流程运行结束后的数据处理
	 * 
	 * @param reportPath
	 * @param dataKey
	 * @param appTitle
	 * @param projectFile
	 * @param projectId
	 * @param dataList
	 * @return
	 * @author lin
	 * @date 2016年4月28日上午11:21:01
	 */
	public boolean S16(String reportPath, String dataKey, String appTitle, String projectFile, String projectId,
			List<DataFile> dataList) {
		// 1. 追加表头
		StringBuffer resultArray = new StringBuffer();
		resultArray.append(appTitle);
		FileTools.appendWrite(projectFile, resultArray.toString());
		// 2. 遍历数据列表
		for (DataFile data : dataList) {
			String resultPath = reportPath + data.getDataKey() + File.separatorChar + "report.txt";
			if (new File(resultPath).exists()) {
				String line = FileTools.getFirstLine(resultPath);
				FileTools.appendWrite(projectFile, data.getDataKey() + "\t" + data.getFileName() + "\t" + line + "\n");
			} else {
				FileTools.appendWrite(projectFile, data.getDataKey() + "\t" + data.getFileName() + "\tno result\n");
			}
		}
		return true;
	}

	/**
	 * BRAF流程运行结束后的数据处理
	 * 
	 * @param reportPath
	 * @param dataKey
	 * @param appTitle
	 * @param projectFile
	 * @param projectId
	 * @param dataList
	 * @return
	 * @author MQ
	 */
	public boolean BRAF(String reportPath, String dataKey, String appTitle, String projectFile, String projectId,
			List<DataFile> dataList) {
		// 1. 追加表头
		StringBuffer resultArray = new StringBuffer();
		resultArray.append(appTitle);
		// 2. 遍历数据列表
		for (DataFile data : dataList) {
			String finalPath = reportPath + data.getDataKey() + File.separator + "report.txt.wz.1";
			String context = FileTools.readAppoint(finalPath).replace("<br />", ";");
			resultArray.append(data.getDataKey() + "\t" + data.getFileName() + "\t" + context + "\n");
		}
		FileTools.appendWrite(projectFile, resultArray.toString());
		return true;
	}

	/**
	 * UGT流程运行结束后的数据处理
	 * 
	 * @param reportPath
	 * @param dataKey
	 * @param appTitle
	 * @param projectFile
	 * @param projectId
	 * @param dataList
	 * @return
	 * @author lin
	 * @date 2016年3月25日下午4:28:44
	 */
	public boolean UGT(String reportPath, String dataKey, String appTitle, String projectFile, String projectId,
			List<DataFile> dataList) {
		// 1. 追加表头
		StringBuffer resultArray = new StringBuffer();
		resultArray.append(appTitle);
		// 2. 遍历数据列表
		for (DataFile data : dataList) {
			String finalPath = reportPath + data.getDataKey() + File.separator + "report.txt.wz.1";
			String context = FileTools.readAppoint(finalPath);
			if (context != null && context.contains("The type is ")) {
				context = context.split("<br />")[0].replace("The type is ", "");
			} else {
				context = "no result";
			}
			resultArray.append(data.getDataKey() + "\t" + data.getFileName() + "\t" + context + "\n");
		}
		FileTools.appendWrite(projectFile, resultArray.toString());
		return true;
	}

	/**
	 * DPD流程运行结束后的数据处理
	 * 
	 * @param reportPath
	 * @param dataKey
	 * @param appTitle
	 * @param projectFile
	 * @param projectId
	 * @param dataList
	 * @return
	 * @author lin
	 * @date 2016年3月25日下午4:26:00
	 */
	public boolean DPD(String reportPath, String dataKey, String appTitle, String projectFile, String projectId,
			List<DataFile> dataList) {
		// 1. 追加表头
		StringBuffer resultArray = new StringBuffer();
		resultArray.append(appTitle);
		// 2. 遍历数据列表
		for (DataFile data : dataList) {
			String finalPath = reportPath + data.getDataKey() + File.separator + "report.txt.wz.1";
			String context = FileTools.readAppoint(finalPath).replace("<br />", ";");
			resultArray.append(data.getDataKey() + "\t" + data.getFileName() + "\t" + context + "\n");
		}
		FileTools.appendWrite(projectFile, resultArray.toString());
		return true;
	}

	/**
	 * KRAS流程运行结束后的数据处理
	 * 
	 * @param reportPath
	 * @param dataKey
	 * @param appTitle
	 * @param projectFile
	 * @param projectId
	 * @param dataList
	 * @return
	 * @author lin
	 * @date 2016年3月22日下午4:31:03
	 */
	public boolean KRAS(String reportPath, String dataKey, String appTitle, String projectFile, String projectId,
			List<DataFile> dataList) {
		// 1. 追加表头
		StringBuffer resultArray = new StringBuffer();
		resultArray.append(appTitle);
		// 2. 遍历数据列表
		for (DataFile data : dataList) {
			String finalPath = reportPath + data.getDataKey();
			String first = FileTools.getFirstLine(finalPath + "/report.txt");
			String conclusion = FileTools.readAppoint(finalPath + "/report.txt.Report");
			resultArray.append(data.getDataKey() + "\t" + data.getFileName() + "\t" + first + "\t" + conclusion + "\n");
		}
		FileTools.appendWrite(projectFile, resultArray.toString());
		return true;
	}

	/**
	 * EGFR流程运行结束后的数据处理
	 * 
	 * @param reportPath
	 * @param dataKey
	 * @param appTitle
	 * @param projectFile
	 * @param projectId
	 * @param dataList
	 * @return
	 * @author lin
	 * @date 2016年3月10日下午3:03:25
	 */
	public boolean EGFR(String reportPath, String dataKey, String appTitle, String projectFile, String projectId,
			List<DataFile> dataList) {
		// 1. 追加表头
		StringBuffer resultArray = new StringBuffer();
		resultArray.append(appTitle);
		// 2. 遍历数据列表
		for (DataFile data : dataList) {
			String finalPath = reportPath + data.getDataKey();
			String first = FileTools.getFirstLine(finalPath + "/report.txt");
			String result = StringUtils.isBlank(first) ? "no result" : first.replace("Exon", "").trim();
			String re = FileTools.readAppoint(finalPath + "/report.txt.wz.Report").replace("<br />", "");
			resultArray.append(data.getDataKey() + "\t" + data.getFileName() + "\t" + result + "\t" + re + "\n");
		}
		FileTools.appendWrite(projectFile, resultArray.toString());
		return true;
	}

	/**
	 * MIB流程运行结束后的处理
	 * 
	 * @param reportPath
	 * @param dataKey
	 * @param appTitle
	 * @param projectFile
	 * @param projectId
	 * @param dataList
	 * @return
	 */
	public boolean MIB(String reportPath, String dataKey, String appTitle, String projectFile, String projectId,
			List<DataFile> dataList) {
		// 1. 追加表头
		StringBuffer resultArray = new StringBuffer();
		if (FileTools.countLines(projectFile) < 1) {
			resultArray.append(appTitle).append("\n");
		}
		resultArray.append(dataKey).append("\t");
		for (int i = 0; i < dataList.size(); i++) {
			resultArray.append(dataList.get(i).getFileName());
			if (i < dataList.size() - 1) {
				resultArray.append("&");
			}
		}
		String tablePath = reportPath + "/result/taxi.all.fastq.table";
		resultArray.append("\t");
		if (new File(tablePath).exists()) {
			List<String> list = FileTools.readLinestoString(reportPath + "result/taxi.all.fastq.table");
			if (!(list == null || list.isEmpty())) {
				for (int z = 1; z < list.size(); z++) {
					if (z == 1) {
						String[] line_z = list.get(z).split("\t");
						resultArray.append(FileTools.getArray(line_z, 0)).append("\t")
								.append(FileTools.getArray(line_z, 5)).append("\t")
								.append(FileTools.getArray(line_z, 6));
					}
				}
			} else {
				resultArray.append("运行结果异常").append("\t&nbsp;\t&nbsp;");
			}
		} else {
			resultArray.append("运行结果异常").append("\t&nbsp;\t&nbsp;");
		}
		FileTools.appendWrite(projectFile, resultArray.toString() + "\n");
		return true;
	}

	/**
	 * 乳腺癌流程运行结束后的报告处理
	 * 
	 * @param reportPath
	 * @param dataKey
	 * @param appTitle
	 * @param projectFile
	 * @param projectId
	 * @param dataList
	 * @return
	 */
	public boolean rocky(String reportPath, String dataKey, String appTitle, String projectFile, String projectId,
			List<DataFile> dataList) {
		return true;
	}

	/**
	 * split流程运行结束后的处理
	 * 
	 * @param reportPath
	 * @param dataKey
	 * @param appTitle
	 * @param projectFile
	 * @param projectId
	 * @param dataList
	 * @return
	 */
	public boolean split(String reportPath, String dataKey, String appTitle, String projectFile, String projectId,
			List<DataFile> dataList) {
		// 1. 追加表头
		StringBuffer resultArray = new StringBuffer();
		if (FileTools.countLines(projectFile) < 1) {
			resultArray.append(appTitle).append("\n");
		}
		resultArray.append(dataKey).append("\t");
		for (int i = 0; i < dataList.size(); i++) {
			resultArray.append(dataList.get(i).getFileName());
			if (i < dataList.size() - 1) {
				resultArray.append("&");
			}
		}
		resultArray.append("\t");
		File f = new File(reportPath + "result/statistic.xls");
		if (f.exists()) {
			List<String> list = FileTools.readLinestoString(reportPath + "result/statistic.xls");
			if (!(list == null || list.isEmpty())) {
				resultArray.append(FileTools.listIsNull(list, 0)).append("\t").append(FileTools.listIsNull(list, 1))
						.append("\t").append(FileTools.listIsNull(list, 2));
			}
		} else {
			resultArray.append("运行结果异常").append("\t&nbsp;\t&nbsp;");
		}
		FileTools.appendWrite(projectFile, resultArray.toString() + "\n");
		return true;
	}

	/**
	 * CMP/CMP_199/GDD流程运行结束后的处理
	 * 
	 * @param reportPath
	 * @param dataKey
	 * @param appTitle
	 * @param projectFile
	 * @param projectId
	 * @param dataList
	 * @return
	 */
	public boolean CMP(String reportPath, String dataKey, String appTitle, String projectFile, String projectId,
			List<DataFile> dataList) {
		// 1. 追加表头
		StringBuffer resultArray = new StringBuffer();
		if (FileTools.countLines(projectFile) < 1) {
			resultArray.append(appTitle).append("\n");
		}
		resultArray.append(dataKey).append("\t");
		for (int i = 0; i < dataList.size(); i++) {
			resultArray.append(dataList.get(i).getFileName());
			if (i < dataList.size() - 1) {
				resultArray.append("&");
			}
		}
		resultArray.append("\t");
		File f = new File(reportPath + "result/statistic.xls");
		if (f.exists()) {
			List<String> list = FileTools.readLinestoString(reportPath + "result/statistic.xls");
			if (!(list == null || list.isEmpty())) {
				resultArray.append(FileTools.listIsNull(list, 0)).append("\t").append(FileTools.listIsNull(list, 1))
						.append("\t").append(FileTools.listIsNull(list, 2)).append("\t");
			} else {
				resultArray.append("运行结果异常").append("\t&nbsp;\t&nbsp;\t");
			}
			File avgFile = new File(reportPath + "result/average.info");
			if (avgFile.exists()) {
				resultArray.append(FileTools.getFirstLine(reportPath + "/result/average.info"));
			} else {
				resultArray.append("&nbsp;");
			}
		} else {
			resultArray.append("运行结果异常").append("\t&nbsp;\t&nbsp;\t&nbsp;");
		}

		FileTools.appendWrite(projectFile, resultArray.toString() + "\n");
		return true;
	}

	/**
	 * PGS系列流程项目运行结束后的处理
	 * 
	 * @param appPath
	 * @param appName
	 * @param appTitle
	 * @param projectFile
	 * @param projectId
	 * @param proDataList
	 * @return
	 */
	public boolean PGS(String appPath, String appName, String appTitle, String projectFile, String projectId,
			List<DataFile> proDataList, String dataKey) {
		// 1. 追加表头
		StringBuffer resultArray = new StringBuffer();
		if (!new File(projectFile).exists()) {
			resultArray.append(appTitle);
		}
		StringBuffer sb = new StringBuffer();
		// 2. 遍历数据列表
		for (DataFile d : proDataList) {
			String filename = d.getFileName();
			String datakey = d.getDataKey();
			String anotherName = d.getAnotherName() == null ? "" : d.getAnotherName();

			// 3. 为项目PDF生成数据
			sb.append(datakey).append(",").append(CustomStringUtils.getBarcode(filename)).append(",")
					.append(anotherName).append(";");
			if (!datakey.equals(dataKey)) {
				continue;
			}
			String finalPath = appPath + datakey;
			String xls = FileTools.fileExist(finalPath, datakey + ".xls", "endsWith");
			if (xls.equals("")) {
				xls = FileTools.fileExist(finalPath, "no_enough_reads.xls", "endsWith");
			}
			String result[] = { "Error:未产生xls文件" };
			if (!xls.equals("")) {
				String r[] = FileTools.readFileToString(finalPath + "/" + xls).split("\n");
				if (r.length > 2) {
					result = FileTools.getArray(r, 2).split("\t");
				} else {
					result = FileTools.getLastLine(finalPath + "/" + xls).split("\t");
				}
			}
			resultArray.append(filename).append("\t").append(datakey).append("\t").append(anotherName).append("\t");
			if (result.length == 1) {
				resultArray.append(FileTools.getArray(result, 0)).append("\n");
			} else {
				int titleLength = appTitle.split("\t").length;
				for (int i = 0; i < titleLength - 3; i++) {
					resultArray.append(FileTools.getArray(result, i));
					if (i == titleLength - 4) {
						resultArray.append("\n");
					} else {
						resultArray.append("\t");
					}
				}
			}
		}
		if (FileTools.countLines(projectFile) == proDataList.size() + 1) {
			// 4.生成项目pdf
			try {
				PGSProjectPDF.createPDF(appPath, appName, 220, 800, sb.toString(), projectId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		FileTools.appendWrite(projectFile, resultArray.toString());
		return true;
	}

	/**
	 * HBV_SNP流程项目运行结束后的处理
	 * 
	 * @param appPath
	 * @param appName
	 * @param appTitle
	 * @param projectFile
	 * @param projectId
	 * @param proDataList
	 * @return
	 */
	public boolean HBV(String appPath, String appName, String appTitle, String projectFile, String projectId,
			List<DataFile> proDataList) {
		// 1. 追加表头
		StringBuffer resultArray = new StringBuffer();
		resultArray.append(appTitle);
		// 2. 遍历数据列表
		for (DataFile DataFile : proDataList) {
			String finalPath = appPath + DataFile.getDataKey();
			String result = FileTools.readAppoint(finalPath + "/SVG/type.txt");
			result = result.replace("Type: ", "");
			result = result.replace("<br />", "");
			String snpType = FileTools.readAppoint(finalPath + "/SVG/Report.txt");
			String type[] = snpType.split("<br />");
			String typeResult[] = new String[7];
			for (int j = 0; j < type.length; j++) {
				if (type[j].contains("TDF")) {
					if (type[j].startsWith("检测到")) {
						typeResult[0] = "不敏感\t";
					} else {
						typeResult[0] = "敏感\t";
					}
				}
				if (type[j].contains("LDT")) {
					if (type[j].startsWith("检测到")) {
						typeResult[1] = "不敏感\t";
					} else {
						typeResult[1] = "敏感\t";
					}
				}
				if (type[j].contains("ADV")) {
					if (type[j].startsWith("检测到")) {
						typeResult[2] = "不敏感\t";
					} else {
						typeResult[2] = "敏感\t";
					}
				}
				if (type[j].contains("LAM")) {
					if (type[j].startsWith("检测到")) {
						typeResult[3] = "不敏感\t";
					} else {
						typeResult[3] = "敏感\t";
					}
				}
				if (type[j].contains("FTC")) {
					if (type[j].startsWith("检测到")) {
						typeResult[4] = "不敏感\t";
					} else {
						typeResult[4] = "敏感\t";
					}
				}
				if (type[j].contains("ETV")) {
					if (type[j].startsWith("检测到")) {
						typeResult[5] = "不敏感\t";
					} else {
						typeResult[5] = "敏感\t";
					}
				}
				if (type[j].contains("Other")) {
					if (type[j].contains("Y")) {
						typeResult[6] = "有\t";
					} else {
						typeResult[6] = "无\t";
					}
				}
			}
			StringBuffer typeTotal = new StringBuffer();
			for (int j = 0; j < typeResult.length; j++) {
				typeTotal.append(typeResult[j]);
			}
			if (StringUtils.isBlank(result)) {
				typeTotal = new StringBuffer("测序失败，建议重测");
			}
			resultArray.append(DataFile.getDataKey()).append("\t").append(DataFile.getFileName()).append("\t")
					.append(result).append("\t").append(typeTotal.toString()).append("\n");
		}
		FileTools.appendWrite(projectFile, resultArray.toString());
		return true;
	}

	private static String[] HCVType = { "1b", "2a", "3a", "3b", "6a" };
	private static List<String> typeList = Arrays.asList(HCVType);

	/**
	 * HCV流程项目运行结束后的处理
	 * 
	 * @param appPath
	 * @param appName
	 * @param appTitle
	 * @param projectFile
	 * @param projectId
	 * @param proDataList
	 * @return
	 */
	public boolean HCV(String appPath, String appName, String appTitle, String projectFile, String projectId,
			List<DataFile> proDataList) {
		// 1. 追加表头
		StringBuffer resultArray = new StringBuffer();
		resultArray.append(appTitle);
		// 2. 遍历数据列表
		for (DataFile DataFile : proDataList) {
			String finalPath = appPath + "/" + DataFile.getDataKey() + "/Result.txt";
			String context = null;
			if (FileTools.checkPath(finalPath)) {
				context = FileTools.getLastLine(finalPath);
				if (context != null) {
					String c[] = context.split("\t");
					if (c.length > 4) {
						if (!typeList.contains(FileTools.getArray(c, 1))) {
							c[1] = "其他";
						}
						context = FileTools.getArray(c, 0) + "\t" + FileTools.getArray(c, 1) + "\t"
								+ FileTools.getArray(c, 2) + "\t" + FileTools.getArray(c, 3);
					}
				} else {
					context = " \t \t \t";
				}
			}
			resultArray.append(DataFile.getDataKey()).append("\t").append(context).append("\n");
		}
		FileTools.appendWrite(projectFile, resultArray.toString());
		return true;
	}

	/**
	 * oncogene 流程项目运行结束后的处理
	 * 
	 * @param appPath
	 * @param appName
	 * @param appTitle
	 * @param projectFile
	 * @param projectId
	 * @param proDataList
	 * @return
	 */
	public boolean oncogene(String appPath, String appName, String appTitle, String projectFile, String projectId,
			List<DataFile> proDataList) {
		// 1. 追加表头
		StringBuffer resultArray = new StringBuffer();
		resultArray.append(appTitle);
		// 2. 遍历数据列表
		for (DataFile data : proDataList) {
			String finalPath = appPath + data.getDataKey();
			String first = FileTools.getFirstLine(finalPath + "/report.txt");
			String result = CustomStringUtils.isEmpty(first) ? "no result"
					: first.replace("Exon", "").split("@")[0].trim();
			String re = FileTools.readAppoint(finalPath + "/report.txt.wz.Report").replace("<br />", "");
			String geneName = FileTools.getFirstLine(finalPath + "/Result/edit_dir/gene_name.txt");
			if (CustomStringUtils.isEmpty(geneName)) {
				geneName = "no result";
			}
			resultArray.append(data.getDataKey()).append("\t").append(data.getFileName()).append("\t").append(geneName)
					.append("\t").append(result).append("\t").append(re).append("\n");
		}
		FileTools.appendWrite(projectFile, resultArray.toString());
		return true;
	}
}
