package com.common.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

import com.common.web.filter.init.InitManage;

/**
 * @author longzy
 * 
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 6750987365981714097L;
	public static String rootpath="";
	public static String appname="";
	@Override
	public void init() throws ServletException {
		super.init();
		String webAppRootKey = getServletContext().getRealPath("/");
		//System.setProperty("webapp.root", webAppRootKey);
		rootpath=webAppRootKey;
		appname=this.getServletContext().getContextPath();
	    initLogging();
	    try {
			InitManage.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getPhysicalPath(String filePathName) {
		String path = filePathName == null ? "" : filePathName;
		if (File.separator.equals("/"))
			path = path.replace('\\', File.separator.charAt(0));
		else {
			path = path.replace('/', File.separator.charAt(0));
		}

		if (path.startsWith(File.separator)) {
			path = path.substring(1);
		}
		try {
			String val = rootpath;//System.getProperty("webapp.root");
			if (val != null)
				return val.toString() + File.separator + path;
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return path;
	}

	public void initLogging(){
		Properties logProps = new Properties();
		String homeDir = rootpath;//System.getProperty("webapp.root");
		String location = getPhysicalPath("WEB-INF/classes/log4j.properties");
		FileInputStream istream=null;
		try {
			istream = new FileInputStream(location);
			logProps.load(istream);
	        logProps.setProperty("log4j.appender.D.File", homeDir+File.separator+"WEB-INF"+File.separator+"logs"+File.separator+logProps.getProperty("log4j.appender.D.File"));
	        logProps.setProperty("log4j.appender.E.File", homeDir+File.separator+"WEB-INF"+File.separator+"logs"+File.separator+logProps.getProperty("log4j.appender.E.File"));
	        PropertyConfigurator.configure(logProps); //装入log4j配置信息
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(istream!=null)
					istream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}