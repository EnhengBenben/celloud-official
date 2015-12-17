package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.celloud.dao.ProjectDao;
import com.celloud.sdo.Project;
import com.mysql.jdbc.Statement;
import com.nova.utils.ConnectManager;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-23下午3:52:19
 * @version Revision: 1.0
 */
public class ProjectDaoImpl extends BaseDao implements ProjectDao {
    Logger log = Logger.getLogger(ProjectDaoImpl.class);
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    @Override
    public Long insertProject(Project pro) {
	String sql = "insert into tb_project(project_name,user_id,num,size,create_date) values(?,?,?,?,now());";
	Long id = null;
	try {
	    conn = ConnectManager.getConnection();
	    ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    ps.setString(1, pro.getProjectName());
	    ps.setLong(2, pro.getUserId());
	    ps.setInt(3, pro.getFileNum());
	    ps.setString(4, pro.getFileSize());
	    ps.executeUpdate();
	    // 检索由于执行此 Statement 对象而创建的所有自动生成的键
	    rs = ps.getGeneratedKeys();
	    if (rs.next()) {
		id = rs.getLong(1);
	    }
	} catch (SQLException e) {
	    log.error("用户" + super.userName + "查询个人所有项目名失败");
	    e.printStackTrace();
	} finally {
	    ConnectManager.free(conn, ps, rs);
        }
        return id;
    }

}
