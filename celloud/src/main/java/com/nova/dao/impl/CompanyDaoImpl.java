package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nova.dao.ICompanyDao;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.Company;
import com.nova.utils.ConnectManager;

public class CompanyDaoImpl implements ICompanyDao {
    Logger log = Logger.getLogger(CompanyDaoImpl.class);

    @Override
    public int addCompany(Company company) {
        Connection conn = null;
        PreparedStatement ps = null;
        int flag = 0;
		String sql = "insert into tb_company (company_name,english_name,company_icon,address,address_en,zip_code,tel,create_date) values (?,?,?,?,?,?,?,now())";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, company.getCompanyName());
            ps.setString(2, company.getEnglishName());
            ps.setString(3, company.getCompanyIcon());
            ps.setString(4, company.getAddress());
            ps.setString(5, company.getAddressEn());
            ps.setString(6, company.getZipCode());
            ps.setString(7, company.getTel());
            flag = ps.executeUpdate();
            log.info("新增公司信息成功");
        } catch (SQLException e) {
            log.error("新增公司信息失败" + e);
        } finally {
            ConnectManager.free(conn, ps, null);
        }
        return flag;
    }

    @Override
    public List<Company> getAllCompany() {
        String sql = "select company_id,company_name,english_name,company_icon,address,address_en,zip_code,tel,state from tb_company";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Company> list = new ArrayList<Company>();
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
				Company com = new Company();
                com.setCompanyId(rs.getInt("company_id"));
                com.setCompanyName(rs.getString("company_name"));
                com.setEnglishName(rs.getString("english_name"));
                com.setCompanyIcon(rs.getString("company_icon"));
                com.setAddress(rs.getString("address"));
                com.setAddressEn(rs.getString("address_en"));
                com.setZipCode(rs.getString("zip_code"));
                com.setTel(rs.getString("tel"));
                com.setState(rs.getInt("state"));
                list.add(com);
            }
            log.info("查询全部公司信息成功");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("查询全部公司信息失败：" + e);
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return list;
    }

    @Override
    public PageList<Company> getCompanyPage(Page page) {
        String sql = "select company_id,company_name,english_name,company_icon,address,address_en,zip_code,tel,state from tb_company limit "
                + (page.getCurrentPage() - 1)
                * page.getPageSize()
                + ","
                + page.getPageSize();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PageList<Company> pageList = new PageList<Company>();
        List<Company> list = new ArrayList<Company>();
        int totalNum = 0;
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Company com = new Company();
                com.setCompanyId(rs.getInt("company_id"));
                com.setCompanyName(rs.getString("company_name"));
                com.setEnglishName(rs.getString("english_name"));
                com.setCompanyIcon(rs.getString("company_icon"));
                com.setAddress(rs.getString("address"));
                com.setAddressEn(rs.getString("address_en"));
                com.setZipCode(rs.getString("zip_code"));
                com.setTel(rs.getString("tel"));
                com.setState(rs.getInt("state"));
                list.add(com);
            }
            sql = "select count(company_id) from tb_company";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                totalNum = rs.getInt(1);
            }
            log.info("分页查询公司信息成功");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("分页查询公司信息失败：" + e);
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        page.make(totalNum);
        pageList.setDatas(list);
        pageList.setPage(page);
        return pageList;
    }

    @Override
    public int updateCompany(Company company) {
        Connection conn = null;
        PreparedStatement ps = null;
        int flag = 0;
        String sql = "update tb_company set company_name=?,english_name=?,company_icon=?,address=?,address_en=?,zip_code=?,tel=? where company_id=?";
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, company.getCompanyName());
            ps.setString(2, company.getEnglishName());
            ps.setString(3, company.getCompanyIcon());
            ps.setString(4, company.getAddress());
            ps.setString(5, company.getAddressEn());
            ps.setString(6, company.getZipCode());
            ps.setString(7, company.getTel());
            ps.setInt(8, company.getCompanyId());
            flag = ps.executeUpdate();
            log.info("修改公司信息成功");
        } catch (SQLException e) {
            log.error("修改公司信息失败" + e);
        } finally {
            ConnectManager.free(conn, ps, null);
        }
        return flag;
    }

    @Override
    public Company getCompany(int companyId) {
        String sql = "select * from tb_company where company_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Company com = null;
        try {
            conn = ConnectManager.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, companyId);
            rs = ps.executeQuery();
            while (rs.next()) {
                com = new Company();
                com.setCompanyId(rs.getInt("company_id"));
                com.setCompanyName(rs.getString("company_name"));
                com.setEnglishName(rs.getString("english_name"));
                com.setCompanyIcon(rs.getString("company_icon"));
                com.setAddress(rs.getString("address"));
                com.setAddressEn(rs.getString("address_en"));
                com.setZipCode(rs.getString("zip_code"));
                com.setTel(rs.getString("tel"));
                com.setState(rs.getInt("state"));
            }
            log.info("查询单个公司信息成功");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("查询单个公司信息失败：" + e);
        } finally {
            ConnectManager.free(conn, ps, rs);
        }
        return com;
    }

	@Override
	public int updateCompany(int companyId, int state) {
		Connection conn = null;
		PreparedStatement ps = null;
		int flag = 0;
		String sql = "update tb_company set state=? where company_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, state);
			ps.setInt(2, companyId);
			flag = ps.executeUpdate();
			log.info("修改公司信息成功");
		} catch (SQLException e) {
			log.error("修改公司信息失败" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}
}