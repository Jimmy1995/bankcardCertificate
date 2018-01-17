package com.common.web.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class BaseTag extends BodyTagSupport{
	private static final long serialVersionUID = 6520911635801533948L;
	protected String name;
	protected String cssClass;
	protected String cssStyle;
	protected String fnRender;
	protected String display;
	protected List<Map<String, Object>> children;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the cssClass
	 */
	public String getCssClass() {
		return cssClass;
	}
	/**
	 * @param cssClass the cssClass to set
	 */
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	/**
	 * @return the fnRender
	 */
	public String getFnRender() {
		return fnRender;
	}
	/**
	 * @param fnRender the fnRender to set
	 */
	public void setFnRender(String fnRender) {
		this.fnRender = fnRender;
	}
	/**
	 * @return the display
	 */
	public String getDisplay() {
		return display;
	}
	/**
	 * @param display the display to set
	 */
	public void setDisplay(String display) {
		this.display = display;
	}
	public final void addChildren(Map<String, Object> children) {
	    if (this.children == null) {
	      this.children = new ArrayList<Map<String, Object>>();
	    }
	    this.children.add(children);
	}
	/**
	 * @return the children
	 */
	public List<Map<String, Object>> getChildren() {
		return children;
	}
	/**
	 * @param cols the children to set
	 */
	public void setChildren(List<Map<String, Object>> children) {
		this.children = children;
	}
	/**
	 * @return the cssStyle
	 */
	public String getCssStyle() {
		return cssStyle;
	}
	/**
	 * @param cssStyle the cssStyle to set
	 */
	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}
	public String body()
	  {
	    BodyContent body = getBodyContent();

	    if (body != null)
	      return body.getString();

	    return "";
	  }
}
