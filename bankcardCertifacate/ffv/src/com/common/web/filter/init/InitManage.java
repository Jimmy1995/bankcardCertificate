package com.common.web.filter.init;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.common.web.InitServlet;
import com.common.web.SpringContextHolder;
import com.common.web.cache.OSCacheManage;
import com.common.web.quartz.QuartzManager;


public class InitManage {
	Log log=LogFactory.getLog(InitManage.class);
	private static volatile InitManage obj;//java 5+
	private static ManageBean bean;
	private static int refreshCount=0;
	protected OSCacheManage osCacheManage = OSCacheManage.getInstance();
	public void getInitManage()throws Exception{
		bean=loadConfig();
		//CacheUtil.refreshDictionaryCache(osCacheManage);//把公开的数据字典全记录在内存当中
		//SpringContextHolder.registerBean4DataBase();//从数据库注册bean
		if(refreshCount<1){//只在程序启动的时候运行，刷新的时候不管
			refreshCount++;
//			List<ICacheService> cacheServicelist=SpringContextHolder.getBeanList(ICacheService.class);
//			for(ICacheService cache:cacheServicelist){
//				cache.initCache();
//			}
			QuartzManager.start4DBInit();//启动定时任务
			SpringContextHolder.registerBean4DataBase();//从数据库注册bean
		}
	}
	/**
	 * 从config.xml加载配置信息
	 * @throws Exception
	 */
	public ManageBean loadConfig()throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
		factory.setNamespaceAware(true);    
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(InitServlet.rootpath+"/WEB-INF/classes/config/init/manageConfig.xml");  
		XPathFactory pathFactory = XPathFactory.newInstance();  
		XPath xpath = pathFactory.newXPath();
		XPathExpression pathExpression = xpath.compile("/manage/manageKeyWords/text/text()");  
		XPathExpression pathExpression1 = xpath.compile("/manage/disManageKeyWords/text/text()");
		XPathExpression pathExpression2 = xpath.compile("/manage/noLogin/text/text()");
		XPathExpression property = xpath.compile("/manage/property/*");
		NodeList mlist = (NodeList)pathExpression.evaluate(doc, XPathConstants.NODESET);    
		NodeList dlist = (NodeList)pathExpression1.evaluate(doc, XPathConstants.NODESET);
		NodeList nlist = (NodeList)pathExpression2.evaluate(doc, XPathConstants.NODESET);
		NodeList propertylist = (NodeList)property.evaluate(doc, XPathConstants.NODESET);
		for(int i=0;i<propertylist.getLength();i++){//${
			String ptext=propertylist.item(i).getTextContent();
			String p="";
			int ij=0;
			for(String x:ptext.split("#")){//${catalina.home}#upload#------------->System.getProperty("catalina.home")+File.separator+upload
				if(x.startsWith("${")&&x.endsWith("}")){
					if(ij>0)p+=File.separator;
					p+=System.getProperty(x.substring(2, x.length()-1));
				}else{
					if(ij>0)p+=File.separator;
					p+=x;
				}
				ij++;
			}
			System.setProperty(InitServlet.appname+propertylist.item(i).getNodeName(),p);
		}
		String[] manageKeyWords=new String[mlist.getLength()];
		String[] disManageKeyWords=new String[dlist.getLength()];
		String[] noLogins=new String[nlist.getLength()];
		for(int i=0;i<mlist.getLength();i++){
			manageKeyWords[i]=mlist.item(i).getTextContent();
		}
		for(int i=0;i<dlist.getLength();i++){
			disManageKeyWords[i]=dlist.item(i).getTextContent();
		}
		for(int i=0;i<nlist.getLength();i++){
			noLogins[i]=nlist.item(i).getTextContent();
		}
		ManageBean bean1=new ManageBean();
		bean1.setDisManageKeyWords(disManageKeyWords);
		bean1.setManageKeyWords(manageKeyWords);
		bean1.setNoLogin(noLogins);
		return bean1;
	}
	
	public static ManageBean getBean() {
		if(bean==null){
			try {
				getInstance().getInitManage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bean;
	}
	public static void init() throws Exception{
		getInstance().getInitManage();
	}
	
	public static InitManage getInstance(){
		if(null==obj){
			synchronized (InitManage.class) {
				if(null==obj){
					obj=new InitManage();
				}
			}
		}
		return obj;
	}
	public static String getProperty(String key){
		return System.getProperty(InitServlet.appname+key);
	}
	/*public static void main(String[] args) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
		factory.setNamespaceAware(true);    
		DocumentBuilder builder = factory.newDocumentBuilder();    
		Document doc = builder.parse("c:\\manageConfig.xml");  
		XPathFactory pathFactory = XPathFactory.newInstance();  
		XPath xpath = pathFactory.newXPath();
		XPathExpression pathExpression = xpath.compile("/manage/manageKeyWords/*");  
		NodeList nodelist = (NodeList)pathExpression.evaluate(doc, XPathConstants.NODESET);
		for(int i=0;i<nodelist.getLength();i++){
			System.out.println(nodelist.item(i).getNodeName()+"="+nodelist.item(i).getTextContent());
		}
	}*/
}
