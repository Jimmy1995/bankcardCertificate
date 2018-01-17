package com.common.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.common.base.DBHelper;
import com.common.util.MyClassLoader;


//以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext. 

public class SpringContextHolder implements ApplicationContextAware,DisposableBean {
	private static Log log=LogFactory.getLog(SpringContextHolder.class);
	private static ApplicationContext applicationContext;
	//缓存bean的最后修改时间
	public static Map<String,Object> cacheBeanLastModifyTimeMap = new HashMap<String,Object>();
	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext;
		log.info("********************注入ApplicationContext到SpringContextHolder:" + applicationContext);
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		T t=null;
		try{
			t=(T) applicationContext.getBean(name);
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		return t;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型. 如果有多个Bean符合Class, 取出第一个.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		Map beanMaps = applicationContext.getBeansOfType(clazz);
		if (beanMaps != null && !beanMaps.isEmpty()) {
			return (T) beanMaps.values().iterator().next();
		} else {
			return null;
		}
	}

	public static <T> List<T> getBeanList(Class<T> clazz) {
		checkApplicationContext();
		Map<String, T>  beanMaps = applicationContext.getBeansOfType(clazz);
		if (beanMaps != null && !beanMaps.isEmpty()) {
			List<T> l=new ArrayList<T>();
			for(Entry<String,T> s:beanMaps.entrySet()){
				l.add(s.getValue());
			}
			return l;
		} else {
			return null;
		}
	}
	
	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException(
					"applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}
	
    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
   public static void clearHolder() {
	   log.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
       applicationContext = null;
   }

   /**
    * 实现DisposableBean接口, 在Context关闭时清理静态变量.
    */
   public void destroy() throws Exception {
       SpringContextHolder.clearHolder();
   }
   /**
    * 从数据库注册bean(实现热部署bean).此方法加载的bean都无事务
    */
   public static void registerBean4DataBase(){
	   DefaultListableBeanFactory factory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
	   List<Map<String,Object>> beans=DBHelper.queryForList("select * from sys_beans");
	   if(beans!=null&&beans.size()>0){
		   MyClassLoader loader;
		   for(Map<String, Object> bean:beans){
			 //如果最后修改时间没有变动则不重新加载
			   if(null==bean.get("createTime")||bean.get("createTime").equals(cacheBeanLastModifyTimeMap.get((String)bean.get("bean_id"))))continue;
			    loader=new MyClassLoader(Thread.currentThread().getContextClassLoader(), (String)bean.get("class_name"));
			    try {
			    	if(factory.containsBean((String)bean.get("bean_id"))){
			    		factory.destroySingleton((String)bean.get("bean_id"));
			    	}
			    	Object o=factory.initializeBean(loader.loadClass().newInstance(), (String)bean.get("bean_id"));
			    	factory.registerSingleton((String)bean.get("bean_id"), o);
					cacheBeanLastModifyTimeMap.put((String)bean.get("bean_id"), bean.get("createTime"));
					log.info("动态注册bean成功***************:"+bean.get("bean_id")+";全路径为："+bean.get("class_name"));
				} catch (Exception e){
					e.printStackTrace();
					StringWriter sw = new StringWriter();  
		            e.printStackTrace(new PrintWriter(sw, true));  
		            log.error(sw.toString());
		            log.info("动态注册bean失败***************:"+bean.get("bean_id")+";全路径为："+bean.get("class_name"));
				}
		   }
	   }
   }

   /**public static void registerBean(String beanName,String beanClassName){
	   DefaultListableBeanFactory factory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
	   GenericBeanDefinition beandefinition=new GenericBeanDefinition();
	   beandefinition.setBeanClassName(beanClassName);
	   beandefinition.setSingleton(true);
	   factory.registerBeanDefinition(beanName, beandefinition);
   }**/

}