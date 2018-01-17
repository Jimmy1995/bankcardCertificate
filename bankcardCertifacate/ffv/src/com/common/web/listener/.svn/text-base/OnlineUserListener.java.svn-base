package com.common.web.listener;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;

import com.common.frame.model.UserInfo;
import com.common.web.MySessionContext;

/**
 * @author Longzy 一个监听类，统计在线用户
 */
@SuppressWarnings("unchecked")
public class OnlineUserListener implements HttpSessionListener, HttpSessionAttributeListener {
	Log log=LogFactory.getLog("appLogger");
	//public static Map userMap = new HashMap();
    private   MySessionContext myc=MySessionContext.getInstance();

	/**
	 * session 创建
	 */
	public void sessionCreated(HttpSessionEvent se) {
		//myc.AddSession(se.getSession());//这个功能搬到了管理人员登录页面
		//log.info("session创建-----------------");
	}
	/**
	 * session 销毁
	 */
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		ServletContext application = session.getServletContext();
		UserInfo user=(UserInfo)session.getAttribute("userAdmin");
		Map onlineUser=(Map)application.getAttribute("onlineUserList");
		if (user != null) {// 取得登录的用户名
			if (onlineUser != null) {// 从在线列表中删除用户名
				onlineUser.remove(user.getUserName());
			}
			if(onlineUser!=null){
				if(user.getName()!=null)
				MDC.put("name",user.getName());
				MDC.put("userName",user.getUserName());
				MDC.put("ip","");
				MDC.put("place", "");
				log.info("退出登陆！");
			}
		}
		
		//log.info("session销毁-----------------");
		
        myc.DelSession(session);

	}
	/**
	 * session 赋值
	 */
	public void attributeAdded(HttpSessionBindingEvent arg0) {
		//if("userAdmin".equals(arg0.getName())){
		//	WebUtils.setUser((UserInfo)arg0.getValue());
		//}
		//log.info("=========================用户信息session放入ThreadLocal,可以用静态方法WebUtils.getUser()得到user的信息了===============================");
	}
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * session 被再赋值
	 */
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		//if("userAdmin".equals(arg0.getName())){
		//	WebUtils.setUser((UserInfo)arg0.getValue());
		//}
		//log.info("=========================用户信息session被覆盖,可以用静态方法WebUtils.getUser()得到user的信息了===============================");
	}
}