package com.common.frame.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.frame.model.Role;
import com.common.frame.service.ICacheService;
import com.common.frame.service.IRoleManageService;
import com.common.web.SpringContextHolder;
import com.common.web.cache.CacheUtil;
import com.common.web.cache.OSCacheManage;
import com.common.web.filter.init.InitManage;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

@Service
public class CacheService implements ICacheService {
	protected OSCacheManage osCacheManage = OSCacheManage.getInstance();
	@Autowired
	protected IRoleManageService roleManageService;
	public String initCache() {
		try {
			SpringContextHolder.registerBean4DataBase();
			CacheUtil.refreshDictionaryCache(osCacheManage);
			InitManage.init();// 刷新权限（从manageConfig.xml中）
			CacheUtil.refreshCacheConfigByDB(osCacheManage);
		} catch (Exception e) {// 刷新失败
			e.printStackTrace();
		}
		return null;
	}
	public String refreshCache() {
		SpringContextHolder.registerBean4DataBase();
		String msg = "系统缓存刷新成功!";
		GeneralCacheAdministrator cacheObj = OSCacheManage.getOsCache();//把原来的缓存保存起来，以免刷新失败时原来的缓存丢失
		try {
			osCacheManage.clearAllCache();// 先清除所有的缓存
			List<Role> roles = roleManageService.findAllRoles();
			if (roles != null && roles.size() > 0) {
				for (Role role : roles) {
					CacheUtil.refreshRoleCache(osCacheManage, role);// 根据角色刷新菜单机构等信息
				}
			}
			CacheUtil.refreshDictionaryCache(osCacheManage);
			InitManage.init();// 刷新权限（从manageConfig.xml中）
			CacheUtil.refreshCacheConfigByDB(osCacheManage);
		} catch (Exception e) {// 刷新失败
			OSCacheManage.setCache(cacheObj);
			e.printStackTrace();
			msg = "系统缓存刷新失败,请重新配置相关文件!错误为:" + e.getMessage();
		}
		return msg;
	}
}
