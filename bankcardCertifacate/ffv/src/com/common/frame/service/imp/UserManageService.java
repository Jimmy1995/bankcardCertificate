package com.common.frame.service.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.criteria.JoinType;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.common.base.DAOHelper;
import com.common.base.DBHelper;
import com.common.base.dao.ICommonDAO;
import com.common.base.service.CommonService;
import com.common.frame.model.DataDictionary;
import com.common.frame.model.Group;
import com.common.frame.model.Menu;
import com.common.frame.model.Role;
import com.common.frame.model.UserInfo;
import com.common.frame.model.UserKind;
import com.common.frame.service.IUserManageService;
import com.common.frame.vo.RoleCache;
import com.common.util.MD5;
import com.common.web.MySessionContext;
import com.common.web.cache.CacheUtil;
import com.common.web.cache.OSCacheManage;
import com.common.web.page.Page;

/**
 * @className:UserManageService.java
 * @classDescription:用户管理类
 * @author:longzy
 * @createTime:2010-7-8
 */
@Service
public class UserManageService extends CommonService<UserInfo> implements IUserManageService{
	private static final long serialVersionUID = -2332216530879310336L;
	@Resource(name="userInfoDAO")
	private  ICommonDAO<UserInfo> userDAO;
	Log log=LogFactory.getLog("appLogger");
		
	//====方法定义区====//
	
