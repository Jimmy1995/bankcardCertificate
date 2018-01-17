package com.common.web.page;

import java.io.Serializable;
import java.util.List;

/**
 * @className:Page.java
 * @classDescription:分页参数对象
 * @author:longzy
 * @createTime:2012-7-12
 */
public class Page implements Serializable{
	private static final long serialVersionUID = 2534766215247403238L;
	//-- 分页参数 --//
	protected int pageNo = 1;// 页数
	protected int startCount;
	protected int pageSize = 1;// 显示条数
	private String root;// 根目录
	protected String order;//设置排序字段，多个字段用，隔开 格式（name:desc,createDate:asc）
	@SuppressWarnings("unchecked")
	protected List result;//取得页内的记录列表.
	private String formAction;//form的action属性,为了做搜索引擎优化新增加的。为选填项
	/**
	 * @return the formAction
	 */
	public String getFormAction() {
		return formAction;
	}

	/**
	 * @param formAction the formAction to set
	 */
	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}
	//-- 返回结果 --//
	protected long totalCount = -1;// 总条数


	public Page(int pageSize) {
		this.pageSize = pageSize;
	}

	//-- 访问查询参数函数 --//
	/**
	 * 获得当前页的页号,序号如果大于总条数，则当前页定位到总页数
	 */
	public int getPageNo() {
		if(this.getTotalPages()>-1&&this.pageNo>this.getTotalPages()){		
			this.pageNo=(int) this.getTotalPages();
		}
		return pageNo;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
		
	}

	public Page pageNo(final int thePageNo) {
		setPageNo(thePageNo);
		return this;
	}

	/**
	 * 获得每页的记录数量,默认为1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量,低于1时自动调整为1.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	public Page pageSize(final int thePageSize) {
		setPageSize(thePageSize);
		return this;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	public int getFirst() {
		return ((pageNo - 1) * pageSize) + 1;
	}


	/**
	 * 获得排序方向.
	 */
	public String getOrder() {
		 if(order==null){
			 return "";
		 }
		return order;
	}

	/**
	 * 设置排序方式向.
	 * 
	 * @param order 可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(final String order) {
		this.order = order;
	}

	//-- 访问查询结果函数 --//

	/**
	 * 取得页内的记录列表.
	 */
	@SuppressWarnings("unchecked")
	public List getResult() {
		return result;
	}

	/**
	 * 设置页内的记录列表.
	 */
	@SuppressWarnings("unchecked")
	public void setResult(final List result) {
		this.result = result;
	}

	/**
	 * 取得总记录数, 默认值为-1.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总记录数.
	 */
	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public long getTotalPages() {
		if (totalCount < 0) {
			return -1;
		}

		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号, 序号从1开始.
	 * 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始.
	 * 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * @return the forwordName
	 */
	public String getRoot() {
		return root;
	}

	/**
	 * @param forwordName the forwordName to set
	 */
	public void setRoot(String root) {
		this.root = root;
	}
	public int getStartCount() {
		return startCount;
	}

	public void setStartCount(int startCount) {
		this.startCount = startCount;
	}
	@SuppressWarnings("unchecked")
	public Page(int pageSize, List result, long totalCount,int startCount,int pageNo) {
		this.pageSize = pageSize;
		this.result = result;
		this.totalCount = totalCount;
		this.startCount = startCount;
		this.pageNo=pageNo;
	}
	/**
	 * 这个方法还没用上	
	 * @param pageSize
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	public Page(int pageSize, List result) {
		this.result = result;
		this.totalCount = result.size();
		this.pageNo=(int)totalCount/pageSize+1;
		this.pageSize = pageSize;
	}
	
	public Page(){
	}
}
