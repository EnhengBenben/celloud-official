package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.celloud.constants.AppOffline;
import com.celloud.constants.AppPermission;
import com.celloud.dao.AppDao;
import com.celloud.sdo.App;
import com.celloud.sdo.Classify;
import com.celloud.sdo.Screen;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.utils.ConnectManager;

/**
 * APP操作数据库实现
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
    public List<App> getAppsByFormat(Integer formatId, Integer userId) {
        List<App> list = new ArrayList<>();
        String sql = "select s.software_id,s.software_name,s.data_num from tb_software s left join tb_software_format_relat sf on s.software_id = sf.software_id left join tb_user_software us on s.software_id=us.software_id where sf.format_id = ? and s.off_line = ? and us.user_id=? order by s.create_date;";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, formatId);
            ps.setInt(2, AppOffline.ON);
            ps.setInt(3, userId);
            rs = ps.executeQuery();
            App app = null;
            while (rs.next()) {
                app = new App();
                app.setSoftwareId(rs.getLong("software_id"));
                app.setSoftwareName(rs.getString("software_name"));
                app.setDataNum(rs.getInt("data_num"));
                list.add(app);
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
            if (rs.next()) {
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
    public Classify getClassifyById(Integer id) {
        Classify c = null;
        String sql = "select classify_id,classify_pid,classify_name from tb_classify where classify_id=?;";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                c = new Classify();
                c.setClassifyId(rs.getInt("classify_id"));
                c.setClassifyName(rs.getString("classify_name"));
                c.setClassifyPid(rs.getInt("classify_pid"));
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "APP分类" + id + "失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return c;
    }

    @Override
    public List<Classify> getClassify(Integer pid) {
        List<Classify> list = new ArrayList<>();
        String sql = "select classify_id,classify_pid,classify_name from tb_classify where classify_pid=?;";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, pid);
            rs = ps.executeQuery();
            Classify c = null;
            while (rs.next()) {
                c = new Classify();
                c.setClassifyId(rs.getInt("classify_id"));
                c.setClassifyName(rs.getString("classify_name"));
                c.setClassifyPid(pid);
                list.add(c);
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "app分类列表失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return list;
    }

    @Override
    public List<App> getAppByClassify(Integer classifyId, Integer companyId) {
        List<App> list = new ArrayList<>();
        String sql = "select s.software_id,s.software_name,s.picture_name,s.intro,s.description,s.create_date,c.company_name from tb_software s left join tb_software_classify_relat sc on s.software_id=sc.software_id left join tb_company c on s.company_id = c.company_id where sc.classify_id=? and s.off_line=? and ((s.company_id=? and s.attribute=?) or s.attribute=?);";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, classifyId);
            ps.setInt(2, AppOffline.ON);
            ps.setInt(3, companyId);
            ps.setInt(4, AppPermission.PRIVATE);
            ps.setInt(5, AppPermission.PUBLIC);
            rs = ps.executeQuery();
            App app = null;
            while (rs.next()) {
                app = new App();
                app.setSoftwareId(rs.getLong("software_id"));
                app.setSoftwareName(rs.getString("software_name"));
                app.setPictureName(rs.getString("picture_name"));
                app.setIntro(rs.getString("intro"));
                app.setDescription(rs.getString("description"));
                app.setCreateDate(rs.getDate("create_date"));
                app.setCompanyName(rs.getString("company_name"));
                list.add(app);
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "查寻" + classifyId + "分类下的APP列表失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return list;
    }

    @Override
    public App getAppById(Integer id, Integer userId) {
        App app = new App();
        String sql = "select s.software_id,s.software_name,s.english_name,s.host,s.picture_name,s.create_date,s.intro,s.description,s.app_doc,s.data_num,c.company_name,GROUP_CONCAT(cls.classify_name) classify,(select count(us.id) from tb_user_software us where us.software_id=s.software_id and us.user_id=?) isAdded from tb_software s left join tb_company c on s.company_id=c.company_id left join tb_software_classify_relat sc on s.software_id=sc.software_id left join tb_classify cls on sc.classify_id=cls.classify_id where s.software_id=?;";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                app = new App();
                app.setSoftwareId(rs.getLong("software_id"));
                app.setSoftwareName(rs.getString("software_name"));
                app.setEnglishName(rs.getString("english_name"));
                app.setHost(rs.getString("host"));
                app.setPictureName(rs.getString("picture_name"));
                app.setDescription(rs.getString("description"));
                app.setCreateDate(rs.getDate("create_date"));
                app.setIntro(rs.getString("intro"));
                app.setAppDoc(rs.getString("app_doc"));
                app.setDataNum(rs.getInt("data_num"));
                app.setCompanyName(rs.getString("company_name"));
                app.setClassifyNames(rs.getString("classify"));
                app.setIsAdded(rs.getInt("isAdded"));
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "查看APP" + id + "失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return app;
    }

    @Override
    public PageList<App> getAppPageListByClassify(Integer classifyId,
            Integer classifyPId, Integer companyId, String sortField,
            String sortType, Page page) {
        PageList<App> pageList = new PageList<>();
        StringBuffer queryBuff = new StringBuffer();
        StringBuffer countBuff = new StringBuffer();
        queryBuff
                .append("select s.software_id,s.software_name,s.picture_name,s.intro,s.description,s.create_date,c.company_name from tb_software s left join tb_software_classify_relat sc on s.software_id=sc.software_id left join tb_company c on s.company_id = c.company_id where s.off_line=? and ((s.company_id=? and s.attribute=?) or s.attribute=?)");
        countBuff
                .append("select count(s.software_name) scount from tb_software s left join tb_software_classify_relat sc on s.software_id=sc.software_id where s.off_line=? and ((s.company_id=? and s.attribute=?) or s.attribute=?)");
        String classyCondition = "";
        if (classifyPId == 0) {
            classyCondition = " and sc.classify_id in (select classify_id from tb_classify c where classify_pid = ?)";
        } else {
            classyCondition = " and sc.classify_id=?";
        }
        queryBuff.append(classyCondition);
        countBuff.append(classyCondition);
        if (!sortField.equals("")) {
            queryBuff.append(" order by ").append(sortField).append(" ")
                    .append(sortType);
        }
        int start = (page.getCurrentPage() - 1) * page.getPageSize();
        queryBuff.append(" limit ").append(start).append(",")
                .append(page.getPageSize());
        List<App> list = new ArrayList<>();
        System.out.println(queryBuff.toString());
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(queryBuff.toString());
            ps.setInt(1, AppOffline.ON);
            ps.setInt(2, companyId);
            ps.setInt(3, AppPermission.PRIVATE);
            ps.setInt(4, AppPermission.PUBLIC);
            ps.setInt(5, classifyId);
            rs = ps.executeQuery();
            App app = null;
            while (rs.next()) {
                app = new App();
                app.setSoftwareId(rs.getLong("software_id"));
                app.setSoftwareName(rs.getString("software_name"));
                app.setPictureName(rs.getString("picture_name"));
                app.setIntro(rs.getString("intro"));
                app.setDescription(rs.getString("description"));
                app.setCreateDate(rs.getDate("create_date"));
                app.setCompanyName(rs.getString("company_name"));
                list.add(app);
            }
            ps = conn.prepareStatement(countBuff.toString());
            ps.setInt(1, AppOffline.ON);
            ps.setInt(2, companyId);
            ps.setInt(3, AppPermission.PRIVATE);
            ps.setInt(4, AppPermission.PUBLIC);
            ps.setInt(5, classifyId);
            rs = ps.executeQuery();
            if (rs.next()) {
                page.make(rs.getInt("scount"));
            } else {
                page.make(0);
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "的APP分类列表失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        pageList.setDatas(list);
        pageList.setPage(page);
        return pageList;
    }

    @Override
    public List<App> getMyAppList(Integer userId) {
        List<App> list = new ArrayList<>();
        String sql = "select us.user_id,s.software_id,s.software_name,s.picture_name,s.intro,s.description,s.create_date,GROUP_CONCAT(c.classify_name) classify,com.company_name from tb_user_software us left join tb_software s on us.software_id=s.software_id left join tb_software_classify_relat sc on s.software_id=sc.software_id left join tb_classify c on c.classify_id=sc.classify_id left join tb_company com on s.company_id=com.company_id where us.user_id=? and s.off_line=? group by s.software_id order by create_date desc;";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, AppOffline.ON);
            rs = ps.executeQuery();
            App app = null;
            while (rs.next()) {
                app = new App();
                app.setSoftwareId(rs.getLong("software_id"));
                app.setSoftwareName(rs.getString("software_name"));
                app.setPictureName(rs.getString("picture_name"));
                app.setIntro(rs.getString("intro"));
                app.setDescription(rs.getString("description"));
                app.setCreateDate(rs.getDate("create_date"));
                app.setClassifyNames(rs.getString("classify"));
                app.setCompanyName(rs.getString("company_name"));
                list.add(app);
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "查寻已添加的APP列表失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return list;
    }

    @Override
    public List<Screen> getScreenByAppId(Integer id) {
        List<Screen> list = new ArrayList<>();
        String sql = "select screen_id,screen_name,software_id from tb_screen where software_id=?";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            Screen screen = null;
            while (rs.next()) {
                screen = new Screen();
                screen.setScreenId(rs.getInt("screen_id"));
                screen.setScreenName(rs.getString("screen_name"));
                screen.setSoftwareId(rs.getInt("software_id"));
                list.add(screen);
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "查寻APP" + id + "的截图列表失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return list;
    }

    @Override
    public Integer userAddApp(Integer userId, Integer appId) {
        Integer result = null;
        String sql = "insert into tb_user_software(user_id,software_id,create_date) values(?,?,now())";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, appId);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            log.error("用户添加APP到可运行列表失败" + e);
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return result;
    }

    @Override
    public Integer userRemoveApp(Integer userId, Integer appId) {
        Integer result = null;
        String sql = "delete from tb_user_software where user_id=? and software_id=?";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, appId);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            log.error("用户取消APP到可运行列表失败" + e);
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return result;
    }
}