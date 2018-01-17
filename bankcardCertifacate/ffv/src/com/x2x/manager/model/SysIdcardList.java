package com.x2x.manager.model;

public class SysIdcardList {
	private Long ilid;
	private String cardId;
	private String idName;
	private String mobileNumber;
	private String cardStartTime;
	private String cardEndTime;
	private String cardFrontPhoto;
	private String cardBackPhoto;
	private String cardBusType;
	private String linkId;
	private String createTime;
	private String status;
	private String remark;
	public SysIdcardList() {
		super();
	}
	public SysIdcardList(Long ilid, String cardId, String idName,
			String mobileNumber, String cardStartTime, String cardEndTime,
			String cardFrontPhoto, String cardBackPhoto, String cardBusType,
			String linkId, String createTime, String status, String remark) {
		super();
		this.ilid = ilid;
		this.cardId = cardId;
		this.idName = idName;
		this.mobileNumber = mobileNumber;
		this.cardStartTime = cardStartTime;
		this.cardEndTime = cardEndTime;
		this.cardFrontPhoto = cardFrontPhoto;
		this.cardBackPhoto = cardBackPhoto;
		this.cardBusType = cardBusType;
		this.linkId = linkId;
		this.createTime = createTime;
		this.status = status;
		this.remark = remark;
	}
	public Long getIlid() {
		return ilid;
	}
	public void setIlid(Long ilid) {
		this.ilid = ilid;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getIdName() {
		return idName;
	}
	public void setIdName(String idName) {
		this.idName = idName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getCardStartTime() {
		return cardStartTime;
	}
	public void setCardStartTime(String cardStartTime) {
		this.cardStartTime = cardStartTime;
	}
	public String getCardEndTime() {
		return cardEndTime;
	}
	public void setCardEndTime(String cardEndTime) {
		this.cardEndTime = cardEndTime;
	}
	public String getCardFrontPhoto() {
		return cardFrontPhoto;
	}
	public void setCardFrontPhoto(String cardFrontPhoto) {
		this.cardFrontPhoto = cardFrontPhoto;
	}
	public String getCardBackPhoto() {
		return cardBackPhoto;
	}
	public void setCardBackPhoto(String cardBackPhoto) {
		this.cardBackPhoto = cardBackPhoto;
	}
	public String getCardBusType() {
		return cardBusType;
	}
	public void setCardBusType(String cardBusType) {
		this.cardBusType = cardBusType;
	}
	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
