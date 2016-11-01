package com.celloud.constants;

import java.util.List;
import java.util.Map;

import com.celloud.model.mysql.Metadata;

public class SampleTypes {
	/**
	 * 样本index
	 */
	public static List<Metadata> index = null;
	/**
	 * 样本index的String格式
	 */
	public static List<String> indexString = null;
	/**
	 * 样本类型
	 */
	public static List<Metadata> types = null;
	/**
	 * 样本类型的map格式
	 */
	public static Map<String, String> typesMap = null;
	/**
	 * 文库index
	 */
	public static List<Metadata> libraryIndex = null;

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
