package com.nova.tools.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description: 配置文件读取工具类
 * @author lin
 * @date 2013-7-30 上午11:26:42
 */
public class PropertiesUtils {
    /**
     * SNP流程的perl路径
     */
    public static String SNP_multiple;
    /**
     * tree流程的perl路径
     */
    public static String tree;
    /**
     * qc流程的perl路径
     */
    public static String QC;
    /**
     * miRNA 流程的 perl 路径
     */
    public static String miRNA;
    /**
     * miRNA_diff 流程的 perl 路径
     */
    public static String miRNA_diff;
    /**
     * DGE
     */
    public static String DGE;

    /**
     * RNA_seq（转录组）
     */
    public static String RNA_seq;

    /**
     * 转录组无参照组装
     */
    public static String RNA_DeNovo;
    /**
     * 转录组无参照差异
     */
    public static String DeNovo_Diff;
    /**
     * HCV
     */
    public static String HCV;
    public static String PGS;
    public static String img;

    /**
     * HBV_SNP2
     */
    public static String HBV_SNP2;
    public static String gDNA;
    /**
     * 上传文件的存放位置
     */
    public static String dataPath;
    /**
     * EST流程的结果文件名称
     */
    public static String estFileName;
    /**
     * 外网地址
     */
    public static String outProject;
    /**
     * data.list 文件的存放位置
     */
    public static String datalist;

    /**
     * 内网Celloud地址
     */
    public static String celloud;
    public static String ExomeSNV;
    public static String miRNA_MG;
    public static String EGFR;
    public static String TB;
    public static String MalBac;
    public static String gDNA_MR;
    public static String MDA_MR;
    public static String KRAS;
    public static String MDA_HR_perl;
    public static String Translate;
    public static String _16S;
    public static String SurePlex;
    public static String NIPT;
    public static String gDNA_MR_v1;
    public static String MDA_MR_v1;
    public static String MDA_HR_v1;
    public static String SurePlex_v1;
    public static String MalBac_v1;
    public static String Sureplex_HR;
    public static String tbinh;
    public static String DPD;
    public static String BRAF;
    public static String UGT;
    public static String CMP;
    public static String CMP199;
    public static String GDD;
    public static String split;
    public static String MIB;
    public static String report_dburl;
    public static String report_dbname;
    static {
        getPath();
    }

    /**
     * 获取文件存放路径
     * 
     * @return
     */
    private static void getPath() {
        Properties pro = new Properties();
        InputStream is = PropertiesUtils.class.getClassLoader()
                .getResourceAsStream("system.properties");
        try {
            pro.load(is);
        } catch (IOException e) {
            System.err.println("获取资源文件信息失败" + e);
        }
        tree = pro.getProperty("tree");
        dataPath = pro.getProperty("dataPath");
        estFileName = pro.getProperty("estFileName");
        outProject = pro.getProperty("outProject");
        SNP_multiple = pro.getProperty("SNP_multiple");
        datalist = pro.getProperty("datalist");
        QC = pro.getProperty("QC");
        celloud = pro.getProperty("celloud");
        miRNA = pro.getProperty("miRNA");
        miRNA_diff = pro.getProperty("miRNA_diff");
        ExomeSNV = pro.getProperty("ExomeSNV");
        miRNA_MG = pro.getProperty("miRNA_MG");
        DGE = pro.getProperty("DGE");
        RNA_seq = pro.getProperty("RNA_seq");
        RNA_DeNovo = pro.getProperty("RNA_DeNovo");
        DeNovo_Diff = pro.getProperty("DeNovo_Diff");
        HCV = pro.getProperty("HCV");
        PGS = pro.getProperty("MDA");
        HBV_SNP2 = pro.getProperty("HBV_SNP2");
        gDNA = pro.getProperty("gDNA");
        img = pro.getProperty("img");
        EGFR = pro.getProperty("EGFR");
        MalBac = pro.getProperty("MalBac");
        gDNA_MR = pro.getProperty("gDNA_MR");
        MDA_MR = pro.getProperty("MDA_MR");
        KRAS = pro.getProperty("KRAS");
        TB = pro.getProperty("TB");
        MDA_HR_perl = pro.getProperty("MDA_HR");
        Translate = pro.getProperty("Translate");
        _16S = pro.getProperty("_16S");
        SurePlex = pro.getProperty("SurePlex");
        NIPT = pro.getProperty("NIPT");
        gDNA_MR_v1 = pro.getProperty("gDNA_MR_v1");
        MDA_MR_v1 = pro.getProperty("MDA_MR_v1");
        MDA_HR_v1 = pro.getProperty("MDA_HR_v1");
        SurePlex_v1 = pro.getProperty("SurePlex_v1");
        MalBac_v1 = pro.getProperty("MalBac_v1");
        Sureplex_HR = pro.getProperty("Sureplex_HR");
        tbinh = pro.getProperty("TBINH");
        DPD = pro.getProperty("DPD");
        BRAF = pro.getProperty("BRAF");
        UGT = pro.getProperty("UGT");
        CMP = pro.getProperty("CMP");
        CMP199 = pro.getProperty("CMP199");
        GDD = pro.getProperty("GDD");
        split = pro.getProperty("split");
        MIB = pro.getProperty("MIB");
        report_dburl = pro.getProperty("report_dburl");
        report_dbname = pro.getProperty("report_dbname");
    }
}