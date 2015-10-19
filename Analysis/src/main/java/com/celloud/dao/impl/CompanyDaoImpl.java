package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.Logger;

import com.celloud.dao.CompanyDao;
import com.celloud.sdo.Company;
import com.celloud.utils.ConnectManager;
import com.celloud.utils.PropertiesUtil;

public class CompanyDaoImpl implements CompanyDao {
    Logger log = Logger.getLogger(CompanyDaoImpl.class);
    private Connection conn = ConnectManager.getConnection();
    private QueryRunner qr = new QueryRunner();
    private String noUserid = PropertiesUtil.noUserid;

    @Override
    public Object getBigUserCompanyNum(Integer companyId) {
        log.info("获取大客户的所有医院量");
        Map<String, Object> map = null;
        String sql = "select count(*) num from (select c.company_name from tb_user u,tb_dept d,tb_company c where u.dept_id=d.dept_id and c.state=0 and c.company_id=d.company_id and u.company_id=? and u.user_id not in ("
                + noUserid + ") group by c.company_id) t";
        try {
            map = qr.query(conn, sql, new MapHandler(), companyId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map.get("num");
    }

    @Override
    public List<Map<String, Object>> getBigUserCompanyNumByAddr(
            Integer companyId) {
        log.info("根据医院地址获取大客户各省的医院数量");
        List<Map<String, Object>> list = null;
        String sql = "select company_id,company_name,address,count(company_id) num from (select c.company_id,c.company_name,c.address from tb_user u,tb_dept d,tb_company c where u.dept_id=d.dept_id and c.state=0 and c.company_id=d.company_id and u.company_id=? and u.user_id not in ("
                + noUserid + ") group by c.company_id)t group by address;";
        try {
            list = qr.query(conn, sql, new MapListHandler(), companyId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Company> getCompanyByAddr(String addr) {
        log.info("根据医院地址获取医院信息");
        List<Company> list = null;
        try {
            ResultSetHandler<List<Company>> rsh = new BeanListHandler<Company>(
                    Company.class);
            StringBuffer sql = new StringBuffer(
                    "select user_name as userName,count(*) as logNum from tb_log where 1=1");
            list = qr.query(conn, sql.toString(), rsh);
        } catch (SQLException e) {
            log.error("统计指定时间内每个用户登录次数出错！！！" + e);
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getCompanyNumEveryMonth(Integer companyId) {
        log.info("获取大客户的所有医院量");
        List<Map<String, Object>> list = null;
        String sql = "select count(*) num,left(create_date,7) createDate from (select c.company_name,c.create_date from tb_user u,tb_dept d,tb_company c where u.dept_id=d.dept_id and c.state=0 and c.company_id=d.company_id and u.company_id=? and u.user_id not in ("
                + noUserid
                + ") group by c.company_id) t group by left(create_date,7);";
        try {
            list = qr.query(conn, sql, new MapListHandler(), companyId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Company> getCompanyDetailById(Integer companyId) {
        log.info("获取医院详细信息");
        String sql = "select a.company_id,a.company_name,a.address,a.tel,a.create_date,count(username) userNum,sum(a.fnum) fileNum,sum(a.fsize) fileSize,sum(a.rnum) reportNum from (select c.company_id,c.company_name,c.address,c.tel,c.create_date ,(select count(f.file_id) from tb_file f where f.user_id=u.user_id and f.state=0) fnum,(select ifnull(sum(f.size),0) from tb_file f where f.user_id=u.user_id and f.state=0) fsize,(select count(*) from tb_report r where r.user_id=u.user_id and r.isdel=0 and (r.flag=0 or r.report_id=11)) rnum,u.username from tb_company c,tb_dept d,tb_user u where c.company_id=d.company_id and d.dept_id=u.dept_id and c.state=0 and u.state=0 and u.company_id=? and u.user_id not in ("
                + noUserid
                + ") )a  group by a.company_id order by a.fsize desc; ";
        List<Company> list = null;
        try {
            ResultSetHandler<List<Company>> rsh = new BeanListHandler<Company>(
                    Company.class);
            list = qr.query(conn, sql, rsh, companyId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Company getCompanyById(Integer compId) {
        log.info("获取单个医院信息");
        String sql = "select  company_id,company_name,address,tel,create_date, count(id.user_id) userNum,group_concat(username) userNames,group_concat(distinct dept_name) deptNames,sum(fileNum) fileNum,sum(fileSize) fileSize,sum(reportNum) reportNum    from (select a.user_id,a.username,d.dept_name,c.company_id,c.company_name,c.address,c.tel,c.create_date from tb_company c,tb_dept d,tb_user a  where c.company_id=d.company_id and a.dept_id=d.dept_id and a.state=0 and a.user_id not in ("
                + noUserid
                + ") and c.company_id=?) id left join  (select user_id,count(file_id) fileNum,ifnull(sum(size),0) fileSize from tb_file where state = 0 group by user_id) f on f.user_id=id.user_id left join  (select user_id,count(report_id) reportNum from tb_report where isdel = 0 and (flag = 0 or software_id = 11) group by user_id) r on r.user_id=id.user_id";
        Company com = null;
        try {
            ResultSetHandler<Company> rsh = new BeanHandler<Company>(
                    Company.class);
            com = qr.query(conn, sql, rsh, compId);
        } catch (SQLException e) {
            log.error("获取单个医院信息出错：" + e);
            e.printStackTrace();
        }
        return com;
    }

    @Override
    public List<Map<String, Object>> getProvince(Integer companyId) {
        log.info("获取平台医院分布");
        List<Map<String, Object>> list = null;
        String sql = "select province,count(distinct c.company_id) as num from tb_user u,tb_dept d,tb_company c where u.dept_id=d.dept_id and d.company_id=c.company_id and u.user_id not in ("
                + noUserid + ") and u.company_id = ? group by province;";
        try {
            list = qr.query(conn, sql, new MapListHandler(), companyId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
