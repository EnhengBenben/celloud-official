package com.celloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.celloud.mapper.CompanyEmailMapper;
import com.celloud.model.mysql.CompanyEmail;
import com.celloud.service.CompanyEmailService;

@Service
public class CompanyEmailServiceImpl implements CompanyEmailService {

    @Autowired
    private CompanyEmailMapper companyEmailMapper;

    @Override
    public List<CompanyEmail> selectBySelective(CompanyEmail companyEmail) {
        return companyEmailMapper.selectBySelective(companyEmail);
    }

}
