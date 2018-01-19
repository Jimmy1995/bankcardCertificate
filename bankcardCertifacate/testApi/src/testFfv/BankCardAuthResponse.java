package testFfv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @DESC 银行卡实名认证 响应实体
 * @AUTHOR JIANGCW
 * @DATE 2018-1-11下午05:45:19
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class BankCardAuthResponse {
	private ResHead head;
	private body data;
	
	public BankCardAuthResponse() {
		super();
	}

	public BankCardAuthResponse(ResHead head, body data) {
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
		private String extend1;
		private String extend2;
		private String extend3;
		private String extend4;
		private String extend5;
		public body(String extend1, String extend2,
				String extend3, String extend4, String extend5) {
			super();
			this.extend1 = extend1;
			this.extend2 = extend2;
			this.extend3 = extend3;
			this.extend4 = extend4;
			this.extend5 = extend5;
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
		public body() {
			super();
		}
	}
}