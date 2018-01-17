package com.common.web.tag.panel;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;

import com.common.web.tag.BaseTag;

public class PanelTag extends BaseTag{
	private static final long serialVersionUID = 4216725978594739664L;
	@Override
	public int doStartTag() throws JspException {
		StringBuilder str = new StringBuilder();
		str.append("<div class='panel ");
		if(!StringUtils.isEmpty(cssClass)){
			str.append(cssClass+" '");
		}else{
			str.append(" panel-info '");
		}
		if(!StringUtils.isEmpty(id)){
			str.append(" id='"+id+"'");
		}
		if(!StringUtils.isEmpty(name)){
			str.append(" name='"+name+"'");
		}
		if(!StringUtils.isEmpty(cssStyle)){
			str.append(" style='"+cssStyle+"' ");
		}
		str.append(">");
		try {
			pageContext.getOut().write(str.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}
	@Override
	public int doEndTag() throws JspException {
		String str="</div>";
		try {
			pageContext.getOut().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}
}
