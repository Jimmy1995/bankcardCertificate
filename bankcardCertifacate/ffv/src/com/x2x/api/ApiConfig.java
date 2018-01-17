package com.x2x.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.common.base.DBHelper;

public class ApiConfig {
	public static int freshcount=0;
	private static Map<String,String> m=new HashMap<String,String>();
	private static Map<String,List<String>> authmap=new HashMap<String,List<String>>();
	public static String getSubMchPrivateKey(){//平台私钥
		return getInteConfig("subpri");
	}
	public static String getSubMchPubKey(String bcode){//商户公钥
		return getValue(bcode);
	}
	public static boolean veriyAppIds(String keyname,String appId){//验证此商户是否有这个appid的权限
		String appids = getValue(keyname+"appIds");
		if(StringUtils.isBlank(appids)){//空值返回false
			return false;
		}
		if(StringUtils.isBlank(appId)){//空值返回false
			return false;
		}
		if(appId.equals(appids)){//完全相等，返回true
			return true;
		}
		if(appids.contains(appId)&&appids.replace(appId, "").startsWith(",")){//appids包含appid
			return true;
		}
		return false;
	}
	public static List<String> getReqTypeList(String appid){//根据某一个appid返回一组有权限的reqType
		if(freshcount<1){
			init();
		}
		return authmap.get(appid);
	}
	public static String getValue(String keyname){
		if(freshcount<1){
			init();
		}
		return m.get(keyname);
	}
	public static void init(){
		{//商户密钥初始化
			m.clear();
			List<Map<String, Object>>  bl=DBHelper.queryForList("select * from sys_api_key where yxbz='0'");
			if(bl!=null){
				for(Map<String, Object> b:bl){
					m.put(b.get("bkeyType")+"_"+b.get("bcode"), b.get("bkey")+"");
					if(null!=b.get("ip")&&StringUtils.isNotBlank(b.get("ip")+"")){
						m.put(b.get("bkeyType")+"_"+b.get("bcode")+"ip", b.get("ip")+"");
					}
					m.put(b.get("bkeyType")+"_"+b.get("bcode")+"appIds", b.get("appIds")+"");
				}
			}
		}
		{//api 接口权限初始化
			authmap.clear();
			List<Map<String,Object>> mlist=DBHelper.queryForList("select * from sys_api_appauth");
			if(null==mlist)return;
			for(Map<String,Object> m:mlist){
				List<String> xlist=authmap.get(m.get("appId"));
				if(null==xlist){
					xlist=new ArrayList<String>();
				}
				xlist.add(m.get("reqType")+"");
				authmap.put(m.get("appId")+"", xlist);
			}
		}
		freshcount++;
	}
	public static String getSafeIp(String keyname){
		if(freshcount<1){
			init();
		}
		return m.get(keyname+"ip");
	}
	public static String getInteConfig(String keyname){
		return InteConfig.getInteConfig().get("api."+keyname);
	}
}
