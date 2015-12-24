package com.celloud.exception;

/**
 * 业务异常
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月23日 下午4:11:19
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String msg;

    public BusinessException() {
        super();
    }

    public BusinessException(String msg) {
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
