package com.common.frame.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.common.base.BaseAction;
import com.common.frame.model.Group;
import com.common.frame.model.Role;
import com.common.frame.model.UserInfo;
import com.common.frame.service.IGroupManageService;
import com.common.frame.service.ILoginTransformService;
import com.common.frame.service.IRoleManageService;
import com.common.frame.service.IUserManageService;
import com.common.frame.vo.OtherPojo;
import com.common.frame.vo.ResultVO;
import com.common.util.DateUtil;
import com.common.util.MD5;
import com.common.util.Struts2Utils;
import com.common.util.WebUtils;
import com.common.util.json.JsonUtils;
import com.common.web.MySessionContext;
import com.common.web.cache.OSCacheManage;




/**
 * @className:UserManageAction.java
 * @classDescription:用户管理Action
 * @author:longzy
 * @createTime:2010-7-8
 */
@Namespace("/manage")
/**@InterceptorRefs({//表示save方法不能重复提交（includeMethods表示哪些方法参与拦截器，excludeMethods表示哪些方法不用参与拦截器）
	@InterceptorRef(value="tokenSession",params={"includeMethods","save"}),
	@InterceptorRef("frame-stack")
	})**/
@Results({ 
	/*@Result(name = "input", location = "/manage/index/index.jsp"),*/
	@Result(name = "success", location = "/manage/user/userManage.jsp"),
	@Result(name = BaseAction.RELOAD, location = "/manage/user-manage.action", type = "redirect"),
	@Result(name = "login", location = "/manage/index/index.jsp"),
	@Result(name = "alterUser", location = "/manage/user/alterUser.jsp"), 
	@Result(name = "toSetRole", location = "/manage/user/setRole.jsp"),
	@Result(name = "listUsersByRole",location = "/manage/role/setUser.jsp"),
	@Result(name = "setRoleByUsers",location = "/manage/role-manage.action", type = "redirect"),
	@Result(name = "listUserByGroup", location = "/manage/group/listUserByGroup.jsp"),
	@Result(name = "noInThisGroup", location = "/manage/group/listUserNoGroup.jsp"),
	@Result(name = "error", location = "/manage/login.jsp") })
	@SuppressWarnings("unchecked")
