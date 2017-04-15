package com.celloud.model.mysql;

import java.math.BigDecimal;
import java.util.Date;

public class App {
	private Integer appId;

	private String appName;

	private String englishName;

	private String address;

	private String pictureName;

	private Date createDate;

	private String intro;

	private String description;

	private Integer companyId;

	private Integer attribute;

	private Integer runType;

	private Integer flag;

	private Integer runData;

	private Integer dataNum;

	private Integer param;

	private Integer offLine;

	private String command;

	private String title;

	private String method;

	private String appDoc;

	private Integer maxTask;

    private Integer classic;

    private String version;

	// 展示字段
	private String companyName;
	/** 是否已被用户添加 =0——>未添加 >0——>已添加 */
	private Integer isAdded;
	/** 所属分类名称 */
	private String classifyNames;
	/** APP单价 */
	private BigDecimal price;
	private String tagName;
	private String code;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appId == null) ? 0 : appId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		App other = (App) obj;
		if (appId == null) {
			if (other.appId != null)
				return false;
		} else if (!appId.equals(other.appId))
			return false;
		return true;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName == null ? null : appName.trim();
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName == null ? null : englishName.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName == null ? null : pictureName.trim();
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
		this.intro = intro == null ? null : intro.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
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

	public Integer getRunType() {
		return runType;
	}

	public void setRunType(Integer runType) {
		this.runType = runType;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getRunData() {
		return runData;
	}

	public void setRunData(Integer runData) {
		this.runData = runData;
	}

	public Integer getDataNum() {
		return dataNum;
	}

	public void setDataNum(Integer dataNum) {
		this.dataNum = dataNum;
	}

	public Integer getParam() {
		return param;
	}

	public void setParam(Integer param) {
		this.param = param;
	}

	public Integer getOffLine() {
		return offLine;
	}

	public void setOffLine(Integer offLine) {
		this.offLine = offLine;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command == null ? null : command.trim();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method == null ? null : method.trim();
	}

	public String getAppDoc() {
		return appDoc;
	}

	public void setAppDoc(String appDoc) {
		this.appDoc = appDoc == null ? null : appDoc.trim();
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getIsAdded() {
		return isAdded;
	}

	public void setIsAdded(Integer isAdded) {
		this.isAdded = isAdded;
	}

	public String getClassifyNames() {
		return classifyNames;
	}

	public void setClassifyNames(String classifyNames) {
		this.classifyNames = classifyNames;
	}

	public Integer getMaxTask() {
		return maxTask;
	}

	public void setMaxTask(Integer maxTask) {
		this.maxTask = maxTask;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

    public Integer getClassic() {
        return classic;
    }

    public void setClassic(Integer classic) {
        this.classic = classic;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}