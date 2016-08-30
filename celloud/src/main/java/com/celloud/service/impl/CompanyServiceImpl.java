package com.celloud.service.impl;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.celloud.constants.CompanyConstants;
import com.celloud.mapper.CompanyMapper;
import com.celloud.model.mysql.Company;
import com.celloud.service.CompanyService;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
	@Resource
	private CompanyMapper cm;

	@Override
	public Company selectByPrimaryKey(Integer companyId) {
		return cm.selectByPrimaryKey(companyId);
	}

    @Override
    public int updateCompanyIcon(Company company) {
        try {
            String filename = company.getCompanyIcon();
            if (StringUtils.isNotBlank(filename)) {
                FileUtils.moveFile(new File(CompanyConstants.getCompanyIconTempPath() + File.separator + filename),
                    new File(CompanyConstants.getCompanyIconPath() + File.separator + filename));
                return cm.updateByPrimaryKeySelective(company);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
