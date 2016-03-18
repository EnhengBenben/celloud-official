package com.celloud.backstage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
    
    
    /**
     * 根据userId查询tb_file列表
     *
     * @param userId
     * @return
     * @author han
     * @date 2016年2月22日 下午5:17:19
     */
    public List<DataFile> selectByUserId(@Param("userId")Integer userId);
    
    /**
     * 硬删除tb_file_project_relat
     *
     * @param userId
     * @return
     * @author han
     * @date 2016年2月22日 下午4:49:23
     */
    public int deletefileProjectRelatByUserId(@Param("userId")Integer userId);
    
    /**
     * 硬删除 tb_file
     */
    public int deleteDataFileByUserId(@Param("userId")Integer userId);
    
}