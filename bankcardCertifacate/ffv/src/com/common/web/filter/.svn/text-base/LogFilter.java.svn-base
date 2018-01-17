package com.common.web.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;

import com.common.frame.model.UserInfo;
import com.common.util.WebUtils;
import com.common.web.filter.wrapper.StatusExposingServletResponse;
/**
 * @classDescription:将操作信息保存到数据库中
 * @author:longzy龙家小哥
 * @createTime:2012/11/15
 */
public class LogFilter implements Filter {
	private static final long serialVersionUID = 8755749896505968324L;
	Log log=LogFactory.getLog("appLogger");
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse servletResponse,FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		// 获取访问的url
		String requestUrl = req.getRequestURI();
		requestUrl=requestUrl.replaceFirst(req.getContextPath(), "");
		StatusExposingServletResponse response = new StatusExposingServletResponse((HttpServletResponse)servletResponse);

		if(requestUrl.indexOf("login")!=-1){//如果想登录就不管他了，就让他走
			filterChain.doFilter(request, response);
			return ;
		}
		// 获得登陆时的用户
		UserInfo userinfo = (UserInfo) req.getSession().getAttribute("userAdmin");
		if(userinfo==null){//如果他没登录就不管他了，就让他走
			filterChain.doFilter(request, response);
			return ;
		}
		Map<String,String> userUrlMap = (Map<String,String>) req.getSession().getAttribute("userUrlMap");
		if(userUrlMap!=null){
			for(Entry<String, String> url:userUrlMap.entrySet()){
				if(requestUrl.contains("log-manage")){//日志查询不记录
					filterChain.doFilter(request, response);
					return;
				}
				if(requestUrl.equals(url.getKey())){
					if(userinfo.getName()!=null)
					MDC.put("name",userinfo.getName());
					MDC.put("userName",userinfo.getUserName());
					MDC.put("ip",WebUtils.getIP((HttpServletRequest)request));
					MDC.put("place", "");
					log.info(url.getValue());
					userUrlMap=null;
					filterChain.doFilter(request, response);
					return;
				}
				userUrlMap=null;
			}
		}
		filterChain.doFilter(request, response);
		//statisticResult.completeRequest(servletRequest,response,exception,startTime);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
