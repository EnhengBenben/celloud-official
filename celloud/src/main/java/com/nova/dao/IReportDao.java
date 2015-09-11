package com.nova.dao;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;
import com.nova.dao.impl.ReportDaoImpl;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.CmpDrug;
import com.nova.sdo.Data;
import com.nova.sdo.Report;
import com.nova.sdo.SoftCMPGene;
import com.nova.sdo.Software;
import com.nova.sdo.UserReport;

/**
 * 类名称：IReportDao   类描述：报告 创建人：zl   创建时间：2013-5-22 上午9:46:10    
 * 
 * @version      
 */
@ImplementedBy(ReportDaoImpl.class)
public interface IReportDao {
    /**
     * 获取项目报告列表
     * 
     * @param userId
     * @param page
     * @return
     */
    PageList<Map<String, String>> getReportList(int userId, Page page);

    /**
     * @param userId
     * @param page
     * @return
     */
    PageList<Map<String, String>> getReportList(int userId, Page page,
	    String proName, String start, String end, int appId);

    /**
     * 修改报告打印完整信息
     * 
     * @param userId
     * @param softwareId
     * @param id
     * @param flag
     *            ：flag为0，则id 为数据报告；flag为1，则id为项目报告
     * @param context
     * @return
     */
    int updatePrintContext(int userId, int softwareId, int id, int flag,
	    String context);

    /**
     * 查询报告打印全部信息
     * 
     * @param userId
     * @param softwareId
     * @param id
     * @param flag
     *            ：flag为0，则id 为数据报告；flag为1，则id为项目报告
     * @return
     */
    String getPrintContext(int userId, int softwareId, int id, int flag);

    /**
     * 添加数据报告
     * 
     * @param report
     * @return
     */
    int addReportInfo(Report report);

    /**
     * 添加项目报告
     * 
     * @param report
     * @return
     */
    int addProReportInfo(Report report);

    /**
     * 获取数据报告
     * 
     * @param userId
     * @param appId
     * @param date
     * @param page
     * @return
     */
    PageList<UserReport> getDataReportList(int userId, int appId, String start,
	    String end, String fileName, Page page);

    /**
     * 根据项目编号获取app列表
     * 
     * @param fileId
     * @return
     */
    List<Report> getReportListByProId(int projectId);

    /**
     * 用户的获取数据报告总数量
     * 
     * @return
     */
    int getTotalReportNum(int userId);

    /**
     * 获取用户私有的报告总数量
     * 
     * @param userId
     * @return
     */
    int getPrivateReportNum(int userId);

    /**
     * 修改报告状态为指定状态
     * 
     * @param reportId
     * @param state
     * @return
     */
    int updateReportState(int reportId, int state);

    /**
     * 修改报告状态为指定状态
     * 
     * @param projectId
     * @param softwareId
     * @param state
     * @return
     */
    int updateReportStateByProSoftId(int userId, int projectId, int softwareId,
	    int state, String context);

    /**
     * 根据用户编号、文件编号获取报告编号
     * 
     * @param userId
     * @param fileId
     * @return
     */
    String getReportIdByUserFileId(String userId, String fileId);

    /**
     * 进化树保存报告信息
     * 
     * @param project
     * @return
     */
    int saveTreeProject(Integer userId, String dataIds, int softwareId);

    boolean existsReport(int userId, int softwareId, int dataId);

    int saveProDataReport(int userId, List<Data> dataList, int softwareId);

    /**
     * 根据项目编号查询软件编号
     * 
     * @param projectId
     * @return
     */
    int getSoftwareIdByProjectId(int projectId);

    int updateReportStateByProjectId(int projectId, int state);

    int delReport(int userId, List<Data> dataList, int softwareId);

    /**
     * 验证项目是否已存在报告
     * 
     * @param userId
     * @param projectId
     * @return
     */
    boolean proExistsReport(int userId, int projectId);

    /**
     * 验证项目是否已存在报告
     * 
     * @param userId
     * @param projectId
     * @return
     */
    List<String> checkProjectHasReport(int userId, int projectId);

    /**
     * 删除项目报告
     * 
     * @param projectId
     * @return
     */
    boolean deleteProReport(int userId, int projectId);

