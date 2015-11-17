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

import com.celloud.dao.DataDao;
import com.celloud.sdo.Data;
import com.mysql.jdbc.Statement;
import com.nova.constants.DataState;
import com.nova.constants.ReportState;
import com.nova.constants.ReportType;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.utils.ConnectManager;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-14上午10:49:19
 * @version Revision: 1.0
 */
public class DataDaoImpl extends BaseDao implements DataDao {
    Logger log = Logger.getLogger(DataDaoImpl.class);
    private Connection conn = null;
    private PreparedStatement qps = null;
    private PreparedStatement cps = null;
    private ResultSet qrs = null;
    private ResultSet crs = null;

    @Override
    public PageList<Data> getAllData(Page page, Integer userId) {
        PageList<Data> dataPageList = new PageList<Data>();
        StringBuffer queryBuff = new StringBuffer();
        StringBuffer countBuff = new StringBuffer();
        queryBuff
                .append("select f.*,(select count(r.report_id) reportNum from tb_report r where r.file_id=f.file_id and r.flag=? and r.isdel=?)as reportNum,(select count(software_id) from tb_report x,tb_data_project_relat y where y.file_id=f.file_id and y.project_id=x.project_id and x.state!=? and x.isdel=?) as isRunning from tb_file f where f.user_id=? and f.state=?");
        queryBuff.append(
                " order by f.create_date desc,f.file_name asc limit 0,")
                .append(page.getPageSize());
        countBuff
                .append("select count(file_name) data_count from tb_file f where f.user_id=? and f.state=?");
        List<Data> datas = new ArrayList<Data>();
        try {
            conn = ConnectManager.getConnection();
            qps = conn.prepareStatement(queryBuff.toString());
            qps.setInt(1, ReportType.DATA);
            qps.setInt(2, DataState.ACTIVE);
            qps.setInt(3, ReportState.COMPLETE);
            qps.setInt(4, DataState.ACTIVE);
            qps.setInt(5, userId);
            qps.setInt(6, DataState.ACTIVE);
            qrs = qps.executeQuery();
            cps = conn.prepareStatement(countBuff.toString());
            cps.setInt(1, userId);
            cps.setInt(2, DataState.ACTIVE);
            crs = cps.executeQuery();
            while (qrs.next()) {
                Data data = new Data();
                data.setFileId(qrs.getLong("file_id"));
                data.setDataKey(qrs.getString("data_key"));
                data.setFileName(qrs.getString("file_name"));
                data.setAnotherName(qrs.getString("another_name"));
                data.setSize(qrs.getLong("size"));
                data.setStrain(qrs.getString("strain"));
                data.setFileFormat(qrs.getInt("file_format"));
                data.setDataTags(qrs.getString("data_tags"));
                data.setCreateDate(qrs.getTimestamp("create_date"));
                data.setSample(qrs.getString("sample"));
                data.setReportNum(qrs.getInt("reportNum"));
                data.setIsRunning(qrs.getInt("isRunning"));
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
    public PageList<Data> getDataByCondition(Page page, Integer userId,
            Integer sortType, String sortByName, String sortByDate,
            String condition) {
        PageList<Data> dataPageList = new PageList<Data>();
        StringBuffer queryBuff = new StringBuffer();
        StringBuffer countBuff = new StringBuffer();
        queryBuff
                .append("select f.*,(select count(r.report_id) reportNum from tb_report r where r.file_id=f.file_id and r.flag=? and r.isdel=?)as reportNum,(select count(software_id) from tb_report x,tb_data_project_relat y where y.file_id=f.file_id and y.project_id=x.project_id and x.state!=? and x.isdel=?) as isRunning from tb_file f where f.user_id=? and f.state=?");
        countBuff
                .append("select count(file_name) data_count from tb_file f where f.user_id=? and f.state=?");
        if ((null != condition) && !("".equals(condition))
                && !("null".equals(condition))) {
            queryBuff.append(" and (f.data_tags like '%").append(condition)
                    .append("%' or f.file_name like '%").append(condition)
                    .append("%' or f.another_name like '%").append(condition)
                    .append("%')");
            countBuff.append(" and (f.data_tags like '%").append(condition)
                    .append("%' or f.file_name like '%").append(condition)
                    .append("%' or f.another_name like '%").append(condition)
                    .append("%')");
        }
        if (sortType == 1) {
            queryBuff.append(" order by f.file_name ").append(sortByName)
                    .append(", f.create_date ").append(sortByDate);
        } else {
            queryBuff.append(" order by f.create_date ").append(sortByDate)
                    .append(", f.file_name ").append(sortByName);
        }
        int start = (page.getCurrentPage() - 1) * page.getPageSize();
        queryBuff.append(" limit ").append(start).append(",")
                .append(page.getPageSize());
        List<Data> datas = new ArrayList<Data>();
        try {
            conn = ConnectManager.getConnection();
            qps = conn.prepareStatement(queryBuff.toString());
            qps.setInt(1, ReportType.DATA);
            qps.setInt(2, DataState.ACTIVE);
            qps.setInt(3, ReportState.COMPLETE);
            qps.setInt(4, DataState.ACTIVE);
            qps.setInt(5, userId);
            qps.setInt(6, DataState.ACTIVE);
            qrs = qps.executeQuery();
            cps = conn.prepareStatement(countBuff.toString());
            cps.setInt(1, userId);
            cps.setInt(2, DataState.ACTIVE);
            crs = cps.executeQuery();
            while (qrs.next()) {
                Data data = new Data();
                data.setFileId(qrs.getLong("file_id"));
                data.setDataKey(qrs.getString("data_key"));
                data.setFileName(qrs.getString("file_name"));
                data.setAnotherName(qrs.getString("another_name"));
                data.setSize(qrs.getLong("size"));
                data.setStrain(qrs.getString("strain"));
                data.setFileFormat(qrs.getInt("file_format"));
                data.setDataTags(qrs.getString("data_tags"));
                data.setCreateDate(qrs.getTimestamp("create_date"));
                data.setSample(qrs.getString("sample"));
                data.setReportNum(qrs.getInt("reportNum"));
                data.setIsRunning(qrs.getInt("isRunning"));
                datas.add(data);
            }
            if (crs.next()) {
                page.make(crs.getInt("data_count"));
            } else {
                page.make(0);
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "搜索关于" + condition + "的数据列表失败");
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
    public Map<String, Integer> getFormatNumByIds(String dataIds) {
        Map<String, Integer> map = new HashMap<>();
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select count(*) num,file_format from(select file_format from tb_file where file_id in (")
                .append(dataIds).append(") group by file_format) t");
        try {
            conn = ConnectManager.getConnection();
            qps = conn.prepareStatement(sql.toString());
            qrs = qps.executeQuery();
            if (qrs.next()) {
                map.put("formatNum", qrs.getInt("num"));
                map.put("format", qrs.getInt("file_format"));
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "查询数据" + dataIds + "每种数据类型的个数失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, qps, qrs);
        }
        return map;
    }

    @Override
    public List<Integer> getRunningDataBySoft(String dataIds, Integer appId) {
        List<Integer> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select dp.file_id from tb_report r, tb_project p,tb_data_project_relat dp where r.project_id=p.project_id and p.project_id=dp.project_id and dp.file_id in (")
                .append(dataIds)
                .append(") and r.software_id=? and r.state!=? and r.isdel=? group by dp.file_id;");
        try {
            conn = ConnectManager.getConnection();
            qps = conn.prepareStatement(sql.toString());
            qps.setInt(1, appId);
            qps.setInt(2, ReportState.COMPLETE);
            qps.setInt(3, DataState.ACTIVE);
            qrs = qps.executeQuery();
            while (qrs.next()) {
                list.add(qrs.getInt("file_id"));
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "获取数据列表" + dataIds + "正在运行APP"
                    + appId + "的数据失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, qps, qrs);
        }
        return list;
    }

    @Override
    public Integer deleteDataByIds(String dataIds) {
        StringBuffer sql = new StringBuffer();
        sql.append("update tb_file set state=? where file_id in (")
                .append(dataIds).append(")");
        Integer num = null;
        try {
            conn = ConnectManager.getConnection();
            qps = conn.prepareStatement(sql.toString());
            qps.setInt(1, DataState.DEELTED);
            num = qps.executeUpdate();
        } catch (SQLException e) {
            log.error("用户" + super.userName + "删除数据" + dataIds + "失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, qps, null);
        }
        return num;
    }

    @Override
    public List<Map<String, String>> getStrainList(Integer userId) {
        String sql = "select distinct strain from tb_file where user_id=? and strain is not null and strain !='';";
        List<Map<String, String>> list = new ArrayList<>();
        try {
            conn = ConnectManager.getConnection();
            qps = conn.prepareStatement(sql);
            qps.setInt(1, userId);
            qrs = qps.executeQuery();
            while (qrs.next()) {
                list.add(setStrainMap(qrs.getString("strain")));
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "获取物种信息列表");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, qps, qrs);
        }
        return list;
    }

    @Override
    public Data getDataAndStrain(Integer userId, Long fileId) {
        String sql = "select f.another_name,f.strain,f.sample,f.data_tags,f1.strain strains from tb_file f left join (select distinct strain,user_id from tb_file where user_id=? and strain is not null and strain !='') f1 on f.user_id=f1.user_id where f.file_id=?;";
        Data data = new Data();
        try {
            conn = ConnectManager.getConnection();
            qps = conn.prepareStatement(sql);
            qps.setInt(1, userId);
            qps.setLong(2, fileId);
            qrs = qps.executeQuery();
            List<String> list = new ArrayList<>();
            StringBuffer strains = null;
            data.setFileId(fileId);
            if (qrs.next()) {
                data.setAnotherName(qrs.getString("another_name"));
                data.setStrain(qrs.getString("strain"));
                data.setSample(qrs.getString("sample"));
                data.setDataTags(qrs.getString("data_tags"));
                strains = new StringBuffer();
                strains.append("{\"id\":\"").append(qrs.getString("strains"))
                        .append("\",\"text\":\"")
                        .append(qrs.getString("strains")).append("\"}");
                list.add(strains.toString());
            }
            while (qrs.next()) {
                strains = new StringBuffer();
                strains.append("{\"id\":\"").append(qrs.getString("strains"))
                        .append("\",\"text\":\"")
                        .append(qrs.getString("strains")).append("\"}");
                list.add(strains.toString());
            }
            data.setStrainList(list);
        } catch (SQLException e) {
            log.error("用户" + super.userName + "获取数据" + fileId + "的其他信息失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, qps, qrs);
        }
        return data;
    }

    private Map<String, String> setStrainMap(String strain) {
        Map<String, String> map = new HashMap<>();
        map.put("id", strain);
        map.put("text", strain);
        return map;
    }

    @Override
    public List<Data> getDatasByIds(String dataIds) {
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select file_id,data_key,file_name,another_name,strain,sample,data_tags from tb_file where file_id in (")
                .append(dataIds).append(")");
        List<Data> list = new ArrayList<>();
        try {
            conn = ConnectManager.getConnection();
            qps = conn.prepareStatement(sql.toString());
            qrs = qps.executeQuery();
            while (qrs.next()) {
                Data data = new Data();
                data.setFileId(qrs.getLong("file_id"));
                data.setDataKey(qrs.getString("data_key"));
                data.setFileName(qrs.getString("file_name"));
                data.setAnotherName(qrs.getString("another_name"));
                data.setStrain(qrs.getString("strain"));
                data.setSample(qrs.getString("sample"));
                data.setDataTags(qrs.getString("data_tags"));
                list.add(data);
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "查询数据" + dataIds + "信息列表失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, qps, qrs);
        }
        return list;
    }

    @Override
    public List<Data> getDataByDataKeys(String dataKeys) {
        List<Data> list = new ArrayList<Data>();
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select file_id,data_key,file_name,another_name,user_id,strain,sample,data_tags,size from tb_file where data_key in (")
                .append(dataKeys).append(")");
        try {
            conn = ConnectManager.getConnection();
            qps = conn.prepareStatement(sql.toString());
            qrs = qps.executeQuery();
            while (qrs.next()) {
                Data data = new Data();
                data.setFileId(qrs.getLong("file_id"));
                data.setDataKey(qrs.getString("data_key"));
                data.setFileName(qrs.getString("file_name"));
                data.setAnotherName(qrs.getString("another_name"));
                data.setUserId(qrs.getInt("user_id"));
                data.setStrain(qrs.getString("strain"));
                data.setSample(qrs.getString("sample"));
                data.setDataTags(qrs.getString("data_tags"));
                data.setSize(qrs.getLong("size"));
                list.add(data);
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "根据数据编号" + dataKeys + "查询数据列表失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, qps, qrs);
        }
        return list;
    }

    @Override
    public Integer updateData(String dataIds, Data data) {
        Integer num = 0;
        StringBuffer sql = new StringBuffer();
        sql.append(
                "update tb_file set strain=?,data_tags=?,sample=?,another_name=? where file_id in (")
                .append(dataIds).append(")");
        try {
            conn = ConnectManager.getConnection();
            qps = conn.prepareStatement(sql.toString());
            qps.setString(1, data.getStrain());
            qps.setString(2, data.getDataTags());
            qps.setString(3, data.getSample());
            qps.setString(4, data.getAnotherName());
            num = qps.executeUpdate();
        } catch (SQLException e) {
            log.error("用户" + super.userName + "修改数据" + dataIds + "信息失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, qps, qrs);
        }
        return num;
    }

    @Override
    public Integer updateData(Data data) {
        Integer num = 0;
        String sql = "update tb_file set data_key=?,size=?,path=?,another_name=?,file_format=?,state=? where file_id=?";
        try {
            conn = ConnectManager.getConnection();
            qps = conn.prepareStatement(sql.toString());
            qps.setString(1, data.getDataKey());
            qps.setLong(2, data.getSize());
            qps.setString(3, data.getPath());
            qps.setString(4, data.getAnotherName());
            qps.setInt(5, data.getFileFormat());
            qps.setInt(6, data.getState());
            qps.setLong(7, data.getFileId());
            num = qps.executeUpdate();
        } catch (SQLException e) {
            log.error("用户" + super.userName + "修改数据" + data.getFileId()
                    + "信息失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, qps, qrs);
        }
        return num;
    }

    @Override
    public Integer updateDatas(List<Data> list) {
        Integer num = 0;
        try {
            StringBuffer sql = new StringBuffer();
            StringBuffer strain = new StringBuffer();
            StringBuffer tags = new StringBuffer();
            StringBuffer sample = new StringBuffer();
            StringBuffer aname = new StringBuffer();
            StringBuffer ids = new StringBuffer();
            for (Data d : list) {
                strain.append("WHEN ").append(d.getFileId()).append(" THEN '")
                        .append(d.getStrain()).append("' ");
                tags.append("WHEN ").append(d.getFileId()).append(" THEN '")
                        .append(d.getDataTags()).append("' ");
                sample.append("WHEN ").append(d.getFileId()).append(" THEN '")
                        .append(d.getSample()).append("' ");
                aname.append("WHEN ").append(d.getFileId()).append(" THEN '")
                        .append(d.getAnotherName()).append("' ");
                ids.append(d.getFileId()).append(",");
            }
            ids.deleteCharAt(ids.length() - 1);
            sql.append("UPDATE tb_file set strain = CASE file_id ")
                    .append(strain).append(" END,data_tags = CASE file_id ")
                    .append(tags).append(" END,sample = CASE file_id ")
                    .append(sample).append(" END,another_name = CASE file_id ")
                    .append(aname).append(" END WHERE file_id in (")
                    .append(ids).append(")");
            conn = ConnectManager.getConnection();
            qps = conn.prepareStatement(sql.toString());
            num = qps.executeUpdate();
        } catch (SQLException e) {
            log.error("用户" + super.userName + "批量修改数据信息失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, qps, qrs);
        }
        return num;
    }

    @Override
    public String getDataSize(String dataIds) {
        StringBuffer sql = new StringBuffer();
        sql.append("select sum(size) size from tb_file where file_id in (")
                .append(dataIds).append(")");
        String size = null;
        try {
            conn = ConnectManager.getConnection();
            qps = conn.prepareStatement(sql.toString());
            qrs = qps.executeQuery();
            if (qrs.next()) {
                size = qrs.getString("size");
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "查询数据" + dataIds + "总大小失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, qps, qrs);
        }
        return size;
    }

    @Override
    public Integer addDataToPro(String[] dataIdArr, Long proId) {
        Integer num = null;
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("insert into tb_data_project_relat(file_id,project_id) values");
            for (String dataId : dataIdArr) {
                sql.append("(").append(dataId).append(",").append(proId)
                        .append("),");
            }
            sql.deleteCharAt(sql.length() - 1);
            conn = ConnectManager.getConnection();
            qps = conn.prepareStatement(sql.toString());
            num = qps.executeUpdate();
        } catch (SQLException e) {
            log.error("用户" + super.userName + "给数据" + dataIdArr + "分配项目"
                    + proId + "失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, qps, qrs);
        }
        return num;
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
    public Integer addData(Data data) {
        Integer num = 0;
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("insert into tb_file(user_id,data_key,file_name,size,path,file_format,another_name,create_date,state) values(?,?,?,?,?,?,?,now(),?);");
            conn = ConnectManager.getConnection();
            qps = conn.prepareStatement(sql.toString(),
                    Statement.RETURN_GENERATED_KEYS);
            qps.setInt(1, data.getUserId());
            qps.setString(2, data.getDataKey());
            qps.setString(3, data.getFileName());
            qps.setLong(4, data.getSize());
            qps.setString(5, data.getPath());
            qps.setInt(6, data.getFileFormat());
            qps.setString(7, data.getAnotherName());
            qps.setInt(8, data.getState());
            num = qps.executeUpdate();
            // 检索由于执行此 Statement 对象而创建的所有自动生成的键
            qrs = qps.getGeneratedKeys();
            if (qrs.next()) {
                num = (int) qrs.getLong(1);
            }
        } catch (SQLException e) {
            log.error("用户" + super.userName + "新增数据信息失败");
            e.printStackTrace();
        } finally {
            ConnectManager.free(conn, qps, qrs);
        }
        return num;
    }
}
