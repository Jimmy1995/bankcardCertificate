package com.common.frame.service.imp;

import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.common.base.dao.ICommonDAO;
import com.common.base.service.CommonService;
import com.common.frame.model.Log;
import com.common.frame.service.ILogManageService;
import com.common.web.page.Page;

@Service
public class LogManageService extends CommonService<Log> implements
		ILogManageService {
	private static final long serialVersionUID = 4857070713829469783L;
	@Resource(name = "logDAO")
	private ICommonDAO<Log> logDAO;

	public Page findLogByPage(Log obj, Map paramMap) {
		DetachedCriteria query = DetachedCriteria.forClass(Log.class, "log");
		if (obj != null) {
			if (obj.getUserName() != null&&!obj.getUserName().equals(""))
				query.add(Restrictions.like("userName", obj.getUserName(),MatchMode.ANYWHERE));
			if (obj.getLogLevel() != null&&!obj.getLogLevel().equals(""))
				query.add(Restrictions.like("logLevel", obj.getLogLevel(),MatchMode.ANYWHERE));
			if (obj.getLocation() != null&&!obj.getLocation().equals(""))
				query.add(Restrictions.like("location", obj.getLocation(),MatchMode.ANYWHERE));
			if (obj.getMessage() != null&&!obj.getMessage().equals(""))
				query.add(Restrictions.like("message", obj.getMessage(),MatchMode.ANYWHERE));
		}
		return logDAO.queryPage(query);
	}

	@Override
	protected ICommonDAO<Log> getDAO() {
		// TODO Auto-generated method stub
		return null;
	}
}
