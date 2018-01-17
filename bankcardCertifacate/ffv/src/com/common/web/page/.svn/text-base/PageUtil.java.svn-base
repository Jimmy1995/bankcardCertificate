package com.common.web.page;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.common.base.BaseException;


/**
 * @className:PageUtil.java
 * @classDescription:实现分页接口
 * @author:longzy
 * @createTime:2012-9-7
 */
public class PageUtil{
	private static ThreadLocal<Page> pagLocal = new ThreadLocal<Page>();
	public static final Integer default_pageSize=10; //默认分页条数
	public static void setPage(HttpServletRequest request,Integer pageSize) {
		Page page=new Page(default_pageSize);
		String pageNo = request.getParameter("pageNo");
        String order = request.getParameter("order");
        if(pageSize==null){
        	String pageSizeStr = request.getParameter("pageSize");
        	if(!StringUtils.isEmpty(pageSizeStr)){
            	try{
            		page.setPageSize(Integer.parseInt(pageSizeStr));
            	}catch (Exception e) {
            		throw new BaseException("分页总页数非法字符");
    			}
            }
        }else{
        	page.setPageSize(pageSize);
        }
        
        if(!StringUtils.isEmpty(pageNo)){
        	try{
        		page.setPageNo(Integer.parseInt(pageNo));
        	}catch (Exception e) {
				throw new BaseException("分页页码非法字符！");
			}
        }
        if(!StringUtils.isEmpty(order)){
        	page.setOrder(order);
        }
        pagLocal.set(page);
	}
	public static Page getPage() {
		Page page=pagLocal.get();
		if(page==null){
			page=new Page(10);
		}
		return page;
	}
	/**
	 * 获取分页显示标签
	 * 
	 * @param page
	 *            page对象
	 * @return
	 */
	public static String getTag(Page page) {
		 StringBuffer tag=new StringBuffer();
		 tag.append("<table width='100%'>" +
		 			   "<tr>");
		 
				 tag.append("<td name=\"pageLeft\" align='left'>" +
				 				"<table height='22' border='0' cellpadding='4' cellspacing='0' style='font:14px'>" +
				 					"<tr>");
							 tag.append("<td height='22' valign='middle'>");
								 tag.append("共<font color='red'>" + page.getTotalCount() + "</font>条  每页<font color='red'>" + page.getPageSize() + "</font>条  当前"
										    +"<font color='red'>" + page.getPageNo() + "/" + page.getTotalPages() + "</font>页  ");
							 tag.append("</td>");
							 
							 tag.append("<td height='22' valign='middle'>");
							 tag.append("<span><input  onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\"  style='height:20px; width:25px;'  height='20'  id=\"pageSize\" name=\"pageSize\" type=\"text\" size=\"1\" value=\""+page.getPageSize()+"\"/></span>");
						     tag.append("</td>");
						     tag.append("<td  height='22' valign='middle' nowrap='nowrap'><a style='text-decoration:none;' href='javascript:void(0)'>");
							 tag.append("<img border='0' height='20' onClick=\"javascript:myFireEvent(document.listForm,'onsubmit');document.listForm.submit();\" src=\""+page.getRoot()+"/images/page/sz.gif\">");
							 tag.append("</a>" +//点击下面这个就是导出excel
							 		"&nbsp;&nbsp;<a style='text-decoration:none;' href='javascript:void(0)'><img border='0' height='20' onClick=\"exportGridView('excel');\"  src=\""+page.getRoot()+"/images/page/excel.gif\"/></a></td>");
						 tag.append("</tr>" +
							    "</table>" +
				 		    "</td>");
			
						 
				 tag.append("<td name=\"pageRight\" align='right'>" +
				 				"<table height='22' border='0' cellpadding='4' cellspacing='0' style='font:14px'>" +
				 					"<tr>");
								 tag.append("<td  height='22' valign='middle'><a style='text-decoration:none;' href='javascript:void(0)'>");
								 tag.append("<img border='0' height='20' onClick=\"javascript:jump(1);\" src=\""+page.getRoot()+"/images/page/page_first_1.gif\">");
								 tag.append("</a></td>");
								 tag.append("<td  height='22' valign='middle'><a style='text-decoration:none;' href='javascript:void(0)'>");
								 tag.append("<img border='0' height='20' onClick=\"javascript:jump("+ page.getPrePage() +");\" src=\""+page.getRoot()+"/images/page/page_back_1.gif\">");
								 tag.append("</a></td>");
								 tag.append("<td  height='22' valign='middle'><a style='text-decoration:none;' href='javascript:void(0)'>");
								 tag.append("<img border='0' height='20' onClick=\"javascript:jump("+ page.getNextPage() +");\" src=\""+page.getRoot()+"/images/page/page_next.gif\">");
								 tag.append("</a></td>");
								 tag.append("<td  height='22' valign='middle'><a style='text-decoration:none;' href='javascript:void(0)'>");
								 tag.append("<img border='0' height='20' onClick=\"javascript:jump("+ page.getTotalPages() +");\" src=\""+page.getRoot()+"/images/page/page_last.gif\">");
								 tag.append("</td>");
								 tag.append("<td  height='22' valign='middle'>");
								 tag.append("到第");
								 tag.append("</td>");
								 tag.append("<td  height='22' valign='middle'>");
								 tag.append("<span><input onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\" style='height:20px; width:25px;'  height='20'  id=\"pageNo\" name=\"pageNo\" type=\"text\" size=\"1\" value=\""+page.getPageNo()+"\"/></span>");
								 tag.append("</td>");
								 tag.append("<td  height='22' valign='middle'>");
								 tag.append("页");
								 tag.append("</td>");
								 tag.append("<td  height='22' valign='middle'>");
								 tag.append("</a></td>");
								 tag.append("<td  height='22' valign='middle'><a style='text-decoration:none;' href='javascript:void(0)'>");
								 tag.append("<img border='0' height='20' onClick=\"javascript:jump(document.getElementById('pageNo').value);\" src=\""+page.getRoot()+"/images/page/go.gif\">");
								 tag.append("</a></td>");
						tag.append("</tr>" +
				 		   "</table>" +
				 		 "</td>");
				 
		 
		  tag.append("</tr>" +
			   "</table>");
		 tag.append("<input type='hidden' name='order' id='order' value='"+page.getOrder()+"'>");//这个保存排序的
		 tag.append("<input type='hidden' name='gridViewExport' id='gridViewExport'/>");//保存导出类型
		 tag.append("<script language=\"javascript\">");
		 	 //跳到某页
			 tag.append(" function jump(count){ ");
			 tag.append(" document.getElementById('pageNo').value=count;");
			 tag.append(" myFireEvent(document.listForm,'onsubmit');document.listForm.submit();");
			 tag.append(" }");
			 //排序
			 tag.append(" function order(order){ ");
			 tag.append(" document.getElementById('order').value=order;");
			 tag.append(" myFireEvent(document.listForm,'onsubmit');document.listForm.submit();");
			 tag.append(" }");
			 //document.all ? 'IE' : 'others'
			 //导出数据excel
			 tag.append(" function exportGridView(type){ ");
			 tag.append(" document.getElementById('gridViewExport').value=type;");
			 tag.append(" document.listForm.submit();" +
			 			" document.getElementById('gridViewExport').value='';");
			 tag.append(" }");
			 
			 tag.append(" function myFireEvent(obj,event1){ ");
			 tag.append(" if(document.all){obj.fireEvent(event1);}else{var tevent=document.createEvent('HTMLEvents'); tevent.initEvent('submit',true,true);obj.dispatchEvent(tevent);}");  
			 tag.append(" }");
		 tag.append("</script>");
		return tag.toString();
	}
}
