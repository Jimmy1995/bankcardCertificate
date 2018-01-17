package com.common.frame.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @className:Log.java
 * @classDescription:日志类
 * @author:longzy
 * @createTime:2010-7-5
 */
@Entity
@Table(name = "sys_log")
public class Log implements Serializable{
	private static final long serialVersionUID = 1166832255032161829L;
	// Fields
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解
	@GeneratedValue(generator="idGenerator") //使用uuid的生成策略
	@Column(name="id_")
	private String id;
	@Column(name="logDate_",length=50)
	private String logDate;//日志插入时间
	@Column(name="logLevel_",length=20)
	private String logLevel;//日志级别
	@Column(name="location_",length=500)
	private String location;//日志发生类
	@Column(name="message_",length=2000)
	private String message;//日志信息
	@Column(name="userName_")
	private String userName;
	@Column(name="ip_")
	private String ip;
	@Column(name="name_")
	private String name;
	@Column(name="place_")
	private String place;
	@Column(name="ect2")
	private String ect2;

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
	 * @return the logDate
	 */
	public String getLogDate() {
		return logDate;
	}
	/**
	 * @param logDate the logDate to set
	 */
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	/**
	 * @return the logLevel
	 */
	public String getLogLevel() {
		return logLevel;
	}
	/**
	 * @param logLevel the logLevel to set
	 */
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
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
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the ect
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param ect the ect to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the ect1
	 */
	public String getPlace() {
		return place;
	}
	/**
	 * @param ect1 the ect1 to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}
	/**
	 * @return the ect2
	 */
	public String getEct2() {
		return ect2;
	}
	/**
	 * @param ect2 the ect2 to set
	 */
	public void setEct2(String ect2) {
		this.ect2 = ect2;
	}
	public Log() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Log(String id, String logDate, String logLevel, String location,
			String message, String userName, String ip, String name,
			String place, String ect2) {
		super();
		this.id = id;
		this.logDate = logDate;
		this.logLevel = logLevel;
		this.location = location;
		this.message = message;
		this.userName = userName;
		this.ip = ip;
		this.name = name;
		this.place = place;
		this.ect2 = ect2;
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
		Log other = (Log) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}