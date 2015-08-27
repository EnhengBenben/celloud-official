package com.nova.dao;

import com.google.inject.ImplementedBy;
import com.nova.dao.impl.UploadDaoImpl;

/**
 * @Description:文件断点续传的接口
 * @author lin
 * @date 2015-3-12 上午10:25:26
 */
@ImplementedBy(UploadDaoImpl.class)
public interface IUploadDao {
    /**
     * 新增上传记录
     * 
     * @param filename
     * @param chunk
     * @param chunks
     * @param md5
     */
    public void addInfo(String filename, Integer chunk, Integer chunks,
	    String md5);

    /**
     * 查询某文件的上传记录
     * 
     * @param filename
     * @return
     */
    public Integer getInfo(String filename);

    /**
     * 上传完成后删除记录信息
     * 
     * @param filename
     */
    public void deleteInfo(String filename);
}
