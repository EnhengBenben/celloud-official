package com.celloud.model.mongo;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;

import com.celloud.model.mysql.DataFile;

/**
 * AccuSeqα2 Mongo集合内容
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">leamo</a>
 * @date 2016年11月9日 上午10:50:56
 */
public class AccuSeqα2 extends Base {
    private static final long serialVersionUID = 1L;
    /**
     * 所运行数据信息
     */
    @Embedded
    private List<DataFile> data;
    /**
     * 数据A的全部运行结果
     */
    @Embedded
    private CmpReport dataAReport;
    /**
     * 数据B的全部运行结果
     */
    @Embedded
    private CmpReport dataBReport;
    /**
     * 有指导意义的阳性位点列表
     */
    private List<GeneDetectionResult> usefulGeneResult;
    /**
     * 用户填写部分
     */
    private AccuSeqα2Fill accuSeqFill;

    public List<DataFile> getData() {
        return data;
    }

    public void setData(List<DataFile> data) {
        this.data = data;
    }

    public CmpReport getDataAReport() {
        return dataAReport;
    }

    public void setDataAReport(CmpReport dataAReport) {
        this.dataAReport = dataAReport;
    }

    public CmpReport getDataBReport() {
        return dataBReport;
    }

    public void setDataBReport(CmpReport dataBReport) {
        this.dataBReport = dataBReport;
    }

    public List<GeneDetectionResult> getUsefulGeneResult() {
        return usefulGeneResult;
    }

    public void setUsefulGeneResult(
            List<GeneDetectionResult> usefulGeneResult) {
        this.usefulGeneResult = usefulGeneResult;
    }

    public AccuSeqα2Fill getAccuSeqFill() {
        return accuSeqFill;
    }

    public void setAccuSeqFill(AccuSeqα2Fill accuSeqFill) {
        this.accuSeqFill = accuSeqFill;
    }

}
