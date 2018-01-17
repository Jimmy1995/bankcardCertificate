package com.common.web.tag.grid;

import java.io.Serializable;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class GridToolBarTag extends BodyTagSupport implements Serializable{
	private static final long serialVersionUID = -7041117909794419681L;
	private int colspan;
	private String body;
	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}
	@Override
	public int doEndTag() throws JspException {
		Object parent = getParent();
		if (parent instanceof GridTag) {//直接列
			((GridTag)parent).setToolBar(this);
		}	
		return SKIP_BODY;
	}
	@Override
	public int doAfterBody () throws JspException {
        body=getBodyContent().getString ();
        return super.doAfterBody();
    }
	@Override
	public String toString() {
		return "<tr class='gridtoolbar'><th colspan='"+colspan+"'>"+body+"</th></tr>";
	}
	
	public void destroy(){
		body=null;
		colspan=1;
	}
	
	public int getColspan() {
		return colspan;
	}
	public void setColspan(int colspan) {
		this.colspan = colspan;
	}
}
