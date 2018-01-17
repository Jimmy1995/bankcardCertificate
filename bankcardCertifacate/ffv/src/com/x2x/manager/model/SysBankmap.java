package com.x2x.manager.model;

public class SysBankmap {
	private String code;
	private String name;
	private String supercode;
	private String supername;
	private String glyhcode;
	private String bankcode;
	private String yxbz;
	private String bankShortName;
	public SysBankmap() {
		super();
	}
	public SysBankmap(String code, String name, String supercode,
			String supername, String glyhcode, String bankcode, String yxbz,
			String bankShortName) {
		super();
		this.code = code;
		this.name = name;
		this.supercode = supercode;
		this.supername = supername;
		this.glyhcode = glyhcode;
		this.bankcode = bankcode;
		this.yxbz = yxbz;
		this.bankShortName = bankShortName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSupercode() {
		return supercode;
	}
	public void setSupercode(String supercode) {
		this.supercode = supercode;
	}
	public String getSupername() {
		return supername;
	}
	public void setSupername(String supername) {
		this.supername = supername;
	}
	public String getGlyhcode() {
		return glyhcode;
	}
	public void setGlyhcode(String glyhcode) {
		this.glyhcode = glyhcode;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getYxbz() {
		return yxbz;
	}
	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
	}
	public String getBankShortName() {
		return bankShortName;
	}
	public void setBankShortName(String bankShortName) {
		this.bankShortName = bankShortName;
	}
}
