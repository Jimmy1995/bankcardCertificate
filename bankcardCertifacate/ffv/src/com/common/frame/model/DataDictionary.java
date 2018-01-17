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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

public class DataDictionary implements Serializable{
	
	private static final long serialVersionUID = 3968835510774448322L;
	// Fields
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解
	@GeneratedValue(generator="idGenerator") //使用uuid的生成策略
	@Column(name="dicId")
	private String dicId;
	@Column
	private String codetype;
	@Column(name="dicKey",length=20)
	private String dicKey;//键
	@Column(name="dicValue",length=100)
	private String dicValue;//值
	@Column(name="orders",length=100)
	private String orders;//排序
	@Column(name="description",length=100)
	private String description;//描述
	@Column(name="createTime")
	private Date createTime=new Date();//创建时间
	@ManyToOne(fetch = FetchType.LAZY)  
	@JoinColumn(name = "pid")
	private DataDictionary parentDic;
	@OneToMany(targetEntity = DataDictionary.class, cascade={ CascadeType.ALL }, mappedBy = "parentDic")  
	@Fetch(FetchMode.SUBSELECT)  
	@OrderBy("orders")
	private Set<DataDictionary> childDic=new HashSet<DataDictionary>(0);
	@Column(name="isOpen",length=100)
	private String isOpen="0";//是否开放给所有人（不管登录不登录）0:不开放，1:开放
	@Column(name="ect1",length=100)
	private String ect1;//备用字段
	@Column(name="ect2",length=100)
	private String ect2;//备用字段
	//多对多
	@ManyToMany(cascade={CascadeType.REFRESH,CascadeType.MERGE},fetch=FetchType.LAZY)
	@JoinTable(name="sys_roledic",inverseJoinColumns={@JoinColumn(name="roleId")},joinColumns={@JoinColumn(name="dicId")})
	private Set<Role> roles = new HashSet<Role>(0);//角色集合多对多关联
	public DataDictionary(String dicId){
		this.dicId=dicId;
	}
	
	public DataDictionary() {
	}
	public DataDictionary(String dicId, String codetype, String dicKey, String dicValue,
			String orders, String description, Date createTime,
			DataDictionary parentDic, Set<DataDictionary> childDic,
			String isOpen, String ect1, String ect2, Set<Role> roles) {
		super();
		this.dicId = dicId;
		this.codetype = codetype;
		this.dicKey = dicKey;
		this.dicValue = dicValue;
		this.orders = orders;
		this.description = description;
		this.createTime = createTime;
		this.parentDic = parentDic;
		this.childDic = childDic;
		this.isOpen = isOpen;
		this.ect1 = ect1;
		this.ect2 = ect2;
		this.roles = roles;
	}
	
	public String getDicId() {
		return dicId;
	}

	public void setDicId(String dicId) {
		this.dicId = dicId;
	}

	public String getDicKey() {
		return dicKey;
	}

	public void setDicKey(String dicKey) {
		this.dicKey = dicKey;
	}

	public String getDicValue() {
		return dicValue;
	}

	public void setDicValue(String dicValue) {
		this.dicValue = dicValue;
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
	/**
	 * @return the ect
	 */
	public String getIsOpen() {
		return isOpen;
	}
	/**
	 * @param ect the ect to set
	 */
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	/**
	 * @return the ect
	 */
	public String getEct1() {
		return ect1;
	}
	/**
	 * @param ect the ect to set
	 */
	public void setEct1(String ect1) {
		this.ect1 = ect1;
	}
	/**
	 * @return the ect
	 */
	public String getEct2() {
		return ect2;
	}
	/**
	 * @param ect the ect to set
	 */
	public void setEct2(String ect2) {
		this.ect2 = ect2;
	}
	/**
	 * @return the ect
	 */
	public String getOrders() {
		return orders;
	}
	/**
	 * @param ect the ect to set
	 */
	public void setOrders(String orders) {
		this.orders = orders;
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
	/**
	 * @return the parentDic
	 */
	public DataDictionary getParentDic() {
		return parentDic;
	}
	/**
	 * @param parentDic the parentDic to set
	 */
	public void setParentDic(DataDictionary parentDic) {
		this.parentDic = parentDic;
	}
	/**
	 * @return the childDic
	 */
	public Set<DataDictionary> getChildDic() {
		return childDic;
	}
	/**
	 * @param childDic the childDic to set
	 */
	public void setChildDic(Set<DataDictionary> childDic) {
		this.childDic = childDic;
	}
	public String getCodetype() {
		return codetype;
	}
	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dicId == null) ? 0 : dicId.hashCode());
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
		DataDictionary other = (DataDictionary) obj;
		if (dicId == null) {
			if (other.dicId != null)
				return false;
		} else if (!dicId.equals(other.dicId))
			return false;
		return true;
	}
}
