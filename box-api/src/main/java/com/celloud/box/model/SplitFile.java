package com.celloud.box.model;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import com.celloud.box.utils.UploadPath;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * split校验文件，存储split能否运行的校验信息<br>
 * 该文件的文件名由userId、batch、pubName三个参数md5而来
 * 
 * @author <a href="mailto:sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年10月26日下午3:01:27
 * @version Revision: 1.0
 */
public class SplitFile {
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 批次
	 */
	private String batch;
	/**
	 * 提取后的名字("R1"或"R2"前面的部分)
	 */
	private String name;
	/**
	 * 文件的数据标签
	 */
	private Integer tagId;
	/**
	 * R1文件的路径
	 */
	private String r1Path;
	/**
	 * R2文件的路径
	 */
	private String r2Path;
	/**
	 * txt或lis文件的路径
	 */
	private String txtPath;
	/**
	 * split输出结果的路径
	 */
	private String outPath;
	/**
	 * list文件的路径
	 */
	private String listPath;
	/**
	 * 是否正在运行
	 */
	private boolean running;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getR1Path() {
		return r1Path;
	}

	public void setR1Path(String r1Path) {
		this.r1Path = r1Path;
	}

	public String getR2Path() {
		return r2Path;
	}

	public void setR2Path(String r2Path) {
		this.r2Path = r2Path;
	}

	public String getTxtPath() {
		return txtPath;
	}

	public void setTxtPath(String txtPath) {
		this.txtPath = txtPath;
	}

	public String getOutPath() {
		return outPath;
	}

	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}

	public String getListPath() {
		return listPath;
	}

	public void setListPath(String listPath) {
		this.listPath = listPath;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public String toJSON() {
		StringWriter w = new StringWriter();
		try {
			new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE).writeValue(w, this);
		} catch (IOException e) {
		}
		return w.toString();
	}

	public boolean check() {
		return this.r1Path != null && this.r2Path != null;
	}

	public boolean toFile() {
		if (this.name == null) {
			return false;
		}
		try {
			File file = new File(UploadPath.getSplitCheckingPath(userId, batch, name));
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE).writeValue(file, this);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public SplitFile load() {
		if (name == null) {
			return null;
		}
		return load(UploadPath.getSplitCheckingPath(userId, batch, name));
	}

	public static SplitFile load(String path) {
		SplitFile splitFile = null;
		try {
			splitFile = new ObjectMapper().readValue(new File(path), SplitFile.class);
		} catch (IOException e) {
		}
		return splitFile;
	}
}
