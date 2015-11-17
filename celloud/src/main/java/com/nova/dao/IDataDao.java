package com.nova.dao;

import java.util.Date;
import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.dao.impl.DataDaoImpl;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.Data;

/**
 * 类名称：IDataDao   类描述：数据管理接口   创建人：zl   创建时间：2013-5-20 下午3:48:44    
 * 
 * @version  1.0    
 */
@ImplementedBy(DataDaoImpl.class)
public interface IDataDao {
	
	/**
	 * 有多少个数据当前正在运行
	 * @return
	 */
	int dataRunning(String appIds);

    /**
     * 客户端用的增加方法
     * 
     * @param data
     * @return
     */
    Integer clientAdd(Data data);

    /**
     * 客户端用的修改方法
     * 
     * @param data
     * @return
     */
    Integer clientUpdate(Data data);

    /**
     * 获取当前登录人自己的数据列表
     * 
     * @param dataTag
     *            数据标签，搜索使用
     * @param page
     * @return
     */
    PageList<Data> getDataList(String dataTag, Page page, int userId, int type,
            String sort);

    /**
     * 为数据分配项目
     * 
     * @return
     */
    public int allocateDatasToProject(String dataIds, int projectId);

    /**
     * 添加上传文件信息
     * 
     * @param data
     * @return
     */
    public int addDataInfo(Data data);

    /**
     * 添加上传文件信息，返回数据对象
     * 
     * @param data
     * @return
     */
    public Data saveDataInfo(Data data);

    /**
     * 根据数据编号获取文件信息
     * 
     * @param dataKey
     * @return
     */
    public Data getDataByKey(String dataKey);

    /**
     * 根据dataId获取data全部信息
     * 
     * @param dataId
     * @return
     */
    public Data getDataById(String dataId);

    /**
     * 获取所有的数据编号
     * 
     * @return
     */
    public List<String> getAllDataKey();

    /**
     * 根据项目编号获取项目下的文件列表
     * 
     * @param projectId
     * @return
     */
    List<Data> getDataListByProjectId(int projectId);

    /**
     * 批量删除数据
     * 
     * @param dataIds
     * @return
     */
    public int delDatas(String dataIds);

    /**
     * 修改数据信息
     * 
     * @param data
     * @return
     */
    public int updateDataInfoByFileId(Data data);

    /**
     * 查询数据信息
     * 
     * @param userId
     *            ：可以为多个用户id，比如 3，4，5
     * @param start
     *            ：开始时间，2014-01-01
     * @param end
     *            ：结束时间，2014-01-02
     * @return
     */
    public List<Data> getDataList(String userId, String start, String end);

    /**
     * 获取用户最近输入数据的日期
     * 
     * @param userId
     * @return
     */
    public Date getLatestDate(int userId);

    /**
     * 根据用户编号查询数据列表
     * 
     * @param userId
     * @param page
     *            分页
     * @param fileType
     *            数据类型
     * @param softwareId
     * @return
     * 
     * @return PageList<Data>
     */
    public PageList<Data> getAppDataById(Integer userId, Page page,
	    String fileType, Integer softwareId);

    /**
     * 根据id修改别名
     * 
     * @param data
     * @return
     */
    public int updateAnotherNameById(Data data);

    /**
     * 根据多个dataKey获取数据列表
     * 
     * @param dataKeys
     * @return
     */
    public List<Data> getDataByDataKeys(String dataKeys, Integer userId);
}
