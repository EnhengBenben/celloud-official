package com.nova.dao;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.dao.impl.ScreenDaoImpl;
import com.nova.sdo.Screen;

/**
 * @author <a href="mailto:linyongchao@novacloud.com">linyc</a>
 * @date 2013-1-25上午10:40:55
 * @version Revision: 1.0
 */
@ImplementedBy(ScreenDaoImpl.class)
public interface IScreenDao {
	/**
	 * 新增关系
	 * 
	 * @param screenName
	 * @param softwareId
	 * @return
	 */
	int createScreen(String screenName, int softwareId);

	/**
	 * 删除关系
	 * 
	 * @param softwareId
	 * @return
	 */
	int deleteScreen(int softwareId);

	/**
	 * 根据软件id获取所有的截图
	 * 
	 * @param softId
	 * @return
	 */
	List<Screen> getAllScreen(int softId);
}
