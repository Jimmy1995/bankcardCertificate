package com.common.web.cache;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.common.SysConstants;
import com.common.base.BaseException;
import com.common.base.DAOHelper;
import com.common.base.DBHelper;
import com.common.base.handler.IHandler;
import com.common.frame.model.ActionInfo;
import com.common.frame.model.CacheConfig;
import com.common.frame.model.DataDictionary;
import com.common.frame.model.Menu;
import com.common.frame.model.Role;
import com.common.frame.model.UserKind;
import com.common.frame.service.IDataDictionaryService;
import com.common.frame.vo.RoleCache;
import com.common.util.bean.BeanHelper;
import com.common.web.SpringContextHolder;
import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.base.algorithm.AbstractConcurrentReadCache;

public class CacheUtil {
	/**
	 * @author 龙志勇longzy
	 * @Description 根据角色保存访问权限、菜单权限、字典权限数据
	 * @param osCacheManage
	 * @param role
	 */
	public static void refreshRoleCache(OSCacheManage osCacheManage, Role role) {
		RoleCache roleCache = new RoleCache();
		Map<String, String> urlMap = new HashMap<String, String>();// 访问权限
		Map<String, Menu> menuMap = new LinkedHashMap<String, Menu>();// 菜单权限
		Set<DataDictionary> dicSet = new HashSet<DataDictionary>();// 字典权限
		for (ActionInfo action : role.getActions()) {
			urlMap.put(action.getActionPath(), action.getActionName());
		}
		for (Menu menu : role.getMenus()) {
			menuMap.put(menu.getMenuId(), menu);
		}
		for (DataDictionary dic : role.getDataDictionarys()) {
			dicSet.add(dic);
		}

		// 设置菜单和url
		roleCache.setMenuMap(menuMap);
		roleCache.setUrlMap(urlMap);
		roleCache.setDicSet(dicSet);
		osCacheManage.setCache("role_" + role.getRoleId(), roleCache);
	}
	/**
	 * 刷新数据字典的缓存
	 * @param osCacheManage
	 */
	public static void refreshDictionaryCache(OSCacheManage osCacheManage) {
		IDataDictionaryService dictionaryService = SpringContextHolder.getBean("dataDictionaryService");
		List<DataDictionary> dics=dictionaryService.findList("from DataDictionary where isOpen='1' order by orders");
		Map<String,Map<String,String>> dicmap=new HashMap<String,Map<String,String>>();
		if(dics!=null&&dics.size()>0){
			for(DataDictionary dic:dics){
				Map<String,String> mapsub=dicmap.get(dic.getDicKey());
				if(mapsub==null){
					mapsub=new HashMap<String,String>();
				}
				mapsub.put(dic.getDicKey(), dic.getDicValue());
				if(dicmap.containsKey(dic.getCodetype())){
					Map map=dicmap.get(dic.getCodetype());
					map.putAll(mapsub);
					dicmap.put(dic.getCodetype(), map);
				}else{
					dicmap.put(dic.getCodetype(), mapsub);
				}
			}
		}
		osCacheManage.setCache(SysConstants.OPENCODE,dicmap);// 把公开的数据字典全记录在内存当中
	}

	/**
	 * 刷新缓存数据库表配置的缓存
	 * @param osCacheManage
	 */
	public static void refreshCacheConfigByDB(OSCacheManage osCacheManage){
		IHandler handler=SpringContextHolder.getBean("springHandler");
		List<CacheConfig> configs=(List<CacheConfig>)handler.findListOfObj("from CacheConfig cache where cache.validFlag='1'");
		if(configs==null||configs.size()<1){
			return ;
		}
		for(CacheConfig config:configs){
			if(!StringUtils.isEmpty(config.getLoadSql())){
				String typeClass=config.getCacheClass();
				if(StringUtils.isEmpty(typeClass)){
					List<Map<String,Object>> listm=DBHelper.queryForList(config.getLoadSql());
					if(listm!=null&&listm.size()>0){
						Map m=new HashMap();
						for(Map<String,Object> mobj:listm){
							m.put(mobj.get(config.getFind_key()), mobj.get("value"));
						}
						osCacheManage.setCache(config.getCacheName(),m);
					}
				}else{
					List listm;
					try {
						listm = DBHelper.queryForList(config.getLoadSql(),null,null,Class.forName(config.getCacheClass()));
						if(listm!=null&&listm.size()>0){
							Map m=new HashMap();
							for(Object mobj:listm){
								try {
									m.put(BeanHelper.getBeanAttr(config.getFind_key()), mobj);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							osCacheManage.setCache(config.getCacheName(),m);
						}
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}else{
				throw new BaseException("loadsql不能为空!");
			}
		}
	}
	
	/**
	 * 获取ServletCache的全部Application Scope的cache
	 * @return
	 */
	public static Map getAppScopeCaches(OSCacheManage cacheManage) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取Cache对象实例
		Cache cache = OSCacheManage.getOsCache().getCache();
		// 通过反射机制获取Cache私有成员变量cacheMap
		AbstractConcurrentReadCache cacheMap = null;
		Field field;
		try {
			field = Cache.class.getDeclaredField("cacheMap");
			field.setAccessible(true);
			cacheMap = (AbstractConcurrentReadCache) field.get(cache);
			field.setAccessible(false);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 返回包含在cacheMap中的Map关系的 Set视图。
		if(cacheMap==null)return map;
		Set<Entry<String, Object>> entrySet = cacheMap.entrySet();
		// 使用for遍历cacheMap中的entrySet
		for (Map.Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			Object value = null;
			try {
				value = cache.getFromCache(key);
			} catch (NeedsRefreshException e) {
				cache.cancelUpdate(key);
				//cacheMap.remove(key);//删除
				//continue;
				//e.printStackTrace();
			}
			// 以下方法只能得到CacheEntry对象实例Id
			// Object value = entry.getValue();
			String mvalue="";
			if(value==null){
				mvalue="**该缓存已过期**";
			}else{
				mvalue=value.getClass().getName();
				if(value instanceof Collection){
					mvalue=mvalue+" : "+((Collection) value).size();
				}
				if(value instanceof Map){
					mvalue=mvalue+" : "+((Map) value).size();
				}
			}
			map.put(key, mvalue);
		}

		return map;
	}
	
	/**
	 * 保存登录用户类型（sys_userkind）
	 * @param kindtype
	 * @return
	 */
	public static UserKind getUserKind(String kindtype){
		OSCacheManage osCacheManage=OSCacheManage.getInstance();
		Map<String,UserKind> map=(Map)osCacheManage.getCache("userKind");
		if(null==map){
			map=new HashMap();
			List<UserKind> ul=DAOHelper.findAllObject(UserKind.class);
			if(ul!=null&&ul.size()>0){
				for(UserKind uk:ul){
					map.put(uk.getType(), uk);
				}
			}
			osCacheManage.setCache("userKind", map);
		}
		return map.get(kindtype);
	}
	/**
	 * 得到数据字典
	 * @param codeType
	 * @return
	 */
	public static Map getDic(String codeType){
		OSCacheManage osCacheManage=OSCacheManage.getInstance();
		Map dicmap = (Map)osCacheManage.getCache(SysConstants.OPENCODE);
//		if(null==dicmap){
//			refreshDictionaryCache(osCacheManage);
//			dicmap = (Map)osCacheManage.getCache(SysConstants.OPENCODE);
//		}
		if(null==dicmap){
			return null;
		}
		Map codemap=(Map)dicmap.get(codeType);
		if(null==codemap){
			return null;
		}
		return codemap;
	}
}
