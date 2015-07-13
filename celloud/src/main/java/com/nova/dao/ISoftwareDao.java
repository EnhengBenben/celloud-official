package com.nova.dao;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.dao.impl.SoftwareDaoImpl;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.ParamStrain;
import com.nova.sdo.Project;
import com.nova.sdo.Software;

@ImplementedBy(SoftwareDaoImpl.class)
public interface ISoftwareDao {

	/**
	 * 分页查询软件
	 * 
	 * @param userId
	 *            ：用户ID
	 * @param classifyId
	 *            ：类型编号
	 * @param page
	 *            ：分页类
	 * @return
	 */
	PageList<Software> getSoftwarePageList(Integer userId, Integer classifyId,
			Page page);

	List<Software> getAllDb(int classifyId, int userId, int type);

	/**
	 * @Title: getAll
	 * @Description: (获取用户添加和未添加的软件)
	 * @param @param userId
	 * @param @return
	 * @return List<Software>
	 * @throws
	 */
	List<Software> getAll(int userId);

	/**
	 * @Title: updateBhri
	 * @Description: (更新人气指数)
	 * @param @param softwareId
	 * @param void
	 * @throws
	 */
	void updateBhri(int softwareId);

	/**
	 * @Title: saveSoftwareOnDesk
	 * @Description: (保存软件到桌面)
	 * @param @param userId
	 * @param @param softwareId
	 * @param @param deskNo
	 * @param @return
	 * @param boolean
	 * @throws
	 */
	boolean saveSoftwareOnDesk(int userId, int softwareId, int deskNo);

	/**
	 * @Title: cancleSoftwareOnDesk
	 * @Description: (从桌面删除软件)
	 * @param @param userId
	 * @param @param softwareId
	 * @param void
	 * @throws
	 */
	void cancleSoftwareOnDesk(int userId, int softwareId);

	/**
	 * @Title: getSoftWareOfUser
	 * @Description: (获取用户桌面上的软件)
	 * @param userId
	 * @return
	 * @param List
	 *            <Software>
	 * @throws
	 */
	List<Software> getSoftWareOfUser(int userId);

	/**
	 * @Title: getSoftware
	 * @Description: (获取一个软件的详细信息)
	 * @param softwareId
	 * @return
	 * @param Software
	 * @throws
	 */
	Software getSoftware(int softwareId);

	/**
	 * @Title:updateSoftwareOnDesk
	 * @Description: 改变软件所在的桌面
	 * @author lin
	 * @date 2012-6-21 上午10:09:53
	 */
	void updateSoftwareOnDesk(int userId, int softwareId, int deskNo);

	/**
	 * @Title: search
	 * @Description: (软件收索)
	 * @return
	 * @param List
	 *            <Software>
	 * @throws
	 */
	List<Software> search(int type, String softwareName, int userId);

	/**
	 * 获取系统中所用到的软件安装IP
	 * 
	 * @return
	 */
	List<String> getAllIp(String processName);

	/**
	 * 根据软件名称获取软件编号
	 * 
	 * @return
	 */
	int getSoftIdByName(String softName);

	/**
	 * 根据软件名称获取软件编号,前提是已经上线的
	 * 
	 * @return
	 */
	int getSoftIdByNameOffLine(int softwareId, String softName, String editType);

	/**
	 * 根据固定软件名称获取软件编号
	 * 
	 * @return
	 */
	List<Integer> getSoftIdByName();

	/**
	 * 获取应用运行的次数
	 * 
	 * @return
	 */
	int getRunningSoftwareNum(int userId);

	/**
	 * 获取指定应用的运行次数
	 * 
	 * @return
	 */
	int getRunningSoftwareNumByName(String softwareName, int userId);

	/**
	 * 获取所有的记录数目
	 * 
	 * @return
	 */
	int getTotalSize();

	/**
	 * 分页查询软件
	 * 
	 * @param page
	 * @return
	 */
	List<Software> getSoftPageList(Page page);

	/**
	 * 获取每个软件的运行次数
	 * 
	 * @param softwareId
	 * @return
	 */
	List<Software> getSoftwaresRunNum();

