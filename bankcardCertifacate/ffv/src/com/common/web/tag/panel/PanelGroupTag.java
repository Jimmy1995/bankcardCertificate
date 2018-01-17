package com.common.web.tag.panel;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;

import com.common.web.tag.BaseTag;

public class PanelGroupTag extends BaseTag{
	private static final long serialVersionUID = -8003098225744392523L;
	@Override
	public int doStartTag() throws JspException {
		StringBuilder str = new StringBuilder();
		str.append("<div class='panel-group' ");
		if(!StringUtils.isEmpty(id)){
			str.append(" id='"+id+"'");
		}
		if(!StringUtils.isEmpty(name)){
			str.append(" name='"+name+"'");
		}
		if(!StringUtils.isEmpty(cssStyle)){
			str.append(" style='"+cssStyle+"' ");
		}
		str.append(" role=\"tablist\" aria-multiselectable=\"true\">");
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