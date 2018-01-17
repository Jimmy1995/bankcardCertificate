package com.common.frame.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.base.BaseAction;
import com.common.frame.model.Log;
import com.common.frame.service.ILogManageService;
import com.common.util.WebUtils;
@Namespace("/manage")
@Results({
	@Result(name = "success", location = "/manage/log/logManage.jsp")
})
public class LogManageAction extends BaseAction<Log> {
	private static final long serialVersionUID = 815962700759335566L;
	@Autowired
	private ILogManageService logManageService;
	public String alter() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() throws Exception {
		//setPage(logManageService.findLogByPage(obj, null));
		if(WebUtils.isAJAXRequest(getRequest())){
			writeJsonReturn("成功！", logManageService.findLogByPage(obj, null));
			return NONE;
		}else{
			return SUCCESS;
		}
	}


}
