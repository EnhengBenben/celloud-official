package com.celloud.backstage.service;


import java.util.List;

import com.celloud.backstage.model.Classify;

/**
 * App分类接口
 *
 * @author han
 * @date 2016年2月24日 下午5:32:57
 */
public interface ClassifyService {
    
    public List<Classify> getLeaf();
    
}
