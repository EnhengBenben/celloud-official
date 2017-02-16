package com.celloud.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.constants.FileFormat;
import com.celloud.constants.FileTitleType;

public class CheckFileTypeUtil {
	Logger log = LoggerFactory.getLogger(CheckFileTypeUtil.class);

	/**
	 * 验证上传文件的类型格式
	 * 
	 * @return
	 */
	public int checkFileType(String fileName, String dayPath) {
		String extName = "";
		if (fileName.lastIndexOf(".") > 0) {
			extName = fileName.substring(fileName.lastIndexOf("."));
		}
		if (fileName.toLowerCase().indexOf(".fastq") != -1 || fileName.toLowerCase().indexOf(".fq") != -1) {
			return FileFormat.FQ;
		}
		if (extName.equals(".tsv")) {
			return FileFormat.TSV;
		}
		if (".xls".equals(extName) || ".xlsx".equals(extName)) {
			return FileFormat.XLS;
		}
		String titleType = checkTitleType(fileName, dayPath);
		if (titleType.startsWith(FileTitleType.ABI)
				&& (".ab1".equals(extName.toLowerCase()) || ".abi".equals(extName.toLowerCase()))) {
			// 文件为峰图文件
			return FileFormat.FENGTU;
		}
		if (fileName.toLowerCase().indexOf(".fastq") == -1 && fileName.toLowerCase().indexOf(".fq") == -1
				&& ((".zip".equals(extName.toLowerCase()) && (titleType.startsWith(FileTitleType.ZIP)))
						|| (".gz".equals(extName.toLowerCase()) && (titleType.startsWith(FileTitleType.GZ))))) {
			// 文件为压缩文件
			return FileFormat.YASUO;
		}
		if (titleType.startsWith(FileTitleType.BAM) && extName.equals(".bam")) {
			// bam格式文件
			return FileFormat.BAM;
		}
		if (titleType.startsWith(FileTitleType.FASTA) && (checkFasta(fileName, dayPath)
				|| ".fa".equals(extName.toLowerCase()) || ".fasta".equals(extName.toLowerCase()))) {
			return FileFormat.FA;
		}
		return 0;
	}

	/**
	 * 检测上传文件是否为fasta文件
	 * 
	 * @return
	 */
	public boolean checkFasta(String fileName, String dayPath) {
		boolean isFasta = false;
		String path = dayPath + File.separator + fileName;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			String lineString = br.readLine();
			if (!"".equals(lineString) && ">".equals(lineString.substring(0, 1))) {
				isFasta = true;
			}
		} catch (FileNotFoundException e) {
			log.info("找不到文件：" + fileName);
			isFasta = false;
			return isFasta;
		} catch (IOException e) {
			log.error("文件读取异常");
			isFasta = false;
			return isFasta;
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
		return isFasta;
	}

	public boolean checkFastq(String fileName, String dayPath) {
		boolean isFastq = false;
		String path = dayPath + File.separator + fileName;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			int lineNum = 1;
			String lineString = "";
			boolean firstCheck = false;
			boolean secondCheck = false;
			while (lineNum < 15 && (lineString = br.readLine()) != null) {
				if (!"".equals(lineString) && lineNum % 4 == 1) {
					if ("@".equals(lineString.split("")[1])) {
						firstCheck = true;
					} else {
						firstCheck = false;
					}
				} else if (!"".equals(lineString) && lineNum % 4 == 3) {
					if ("@".equals(lineString.split("")[1])) {
						secondCheck = true;
					} else {
						secondCheck = false;
					}
				}
				lineNum++;
			}
			if (firstCheck == true && secondCheck == false) {
				isFastq = true;
			}
		} catch (FileNotFoundException e) {
			log.info("找不到文件：" + fileName);
			isFastq = false;
		} catch (IOException e) {
			log.error("文件读取异常");
			isFastq = false;
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
		return isFastq;
	}

	public String checkTitleType(String fileName, String dayPath) {
		String path = dayPath + File.separator + fileName;
		return getTypeByStream(path);
	}

	/**
	 * 根据文件流读取文件真实类型
	 * 
	 * @param is
	 * @return
	 */
	public String getTypeByStream(String src) {
		byte[] b = new byte[5];
		FileInputStream is = null;
		String type = null;
		try {
			is = new FileInputStream(src);
			int bytes = is.read(b, 0, b.length);
			if (bytes == 0) {
				log.info("读取文件头信息异常");
			} else {
				type = bytesToHexString(b).toUpperCase();
			}
		} catch (IOException e) {
			log.error("判断文件头格式时文件读取异常{}", e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
		}
		return type;
	}

	/**
	 * byte数组转换成16进制字符串
	 * 
	 * @param src
	 * @return
	 */
	public String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
}
