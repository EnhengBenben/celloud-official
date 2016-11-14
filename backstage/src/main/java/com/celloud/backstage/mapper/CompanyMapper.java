package com.celloud.backstage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.backstage.model.Company;
import com.celloud.backstage.page.Page;

public interface CompanyMapper {
    int deleteByPrimaryKey(Integer companyId);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Integer companyId);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);
    /**
     * 公司分页列表
     *
     * @return
     * @author han
     * @date 2016年1月25日 下午1:44:53
     */
    public List<Company> getComanyByPage(@Param("state") int state,Page page,@Param("keyword") String keyword);
    
    public List<Company> getComanyList(@Param("state") int state);
    
    public int updateCompany(Company company);
    
    public int addCompany(Company company);
    
    public int updateCompanyState(@Param("companyId")int companyId,@Param("state") int state);
    
    public Company getCompanyById(@Param("companyId") int companyId,@Param("state") int state);
    
	/**
	 * 获取所有拥有APP的公司
	 * 
	 * @return
	 * @author lin
	 * @date 2016年11月14日下午2:06:02
	 */
	public List<Company> getAllCompanyHaveApp();

}