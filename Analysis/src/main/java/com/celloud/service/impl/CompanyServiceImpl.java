package com.celloud.service.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.celloud.dao.AppDao;
import com.celloud.dao.CompanyDao;
import com.celloud.dao.UserDao;
import com.celloud.sdo.App;
import com.celloud.sdo.Company;
import com.celloud.sdo.DataFile;
import com.celloud.service.CompanyService;
import com.celloud.utils.ConnectManager;
import com.google.inject.Inject;

public class CompanyServiceImpl implements CompanyService {
	@Inject
	private CompanyDao companyDao;
	@Inject
	private AppDao appDao;
	@Inject
	private UserDao userDao;
	Logger log = Logger.getLogger(this.getClass().getSimpleName());

	@Override
	public List<Map<String, Object>> getCompanyNumEveryMonth(Integer companyId, Integer role) {
		List<Map<String, Object>> list = companyDao.getCompanyNumEveryMonth(companyId, role);
		return list;
	}

	/**
	 * 工具方法： 判断list中是否有指定时间的数据
	 * 
	 * @param list
	 *            {["createDate":"2014-01","num","0"]}
	 * @param date
	 *            "2014-02"
	 * @return
	 */
	public String mapListHasIt(List<Map<String, Object>> list, String date) {
		boolean isEq = false;
		int num = 0;
		String data = "";
		for (Map<String, Object> m : list) {
			if (date.equals(m.get("createDate").toString())) {
				isEq = true;
				break;
			}
			num++;
		}
		if (isEq) {
			data = list.get(num).get("num") + ",";
		} else {
			data = "0,";
		}
		return data;
	}

	@Override
	public List<Company> getCompanyDetailById(Integer companyId, Integer role, String orderBy) {
		return companyDao.getCompanyDetailById(companyId, role, orderBy);
	}

	@Override
	public Company getCompanyById(Integer compId) {
		return companyDao.getCompanyById(compId);
	}

	@Override
	public List<Map<String, Object>> getProvince(Integer companyId, int role) {
		return companyDao.getProvince(companyId, role);
	}

	@Override
	public List<App> getCompanyRunAppNumByCId(Integer companyId) {
		return companyDao.getCompanyRunAppNumByCId(companyId);
	}

	@Override
	public List<DataFile> getCompanyUpLoadGroupMonthByCId(Integer companyId) {
		List<DataFile> list = companyDao.getCompanyUpLoadDataByCId(companyId, "month");
		LinkedList<DataFile> res = new LinkedList<>();
		if (list != null && list.size() > 0) {
			DataFile data = list.get(0);
			DataFile endData = list.get(list.size() - 1);
			int startYear = Integer.parseInt(data.getYearMonth().substring(0, 4));
			int startMonth = Integer.parseInt(data.getYearMonth().substring(5, 7));
			int endYear = Integer.parseInt(endData.getYearMonth().substring(0, 4));
			int endMonth = Integer.parseInt(endData.getYearMonth().substring(5, 7));
			// 2013~2015遍历年
			for (int y = startYear; y <= endYear; y++) {
				// 月份遍历
				if (y == startYear && y == endYear) {
					for (int m = startMonth; m <= endMonth; m++) {
						String ym = catYearMonth(y, m);
						DataFile obj = DateHandle(list, ym);
						if (obj != null)
							res.add(obj);
						else {
							DataFile d = new DataFile();
							d.setYearMonth(ym);
							d.setFileNum(0);
							res.add(d);
						}
					}
				} else if (y > startYear && y < endYear) {
					for (int m = 1; m <= 12; m++) {
						String ym = catYearMonth(y, m);
						DataFile obj = DateHandle(list, ym);
						if (obj != null)
							res.add(obj);
						else {
							DataFile d = new DataFile();
							d.setYearMonth(ym);
							d.setFileNum(0);
							res.add(d);
						}
					}
				} else if (y > startYear && y == endYear) {
					for (int m = 1; m <= endMonth; m++) {
						String ym = catYearMonth(y, m);
						DataFile obj = DateHandle(list, ym);
						if (obj != null)
							res.add(obj);
						else {
							DataFile d = new DataFile();
							d.setYearMonth(ym);
							d.setFileNum(0);
							res.add(d);
						}
					}

				} else if (y == startYear && y < endYear) {
					for (int m = startMonth; m <= 12; m++) {
						String ym = catYearMonth(y, m);
						DataFile obj = DateHandle(list, ym);
						if (obj != null)
							res.add(obj);
						else {
							DataFile d = new DataFile();
							d.setYearMonth(ym);
							d.setFileNum(0);
							res.add(d);
						}
					}
				}
			}
		}
		return res;
	}

	public String catYearMonth(int year, int month) {
		if (month < 10)
			return year + "-0" + month;
		else
			return year + "-" + month;
	}

	public DataFile DateHandle(List<DataFile> list, String ym) {
		for (DataFile data : list) {
			if (data.getYearMonth().equals(ym))
				return data;
		}
		return null;
	}

