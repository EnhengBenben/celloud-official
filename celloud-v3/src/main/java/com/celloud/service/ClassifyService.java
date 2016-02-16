package com.celloud.service;

import java.util.List;

import com.celloud.model.Classify;

/**
 * 
 *
 * @author han
 * @date 2016年1月6日 上午10:20:13
 */
public interface ClassifyService {
    /**
     * 查询分类列表
     * 
     * @param pid
     *            父级分类id,0-查询父分类列表
     * @return
     * @author han
     * @date 2016年1月6日 上午10:08:29
     */
    public List<Classify> getClassify(Integer pid);

    /**
     * 获取分类信息
     *
     * @param id
     * @return
     * @author han
     * @date 2016年1月6日 上午10:25:40
     */
    public Classify getClassifyById(Integer id);
}
