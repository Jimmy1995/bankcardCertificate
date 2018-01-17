package com.common.frame.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 管理bean的表，类似于spring的配置文件或者说spring的注解。主要是方便动态的插入或更改bean的实现
 * @author Administrator
 */
public class Beans implements Serializable{
	private static final long serialVersionUID = -5229497610549906560L;
	@Id
	@Column(name="bean_id",length=32,nullable=false)
	private String beanId;//
	@Column(name="class_name",length=255)
	private String className;//类全称
	@Column(name="isSingle",nullable=false)
	private Boolean isSingle;//是否单例
	@Column(name="description")
	private String description;//描述
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createTime")
	private Date createTime;//创建时间
	/**
	 * @return the beanID
	 */
	public String getBeanId() {
		return beanId;
	}
	/**
	 * @param beanID the beanID to set
	 */
	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return the isSingle
	 */
	public Boolean getIsSingle() {
		return isSingle;
	}
	/**
	 * @param isSingle the isSingle to set
	 */
	public void setIsSingle(Boolean isSingle) {
		this.isSingle = isSingle;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beanId == null) ? 0 : beanId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Beans other = (Beans) obj;
		if (beanId == null) {
			if (other.beanId != null)
				return false;
		} else if (!beanId.equals(other.beanId))
			return false;
		return true;
	}
	public Beans() {
		super();
	}
	public Beans(String beanId, String className, Boolean isSingle,
			String description, Date createTime) {
		super();
		this.beanId = beanId;
		this.className = className;
		this.isSingle = isSingle;
		this.description = description;
		this.createTime = createTime;
	}
}
