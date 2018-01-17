package com.common.frame.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.common.frame.enums.ServiceType;

/**
 * @author longzy
 * @description 保存webservice的配置信息
 */
public class ServiceParam implements Serializable{
	private static final long serialVersionUID = -4680793876136188509L;
	@Id
	@GenericGenerator(name="idGenerator", strategy="native") //这个是hibernate的注解
	@GeneratedValue(generator="idGenerator") //使用native的生成策略
	@Column(name="service_id")
	private Long serviceId;
	@Column(name="service_type")
	@Enumerated(EnumType.STRING)
	private ServiceType serviceType;//EnumType.ORDINAL是枚举类型的序号;EnumType.STRING是枚举类型的值存入数据库
	@Column(name="service_url")
	private String serviceUrl;
	@Column(name="service_user")
	private String serviceUser;
	@Column(name="service_password")
	private String servicePassword;
	@Column(name="description")
	private String description;
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public ServiceType getServiceType() {
		return serviceType;
	}
	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public String getServiceUser() {
		return serviceUser;
	}
	public void setServiceUser(String serviceUser) {
		this.serviceUser = serviceUser;
	}
	public String getServicePassword() {
		return servicePassword;
	}
	public void setServicePassword(String servicePassword) {
		this.servicePassword = servicePassword;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ServiceParam(Long serviceId, ServiceType serviceType,
			String serviceUrl, String serviceUser, String servicePassword,
			String description) {
		super();
		this.serviceId = serviceId;
		this.serviceType = serviceType;
		this.serviceUrl = serviceUrl;
		this.serviceUser = serviceUser;
		this.servicePassword = servicePassword;
		this.description = description;
	}
	public ServiceParam() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((serviceId == null) ? 0 : serviceId.hashCode());
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
		ServiceParam other = (ServiceParam) obj;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		return true;
	}
	
}
