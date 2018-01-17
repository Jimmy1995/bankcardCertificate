package com.common.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.common.base.handler.IHandler;
import com.common.web.SpringContextHolder;
import com.common.web.page.Page;
import com.common.web.page.PageUtil;

public class DAOHelper{
	private static IHandler handler=SpringContextHolder.getBean("springHandler");
	public static boolean save(Object obj) {
		return handler.saveObj(obj);
	}
	public static boolean saveOrUpdate(Object obj) {
		return handler.saveOrUpdate(obj);
	}
	public static boolean del(Object obj) {
		return handler.deleteObj(obj);
	}

	public static boolean alter(Object obj) {
		return handler.alterObj(obj);
	}

	public static boolean merge(Object obj) {
		return handler.merge(obj);
	}

	public static Object findBean(String hql) {
		return handler.findObj(hql);
	}

	public static <T> T findById(Class<T> className, Serializable id) {
		return (T)handler.findObjById(className, id);
	}

	public static <T> T findByIdLoad(Class<T> className, Serializable id) {
		return (T)handler.findObjByIdLoad(className, id);
	}

	public static List findList(String hql) {
		return handler.findListOfObj(hql);
	}

	public static Object findBean(String hql, Map<Serializable, Serializable> map) {
		return handler.findObj(hql, map);
	}

	public static List findList(String hql, Map<Serializable, Serializable> map) {
		return handler.findListOfObj(hql, map);
	}

	public static Page queryPageByHQL(String hql, int pageSize, int pageNo,String order) {
		return handler.queryPageByHQL(hql, pageSize, pageNo,order);
	}
	public static Page queryPageByHQL(String hql) {
		Page page=PageUtil.getPage();
		return queryPageByHQL(hql, page.getPageSize(), page.getPageNo(),page.getOrder());
	}
	public static boolean executeSql(String sql) {
		return handler.executeSql(sql);
	}

	public static Page findPageByCriteria(DetachedCriteria detachedCriteria,
			int pageSize, int pageNo, String orderStr) {
		return handler.findPageByCriteria(detachedCriteria, pageSize, pageNo, orderStr);
	}

	public static <T> List<T> findAllObject(Class<T> className) {
		return handler.findAllObject(className);
	}

	public static Page queryPageBySQL(String sql, int pageSize, int pageNo, Class type) {
		return handler.queryPageBySQL(sql, pageSize, pageNo, type);
	}

	public static List findList(DetachedCriteria detachedCriteria) {
		return handler.findList(detachedCriteria);
	}

	public static int executeHQL(String hql) {
		return handler.executeHQL(hql);
	}

	public static <T> List<T> findListBySql(String sql, Class<T> type) {
		return handler.findListBySql(sql, type);
	}

	public static List findListBySql(String sql) {
		return handler.findListBySql(sql);
	}

	public static int update(Map<String, Serializable> dataMap, Class type,
			Object updates, Object wheres) {
		return handler.update(dataMap, type, updates, wheres);
	}
	public static Object findObj(String hql){
		return handler.findObj(hql);
	}
}
