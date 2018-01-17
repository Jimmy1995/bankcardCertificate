package com.x2x.manager.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @DESC 商户信息表
 * @AUTHOR JIANGCW
 * @DATE 2018-1-15上午08:45:21
 */
public class FfvBusiness01 implements Serializable{
	private static final long serialVersionUID = 2630582505251016850L;
	
	private Long b01id;
	private String busMchid;
	private String bType;
	private String busName;
	private String busShortName;
	private Long proxy01id;
	private Long totalBalance;
	private Long balance;
	private Date createTime;
	private Date updateTime;
	private String bStatus;
	private String remark;
	public Long getB01id() {
		return b01id;
	}
	public void setB01id(Long b01id) {
		this.b01id = b01id;
	}
	public String getBusMchid() {
		return busMchid;
	}
	public void setBusMchid(String busMchid) {
		this.busMchid = busMchid;
	}
	public String getbType() {
		return bType;
	}
	public void setbType(String bType) {
		this.bType = bType;
	}
	public String getBusName() {
		return busName;
	}
	public void setBusName(String busName) {
		this.busName = busName;
	}
	public String getBusShortName() {
		return busShortName;
	}
	public void setBusShortName(String busShortName) {
		this.busShortName = busShortName;
	}
	public Long getProxy01id() {
		return proxy01id;
	}
	public void setProxy01id(Long proxy01id) {
		this.proxy01id = proxy01id;
	}
	public Long getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(Long totalBalance) {
		this.totalBalance = totalBalance;
	}
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getbStatus() {
		return bStatus;
	}
	public void setbStatus(String bStatus) {
		this.bStatus = bStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
