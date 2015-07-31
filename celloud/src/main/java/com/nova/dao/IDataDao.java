package com.nova.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;
import com.nova.dao.impl.DataDaoImpl;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.Data;
import com.nova.sdo.User;

/**
 * 类名称：IDataDao   类描述：数据管理接口   创建人：zl   创建时间：2013-5-20 下午3:48:44    
 * 
 * @version  1.0    
 */
@ImplementedBy(DataDaoImpl.class)
public interface IDataDao {

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
	 * 获取分享给当前登录人的数据列表
	 * 
	 * @param dataTag
	 *            数据标签，搜索使用
	 * @param page
	 * @return
	 */
	PageList<Data> getDataListSharedToMe(String dataTag, Page page, int userId,
			int type, String sort);

	/**
	 * 判断一个数据是否已分配项目
	 * 
	 * @param fileId
	 * @return
	 */
	public boolean inProject(int fileId);

	/**
	 * 添加数据标签
	 * 
	 * @param fileId
	 * @return
	 */
	public int addDataTag(int fileId, String tag);

	/**
	 * 添加数据物种
	 * 
	 * @param fileId
	 * @return
	 */
	public int addDataStrain(int fileId, String strain);

	/**
	 * 为数据分配项目
	 * 
	 * @return
	 */
	public int allocateDataToProject(int dataId, int projectId);

	/**
	 * 为数据分配项目(多个)
	 * 
	 * @return
	 */
	public int allocateDataToProjects(int dataId, String proIds);

	/**
	 * 为数据分配项目
	 * 
	 * @return
	 */
	public int allocateDatasToProject(String dataIds, int projectId);

	/**
	 * 数据共享
	 */
	public int shareData(int fileId, String userIds, int userId);

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
	 * 查询我的数据统计数量
	 * 
	 * @param userId
	 * @return
	 */
	public Long getMyOwnDataNum(int userId);

	/**
	 * 查询某人所有数据大小
	 * 
	 * @param userId
	 * @return
	 */
	public Long getDataSize(int userId);

	/**
	 * 查询我的所有数据统计数量
	 * 
	 * @param userId
	 * @return
	 */
	public Long getAllDataNum(int userId);

	/**
	 * 根据文件编号获取已共享该文件的用户
	 * 
	 * @param fileId
	 * @return
	 */
	public List<User> getSharedUserListByFileId(int fileId, int userId);

	/**
	 * 读取用户输入过的物种信息列表
	 * 
	 * @return
	 */
	List<Map<String, String>> getStrainItem(int userId);

	/**
	 * 读取用户输入过的物种信息列表
	 * 
	 * @return
	 */
	List<Map<String, String>> getDataStrainItem(int userId);

	/**
	 * 根据文件编号获取项目名
	 * 
	 * @param dataId
	 * @return
	 */
	public String getProjectNameByDataId(int dataId);

	/**
	 * 根据文件编号获取项目编号
	 * 
	 * @param dataId
	 * @return
	 */
	public int getProjectIdByDataId(int dataId);

	/**
	 * 根据用户编号、sppName获取数据列表
	 * 
	 * @param userId
	 * @param appName
	 * @return
	 */
	public List<Data> getDataListByUserIdAppName(int userId, int dataType);

	/**
	 * 根据用户编号、数据格式获取数据列表
	 * 
	 * @param userId
	 * @param dataType
	 * @return
	 */
	public List<Data> getDataListByUserIdFileFormats(int userId, String formats);

	/**
	 * 根据共享列表编号删除共享记录
	 * 
	 * @return
	 */
	boolean deleteSharedDataById(int sharedId);

	/**
	 * 删除已共享的项目
	 * 
	 * @param userId
	 * @param projectId
	 * @return
	 */
	boolean deleteSharedData(int userId, int dataId);

	/**
	 * 查询所有已被取消共享的用户所再次共享的记录编号
	 * 
	 * @param userId
	 * @param projectId
	 * @return
	 */
	Map<String, Integer> getAllErrorSharedData(int userId, int dataId);

	/**
	 * 根据文件类型查询数据信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<Data> getAllDataByFileType(int userId, int fileType);

	/**
	 * 批量删除数据
	 * 
	 * @param dataIds
	 * @return
	 */
	public int delDatas(String dataIds);

	/**
	 * 数据导入项目
	 * 
	 * @param dataIds
	 * @param proIds
	 * @return
	 */
	public int importDataToPro(String dataIds, String proIds);

	/**
	 * 修改数据信息
	 * 
	 * @param data
	 * @return
	 */
	public int updateDataInfoByFileId(Data data);

	/**
	 * 根据数据id获取数据编号
	 * 
	 * @param dataIds
	 * @return
	 */
	public String getDataKeyListByDataIds(String dataIds);

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
	 * 根据数据id批量修改数据信息
	 * 
	 * @param dataIds
	 * @return
	 */
	public int updateDataInfoListByFileId(String dataIds, Data data);

	/**
	 * 根据数据id获取数据信息
	 * 
	 * @param dataIds
	 * @return
	 */
	public List<Data> getStrainDataKeySampleById(String dataIds);

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
