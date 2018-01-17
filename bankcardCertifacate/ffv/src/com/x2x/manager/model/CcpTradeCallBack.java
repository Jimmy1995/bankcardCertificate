package com.x2x.manager.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 异步通知表
 * @author longzy
 *
 */
public class CcpTradeCallBack implements Serializable{
	private static final long serialVersionUID = -9141970123017817317L;
	private Long backid;
	private Long t01id;
	private Long t02id;
	private String bcode;
	private String reqNo;
	private String backurl;
	private Integer runcount;
	private String status;
	private Date startdate;
	private Date lasttime;
	public Long getBackid() {
		return backid;
	}
	public void setBackid(Long backid) {
		this.backid = backid;
	}
	public Long getT01id() {
		return t01id;
	}
	public void setT01id(Long t01id) {
		this.t01id = t01id;
	}
	public Long getT02id() {
		return t02id;
	}
	public void setT02id(Long t02id) {
		this.t02id = t02id;
	}
	public String getBcode() {
		return bcode;
	}
	public void setBcode(String bcode) {
		this.bcode = bcode;
	}
	public String getReqNo() {
		return reqNo;
	}
	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	public String getBackurl() {
		return backurl;
	}
	public void setBackurl(String backurl) {
		this.backurl = backurl;
	}
	public Integer getRuncount() {
		return runcount;
	}
	public void setRuncount(Integer runcount) {
		this.runcount = runcount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getLasttime() {
		return lasttime;
	}
	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}
	public CcpTradeCallBack(Long backid, Long t01id, Long t02id, String bcode,
			String reqNo, String backurl, Integer runcount, String status,
			Date startdate, Date lasttime) {
		super();
		this.backid = backid;
		this.t01id = t01id;
		this.t02id = t02id;
		this.bcode = bcode;
		this.reqNo = reqNo;
		this.backurl = backurl;
		this.runcount = runcount;
		this.status = status;
		this.startdate = startdate;
		this.lasttime = lasttime;
	}
	public CcpTradeCallBack() {
		super();
	} 
}
