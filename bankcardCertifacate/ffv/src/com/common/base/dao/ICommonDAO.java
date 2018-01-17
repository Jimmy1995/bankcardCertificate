package com.common.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.common.web.page.Page;

/**
 * @className:DAOInterface.java
 * @classDescription:DAO通用接口
 * @author:longzy
 * @createTime:2010-7-7
 */
@SuppressWarnings("unchecked")
public interface ICommonDAO<T> extends IBaseDAO{
	
	/**
	 * 保存对象
	 * 
	 * @param obj
	 *            要保存的对象
	 * @return 布尔值
	 */
	public boolean save(T obj);

	public void saveOrUpdateAll(List list);
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
	public T find(final String hql);
	/**
	 * 查询对象(防止依赖注入)
	 * 
	 * @param hql
	 *            查找语句
	 * @param map 参数           
	 * @return 对象
	 */
	public T findOfMap(final String hql,Map<Serializable,Serializable> map);
	/**
	 * 主要用于防止sql依赖注入
	 * 
	 * @param hql
	 *            hql语句
	 * @param map
	 * @return List集合
	 */
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
	public List<T> findList(String hql);

	//-------------------sql语句-----------------------//


	/**
	 * sql語句操作數據庫
	 * 
	 * @param sql
	 * @return boolean
	 */
	public boolean executeSql(String sql) ;
	
	/**
	 * 分页查询功能
	 * @param detachedCriteria
	 * @param pageSize
	 * @param startIndex
	 * @param orderStr//格式：order:asc，如果没有就写null
	 * @return page
	 */
	@Deprecated
	public Page queryPage(final DetachedCriteria detachedCriteria,
			final int pageSize, final int pageNo,final String orderStr);
	public Page queryPage(final DetachedCriteria detachedCriteria);
	
	public List<T> findAllObject();
	
	/**
	 * sql分页
	 * @param sql
	 * @param pageSize
	 * @param startIndex
	 * @param StrIndex
	 * @param type为bean/map/list
	 * @return
	 */
	@Deprecated
	public Page queryPageBySQL(String sql,final int pageSize,final int pageNo,Class type);
	public Page queryPageBySQL(String sql,Class type);
	/**
	 * 根据页数和条数查询集合
	 * 
	 * @param hql
	 *            要保存的对象
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            显示条数
	 * @return 集合
	 */
	@Deprecated
	public Page queryPageByHQL(String hql, int pageSize, int pageNo);
	public Page queryPageByHQL(String hql);
	public List findList(final DetachedCriteria detachedCriteria);
	public List findListOfHQL(final String hql);
	/**
	 * 执行HQL语句
	 * @param hql
	 * @return
	 */
	public int executeHQL(final String hql);
	/**
	 * 修改对象
	 * @descript merge和saveOrUpdate方法区别在于：
	 * merge方法是把我们提供的对象转变为托管状态的对象；
	 * 而saveOrUpdate则是把我们提供的对象变成一个持久化对象；
	 * 说的通俗一点就是：saveOrUpdate后的对象会纳入session的管理，对象的状态会跟数据库同步，再次查询该对象会直接从session中取;
	 * merge后的对 象不会纳入session的管理，再次查询该对象还是会从数据库中取
	 * @param obj
	 *            要修改的对象
	 * @return 布尔值
	 */
	public boolean merge(Object obj);
}
