package com.nova.tools.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import com.itextpdf.text.DocumentException;
import com.nova.tools.constant.AppNameIDConstant;
import com.nova.tools.constant.Mod;
import com.nova.tools.itext.AB1_PDF;
import com.nova.tools.itext.NIPTPDF;
import com.nova.tools.itext.NIPTProjectPDF;
import com.nova.tools.itext.PGS_PDF;
import com.nova.tools.utils.FileTools;
import com.nova.tools.utils.GanymedSSH;
import com.nova.tools.utils.PropertiesUtils;
import com.nova.tools.utils.ScreeningUtil;
import com.nova.tools.utils.XMLUtil;

/**
 * @Description:运行App的具体实现
 * @author lin
 * @date 2013-7-30 上午10:22:28
 */
public class RunAppServiceImpl {

	private static String dataPath = PropertiesUtils.dataPath;
	private static String datalist = PropertiesUtils.datalist;

	private static String treePerl = PropertiesUtils.tree;
	private static String SNP_multiple = PropertiesUtils.SNP_multiple;
	private static String HCV = PropertiesUtils.HCV;
	private static String PGS = PropertiesUtils.PGS;
	private static String HBV_SNP2_perl = PropertiesUtils.HBV_SNP2;
	private static String gDNA_perl = PropertiesUtils.gDNA;
	private static String EGFR_perl = PropertiesUtils.EGFR;
	private static String TB_perl = PropertiesUtils.TB;

	private static String host158 = XMLUtil.machines.get("158").get(Mod.HOST);
	private static String pwd = XMLUtil.machines.get("158").get(Mod.PWD);
	private static String userName = XMLUtil.machines.get("158").get(
			Mod.USERNAME);

	private static String MalBac_perl = PropertiesUtils.MalBac;
	private static String gDNA_MR_perl = PropertiesUtils.gDNA_MR;
	private static String MDA_MR_perl = PropertiesUtils.MDA_MR;
	private static String MDA_HR_perl = PropertiesUtils.MDA_HR_perl;
	private static String SurePlex_perl = PropertiesUtils.SurePlex;
	private static String SurePlex_HR_perl = PropertiesUtils.Sureplex_HR;
	private static String KRAS_perl = PropertiesUtils.KRAS;
	private static String translate_perl = PropertiesUtils.Translate;
	private static String _16S_perl = PropertiesUtils._16S;
	private static String NIPT_perl = PropertiesUtils.NIPT;
	private static String gDNA_MR_v1 = PropertiesUtils.gDNA_MR_v1;
	private static String MDA_MR_v1 = PropertiesUtils.MDA_MR_v1;
	private static String MDA_HR_v1 = PropertiesUtils.MDA_HR_v1;
	private static String SurePlex_v1 = PropertiesUtils.SurePlex_v1;
	private static String MalBac_v1 = PropertiesUtils.MalBac_v1;
	private static String TBINH_perl = PropertiesUtils.tbinh;
	private static String DPD_perl = PropertiesUtils.DPD;
	private static String BRAF_perl = PropertiesUtils.BRAF;
	private static String UGT_perl = PropertiesUtils.UGT;
	private static String CMP_perl = PropertiesUtils.CMP;
	private static String CMP199_perl = PropertiesUtils.CMP199;
	private static String GDD_perl = PropertiesUtils.GDD;
	private static String split_perl = PropertiesUtils.split;

	public void VSP(String appPath, String projectId, String dataKeyList) {
		String dataArray[] = dataKeyList.split(";");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dataArray.length; i++) {
			String[] dataDetail = dataArray[i].split(",");
			sb.append(getArray(dataDetail, 1) + "\t" + getArray(dataDetail, 2)
					+ "\n");
		}

