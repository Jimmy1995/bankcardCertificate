package com.x2x.api.model.req;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.x2x.api.model.ReqHead;
/**
 * 空报文体
 * @author longzy
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmptyRequest {
	private ReqHead head;
	private String data;
	
	public EmptyRequest() {
		super();
	}

	public EmptyRequest(ReqHead head, String data) {
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
