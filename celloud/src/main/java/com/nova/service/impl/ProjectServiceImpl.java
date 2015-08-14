package com.nova.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

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

	@Override
	public List<Integer> getProIdsByFileId(int fileId) {
		return projectDao.getProIdsByFileId(fileId);
	}
}