		String projectFile = appPath + "/" + projectId + "/" + projectId
				+ ".txt";
		FileTools.createFile(projectFile);
		FileTools.appendWrite(projectFile, "dataKey\tFile_Name\n");
		for (int i = 0; i < dataArray.length; i++) {
			String[] dataDetail = dataArray[i].split(",");
			File result = new File(appPath + "/" + getArray(dataDetail, 0));
			String dataListFile = result + "/datalist.txt";
			FileTools.createFile(dataListFile);
			FileTools.appendWrite(dataListFile, sb.toString());
			for (int j = 0; j < dataArray.length; j++) {
				String[] src = dataArray[j].split(",");
				try {
					FileUtils.copyFileToDirectory(
							new File(dataPath + getArray(src, 1)), result);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileTools.appendWrite(projectFile, getArray(dataDetail, 0) + "\t"
					+ getArray(dataDetail, 2) + "\n");
		}
	}


	/**
	 * 运行MIB
	 * 
	 * @param outPath
	 * @param projectId
	 * @param dataKeyList
	 * @param appId
	 * @param appName
	 * @param userId
	 * @param dataInfos
	 * @param company
	 * @param user
	 */
	// TODO
	public void MIB(String command, String taskId, String outPath,
			String projectId, String dataKey, String fileName, String appId,
			String appName, String userId, String dataInfos, String company,
			String user, String dept) {
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
        ssh.sshSubmit(false);
	}

	/**
	 * 运行split
	 * 
	 * @param outPath
	 * @param projectId
	 * @param dataKeyList
	 * @param appId
	 * @param appName
	 * @param userId
	 * @param dataInfos
	 * @param company
	 * @param user
	 */
    public void split(String outPath, String projectId, String dataKeyList,
			String appId, String appName, String userId, String dataInfos,
			String company, String user, String dept) {
		String dataListFile = formatDataKeyListToSplit(dataKeyList);
		String command = split_perl + " " + dataListFile + " " + outPath
                + " ProjectID" + projectId + " &>" + outPath + "/" + projectId
                + "/log ";
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
        ssh.sshSubmit(false);
	}

	/**
	 * 运行CMP
	 * 
	 * @param outPath
	 * @param projectId
	 * @param dataKeyList
	 * @param appId
	 * @param appName
	 * @param userId
	 * @param dataInfos
	 * @param company
	 * @param user
	 */
	public void runCMP(String outPath, String projectId, String dataKeyList,
			String appId, String appName, String userId, String dataInfos,
			String company, String user, String dept) {
		String dataListFile = formatDataKeyList(dataKeyList);
		String command = null;
		if (AppNameIDConstant.CMP.equals(appId)) {
            command = CMP_perl;
		} else if (AppNameIDConstant.CMP_199.equals(appId)) {
            command = CMP199_perl;
		} else if (AppNameIDConstant.GDD.equals(appId)) {
            command = GDD_perl;
		}
        command += " " + dataListFile + " " + outPath + " ProjectID"
                + projectId + " &>" + outPath + "/" + projectId + "/log ";
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
        ssh.sshSubmit(false);
	}

	public void _16S(String appPath, String projectId, String dataKeyList) {
		String[] data = dataKeyList.split(",");
		String dataKeyPath = appPath + "/" + getArray(data, 0);
		FileTools.createDir(dataKeyPath);
		String command = _16S_perl + " --input " + dataPath + getArray(data, 1)
				+ "  --outdir " + dataKeyPath;
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		boolean state = ssh.sshSubmit(true);
		if (state) {
			String projectFile = appPath + "/" + projectId + "/" + projectId
					+ ".txt";
			FileTools.createFile(projectFile);
			FileTools.appendWrite(projectFile, "dataKey\t文件名称\t文件大小\t序列条数\n");
			if (new File(dataKeyPath + "/report.txt").exists()) {
				String line = FileTools.getFirstLine(dataKeyPath
						+ "/report.txt");
				FileTools.appendWrite(projectFile, getArray(data, 0) + "\t"
						+ getArray(data, 2).split(";")[0] + "\t" + line);
			}
		}
	}

	/**
	 * @param appPath
	 * @param projectId
	 * @param dataKeyList
	 */
	public void translate(String appPath, String projectId, String dataKeyList) {
		String[] data = dataKeyList.split(",");
		String resultPath = appPath + "/" + getArray(data, 0) + "/"
				+ getArray(data, 0) + ".txt";
		FileTools.createFile(resultPath);
		String command = translate_perl + " " + dataPath + getArray(data, 1)
				+ " --output " + resultPath;
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		boolean state = ssh.sshSubmit(true);
		if (state) {
			// 创建项目结果文件
			String projectFile = appPath + "/" + projectId + "/" + projectId
					+ ".txt";
			FileTools.createFile(projectFile);
			String seq = null;
			try {
				seq = FileUtils.readFileToString(new File(resultPath));
				if (seq != null && seq.length() > 50) {
					seq = seq.substring(0, 50);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 追加表头
			FileTools.appendWrite(projectFile, "dataKey\t文件名称\t结果\n");
			FileTools.appendWrite(projectFile, getArray(data, 0) + "\t"
					+ getArray(data, 2).split(";")[0] + "\t" + seq + "\n");
		}
	}

	/**
	 * 运行 HCV
	 * 
	 * @param resultPath
	 * @param dataKeyList
	 * @return
	 */
    public void runHCV(String appPath, String projectId, String dataKeyList) {
        // 创建要运行的文件列表文件
        String dataListFile = dealDataKeyListContainFileName(dataKeyList);
        String command = HCV + dataListFile + " " + appPath + "/ ProjectID"
                + projectId + " &>" + appPath + "/" + projectId + "/log ";
        GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
        ssh.sshSubmit(false);
    }

	/**
	 * 运行 HBV_Tree
	 * 
	 * @param projectPath
	 * @param dataKeyList
	 * @return
	 * @throws TranscoderException
	 * @throws IOException
	 */
	public void runTree(String basePath, String userId, String appId,
			String projectId, String dataKeyList) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyListContainFileName(dataKeyList);
		String command = treePerl + " " + dataListFile + " " + projectId + " "
				+ basePath + "/" + userId + "/" + appId + "/" + projectId + "/";
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		ssh.sshSubmit(true);
	}

	/**
	 * 运行PGS项目
	 * 
	 * @param basePath
	 * @param projectId
	 * @param dataKeyList
	 */
	public void runPGSProject(String basePath, String projectId,
			String dataKeyList, String appName) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyListContainFileName(dataKeyList);
		String command = PGS + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId + " &>" + basePath + "ProjectID"
				+ projectId + ".log";
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		ssh.sshSubmit(false);
	}

	/**
	 * 运行 NIPT 项目
	 * 
	 * @param basePath
	 * @param projectId
	 * @param dataKeyList
	 * @param appName
	 */
	public void runNIPTProject(String basePath, String projectId,
			String dataKeyList, String appName) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyListContainFileName(dataKeyList);
		String command = NIPT_perl + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId;
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		boolean state = ssh.sshSubmit(true);
		StringBuffer resultArray = new StringBuffer();
		if (state) {
			String dataArray[] = dataKeyList.split(";");
			// 创建项目结果文件
			String projectFile = basePath + projectId + "/" + projectId
					+ ".txt";
			FileTools.createFile(projectFile);
			// 追加表头
			resultArray
					.append("dataName\tdataKey\tAnotherName\tChr13\tChr18\tChr21\n");
			StringBuffer keys = new StringBuffer();
			for (int i = 0; i < dataArray.length; i++) {
				String[] dataDetail = dataArray[i].split(",");
				keys.append(getArray(dataDetail, 0)).append(";");
				try {
					NIPTPDF.createPDF(basePath + getArray(dataDetail, 0)
							+ "/Result", appName, 230, 800);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String finalPath = basePath + getArray(dataDetail, 0)
						+ "/Result/Zscore.txt";
				if (new File(finalPath).exists()) {
					resultArray.append(getArray(dataDetail, 2)).append("\t")
							.append(getArray(dataDetail, 0)).append("\t")
							.append(getArray(dataDetail, 3)).append("\t")
							.append(FileTools.getColumns(finalPath))
							.append("\n");
				}
			}
			try {
				NIPTProjectPDF.createPDF(basePath, appName, 230, 800,
						keys.toString(), projectId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			FileTools.appendWrite(projectFile, resultArray.toString());
		}
	}

	/**
	 * gDNA项目
	 * 
	 * @param basePath
	 * @param projectId
	 * @param dataKeyList
	 */
	public void rungDNAProject(String basePath, String projectId,
			String dataKeyList, String appName) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyListContainFileName(dataKeyList);
		String command = gDNA_perl + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId + " &>" + basePath + "ProjectID"
				+ projectId + ".log";
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		ssh.sshSubmit(false);
	}

	public void runMDA_HRProject(String basePath, String projectId,
			String dataKeyList, String appName) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyListContainFileName(dataKeyList);
		String command = MDA_HR_perl + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId + " &>" + basePath + "ProjectID"
				+ projectId + ".log";
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		ssh.sshSubmit(false);
	}

	public void runSurePlexProject(String basePath, String projectId,
			String dataKeyList, String appName) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyListContainFileName(dataKeyList);
		String command = SurePlex_perl + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId + " &>" + basePath + "ProjectID"
				+ projectId + ".log";
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		ssh.sshSubmit(false);
	}