public class UserManageAction extends BaseAction<UserInfo>{
	private static final long serialVersionUID = -3455214533697242385L;
	private OtherPojo pojo;//myTest1
	Log log=LogFactory.getLog("appLogger");
	@Autowired
	protected IGroupManageService groupManageService;
	@Autowired
	protected IUserManageService userManageService;
	@Autowired
	protected IRoleManageService roleManageService;
	@Autowired
	private List<ILoginTransformService> servicelist;
	// 申明缓存对象
	protected OSCacheManage osCacheManage = OSCacheManage.getInstance();
	//@Resource(name="JBPMHandler")
	//protected JBPMHandler jbpmUtil;//jbpm工作流工具类
	/**
	 * 登录
	 */
	/*@Validations(requiredStrings = {
			@RequiredStringValidator(type = ValidatorType.SIMPLE, trim = true, fieldName = "obj.userName", message = "请输入书籍名称. *")
	})*/
	public String login() throws Exception{
		ResultVO rvo=new ResultVO();
		ServletContext context = ServletActionContext.getServletContext();
		for(ILoginTransformService transformservice:servicelist){//登录前处理（）
			if(transformservice.executeTranform(request,obj,rvo)){
				break;
			}
		}
		if(StringUtils.isEmpty(rvo.getLogin())){
			rvo=new ResultVO("/manage/index/index.jsp","/manage/login.jsp");
		}
		if(obj.getUserName()==null&&this.getSessionAttribute("userMenuMap")!=null){//判断是否已经登录，登录了就直接跳到主页（同一浏览器未关闭有效）
			context.getRequestDispatcher(rvo.getLogin()).forward(request, response);
			return NONE;
		}
		UserInfo user=this.userManageService.login(obj.getUserName(),obj.getUserPassword(),obj.getUserkind());
		if(user!=null){
			userManageService.saveUserWhenLogin(user);//检查登录成功的用户是否在userinfo表中，如果不在，就在表中写入一条记录
			HttpSession session=ServletActionContext.getRequest().getSession();
			MySessionContext.getInstance().AddSession(session);//把当前session加入到上下文保存
			userManageService.keepOneUserOnce(user,context);//保证一个账号只能登录一次
			if(user.getName()!=null)
			MDC.put("name",user.getName());
			MDC.put("userName",user.getUserName());
			MDC.put("ip",WebUtils.getIP(getRequest()));
			MDC.put("place", "");
			//log.info("登录系统，他属于"+user.getGroup().size()+"个部门");//这个还是有用的，为了对付懒加载，使用后，在webUtils中的getUser()中就可以取到group了，否则会报懒加载异常
			userManageService.memoryRoleCache(osCacheManage, this.getSession(), user);//保存登录的信息（包括角色，权限，菜单，数据字典）
			userManageService.memoryOnLineUser(context,obj.getUserName(),getSession());// 保存在线的用户
			context.getRequestDispatcher(rvo.getLogin()).forward(request, response);
			return NONE;
		}else{
			this.setRequestAttribute("message", "未启用或用户名、密码错误");
			context.getRequestDispatcher(rvo.getError()).forward(request, response);
			return NONE;
		}	
	}
	/**
	 * 查找所有的人员
	 */
	public String list()throws Exception{
		//String s=jbpmUtil.getIdentityService().findGroupsByUser(WebUtils.getUser().getId()).get(0).getName();
		//System.out.println(s);
		
		if(!WebUtils.isAJAXRequest(getRequest())){
			request.setAttribute("roleList",roleManageService.findAllRoles());
			return SUCCESS;
		}
		//查询所有权限，并放入会话
		Set roles=new HashSet();
		HashMap<String,Object> map=null;
		String roleid=request.getParameter("roleId");
		String online=request.getParameter("onlyOnline");
		if(roleid!=null&&!roleid.equals("")){//根据指定角色查询用户
			roles.add(new Role(roleid));
			obj.setRoles(roles);
		}
		
		if(WebUtils.getUser(request).getGroup()!=null&&WebUtils.getUser(request).getGroup().size()>0){//得到这个用户所在的部门，包括其管辖的子部门
			Set<Group> gset=WebUtils.getUser(request).getGroup(); 
			for(Group g:gset){
				List<Group> groups=groupManageService.findOneTree(g.getGroupId());
				if(groups!=null&&groups.size()>0){
					for(Group gs:groups){
						obj.getGroup().add(gs);
					}
				}
			}
		}
		
		if(online!=null&&online.equals("1")){//这儿的条件是查询在线的用户
			ServletContext application = ServletActionContext.getRequest().getSession().getServletContext();
			map=(HashMap<String,Object>)application.getAttribute("onlineUserList");
			List userNameList=new ArrayList();
			for( Map.Entry<String,Object> entry:map.entrySet()){
				userNameList.add(entry.getKey());
			}
			map.put("userNameList", userNameList);
		}
		//setPage(userManageService.findUsersByPage(obj,map));
		//request.setAttribute("roleList",roleManageService.findAllRoles());
		//request.setAttribute("roleid", roleid);
		//request.setAttribute("onlyOnline", online);
		SerializeFilter filter=new SimplePropertyPreFilter(UserInfo.class,"userId","name","userName","createTime");
		JsonUtils.setPropertyFilter(filter);
		writeJsonReturn("", userManageService.findUsersByPage(obj,map));
		return NONE;
	}
	/**
	 * 在用户列表页面踢用户下线
	 */
	public String offLine()throws Exception{
		String sessionId=request.getParameter("sessionId");
		if(sessionId!=null&&!sessionId.equals("")){
			MySessionContext.getInstance().getSession(sessionId).invalidate();
			log.info("==强行踢一用户下线==");
		}
		return RELOAD;
	}
	/**
	 * 删除用户
	 */
	public String delete() throws Exception {
		String userids=getParameter("userids");
		if(userids!=null){
			String[] us=userids.split(",");
			for(String id:us){
				obj=userManageService.findById(Long.parseLong(id.trim()));
				if("super".equals(obj.getCode())){//超级用户也不能删除
				}else if(!WebUtils.getUser(request).getUserId().equals(obj.getUserId())){//自己不准删除自己
					this.userManageService.deleteById(obj.getUserId());
				}
			}
		}
		Struts2Utils.renderText("1");
		return null;

	}
	
