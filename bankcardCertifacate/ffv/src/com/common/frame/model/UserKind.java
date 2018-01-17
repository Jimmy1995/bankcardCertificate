package com.common.frame.model;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @author 登录用户类别
 */
public class UserKind {
	@Id
	@Column(length=40)
	private String userkindid;//主键
	@Column
	private String type;//类型0:系统管理(admin)，类型1：企业(corp)
	@Column(length=500)
	private String login_sql;//登录SQL
	@Column(length=500)
	private String query_sql;//查询SQL
	@Column
	private String description;//描述
	@Column(length=2)
	private String valid;//0：无效，1：有效
	@Column(length=40)
	private String default_roleid;//默认角色
	@Column(length=40)
	private String default_groupid;//默认部门
	@Column(length=255)
	private String ect;
	public String getEct() {
		return ect;
	}
	public void setEct(String ect) {
		this.ect = ect;
	}
	public String getUserkindid() {
		return userkindid;
	}
	public void setUserkindid(String userkindid) {
		this.userkindid = userkindid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLogin_sql() {
		return login_sql;
	}
	public void setLogin_sql(String login_sql) {
		this.login_sql = login_sql;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getDefault_roleid() {
		return default_roleid;
	}
	public void setDefault_roleid(String default_roleid) {
		this.default_roleid = default_roleid;
	}
	public String getDefault_groupid() {
		return default_groupid;
	}
	public void setDefault_groupid(String default_groupid) {
		this.default_groupid = default_groupid;
	}
	public String getQuery_sql() {
		return query_sql;
	}
	public void setQuery_sql(String query_sql) {
		this.query_sql = query_sql;
	}
	public UserKind() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserKind(String userkindid, String type, String login_sql,
			String query_sql, String description, String valid,
			String default_roleid, String default_groupid) {
		super();
		this.userkindid = userkindid;
		this.type = type;
		this.login_sql = login_sql;
		this.query_sql = query_sql;
		this.description = description;
		this.valid = valid;
		this.default_roleid = default_roleid;
		this.default_groupid = default_groupid;
	}
}
