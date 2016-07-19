package com.celloud.backstage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.backstage.model.EmailTemplate;


public interface EmailTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmailTemplate record);

    int insertSelective(EmailTemplate record);

    EmailTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmailTemplate record);

    int updateByPrimaryKeyWithBLOBs(EmailTemplate record);

    int updateByPrimaryKey(EmailTemplate record);

	List<EmailTemplate> getAll(@Param("state") Integer state);

	EmailTemplate getTemplate(@Param("method") String method, @Param("state") Integer state);
}