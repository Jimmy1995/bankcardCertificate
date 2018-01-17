package com.common.frame.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.common.base.service.ICommonService;
import com.common.frame.model.UserInfo;
import com.common.web.cache.OSCacheManage;
import com.common.web.page.Page;

/**
 * @className:IUserManageService.java
 * @classDescription:用户管理接口
 * @author:longzy
 * @createTime:2010-7-8
 */
public interface IUserManageService extends ICommonService<UserInfo>{
	/**
	 * 根据用户名密码查找对象
	 */
	public UserInfo login(String userName,String password,String type) throws Exception;
	/**
	 * 分页查询
	 * @param 
	 * @param 
	 * @return page
	 */
	public Page findUsersByPage(UserInfo user,Map param);
	/**
	 * 检测用户名是否存在
	 * @param userName
	 * @return
	 */
	public boolean checkUserName(String userName);
	
	/**
	 * 查询所有的用户
	 * @return
	 */
	public List<UserInfo> findAllUsers();
	/**
	 * 保证一个账号只能登录一次
	 * @param user
	 */
	public void keepOneUserOnce(UserInfo user,ServletContext application);
	/**
	 * 保存在线的用户
	 */
	public void memoryOnLineUser(ServletContext application,String userName,HttpSession session);
	
	public void memoryRoleCache(OSCacheManage osCacheManage,HttpSession session,UserInfo user);
	/**
	 * 检查登录成功的用户是否在userinfo表中，如果不在，就在表中写入一条记录
	 */
	public void saveUserWhenLogin(UserInfo user);
	
	public void updateToForbid(Long userid,String flag)throws Exception;
}
