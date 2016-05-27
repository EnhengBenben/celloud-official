package com.celloud.service;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.celloud.model.mongo.ABINJ;
import com.celloud.model.mongo.BRAF;
import com.celloud.model.mongo.BSI;
import com.celloud.model.mongo.CmpFilling;
import com.celloud.model.mongo.CmpGeneSnpResult;
import com.celloud.model.mongo.CmpReport;
import com.celloud.model.mongo.DPD;
import com.celloud.model.mongo.EGFR;
import com.celloud.model.mongo.GddDiseaseDict;
import com.celloud.model.mongo.HBV;
import com.celloud.model.mongo.HCV;
import com.celloud.model.mongo.KRAS;
import com.celloud.model.mongo.MIB;
import com.celloud.model.mongo.Oncogene;
import com.celloud.model.mongo.Pgs;
import com.celloud.model.mongo.S16;
import com.celloud.model.mongo.Split;
import com.celloud.model.mongo.TBINH;
import com.celloud.model.mongo.TBRifampicin;
import com.celloud.model.mongo.TaskQueue;
import com.celloud.model.mongo.Translate;
import com.celloud.model.mongo.UGT;
import com.celloud.model.mysql.Report;
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
     * 获取TBINH下是否是野生型的数量, 0:都不是1:是野生2:不是野生
     * 
     * @param userId
     * @param geneName
     * @param isWild
     * @return
     */
    public Integer getTBINHisWildByGeneNameAndUserId(Integer userId, String simpleGeneName, Integer isWild);

    /**
     * 获取TBINH报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author MQ
     */
    public TBINH getTBINHReport(String dataKey, Integer projectId, Integer appId);

    /**
     * 获取BRAF报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author MQ
     */
    public BRAF getBRAFReport(String dataKey, Integer projectId, Integer appId);

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
    PageList<Map<String, Object>> getReportPageList(Integer userId, Page pager, String condition, String start,
            String end, Integer appId, Integer belongs);

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
     * 获取ABINJ数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author lin
     * @date 2016年4月13日下午4:15:29
     */
    public ABINJ getABINJReport(String dataKey, Integer projectId, Integer appId);

    /**
     * 获取16S数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author lin
     * @date 2016年4月28日下午12:14:18
     */
    public S16 get16SReport(String dataKey, Integer projectId, Integer appId);

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
     * 获取Oncogene报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author lin
     * @date 2016年1月28日下午7:15:48
     */
    public Oncogene getOncogeneReport(String dataKey, Integer projectId, Integer appId);

    /**
     * 获取HCV数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author lin
     * @date 2016年3月7日下午5:06:00
     */
    public HCV getHCVReport(String dataKey, Integer projectId, Integer appId);

    /**
     * 获取Translate数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author lin
     * @date 2016年4月19日下午5:45:22
     */
    public Translate getTranslateReport(String dataKey, Integer projectId, Integer appId);

    /**
     * 获取EGFR报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author lin
     * @date 2016年3月10日下午2:46:20
     */
    public EGFR getEGFRReport(String dataKey, Integer projectId, Integer appId);

    /**
     * 获取KRAS报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author lin
     * @date 2016年3月22日下午4:36:17
     */
    public KRAS getKRASReport(String dataKey, Integer projectId, Integer appId);

    /**
     * 获取TBRifampicin报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     */
    public TBRifampicin getTBRifampicinReport(String dataKey, Integer projectId, Integer appId);

    /**
     * 获取UGT报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author lin
     * @date 2016年3月25日下午3:58:15
     */
    public UGT getUGTReport(String dataKey, Integer projectId, Integer appId);

    /**
     * 获取DPD报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author lin
     * @date 2016年3月25日下午3:58:26
     */
    public DPD getDPDReport(String dataKey, Integer projectId, Integer appId);

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
     * 修改MIB报告用户填写部分
     * 
     * @param mib
     * @author leamo
     * @date 2016年2月18日 上午11:05:37
     */
    public Integer updateMIBFilling(MIB mib);

    /**
     * 修改BSI报告用户填写部分
     * 
     * @param bsi
     * @author leamo
     * @date 2016年4月18日 下午4:33:55
     */
    public Integer updateBSIFilling(BSI bsi);

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
    public CmpReport getCMPReport(String dataKey, Integer projectId, Integer appId);

    /**
     * 修改CMP用户填写信息
     * 
     * @param id
     * @param cmpFill
     * @author leamo
     * @date 2016年2月1日 下午6:08:35
     */
    public void updateCmpFilling(ObjectId id, CmpFilling cmpFill);

    /**
     * 获取 BSI 报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-10 下午10:33:49
     */
    public BSI getBSIReport(String dataKey, Integer projectId, Integer appId);

    /**
     * 系统统计业务
     * 
     * @param userId
     * @return
     */
    public Map<String, Object> systemCount(Integer userId);

    /**
     * 获取GDD未检测到的疾病
     * 
     * @param unnormalGene
     * @return
     * @author leamo
     * @date 2016年1月18日 下午3:08:01
     */
    public List<GddDiseaseDict> getGddDiseaseDictNormal(String[] fields, Map<String, List<String>> conditionMap,
            String sortField);

    /**
     * 获取GDD总表检测突变数统计
     * 
     * @param dataKey
     * @param proId
     * @param appId
     * @return
     */
    public List<CmpGeneSnpResult> getGddResult(String dataKey, Integer proId, Integer appId);

    /**
     * 新增项目报告
     * 
     * @param report
     * @return
     * @author leamo
     * @date 2016-1-10 下午5:00:12
     */
    public Integer insertProReport(Report report, Integer dataId);

    /**
     * 为多个APP添加报告
     * 
     * @param report
     * @param appProId
     * @return 失败的APPid
     * @author leamo
     * @date 2016年1月14日 下午2:11:52
     */
    public List<Integer> insertMultipleProReport(Report report, Map<Integer, Integer> appProId, String[] dataIds);

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
     * KRAS 数据参数同比
     * 
     * @param appId
     * @param length
     * @return
     * @date 2016-1-9 下午3:07:38
     */
    public String krasCompare(Integer length);

    /**
     * EGFR 数据参数同比
     * 
     * @param length
     * @return
     * @date 2016-1-9 下午3:07:38
     */
    public String egfrCompare(Integer length);

    /**
     * TBRifampicin 数据参数同比
     * 
     * @return
     */
    public String tbrifampicinCompare();

    /**
     * HCV 数据参数同比
     * 
     * @param appId
     * @param path
     * @return
     * @date 2016-1-9 下午3:18:39
     */
    public String hcvCompare();

    /**
     * PGS 数据参数同比 对dao层返回的数据进行拼接返回到页面
     * 
     * @param appId
     * @param path
     * @param columns
     * @return
     * @date 2016-1-9 下午3:25:22
     */
    public String pgsCompare(Integer appId, String columns);

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
    public Integer reportCompeleteByProId(Integer projectId, String context);

    /**
     * 检索数据报告
     * 
     * @param userId
     * @param appId
     * @param projectId
     * @param fileId
     * @param flag
     * @return
     * @author lin
     * @date 2016年1月18日上午11:19:09
     */
    Report getReport(Integer userId, Integer appId, Integer projectId, Integer fileId, Integer flag);

    /**
     * 根据外键组合修改非外键字段<br>
     * 
     * 外键组合：user_id（可选），app_id（可选），file_id（flag==0时必选），project_id（必选），flag（可选，0-
     * 数据报告，1-项目报告，Null－数据&项目）<br>
     * 修改（均可选）：period，readed，state，end_date，print_context，context
     * 
     * @param report
     * @return
     * @author lin
     * @date 2016年1月19日上午10:22:28
     */
    int updateReport(Report report);

    /**
     *  保存spark排队任务
     * 
     * @param tq
     * @author lin
     * @date 2016年2月26日下午1:45:27
     */
    public void saveTask(TaskQueue tq);

    /**
     * 根据项目ID检索任务信息
     * 
     * @param projectId
     * @return
     * @author lin
     * @date 2016年2月26日下午1:51:09
     */
    public TaskQueue getTaskQueue(Integer projectId);

    /**
     * 根据projectId检索项目报告
     * 
     * @param projectId
     * @return
     * @author lin
     * @date 2016年5月16日上午11:29:35
     */
    public Report getReportByProjectId(Integer projectId);

    public Integer updateSplitReport(Split split);

    /**
     * 删除bsi报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @author leamo
     * @date 2016年5月27日 上午11:42:08
     */
    public void deleteBSIReport(String dataKey, Integer projectId,
            Integer appId);

}
