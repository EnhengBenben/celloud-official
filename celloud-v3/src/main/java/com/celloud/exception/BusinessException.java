package com.celloud.exception;

public class BusinessException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String msg;
	public BusinessException(){
		super();
	}
	public BusinessException(String msg){
		super(msg);
		this.msg=msg;
	}
	public BusinessException(String code,String msg){
		super();
		this.code=code;
		this.msg=msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
