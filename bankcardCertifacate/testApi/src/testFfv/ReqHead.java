package testFfv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 请求协议头
 * @author longzy
 */
@XmlRootElement(name = "head")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReqHead {
	private String appId;
	private String version;
	private String reqType;
	private String mchid;
	private String reqNo;
	private String backURL;
	private String sign;
	private String signType;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public String getMchid() {
		return mchid;
	}
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	public String getReqNo() {
		return reqNo;
	}
	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	public String getBackURL() {
		return backURL;
	}
	public void setBackURL(String backURL) {
		this.backURL = backURL;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	
	public ReqHead(String appId, String version, String reqType,
			String mchid, String reqNo, 
			String backURL, String sign, String signType) {
		super();
		this.appId = appId;
		this.version = version;
		this.reqType = reqType;
		this.mchid = mchid;
		this.reqNo = reqNo;
		this.backURL = backURL;
		this.sign = sign;
		this.signType = signType;
	}
	public ReqHead() {
		super();
	}
}