package com.common.frame.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.pool.DruidDataSource;
import com.common.base.BaseAction;
import com.common.frame.model.ActionInfo;
import com.common.frame.service.ICacheService;
import com.common.web.SpringContextHolder;
import com.common.web.cache.CacheUtil;
import com.common.web.cache.OSCacheManage;

/**
 * @author longzy
 * @description 刷新系统缓存
 */
@Namespace("/manage")
public class RefreshCacheAction extends BaseAction<ActionInfo> {
	private static final long serialVersionUID = 240756107688564451L;
	@Autowired
	protected List<ICacheService> cacheService;
	// 申明缓存对象
	protected OSCacheManage osCacheManage = OSCacheManage.getInstance();

	/**
	 * 1、用户、角色、菜单、机构缓存
	 * 2、openDic
	 */
	@Override
	public String list() throws Exception {
		writeJsonReturn(null, merg());
		return null;
	}
	/**
	 * 1、用户、角色、菜单、机构缓存
	 * 2、openDic
	 */
	public String refresh() throws Exception {
		String msg="";
		for(ICacheService cache:cacheService){
			msg+=cache.refreshCache();
		}
		writeJsonReturn(msg, merg());
		return null;
	}
	private Map<String,Object> merg(){
		Map<String,Object> map=new LinkedHashMap<String,Object>();
		DruidDataSource src=(DruidDataSource)SpringContextHolder.getBean("dataSourceA");
		map.put("最大连接数", src.getMaxActive());
		map.put("活动连接数", src.getActiveCount());
		map.put("spring bean数", SpringContextHolder.getApplicationContext().getBeanDefinitionCount());
		map.putAll(CacheUtil.getAppScopeCaches(osCacheManage));
		return map;
	}
}
