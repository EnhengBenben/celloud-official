package com.celloud.model.mongo;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * FSocg 流程的数据库
 * 
 * @author lin
 *
 */
@Entity(noClassnameStored = true)
public class FSocgDB {
	@Id
	private ObjectId id;
	private String chrom;
	private String positionStr;
	private String positionEnd;
	private String alleleName;
	private String num;
	private String plus;
	private String variant;
	private String gene;

	public String getChrom() {
		return chrom;
	}

	public void setChrom(String chrom) {
		this.chrom = chrom;
	}

	public String getPositionStr() {
		return positionStr;
	}

	public void setPositionStr(String positionStr) {
		this.positionStr = positionStr;
	}

	public String getPositionEnd() {
		return positionEnd;
	}

	public void setPositionEnd(String positionEnd) {
		this.positionEnd = positionEnd;
	}

	public String getAlleleName() {
		return alleleName;
	}

	public void setAlleleName(String alleleName) {
		this.alleleName = alleleName;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getPlus() {
		return plus;
	}

	public void setPlus(String plus) {
		this.plus = plus;
	}

	public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}

	public String getGene() {
		return gene;
	}

	public void setGene(String gene) {
		this.gene = gene;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

}
