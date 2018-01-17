package com.x2x.api.model.res;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.x2x.api.model.ResHead;
/**
 * 空报文体
 * @author longzy
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmptyResponse {
	private ResHead head;
	private String data;
	
	public EmptyResponse() {
		super();
	}
	public EmptyResponse(ResHead head, String data) {
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}