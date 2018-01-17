package com.common.frame.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * @className:Userinfo.java
 * @classDescription:用户类
 * @author:longzy
 * @createTime:2010-7-5
 */
public class UserInfo implements Serializable{
	private static final long serialVersionUID = -2711668672504086538L;
	// Fields
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name="idGenerator", strategy="native") //这个是hibernate的注解
	@GeneratedValue(generator="idGenerator") //使用uuid的生成策略
	@Column(name="userId")
	private Long userId;
	@Column(name="userName",length=50,nullable=false)
	private String userName;//用户名
	@Column(name="userPassword",length=50,nullable=false)
	private String userPassword;//用户密码
	@Column(name="sex",length=2)
	private String sex;//性别 1为男，2为女
	@Column(name="email",length=100)
	private String email;//email
	@Column(name="qq",length=12)
	private String qq;//qq
	@Column(name="isEnable")
	private Integer isEnable;//是否可用
	@Column(name="lonline")
	private long online;//在线时长
	@Column(name="score")
	private Integer score;//积分
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createTime",nullable=false)
	private Date createTime=new Date();//注册时间
	@Column(name="name")
	private String name;//姓名
	@Column(name="isBetter")
    private Integer isBetter=0;//是否为高级用户
	@Column(name="flag")
	private String flag="1";//用户是否允许登录0：禁用1：允许 
	@OrderBy("orders")
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinTable(name="sys_userrole",inverseJoinColumns={@JoinColumn(name="roleId")},joinColumns={@JoinColumn(name="userId")})
	private Set<Role> roles=new HashSet<Role>(0);

	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinTable(name="sys_usergroup",inverseJoinColumns={@JoinColumn(name="groupId")},joinColumns={@JoinColumn(name="userId")})
    private Set<Group> group=new HashSet<Group>(0);
	
	@Column(name="code")
	private String code;
	
	@Column
	private String userkind;
	@Transient
	private Map attrMap;//运行过程中要新加的属性可放到这个字段中
	public Object get(Object key) {
		if(attrMap==null){
			return null;
		}
		return attrMap.get(key);
	}
	public void set(String key,Object value){
		if(attrMap==null){
			attrMap=new HashMap();
		}
		attrMap.put(key, value);
	}
	
	public String getUserkind() {
		return userkind;
	}

	public void setUserkind(String userkind) {
		this.userkind = userkind;
	}
	public void setAttrMap(Map attrMap) {
		this.attrMap = attrMap;
	}

	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the id
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param id the id to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * @param qq the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * @return the isEnable
	 */
	public Integer getIsEnable() {
		return isEnable;
	}

	/**
	 * @param isEnable the isEnable to set
	 */
	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	/**
	 * @return the online
	 */
	public long getOnline() {
		return online;
	}

	/**
	 * @param online the online to set
	 */
	public void setOnline(long online) {
		this.online = online;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}





	/**
	 * @return the content
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param content the content to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the isBetter
	 */
	public Integer getIsBetter() {
		return isBetter;
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
	 * @param isBetter the isBetter to set
	 */
	public void setIsBetter(Integer isBetter) {
		this.isBetter = isBetter;
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
	public Set<Group> getGroup() {
		return group;
	}

	public void setGroup(Set<Group> group) {
		this.group = group;
	}


	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserInfo(Long userId) {
		this.userId=userId;
		// TODO Auto-generated constructor stub
	}
	public UserInfo(Long userId, String userName, String userPassword,
			String sex, String email, String qq, Integer isEnable, long online,
			Integer score, Date createTime, String name, Integer isBetter,
			Set<Role> roles, Set<Group> group,String flag,String code,String userkind) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.sex = sex;
		this.email = email;
		this.qq = qq;
		this.isEnable = isEnable;
		this.online = online;
		this.score = score;
		this.createTime = createTime;
		this.name = name;
		this.isBetter = isBetter;
		this.roles = roles;
		this.group = group;
		this.flag = flag;
		this.code=code;
		this.userkind=userkind;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean flag=false;
		if(!(obj instanceof UserInfo)){
			flag=false;
		}else{
			UserInfo user = (UserInfo)obj;
			if(user.getUserId().equals(this.getUserId())){
				flag=true;
			}
		}
		return flag;
	}
 


}