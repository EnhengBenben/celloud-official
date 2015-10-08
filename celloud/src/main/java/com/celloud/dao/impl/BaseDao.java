package com.celloud.dao.impl;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

public class BaseDao {
    protected Map<String, Object> session = ActionContext.getContext()
	    .getSession();
    protected String userName = String.valueOf(session.get("userName"));
    protected Integer userId = (Integer) session.get("userId");
    protected Integer companyId = (Integer) session.get("companyId");
}
