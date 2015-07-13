package com.nova.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.nova.dao.IDataDao;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.Data;
import com.nova.sdo.User;
import com.nova.service.IDataService;

public class DataServiceImpl implements IDataService {
	@Inject
	private IDataDao dataDao;

	@Override
	public PageList<Data> getDataList(String dataTag, Page page, int userId,
			int type, String sort) {
		return dataDao.getDataList(dataTag, page, userId, type, sort);
	}

	@Override
	public PageList<Data> getDataListSharedToMe(String dataTag, Page page,
			int userId, int type, String sort) {
		return dataDao.getDataListSharedToMe(dataTag, page, userId, type, sort);
	}

	@Override
	public boolean inProject(int fileId) {
		return dataDao.inProject(fileId);
	}

	@Override
	public int addDataTag(int fileId, String tag) {
		return dataDao.addDataTag(fileId, tag);
	}

	@Override
	public int allocateDataToProject(int dataId, int projectId) {
		return dataDao.allocateDataToProject(dataId, projectId);
	}

	@Override
	public int shareData(int fileId, String userIds, int userId) {
		return dataDao.shareData(fileId, userIds, userId);
	}

	@Override
	public List<Data> getDataListByProjectId(int projectId) {
		return dataDao.getDataListByProjectId(projectId);
	}

	@Override
	public int addDataInfo(Data data) {
		return dataDao.addDataInfo(data);
	}

	@Override
	public Data saveDataInfo(Data data) {
		return dataDao.saveDataInfo(data);
	}

    @Override
    public Data getDataByKey(String dataKey) {
        return dataDao.getDataByKey(dataKey);
    }

	@Override
	public List<String> getAllDataKey() {
		return dataDao.getAllDataKey();
	}

	@Override
	public Long getMyOwnDataNum(int userId) {
		return dataDao.getMyOwnDataNum(userId);
	}

	@Override
	public Long getAllDataNum(int userId) {
		return dataDao.getAllDataNum(userId);
	}

	@Override
	public int addDataStrain(int fileId, String strain) {
		return dataDao.addDataStrain(fileId, strain);
	}

	@Override
	public List<User> getSharedUserListByFileId(int fileId, int userId) {
		return dataDao.getSharedUserListByFileId(fileId, userId);
	}

	@Override
	public List<Map<String, String>> getStrainItem(int userId) {
		return dataDao.getStrainItem(userId);
	}

	@Override
	public String getProjectNameByDataId(int dataId) {
		return dataDao.getProjectNameByDataId(dataId);
	}

	@Override
	public int getProjectIdByDataId(int dataId) {
		return dataDao.getProjectIdByDataId(dataId);
	}

	@Override
	public int allocateDatasToProject(String dataIds, int projectId) {
		return dataDao.allocateDatasToProject(dataIds, projectId);
	}

	@Override
	public int allocateDataToProjects(int dataId, String proIds) {
		return dataDao.allocateDataToProjects(dataId, proIds);
	}

	@Override
	public List<Data> getDataListByUserIdAppName(int userId, int dataType) {
		return dataDao.getDataListByUserIdAppName(userId, dataType);
	}

	@Override
	public boolean deleteSharedDataById(int sharedId) {
		return dataDao.deleteSharedDataById(sharedId);
	}

	@Override
	public boolean deleteSharedData(int userId, int dataId) {
		return dataDao.deleteSharedData(userId, dataId);
	}

	@Override
	public Map<String, Integer> getAllErrorSharedData(int userId, int dataId) {
		return dataDao.getAllErrorSharedData(userId, dataId);
	}

	@Override
	public List<Data> getAllDataByFileType(int userId, int fileType) {
		return dataDao.getAllDataByFileType(userId, fileType);
	}

	@Override
	public List<Map<String, String>> getDataStrainItem(int userId) {
		return dataDao.getDataStrainItem(userId);
	}

	@Override
	public int delDatas(String dataIds) {
		return dataDao.delDatas(dataIds);
	}

	@Override
	public int importDataToPro(String dataIds, String proIds) {
		return dataDao.importDataToPro(dataIds, proIds);
	}

	@Override
	public int updateDataInfoByFileId(Data data) {
		return dataDao.updateDataInfoByFileId(data);
	}

	@Override
	public String getDataKeyListByDataIds(String dataIds) {
		return dataDao.getDataKeyListByDataIds(dataIds);
	}

	@Override
	public List<Data> getDataListByUserIdFileFormats(int userId, String formats) {
		return dataDao.getDataListByUserIdFileFormats(userId, formats);
	}

	@Override
	public void outPutData(String userId, String start, String end, String path) {
		List<Data> list = dataDao.getDataList(userId, start, end);
		StringBuffer sb = new StringBuffer(
				"user_id\tdata_key\tfile_name\tcreate_date\tpath\tsoft\n");
		for (Data data : list) {
			sb.append(data.getUserId() + "\t" + data.getDataKey() + "\t"
					+ data.getFileName() + "\t"
					+ data.getCreateDate().toString().substring(0, 10) + "\t"
					+ data.getPath() + "\t" + data.getOwner() + "\n");
		}
		FileWriter fw = null;
		try {
			fw = new FileWriter(path, true);
			fw.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Date getLatestDate(int userId) {
		return dataDao.getLatestDate(userId);
	}

	@Override
	public int updateDataInfoListByFileId(String dataIds, Data data) {
		return dataDao.updateDataInfoListByFileId(dataIds, data);
	}

	@Override
	public List<Data> getStrainDataKeySampleById(String dataIds) {
		return dataDao.getStrainDataKeySampleById(dataIds);
	}

	@Override
	public PageList<Data> getAppDataById(Integer userId, Page page,
			String fileType, Integer softwareId) {
		return dataDao.getAppDataById(userId, page, fileType, softwareId);
	}

    @Override
    public int updateAnotherNameById(Data data) {
        return dataDao.updateAnotherNameById(data);
    }

	@Override
	public Long getDataSize(int userId) {
		return dataDao.getDataSize(userId);
	}

	@Override
	public Integer clientAdd(Data data) {
		return dataDao.clientAdd(data);
	}

	@Override
	public Integer clientUpdate(Data data) {
		return dataDao.clientUpdate(data);
	}

	@Override
	public Data getDataById(String dataId) {
		return dataDao.getDataById(dataId);
	}
}
