package com.celloud.manager.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.celloud.manager.constants.AppOffline;
import com.celloud.manager.constants.DataState;
import com.celloud.manager.constants.IconConstants;
import com.celloud.manager.constants.ReportPeriod;
import com.celloud.manager.constants.ReportType;
import com.celloud.manager.constants.UserRole;
import com.celloud.manager.mapper.AppMapper;
import com.celloud.manager.mapper.CompanyMapper;
import com.celloud.manager.mapper.DataFileMapper;
import com.celloud.manager.mapper.ReportMapper;
import com.celloud.manager.mapper.UserMapper;
import com.celloud.manager.model.App;
import com.celloud.manager.model.Company;
import com.celloud.manager.page.Page;
import com.celloud.manager.page.PageList;
import com.celloud.manager.service.CompanyService;
import com.celloud.manager.utils.PropertiesUtil;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private ReportMapper reportMapper;

    @Resource
    private AppMapper appMapper;

    @Resource
    private DataFileMapper dataFileMapper;

    @Override
    public Map<String, Object> companyGuideCount(Integer companyId) {
        Map<String, Object> map = new HashMap<String, Object>();
        int userNum = userMapper.countUser(companyId, DataState.ACTIVE, PropertiesUtil.testAccountIds);// 用户数量
        int companyNum = userMapper.countCompany(companyId, DataState.ACTIVE, PropertiesUtil.testAccountIds);// 医院数量
        List<Map<String, Object>> companyData = companyMapper.getCompanyNumOfMon(companyId, DataState.ACTIVE,
                PropertiesUtil.testAccountIds, "desc");
        map.put("userNum", userNum);
        map.put("companyNum", companyNum);
        map.put("companyData", companyData);
        return map;
    }

    @Override
    public Map<String, Object> getCompanyGuideData(Integer companyId) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Integer>> provinceData = userMapper.countCompanyByProvince(companyId, DataState.ACTIVE,
                PropertiesUtil.testAccountIds);// 医院地理分布统计
        List<Map<String, Object>> companyData = companyMapper.getCompanyNumOfMon(companyId, DataState.ACTIVE,
                PropertiesUtil.testAccountIds, "asc");
        map.put("provinceData", provinceData);
        map.put("companyData", companyData);
        return map;
    }

    @Override
    public Map<String, Object> getBigCustomerUserCountByMon() {
        List<Company> coms = companyMapper.getBigCustomerCompany(UserRole.BIG_CUSTOMER, DataState.ACTIVE);
        Map<String, Object> map = null;
        if (coms != null && coms.size() > 0) {
            List<String> seriesName = new ArrayList<String>();
            List<Object> list = new ArrayList<Object>();
            map = new HashMap<String, Object>();
            for (Company c : coms) {
                List<Map<String, Object>> obj = userMapper.getUserNumOfMon(c.getCompanyId(), DataState.ACTIVE,
                        PropertiesUtil.testAccountIds);
                list.add(obj);
                seriesName.add(c.getCompanyName());
            }
            map.put("seriesName", seriesName);
            map.put("userMon", list);
        }
        return map;
    }

    @Override
    public List<Company> getCompany(Integer companyId) {
        return companyMapper.getCompany(companyId, DataState.ACTIVE, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getCompanyReport(Integer companyId) {
        List<Map<String, Object>> list = reportMapper.countReportOfApp(companyId, DataState.ACTIVE,
                ReportPeriod.COMPLETE, PropertiesUtil.testAccountIds, AppOffline.ON);
        List<Company> cms = companyMapper.getCompany(companyId, DataState.ACTIVE, PropertiesUtil.testAccountIds);
        Map<Integer, Object> groups = new HashMap<Integer, Object>();
        for (Company c : cms) {
            groups.put(c.getCompanyId(), new ArrayList<Object>());
        }
        for (Map<String, Object> map : list) {
            List<Map<String, Object>> groupData = (List<Map<String, Object>>) groups.get(map.get("companyId"));
            groupData.add(map);
        }
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Company c : cms) {
            List<Map<String, Object>> groupData = (List<Map<String, Object>>) groups.get(c.getCompanyId());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("companyId", c.getCompanyId());
            map.put("companyName", c.getCompanyName());
            for (Map<String, Object> temp : groupData) {
                map.put("appId" + temp.get("appId"), temp.get("reportNum"));
            }
            result.add(map);
        }
        return result;
    }

    @Override
    public List<App> getAppOfBigCustomer(Integer companyId) {
        return appMapper.getAppOfBigCustomer(companyId, AppOffline.ON);
    }

    @Override
    public List<Map<String, Object>> bigCustomerDataCount() {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        List<Company> list = companyMapper.getBigCustomerCompany(UserRole.BIG_CUSTOMER, DataState.ACTIVE);
        List<Map<String, Integer>> userNumList = userMapper.getUserNumCount(DataState.ACTIVE,
                PropertiesUtil.testAccountIds);
        List<Map<String, Object>> companyNumList = companyMapper.getCompanyNumCount(DataState.ACTIVE,
                PropertiesUtil.testAccountIds);
        List<Map<String, Object>> dataFileSizeList = dataFileMapper.countDataFileByBigCustomer(DataState.ACTIVE,
                PropertiesUtil.testAccountIds);
        List<Map<String, Integer>> appRunNumList = appMapper.countAppRunNumByBigCustomer(AppOffline.ON,
                ReportType.PROJECT, ReportPeriod.COMPLETE, PropertiesUtil.testAccountIds);
        for (Company c : list) {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("companyId", c.getCompanyId());
            result.put("companyName", c.getCompanyName());
            result.put("createDate", c.getCreateDate());
            for (Map<String, Integer> map : userNumList) {
                if (map.get("companyId").equals(c.getCompanyId())) {
                    result.put("userNum", map.get("userNum"));
                    break;
                }
            }
            for (Map<String, Object> map : companyNumList) {
                if (map.get("companyId").equals(c.getCompanyId())) {
                    result.put("companyNum", map.get("companyNum"));
                    break;
                }
            }
            for (Map<String, Object> map : dataFileSizeList) {
                if (map.get("companyId").equals(c.getCompanyId())) {
                    result.put("dataNum", map.get("dataNum"));
                    result.put("dataSize", map.get("dataSize"));
                    break;
                }
            }
            for (Map<String, Integer> map : appRunNumList) {
                if (map.get("companyId").equals(c.getCompanyId())) {
                    result.put("runNum", map.get("runNum"));
                    break;
                }
            }
            resultList.add(result);
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> getCompanyNumCount() {
        return companyMapper.getCompanyNumCount(DataState.ACTIVE, PropertiesUtil.testAccountIds);
    }

    @Override
    public Company getCompanyById(Integer companyId) {
        return companyMapper.selectByPrimaryKey(companyId);
    }

    @Override
    public PageList<Company> getCompanyByPage(Page page, String keyword) {
        List<Company> list = companyMapper.getComanyByPage(DataState.ACTIVE, page, keyword);
        return new PageList<Company>(page, list);
    }

    @Override
    public Company getCompanyByIdAndState(Integer companyId) {
        if (companyId == null) {
            return null;
        }
        return companyMapper.getCompanyByIdAndState(companyId, DataState.ACTIVE);
    }

    @Override
    public List<Company> getAllCompany() {
        return companyMapper.getComanyList(DataState.ACTIVE);
    }

    @Override
    public List<Map<String, String>> getAllToSelect() {
        return companyMapper.getAllToSelect();
    }

    @Override
    public int addCompany(Company company) {
        company.setState(DataState.ACTIVE);
        String companyIcon = company.getCompanyIcon();
        if (StringUtils.isNotBlank(companyIcon)) {
            try {
				FileUtils.moveFile(new File(IconConstants.getTempPath(companyIcon)),
						new File(IconConstants.getCompanyPath(companyIcon)));
				IconConstants.cleanTemp();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return companyMapper.addCompany(company);
    }

}
