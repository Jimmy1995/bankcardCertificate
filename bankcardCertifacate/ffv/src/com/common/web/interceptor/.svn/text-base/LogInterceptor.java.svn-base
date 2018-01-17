package com.common.web.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.HttpParameters;
import org.apache.struts2.result.ServletDispatcherResult;

import com.common.base.BaseException;
import com.common.util.WebUtils;
import com.common.util.json.JsonUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LogInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 5089848283638645993L;
	Log log = LogFactory.getLog(LogInterceptor.class);
	Log dblog=LogFactory.getLog("appLogger");
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionProxy aproxy = invocation.getProxy();
		ActionContext ctx = invocation.getInvocationContext();
		HttpServletRequest request = ServletActionContext.getRequest();//(HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = ServletActionContext.getResponse();
		if (log.isInfoEnabled()) {
			HttpParameters paramMap=ctx.getParameters();
			log.info("-------------------------action-------------------------  :" + WebUtils.getRequestURIWithParam(request));
			log.info("param   :"+JsonUtils.toString(paramMap.toMap()));
		}
		long startTime = System.currentTimeMillis();
		String result=null;
		try{
		    result=invocation.invoke();
		}catch (BaseException e) {//(Throwable e)
			if(WebUtils.isAJAXRequest(request)){
				dblog.error(e.getMessage());
				WebUtils.writeError(e.getMessage(),request,response);
			}else{
				result="error";
				throw e;
			}
		}catch (Exception e) {//(Throwable e)
			if(WebUtils.isAJAXRequest(request)){
				e.printStackTrace();  
	            StringWriter sw = new StringWriter();  
	            e.printStackTrace(new PrintWriter(sw, true));  
	            String str = sw.toString();
				dblog.error(str);
				int ni=str.indexOf("\n");
				if(ni!=-1){
					if(str.length()>ni+1){
						str=str.substring(0,str.indexOf("\n",ni+1));
					}else{
						str=str.substring(0,ni);
					}
				}
				WebUtils.writeError(str,request, response);
			}else{
				result="error";
				throw e;
			}
		}
		long endTime = System.currentTimeMillis();
		if (log.isInfoEnabled()) {
			log.info("result  :"+invocation.getResultCode());
			Result dr= invocation.getResult();
			log.info("exec    :"+ (endTime-startTime)+"ms at "+aproxy.getConfig().getClassName()+"."+aproxy.getMethod()+String.format("("+aproxy.getAction().getClass().getSimpleName()+".java:%s)", 1));
			if(dr!=null&&(dr instanceof ServletDispatcherResult)){
				log.info("view    :"+((ServletDispatcherResult)dr).getLocation());
			}else{
				log.info("view    :null");
			}
		}
		return result;
	}
}
