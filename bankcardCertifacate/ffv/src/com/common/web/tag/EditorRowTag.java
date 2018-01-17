package com.common.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;

public class EditorRowTag extends BaseTag {
	private static final long serialVersionUID = 2947557291131322934L;

	public int doStartTag() throws JspException {
		StringBuffer str = new StringBuffer();
		str.append("<tr ");
		if (!StringUtils.isEmpty(this.cssClass)) {
			str.append(" class='"+this.cssClass+"'");
		}
		if (!StringUtils.isEmpty(this.id)) {
			str.append(" id='" + this.id+"'");
		}
		if (!StringUtils.isEmpty(this.cssStyle)) {
			str.append(" style='"+cssStyle+"'");
		}
		str.append(" >");
		try {
			pageContext.getOut().write(str.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().write("</tr>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}
}
