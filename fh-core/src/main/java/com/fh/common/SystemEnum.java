 package com.fh.common;

public enum SystemEnum {
	ERROR_INFO(300,"error"),
	SUCCESS_INFO(200,"ok");

	private int code;
	
	private String msg;
	
	private SystemEnum(int code,String msg){
		this.code=code;
		this.msg=msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
}
