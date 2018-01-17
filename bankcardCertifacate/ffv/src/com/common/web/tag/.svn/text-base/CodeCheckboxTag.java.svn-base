package com.common.web.tag;

import java.util.Map;

import org.apache.struts2.views.jsp.ui.CheckboxListTag;

import com.common.web.cache.CacheUtil;

public class CodeCheckboxTag extends CheckboxListTag{
	private static final long serialVersionUID = -4401301748287523157L;
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
