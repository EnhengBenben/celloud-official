package com.celloud.backstage.service;

import java.util.List;
import java.util.Map;

import com.celloud.backstage.model.DataFile;

/**
 * 数据管理服务接口
 * 
 * @author han
 * @date 2015年12月23日 下午5:58:14
 */
public interface DataService {

    /**
     * 添加上传文件信息
     * 
     * @param data
     * @return
     */
    public int addDataInfo(DataFile data);

    /**
     * 修改数据信息
     * 
     * @param data
     * @return
     */
    public int updateDataInfoByFileId(DataFile data);
    
    /**
     * 硬删除账户数据
     *
     * @param userId
     * @return
     * @author han
     * @date 2016年2月22日 下午3:58:23
     */
    public boolean removeData(Integer userId);

    /**
     * 
     * @author MQ
     * @date 2016年8月9日上午10:51:17
     * @description 获取历史每周登录次数
     * @param companyId
     *            医院编号
     * @return
     *
     */
    public List<Map<String, Object>> getHistoryWeekUserLogin(Integer companyId);

    /**
     * 
     * @author MQ
     * @date 2016年8月9日上午10:51:04
     * @description 获取历史每周活跃用户
     * @param companyId
     *            医院编号
     * @return
     *
     */
    public List<Map<String, Object>> getHistoryWeekActiveUser(Integer companyId);

    /**
     * 
     * @author MQ
     * @date 2016年8月9日上午10:50:51
     * @description 获取历史每周App运行次数
     * @param companyId
     *            医院编号
     * @return
     *
     */
    public List<Map<String, Object>> getHistoryWeekAppRun(Integer companyId);

    /**
     * 
     * @author MQ
     * @date 2016年8月9日上午10:50:37
     * @description 获取历史周App活跃数
     * @param companyId
     *            医院编号
     * @return
     *
     */
    public List<Map<String, Object>> getHistoryWeekAppActive(Integer companyId);

    /**
     * 
     * @author MQ
     * @date 2016年8月9日上午10:50:21
     * @description 获取历史周数据大小
     * @param companyId
     *            医院编号
     * @return
     *
     */
    public List<Map<String, Object>> getHistoryWeekDataSize(Integer companyId);


}
