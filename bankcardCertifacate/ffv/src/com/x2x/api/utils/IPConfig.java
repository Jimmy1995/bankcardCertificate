package com.x2x.api.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.common.base.DBHelper;
import com.x2x.manager.model.SysXzqx;

public class IPConfig {
	public static int freshcount = 0;
	private static Map<String, String> map = new HashMap<String, String>();//名称反查编码
	private static Map<String, String> sjmap= new HashMap<String, String>();//编码反查上级编码
	//名称反查编码
	public static Map<String, String> getIPConfig() {
		if (freshcount < 1 || 0 == map.size()) {
			init();
		}
		return map;
	}
	//编码反查上级编码
	public static Map<String, String> getSjmap() {
		if (freshcount < 1 || 0 == sjmap.size()) {
			init();
		}
		return sjmap;
	}
	public static void init() {
		freshcount++;
		List<SysXzqx> sx = DBHelper.queryForList("select * from sys_xzqx",
				null, null, SysXzqx.class);
		if (sx != null) {
			for (SysXzqx s : sx) {
				map.put(s.getQymc(), s.getQybm());
				sjmap.put(s.getQybm(), StringUtils.isBlank(s.getSjqybm())?"1":s.getSjqybm());//1为中国
			}
		}
	}
}
