package com.common.frame.enums;

public enum MenuType {
	COMMON("公共"),GENERAL("一般");
	// 成员变量
	private String name;

	// 构造方法
	private MenuType(String name) {
		this.name = name;
	}

	// 覆盖方法
	@Override
	public String toString() {
		return this.name;
	}

}
