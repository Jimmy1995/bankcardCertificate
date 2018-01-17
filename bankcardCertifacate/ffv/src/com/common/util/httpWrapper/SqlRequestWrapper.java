package com.common.util.httpWrapper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
/**
 * 包装类，替换敏感文字 
 * @author longzy
 */
public class SqlRequestWrapper extends HttpServletRequestWrapper {
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletRequestWrapper#getParameterMap()
	 */
	@Override
	public Map getParameterMap() {
		Map<String, String[]> params = super.getParameterMap();
		Map<String, String[]> newMap = new HashMap();
		String value[];
		for (Map.Entry<String, String[]> kv : params.entrySet()) {
			value = kv.getValue();
			String newValue[] = new String[value.length];
			for (int i = 0; i < value.length; i++) {
				newValue[i] = reqEncode(value[i]);
			}
			newMap.put(kv.getKey(), newValue);
		}
		return newMap;
	}

	HttpServletRequest orgRequest = null;

	public SqlRequestWrapper(HttpServletRequest request) {
		super(request);
		orgRequest = request;
	}

	/**
	 * 覆盖getParameter方法，将参数名和参数值都做req过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
	 * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
	 */
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if (value != null) {
			value = reqEncode(value);
		}
		return value;
	}

	/**
	 * 覆盖getHeader方法，将参数名和参数值都做req过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
	 * getHeaderNames 也可能需要覆盖
	 */
	/*
	 * @Override public String getHeader(String name) { String value =
	 * super.getHeader(reqEncode(name)); if (value != null) { value =
	 * reqEncode(value); } return value; }
	 */

	/**
	 * 将容易引起req漏洞的半角字符直接替换成全角字符
	 * 
	 * @param s
	 * @return
	 */
	private static String reqEncode(String s) {
		if (s == null || "".equals(s)) {
			return s;
		}
		StringBuilder sb = new StringBuilder(s.length() + 16);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\'':
				sb.append("&prime;");// &acute;");
				break;
			case '′':
				sb.append("&prime;");// &acute;");
				break;
			case '\"':
				sb.append("&quot;");
				break;
			case '＂':
				sb.append("&quot;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '#':
				sb.append("＃");
				break;
			case '\\':
				sb.append(' ');
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '<':
				sb.append("&lt;");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 获取最原始的request
	 * 
	 * @return
	 */
	public HttpServletRequest getOrgRequest() {
		return orgRequest;
	}

	/**
	 * 获取最原始的request的静态方法
	 * 
	 * @return
	 */
	public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
		if (req instanceof SqlRequestWrapper) {
			return ((SqlRequestWrapper) req).getOrgRequest();
		}

		return req;
	}
}