	/**
	 * 禁用/启用;用户
	 */
	public String forbid() throws Exception {
		String flag=obj.getFlag();//页面传过来的状态
		String userids=getParameter("userids");
		if(userids!=null){
			String[] us=userids.split(",");
			for(String id:us){
				userManageService.updateToForbid(Long.parseLong(id.trim()),flag);
			}
		}
		Struts2Utils.renderText("1");
		return null;
	}
	/**
	 * 去修改用户
	 */
	public String toAlter() throws Exception {
		obj=this.userManageService.findById(obj.getUserId());
		this.setRequestAttribute("userInfo", obj);	
		return "alterUser";
	}
	/**
	 * 修改用户
	 */
	public String alter() throws Exception {
		UserInfo user=this.userManageService.findById(obj.getUserId());
		user.setUserPassword(MD5.getInstance().createMD5(obj.getUserPassword()));
		user.setEmail(obj.getEmail());
		user.setQq(obj.getQq());
		user.setName(obj.getName());
		user.setSex(obj.getSex());
		this.userManageService.alter(user);
		writeJsonReturn("保存成功！",null);
		return NONE;
	}
	/**
	 * 保存用户
	 */
	public String save() throws Exception {
		obj.setUserPassword(MD5.getInstance().createMD5(obj.getUserPassword()));
		obj.setCreateTime(DateUtil.datetime());
		obj.setUserkind("0");
		obj.setGroup(WebUtils.getUser().getGroup());//给个默认的机构（当前登录用户的机构）
		this.userManageService.save(obj);
		writeJsonReturn("保存成功！",null);
		return NONE;
	}
	
