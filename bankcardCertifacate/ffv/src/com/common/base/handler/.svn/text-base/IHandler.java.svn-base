package com.common.base.handler;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.common.web.page.Page;


/**
 * @className:IHandler.java
 * @classDescription:数据库处理接口
 * @author:longzy
 * @createTime:May 14, 2011
 */
@SuppressWarnings("unchecked")
public interface IHandler {

	/**
	 * 保存对象
	 * 
	 * @param obj
	 * @return
	 */
	public boolean saveObj(Object obj);
	public boolean saveOrUpdate(Object obj);
	public void saveOrUpdateAll(List list);
	/**
	 * 删除对象
	 * 
	 * @param obj
	 * @return
	 */
	public boolean deleteObj(Object obj);

	/**
	 * 修改对象
	 * 
	 * @param obj
	 * @return
	 */
	public boolean alterObj(Object obj);
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
	/**
	 * 查找对象
	 * 
	 * @param hql
	 *            hql语句
	 * @return 对象
	 */
	public Object findObj(String hql);

	/**
	 * 根据id查找对象
	 * 
	 * @param className
	 *            类型名
	 * @param id
	 *            对象id
	 * @return 对象
	 */
	public Object findObjById(Class className, Serializable id);
	/**
	 * 根据id查询延时对象
	 * 
	 * @param className
	 *            类名
	 * @param id
	 *            类id
	 * @return 对象
	 */
	public Object findObjByIdLoad(Class className, Serializable id);

	/**
	 * 根据hql查找集合
	 * 
	 * @param hql
	 *            hql语句
	 * @return 返回集合
	 */
	public List findListOfObj(String hql);
	/**
	 * 查询对象(防止依赖注入)
	 * 
	 * @param hql
	 *            查找语句
	 * @param map 参数       
	 * @return 对象
	 */
	public Object findObj(final String hql,final Map<Serializable, Serializable> map);

	/**
	 * 主要用于防止sql依赖注入
	 * 
	 * @param hql
	 *            hql语句（例如" from UserInfo where id=:id and
	 *            password=:password")query.setParameter("id",userForm.getId())
	 * @param map
	 * @return List集合
	 */
	public List findListOfObj(String hql, Map<Serializable, Serializable> map);

	/**
	 * 根据hql，集合索引，显示条数 查找集合（主要用于分页）
	 * 
	 * @param hql
	 *            hql查询语句
	 * @param page
	 *            当前页数
	 * @param count
	 *            取几条数据
	 * @return 集合对象
	 */
	public Page queryPageByHQL(final String hql, final int pageSize, final int pageNo, final String orderStr);

	/**
	 * sql語句操作數據庫
	 * 
	 * @param sql
	 * @return boolean
	 */
	public boolean executeSql(String sql);
	
	/**
	 * 分页查询功能
	 * @param detachedCriteria
	 * @param pageSize
	 * @param startIndex
	 * @param orderStr//格式：field:asc，如果没有就写null
	 * @return page
	 */
	public Page findPageByCriteria(final DetachedCriteria detachedCriteria, final int pageSize, 
            final int pageNo,final String orderStr);
	
	public List findAllObject(Class className);
	/**
	 * sql分页
	 * @param sql
	 * @param pageSize
	 * @param startIndex
	 * @param StrIndex
	 * @param type为bean/map/list
	 * @return
	 */
	public Page queryPageBySQL(String sql,final int pageSize,final int pageNo,Class type);
	public Page queryPageBySQL(String sql,final int pageSize,final int pageNo,Class type,Object[] params);
	public List findList(final DetachedCriteria detachedCriteria);
	/**
	 * 执行HQL语句
	 * @param hql
	 * @return
	 */
	public int executeHQL(final String hql);
	
	public List findListBySql(String sql,Class type);
	
	public List findListBySql(String sql);
	/**
	 * @param dataMap 要更新的字段与where语句的数据都在这儿，不能为空
	 * @param type Hibernate实体类,不能为空
	 * @param updates 类型：String[]或List   可为空,默认更新主建及wheres中包含的键之外的
	 * @param wheres 类型：String[]或List 可为空，默认为主键
	 * @return
	 */
	public int update(Map<String, Serializable> dataMap, Class type,Object updates,Object wheres);
}
