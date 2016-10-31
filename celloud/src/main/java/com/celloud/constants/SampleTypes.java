package com.celloud.constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleTypes {
    public static final List<String> index = Arrays.asList("BSS1:AG", "BSS2:AT",
            "BSS3:CA", "BSS4:CG", "BSS5:GA", "BSS6:GT", "BSS7:CTGG",
            "BSS8:CTTG", "BSS9:ACGT", "BSS10:GGCT", "BSS11:AAGC", "BSS12:CTTA",
            "BSS13:CTGT", "BSS14:GCAG", "BSS15:TCAC", "BSS16:AACT",
            "BSS17:ACCAGAG", "BSS18:TGAGAGT", "BSS19:TGATACG", "BSS20:GGCAGAC",
            "BSS21:CTTCTAA", "BSS22:GCATCGT", "BSS23:TCTACTG", "BSS24:TACTTCC");

    @SuppressWarnings("serial")
    public static final Map<String, String> types = new HashMap<String, String>() {
        {
            put("血液", "BL");
            put("组织液", "TF");
            put("引流液", "DF");
            put("关节液", "JF");
            put("心包积液", "PE");
            put("胸水", "PF");
            put("脓液", "PU");
            put("脑脊液", "CF");
            put("阴道拭子", "VS");
            put("腹水", "AS");
            put("粪便", "FE");
            put("尿液", "UR");
            put("肺泡灌洗液", "BA");
        }
    };

    /** 暂存状态 */
    public static final int NOTADD = 0;
    /** 已提交 */
    public static final int ISADD = 1;

    /** 采样 */
    public static final int SAMPLING = 0;
    /** 入库 */
    public static final int SCAN_STORAGE = 1;
    /** 提DNA */
    public static final int TOKEN_DNA = 2;
    /** 建库 */
    public static final int BUID_LIBRARY = 3;
    /** 建库完成 */
    public static final int IN_LIBRARY = 4;
}
