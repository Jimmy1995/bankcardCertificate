package com.x2x.manager.vo;

import java.util.Map;

public class ExtVO{
	public String mType;
	public Map map;
	public String getmType() {
		return mType;
	}
	public void setmType(String mType) {
		this.mType = mType;
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public ExtVO(String mType, Map map) {
		super();
		this.mType = mType;
		this.map = map;
	}
	public ExtVO() {
		super();
	}
}
