package com.mongo.sdo;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * GDD遗传方式
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-29下午6:26:23
 * @version Revision: 1.0
 */
@Entity(noClassnameStored = true)
public class GddGeneticMethod implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private ObjectId id;
    /** 基因名称 */
    private String gene;
    /** 遗传方式 */
    private String method;

    public ObjectId getId() {
	return id;
    }

    public void setId(ObjectId id) {
	this.id = id;
    }

    public String getGene() {
	return gene;
    }

    public void setGene(String gene) {
	this.gene = gene;
    }

    public String getMethod() {
	return method;
    }

    public void setMethod(String method) {
	this.method = method;
    }
}
