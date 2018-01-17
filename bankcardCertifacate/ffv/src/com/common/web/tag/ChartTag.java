package com.common.web.tag;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.common.util.template.JspTemplateManager;

public class ChartTag extends TagSupport{
	private static final long serialVersionUID = 462737074578049316L;
	private String chartjsp = "/commons/template/charts/";
	private String id;
	private String title;
	private String chartsType;
	private String series;
	private String unit;
	private String cssStyle;
	private String xAxis;
	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		putParam();
		Writer writer = new StringWriter();
		try {	
			JspTemplateManager.getInstance().buildFile(chartjsp+chartsType+".jsp", writer,
				pageContext.getRequest(),
				(HttpServletResponse) pageContext.getResponse());
			String x=writer.toString();
			writer.flush();
			pageContext.getOut().write(x);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
	public void putParam(){
		Map<String,String> map=new HashMap<String,String>();
		map.put("id", id);
		map.put("title", title);
		map.put("chartsType", chartsType);
		map.put("series", series);
		map.put("unit", unit);
		map.put("cssStyle", cssStyle);
		map.put("xAxis", xAxis);
		pageContext.getRequest().setAttribute("chartParamMap", map);
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getChartsType() {
		return chartsType;
	}

	public void setChartsType(String chartsType) {
		this.chartsType = chartsType;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}
	public String getxAxis() {
		return xAxis;
	}

	public void setxAxis(String xAxis) {
		this.xAxis = xAxis;
	}
}
