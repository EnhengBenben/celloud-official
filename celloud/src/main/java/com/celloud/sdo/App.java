package com.celloud.sdo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.code.morphia.annotations.Entity;
import com.nova.sdo.DataFormat;

/**
 * APP数据模型
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-14下午5:34:41
 * @version Revision: 1.0
 */
@Entity
public class App implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long softwareId;
    private String softwareName;
    private String englishName;
    /** 软件访问地址 */
    private String host;
    /** 软件图标名称 */
    private String pictureName;
    /** 人气指数 */
    private Integer bhri;
    /** 创建时间 */
    private Date createDate;
    /** 简介 */
    private String intro;
    /** 软件描述 */
    private String description;
    /** 详细文档说明,带样式 */
    private String appDoc;
    /** 软件应用类型，0：CS软件，1：BS软件，2：数据库系统 */
    private Integer type;
    /** 推荐标志位 */
    private Integer flag;
    /** 需要的最小数据个数 */
    private Integer dataNum;
    /** 下线标志 */
    private Integer offLine;
    /** 所属公司ID */
    private Integer companyId;
    /** 是否开放 0-所有人可见 1-所属公司旗下的用户可见 默认为0 */
    private Integer attribute;
    /** 支持数据类型 */
    private List<DataFormat> dataFormat;
    /** 执行命令 */
    private String command;
    /** APP的所属分类 */
    private Classify classify;
    /** 运行完成后需要调用的方法名称 */
    private String method;
    /** 项目报告title */
    private String title;
    /** 所属分类名称 */
    private String classifyNames;
    private String companyName;
    private String formatDesc;
    /** 是否已被用户添加 =0——>未添加 >0——>已添加 */
    private Integer isAdded;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Long getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Long softwareId) {
        this.softwareId = softwareId;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public Integer getBhri() {
        return bhri;
    }

    public void setBhri(Integer bhri) {
        this.bhri = bhri;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppDoc() {
        return appDoc;
    }

    public void setAppDoc(String appDoc) {
        this.appDoc = appDoc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getDataNum() {
        return dataNum;
    }

    public void setDataNum(Integer dataNum) {
        this.dataNum = dataNum;
    }

    public Integer getOffLine() {
        return offLine;
    }

    public void setOffLine(Integer offLine) {
        this.offLine = offLine;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public Classify getClassify() {
        return classify;
    }

    public void setClassify(Classify classify) {
        this.classify = classify;
    }

    public List<DataFormat> getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(List<DataFormat> dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFormatDesc() {
        return formatDesc;
    }

    public void setFormatDesc(String formatDesc) {
        this.formatDesc = formatDesc;
    }

    public String getClassifyNames() {
        return classifyNames;
    }

    public void setClassifyNames(String classifyNames) {
        this.classifyNames = classifyNames;
    }

    public Integer getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(Integer isAdded) {
        this.isAdded = isAdded;
    }

}
