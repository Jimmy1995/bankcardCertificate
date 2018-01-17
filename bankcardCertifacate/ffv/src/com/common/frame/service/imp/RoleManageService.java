package com.common.frame.service.imp;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.common.base.dao.ICommonDAO;
import com.common.base.service.CommonService;
import com.common.frame.model.Role;
import com.common.frame.service.IRoleManageService;
import com.common.web.cache.CacheUtil;
import com.common.web.cache.OSCacheManage;
import com.common.web.page.Page;



/**
 * @className:RoleManageService.java
 * @classDescription:角色管理类
 * @author:longzy
 * @createTime:2010-7-8
 */
@Service
public class RoleManageService extends CommonService<Role> implements IRoleManageService{
	private static final long serialVersionUID = -7710552460104800827L;
	@Resource(name="roleDAO")
	private  ICommonDAO<Role> roleDAO;
	
	//====方法定义区====//
	/**
	 * 分页查询
	 * @param 
	 * @param 
	 * @return page
	 */
	public Page findAllRoles(Role obj){
		DetachedCriteria query=DetachedCriteria.forClass(Role.class,"role");
		if(obj!=null){
			if(obj.getRoleName()!=null&&!obj.getRoleName().equals("")){
				query.add(Restrictions.like("roleName",obj.getRoleName(),MatchMode.ANYWHERE));
			}
		}
		return roleDAO.queryPage(query);
	}
	/**
	 * 查询所有的角色
	 * @param page
	 * @param pfList 查询条件
	 * @return
	 */
	public List<Role> findAllRoles(){
		String hql="from Role order by orders asc";
		return this.roleDAO.findList(hql);
	}
	
	//==========//
	@Override
	protected ICommonDAO<Role> getDAO() {
		return roleDAO;
	}

	/**
	 * @return the roleDAO
	 */
	public ICommonDAO<Role> getRoleDAO() {
		return roleDAO;
	}

	/**
	 * @param roleDAO the roleDAO to set
	 */
	public void setRoleDAO(ICommonDAO<Role> roleDAO) {
		this.roleDAO = roleDAO;
	}
	/**
	 * 刷新缓存中的角色
	 */
	public void refreshRoleCache(Serializable id,OSCacheManage osCacheManage) {
		//更新缓存
		osCacheManage.clearCache("role_"+id);
		Role role=this.findById(id);
		CacheUtil.refreshRoleCache(osCacheManage, role);
	}

}
