package com.common.frame.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.common.frame.enums.RoleType;



/**
 * @className:Role.java
 * @classDescription:角色类
 * @author:longzy
 * @createTime:2010-7-5
 */
public class Role implements Serializable{
	private static final long serialVersionUID = 8295938621809746593L;
	// Fields
	@Id
	//@GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解
	//@GeneratedValue(generator="idGenerator") //使用uuid的生成策略
	@Column(name="roleId")
	private String roleId;
	@Column(name="roleName",length=50,nullable=false)
	private String roleName;//角色名称
	@Column(name="roleInfo",length=1000)
	private String roleInfo;//角色介绍
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createTime",nullable=false)
	private Date createTime;//创建时间

	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinTable(name="sys_roleaction",inverseJoinColumns={@JoinColumn(name="actionId")},joinColumns={@JoinColumn(name="roleId")})
    private Set<ActionInfo> actions = new HashSet<ActionInfo>(0);//权限（一个角色对应多个权限，一个权限对应多个角色）
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinTable(name="sys_userrole",inverseJoinColumns={@JoinColumn(name="userId")},joinColumns={@JoinColumn(name="roleId")})
	private Set<UserInfo> users=new HashSet<UserInfo>(0);//用户（一个角色对应多个用户，一个用户对应多个角色）
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinTable(name="sys_rolemenu",inverseJoinColumns={@JoinColumn(name="menuId")},joinColumns={@JoinColumn(name="roleId")})
	@OrderBy("orders")
	private Set<Menu> menus=new HashSet<Menu>(0);//菜单（一个角色对应多个菜单，一个菜单对应多个角色）

	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinTable(name="sys_roledic",inverseJoinColumns={@JoinColumn(name="dicId")},joinColumns={@JoinColumn(name="roleId")})
	@OrderBy("orders")
	private Set<DataDictionary> dataDictionarys=new HashSet<DataDictionary>(0);//数据字典（一个角色对应多个字典，一个字典对应多个角色）
	@Column(name="orders",length=100)
	private String orders;//排序
	
	@Column(name="type")
	@Enumerated(EnumType.ORDINAL)
	private RoleType type;//角色类型
	
	@Column(name="groupCode",length=100)
	private String groupCode;//机构编号
	@Column(name="welcomePage",length=200)
	private String welcomePage;//欢迎界面

	public String getWelcomePage() {
		return welcomePage;
	}

	public void setWelcomePage(String welcomePage) {
		this.welcomePage = welcomePage;
	}



	/**
	 * @return the groupCode
	 */
	public String getGroupCode() {
		return groupCode;
	}



	/**
	 * @param groupCode the groupCode to set
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}



	/**
	 * @return the type
	 */
	public RoleType getType() {
		return type;
	}



	/**
	 * @param type the type to set
	 */
	public void setType(RoleType type) {
		this.type = type;
	}





	/**
	 * @return the dataDictionary
	 */
	public Set<DataDictionary> getDataDictionarys() {
		return dataDictionarys;
	}



	/**
	 * @param dataDictionary the dataDictionary to set
	 */
	public void setDataDictionarys(Set<DataDictionary> dataDictionarys) {
		this.dataDictionarys = dataDictionarys;
	}



	/**
	 * @return the id
	 */
	public String getRoleId() {
		return roleId;
	}



	/**
	 * @param id the id to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the roleInfo
	 */
	public String getRoleInfo() {
		return roleInfo;
	}

	/**
	 * @param roleInfo the roleInfo to set
	 */
	public void setRoleInfo(String roleInfo) {
		this.roleInfo = roleInfo;
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
	 * @return the actions
	 */
	public Set<ActionInfo> getActions() {
		return actions;
	}

	/**
	 * @param actions the actions to set
	 */
	public void setActions(Set<ActionInfo> actions) {
		this.actions = actions;
	}

	/**
	 * @return the users
	 */
	public Set<UserInfo> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Set<UserInfo> users) {
		this.users = users;
	}

	/**
	 * @return the menus
	 */
	public Set<Menu> getMenus() {
		return menus;
	}

	/**
	 * @param menus the menus to set
	 */
	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
	/**
	 * @return the orders
	 */
	public String getOrders() {
		return orders;
	}

	/**
	 * @param orders the orders to set
	 */
	public void setOrders(String orders) {
		this.orders = orders;
	}
	public Role() {
	}
	public Role(String roleId) {
		this.roleId=roleId;
	}
	public Role(String roleId, String roleName, String roleInfo, Date createTime,
			Set<ActionInfo> actions, Set<UserInfo> users, Set<Menu> menus,Set<DataDictionary> dataDictionarys,String orders) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleInfo = roleInfo;
		this.createTime = createTime;
		this.actions = actions;
		this.users = users;
		this.menus = menus;
		this.dataDictionarys = dataDictionarys;
		this.orders=orders;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
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
		Role other = (Role) obj;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		return true;
	}
}