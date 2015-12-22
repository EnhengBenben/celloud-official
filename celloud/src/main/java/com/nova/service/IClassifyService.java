package com.nova.service;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.sdo.Classify;
import com.nova.sdo.Software;
import com.nova.service.impl.ClassifyServiceImpl;

/**
 * 软件分类service
 * 
 * @author <a href="mailto:linyongchao@novacloud.com">linyc</a>
 * @date 2013-1-23下午7:52:51
 * @version Revision: 1.0
 */
@ImplementedBy(ClassifyServiceImpl.class)
public interface IClassifyService {
    /**
     * 创建软件分类
     * 
     * @param classify
     * @return
     */
    int createClassify(Classify classify);

    /**
     * 删除软件分类
     * 
     * @param classifyId
     * @return
     */
    int deleteClassify(int classifyId);

    /**
     * 修改软件分类
     * 
     * @param classify
     * @return
     */
    int updateClassify(Classify classify);

    /**
     * 查询单个软件分类
     * 
     * @param classifyId
     * @return
     */
    Classify getClassify(int classifyId);

    /**
     * 统计所有软件分类数量
     * 
     * @return
     */
    int getTotalClassify();

    /**
     * 查看其是否有子节点,无子节点返回true
     * 
     * @param classifyId
     * @return
     */
    List<Classify> selectChildNode(int classifyId);

    /**
     * 检验软件分类名称是否重复，重复返回false,不重复返回true
     * 
     * @param ClassifyName
     * @return
     */
    String selectClassifyName(String ClassifyName);

    /**
     * 查看属于该分类的软件
     * 
     * @param classifyId
     * @return
     */
    List<Software> selectChildSoft(int classifyId);

    /**
     * 查询所有二级分类
     * 
     * @return
     */
    List<Classify> getAllSubClassifyList();
}
