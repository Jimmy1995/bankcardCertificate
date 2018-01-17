package com.common.web.tag.dropdown;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.common.web.tag.BaseTag;

public class DropdownItemTag extends BaseTag {
	private static final long serialVersionUID = -4488733390529391049L;
	private String value;
	private String onclick;
	private Boolean isDivider; 
	@Override
	public int doStartTag() throws JspException {
		String dropstr="";
		if(getIsDivider()!=null&&getIsDivider()){
			 dropstr="<li class=\"divider\"></li>";
		}else{
		     dropstr = "<li><a href=\"#\" id=\"" + this.getId() + "\" name=\""
					+ this.getName() + "\" onclick=\"" + this.getOnclick() + "\">"
					+ this.getValue() + "</a></li>";
		}
		try {
			pageContext.getOut().write(dropstr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}
	public Boolean getIsDivider() {
		return isDivider;
	}

	public void setIsDivider(Boolean isDivider) {
		this.isDivider = isDivider;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
