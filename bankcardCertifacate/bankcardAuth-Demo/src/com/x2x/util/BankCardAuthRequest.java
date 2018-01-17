package com.x2x.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 * @DESC 银行卡实名认证请求实体
 * @AUTHOR JIANGCW
 * @DATE 2018-1-11下午05:45:04
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class BankCardAuthRequest {

	private ReqHead head;
	private body data;
	
	public BankCardAuthRequest() {
		super();
	}

	public BankCardAuthRequest(ReqHead head, body data) {
		super();
		this.head = head;
		this.data = data;
	}

	public ReqHead getHead() {
		return head;
	}

	public void setHead(ReqHead head) {
		this.head = head;
	}

	public body getData() {
		return data;
	}

	public void setData(body data) {
		this.data = data;
	}

	@XmlRootElement(name = "data")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class body{
		private String realName;//开户人姓名
		private String idCard;//身份证
		private String bankCard;//银行卡
		private String bankPhone;//预留手机号
		private String serviceCode;//认证模式 301：银行卡二要素实名认证 302：银行卡三要素实名认证 303：银行卡四要素实名认证 312：银行卡三要素精准错误返回实名认证 313：银行卡四要素精准错误返回实名认证
		private String extend1;
		private String extend2;
		private String extend3;
		private String extend4;
		private String extend5;
		
		public String getRealName() {
			return realName;
		}
		public void setRealName(String realName) {
			this.realName = realName;
		}
		public String getIdCard() {
			return idCard;
		}
		public void setIdCard(String idCard) {
			this.idCard = idCard;
		}
		public String getBankCard() {
			return bankCard;
		}
		public void setBankCard(String bankCard) {
			this.bankCard = bankCard;
		}
		public String getBankPhone() {
			return bankPhone;
		}
		public void setBankPhone(String bankPhone) {
			this.bankPhone = bankPhone;
		}
		public String getServiceCode() {
			return serviceCode;
		}
		public void setServiceCode(String serviceCode) {
			this.serviceCode = serviceCode;
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
		public body(String realName, String idCard,String bankCard, 
				String bankPhone, String serviceCode, String extend1,
				String extend2, String extend3, String extend4, String extend5) {
			super();
			this.realName = realName;
			this.idCard = idCard;
			this.bankCard = bankCard;
			this.bankPhone = bankPhone;
			this.serviceCode = serviceCode;
			this.extend1 = extend1;
			this.extend2 = extend2;
			this.extend3 = extend3;
			this.extend4 = extend4;
			this.extend5 = extend5;
		}
		public body() {
			super();
		}
	}
}