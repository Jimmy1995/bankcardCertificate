package com.common.frame.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.common.frame.enums.MenuType;

/**
 * @className:Menu.java
 * @classDescription:菜单对象
 * @author:longzy
 * @createTime:2010-7-5
 */
public class Menu implements Serializable{
	private static final long serialVersionUID = 7322894880467897110L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	// 这个是hibernate的注解
	@GeneratedValue(generator = "idGenerator")
	// 使用uuid的生成策略
	@Column(name = "menuId")
	private String menuId;
	@Column(name = "menuName", length = 50, nullable = false)
	private String menuName;// 显示名称
	@Column(name = "parentId", nullable = false)
	private String parentId = "0";// 父id,如果为顶级菜单则为0
	@Column(name = "menuUrl", length = 200)
	private String menuUrl;// 链接url
	@Column(name = "imageUrl", length = 200)
	private String imageUrl;// image链接url
	@Column(name = "menuLevel")
	private int menuLevel;// 层次
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", nullable = false)
	private Date createTime = new Date();// 创建时间
	@Column(name = "orders")
	private String orders;// 排序

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "sys_rolemenu", inverseJoinColumns = { @JoinColumn(name = "roleId") }, joinColumns = { @JoinColumn(name = "menuId") })
	private Set<Role> roles = new HashSet<Role>(0);
	@Transient
	private List<Menu> children = new ArrayList<Menu>();// 子菜单

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menu")
	// mappedBy 单向关系不需要设置该属性，双向关系必须设置，避免双方都建立外键字段？？？（好像我的多对多都没设置这个的）
	// 数据库中1对多的关系，关联关系总是被多方维护的即外键建在多方，我们在单方对象的@OneToMany(mappedby=" "),module的操作是不会级联到action，反之却可以
	// 只有OneToOne,OneToMany,ManyToMany上才有mappedBy属性，ManyToOne不存在该属性,
	// MappedBy跟JoinColumn/JoinTable总是处于互斥的一方
	@OrderBy("orders")
	private Set<ActionInfo> actions = new HashSet<ActionInfo>(0);


	@Column(name = "menuType")
	@Enumerated(EnumType.ORDINAL)
	private MenuType menuType;// 菜单类型

	@Column(name = "flag")
	private String flag = "1";// 0：禁用1：允许
	@Column(name="description",length=100)
	private String description;//描述


	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public Set<ActionInfo> getActions() {
		return actions;
	}

	public void setActions(Set<ActionInfo> actions) {
		this.actions = actions;
	}

	public MenuType getMenuType() {
		return menuType;
	}

	public void setMenuType(MenuType menuType) {
		this.menuType = menuType;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Menu() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Menu(String menuId) {
		this.menuId = menuId;
		// TODO Auto-generated constructor stub
	}

	public Menu(String menuId, String menuName, String parentId, String menuUrl,
			String imageUrl, int menuLevel, Date createTime, String orders,
			Set<Role> roles, List<Menu> children) {
		super();
		this.menuId = menuId;
		this.menuName = menuName;
		this.parentId = parentId;
		this.menuUrl = menuUrl;
		this.imageUrl = imageUrl;
		this.menuLevel = menuLevel;
		this.createTime = createTime;
		this.orders = orders;
		this.roles = roles;
		this.children = children;
	}

	
}
