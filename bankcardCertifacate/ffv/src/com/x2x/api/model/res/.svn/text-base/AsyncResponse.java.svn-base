package com.x2x.api.model.res;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.x2x.api.model.ResHead;

/**
 * 异步通知
 * @author longzy
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class AsyncResponse {
	private ResHead head;
	private body data;
	
	public AsyncResponse() {
		super();
	}

	public AsyncResponse(ResHead head, body data) {
		super();
		this.head = head;
		this.data = data;
	}

	public ResHead getHead() {
		return head;
	}

	public void setHead(ResHead head) {
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
		private String payTime;
		private Integer money;
		private Integer settleMoney;
		private String payStatus;
		private String settleStatus;
		private String extend1;
		private String extend2;
		private String extend3;
		private String extend4;
		private String settleStatusDesc;
		
		public String getPayTime() {
			return payTime;
		}


		public void setPayTime(String payTime) {
			this.payTime = payTime;
		}


		public Integer getMoney() {
			return money;
		}


		public void setMoney(Integer money) {
			this.money = money;
		}


		public Integer getSettleMoney() {
			return settleMoney;
		}


		public void setSettleMoney(Integer settleMoney) {
			this.settleMoney = settleMoney;
		}


		public String getPayStatus() {
			return payStatus;
		}


		public void setPayStatus(String payStatus) {
			this.payStatus = payStatus;
		}


		public String getSettleStatus() {
			return settleStatus;
		}


		public void setSettleStatus(String settleStatus) {
			this.settleStatus = settleStatus;
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

		public body(String payTime, Integer money, Integer settleMoney,
				String payStatus, String settleStatus, String extend1,
				String extend2, String extend3, String extend4,
				String settleStatusDesc) {
			super();
			this.payTime = payTime;
			this.money = money;
			this.settleMoney = settleMoney;
			this.payStatus = payStatus;
			this.settleStatus = settleStatus;
			this.extend1 = extend1;
			this.extend2 = extend2;
			this.extend3 = extend3;
			this.extend4 = extend4;
			this.settleStatusDesc = settleStatusDesc;
		}


		public String getSettleStatusDesc() {
			return settleStatusDesc;
		}


		public void setSettleStatusDesc(String settleStatusDesc) {
			this.settleStatusDesc = settleStatusDesc;
		}


		public body() {
			super();
		}
	}

}
