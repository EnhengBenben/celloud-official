package com.celloud.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.celloud.dao.AppDao;
import com.celloud.dao.CompanyDao;
import com.celloud.dao.DataDao;
import com.celloud.dao.ReportDao;
import com.celloud.dao.UserDao;
import com.celloud.sdo.App;
import com.celloud.sdo.Company;
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.User;
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
		Object companyNum = companyDao.getBigUserCompanyNum(conn, cmpId, role);
		Object reportNum = reportDao.getBigUserReportNum(conn, cmpId, role);
		Object appNum = appDao.getBigUserAPPNum(conn, cmpId, role);
		Object dataNum = dataDao.getBigUserDataNum(conn, cmpId, role);
		Object dataSize = dataDao.getBigUserDataSize(conn, cmpId, role);
		ConnectManager.close(conn);
		List<LoginLog> browser = appDao.getBrowerCount();
		resultMap.put("dataNum", dataNum);
		resultMap.put("dataSize", dataSize);
		resultMap.put("userNum", userNum);
		resultMap.put("companyNum", companyNum);
		resultMap.put("reportNum", reportNum);
		resultMap.put("appNum", appNum);
		resultMap.put("browser", browser);
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

	@Override
	public Map<String, Object> companyPreView(Integer cmpId, Integer role) {

		Map<String, Object> res = new HashMap<>();

		Connection conn = ConnectManager.getConnection();
		Object num = companyDao.getBigUserCompanyNum(conn, cmpId, role);
		Object userNum = userDao.getBigUsersUserNum(conn, cmpId, role);

		ConnectManager.close(conn);
		List<Map<String, Object>> mapList = companyDao.getCompanyNumEveryMonth(cmpId, role);
		res.put("num", num);
		res.put("userNum", userNum);
		res.put("mapList", mapList);

		return res;
	}

	private List<Company> getDataFileMap(Map<String, Object> map, String cmpName) {
		Object object = map.get(cmpName);
		if (object == null) {
			object = new ArrayList<Company>();
			map.put(cmpName, object);
		}
		return (List<Company>) object;
	}

	private Company getDataFileList(String time, String cmpName, List<Company> list) {
		Company df = null;
		for (Company f : list) {
			if (f.getYearMonth().equals(time) && f.getCompany_name().equals(cmpName)) {
				df = f;
				break;
			}
		}

		if (df == null) {
			df = new Company();
			df.setYearMonth(time);
			df.setCompany_name(cmpName);
			df.setNum(0);
		}
		return df;
	}

	@Override
	public Map<String, Object> getPreDataViewBigUesrNewCmp() {
		Connection conn = ConnectManager.getConnection();
		List<Company> cmpList = companyDao.getBigUserCmpNum(conn);
		List<User> userList = userDao.getBigUserList(conn);
		ConnectManager.close(conn);
		List<String> times = new ArrayList<>(48);
		Map<String, Object> map = new HashMap<>();
		Set<String> compnayNames = new HashSet<>(10);
		for (User u : userList) {
			compnayNames.add(u.getCompany_name());
		}
		map.put("companyNames", compnayNames);
		for (Company item : cmpList) {
			if (!times.contains(item.getYearMonth())) {
				times.add(item.getYearMonth());
			}
		}
		map.put("xAxis", times);
		for (String time : times) {
			for (String cmpName : compnayNames) {
				getDataFileMap(map, cmpName).add(getDataFileList(time, cmpName, cmpList));
			}
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> getCompanyBaseInfo() {
		Connection conn = ConnectManager.getConnection();
		List<Map<String, Object>> list = companyDao.getCompanyBaseInfo(conn);
		ConnectManager.close(conn);
		return list;
	}
}