    /**
     * 添加SNP数据报告
     * 
     * @param userId
     * @param projectId
     * @param dataIds
     * @param softId
     * @return
     */
    int saveSnpReport(int userId, int projectId, String dataIds, int softId);

    /**
     * 验证一个数据是否已关联某个app
     * 
     * @param dataId
     * @param softwareId
     * @return
     */
    boolean hasDataReport(int dataId, int softwareId);

    /**
     * 验证一个项目是否已关联某个app
     * 
     * @param dataId
     * @param softwareId
     * @return
     */
    boolean hasProReport(int projectId, int softwareId);

    /**
     * 删除指定报告
     * 
     * @param reportId
     * @return
     */
    boolean deleteReport(int reportId);

    boolean deleteReportByProSoftId(int reportId, int projectId, int softwareId);

    /**
     * 该方法是项目运行Qc程序时要为数据添加报告
     * 
     * @param userId
     * @param dataList
     * @param softwareId
     * @return
     */
    int saveQcProDataReport(int userId, List<Data> dataList, int softwareId);

    /**
     * 修改报告状态
     * 
     * @param userId
     * @param fileId
     * @param softwareId
     * @param state
     * @return
     */
    int updateReportState(int userId, int fileId, int softwareId, int state);

    /**
     * 修改报告状态
     * 
     * @param userId
     * @param fileId
     * @param softwareId
     * @param state
     * @return
     */
    int updateReportState(int fileId, int softwareId, int state);

    /**
     * Qc修改项目状态
     * 
     * @param projectId
     * @param softwareId
     * @param state
     * @return
     */
    int updateReportByDataKeyProjectId(List<Data> dataList, int projectId,
	    int softwareId, int state);

    /**
     * 根据软件编号、文件编号获取用户编号
     * 
     * @param softwareId
     * @param fileId
     * @return
     */
    public int getUserIdBySoftIdDataKey(int softwareId, int fileId);

    public Report getReportById(int resportId);

    /**
     * 检查项目是否有报告
     * 
     * @param proIds
     * @return 返回项目名列表
     */
    List<String> checkProsReport(String proIds);

    /**
     * 检查数据miRNA app是否运行完成
     * 
     * @param dataKey
     * @return
     */
    public boolean checkDatamiRNAReportState(String dataKey, int softwareId);

    /**
     * 检查项目miRNA app是否运行完成
     * 
     * @param dataKey
     * @return
     */
    public boolean checkPromiRNAReportState(int projectId, int softwareId);

    /**
     * 根据数据编号检查数据miRNA报告是否运行完成
     * 
     * @param dataIds
     * @param softwareId
     * @return
     */
    public List<String> checkDatasReportState(String dataIds, int softwareId);

    /**
     * 添加数据报告
     * 
     * @param userId
     * @param dataIds
     * @param state
     * @return
     */
    public int addDatasReport(int userId, int softwareId, String dataIds,
	    int state);

    /**
     * 根据数据编号及appId判断该数据在该app下是否运行
     * 
     * @param dataIds
     * @return
     */
    public List<String> getFileNamesByDataIds(String dataIds, Integer appId);

    /**
     * 根据项目编号获取添加的软件编号列表
     * 
     * @param projectId
     * @return
     */
    public List<Software> getSiftListByProId(int projectId);

    public int updateReportReadStateByData(int fileId, int softwareId);

    public int updateReportReadStateByPro(int projectId, int softwareId);

    /*************************** 新增报告模块 *********************************************/
    /**
     * 查询所有已经开始运行的数据报告
     * 
     * @param userId
     * @return
     */
    public PageList<UserReport> getAllDataReport(int userId, String fileName,
	    Page page);

    /**
     * 根据文件编号、软件编号删除报告
     * 
     * @param fileId
     * @param softwareId
     * @return
     */
    public int deleteReportByFileSoftId(int fileId, int softwareId);

    /**
     * 根据文件编号,获得文件路径
     * 
     * @param fileId
     * @param
     * @return
     */
    public String getFilePath(int fileId);

    /**
     * 根据CMP中50个基因的基因名获取基因的详细信息
     * 
     * @param gname
     * @return
     */
    SoftCMPGene getCMPGeneByName(String gname);

    /**
     * 添加CMP个性化用药信息
     * 
     * @param cmpDrug
     * @return
     */
    int addCmpDrug(CmpDrug cmpDrug);
}
