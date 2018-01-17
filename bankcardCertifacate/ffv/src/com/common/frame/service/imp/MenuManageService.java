package com.common.frame.service.imp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.common.base.dao.ICommonDAO;
import com.common.base.service.CommonService;
import com.common.frame.model.Menu;
import com.common.frame.service.IMenuManageService;
import com.common.web.page.Page;


/**
 * @className:MenuManageService.java
 * @classDescription:菜单管理类
 * @author:longzy
 * @createTime:2010-7-8
 */
@Service
public class MenuManageService extends CommonService<Menu> implements IMenuManageService{
	private static final long serialVersionUID = -1782955411294382978L;
	@Resource(name="menuDAO")
	private  ICommonDAO<Menu> menuDAO;
	
	//====方法定义区====//
	/**
	 * 分页查询
	 * @param 
	 * @param 
	 * @return page
	 */
	public Page findAllMenus(Menu obj){
		DetachedCriteria query=DetachedCriteria.forClass(Menu.class,"menu");
		if(obj!=null){
			if(obj.getMenuName()!=null&&!obj.getMenuName().equals("")){
				query.add(Restrictions.like("menuName",obj.getMenuName(),MatchMode.ANYWHERE));
			}if(obj.getMenuUrl()!=null&&!obj.getMenuUrl().equals("")){
				query.add(Restrictions.like("menuUrl", obj.getMenuUrl(),MatchMode.ANYWHERE));
			}if(obj.getParentId()!=null&&!obj.getParentId().equals("")){
				query.add(Restrictions.eq("parentId", obj.getParentId()));
			}
		}
		return menuDAO.queryPage(query);
	}
	/**
	 * 查询所有的菜单
	 * @return
	 */
	public List<Menu> findAllMenus(){
		String hql="from Menu order by orders";
		return this.menuDAO.findList(hql);
	}

	
	
	
	
	//==========//	


	@Override
	protected ICommonDAO<Menu> getDAO() {
		return menuDAO;
	}

	/**
	 * @return the menuDAO
	 */
	public ICommonDAO<Menu> getMenuDAO() {
		return menuDAO;
	}

	/**
	 * @param menuDAO the menuDAO to set
	 */
	public void setMenuDAO(ICommonDAO<Menu> menuDAO) {
		this.menuDAO = menuDAO;
	}

	/**
	 * 根据一个节点ID（以这个节点为根）查询一颗除了第一个节点外的菜单树
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> findOneTreeNoFirst(Serializable id) {
		Map map=new HashMap();
		map.put("fid", id);
		List<Menu> list=menuDAO.findListOfMap("from Menu where parentId=:fid", map);
		List<Menu> list1=new ArrayList<Menu>();
		if(list !=null){
			for(Menu m:list){
				list1=this.findOneTreeNoFirst(m.getMenuId());
			}
			if(list1!=null){
				list.addAll(list1);
			}
		}
		return list;
	}
	/**
	 * 根据一个节点ID（以这个节点为根）查询一颗完整的菜单树
	 */
	public List<Menu> findOneTree(String id) {
		List<Menu> mm=new ArrayList<Menu>();
		Menu menu=menuDAO.findById(id);
		List<Menu> mms=findOneTreeNoFirst(id);
		if(mms!=null){
			mm.addAll(mms);
		}
		if(menu!=null){
			mm.add(menu);
		}
		return mm;
	}

}
