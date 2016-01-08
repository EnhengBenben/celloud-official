package com.celloud.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.DataState;
import com.celloud.constants.ReportType;
import com.celloud.dao.ReportDao;
import com.celloud.mapper.ReportMapper;
import com.celloud.model.HBV;
import com.celloud.model.Pgs;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.ReportService;

/**
 * 报告接口 实现类
 * 
 * @author han
 * @date 2015年12月25日 下午3:50:02
 */
@Service("reportService")
public class ReportServiceImpl implements ReportService {
    @Resource
    ReportMapper reportMapper;
    @Resource
    ReportDao reportDao;

    @Override
    public Integer countReport(Integer userId) {
        return reportMapper.countReport(userId, DataState.ACTIVE, ReportType.PROJECT);
    }

    @Override
    public List<Map<String, String>> countReport(Integer userId, Integer time) {
        return reportMapper.countReportByTime(userId, time, DataState.ACTIVE, ReportType.PROJECT);
    }

    @Override
    public PageList<Map<String, Object>> getReportPageList(Integer userId, Page pager, String condition, String start,
            String end, Integer appId) {
        long s = System.currentTimeMillis();
        System.out.println("【Service】 start " + s);
        List<Map<String, Object>> list = reportMapper.getReportList(userId, pager, condition, start, end, appId);
        long e = System.currentTimeMillis();
        System.out.println("【Service】 end " + e);
        System.out.println("【Service】 time: " + (e - s));
        return new PageList<>(pager, list);
    }
    @Override
	public List<Map<String, String>> countAppRunNum(Integer userId) {
		return reportMapper.countAppRunNumByUserId(userId);
    }

    @Override
    public HBV getHBVReport(String dataKey, Integer projectId, Integer appId) {
        HBV hbv = reportDao.getDataReport(HBV.class, dataKey, projectId, appId);
        if (hbv == null)
            return null;
        // 其他突变位点排序
        Map<String, String> map = hbv.getOther();
        if (map != null) {
            String[] array = new String[map.size()];
            int i = 0;
            for (Map.Entry<String, String> m : map.entrySet()) {
                array[i] = m.getKey();
                i++;
            }
            Arrays.sort(array);
            StringBuffer sb = new StringBuffer();
            for (String s : array) {
                sb.append(map.get(s)).append(",");
            }
            String img = sb.toString();
            if (img.length() > 1) {
                hbv.setImgString(img.substring(0, img.length() - 1));
            } else {
                hbv.setImgString("");
            }
        }
        // jstl 处理 \n 很困难，就在 java 端处理
        hbv.setReporttxt(hbv.getReporttxt().replace("\n", "<br/>"));
        return hbv;
    }

    @Override
    public Pgs getPgsReport(String dataKey, Integer projectId, Integer appId) {
        return reportDao.getDataReport(Pgs.class, dataKey, projectId, appId);
    }
}
