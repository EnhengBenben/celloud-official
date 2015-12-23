package com.celloud.utils;

import java.util.Date;
import java.util.List;

import com.celloud.sdo.User;

public class SqlController {

	/**
	 * " and " + tbName + ".user_id not in (" + userids + ") ";
	 * @param tbName
	 * @param role
	 * @param userids
	 * @return
	 */
	public static String notUserId(String tbName, Integer role, String userids) {
		String sql = "";
		if (role == User.BIG_USER || role == User.ADMIN)
			sql = " and " + tbName + ".user_id not in (" + userids + ") ";
		return sql;
	}

	/**
	 * " and " + tbName + "." + colName + " not in (" + userNames + ") ";
	 * 
	 * @param tbName
	 * @param colName
	 * @param userNames
	 * @return
	 */
	public static String notUserName(int role, String tbName, String colName, String userNames) {
		String sql = null;
		if (role == User.BIG_USER || role == User.ADMIN)
			sql = " and " + tbName + "." + colName + " not in (" + userNames + ") ";
		return sql;
	}

	/**
	 * " where " + tbName + "." + colName + " not in (" + userNames + ") ";
	 * 
	 * @param tbName
	 * @param colName
	 * @param userNames
	 * @return
	 */
	public static String whereNotUserName(String tbName, String colName, String userNames) {
		String sql = null;
		sql = " where " + tbName + "." + colName + " not in (" + userNames + ") ";
		return sql;
	}

	/**
	 * " where " + tbName + ".user_id not in (" + userids + ") ";
	 * 
	 * @param tbName
	 * @param userids
	 * @return
	 */
	public static String whereNotUserId(String tbName, String userids) {
		return " where " + tbName + ".user_id not in (" + userids + ") ";
	}

	/**
	 * and tbName.colName not in (userName)
	 * 
	 * @param tbName
	 * @param colName
	 * @param role
	 * @param usernames
	 * @return
	 */
	public static String notUserName(String tbName, String colName, int role, String usernames) {
		String sql = "";
		if (role == User.BIG_USER || role == User.ADMIN)
			sql = " and " + tbName + "." + colName + " not in (" + usernames + ") ";
		return sql;
	}


	/**
	 * and tbName.colName = companyId
	 * 
	 * @param tbName
	 * @param colName
	 * @param role
	 * @param companyId
	 * @return
	 */
	public static String whereCompany(String tbName, String colName, int role, Integer companyId) {
		String sql = "";
		if (role == User.BIG_USER)
			sql = " and " + tbName + ". " + colName + " = " + companyId + " ";
		return sql;
	}

	/**
	 * and tbName.software_id = ?
	 * 
	 * @param tbName
	 * @param softwareId
	 * @return
	 */
	public static String whereSoftware(String tbName, String softwareId) {
		if (softwareId == null || softwareId.length() < 1)
			return " ";
		else
			return " and " + tbName + ".software_id = " + softwareId;
	}

	/**
	 * and " + tbName + "." + colName + " between ? and ? ";
	 * 
	 * @param tbName
	 * @param colName
	 * @param start
	 * @param end
	 * @return
	 */
	public static String whereifTimeNull(String tbName, String colName, Date start, Date end) {
		String sql = "";
		sql = " and " + tbName + "." + colName + " between ? and ? ";
		return sql;
	}

	/**
	 * limit topN
	 * 
	 * @param topN
	 * @return
	 */
	public static String limit(int topN) {
		if (topN > 0)
			return " limit " + topN;
		else
			return " ";
	}

	public static String whereIdNotIn(String tbName, String colName, List<Integer> ids) {
		if (ids == null || ids.size() < 1)
			return "";
		StringBuffer sb = new StringBuffer();
		for (Integer i : ids) {
			if (i != null)
				sb.append("" + i + ",");
		}
		if (sb.length() < 2)
			return "";
		String sql = sb.substring(0, sb.length() - 1);
		return " and " + tbName + "." + colName + " in ( " + sql + " ) ";
	}
}
