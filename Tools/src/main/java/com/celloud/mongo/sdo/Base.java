/**  */
package com.celloud.mongo.sdo;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;

/**
 * 基本数据,包含id
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-9下午3:40:14
 * @version Revision: 1.0
 */
public class Base {
	@Id
	private ObjectId id;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
}
