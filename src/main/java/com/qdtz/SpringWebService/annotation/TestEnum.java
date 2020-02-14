package com.qdtz.SpringWebService.annotation;

public enum TestEnum {
	
	TEST_ENUM01(1, "add"), TEST_ENUM02(2, "update");
	
	private final Integer code;
	private final String value;

	private TestEnum(final Integer code, final String value) {
		this.code = code;
		this.value = value;
	}

	public Integer code() {
		return this.code;
	}

	public String value() {
		return this.value;
	}
}
