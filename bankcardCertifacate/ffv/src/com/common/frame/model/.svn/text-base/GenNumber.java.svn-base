package com.common.frame.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author longzy
 * @description 流水号生成表
 */
@Entity
@Table(name = "sys_gennumber")
public class GenNumber implements Serializable{
	private static final long serialVersionUID = -5127307888106200888L;
	@Id
	@Column(name="genType",length=100)
	private String genType;
	@Column(name="endNumber",length=100)
	private Long endNumber;
	@Column(name="isBlack",length=100)
	private String isBlack;
	@Column(name="genFunction",length=100)
	private String genFunction;
	/**
	 * @return the genType
	 */
	public String getGenType() {
		return genType;
	}
	/**
	 * @param genType the genType to set
	 */
	public void setGenType(String genType) {
		this.genType = genType;
	}
	/**
	 * @return the endNumber
	 */
	public Long getEndNumber() {
		return endNumber;
	}
	/**
	 * @param endNumber the endNumber to set
	 */
	public void setEndNumber(Long endNumber) {
		this.endNumber = endNumber;
	}
	/**
	 * @return the isBlack
	 */
	public String getIsBlack() {
		return isBlack;
	}
	/**
	 * @param isBlack the isBlack to set
	 */
	public void setIsBlack(String isBlack) {
		this.isBlack = isBlack;
	}
	/**
	 * @return the genFunction
	 */
	public String getGenFunction() {
		return genFunction;
	}
	/**
	 * @param genFunction the genFunction to set
	 */
	public void setGenFunction(String genFunction) {
		this.genFunction = genFunction;
	}
	public GenNumber() {
	}
	public GenNumber(String genType, Long endNumber,
			String isBlack, String genFunction) {
		super();
		this.genType = genType;
		this.endNumber = endNumber;
		this.isBlack = isBlack;
		this.genFunction = genFunction;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genType == null) ? 0 : genType.hashCode());
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
		GenNumber other = (GenNumber) obj;
		if (genType == null) {
			if (other.genType != null)
				return false;
		} else if (!genType.equals(other.genType))
			return false;
		return true;
	}
}
