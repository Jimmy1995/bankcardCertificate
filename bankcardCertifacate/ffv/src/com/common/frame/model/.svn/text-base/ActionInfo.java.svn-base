package com.common.frame.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @className:Action.java
 * @classDescription:权限类
 * @author:longzy
 * @createTime:2010-7-5
 */
public class ActionInfo implements Serializable{
	private static final long serialVersionUID = 2839139312199881715L;
	// Fields
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name="idGenerator", strategy="native") //这个是hibernate的注解
	@GeneratedValue(generator="idGenerator") //使用uuid的生成策略
	@Column(name="actionId")
	private Long actionId;
	@Column(name="actionName",length=50)
	private String actionName;//动作名称（例如增加，删除）
	@Column(name="actionPath",length=100,nullable=false)
	private String actionPath;//相对于主目录的路径
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createTime",nullable=false)
	private Date createTime;//创建时间
	@Column(name="orders",nullable=false)
	private int orders;//排序
	
	//多对一
	@ManyToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.LAZY ,optional = false)
    @JoinColumn(name = "menuId")     
	private Menu menu ;//模块
	//多对多
	@ManyToMany(cascade={CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.LAZY)
	@JoinTable(name="sys_roleaction",inverseJoinColumns={@JoinColumn(name="roleId")},joinColumns={@JoinColumn(name="actionId")})
	private Set<Role> roles = new HashSet<Role>(0);//角色集合多对多关联
	/**
	 * @return the orders
	 */
	public int getOrders() {
		return orders;
	}
	/**
	 * @param orders the orders to set
	 */
	public void setOrders(int orders) {
		this.orders = orders;
	}


	/**
	 * @return the id
	 */
	public Long getActionId() {
		return actionId;
	}
	/**
	 * @param id the id to set
	 */
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}
	
	/**
	 * @return the actionName
	 */
	public String getActionName() {
		return actionName;
	}
	/**
	 * @param actionName the actionName to set
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	/**
	 * @return the path
	 */
	public String getActionPath() {
		return actionPath;
	}
	/**
	 * @param path the path to set
	 */
	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
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
	 * @return the module
	 */
	public Menu getMenu() {
		return menu;
	}
	/**
	 * @param module the module to set
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}
	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	
	public ActionInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ActionInfo(Long actionId, String actionName, String path, Date createTime,
			int orders, Menu menu, Set<Role> roles) {
		super();
		this.actionId = actionId;
		this.actionName = actionName;
		this.actionPath = actionPath;
		this.createTime = createTime;
		this.orders = orders;
		this.menu = menu;
		this.roles = roles;
	}

    

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionId == null) ? 0 : actionId.hashCode());
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
		ActionInfo other = (ActionInfo) obj;
		if (actionId == null) {
			if (other.actionId != null)
				return false;
		} else if (!actionId.equals(other.actionId))
			return false;
		return true;
	}
    

}