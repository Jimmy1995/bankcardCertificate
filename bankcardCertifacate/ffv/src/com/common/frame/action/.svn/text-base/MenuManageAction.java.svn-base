package com.common.frame.action;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.base.BaseAction;
import com.common.frame.model.Menu;
import com.common.frame.model.Role;
import com.common.frame.service.IMenuManageService;
import com.common.frame.service.IRoleManageService;
import com.common.web.cache.OSCacheManage;

/**
 * @className:MenuManageAction.java
 * @classDescription:角色管理Action
 * @author:longzy
 * @createTime:2010-7-16
 */
@Namespace("/manage")
@Results({ 
	@Result(name = "success", location = "/manage/menu/menuManage.jsp"),
	@Result(name = BaseAction.RELOAD, location = "/manage/menu/menuIndex.jsp", type = "redirect"),
	@Result(name = "alterMenu", location = "/manage/menu/alterMenu.jsp"), 
	@Result(name = "toSaveMenu", location = "/manage/menu/addMenu.jsp"), 
	@Result(name = "error", location = "/manage/login.jsp")})
public class MenuManageAction extends BaseAction<Menu>{
	private static final long serialVersionUID = 6775663565925490763L;
	@Autowired
	protected IMenuManageService menuManageService;
	@Autowired
	protected IRoleManageService roleManageService;
	// 申明缓存对象
	protected OSCacheManage osCacheManage = OSCacheManage.getInstance();
	/**
	 * 分页查找菜单
	 */
	public String list()throws Exception{
		//查询所有权限，并放入会话
		setPage(menuManageService.findAllMenus(obj));
		return SUCCESS;
	}
	/**
	 * 生成菜单管理的树
	 * @return
	 */
	public String queryAllMenu(){
		List<Menu> list=menuManageService.findAllMenus();
		StringBuffer s=new StringBuffer();
		for(Menu menu:list){
			s.append("zNodes.push({id:\""+menu.getMenuId()+"\",pId:\""+menu.getParentId()+"\",name:\""+menu.getMenuName()+"\",url:\""+getRoot()+"/manage/action-manage.action?menuId="+menu.getMenuId()+"\",target:\"right\"});");
		}
		try {
			response.setCharacterEncoding("utf-8");
			this.response.getWriter().write(s.toString());
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除权限
	 */
	public String delete() throws Exception {
		String id=this.getParameter("menuId");
		Set<Role> roles=this.menuManageService.findById(id).getRoles();
		boolean flag=(roles!=null&&roles.size()>0);
		this.menuManageService.deleteById(id);
		if(flag){
			for (Role role:roles){
				roleManageService.refreshRoleCache(role.getRoleId(),osCacheManage);//刷新缓存
			}
		}
		return BaseAction.RELOAD;

	}
	/**
	 * 修改菜单
	 */
	public String toAlter() throws Exception {
		String id=this.getParameter("menuId");
		obj=menuManageService.findById(id);
		//获取菜单集合
		List<Menu>menuList=this.menuManageService.findAllMenus();
		this.setRequestAttribute("menuList",menuList);		
			
		return "alterMenu";
	}
	/**
	 * 修改菜单
	 */
	public String alter() throws Exception {
		//修改菜单
		Menu menu=this.menuManageService.findById(obj.getMenuId());
		
		menu.setMenuName(obj.getMenuName());
		menu.setMenuUrl(obj.getMenuUrl());
		menu.setImageUrl(obj.getImageUrl());
		menu.setParentId(obj.getParentId());
		menu.setOrders(obj.getOrders());
		this.menuManageService.alter(menu);
		Set<Role> roles=menu.getRoles();
		if(roles!=null&&roles.size()>0){
			for (Role role:roles){
				roleManageService.refreshRoleCache(role.getRoleId(),osCacheManage);//刷新缓存
			}
		}
		//return BaseAction.RELOAD;
		writeJsonReturn("修改成功！",obj.getMenuId());
		return NONE;
	}

	/**
	 * 去保存菜单
	 */
	public String toSave() throws Exception {
		String checkMenuId=this.getParameter("menuId");
		
		List<Menu>menuList=this.menuManageService.findAllMenus();
		this.setRequestAttribute("menuList",menuList);		
		this.setRequestAttribute("checkMenuId",checkMenuId);
		
		return "toSaveMenu";
}
	/**
	 * 保存菜单
	 */
	public String save() throws Exception {
		this.menuManageService.save(obj);
		//return BaseAction.RELOAD;
		writeJsonReturn("添加成功！",obj.getMenuId());
		return NONE;
	}
}
