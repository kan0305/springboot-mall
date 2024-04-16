package com.example.springmall.common;

import java.util.HashMap;
import java.util.Map;

public class ResponseVO {
	public enum CodeType {
		SUCCESS("S001", "執行成功"), 
		FAIL("E001", "執行失敗"),
		PRODUCT_NOT_FOUND("E002", "查無商品"),
		;

		private String code;
		private String message;

		CodeType(String code, String message) {
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}

	/**
	 * Response Data
	 */
	private String rtnCode;
	private String rtnMsg;
	private Map<String, Object> rtnObj = new HashMap<>();

	public ResponseVO() {
		this.rtnCode = CodeType.SUCCESS.code;
		this.rtnMsg = CodeType.SUCCESS.message;
	}

	public String getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}

	public String getRtnMsg() {
		return rtnMsg;
	}

	public void setRtnMsg(String rtnMsg) {
		this.rtnMsg = rtnMsg;
	}

	public Map<String, Object> getRtnObj() {
		return rtnObj;
	}

	public void setRtnObj(Map<String, Object> rtnObj) {
		this.rtnObj = rtnObj;
	}
}
