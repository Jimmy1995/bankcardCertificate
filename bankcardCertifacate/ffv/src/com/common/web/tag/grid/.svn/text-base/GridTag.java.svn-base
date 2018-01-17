package com.common.web.tag.grid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;

import com.common.util.json.JsonUtils;
import com.common.web.tag.BaseTag;


public class GridTag extends BaseTag {
	private static final long serialVersionUID = -4608130558374503062L;
	private String action;
	private int pageSize;
	private String height="";
	private String formId;
	private String width;
	private Boolean mutipleSelect=false;
	private String lengthMenu="[10, 25, 50, 100]";
	private List<GridColumnTag> columns;
	private GridToolBarTag toolBar;
	private String orders;
	private List<Integer> disorderTarget=new ArrayList();
	private Boolean showPagesize=true;
	/** 
     *  doStartTag()方法返回一个整数值，用来决定程序的后续流程。  
     *　A.Tag.SKIP_BODY：表示标签之间的内容被忽略  
     *　B.Tag.EVAL_BODY_INCLUDE：表示标签之间的内容被正常执行  
     */  
	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}
  /**  
   *  doEndTag：当JSP容器遇到自定义标签的结束标志，就会调用doEndTag()方法。doEndTag()方法也返回一个整数值，用来决定程序后续流程。  
　　  *  A.Tag.SKIP_PAGE：表示立刻停止执行网页，网页上未处理的静态内容和JSP程序均被忽略任何已有的输出内容立刻返回到客户的浏览器上。  
　　  *  B.Tag.EVAL_PAGE：表示按照正常的流程继续执行JSP网页  
   */ 
	@Override
	public int doEndTag() throws JspException {
		StringBuffer inner=new StringBuffer();
		inner.append("id='"+id+"'");
		if(cssClass!=null){
			inner.append(" class='"+cssClass+"'");
		}else{
			inner.append(" class='its'");
		}
		if(!StringUtils.isEmpty(width)){
			inner.append(" width ='"+width+"'");
		}
		try {
			pageContext.getOut().write("<table "+inner.toString()+" >\n<thead>\n");
			if(toolBar!=null){//工具栏
				toolBar.setColspan(columns.size());
				pageContext.getOut().write(toolBar.toString());
			}
			pageContext.getOut().write(getHeader(columns));//表头
			pageContext.getOut().write("</thead>\n<tbody></tbody>\n</table>");
			if(pageSize==0){//如果没有指定每页多少条记录，就取下拉页中的第一个值
				setPageSize((Integer)JsonUtils.deserialize(lengthMenu, List.class).get(0));
			}
			String json=JsonUtils.toString(children);
			json=json.replaceAll("\"(function\\b[\\s\\S]+?})\"", "$1");
			if(StringUtils.isEmpty(orders)){//初始排序
				orders="[0,'desc']";
			}
			pageContext.getOut().write("<script type=\"text/javascript\">"
			+" var "+id+"_disorderTarget="+JsonUtils.toString(disorderTarget)	+";"
			+" var "+id+"_json="+json+";\n var "+id+"_mutipleSelect="+mutipleSelect+";var "+id+"_orders=new Array("+orders+");"
			+" var "+id+"_showPagesize="+showPagesize+";"
			+" var "+id+"=$.fn.dataTableGrid({showPagesize:"+id+"_showPagesize,disorderTarget:"+id+"_disorderTarget,order:"+id+"_orders,lengthMenu:'"+lengthMenu+"',formId:'"+formId+"',action:'"+action+"',json:"+id+"_json,id:'"+id+"',pageSize:"+pageSize+",scrollY:\""+getHeight()+"\"});</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		destroy();
		return EVAL_PAGE;
	}
	/**
	 * 计算表头
	 * @param columns
	 * @return
	 */
	private String getHeader(List<GridColumnTag> columns){
		List<GridColumnTag> columnsNew=new ArrayList<GridColumnTag>();
		String str="<tr>\n";
		Integer maxRow=1;
		for(GridColumnTag column:columns){//找出总共多少行
			if(column.getSubRownum()>maxRow){
				maxRow=column.getSubRownum();
			}
		}
		int i=0;
		int realcol=0;
		for(GridColumnTag column:columns){//构建表头列
			Integer rowspan=(1==column.getSubRownum()?maxRow:column.getSubRownum()-maxRow+1);
			Integer colspan=(0==column.getSubColnum()?1:column.getSubColnum());
			realcol+=colspan;
			str+="<th name=\""+column.getName()+"\" rowspan=\""+rowspan+"\" colspan=\""+colspan+"\"";
			if(!StringUtils.isEmpty(column.getWidth())){
				str+=" width=\""+column.getWidth()+"\"";
			}
			str+=" >"+column.getDisplay()+"</th>\n";
			if(column.getColumns()!=null&&column.getColumns().size()>0){
				columnsNew.addAll(column.getColumns());
			}
			if(!StringUtils.isEmpty(column.getOrder())){//找到排序字段
				orders="["+(realcol-1)+",'"+column.getOrder()+"']";
			}
			if(false==column.getOrderable()){//如果字段禁用排序
				disorderTarget.add(realcol-1);
			}
			i++;
		}
		str+="</tr>\n";
		if(columnsNew!=null&&columnsNew.size()>0){
			str+=getHeader(columnsNew);
		}
		return str;
	}
	public void destroy(){
		children=null;
		id=null;
		cssClass=null;
		action=null;
		columns=null;
		disorderTarget=new ArrayList();
		mutipleSelect=false;
		orders=null;
		toolBar=null;
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public Boolean getMutipleSelect() {
		return mutipleSelect;
	}
	public void setMutipleSelect(Boolean mutipleSelect) {
		this.mutipleSelect = mutipleSelect;
	}
	public String getLengthMenu() {
		return lengthMenu;
	}
	public void setLengthMenu(String lengthMenu) {
		this.lengthMenu = lengthMenu;
	}
	public List<GridColumnTag> getColumns() {
		return columns;
	}
	public void setColumns(List<GridColumnTag> columns) {
		this.columns = columns;
	}
	public String getOrders() {
		return orders;
	}
	public void setOrders(String orders) {
		this.orders = orders;
	}
	public void addColumns(GridColumnTag column) {
		if(null==this.columns){
			this.columns=new ArrayList<GridColumnTag>();
		}
		this.columns.add((GridColumnTag)column.clone());
	}
	public Boolean getShowPagesize() {
		return showPagesize;
	}
	public void setShowPagesize(Boolean showPagesize) {
		this.showPagesize = showPagesize;
	}
	public GridToolBarTag getToolBar() {
		return toolBar;
	}
	public void setToolBar(GridToolBarTag toolBar) {
		this.toolBar = toolBar;
	}
}
