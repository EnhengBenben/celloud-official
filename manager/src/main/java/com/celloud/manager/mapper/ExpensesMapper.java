package com.celloud.manager.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.manager.model.Expenses;
import com.celloud.manager.page.Page;

public interface ExpensesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Expenses record);

    int insertSelective(Expenses record);

    Expenses selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Expenses record);

    int updateByPrimaryKey(Expenses record);

	/**
	 * 新增数据消费记录关系
	 * 
	 * @param expensesId
	 * @param projectId
	 * @param fileId
	 * @param dataKey
	 * @param fileName
	 * @return
	 * @author leamo
	 * @date 2016年3月4日 下午4:27:18
	 */
	int addFileExpenseRelat(@Param("expensesId") Integer expensesId, @Param("projectId") Integer projectId,
			@Param("fileId") Integer fileId, @Param("dataKey") String dataKey, @Param("fileName") String fileName);

	/**
	 * 查询数据消费次数
	 * 
	 * @param fileId
	 * @param itemId
	 * @return
	 * @author leamo
	 * @date 2016年3月4日 下午4:27:31
	 */
	int getFileExpenseNum(@Param("fileId") Integer fileId, @Param("itemId") Integer itemId,
			@Param("itemType") Byte itemType);

	/**
	 * 查询所有运行消费记录
	 * 
	 * @param page
	 * @param userId
	 * @return
	 * @author leamo
	 * @date 2016年3月4日 下午5:26:41
	 */
	List<Expenses> getAllRunExpensesByUser(Page page, @Param("userId") Integer userId,
			@Param("ItemType") Byte itemType);

	/**
	 * 查询用户总消费金额
	 * 
	 * @param userId
	 * @return
	 * @author leamo
	 * @date 2016年3月8日 上午11:26:58
	 */
	BigDecimal getTotalExpensesByUser(@Param("userId") Integer userId);
}