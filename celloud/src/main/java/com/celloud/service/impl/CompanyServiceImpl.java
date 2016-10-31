package com.celloud.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.celloud.constants.IconConstants;
import com.celloud.mapper.CompanyMapper;
import com.celloud.mapper.UserMapper;
import com.celloud.model.mysql.Company;
import com.celloud.model.mysql.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.CompanyService;
import com.celloud.utils.PropertiesUtil;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
	@Resource
    private CompanyMapper companyMapper;
    @Autowired
    private UserMapper userMapper;

	@Override
	public Company selectByPrimaryKey(Integer companyId) {
        return companyMapper.selectByPrimaryKey(companyId);
	}

    @Override
    public int updateCompanyIcon(Company company) {
        try {
            String filename = company.getCompanyIcon();
            if (StringUtils.isNotBlank(filename)) {
				FileUtils.moveFile(new File(IconConstants.getTempPath(filename)),
						new File(IconConstants.getCompanyPath(filename)));
                return companyMapper.updateByPrimaryKeySelective(company);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public PageList<User> pageQueryUser(Integer loginUserId, Integer companyId, Page page) {
        List<User> datas = userMapper.findUsersByCompanyId(loginUserId, companyId, PropertiesUtil.testAccountIds, page);
        return new PageList<User>(page, datas);
    }

    @Override
    public Company getCompanyById(Integer companyId) {
        return companyMapper.selectByPrimaryKey(companyId);
    }

    @Override
    public Boolean updateByPrimaryKeySelective(Company updateCompany) {
        updateCompany.setCreateDate(null);
        updateCompany.setUpdateDate(new Date());
        return companyMapper.updateByPrimaryKeySelective(updateCompany) == 1;
    }

}
