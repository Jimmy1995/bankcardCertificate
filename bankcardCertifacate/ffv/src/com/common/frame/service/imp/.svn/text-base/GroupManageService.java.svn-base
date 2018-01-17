package com.common.frame.service.imp;

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
import com.common.frame.model.Group;
import com.common.frame.service.IGroupManageService;
import com.common.web.page.Page;
@Service
public class GroupManageService extends CommonService<Group> implements IGroupManageService {
	private static final long serialVersionUID = 7632600748658646183L;
	@Resource(name="groupDAO")
	private  ICommonDAO<Group> groupDAO;
	public Page findActionsByPage(Group obj,Map param) {
		DetachedCriteria query=DetachedCriteria.forClass(Group.class,"group");
		if(obj!=null){
			if(obj.getGroupName()!=null&&!obj.getGroupName().equals("")){
				query.add(Restrictions.like("groupName",obj.getGroupName(),MatchMode.ANYWHERE));
			}if(obj.getPid()!=null&&!obj.getPid().equals("")){
				query.add(Restrictions.eq("fid", obj.getPid()));
			}if(obj.getGroupCode()!=null&&!obj.getGroupCode().equals("")){
				query.add(Restrictions.eq("groupCode", obj.getGroupCode()));
			}
		}
		return groupDAO.queryPage(query);
	}
	public List<Group> findAllObject(){
		return groupDAO.findAllObject();
	}
	public ICommonDAO<Group> getGroupDAO() {
		return groupDAO;
	}
	public void setGroupDAO(ICommonDAO<Group> groupDAO) {
		this.groupDAO = groupDAO;
	}
	@Override
	protected ICommonDAO<Group> getDAO() {
		// TODO Auto-generated method stub
		return groupDAO;
	}
	/**
	 * 根据一个节点ID（以这个节点为根）查询它的子树
	 */
	@SuppressWarnings("unchecked")
	public List<Group> findOneTreeNoFirst(String id) {
		Map map=new HashMap();
		map.put("pid", id);
		List<Group> list=groupDAO.findListOfMap("from Group where pid=:pid", map);
		List<Group> list1=new ArrayList<Group>();
		if(list !=null){
			for(Group g:list){
				list1=this.findOneTreeNoFirst(g.getGroupId());
			}
			if(list1!=null){
				list.addAll(list1);
			}
		}
		return list;
	}
	/**
	 * 根据一个节点ID（以这个节点为根）查询一颗完整的机构树
	 */
	public List<Group> findOneTree(String id) {
		List<Group> mm=new ArrayList<Group>();
		Group group=groupDAO.findById(id);
		List<Group> mms=findOneTreeNoFirst(id);
		if(mms!=null){
			mm.addAll(mms);
		}
		if(group!=null){
			mm.add(group);
		}
		return mm;
	}
	public Boolean checkCode(String code) {
		boolean flag=true;
		String hql="from Group where groupCode=:groupCode";
		Map map=new HashMap();
		map.put("groupCode", code);
		
		Group group= this.groupDAO.findOfMap(hql, map);
		if(null==group){
			flag=false;
		}
		return flag;
	}
}
