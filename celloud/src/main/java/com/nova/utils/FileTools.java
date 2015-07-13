package com.nova.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:文件操作工具类
 * @author lin
 * @date 2013-7-29 下午7:36:51
 */
public class FileTools {

	/**
	 * 向文件内追加内容
	 * 
	 * @param filePath
	 * @param writeContext
	 */
	public static void appendWrite(String filePath, String writeContext) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(filePath, true);
			fw.write(writeContext);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 创建文件
	 * 
	 * @param path
	 *            ： 路径格式若为：/home/down/test.txt，
	 *            若路径不存在，则生成home/down文件夹后生成test.txt文件
	 */
	public static void createFile(String path) {
		File file = new File(path);
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.out.println("文件创建异常：" + e);
		}
	}

	/**
	 * 检验该路径是否存在：适用于文件和文件夹
	 * 
	 * @param path
	 * @return： true，存在；false，不存在
	 */
	public static boolean checkPath(String path) {
		return new File(path).exists();
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtName(String fileName) {
		String extName = "";
		if (fileName.lastIndexOf(".") > 0) {
			if (fileName.toLowerCase().indexOf(".fastq.") > 0
					|| fileName.toLowerCase().indexOf(".fq.") > 0) {
				extName = fileName.substring(fileName.lastIndexOf(".fastq."));
			} else {
				extName = fileName.substring(fileName.lastIndexOf("."));
			}
		}
		return extName;
	}

	/**
	 * 获取文件大小
	 * 
	 * @param pathStr
	 * @return
	 */
	public static Long getFileSize(String pathStr) {
		Path path = Paths.get(pathStr);
		BasicFileAttributes attributes;
		try {
			attributes = Files.readAttributes(path, BasicFileAttributes.class);
			return attributes.size();
		} catch (IOException e) {
			System.out.println("读取不到文件" + pathStr);
			e.printStackTrace();
		}
		return 0L;
	}

	/**
	 * 文件下载方法
	 * 
	 * @param response
	 *            :HttpServletResponse
	 * @param filePath
	 *            ：带有路径的文件名，如 path/fileName.zip
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	public static void fileDownLoad(HttpServletResponse response,
			String filePath) {
		int endIndex = 0;
		if (filePath.indexOf("/") != -1) {
			endIndex = filePath.lastIndexOf("/");
		} else {
			endIndex = filePath.lastIndexOf("\\");
		}
		String newFileName = filePath.substring(endIndex + 1);
		File file = new File(filePath);
		response.addHeader("Content-Disposition", "attachment;filename="
				+ newFileName);
		response.setContentType("application/octet-stream");
		FileInputStream is = null;
		ServletOutputStream out = null;
		try {
			is = new FileInputStream(file);
			int length = is.available();
			byte[] content = new byte[length];
			is.read(content);
			out = response.getOutputStream();
			out.write(content);
			out.flush();
			response.setStatus(response.SC_OK);
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static byte[] getByte(File file) {
		byte[] bytes = null;
		if (file != null) {
			InputStream is = null;
			try {
				is = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			int length = (int) file.length();
			if (length > Integer.MAX_VALUE) {
				System.out.println("this file is max ");
				return new byte[0];
			}
			bytes = new byte[length];
			int offset = 0;
			int numRead = 0;
			try {
				while (offset < bytes.length
						&& (numRead = is.read(bytes, offset, bytes.length
								- offset)) >= 0) {
					offset += numRead;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 如果得到的字节长度和file实际的长度不一致就可能出错了
			if (offset < bytes.length) {
				System.out.println("file length is error");
				return new byte[0];
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bytes;
	}

	/**
	 * 检索文件夹下符合规则的文件名
	 * 
	 * @param folderPath
	 *            ：要检索的目标文件夹
	 * @param regulation
	 *            ：要匹配的字符串
	 * @param mate
	 *            ：匹配方式，支持 endWith , startWith , contains 三种，默认 contains
	 * @return
	 */
	public static List<String> fileSearch(String folderPath, String regulation,
			String mate) {
		File dir = new File(folderPath);
		File file[] = dir.listFiles();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < file.length; i++) {
			if ("endWith".equals(mate)) {
				if (file[i].getName().endsWith(regulation)) {
					list.add(file[i].getName());
				}
			} else if ("startWith".equals(mate)) {
				if (file[i].getName().startsWith(regulation)) {
					list.add(file[i].getName());
				}
			} else {
				if (file[i].getName().contains(regulation)) {
					list.add(file[i].getName());
				}
			}
		}
		return list;
	}

	/**
	 * 获取文件的第一行
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFirstLine(String filePath) {
		return getLineByNum(filePath, 1);
	}

	/**
	 * 获取文件内指定行的内容
	 * 
	 * @param filePath
	 * @param num
	 * @return
	 */
	public static String getLineByNum(String filePath, int num) {
		if (!new File(filePath).exists()) {
			return null;
		}
		FileReader in = null;
		try {
			in = new FileReader(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		LineNumberReader reader = new LineNumberReader(in);
		String line = null;
		int i = 0;
		try {
			while ((line = reader.readLine()) != null) {
				i++;
				if (i == num) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			reader.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}
	/**
	 * 取特定的行，从第 from 行到第 to 行
	 * 
	 * @param filePath
	 *            ：文件路径
	 * @param from
	 *            ：起始行（包涵）
	 * @param to
	 *            ：结束行（包涵）
	 * @return
	 */
	public static String getLimitLines(String filePath, Integer from, Integer to) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(filePath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int count = 0;
		String line = null;
		StringBuffer sb = new StringBuffer();
		try {
			while ((line = br.readLine()) != null) {
				count++;
				if (count <= to && count >= from) {
					sb.append(line).append("\n");
				}
				if (count > to) {
					break;
				}
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
		return sb.toString();
	}
}