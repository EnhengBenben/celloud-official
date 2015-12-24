package com.celloud.exception;

/**
 * 安全异常，主要用来校验用户是否已登录
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月23日 下午4:11:54
 */
public class SecurityException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String msg="当前用户未登录或登录已超时！";

    public SecurityException() {
        super();
    }

    public SecurityException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
