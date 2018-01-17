package com.common.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.common.util.httpWrapper.SqlRequestWrapper;
/**
 * 防止SQL注入
 * @author 龙志勇
 */
public class SqlFilter implements Filter {
	private static String[] onlyFilter;//存储只过滤哪些地址
	private static String[] disFilter;//存储不被过滤的地址或关键字
	public void init(FilterConfig config) throws ServletException {
		String d=config.getInitParameter("disFilterUrl");
		if(d!=null&&!d.equals("")){
			disFilter=d.split(";");
		}
		String ol=config.getInitParameter("onlyFilterUrl");
		if(ol!=null&&!ol.equals("")){
			onlyFilter=ol.split(";");
			disFilter=null;
		}
	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if((null==disFilter)&&(null==onlyFilter)){//都没有配置当然就全部要过滤了
			SqlRequestWrapper reqRequest1 = new SqlRequestWrapper((HttpServletRequest) request);
			chain.doFilter(reqRequest1, response); 
			return;
		}
		HttpServletRequest req = (HttpServletRequest) request;
		String requestUrl = req.getRequestURI();
		requestUrl = requestUrl.substring(requestUrl.indexOf("/", 1) + 1,requestUrl.length());
		if(disFilter!=null&&disFilter.length>0){//这些URL不被过滤
			for(String url:disFilter){
				if(requestUrl.indexOf(url) > -1){
					chain.doFilter(request, response);
					return;
				}
			}
		}
		SqlRequestWrapper reqRequest = new SqlRequestWrapper((HttpServletRequest) request);
		if(onlyFilter!=null&&onlyFilter.length>0){
			for(String ourl:onlyFilter){
				if(requestUrl.indexOf(ourl) > -1){//只有这些URL要被过滤
					chain.doFilter(reqRequest, response); 
					return;
				}else{//除了上面的就不被过滤
					chain.doFilter(request, response);
					return;
				}
			}
		}	
		chain.doFilter(reqRequest, response);  //如果onlyFilter没有配置 ，那么除了disFilter中配置的URL都要被过滤 
	}
}