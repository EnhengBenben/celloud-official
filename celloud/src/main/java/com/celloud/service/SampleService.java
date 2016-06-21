package com.celloud.service;

import java.util.List;

import com.celloud.model.mysql.Sample;

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
     * @date 2016年6月20日 上午11:18:46
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
}
