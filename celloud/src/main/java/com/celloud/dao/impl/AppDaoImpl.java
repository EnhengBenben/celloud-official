package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.celloud.constants.AppOffline;
import com.celloud.constants.AppPermission;
import com.celloud.dao.AppDao;
import com.celloud.sdo.App;
import com.celloud.sdo.Classify;
import com.nova.utils.ConnectManager;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-15下午2:09:19
 * @version Revision: 1.0
 */
public class AppDaoImpl extends BaseDao implements AppDao {
    Logger log = Logger.getLogger(AppDaoImpl.class);
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    @Override
    public List<App> getAppsByFormat(Integer formatId) {
        List<App> list = new ArrayList<>();
        String sql = "select s.software_id,s.software_name,s.data_num from tb_software s left join tb_software_format_relat sf on s.software_id = sf.software_id where sf.format_id = ? and s.off_line = ? and ((attribute = ? and company_id = ?) or attribute= ?) order by s.create_date;";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, formatId);
            ps.setInt(2, AppOffline.ON);
            ps.setInt(3, AppPermission.PRIVATE);
            ps.setInt(4, super.companyId);
            ps.setInt(5, AppPermission.PUBLIC);
            rs = ps.executeQuery();
            App soft = null;
            while (rs.next()) {
                soft = new App();
                soft.setSoftwareId(rs.getLong("software_id"));
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

    @Override
    public String getAppNameById(Long softwareId) {
        String sql = "select software_name from tb_software where software_id = ?";
        String appName = null;
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, softwareId);
            rs = ps.executeQuery();
            while (rs.next()) {
                appName = rs.getString("software_name");
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "查询软件" + softwareId + "软件名称失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return appName;
    }

    @Override
    public List<App> getAllSoftware() {
        List<App> list = new ArrayList<>();
        String sql = "select * from tb_software where off_line = ?;";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, AppOffline.ON);
            rs = ps.executeQuery();
            App soft = null;
            while (rs.next()) {
                soft = new App();
                soft.setSoftwareId(rs.getLong("software_id"));
                soft.setSoftwareName(rs.getString("software_name"));
                soft.setCommand(rs.getString("command"));
                list.add(soft);
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "全查APP列表失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return list;
    }

    @Override
    public Map<String, List<Classify>> getDoubleClassify(Integer classifyId) {
        Map<String, List<Classify>> map = new HashMap<>();
        List<Classify> plist = new ArrayList<>();
        List<Classify> slist = new ArrayList<>();
        String sql = "select classify_id,classify_pid,classify_name from tb_classify where classify_pid=0 or classify_pid=?;";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, classifyId);
            rs = ps.executeQuery();
            Classify clas = null;
            while (rs.next()) {
                clas= new Classify();
                clas.setClassifyId(rs.getInt("classify_id"));
                clas.setClassifyName(rs.getString("classify_name"));
                int pid = rs.getInt("classify_pid");
                clas.setClassifyPid(pid);
                if (pid == 0) {
                    plist.add(clas);
                } else {
                    slist.add(clas);
                }
            }
            map.put("pclassify", plist);
            map.put("sclassify", slist);
        } catch (SQLException e) {
            log.error("用户" + super.userName + "全查APP列表失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return map;
    }

    @Override
    public List<App> getAppByClassify(Integer classifyId, Integer companyId) {
        List<App> list = new ArrayList<>();
        String sql = "select s.software_id,s.software_name,s.picture_name,s.description,s.create_date,c.classify_id,c.classify_name from tb_software s left join tb_software_classify_relat sc on s.software_id=sc.software_id left join tb_classify c on c.classify_id=sc.classify_id where c.classify_pid=? and s.off_line=? and ((s.company_id=? and s.attribute=?) or s.attribute=?) order by s.create_date desc;";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, classifyId);
            ps.setInt(2, AppOffline.ON);
            ps.setInt(3, companyId);
            ps.setInt(4, AppPermission.PRIVATE);
            ps.setInt(5, AppPermission.PUBLIC);
            rs = ps.executeQuery();
            App soft = null;
            Classify clas = null;
            while (rs.next()) {
                soft = new App();
                soft.setSoftwareId(rs.getLong("software_id"));
                soft.setSoftwareName(rs.getString("software_name"));
                soft.setPictureName(rs.getString("picture_name"));
                soft.setDescription(rs.getString("description"));
                soft.setCreateDate(rs.getDate("create_date"));
                clas = new Classify();
                clas.setClassifyId(rs.getInt("classify_id"));
                clas.setClassifyName(rs.getString("classify_name"));
                soft.setClassify(clas);
                list.add(soft);
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "全查APP列表失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return list;
    }

}