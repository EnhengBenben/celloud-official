package com.celloud.service;

import java.util.List;
import java.util.Map;

import com.celloud.model.mysql.Patient;
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
	 * 
	 * @author miaoqi
	 * @description 根据主键查询样本对象
	 * @date 2017年1月12日
	 *
	 * @param id
	 * @return
	 */
	Sample findByPrimaryKey(Integer id);

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
     * 
     * @description 增加样本信息和病人信息
     * @author miaoqi
     * @date 2017年2月13日 上午10:53:10
     * @param userId
     * @param samplename
     * @param type
     * @param tagId
     * @param patient
     */
    public Boolean saveSampleInfoAndPatient(Integer userId, Sample sample, Patient patient);

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

    /**
     * 
     * @description 获取样本和病人信息
     * @author miaoqi
     * @date 2017年2月13日 下午6:03:16
     * @param loginUserId
     * @return
     */
    List<Map<String, String>> listSampleAndPatient(Integer userId, Integer isAdd, Integer orderId);

    /**
     * 
     * @description 删除样本信息,级联删除病人信息
     * @author miaoqi
     * @date 2017年2月14日 下午1:18:53
     * @param sampleId
     * @return
     */
    Boolean removeSampleInfo(Integer sampleId);

    /**
     * 
     * @description 根据样本id查询样本和病人信息
     * @author miaoqi
     * @date 2017年2月14日 下午2:12:23
     * @param loginUserId
     * @param sampleId
     * @return
     */
    Map<String, String> getSampleAndPatient(Integer userId, Integer sampleId);

    /**
     * 
     * @description 更新患者和样本信息
     * @author miaoqi
     * @date 2017年2月16日 上午10:55:59
     * @param patient
     * @param tagId
     * @param type
     * @return
     */
    Boolean updateSampleInfoAndPatient(Patient patient, Sample sample, Integer oldTagId);

    /**
     * 
     * @description 提交样本信息订单, 返回订单主键
     * @author miaoqi
     * @date 2017年2月16日 下午2:01:58
     * @param loginUserId
     * @return
     */
    Integer commitSampleInfo(Integer userId);

    Map<String, Object> getSampleInfoOrderInfo(Integer userId, Integer orderId);

    /**
     * 
     * @description 向用户所属的大客户发送订单
     * @author miaoqi
     * @date 2017年2月17日 上午10:18:18
     * @param userId
     * @param orderId
     * @return
     */
    Boolean sendOrderInfo(Integer userId, Integer orderId);
}
