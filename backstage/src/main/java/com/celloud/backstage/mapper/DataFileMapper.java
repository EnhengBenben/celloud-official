package com.celloud.backstage.mapper;

import com.celloud.backstage.model.DataFile;

public interface DataFileMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(DataFile record);

    int insertSelective(DataFile record);

    DataFile selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(DataFile record);

    int updateByPrimaryKeyWithBLOBs(DataFile record);

    int updateByPrimaryKey(DataFile record);
    /**
     * 添加上传文件信息
     * 
     * @param data
     * @return
     * @author han
     * @date 2015年12月31日 上午10:28:28
     */
    public int addDataInfo(DataFile data);

    /**
     * 修改数据信息
     * 
     * @param data
     * @return
     * @author han
     * @date 2015年12月31日 上午10:28:37
     */
    public int updateDataInfoByFileId(DataFile data);
}