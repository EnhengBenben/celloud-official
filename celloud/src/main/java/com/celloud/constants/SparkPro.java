package com.celloud.constants;

/**
 * Spark 配置
 * 
 * @author lin
 * 
 */
public class SparkPro {

    /**
     * Spark最大任务数
     */
    public static final int MAXTASK = 12;

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
	public static final String PYTHONPATH = "python /share/biosoft/perl/PGS_MG/python/runover.py";
    
    /**
     * 由python进行全部的后续处理
     */
    public static final String PYTHONRUNOVER = "python /share/biosoft/perl/PGS_MG/python/project_run_over.py";

    /**
     * 任务运行结束的python路径
     */
    public static final String TASKOVERPY = "/share/biosoft/perl/PGS_MG/python/task_over.py";

    /**
     * SGE 杀任务
     */
    public static final String SGEKILL = "perl /share/biosoft/perl/PGS_MG/bin/to_qdel.pl";

    /**
     * spark 杀任务
     */
    public static final String SPARKKILL = "perl /share/biosoft/perl/wangzhen/16s-meta-pipeline/spark_kill.pl";

	/**
	 * rocky生成pdf路径
	 */
	public static final String ROCKYPDF = "python /share/biosoft/perl/PGS_MG/python/app/Rocky_PDF.py";

}
