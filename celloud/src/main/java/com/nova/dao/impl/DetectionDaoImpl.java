/**    
* @Title: DetectionDaoImpl.java  
* @Package com.nova.dao.impl   
* @author summer    
* @date 2012-7-7 下午06:27:42  
* @version V1.0    
*/
package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nova.dao.IDetectionDao;
import com.nova.utils.ConnectManager;

/**  
 * @ClassName: DetectionDaoImpl  
 * @Description: (负载均衡)  
 * @author summer 
 * @date 2012-7-7 下午06:27:42  
 *    
 */
public class DetectionDaoImpl implements IDetectionDao {
	Logger log = Logger.getLogger(DetectionDaoImpl.class);
	
	/**  
	* @Title: getSoftwareMaxLink  
	* @Description: (获取每台机器的最大链接数)  
	* @return 
	* @param Map<String,Integer>  
	* @throws  
	*/  
	public Map<String,Integer> getSoftwareMaxLink() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String,Integer> map = new HashMap<String,Integer>();
		String sql = "select * from tb_software as a,tb_machine as b where  b.software_name = a.software_name;" ;
		try{
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				map.put(rs.getString("ip")+";"+rs.getString("process_name"), rs.getInt("max_link"));
			}
		}catch (SQLException e) {
			log.error("获取最大连接数失败"+e);
			e.printStackTrace();
		}finally{
			ConnectManager.free(conn, ps, rs);
		}
		return map;
	}
}
