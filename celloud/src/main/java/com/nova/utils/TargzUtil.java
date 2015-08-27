package com.nova.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.log4j.Logger;

public class TargzUtil {
    private static Logger log = Logger.getLogger(TargzUtil.class);
    private BufferedOutputStream bufferedOutputStream;
    public List<File> fileList;
    String zipfileName = null;

    public TargzUtil() {

    }

    public TargzUtil(String fileName) {
	this.zipfileName = fileName;
    }

    /*
     * 执行入口,rarFileName为需要解压的文件路径(具体到文件),destDir为解压目标路径
     */
    public List<File> unTargzFile(String rarFileName, String destDir) {
	TargzUtil gzip = new TargzUtil(rarFileName);
	String outputDirectory = destDir;
	File file = new File(outputDirectory);
	if (!file.exists()) {
	    boolean isTrue = file.mkdirs();
	    if (!isTrue) {
		log.error("路径创建失败：" + outputDirectory);
	    }
	}
	return gzip.unzipTarFile(outputDirectory);
    }

    public List<File> unzipTarFile(String outputDirectory) {
	fileList = new ArrayList<File>();
	fileList.clear();
	FileInputStream fis = null;
	ArchiveInputStream in = null;
	BufferedInputStream bufferedInputStream = null;
	try {
	    fis = new FileInputStream(zipfileName);
	    GZIPInputStream is = new GZIPInputStream(new BufferedInputStream(
		    fis));
	    in = new ArchiveStreamFactory().createArchiveInputStream("tar", is);
	    bufferedInputStream = new BufferedInputStream(in);
	    TarArchiveEntry entry = (TarArchiveEntry) in.getNextEntry();
	    while (entry != null) {
		String name = entry.getName();
		String[] names = name.split("/");
		String fileName = outputDirectory;
		for (int i = 0; i < names.length; i++) {
		    String str = names[i];
		    fileName = fileName + File.separator + str;
		}
		if (name.endsWith("/")) {
		    mkFolder(fileName);
		} else {
		    FileTools.createFile(fileName);
		    File file = new File(fileName);
		    this.fileList.add(file);
		    bufferedOutputStream = new BufferedOutputStream(
			    new FileOutputStream(file));
		    int b = 0;
		    while ((b = bufferedInputStream.read()) != -1) {
			bufferedOutputStream.write(b);
		    }
		    bufferedOutputStream.flush();
		    bufferedOutputStream.close();
		}
		entry = (TarArchiveEntry) in.getNextEntry();
	    }

	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    try {
		if (bufferedInputStream != null) {
		    bufferedInputStream.close();
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	return fileList;
    }

    private void mkFolder(String fileName) {
	File file = new File(fileName);
	if (!file.exists()) {
	    boolean isTrue = file.mkdirs();
	    if (!isTrue) {
		log.error("路径创建失败：" + fileName);
	    }
	}
    }

    public List<File> getFileList() {
	return this.fileList;
    }
}
