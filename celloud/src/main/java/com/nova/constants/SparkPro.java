package com.nova.constants;

import java.util.Arrays;
import java.util.List;

/**
 * Spark 配置
 * 
 * @author lin
 * 
 */
public class SparkPro {

    /**
     * Spark节点数量及需要的端口数量
     */
    public static final int NODES = 25;

    /**
     * Spark 任务投递从哪个端口开始
     */
    public static final int START = 4040;

    /**
     * 配置 Tools 路径
     */
    public static final String TOOLSPATH = "/share/data/webapps/Tools/upload/";

    /**
     * 配置python路径
     */
    public static final String PYTHONPATH = "/share/biosoft/perl/PGS_MG/python/runover.py";

    /**
     * 需要投递到spark集群的appId
     */
    public static final List<String> apps = Arrays.asList("86", "92", "93",
            "99", "100", "101");

    /**
     * SGE 杀任务
     */
    public static final String SGEKILL = "perl /share/biosoft/perl/PGS_MG/bin/to_qdel.pl";

    /**
     * spark 杀任务
     */
    public static final String SPARKKILL = "perl /share/biosoft/perl/PGS_MG/bin/spark_kill.pl";
    
    /**
     * celloud端直接投递到SGE的appId
     */
    public static final List<String> SGEAPPS = Arrays.asList("116");
}
