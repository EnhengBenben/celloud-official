package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.celloud.dao.SoftwareDao;
import com.celloud.sdo.Software;
import com.nova.constants.Attribute;
import com.nova.constants.SoftWareOffLineState;
import com.nova.utils.ConnectManager;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-15下午2:09:19
 * @version Revision: 1.0
 */
public class SoftwareDaoImpl extends BaseDao implements SoftwareDao {
    Logger log = Logger.getLogger(DataDaoImpl.class);
    private Connection conn = ConnectManager.getConnection();
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    @Override
    public List<Software> getAppsByFormat(Integer formatId) {
	List<Software> list = new ArrayList<>();
	String sql = "select s.software_id,s.software_name,s.data_num from tb_software s left join tb_software_format_relat sf on s.software_id = sf.software_id where sf.format_id = ? and s.off_line = ? and ((attribute = ? and company_id = ?) or attribute= ?) order by s.create_date;";
	try {
	    ps = conn.prepareStatement(sql);
	    ps.setInt(1, formatId);
	    ps.setInt(2, SoftWareOffLineState.ON);
	    ps.setInt(3, Attribute.PRIVATE);
	    ps.setInt(4, super.companyId);
	    ps.setInt(5, Attribute.PUBLIC);
	    rs = ps.executeQuery();
	    Software soft = null;
	    while (rs.next()) {
		soft = new Software();
		soft.setSoftwareId(rs.getInt("software_id"));
		soft.setSoftwareName(rs.getString("software_name"));
		soft.setDataNum(rs.getInt("data_num"));
		list.add(soft);
	    }
	} catch (SQLException e) {
	    log.error("用户" + super.userName + "查询数据可运行的APP列表失败");
	    e.printStackTrace();
	} finally {
	    ConnectManager.free(conn, ps, rs);
	}
	return list;
    }

}