	public void runSurePlex_HRProject(String basePath, String projectId,
			String dataKeyList, String appName) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyListContainFileName(dataKeyList);
		String command = SurePlex_HR_perl + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId + " &>" + basePath + "ProjectID"
				+ projectId + ".log";
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		ssh.sshSubmit(false);
	}

	public void rungDNA_MRProject(String basePath, String projectId,
			String dataKeyList, String appName) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyListContainFileName(dataKeyList);
		String command = gDNA_MR_perl + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId + " &>" + basePath + "ProjectID"
				+ projectId + ".log";
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		ssh.sshSubmit(false);
	}

	public void rungDNA_MR_v1Project(String basePath, String projectId,
			String dataKeyList, String appName) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyListContainFileName(dataKeyList);
		String command = gDNA_MR_v1 + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId;
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		boolean state = ssh.sshSubmit(true);
		StringBuffer resultArray = new StringBuffer();
		if (state) {
			String dataArray[] = dataKeyList.split(";");
			// 创建项目结果文件
			String projectFile = basePath + projectId + "/" + projectId
					+ ".txt";
			FileTools.createFile(projectFile);
			// 追加表头
			resultArray
					.append("dataName\tdataKey\tTotal_Reads\tMap_Reads\tMap_Ratio(%)\tDuplicate\tGC_Count(%)\t*SD\t\n");
			for (int i = 0; i < dataArray.length; i++) {
				String[] dataDetail = dataArray[i].split(",");
				String finalPath = basePath + getArray(dataDetail, 0);
				try {
					PGS_PDF.createPDF(finalPath, appName,
							getBarcode(getArray(dataDetail, 2)),
							getArray(dataDetail, 3), getArray(dataDetail, 0),
							200, 800);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String fileName = FileTools.fileExist(finalPath, ".xls",
						"endsWith");
				String result[] = null;
				try {
					String r[] = FileUtils.readFileToString(
							new File(finalPath + "/" + fileName)).split("\n");
					if (r.length > 2) {
						result = getArray(r, 2).split("\t");
					} else {
						result = FileTools.getLastLine(
								finalPath + "/" + fileName).split("\t");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (result.length == 1) {
					resultArray.append(getArray(dataDetail, 2) + "\t"
							+ getArray(dataDetail, 0) + "\t"
							+ getArray(result, 0) + "\n");
				} else {
					resultArray.append(getArray(dataDetail, 2) + "\t"
							+ getArray(dataDetail, 0) + "\t"
							+ getArray(result, 0) + "\t" + getArray(result, 1)
							+ "\t" + getArray(result, 2) + "\t"
							+ getArray(result, 3) + "\t" + getArray(result, 4)
							+ "\t" + getArray(result, 5) + "\n");
				}
			}
			FileTools.appendWrite(projectFile, resultArray.toString());
		}
	}

	public void runMDA_MR_v1Project(String basePath, String projectId,
			String dataKeyList, String appName) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyListContainFileName(dataKeyList);
		String command = MDA_MR_v1 + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId;
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		boolean state = ssh.sshSubmit(true);
		StringBuffer resultArray = new StringBuffer();
		if (state) {
			String dataArray[] = dataKeyList.split(";");
			// 创建项目结果文件
			String projectFile = basePath + projectId + "/" + projectId
					+ ".txt";
			FileTools.createFile(projectFile);
			// 追加表头
			resultArray
					.append("dataName\tdataKey\tTotal_Reads\tMap_Reads\tMap_Ratio(%)\tDuplicate\tGC_Count(%)\t*SD\n");
			for (int i = 0; i < dataArray.length; i++) {
				String[] dataDetail = dataArray[i].split(",");
				String finalPath = basePath + getArray(dataDetail, 0);
				try {
					PGS_PDF.createPDF(finalPath, appName,
							getBarcode(getArray(dataDetail, 2)),
							getArray(dataDetail, 3), getArray(dataDetail, 0),
							200, 800);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String fileName = FileTools.fileExist(finalPath, ".xls",
						"endsWith");
				String result[] = null;
				try {
					String r[] = FileUtils.readFileToString(
							new File(finalPath + "/" + fileName)).split("\n");
					if (r.length > 2) {
						result = getArray(r, 2).split("\t");
					} else {
						result = FileTools.getLastLine(
								finalPath + "/" + fileName).split("\t");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (result.length == 1) {
					resultArray.append(getArray(dataDetail, 2) + "\t"
							+ getArray(dataDetail, 0) + "\t"
							+ getArray(result, 0) + "\n");
				} else {
					resultArray.append(getArray(dataDetail, 2) + "\t"
							+ getArray(dataDetail, 0) + "\t"
							+ getArray(result, 0) + "\t" + getArray(result, 1)
							+ "\t" + getArray(result, 2) + "\t"
							+ getArray(result, 3) + "\t" + getArray(result, 4)
							+ "\t" + getArray(result, 5) + "\n");
				}
			}
			FileTools.appendWrite(projectFile, resultArray.toString());
		}
	}

	public void runMDA_HR_v1Project(String basePath, String projectId,
			String dataKeyList, String appName) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyListContainFileName(dataKeyList);
		String command = MDA_HR_v1 + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId;
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		boolean state = ssh.sshSubmit(true);
		StringBuffer resultArray = new StringBuffer();
		if (state) {
			String dataArray[] = dataKeyList.split(";");
			// 创建项目结果文件
			String projectFile = basePath + projectId + "/" + projectId
					+ ".txt";
			FileTools.createFile(projectFile);
			// 追加表头
			resultArray
					.append("dataName\tdataKey\tTotal_Reads\tMap_Reads\tMap_Ratio(%)\tDuplicate\tGC_Count(%)\n");
			for (int i = 0; i < dataArray.length; i++) {
				String[] dataDetail = dataArray[i].split(",");
				String finalPath = basePath + getArray(dataDetail, 0);
				try {
					PGS_PDF.createPDF(finalPath, appName,
							getBarcode(getArray(dataDetail, 2)),
							getArray(dataDetail, 3), getArray(dataDetail, 0),
							200, 800);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String fileName = FileTools.fileExist(finalPath, ".xls",
						"endsWith");
				String result[] = null;
				try {
					String r[] = FileUtils.readFileToString(
							new File(finalPath + "/" + fileName)).split("\n");
					if (r.length > 2) {
						result = getArray(r, 2).split("\t");
					} else {
						result = FileTools.getLastLine(
								finalPath + "/" + fileName).split("\t");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (result.length == 1) {
					resultArray.append(getArray(dataDetail, 2) + "\t"
							+ getArray(dataDetail, 0) + "\t"
							+ getArray(result, 0) + "\n");
				} else {
					resultArray.append(getArray(dataDetail, 2) + "\t"
							+ getArray(dataDetail, 0) + "\t"
							+ getArray(result, 0) + "\t" + getArray(result, 1)
							+ "\t" + getArray(result, 2) + "\t"
							+ getArray(result, 3) + "\t" + getArray(result, 4)
							+ "\n");
				}
			}
			FileTools.appendWrite(projectFile, resultArray.toString());
		}
	}

	public void runSurePlex_v1Project(String basePath, String projectId,
			String dataKeyList, String appName) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyListContainFileName(dataKeyList);
		String command = SurePlex_v1 + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId;
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		boolean state = ssh.sshSubmit(true);
		StringBuffer resultArray = new StringBuffer();
		if (state) {
			String dataArray[] = dataKeyList.split(";");
			// 创建项目结果文件
			String projectFile = basePath + projectId + "/" + projectId
					+ ".txt";
			FileTools.createFile(projectFile);
			// 追加表头
			resultArray
					.append("dataName\tdataKey\tTotal_Reads\tMap_Reads\tMap_Ratio(%)\tDuplicate\tGC_Count(%)\n");
			for (int i = 0; i < dataArray.length; i++) {
				String[] dataDetail = dataArray[i].split(",");
				String finalPath = basePath + getArray(dataDetail, 0);
				try {
					PGS_PDF.createPDF(finalPath, appName,
							getBarcode(getArray(dataDetail, 2)),
							getArray(dataDetail, 3), getArray(dataDetail, 0),
							230, 800);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String fileName = FileTools.fileExist(finalPath, ".xls",
						"endsWith");
				String result[] = FileTools.getLastLine(
						finalPath + "/" + fileName).split("\t");
				if (result.length == 1) {
					resultArray.append(getArray(dataDetail, 2) + "\t"
							+ getArray(dataDetail, 0) + "\t"
							+ getArray(result, 0) + "\n");
				} else {
					resultArray.append(getArray(dataDetail, 2) + "\t"
							+ getArray(dataDetail, 0) + "\t"
							+ getArray(result, 0) + "\t" + getArray(result, 1)
							+ "\t" + getArray(result, 2) + "\t"
							+ getArray(result, 3) + "\t" + getArray(result, 4)
							+ "\n");
				}
			}
			FileTools.appendWrite(projectFile, resultArray.toString());
		}
	}

	public void runMalBac_v1Project(String basePath, String projectId,
			String dataKeyList, String appName) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyListContainFileName(dataKeyList);
		String command = MalBac_v1 + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId;
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		boolean state = ssh.sshSubmit(true);
		StringBuffer resultArray = new StringBuffer();
		if (state) {
			String dataArray[] = dataKeyList.split(";");
			// 创建项目结果文件
			String projectFile = basePath + projectId + "/" + projectId
					+ ".txt";
			FileTools.createFile(projectFile);
			// 追加表头
			resultArray
					.append("dataName\tdataKey\tTotal_Reads\tDuplicate\tMap_Reads\tMap_Ratio(%)\tGC_Count(%)\n");
			for (int i = 0; i < dataArray.length; i++) {
				String[] dataDetail = dataArray[i].split(",");
				String finalPath = basePath + getArray(dataDetail, 0);
				try {
					PGS_PDF.createPDF(finalPath, appName,
							getBarcode(getArray(dataDetail, 2)),
							getArray(dataDetail, 3), getArray(dataDetail, 0),
							230, 800);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String fileName = FileTools.fileExist(finalPath, ".xls",
						"endsWith");
				String result[] = FileTools.getLastLine(
						finalPath + "/" + fileName).split("\t");
				if (result.length == 1) {
					resultArray.append(getArray(dataDetail, 2) + "\t"
							+ getArray(dataDetail, 0) + "\t"
							+ getArray(result, 0) + "\n");
				} else {
					resultArray.append(getArray(dataDetail, 2) + "\t"
							+ getArray(dataDetail, 0) + "\t"
							+ getArray(result, 0) + "\t" + getArray(result, 1)
							+ "\t" + getArray(result, 2) + "\t"
							+ getArray(result, 3) + "\t" + getArray(result, 4)
							+ "\n");
				}
			}
			FileTools.appendWrite(projectFile, resultArray.toString());
		}
	}

	public void runMDA_MRProject(String basePath, String projectId,
			String dataKeyList, String appName) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyListContainFileName(dataKeyList);
		String command = MDA_MR_perl + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId + " &>" + basePath + "ProjectID"
				+ projectId + ".log";
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		ssh.sshSubmit(false);
	}

	public void runMalBacProject(String basePath, String projectId,
			String dataKeyList, String appName) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyListContainFileName(dataKeyList);
		String command = MalBac_perl + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId + " &>" + basePath + "ProjectID"
				+ projectId + ".log";
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		ssh.sshSubmit(false);
	}

	/**
	 * 执行 EGFR 项目
	 * 
	 * @param basePath
	 *            ：basePath + "/" + userId + "/" + appId + "/"
	 * @param userId
	 * @param appId
	 * @param projectId
	 * @param dataKeyList
	 */
	public void runEGFRProject(String basePath, String projectId,
			String dataKeyList, String appName) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyList(dataKeyList);
		String command = EGFR_perl + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId;
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		boolean state = ssh.sshSubmit(true);
		StringBuffer resultArray = new StringBuffer();
		if (state) {
			String dataArray[] = dataKeyList.split(";");
			// 创建项目结果文件
			String projectFile = basePath + projectId + "/" + projectId
					+ ".txt";
			FileTools.createFile(projectFile);
			// 追加表头
			FileTools.appendWrite(projectFile,
					"dataID\t文件名称\tEGFR exon number\t结论\n");
			for (int i = 0; i < dataArray.length; i++) {
				String[] dataDetail = dataArray[i].split(",");
				String finalPath = basePath + getArray(dataDetail, 0);
				try {
					AB1_PDF.createPDF(finalPath + "/", getArray(dataDetail, 2),
							appName, 230, 800);
				} catch (DocumentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				String first = FileTools
						.getFirstLine(finalPath + "/report.txt");
				String re = FileTools.readAppoint(
						finalPath + "/report.txt.wz.Report").replace("<br />",
						"");
				String result = (first == null || first.equals("")) ? "no result"
						: first.replace("Exon	", "");
				FileTools.appendWrite(projectFile, getArray(dataDetail, 0)
						+ "\t" + getArray(dataDetail, 2) + "\t" + result + "\t"
						+ re + "\n");
				resultArray.append(getArray(dataDetail, 0) + ",");
			}
		}
	}

	/**
	 * 执行 TB 项目
	 * 
	 * @param basePath
	 *            ：basePath + "/" + userId + "/" + appId + "/"
	 * @param userId
	 * @param appId
	 * @param projectId
	 * @param dataKeyList
	 */
	public void runTBProject(String basePath, String projectId,
			String dataKeyList, String appName) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyList(dataKeyList);
		String command = TB_perl + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId;
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		boolean state = ssh.sshSubmit(true);
		StringBuffer resultArray = new StringBuffer();
		if (state) {
			String dataArray[] = dataKeyList.split(";");
			// 创建项目结果文件
			String projectFile = basePath + projectId + "/" + projectId
					+ ".txt";
			FileTools.createFile(projectFile);
			// 追加表头
			FileTools.appendWrite(projectFile,
					"dataID\t文件名称\tPosition\tamino acid\n");
			for (int i = 0; i < dataArray.length; i++) {
				String[] dataDetail = dataArray[i].split(",");
				String finalPath = basePath + getArray(dataDetail, 0);
				try {
					AB1_PDF.createPDF(finalPath + "/", getArray(dataDetail, 2),
							appName, 190, 800);
				} catch (DocumentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				FileTools.appendWrite(
						projectFile,
						getArray(dataDetail, 0)
								+ "\t"
								+ getArray(dataDetail, 2)
								+ "\t"
								+ ScreeningUtil.screen(finalPath
										+ "/report.txt") + "\n");
				resultArray.append(getArray(dataDetail, 0) + ",");
			}
		}
	}

	/**
	 * 执行 TBINH 项目
	 * 
	 * @param basePath
	 *            ：basePath + "/" + userId + "/" + appId + "/"
	 * @param projectId
	 * @param dataKeyList
	 */
	public void runTBINHProject(String basePath, String projectId,
			String dataKeyList) {
		String dataListFile = dealDataKeyList(dataKeyList);
		String command = TBINH_perl + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId;
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		boolean state = ssh.sshSubmit(true);
		StringBuffer resultArray = new StringBuffer();
		if (state) {
			String dataArray[] = dataKeyList.split(";");
			String projectFile = basePath + projectId + "/" + projectId
					+ ".txt";
			FileTools.createFile(projectFile);
			FileTools.appendWrite(projectFile,
					"dataID\tSample name\tGene\tPosition\n");
			for (int i = 0; i < dataArray.length; i++) {
				String[] dataDetail = dataArray[i].split(",");
				String finalPath = basePath + getArray(dataDetail, 0);
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
					// 构建统计文件
					String countTxt = basePath + "/" + gene + ".txt";
					if (!new File(countTxt).exists()) {
						FileTools.createFile(countTxt);
						FileTools.appendWrite(countTxt, "0\n0");
					}
					int countSNP = Integer.parseInt(FileTools
							.getFirstLine(countTxt));
					int countWild = Integer.parseInt(FileTools.getLineByNum(
							countTxt, 2));
					String isWild = FileTools.getLineByNum(report, 2);// 根据第二行判断是否是野生型
					if (isWild.startsWith("SNP")) {// 不是野生型
						countSNP++;
					} else {// 是野生型
						countWild++;
					}
					new File(countTxt).delete();
					FileTools.createFile(countTxt);
					FileTools
							.appendWrite(countTxt, countSNP + "\n" + countWild);
				}
				boolean isKatG = gene.equals("KatG");
				String position = ScreeningUtil.unitColumn(finalPath
						+ "/report.txt.1", isKatG);
				FileTools.appendWrite(projectFile, getArray(dataDetail, 0)
						+ "\t" + getArray(dataDetail, 2) + "\t" + gene + "\t"
						+ position + "\n");
				resultArray.append(getArray(dataDetail, 0) + ",");
			}
		}
	}

	/**
	 * 执行 KRAS 项目
	 * 
	 * @param basePath
	 *            ：basePath + "/" + userId + "/" + appId + "/"
	 * @param userId
	 * @param appId
	 * @param projectId
	 * @param dataKeyList
	 */
	public void runKRASProject(String basePath, String projectId,
			String dataKeyList, String appName) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyList(dataKeyList);
		String command = KRAS_perl + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId;
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		boolean state = ssh.sshSubmit(true);
		StringBuffer resultArray = new StringBuffer();
		if (state) {
			String dataArray[] = dataKeyList.split(";");
			// 创建项目结果文件
			String projectFile = basePath + projectId + "/" + projectId
					+ ".txt";
			FileTools.createFile(projectFile);
			// 追加表头
			FileTools.appendWrite(projectFile,
					"dataID\t文件名称\tKRAS exon number\tPosition\tamino acid\n");
			for (int i = 0; i < dataArray.length; i++) {
				String[] dataDetail = dataArray[i].split(",");
				String finalPath = basePath + getArray(dataDetail, 0);
				try {
					AB1_PDF.createPDF(finalPath + "/", getArray(dataDetail, 2),
							appName, 230, 800);
				} catch (DocumentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				String first = FileTools
						.getFirstLine(finalPath + "/report.txt");
				String result = first == null ? "no result" : first.replace(
						"EGFR exon number is ", "");
				FileTools.appendWrite(projectFile, getArray(dataDetail, 0)
						+ "\t" + getArray(dataDetail, 2) + "\t" + result + "\t"
						+ ScreeningUtil.screen(finalPath + "/report.txt")
						+ "\n");
				resultArray.append(getArray(dataDetail, 0) + ",");
			}
		}
	}

	/**
	 * 执行 HBV_SNP 项目
	 * 
	 * @param basePath
	 *            ：basePath + "/" + userId + "/" + appId + "/"
	 * @param userId
	 * @param appId
	 * @param projectId
	 * @param dataKeyList
	 */
	public void runSNPProject(String basePath, String projectId,
			String dataKeyList) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyList(dataKeyList);
		String command = "perl " + SNP_multiple + " " + dataListFile + " "
				+ basePath + "  ProjectID" + projectId;
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		boolean state = ssh.sshSubmit(true);
		StringBuffer resultArray = new StringBuffer();
		if (state) {
			String dataArray[] = dataKeyList.split(";");
			// 创建 SNP 项目结果文件
			String projectFile = basePath + projectId + "/" + projectId
					+ ".txt";
			FileTools.createFile(projectFile);
			// 追加表头
			FileTools
					.appendWrite(projectFile,
							"dataID\t文件名称\t类型\t拉米夫定LAM\t阿德福韦ADV\t恩替卡韦ETV\t替比夫定LDT\t其他突变\t\n");
			for (int i = 0; i < dataArray.length; i++) {
				String[] dataDetail = dataArray[i].split(",");
				String finalPath = basePath + getArray(dataDetail, 0);
				String result = FileTools.readAppoint(finalPath
						+ "/SVG/type.txt");
				result = result.replace("Type:", "");
				result = result.replace("<br />", "");
				String snpType = FileTools.readAppoint(finalPath
						+ "/SVG/Report.txt");
				String type[] = snpType.split("<br />");
				String typeResult[] = new String[5];
				for (int j = 0; j < type.length; j++) {
					if (type[j].contains("LAM")) {
						if (type[j].startsWith("检测到")) {
							typeResult[0] = "不敏感\t";
						} else {
							typeResult[0] = "敏感\t";
						}
					}
					if (type[j].contains("ADV")) {
						if (type[j].startsWith("检测到")) {
							typeResult[1] = "不敏感\t";
						} else {
							typeResult[1] = "敏感\t";
						}
					}
					if (type[j].contains("ETV")) {
						if (type[j].startsWith("检测到")) {
							typeResult[2] = "不敏感\t";
						} else {
							typeResult[2] = "敏感\t";
						}
					}
					if (type[j].contains("LDT")) {
						if (type[j].startsWith("检测到")) {
							typeResult[3] = "不敏感\t";
						} else {
							typeResult[3] = "敏感\t";
						}
					}
					if (type[j].contains("Other")) {
						if (type[j].contains("Y")) {
							typeResult[4] = "有\t";
						} else {
							typeResult[4] = "无\t";
						}
					}
				}
				StringBuffer typeTotal = new StringBuffer();
				for (int j = 0; j < typeResult.length; j++) {
					typeTotal.append(typeResult[j]);
				}
				FileTools.appendWrite(projectFile, getArray(dataDetail, 0)
						+ "\t" + getArray(dataDetail, 2) + "\t" + result + "\t"
						+ typeTotal.toString() + "\n");
				resultArray.append(getArray(dataDetail, 0) + ",");
			}
		}
	}

	/**
	 * DPD项目
	 * 
	 * @param basePath
	 *            :basePath + "/" + userId + "/" + appId + "/"
	 * @param projectId
	 * @param dataKeyList
	 * @param uploadPath
	 *            ：basePath
	 */
	public void runDPDProject(String basePath, String projectId,
			String dataKeyList, String uploadPath) {
		String dataListFile = dealDataKeyList(dataKeyList);
		String command = DPD_perl + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId;
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		boolean state = ssh.sshSubmit(true);
		if (state) {
			String dataArray[] = dataKeyList.split(";");
			String projectFile = basePath + projectId + "/" + projectId
					+ ".txt";
			FileTools.createFile(projectFile);
			FileTools.appendWrite(projectFile, "DataKey\t文件名称\t突变类型\n");
			for (int i = 0; i < dataArray.length; i++) {
				String[] dataDetail = dataArray[i].split(",");
				String finalPath = basePath + getArray(dataDetail, 0)
						+ "/report.txt.wz.1";
				String context = FileTools.readAppoint(finalPath).replace(
						"<br />", ";");
				FileTools.appendWrite(projectFile, getArray(dataDetail, 0)
						+ "\t" + getArray(dataDetail, 2) + "\t" + context
						+ "\n");
			}
		}
	}

	/**
	 * BRAF项目
	 * 
	 * @param basePath
	 *            :basePath + "/" + userId + "/" + appId + "/"
	 * @param projectId
	 * @param dataKeyList
	 * @param uploadPath
	 *            ：basePath
	 */
	public void runBRAFProject(String basePath, String projectId,
			String dataKeyList, String uploadPath) {
		String dataListFile = dealDataKeyList(dataKeyList);
		String command = BRAF_perl + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId;
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		boolean state = ssh.sshSubmit(true);
		if (state) {
			String dataArray[] = dataKeyList.split(";");
			String projectFile = basePath + projectId + "/" + projectId
					+ ".txt";
			FileTools.createFile(projectFile);
			FileTools.appendWrite(projectFile, "DataKey\t文件名称\t突变类型\n");
			for (int i = 0; i < dataArray.length; i++) {
				String[] dataDetail = dataArray[i].split(",");
				String finalPath = basePath + getArray(dataDetail, 0)
						+ "/report.txt.wz.1";
				String context = FileTools.readAppoint(finalPath).replace(
						"<br />", ";");
				FileTools.appendWrite(projectFile, getArray(dataDetail, 0)
						+ "\t" + getArray(dataDetail, 2) + "\t" + context
						+ "\n");
			}
		}
	}

	/**
	 * UGT项目
	 * 
	 * @param basePath
	 * @param projectId
	 * @param dataKeyList
	 * @param uploadPath
	 */
	public void runUGTProject(String basePath, String projectId,
			String dataKeyList, String uploadPath) {
		String dataListFile = dealDataKeyList(dataKeyList);
		String command = UGT_perl + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId;
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		boolean state = ssh.sshSubmit(true);
		if (state) {
			String dataArray[] = dataKeyList.split(";");
			String projectFile = basePath + projectId + "/" + projectId
					+ ".txt";
			FileTools.createFile(projectFile);
			FileTools.appendWrite(projectFile, "DataKey\t文件名称\t突变类型\n");
			for (int i = 0; i < dataArray.length; i++) {
				String[] dataDetail = dataArray[i].split(",");
				String finalPath = basePath + getArray(dataDetail, 0)
						+ "/report.txt.wz.1";
				String context = FileTools.readAppoint(finalPath);
				if (context != null && context.contains("The type is ")) {
					context = context.split("<br />")[0].replace(
							"The type is ", "");
				} else {
					context = "no result";
				}
				FileTools.appendWrite(projectFile, getArray(dataDetail, 0)
						+ "\t" + getArray(dataDetail, 2) + "\t" + context
						+ "\n");
			}
		}
	}

	/**
	 * HBV_SNP2项目
	 * 
	 * @param basePath
	 *            :basePath + "/" + userId + "/" + appId + "/"
	 * @param projectId
	 * @param dataKeyList
	 * @param uploadPath
	 *            ：basePath
	 */
	public void runHBV_SNP2Project(String basePath, String projectId,
			String dataKeyList, String uploadPath) {
		// 创建要运行的文件列表文件
		String dataListFile = dealDataKeyList(dataKeyList);
		String command = HBV_SNP2_perl + " " + dataListFile + " " + basePath
				+ " ProjectID" + projectId + " &>" + basePath + "ProjectID"
				+ projectId + ".log";
		GanymedSSH ssh = new GanymedSSH(host158, userName, pwd, command);
		ssh.sshSubmit(false);
	}

	// 以下是通用方法

	/**
	 * 将 dataKeyList 封装成 dataListFile 文件，包含文件名
	 * 
	 * @param dataKeyList
	 * @return
	 */
	private String dealDataKeyListContainFileName(String dataKeyList) {
		StringBuffer sb = new StringBuffer();
		String dataListFile = datalist + new Date().getTime() + "_"
				+ new Double(Math.random() * 1000).intValue() + ".txt";
		FileTools.createFile(dataListFile);
		String dataArray[] = dataKeyList.split(";");
		for (int i = 0; i < dataArray.length; i++) {
			String[] dataDetail = dataArray[i].split(",");
			sb.append(dataPath + getArray(dataDetail, 1) + "\t"
					+ getArray(dataDetail, 2) + "\n");
		}
		FileTools.appendWrite(dataListFile, sb.toString());
		return dataListFile;
	}

	/**
	 * 将 dataKeyList 封装成 dataListFile 文件
	 * 
	 * @param dataKeyList
	 * @return
	 */
	private String dealDataKeyList(String dataKeyList) {
		StringBuffer sb = new StringBuffer();
		String dataListFile = datalist + new Date().getTime() + "_"
				+ new Double(Math.random() * 1000).intValue() + ".txt";
		FileTools.createFile(dataListFile);
		String dataArray[] = dataKeyList.split(";");
		for (int i = 0; i < dataArray.length; i++) {
			String[] detail = dataArray[i].split(",");
			sb.append(dataPath + detail[1] + "\n");
		}
		FileTools.appendWrite(dataListFile, sb.toString());
		return dataListFile;
	}

	/**
	 * 将dataKeyList格式化成CMP需要的文件
	 * 
	 * @param dataKeyList
	 * @return
	 */
	private String formatDataKeyList(String dataKeyList) {
		StringBuffer sb = new StringBuffer();
		String dataListFile = datalist + new Date().getTime() + "_"
				+ new Double(Math.random() * 1000).intValue() + ".txt";
		FileTools.createFile(dataListFile);
		String dataArray[] = dataKeyList.split(";");
		for (int i = 0; i < dataArray.length; i = i + 2) {
			String[] detail1 = dataArray[i].split(",");
			String[] detail2 = dataArray[i + 1].split(",");
			sb.append(dataPath + detail1[1] + "\t" + dataPath + detail2[1]
					+ "\n");
		}
		FileTools.appendWrite(dataListFile, sb.toString());
		return dataListFile;
	}

	/**
	 * 将dataKeyList格式化成split需要的文件
	 * 
	 * @param dataKeyList
	 * @return
	 */
	private String formatDataKeyListToSplit(String dataKeyList) {
		StringBuffer sb = new StringBuffer();
		String dataListFile = datalist + new Date().getTime() + "_"
				+ new Double(Math.random() * 1000).intValue() + ".txt";
		FileTools.createFile(dataListFile);
		String dataArray[] = dataKeyList.split(";");
		for (int i = 0; i < dataArray.length; i = i + 3) {
			String detail1 = dataArray[i].split(",")[1];
			String detail2 = dataArray[i + 1].split(",")[1];
			String detail3 = dataArray[i + 2].split(",")[1];
			String endData = "";
			String d1 = "";
			String d2 = "";
			if (FileTools.getExt(detail1).equals(".lis")) {
				endData = detail1;
				d1 = detail2;
				d2 = detail3;
			} else if (FileTools.getExt(detail2).equals(".lis")) {
				endData = detail2;
				d1 = detail1;
				d2 = detail3;
			} else if (FileTools.getExt(detail3).equals(".lis")) {
				endData = detail3;
				d1 = detail1;
				d2 = detail2;
			}
			sb.append(dataPath).append(d1).append("\t").append(dataPath)
					.append(d2).append("\t").append(dataPath).append(endData)
					.append("\n");
		}
		FileTools.appendWrite(dataListFile, sb.toString());
		return dataListFile;
	}

	private static String getArray(String[] n, int num) {
		return n == null ? null : (n.length > num ? n[num] : null);
	}

	private static String getBarcode(String fileName) {
		String[] s = fileName.split("_");
		if (s.length > 2) {
			fileName = s[0] + "_" + s[1];
		}
		return fileName;
	}
}