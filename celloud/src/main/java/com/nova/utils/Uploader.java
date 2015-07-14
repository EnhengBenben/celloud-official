package com.nova.utils;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.util.Streams;

import sun.misc.BASE64Decoder;

public class Uploader {
	private String url = "";

	private String fileName = "";

	private String state = "";

	private String type = "";

	private String originalName = "";

	private String size = "";

	private javax.servlet.http.HttpServletRequest request = null;
	private String title = "";

	private String savePath = "upload";

	private String[] allowFiles = { ".rar", ".doc", ".docx", ".zip", ".pdf",
			".txt", ".swf", ".wmv", ".gif", ".png", ".jpg", ".jpeg", ".bmp" };

	private int maxSize = 10000;

	private java.util.HashMap<String, String> errorInfo = new java.util.HashMap<String, String>();

	public Uploader(javax.servlet.http.HttpServletRequest request) {
		this.request = request;
		java.util.HashMap<String, String> tmp = this.errorInfo;
		tmp.put("SUCCESS", "SUCCESS");
		tmp.put("NOFILE", "未包含文件上传域");
		tmp.put("TYPE", "不允许的文件格式");
		tmp.put("SIZE", "文件大小超出限制");
		tmp.put("ENTYPE", "请求类型ENTYPE错误");
		tmp.put("REQUEST", "上传请求异常");
		tmp.put("IO", "IO异常");
		tmp.put("DIR", "目录创建失败");
		tmp.put("UNKNOWN", "未知错误");
	}

	public void upload() throws Exception {
		boolean isMultipart = org.apache.commons.fileupload.servlet.ServletFileUpload
				.isMultipartContent(this.request);
		if (!isMultipart) {
			this.state = ((String) this.errorInfo.get("NOFILE"));
			return;
		}
		DiskFileItemFactory dff = new DiskFileItemFactory();
		String savePath = getFolder(this.savePath);
		dff.setRepository(new java.io.File(savePath));
		try {
			org.apache.commons.fileupload.servlet.ServletFileUpload sfu = new org.apache.commons.fileupload.servlet.ServletFileUpload(
					dff);
			sfu.setSizeMax(this.maxSize * 1024);
			sfu.setHeaderEncoding("UTF-8");
			FileItemIterator fii = sfu.getItemIterator(this.request);
			while (fii.hasNext()) {
				org.apache.commons.fileupload.FileItemStream fis = fii.next();
				if (!fis.isFormField()) {
					this.originalName = fis.getName().substring(
							fis.getName().lastIndexOf(
									System.getProperty("file.separator")) + 1);
					if (!checkFileType(this.originalName)) {
						this.state = ((String) this.errorInfo.get("TYPE"));
					} else {
						this.fileName = getName(this.originalName);
						this.type = getFileExt(this.fileName);
						this.url = (savePath + "/" + this.fileName);
						java.io.BufferedInputStream in = new java.io.BufferedInputStream(
								fis.openStream());
						FileOutputStream out = new FileOutputStream(
								new java.io.File(getPhysicalPath(this.url)));
						BufferedOutputStream output = new BufferedOutputStream(
								out);
						Streams.copy(in, output, true);
						this.state = ((String) this.errorInfo.get("SUCCESS"));

						break;
					}
				} else {
					String fname = fis.getFieldName();

					if (fname.equals("pictitle")) {
						java.io.BufferedInputStream in = new java.io.BufferedInputStream(
								fis.openStream());
						java.io.BufferedReader reader = new java.io.BufferedReader(
								new InputStreamReader(in));
						StringBuffer result = new StringBuffer();
						while (reader.ready()) {
							result.append((char) reader.read());
						}
						this.title = new String(result.toString().getBytes(),
								"utf-8");
						reader.close();
					}
				}
			}
		} catch (FileUploadBase.SizeLimitExceededException e) {
			this.state = ((String) this.errorInfo.get("SIZE"));
		} catch (FileUploadBase.InvalidContentTypeException e) {
			this.state = ((String) this.errorInfo.get("ENTYPE"));
		} catch (FileUploadException e) {
			this.state = ((String) this.errorInfo.get("REQUEST"));
		} catch (Exception e) {
			this.state = ((String) this.errorInfo.get("UNKNOWN"));
		}
	}

	public void uploadBase64(String fieldName) {
		String savePath = getFolder(this.savePath);
		String base64Data = this.request.getParameter(fieldName);
		this.fileName = getName("test.png");
		this.url = (savePath + "/" + this.fileName);
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			java.io.File outFile = new java.io.File(getPhysicalPath(this.url));
			java.io.OutputStream ro = new FileOutputStream(outFile);
			byte[] b = decoder.decodeBuffer(base64Data);
			for (int i = 0; i < b.length; i++) {
				if (b[i] < 0) {
					int tmp124_122 = i;
					byte[] tmp124_120 = b;
					tmp124_120[tmp124_122] = ((byte) (tmp124_120[tmp124_122] + 256));
				}
			}
			ro.write(b);
			ro.flush();
			ro.close();
			this.state = ((String) this.errorInfo.get("SUCCESS"));
		} catch (Exception e) {
			this.state = ((String) this.errorInfo.get("IO"));
		}
	}

	private boolean checkFileType(String fileName) {
		Iterator<String> type = Arrays.asList(this.allowFiles).iterator();
		while (type.hasNext()) {
			String ext = (String) type.next();
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}

	private String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	private String getName(String fileName) {
		java.util.Random random = new java.util.Random();
		return this.fileName = random.nextInt(10000)
				+ System.currentTimeMillis() + getFileExt(fileName);
	}

	private String getFolder(String path) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		path = path + "/" + formater.format(new Date());
		java.io.File dir = new java.io.File(getPhysicalPath(path));
		if (!dir.exists()) {
			try {
				dir.mkdirs();
			} catch (Exception e) {
				this.state = ((String) this.errorInfo.get("DIR"));
				return "";
			}
		}
		return path;
	}

	private String getPhysicalPath(String path) {
		String servletPath = this.request.getServletPath();
		String realPath = this.request.getSession().getServletContext()
				.getRealPath(servletPath);
		return new java.io.File(realPath).getParent() + "/" + path;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public void setAllowFiles(String[] allowFiles) {
		this.allowFiles = allowFiles;
	}

	public void setMaxSize(int size) {
		this.maxSize = size;
	}

	public String getSize() {
		return this.size;
	}

	public String getUrl() {
		return this.url;
	}

	public String getFileName() {
		return this.fileName;
	}

	public String getState() {
		return this.state;
	}

	public String getTitle() {
		return this.title;
	}

	public String getType() {
		return this.type;
	}

	public String getOriginalName() {
		return this.originalName;
	}
}