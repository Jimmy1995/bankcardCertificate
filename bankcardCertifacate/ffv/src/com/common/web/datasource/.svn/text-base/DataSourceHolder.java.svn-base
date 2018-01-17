package com.common.web.datasource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataSourceHolder{

	protected static final Log logger = LogFactory.getLog(java.sql.Connection.class);
	private static ThreadLocal<String> customerType = new ThreadLocal<String>();
	public static void setCustomerType(String type) {
		customerType.set(type);
	}

	public static String getCustomerType() {
		String data=(String)customerType.get();
		if(data!=null){
			System.out.println("DataSource Name:["+data+"]");
		}else{
			System.out.println("DataSource Name:default");
		}
		return data;
	}

	public static void clearCustomerType() {
		customerType.remove();
	}

}