package com.common.frame.action;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.base.BaseAction;
import com.common.frame.model.QuartzJob;
import com.common.frame.service.IQuartzJobService;
import com.common.util.WebUtils;
import com.common.web.quartz.QuartzManager;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.inject.Inject;

@Namespace("/manage")
@Results( {
		@Result(name = "success", location = "/manage/quartzJob/quartzJobManage.jsp"),
		@Result(name = "toAlter", location = "/manage/quartzJob/alterQuartzJob.jsp"),
		@Result(name = BaseAction.RELOAD, location = "/manage/quartz-job-manage.action", type = "redirect"),
		@Result(name = "error", location = "/manage/login.jsp") })
public class QuartzJobManageAction extends BaseAction<QuartzJob> {
	private static final long serialVersionUID = 4632989810225954023L;
	@Autowired
	protected IQuartzJobService quartzJobService;
	private String[] jobs;
	private ObjectFactory objectFactory;

	@Override
	public String list() throws Exception {
		if(!WebUtils.isAJAXRequest(getRequest())){
			return SUCCESS;
		}
		writeJsonReturn(null,quartzJobService.queryJobByPage(obj, null));
		return NONE;
	}
	/**
	 * 立即执行一次
	 * @return
	 * @throws Exception
	 */
	public String runOnce() throws Exception{
		//quartzJobService.runOnce(obj);
		obj=quartzJobService.findById(obj.getId());
    	Class classz=Class.forName(obj.getStateFulljobExecuteClass());
		Method method = classz.getDeclaredMethod("executeInternal",new Class[]{JobExecutionContext.class}); 
		method.setAccessible(true);
		method.invoke(classz.newInstance(),new Object[]{null});
		writeJsonReturn("执行成功！",null);
		return NONE;
	}
	/**
	 * 添加新的任务
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		obj.setCreateTime(new Date());
		quartzJobService.save(obj);
		writeJsonReturn("保存成功！",null);
		return NONE;
	}

	/**
	 * 去修改任务
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toAlter() throws Exception {
		obj = quartzJobService.findById(obj.getId());
		QuartzManager.disableSchedule(obj.getId(), obj.getJobGroup());// 先把这个任务停下来了
		return "toAlter";
	}

	/**
	 * 修改任务
	 * 
	 * @return
	 * @throws Exception
	 */
	public String alter() throws Exception {
		QuartzJob job = quartzJobService.findById(obj.getId());
		job.setCronExpression(obj.getCronExpression());
		job.setJobName(obj.getJobName());
		job.setJobGroup(obj.getJobGroup());
		job.setStateFulljobExecuteClass(obj.getStateFulljobExecuteClass());
		job.setDescription(obj.getDescription());
		quartzJobService.alter(job);
		writeJsonReturn("保存成功！",null);
		return NONE;
	}

	/**
	 * 删除任务（先要停止任务）
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		for (String id : jobs) {
			obj = quartzJobService.findById(id);
			boolean flag = QuartzManager.disableSchedule(obj.getId(), obj
					.getJobGroup());// 先把这个任务停下来了
			if (flag) {// 任务停止成功就可以删除
				quartzJobService.deleteById(obj.getId());
			}
		}
		return BaseAction.RELOAD;
	}

	/**
	 * 停止任务
	 * 
	 * @return
	 * @throws Exception
	 */
	public String stop() throws Exception {
		obj = quartzJobService.findById(obj.getId());
		boolean flag = QuartzManager.disableSchedule(obj.getId(), obj
				.getJobGroup());
		if (flag) {
			writeJsonReturn("停止成功", null);
		} else {
			writeJsonReturn("停止失败", null);
		}
		return null;
	}

	/**
	 * 启动任务
	 * 
	 * @return
	 * @throws Exception
	 */
	public String start() throws Exception {
		obj = quartzJobService.findById(obj.getId());
		JobDataMap paramsMap=new JobDataMap();
		paramsMap.put("objectFactory", objectFactory);
		QuartzManager.enableCronSchedule(obj, paramsMap, true);
		writeJsonReturn("启动成功", null);
		return null;
	}

	/**
	 * @return the jobs
	 */
	public String[] getJobs() {
		return jobs;
	}

	/**
	 * @param jobs
	 *            the jobs to set
	 */
	public void setJobs(String[] jobs) {
		this.jobs = jobs;
	}

	@Inject
	public void setObjectFactory(ObjectFactory fac) {
		this.objectFactory = fac;
	}
}
