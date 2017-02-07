package com.celloud.service;

import java.util.List;
import java.util.Map;

import com.celloud.model.mysql.App;
import com.celloud.model.mysql.DataFile;
import com.celloud.utils.Response;

public interface RunService {
	/**
	 * 根据appId生成dataListFile
	 * 
	 * @param appId
	 * @param dataList
	 * @return
	 * @author lin
	 * @date 2016年9月21日下午3:57:46
	 */
    public Map<String, Object> getDataListFile(Integer appId,
            List<DataFile> dataList);

	/**
	 * 数据分组
	 * 
	 * @param dataList
	 * @return
	 * @author lin
	 * @date 2016年9月21日下午4:28:05
	 */
	public Map<Integer, List<DataFile>> dataGroup(List<DataFile> dataList);

	/**
	 * 创建项目
	 * 
	 * @param userId
	 * @param dataList
	 * @param dataReportNum
	 * @return
	 * @author lin
	 * @date 2016年9月21日下午5:59:27
	 */
	public Integer createProject(Integer userId, List<DataFile> dataList, Integer dataReportNum);

	/**
	 * 创建报告
	 * 
	 * @param userId
	 * @param appId
	 * @param projectId
	 * @param dataList
	 * @return
	 * @author lin
	 * @date 2016年9月21日下午5:15:47
	 */
	public boolean createReport(Integer userId, Integer appId, Integer projectId, List<DataFile> dataList);

	/**
	 * 判断是否需要排队
	 * 
	 * @param app
	 * @return
	 * @author lin
	 * @date 2016年9月22日上午10:56:06
	 */
	public boolean runCheckIsWait(App app);

	/**
	 * 运行主方法（会根据数据的产品标签分组成项目运行）
	 * 
	 * @param userId
	 * @param dataIds
	 * @return
	 * @author lin
	 * @date 2016年9月22日下午1:49:39
	 */
	public Response run(Integer userId, String dataIds);

	/**
	 * 以某个APP运行一组数据
	 * 
	 * @param userId
	 * @param appId
	 * @param dataList
	 * @return
	 * @author lin
	 * @date 2016年9月22日下午1:50:26
	 */
	public String runSingle(Integer userId, Integer appId, List<DataFile> dataList);

	/**
	 * 运行下一个任务
	 * 
	 * @param appId
	 * @author lin
	 * @date 2016年9月27日下午1:43:39
	 */
	public void runNext(Integer appId);

    /**
     * 校验是否运行BSI
     * 
     * @param batch
     * @param dataId
     * @param dataKey
     * @param needSplit
     * @param originalName
     * @param userId
     * @param fileFormat
     * @return
     * @author leamo
     * @date 2016年11月28日 下午3:24:38
     */
    public String bsiCheckRun(String batch, Integer dataId, String dataKey,
            String originalName, Integer userId, Integer fileFormat);

	/**
	 * 校验是否运行rocky
	 * 
	 * @param appId
	 * @param data
	 * @author lin
	 * @date 2016年9月27日下午4:09:02
	 */
	public void rockyCheckRun(Integer appId, DataFile data);

}
