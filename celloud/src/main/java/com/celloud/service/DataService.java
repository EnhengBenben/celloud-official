package com.celloud.service;

import java.util.List;
import java.util.Map;

import com.celloud.sdo.Data;
import com.celloud.service.impl.DataServiceImpl;
import com.google.inject.ImplementedBy;
import com.nova.pager.Page;
import com.nova.pager.PageList;

/**
 * 数据管理服务接口
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-14上午10:31:29
 * @version Revision: 1.0
 */
@ImplementedBy(DataServiceImpl.class)
public interface DataService {
    public PageList<Data> getAllData(Page page, Integer userId);

    public PageList<Data> getDataByCondition(Page page, Integer userId,
            Integer sortType, String sortByName, String sortByDate,
            String condition);

    /**
     * 获取数据列表每种数据类型的个数
     * 
     * @param dataIds
     * @return
     */
    public Map<String, Integer> getFormatNumByIds(String dataIds);

    /**
     * 获取数据列表中所有正在运行指定App的数据
     * 
     * @param dataIds
     * @param appId
     * @return 数据编号列表
     */
    public List<Integer> getRunningDataBySoft(String dataIds, Integer appId);

    /**
     * 删除数据
     * 
     * @param dataIds
     * @return
     */
    public Integer deleteDataByIds(String dataIds);

    /**
     * 获取用户输入过的物种列表（--用于select2.js）
     * 
     * @param userId
     * @return
     */
    public List<Map<String, String>> getStrainList(Integer userId);

    /**
     * 查询数据信息(包含物种列表)
     * 
     * @param userId
     * @param fileId
     * @return
     */
    public Data getDataAndStrain(Integer userId, Long fileId);

    /**
     * 根据id获取数据列表
     * 
     * @param dataIds
     * @return
     */
    public List<Data> getDatasByIds(String dataIds);

    /**
     * 根据多个dataKey获取数据列表
     * 
     * @param dataKeys
     * @return
     */
    public List<Data> getDataByDataKeys(String dataKeys);

    /**
     * 批量修改数据
     * 
     * @param dataIds
     * @param data
     * @return
     */
    public Integer updateData(String dataIds, Data data);

    /**
     * 批量修改数据
     * 
     * @param list
     * @return
     */
    public Integer updateDatas(List<Data> list);

    /**
     * 修改数据
     * 
     * @param data
     * @return
     */
    public Integer updateData(Data data);

    /**
     * 获取数据总大小
     * 
     * @param dataIds
     * @return
     */
    public String getDataSize(String dataIds);

    /**
     * 给数据分配项目
     * 
     * @param dataIds
     * @param proId
     * @return
     */
    public Integer addDataToPro(String[] dataIdArr, Long proId);

    /**
     * 添加数据
     * 
     * @param list
     * @return
     */
    public Integer addData(Data data);
}
