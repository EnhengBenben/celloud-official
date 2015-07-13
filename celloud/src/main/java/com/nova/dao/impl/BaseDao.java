package com.nova.dao.impl;

import com.opensymphony.xwork2.ActionContext;

public class BaseDao {
	protected String userName = String.valueOf(ActionContext.getContext().getSession().get("userName"));
}
