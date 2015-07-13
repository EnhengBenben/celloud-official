/**    
 * @Title: IDetectionDao.java  
 * @Package com.nova.dao   
 * @author summer    
 * @date 2012-7-7 下午05:37:59  
 * @version V1.0    
 */
package com.nova.dao;

import java.util.Map;

import com.google.inject.ImplementedBy;
import com.nova.dao.impl.DetectionDaoImpl;

/**
 * @ClassName: IDetectionDao
 * @Description: (谋取每个机器上的每个软件的最大链接数)
 * @author summer
 * @date 2012-7-7 下午05:37:59
 * 
 */
@ImplementedBy(DetectionDaoImpl.class)
public interface IDetectionDao {
	/**
	 * @Title: getSoftwareMaxLink
	 * @Description: (获取每台机器的最大链接数)
	 * @return
	 * @param Map
	 *            <String,Integer>
	 * @throws
	 */
	Map<String, Integer> getSoftwareMaxLink();
}
