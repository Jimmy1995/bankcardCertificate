package com.common.web.tag;

import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.views.jsp.ui.SelectTag;

import com.common.web.cache.CacheUtil;

public class CodeSelectTag extends SelectTag {
	@Override
	public int doStartTag() throws JspException {
		if(StringUtils.isEmpty(cssClass)){
			cssClass="form-control input-sm";
		}
		return super.doStartTag();
	}

	private static final long serialVersionUID = -4693229492582261795L;
	protected String codeType = null;
	protected void populateParams() {
		Map codemap =CacheUtil.getDic(codeType);
		if (codemap == null) {
			this.list = "#{}";
		} else {
			String codeName = String.format("_codetable_%1$s_%2$s",new Object[] {codeType, this.id });
			this.pageContext.getRequest().setAttribute(codeName, codemap);
			this.list = "#request." + codeName;
		}
		this.listKey = "key";
		this.listValue = "value";
		super.populateParams();
	}
	@Override
	public int doEndTag() throws JspException{
//		try {
//			pageContext.getOut().write("<script type=\"text/javascript\">$(function(){$(\"#"+this.id+"\").select2({language: \"zh-CN\"});});</script>");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return super.doEndTag();
	}

	/**
	 * @return the codeType
	 */
	public String getCodeType() {
		return codeType;
	}

	/**
	 * @param codeType
	 *            the codeType to set
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
}
