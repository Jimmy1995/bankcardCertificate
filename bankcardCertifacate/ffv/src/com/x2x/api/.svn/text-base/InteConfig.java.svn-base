package com.x2x.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.base.DBHelper;

public class InteConfig {
	public static int freshcount=0;
	public static Map<String,String> m=new HashMap<String,String>();
	public static Map<String,String> getInteConfig(){
		if(freshcount<1||0==m.size()){
			init();
		}
		return m;
	}
	public static void init(){
		freshcount++;
		List<Map<String,Object>> bl=DBHelper.queryForList("select * from sys_inte_config");
		if(bl!=null){
			for(Map<String,Object> b:bl){
				m.put(b.get("inte_code")+"", b.get("inte_value")+"");
			}
		}
	}
}
