package com.common.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;

public class TextFieldTag extends org.apache.struts2.views.jsp.ui.TextFieldTag{
	private static final long serialVersionUID = -4834825710302437164L;
	private Boolean showTip=false;
	private Integer colspan=1;
	private String icon;
	@Override
	public int doStartTag() throws JspException {
		if(!StringUtils.isEmpty(label)){
			if(StringUtils.isEmpty(cssClass)){
				cssClass="form-control input-sm";
			}
			try {
				String str="<td class='tdLabel'>"+label+"</td><td class='tdInput' colspan='"+colspan+"'>";
				if(!StringUtils.isEmpty(icon)){
					str+="<div class=\"has-feedback\" >";
				}
				pageContext.getOut().write(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return super.doStartTag();
	}
	@Override
	public int doEndTag() throws JspException {
		super.doEndTag();
		if(!StringUtils.isEmpty(label)){
			try {
				String str="</td>";
				if(!StringUtils.isEmpty(icon)){
					str="<span class=\""+icon+" form-control-feedback\" ></span></div>"+str;
				}
				if(showTip){
					str+="<td><div id=\""+id+"Tip\"></div></td>";
				}
				pageContext.getOut().write(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return EVAL_PAGE;
	}
	public Boolean getShowTip() {
		return showTip;
	}
	public void setShowTip(Boolean showTip) {
		this.showTip = showTip;
	}
	public Integer getColspan() {
		return colspan;
	}
	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}