	/**
	 * 获取用户运行每个软件次数
	 * 
	 * @param softwareId
	 * @return
	 */
	List<Software> getUsersSoftwareRunNum(int userId);

	/**
	 * 所有没有与指定项目关联的app列表
	 * 
	 * @param softwareId
	 * @return
	 */
	List<Software> getAllSoftwareNotInPro(Project project);

	/**
	 * 所有没有与指定数据关联的app列表
	 * 
	 * @param dataId
	 * @return
	 */
	List<Software> getAllSoftwareNotInData(int dataId);

	/**
	 * 添加软件信息
	 * 
	 * @param software
	 * @return
	 */
	int insertSoftware(Software software);

	/**
	 * 修改软件信息
	 * 
	 * @param software
	 * @return
	 */
	int updateSoftware(Software software);

	/**
	 * 根据软件编号删除软件
	 * 
	 * @param softwareId
	 * @return
	 */
	int deleteSoftware(int softwareId);

	/**
	 * 获取所有软件列表
	 * 
	 * @param type
	 * @return
	 */
	List<Software> getAllSofts();

	/*********************************************************************************************
	 * 以下为最新software方法列表
	 */
	/**
	 * 根据用户Id获取软件数量
	 * 
	 * @param userId
	 * @return
	 */
	int getAllSoftwareNum(Integer userId);

	/**
	 * 根据软件类型分页查询软件
	 * 
	 * @param userId
	 *            ：当前登录用户编号
	 * @param softName
	 *            ：根据软件名模糊查询
	 * @param page
	 *            ：分页类
	 * @return
	 */
	PageList<Software> getAll(int userId, String softName, Page page);

	/**
	 * 根据软件类型分页查询软件
	 * 
	 * @param userId
	 *            ：当前登录用户编号
	 * @param softName
	 *            ：根据软件名模糊查询
	 * @return
	 */
	List<Software> getAll(int userId, String softName);

	/**
	 * @Title: updateBhri
	 * @Description: (更新人气指数)
	 * @param @param softwareId
	 * @param void
	 * @throws
	 */
	boolean updateBhri(int softwareId, int userId, int deskNo);

	/**
	 * 保存软件可处理的数据类型
	 * 
	 * @param softwareId
	 * @param typeIds
	 * @return
	 */
	public boolean saveSoftwareType(int softwareId, String[] typeIds);

	/**
	 * 软件推荐
	 * 
	 * @param softwareId
	 * @return
	 */
	public boolean recommendSoftware(int softwareId);

	/**
	 * 软件取消推荐
	 * 
	 * @param softwareId
	 * @return
	 */
	public boolean recommendNoSoftware(int softwareId);

	/**
	 * 根据软件编号获取管理员邮箱
	 * 
	 * @param softwareId
	 * @return
	 */
	public String getEmailBySoftId(int softwareId);

	/**
	 * 根据软件编号获取数据类型列表
	 * 
	 * @return
	 */
	public List<Integer> getTypeListBySoftwareId(int softwareId);

	/**
	 * 根据数据格式获取app列表
	 * 
	 * @param format
	 * @return
	 */
	public List<Software> getSoftListByFormat(Integer format, Integer userId);

	public int getAppRunDataNum(int softwareId);

	/**
	 * 根据软件编号获取物种列表
	 * 
	 * @param softwareId
	 * @return
	 */
	public List<ParamStrain> getStrainListBySoftwareId(String softwareId);

	/**
	 * 软件下线
	 * 
	 * @param softwareId
	 * @param flag
	 * @return
	 */
	public int offLineSoftware(int softwareId, int flag);

	/**
	 * 根据软件编号获取可以处理的数据格式
	 * 
	 * @param softwareId
	 * @return
	 */
	public String getFormatsByAppId(int softwareId);

	/**
	 * 获取可以运行数据（非JS）的app列表
	 * 
	 * @return
	 */
	List<Software> getRunDataApp();

	/**
	 * 获取用户运行过的app列表
	 * 
	 * @param userid
	 * @return
	 */
	List<Software> getRunDataAppById(int userid);

}
