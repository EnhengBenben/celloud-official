package com.celloud.manager.constants;

import java.util.ArrayList;
import java.util.List;

public enum Bank {
	/**
	 * 中国工商银行
	 */
	ICBC("1025", "中国工商银行", "1025", 1),
	/**
	 * 中国建设银行
	 */
	CCB("1051", "中国建设银行", "1051", 1),
	/**
	 * 中国银行
	 */
	BOC("104", "中国银行", "104", 1),
	/**
	 * 中国农业银行
	 */
	ABC("103", "中国农业银行", "103", 1),
	/**
	 * 交通银行
	 */
	BCM("3407", "交通银行", "3407", 1),
	/**
	 * 邮政储蓄银行
	 */
	PSBC("3230", "中国邮政储蓄银行", "3230", 1),
	/**
	 * 招商银行
	 */
	CMB("3080", "招商银行", "3080", 1),
	/**
	 * 中信银行
	 */
	CITIC("313", "中信银行", "313", 1),
	/**
	 * 上海浦东发展银行
	 */
	SPDB("314", "上海浦东发展银行", "314", 1),
	/**
	 * 兴业银行
	 */
	CIB("309", "兴业银行", "309", 1),
	/**
	 * 中国民生银行
	 */
	CMBC("305", "中国民生银行", "305", 1),
	/**
	 * 光大银行
	 */
	CEB("312", "光大银行", "312", 1),
	/**
	 * 平安银行
	 */
	SPABANK("307", "平安银行", "307", 1),
	/**
	 * 华夏银行
	 */
	HUAXIA("311", "华夏银行", "311", 1),
	/**
	 * 北京银行
	 */
	BOB("310", "北京银行", "310", 1),
	/**
	 * 广东发展银行
	 */
	GDB("3061", "广东发展银行", "3061", 1),
	/**
	 * 上海银行
	 */
	SHBANK("326", "上海银行", "326", 1),

	/**
	 * 北京农商行
	 */
	BJRCB("335", "北京农商行", "335", 1),
	/**
	 * 重庆农商银行
	 */
	CQRCB("342", "重庆农商银行", "342", 1),
	/**
	 * 上海农商行
	 */
	SRCB("343", "上海农商行", "343", 1),
	/**
	 * 南京银行
	 */
	BON("316", "南京银行", "316", 1),
	/**
	 * 宁波银行
	 */
	NBCB("302", "宁波银行", "302", 1),
	/**
	 * 杭州银行
	 */
	HZCB("324", "杭州银行", "324", 1),
	/**
	 * 成都银行
	 */
	BOCD("336", "成都银行", "336", 1),
	/**
	 * 青岛银行
	 */
	QDCCB("3341", "青岛银行", "3341", 1),
	/**
	 * 恒丰银行
	 */
	EGBANK("344", "恒丰银行", "344", 1),
	/**
	 * 渤海银行
	 */
	CBHB("317", "渤海银行", "317", 1),
	/**
	 * 厦门银行
	 */
	XMCCB("401", "厦门银行", "401", 1),
	/**
	 * 陕西信合
	 */
	SXNXS("402", "陕西信合", "402", 1),
	/**
	 * 浙江稠州银行
	 */
	czcb("403", "浙江稠州银行", "403", 1),
	/**
	 * 贵州农信
	 */
	GZNX("404", "贵州农信", "404", 1),
	/**
	 * 工商银行企业银行
	 */
	ICBC_B2B("9102", "中国工商银行", "1025", 2),
	/**
	 * 农业银行企业银行
	 */
	ABC_B2B("9103", "中国农业银行", "103", 2),
	/**
	 * 交通银行企业银行
	 */
	BCM_B2B("9104", "交通银行", "3407", 2),
	/**
	 * 中国建设银行企业银行
	 */
	CCB_B2B("9105", "中国建设银行", "1051", 2),
	/**
	 * 招商银行企业银行
	 */
	CMB_B2B("9107", "招商银行", "3080", 2),
	/**
	 * 光大银行企业银行
	 */
	CEB_B2B("9108", "光大银行", "312", 2),
	/**
	 * 中国银行企业银行
	 */
	BOC_B2B("9109", "中国银行", "104", 2),
	/**
	 * 平安银行企业银行
	 */
	SPABANK_B2B("9110", "平安银行", "307", 2);
	private String bankCode;
	private String bankName;
	private String bankLogo;
	private Integer bankType;

	private Bank(String bankCode, String bankName, String bankLogo, Integer bankType) {
		this.bankCode = bankCode;
		this.bankName = bankName;
		this.bankLogo = bankLogo;
		this.bankType = bankType;
	}

	public String getBankName() {
		return this.bankName;
	}

	public String getBankCode() {
		return this.bankCode;
	}

	public String getBankLogo() {
		return this.bankLogo;
	}

	public Integer getBankType() {
		return this.bankType;
	}

	public static List<Bank> listB2C() {
		List<Bank> banks = new ArrayList<>();
		for (Bank bank : Bank.values()) {
			if (bank.bankType == 1) {
				banks.add(bank);
			}
		}
		return banks;
	}

	public static List<Bank> listB2B() {
		List<Bank> banks = new ArrayList<>();
		for (Bank bank : Bank.values()) {
			if (bank.bankType == 2) {
				banks.add(bank);
			}
		}
		return banks;
	}

	public static List<Bank> listCC() {
		List<Bank> banks = new ArrayList<>();
		for (Bank bank : Bank.values()) {
			if (bank.bankType == 3) {
				banks.add(bank);
			}
		}
		return banks;
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
