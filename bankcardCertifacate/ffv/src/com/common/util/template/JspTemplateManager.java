package com.common.util.template;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Include;




public class JspTemplateManager {
	private static JspTemplateManager JspTemplateManager = null;
	public JspTemplateManager() {}
	/**
	 * 取得模板处理的实例
	 * @return JspTemplateManager 模板处理实例
	 */
	public synchronized static JspTemplateManager getInstance() {
		if (JspTemplateManager == null) {
			JspTemplateManager = new JspTemplateManager();
		}
		return JspTemplateManager;
	}
	public void buildFile(String filePath,Writer writer,ServletRequest request,HttpServletResponse response){
		 try {
			response.setCharacterEncoding("utf-8"); 
			response.setContentType("text/html;charset=utf-8");
			Include.include(filePath, writer,request, response, "utf-8");
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
