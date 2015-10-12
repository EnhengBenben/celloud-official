package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nova.constants.DataState;
import com.nova.constants.FileFormat;
import com.nova.dao.IProjectDao;
import com.nova.sdo.Data;
import com.nova.sdo.Project;
import com.nova.utils.ConnectManager;

/**
 * 项目管理实现类
 * 
 * @author <a href="mailto:liuqingxiao@novacloud.com">liuqx</a>
 * @date 2013-5-20下午03:16:00
 * @vequeryRsion Revision: 1.0
 */
public class ProjectDaoImpl extends BaseDao implements IProjectDao {
	Logger log = Logger.getLogger(ProjectDaoImpl.class);
	DecimalFormat format = new DecimalFormat(",###");
	private static Map<Integer, String> sortType = new HashMap<Integer, String>();
	static {
		sortType.put(1, "project_name");
		sortType.put(2, "create_date");
	}

	@Override
	public boolean insertProject(Project project) {
		log.info("用户" + super.userName + "新增项目" + project.getProjectName());
		boolean insertSuc = true;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String sql = "insert into tb_project(project_name,user_id,strain,data_format,pro_data_type,create_date)"
					+ " values(?,?,?,?,?,now());";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, project.getProjectName());
			ps.setInt(2, project.getUserId());
			ps.setString(3, project.getStrain());
			ps.setInt(4, project.getDataFormat());
			ps.setInt(5, project.getProDataType());
			int insertNum = ps.executeUpdate();
			if (insertNum < 1) {
				insertSuc = false;
				log.info("用户" + super.userName + "新增项目"
						+ project.getProjectName() + "失败");
			}
		} catch (SQLException e) {
			insertSuc = false;
			log.info("用户" + super.userName + "新增项目" + project.getProjectName()
					+ "失败");
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return insertSuc;
	}

	@Override
	public int createProject(Project project) {
		log.info("用户" + super.userName + "新增项目" + project.getProjectName());
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		try {
			String sql = "insert into tb_project(project_name,user_id,strain,data_format,pro_data_type,create_date)"
					+ " values(?,?,?,?,?,now());";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, project.getProjectName());
			ps.setInt(2, project.getUserId());
			ps.setString(3, project.getStrain());
			ps.setInt(4, project.getDataFormat());
			ps.setInt(5, project.getProDataType());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			log.info("用户" + super.userName + "新增项目" + project.getProjectName()
					+ "失败" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return result;
	}

	@Override
	public List<Data> getAllFileInProject(int projectId) {
		List<Data> fileList = new ArrayList<Data>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select f.file_id as file_id,file_name,another_name,data_key,size,create_date from tb_file f "
					+ "join tb_data_project_relat dpr on f.file_id=dpr.file_id where dpr.project_id=?";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, projectId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Data data = new Data();
				data.setFileId(rs.getInt("file_id"));
				data.setFileName(rs.getString("file_name"));
				data.setDataKey(rs.getString("data_key"));
				data.setSize(rs.getLong("size"));
				data.setAnotherName(rs.getString("another_name"));
				fileList.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return fileList;
	}

	@Override
	public int insertShareProject(String userIds, int projectId, int userId) {
		log.info("用户" + super.userName + "将项目" + projectId + "共享给用户" + userIds);
		int result = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			String[] ids = userIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				String sql = "insert into tb_file_share(project_id,user_id,sharer_id,state) values("
						+ projectId + "," + ids[i] + "," + userId + ",1)";
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
			}
			String sqlP = "update tb_project set share = 1 where project_id = ?";
			ps = conn.prepareStatement(sqlP);
			ps.setInt(1, projectId);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			result = 0;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.error("用户" + super.userName + "将项目" + projectId + "共享给用户"
					+ userIds + "失败");
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
	public String getProjectNameById(int projectId) {
		String sql = "select project_name from tb_project where project_id="
				+ projectId;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("project_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return null;
	}

	@Override
	public List<Project> getAllProNameList(int userId) {
		String sql = "select project_id,project_name from tb_project where state=? and user_id=? order by create_date desc";
		List<Project> proNameList = new ArrayList<Project>();
		Connection conn = null;
		PreparedStatement queryPreState = null;
		ResultSet queryRs = null;
		try {
			conn = ConnectManager.getConnection();
			queryPreState = conn.prepareStatement(sql);
			queryPreState.setInt(1, DataState.ACTIVE);
			queryPreState.setInt(2, userId);
			queryRs = queryPreState.executeQuery();
			while (queryRs.next()) {
				Project project = new Project();
				project.setProjectId(queryRs.getInt("project_id"));
				project.setProjectName(queryRs.getString("project_name"));
				proNameList.add(project);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, queryPreState, queryRs);
		}
		return proNameList;
	}

	@Override
	public List<String> getAllProName() {
		List<String> proNameList = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select project_name from tb_project";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String projectName = rs.getString("project_name");
				proNameList.add(projectName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return proNameList;
	}

	@Override
	public boolean removeData(String projectId, int dataId) {
		log.info("用户" + super.userName + "将数据" + dataId + "从项目" + projectId
				+ "移除");
		boolean flag = true;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String sql = "delete from tb_data_project_relat where project_id=? and file_id=?";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(projectId));
			ps.setInt(2, dataId);
			ps.executeUpdate();
		} catch (SQLException e) {
			flag = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.error("用户" + super.userName + "将数据" + dataId + "从项目"
					+ projectId + "移除失败");
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	@Override
	public boolean addProStrain(int projectId, String strain) {
		log.info("用户" + super.userName + "修改项目" + projectId + "物种信息" + strain);
		boolean addBool = false;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String sql = "update tb_project set strain=? where project_id=?";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, strain);
			ps.setInt(2, projectId);
			int addNum = ps.executeUpdate();
			if (addNum > 0) {
				addBool = true;
			}
		} catch (SQLException e) {
			addBool = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.error("用户" + super.userName + "修改项目" + projectId + "物种信息"
					+ strain + "失败");
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return addBool;
	}

	@Override
	public boolean addProDataType(int projectId, String dataType) {
		log.info("用户" + super.userName + "修改项目" + projectId + "数据类型" + dataType);
		boolean addBool = true;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String sql = "update tb_project set pro_data_type=? where project_id=?";
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			if (null == dataType || "".equals(dataType)
					|| "null".equals(dataType)) {
				ps.setInt(1, 0);
			} else {
				ps.setString(1, dataType);
			}
			ps.setInt(2, projectId);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			addBool = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.error("用户" + super.userName + "修改项目" + projectId + "数据类型"
					+ dataType + "失败");
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return addBool;
	}

	@Override
	public int getProjectIdByName(String projectName) {
		log.info("根据文件名称获取文件编号");
		int projectId = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select project_id from tb_project where project_name=?";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, projectName);
			rs = ps.executeQuery();
			while (rs.next()) {
				projectId = rs.getInt("project_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return projectId;
	}

	@Override
	public int checkProjectName(int projectId, String projectName, int userId) {
		log.info("根据项目名称、用户编号获取项目编号");
		int rprojectId = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select project_id from tb_project where state=? and project_name=? and user_id=? and project_id!=?";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, DataState.ACTIVE);
			ps.setString(2, projectName);
			ps.setInt(3, userId);
			ps.setInt(4, projectId);
			rs = ps.executeQuery();
			if (rs.next()) {
				rprojectId = rs.getInt("project_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return rprojectId;
	}

	@Override
	public String getStrainByProId(int projectId) {
		log.info("根据项目编号获取物种信息");
		String strain = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select strain from tb_project where project_id=?";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, projectId);
			rs = ps.executeQuery();
			while (rs.next()) {
				strain = rs.getString("strain");
			}
			log.info("根据项目编号获取物种信息成功");
		} catch (SQLException e) {
			log.error("根据项目编号获取物种信息失败" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return strain;
	}

	@Override
	public List<Map<String, String>> getProStrainItem(int userId) {
		List<Map<String, String>> strainList = new ArrayList<Map<String, String>>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select strain from tb_project where user_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("strain", rs.getString("strain"));
				strainList.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return strainList;
	}

	@Override
	public int getUserIdByProjectId(int projectId) {
		int userId = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select user_id from tb_project where project_id=?";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, projectId);
			rs = ps.executeQuery();
			if (rs.next()) {
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
	public List<Project> getAllProNameListByFileId(int fileId) {
		List<Project> proList = new ArrayList<Project>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select project_id,project_name from tb_project where project_id in(select project_id from tb_data_project_relat where file_id="
					+ fileId + ")";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Project pro = new Project();
				pro.setProjectId(rs.getInt("project_id"));
				pro.setProjectName(rs.getString("project_name"));
				proList.add(pro);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return proList;
	}

	@Override
	public boolean updateProName(int projectId, String projectName) {
		log.info("修改项目名称");
		boolean updateName = false;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String sql = "update tb_project set project_name=? where project_id=?";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, projectName);
			ps.setInt(2, projectId);
			int updateNum = ps.executeUpdate();
			if (updateNum > 0) {
				updateName = true;
				log.info("添加项目名称成功");
			}
		} catch (SQLException e) {
			updateName = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.error("添加项目名称失败！" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return updateName;
	}

	@Override
	public List<Project> getProListForData(int userId, int fileType) {
		List<Project> proList = new ArrayList<Project>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select project_id,project_name from tb_project where state=0 and user_id = ? and(data_format=? or data_format=?)";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, fileType);
			ps.setInt(3, FileFormat.NONE);
			rs = ps.executeQuery();
			while (rs.next()) {
				Project pro = new Project();
				pro.setProjectId(rs.getInt("project_id"));
				pro.setProjectName(rs.getString("project_name"));
				proList.add(pro);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return proList;
	}

	@Override
	public int addDataToPro(int projectId, int dataId) {
		log.info("用户" + super.userName + "将数据" + dataId + "添加到项目" + projectId);
		int addNum = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String sql = "insert into tb_data_project_relat(project_id,file_id) values(?,?)";
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);

			ps = conn.prepareStatement(sql);
			ps.setInt(1, projectId);
			ps.setInt(2, dataId);
			addNum = ps.executeUpdate();
			// 检查项目文件格式字段是否有值，若没有，则修改格式字段值
			String formatSql = "select data_format from tb_project where project_id=?";
			ps = conn.prepareStatement(formatSql);
			ps.setInt(1, projectId);
			rs = ps.executeQuery();
			if (rs.next()) {
				int format = rs.getInt("data_format");
				if (format == 0) {
					int fileFormat = 0;
					String fsql = "select file_format from tb_file where file_id=?";
					ps = conn.prepareStatement(fsql);
					ps.setInt(1, dataId);
					rs1 = ps.executeQuery();
					if (rs1.next()) {
						fileFormat = rs1.getInt("file_format");
					}
					String updateSql = "update tb_project set data_format=? where project_id=?";
					ps = conn.prepareStatement(updateSql);
					ps.setInt(1, fileFormat);
					ps.setInt(2, projectId);
					ps.executeUpdate();
				}
			}
			conn.commit();
		} catch (SQLException e) {
			log.info("用户" + super.userName + "将数据" + dataId + "添加到项目"
					+ projectId + "失败");
		} finally {
			ConnectManager.free(conn, ps, rs);
			ConnectManager.free(conn, ps, rs1);
		}
		return addNum;
	}

	@Override
	public boolean deleteSharedPro(int userId, int projectId) {
		boolean hasDel = false;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "delete from tb_file_share where sharer_id=? and project_id=? and state=1";
		String sqlP = "update tb_project set share = 0 where project_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, projectId);
			int delNum = ps.executeUpdate();
			ps = conn.prepareStatement(sqlP);
			ps.setInt(1, projectId);
			ps.executeUpdate();
			if (delNum > 0) {
				hasDel = true;
				log.info("删除项目共享记录成功");
			}
		} catch (SQLException e) {
			log.info("删除项目共享记录失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return hasDel;
	}

	@Override
	public Map<String, Integer> getAllErrorSharedPro(int userId, int projectId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select share_id from tb_file_share where sharer_id not in(select user_id from tb_file_share where project_id=? and state=1) and sharer_id!=? and project_id=? and state=1";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, projectId);
			ps.setInt(2, userId);
			ps.setInt(3, projectId);
			rs = ps.executeQuery();
			while (rs.next()) {
				map.put("share_id", rs.getInt("share_id"));
			}
		} catch (SQLException e) {
			log.info("查询所有已经被取消共享的共享信息失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return map;
	}

	@Override
	public boolean deleteSharedProById(int sharedId) {
		boolean hasDel = false;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "delete from tb_file_share where share_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sharedId);
			int delNum = ps.executeUpdate();
			if (delNum > 0) {
				hasDel = true;
				log.info("根据共享编号删除项目共享记录成功");
			}
		} catch (SQLException e) {
			log.info("根据共享编号删除项目共享记录失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return hasDel;
	}

	@Override
	public String getDataTypeById(int projectId) {
		String dataType = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select data_format from tb_project where project_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, projectId);
			rs = ps.executeQuery();
			while (rs.next()) {
				dataType = rs.getString("data_format");
			}
		} catch (SQLException e) {
			log.info("查询项目数据类型失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return dataType;
	}

	@Override
	public int updateDataFormatById(int projectId, int format) {
		int flag = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "update tb_project set data_format=? where project_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, format);
			ps.setInt(2, projectId);
			flag = ps.executeUpdate();
		} catch (SQLException e) {
			flag = 0;
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	@Override
	public int deleteProject(int projectId) {
		// 删除项目需要做三件事
		// 1、删除项目、数据关联表中关于该项目的关联关系
		// 2、删除报告表中关于该项目的报告信息（其下数据及其报告不删除）
		// 3、删除项目表中该项目信息
		// 删除关联关系
		int flag = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			log.info("用户" + super.userName + "删除项目编号为" + projectId + "的报告");
			String reportSql = "update tb_report set isdel=? where project_id=?";
			ps = conn.prepareStatement(reportSql);
			ps.setInt(1, DataState.DEELTED);
			ps.setInt(2, projectId);
			ps.executeUpdate();
			log.info("用户" + super.userName + "删除项目" + projectId);
			String proSql = "update tb_project set state=? where project_id=?";
			ps = conn.prepareStatement(proSql);
			ps.setInt(1, DataState.DEELTED);
			ps.setInt(2, projectId);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			log.info("用户" + super.userName + "删除项目" + projectId + "失败");
			flag = 0;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	@Override
	public Project getProjectById(int projectId) {
		Project pro = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from(select project_name,strain,pro_data_type,create_date,user_id from tb_project where project_id=?) t left join tb_data_type p on t.pro_data_type=p.type_id";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, projectId);
			rs = ps.executeQuery();
			if (rs.next()) {
				pro = new Project();
				pro.setProjectName(rs.getString("project_name"));
				pro.setStrain(rs.getString("strain"));
				pro.setProDataType(rs.getInt("pro_data_type"));
				pro.setDataTypeShow(rs.getString("type_desc"));
				pro.setCreateDate(rs.getTimestamp("create_date"));
				pro.setUserId(rs.getInt("user_id"));
			}
		} catch (SQLException e) {
			log.info("查询项目信息失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return pro;
	}

	@Override
	public List<Integer> getProIdsByFileId(int fileId) {
		List<Integer> proIds = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select dp.project_id from tb_data_project_relat dp,tb_project p where dp.project_id=p.project_id and p.state=0 and dp.file_id=?;";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, fileId);
			rs = ps.executeQuery();
			proIds = new ArrayList<Integer>();
			if (rs.next()) {
				proIds.add(rs.getInt("project_id"));
			}
		} catch (SQLException e) {
			log.info("获取数据的所属的项目编号失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return proIds;
	}
}
