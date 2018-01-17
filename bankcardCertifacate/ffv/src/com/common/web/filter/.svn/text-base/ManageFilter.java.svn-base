package com.common.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.frame.model.Role;
import com.common.frame.model.UserInfo;
import com.common.frame.vo.RoleCache;
import com.common.util.WebUtils;
import com.common.web.cache.CacheUtil;
import com.common.web.cache.OSCacheManage;
import com.common.web.filter.init.InitManage;
import com.common.web.page.PageUtil;

/**
 * 过滤权限作用
 * @className:ManageFilter.java
 * @classDescription:
 * @author:longzy龙家小哥
 * @createTime:2012/11/15
 */
public class ManageFilter implements Filter {
	private static final long serialVersionUID = 2341497961907946504L;
	private  String[] manageUrl;
	private  String[] disManageUrl;
	private  String[] noLoginUrl;
	private  OSCacheManage osCacheManage = OSCacheManage.getInstance();

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		manageUrl=InitManage.getBean().getManageKeyWords();
		disManageUrl=InitManage.getBean().getDisManageKeyWords();
		noLoginUrl=InitManage.getBean().getNoLogin();
		HttpServletRequest req = (HttpServletRequest) request;
		PageUtil.setPage(req, null);
		// 获取访问的url
		String requestUrl = req.getRequestURI();
		Object ou=req.getSession().getAttribute("userAdmin");
		if(ou!=null){WebUtils.setUser((UserInfo)ou);}else{WebUtils.setUser(null);}
		//String requestUrl1 = requestUrl.substring(requestUrl.indexOf("/", 1) + 1,requestUrl.length());
		requestUrl=requestUrl.replaceFirst(req.getContextPath(), "");
		for(String url:noLoginUrl){//不用登录的请求，比如"去登录"或者是"退出登录",那就放他过去(默认是所有的请求都要登录的，除非是disManageUrl中写明的)
			if(requestUrl.indexOf(url) > -1){
				filterChain.doFilter(request, response);
				return;
			}
		}
		// 获得登陆时的用户
		UserInfo userinfo = (UserInfo)ou;
		
		//再设置一次session,为了让ThreadLocal不失效。（监听了setattribute的时候把userinfo放到ThreadLocal中），实现在静态方法中得到session中的对象（com.common.tool.WebUtils）
		//if(null!=userinfo)req.getSession().setAttribute("userAdmin", userinfo);
		//是否登录了
		if (null==userinfo) {
			// 如果没有用户对象,则表示没有登陆。或是登陆的会话时间已过
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			if(WebUtils.isAJAXRequest(req)){//如果是ajax访问
				WebUtils.writeError("未登录或登录已超时,请先登录！",(HttpServletRequest)request, httpResponse);
				return;
			}
			PrintWriter out = httpResponse.getWriter();
			response.setContentType("text/html;charset=utf-8"); 
			out.println("<html>");  
		    out.println("<script type=\"text/javascript\">");
		    out.println("window.open ('"+req.getContextPath()+ InitManage.getProperty("noActionPage")+"'"+",'_top')");  
		    out.println("</script>");  
		    out.println("</html>");
		    out.flush();
		    out.close();
			/*httpResponse.sendRedirect(req.getContextPath()
					+ "/manage/login.jsp");*/
			return;
		}
		
		boolean flag=false;
		for(String url:manageUrl){
			if(requestUrl.indexOf(url) > -1){//如果需要访问权限
				flag=true;
				for(String durl:disManageUrl){//disManage中是否配置过，配置了就 还是不用访问权限了
					if(requestUrl.indexOf(durl) > -1){
						flag=false;
						break;
					}
				}
				break;
			}
		}
		if (flag) {//判断她是否有访问权限
			// 申明缓存对象
			Map<String,String> userUrlMap = (HashMap<String,String>) req.getSession().getAttribute("userUrlMap");
			if (null==userUrlMap) {
				userUrlMap = new HashMap();
				Set<Role> roles=userinfo.getRoles();
				// 获取用户的所有角色，然后遍历出它的权限，判断url是否有权访问
				if(roles!=null)
				for (Role role : roles) {
					// 如果缓存中没有，则将数据添加到缓存中
					if (null == osCacheManage.getCache("role_" + role.getRoleId())) {
						CacheUtil.refreshRoleCache(osCacheManage, role);
					}
					//RoleCache rc= (RoleCache) osCacheManage.getCache("role_" + role.getId());
					userUrlMap.putAll(((RoleCache) osCacheManage.getCache("role_" + role.getRoleId())).getUrlMap());
				}
				roles=null;
			}
			if(userUrlMap.containsKey(requestUrl)){
				userUrlMap=null;
				filterChain.doFilter(request, response);
				return;
			}
			userUrlMap=null;
			// 如果没有权限则跳转到友好提示页面
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			if(WebUtils.isAJAXRequest(req)){//如果是ajax访问
				WebUtils.writeError("无权限！",(HttpServletRequest)request, httpResponse);
				return;
			}
			httpResponse.sendRedirect(req.getContextPath()
					+ "/commons/noAction.jsp");
			return;
		} else {//不需要访问权限
			filterChain.doFilter(request, response);
			return;
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
