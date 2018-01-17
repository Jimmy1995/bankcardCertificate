package com.common.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.common.util.GenericsUtils;
import com.common.web.page.Page;
import com.common.web.page.PageUtil;

/**
 * @className:CommonDAO.java
 * @classDescription:公共DAO，实现对数据库的增删改查
 * @author:longzy
 * @createTime:Jun 16, 2010
 */
@SuppressWarnings("unchecked")
public abstract class CommonDAO<T> extends BaseDAO implements ICommonDAO<T>{
	private Class<T> clazz;
	 
	public CommonDAO() {
		 clazz =GenericsUtils.getSuperClassGenricType(getClass());
	    }
	/**
	 * 保存对象
	 * 
	 * @param obj
	 *            要保存的对象
	 * @return 布尔值
	 */
	public boolean save(T obj) {
		
		return handler.saveObj(obj);

	}
	public void saveOrUpdateAll(List list) {
        handler.saveOrUpdateAll(list);
	}
	/**
	 * 删除对象
	 * 
	 * @param obj
	 *            要删除的对象
	 * @return 布尔值
	 */
	public boolean delete(T obj) {

		return handler.deleteObj(obj);

	}
	/**
	 * 根据Id删除对象
	 * 
	 * @param obj
	 *            要删除的对象
	 * @return 布尔值
	 */
	public boolean deleteById(Serializable id) {

		return handler.deleteObj(handler.findObjById(clazz, id));

	}
	/**
	 * 修改对象
	 * 
	 * @param obj
	 *            要修改的对象
	 * @return 布尔值
	 */
	public boolean alter(T obj) {

		return handler.alterObj(obj);

	}

	/**
	 * 查询对象
	 * 
	 * @param hql
	 *            查找语句
	 * @return 对象
	 */
	public T find(final String hql) {
		return (T) handler.findObj(hql);		
	}
	public List findListOfHQL(final String hql) {
		return handler.findListOfObj(hql);		
	}
	/**
	 * 查询对象(防止依赖注入)
	 * 
	 * @param hql
	 *            查找语句
	 * @param map 参数           
	 * @return 对象
	 */
	public T findOfMap(final String hql,Map<Serializable,Serializable> map) {
		return (T) handler.findObj(hql,map);		
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
		return handler.findListOfObj(hql, map);
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

		return (T) handler.findObjById(clazz, id);

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

		return (T) handler.findObjByIdLoad(clazz, id);

	}

	/**
	 * 查询集合
	 * 
	 * @param sql
	 *            要保存的对象
	 * @return 集合
	 */
	public List<T> findList(String hql) {

		return handler.findListOfObj(hql);

	}

	/**
	 * 根据页数和条数查询集合
	 * 
	 * @param hql
	 *            要保存的对象
	 * @param page
	 *            页数
	 * @param count
	 *            显示条数
	 * @return 集合
	 */
	public Page queryPageByHQL(String hql, int pageSize, int pageNo) {
		Page page=PageUtil.getPage();
		return handler.queryPageByHQL(hql,pageSize,pageNo,page.getOrder());

	}
	public Page queryPageByHQL(String hql) {
		Page page=PageUtil.getPage();
		return handler.queryPageByHQL(hql,page.getPageSize(),page.getPageNo(),page.getOrder());

	}
	//-------------------sql语句-----------------------//


	/**
	 * sql語句操作數據庫
	 * 
	 * @param sql
	 * @return boolean
	 */
	public boolean executeSql(String sql) {
		return handler.executeSql(sql);
	}
	/**
	 * 分页查询功能
	 * @param detachedCriteria
	 * @param pageSize
	 * @param startIndex
	 * @param orderStr//格式：order:asc，如果没有就写null
	 * @return page
	 */
	public Page queryPage(final DetachedCriteria detachedCriteria,
			final int pageSize, final int pageNo,final String orderStr){
		return handler.findPageByCriteria(detachedCriteria, pageSize, pageNo,orderStr);
	}
	public Page queryPage(final DetachedCriteria detachedCriteria){
		Page page=PageUtil.getPage();
		return handler.findPageByCriteria(detachedCriteria, page.getPageSize(), page.getPageNo(),page.getOrder());
	}
	public List<T> findAllObject(){
		return handler.findAllObject(clazz);
	}
	/**
	 * sql分页
	 * @param sql
	 * @param pageSize
	 * @param startIndex
	 * @param StrIndex
	 * @param type为bean/map/list
	 * @return
	 */
	public Page queryPageBySQL(String sql,final int pageSize,final int pageNo,Class type){
		return handler.queryPageBySQL(sql, pageSize, pageNo,type);
	}
	
	public Page queryPageBySQL(String sql,Class type){
		Page page=PageUtil.getPage();
		String orderStr = page.getOrder();
		if (orderStr != null && !orderStr.equals("")) {// 在这儿设置排序规则,页面传过来的排序格式：field:asc
//			String[] order = orderStr.split(":");
//			if (order[1].equals("asc"))
//				sql="select * from ("+sql+") lzy order by lzy."+order[0]+" asc";
//			else
//				sql="select * from ("+sql+") lzy order by lzy."+order[0]+" desc";
			sql="select * from ("+sql+") lzy order by "+orderStr.replace(":"," ");
		}
		return handler.queryPageBySQL(sql, page.getPageSize(), page.getPageNo(),type);
	}
	public List<T> findList(final DetachedCriteria detachedCriteria){
		return handler.findList(detachedCriteria);
	}
	/**
	 * 执行HQL语句
	 * @param hql
	 * @return
	 */
	public int executeHQL(final String hql){
		return handler.executeHQL(hql);
	}
	
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
	public boolean merge(Object obj){
		return handler.merge(obj);
	}
}
