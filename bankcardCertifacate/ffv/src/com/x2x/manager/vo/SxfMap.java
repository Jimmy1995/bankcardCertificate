package com.x2x.manager.vo;

import java.io.Serializable;

public class SxfMap implements Serializable{
	private static final long serialVersionUID = 2953209092303847749L;
	private Integer sxf;//交易手续费率
	private Integer fee;//交易手续费金额
	private Integer isFeeType;//'交易手续费计费模式，10：比率；20：固定金额；默认值为10',
	
	private Integer drawOutSxf;//提现手续费率
	private Integer drawOutFee;//提现手续费金额
	private Integer drawOutIsFeeType;//'提现手续费计费模式，10：比率；20：固定金额；默认值为10',
	
	public Integer getIsFeeType() {
		return isFeeType;
	}
	public void setIsFeeType(Integer isFeeType) {
		this.isFeeType = isFeeType;
	}
	public Integer getSxf() {
		return sxf;
	}
	public void setSxf(Integer sxf) {
		this.sxf = sxf;
	}
	public Integer getFee() {
		return fee;
	}
	public void setFee(Integer fee) {
		this.fee = fee;
	}
	
	public Integer getDrawOutSxf() {
		return drawOutSxf;
	}
	public void setDrawOutSxf(Integer drawOutSxf) {
		this.drawOutSxf = drawOutSxf;
	}
	public Integer getDrawOutFee() {
		return drawOutFee;
	}
	public void setDrawOutFee(Integer drawOutFee) {
		this.drawOutFee = drawOutFee;
	}
	public Integer getDrawOutIsFeeType() {
		return drawOutIsFeeType;
	}
	public void setDrawOutIsFeeType(Integer drawOutIsFeeType) {
		this.drawOutIsFeeType = drawOutIsFeeType;
	}
	
	public SxfMap(Integer sxf, Integer fee, Integer isFeeType,
			Integer drawOutSxf, Integer drawOutFee, Integer drawOutIsFeeType) {
		super();
		this.sxf = sxf;
		this.fee = fee;
		this.isFeeType = isFeeType;
		this.drawOutSxf = drawOutSxf;
		this.drawOutFee = drawOutFee;
		this.drawOutIsFeeType = drawOutIsFeeType;
	}
	public SxfMap() {
		super();
	}
}
