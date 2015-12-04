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
import com.nova.constants.DataState;
import com.nova.dao.IDataDao;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.Data;
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
        ResultSet rs = null;
		int result = 0;
        String sql = "insert into tb_file(user_id,file_name,create_date,md5,state) values(?,?,now(),?,?)";
		try {
			conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, data.getUserId());
            ps.setString(2, data.getFileName());
            ps.setString(3, data.getMd5());
            ps.setInt(4, data.getState());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                result = (int) rs.getLong(1);
            }
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
	public int updateDataInfoByFileId(Data data) {
		int flag = 1;
		Connection conn = null;
		PreparedStatement ps = null;
        String sql = "update tb_file set data_key=?,size=?,path=?,another_name=?,file_format=?,state=? where file_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
            ps.setString(1, data.getDataKey());
            ps.setLong(2, data.getSize());
            ps.setString(3, data.getPath());
            ps.setString(4, data.getAnotherName());
            ps.setInt(5, data.getFileFormat());
            ps.setInt(6, data.getState());
            ps.setInt(7, data.getFileId());
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

	@Override
	public int dataRunning(String appIds) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int num = 0;
		String sql = "select count(*) from tb_report r,tb_data_project_relat d where d.project_id = r.project_id and r.flag = 1 and r.isdel = 0 and r.state = 1 and r.software_id in ("+appIds+");";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				 num = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return num;
	}
}