package com.celloud.constants;

public enum Bank {
	/**
	 * 中国工商银行
	 */
	ICBC("1025", "中国工商银行"),
	/**
	 * 中国建设银行
	 */
	CCB("1051", "中国建设银行"),
	/**
	 * 中国银行
	 */
	BOC("104", "中国银行"),
	/**
	 * 中国农业银行
	 */
	ABC("103", "中国农业银行"),
	/**
	 * 交通银行
	 */
	BCM("3407", "交通银行"),
	/**
	 * 邮政储蓄银行
	 */
	PSBC("3230", "中国邮政储蓄银行"),
	/**
	 * 招商银行
	 */
	CMB("3080", "招商银行"),
	/**
	 * 中信银行
	 */
	CITIC("313", "中信银行"),
	/**
	 * 上海浦东发展银行
	 */
	SPDB("314", "上海浦东发展银行"),
	/**
	 * 兴业银行
	 */
	CIB("309", "兴业银行"),
	/**
	 * 中国民生银行
	 */
	CMBC("305", "中国民生银行"),
	/**
	 * 光大银行
	 */
	CEB("312", "光大银行"),
	/**
	 * 平安银行
	 */
	SPABANK("307", "平安银行"),
	/**
	 * 华夏银行
	 */
	HUAXIA("311", "华夏银行"),
	/**
	 * 北京银行
	 */
	BOB("310", "北京银行"),
	/**
	 * 广东发展银行
	 */
	GDB("3061", "广东发展银行"),
	/**
	 * 上海银行
	 */
	SHBANK("326", "上海银行"),

	/**
	 * 北京农商行
	 */
	BJRCB("335", "北京农商行"),
	/**
	 * 重庆农商银行
	 */
	CQRCB("342", "重庆农商银行"),
	/**
	 * 上海农商行
	 */
	SRCB("343", "上海农商行"),
	/**
	 * 南京银行
	 */
	BON("316", "南京银行"),
	/**
	 * 宁波银行
	 */
	NBCB("302", "宁波银行"),
	/**
	 * 杭州银行
	 */
	HZCB("324", "杭州银行"),
	/**
	 * 成都银行
	 */
	BOCD("336", "成都银行"),
	/**
	 * 青岛银行
	 */
	QDCCB("3341", "青岛银行"),
	/**
	 * 恒丰银行
	 */
	EGBANK("344", "恒丰银行"),
	/**
	 * 渤海银行
	 */
	CBHB("317", "渤海银行"),
	/**
	 * 厦门银行
	 */
	XMCCB("401", "厦门银行"),
	/**
	 * 陕西信合
	 */
	SXNXS("402", "陕西信合"),
	/**
	 * 浙江稠州银行
	 */
	czcb("403", "浙江稠州银行"),
	/**
	 * 贵州农信
	 */
	GZNX("404", "贵州农信");
	private String bankCode;
	private String bankName;

	private Bank(String bankCode, String bankName) {
		this.bankCode = bankCode;
		this.bankName = bankName;
	}

	public String getBankName() {
		return this.bankName;
	}

	public String getBankCode() {
		return this.bankCode;
	}

	public static String findBankNameBySortName(String sortName) {
		return Bank.valueOf(sortName).getBankName();
	}

	public static String findBankNameByBankCode(String bankCode) {
		for (Bank bank : Bank.values()) {
			if (bank.getBankCode().equals(bankCode)) {
				return bank.getBankName();
			}
		}
		return null;
	}

	public static String findBankCodeByBankName(String bankName) {
		for (Bank bank : Bank.values()) {
			if (bank.getBankName().equals(bankName)) {
				return bank.getBankCode();
			}
		}
		return null;
	}
}
