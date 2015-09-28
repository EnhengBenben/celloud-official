package com.celloud.dao;

import java.util.List;

import com.celloud.dao.impl.SoftwareDaoImpl;
import com.celloud.sdo.Software;
import com.google.inject.ImplementedBy;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-14下午5:33:54
 * @version Revision: 1.0
 */
@ImplementedBy(SoftwareDaoImpl.class)
public interface SoftwareDao {
    /**
     * 根据数据类型查询APP列表
     * 
     * @param formatId
     * @return
     */
    public List<Software> getAppsByFormat(Integer formatId);

    /**
     * 根据编号获取软件信息
     * 
     * @param softwareId
     * @return
     */
    public String getAppNameById(Long softwareId);
}
