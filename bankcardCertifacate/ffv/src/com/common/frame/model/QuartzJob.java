/**
 * @author:longzy
 * @description 描述Quartz定时任务的模型
 */
package com.common.frame.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sys_quartzjob")
public class QuartzJob implements Serializable{
	private static final long serialVersionUID = -5631835062657465617L;
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解
	@GeneratedValue(generator="idGenerator") //使用uuid的生成策略
	@Column(name="id_")
	private String id;
	@Column(name="jobId_")
	private String jobId; // 任务的Id，一般为所定义Bean的ID
	@Column(name="jobName_")
	private String jobName; // 任务名
	@Column(name="jobGroup_")
	private String jobGroup; // 任务所属组的名称
	@Column(name="jobStatus_",length=4)
	private String jobStatus; // 任务的状态，0：启用；1：禁用；2：已删除
	@Column(name="cronExpression_")
	private String cronExpression; // 定时任务运行时间表达式
	@Column(name="description_")
	private String description; // 任务描述
	@Column(name="stateFulljobExecuteClass_")
	private String stateFulljobExecuteClass;//同步的执行类，需要从StatefulMethodInvokingJob继承
	@Column(name="jobExecuteClass_")
	private String jobExecuteClass;//异步的执行类，需要从MethodInvokingJob继承
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createTime_",nullable=false)
	private Date createTime=new Date();//创建时间
	
	@Column(name="loadOnStart_")
	private Boolean loadOnStart; // 程序启动的时候加载
	/**
	 * @return the loadOnStart
	 */
	public Boolean getLoadOnStart() {
		return loadOnStart;
	}
	/**
	 * @param loadOnStart the loadOnStart to set
	 */
	public void setLoadOnStart(Boolean loadOnStart) {
		this.loadOnStart = loadOnStart;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the jobId
	 */
	public String getJobId() {
		return jobId;
	}
	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * @return the jobGroup
	 */
	public String getJobGroup() {
		return jobGroup;
	}
	/**
	 * @param jobGroup the jobGroup to set
	 */
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	/**
	 * @return the jobStatus
	 */
	public String getJobStatus() {
		return jobStatus;
	}
	/**
	 * @param jobStatus the jobStatus to set
	 */
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	/**
	 * @return the cronExpression
	 */
	public String getCronExpression() {
		return cronExpression;
	}
	/**
	 * @param cronExpression the cronExpression to set
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the stateFulljobExecuteClass
	 */
	public String getStateFulljobExecuteClass() {
		return stateFulljobExecuteClass;
	}
	/**
	 * @param stateFulljobExecuteClass the stateFulljobExecuteClass to set
	 */
	public void setStateFulljobExecuteClass(String stateFulljobExecuteClass) {
		this.stateFulljobExecuteClass = stateFulljobExecuteClass;
	}
	/**
	 * @return the jobExecuteClass
	 */
	public String getJobExecuteClass() {
		return jobExecuteClass;
	}
	/**
	 * @param jobExecuteClass the jobExecuteClass to set
	 */
	public void setJobExecuteClass(String jobExecuteClass) {
		this.jobExecuteClass = jobExecuteClass;
	}
	public String getTriggerName(){
		return this.getId()+"Trigger";
	}
	public QuartzJob() {
		super();
		// TODO Auto-generated constructor stub
	}
	public QuartzJob(String id, String jobId, String jobName, String jobGroup,
			String jobStatus, String cronExpression, String description,
			String stateFulljobExecuteClass, String jobExecuteClass,Date createTime,Boolean loadOnStart) {
		super();
		this.id = id;
		this.jobId = jobId;
		this.jobName = jobName;
		this.jobGroup = jobGroup;
		this.jobStatus = jobStatus;
		this.cronExpression = cronExpression;
		this.description = description;
		this.stateFulljobExecuteClass = stateFulljobExecuteClass;
		this.jobExecuteClass = jobExecuteClass;
		this.createTime=createTime;
		this.loadOnStart=loadOnStart;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuartzJob other = (QuartzJob) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
