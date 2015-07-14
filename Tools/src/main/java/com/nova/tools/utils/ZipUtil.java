package com.nova.tools.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class ZipUtil {

	public static void ZIP(String sourcePath, String zipFileName)
			throws IOException {
		ZipOutputStream zos = new ZipOutputStream(new File(zipFileName));
		// 设置压缩的时候文件名编码为gb2312
		zos.setEncoding("gb2312");
		File file = new File(sourcePath);
		if (file.isDirectory()) {
			// 此处使用/来表示目录,如果使用\\来表示目录的话,有可能导致压缩后的文件目录组织形式在解压缩的时候不能正确识别。
			ZIPDIR(sourcePath, zos, file.getName() + "/");
		} else {
			// 如果直接压缩文件
			ZIPDIR(file.getPath(), zos, new File(file.getParent()).getName()
					+ "/");
			ZIPFile(file.getPath(), zos, new File(file.getParent()).getName()
					+ "/" + file.getName());
		}
		zos.closeEntry();
		zos.close();
	}

	public static void ZIPDIR(String sourceDir, ZipOutputStream zos,
			String tager) throws IOException {
		// System.out.println(tager);
		ZipEntry ze = new ZipEntry(tager);
		zos.putNextEntry(ze);
		// 提取要压缩的文件夹中的所有文件
		File f = new File(sourceDir);
		File[] flist = f.listFiles();
		if (flist != null) {
			// 如果该文件夹下有文件则提取所有的文件进行压缩
			for (File fsub : flist) {
				if (fsub.isDirectory()) {
					// 如果是目录则进行目录压缩
					ZIPDIR(fsub.getPath(), zos, tager + fsub.getName() + "/");
				} else {
					// 如果是文件，则进行文件压缩
					ZIPFile(fsub.getPath(), zos, tager + fsub.getName());
				}
			}
		}
	}

	public static void ZIPFile(String sourceFileName, ZipOutputStream zos,
			String tager) throws IOException {
		// System.out.println(tager);
		ZipEntry ze = new ZipEntry(tager);
		zos.putNextEntry(ze);
		// 读取要压缩文件并将其添加到压缩文件中
		FileInputStream fis = new FileInputStream(new File(sourceFileName));
		byte[] bf = new byte[2048];
		int location = 0;
		while ((location = fis.read(bf)) != -1) {
			zos.write(bf, 0, location);
		}
		fis.close();
	}

	@SuppressWarnings("unchecked")
	public static void UnZIP(String sourceFileName, String desDir)
			throws IOException {
		// 创建压缩文件对象
		ZipFile zf = new ZipFile(new File(sourceFileName), "GB2312");
		// 获取压缩文件中的文件枚举
		Enumeration en = zf.getEntries();
		int length = 0;
		byte[] b = new byte[1024];
		// 提取压缩文件夹中的所有压缩实例对象
		while (en.hasMoreElements()) {
			Object ze = en.nextElement();
			// System.out.println("压缩文件夹中的内容："+ze.getName());
			// System.out.println("是否是文件夹："+ze.isDirectory());
			// 创建解压缩后的文件实例对象
			File f = new File(desDir + ((File) ze).getName());
			// System.out.println("解压后的内容："+f.getPath());
			// System.out.println("是否是文件夹："+f.isDirectory());
			// 如果当前压缩文件中的实例对象是文件夹就在解压缩后的文件夹中创建该文件夹
			if (f.isDirectory()) {
				f.mkdirs();
			} else {
				// 如果当前解压缩文件的父级文件夹没有创建的话，则创建好父级文件夹
				if (!f.getParentFile().exists()) {
					f.getParentFile().mkdirs();
				}
				// 将当前文件的内容写入解压后的文件夹中。
				FileOutputStream outputStream = new FileOutputStream(f);
				InputStream inputStream = zf.getInputStream((ZipEntry) ze);
				while ((length = inputStream.read(b)) > 0) {
					outputStream.write(b, 0, length);
				}
				inputStream.close();
				outputStream.close();
			}
		}
		zf.close();
	}

	public static void main(String[] args) {
		try {
			ZIP("/home/lin/test/Exome_SNV_xxxs",
					"/home/lin/test/Exome_SNV_xxxs.zip");
			// UnZIP("E:/带动.zip","c:/BBBBBB/");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
