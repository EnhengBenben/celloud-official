package com.celloud.service;

import java.util.List;
import java.util.Map;

import com.celloud.model.mysql.Sample;
import com.celloud.model.mysql.SampleStorage;
import com.celloud.page.Page;
import com.celloud.page.PageList;

/**
 * 样本收集管理接口
 * 
 * @author <a href="liuqingxiao@celloud.cn">liuqx</a>
 * @date 2016年6月20日 上午11:11:11
 */
public interface SampleService {

    /**
     * 新增样本信息
     * 
     * @param sample
     * @return
     * @author leamo
     * @date 2016年6月20日 上午11:18:46
     */
    public Integer saveSample(String sampleName, Integer userId);

    /**
     * 批量提交样本信息
     * 
     * @param sample
     * @return
     * @author leamo
     * @param integer 
     * @date 2016年6月20日 上午11:18:46
     */
    public Integer commitSamples(List<Integer> sampleIds, Integer appId, Integer userId);

    /**
     * 批量提交采样列表
     * 
     * @param sampleIds
     * @param userId
     * @return
     * @author leamo
     * @date 2016年9月6日 下午5:21:07
     */
    public Integer commitSamples(List<Integer> sampleIds, Integer userId);

    /**
     * 获取所有未添加的样本编号信息
     * 
     * @param userId
     * @return
     * @author leamo
     * @date 2016年6月20日 下午2:03:41
     */
    public List<Sample> allUnaddSample(Integer userId);

    /**
     * 检查样本是否已存在
     * 
     * @param sampleName
     * @param userId
     * @return
     * @author leamo
     * @date 2016年6月20日 下午3:22:53
     */
    public Boolean checkSample(String sampleName, Integer userId);

    /**
     * 删除样本
     * 
     * @param sampleId
     * @return
     * @author leamo
     * @date 2016年6月21日 下午2:47:16
     */
    public Integer delete(Integer sampleId);

    /**
     * 批量删除样本
     * 
     * @param sampleIds
     * @return
     * @author leamo
     * @date 2016年6月21日 下午2:50:31
     */
    public Integer deleteList(List<Integer> sampleIds);

    /**
     * 获取指定状态下的样本列表
     * 
     * @param page
     * @param userId
     * @param experState
     *            实验状态
     * @return
     * @author leamo
     * @date 2016年9月5日 下午3:21:03
     */
    public PageList<Sample> getSamples(Page page, Integer userId,
            Integer experState);

    /**
     * 根据样本编号和实验状态获取样本信息
     * 
     * @param userId
     * @param sampleName
     * @param experState
     * @return
     * @author leamo
     * @date 2016年9月5日 下午4:26:07
     */
    public Sample getByNameExperState(String orderNo, String sampleName,
            Integer experState);

    /**
     * 扫码增加样本
     * 
     * @param userId
     * @param sample
     * @return
     * @author leamo
     * @date 2016年9月6日 下午4:02:33
     */
    public Integer samplingAddSample(Integer userId, String sampleName,
            String type, Integer tagId);

    /**
     * 修改样本实验状态
     * 
     * @param userId
     * @param experState
     * @param sampleId
     * @return
     * @author leamo
     * @date 2016年9月5日 下午4:37:38
     */
    public String updateExperState(Integer userId, Integer experState,
            Integer sampleId);
    
    /**
     * 修改样本实验状态并增加index
     * 
     * @param userId
     * @param experState
     * @param sampleId
     * @return
     * @author leamo
     * @date 2016年9月5日 下午4:37:38
     */
    public Integer updateExperStateAndIndex(Integer userId, Integer experState,
            Integer sampleId, List<String> sindexList);

    /**
     * 删除样本的实验过程
     * 
     * @param sampleLogId
     * @return
     * @author leamo
     * @date 2016年9月6日 下午9:30:10
     */
    public Integer deleteSampleLog(Integer sampleLogId);

    /**
     * 修改样本备注
     * 
     * @param sampleId
     * @param remark
     * @return
     * @author leamo
     * @date 2016年9月6日 下午10:15:48
     */
    public Integer editRemark(Integer sampleId, String remark);

    /**
     * 构建文库
     * 
     * @param name
     * @param index
     * @param sampleIds
     * @return
     * @author leamo
     * @date 2016年9月7日 上午3:00:26
     */
    public SampleStorage addStorage(String name, String index,
            List<Integer> sampleIds, Integer userId);

    public PageList<SampleStorage> getSampleStorages(Page page, Integer userId);

    /**
     * 获取文库中的样本信息
     * 
     * @param userId
     * @param ssId
     * @return
     * @author leamo
     * @date 2016年9月7日 下午3:44:42
     */
    public List<Map<String, Object>> sampleListInStorage(Integer userId, Integer ssId);

    /**
     * 获取样品订单信息
     * 
     * @param userId
     * @param orderId
     * @return
     * @author leamo
     * @date 2016年10月19日 下午4:42:48
     */
    public Map<String, Object> getSampleOrderInfo(Integer userId,
            Integer orderId);

    public Sample getSampleByNameAndOrderNo(String orderNo, String sampleName);

    /**
     * 根据实验样本编号及实验状态获取样本信息
     * 
     * @param experSampleName
     * @param experState
     * @return
     * @author leamo
     * @date 2016年10月24日 下午4:54:25
     */
    public Sample getByExperNameExperState(
            String experSampleName, Integer experState);

    /**
     * 根据文库名称获取样本列表
     * 
     * @param storageName
     * @return
     * @author leamo
     * @date 2016年10月25日 下午4:15:40
     */
    public List<Sample> getSamplesByStorageName(String storageName);

    /**
     * 获取样本的实验状态列表
     * 
     * @param page
     * @param userId
     * @param sampleName
     * @return
     * @author leamo
     * @date 2016年12月1日 上午11:00:03
     */
    public PageList<Sample> getSamplesExperState(Page page, Integer userId,
            String sampleName);

    /**
     * 修改文库上机状态
     * 
     * @param inMechine
     * @param sampleStorageId
     * @return
     * @author leamo
     * @date 2016年12月2日 下午1:45:56
     */
    public Integer updateSampleInMechine(Integer userId, Integer sampleStorageId);
}
