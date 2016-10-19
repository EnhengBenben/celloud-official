package com.celloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.BoxConfig;

public interface BoxConfigMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(BoxConfig record);

	int insertSelective(BoxConfig record);

	BoxConfig selectByPrimaryKey(Integer id);

	public List<BoxConfig> selectByUserId(@Param("userId") Integer userId, @Param("ip") String ip);

	int updateByPrimaryKeySelective(BoxConfig record);

	int updateByPrimaryKey(BoxConfig record);

	/**
	 * 根据盒子的序列号查找盒子
	 * 
	 * @param serialNumber
	 * @return
	 */
	BoxConfig selectBySerialNumber(String serialNumber);

	/**
	 * 更新盒子的健康状态
	 * 
	 * @param config
	 * @return
	 */
	int updateBoxHealth(BoxConfig config);
}