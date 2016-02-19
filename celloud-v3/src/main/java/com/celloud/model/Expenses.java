package com.celloud.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

/**
 * 消费记录的基类
 * 
 * @author lin
 * @date 2016年2月18日 下午6:29:43
 */
public class Expenses implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private ObjectId id;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * 折扣
	 */
	private Object discount;
	/**
	 * 真实价格
	 */
	private BigDecimal realPrice;
	/**
	 * 交易快照
	 */
	private Object snapshot;
	/**
	 * 创建时间
	 */
	private Date createDate;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Object getDiscount() {
		return discount;
	}

	public void setDiscount(Object discount) {
		this.discount = discount;
	}

	public BigDecimal getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}

	public Object getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(Object snapshot) {
		this.snapshot = snapshot;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
