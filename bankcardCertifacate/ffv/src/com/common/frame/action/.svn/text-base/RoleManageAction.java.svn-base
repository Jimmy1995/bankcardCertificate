package com.common.frame.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.common.base.BaseAction;
import com.common.base.DBHelper;
import com.common.frame.model.ActionInfo;
import com.common.frame.model.Menu;
import com.common.frame.model.Role;
import com.common.frame.service.IMenuManageService;
import com.common.frame.service.IRoleManageService;
import com.common.frame.vo.OtherPojo;
import com.common.util.DateUtil;
import com.common.util.Struts2Utils;
import com.common.util.WebUtils;
import com.common.util.json.JsonUtils;
import com.common.web.cache.OSCacheManage;

/**
 * @className:RoleManageAction.java
 * @classDescription:角色管理Action
 * @author:longzy
 * @createTime:2010-7-16
 */
@Namespace("/manage")
/**@InterceptorRefs({//表示save方法不能重复提交（includeMethods表示哪些方法参与拦截器，excludeMethods表示哪些方法不用参与拦截器）
	@InterceptorRef(value="tokenSession",params={"includeMethods","save"}),
	@InterceptorRef("frame-stack")
	})**/
@Results({ 
	@Result(name = "success", location = "/manage/role/roleManage.jsp"),
	@Result(name = BaseAction.RELOAD, location = "/manage/role-manage.action", type = "redirect"),
	@Result(name = "alterRole", location = "/manage/role/alterRole.jsp"),
	@Result(name = "toSetAction", location = "/manage/role/setAction.jsp"),
	@Result(name = "toSetMenu", location = "/manage/role/setMenu.jsp"),
	@Result(name = "toSave", location = "/manage/role/addRole.jsp"),
	@Result(name = "error", location = "/manage/login.jsp",type = "redirect") })
	@SuppressWarnings("unchecked")
public class RoleManageAction extends BaseAction<Role>{
	private static final long serialVersionUID = 129872572904993988L;
	private OtherPojo pojo;
	@Autowired
	protected IRoleManageService roleManageService;
	@Autowired
	protected IMenuManageService menuManageService;
	// 申明缓存对象
	protected OSCacheManage osCacheManage = OSCacheManage.getInstance();
	/**
	 * 查找所有的人员
	 */
	public String list()throws Exception{
		if(!WebUtils.isAJAXRequest(getRequest())){
			return SUCCESS;
		}
		SimplePropertyPreFilter filter=new SimplePropertyPreFilter(Role.class,"roleId","roleName","roleInfo","orders","createTime");
		JsonUtils.setPropertyFilter(filter);
		writeJsonReturn("", roleManageService.findAllRoles(obj));
		return NONE;
	}
	
	/**
	 * 删除角色
	 */
	public String delete() throws Exception {
		String roleids=getParameter("roleids");
		if(roleids!=null){
			getPojo().setRoles(roleids.split(","));
		}
		if(pojo.getRoles()!=null&&pojo.getRoles().length>0){
			for(String id:pojo.getRoles()){
				this.roleManageService.deleteById(id.trim());
			}
		}
		Struts2Utils.renderText("1");
		return null;

	}
	/**
	 * 查找角色
	 */
	public String toAlter() throws Exception {
		obj=roleManageService.findById(obj.getRoleId());
		return "alterRole";
	}
	/**
	 * 修改角色
	 */
	public String alter() throws Exception {	
		//修改角色
		Role role=this.roleManageService.findById(obj.getRoleId());
  
		role.setRoleName(obj.getRoleName());
		role.setRoleInfo(obj.getRoleInfo());
		role.setOrders(obj.getOrders());
		role.setType(obj.getType());
		role.setWelcomePage(obj.getWelcomePage());
		this.roleManageService.alter(role);
		roleManageService.refreshRoleCache(obj.getRoleId(),osCacheManage);//刷新缓存
		writeJsonReturn("保存成功！",null);
		return NONE;
	}
	
	/**
	 * 去新增角色
	 */
	public String toSave() throws Exception {
		return "toSave";
	}
	/**
	 * 保存角色
	 */
	public String save() throws Exception {
		//保存角色
		obj.setCreateTime(DateUtil.datetime());
		String id=DBHelper.getMaxId("sys_role");
		obj.setRoleId(id);
		this.roleManageService.save(obj);
		writeJsonReturn("保存成功！",null);
		return NONE;
	}
	
	/**
	 * 为角色分配权限
	 */
	public String setActionByRole() throws Exception{
		//获取角色
		Role role=this.roleManageService.findById(obj.getRoleId());
		//获取所有权限
		Long[] actionsId=getPojo().getActions();
		Set actionSet=new HashSet();
		if(actionsId!=null){
			for(Long actionId:actionsId){
			    ActionInfo action=new ActionInfo();
			    action.setActionId(actionId);
			    actionSet.add(action);
			}
		}
		// 保存角色
		role.setActions(actionSet);
		this.roleManageService.alter(role);
		return BaseAction.RELOAD;
	}
	/**
	 * 去为角色分配菜单
	 * @return
	 */
	public String toSetMenuByRole(){
		//获取角色
		Role role=this.roleManageService.findById(obj.getRoleId());
		//获取此角色的所有菜单与权限
		List<Menu> menuList=menuManageService.findAllMenus();
		/*Set<Menu> menuList=role.getMenus();
		Set<ActionInfo> actionList=role.getActions();
		if(menuList!=null){//剔除掉非当前角色的权限
			Iterator<ActionInfo> ia=null;
			ActionInfo a=null;
			for(Menu m:menuList){
				if(m.getActions()!=null){
				    ia=m.getActions().iterator();
					if(ia!=null){
						while(ia.hasNext()){
							a=ia.next();
							if(!actionList.contains(a)){//如果不是当前角色的权限，就剔除掉
								ia.remove();
							}
						}
					}
				}
			}
		}*/
		this.setRequestAttribute("role", role);
		this.setRequestAttribute("menuList", menuList);
		return "toSetMenu";
	}
	/**
	 * 为角色分配菜单
	 */
	public String setMenuByRole() throws Exception{
		//获取角色
		Role role=this.roleManageService.findById(obj.getRoleId());
		//获取所有权限
		String[] menusId=getPojo().getMenus();
		Long[] actionsId=getPojo().getActions();
		Set<Menu> menuSet=new HashSet();
		Set<ActionInfo> actionSet=new HashSet();
		if(menusId!=null){
			for(String menuId:menusId){
				Menu menu=new Menu();
				menu.setMenuId(menuId);
			    menuSet.add(menu);
			}
		}
		if(actionsId!=null){
			for(Long actionId:actionsId){
				ActionInfo action=new ActionInfo();
				action.setActionId(actionId);
				actionSet.add(action);
			}
		}
		// 保存角色
		role.setMenus(menuSet);
		role.setActions(actionSet);
		this.roleManageService.alter(role);
		//return setActionByRole();//BaseAction.RELOAD;
		writeJsonReturn("保存成功！",role);
		return NONE;
	}
	/**
	 * 更新角色缓存(需重新登录)
	 * @return
	 * @throws Exception
	 */
	public String updateCacheByRole() throws Exception{
		String roleids=getParameter("roleids");
		if(roleids!=null){
			getPojo().setRoles(roleids.split(","));
		}
		if(pojo.getRoles()!=null&&pojo.getRoles().length>0){
			for(String id:pojo.getRoles()){
				roleManageService.refreshRoleCache(id.trim(),osCacheManage);
			}
			Struts2Utils.renderText("1");
		}
		return null;
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
