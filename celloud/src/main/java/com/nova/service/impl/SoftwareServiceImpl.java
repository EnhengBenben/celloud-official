package com.nova.service.impl;

import java.util.List;

import com.google.inject.Inject;
import com.nova.dao.ISoftwareDao;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.ParamStrain;
import com.nova.sdo.Project;
import com.nova.sdo.Software;
import com.nova.service.ISoftwareService;

/**
 * @ClassName: SoftwareServiceImpl
 * @Description: (软件service实现类)
 * @author summer
 * @date 2012-6-19 下午02:02:27
 * 
 */
public class SoftwareServiceImpl implements ISoftwareService {
	@Inject
	private ISoftwareDao softwareDao;

	@Override
	public List<Software> getAllDb(int classifyId, int userId, int type) {
		return softwareDao.getAllDb(classifyId, userId, type);
	}

	/**
	 * @Title: getAll
	 * @Description: (获取某个用户下面的未添加和已添加的软件)
	 * @param @param userId
	 * @param @return
	 * @return String
	 * @throws
	 */
	@Override
	public String getAll(int userId) {
		List<Software> list = softwareDao.getAll(userId);
		StringBuffer s = new StringBuffer("<root>");
		for (Software software : list) {
			s.append("<software><id>");
			s.append(software.getSoftwareId());
			s.append("</id><name>");
			s.append(software.getSoftwareName());
			s.append("</name><picture>");
			s.append(software.getPictureName());
			s.append("</picture><bhri>");
			s.append(software.getBhri());
			s.append("</bhri><host>");
			s.append(software.getHost());
			s.append("</host><classify>");
			s.append(software.getClassify());
			s.append("</classify><classifyId>");
			s.append(software.getClassifyId());
			s.append("</classifyId><type>");
			s.append(software.getType());
			s.append("</type><idAdd>");
			s.append(software.getIsAdd());
			s.append("</idAdd></software>");
		}
		s.append("</root>");
		return s.toString();
	}

	/**
	 * @Title: saveSoftwareOnDesk
	 * @Description: (添加软件)
	 * @param @param userId
	 * @param @param softwareId
	 * @param @param deskNo 桌面号码
	 * @return void
	 * @throws
	 */
	@Override
	public void saveSoftwareOnDesk(int userId, int softwareId, int deskNo) {
		softwareDao.saveSoftwareOnDesk(userId, softwareId, deskNo);
	}

	/**
	 * @Title: updateBhri
	 * @Description: (更新人气指数)
	 * @param @param softwareId
	 * @param void
	 * @throws
	 */
	@Override
	public void updateBhri(int softwareId) {
		softwareDao.updateBhri(softwareId);
	}

	/**
	 * @Title: cancleSoftwareOnDesk
	 * @Description: (从桌面上移除软件)
	 * @param @param userId
	 * @param @param softwareId
	 * @return void
	 * @throws
	 */
	@Override
	public void cancleSoftwareOnDesk(int userId, int softwareId) {
		softwareDao.cancleSoftwareOnDesk(userId, softwareId);
	}

	/**
	 * @Title: getSoftWareOfUser
	 * @Description: (获取某个用户的桌面软件)
	 * @param @param userId
	 * @param @return
	 * @return String
	 * @throws
	 */
	@Override
	public String getSoftWareOfUser(int userId) {
		List<Software> list = softwareDao.getSoftWareOfUser(userId);
		String xml = getXml(list);
		return xml;
	}

	/**
	 * @Title: getXml
	 * @Description: (解析成xml)
	 * @param @param list
	 * @param @return
	 * @param String
	 * @throws
	 */
	private String getXml(List<Software> list) {
		return null;
	}

	/**
	 * @Title: getSoftware
	 * @Description: (获取一个软件的详细信息)
	 * @param softwareId
	 * @return
	 * @param Software
	 * @throws
	 */
	@Override
	public Software getSoftware(int softwareId) {
		return softwareDao.getSoftware(softwareId);
	}

	@Override
	public String getOneSoftwareXML(int softwareId) {
		return null;
	}

	/**
	 * @Title:updateSoftwareOnDesk
	 * @Description: 改变软件所在的桌面
	 * @author lin
	 * @date 2012-6-21 上午10:09:53
	 */
	@Override
	public void updateSoftwareOnDesk(int userId, int softwareId, int deskNo) {
		softwareDao.updateSoftwareOnDesk(userId, softwareId, deskNo);
	}

	/**
	 * @Title: getDeskTopNo
	 * @Description: (设置桌面号码到session中)
	 * @param deskNo
	 * @throws
	 */
	@Override
	public void getDeskTopNo(int deskNo) {
	}

	/**
	 * @Title: search
	 * @Description: (软件收索)
	 * @return
	 * @param List
	 *            <Software>
	 * @throws
	 */
	@Override
	public List<Software> search(int type, String softwareName, int userId) {
		return softwareDao.search(type, softwareName, userId);
	}

