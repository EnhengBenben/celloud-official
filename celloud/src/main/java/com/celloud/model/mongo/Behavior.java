package com.celloud.model.mongo;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.celloud.model.mysql.ActionLog;

/**
 * 用户行为分析
 * 
 * @author lin
 * @date 2016年3月16日 下午4:31:01
 */
@Entity(noClassnameStored = true)
public class Behavior extends ActionLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private ObjectId id;

	private String method;
	private String action;
	private Long consumeTime;
	private String queryString;

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

	public Long getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Long consumeTime) {
		this.consumeTime = consumeTime;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

}
