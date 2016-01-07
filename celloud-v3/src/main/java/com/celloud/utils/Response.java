package com.celloud.utils;

/**
 * 后台响应工具类,用来向前台传递操作结果
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年1月6日 下午12:59:26
 */
public class Response {
    public static final Response SUCESS = new Response(true, "101", "操作成功！");
    public static final Response SAVE_SUCESS = new Response(true, "102", "保存成功！");
    public static final Response UPDATE_SUCESS = new Response(true, "103", "修改成功！");
    public static final Response DELETE_SUCESS = new Response(true, "104", "删除成功！");
    /**
     * 操作是否成
     */
    private boolean sucess;
    /**
     * 操作失败的错误码
     */
    private String code;
    /**
     * 操作结果描述
     */
    private String message;
    /**
     * 数据
     */
    private Object data;

    public Response() {
    }

    public Response(boolean sucess, String code, String message) {
        this.sucess = sucess;
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数，使用此构造函数构造的Response，代表失败的响应 <br>
     * <strong>成功的道理都是相似的,失败的原因各有不同<strong>
     * 
     * @param code
     * @param message
     */
    public Response(String code, String message) {
        this.sucess = false;
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数，使用此构造函数构造的Response，代表失败的响应 <br>
     * <strong>成功的道理都是相似的,失败的原因各有不同<strong>
     * 
     * @param code
     * @param message
     */
    public Response(String message) {
        this.sucess = false;
        this.code = "201";
        this.message = message;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Response setData(Object data) {
        this.data = data;
        return this;
    }

    public Object getData() {
        return this.data;
    }

}
