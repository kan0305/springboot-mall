package com.example.springmall.common;

import java.util.HashMap;
import java.util.Map;

public class ResponseVO {
	public enum CodeType {
		SUCCESS("S001", "執行成功"), 
		FAIL("E001", "執行失敗"),
		
		// Product
		PRODUCT_NOT_FOUND("E002", "查無商品"),
		PRODUCT_CREATE_FAIL("E003", "商品建立失敗"),
		
		// User
		USER_SAME_EMAIL_FAIL("E004", "該帳戶已註冊"),
		USER_CREATE_FAIL("E005", "會員註冊失敗"),
		USER_LOGIN_FAIL("E006", "登入失敗，請確認帳戶或密碼是否輸入錯誤"),
		
		// Order
		ORDER_CREATE_FAIL("E007", "訂單創建失敗"),
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
