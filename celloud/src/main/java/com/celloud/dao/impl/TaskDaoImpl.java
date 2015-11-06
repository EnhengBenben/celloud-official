package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.celloud.constants.TaskState;
import com.celloud.dao.TaskDao;
import com.celloud.sdo.Task;
import com.mysql.jdbc.Statement;
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
        String sql = "INSERT INTO tb_task (user_id, app_id, data_key, command, state, params, create_date) VALUES (?, ?, ?, ?, ?, ?, now());";
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

}
