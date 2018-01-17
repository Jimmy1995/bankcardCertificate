package com.common.frame.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
public class Group implements Serializable{
	private static final long serialVersionUID = 3631014686947362218L;
	// Fields
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解
	//@GeneratedValue(generator="idGenerator") //使用uuid的生成策略
	@Column(name="groupId")
	private String groupId;
	@Column(name="pid")
	private String pid;
	@Column(name="groupName",length=100)
	private String groupName;//名称
	@Column(name="groupCode",length=50)
	private String groupCode;//机构代码
	@Column(name="type",length=20)
	private String type;//机构类型
	@Column(name="zipCode",length=100)
	private String zipCode;//邮编
	@Column(name="address",length=100)
	private String address;//地址
	@Column(name="phone",length=30)
	private String phone;//电话
	@Column(name="contact",length=100)
	private String contact;//联系方式
	@Column(name="ect")
	private String ect;
	@Column(name="ect1")
	private String ect1;
	@Column(name="ect2")
	private String ect2;
	@Column(name="ect3")
	private String ect3;
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinTable(name="sys_usergroup",inverseJoinColumns={@JoinColumn(name="userId")},joinColumns={@JoinColumn(name="groupId")})
	private Set<UserInfo> user=new HashSet<UserInfo>(0);
	public Group(String groupId, String pid, String groupName, String groupCode, String type,
			Set<UserInfo> user,String zipCode,String address,String phone,String contact,String ect,String ect1,String ect2,String ect3) {
		this.groupId = groupId;
		this.pid = pid;
		this.groupName = groupName;
		this.groupCode = groupCode;
		this.type = type;
		this.user = user;
		this.zipCode=zipCode;
		this.address=address;
		this.phone=phone;
		this.contact=contact;
		this.ect=ect;
		this.ect1=ect1;
		this.ect2=ect2;
		this.ect3=ect3;
	}
	
	public Group() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Group(String groupId) {
		super();
		this.groupId = groupId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
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
		Group other = (Group) obj;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		return true;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEct() {
		return ect;
	}

	public void setEct(String ect) {
		this.ect = ect;
	}

	public String getEct1() {
		return ect1;
	}

	public void setEct1(String ect1) {
		this.ect1 = ect1;
	}

	public String getEct2() {
		return ect2;
	}

	public void setEct2(String ect2) {
		this.ect2 = ect2;
	}

	public String getEct3() {
		return ect3;
	}

	public void setEct3(String ect3) {
		this.ect3 = ect3;
	}

	public Set<UserInfo> getUser() {
		return user;
	}

	public void setUser(Set<UserInfo> user) {
		this.user = user;
	}
	
}
