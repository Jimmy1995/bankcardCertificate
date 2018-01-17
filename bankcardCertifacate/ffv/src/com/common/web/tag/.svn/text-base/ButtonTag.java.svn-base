package com.common.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

public class ButtonTag extends TagSupport {
	private static final long serialVersionUID = -5858120552433092495L;
	private String id;
	private String name;
	private String cssStyle;
	private String cssClass="btn btn-info btn-sm";
	private String value;
	private String type="button";
	private String icon;
	private String iconalign;
	@Override
	public int doStartTag() throws JspException {
		
		
		String str = "<button class='"+cssClass+"' id='" + getId() + "' name='"
				+ getName() + "' type='"+getType()+"'>";
		try {
			pageContext.getOut().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			String str=getValue();
			if(!StringUtils.isEmpty(icon)){
				if("right".equals(iconalign)){
					str+="<span class=\""+icon+"\" aria-hidden=\"true\"></span>";
				}else{
					str="<span class=\""+icon+"\" aria-hidden=\"true\"></span>"+str;
				}
			}
			pageContext.getOut().write(str+"</button>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconalign() {
		return iconalign;
	}

	public void setIconalign(String iconalign) {
		this.iconalign = iconalign;
	}
}