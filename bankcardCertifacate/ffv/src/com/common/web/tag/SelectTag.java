package com.common.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;

public class SelectTag extends org.apache.struts2.views.jsp.ui.SelectTag{
	private static final long serialVersionUID = 6387015959642160720L;
	private Boolean showTip=false;
	private Integer colspan=1;
	@Override
	public int doStartTag() throws JspException {
		if(StringUtils.isEmpty(cssClass)){
			cssClass="form-control input-sm";
		}
		try {
			if(!StringUtils.isEmpty(label)){
				pageContext.getOut().write("<td class='tdLabel'>"+label+"</td>");
			}
			pageContext.getOut().write("<td class='tdInput' colspan='"+colspan+"'>");
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
