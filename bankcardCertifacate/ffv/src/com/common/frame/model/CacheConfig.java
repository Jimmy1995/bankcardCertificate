package com.common.frame.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class CacheConfig implements Serializable{
	private static final long serialVersionUID = -1956678775800950727L;
	@Id
	@Column(name="cache_name",length=32,nullable=false)
	private String cacheName;//
	@Column(name="cache_desc",length=255)
	private String cacheDesc;//描述
	@Column(name="cache_class",length=255)
	private String cacheClass;//
	@Column(name="load_sql",length=1000)
	private String loadSql;//
	@Column(name="valid_flag",length=1)
	private String validFlag;//
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createTime")
	private Date createTime;//创建时间
	private String find_key;
	public String getFind_key() {
		return find_key;
	}
	public void setFind_key(String find_key) {
		this.find_key = find_key;
	}
	/**
	 * @return the validFlag
	 */
	public String getValidFlag() {
		return validFlag;
	}
	/**
	 * @param validFlag the validFlag to set
	 */
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	/**
	 * @return the cacheName
	 */
	public String getCacheName() {
		return cacheName;
	}
	/**
	 * @param cacheName the cacheName to set
	 */
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	/**
	 * @return the cacheDesc
	 */
	public String getCacheDesc() {
		return cacheDesc;
	}
	/**
	 * @param cacheDesc the cacheDesc to set
	 */
	public void setCacheDesc(String cacheDesc) {
		this.cacheDesc = cacheDesc;
	}
	/**
	 * @return the cacheClass
	 */
	public String getCacheClass() {
		return cacheClass;
	}
	/**
	 * @param cacheClass the cacheClass to set
	 */
	public void setCacheClass(String cacheClass) {
		this.cacheClass = cacheClass;
	}
	/**
	 * @return the loadSQL
	 */
	public String getLoadSql() {
		return loadSql;
	}
	/**
	 * @param loadSQL the loadSQL to set
	 */
	public void setLoadSql(String loadSql) {
		this.loadSql = loadSql;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cacheName == null) ? 0 : cacheName.hashCode());
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
		CacheConfig other = (CacheConfig) obj;
		if (cacheName == null) {
			if (other.cacheName != null)
				return false;
		} else if (!cacheName.equals(other.cacheName))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CacheConfig [cacheClass=" + cacheClass + ", cacheDesc="
				+ cacheDesc + ", cacheName=" + cacheName + ", createTime="
				+ createTime + ", loadSql=" + loadSql+",find_key="+find_key
				+ "]";
	}
	public CacheConfig() {
		super();
	}
	public CacheConfig(String cacheName, String cacheDesc, String cacheClass,
			String loadSql, Date createTime,String validFlag,String find_key) {
		super();
		this.cacheName = cacheName;
		this.cacheDesc = cacheDesc;
		this.cacheClass = cacheClass;
		this.loadSql = loadSql;
		this.createTime = createTime;
		this.validFlag=validFlag;
		this.find_key=find_key;
	}
}
