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
            put("血", "B");
            put("组织液", "T");
            put("引流液", "D");
            put("关节液", "J");
            put("心包积液", "X");
            put("胸水", "H");
            put("脓液", "P");
            put("脑脊液", "C");
            put("阴道拭子", "V");
            put("腹水", "A");
            put("粪便", "F");
            put("尿液", "U");
            put("肺泡灌洗液", "BA");
        }
    };
}