	/**
	 * 支持使用Jquery.validate Ajax检验用户名是否重复.
	 */
	public String checkUserName() {
		boolean flag=this.userManageService.checkUserName(obj.getUserName());
		if (flag) {
			Struts2Utils.renderText("1");
		} else {
			Struts2Utils.renderText("0");
		}
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}
	/**
	 * 去为用户分配角色
	 * @return
	 */
	public String toSetRoleByUser(){
		//获取用户
		obj=this.userManageService.findById(obj.getUserId());
		//获取所有角色
		List<Role> roleList=this.roleManageService.findAllRoles();
		//this.setRequestAttribute("user", user);
		this.setRequestAttribute("roleList", roleList);
		return "toSetRole";
	}
	/**
	 * 为用户分配角色
	 */
	public String setRoleByUser(){
		//获取用户
		UserInfo user=this.userManageService.findById(obj.getUserId());
		//获取所有角色
		String[] rolesId=getPojo().getRoles();
		Set roleSet=new HashSet();
		if(rolesId!=null){
		for(String roleId:rolesId){
		    Role role=new Role();
		    role.setRoleId(roleId);
		    roleSet.add(role);
		}
		}
		// 保存角色
		user.setRoles(roleSet);
		this.userManageService.alter(user);
		writeJsonReturn("保存成功！",null);
		return NONE;
	}
	/**
	 * 根据机构或部门显示用户
	 * @return
	 * @throws Exception
	 */
	public String listUserByGroup()throws Exception{
		if(!WebUtils.isAJAXRequest(getRequest())){
			return "listUserByGroup";
		}
		String groupid=request.getParameter("groupid");
		Set group=new HashSet();
		if(groupid!=null&&!groupid.equals("")){//根据指定部门查询用户
			group.add(new Group(groupid));
			obj.setGroup(group);
		}
		SerializeFilter filter=new SimplePropertyPreFilter(UserInfo.class,"userId","name","userName","createTime");
		JsonUtils.setPropertyFilter(filter);
		writeJsonReturn(null,userManageService.findUsersByPage(obj,null));
		return NONE;
	}
	/**
	 * 去为部门或机构分配用户（这个用户不在当前的部门或机构中）
	 * @return
	 * @throws Exception
	 */
	public String listNoThisGroup()throws Exception{
		if(!WebUtils.isAJAXRequest(getRequest())){
			return "noInThisGroup";
		}
		String groupid=request.getParameter("groupid");
		Map map=new HashMap();
		List group=new ArrayList();
		group.add(groupid);
		map.put("noThisGroupId", group);
		SerializeFilter filter=new SimplePropertyPreFilter(UserInfo.class,"userId","name","userName","createTime");
		JsonUtils.setPropertyFilter(filter);
		writeJsonReturn(null,userManageService.findUsersByPage(obj,map));
		return NONE;
	}
	/**
	 * 为部门分配多个用户
	 */
	public String setGroupByUsers()throws Exception{
		Set<UserInfo> users=new HashSet<UserInfo>(); 
		String groupid=request.getParameter("groupid");
		Group group=groupManageService.findById(groupid);
		if(getPojo().getUserIds()!=null){
			for(long userid:getPojo().getUserIds()){
				users.add(new UserInfo(userid));
			}
		}
		users.addAll(group.getUser());
		group.setUser(users);
		groupManageService.alter(group);
		writeJsonReturn("移动成功", null);
		return NONE;
		//return listUserByGroup();
	}
	/**
	 * 某用户退出某部门
	 */
	public String outOfGroup()throws Exception{
		 
		String groupid=request.getParameter("groupid");
		Group group=groupManageService.findById(groupid);
		Set<UserInfo> users=group.getUser();
		for(UserInfo user:users){
			if(user.getUserId().equals(obj.getUserId())){
				users.remove(user);
				break;
			}
		}
		group.setUser(users);
		groupManageService.alter(group);
		writeJsonReturn("成功退出！", null);
		return NONE;
		//return listUserByGroup();
	}
	/**
	 * 去为角色分配用户
	 */
	public String listUsersByRole()throws Exception{
		Map param=new HashMap();
		String roleid=request.getParameter("roleid");
		String isThisRole=request.getParameter("isThisRole");
		param.put("roleid", roleid);
		param.put("isThisRole", isThisRole);
		Set roles=new HashSet();
		roles.add(roleid);
		setPage(userManageService.findUsersByPage(obj,param));
		request.setAttribute("roleid", roleid);
		return "listUsersByRole";
	}
	/**
	 * 为角色分配多个用户
	 */
	public String setRoleByUsers() throws Exception{
		Set<UserInfo> users=new HashSet<UserInfo>(); 
		String roleid=request.getParameter("roleid");
		String isThisRole=request.getParameter("isThisRole");//如果这个为1表示要把这些用户从当前角色退出，为0表示要把这些用户添加到当前角色
		Role role=roleManageService.findById(roleid);
		if(getPojo().getUserIds()!=null){
			for(long userid:getPojo().getUserIds()){
				users.add(new UserInfo(userid));
			}
			if("0".equals(isThisRole)){//添加到角色
				role.getUsers().addAll(users);
			}else if("1".equals(isThisRole)){//退出角色
				role.getUsers().removeAll(users);
			}
			
		}
		roleManageService.alter(role);
		//roleManageService.refreshRoleCache(roleid,osCacheManage);//刷新缓存
		return listUsersByRole();//"setRoleByUsers";
	}
	/**
	 * 退出登录
	 */
	public String loginOut()throws Exception{
		this.getSession().invalidate();
		return ERROR;
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
