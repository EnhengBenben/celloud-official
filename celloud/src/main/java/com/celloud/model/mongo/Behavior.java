package com.celloud.model.mongo;

import java.io.Serializable;
import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

import com.celloud.model.mysql.ActionLog;

/**
 * 用户行为分析
 * 
 * @author lin
 * @date 2016年3月16日 下午4:31:01
 */
public class Behavior extends ActionLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private ObjectId id;

	private String method;
	private String action;
	private Map<String, String[]> param;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map<String, String[]> getParam() {
		return param;
	}

	public void setParam(Map<String, String[]> param) {
		this.param = param;
	}

}
