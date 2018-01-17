/**
 * 
 */
package com.common.web.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdScheduler;

import com.common.base.BaseException;
import com.common.frame.model.QuartzJob;
import com.common.frame.service.IQuartzJobService;
import com.common.web.SpringContextHolder;
import com.opensymphony.xwork2.ObjectFactory;
public class QuartzManager {
	Log log=LogFactory.getLog(QuartzManager.class);
	private static Scheduler scheduler;
	//缓存bean的最后修改时间
	public static Map<String,Object> cacheLastModifyTimeMap = new HashMap<String,Object>();
	static {
		//ApplicationContext context = new ClassPathXmlApplicationContext(
			//	"config/spring/quartzDynamic.xml");
		scheduler = (StdScheduler) SpringContextHolder.getBean("schedulerFactory");
	}
	/**
	 * @return the scheduler
	 */
	/**
	 * 启动一个自定义的job
	 * 
	 * @param schedulingJob
	 *            自定义的job
	 * @param paramsMap
	 *            传递给job执行的数据
	 * @param isStateFull
	 *            是否是一个同步定时任务，true：同步，false：异步
	 * @return 成功则返回true，否则返回false
	 */
	public static void enableCronSchedule(QuartzJob schedulingJob,
			JobDataMap paramsMap, boolean isStateFull) {
		if (schedulingJob == null) {
			return;
		}
		ObjectFactory objectFactory=(ObjectFactory)paramsMap.get("objectFactory");
		try {
			CronTrigger trigger = (CronTrigger) scheduler
					.getTrigger(schedulingJob.getTriggerName(),
							schedulingJob.getJobGroup());
			if (null == trigger) {// 如果不存在该trigger则创建一个
				JobDetail jobDetail = null;
				if (isStateFull) {
					jobDetail = new JobDetail(schedulingJob.getId(),
							schedulingJob.getJobGroup(),
							objectFactory.getClassInstance(schedulingJob.getStateFulljobExecuteClass()));
				} else {
					jobDetail = new JobDetail(schedulingJob.getJobId(),
							schedulingJob.getJobGroup(),
							objectFactory.getClassInstance(schedulingJob.getJobExecuteClass()));
				}
				jobDetail.setJobDataMap(paramsMap);
				trigger = new CronTrigger(schedulingJob.getTriggerName(),
						schedulingJob.getJobGroup(),
						schedulingJob.getCronExpression());
				scheduler.scheduleJob(jobDetail, trigger);
			} else {
				// Trigger已存在，那么更新相应的定时设置
				trigger.setCronExpression(schedulingJob.getCronExpression());
				scheduler.rescheduleJob(trigger.getName(), trigger.getGroup(),
						trigger);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException(e);
		}
	}

	/**
	 * 禁用一个job
	 * 
	 * @param id
	 *            需要被禁用的job的ID
	 * @param jobGroup
	 *            需要被警用的jobGroup
	 * @return 成功则返回true，否则返回false
	 */
	public static boolean disableSchedule(String id, String jobGroup) throws Exception{
		if (id.equals("") || jobGroup.equals("")) {
			return false;
		}
			Trigger trigger = getJobTrigger(id, jobGroup);
			if (null != trigger) {
				scheduler.interrupt(id, jobGroup);
				scheduler.deleteJob(id, jobGroup);
			}
		return true;
	}

	/**
	 * 得到job的详细信息
	 * 
	 * @param id
	 *            job的ID
	 * @param jobGroup
	 *            job的组ID
	 * @return job的详细信息,如果job不存在则返回null
	 */
	public static JobDetail getJobDetail(String id, String jobGroup) {
		if (StringUtils.isEmpty(id)||StringUtils.isEmpty(jobGroup)) {
			return null;
		}
		try {
			return scheduler.getJobDetail(id, jobGroup);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 得到job对应的Trigger
	 * 
	 * @param id
	 *            job的ID
	 * @param jobGroup
	 *            job的组ID
	 * @return job的Trigger,如果Trigger不存在则返回null
	 */
	public static Trigger getJobTrigger(String id, String jobGroup) {
		if (StringUtils.isEmpty(id)||StringUtils.isEmpty(jobGroup)) {
			return null;
		}
		try {
			return scheduler.getTrigger(id + "Trigger", jobGroup);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void start4DBInit(){
		IQuartzJobService jobservice=SpringContextHolder.getBean("quartzJobService");
		List<QuartzJob> jobs=jobservice.findList("from QuartzJob where loadOnStart=true");
		ObjectFactory objectFactory=new ObjectFactory();
		JobDataMap paramsMap=new JobDataMap();
		paramsMap.put("objectFactory", objectFactory);
		if(jobs!=null&&jobs.size()>0){
			for(QuartzJob job:jobs){
				//如果最后修改时间没有变动则不重新加载
				if(null==job.getCreateTime()||job.getCreateTime().equals(cacheLastModifyTimeMap.get(job.getId())))continue;
				try{
					disableSchedule(job.getId(), job.getJobGroup());
				}catch(Exception e){
					e.printStackTrace();
				}
				enableCronSchedule(job, paramsMap, true);
				cacheLastModifyTimeMap.put(job.getId(), job.getCreateTime());
			}
		}
	}
}
