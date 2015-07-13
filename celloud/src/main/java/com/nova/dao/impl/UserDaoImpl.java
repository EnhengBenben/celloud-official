package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Statement;
import com.nova.dao.IUserDao;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.User;
import com.nova.utils.ConnectManager;
import com.nova.utils.MD5Util;
import com.nova.utils.ResetPwdUtils;

/**
 * @ClassName: UserDaoImpl
 * @Description: 实现用户登录和用户注册
 * @author: ASUS
 * @data: 2012-5-14
 */
public class UserDaoImpl extends BaseDao implements IUserDao{
	Logger log = Logger.getLogger(SoftwareDaoImpl.class);
	@Override
	public User login(String uuid) {
		User user = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from tb_user where login_uuid=?";
		String username = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, uuid);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setUserId(rs.getInt("user_id"));
				username = rs.getString("username");
				user.setUsername(username);
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
				log.info("用户" + username + "登陆成功！");
			} else {
				log.error("用户" + username + "登录失败");
			}
		} catch (SQLException e) {
			log.error("查询用户" + username + "信息失败" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return user;
	}

	@Override
	public User login(String username, String password) {
		log.info("用户" + username + "尝试登录");
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
			ps.setString(3, password);
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
				user.setCompanyId(rs.getInt("company_id"));
				user.setTruename(rs.getString("truename"));
				log.info("用户" + username + "登陆成功！");
			} else {
				log.error("用户" + username + "登录失败");
			}
		} catch (SQLException e) {
			log.error("查询用户" + username + "信息失败" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return user;
	}

	@Override
	public int register(User user) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int userId = 0;
		String sql = "insert into tb_user(username,password,host_pwd,email,cellphone,create_date,role,truename,dept_id) values(?,?,?,?,?,now(),?,?,?)";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getUsername());
			ps.setString(2, MD5Util.getMD5(user.getPassword()));
			ps.setString(3, user.getHostPwd());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getCellPhone());
			ps.setInt(6, user.getRole());
			ps.setString(7, user.getTruename());
			ps.setInt(8, user.getDeptId());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				userId = rs.getInt(1);
			}
			log.debug("注册用户成功" + user.getUsername());
		} catch (SQLException e) {
			log.error("用户" + user.getUsername() + "注册失败" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return userId;
	}

	@Override
	public boolean checkUsername(String username) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean result = false;
		String sql = "select username from tb_user where username=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			log.error("检测用户名失败" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return result;
	}
	
	@Override
	public boolean checkUsernameByUserId(int userId, String username) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean result = false;
		String sql = "select username from tb_user where username=? and user_id!=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setInt(2, userId);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			log.error("检测用户名失败" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return result;
	}

	@Override
	public boolean checkUserEmail(String email) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean result = false;
		String sql = "select email from tb_user where email=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			log.error("检测用户邮箱失败" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return result;
	}

	@Override
	public boolean checkUserEmailByUserId(int userId, String email) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean result = false;
		String sql = "select email from tb_user where email=? and user_id!=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setInt(2, userId);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			log.error("检测用户邮箱失败" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return result;
	}
	
	@Override
	public boolean updateTheme(String theme, int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "update tb_user set theme=? where user_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, theme);
			ps.setInt(2, userId);
			int i = ps.executeUpdate();
			if (i != 1) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			log.error("用户更新主题失败" + e);
			e.printStackTrace();
			return false;
		} finally {
			ConnectManager.free(conn, ps, null);
		}
	}

	@Override
	public void initData(int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "insert into tb_user_software(user_id,software_id,desk_no,create_date) values(?,?,?,now())";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			int[] softwares = { 2, 5, 6, 8, 7 };
			int[] desks = { 1, 2, 3, 4, 5 };
			for (int i = 0; i < desks.length; i++) {
				ps.setInt(1, userId);
				ps.setInt(2, softwares[i]);
				ps.setInt(3, desks[i]);
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			log.error("初始化数据失败" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
	}

	@Override
	public boolean changePassword(int userId, String newPwd) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean result = true;
		String sql = "update tb_user set password=? where user_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, MD5Util.getMD5(newPwd));
			ps.setInt(2, userId);
			int i = ps.executeUpdate();
			if (i < 1) {
				result = false;
			}
		} catch (SQLException e) {
			log.error("用户ID=" + userId + "修改密码失败" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
	public boolean checkPwd(int userId, String pwd) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean result = true;
		String sql = "select * from tb_user where user_id=? and password=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setString(2, MD5Util.getMD5(pwd));
			rs = ps.executeQuery();
			if (!rs.next()) {
				result = false;
			}
		} catch (SQLException e) {
			log.error("用户ID=" + userId + "获取密码失败" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return result;
	}

	@Override
	public List<User> getUserList(int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<User> userList = new ArrayList<User>();
		String sql = "select user_id,username from tb_user where user_id!=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				userList.add(user);
			}
		} catch (SQLException e) {
			log.error("获取用户信息列表失败！" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return userList;
	}

	@Override
	public String getEmailByUserId(int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select email from tb_user where user_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("email");
			}
		} catch (SQLException e) {
			log.error("获取用户邮箱信息失败！" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return null;
	}

	@Override
	public List<User> getUserListNotSharedPro(int userId, int projectId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<User> userList = new ArrayList<User>();
		String sql = "select user_id,username from tb_user where user_id!=? and user_id not in(select user_id from tb_file_share where project_id=? and state=1)";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, projectId);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return userList;
	}

	@Override
	public List<User> getUserListSharedPro(int userId, int projectId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<User> userList = new ArrayList<User>();
		String sql = "select user_id,username from tb_user where user_id!=? and user_id in(select user_id from tb_file_share where project_id=? and sharer_id=? and state=1)";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, projectId);
			ps.setInt(3, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return userList;
	}

	@Override
	public int getRoleByUserId(int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int role = 0;
		String sql = "select role from tb_user where user_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				role = rs.getInt("role");
			}
			log.info("获取用户角色成功");
		} catch (SQLException e) {
			log.error("获取用户角色失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return role;
	}

	@Override
	public int getUserIdByName(String userName) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int userId = 0;
		String sql = "select user_id from tb_user where username=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			while (rs.next()) {
				userId = rs.getInt("user_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return userId;
	}

	@Override
	public List<User> getAllUserList() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<User> userList = new ArrayList<User>();
		String sql = "select user_id,username,email from tb_user";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return userList;
	}

	@Override
	public PageList<User> getAllUserPageList(Page page, String userName) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PageList<User> userPageList = new PageList<User>();
		List<User> userList = new ArrayList<User>();
		StringBuffer sql = new StringBuffer();
		int start = (page.getCurrentPage() - 1) * page.getPageSize();
		sql.append("select * from tb_user where state=0 and username like '%");
		sql.append(userName).append("%' order by create_date desc limit ");
		sql.append(start).append(",").append(page.getPageSize());
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setHostPwd(rs.getString("host_pwd"));
				user.setTheme(rs.getString("theme"));
				user.setEmail(rs.getString("email"));
				user.setCellPhone(rs.getString("cellPhone"));
				user.setCreateDate(rs.getTimestamp("create_date"));
				user.setRole(rs.getInt("role"));
				user.setRemark(rs.getString("remark"));
				user.setTruename(rs.getString("truename"));
                user.setDeptId(rs.getInt("dept_id"));
				userList.add(user);
			}
			userPageList.setDatas(userList);
			userPageList.setPage(page);
			log.info("获取用户信息的分页列表成功");
		} catch (SQLException e) {
			log.error("获取用户信息的分页列表失败" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return userPageList;
	}

	@Override
	public int countUser(String userName) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int userCount = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*)c from tb_user where state=0 and username like '%");
		sql.append(userName).append("%'");
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				userCount = rs.getInt("c");
			}
			log.info("获取用户数量成功");
		} catch (SQLException e) {
			log.error("获取用户数量失败" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return userCount;
	}

	@Override
	public int addUser(User user) {
		Connection conn = null;
		PreparedStatement ps = null;
		int addNum = 0;
		String sql = "insert into tb_user (username,password,host_pwd,email,cellphone,create_date,theme,role,remark,truename,dept_id,company_id) values(?,?,?,?,?,now(),?,?,?,?,?,?)";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, MD5Util.getMD5(user.getPassword()));
			ps.setString(3, "");
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getCellPhone());
			ps.setString(6, "blue_sky.jpg");
			ps.setInt(7, user.getRole());
			ps.setString(8, user.getRemark());
			ps.setString(9, user.getTruename());
			ps.setInt(10, user.getDeptId());
			ps.setInt(11, user.getCompanyId());
			addNum = ps.executeUpdate();
			log.info("新增用户成功");
		} catch (SQLException e) {
			log.error("新增用户失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return addNum;
	}

	@Override
	public int updateUser(User user) {
		Connection conn = null;
		PreparedStatement ps = null;
		int updateNum = 0;
		String sql = "update tb_user set username=?,host_pwd=?,email=?,cellphone=?,role=?,remark=?,truename=?,dept_id=?,company_id=? where user_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getHostPwd());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getCellPhone());
			ps.setInt(5, user.getRole());
			ps.setString(6, user.getRemark());
			ps.setString(7, user.getTruename());
			ps.setInt(8, user.getDeptId());
			ps.setInt(9, user.getCompanyId());
			ps.setInt(10, user.getUserId());
			updateNum = ps.executeUpdate();
			log.info("修改用户成功");
		} catch (SQLException e) {
			log.error("修改用户失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return updateNum;
	}

	@Override
	public User getUserById(int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = new User();
		String sql = "select * from tb_user where user_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setHostPwd(rs.getString("host_pwd"));
				user.setTheme(rs.getString("theme"));
				user.setEmail(rs.getString("email"));
				user.setCellPhone(rs.getString("cellPhone"));
				user.setCreateDate(rs.getTimestamp("create_date"));
				user.setRole(rs.getInt("role"));
				user.setRemark(rs.getString("remark"));
				user.setTruename(rs.getString("truename"));
                user.setDeptId(rs.getInt("dept_id"));
				user.setCompanyId(rs.getInt("company_id"));
			}
			log.info("根据编号获取用户信息成功");
		} catch (SQLException e) {
			log.error("根据编号获取用户信息失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return user;
	}

	@Override
	public boolean resetPwd(int userId) {
		String password = new ResetPwdUtils().getPwd();
		Connection conn = null;
		PreparedStatement ps = null;
		boolean flag = true;
		String sql = "update tb_user set password=? where user_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, MD5Util.getMD5(password));
			ps.setInt(2, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			flag = false;
			log.error("密码重置失败" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	@Override
	public String getEmailByUserName(String userName) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select email from tb_user where state=0 and username=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("email");
			}
		} catch (SQLException e) {
			log.error("获取用户邮箱信息失败！" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return null;
	}

	@Override
	public String getUserNameByEmail(String email) {
		String userName = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select username from tb_user where state=0 and email=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				userName = rs.getString("username");
			}
		} catch (SQLException e) {
			log.error("根据用户邮箱" + email + "获取用户信息失败！" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return userName;
	}

	@Override
	public boolean insertFindPwdInfo(int userId, String md5) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean flag = true;
		String delSql = "delete from tb_user_find_pwd where user_id=?";
		String insertSql = "insert into tb_user_find_pwd(user_id,expire_date,md5) values(?,DATE_ADD(now(),INTERVAL 1 HOUR),?)";
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);

			ps = conn.prepareStatement(delSql);
			ps.setInt(1, userId);
			ps.executeUpdate();

			ps = conn.prepareStatement(insertSql);
			ps.setInt(1, userId);
			ps.setString(2, md5);
			ps.executeUpdate();

			conn.commit();
		} catch (SQLException e) {
			flag = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.error("随机验证码插入失败" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	@Override
	public boolean insertFindPwdInfo(String email, String md5) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean flag = true;
		String delSql = "delete from tb_user_find_pwd where email=?";
		String insertSql = "insert into tb_user_find_pwd(expire_date,md5,email) values(DATE_ADD(now(),INTERVAL 1 DAY),?,?)";
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);

			ps = conn.prepareStatement(delSql);
			ps.setString(1, email);
			ps.executeUpdate();

			ps = conn.prepareStatement(insertSql);
			ps.setString(1, md5);
			ps.setString(2, email);
			ps.executeUpdate();

			conn.commit();
		} catch (SQLException e) {
			flag = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.error("随机验证码插入失败" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	@Override
	public boolean validateUserByNameCode(String userName, String code) {
		boolean flag = true;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from tb_user where state=0 and username=? and validate_code=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, code);
			rs = ps.executeQuery();
			if (!rs.next()) {
				flag = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return flag;
	}

	@Override
	public boolean getValidate(String email, String md5) {
		boolean flag = true;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from tb_user_find_pwd where email=? and md5=? and expire_date>now()";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, md5);
			rs = ps.executeQuery();
			if (!rs.next()) {
				flag = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return flag;
	}

	@Override
	public Date getExpireDateByUserIdCode(int userId, String code) {
		Date expireDate = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select expire_date from tb_user_find_pwd where user_id=? and md5=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setString(2, code);
			rs = ps.executeQuery();
			if (rs.next()) {
				expireDate = rs.getTimestamp("expire_date");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return expireDate;
	}

	@Override
	public int updateUserByUserName(User user) {
		Connection conn = null;
		PreparedStatement ps = null;
		int flag = 0;
		String sql = "update tb_user set email=?,cellphone=?,remark=?,dept_id=? where username=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getCellPhone());
			ps.setString(3, user.getRemark());
			ps.setInt(4, user.getDeptId());
			ps.setString(5, user.getUsername());
			flag = ps.executeUpdate();
		} catch (SQLException e) {
			flag = 0;
			log.error("用户" + user.getUsername() + "信息修改失败" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}
	
	@Override
	public String getEmailBySessionUserId(int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String email = null;
		try {
			conn = ConnectManager.getConnection();
			String sql = "select email from tb_user where user_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			if(rs.next()){
				email = rs.getString("email");
			}
		} catch (SQLException e) {
			log.error("查询用户" + userId + "邮箱信息失败" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return email;
	}
	
	@Override
	public int updateExpireDateByUserId(int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		int flag = 0;
		String sql = "update tb_user_find_pwd set expire_date=now() where user_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			flag = ps.executeUpdate();
		} catch (SQLException e) {
			flag = 0;
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}
	
	@Override
	public int deleteUserByUserId(int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int flag = 1;
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			//删除之前先查找用户名
			String userName = null;
			String nSql = "select username from tb_user where user_id=?";
			ps = conn.prepareStatement(nSql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			if(rs.next()){
				userName = rs.getString("username");
			}
//			String sql = "delete from tb_user where user_id=?";
			String sql = "update tb_user set state=1 where user_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.executeUpdate();
			//1、删除数据项目关联信息，首先查询该用户下所有数据
			String delDSql = "select file_id from tb_file where user_id=?";
			ps = conn.prepareStatement(delDSql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			List<Integer> fileIdList = new ArrayList<Integer>();
			while(rs.next()){
				fileIdList.add(rs.getInt("file_id"));
			}
			//删除数据信息
//			String dSql = "delete from tb_file where user_id=?";
			String dSql = "update tb_file set state=1 where user_id=?";
			ps = conn.prepareStatement(dSql);
			ps.setInt(1, userId);
			ps.executeUpdate();
			//删除项目信息
//			String pSql = "delete from tb_project where user_id=?";
			String pSql = "update tb_project set state=1 where user_id=?";
			ps = conn.prepareStatement(pSql);
			ps.setInt(1, userId);
			ps.executeUpdate();
			//删除数据项目的关联关系
			if(fileIdList.size()>0){
				String rSql = "delete from tb_data_project_relat where file_id in(";
				StringBuffer sb = new StringBuffer();
				for(Integer fileId :fileIdList){
					sb.append(fileId + ",");
				}
				String ids = sb.toString();
				if(!ids.equals("")){
					ids = ids.substring(0, ids.length()-1);
					rSql += ids + ")";
					ps = conn.prepareStatement(rSql);
					ps.executeUpdate();
				}
			}
			//删除共享信息
			String sSql = "delete from tb_file_share where user_id=? or sharer_id=?"; 
			ps = conn.prepareStatement(sSql);
			ps.setInt(1, userId);
			ps.setInt(2, userId);
			ps.executeUpdate();
			//删除登录信息
			if(userName!=null&&!"".equals(userName)){
				String lSql = "delete from tb_log where user_name=?";
				ps = conn.prepareStatement(lSql);
				ps.setString(1, userName);
				ps.executeUpdate();
			}
			//删除报告信息
//			String rSql = "delete from tb_report where user_id=?";
			String rSql = "update tb_report set isdel=1 where user_id=?";
			ps = conn.prepareStatement(rSql);
			ps.setInt(1, userId);
			ps.executeUpdate();
			//删除用户找回密码信息
			String pwdSql = "delete from tb_user_find_pwd where user_id=?";
			ps = conn.prepareStatement(pwdSql);
			ps.setInt(1, userId);
			ps.executeUpdate();
			//删除用户添加的软件信息
			String uaSql = "delete from tb_user_software where user_id=?";
			ps = conn.prepareStatement(uaSql);
			ps.setInt(1, userId);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			flag = 0;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return flag;
	}
	
	@Override
	public boolean updateNotify(int userId, int notify) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean flag = true;
		String sql = "update tb_user set navigation=? where user_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, notify);
			ps.setInt(2, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			flag = false;
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	@Override
	public void deleteValidate(String email) {
		Connection conn = null;
		PreparedStatement ps = null;
		String delSql = "delete from tb_user_find_pwd where email=?";
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(delSql);
			ps.setString(1, email);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.error("随机验证码插入失败" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
	}
}