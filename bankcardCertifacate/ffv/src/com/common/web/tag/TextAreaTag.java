package com.common.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;

public class TextAreaTag extends org.apache.struts2.views.jsp.ui.TextareaTag{
	private static final long serialVersionUID = -4834825710302437164L;
	private Boolean showTip=false;
	private Integer colspan=1;
	@Override
	public int doStartTag() throws JspException {
		if(StringUtils.isEmpty(cssClass)){
			cssClass="form-control";
		}
		try {
			pageContext.getOut().write("<td class='tdLabel'>"+label+"</td><td class='tdInput' colspan='"+colspan+"'>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
	@Override
	public int doEndTag() throws JspException {
		super.doEndTag();
		try {
			String str="</td>";
			if(showTip){
				str+="<td><div id=\""+id+"Tip\"></div></td>";
			}
			pageContext.getOut().write(str);
		} catch (IOException e) {
			e.printStackTrace();
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
}
