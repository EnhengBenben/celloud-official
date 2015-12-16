package com.nova.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:该文件用于配置app的ID
 * @author lin
 * @date 2013-9-12 下午2:26:20
 */
public class AppNameIDConstant {
    // 绑定数据库ID
    /**
     * HBV_SNP
     */
    public static final String SNP = "6";
    /**
     * EST
     */
    public static final String EST = "9";
    /**
     * miRNA
     */
    public static final String miRNA = "10";
    /**
     * HBV_Tree
     */
    public static final String Tree = "11";
    /**
     * FastQC
     */
    public static final String QC = "12";
    /**
     * 外显子组
     */
    public static final String ExomeSNV = "74";
    /**
     * miRNA_diff
     */
    public static final String miRNADiff = "75";

    /**
     * 全流程 miRNAMG
     */
    public static final String miRNAMG = "76";

    /**
     * DGE（表达谱）
     */
    public static final String DGE = "77";

    /**
     * 转录组(无参照)：组装
     */
    public static final String RNA_DeNovo = "78";

    /**
     * 转录组(无参照)：差异分析
     */
    public static final String DeNovo_Diff = "79";

    /**
     * HCV
     */
    public static final String HCV = "80";

    /**
     * 转录组(有参照)
     */
    public static final String RNA_seq = "";
    /**
     * PGS=MDA
     */
    public static final String PGS = "81";

    /**
     * HBV_SNP2
     */
    public static final String HBV_SNP2 = "82";

    public static final String gDNA = "83";

    public static final String EGFR = "84";
    public static final String MalBac = "85";
    public static final String gDNA_HR = "86";
    public static final String gDNA_MR = "87";
    public static final String MDA_MR = "88";
    public static final String KRAS = "89";
    public static final String TB = "90";

    public static final String gDNA_Chimeric = "92";
    public static final String MDA_Chimeric = "93";
    public static final String MDA_HR = "91";
    public static final String TRANSLATE = "73";
    public static final String _16S = "1";
    public static final String SurePlex = "94";
    public static final String NIPT = "95";
    public static final String gDNA_MR_v1 = "96";
    public static final String MDA_MR_v1 = "97";
    public static final String MDA_HR_v1 = "98";
    public static final String gDNA_HR_v1 = "99";
    public static final String MDA_Chimeric_v1 = "100";
    public static final String gDNA_Chimeric_v1 = "101";
    public static final String SurePlex_v1 = "102";
    public static final String MalBac_v1 = "103";
    public static final String Sureplex_HR = "104";
    public static final String TB_INH = "105";
    public static final String DPD = "106";
    public static final String BRAF = "107";
    public static final String UGT = "108";
    public static final String VSP = "109";
    public static final String CMP = "110";
    public static final String CMP_199 = "111";
    public static final String GDD = "112";
    public static final String SureplexMosaic = "116";

    // 根据ID获取名称
    /**
     * appID--appName对应关系
     */
    public static Map<String, String> map = new HashMap<String, String>();
    static {
	map.put(SNP, "HBV_SNP");
	map.put(EST, "EST");
	map.put(miRNA, "miRNA");
	map.put(Tree, "ABI_NJ");
	map.put(QC, "FastQC");
	map.put(ExomeSNV, "Exome_SNV");
	map.put(miRNADiff, "miRNA_diff");
	map.put(miRNAMG, "miRNA_MG");
	map.put(DGE, "DGE");
	map.put(RNA_DeNovo, "RNA_DeNovo");
	map.put(DeNovo_Diff, "RNA_DN_Diff");
	map.put(RNA_seq, "RNA_seq");
	map.put(HCV, "HCV_Genotype");
	map.put(PGS, "MDA");
	map.put(HBV_SNP2, "HBV_SNP");
	map.put(gDNA, "gDNA");
	map.put(EGFR, "EGFR");
	map.put(MalBac, "MalBac");
	map.put(gDNA_HR, "gDNA_HR");
	map.put(gDNA_MR, "gDNA_MR");
	map.put(MDA_MR, "MDA_MR");
	map.put(KRAS, "KRAS");
	map.put(TB, "TB-Rifampicin");

	map.put(gDNA_Chimeric, "gDNA_Chimeric");
	map.put(MDA_Chimeric, "MDA_Chimeric");
	map.put(MDA_HR, "MDA_HR");
	map.put(TRANSLATE, "translate_simplified");
	map.put(_16S, "16S");
	map.put(SurePlex, "SurePlex");
	map.put(NIPT, "NIPT");
	map.put(gDNA_MR_v1, "gDNA_MR_v1");
	map.put(MDA_MR_v1, "MDA_MR_v1");
	map.put(MDA_HR_v1, "MDA_HR_v1");
	map.put(gDNA_HR_v1, "gDNA_HR_v1");
	map.put(MDA_Chimeric_v1, "MDA_Chimeric_v1");
	map.put(gDNA_Chimeric_v1, "gDNA_Chimeric_v1");
	map.put(SurePlex_v1, "SurePlex_v1");
	map.put(MalBac_v1, "MalBac_v1");
	map.put(Sureplex_HR, "Sureplex_HR");
	map.put(TB_INH, "TB-INH");
	map.put(DPD, "DPD");
	map.put(BRAF, "BRAF");
	map.put(UGT, "UGT");
	map.put(CMP, "CMP");
	map.put(VSP, "VSP");
	map.put(CMP_199, "CMP_199");
	map.put(GDD, "GDD");
	map.put(SureplexMosaic, "SureplexMosaic");
    }
}