	@Override
	public List<String> getAllIp(String processName) {
		return softwareDao.getAllIp(processName);
	}

	@Override
	public int getSoftIdByName(String softName) {
		return softwareDao.getSoftIdByName(softName);
	}

	@Override
	public int getRunningSoftwareNum(int userId) {
		return softwareDao.getRunningSoftwareNum(userId);
	}

	@Override
	public int getRunningSoftwareNumByName(String softwareName, int userId) {
		return softwareDao.getRunningSoftwareNumByName(softwareName, userId);
	}

	@Override
	public List<Integer> getSoftIdByName() {
		return softwareDao.getSoftIdByName();
	}

	@Override
	public List<Software> getSoftPageList(Page page) {
		return softwareDao.getSoftPageList(page);
	}

	@Override
	public int getTotalSize() {
		return softwareDao.getTotalSize();
	}

	@Override
	public List<Software> getSoftwaresRunNum() {
		return softwareDao.getSoftwaresRunNum();
	}

	@Override
	public List<Software> getAllSoftwareNotInPro(Project project) {
		return softwareDao.getAllSoftwareNotInPro(project);
	}

	@Override
	public List<Software> getAllSoftwareNotInData(int dataId) {
		return softwareDao.getAllSoftwareNotInData(dataId);
	}

	@Override
	public int insertSoftware(Software software) {
		return softwareDao.insertSoftware(software);
	}

	@Override
	public int updateSoftware(Software software) {
		return softwareDao.updateSoftware(software);
	}

	@Override
	public int deleteSoftware(int softwareId) {
		return softwareDao.deleteSoftware(softwareId);
	}

	@Override
	public List<Software> getUsersSoftwareRunNum(int userId) {
		return softwareDao.getUsersSoftwareRunNum(userId);
	}

	@Override
	public List<Software> getAllSofts() {
		return softwareDao.getAllSofts();
	}

	@Override
	public List<Software> getAllSoft(int userId) {
		return softwareDao.getAll(userId);
	}

	@Override
	public int getAllSoftwareNum(Integer userId) {
		return softwareDao.getAllSoftwareNum(userId);
	}

	@Override
	public List<Software> getAll(int userId, String softName) {
		return softwareDao.getAll(userId, softName);
	}

	@Override
	public PageList<Software> getAll(int userId, String softName, Page page) {
		return softwareDao.getAll(userId, softName, page);
	}

	@Override
	public boolean updateBhri(int softwareId, int userId, int deskNo) {
		return softwareDao.updateBhri(softwareId, userId, deskNo);
	}

	/***************************
	 * 数据库
	 */

	@Override
	public boolean saveSoftwareType(int softwareId, String[] typeIds) {
		return softwareDao.saveSoftwareType(softwareId, typeIds);
	}

	@Override
	public boolean recommendSoftware(int softwareId) {
		return softwareDao.recommendSoftware(softwareId);
	}

	@Override
	public String getEmailBySoftId(int softwareId) {
		return softwareDao.getEmailBySoftId(softwareId);
	}

	@Override
	public List<Integer> getTypeListBySoftwareId(int softwareId) {
		return softwareDao.getTypeListBySoftwareId(softwareId);
	}

	@Override
	public List<Software> getSoftListByFormat(Integer format, Integer userId) {
		return softwareDao.getSoftListByFormat(format,userId);
	}

	@Override
	public int getAppRunDataNum(int softwareId) {
		return softwareDao.getAppRunDataNum(softwareId);
	}

	@Override
	public List<ParamStrain> getStrainListBySoftwareId(String softwareId) {
		return softwareDao.getStrainListBySoftwareId(softwareId);
	}

	@Override
	public boolean recommendNoSoftware(int softwareId) {
		return softwareDao.recommendNoSoftware(softwareId);
	}

	@Override
	public int offLineSoftware(int softwareId, int flag) {
		return softwareDao.offLineSoftware(softwareId, flag);
	}

	@Override
	public String getFormatsByAppId(int softwareId) {
		return softwareDao.getFormatsByAppId(softwareId);
	}

	@Override
	public int getSoftIdByNameOffLine(int softwareId, String softName,
			String editType) {
		return softwareDao.getSoftIdByNameOffLine(softwareId, softName,
				editType);
	}

	@Override
	public List<Software> getRunDataApp() {
		return softwareDao.getRunDataApp();
	}

	@Override
	public List<Software> getRunDataAppById(int userId) {
		return softwareDao.getRunDataAppById(userId);
	}

	@Override
	public PageList<Software> getSoftwarePageList(Integer userId,
			Integer classifyId, Page page) {
		return softwareDao.getSoftwarePageList(userId, classifyId, page);
	}
}