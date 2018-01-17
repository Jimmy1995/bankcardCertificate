package com.common.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.util.excel.Html2Excel;
import com.common.util.httpWrapper.CharResponseWrapper;
/**
 * @classDescription导出数据
 * @author longzy龙锅
 */
public class ExportFilter implements Filter {
	private static final long serialVersionUID = -7177889964374904371L;
	private Html2Excel exportExcel=new Html2Excel();
	public void init(FilterConfig arg0) throws ServletException {
	}
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws ServletException, IOException {
		String exportType=request.getParameter("gridViewExport");
		if(exportType==null||exportType.equals("")){//如果不需要导出，就直接去跳过这个过滤器
			chain.doFilter(request, response);
			return;
		}
		
		ServletResponse newResponse = response;
		if (request instanceof HttpServletRequest) {
			newResponse = new CharResponseWrapper((HttpServletResponse) response);
		}
		chain.doFilter(request, newResponse);
		if (newResponse instanceof CharResponseWrapper) {
			String text = newResponse.toString();
//			java.util.Map mmm=com.common.util.json.JsonUtils.deserialize(text, java.util.Map.class);
//			mmm=com.common.util.json.JsonUtils.deserialize(mmm.get("data")+"", java.util.Map.class);
//			java.util.List<java.util.Map> d=com.common.util.json.JsonUtils.deserialize(mmm.get("result")+"", java.util.List.class);
			if (text != null&&text.length()>0) {
				if("excel".equals(exportType)){//如果是excel就调用excel导出程序
					exportExcel.export((HttpServletResponse)response, text);
				}else if("word".equals(exportType)){
					response.reset();
					response.setContentType("application/msword");//word文件
					response.setCharacterEncoding("utf-8");
					response.getWriter().write(text);
				}else{
					response.reset();
					response.setCharacterEncoding("utf-8");
					response.getWriter().write(text);
					response.flushBuffer();
				}
				text=null;
			}
		}
	}
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
