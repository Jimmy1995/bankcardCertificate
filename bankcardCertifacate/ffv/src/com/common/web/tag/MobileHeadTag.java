package com.common.web.tag;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.common.util.template.JspTemplateManager;

public class MobileHeadTag extends TagSupport {
	private static final long serialVersionUID = -5856585831506115582L;
	private String headjsp = "/bjxt/head.jsp";
	private String title;

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		Writer writer = new StringWriter();
		JspTemplateManager.getInstance().buildFile(headjsp, writer,
				pageContext.getRequest(),
				(HttpServletResponse) pageContext.getResponse());
		try {
			if(!StringUtils.isEmpty(title)){
				writer.append("<title>"+title+"</title>");
			}
			pageContext.getOut().write(writer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
