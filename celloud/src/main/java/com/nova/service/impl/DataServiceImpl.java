package com.nova.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.google.inject.Inject;
import com.nova.dao.IDataDao;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.Data;
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
	public int allocateDatasToProject(String dataIds, int projectId) {
		return dataDao.allocateDatasToProject(dataIds, projectId);
	}

	@Override
	public int delDatas(String dataIds) {
		return dataDao.delDatas(dataIds);
	}

	@Override
	public int updateDataInfoByFileId(Data data) {
		return dataDao.updateDataInfoByFileId(data);
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
	public PageList<Data> getAppDataById(Integer userId, Page page,
			String fileType, Integer softwareId) {
		return dataDao.getAppDataById(userId, page, fileType, softwareId);
	}

    @Override
    public int updateAnotherNameById(Data data) {
        return dataDao.updateAnotherNameById(data);
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

	@Override
	public List<Data> getDataByDataKeys(String dataKeys, Integer userId) {
		return dataDao.getDataByDataKeys(dataKeys, userId);
	}

	@Override
	public int dataRunning(String appIds) {
		return dataDao.dataRunning(appIds);
	}
}
