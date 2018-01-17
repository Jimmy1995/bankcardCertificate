package com.common.web.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.springframework.util.StringUtils;

import com.common.util.json.JsonUtils;

public class EditorLayoutTag extends BaseTag {
	private static final long serialVersionUID = -209657609942606396L;

	protected String cols;
	protected int colCount;
	protected String head;
	protected String foot;
	private String widthList; 
	public int doStartTag() throws JspException {
		String colsExp = this.cols;
		if (!(StringUtils.hasText(colsExp))) {
			colsExp = "6";
		}

		StringBuffer str = new StringBuffer();
		str.append("<table ");
		if (StringUtils.hasText(this.id)) {
			str.append(" id='"+id+"' ");
		}
		if (StringUtils.hasText(this.cssStyle)) {
			str.append(" style='"+cssStyle+"' ");
		} 
		if (StringUtils.hasText(this.cssClass)) {
			str.append(" class='"+cssClass+"' ");
		} else {
			str.append(" class='tableEditor' ");
		}
		str.append("><colgroup>");

		if (colsExp.indexOf(",") >= 0) {
			String[] cola = colsExp.split(",");
			setColCount(cola.length);
			for (String col : cola)
				str.append("<col width=\"").append(col).append("\" />");
		} else {
			int l;
			int col = Integer.parseInt(colsExp.trim());
			setColCount(col);
			if(null!=widthList&&!"".equals(widthList)){//如果有指定宽度
				List list=JsonUtils.deserialize(widthList, List.class);
				for(l=0;l<col;l++){
					str.append("<col width=\""+list.get(l)+"\" />");
				}
			}else if (col == 4) {
				for (l = 0; l < 2; ++l) {
					str.append("<col width=\"20%\" />");
					str.append("<col width=\"30%\" />");
				}
			} else if (col == 6) {
				for (l = 0; l < 3; ++l) {
					str.append("<col width=\"12%\" />");
					str.append("<col width=\"21.33%\" />");
				}
			} else if (col == 8) {
				for (l = 0; l < 4; ++l) {
					str.append("<col width=\"10%\" />");
					str.append("<col width=\"15%\" />");
				}
			} else if (col == 10) {
				for (l = 0; l < 10; ++l)
					str.append("<col width=\"10%\" />");
			} else if (col == 12) {
				for (l = 0; l < 12; ++l)
					str.append("<col class=\"col-td-1\" />");
			} else {
				int i = 0;
				int j = 0;
				int l1 = 0;
				i = 100 % col;
				j = 100 / col;
				l1 = j;
				if (i != 0) {
					++l1;
				}

				StringBuffer col1 = new StringBuffer("<col width=\"").append(j)
						.append("%\" />");
				StringBuffer col2 = new StringBuffer("<col width=\"")
						.append(l1).append("%\" />");
				for (int l2 = 0; l2 < col / 2; ++l2)
					str.append(col1).append(col2);
			}
		}

		str.append("</colgroup>");
		str.append("<tbody>");
		try {
			pageContext.getOut().write(str.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
			try {
				if (StringUtils.hasLength(this.head)){
					pageContext.getOut().write(this.head);
				}
				if (StringUtils.hasLength(this.foot)) {
					pageContext.getOut().write(this.foot);
				}
				pageContext.getOut().write("</tbody>");
				pageContext.getOut().write("</table>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		return super.doEndTag();
	}

	public void destroy() {
		this.cols = null;
		this.colCount = 0;
		this.head = null;
		this.foot = null;
	}

	public final int getColCount() {
		return this.colCount;
	}

	public final void setColCount(int colCount) {
		this.colCount = colCount;
	}

	public final String getHead() {
		return this.head;
	}

	public final void setHead(String head) {
		this.head = head;
	}

	public final String getFoot() {
		return this.foot;
	}

	public final void setFoot(String foot) {
		this.foot = foot;
	}
	public EditorLayoutTag() {
	}

	public static String tagName() {
		return "editorlayout";
	}

	public String getCols() {
		return this.cols;
	}

	public void setCols(String cols) {
		this.cols = cols;
	}

	public void setCols(int cols) {
		this.cols = String.valueOf(cols);
	}
	public String getWidthList() {
		return widthList;
	}

	public void setWidthList(String widthList) {
		this.widthList = widthList;
	}
}
