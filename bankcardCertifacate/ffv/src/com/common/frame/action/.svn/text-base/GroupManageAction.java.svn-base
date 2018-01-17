package com.common.frame.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.base.BaseAction;
import com.common.base.DBHelper;
import com.common.frame.model.Group;
import com.common.frame.service.IGroupManageService;
import com.common.util.Struts2Utils;
import com.common.util.WebUtils;
@Namespace("/manage")
@Results({ 
	@Result(name = "success", location = "/manage/group/groupManage.jsp"),
	@Result(name = BaseAction.RELOAD, location = "/manage/user-manage!listUserByGroup.action", type = "redirect"),
	@Result(name = "toAlterGroup", location = "/manage/group/alterGroup.jsp"), 
	@Result(name = "error", location = "/manage/login.jsp")})
public class GroupManageAction extends BaseAction<Group>{
	private static final long serialVersionUID = 3360374632468917492L;
	@Autowired
	protected IGroupManageService groupManageService;
	@Override
	public String list() throws Exception {
		setPage(groupManageService.findActionsByPage(obj,null));
		return SUCCESS;
	}
	/**
	 * 生成机构管理的树
	 * @return
	 */
	public String queryAllGroup(){
		List<Group> list=new ArrayList<Group>();
		Set<Group> groups=WebUtils.getUser(request).getGroup();
		if(groups.size()<1){//如果是没有分配机构部门的
			list=groupManageService.findAllObject();
		}else{//如果这个用户分配了部门，那就只能管到这个机构或部门下的
			for(Group group:groups){//可能在多个部门
				list.addAll(groupManageService.findOneTree(group.getGroupId()));
			}
		}
		StringBuffer s=new StringBuffer();
		for(Group group:list){
			s.append("zNodes.push({id:\""+group.getGroupId()+"\",pId:\""+group.getPid()+"\",name:\""+group.getGroupName()+"\",url:\""+getRoot()+"/manage/user-manage!listUserByGroup.action?groupid="+group.getGroupId()+"\",target:\"right\"});");
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
	public String alter() throws Exception {
		Group group=groupManageService.findById(obj.getGroupId());
		group.setType(obj.getType());
		group.setGroupName(obj.getGroupName());
		group.setZipCode(obj.getZipCode());
		group.setGroupCode(obj.getGroupCode());
		group.setContact(obj.getContact());
		group.setPhone(obj.getPhone());
		group.setAddress(obj.getAddress());
		groupManageService.alter(group);
		writeJsonReturn("保存成功！",null);
		return NONE;
	}
	
	public String toAlter() throws Exception {
		obj=groupManageService.findById(obj.getGroupId());
		if(obj==null){
			return BaseAction.RELOAD;
		}else{
			return "toAlterGroup";
		}
	}
	
	/**
	 * 在机构树上删除机构,把子树了也删除了
	 * @param id
	 * @return
	 */
	public String delete() throws Exception {
		List<Group> list=groupManageService.findOneTree(obj.getGroupId());
		for(Group g:list){
			groupManageService.delete(g);
		}
		writeJsonReturn("删除成功！",null);
		return null;
	}

	public String save() throws Exception {
		obj.setGroupId(DBHelper.getMaxId("sys_group"));
		groupManageService.save(obj);
		writeJsonReturn("保存成功！",null);
		return NONE;
	}
	
	
	/**
	 * 支持使用Jquery.validate Ajax检验code是否重复.
	 */
	public String check() {
		boolean flag=this.groupManageService.checkCode(obj.getGroupCode());
		if (flag) {
			Struts2Utils.renderText("1");
		} else {
			Struts2Utils.renderText("0");
		}
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}
}
