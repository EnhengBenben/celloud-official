package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.celloud.constants.TaskState;
import com.celloud.dao.TaskDao;
import com.celloud.sdo.App;
import com.celloud.sdo.Project;
import com.celloud.sdo.Task;
import com.celloud.sdo.User;
import com.mysql.jdbc.Statement;
import com.nova.constants.ReportType;
import com.nova.utils.ConnectManager;

/**
 * 操作tb_task实现类
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-11-3下午3:32:49
 * @version Revision: 1.0
 */
public class TaskDaoImpl extends BaseDao implements TaskDao {
    Logger log = Logger.getLogger(TaskDaoImpl.class);
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    @Override
    public Long create(Task task) {
        String sql = "INSERT INTO tb_task (user_id, app_id, data_key, command, state, params, project_id, create_date) VALUES (?, ?, ?, ?, ?, ?, ?, now());";
        Long id = null;
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, task.getUserId());
            ps.setLong(2, task.getAppId());
            ps.setString(3, task.getDataKey());
            ps.setString(4, task.getCommand());
            ps.setInt(5, TaskState.WAITTING);
            ps.setString(6, task.getParams());
            ps.setLong(7, task.getProjectId());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "创建任务失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return id;
    }

    @Override
    public Task getFirstTask(Long appId) {
        Task task = null;
        try {
            conn = ConnectManager.getConnection();
            String sql = "select * from tb_task WHERE state=? and app_id=? ORDER BY task_id LIMIT 1;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, TaskState.WAITTING);
            ps.setLong(2, appId);
            rs = ps.executeQuery();
            if (rs.next()) {
                task = new Task();
                task.setTaskId(rs.getLong("task_id"));
                task.setAppId(rs.getLong("app_id"));
                task.setUserId(rs.getLong("user_id"));
                task.setDataKey(rs.getString("data_key"));
                task.setCommand(rs.getString("command"));
                task.setParams(rs.getString("params"));
                task.setProjectId(rs.getLong("project_id"));
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "修改任务信息失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return task;
    }

    @Override
    public Integer updateToRunning(Long taskId) {
        Integer num = 0;
        String sql = "update tb_task set state=?,start_date=now() where task_id=?";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql.toString());
            ps.setInt(1, TaskState.RUNNING);
            ps.setLong(2, taskId);
            num = ps.executeUpdate();
        } catch (SQLException e) {
            log.error("用户" + super.userName + "修改任务信息失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return num;
    }

    @Override
    public Integer updateToDone(Long taskId) {
        Integer num = 0;
        String sql = "update tb_task set state=?,end_date=now() where task_id=?";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql.toString());
            ps.setInt(1, TaskState.DONE);
            ps.setLong(2, taskId);
            num = ps.executeUpdate();
        } catch (SQLException e) {
            log.error("用户" + super.userName + "修改任务信息失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return num;
    }

    @Override
    public Integer getRunningNumByAppId(Long appId) {
        Integer num = 0;
        String sql = "select count(command) runNum from tb_task where app_id=? and state=?;";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql.toString());
            ps.setLong(1, appId);
            ps.setInt(2, TaskState.RUNNING);
            rs = ps.executeQuery();
            if (rs.next()) {
                num = rs.getInt("runNum");
            }
        } catch (SQLException e) {
            log.error("获取" + appId + "正在运行的任务数失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return num;
    }

    @Override
    public Map<String, Object> getTaskInfoByProId(Long proId) {
        Map<String, Object> map = new HashMap<>();
        String sql = "select p.project_id,p.project_name,p.create_date,u.user_id,u.username,u.email,s.software_id,s.software_name,s.title,s.method,count(dp.file_id) dataNum from tb_project p left join tb_user u on p.user_id=u.user_id left join tb_report r on p.project_id=r.project_id left join tb_software s on r.software_id=s.software_id left join tb_data_project_relat dp on p.project_id=dp.project_id where p.project_id=? and r.flag=? group by p.project_id";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql.toString());
            ps.setLong(1, proId);
            ps.setInt(2, ReportType.PROJECT);
            rs = ps.executeQuery();
            Project pro = new Project();
            App app = new App();
            User user = new User();
            if (rs.next()) {
                pro.setProjectId(rs.getLong("project_id"));
                pro.setProjectName(rs.getString("project_name"));
                pro.setCreateDate(rs.getDate("create_date"));
                pro.setFileNum(rs.getInt("dataNum"));
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                app.setSoftwareId(rs.getLong("software_id"));
                app.setSoftwareName(rs.getString("software_name"));
                app.setTitle(rs.getString("title"));
                app.setMethod(rs.getString("method"));
            }
            map.put("project", pro);
            map.put("app", app);
            map.put("user", user);
        } catch (SQLException e) {
            log.error("根据proId" + proId + "获取报告信息、任务编号、app信息、数据个数失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return map;
    }

    @Override
    public Task getTaskDataAppPro(String dataKey, Long appId, Long proId) {
        Task task = null;
        String sql = "select * from tb_task where data_key=? and app_id=? and project_id=?;";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, dataKey);
            ps.setLong(2, appId);
            ps.setLong(3, proId);
            rs = ps.executeQuery();
            if (rs.next()) {
                task = new Task();
                task.setTaskId(rs.getLong("task_id"));
                task.setAppId(rs.getLong("app_id"));
                task.setUserId(rs.getLong("user_id"));
                task.setDataKey(rs.getString("data_key"));
                task.setCommand(rs.getString("command"));
                task.setParams(rs.getString("params"));
                task.setProjectId(rs.getLong("project_id"));
            }
        } catch (SQLException e) {
            log.error("获取" + appId + "正在运行的任务数失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return task;
    }

    @Override
    public Integer deleteTask(Long proId) {
        Integer num = 0;
        String sql = "update tb_task set state=?,delete_date=now() where proId=? and state<?;";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql.toString());
            ps.setInt(1, TaskState.DELETE);
            ps.setLong(2, proId);
            ps.setInt(3, TaskState.DONE);
            num = ps.executeUpdate();
        } catch (SQLException e) {
            log.error("根据proId" + proId + "删除未运行或正在运行的任务");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return num;
    }

}
