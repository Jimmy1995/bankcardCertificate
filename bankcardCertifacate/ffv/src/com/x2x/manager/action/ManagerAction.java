package com.x2x.manager.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.common.base.BaseAction;
import com.common.web.SpringContextHolder;
import com.x2x.manager.service.IManagerService;

/**
 * 回调服务接口
 * @author longzy
 */
@Namespace("/x2xpay")
 @Results({@Result(name = "go404", location = "/commons/404.jsp") })
public class ManagerAction extends BaseAction<Object>{
	private static final long serialVersionUID = -5673091093587008102L;
	@Override
	public String list() throws Exception {
		return null;
	}
	private IManagerService mangerService(){ return SpringContextHolder.getBean("managerSerive"); }
	@Action(value = "refreshCache")
    public String refreshCache() {
    	return mangerService().refreshCache(request, response);
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
