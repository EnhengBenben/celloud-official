package com.nova.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.nova.service.IPythonService;

@ParentPackage("json-default")
@Action("python")
@Results({
		@Result(name = "success", type = "json", params = { "root", "flag" }),
		@Result(name = "num", type = "json", params = { "root", "num" }) })
public class PythonAction extends BaseAction {
	Logger log = Logger.getLogger(PythonAction.class);
	private static final long serialVersionUID = 1L;

	@Inject
	private IPythonService pythonService;
	private String username;
	private String pwd;
	private int id;
	private String fileName;
	private String dataKey;
	private String flag;
	private String md5;
	private long num;

	/**
	 * python 客户端登录
	 * 
	 * @return
	 */
	public String login() {
		flag = pythonService.login(username, pwd);
		return SUCCESS;
	}

	/**
	 * 获取用户上传文件的总大小
	 * 
	 * @return
	 */
	public String getSize() {
		num = pythonService.getSize(id);
		return "num";
	}

	/**
	 * 获取用户上传文件的总个数
	 * 
	 * @return
	 */
	public String getNumber() {
		num = pythonService.getNumber(id);
		return "num";
	}

	/**
	 * 文件上传前获取 DataKey
	 * 
	 * @return
	 */
	public String initDataKey() {
		flag = pythonService.getDataKey(id, fileName, md5);
		return SUCCESS;
	}

	/**
	 * 文件上传结束后保存进数据库
	 * 
	 * @return
	 */
	public String uploaded() {
		flag = pythonService.uploaded(dataKey);
		return SUCCESS;
	}

	/**
	 * 获取客户端版本
	 * 
	 * @return
	 */
	public String getClientVersion() {
		flag = pythonService.getClientVersion();
		return SUCCESS;
	}

	/**
	 * 断点续传时获取文件大小
	 * 
	 * @return
	 */
	public String getDataSize() {
		num = pythonService.getDataSize(id, dataKey.split("\\.")[0]);
		return "num";
	}

	/**
	 * 保存已上传的文件大小
	 * 
	 * @return
	 */
	public String saveDataSize() {
		num = pythonService.saveDataSize(dataKey.split("\\.")[0], num);
		return "num";
	}
	
    /**
     * 文件上传完成发送邮件
     */
    public String sendEmail() {
        pythonService.sendEmail(id, fileName, dataKey);
        num = 0l;
        return "num";
    }

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

}