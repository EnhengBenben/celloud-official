package com.celloud.box.constants;

/**
 * 后台响应工具类,用来向前台传递操作结果
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年1月6日 下午12:59:26
 */
public class Response {
    public static final Response SUCCESS = new Response(true, "101", "操作成功！");
    public static final Response SAVE_SUCCESS = new Response(true, "102", "保存成功！");
    public static final Response UPDATE_SUCCESS = new Response(true, "103", "修改成功！");
    public static final Response DELETE_SUCCESS = new Response(true, "104", "删除成功！");
	public static final Response FILED_SUCCESS = new Response(true, "105", "归档成功！");
    public static final Response FAIL = new Response("210", "操作失败，原因未知！");
    /**
     * 操作是否成
     */
    private boolean success;
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
    private Object data ;

    public Response() {
    }

    public Response(boolean success, String code, String message) {
        this.success = success;
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
        this.success = false;
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
        this.success = false;
        this.code = "201";
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
