package com.celloud.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.celloud.dao.CompanyDao;
import com.celloud.sdo.Company;
import com.celloud.service.CompanyService;
import com.google.inject.Inject;

public class CompanyServiceImpl implements CompanyService {
	@Inject
	private CompanyDao companyDao;

	@Override
	public Object getBigUserCompanyNum(Integer companyId) {
		return companyDao.getBigUserCompanyNum(companyId);
	}

	@Override
	public Map<String, Object> getCompanyNumEveryMonth(Integer companyId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = companyDao
				.getCompanyNumEveryMonth(companyId);
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		String timeLine = "'2014-08','2014-09','2014-10','2014-11','2014-12'";
		String data = "";
		for(int i=8;i<=12;i++){
			String tmp = "";
			if (i < 10) {
				tmp = "2014-0" + i;
			} else {
				tmp = "2014-" + i;
			}
			data += mapListHasIt(list, tmp);
		}
		for (int i = 2015; i <= year; i++) {
			int end = 12;
			if (i == year) {
				end = month;
			}
			for (int j = 1; j <= end; j++) {
				String tmp = "";
				if (j < 10) {
					tmp = i + "-0" + j;
				} else {
					tmp = i + "-" + j;
				}
				data += mapListHasIt(list, tmp);
				timeLine += ",'" + tmp + "'";
			}
		}
		resultMap.put("timeLine", timeLine);
		resultMap.put("data", data.substring(0, data.length() - 1));
		return resultMap;
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
	public List<Company> getCompanyDetailById(Integer companyId) {
		List<Company> list = companyDao.getCompanyDetailById(companyId);
		List<Company> list1 = new ArrayList<Company>();
		for (Company c : list) {
			Company c1 = c;
			Double size = c.getFileSize();
			if (size != 0 || size != null) {
				size = size / (1024 * 1024 * 1024);
			} else {
				size = 0.0;
			}
			c1.setFileSize(size);
			list1.add(c1);
		}
		return list1;
	}

	@Override
	public Company getCompanyById(Integer compId) {
		return companyDao.getCompanyById(compId);
	}
}
