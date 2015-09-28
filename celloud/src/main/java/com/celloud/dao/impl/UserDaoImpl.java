package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.celloud.dao.UserDao;
import com.celloud.sdo.Company;
import com.celloud.sdo.Dept;
import com.celloud.sdo.User;
import com.nova.utils.ConnectManager;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-28上午11:38:27
 * @version Revision: 1.0
 */
public class UserDaoImpl extends BaseDao implements UserDao {
    Logger log = Logger.getLogger(UserDaoImpl.class);
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    @Override
    public Map<String, Object> getUserAllInfo(Integer userId) {
	String sql = "select c.company_id,c.company_name,c.english_name cengname,c.company_icon,c.address,c.address_en,c.zip_code,c.tel ctel,d.dept_name,d.english_name dengname,d.dept_icon,d.tel dtel,u.user_id,u.username,u.email from tb_company c,tb_user u,tb_dept d where u.dept_id=d.dept_id and d.company_id=c.company_id and u.user_id=?";
	Map<String, Object> map = new HashMap<>();
	try {
	    conn = ConnectManager.getConnection();
	    ps = conn.prepareStatement(sql);
	    ps.setInt(1, userId);
	    rs = ps.executeQuery();
	    Company com = new Company();
	    Dept dept = new Dept();
	    User user = new User();
	    while (rs.next()) {
		com.setCompanyId(rs.getInt("company_id"));
		com.setCompanyName(rs.getString("company_name"));
		com.setEnglishName(rs.getString("cengname"));
		com.setCompanyIcon(rs.getString("company_icon"));
		com.setAddress(rs.getString("address"));
		com.setAddressEn(rs.getString("address_en"));
		com.setZipCode(rs.getString("zip_code"));
		com.setTel(rs.getString("ctel"));
		dept.setDeptName(rs.getString("dept_name"));
		dept.setEnglishName(rs.getString("dengname"));
		dept.setDeptIcon(rs.getString("dept_icon"));
		dept.setTel(rs.getString("dtel"));
		user.setUserId(rs.getInt("user_id"));
		user.setUsername(rs.getString("username"));
		user.setEmail(rs.getString("email"));
	    }
	    map.put("user", user);
	    map.put("company", com);
	    map.put("dept", dept);
	} catch (SQLException e) {
	    log.error("用户" + super.userName + "获取医院信息和部门信息");
	    e.printStackTrace();
	} finally {
	    ConnectManager.free(conn, ps, rs);
	}
	return map;
    }

}
