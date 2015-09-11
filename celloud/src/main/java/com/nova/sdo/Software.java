package com.nova.sdo;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: Software
 * @Description: (软件的数据服务对象)
 * @author summer
 * @date 2012-6-19 下午02:38:51
 * 
 */
public class Software implements Serializable {
    private static final long serialVersionUID = 1L;
    private int softwareId;// 软件编号
    private String softwareName;// 软件名称
    private String softwareEntry;// 软件调用名称
    private String host;// 软件安装主机名
    private String processName;// 进程名
    private String pictureName;// 软件图片名称
    private int bhri;// 人气指数
    private String classify;// 分类
    private int classifyId;// 类型编号
    private Date createDate;// 创建时间
    private String description;// 软件描述
    private String email;// 要发送的管理员的邮箱
    private int type;// 软件应用类型，0：CS软件，1：BS软件，2：数据库系统

    private int flag;// 推荐标志位
    private String englishName;// 英文名
    private String intro;// 简介
    private String classifyName;// 分类名

    private int deskno;// 软件所在的桌面
    private String isAdd;// 是否添加
    private String rdp;// 生成的rdp文件

    private int runNum; // 运行次数

    private int runData;// 是否支持数据的运行，默认为支持
    private int dataNum;

    private int param;// 是否需要设置参数

    private String appDoc;// 文档说明

    private int fileFormat;// 运行的文件格式
    private String formatDesc;
    private String proDataType;
    private String screenShot;// 截图
    private int offLine;// 下线标志
    private int companyId;// 所属公司ID
    private int attribute;// 是否开放 0-所有人可见 1-所属公司旗下的用户可见 默认为0

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public int getSoftwareId() {
	return softwareId;
    }

    public void setSoftwareId(int softwareId) {
	this.softwareId = softwareId;
    }

    public String getSoftwareName() {
	return softwareName;
    }

    public void setSoftwareName(String softwareName) {
	this.softwareName = softwareName;
    }

    public String getSoftwareEntry() {
	return softwareEntry;
    }

    public void setSoftwareEntry(String softwareEntry) {
	this.softwareEntry = softwareEntry;
    }

    public String getHost() {
	return host;
    }

    public void setHost(String host) {
	this.host = host;
    }

    public String getProcessName() {
	return processName;
    }

    public void setProcessName(String processName) {
	this.processName = processName;
    }

    public String getPictureName() {
	return pictureName;
    }

    public void setPictureName(String pictureName) {
	this.pictureName = pictureName;
    }

    public int getBhri() {
	return bhri;
    }

    public void setBhri(int bhri) {
	this.bhri = bhri;
    }

    public String getClassify() {
	return classify;
    }

    public void setClassify(String classify) {
	this.classify = classify;
    }

    public int getClassifyId() {
	return classifyId;
    }

    public void setClassifyId(int classifyId) {
	this.classifyId = classifyId;
    }

    public Date getCreateDate() {
	return createDate;
    }

    public void setCreateDate(Date createDate) {
	this.createDate = createDate;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public int getType() {
	return type;
    }

    public void setType(int type) {
	this.type = type;
    }

    public String getIsAdd() {
	return isAdd;
    }

    public void setIsAdd(String isAdd) {
	this.isAdd = isAdd;
    }

    public String getRdp() {
	return rdp;
    }

    public void setRdp(String rdp) {
	this.rdp = rdp;
    }

    public int getDeskno() {
	return deskno;
    }

    public void setDeskno(int deskno) {
	this.deskno = deskno;
    }

    public int getRunNum() {
	return runNum;
    }

    public void setRunNum(int runNum) {
	this.runNum = runNum;
    }

    public int getFlag() {
	return flag;
    }

    public void setFlag(int flag) {
	this.flag = flag;
    }

    public String getEnglishName() {
	return englishName;
    }

    public void setEnglishName(String englishName) {
	this.englishName = englishName;
    }

    public String getIntro() {
	return intro;
    }

    public void setIntro(String intro) {
	this.intro = intro;
    }

    public String getClassifyName() {
	return classifyName;
    }

    public void setClassifyName(String classifyName) {
	this.classifyName = classifyName;
    }

    public int getRunData() {
	return runData;
    }

    public void setRunData(int runData) {
	this.runData = runData;
    }

    public int getDataNum() {
	return dataNum;
    }

    public void setDataNum(int dataNum) {
	this.dataNum = dataNum;
    }

    public int getParam() {
	return param;
    }

    public void setParam(int param) {
	this.param = param;
    }

    public String getAppDoc() {
	return appDoc;
    }

    public void setAppDoc(String appDoc) {
	this.appDoc = appDoc;
    }

    public int getFileFormat() {
	return fileFormat;
    }

    public void setFileFormat(int fileFormat) {
	this.fileFormat = fileFormat;
    }

    public String getScreenShot() {
	return screenShot;
    }

    public void setScreenShot(String screenShot) {
	this.screenShot = screenShot;
    }

    public String getFormatDesc() {
	return formatDesc;
    }

    public void setFormatDesc(String formatDesc) {
	this.formatDesc = formatDesc;
    }

    public String getProDataType() {
	return proDataType;
    }

    public void setProDataType(String proDataType) {
	this.proDataType = proDataType;
    }

    public int getOffLine() {
	return offLine;
    }

    public void setOffLine(int offLine) {
	this.offLine = offLine;
    }

    public int getCompanyId() {
	return companyId;
    }

    public void setCompanyId(int companyId) {
	this.companyId = companyId;
    }

    public int getAttribute() {
	return attribute;
    }

    public void setAttribute(int attribute) {
	this.attribute = attribute;
    }

}
