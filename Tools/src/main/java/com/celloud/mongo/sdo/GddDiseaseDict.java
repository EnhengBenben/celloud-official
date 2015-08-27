/**  */
package com.celloud.mongo.sdo;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;

/**
 * GDD疾病名称及类型表
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-8-25下午5:36:00
 * @version Revision: 1.0
 */
public class GddDiseaseDict {
	@Id
	private ObjectId id;
	/** 疾病类型 */
	private String type;
	/** 疾病名称 */
	private String name;
	/** 疾病英文名称 */
	private String engName;
	/** 基因 */
	private String gene;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getGene() {
		return gene;
	}

	public void setGene(String gene) {
		this.gene = gene;
	}
}
