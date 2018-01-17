package com.common.util.template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.log4j.Logger;

import com.common.web.page.Page;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 模板工具类
 * 
 * @author Administrator
 * 
 */
public class FreemarkerTemplateManager {
	public static Logger logger = Logger.getLogger(FreemarkerTemplateManager.class.getName());
	private static FreemarkerTemplateManager freemarkerTemplateManager = null;
	private Configuration configuration = null;
	public FreemarkerTemplateManager() {
		// 设置freemarker的参数
		configuration = new Configuration();
		configuration.setObjectWrapper(new DefaultObjectWrapper());
		configuration.setDefaultEncoding("UTF-8");
	}
	/**
	 * 取得模板处理的实例
	 * @return freemarkerTemplateManager 模板处理实例
	 */
	public synchronized static FreemarkerTemplateManager getInstance() {
		if (freemarkerTemplateManager == null) {
			freemarkerTemplateManager = new FreemarkerTemplateManager();
		}
		return freemarkerTemplateManager;
	}
	/**
	 * 生成静态文件<br/>
	 * [概要]<br/>
	 * <p>
	 * 读取模板文件生成静态文件
	 * </p>
	 * [详细]<br/>
	 * <ol>
	 * <li>取得模板文件</li>
	 * <li>设置生成文件路径</li>
	 * <li>载入objectMap中的内容生成文件</li>
	 * </ol>
	 * @param templateFolder 模板相对于classpath的路径
	 * @param templateFileName  模版名称
	 * @param htmlFilePath 要生成的静态文件的路径
	 * @param objectMap 模板中对象集合
	 * @return boolean 是否生成成功
	 */
	@SuppressWarnings("unchecked")
	public void buildFile(String templateFolder, String templateFileName,
			String htmlFilePath, Map objectMap) {
		Writer out = null;
		try {
			File f=new File(templateFolder);
		    f.mkdirs();
			configuration.setDirectoryForTemplateLoading(f);
			Template template = configuration.getTemplate(templateFileName);
			template.setEncoding("UTF-8");
			// 创建生成文件目录
			File htmlFile = new File(htmlFilePath);
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(htmlFile), "UTF-8"));
			template.process(objectMap, out);
			out.flush();
		} catch (TemplateException ex) {
			logger.error("Build Error" + templateFileName, ex);
		} catch (IOException e) {
			logger.error("Build Error" + templateFileName, e);
		} finally {
			try {
				if(out!=null)
				out.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	/**
	 * 生成静态文件<br/>
	 * [概要]<br/>
	 * <p>
	 * 读取模板文件生成静态文件
	 * </p>
	 * 
	 * [详细]<br/>
	 * <ol>
	 * <li>取得模板文件</li>
	 * <li>设置生成文件路径</li>
	 * <li>载入objectMap中的内容生成文件</li>
	 * </ol>
	 * 
	 * @param templateFolder
	 *            模板相对于classpath的路径
	 * @param templateFileName
	 *            模版名称
	 * @param htmlFolder
	 *            要生成的静态文件的目录
	 * @param htmlFileName
	 *            要生成的文件名
	 * @param objectMap
	 *            模板中对象集合
	 * @return boolean 是否生成成功
	 */
	@SuppressWarnings("unchecked")
	public void buildFile(Page page, String templateFolder,
			String templateFileName, String htmlFolder, String htmlFileName,
			String htmlFileType, Map objectMap) {
		String a="";
		for (int i = 1; i <= page.getPageNo(); i++) {
			a+="<a href='"+htmlFileName+"_"+ i + "." + htmlFileType+"'>["+i+"]</a>     ";
		}
		//objectMap.put("href",a);
		//生成文件
		File f=null;
		String[] sts=a.split("</a>");
		for (int i = 1; i <= page.getPageNo(); i++) {
			objectMap.put("href",a.replace(sts[i-1]+"</a>", i+""));
			//得到每个分页的内容
			objectMap.put("list", page.getResult().subList(
															(i - 1) * page.getPageSize(),
															(i * page.getPageSize() < ((int) page.getTotalCount()) ? 
															(i * page.getPageSize()): ((int) page.getTotalCount()))
														   ));
			//生成文件
			f=new File(htmlFolder);
			f.mkdirs();
			buildFile(templateFolder, templateFileName, htmlFolder
					+ htmlFileName +"_"+ i + "." + htmlFileType, objectMap);
		}
	}
	/**
	 * @param templateFolder
	 *            模板相对于classpath的路径
	 * @param templateFileName
	 *            模版名称
	 * @param objectMap
	 * 			      模板中对象集合
	 * @return
	 */
	public String buildFile(String templateFolder, String templateFileName, Map objectMap) {
		Writer out = new StringWriter();
		try {
			File f=new File(templateFolder);
		    f.mkdirs();
			configuration.setDirectoryForTemplateLoading(f);
			Template template = configuration.getTemplate(templateFileName);
			template.setEncoding("UTF-8");
			template.process(objectMap, out);
		} catch (TemplateException ex) {
			logger.error("Build Error" + templateFileName, ex);
		} catch (IOException e) {
			logger.error("Build Error" + templateFileName, e);
		} finally {
			try {
				if(out!=null)
				out.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return out.toString();
	}
}