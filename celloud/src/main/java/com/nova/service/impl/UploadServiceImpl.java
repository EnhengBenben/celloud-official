package com.nova.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebService;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.nova.constants.FileFormat;
import com.nova.sdo.Client;
import com.nova.sdo.Data;
import com.nova.sdo.User;
import com.nova.service.IUploadService;
import com.nova.utils.CheckFileTypeUtil;
import com.nova.utils.DataUtil;
import com.nova.utils.DictionaryUtils;
import com.nova.utils.FileTools;
import com.nova.utils.PerlUtils;
import com.nova.utils.PropertiesUtil;
import com.nova.utils.SQLUtils;
import com.nova.utils.UncompressFileGZIP;

@WebService(endpointInterface = "com.nova.service.IUploadService")
public class UploadServiceImpl implements IUploadService {
	Logger log = Logger.getLogger(UploadServiceImpl.class);
	private String path = PropertiesUtil.bigFilePath;
	private CheckFileTypeUtil checkFileType = new CheckFileTypeUtil();
	private SQLUtils sql = new SQLUtils();

	@Override
	public String login(final String username, String pwd) {
		log.info("用户：" + username + "尝试登陆");
		User user = sql.login(username, pwd);
		if (user == null) {
			log.info("用户：" + username + "登陆失败");
			return 0 + "";
		} else {
			log.info("用户：" + username + "登陆成功");
			String uuid = UUID.randomUUID().toString();
			Integer userId = user.getUserId();
			sql.addUUID(userId, uuid);
			new Runnable() {
				public void run() {
					sql.addLog(username);
				}
			}.run();
			return userId + "," + uuid;
		}
	}

	@Override
	public long getSize(int id) {
		return sql.getSize(id);
	}

	@Override
	public long getNumber(int id) {
		return sql.getMyOwnDataNum(id);
	}

	@Override
	public String init(Integer id, String fileName) {
		List<String> dataKeyList = sql.getAllDataKey();
		String dataKey = DataUtil.getNewDataKey();
		while (dataKeyList.contains(dataKey)) {
			dataKey = DataUtil.getNewDataKey();
		}
		Data data = new Data();
		data.setUserId(id);
		// 只允许字母和数字
		String regEx = "[^\\w\\.\\_\\-\u4e00-\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(fileName);
		data.setFileName(m.replaceAll("").trim());
		String newName = dataKey + FileTools.getExtName(fileName);
		data.setDataKey(dataKey);
		data.setPath(path + newName);
		sql.addDataInfo(data);
		log.info("为用户：" + id + "返回" + newName);
		return newName;
	}

