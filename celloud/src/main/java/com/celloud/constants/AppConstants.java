package com.celloud.constants;

import java.util.Arrays;
import java.util.List;

/**
 * 软件权限
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-10-27下午1:50:24
 * @version Revision: 1.0
 */
public class AppConstants {
    /** 开放，所有用户可见 */
    public static final int PUBLIC = 0;
    /** 私有，只有APP提供者的用户可见 */
    public static final int PRIVATE = 1;

    /** 未添加 */
    public static final int NOT_ADDED = 0;
    /** 已添加 */
    public static final int ALREADY_ADDED = 1;

    /** 未下线 */
    public static final int ON = 0;
    /** 已下线 */
    public static final int OFF = 1;
	/** 产品组：百菌探组+华木兰 */
	public static final List<Integer> BACTIVE_GROUP = Arrays.asList(118, 123, 133, 134, 135, 136, 137);
}
