package com.common.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.common.util.GenericsUtils;
import com.common.util.WebUtils;
import com.common.web.page.Page;
import com.common.web.page.PageUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


/**
 * @className:BaseAction.java
 * @classDescription:父类Action,包括一些通用的方法
 * @author:longzy
 * @createTime:2012-8-24
 */
@AllowedMethods(value="regex:.*")
@SuppressWarnings("serial")
@ParentPackage("frame-default")
@Controller
@Scope("prototype")//保证每一个请求有一个单独的Action来处理,避免struts中Action的线程安全问题
public abstract class BaseAction<T> extends ActionSupport  implements  ServletRequestAware, ServletResponseAware,ModelDriven<T>{
	protected T obj;
	private Class<T> clazz;
	public Page page=new Page(15);//默认一页15条数据记录
	/** 进行增删改操作后,以redirect方式重新打开action默认页的result名.*/
	public static final String RELOAD = "reload";
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	/**
	 * Action函数, 默认的action函数, 默认调用list()函数.
	 */
	public String execute() throws Exception {
		return list();
	}

	//-- CRUD Action函数 --//
	/**
	 * Action函数,显示Entity列表界面.
	 * return SUCCESS.
	 */
	public abstract String list() throws Exception;
	
	//--  简化取值----//
	/**
	 * 取得HttpRequest中Parameter的简化方法.
	 */
	public  String getParameter(String name) {
		return this.request.getParameter(name);
	}
	/**
	 * 取得HttpRequest中Attribute的简化函数.
	 */
	public  Object getRequestAttribute(String name) {
		return request.getAttribute(name);
	}
	/**
	 * 取得HttpSession中Attribute的简化函数.
	 */
	public  Object getSessionAttribute(String name) {
		 return this.getSession().getAttribute(name);
	}
	/**
	 * 取得HttpSession的简化函数.
	 */
	public  HttpSession getSession() {
		return request.getSession();
	}


	/**
	 * 设置HttpRequest中Attribute的简化函数.
	 */
	public  void setRequestAttribute(String key,Object object) {
			 request.setAttribute(key,object);
	}
	/**
	 * 设置HttpSession中Attribute的简化函数.
	 */
	public  void setSessionAttribute(String name,Object object) {
			getSession().setAttribute(name,object);
	}
	/**
	 * 获取根目录
	 */
	public String getRoot(){
		return request.getContextPath();
	}
	/**
	 * 获取根目录
	 */
	public String getRealRoot(){
		return this.getSession().getServletContext().getRealPath("/");
	}

	//-------自动生成----------//
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		page.setRoot(getRoot());
		this.setRequestAttribute("pageTag", PageUtil.getTag(page));//分页标签
		this.page = page;
	}
	
	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}
	@SuppressWarnings("unchecked")
	public T getModel(){//getModel方法是ModelDriven类中的一个方法，用它后，主要是把null值的实体初始化
		try {
			if(obj==null){
				clazz =GenericsUtils.getSuperClassGenricType(getClass());
				obj= clazz.newInstance();
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return obj;
	}
	public void writeJsonReturn(String message,Object object){
		WebUtils.writeJsonReturn(message, object,request, response);
	}
	public void writeError(String message){
		WebUtils.writeError(message,request, response);
	}
}
