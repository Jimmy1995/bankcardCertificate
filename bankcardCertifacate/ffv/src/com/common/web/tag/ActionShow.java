package com.common.web.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
/**
 * @author longzy
 * @description 查询某条URL是否在用户的操作范围
 */
public class ActionShow extends TagSupport{
	private static final long serialVersionUID = 3031282485093954266L;
	private String url;
	private String ok="1";
	private String no="0";
	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		Map<String, String> userUrlMap = (HashMap<String, String>) pageContext.getSession().getAttribute("userUrlMap");
		try {
			if (userUrlMap!=null&&userUrlMap.containsKey(url)) {
				pageContext.getOut().write(ok);
			}else{
				pageContext.getOut().write(no);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return super.doStartTag();
	}


	/**
	 * @return the actionUrl
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param actionUrl
	 *            the actionUrl to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the ok
	 */
	public String getOk() {
		return ok;
	}


	/**
	 * @param ok the ok to set
	 */
	public void setOk(String ok) {
		this.ok = ok;
	}


	/**
	 * @return the no
	 */
	public String getNo() {
		return no;
	}


	/**
	 * @param no the no to set
	 */
	public void setNo(String no) {
		this.no = no;
	}
}
