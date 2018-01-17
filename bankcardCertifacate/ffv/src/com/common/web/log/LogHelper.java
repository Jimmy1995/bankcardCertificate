package com.common.web.log;


public class LogHelper {
	private static ThreadLocal<Boolean> doLog = new ThreadLocal<Boolean>();

	public static Boolean getDoLog() {
		if(null==doLog.get())doLog.set(true);
		return doLog.get();
	}

	public static void setDoLog(Boolean doLog) {
		LogHelper.doLog.set(doLog);
	}
	
}
