package testFfv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 响应协议头
 * @author longzy
 */
@XmlRootElement(name = "head")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResHead {
	private String respCd;
	private String respMsg;
	private String reqNo;
	private String respNo;
	private String sign;
	public String getRespCd() {
		return respCd;
	}
	public void setRespCd(String respCd) {
		this.respCd = respCd;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getReqNo() {
		return reqNo;
	}
	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	public String getRespNo() {
		return respNo;
	}
	public void setRespNo(String respNo) {
		this.respNo = respNo;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public ResHead(String respCd, String respMsg, String reqNo, String respNo,
			String sign) {
		super();
		this.respCd = respCd;
		this.respMsg = respMsg;
		this.reqNo = reqNo;
		this.respNo = respNo;
		this.sign = sign;
	}
	public ResHead() {
		super();
	}
}