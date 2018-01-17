package com.common.frame.service;



import java.io.Serializable;
import java.util.List;

import com.common.base.service.ICommonService;
import com.common.frame.model.Role;
import com.common.web.cache.OSCacheManage;
import com.common.web.page.Page;

/**
 * @className:IRoleManageService.java
 * @classDescription:权限管理接口
 * @author:longzy
 * @createTime:2010-7-8
 */
public interface IRoleManageService extends ICommonService<Role>{
	/**
	 * 分页查询
	 * @param 
	 * @param 
	 * @return page
	 */
	public Page findAllRoles(Role role);
	/**
	 * 查询所有的角色
	 * @param page
	 * @param pfList 查询条件
	 * @return
	 */
	public List<Role> findAllRoles();
	/**
	 * 刷新缓存中的角色
	 */
	public void refreshRoleCache(Serializable id,OSCacheManage osCacheManage);
}
