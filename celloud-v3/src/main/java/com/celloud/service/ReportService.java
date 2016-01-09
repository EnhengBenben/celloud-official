package com.celloud.service;

import java.util.List;
import java.util.Map;

import com.celloud.model.HBV;
import com.celloud.model.Pgs;
import com.celloud.page.Page;
import com.celloud.page.PageList;

/**
 * 报告接口
 * 
 * @author han
 * @date 2015年12月25日 下午3:47:07
 */
public interface ReportService {
    /**
     * 
     * 统计个人报告数量
     * 
     * @param userId
     * @return
     * @date 2015年12月25日 下午3:47:42
     */
    public Integer countReport(Integer userId);

    /**
     * 
     * 按时间统计个人报告数量
     * 
     * @param userId
     * @return
     * @date 2015年12月25日 下午3:47:42
     */
    public List<Map<String, String>> countReport(Integer userId, Integer time);

    /**
     * 报告检索
     * 
     * @param userId
     *            ：用户ID
     * @param pager
     *            ：分页类
     * @param condition
     *            ：检索条件
     * @param start
     *            ：开始时间
     * @param end
     *            ：结束时间
     * @param appId
     *            ：APPID
     * @return
     * @date 2016-1-5 下午3:26:05
     */
    PageList<Map<String, Object>> getReportPageList(Integer userId, Page pager,
            String condition, String start, String end, Integer appId);

    /**
     * 统计用户使用各App的次数
     * 
     * @param userId
     * @return
     */
    public List<Map<String, String>> countAppRunNum(Integer userId);

    /**
     * 获取HBV数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-8 下午4:40:37
     */
    public HBV getHBVReport(String dataKey, Integer projectId, Integer appId);

    /**
     * 获取PGS报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-9 上午2:56:01
     */
    public Pgs getPgsReport(String dataKey, Integer projectId, Integer appId);

    /**
     * 系统统计业务
     * 
     * @param userId
     * @return
     */
    public Map<String, Object> systemCount(Integer userId);

    /**
     * HBV 数据参数同比
     * 
     * @param appId
     * @param path
     * @return
     * @date 2016-1-9 下午2:57:29
     */
    public String getHBVCount(Integer appId, String path);

    /**
     * EGFR 和 KRAS 数据参数同比
     * 
     * @param appId
     * @param path
     * @param length
     * @return
     * @date 2016-1-9 下午3:07:38
     */
    public String getEGFRCount(Integer appId, String path, String length);

    /**
     * HCV 数据参数同比
     * 
     * @param appId
     * @param path
     * @return
     * @date 2016-1-9 下午3:18:39
     */
    public String getHCVCount(Integer appId, String path);

    /**
     * PGS 数据参数同比
     * 
     * @param appId
     * @param path
     * @param columns
     * @return
     * @date 2016-1-9 下午3:25:22
     */
    public String getPGSCount(Integer appId, String path, String columns);
}
