package com.common.frame.service.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.common.base.dao.ICommonDAO;
import com.common.base.service.CommonService;
import com.common.frame.model.QuartzJob;
import com.common.frame.service.IQuartzJobService;
import com.common.web.page.Page;
import com.common.web.quartz.QuartzManager;
@Service
public class QuartzJobService extends CommonService<QuartzJob> implements IQuartzJobService {
	private static final long serialVersionUID = -8967250336011264083L;
	@Resource(name="quartzJobDAO")
	private  ICommonDAO<QuartzJob> quartzJobDAO;
	Log log=LogFactory.getLog("appLogger");
	public Page queryJobByPage(QuartzJob obj, Map param) {
		DetachedCriteria query=DetachedCriteria.forClass(QuartzJob.class,"job");
		if(obj!=null){
			if(!StringUtils.isEmpty(obj.getJobName())){
				query.add(Restrictions.like("jobName", obj.getJobName(),MatchMode.ANYWHERE));
			}
		}
		Page page = quartzJobDAO.queryPage(query);
		List<QuartzJob> jobs=page.getResult();
		for(QuartzJob job:jobs){
			if(QuartzManager.getJobDetail(job.getId(), job.getJobGroup())!=null){
				job.setJobStatus("0");
			}else{
				job.setJobStatus("1");
			}
		}
		return page;
	}
	/**@Async
	public void runOnce(QuartzJob obj){
		try {
			obj=this.findById(obj.getId());
	    	Class classz=Class.forName(obj.getStateFulljobExecuteClass());
			Method method = classz.getDeclaredMethod("executeInternal",new Class[]{JobExecutionContext.class}); 
			method.setAccessible(true);
			method.invoke(classz.newInstance(),new Object[]{null});
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}**/
	@Override
	protected ICommonDAO<QuartzJob> getDAO() {
		return quartzJobDAO;
	}
}