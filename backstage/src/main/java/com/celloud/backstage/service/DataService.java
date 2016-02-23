package com.celloud.backstage.service;

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


}
