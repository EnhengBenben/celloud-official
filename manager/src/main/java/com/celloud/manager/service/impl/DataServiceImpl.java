package com.celloud.manager.service.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.celloud.manager.constants.DataState;
import com.celloud.manager.constants.UserRole;
import com.celloud.manager.dao.ReportDao;
import com.celloud.manager.mapper.CompanyMapper;
import com.celloud.manager.mapper.DataFileMapper;
import com.celloud.manager.mapper.UserMapper;
import com.celloud.manager.model.Company;
import com.celloud.manager.model.User;
import com.celloud.manager.model.mongo.HBV;
import com.celloud.manager.service.DataService;
import com.celloud.manager.utils.PropertiesUtil;

@Service("dataService")
public class DataServiceImpl implements DataService{

    @Resource
    private DataFileMapper dataFileMapper;
    @Resource
    private CompanyMapper companyMapper;
    @Resource
    private ReportDao reportDao;
    
    @Resource
    private UserMapper userMapper;

    @Override
    public int totalDataNum(Integer companyId) {
        return dataFileMapper.countDataFile(companyId, DataState.ACTIVE,PropertiesUtil.testAccountIds);
    }

    @Override
    public Long totalDataSize(Integer companyId) {
        return dataFileMapper.countDataFileSize(companyId, DataState.ACTIVE,PropertiesUtil.testAccountIds);
    }

