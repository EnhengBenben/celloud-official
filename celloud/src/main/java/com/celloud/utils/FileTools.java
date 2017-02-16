package com.celloud.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @Description:文件操作工具类
 * @author lin
 * @date 2013-7-29 下午7:36:51
 */
public class FileTools {
	private static Logger log = Logger.getLogger(FileTools.class);
	
	/**
	 * @Description:文件转list
	 * @param file
	 * @return
	 * @author lin
	 * @date 2017年2月8日 下午2:22:30
	 */
	public static List<String> filetoList(File file) {
		if (!file.exists()) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println(file.getAbsolutePath() + "文件不存在");
		}
		if (br == null)
			return null;
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				list.add(line);
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
		return list;
	}

	/**
	 * 文件重命名
	 * 
	 * @param path
	 * @param oldName
	 * @param newName
	 * @return
	 * @author lin
	 * @date 2016年4月8日下午3:58:48
	 */
	public static boolean renameFile(String path, String oldName, String newName) {
		if (!oldName.equals(newName)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
			return mvFile(path, oldName, path, newName);
		} else {
			log.error("新文件名和旧文件名相同");
			return false;
		}
	}

	/**
	 * 移动文件
	 * 
	 * @param oldPath
	 * @param oldName
	 * @param newPath
	 * @param newName
	 * @return
	 * @author lin
	 * @date 2016年4月8日下午4:15:12
	 */
	public static boolean mvFile(String oldPath, String oldName, String newPath, String newName) {
		File oldFile = new File(oldPath + File.separator + oldName);
		if (!oldFile.exists()) {
			log.error("旧文件{}不存在" + oldFile.getAbsolutePath());
			return false;
		}
		File newFile = new File(newPath + File.separator + newName);
		if (newFile.exists()) {
			log.error("新文件{}已存在" + newFile.getAbsolutePath());
			return false;
		}
		oldFile.renameTo(newFile);
		return true;
	}

	/**
	 * 获取文件锁
	 * 
	 * @param file
	 * @return
	 */
	public static FileLock getFileLock(File file) {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "rw");
		} catch (FileNotFoundException e2) {
			log.error(file.getAbsoluteFile() + "文件不存在");
		}
		if (raf == null)
			return null;
		FileChannel fc = raf.getChannel();
		FileLock fl = null;
		while (true) {
			try {
				fl = fc.tryLock();
				if (fl != null) {
					break;
				}
			} catch (Exception e) {
				log.error("获取文件锁失败");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				log.error("获取文件锁失败，睡眠一秒钟异常：" + e1);
			}
		}
		return fl;
	}

	/**
	 * 读取报告
	 * 
	 * @param sourceFile
	 * @return
	 * @throws IOException
	 */
	public static String readAppoint(String sourceFile) {
		String context = null;
		try {
			context = FileUtils.readFileToString(new File(sourceFile), "GBK");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return context == null ? "" : context.replaceAll("\n", "<br />");
	}

	/**
	 * 获取文件的最后一行
	 * 
	 * @note 该方法只适用小文件
	 * @param filePath
	 * @return
	 */
	public static String getLastLine(String filePath) {
		FileReader in = null;
		try {
			in = new FileReader(filePath);
		} catch (FileNotFoundException e) {
			log.error(filePath + "文件不存在");
		}
		if (in == null)
			return null;
		LineNumberReader reader = new LineNumberReader(in);
		String s = null;
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				if (!"".equals(line.trim())) {
					s = line;
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
		return s;
	}

	/**
	 * 判断文件夹下是否有符合规则的文件，有则返回文件名，无则返回 ""
	 * 
	 * @param folderPath
	 *            ：要查询的文件夹
	 * @param regulation
	 *            ：要匹配的字符串
	 * @param mate
	 *            ：匹配方式，支持 endsWith , startsWith , contains 三种，默认 contains
	 * @return
	 */
	public static String fileExist(String folderPath, String regulation, String mate) {
		File dir = new File(folderPath);
		File file[] = dir.listFiles();
		if (file != null) {
			for (int i = 0; i < file.length; i++) {
				if ("endsWith".equals(mate)) {
					if (file[i].getName().toLowerCase().endsWith(regulation.toLowerCase())) {
						return file[i].getName();
					}
				} else if ("startsWith".equals(mate)) {
					if (file[i].getName().toLowerCase().startsWith(regulation.toLowerCase())) {
						return file[i].getName();
					}
				} else {
					if (file[i].getName().toLowerCase().contains(regulation.toLowerCase())) {
						return file[i].getName();
					}
				}
			}
		}
		return "";
	}

	/**
	 * 对datakeylist进行排序
	 * 
	 * @param dataKeyList
	 * @return
	 */
	public static String dataListSort(String dataKeyList) {
		String[] dataKey = dataKeyList.split(";");
		Map<String, String> map = new HashMap<String, String>();
		String[] array = new String[dataKey.length];
		for (int i = 0; i < dataKey.length; i++) {
			String data = dataKey[i];
			String d[] = data.split(",");
			map.put(d[2] + d[0], data);
			array[i] = d[2] + d[0];
		}
		Arrays.sort(array);
		dataKeyList = "";
		for (String s : array) {
			dataKeyList += map.get(s) + ";";
		}
		return dataKeyList;
	}

	/**
	 * 向文件内追加内容
	 * 
	 * @param filePath
	 * @param writeContext
	 */
	public static void appendWrite(String filePath, String writeContext) {
		if (!new File(filePath).exists()) {
			createFile(filePath);
		}
		FileWriter fw = null;
		try {
			fw = new FileWriter(filePath, true);
			fw.write(writeContext);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
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
	 * 			@return： true，存在；false，不存在
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
			if (fileName.toLowerCase().indexOf(".fastq.") > 0) {
				extName = fileName.substring(fileName.lastIndexOf(".fastq."));
			} else if (fileName.toLowerCase().indexOf(".fq.") > 0) {
				extName = fileName.substring(fileName.lastIndexOf(".fq."));
			} else if (fileName.toLowerCase().endsWith(".tar.gz")) {
				extName = fileName.substring(fileName.lastIndexOf(".tar.gz"));
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
	public static void fileDownLoad(HttpServletResponse response, String filePath) {
		int endIndex = 0;
		if (filePath.indexOf("/") != -1) {
			endIndex = filePath.lastIndexOf("/");
		} else {
			endIndex = filePath.lastIndexOf("\\");
		}
		String newFileName = filePath.substring(endIndex + 1);
		File file = new File(filePath);
		response.addHeader("Content-Disposition", "attachment;filename=" + newFileName);
        response.addHeader("Content-Length", file.length() + "");
		response.setContentType("application/octet-stream");
		FileInputStream is = null;
		ServletOutputStream out = null;
		try {
			is = new FileInputStream(file);
            byte[] content = new byte[1024];
            int len = -1;
            while ((len = is.read(content)) != -1) {
                out = response.getOutputStream();
                out.write(content, 0, len);
                out.flush();
            }
			response.setStatus(response.SC_OK);
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    /**
     * 文件下载方法, 文件名自定义
     * 
     * @param response
     *            :HttpServletResponse
     * @param filePath
     *            ：带有路径的文件名，如 path/fileName.zip
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    @SuppressWarnings("static-access")
    public static void fileDownLoad(HttpServletResponse response, String filePath, String customerFileName)
            throws UnsupportedEncodingException {
        File file = new File(filePath);
        if (StringUtils.isBlank(customerFileName)) {
            customerFileName = "未知名称";
        }
        response.addHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(customerFileName, "UTF-8"));
        response.addHeader("Content-Length", file.length() + "");
        response.setContentType("application/octet-stream");
        FileInputStream is = null;
        ServletOutputStream out = null;
        try {
            is = new FileInputStream(file);
            byte[] content = new byte[1024];
            int len = -1;
            while ((len = is.read(content)) != -1) {
                out = response.getOutputStream();
                out.write(content, 0, len);
                out.flush();
            }
            response.setStatus(response.SC_OK);
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
                if (is != null)
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
				while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
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
	public static List<String> fileSearch(String folderPath, String regulation, String mate) {
		File dir = new File(folderPath);
		File file[] = dir.listFiles();
		List<String> list = new ArrayList<String>();
		if (file == null) {
			return list;
		}
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
			log.error(filePath + "文件不存在");
		}
		if (in == null)
			return null;
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
	 * 获取文件内指定行的内容
	 * 
	 * @param filePath
	 * @param num
	 * @return
	 */
	public static List<String> getLineByNum(String filePath, int start, int end) {
		if (!new File(filePath).exists()) {
			return null;
		}
		FileReader in = null;
		try {
			in = new FileReader(filePath);
		} catch (FileNotFoundException e) {
			log.error(filePath + "文件不存在");
		}
		if (in == null)
			return null;
		LineNumberReader reader = new LineNumberReader(in);
		String line = null;
		List<String> list = new ArrayList<String>();
		int i = 0;
		try {
			while ((line = reader.readLine()) != null) {
				i++;
				if (i >= start && i <= end) {
					list.add(line);
					if (i == end) {
						break;
					}
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
		return list;
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
			log.error(filePath + "文件不存在");
		}
		if (br == null)
			return null;
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

	/**
	 * 按行读取文件
	 */
	public static List<String> readLinestoString(String path) {
		List<String> list = new ArrayList<String>();
		try {
			list = FileUtils.readLines(new File(path), "GBK");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static String getArray(String[] n, int num) {
		return n == null ? null : (n.length > num ? n[num] : null);
	}

	public static String listIsNull(List<String> list, int num) {
		String list_num = "";
		if (list != null && list.size() > num) {
			list_num = list.get(num);
		}
		return list_num;
	}

	public static String getExt(String fileName) {
		String extName = fileName.substring(fileName.lastIndexOf("."));
		return extName;
	}

	/**
	 * 应用于split
	 * 
	 * @param dataKeyList
	 * @return
	 */
	public static String dataListSortNoEnd(String dataKeyList) {
		String[] dataKey = dataKeyList.split(";");
		Map<String, String> map = new HashMap<String, String>();
		String[] array = new String[dataKey.length - 1];
		String endData = "";
		int number = 0;
		for (int i = 0; i < dataKey.length; i++) {
			String data = dataKey[i];
			String d[] = data.split(",");
			if (FileTools.getExt(d[2]).equals(".txt") || FileTools.getExt(d[2]).equals(".lis")) {
				endData = data;
			} else {
				map.put(d[2] + d[0], data);
				array[number] = d[2] + d[0];
				number++;
			}
		}
		Arrays.sort(array);
		dataKeyList = "";
		for (String s : array) {
			dataKeyList += map.get(s) + ";";
		}
		dataKeyList += endData + ";";
		return dataKeyList;
	}

	/**
	 * 统计文件行数
	 * 
	 * @param filePath
	 * @return
	 */
	public static int countLines(String filePath) {
		File f = new File(filePath);
		return f.exists() ? countLines(f) : 0;
	}

	/**
	 * 统计文件行数
	 * 
	 * @param file
	 * @return
	 */
	public static int countLines(File file) {
		if (!file.exists()) {
			return 0;
		}
		BufferedReader br = null;
		int count = 0;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			log.error(file.getAbsolutePath() + "文件不存在");
		}
		if (br == null)
			return 0;
		try {
			while (br.readLine() != null) {
				count++;
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
		return count;
	}

	/**
	 * 遍历文件夹，返回其下所有文件列表（不包含子文件夹）
	 * 
	 * @param folderPath
	 * @return
	 */
	public static HashSet<String> getFiles(String folderPath) {
		if (!folderPath.endsWith("/")) {
			folderPath = folderPath + "/";
		}
		File dir = new File(folderPath);
		File file[] = dir.listFiles();
		if (file == null || file.length == 0) {
			return null;
		}
		HashSet<String> set = new HashSet<String>();
		for (int i = 0; i < file.length; i++) {
			if (new File(folderPath + file[i].getName()).isFile()) {
				set.add(file[i].getName());
			}
		}
		return set;
	}

	/**
	 * 调用了 FileUtils.readFileToString 消除了其抛出的异常
	 * 
	 * @param path
	 * @return
	 */
	public static String readFileToString(String path) {
		File f = new File(path);
		if (!f.exists()) {
			return null;
		}
		String context = null;
		try {
			context = FileUtils.readFileToString(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return context;
	}

	public static boolean nioTransferCopy(File source, File target) {
		FileChannel in = null;
		FileChannel out = null;
		FileInputStream inStream = null;
		FileOutputStream outStream = null;
		boolean result = true;
		try {
			inStream = new FileInputStream(source);
			outStream = new FileOutputStream(target);
			in = inStream.getChannel();
			out = outStream.getChannel();
			in.transferTo(0, in.size(), out);
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
				if (in != null) {
					in.close();
				}
				if (outStream != null) {
					outStream.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		mvFile("/Users/lin/xxx", "t.sql", "/Users/lin", "sql.txt");
	}
}
