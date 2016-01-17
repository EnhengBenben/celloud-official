package com.celloud.service;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.celloud.model.CmpReport;
import com.celloud.model.HBV;
import com.celloud.model.MIB;
import com.celloud.model.Pgs;
import com.celloud.model.Report;
import com.celloud.model.Split;
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
    public List<Map<String, String>> countReport(Integer userId, String time);

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
     * 获取 MIB 报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-10 下午10:33:49
     */
    public MIB getMIBReport(String dataKey, Integer projectId, Integer appId);

    /**
     * 获取 Split 报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-10 下午10:42:22
     */
    public Split getSplitReport(String dataKey, Integer projectId, Integer appId);

    /**
     * 获取 CMP 和 GDD 数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-10 下午10:51:50
     */
    public CmpReport getCMPReport(String dataKey, Integer projectId,
            Integer appId);

    /**
     * 系统统计业务
     * 
     * @param userId
     * @return
     */
    public Map<String, Object> systemCount(Integer userId);

    /**
     * 新增项目报告
     * 
     * @param report
     * @return
     * @author leamo
     * @date 2016-1-10 下午5:00:12
     */
    public Integer insertProReport(Report report);

    /**
     * 为多个APP添加报告
     * 
     * @param report
     * @param appProId
     * @return 失败的APPid
     * @author leamo
     * @date 2016年1月14日 下午2:11:52
     */
    public List<Integer> insertMultipleProReport(Report report,
            Map<Integer, Integer> appProId, String[] dataIds);

    /**
     * 新增数据报告
     * 
     * @param report
     * @param dataIds
     * @return
     * @author leamo
     * @date 2016-1-10 下午5:01:31
     */
    public Integer insertDataReport(Report report, String[] dataIds);
    
    /**
     * HBV 数据参数同比
     * 
     * @param appId
     * @param path
     * @return
     * @date 2016-1-9 下午2:57:29
     */
    public String hbvCompare(Integer appId, String path);

    /**
     * EGFR 和 KRAS 数据参数同比
     * 
     * @param appId
     * @param path
     * @param length
     * @return
     * @date 2016-1-9 下午3:07:38
     */
    public String egfrCompare(Integer appId, String path, String length);

    /**
     * HCV 数据参数同比
     * 
     * @param appId
     * @param path
     * @return
     * @date 2016-1-9 下午3:18:39
     */
    public String hcvCompare(Integer appId, String path);

    /**
     * PGS 数据参数同比
     * 
     * @param appId
     * @param path
     * @param columns
     * @return
     * @date 2016-1-9 下午3:25:22
     */
    public String pgsCompare(Integer appId, String path, String columns);

    /**
     * split数据参数同比
     * 
     * @param id
     * @return原始数据 [平均质量,序列数]列表 分数据 [平均质量,序列数]列表
     * @author leamo
     * @date 2016年1月17日 下午1:38:30
     */
    public Map<String, List<List<Float>>> splitCompare(ObjectId id);

    /**
     * HBV 统计
     * 
     * @param userId
     * @return
     * @date 2016-1-10 上午12:24:41
     */
    public Map<String, Object> hbvCount(Integer userId);

    /**
     * PGS 统计
     * 
     * @param userId
     * @return
     * @date 2016-1-10 上午12:36:22
     */
    public List<Pgs> pgsCount(Integer userId);

    /**
     * CMP 统计
     * 
     * @param userId
     * @return
     * @date 2016-1-10 上午12:46:57
     */
    public Map<String, Object> cmpCount(Integer userId);

    /**
     * 项目报告运行完成
     * 
     * @param projectId
     * @param context
     * @return
     * @author leamo
     * @date 2016年1月15日 上午10:58:52
     */
    public Integer reportCompeleteByProId(Integer projectId,
            String context);
}
