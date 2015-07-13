package com.nova.dao;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.dao.impl.AppDaoImpl;
import com.nova.sdo.App;

@ImplementedBy(AppDaoImpl.class)
public interface IAppDao {
	/**
	 * 获取当前用户桌面软件
	 * 
	 * @param userId
	 * @return
	 */
	List<App> getAll(int userId);

	/**
	 * 获取当前用户某个桌面的app
	 * 
	 * @param userId
	 * @param deskNo
	 * @return
	 */
	List<App> getAll(int userId, int deskNo);

	/**
	 * 全局视图app托动 保存
	 * 
	 * @param userId
	 * @param deskNo
	 * @param oldDeskNo
	 * @param appId
	 * @return
	 */
	boolean updateDesk(int userId, int deskNo, int oldDeskNo, int appId);

	/**
	 * 从桌面上删除app
	 * 
	 * @param userId
	 * @param deskNo
	 * @param appId
	 * @return
	 */
	boolean deleteAppFromDesk(int userId, int deskNo, int appId);
}