	public App SoftwareHandle(List<App> list, String ym) {
		for (App data : list) {
			if (data.getYearMonth().equals(ym))
				return data;
		}
		return null;
	}

	@Override
	public List<App> getCompanyRunAppNumGroupByMonth(Integer companyId) {
		List<App> list = null;
		// companyDao.getCompanyRunAppNumByCId(companyId, "month");
		LinkedList<App> res = new LinkedList<>();
		if (list != null && list.size() > 0) {
			App data = list.get(0);
			App endData = list.get(list.size() - 1);
			int startYear = Integer.parseInt(data.getYearMonth().substring(0, 4));
			int startMonth = Integer.parseInt(data.getYearMonth().substring(5, 7));
			int endYear = Integer.parseInt(endData.getYearMonth().substring(0, 4));
			int endMonth = Integer.parseInt(endData.getYearMonth().substring(5, 7));
			/** 检查数据中的数据是否有时间间隔 */
			// 2013~2015遍历年
			for (int y = startYear; y <= endYear; y++) {
				// 月份遍历
				if (y == startYear && y == endYear) {
					for (int m = startMonth; m <= endMonth; m++) {
						String ym = catYearMonth(y, m);
						App obj = SoftwareHandle(list, ym);
						if (obj != null)
							res.add(obj);
						else {
							App d = new App();
							d.setYearMonth(ym);
							d.setRunNum(0);
							res.add(d);
						}
					}
				} else if (y > startYear && y < endYear) {
					for (int m = 1; m <= 12; m++) {
						String ym = catYearMonth(y, m);
						App obj = SoftwareHandle(list, ym);
						if (obj != null)
							res.add(obj);
						else {
							App d = new App();
							d.setYearMonth(ym);
							d.setRunNum(0);
							res.add(d);
						}
					}
				} else if (y > startYear && y == endYear) {
					for (int m = 1; m <= endMonth; m++) {
						String ym = catYearMonth(y, m);
						App obj = SoftwareHandle(list, ym);
						if (obj != null)
							res.add(obj);
						else {
							App d = new App();
							d.setYearMonth(ym);
							d.setRunNum(0);
							res.add(d);
						}
					}
				} else if (y == startYear && y < endYear) {
					for (int m = startMonth; m <= 12; m++) {
						String ym = catYearMonth(y, m);
						App obj = SoftwareHandle(list, ym);
						if (obj != null)
							res.add(obj);
						else {
							App d = new App();
							d.setYearMonth(ym);
							d.setRunNum(0);
							res.add(d);
						}
					}
				}
			}
		}
		return res;
	}

	@Override
	public List<App> getCompanyRunAppNumGroupByWeek(Integer companyId) {

		// return companyDao.getCompanyRunAppNumByCId(companyId, "week");
		return null;
	}

	@Override
	public List<DataFile> getCompanyUpLoadGroupWeekByCId(Integer companyId) {
		return companyDao.getCompanyUpLoadDataByCId(companyId, "week");
	}

	@Override
	public List<Company> getCompanyClient(Integer cmpId, Integer role) {
		return companyDao.getCompanyClient(cmpId, role);
	}

	@Override
	public List<Company> BigUserList() {
		return companyDao.BigUserList();
	}

	@Override
	public Map<String, Object> getCompanyFile(int role, int cmpId, Date start, Date end, int topN) {
		Connection conn = ConnectManager.getConnection();
		HashMap<String, Object> result = new HashMap<>();
		result.put("hFileNum", companyDao.getCompanyFileNum(conn, role, cmpId, start, end, topN));
		result.put("hSize", companyDao.getCompanyFileSize(conn, role, cmpId, start, end, topN));
		List<App> list = appDao.getAppList(conn, role, cmpId, start, end, topN);
		result.put("appRun", list);
		List<DataFile> fileNum = userDao.getUserFileNum(conn, role, cmpId, start, end, topN);
		List<DataFile> fileSize = userDao.getUserFileSize(conn, role, cmpId, start, end, topN);
		List<App> appRun = userDao.getUserRunApp(conn, role, cmpId, start, end, topN);
		result.put("uFileNum", fileNum);
		result.put("uSize", fileSize);
		result.put("uAppRun", appRun);
		ConnectManager.close(conn);
		return result;
	}

	@Override
	public Map<String, List> getList(int role, int cmpId, Date start, Date end, int topN) {
		Connection conn = ConnectManager.getConnection();
		HashMap<String, List> result = new HashMap<>();
		result.put("hFile", companyDao.getCompanyFileNum(conn, role, cmpId, start, end, topN));
		List<App> list = appDao.getAppList(conn, role, cmpId, start, end, topN);
		result.put("appRun", list);
		List<DataFile> fileNum = userDao.getUserFileNum(conn, role, cmpId, start, end, topN);
		result.put("uFile", fileNum);
		ConnectManager.close(conn);

		return result;
	}
}
