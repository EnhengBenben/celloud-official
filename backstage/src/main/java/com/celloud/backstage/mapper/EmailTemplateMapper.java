package com.celloud.backstage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.backstage.model.EmailTemplate;
import com.celloud.backstage.page.Page;

public interface EmailTemplateMapper {
    int deleteByPrimaryKey(@Param("id") Integer id,
            @Param("state") Integer state);

    int insert(EmailTemplate record);

    int insertSelective(EmailTemplate record);

    EmailTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmailTemplate record);

    int updateByPrimaryKeyWithBLOBs(EmailTemplate record);

    int updateByPrimaryKey(EmailTemplate record);

    List<EmailTemplate> getEmailTemplates(Page page,
            @Param("state") Integer state);

    EmailTemplate getTemplateByMethod(@Param("method") String method,
            @Param("id") Integer id, @Param("state") Integer state);

    EmailTemplate getTemplate(@Param("method") String method,
            @Param("state") Integer state);

    List<EmailTemplate> getAll(@Param("state") Integer state);
}