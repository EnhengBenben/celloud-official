package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nova.constants.DataState;
import com.nova.constants.FileFormat;
import com.nova.constants.ReportState;
import com.nova.dao.IDataDao;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.Data;
import com.nova.sdo.User;
import com.nova.utils.ConnectManager;

public class DataDaoImpl extends BaseDao implements IDataDao {
	Logger log = Logger.getLogger(DataDaoImpl.class);

	@Override
	public PageList<Data> getDataList(String dataTag, Page page, int userId,
			int type, String sort) {
		log.info("用户" + super.userName + "访问自己的数据列表");
		if (null == page) {// 若page为null,则从第1页开始
			page = new Page(10, 1);
		}
		PageList<Data> dataPageList = new PageList<Data>();

		StringBuffer queryBuff = new StringBuffer();
		StringBuffer countBuff = new StringBuffer();
		queryBuff
				.append("select * from(select distinct t1.*,case when s.file_id is null then 0 else s.file_id end sfile_id from(select t.*,u.role from(select f.file_id,f.file_name,f.size,f.user_id,f.data_key,f.strain,f.data_tags,f.create_date,f.file_format,f.state,f.sample,f.another_name,r.project_id,(select count(software_id) from tb_report p where p.file_id=f.file_id and p.flag=0 and p.state=3 and p.readed=0) as rNum,(select count(software_id) from tb_report x,tb_data_project_relat y where y.file_id=f.file_id and y.project_id=x.project_id and x.flag=1 and x.state!=3 and x.isdel=0) as isRun from tb_file f left join tb_data_project_relat r on f.file_id = r.file_id group by f.file_id order by f.create_date desc) t,tb_user u where t.user_id="
						+ userId);
		queryBuff
				.append(" and u.user_id=t.user_id) t1 left join tb_file_share s on t1.file_id=s.file_id and s.sharer_id=")
				.append(userId);

		if ((null != dataTag) && !("".equals(dataTag))
				&& !("null".equals(dataTag))) {
			log.info("用户" + super.userName + "搜索关于" + dataTag + "的数据列表");
			queryBuff.append(" where t1.data_tags like '%" + dataTag
					+ "%' or t1.file_name like '%" + dataTag
					+ "%' or t1.another_name like '%" + dataTag + "%'");
		}
		queryBuff.append(") t2 where t2.state=" + DataState.ACTIVE);
		if (type == 1) {// 按照文件名进行排序
			queryBuff.append(" order by t2.file_name " + sort);
		} else if (type == 2) {// 按照上传时间进行排序
			queryBuff.append(" order by t2.create_date " + sort);
		} else if (type == 3) {// 按照物种字段进行排序
			queryBuff.append(" order by t2.strain " + sort);
		} else if (type == 4) {// 按照数据标签进行排序
			queryBuff.append(" order by t2.data_tags " + sort);
		} else {
			queryBuff.append(" order by t2.create_date asc");
		}
		int start = (page.getCurrentPage() - 1) * page.getPageSize();
		queryBuff.append(" limit " + start + "," + page.getPageSize());
		countBuff
                .append("select count(*) data_count from(select distinct t1.*,s.file_id sfile_id from(select t.*,u.role from(select f.file_id,f.file_name,f.size,f.user_id,f.strain,f.data_tags,f.create_date,f.file_format,f.state,f.another_name,r.project_id from tb_file f left join tb_data_project_relat r on f.file_id = r.file_id group by f.file_id order by f.create_date desc) t,tb_user u where t.user_id="
						+ userId);
		countBuff
				.append(" and u.user_id=t.user_id) t1 left join tb_file_share s on t1.file_id=s.file_id");
		if ((null != dataTag) && !("".equals(dataTag))
				&& !("null".equals(dataTag))) {
			countBuff.append(" where t1.data_tags like '%" + dataTag
					+ "%' or t1.file_name like '%" + dataTag + "%'");
		}
		countBuff.append(")t2 where t2.state=" + DataState.ACTIVE);
		Connection conn = null;
		PreparedStatement qps = null;
		PreparedStatement cps = null;
		ResultSet qrs = null;
		ResultSet crs = null;
		List<Data> datas = new ArrayList<Data>();
		try {
			conn = ConnectManager.getConnection();
			qps = conn.prepareStatement(queryBuff.toString());
			qrs = qps.executeQuery();
			cps = conn.prepareStatement(countBuff.toString());
			crs = cps.executeQuery();
			while (qrs.next()) {
				Data data = new Data();
				int fileId = qrs.getInt("file_id");
				data.setFileId(fileId);

				int projectId = qrs.getInt("project_id");
				data.setProjectId(projectId);
				if (projectId == 0) {
					data.setInProject(false);
				} else {
					data.setInProject(true);
				}
				data.setDataKey(qrs.getString("data_key"));
				data.setUserRole(qrs.getInt("role"));
				data.setShareFileId(qrs.getInt("sfile_id"));
				data.setFileName(qrs.getString("file_name"));
				data.setSize(qrs.getLong("size"));
				data.setStrain(qrs.getString("strain"));
				data.setFileFormat(qrs.getInt("file_format"));
				data.setDataTags(qrs.getString("data_tags"));
				data.setCreateDate(qrs.getTimestamp("create_date"));
				data.setSample(qrs.getString("sample"));
				data.setReportNum(qrs.getInt("rNum"));
                data.setAnotherName(qrs.getString("another_name"));
				data.setIsRunning(qrs.getInt("isRun"));
				datas.add(data);
			}
			if (crs.next()) {
				page.make(crs.getInt("data_count"));
			} else {
				page.make(0);
			}
		} catch (SQLException e) {
			log.error("用户" + super.userName + "访问自己的数据列表失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, qps, qrs);
			ConnectManager.free(conn, cps, crs);
		}
		dataPageList.setDatas(datas);
		dataPageList.setPage(page);
		return dataPageList;
	}

	@Override
	public PageList<Data> getDataListSharedToMe(String dataTag, Page page,
			int userId, int type, String sort) {
		log.info("用户" + super.userName + "访问共享给自己的数据列表");
		if (null == page) {// 若page为null,则从第1页开始
			page = new Page(10, 1);
		}
		PageList<Data> dataPageList = new PageList<Data>();
		if ((null == dataTag) || ("".equals(dataTag))
				|| ("null".equals(dataTag))) {
			dataTag = "";
		}
		StringBuffer queryBuff = new StringBuffer();
		StringBuffer countBuff = new StringBuffer();

		queryBuff
                .append("select distinct * from(select t.* from(select file_id,file_name,data_key,f.create_date,username,role,strain,data_tags,another_name,(select count(fs.file_id) from tb_file_share fs where fs.file_id=f.file_id and fs.sharer_id="
						+ userId
						+ ") sharedCount from tb_file f,tb_user u where f.user_id=u.user_id and f.file_id in (select file_id from tb_file_share where user_id="
						+ userId + " and state=0) group by f.file_id) t");
		queryBuff
				.append(" left join tb_data_project_relat r on t.file_id=r.file_id");
		queryBuff.append(") t1");
		if ((null != dataTag) && !("".equals(dataTag))
				&& !("null".equals(dataTag))) {
			log.info("用户" + super.userName + "在共享列表中搜索关于" + dataTag + "的数据信息");
			queryBuff.append(" where t1.data_tags like '%" + dataTag
					+ "%' or t1.file_name like '%" + dataTag + "%'");
		}
		int start = (page.getCurrentPage() - 1) * page.getPageSize();
		if (type == 1) {// 按照文件名进行排序
			queryBuff.append(" order by file_name " + sort);
		} else if (type == 2) {// 按照上传时间进行排序
			queryBuff.append(" order by create_date " + sort);
		} else if (type == 3) {// 按照物种字段进行排序
			queryBuff.append(" order by strain " + sort);
		} else if (type == 4) {// 按照数据标签进行排序
			queryBuff.append(" order by data_tags " + sort);
		} else {
			queryBuff.append(" order by create_date asc");
		}
		queryBuff.append(" limit " + start + "," + page.getPageSize());
		countBuff
				.append("select count(*) data_count from(select distinct t.* from(select file_id,file_name,f.create_date,username,role,strain,data_tags from tb_file f,tb_user u where f.user_id=u.user_id and f.file_id in (select file_id from tb_file_share where user_id="
						+ userId
						+ " and state=0) group by f.file_id) t left join tb_data_project_relat r on t.file_id=r.file_id) t1");
		if (!"".equals(dataTag)) {
			countBuff.append(" where t1.data_tags like '%" + dataTag
					+ "%' or t1.file_name like '%" + dataTag + "%'");
		}
		Connection conn = null;
		PreparedStatement qps = null;
		PreparedStatement cps = null;
		ResultSet qrs = null;
		ResultSet crs = null;
		List<Data> datas = new ArrayList<Data>();
		try {
			conn = ConnectManager.getConnection();
			qps = conn.prepareStatement(queryBuff.toString());
			qrs = qps.executeQuery();
			cps = conn.prepareStatement(countBuff.toString());
			crs = cps.executeQuery();
			while (qrs.next()) {
				Data data = new Data();
				int fileId = qrs.getInt("file_id");
				data.setFileId(fileId);
				// int projectId = qrs.getInt("project_id");
				// if (projectId == 0) {
				// data.setInProject(false);
				// } else {
				// data.setInProject(true);
				// }
				data.setDataKey(qrs.getString("data_key"));
				data.setUserRole(qrs.getInt("role"));
				data.setFileName(qrs.getString("file_name"));
				data.setStrain(qrs.getString("strain"));
				data.setDataTags(qrs.getString("data_tags"));
				data.setCreateDate(qrs.getDate("create_date"));
				data.setOwner(qrs.getString("username"));
				data.setSharedCount(qrs.getInt("sharedCount"));
                data.setAnotherName(qrs.getString("another_name"));
				datas.add(data);
			}
			if (crs.next()) {
				page.make(crs.getInt("data_count"));
			}
		} catch (SQLException e) {
			log.info("用户" + super.userName + "访问共享给自己的数据列表失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, qps, qrs);
			ConnectManager.free(conn, cps, crs);
		}
		dataPageList.setDatas(datas);
		dataPageList.setPage(page);
		return dataPageList;
	}

	/**
	 * 判断一个数据file是否已分配项目
	 * 
	 * @param fileId
	 * @return
	 */
	@Override
	public boolean inProject(int fileId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean inPro = false;
		String sql = "select * from tb_data_project_relat where file_id="
				+ fileId;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				inPro = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return inPro;
	}

	@Override
	public int addDataTag(int fileId, String tag) {
		log.info("用户" + super.userName + "修改数据" + fileId + "数据标签信息为" + tag);
		Connection conn = null;
		PreparedStatement ps = null;
		int result = 0;
		String sql = "update tb_file set data_tags=? where file_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, tag);
			ps.setInt(2, fileId);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			log.info("用户" + super.userName + "修改数据" + fileId + "数据标签信息为" + tag
					+ "失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
	public int allocateDataToProject(int dataId, int projectId) {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement dps = null;
		int result = 0;
		String delSql = "delete from tb_data_project_relat where file_id=?";
		String sql = "insert into tb_data_project_relat(file_id,project_id) values(?,?)";
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			dps = conn.prepareStatement(delSql);
			dps.setInt(1, dataId);

			ps = conn.prepareStatement(sql);
			ps.setInt(1, dataId);
			ps.setInt(2, projectId);

			dps.executeUpdate();
			result = ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
			ConnectManager.free(conn, dps, null);
		}
		return result;
	}

	@Override
	public int allocateDatasToProject(String dataIds, int projectId) {
		String[] dataIdArr = dataIds.split(",");
		Connection conn = null;
		PreparedStatement ps = null;
		int result = 1;
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "insert into tb_data_project_relat(file_id,project_id) values(?,?)";
			for (String dataId : dataIdArr) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(dataId));
				ps.setInt(2, projectId);
				ps.executeUpdate();
			}
			String sqlRelat = "update tb_project set num=(select count(*) from tb_data_project_relat where project_id=?),size=(select sum(f.size) from tb_data_project_relat r,tb_file f where r.file_id=f.file_id and project_id=?) where project_id = ?";
			ps = conn.prepareStatement(sqlRelat);
			ps.setInt(1, projectId);
			ps.setInt(2, projectId);
			ps.setInt(3, projectId);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				result = 0;
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
	public int shareData(int fileId, String userIds, int userId) {
		log.info("用户" + super.userName + "将数据" + fileId + "共享给用户" + userIds);
		Connection conn = null;
		PreparedStatement ps = null;
		int result = 1;
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			String[] ids = userIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				String sql = "insert into tb_file_share(file_id,user_id,sharer_id,state) values("
						+ fileId + "," + ids[i] + "," + userId + ",0)";
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
			}
			conn.commit();
		} catch (SQLException e) {
			result = 0;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.error("用户" + super.userName + "将数据" + fileId + "共享给用户"
					+ userIds + "失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
	public List<Data> getDataListByProjectId(int projectId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Data> dataList = new ArrayList<Data>();
		String sql = "select file_id,file_name,data_key,strain,data_tags,size,create_date,sample,another_name from tb_file where file_id in(select file_id from tb_data_project_relat where project_id=?)";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, projectId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Data data = new Data();
				data.setFileId(rs.getInt("file_id"));
				data.setFileName(rs.getString("file_name"));
				data.setDataKey(rs.getString("data_key"));
				data.setStrain(rs.getString("strain"));
				data.setDataTags(rs.getString("data_tags"));
				data.setSize(rs.getLong("size"));
				data.setProjectId(projectId);
				data.setCreateDate(rs.getTimestamp("create_date"));
				data.setSample(rs.getString("sample"));
				data.setAnotherName(rs.getString("another_name"));
				dataList.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return dataList;
	}

	@Override
	public int addDataInfo(Data data) {
		log.info("用户" + super.userName + "上传文件" + data.getFileName());
		Connection conn = null;
		PreparedStatement ps = null;
		int result = 0;
		String sql = "insert into tb_file(user_id,data_key,file_name,strain,size,create_date,path,file_format,md5,another_name) values(?,?,?,?,?,now(),?,?,?,?)";
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
			ps.setString(8, data.getMd5());
			ps.setString(9, data.getAnotherName());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			log.info("用户" + super.userName + "上传文件" + data.getFileName()
					+ "信息保存失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
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
			log.info("用户" + super.userName + "上传文件" + data.getFileName()
					+ "信息保存失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return data;
	}

    @Override
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
				data.setFileFormat(rs.getInt("file_format"));
				data.setAnotherName(rs.getString("another_name"));
				data.setMd5(rs.getString("md5"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return data;
    }

	@Override
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

	@Override
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

	@Override
	public Long getAllDataNum(int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long dataNum = 0L;
		try {
			conn = ConnectManager.getConnection();
			String sql = "select count(*) c from (select * from tb_file_share where user_id=? and state=1) t";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			if (rs.next()) {
				dataNum = rs.getLong("c");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return dataNum;
	}

	@Override
	public int addDataStrain(int fileId, String strain) {
		log.info("用户" + super.userName + "修改数据" + fileId + "物种信息为" + strain);
		Connection conn = null;
		PreparedStatement ps = null;
		int result = 0;
		String sql = "update tb_file set strain=? where file_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, strain);
			ps.setInt(2, fileId);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			log.info("用户" + super.userName + "修改数据" + fileId + "物种信息为" + strain
					+ "失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
	public List<User> getSharedUserListByFileId(int fileId, int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<User> userList = new ArrayList<User>();
		String sql = "select user_id,username from tb_user where user_id in(select distinct user_id from tb_file_share where file_id="
				+ fileId + " and sharer_id=" + userId + " and state=0)";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
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
	public Data getDataById(String dataId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Data data = null;
		String sql = "select file_name,data_key,another_name,file_format from tb_file where file_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, dataId);
			rs = ps.executeQuery();
			if (rs.next()) {
				data = new Data();
				data.setFileName(rs.getString("file_name"));
				data.setDataKey(rs.getString("data_key"));
				data.setAnotherName(rs.getString("another_name"));
				data.setFileFormat(rs.getInt("file_format"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return data;
	}

	@Override
	public List<Map<String, String>> getStrainItem(int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, String>> strainList = new ArrayList<Map<String, String>>();
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
	public List<Map<String, String>> getDataStrainItem(int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, String>> strainList = new ArrayList<Map<String, String>>();
		String sql = "select distinct strain from tb_file where user_id=?";
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
	public String getProjectNameByDataId(int dataId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String treeName = null;
		String sql = "select tree_name from tb_file_tree where file_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dataId);
			rs = ps.executeQuery();
			if (rs.next()) {
				treeName = rs.getString("tree_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return treeName;
	}

	@Override
	public int getProjectIdByDataId(int dataId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int projectId = 0;
		String sql = "select project_id from tb_data_project_relat where file_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dataId);
			rs = ps.executeQuery();
			if (rs.next()) {
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
	public int allocateDataToProjects(int dataId, String proIds) {
		log.info("用户" + super.userName + "将数据" + dataId + "添加到项目" + proIds);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		int result = 1;
		List<Integer> oldProIds = new ArrayList<Integer>();
		List<Integer> addProIds = new ArrayList<Integer>();
		List<Integer> delProIds = new ArrayList<Integer>();
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			// 先保存之前该数据所在项目
			String oldRlatSql = "select project_id from tb_data_project_relat where file_id=?";
			ps = conn.prepareStatement(oldRlatSql);
			ps.setInt(1, dataId);
			rs = ps.executeQuery();
			while (rs.next()) {
				oldProIds.add(rs.getInt("project_id"));
			}
			String[] proIdArr = proIds.split(",");
			for (String proId : proIdArr) {
				if (proId != null && !"".equals(proId)) {
					boolean flag = false;
					for (Integer oldId : oldProIds) {
						if (Integer.parseInt(proId) == oldId) {
							flag = true;
						}
					}
					if (!flag) {
						addProIds.add(Integer.parseInt(proId));
					}
				}
			}
			for (Integer proId : oldProIds) {
				boolean flag = false;
				for (String newId : proIdArr) {
					if (newId != null && !"".equals(newId)) {
						if (Integer.parseInt(newId) == proId) {
							flag = true;
						}
					}
				}
				if (!flag) {
					delProIds.add(proId);
				}
			}
			// 删除关联
			for (Integer proId : delProIds) {
				String delSql = "delete from tb_data_project_relat where file_id=? and project_id=?";
				ps = conn.prepareStatement(delSql);
				ps.setInt(1, dataId);
				ps.setInt(2, proId);
				ps.executeUpdate();
				// 操作完检查项目下是否有数据，若没有，修改项目数据格式为最初始状态
				String countSql = "select * from tb_data_project_relat where project_id=?";
				ps = conn.prepareStatement(countSql);
				ps.setInt(1, proId);
				rs = ps.executeQuery();
				if (!rs.next()) {
					String updateSql = "update tb_project set data_format=? where project_id=?";
					ps = conn.prepareStatement(updateSql);
					ps.setInt(1, FileFormat.NONE);
					ps.setInt(2, proId);
					ps.executeUpdate();
				}
				// 删除报告
				String delReportSql = "update tb_report set state=? where project_id=?";
				ps = conn.prepareStatement(delReportSql);
				ps.setInt(1, ReportState.UNRUN);
				ps.setInt(2, proId);
				ps.executeUpdate();
			}
			// 添加关联

			// 获取文件格式
			int fileFormat = getDataById(dataId + "").getFileFormat();
			for (Integer proId : addProIds) {
				// 检查项目数据格式字段是否有值
				// 1、若没有，表示肯定没有数据，直接添加数据然后修改项目文件格式字段
				// 2、若有值，检查数据与项目格式是否一致，一致允许添加，不一致则不允许添加
				String formatSql = "select data_format from tb_project where project_id=?";
				ps = conn.prepareStatement(formatSql);
				ps.setInt(1, proId);
				rs = ps.executeQuery();
				if (rs.next()) {
					int format = rs.getInt("data_format");
					if (format == 0) {
						// 没有格式，为情况一
						String insertSql = "insert into tb_data_project_relat(file_id,project_id) values(?,?)";
						ps = conn.prepareStatement(insertSql);
						ps.setInt(1, dataId);
						ps.setInt(2, proId);
						ps.executeUpdate();

						String updateSql = "update tb_project set data_format=? where project_id=?";
						ps = conn.prepareStatement(updateSql);
						ps.setInt(1, fileFormat);
						ps.setInt(2, proId);
						ps.executeUpdate();
					} else {
						if (format == fileFormat) {
							String checkSql = "select * from tb_data_project_relat where file_id=? and project_id=?";
							ps = conn.prepareStatement(checkSql);
							ps.setInt(1, dataId);
							ps.setInt(2, proId);
							rs = ps.executeQuery();
							if (!rs.next()) {
								String insertSql = "insert into tb_data_project_relat(file_id,project_id) values(?,?)";
								ps = conn.prepareStatement(insertSql);
								ps.setInt(1, dataId);
								ps.setInt(2, proId);
								ps.executeUpdate();
							}
						} else {
							result = 2;
							conn.rollback();
							return result;
						}
					}
				}
				// 删除报告
				String delReportSql = "update tb_report set state=? where project_id=?";
				ps = conn.prepareStatement(delReportSql);
				ps.setInt(1, ReportState.UNRUN);
				ps.setInt(2, proId);
				ps.executeUpdate();
			}
			conn.commit();
		} catch (SQLException e) {
			result = 0;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.info("用户" + super.userName + "将数据" + dataId + "添加到项目" + proIds
					+ "失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
			ConnectManager.free(conn, ps, rs1);
		}
		return result;
	}

	@Override
	public List<Data> getDataListByUserIdAppName(int userId, int dataType) {
		List<Data> dataList = new ArrayList<Data>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select file_id,data_key,file_name from tb_file where user_id=? and file_format=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, dataType);
			rs = ps.executeQuery();
			while (rs.next()) {
				Data data = new Data();
				data.setFileId(rs.getInt("file_id"));
				data.setDataKey(rs.getString("data_key"));
				data.setFileName(rs.getString("file_name"));
				dataList.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return dataList;
	}

	@Override
	public List<Data> getDataListByUserIdFileFormats(int userId, String formats) {
		List<Data> dataList = new ArrayList<Data>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select file_id,data_key,file_name from tb_file where user_id=? and file_format in("
				+ formats + ")";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Data data = new Data();
				data.setFileId(rs.getInt("file_id"));
				data.setDataKey(rs.getString("data_key"));
				data.setFileName(rs.getString("file_name"));
				dataList.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return dataList;
	}

	@Override
	public boolean deleteSharedDataById(int sharedId) {
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return hasDel;
	}

	@Override
	public boolean deleteSharedData(int userId, int dataId) {
		boolean hasDel = false;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "delete from tb_file_share where sharer_id=? and file_id=? and state=0";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, dataId);
			int delNum = ps.executeUpdate();
			if (delNum > 0) {
				hasDel = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return hasDel;
	}

	@Override
	public Map<String, Integer> getAllErrorSharedData(int userId, int dataId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select share_id from tb_file_share where sharer_id not in(select user_id from tb_file_share where file_id=? and state=0) and sharer_id!=? and file_id=? and state=0";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dataId);
			ps.setInt(2, userId);
			ps.setInt(3, dataId);
			rs = ps.executeQuery();
			while (rs.next()) {
				map.put("share_id", rs.getInt("share_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return map;
	}

	@Override
	public List<Data> getAllDataByFileType(int userId, int fileType) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Data> dataList = new ArrayList<Data>();
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select file_id,file_name,data_key from tb_file where state="
						+ DataState.ACTIVE + " and user_id =").append(userId)
				.append(" and file_format=").append(fileType);
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				Data data = new Data();
				data.setFileId(rs.getInt("file_id"));
				data.setFileName(rs.getString("file_name"));
				data.setDataKey(rs.getString("data_key"));
				dataList.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return dataList;
	}

	@Override
	public int delDatas(String dataIds) {
		int result = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			for (String dataId : dataIds.split(",")) {
				String dataSql = "update tb_file set state=? where file_id=?";
				ps = conn.prepareStatement(dataSql);
				ps.setInt(1, DataState.DEELTED);
				ps.setInt(2, Integer.parseInt(dataId));
				ps.executeUpdate();
			}
			conn.commit();
		} catch (SQLException e) {
			result = 0;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return result;
	}

	@Override
	public int importDataToPro(String dataIds, String proIds) {
		int result = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			int proFormat = 0;
			for (String proId : proIds.split(",")) {
				proFormat = 0;
				String proFormatSql = "select data_format from tb_project where project_id=?";
				ps = conn.prepareStatement(proFormatSql);
				ps.setInt(1, Integer.parseInt(proId));
				rs = ps.executeQuery();
				if (rs.next()) {
					proFormat = rs.getInt("data_format");
				}
				if (proFormat == 0) {// 项目下没有数据,即也没有报告信息
					for (String dataId : dataIds.split(",")) {
						String relatSql = "insert into tb_data_project_relat(file_id,project_id) values(?,?)";
						ps = conn.prepareStatement(relatSql);
						ps.setInt(1, Integer.parseInt(dataId));
						ps.setInt(2, Integer.parseInt(proId));
						ps.executeUpdate();
					}
					int fileFormat = getDataById(dataIds.split(",")[0])
							.getFileFormat();
					String updateFormatSql = "update tb_project set data_format=? where project_id=?";
					ps = conn.prepareStatement(updateFormatSql);
					ps.setInt(1, fileFormat);
					ps.setInt(2, Integer.parseInt(proId));
					ps.executeUpdate();
				} else {
					// 先保存原来所有的该项目下的数据编号
					String oldDataIdsSql = "select file_id from tb_data_project_relat where project_id=?";
					ps = conn.prepareStatement(oldDataIdsSql);
					ps.setInt(1, Integer.parseInt(proId));
					rs = ps.executeQuery();
					StringBuffer sb = new StringBuffer();
					while (rs.next()) {
						sb.append(rs.getInt("file_id") + ",");
					}
					String oldDataIds = sb.toString();
					if (!"".equals(oldDataIds)) {
						oldDataIds = oldDataIds.substring(0,
								oldDataIds.length() - 1);
					}
					for (String dataId : dataIds.split(",")) {
						String checkSql = "select * from tb_data_project_relat where file_id=? and project_id=?";
						ps = conn.prepareStatement(checkSql);
						ps.setInt(1, Integer.parseInt(dataId));
						ps.setInt(2, Integer.parseInt(proId));
						rs = ps.executeQuery();
						if (!rs.next()) {
							String relatSql = "insert into tb_data_project_relat(file_id,project_id) values(?,?)";
							ps = conn.prepareStatement(relatSql);
							ps.setInt(1, Integer.parseInt(dataId));
							ps.setInt(2, Integer.parseInt(proId));
							ps.executeUpdate();
						}
					}
					// 比较旧数据编号与新数据编号，若相同则不用修改报告信息，若有变动则将该项目所有报告信息修改为未运行状态
					boolean equal = Arrays.equals(dataIds.split(","),
							oldDataIds.split(","));
					if (!equal) {
						String updateReportSql = "update tb_report set state=? where project_id=?";
						ps = conn.prepareStatement(updateReportSql);
						ps.setInt(1, ReportState.UNRUN);
						ps.setInt(2, Integer.parseInt(proId));
						ps.executeUpdate();
					}
				}
			}
			conn.commit();
		} catch (SQLException e) {
			result = 0;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return result;
	}

	@Override
	public int updateDataInfoByFileId(Data data) {
		int flag = 1;
		Connection conn = null;
		PreparedStatement ps = null;
        String sql = "update tb_file set strain=?,data_tags=?,sample=?,another_name=? where file_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, data.getStrain());
			ps.setString(2, data.getDataTags());
			ps.setString(3, data.getSample());
            ps.setString(4, data.getAnotherName());
            ps.setInt(5, data.getFileId());
			ps.executeUpdate();
		} catch (SQLException e) {
			flag = 0;
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	@Override
	public String getDataKeyListByDataIds(String dataIds) {
		String dataKeyList = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		try {
			conn = ConnectManager.getConnection();
			String sql = "select data_key from tb_file where file_id in("
					+ dataIds + ")";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				sb.append(rs.getString("data_key") + ";");
			}
			dataKeyList = sb.toString();
			if (!dataKeyList.equals("")) {
				dataKeyList = dataKeyList
						.substring(0, dataKeyList.length() - 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return dataKeyList;
	}

	@Override
	public List<Data> getDataList(String userId, String start, String end) {
		String sql = "select res.user_id,res.file_id,res.data_key,res.file_name,res.create_date,res.path,GROUP_CONCAT(distinct(res.software_name)) as soft from"
				+ " (select f.user_id,f.file_id,f.data_key,f.file_name,f.create_date,f.path,s.software_name from"
				+ " (select *"
				+ " from tb_file"
				+ " where user_id in ("
				+ userId
				+ " ) and create_date between '"
				+ start
				+ " 00:00:00' and '"
				+ end
				+ " 23:59:59') f"
				+ " left join tb_report r on r.file_id = f.file_id"
				+ " left join tb_software s on r.software_id = s.software_id) res"
				+ " GROUP BY res.user_id,res.file_id"
				+ " order by res.user_id,res.file_id";
		Connection conn = ConnectManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Data> datas = new ArrayList<Data>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Data data = new Data();
				data.setUserId(rs.getInt("user_id"));
				data.setDataKey(rs.getString("data_key"));
				data.setFileName(rs.getString("file_name"));
				data.setCreateDate(rs.getTimestamp("create_date"));
				data.setPath(rs.getString("path"));
				data.setOwner(rs.getString("soft"));
				datas.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return datas;
	}

	@Override
	public Date getLatestDate(int userId) {
		String sql = "select max(create_date) max_date from tb_file f where f.user_id=? and f.state=0";
		Connection conn = ConnectManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Date date = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				date = rs.getDate("max_date");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return date;
	}

	@Override
	public int updateDataInfoListByFileId(String dataIds, Data data) {
		int flag = 1;
		Connection conn = null;
		PreparedStatement ps = null;
        String sql = "update tb_file set strain=?,data_tags=?,sample=?,another_name=? where file_id in ("
				+ dataIds + ")";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, data.getStrain());
			ps.setString(2, data.getDataTags());
			ps.setString(3, data.getSample());
            ps.setString(4, data.getAnotherName());
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
	public List<Data> getStrainDataKeySampleById(String dataIds) {
		List<Data> dataList = new ArrayList<Data>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			String sql = "select file_id,file_name,data_key,strain,data_tags,sample from tb_file where file_id in("
					+ dataIds + ")";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Data data = new Data();
				data.setFileId(rs.getInt("file_id"));
				data.setFileName(rs.getString("file_name"));
				data.setDataKey(rs.getString("data_key"));
				data.setStrain(rs.getString("strain"));
				data.setDataTags(rs.getString("data_tags"));
				data.setSample(rs.getString("sample"));
				dataList.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return dataList;
	}

	@Override
	public PageList<Data> getAppDataById(Integer userId, Page page,
			String fileType, Integer softwareId) {
		List<Data> dataList = new ArrayList<Data>();
		Connection conn = null;
		PreparedStatement qps = null;
		PreparedStatement cps = null;
		ResultSet qrs = null;
		ResultSet crs = null;
		if (null == page) {// 若page为null,则从第1页开始
			page = new Page(10, 1);
		}
		PageList<Data> pageList = new PageList<Data>();
		try {
			conn = ConnectManager.getConnection();
			int start = (page.getCurrentPage() - 1) * page.getPageSize();
			String querySql = "select f.*,(select count(*) from tb_report r,tb_data_project_relat dp where dp.file_id=f.file_id and r.project_id=dp.project_id and r.state=3 and r.flag=1 and software_id=? and isdel=0)"
					+ " as isRuned,(select count(*) from tb_report r,tb_data_project_relat dp where dp.file_id=f.file_id and r.project_id=dp.project_id and r.state!=3 and r.flag=1 and software_id=? and isdel=0)"
					+ " as isRunning from tb_file f where f.user_id=? and f.file_format in (?) and f.state=? order by create_date desc limit "
					+ start + "," + page.getPageSize();
			String countSql = "select count(*) dataNum from (select f.*,(select count(*) from tb_report r,tb_data_project_relat dp where dp.file_id=f.file_id and r.project_id=dp.project_id and r.state=3 and r.flag=1 and software_id=? and isdel=0)"
					+ " as isRuned,(select count(*) from tb_report r,tb_data_project_relat dp where dp.file_id=f.file_id and r.project_id=dp.project_id and r.state!=3 and r.flag=1 and software_id=? and isdel=0)"
					+ " as isRunning from tb_file f where f.user_id=? and f.file_format in (?) and f.state=?) t";
			qps = conn.prepareStatement(querySql);
			qps.setInt(1, softwareId);
			qps.setInt(2, softwareId);
			qps.setInt(3, userId);
			qps.setString(4, fileType);
			qps.setInt(5, DataState.ACTIVE);
			qrs = qps.executeQuery();
			while (qrs.next()) {
				Data data = new Data();
				data.setFileId(qrs.getInt("file_id"));
				data.setFileName(qrs.getString("file_name"));
				data.setDataKey(qrs.getString("data_key"));
				data.setIsRunned(qrs.getInt("isRuned"));
				data.setIsRunning(qrs.getInt("isRunning"));
				dataList.add(data);
			}
			pageList.setDatas(dataList);
			cps = conn.prepareStatement(countSql);
			cps.setInt(1, softwareId);
			cps.setInt(2, softwareId);
			cps.setInt(3, userId);
			cps.setString(4, fileType);
			cps.setInt(5, DataState.ACTIVE);
			crs = cps.executeQuery();
			if (crs.next()) {
				page.make(crs.getInt("dataNum"));
			} else {
				page.make(0);
			}
			pageList.setPage(page);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, qps, qrs);
			ConnectManager.free(conn, cps, crs);
		}
		return pageList;
	}

    @Override
    public int updateAnotherNameById(Data data) {
        int flag = 1;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update tb_file set another_name=? where file_id=?";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, data.getAnotherName());
            ps.setInt(2, data.getFileId());
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

	@Override
	public Integer clientAdd(Data data) {
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

	@Override
	public Integer clientUpdate(Data data) {
		int flag = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "update tb_file set size=?,file_format=?,state=? where data_key=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, data.getSize());
			ps.setInt(2, data.getFileFormat());
			ps.setInt(3, DataState.ACTIVE);
			ps.setString(4, data.getDataKey());
			ps.executeUpdate();
		} catch (SQLException e) {
			flag = 0;
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	@Override
	public List<Data> getDataByDataKeys(String dataKeys, Integer userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Data> list = new ArrayList<Data>();
		String sql = "select * from tb_file where user_id=? and state=0 and data_key in ("
				+ dataKeys
				+ ")";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Data data = new Data();
				data.setFileId(rs.getInt("file_id"));
				data.setUserId(rs.getInt("user_id"));
				data.setDataKey(rs.getString("data_key"));
				data.setFileName(rs.getString("file_name"));
				data.setStrain(rs.getString("strain"));
				data.setDataTags(rs.getString("data_tags"));
				data.setSize(rs.getLong("size"));
				data.setCreateDate(rs.getDate("create_date"));
				data.setFileFormat(rs.getInt("file_format"));
				data.setSample(rs.getString("sample"));
				data.setAnotherName(rs.getString("another_name"));
				list.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}
}