	/**
	 * 根据用户名密码查找对象
	 */
	public UserInfo login(String userName,String password,String type) throws Exception{
		UserKind uk=CacheUtil.getUserKind(type);
		if(null==uk){//不存在此用户类型
			return null;
		}
		Map param=new HashMap();
		param.put("userName", userName);
		Map usermap=DBHelper.queryForMap(uk.getLogin_sql(), param);
		if(null==usermap){
			//不存在此用户
			return null;
		}
		UserInfo u=new UserInfo();
		if(null!=usermap.get("code"))
		u.setCode(usermap.get("code")+"");
		if(null!=usermap.get("email"))
		u.setEmail(usermap.get("email")+"");
		if(null!=usermap.get("name"))
		u.setName(usermap.get("name")+"");
		if(null!=usermap.get("flag"))
		u.setFlag(usermap.get("flag")+"");
		if(null!=usermap.get("userId")){
			u.setUserId(Long.parseLong(usermap.get("userId")+""));
		}
		if(null!=usermap.get("sex"))
		u.setSex(usermap.get("sex")+"");
		if(null!=usermap.get("userkind"))
		u.setUserkind(usermap.get("userkind")+"");
		if(null!=usermap.get("userName"))
		u.setUserName(usermap.get("userName")+"");
		if(null!=usermap.get("userPassword"))
		u.setUserPassword(usermap.get("userPassword")+"");
		//usermap.remove("createTime");
		//BeanUtils.populate(u, usermap);//之所以不用这个方法，是因为oracle中的字段忽略了大小写，而这个方法大小写要一样才行（虽然springjdbc查询出的usermap大小写也是忽略的）
		u.setAttrMap(usermap);
		{//校验密码
			if(StringUtils.isEmpty(u.getUserPassword())){
				if(u.get("default_password")!=null){
					u.setUserPassword(MD5.getInstance().createMD5(u.get("default_password")+""));
				}
			}
			if(!password.equals(u.getUserPassword())){
				//密码错误
				return null;
			}
		}
		Set groupset=new HashSet();
		Set roleset=new HashSet();
		if(u.getUserId()!=null&&u.getUserId()>0){//非首次登录
			List grouplist=DAOHelper.findList("select g from Group g join g.user u where u.userId='"+u.getUserId()+"'");//Groups
			List rolelist=DAOHelper.findList("select r from Role r join r.users u where u.userId='"+u.getUserId()+"'");//role
			groupset.addAll(grouplist);
			roleset.addAll(rolelist);
		}else{//首次登录
			if(!StringUtils.isEmpty(uk.getDefault_groupid())){
				groupset.add(DAOHelper.findById(Group.class, uk.getDefault_groupid()));
			}
			if(!StringUtils.isEmpty(uk.getDefault_roleid())){
				roleset.add(DAOHelper.findById(Role.class, uk.getDefault_roleid()));
			}
		}
		u.setRoles(roleset);
		u.setGroup(groupset);
		
		//String hql="from UserInfo where userName=:userName and userPassword=:userPassword and flag='1'";
		//Map map=new HashMap();
		//map.put("userName", userName);
		//map.put("userPassword", password);
		//return this.userDAO.findOfMap(hql, map);
		return u;
	}
	/**
	 * 分页查询
	 * @param 
	 * @param 
	 * @return page
	 */
	public Page findUsersByPage(UserInfo obj,Map param){
		DetachedCriteria query=DetachedCriteria.forClass(UserInfo.class,"user");
		if(obj!=null){//实体不为空
			if(obj.getUserName()!=null&&!obj.getUserName().equals("")){
				query.add(Restrictions.like("userName",obj.getUserName(),MatchMode.ANYWHERE));
			}if(obj.getRoles()!=null&&obj.getRoles().size()>0){//根据角色ID查询
				Set setro=new HashSet();
				for(Role ro:obj.getRoles()){
					setro.add(ro.getRoleId());
				}
				query.createAlias("roles", "role").add( Restrictions.in("role.roleId",setro));
			}if(obj.getGroup()!=null&&obj.getGroup().size()>0){//根据机构ID查询用户
				Set setgr=new HashSet();
				for(Group gr:obj.getGroup()){
					setgr.add(gr.getGroupId());
				}
				DetachedCriteria subQuerygro=DetachedCriteria.forClass(UserInfo.class,"user");//子查询
				subQuerygro.setProjection(Property.forName("userId"));//与一面这句配合相当于select id from user
				subQuerygro.createAlias("group", "group",JoinType.LEFT.ordinal());
				subQuerygro.add(Restrictions.in("group.groupId", setgr));
				query.add(Property.forName("userId").in(subQuerygro));
			}
		}
		if(param!=null&&param.size()>0){//参数不为空
			List nameList=(param.get("userNameList")==null?null:(List)param.get("userNameList"));
			if(nameList!=null&&nameList.size()>0){//根据登录名查询用户
				query.add(Restrictions.in("userName", nameList));
			}
			List noThisGroupId=(param.get("noThisGroupId")==null?null:(List)param.get("noThisGroupId"));
			if(noThisGroupId!=null){//查询不在当前机构或部门中的用户
				/**query.createAlias("group", "g",JoinType.LEFT.ordinal());//JoinType.LEFT.ordinal()左连接
				query.add(Restrictions.or(Restrictions.isNull("g.id"), 
										  Restrictions.not(Restrictions.in("g.id", noThisGroupId))));**/
				DetachedCriteria subQuery=DetachedCriteria.forClass(UserInfo.class,"user");//子查询
				subQuery.setProjection(Property.forName("userId"));//与一面这句配合相当于select id from user
				subQuery.createAlias("group", "group",JoinType.LEFT.ordinal());
				subQuery.add(Restrictions.in("group.groupId", noThisGroupId));
				query.add(Property.forName("userId").notIn(subQuery));
			}
			String roleid=(param.get("roleid")==null?null:(String)param.get("roleid"));//角色ID
			String isThisRole=(param.get("isThisRole")==null?null:(String)param.get("isThisRole"));//是否只查询这个角色的用户;0表示只查询不是这个角色的，1表示只查询这个角色的
			if("0".equals(isThisRole)){//只查询不是这个角色的
				//query.createAlias("roles", "role40").add( Restrictions.ne("role40.id", roleid));
				DetachedCriteria subQuery40=DetachedCriteria.forClass(UserInfo.class,"user");//子查询
				subQuery40.setProjection(Property.forName("userId"));//与一面这句配合相当于select id from user
			    subQuery40.createAlias("roles", "roles",JoinType.LEFT.ordinal());
				subQuery40.add(Restrictions.eq("roles.roleId", roleid));
				query.add(Property.forName("userId").notIn(subQuery40));
			}else if("1".equals(isThisRole)){//只查询是这个角色的
				query.createAlias("roles", "roles").add( Restrictions.like("roles.roleId", roleid));
			}
		}
		Page page=userDAO.queryPage(query);
		return page;
	}
	/**
	 * 查询所有的用户
	 * @return
	 */
	public List<UserInfo> findAllUsers(){
		String hql="from UserInfo";
		return this.userDAO.findList(hql);
	}
	/**
	 * 检测用户名是否存在
	 * @param userName
	 * @return
	 */
	public boolean checkUserName(String userName){
		boolean flag=true;
		String hql="from UserInfo where userName=:userName";
		Map map=new HashMap();
		map.put("userName", userName);
		
		UserInfo user= this.userDAO.findOfMap(hql, map);
		if(null==user){
			flag=false;
		}
		return flag;
	}
	
	
	/**
	 * 保证一个账号只能登录一次
	 * @param user
	 */
	public void keepOneUserOnce(UserInfo user,ServletContext application){
		{//踢掉在其它地方登录的相同账号。。。让一个账号只能在一个地方登录
			Map online=(Map)application.getAttribute("onlineUserList");
			
			if(online!=null){
				if(online.get(user.getUserName())!=null){//如果这个账号已经在其他地方登录过，那就把它踢了重新登录
					HttpSession oldSession=MySessionContext.getInstance().getSession(online.get(user.getUserName())+"");
					if(oldSession!=null){
						oldSession.invalidate();
						log.info("强行登录，踢掉另一个用"+user.getUserName()+"账号登录的用户");
					}
				}
			}
		}
	}
	
