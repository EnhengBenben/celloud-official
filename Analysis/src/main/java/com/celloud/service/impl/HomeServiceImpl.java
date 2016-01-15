package com.celloud.service.impl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.celloud.dao.AppDao;
import com.celloud.dao.CompanyDao;
import com.celloud.dao.DataDao;
import com.celloud.dao.ReportDao;
import com.celloud.dao.UserDao;
import com.celloud.sdo.App;
import com.celloud.sdo.Company;
import com.celloud.service.HomeService;
import com.celloud.utils.ConnectManager;
import com.google.inject.Inject;

public class HomeServiceImpl implements HomeService {
	@Inject
	private UserDao userDao;
	@Inject
	private DataDao dataDao;
	@Inject
	private CompanyDao companyDao;
	@Inject
	private ReportDao reportDao;
	@Inject
	private AppDao appDao;

	@Override
	public Map<String, Object> toHome(Integer cmpId, Integer role) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Connection conn = ConnectManager.getConnection();
		Object userNum = userDao.getBigUsersUserNum(conn, cmpId, role);
		Object dataNum = dataDao.getBigUserDataNum(conn, cmpId, role);
		Object dataSize = dataDao.getBigUserDataSize(conn, cmpId, role);
		Object companyNum = companyDao.getBigUserCompanyNum(conn, cmpId, role);
		Object reportNum = reportDao.getBigUserReportNum(conn, cmpId, role);
		Object appNum = appDao.getBigUserAPPNum(conn, cmpId, role);
		ConnectManager.close(conn);
		resultMap.put("userNum", userNum);
		resultMap.put("dataNum", dataNum);
		resultMap.put("dataSize", dataSize);
		resultMap.put("companyNum", companyNum);
		resultMap.put("reportNum", reportNum);
		resultMap.put("appNum", appNum);
		return resultMap;
	}

	@Override
	public Map<String, Object> toCompanyReport(Integer cmpId, Integer role) {
		Connection conn = ConnectManager.getConnection();
		List<App> listApps = appDao.getApps(conn, role, cmpId);

		List<Map<String, Object>> userAppRun = reportDao.getCompanyRunEachApp(conn, role, cmpId);
		List<Company> cmpList = reportDao.getCompanyRunEachApp_Company(conn, role, cmpId);
		Map<String, Object> res = new HashMap<>();
		res.put("listApps", listApps);
		res.put("userAppRun", userAppRun);
		res.put("cmpList", cmpList);
		return res;
	}

}
