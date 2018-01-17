package com.common.frame.service.imp;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.common.base.dao.ICommonDAO;
import com.common.base.service.CommonService;
import com.common.frame.model.ActionInfo;
import com.common.frame.service.IActionManageService;
import com.common.web.page.Page;

/**
 * @className:ActionManageService.java
 * @classDescription:动作权限管理类
 * @author:longzy
 * @createTime:2010-7-8
 */
@Service
public class ActionManageService extends CommonService<ActionInfo> implements IActionManageService{
	private static final long serialVersionUID = 1140136826505248284L;
	@Resource(name="actionDAO")
	private  ICommonDAO<ActionInfo> actionDAO;
	
	
	//====方法定义区====//
	/**
	 * 分页查询
	 * @param 
	 * @param 
	 * @return page
	 */
	public Page findAllActions(ActionInfo obj){
		DetachedCriteria query=DetachedCriteria.forClass(ActionInfo.class,"action");
		if(obj!=null){
			if(obj.getActionName()!=null&&!obj.getActionName().equals("")){
				query.add(Restrictions.like("actionName",obj.getActionName(),MatchMode.ANYWHERE));
			}if(obj.getMenu().getMenuId()!=null&&!obj.getMenu().getMenuId().equals("")){
				query.add(Restrictions.eq("menu.menuId", obj.getMenu().getMenuId()));
			}
		}
		return actionDAO.queryPage(query);
	}
	

	
	
	
	
	//==========//	

	@Override
	protected ICommonDAO<ActionInfo> getDAO() {
		return actionDAO;
	}

	/**
	 * @return the actionDAO
	 */
	public ICommonDAO<ActionInfo> getActionDAO() {
		return actionDAO;
	}
	/**
	 * @param actionDAO the actionDAO to set
	 */
	public void setActionDAO(ICommonDAO<ActionInfo> actionDAO) {
		this.actionDAO = actionDAO;
	}


}
