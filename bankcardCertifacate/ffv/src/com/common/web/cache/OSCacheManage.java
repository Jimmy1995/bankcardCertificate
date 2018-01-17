package com.common.web.cache;

import java.util.Date;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;


/**
 * @className:OSCacheImp.java
 * @classDescription:
 * @author:longzy
 */

public class OSCacheManage {
	private static volatile OSCacheManage myCacheManage;

	private static GeneralCacheAdministrator cache;
	/**
	 * 初始化缓存管理容器
	 */
	static void init() {
		try {
			if (cache == null)
				cache = new GeneralCacheAdministrator();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * 获取CacheManager对象（饿汉单例模式）
	 * 
	 * @return
	 */
	public static OSCacheManage getInstance() {
		if (myCacheManage == null) {
			synchronized (OSCacheManage.class) {
				if (myCacheManage == null) {
					myCacheManage = new OSCacheManage();
					init();
				}
			}
		}
		return myCacheManage;

	}

	/**
	 * 设置Cache
	 * 
	 * @param key
	 * @param value
	 */
	public void setCache(String key, Object value) {
		cache.putInCache(key, value);
	}

	/**
	 * 获取cache
	 * 
	 * @param key
	 *            关键字
	 * @return
	 */
	public Object getCache(String key) {
		Object object = null;
		try {
			object = cache.getFromCache(key);// cache.getFromCache(key,86400);,第二个参数是过期时间：秒，86400一天时间，也就是从第一次取这个值开始，一天时间就从cache中删除
		} catch (NeedsRefreshException e) {
			cache.cancelUpdate(key);//这个必须加，否则线程会一直锁住
		}
		return object;
	}
	/**
	 * 设置cache实例
	 * 
	 * @param key
	 *            关键字
	 * @return
	 */
	public static void setCache(GeneralCacheAdministrator cacheObj) {
		cache=cacheObj;
	}
	/**
	 * 根据key值清除缓存
	 * 
	 * @param key
	 *            关键字
	 */
	public void clearCache(String key) {
		cache.flushEntry(key);
	}

	/**
	 * 清除所有缓存
	 * 
	 * @param key
	 *            关键字
	 */
	public void clearAllCache() {
		cache.flushAll();
	}

	/**
	 * 根据日期清除缓存对象
	 */
	public void removeAll(Date date) {
		cache.flushAll(date);
	}
	
	/**
	 * @return the cache
	 */
	public static GeneralCacheAdministrator getOsCache() {
		return cache;
	}
}
