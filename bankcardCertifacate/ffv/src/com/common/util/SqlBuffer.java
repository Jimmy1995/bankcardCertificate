package com.common.util;

import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;

import com.common.SysConstants;
import com.common.base.BaseException;
import com.common.util.bean.BeanHelper;

public class SqlBuffer implements java.io.Serializable{
	private static final long serialVersionUID = -1478719398714130965L;
	private StringBuffer buffer;
	public SqlBuffer() {
		buffer=new StringBuffer("\n");
	}
	public SqlBuffer(String sql) {
		buffer=new StringBuffer("\n"+sql+"\n");
	}
	public SqlBuffer append(String sql){
		buffer.append(sql+"\n");
		return this;
	}
	public String toString() {
		return buffer.toString();
	}
	public void setLength(int arg0){
		buffer.setLength(arg0);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buffer == null) ? 0 : buffer.hashCode());
		return result;
	}
	/**
	 * 将param中为空的参数条件从SQL中删除掉（:a格式的一整行）
	 * @param param
	 * @return
	 */
	public SqlBuffer validateParameter(Object paramobj){
		Map param;
		try {
			param = BeanHelper.getBeanAttr(paramobj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException(e);
		}
		String sql=toString();
		Matcher matcherp = SysConstants.paramPattern.matcher(sql);
		Matcher matcherw = SysConstants.wherestr.matcher(sql);
		while(matcherp.find()&&matcherw.find()){
			//System.out.println(matcherp.group(0)+","+matcherp.group(2)+","+matcherw.group(0));
			if(null==param||null==param.get(matcherp.group(2))||StringUtils.isEmpty(param.get(matcherp.group(2))+"")){
				sql=sql.replace(matcherw.group(0),"");
			}else{//
				if(matcherp.group(0).substring(0, 4).equalsIgnoreCase("like")){//如果是以like开头，就把值替换
					sql=sql.replace(matcherw.group(0), matcherw.group(0).replace(":"+matcherp.group(2),param.get(matcherp.group(2))+""));
				}
			}
		}
		buffer.setLength(0);
		buffer.append(sql);
		return this;
	}
	/*public static void main(String[] args){
		SqlBuffer lSQL=new SqlBuffer();
		lSQL.append("select * from sys_user where 1=1");
		lSQL.append("	and id=:id	 ");
		lSQL.append("	and username=:username	 ");
		lSQL.append("	and usercode=:	usercode	 ");
		lSQL.append(" 	and x.stockName like '%:stockName%'");
		Map<String, Object> map=new java.util.HashMap<String, Object>();
		map.put("username", "");
		map.put("id", "1");
		map.put("stockName", "1ew");
		lSQL.validateParameter(map);
		System.out.println(lSQL.toString());
	}*/
}
