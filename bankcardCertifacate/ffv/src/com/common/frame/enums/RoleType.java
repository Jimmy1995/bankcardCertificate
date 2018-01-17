package com.common.frame.enums;

public enum RoleType {
	SYSTEM("系统角色"), BUSINESS("业务角色");
	// 成员变量
	private String name;

	// 构造方法
	private RoleType(String name) {
		this.name = name;
	}

	// 覆盖方法
	@Override
	public String toString() {
		return this.name;
	}

//	public static void main(String[] args) {
//		for (RoleType s : RoleType.values()) {
//			System.out.println(s.ordinal());
//		}
//	}
}
