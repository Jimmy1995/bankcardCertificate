package com.x2x.manager.vo;

import java.util.Date;

public class ChannerlFeeVOForBiz {
	private Long bchid;// ccp_business_paychannel_fee'主键',
	private Long b01id;// 'b01Id',
	private Long chid;// '通道Id',
	private Integer isPayProcessType;// '通道交易手续费计费模式，10：比率；20：固定金额；默认值为10',
	private Integer payProcessRate;// '交易手续费，单位：万几',
	private Integer payProcessFee;// '交易手续费，单位：分/笔',
	private Integer isDrawProcessType;// '通道提现手续费计费模式，10：比率；20：固定金额；默认值为10',
	private Integer drawProcessRate;// '提现手续费，单位：万几',
	private Integer drawProcessFee;// '提现手续费，单位：分/笔',
	private Integer payMinLimitPerTrans;// 该通道单笔交易下限
	private Integer payMaxLimitPerTrans;// 该通道单笔交易上限
	private Integer payMinLimitPerDay;// 该通道单日交易下限
	private Integer payMaxLimitPerDay;// 该通道单日交易上限
	private String status;// '状态：10：无效，90：有效',
	private String inUserStatus;// '在用状态：10：否，90：是',
	private Date createTime;
	private String extend1;// '预留字段，可以用于保存通道的交易配置数据',
	private String extend2;
	private String extend3;
	private String extend4;
	private String extend5;
	private String remark;// '备注 ',
	private String manufactureID;//'所属厂商，100：鑫佳宇（待扩充）',
	public ChannerlFeeVOForBiz() {
		super();
	}
	public ChannerlFeeVOForBiz(Long bchid, Long b01id, Long chid,
			Integer isPayProcessType, Integer payProcessRate,
			Integer payProcessFee, Integer isDrawProcessType,
			Integer drawProcessRate, Integer drawProcessFee,
			Integer payMinLimitPerTrans, Integer payMaxLimitPerTrans,
			Integer payMinLimitPerDay, Integer payMaxLimitPerDay,
			String status, String inUserStatus, Date createTime,
			String extend1, String extend2, String extend3, String extend4,
			String extend5, String remark, String manufactureID) {
		super();
		this.bchid = bchid;
		this.b01id = b01id;
		this.chid = chid;
		this.isPayProcessType = isPayProcessType;
		this.payProcessRate = payProcessRate;
		this.payProcessFee = payProcessFee;
		this.isDrawProcessType = isDrawProcessType;
		this.drawProcessRate = drawProcessRate;
		this.drawProcessFee = drawProcessFee;
		this.payMinLimitPerTrans = payMinLimitPerTrans;
		this.payMaxLimitPerTrans = payMaxLimitPerTrans;
		this.payMinLimitPerDay = payMinLimitPerDay;
		this.payMaxLimitPerDay = payMaxLimitPerDay;
		this.status = status;
		this.inUserStatus = inUserStatus;
		this.createTime = createTime;
		this.extend1 = extend1;
		this.extend2 = extend2;
		this.extend3 = extend3;
		this.extend4 = extend4;
		this.extend5 = extend5;
		this.remark = remark;
		this.manufactureID = manufactureID;
	}
	public String getManufactureID() {
		return manufactureID;
	}
	public void setManufactureID(String manufactureID) {
		this.manufactureID = manufactureID;
	}
	public Long getBchid() {
		return bchid;
	}
	public void setBchid(Long bchid) {
		this.bchid = bchid;
	}
	public Long getB01id() {
		return b01id;
	}
	public void setB01id(Long b01id) {
		this.b01id = b01id;
	}
	public Long getChid() {
		return chid;
	}
	public void setChid(Long chid) {
		this.chid = chid;
	}
	public Integer getIsPayProcessType() {
		return isPayProcessType;
	}
	public void setIsPayProcessType(Integer isPayProcessType) {
		this.isPayProcessType = isPayProcessType;
	}
	public Integer getPayProcessRate() {
		return payProcessRate;
	}
	public void setPayProcessRate(Integer payProcessRate) {
		this.payProcessRate = payProcessRate;
	}
	public Integer getPayProcessFee() {
		return payProcessFee;
	}
	public void setPayProcessFee(Integer payProcessFee) {
		this.payProcessFee = payProcessFee;
	}
	public Integer getIsDrawProcessType() {
		return isDrawProcessType;
	}
	public void setIsDrawProcessType(Integer isDrawProcessType) {
		this.isDrawProcessType = isDrawProcessType;
	}
	public Integer getDrawProcessRate() {
		return drawProcessRate;
	}
	public void setDrawProcessRate(Integer drawProcessRate) {
		this.drawProcessRate = drawProcessRate;
	}
	public Integer getDrawProcessFee() {
		return drawProcessFee;
	}
	public void setDrawProcessFee(Integer drawProcessFee) {
		this.drawProcessFee = drawProcessFee;
	}
	public Integer getPayMinLimitPerTrans() {
		return payMinLimitPerTrans;
	}
	public void setPayMinLimitPerTrans(Integer payMinLimitPerTrans) {
		this.payMinLimitPerTrans = payMinLimitPerTrans;
	}
	public Integer getPayMaxLimitPerTrans() {
		return payMaxLimitPerTrans;
	}
	public void setPayMaxLimitPerTrans(Integer payMaxLimitPerTrans) {
		this.payMaxLimitPerTrans = payMaxLimitPerTrans;
	}
	public Integer getPayMinLimitPerDay() {
		return payMinLimitPerDay;
	}
	public void setPayMinLimitPerDay(Integer payMinLimitPerDay) {
		this.payMinLimitPerDay = payMinLimitPerDay;
	}
	public Integer getPayMaxLimitPerDay() {
		return payMaxLimitPerDay;
	}
	public void setPayMaxLimitPerDay(Integer payMaxLimitPerDay) {
		this.payMaxLimitPerDay = payMaxLimitPerDay;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInUserStatus() {
		return inUserStatus;
	}
	public void setInUserStatus(String inUserStatus) {
		this.inUserStatus = inUserStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getExtend1() {
		return extend1;
	}
	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}
	public String getExtend2() {
		return extend2;
	}
	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}
	public String getExtend3() {
		return extend3;
	}
	public void setExtend3(String extend3) {
		this.extend3 = extend3;
	}
	public String getExtend4() {
		return extend4;
	}
	public void setExtend4(String extend4) {
		this.extend4 = extend4;
	}
	public String getExtend5() {
		return extend5;
	}
	public void setExtend5(String extend5) {
		this.extend5 = extend5;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