	/**
	 * 保存在线的用户
	 */
	@Async
	public void memoryOnLineUser(ServletContext application,String userName,HttpSession session){
    	{//用一个application保存登录用户。。。这样方便查询整个系统的在线用户
			Map onLineUser=new HashMap();
			if(application.getAttribute("onlineUserList")!=null){
				onLineUser=(HashMap)application.getAttribute("onlineUserList");
			}
			onLineUser.put(userName, session.getId());
			application.setAttribute("onlineUserList", onLineUser);
		}
    }
	/**
	 * 登录成功后的信息设置
	 */
	public void memoryRoleCache(OSCacheManage osCacheManage, HttpSession session,UserInfo user) {
		session.setAttribute("userAdmin", user);
		Map<String,String> userUrlMap = new HashMap<String,String>();//访问权限
		Map<String,Menu> userMenuMap=new LinkedHashMap<String,Menu>();//菜单权限
		Set<DataDictionary> userDicSet = new HashSet<DataDictionary>();//字典权限
		if(user.getRoles()!=null){
			List<Role> roles=sortRole(user.getRoles());
			for (Role role : roles) {
				// 如果缓存中没有，则将数据添加到缓存中
				if (null == osCacheManage.getCache("role_"+role.getRoleId())) {
					CacheUtil.refreshRoleCache(osCacheManage, role);
				}
				RoleCache roleCache =(RoleCache) osCacheManage.getCache("role_"+role.getRoleId());
				userMenuMap.putAll(roleCache.getMenuMap());//把每次从角色中遍历出来的菜单权限加到userMap中
				userUrlMap.putAll(roleCache.getUrlMap());
				userDicSet.addAll(roleCache.getDicSet());
			}
			session.setAttribute("userMenuMap", getOrder(userMenuMap));//菜单权限，防止osCache刷新了之后出现丢失
			session.setAttribute("userUrlMap", userUrlMap);//访问权限
			session.setAttribute("userDicSet", userDicSet);//字典权限
		}
	}
	/**
	 * @Description 将Role集合进行排序
	 * @param input
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Role> sortRole(Set<Role> input){
		   List<Role> role = new ArrayList<Role>();
		   role.addAll(input);
		   Collections.sort(role,new Comparator(){
		    public int compare(Object a, Object b) {
		     return ((Role)a).getOrders().compareTo(((Role)b).getOrders());  
		    }});
		   return role;
	}
	public LinkedHashMap<String, Menu> getOrder(Map<String, Menu>  map){
		List<Map.Entry<String, Menu>> menus =new ArrayList<Map.Entry<String, Menu>>(map.entrySet());
		//排序
		Collections.sort(menus, new Comparator<Map.Entry<String, Menu>>() {   
		    public int compare(Map.Entry<String, Menu> o1, Map.Entry<String, Menu> o2) {      
		    	Menu p1 = (Menu) o1.getValue();
		    	Menu p2 = (Menu) o2.getValue();
		    	if(p1.getMenuId().equals("0")){
		    		return 1;
		    	}else if(p1.getMenuId().equals(p2.getParentId())){
		    		return 1;
		    	}else
		    		return 0;
		    }
		}); 
/*转换成新map输出*/
		LinkedHashMap<String, Menu> newMap = new LinkedHashMap <String, Menu>();
		for(Map.Entry<String,Menu> entity : menus){
			newMap.put(entity.getKey(), entity.getValue());
		}
		return newMap;
	}
	//==========//
	
	/**
	 * 检查登录成功的用户是否在userinfo表中，如果不在，就在表中写入一条记录
	 */
	public void saveUserWhenLogin(UserInfo user){
		if(null==user.getUserId()||user.getUserId()<1){
			userDAO.save(user);
		}
	}
	
	
	@Override
	protected ICommonDAO<UserInfo> getDAO() {
		return userDAO;
	}
	public void updateToForbid(Long userid,String flag)throws Exception{
		UserInfo obj=this.findById(userid);
		if("super".equals(obj.getCode())){//超级用户不能禁用
		}else{
			obj.setFlag(flag);
			alter(obj);
		}
	}
}