	@Override
	public boolean saveFile(String dataKey, byte[] buffer, int length,
			long position, int blocks) {
		String x = "/share/data/x/" + dataKey + ".txt";
		if (!new File(x).exists()) {
			FileTools.createFile(x);
		}
		FileTools.appendWrite(x, "dataKey:" + dataKey + "\tlength:" + length
				+ "\tposition:" + position + "\n");
		String filePath = path + dataKey;
		File file = new File(filePath);
		if (!file.exists()) {
			FileTools.createFile(filePath);
		}
		boolean isTrue = true;
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "rw");
			raf.seek(position);
			if (buffer.length == length) {
				raf.write(buffer);
			} else {
				byte[] temp = new byte[length];
				System.arraycopy(buffer, 0, temp, 0, length);
				raf.write(temp);
			}
		} catch (FileNotFoundException e) {
			isTrue = false;
			System.out.println(dataKey + "FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			isTrue = false;
			System.out.println(dataKey + "IOException");
			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					isTrue = false;
					System.out.println(dataKey + "close IOException");
					e.printStackTrace();
				}
			}
		}
		return isTrue;
	}

	public static void renameFile(String oldname, String newname) {
		if (!oldname.equals(newname)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(oldname);
			File newfile = new File(newname);
			if (!oldfile.exists()) {
				return;// 重命名文件不存在
			}
			if (newfile.exists())// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				System.out.println(newname + "已经存在！");
			else {
				oldfile.renameTo(newfile);
			}
		} else {
			System.out.println("新文件名和旧文件名相同...");
		}
	}

	@Override
	public boolean checkMD5(String dataKey, String md5, boolean isUnZip) {
		String x = "/share/data/x/" + dataKey + ".txt";
		if (!new File(x).exists()) {
			FileTools.createFile(x);
		}
		FileTools.appendWrite(x, "dataKey:" + dataKey + "\t校验md5");
		String key = dataKey.substring(0, dataKey.indexOf("."));
		String absPath = path + dataKey;// 文件的绝对路径
		File file = new File(absPath);
		if (!file.exists()) {
			return false;
		}
		if (isUnZip) {
			long start = new Date().getTime();
			String absPath2 = absPath + ".gz";
			renameFile(absPath, absPath2);
			UncompressFileGZIP.doUncompressFile(absPath2);
			long end = new Date().getTime();
			System.out.println("解压缩" + absPath2 + "用时：" + (end - start));
		}
		FileInputStream fis = null;
		boolean isTrue = true;
		try {
			long start = new Date().getTime();
			fis = new FileInputStream(file);
			String md52 = DigestUtils.md5Hex(fis);
			long end = new Date().getTime();
			System.out.println("计算" + dataKey + "md5用时：" + (end - start));
			isTrue = md5.equals(md52) ? true : false;
		} catch (FileNotFoundException e) {
			isTrue = false;
			e.printStackTrace();
		} catch (IOException e) {
			isTrue = false;
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					isTrue = false;
					e.printStackTrace();
				}
			}
		}
		if (isTrue) {
			Data data = new Data();
			data.setSize(FileTools.getFileSize(absPath));
			int fileFormat = checkFileType.checkFileType(dataKey);
			data.setFileFormat(fileFormat);
			if (fileFormat == FileFormat.BAM) {
				StringBuffer command = new StringBuffer();
				String outPath = System.getProperty("user.dir").replace("bin",
						"webapps/celloud")
						+ "/temp/" + dataKey;
				String perlPath = System.getProperty("user.dir").replace("bin",
						"webapps/celloud")
						+ "/plugins/getAliases.pl";
				command.append("perl ").append(perlPath).append(" ")
						.append(absPath).append(" ").append(outPath);
				PerlUtils.excutePerl(command.toString());
				data.setAnotherName(FileTools.getFirstLine(outPath));
				new File(outPath).delete();
			}
			data.setDataKey(key);
			sql.updateData(data);
			log.info("文件：" + dataKey + "上传成功");
		} else {
			log.info("文件：" + dataKey + "上传失败");
		}
		log.info(dataKey + "  is  " + isTrue);
		return isTrue;
	}

	public static String getMd5ByFile(File file) throws FileNotFoundException {
		String value = null;
		FileInputStream in = new FileInputStream(file);
		try {
			MappedByteBuffer byteBuffer = in.getChannel().map(
					FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	@Override
	public long getLength(String dataKey) {
		Data data = sql
				.getDataByKey(dataKey.substring(0, dataKey.indexOf(".")));
		return (long) data.getBlocks();
	}

	@Override
	public String getVersion() {
		log.info("获取最新的版本号");
		Client client = sql.getClient();
		return client.getVersion();
	}

	@Override
	public byte[] getClient() {
		log.info("获取客户端");
		String courseFile = null;
		try {
			courseFile = new File("").getCanonicalPath().replace("bin",
					"webapps");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Client client = sql.getClient();
		String path = courseFile + "/celloud/client/" + client.getName();
		System.out.println("client path:" + path);
		byte[] bytes = null;
		try {
			bytes = FileTools.getByte(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}

	@Override
	public boolean deco(String dictionary, String dataKey) {
		// 将字典解析到map中
		System.out.println("dictionary:" + dictionary);
		Map<String, String> map = new HashMap<String, String>();
		String[] m = dictionary.split(";");
		for (String s : m) {
			String[] data = s.split(",");
			map.put(data[0], data[1]);
		}

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(path + dataKey)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = null;
		StringBuffer sb = new StringBuffer();
		try {
			while ((line = br.readLine()) != null) {
				String[] l = line.split("\t");
				// 处理第一列
				String[] first = l[0].split(":");
				for (int j = 0; j < first.length - 2; j++) {
					sb.append(map.get(first[j])).append(":");
				}
				sb.append(first[first.length - 2]).append(":")
						.append(first[first.length - 1]).append("\t");
				// 2-9列不动
				for (int j = 1; j < 9; j++) {
					sb.append(l[j]).append("\t");
				}
				// 处理第十列
				String ten = l[9];
				int tl = ten.length();
				String last = "";
				if (tl % 2 == 1) {
					last = ten.substring(tl - 1, tl);
					ten = ten.substring(0, tl - 1);
				}
				for (int i = 0; i < ten.length(); i += 2) {
					String key = ten.substring(i, i + 2);
					String val = DictionaryUtils.map.get(key);
					if (val == null) {
						sb.append(key);
					} else {
						sb.append(val);
					}
				}
				sb.append(last).append("\t");
				// 11列不动
				sb.append(l[10]).append("\n");
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
		System.out.println(sb.toString());
		return false;
	}
}