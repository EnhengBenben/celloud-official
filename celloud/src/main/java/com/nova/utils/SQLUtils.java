package com.nova.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nova.constants.DataState;
import com.nova.sdo.Client;
import com.nova.sdo.Data;
import com.nova.sdo.User;

/**
 * @Description:为webservice提供数据库查询的工具类
 * @author lin
 * @date 2014-10-13 上午10:40:43
 */
public class SQLUtils {

    /**
     * 客户端登陆成功后向tb_log表增加记录
     * 
     * @param username
     */
    public void addLog(String username) {
	Connection conn = null;
	PreparedStatement ps = null;
	String sql = "insert into tb_log(log_date,os,browser,user_name) values(now(),'Windows','client',?)";
	try {
	    conn = ConnectManager.getConnection();
	    ps = conn.prepareStatement(sql);
	    ps.setString(1, username);
	    ps.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    ConnectManager.free(conn, ps, null);
	}
    }

    /**
     * 查询uuid是否存在
     * 
     * @param login_uuid
     * @return
     */
    public boolean getUUID(String uuid) {
	if (uuid == null) {
	    return false;
	}
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = "select login_uuid from tb_user where login_uuid=?";
	boolean isTrue = false;
	try {
	    conn = ConnectManager.getConnection();
	    ps = conn.prepareStatement(sql);
	    ps.setString(1, uuid);
	    rs = ps.executeQuery();
	    if (rs.next()) {
		String res = rs.getString(1);
		if (uuid.equals(res)) {
		    isTrue = true;
		}
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    ConnectManager.free(conn, ps, rs);
	}
	return isTrue;
    }

    /**
     * 登陆成功后为用户增加uuid
     * 
     * @param userId
     * @param uuid
     * @return
     */
    public int addUUID(Integer userId, String uuid) {
	Connection conn = null;
	PreparedStatement ps = null;
	String sql = "update tb_user set login_uuid = ? where user_id = ?";
	int flag = 0;
	try {
	    conn = ConnectManager.getConnection();
	    ps = conn.prepareStatement(sql);
	    ps.setString(1, uuid);
	    ps.setInt(2, userId);
	    ps.executeUpdate();
	    flag = ps.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    ConnectManager.free(conn, ps, null);
	}
	return flag;
    }

    public User login(String username, String password) {
	System.out.println("用户" + username + "尝试登录");
	User user = null;
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = "select * from tb_user where (username=? or email=?) and password=?";
	try {
	    conn = ConnectManager.getConnection();
	    ps = conn.prepareStatement(sql);
	    ps.setString(1, username);
	    ps.setString(2, username);
	    ps.setString(3, MD5Util.getMD5(password));
	    rs = ps.executeQuery();
	    if (rs.next()) {
		user = new User();
		user.setUserId(rs.getInt("user_id"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setHostPwd(rs.getString("host_pwd"));
		user.setEmail(rs.getString("email"));
		user.setCellPhone(rs.getString("cellPhone"));
		user.setTheme(rs.getString("theme"));
		user.setType(rs.getInt("type"));
		user.setRole(rs.getInt("role"));
		user.setCreateDate(rs.getDate("create_date"));
		user.setNavigation(rs.getInt("navigation"));
		user.setDeptId(rs.getInt("dept_id"));
		user.setTruename(rs.getString("truename"));
		System.out.println("用户" + username + "登陆成功！");
	    } else {
		System.out.println("用户" + username + "登录失败");
	    }
	} catch (SQLException e) {
	    System.out.println("查询用户" + username + "信息失败" + e);
	    e.printStackTrace();
	} finally {
	    ConnectManager.free(conn, ps, rs);
	}
	return user;
    }

    public Long getDataSize(int userId) {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	Long dataNum = 0L;
	String sql = "select sum(size) as c from tb_file f where user_id=? and state=?;";
	try {
	    conn = ConnectManager.getConnection();
	    ps = conn.prepareStatement(sql);
	    ps.setInt(1, userId);
	    ps.setInt(2, DataState.ACTIVE);
	    rs = ps.executeQuery();
	    while (rs.next()) {
		dataNum = rs.getLong("c");
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    ConnectManager.free(conn, ps, rs);
	}
	return dataNum;
    }

    public Long getMyOwnDataNum(int userId) {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	Long dataNum = 0L;
	String sql = "select count(*) c from (select * from tb_file f where user_id=? and state=?) t";
	try {
	    conn = ConnectManager.getConnection();
	    ps = conn.prepareStatement(sql);
	    ps.setInt(1, userId);
	    ps.setInt(2, DataState.ACTIVE);
	    rs = ps.executeQuery();
	    while (rs.next()) {
		dataNum = rs.getLong("c");
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    ConnectManager.free(conn, ps, rs);
	}
	return dataNum;
    }

    public List<String> getAllDataKey() {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	List<String> dataKeyList = new ArrayList<String>();
	String sql = "select data_key from tb_file";
	try {
	    conn = ConnectManager.getConnection();
	    ps = conn.prepareStatement(sql);
	    rs = ps.executeQuery();
	    while (rs.next()) {
		dataKeyList.add(rs.getString("data_key"));
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    ConnectManager.free(conn, ps, rs);
	}
	return dataKeyList;
    }

    public int addDataInfo(Data data) {
	Connection conn = null;
	PreparedStatement ps = null;
	int result = 0;
	String sql = "insert into tb_file(user_id,data_key,file_name,create_date,path,md5,state) values(?,?,?,now(),?,?,?)";
	try {
	    conn = ConnectManager.getConnection();
	    ps = conn.prepareStatement(sql);
	    ps.setInt(1, data.getUserId());
	    ps.setString(2, data.getDataKey());
	    ps.setString(3, data.getFileName());
	    ps.setString(4, data.getPath());
	    ps.setString(5, data.getMd5());
	    ps.setInt(6, DataState.DEELTED);
	    result = ps.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    ConnectManager.free(conn, ps, null);
	}
	return result;
    }

    public Data saveDataInfo(Data data) {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	int result = 0;
	String sql = "insert into tb_file(user_id,data_key,file_name,strain,size,create_date,path,file_format) values(?,?,?,?,?,now(),?,?)";
	try {
	    conn = ConnectManager.getConnection();
	    ps = conn.prepareStatement(sql);
	    ps.setInt(1, data.getUserId());
	    ps.setString(2, data.getDataKey());
	    ps.setString(3, data.getFileName());
	    ps.setString(4, data.getStrain());
	    ps.setDouble(5, data.getSize());
	    ps.setString(6, data.getPath());
	    ps.setInt(7, data.getFileFormat());
	    ps.executeUpdate();
	    rs = ps.getGeneratedKeys();
	    if (rs.next()) {
		result = rs.getInt(1);
	    }
	    data.setFileId(result);
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    ConnectManager.free(conn, ps, rs);
	}
	return data;
    }

    public Data getDataByKey(String dataKey) {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	Data data = new Data();
	String sql = "select * from tb_file where data_key=?";
	try {
	    conn = ConnectManager.getConnection();
	    ps = conn.prepareStatement(sql);
	    ps.setString(1, dataKey);
	    rs = ps.executeQuery();
	    while (rs.next()) {
		data.setFileId(rs.getInt("file_id"));
		data.setFileName(rs.getString("file_name"));
		data.setAnotherName(rs.getString("another_name"));
		data.setMd5(rs.getString("md5"));
		data.setBlocks(rs.getInt("blocks"));
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    ConnectManager.free(conn, ps, rs);
	}
	return data;
    }

    public int updateData(Data data) {
	int flag = 1;
	Connection conn = null;
	PreparedStatement ps = null;
	String sql = "update tb_file set size=?,file_format=?,state=?,create_date=now(),another_name=? where data_key=?";
	try {
	    conn = ConnectManager.getConnection();
	    ps = conn.prepareStatement(sql);
	    ps.setDouble(1, data.getSize());
	    ps.setInt(2, data.getFileFormat());
	    ps.setInt(3, DataState.ACTIVE);
	    ps.setString(4, data.getAnotherName());
	    ps.setString(5, data.getDataKey());
	    ps.executeUpdate();
	} catch (SQLException e) {
	    flag = 0;
	    e.printStackTrace();
	} finally {
	    ConnectManager.free(conn, ps, null);
	}
	return flag;
    }

    public int updateData(String dataKey, int blocks) {
	int flag = 1;
	Connection conn = null;
	PreparedStatement ps = null;
	String sql = "update tb_file set blocks=? where data_key=?";
	try {
	    conn = ConnectManager.getConnection();
	    ps = conn.prepareStatement(sql);
	    ps.setInt(1, blocks);
	    ps.setString(2, dataKey);
	    ps.executeUpdate();
	} catch (SQLException e) {
	    flag = 0;
	    e.printStackTrace();
	} finally {
	    ConnectManager.free(conn, ps, null);
	}
	return flag;
    }

    public Client getClient() {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	Client client = null;
	try {
	    conn = ConnectManager.getConnection();
	    String sql = "select * from tb_client order by id desc limit 1";
	    ps = conn.prepareStatement(sql);
	    rs = ps.executeQuery();
	    if (rs.next()) {
		client = new Client();
		client.setId(rs.getInt("id"));
		client.setName(rs.getString("name"));
		client.setVersion(rs.getString("version"));
		client.setCreateDate(rs.getDate("create_date"));
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    ConnectManager.free(conn, ps, rs);
	}
	return client;
    }
}