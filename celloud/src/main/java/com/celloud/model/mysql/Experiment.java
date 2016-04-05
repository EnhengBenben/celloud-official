package com.celloud.model.mysql;

import java.util.Date;

public class Experiment {
    private Integer id;

    private String number;

    private Double concentration;

    private Integer quality;

    private Double libraryConcentration;

    private Integer step;
    
    private String stepName;

    private Integer sampleType;
    
    private String sampleTypeName;

    private Integer amplificationMethod;
    
    private String amplificationMethodName;

    private Integer sequenator;
    
    private String sequenatorName;

    private String seqIndex;

    private String other;

    private Integer state;

    private Date createDate;

    private Integer userId;

    private Integer fileId;

    private String dataKey;

    private Integer reportId;

    private Date reportDate;

    private Byte qualified;

    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public Double getConcentration() {
        return concentration;
    }

    public void setConcentration(Double concentration) {
        this.concentration = concentration;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public Double getLibraryConcentration() {
        return libraryConcentration;
    }

    public void setLibraryConcentration(Double libraryConcentration) {
        this.libraryConcentration = libraryConcentration;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getSampleType() {
        return sampleType;
    }

    public void setSampleType(Integer sampleType) {
        this.sampleType = sampleType;
    }

    public Integer getAmplificationMethod() {
        return amplificationMethod;
    }

    public void setAmplificationMethod(Integer amplificationMethod) {
        this.amplificationMethod = amplificationMethod;
    }

    public Integer getSequenator() {
        return sequenator;
    }

    public void setSequenator(Integer sequenator) {
        this.sequenator = sequenator;
    }

    public String getSeqIndex() {
        return seqIndex;
    }

    public void setSeqIndex(String seqIndex) {
        this.seqIndex = seqIndex == null ? null : seqIndex.trim();
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey == null ? null : dataKey.trim();
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Byte getQualified() {
        return qualified;
    }

    public void setQualified(Byte qualified) {
        this.qualified = qualified;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getSampleTypeName() {
		return sampleTypeName;
	}

	public void setSampleTypeName(String sampleTypeName) {
		this.sampleTypeName = sampleTypeName;
	}

	public String getAmplificationMethodName() {
		return amplificationMethodName;
	}

	public void setAmplificationMethodName(String amplificationMethodName) {
		this.amplificationMethodName = amplificationMethodName;
	}

	public String getSequenatorName() {
		return sequenatorName;
	}

	public void setSequenatorName(String sequenatorName) {
		this.sequenatorName = sequenatorName;
	}
    
    
}