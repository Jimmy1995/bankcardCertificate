package com.x2x.api.utils;



public class LocalIP {
	private static ThreadLocal<String> ip = new ThreadLocal<String>();
	public static String getIp() {
		String myip=ip.get();
		if(myip==null){
			myip="";
		}
		return myip;
	}
	public static void setIp(String myip){
		ip.set(myip);
	}
}
