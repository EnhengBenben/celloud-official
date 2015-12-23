package com.celloud.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.celloud.dao.CompanyDao;
import com.celloud.sdo.Company;
import com.celloud.sdo.Data;
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.Software;
import com.celloud.service.CompanyService;
import com.celloud.utils.LogUtil;
import com.google.inject.Inject;

public class CompanyServiceImpl implements CompanyService {
	@Inject
	private CompanyDao companyDao;

	@Override
	public Object getBigUserCompanyNum(Integer companyId,int role) {
		return companyDao.getBigUserCompanyNum(companyId,role);
	}

	@Override
	public Map<String, Object> getCompanyNumEveryMonth(Integer companyId,Integer role) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = companyDao.getCompanyNumEveryMonth(companyId,role);
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		String timeLine = "2014-08,2014-09,2014-10,2014-11,2014-12";
		String data = "";
		for (int i = 8; i <= 12; i++) {
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
				timeLine += "," + tmp ;
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
	public List<Company> getCompanyDetailById(Integer companyId,Integer role) {
		List<Company> list = companyDao.getCompanyDetailById(companyId,role);
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

	@Override
	public List<Map<String, Object>> getProvince(Integer companyId,int role) {
		return companyDao.getProvince(companyId,role);
	}

	@Override
	public List<Software> getCompanyRunAppNumByCId(Integer companyId) {
		return companyDao.getCompanyRunAppNumByCId(companyId,"software");
	}

	@Override
	public List<Data> getCompanyUpLoadGroupMonthByCId(Integer companyId) {
		List<Data> list = companyDao.getCompanyUpLoadDataByCId(companyId,"month");
		LinkedList<Data> res = new LinkedList<>();
		if (list != null && list.size() > 0) {
			Data data = list.get(0);
			Data endData = list.get(list.size() - 1);
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
						Data obj = DateHandle(list, ym);
						if (obj != null)
							res.add(obj);
						else {
							Data d = new Data();
							d.setYearMonth(ym);
							d.setFileNum(0);
							res.add(d);
						}
					}
				} else if (y > startYear && y < endYear) {
					for (int m = 1; m <= 12; m++) {
						String ym = catYearMonth(y, m);
						Data obj = DateHandle(list, ym);
						if (obj != null)
							res.add(obj);
						else {
							Data d = new Data();
							d.setYearMonth(ym);
							d.setFileNum(0);
							res.add(d);
						}
					}
				} else if (y > startYear && y == endYear) {
					for (int m = 1; m <= endMonth; m++) {
						String ym = catYearMonth(y, m);
						Data obj = DateHandle(list, ym);
						if (obj != null)
							res.add(obj);
						else {
							Data d = new Data();
							d.setYearMonth(ym);
							d.setFileNum(0);
							res.add(d);
						}
					}
				} else if (y == startYear && y < endYear) {
					for (int m = startMonth; m <= 12; m++) {
						String ym = catYearMonth(y, m);
						Data obj = DateHandle(list, ym);
						if (obj != null)
							res.add(obj);
						else {
							Data d = new Data();
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

	public Data DateHandle(List<Data> list, String ym) {
		for (Data data : list) {
			if (data.getYearMonth().equals(ym))
				return data;
		}
		return null;
	}
	public Software SoftwareHandle(List<Software> list, String ym) {
		for (Software data : list) {
			if (data.getYearMonth().equals(ym))
				return data;
		}
		return null;
	}

	@Override
	public List<Software> getCompanyRunAppNumGroupByMonth(Integer companyId) {
		List<Software> list = companyDao.getCompanyRunAppNumByCId(companyId, "month");
		LinkedList<Software> res = new LinkedList<>();
		if (list != null && list.size() > 0) {
			Software data = list.get(0);
			Software endData = list.get(list.size() - 1);
			int startYear = Integer.parseInt(data.getYearMonth().substring(0, 4));
			int startMonth = Integer.parseInt(data.getYearMonth().substring(5, 7));
			int endYear = Integer.parseInt(endData.getYearMonth().substring(0, 4));
			int endMonth = Integer.parseInt(endData.getYearMonth().substring(5, 7));
			/**检查数据中的数据是否有时间间隔*/
			// 2013~2015遍历年
			for (int y = startYear; y <= endYear; y++) {
				// 月份遍历
				if (y == startYear && y == endYear) {
					for (int m = startMonth; m <= endMonth; m++) {
						String ym = catYearMonth(y, m);
						Software obj = SoftwareHandle(list, ym);
						if (obj != null)
							res.add(obj);
						else {
							Software d = new Software();
							d.setYearMonth(ym);
							d.setRunNum(0);
							res.add(d);
						}
					}
				} else if (y > startYear && y < endYear) {
					for (int m = 1; m <= 12; m++) {
						String ym = catYearMonth(y, m);
						Software obj = SoftwareHandle(list, ym);
						if (obj != null)
							res.add(obj);
						else {
							Software d = new Software();
							d.setYearMonth(ym);
							d.setRunNum(0);
							res.add(d);
						}
					}
				} else if (y > startYear && y == endYear) {
					for (int m = 1; m <= endMonth; m++) {
						String ym = catYearMonth(y, m);
						Software obj = SoftwareHandle(list, ym);
						if (obj != null)
							res.add(obj);
						else {
							Software d = new Software();
							d.setYearMonth(ym);
							d.setRunNum(0);
							res.add(d);
						}
					}
				} else if (y == startYear && y < endYear) {
					for (int m = startMonth; m <= 12; m++) {
						String ym = catYearMonth(y, m);
						Software obj = SoftwareHandle(list, ym);
						if (obj != null)
							res.add(obj);
						else {
							Software d = new Software();
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
	public List<Software> getCompanyRunAppNumGroupByWeek(Integer companyId) {
		
		return companyDao.getCompanyRunAppNumByCId(companyId,"week");
	}

	@Override
	public List<Data> getCompanyUpLoadGroupWeekByCId(Integer companyId) {
		return companyDao.getCompanyUpLoadDataByCId(companyId, "week");
	}

	@Override
	public List<LoginLog> getCompanyLoginInWeek(Integer cmpId,Date start, Date end, List<Integer> cmpIdList,Integer role) {
		return companyDao.getCompanyLoginInWeek(cmpId, start, end, cmpIdList,role);
	}

	@Override
	public List<LoginLog> getCompanyLoginInMonth(Integer cmpId, Date start, Date end, List<Integer> companyList,Integer role) {
		return companyDao.getCompanyLoginInMonth(cmpId, start, end, companyList,role);
	}

	@Override
	public List<Data> getCompanyFileInWeek(Integer cmpId, Date start, Date end, List<Integer> cmpIdList,Integer role) {
		return companyDao.getCompanyFileInWeek(cmpId, start, end, cmpIdList,role);
	}

	@Override
	public List<Data> getCompanyFileInMonth(Integer cmpId,Date start, Date end,List<Integer> cmpIdList,Integer role) {
		return companyDao.getCompanyFileInMonth(cmpId, start, end,cmpIdList, role);
	}

	@Override
	public List<Software> getCompanySoftwareInWeek(Integer cmpId,Date start, Date end, List<Integer> cmpIds,Integer role) {
		return companyDao.getCompanySoftwareInWeek(cmpId, start, end, cmpIds,role);
	}

	@Override
	public List<Software> getCompanySoftwareInMonth(Integer cmpId,Date start, Date end, List<Integer> cmpIds,Integer role) {
		return companyDao.getCompanySoftwareInMonth(cmpId, start, end, cmpIds,role);
	}

	@Override
	public List<Company> getCompanyClient(Integer cmpId, Integer role) {
		return companyDao.getCompanyClient(cmpId, role);
	}
}
