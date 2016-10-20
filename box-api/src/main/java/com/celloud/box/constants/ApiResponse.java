package com.celloud.box.constants;

import java.util.Map;

public class ApiResponse {
	public static final ApiResponse ERROR = new ApiResponse("220", "操作失败，系统异常！");

	public ApiResponse() {
	}

	/**
	 * 构造函数，使用此构造函数构造的Response，代表失败的响应 <br>
	 * <strong>成功的道理都是相似的,失败的原因各有不同<strong>
	 * 
	 * @param code
	 * @param message
	 */
	public ApiResponse(String code, String message) {
		this.success = false;
		this.code = code;
		this.message = message;
	}

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
	private Map<String, Object> data;

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

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
