package com.celloud.mongo.sdo;

import java.util.List;
import java.util.Map;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;

/**
 * 文件分类app
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-10-14下午1:16:46
 * @version Revision: 1.0
 */
@Entity(noClassnameStored = true)
public class Split extends Base {
    private static final long serialVersionUID = 1L;
    /**
     * 所运行数据信息
     */
    @Embedded
    private List<Data> data;
    /** 序列总量 */
    private String totalReads;
    /** 平均质量 */
    private String avgQuality;
    /** 平均GC含量(%) */
    private String avgGCContent;
    /**
     * 分离后的结果文件 Key: name(数据名称) number(序列数量)
     */
    private List<Map<String, String>> resultList;
    /** 1:分离的结果文件已上传 */
    private Integer upload;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getTotalReads() {
        return totalReads;
    }

    public void setTotalReads(String totalReads) {
        this.totalReads = totalReads;
    }

    public String getAvgQuality() {
        return avgQuality;
    }

    public void setAvgQuality(String avgQuality) {
        this.avgQuality = avgQuality;
    }

    public String getAvgGCContent() {
        return avgGCContent;
    }

    public void setAvgGCContent(String avgGCContent) {
        this.avgGCContent = avgGCContent;
    }

    public List<Map<String, String>> getResultList() {
        return resultList;
    }

    public void setResultList(List<Map<String, String>> resultList) {
        this.resultList = resultList;
    }

    public Integer getUpload() {
        return upload;
    }

    public void setUpload(Integer upload) {
        this.upload = upload;
    }

}
