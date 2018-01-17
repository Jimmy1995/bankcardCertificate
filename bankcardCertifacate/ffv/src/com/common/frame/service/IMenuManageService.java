package com.common.frame.service;

import java.util.List;

import com.common.base.service.ICommonService;
import com.common.frame.model.Menu;
import com.common.web.page.Page;


/**
 * @className:IMenuManageService.java
 * @classDescription:菜单管理接口
 * @author:longzy
 * @createTime:2010-7-8
 */
public interface IMenuManageService extends ICommonService<Menu>{
	/**
	 * 分页查询
	 * @param 
	 * @param 
	 * @return page
	 */
	public Page findAllMenus(Menu menu);
	/**
	 * 查询所有的菜单
	 * @return
	 */
	public List<Menu> findAllMenus();
	public List<Menu> findOneTree(String id);
}
