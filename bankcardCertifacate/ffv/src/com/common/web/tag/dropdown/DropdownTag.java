package com.common.web.tag.dropdown;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.common.web.tag.BaseTag;

public class DropdownTag extends BaseTag{
	private static final long serialVersionUID = -125115525383559124L;
	private String value;
	private String onclick;
	private Boolean hasPrveButton;
	@Override
	public int doStartTag() throws JspException {
		String dropstr="<div class='btn-group'>";
			if(getHasPrveButton()!=null&&getHasPrveButton()){
				dropstr +="<button type='button' class='btn btn-info btn-sm' onclick=\""+this.getOnclick()+"\" id='"+this.getId()+"' name='"+this.getName()+"'>"+this.getValue()+"</button>"
						+"<button type='button' class='btn btn-info btn-sm dropdown-toggle' data-toggle='dropdown'>";
			}else{
				dropstr+="<button type='button' class='btn btn-info btn-sm dropdown-toggle' data-toggle='dropdown'>"
					   +getValue();
			}
					  
			  dropstr +="<span class='caret'></span>"
					  +"   <span class='sr-only'>Toggle Dropdown</span>"
					  +"</button>"
					  +"<ul class='dropdown-menu' role='menu'>";
		try {
			pageContext.getOut().write(dropstr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		String dropstr="</ul>"
						+"</div>";
		try {
			pageContext.getOut().write(dropstr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}
	public Boolean getHasPrveButton() {
		return hasPrveButton;
	}

	public void setHasPrveButton(Boolean hasPrveButton) {
		this.hasPrveButton = hasPrveButton;
	}
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

}
