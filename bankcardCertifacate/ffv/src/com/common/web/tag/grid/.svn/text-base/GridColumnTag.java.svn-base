package com.common.web.tag.grid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;

import com.common.base.BaseException;
import com.common.web.tag.BaseTag;

public class GridColumnTag extends BaseTag implements Serializable,Cloneable{
	private static final long serialVersionUID = 8768573762677154714L;
	private List<GridColumnTag> columns;
	private Integer subColnum=0;//有多少子列
	private Integer subRownum=1;//有多少子行
	private String order;//desc、asc
	private String orderName;//排序的name
	private Boolean orderable=true;//此字段是否允许排序
	private String width;

	@Override
	public int doEndTag() throws JspException {
		Object parent = getParent();
		if (parent instanceof GridTag) {//直接列
			if(this.getChildren()!=null){//没有父列只有子列（嵌套列），把子列返回给父标签
				for(Map col:this.getChildren()){
					((GridTag)parent).addChildren(col);//给datatables插件取数用
				}
			}else{//没有父列也没有子列
				addToParent((GridTag)parent,this);//给datatables插件取数用
			}
			((GridTag)parent).addColumns(this);//用来在jsp页面上绘画th表头
		}else if(parent instanceof GridColumnTag){//被嵌套的列
			GridColumnTag gt=((GridColumnTag) parent);
			gt.addColumns(this);//用来在jsp页面上绘画th表头
			gt.setSubColnum(gt.getSubColnum()+(this.getSubColnum()==0?1:this.getSubColnum()));//父列的跨列数加上当前子列数
			if(gt.getSubRownum()==1){//计算出子行个数(包括本身)
				gt.setSubRownum(gt.getSubRownum()+this.getSubRownum());
			}else if(gt.getSubRownum()+this.getSubRownum()>gt.getSubRownum()){
				gt.setSubRownum(gt.getSubRownum()+this.getSubRownum()-1);
			}
			if(this.getChildren()!=null){
				for(Map col:this.getChildren()){
					gt.addChildren(col);//给datatables插件取数用
				}
			}else{
				addToParent(gt,this);//给datatables插件取数用
			}
		}else{
			throw new BaseException("列标签必须被包含在<sys:grid>或<sys:grid-column>标签中!");
		}
		destroy();
		return EVAL_PAGE;
	}
	
	public void addToParent(BaseTag parent,GridColumnTag children){
		Map<String,Object> map=new HashMap<String,Object>();
		if(!StringUtils.isEmpty(children.getName())){
			map.put("mDataProp", children.getName());
		}
		if(!StringUtils.isEmpty(children.getCssClass())){
			map.put("sClass", children.getCssClass());
		}
		if(!StringUtils.isEmpty(orderName)){//如果有设置排序字段就取排序字段
			map.put("orderName", orderName);
		}else{//如果没有就取原字段
			map.put("orderName", children.getName());
		}
		if(!StringUtils.isEmpty(children.getFnRender())){
			map.put("render", "function(data, type, row){return "+children.getFnRender()+"(data, type, row);}");
		}
		map.put("sDefaultContent", null==getBodyContent()?"":getBodyContent().getString());//防止后台没有这个字段时报错
	    parent.addChildren(map);
	}
	
	public void destroy(){
		name=null;
		cssClass=null;
		children=null;
		columns=null;
		subColnum=0;
		subRownum=1;
	}
	public List<GridColumnTag> getColumns() {
		return columns;
	}
	public void setColumns(List<GridColumnTag> columns) {
		this.columns = columns;
	}
	public void addColumns(GridColumnTag column) {
		if(null==this.columns){
			this.columns=new ArrayList<GridColumnTag>();
		}
		this.columns.add((GridColumnTag)column.clone());
	}
	
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	@Override
	public int doStartTag() throws JspException {
		return EVAL_PAGE;
	}
	public Boolean getOrderable() {
		return orderable;
	}

	public void setOrderable(Boolean orderable) {
		this.orderable = orderable;
	}
	public Integer getSubColnum() {
		return subColnum;
	}

	public void setSubColnum(Integer subColnum) {
		this.subColnum = subColnum;
	}

	public Integer getSubRownum() {
		return subRownum;
	}

	public void setSubRownum(Integer subRownum) {
		this.subRownum = subRownum;
	}
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
	@Override
	public Object clone() {  
		try {   
            return super.clone();   
        } catch (CloneNotSupportedException e) {   
            return null;   
        }       
	}  
}
