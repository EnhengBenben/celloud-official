package com.celloud.service;

import java.util.List;

import com.celloud.model.mysql.CompanyEmail;

public interface CompanyEmailService {
    /**
     * 
     * @description 根据不为空的条件进行查询
     * @author miaoqi
     * @date 2017年2月17日 下午1:32:00
     * @param companyEmail
     * @return
     */
    List<CompanyEmail> selectBySelective(CompanyEmail companyEmail);
}
