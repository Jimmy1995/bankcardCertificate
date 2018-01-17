package com.common.frame.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.common.base.BaseAction;
import com.common.frame.model.ActionInfo;
import com.common.frame.model.Menu;
import com.common.frame.service.IActionManageService;
import com.common.frame.service.IMenuManageService;
import com.common.frame.service.IRoleManageService;
import com.common.frame.vo.OtherPojo;
import com.common.util.DateUtil;
import com.common.util.Struts2Utils;
import com.common.util.WebUtils;
import com.common.util.json.JsonUtils;

/**
 * @className:ActionManageAction.java
 * @classDescription:角色管理Action
 * @author:longzy
 * @createTime:2010-7-16
 */
@Namespace("/manage")
@Results({ 
	@Result(name = "success", location = "/manage/action/actionManage.jsp"),
	@Result(name = BaseAction.RELOAD, location = "/manage/action-manage.action", type = "redirect"),
	@Result(name = "alterAction", location = "/manage/action/alterAction.jsp"), 
	@Result(name = "toSaveAction", location = "/manage/action/addAction.jsp"), 
	@Result(name = "error", location = "/manage/login.jsp") })
public class ActionManageAction extends BaseAction<ActionInfo>{
	private static final long serialVersionUID = -8133426711708628684L;
	@Autowired
	protected IActionManageService actionManageService;
	@Autowired
	protected IMenuManageService menuManageService;
	@Autowired
	protected IRoleManageService roleManageService;
	private OtherPojo pojo;
	/**
	 * 查找所有的人员
	 */
	public String list()throws Exception{
		if(!WebUtils.isAJAXRequest(getRequest())){
			String menuId=getParameter("menuId");
			obj.setMenu(new Menu(menuId));
			this.setRequestAttribute("menuId", menuId);
			this.setRequestAttribute("menuList", this.menuManageService.findAllMenus());
			return SUCCESS;
		}
		SerializeFilter filter=new SimplePropertyPreFilter(ActionInfo.class,"actionId","actionName","path","orders","createTime");
		JsonUtils.setPropertyFilter(filter);
		writeJsonReturn("", this.actionManageService.findAllActions(obj));
		return NONE;
	}
	
	/**
	 * 删除权限
	 */
	public String delete() throws Exception {
		String actionids=getParameter("actionids");
		if(actionids!=null){
			String[] us=actionids.split(",");
			for(String id:us){
				this.actionManageService.deleteById(Long.parseLong(id.trim()));
			}
		}
		Struts2Utils.renderText("1");
		return null;
	}
	/**
	 * 查找权限
	 */
	public String toAlter() throws Exception {
		obj=actionManageService.findById(obj.getActionId());
//		/this.setRequestAttribute("action", obj);	

		//获取模块集合
		List<Menu> menuList=this.menuManageService.findAllMenus();
		this.setRequestAttribute("menuList",menuList);	
			
		return "alterAction";
	}
	/**
	 * 修改权限
	 */
	public String alter() throws Exception {
		//获取参数
		String menuId=this.getParameter("menuId");
	

		
		//修改权限
		ActionInfo action=this.actionManageService.findById(obj.getActionId());
		
  
		action.setActionName(obj.getActionName());
		action.setActionPath(obj.getActionPath());
		Menu menu=new Menu();
		menu.setMenuId(menuId);
		action.setMenu(menu);
		action.setOrders(obj.getOrders());
		this.actionManageService.alter(action);
		/*Set<Role> roles=action.getRoles();
		if(roles!=null&&roles.size()>0){
			for (Role role:roles){
				roleManageService.refreshRoleCache(role.getId(),osCacheManage);//刷新缓存
			}
		}*/
		//obj=new ActionInfo();
		//return list();
		writeJsonReturn("保存成功！",null);
		return NONE;
	}

	/**
	 * 查找权限
	 */
	public String toSave() throws Exception {
		List<Menu> menuList=this.menuManageService.findAllMenus();
		this.setRequestAttribute("menuList",menuList);	
		this.setRequestAttribute("menuId", getParameter("menuId"));	
		return "toSaveAction";
	}
	/**
	 * 保存权限
	 */
	public String save() throws Exception {
		//获取参数
		String actionName=this.getParameter("actionName");
		String actionPath=this.getParameter("actionPath");		
		String menuId=this.getParameter("menuId");
		int orders=Integer.parseInt(this.getParameter("orders"));
		
		//保存权限
		ActionInfo action=new ActionInfo();
		
		action.setActionName(actionName);
		action.setActionPath(actionPath);
		Menu menu=new Menu();
		menu.setMenuId(menuId);
		action.setMenu(menu);
		action.setCreateTime(DateUtil.datetime());
		action.setOrders(orders);
		
		this.actionManageService.save(action);
		
		/*Set<Role> roles=action.getRoles();
		if(roles!=null&&roles.size()>0){
			for (Role role:roles){
				roleManageService.refreshRoleCache(role.getId(),osCacheManage);//刷新缓存
			}
		}*/
		//obj=new ActionInfo();
		//return list();
		writeJsonReturn("保存成功！",null);
		return NONE;
	}
	public OtherPojo getPojo() {
		if(pojo==null){
			pojo=new OtherPojo();
		}
		return pojo;
	}

	public void setPojo(OtherPojo pojo) {
		this.pojo = pojo;
	}
}
