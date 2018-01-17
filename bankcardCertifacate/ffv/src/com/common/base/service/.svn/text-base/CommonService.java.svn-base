package com.common.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.common.base.dao.ICommonDAO;

/**
 * @className:CommonService.java
 * @classDescription:基本的Service
 * @author:longzy
 * @createTime:2010-7-8
 */
public abstract class CommonService<T> extends BaseService implements ICommonService<T>,Serializable{
	private static final long serialVersionUID = 7395466659444647957L;

	protected abstract  ICommonDAO<T> getDAO();
	

	/**
	 * 保存对象
	 * 
	 * @param obj
	 *            要保存的对象
	 * @return 布尔值
	 */
	public boolean save(T obj) {		
		return getDAO().save(obj);
	}

	/**
	 * 删除对象
	 * 
	 * @param obj
	 *            要删除的对象
	 * @return 布尔值
	 */
	public boolean delete(T obj) {

		return getDAO().delete(obj);

	}
	/**
	 * 根据Id删除对象
	 * 
	 * @param obj
	 *            要删除的对象
	 * @return 布尔值
	 */
	public boolean deleteById(Serializable id) {

		return getDAO().deleteById(id);

	}
	/**
	 * 修改对象
	 * 
	 * @param obj
	 *            要修改的对象
	 * @return 布尔值
	 */
	public boolean alter(T obj) {

		return getDAO().alter(obj);

	}

	/**
	 * 查询对象
	 * 
	 * @param hql
	 *            查找语句
	 * @return 对象
	 */
	public T find(String hql) {
		return (T) getDAO().find(hql);		
	}
	/**
	 * 查询对象(防止依赖注入)
	 * 
	 * @param hql
	 *            查找语句
	 * @param map 参数           
	 * @return 对象
	 */
	public T findOfMap(String hql,Map<Serializable,Serializable> map) {
		return (T) getDAO().findOfMap(hql,map);		
	}
	/**
	 * 主要用于防止sql依赖注入
	 * 
	 * @param hql
	 *            hql语句
	 * @param map
	 * @return List集合
	 */
	public List<T> findListOfMap(final String hql,
			final Map<Serializable, Serializable> map) {
		return getDAO().findListOfMap(hql, map);
	}

	/**
	 * 根据id查询对象
	 * 
	 * @param className
	 *            类名
	 * @param id
	 *            类id
	 * @return 对象
	 */
	public T findById(Serializable id) {

		return (T) getDAO().findById(id);

	}
	/**
	 * 根据id查询缓存对象
	 * 
	 * @param className
	 *            类名
	 * @param id
	 *            类id
	 * @return 对象
	 */
	public T findByIdLoad(Serializable id) {

		return (T) getDAO().findByIdLoad(id);

	}

	/**
	 * 查询集合
	 * 
	 * @param sql
	 *            要保存的对象
	 * @return 集合
	 */
	public List<T> findList(String hql) {

		return getDAO().findList(hql);

	}

	/**
	 * sql語句操作數據庫
	 * 
	 * @param sql
	 * @return boolean
	 */
	public boolean executeSql(String sql) {
		return this.getDAO().executeSql(sql);
	}
	/**
	 * 执行HQL语句
	 * @param hql
	 * @return
	 */
	public int executeHQL(final String hql){
		return this.getDAO().executeHQL(hql);
	}
}
