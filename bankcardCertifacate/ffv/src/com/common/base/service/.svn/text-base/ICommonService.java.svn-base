package com.common.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @className:ServiceInteface.java
 * @classDescription:通用Service接口
 * @author:longzy
 * @createTime:2010-7-8
 */
public interface ICommonService <T> extends IBaseService{
	/**
	 * 保存对象
	 * 
	 * @param obj
	 *            要保存的对象
	 * @return 布尔值
	 */
	public boolean save(T obj);

	/**
	 * 删除对象
	 * 
	 * @param obj
	 *            要删除的对象
	 * @return 布尔值
	 */
	public boolean delete(T obj);

	/**
	 * 删除对象
	 * 
	 * @param obj
	 *            要删除的对象
	 * @return 布尔值
	 */
	public boolean deleteById(Serializable id);

	/**
	 * 修改对象
	 * 
	 * @param obj
	 *            要修改的对象
	 * @return 布尔值
	 */
	public boolean alter(T obj) ;

	/**
	 * 查询对象
	 * 
	 * @param hql
	 *            查找语句
	 * @return 对象
	 */
	@Deprecated
	public T find(final String hql);
	/**
	 * 查询对象(防止依赖注入)
	 * 
	 * @param hql
	 *            查找语句
	 * @param map 参数           
	 * @return 对象
	 */
	@Deprecated
	public T findOfMap(final String hql,Map<Serializable,Serializable> map);
	/**
	 * 主要用于防止sql依赖注入
	 * 
	 * @param hql
	 *            hql语句
	 * @param map(map支持中List对象)
	 * @return List集合
	 */
	@Deprecated
	public List<T> findListOfMap(final String hql,
			final Map<Serializable, Serializable> map);

	/**
	 * 根据id查询对象
	 * 
	 * @param className
	 *            类名
	 * @param id
	 *            类id
	 * @return 对象
	 */
	public T findById(Serializable id) ;
	/**
	 * 根据id查询缓存对象
	 * 
	 * @param className
	 *            类名
	 * @param id
	 *            类id
	 * @return 对象
	 */
	public T findByIdLoad(Serializable id);

	/**
	 * 查询集合
	 * 
	 * @param sql
	 *            要保存的对象
	 * @return 集合
	 */
	@Deprecated
	public List<T> findList(String hql);


	/**
	 * sql語句操作數據庫
	 * 
	 * @param sql
	 * @return boolean
	 */
	@Deprecated
	public boolean executeSql(String sql) ;
	
	/**
	 * 执行HQL语句
	 * @param hql
	 * @return
	 */
	@Deprecated
	public int executeHQL(final String hql);
}
