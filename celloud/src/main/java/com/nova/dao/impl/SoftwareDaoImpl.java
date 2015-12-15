package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nova.constants.Attribute;
import com.nova.constants.ReportState;
import com.nova.constants.SoftRecommend;
import com.nova.constants.SoftWareOffLineState;
import com.nova.constants.SoftWareState;
import com.nova.dao.ISoftwareDao;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.ParamStrain;
import com.nova.sdo.Project;
import com.nova.sdo.Software;
import com.nova.utils.ConnectManager;

/**
 * @ClassName: SoftwareDaoImpl
 * @Description: 查询,修改,增加,删除
 * @author: ASUS
 * @data: 2012-5-14
 */
public class SoftwareDaoImpl extends BaseDao implements ISoftwareDao {
	Logger log = Logger.getLogger(SoftwareDaoImpl.class);

	@Override
	public List<Software> getAllDb(int classifyId, int userId, int type) {
		log.info("获取每个类别软件的列表");
		List<Software> list = new ArrayList<Software>();
        String sql1 = "select * from tb_software as a where a.software_id in(select b.software_id from tb_user_software as b where b.user_id=?) and off_line=0 and a.type=? and a.classify_id=?";
        String sql2 = "select * from tb_software as a where a.software_id not in(select b.software_id from tb_user_software as b where b.user_id=?) and off_line=0 and a.type=? and a.classify_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql1);
			ps.setInt(1, userId);
			ps.setInt(2, type);
            ps.setInt(3, classifyId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Software sf = new Software();
				sf.setSoftwareId(rs.getInt("software_id"));
				sf.setSoftwareName(rs.getString("software_name"));
				sf.setSoftwareEntry(rs.getString("software_entry"));
				sf.setHost(rs.getString("host"));
				sf.setPictureName(rs.getString("picture_name"));
				sf.setBhri(rs.getInt("bhri"));
				sf.setClassify(rs.getString("classify"));
				sf.setClassifyId(rs.getInt("classify_id"));
				sf.setCreateDate(rs.getDate("create_date"));
				sf.setDescription(rs.getString("description"));
				sf.setType(rs.getInt("type"));
				sf.setProcessName(rs.getString("process_name"));
                sf.setCompanyId(rs.getInt("company_id"));
                sf.setAttribute(rs.getInt("attribute"));

				sf.setIsAdd("yes");
				list.add(sf);
			}

			ps = conn.prepareStatement(sql2);
			ps.setInt(1, userId);
			ps.setInt(2, type);
            ps.setInt(3, classifyId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Software sf = new Software();

				sf.setSoftwareId(rs.getInt("software_id"));
				sf.setSoftwareName(rs.getString("software_name"));
				sf.setSoftwareEntry(rs.getString("software_entry"));
				sf.setHost(rs.getString("host"));
				sf.setPictureName(rs.getString("picture_name"));
				sf.setBhri(rs.getInt("bhri"));
				sf.setClassify(rs.getString("classify"));
				sf.setClassifyId(rs.getInt("classify_id"));
				sf.setCreateDate(rs.getDate("create_date"));
				sf.setDescription(rs.getString("description"));
				sf.setType(rs.getInt("type"));
				sf.setProcessName(rs.getString("process_name"));
                sf.setCompanyId(rs.getInt("company_id"));
                sf.setAttribute(rs.getInt("attribute"));

				sf.setIsAdd("no");
				list.add(sf);
			}
		} catch (SQLException e) {
			log.error("获取每个类别软件的列表" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	/**
	 * @Title: updateBhri
	 * @Description: (更新人气指数)
	 * @param softwareId
	 * @throws
	 */
	@Override
	public void updateBhri(int softwareId) {
		log.info("更新人气指数");
		String sql = "update tb_software set bhri=bhri+1 where software_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, softwareId);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.error("更新人气指数发生异常" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
	}

	/**
	 * @Title: saveSoftwareOnDesk
	 * @Description: (添加软件，保存软件到桌面上)
	 * @param userId
	 * @param softwareId
	 * @param deskNo
	 * @return
	 * @throws
	 */
	@Override
	public boolean saveSoftwareOnDesk(int userId, int softwareId, int deskNo) {
		log.info("往桌面上增加应用");
		boolean result = false;
		String sql = "insert into tb_user_software(user_id,software_id,desk_no,create_date) values(?,?,?,now())";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, softwareId);
			ps.setInt(3, deskNo);
			int i = ps.executeUpdate();
			if (i > 0) {
				result = true;
			}
			log.info("往桌面上增加应用" + userId + softwareId + deskNo);
		} catch (SQLException e) {
			log.error("往桌面上增加应用" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	/**
	 * @Title: cancleSoftwareOnDesk
	 * @Description: (删除桌面上的软件)
	 * @param userId
	 * @param softwareId
	 * @throws
	 */
	@Override
	public void cancleSoftwareOnDesk(int userId, int softwareId) {
		log.info("删除桌面上的软件");
		String sql = "delete from tb_user_software where user_id=? and software_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, softwareId);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.error("删除桌面上的软件" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
	}

	/**
	 * @Title: getSoftWareOfUser
	 * @Description: (根据用户userId获取该用户桌面上的软件)
	 * @param userId
	 * @return
	 * @throws
	 */
	@Override
	public List<Software> getSoftWareOfUser(int userId) {
		log.info("根据用户" + userId + "获取该用户桌面上的软件");
		List<Software> list = new ArrayList<Software>();
        String sql = "select * from tb_software as a,tb_user_software as b where a.software_id = b.software_id and b.user_id=? and a.off_line=0 order by b.desk_no,b.create_date";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Software sf = new Software();
				sf.setSoftwareId(rs.getInt("software_id"));
				sf.setSoftwareName(rs.getString("software_name"));
				sf.setSoftwareEntry(rs.getString("software_entry"));
				sf.setHost(rs.getString("host"));
				sf.setPictureName(rs.getString("picture_name"));
				sf.setBhri(rs.getInt("bhri"));
				sf.setClassify(rs.getString("classify"));
				sf.setClassifyId(rs.getInt("classify_id"));
				sf.setCreateDate(rs.getDate("create_date"));
				sf.setDescription(rs.getString("description"));
				sf.setType(rs.getInt("type"));
				sf.setProcessName(rs.getString("process_name"));
                sf.setCompanyId(rs.getInt("company_id"));
                sf.setAttribute(rs.getInt("attribute"));

				sf.setDeskno(rs.getInt("desk_no"));
				list.add(sf);
			}
			log.info("根据用户" + userId + "获取该用户桌面上的软件成功");
		} catch (SQLException e) {
			log.error("根据用户" + userId + "获取该用户桌面上的软件失败" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	/**
	 * @Title: getSoftware
	 * @Description: (根据软件编号获取某个软件的详细信息)
	 * @param softwareId
	 * @return
	 * @throws
	 */
	@Override
	public Software getSoftware(int softwareId) {
		log.info("根据软件编号=" + softwareId + "获取软件详细信息");
		Software sf = new Software();
		String sql = "select * from tb_software where software_id = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, softwareId);
			rs = ps.executeQuery();
			if (rs.next()) {
				sf.setSoftwareId(rs.getInt("software_id"));
				sf.setSoftwareName(rs.getString("software_name"));
				sf.setEnglishName(rs.getString("english_name"));
				sf.setSoftwareEntry(rs.getString("software_entry"));
				sf.setHost(rs.getString("host"));
				sf.setPictureName(rs.getString("picture_name"));
				sf.setBhri(rs.getInt("bhri"));
				sf.setCreateDate(rs.getDate("create_date"));
				sf.setIntro(rs.getString("intro"));
				sf.setDescription(rs.getString("description"));
				sf.setType(rs.getInt("type"));
				sf.setEmail(rs.getString("email"));
				sf.setProcessName(rs.getString("process_name"));
				sf.setDataNum(rs.getInt("data_num"));
				sf.setFlag(rs.getInt("flag"));
				sf.setRunData(rs.getInt("run_data"));
				sf.setParam(rs.getInt("param"));
				sf.setAppDoc(rs.getString("app_doc"));
				sf.setOffLine(rs.getInt("off_line"));
                sf.setCompanyId(rs.getInt("company_id"));
                sf.setAttribute(rs.getInt("attribute"));
			}
			// 查询分类信息
			String cSql = "select classify_name from tb_classify where classify_id in(select classify_id from tb_software_classify_relat where software_id=?)";
			ps = conn.prepareStatement(cSql);
			ps.setInt(1, softwareId);
			rs = ps.executeQuery();
			StringBuffer sb = new StringBuffer();
			while (rs.next()) {
				sb.append(rs.getString("classify_name") + ";");
			}
			String cNames = sb.toString();
			if (!cNames.equals("")) {
				sf.setClassify(cNames);
			}
			// 查询运行文件格式
			String fSql = "select format_desc from tb_data_format where format_id in(select format_id from tb_software_format_relat where software_id=?)";
			ps = conn.prepareStatement(fSql);
			ps.setInt(1, softwareId);
			rs = ps.executeQuery();
			StringBuffer buffer = new StringBuffer();
			while (rs.next()) {
				buffer.append(rs.getString("format_desc") + ";");
			}
			String fNames = buffer.toString();
			if (!fNames.equals("")) {
				sf.setFormatDesc(fNames);
			}
			// 查询截图信息
			String sSql = "select screen_name from tb_screen where software_id=?";
			ps = conn.prepareStatement(sSql);
			ps.setInt(1, softwareId);
			rs = ps.executeQuery();
			StringBuffer sbu = new StringBuffer();
			while (rs.next()) {
				sbu.append(rs.getString("screen_name") + ";");
			}
			String sNames = sbu.toString();
			if (!sNames.equals("")) {
				sf.setScreenShot(sNames);
			}
			// 查询数据类型信息
			String tSql = "select type_id from tb_software_type_relat where software_id=?";
			ps = conn.prepareStatement(tSql);
			ps.setInt(1, softwareId);
			rs = ps.executeQuery();
			if (rs.next()) {
				sf.setProDataType(String.valueOf(rs.getInt("type_id")));
			}
			log.info("根据软件编号=" + softwareId + "获取软件详细信息成功");
		} catch (SQLException e) {
			log.error("根据软件编号=" + softwareId + "获取软件详细信息失败" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return sf;
	}

	/**
	 * @Title: updateSoftwareOnDesk
	 * @Description: (更新软件在不同桌面上的展示，即软件在不同桌面间切换)
	 * @param userId
	 * @param softwareId
	 * @param deskNo
	 * @throws
	 */
	@Override
	public void updateSoftwareOnDesk(int userId, int softwareId, int deskNo) {
		String sql = "update tb_user_software set desk_no = ?,update_date = now() where user_id = ? and software_id = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deskNo);
			ps.setInt(2, userId);
			ps.setInt(3, softwareId);
			ps.executeUpdate();
			log.info("用户ID=" + userId + "把软件ID=" + softwareId + "的应用转移到"
					+ deskNo + "号桌面上");
		} catch (SQLException e) {
			log.error("用户ID=" + userId + "把软件ID=" + softwareId + "的应用转移到"
					+ deskNo + "号桌面上,失败" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
	}

	/**
	 * @Title: search
	 * @Description: (软件收索)
	 * @return
	 * @param List
	 *            <Software>
	 * @throws
	 */
	@Override
	public List<Software> search(int type, String softwareName, int userId) {
		List<Software> list = new ArrayList<Software>();
        String sql = "select * from tb_software as a where a.off_line=0 and a.software_id in(select b.software_id from tb_user_software as b where b.user_id=?) and type!=? and software_name like '%?%'";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, type);
            ps.setString(3, softwareName);
			rs = ps.executeQuery();
			while (rs.next()) {
				Software sf = new Software();
				sf.setSoftwareId(rs.getInt("software_id"));
				sf.setSoftwareName(rs.getString("software_name"));
				sf.setSoftwareEntry(rs.getString("software_entry"));
				sf.setHost(rs.getString("host"));
				sf.setPictureName(rs.getString("picture_name"));
				sf.setBhri(rs.getInt("bhri"));
				sf.setClassify(rs.getString("classify"));
				sf.setClassifyId(rs.getInt("classify_id"));
				sf.setCreateDate(rs.getDate("create_date"));
				sf.setDescription(rs.getString("description"));
				sf.setType(rs.getInt("type"));
				sf.setProcessName(rs.getString("process_name"));
                sf.setCompanyId(rs.getInt("company_id"));
                sf.setAttribute(rs.getInt("attribute"));

				sf.setIsAdd("yes");
				list.add(sf);
			}
            sql = "select * from tb_software as a where a.off_line=0 and a.software_id not in(select b.software_id from tb_user_software as b where b.user_id=?) and type!=? and software_name like '%?%'";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, type);
            ps.setString(3, softwareName);
			rs = ps.executeQuery();
			while (rs.next()) {
				Software sf = new Software();

				sf.setSoftwareId(rs.getInt("software_id"));
				sf.setSoftwareName(rs.getString("software_name"));
				sf.setSoftwareEntry(rs.getString("software_entry"));
				sf.setHost(rs.getString("host"));
				sf.setPictureName(rs.getString("picture_name"));
				sf.setBhri(rs.getInt("bhri"));
				sf.setClassify(rs.getString("classify"));
				sf.setClassifyId(rs.getInt("classify_id"));
				sf.setCreateDate(new java.util.Date(rs.getDate("create_date")
						.getTime()));
				sf.setDescription(rs.getString("description"));
				sf.setType(rs.getInt("type"));
				sf.setProcessName(rs.getString("process_name"));
                sf.setCompanyId(rs.getInt("company_id"));
                sf.setAttribute(rs.getInt("attribute"));

				sf.setIsAdd("no");
				list.add(sf);
			}
		} catch (SQLException e) {
			log.error("软件搜索失败" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	@Override
	public List<String> getAllIp(String processName) {
		List<String> list = new ArrayList<String>();
		String sql = "select distinct b.ip from tb_software a,tb_machine b where a.software_name = b.software_name and a.process_name=? group by ip";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, processName);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("ip"));
			}
		} catch (SQLException e) {
			log.error("获取对应程序的IP列表失败" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	@Override
	public int getSoftIdByName(String softName) {
		int softwareId = 0;
        String sql = "select software_id from tb_software where software_name=? and off_line=0";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, softName);
			rs = ps.executeQuery();
			if (rs.next()) {
				softwareId = rs.getInt("software_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return softwareId;
	}

	@Override
	public List<Integer> getSoftIdByName() {
		List<Integer> softIdList = new ArrayList<Integer>();
        String sql = "select software_id from tb_software where software_name='SNP' or software_name='mRNA' and off_line=0";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				softIdList.add(rs.getInt("software_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return softIdList;
	}

	@Override
	public int getRunningSoftwareNum(int userId) {
		int softwareNum = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT COUNT(*) count FROM (SELECT * FROM tb_report WHERE STATE>? AND USER_ID=? AND isdel=0) T";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, ReportState.UNRUN);
			ps.setInt(2, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				softwareNum = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return softwareNum;
	}

	@Override
	public int getRunningSoftwareNumByName(String softwareName, int userId) {
		int softwareNum = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
            String sql = "select count(software_id) count from (select r.software_id software_id from tb_report r where exists(select r.software_id from tb_software s where s.software_id=r.software_id and s.off_line=0 and s.software_name=?) and user_id=? and r.state>0) t";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, softwareName);
			ps.setInt(2, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				softwareNum = rs.getInt("count");
			}
			log.info("获取正在运行的应用数量成功");
		} catch (SQLException e) {
			log.error("获取正在运行的应用数量失败" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return softwareNum;
	}

	@Override
	public int getTotalSize() {
		log.info("计算所有记录数");
		int flag = 0;
		String sql = "select count(*) from tb_software";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(sql);
			while (rs.next()) {
				flag = rs.getInt(1);
			}
		} catch (SQLException e) {
			log.error("分页查询--计算所有软件数目：" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return flag;
	}

	@Override
	public List<Software> getSoftPageList(Page page) {
		log.info("分页查询所有软件");
		List<Software> list = new ArrayList<Software>();
		String sql = "select * from(select software_id,software_name,bhri,flag,create_date,type,off_line,company_id,attribute from tb_software soft) t order by off_line,create_date desc limit "
				+ (page.getCurrentPage() - 1)
				* page.getPageSize()
				+ ","
				+ page.getPageSize();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Software sf = new Software();
				int softId = rs.getInt("software_id");
				sf.setSoftwareId(softId);
				sf.setSoftwareName(rs.getString("software_name"));
				sf.setBhri(rs.getInt("bhri"));
				sf.setCreateDate(rs.getTimestamp("create_date"));
				sf.setType(rs.getInt("type"));
				sf.setFlag(rs.getInt("flag"));
				sf.setOffLine(rs.getInt("off_line"));
                sf.setCompanyId(rs.getInt("company_id"));
                sf.setAttribute(rs.getInt("attribute"));

				String cnameSql = "select classify_name from tb_classify where classify_id in(select classify_id from tb_software_classify_relat where software_id=?)";
				ps1 = conn.prepareStatement(cnameSql);
				ps1.setInt(1, softId);
				rs1 = ps1.executeQuery();
				StringBuffer sb = new StringBuffer();
				while (rs1.next()) {
					sb.append(rs1.getString("classify_name") + ";");
				}
				String classify = sb.toString();
				if (!classify.equals("")) {
					sf.setClassify(classify.substring(0, classify.length() - 1));
				}
				list.add(sf);
			}
		} catch (SQLException e) {
			log.error("分页查询所有软件" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
			ConnectManager.free(conn, ps1, rs1);
		}
		return list;
	}

	@Override
	public List<Software> getSoftwaresRunNum() {
		List<Software> runNumList = new ArrayList<Software>();
		String sql = "select s.software_id,software_name,count(case when state>0 then s.software_id end ) c from tb_software s left join tb_report r on r.software_id=s.software_id group by s.software_id order by c desc";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Software soft = new Software();
				soft.setRunNum(rs.getInt("c"));
				soft.setSoftwareId(rs.getInt("software_id"));
				soft.setSoftwareName(rs.getString("software_name"));
				runNumList.add(soft);
			}
			log.info("获取软件的运行次数成功");
		} catch (SQLException e) {
			log.info("获取软件的运行次数失败" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return runNumList;
	}

	@Override
	public List<Software> getUsersSoftwareRunNum(int userId) {
		List<Software> runNumList = new ArrayList<Software>();
		String sql = "select * from (select s.software_id,software_name,count(case when state>0 then s.software_id end ) c from tb_software s,tb_report r where r.software_id=s.software_id and user_id=? group by s.software_id order by c desc)t LIMIT 0,4";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Software soft = new Software();
				soft.setRunNum(rs.getInt("c"));
				soft.setSoftwareId(rs.getInt("software_id"));
				soft.setSoftwareName(rs.getString("software_name"));
				runNumList.add(soft);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return runNumList;
	}

	@Override
	public List<Software> getAllSoftwareNotInPro(Project project) {
		List<Software> softwareList = new ArrayList<Software>();
        String sql = "select software_id,software_name,picture_name,off_line,company_id,attribute from tb_software s where not EXISTS(select software_id from tb_report r where r.software_id=s.software_id and project_id="
				+ project.getProjectId() + ") and type!=" + SoftWareState.JS;
		if (project.getDataFormat() != 0) {
			sql += " and software_id in (select software_id from tb_software_format_relat where format_id=(select data_format from tb_project where project_id="
					+ project.getProjectId() + "))";
		}
		if (project.getProDataType() != 0) {// 如果项目添加了数据类型，还要进一步按照数据类型进行筛选
			sql += " and software_id in (select software_id from tb_software_type_relat where type_id=(select pro_data_type from tb_project where project_id="
					+ project.getProjectId() + "))";
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Software software = new Software();
				software.setSoftwareId(rs.getInt("software_id"));
				software.setSoftwareName(rs.getString("software_name"));
				software.setPictureName(rs.getString("picture_name"));
				software.setOffLine(rs.getInt("off_line"));
				softwareList.add(software);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return softwareList;
	}

	@Override
	public List<Software> getAllSoftwareNotInData(int dataId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Software> softwareList = new ArrayList<Software>();
        String sql = "select software_id,software_name,picture_name,run_data,off_line,company_id,attribute "
				+ "from tb_software s where not EXISTS(select software_id "
				+ "from tb_report r where r.software_id=s.software_id and file_id=?) "
				+ "and software_id in (select software_id from tb_software_format_relat where format_id=(select file_format from tb_file where file_id=?));";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dataId);
			ps.setInt(2, dataId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Software software = new Software();
				software.setSoftwareId(rs.getInt("software_id"));
				software.setSoftwareName(rs.getString("software_name"));
				software.setPictureName(rs.getString("picture_name"));
				software.setRunData(rs.getInt("run_data"));
				software.setOffLine(rs.getInt("off_line"));
				softwareList.add(software);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return softwareList;
	}

	@Override
	public int insertSoftware(Software software) {
		log.info("新增软件信息");
		int insertNum = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			// 1、添加基本信息
			String baseSql = "insert into tb_software(software_name,english_name,software_entry,process_name,"
                    + "host,picture_name,create_date,intro,description,email,type,run_data,data_num,param,app_doc,off_line,company_id,attribute)"
                    + " values(?,?,?,?,?,?,now(),?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(baseSql);
			ps.setString(1, software.getSoftwareName());
			ps.setString(2, software.getEnglishName());
			ps.setString(3, software.getSoftwareEntry());
			ps.setString(4, software.getProcessName());
			ps.setString(5, software.getHost());
			ps.setString(6, software.getPictureName());

			ps.setString(7, software.getIntro());
			ps.setString(8, software.getDescription());
			ps.setString(9, software.getEmail());
            ps.setInt(10, software.getType());
            ps.setInt(11, software.getRunData());
            ps.setInt(12, software.getDataNum());
            ps.setInt(13, software.getParam());
            ps.setString(14, software.getAppDoc());
            ps.setInt(15, 0);
            ps.setInt(16, software.getCompanyId());
            ps.setInt(17, software.getAttribute());
			insertNum = ps.executeUpdate();

			if (insertNum > 0) {
				int softwareId = 0;
				String qsql = "select software_id from tb_software where software_name=?";
				ps = conn.prepareStatement(qsql);
				ps.setString(1, software.getSoftwareName());
				rs = ps.executeQuery();
				if (rs.next()) {
					softwareId = rs.getInt("software_id");
				}
				if (softwareId != 0) {
					// 2、添加软件分类信息
					String classify = software.getClassify();
					String[] classes = classify.substring(0,
							classify.length() - 1).split(";");
					for (String c : classes) {
						if (!c.equals("")) {
							String selSql = "select * from tb_software_classify_relat where classify_id=? and software_id=?";
							ps = conn.prepareStatement(selSql);
							ps.setInt(1, Integer.parseInt(c));
							ps.setInt(2, softwareId);
							rs = ps.executeQuery();
							if (!rs.next()) {
								String classSql = "insert into tb_software_classify_relat(classify_id,software_id) values("
										+ Integer.parseInt(c)
										+ ","
										+ softwareId + ")";
								ps = conn.prepareStatement(classSql);
								ps.executeUpdate();
							}
						}
					}
					// 3、添加软件运行数据格式
					String format = software.getFormatDesc();
					String[] fs = format.substring(0, format.length() - 1)
							.split(";");
					for (String f : fs) {
						if (!f.equals("")) {
							String selSql = "select * from tb_software_format_relat where format_id=? and software_id=?";
							ps = conn.prepareStatement(selSql);
							ps.setInt(1, Integer.parseInt(f));
							ps.setInt(2, softwareId);
							rs = ps.executeQuery();
							if (!rs.next()) {
								String fSql = "insert into tb_software_format_relat(format_id,software_id) values("
										+ Integer.parseInt(f)
										+ ","
										+ softwareId + ")";
								ps = conn.prepareStatement(fSql);
								ps.executeUpdate();
							}
						}
					}
					// 4、添加软件截图
					String sshot = software.getScreenShot();
					String[] screens = sshot.substring(0, sshot.length() - 1)
							.split(";");
					StringBuffer sb = new StringBuffer(
							"insert into tb_screen(software_id,screen_name) values");
					for (String sc : screens) {
						sb.append("(" + softwareId + ",'" + sc + "'),");
					}
					String screenSql = sb.toString();
					ps = conn.prepareStatement(screenSql.substring(0,
							screenSql.length() - 1));
					ps.executeUpdate();
					// 5、添加软件数据类型信息
					String dataType = software.getProDataType();
					if (dataType != null && !dataType.equals("")) {
						String typeSql = "insert into tb_software_type_relat(software_id,type_id) values(?,?)";
						ps = conn.prepareStatement(typeSql);
						ps.setInt(1, softwareId);
						ps.setInt(2,
								Integer.parseInt(software.getProDataType()));
						ps.executeUpdate();
					}
				}
			}
			log.info("新增软件信息成功");
		} catch (SQLException e) {
			insertNum = 0;
			log.error("新增软件信息失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return insertNum;
	}

	@Override
	public int updateSoftware(Software software) {
		log.info("修改软件信息");
		int updateNum = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			String sql = "update tb_software set software_name=?,english_name=?,software_entry=?,process_name=?,"
                    + "host=?,picture_name=?,intro=?,description=?,email=?,type=?,run_data=?,data_num=?,param=?,app_doc=?,company_id=?,attribute=? where software_id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, software.getSoftwareName());
			ps.setString(2, software.getEnglishName());
			ps.setString(3, software.getSoftwareEntry());
			ps.setString(4, software.getProcessName());
			ps.setString(5, software.getHost());
			ps.setString(6, software.getPictureName());
			ps.setString(7, software.getIntro());
			ps.setString(8, software.getDescription());
			ps.setString(9, software.getEmail());
            ps.setInt(10, software.getType());
            ps.setInt(11, software.getRunData());
            ps.setInt(12, software.getDataNum());
            ps.setInt(13, software.getParam());
            ps.setString(14, software.getAppDoc());
			ps.setInt(15, software.getCompanyId());
			ps.setInt(16, software.getAttribute());
			ps.setInt(17, software.getSoftwareId());
			updateNum = ps.executeUpdate();
			if (updateNum > 0) {
				// 2、添加软件分类信息
				String delcsql = "delete from tb_software_classify_relat where software_id=?";
				ps = conn.prepareStatement(delcsql);
				ps.setInt(1, software.getSoftwareId());
				updateNum += ps.executeUpdate();
				String classify = software.getClassify();
				String[] classes = classify.substring(0, classify.length() - 1)
						.split(";");
				for (String c : classes) {
					if (!c.equals("")) {
						String classSql = "insert into tb_software_classify_relat(classify_id,software_id) values("
								+ Integer.parseInt(c)
								+ ","
								+ software.getSoftwareId() + ")";
						ps = conn.prepareStatement(classSql);
						updateNum += ps.executeUpdate();
					}
				}
				// 3、添加软件运行数据格式
				String delfsql = "delete from tb_software_format_relat where software_id=?";
				ps = conn.prepareStatement(delfsql);
				ps.setInt(1, software.getSoftwareId());
				updateNum += ps.executeUpdate();
				String format = software.getFormatDesc();
				String[] fs = format.substring(0, format.length() - 1).split(
						";");
				for (String f : fs) {
					if (!f.equals("")) {
						String fSql = "insert into tb_software_format_relat(format_id,software_id) values("
								+ Integer.parseInt(f)
								+ ","
								+ software.getSoftwareId() + ")";
						ps = conn.prepareStatement(fSql);
						updateNum += ps.executeUpdate();
					}
				}
				// 4、添加软件截图
				String delssql = "delete from tb_screen where software_id=?";
				ps = conn.prepareStatement(delssql);
				ps.setInt(1, software.getSoftwareId());
				updateNum += ps.executeUpdate();
				String sshot = software.getScreenShot();
				String[] screens = sshot.substring(0, sshot.length() - 1)
						.split(";");
				StringBuffer sb = new StringBuffer(
						"insert into tb_screen(software_id,screen_name) values");
				for (String sc : screens) {
					sb.append("(" + software.getSoftwareId() + ",'" + sc
							+ "'),");
				}
				String screenSql = sb.toString();
				ps = conn.prepareStatement(screenSql.substring(0,
						screenSql.length() - 1));
				updateNum += ps.executeUpdate();
				// 5、添加软件数据类型信息
				String deltsql = "delete from tb_software_type_relat where software_id=?";
				ps = conn.prepareStatement(deltsql);
				ps.setInt(1, software.getSoftwareId());
				updateNum += ps.executeUpdate();
				String dataType = software.getProDataType();
				if (dataType != null && !dataType.equals("")) {
					String typeSql = "insert into tb_software_type_relat(software_id,type_id) values(?,?)";
					ps = conn.prepareStatement(typeSql);
					ps.setInt(1, software.getSoftwareId());
					ps.setInt(2, Integer.parseInt(software.getProDataType()));
					updateNum += ps.executeUpdate();
				}
			}
			log.info("修改软件信息成功," + updateNum);
		} catch (SQLException e) {
			log.error("修改软件信息失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return updateNum;
	}

	@Override
	public int deleteSoftware(int softwareId) {
		log.info("删除软件信息");
		int delNum = 0;
		// String sql = "delete from tb_software where software_id=?";
        String sql = "update tb_software set off_line=1 where software_id=?";
		String userSoftwareSql = "delete from tb_user_software where software_id=?";
		String softwareClassifySql = "delete from tb_software_classify_relat where software_id=?";
		String softwareCommSql = "delete from tb_software_comment where software_id=?";
		String softwareFormatSql = "delete from tb_software_format_relat where software_id=?";
		String softwareStrainSql = "delete from tb_software_strain_relat where software_id=?";
		String softwareTypeSql = "delete from tb_software_type_relat where software_id=?";
		// String reportSql = "delete from tb_report where software_id=?";
		String reportSql = "update tb_report set isdel=1 where software_id=?";
		String classifySql = "delete from tb_software_classify_relat where software_id=?";
		// String screenSql = "delete from tb_screen where software_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			// 首先查询该app若正在运行某个数据或项目，则不允许删除
			String rSlq = "select * from tb_report where software_id=? and state>? and state<?";
			ps = conn.prepareStatement(rSlq);
			ps.setInt(1, softwareId);
			ps.setInt(2, ReportState.UNRUN);
			ps.setInt(3, ReportState.COMPLETE);
			rs = ps.executeQuery();
			if (rs.next()) {
				delNum = -1;
				return delNum;
			}
			ps = conn.prepareStatement(sql);
			ps.setInt(1, softwareId);
			delNum = ps.executeUpdate();
			ps = conn.prepareStatement(userSoftwareSql);
			ps.setInt(1, softwareId);
			delNum += ps.executeUpdate();
			ps = conn.prepareStatement(softwareClassifySql);
			ps.setInt(1, softwareId);
			delNum += ps.executeUpdate();
			ps = conn.prepareStatement(softwareCommSql);
			ps.setInt(1, softwareId);
			delNum += ps.executeUpdate();
			ps = conn.prepareStatement(softwareFormatSql);
			ps.setInt(1, softwareId);
			delNum += ps.executeUpdate();
			ps = conn.prepareStatement(softwareStrainSql);
			ps.setInt(1, softwareId);
			delNum += ps.executeUpdate();
			ps = conn.prepareStatement(softwareTypeSql);
			ps.setInt(1, softwareId);
			delNum += ps.executeUpdate();
			ps = conn.prepareStatement(reportSql);
			ps.setInt(1, softwareId);
			delNum += ps.executeUpdate();
			ps = conn.prepareStatement(classifySql);
			ps.setInt(1, softwareId);
			delNum += ps.executeUpdate();
			// ps = conn.prepareStatement(screenSql);
			// ps.setInt(1, softwareId);
			// delNum += ps.executeUpdate();
			log.info("删除软件信息成功");
		} catch (SQLException e) {
			log.error("删除软件信息失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return delNum;
	}

	@Override
	public List<Software> getAllSofts() {
		List<Software> list = new ArrayList<Software>();
		String sql = "select * from tb_software where off_line=0";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Software sf = new Software();
				sf.setSoftwareId(rs.getInt("software_id"));
				sf.setSoftwareName(rs.getString("software_name"));
				sf.setSoftwareEntry(rs.getString("software_entry"));
				sf.setHost(rs.getString("host"));
				sf.setPictureName(rs.getString("picture_name"));
				sf.setBhri(rs.getInt("bhri"));
				sf.setCreateDate(rs.getDate("create_date"));
				sf.setDescription(rs.getString("description"));
				sf.setType(rs.getInt("type"));
				sf.setAppDoc(rs.getString("app_doc"));
				sf.setProcessName(rs.getString("process_name"));
                sf.setCompanyId(rs.getInt("company_id"));
                sf.setAttribute(rs.getInt("attribute"));
				list.add(sf);
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	/***********************************************************************************************
	 * 以下为最新software方法列表
	 */

	/*************
	 * 软件
	 */

	@Override
	public List<Software> getAll(int userId, String softName) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Software> list = new ArrayList<Software>();
		String sql = null;
		if ((null == softName) || (softName.equals(""))) {
			sql = "select * from(select CONCAT('yes') as is_add,software_id,software_name,picture_name,bhri,description from tb_software where type!="
					+ SoftWareState.DATABASE
					+ " and software_id in(select software_id from tb_user_software where user_id="
					+ userId
					+ ") union select CONCAT('no') as is_add,software_id,software_name,picture_name,bhri,description from tb_software where type!="
					+ SoftWareState.DATABASE
					+ " and software_id not in(select software_id from tb_user_software where user_id="
					+ userId + ")) as tb";
		} else {
			sql = "select CONCAT('yes') as is_add,software_id,software_name,picture_name,bhri,description from tb_software where type!="
					+ SoftWareState.DATABASE
					+ " and (software_name like '%"
					+ softName
					+ "%' or english_name like '%"
					+ softName
					+ "%' or intro like '%"
					+ softName
					+ "%' or description like '%"
					+ softName
					+ "%') and software_id in(select software_id from tb_user_software where user_id="
					+ userId
					+ ") union select CONCAT('no') as is_add,software_id,software_name,picture_name,bhri,description from tb_software where type!="
					+ SoftWareState.DATABASE
					+ " and (software_name like '%"
					+ softName
					+ "%' or english_name like '%"
					+ softName
					+ "%' or intro like '%"
					+ softName
					+ "%' or description like '%"
					+ softName
					+ "%') and software_id not in(select software_id from tb_user_software where user_id="
					+ userId + ")";
		}
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Software sf = new Software();
				sf.setSoftwareId(rs.getInt("software_id"));
				sf.setSoftwareName(rs.getString("software_name"));
				sf.setPictureName(rs.getString("picture_name"));
				sf.setBhri(rs.getInt("bhri"));
				sf.setDescription(rs.getString("description"));
				sf.setIsAdd(rs.getString("is_add"));
				// sf.setCompanyId(rs.getInt("company_id"));
				// sf.setAttribute(rs.getInt("attribute"));
				list.add(sf);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	@Override
	public PageList<Software> getAll(int userId, String softName, Page page) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Software> list = new ArrayList<Software>();
		// 由于前台增加了是否为空的判断，所以此处的softName不为空
		log.info("用户" + super.userName + "在应用市场搜索App:" + softName);
		StringBuffer sql = new StringBuffer(
				"select a.*,case when a.software_id in(select b.software_id from tb_user_software as b where b.user_id=?) then 'yes' else 'no' end as c_boolean from (select * from tb_software where off_line=? and ((attribute = ? and company_id = (select company_id from tb_user where user_id = ?)) or attribute= ?)) as a where a.software_name like '%"
						+ softName
						+ "%' or a.english_name like '%"
						+ softName
						+ "%' or a.intro like '%"
						+ softName
						+ "%' or a.description like '%" + softName + "%'");
		String countSql = sql
				.toString()
				.replace(
						"select a.*,case when a.software_id in(select b.software_id from tb_user_software as b where b.user_id=?) then 'yes' else 'no' end as c_boolean",
						"select count(*) as num");
		sql.append(" order by a.create_date desc limit ")
				.append((page.getCurrentPage() - 1) * page.getPageSize())
				.append(",").append(page.getPageSize());
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, userId);
			ps.setInt(2, SoftWareOffLineState.ON);
			ps.setInt(3, Attribute.PRIVATE);
			ps.setInt(4, userId);
			ps.setInt(5, Attribute.PUBLIC);
			rs = ps.executeQuery();
			while (rs.next()) {
				Software sf = new Software();
				sf.setSoftwareId(rs.getInt("software_id"));
				sf.setSoftwareName(rs.getString("software_name"));
				sf.setPictureName(rs.getString("picture_name"));
				sf.setBhri(rs.getInt("bhri"));
				sf.setHost(rs.getString("host"));
				sf.setDescription(rs.getString("description"));
				sf.setIsAdd(rs.getString("c_boolean"));
				list.add(sf);
			}
			ps = conn.prepareStatement(countSql);
			ps.setInt(1, SoftWareOffLineState.ON);
			ps.setInt(2, Attribute.PRIVATE);
			ps.setInt(3, userId);
			ps.setInt(4, Attribute.PUBLIC);
			rs = ps.executeQuery();
			while (rs.next()) {
				page.make(rs.getInt("num"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		PageList<Software> pageList = new PageList<Software>();
		pageList.setDatas(list);
		pageList.setPage(page);
		return pageList;
	}

	@Override
	public boolean updateBhri(int softwareId, int userId, int deskNo) {
		log.info("用户" + super.userName + "添加App" + softwareId + "到桌面" + deskNo);
		boolean flag = true;
		Connection conn = null;
		Statement st = null;
		String sql = "update tb_software set bhri=bhri+1 where software_id="
				+ softwareId;
		String delSql = "delete from tb_user_software where user_id=" + userId
				+ " and software_id=" + softwareId;
		String sql1 = "insert into tb_user_software(user_id,software_id,desk_no,create_date) values("
				+ userId + "," + softwareId + "," + deskNo + ",now())";
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			st.addBatch(delSql);
			st.addBatch(sql);
			st.addBatch(sql1);
			st.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				flag = false;
			} catch (SQLException e1) {
				log.error("用户" + super.userName + "添加App" + softwareId + "到桌面"
						+ deskNo + "失败");
				e.printStackTrace();
				e1.printStackTrace();
			}
		} finally {
			ConnectManager.free(conn, st, null);
		}
		return flag;
	}

	/****************
	 * 数据库
	 */

	/*****************************************************
	 * 软件、数据类型关系匹配
	 */
	@Override
	public boolean saveSoftwareType(int softwareId, String[] typeIds) {
		boolean flag = true;
		Connection conn = null;
		Statement st = null;
		String sql = null;
		try {
			conn = ConnectManager.getConnection();
			st = conn.createStatement();
			conn.setAutoCommit(false);
			for (String typeId : typeIds) {
				sql = "insert into tb_software_type_relat(software_id,type_id) values("
						+ softwareId + "," + typeId + ")";
				st.addBatch(sql);
			}
			st.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			flag = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, st, null);
		}
		return flag;
	}

	@Override
	public boolean recommendSoftware(int softwareId) {
		boolean flag = true;
		String sql = "update tb_software set flag=? where software_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, SoftRecommend.YES);
			ps.setInt(2, softwareId);
			ps.executeUpdate();
		} catch (SQLException e) {
			flag = false;
			log.error("软件推荐出错" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	@Override
	public boolean recommendNoSoftware(int softwareId) {
		boolean flag = true;
		String sql = "update tb_software set flag=? where software_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, SoftRecommend.NO);
			ps.setInt(2, softwareId);
			ps.executeUpdate();
		} catch (SQLException e) {
			flag = false;
			log.error("软件取消推荐出错" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	@Override
	public String getEmailBySoftId(int softwareId) {
		String email = null;
		String sql = "select email from tb_software where software_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, softwareId);
			rs = ps.executeQuery();
			if (rs.next()) {
				email = rs.getString("email");
			}
		} catch (SQLException e) {
			log.error("获取软件" + softwareId + "管理员邮箱信息出错" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return email;
	}

	@Override
	public List<Integer> getTypeListBySoftwareId(int softwareId) {
		List<Integer> formatList = new ArrayList<Integer>();
		String sql = "select format_id from tb_software_format_relat where software_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, softwareId);
			rs = ps.executeQuery();
			while (rs.next()) {
				formatList.add(rs.getInt("format_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return formatList;
	}

	@Override
	public int getAppRunDataNum(int softwareId) {
		int dataNum = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			String sql = "select data_num from tb_software where software_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, softwareId);
			rs = ps.executeQuery();
			if (rs.next()) {
				dataNum = rs.getInt("data_num");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return dataNum;
	}

	@Override
	public List<ParamStrain> getStrainListBySoftwareId(String softwareId) {
		List<ParamStrain> strainList = new ArrayList<ParamStrain>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			String sql = null;
			if (null == softwareId || "".equals(softwareId)
					|| "0".equals(softwareId)) {
				sql = "select * from tb_param_strain";
			} else {
				sql = "select * from tb_param_strain where id in(select strain_id from tb_software_strain_relat where software_id="
						+ Integer.parseInt(softwareId) + ")";
			}
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ParamStrain strain = null;
			while (rs.next()) {
				strain = new ParamStrain();
				strain.setId(rs.getInt("id"));
				strain.setStrainText(rs.getString("strain_text"));
				strain.setStrainVal(rs.getString("strain_val"));
				strainList.add(strain);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return strainList;
	}

	@Override
	public int offLineSoftware(int softwareId, int flag) {
		int dataNum = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			String sql = "update tb_software set off_line=? where software_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, flag);
			ps.setInt(2, softwareId);
			dataNum = ps.executeUpdate();
			if (flag == SoftWareOffLineState.OFF) {
				// 下线需要把用户桌面上添加的app也删除掉
				String delSql = "delete from tb_user_software where software_id=?";
				ps = conn.prepareStatement(delSql);
				ps.setInt(1, softwareId);
				dataNum += ps.executeUpdate();
			}
		} catch (SQLException e) {
			dataNum = 0;
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return dataNum;
	}

	@Override
	public String getFormatsByAppId(int softwareId) {
		String formats = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			String sql = "select format_id from tb_software_format_relat where software_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, softwareId);
			rs = ps.executeQuery();
			StringBuffer sb = new StringBuffer();
			while (rs.next()) {
				sb.append(rs.getInt("format_id") + ",");
			}
			formats = sb.toString();
			if (!formats.equals("")) {
				formats = formats.substring(0, formats.length() - 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return formats;
	}

	@Override
	public int getSoftIdByNameOffLine(int softwareId, String softName,
			String editType) {
		int rsoftwareId = 0;
		String sql = "select software_id from tb_software where software_name='"
				+ softName
				+ "' and off_line="
				+ SoftWareOffLineState.ON
				+ " and software_id!=" + softwareId;
		if (editType.equals("add")) {
			sql = "select software_id from tb_software where software_name='"
					+ softName + "' and off_line=" + SoftWareOffLineState.ON;
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				rsoftwareId = rs.getInt("software_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return rsoftwareId;
	}

	@Override
	public List<Software> getRunDataApp() {
		log.info("获取运行数据的App列表(非JS)");
		List<Software> list = new ArrayList<Software>();
		String sql1 = "SELECT * FROM celloud.tb_software where type = 3 order by software_name";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql1);
			rs = ps.executeQuery();
			while (rs.next()) {
				Software sf = new Software();
				sf.setSoftwareId(rs.getInt("software_id"));
				sf.setSoftwareName(rs.getString("software_name"));
				sf.setSoftwareEntry(rs.getString("software_entry"));
				sf.setHost(rs.getString("host"));
				sf.setPictureName(rs.getString("picture_name"));
				sf.setBhri(rs.getInt("bhri"));
                sf.setCompanyId(rs.getInt("company_id"));
                sf.setAttribute(rs.getInt("attribute"));
				list.add(sf);
			}
		} catch (SQLException e) {
			log.error("获取运行数据的App列表(非JS)" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	@Override
	public List<Software> getRunDataAppById(int userid) {
		log.info("获取用户运行过数据的App列表");
		List<Software> list = new ArrayList<Software>();
		String sql1 = "select s.*,count(*) as num from (select project_id from tb_project where user_id = ? and state = 0 union all select p.project_id from tb_project p,tb_file_share s where p.state=0 and p.project_id=s.project_id and s.user_id = ? and s.state = 1) as p,tb_report as r,tb_software s where r.project_id = p.project_id and r.software_id = s.software_id group by software_id order by num desc , software_name;";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql1);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Software sf = new Software();
				sf.setSoftwareId(rs.getInt("software_id"));
				sf.setSoftwareName(rs.getString("software_name"));
				sf.setSoftwareEntry(rs.getString("software_entry"));
				sf.setHost(rs.getString("host"));
				sf.setPictureName(rs.getString("picture_name"));
				sf.setBhri(rs.getInt("bhri"));
                sf.setCompanyId(rs.getInt("company_id"));
                sf.setAttribute(rs.getInt("attribute"));
				list.add(sf);
			}
		} catch (SQLException e) {
			log.error("获取运行数据的App列表(非JS)" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	@Override
	public PageList<Software> getSoftwarePageList(Integer userId,
			Integer classifyId, Page page) {
		List<Software> list = new ArrayList<Software>();
		StringBuffer sql = new StringBuffer(
				"select a.*,case when a.software_id in(select b.software_id from tb_user_software as b where b.user_id=?) then 'yes' else 'no' end as c_boolean from (select * from tb_software where off_line=? and  ((attribute = ? and company_id = (select company_id from tb_user where user_id = ?)) or attribute= ?) ) as a ");
		if (classifyId != 0) {
			sql.append(
					" where a.software_id in(select software_id from tb_software_classify_relat where classify_id=")
					.append(classifyId).append(")");
		}
		String countSql = sql
				.toString()
				.replace(
						"select a.*,case when a.software_id in(select b.software_id from tb_user_software as b where b.user_id=?) then 'yes' else 'no' end as c_boolean",
						"select count(*) as num");
		sql.append(" order by a.create_date desc limit ")
				.append((page.getCurrentPage() - 1) * page.getPageSize())
				.append(",").append(page.getPageSize());
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, userId);
			ps.setInt(2, SoftWareOffLineState.ON);
			ps.setInt(3, Attribute.PRIVATE);
			ps.setInt(4, userId);
			ps.setInt(5, Attribute.PUBLIC);
			rs = ps.executeQuery();
			while (rs.next()) {
				Software sf = new Software();
				sf.setSoftwareId(rs.getInt("software_id"));
				sf.setSoftwareName(rs.getString("software_name"));
				sf.setSoftwareEntry(rs.getString("software_entry"));
				sf.setHost(rs.getString("host"));
				sf.setPictureName(rs.getString("picture_name"));
				sf.setBhri(rs.getInt("bhri"));
				sf.setCreateDate(rs.getDate("create_date"));
				sf.setDescription(rs.getString("description"));
				sf.setType(rs.getInt("type"));
				sf.setProcessName(rs.getString("process_name"));
				sf.setCompanyId(rs.getInt("company_id"));
				sf.setAttribute(rs.getInt("attribute"));
				sf.setIsAdd(rs.getString("c_boolean"));
				list.add(sf);
			}
			ps = conn.prepareStatement(countSql);
			ps.setInt(1, SoftWareOffLineState.ON);
			ps.setInt(2, Attribute.PRIVATE);
			ps.setInt(3, userId);
			ps.setInt(4, Attribute.PUBLIC);
			rs = ps.executeQuery();
			while (rs.next()) {
				page.make(rs.getInt("num"));
			}
		} catch (SQLException e) {
			log.error("获取每个类别软件的列表" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		PageList<Software> pageList = new PageList<Software>();
		pageList.setDatas(list);
		pageList.setPage(page);
		return pageList;
	}
}