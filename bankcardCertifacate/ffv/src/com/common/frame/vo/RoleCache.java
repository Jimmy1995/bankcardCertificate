package com.common.frame.vo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.common.frame.model.DataDictionary;
import com.common.frame.model.Menu;

/**
 * @className:RoleCache.java
 * @classDescription:缓存对象
 * @author:longzy
 * @createTime:2010-7-23
 */
public class RoleCache {
	private  Map<String,String> urlMap=new HashMap<String,String>();//访问权限
	private  Map<String,Menu>  menuMap=new HashMap<String,Menu>();//菜单权限
	private  Set<DataDictionary> dicSet=new HashSet<DataDictionary>();//数据字典权限

	/**
	 * @return the dicSet
	 */
	public Set<DataDictionary> getDicSet() {
		return dicSet;
	}
	/**
	 * @param dicSet the dicSet to set
	 */
	public void setDicSet(Set<DataDictionary> dicSet) {
		this.dicSet = dicSet;
	}
	/**
	 * @return the urlMap
	 */
	public Map<String, String> getUrlMap() {
		return urlMap;
	}
	/**
	 * @param urlMap the urlMap to set
	 */
	public void setUrlMap(Map<String, String> urlMap) {
		this.urlMap = urlMap;
	}
	/**
	 * @return the menuMap
	 */
	public Map<String, Menu> getMenuMap() {
		return menuMap;
	}
	/**
	 * @param menuMap the menuMap to set
	 */
	public void setMenuMap(Map<String, Menu> menuMap) {
		this.menuMap = menuMap;
	}
}