    @Override
    public Map<String,Object> getBigCustomerDataCountByMon() {
        List<Company> coms=companyMapper.getBigCustomerCompany(UserRole.BIG_CUSTOMER, DataState.ACTIVE);
        Map<String,Object> map=null;
        if(coms!=null&&coms.size()>0){
            List<String> seriesName=new ArrayList<String>();
            List<Object> list=new ArrayList<Object>();
            map=new HashMap<String,Object>();
            for(Company c:coms){
                List<Map<String,Object>> obj=dataFileMapper.countDataFileOfMonth(c.getCompanyId(), DataState.ACTIVE, PropertiesUtil.testAccountIds);
                list.add(obj);
                seriesName.add(c.getCompanyName());
            }
            map.put("seriesName", seriesName);
            map.put("dataMon", list);
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> getBigCustomerDataCountByMon(Integer companyId,String order) {
        return dataFileMapper.countDataFileByMonth(companyId, DataState.ACTIVE, PropertiesUtil.testAccountIds,order);
    }

    @Override
    public List<Map<String, Object>> getBigCustomerDataCountByUser(Integer companyId) {
        return dataFileMapper.countDataFileByUser(companyId, DataState.ACTIVE, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getBigCustomerDataCountByCompany(Integer companyId) {
        return dataFileMapper.countDataFileByCompany(companyId, DataState.ACTIVE, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getBigCustomerDataCount() {
        return dataFileMapper.countBigCustomerDataFile(DataState.ACTIVE, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<User> getUser(Integer companyId) {
        return userMapper.findUserByBigCustomer(companyId, DataState.ACTIVE, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getUserData(String userIds, String start, String end) {
        if(StringUtils.isBlank(userIds)){
            return null;
        }
        if(start!=null&&end!=null){
            return dataFileMapper.getExportData(userIds, start, end);
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getWeekUserLogin(Integer companyId) {
        return dataFileMapper.getWeekUserLogin(companyId, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getWeekAppRun(Integer companyId) {
        return dataFileMapper.getWeekAppRun(companyId, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getWeekDataSize(Integer companyId) {
        return dataFileMapper.getWeekDataSize(companyId, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getHistoryWeekUserLogin(Integer companyId) {
        return dataFileMapper.getHistoryWeekUserLogin(companyId, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getHistoryWeekActiveUser(Integer companyId) {
        return dataFileMapper.getHistoryWeekActiveUser(companyId, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getHistoryWeekAppRun(Integer companyId) {
        return dataFileMapper.getHistoryWeekAppRun(companyId, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getHistoryWeekAppActive(Integer companyId) {
        return dataFileMapper.getHistoryWeekAppActive(companyId, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getHistoryWeekDataSize(Integer companyId) {
        return dataFileMapper.getHistoryWeekDataSize(companyId, PropertiesUtil.testAccountIds);
    }

    @Override
    public Map<String, Map<String, String>> getHBVOtherSiteByUserId(Integer companyId) {

        // 结果Map
        // {"206":{"count":"20","percent":"10%"},"207":{"count":"30","percent":"30%%}}
        Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
        // 封装过滤条件
        Map<String, Object> filters = new HashMap<String, Object>();
        // 如果是超级管理员, 则查找所有hbv的报告, 不用筛选用户
        List<Integer> userIds = null;
        if (null != companyId) { // 如果是大客户, 则查找该大客户下用户的hbv的报告
            // 查找该大客户下的所有用户, 封装过滤条件
            userIds = new ArrayList<Integer>();
            List<User> users = userMapper.findUserByBigCustomer(companyId, DataState.ACTIVE,
                    PropertiesUtil.testAccountIds);
            for (User user : users) {
                userIds.add(user.getUserId());
            }
            filters.put("userId in", userIds);
        }

        // 该用户下所有的hbv报告
        List<HBV> hbvs = reportDao.queryByFilters(HBV.class, filters, new String[] { "other", "fileId", "dataKey" });

        // 保存datakey, 用于去除重复运行
        Map<String, Object> dataKeyMap = new HashMap<String, Object>();
        // 保存md5, 用于去除重复文件
        Map<String, Object> md5Map = new HashMap<String, Object>();
        // hbv对应的文件map集合{"21":{"md5":"xfdsghfsgh"},"22":{"md5":"dgdfsgsdgf"},...}
        Map<Integer, Map<String, String>> md5FileIdMap = dataFileMapper.getMd5FileIdMap(userIds, 82);
        // 遍历hbv数据报告, 去除重复运行, 去除重复文件, 将有用信息封装map
        Float totalSite = 0.0f; // 总的位点数量用于统计半分比
        NumberFormat nt = NumberFormat.getPercentInstance(); // 数字格式化
        nt.setMinimumFractionDigits(2);
        for (HBV hbv : hbvs) {
            if (hbv.getOther() != null && hbv.getOther().size() > 0) { // 其他位点存指才去统计
                if (dataKeyMap.containsKey(hbv.getDataKey())) { // map中已经存在该datakey代表已经存在该文件(去除重复运行)
                    continue;
                } else {
                    // 判断是否包含在md5Map中(去除重复文件)
                    if (md5FileIdMap.containsKey(hbv.getFileId())) { // 代表该文件属于md5有重复的文件
                        // 判断是否已经添加过了
                        if (md5Map.get(md5FileIdMap.get(hbv.getFileId()).get("md5")) != null) { // 代表保存md5的map中已经存在该MD5(去除重复文件)
                            continue;
                        } else {
                            md5Map.put(md5FileIdMap.get(hbv.getFileId()).get("md5"), new Object());
                        }
                    }
                    dataKeyMap.put(hbv.getDataKey(), new Object()); // 代表第一次读取到该datakey作为键存入map中
                    // 该hbv数据既不属于重复运行, 也不属于重复文件, 读取该数据的新的突变位点, 封装map
                    // 其他突变位点统计
                    Map<String, String> map = hbv.getOther();
                    for (String site : map.keySet()) {
                        totalSite++;
                        site = site.split("_")[0]; // 获取位点值
                        if (result.containsKey(site)) { // 结果map中已经统计过该位点,
                                                        // 则取出该位点的Map
                            Map<String, String> siteMap = result.get(site);
                            siteMap.put("count", String.valueOf(Integer.parseInt(siteMap.get("count")) + 1));// 数量+1
                            siteMap.put("percent", Integer.parseInt(siteMap.get("count")) / totalSite + ""); // 重新计算百分比
                        } else {
                            // 构造一个结果map放到result中
                            Map<String, String> siteMap = new HashMap<String, String>();
                            siteMap.put("count", "1");
                            siteMap.put("percent", 1 / totalSite + "");
                            result.put(site, siteMap);
                        }
                    }
                    for (Map.Entry<String, Map<String, String>> entry : result.entrySet()) {
                        entry.getValue().put("percent",
                                nt.format(Float.parseFloat(entry.getValue().get("count")) / totalSite));
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Map<Integer, Map<String, String>> getSiteInfo(Integer companyId, Integer site) {
        // 结果Map
        Map<Integer, Map<String, String>> result = new HashMap<Integer, Map<String, String>>();
        Map<String, Object> filters = new HashMap<String, Object>();
        // 如果是超级管理员, 则查找所有hbv的报告, 不用就5筛选用户
        List<Integer> userIds = null;
        if (null != companyId) { // 如果是大客户, 则查找该大客户下用户的hbv的报告
            // 查找该大客户下的所有用户, 封装过滤条件
            userIds = new ArrayList<Integer>();
            List<User> users = userMapper.findUserByBigCustomer(companyId, DataState.ACTIVE,
                    PropertiesUtil.testAccountIds);
            for (User user : users) {
                userIds.add(user.getUserId());
            }
            filters.put("userId in", userIds);
        }
        filters.put("other." + site + "_new_png exists", true);

        // 该大客户下所有的hbv报告
        List<HBV> hbvs = reportDao.queryByFilters(HBV.class, filters, new String[] { "companyId", "dataKey" });

        // 遍历hbv报告, companyId为key封装结果集
        for (HBV hbv : hbvs) {
            Map<String,String> valueMap = new HashMap<String, String>();
            valueMap.put("dataKey", hbv.getDataKey());
            result.put(hbv.getCompanyId(), valueMap);
        }
        // 查询该大客户下的医院
        List<Company> companys = companyMapper.getCompany(companyId, DataState.ACTIVE, PropertiesUtil.testAccountIds);
        for (Company company : companys) {
            // 如果包含这个医院, 则将医院名字放入值map中
            if (result.containsKey(company.getCompanyId())) {
                result.get(company.getCompanyId()).put("companyName", company.getCompanyName());
            }
        }
        return result;
    }

}
