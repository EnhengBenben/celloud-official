package com.nova.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.inject.Inject;
import com.nova.dao.IProjectDao;
import com.nova.sdo.Data;
import com.nova.sdo.Project;
import com.nova.service.IProjectService;

public class ProjectServiceImpl implements IProjectService {
	Logger log = Logger.getLogger(ProjectServiceImpl.class);
	@Inject
	private IProjectDao projectDao;

	@Override
	public boolean insertProject(Project project) {
		boolean insertSucc = projectDao.insertProject(project);
		return insertSucc;
	}

	@Override
	public int createProject(Project project) {
		int result = projectDao.createProject(project);
		return result;
	}

	@Override
	public List<Data> getAllFileInProject(int projectId) {
		List<Data> dataList = projectDao.getAllFileInProject(projectId);
		return dataList;
	}

	@Override
	public int insertShareProject(String userIds, int projectId, int userId) {
		int shareNum = projectDao
				.insertShareProject(userIds, projectId, userId);
		return shareNum;
	}

	@Override
	public String getProjectNameById(int projectId) {
		return projectDao.getProjectNameById(projectId);
	}

	@Override
	public List<Map<String, String>> getDataTypeItem() {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();
		List<Map<String, String>> dataTypeList = new ArrayList<Map<String, String>>();
		try {
			DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
			String path = ProjectServiceImpl.class.getResource(
					"/dataTypeItems.xml").getPath();
			InputStream in = new FileInputStream(path);
			Document doc = domBuilder.parse(in);
			Element root = doc.getDocumentElement();
			NodeList strains = root.getElementsByTagName("value");
			if (strains != null) {
				for (int i = 0; i < strains.getLength(); i++) {
					Node strain = strains.item(i);
					Map<String, String> map = new HashMap<String, String>();
					map.put("dataType", strain.getTextContent());
					dataTypeList.add(map);
				}
			}
		} catch (ParserConfigurationException e) {
			log.error("创建Dom对象失败" + e);
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			log.error("没有找到配置文件" + e);
			e.printStackTrace();
		} catch (SAXException e) {
			log.error("文件转换失败" + e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("文件读取错误" + e);
			e.printStackTrace();
		}
		return dataTypeList;
	}

	@Override
	public List<Project> getAllProNameList(int userId) {
		return projectDao.getAllProNameList(userId);
	}

	@Override
	public List<String> getAllProName() {
		return projectDao.getAllProName();
	}

	@Override
	public boolean removeData(String projectId, int dataId) {
		return projectDao.removeData(projectId, dataId);
	}

	@Override
	public boolean addProStrain(int projectId, String strain) {
		return projectDao.addProStrain(projectId, strain);
	}

	@Override
	public boolean addProDataType(int projectId, String dataType) {
		return projectDao.addProDataType(projectId, dataType);
	}

	@Override
	public String getStrainByProId(int projectId) {
		return projectDao.getStrainByProId(projectId);
	}

	@Override
	public int getProjectIdByName(String projectName) {
		return projectDao.getProjectIdByName(projectName);
	}

	@Override
	public List<Map<String, String>> getProStrainItem(int userId) {
		return projectDao.getProStrainItem(userId);
	}

	@Override
	public int getUserIdByProjectId(int projectId) {
		return projectDao.getUserIdByProjectId(projectId);
	}

	@Override
	public List<Project> getAllProNameListByFileId(int fileId) {
		return projectDao.getAllProNameListByFileId(fileId);
	}

	@Override
	public boolean updateProName(int projectId, String projectName) {
		return projectDao.updateProName(projectId, projectName);
	}

	@Override
	public List<Project> getProListForData(int userId, int fileType) {
		return projectDao.getProListForData(userId, fileType);
	}

	@Override
	public int addDataToPro(int projectId, int dataId) {
		return projectDao.addDataToPro(projectId, dataId);
	}

	@Override
	public Map<String, Integer> getAllErrorSharedPro(int userId, int projectId) {
		return projectDao.getAllErrorSharedPro(userId, projectId);
	}

	@Override
	public boolean deleteSharedProById(int sharedId) {
		return projectDao.deleteSharedProById(sharedId);
	}

	@Override
	public boolean deleteSharedPro(int userId, int projectId) {
		return projectDao.deleteSharedPro(userId, projectId);
	}

	@Override
	public String getDataTypeById(int projectId) {
		return projectDao.getDataTypeById(projectId);
	}

	@Override
	public int checkProjectName(int projectId, String projectName, int userId) {
		return projectDao.checkProjectName(projectId, projectName, userId);
	}

	@Override
	public int updateDataFormatById(int projectId, int format) {
		return projectDao.updateDataFormatById(projectId, format);
	}

	@Override
	public int deleteProject(int projectId) {
		return projectDao.deleteProject(projectId);
	}

	@Override
	public Project getProjectById(int projectId) {
		return projectDao.getProjectById(projectId);
	}
}
