package com.common.frame.service;

import java.util.List;
import java.util.Map;

import com.common.base.service.ICommonService;
import com.common.frame.model.Group;
import com.common.web.page.Page;

public interface IGroupManageService extends ICommonService<Group>{
	/**
	 * 分页查询
	 * @param 
	 * @param 
	 * @return page
	 */
	public Page findActionsByPage(Group group,Map param);
	
	public List<Group> findAllObject();
	
	public List<Group> findOneTree(String id);
	
	public Boolean checkCode(String code);
}
