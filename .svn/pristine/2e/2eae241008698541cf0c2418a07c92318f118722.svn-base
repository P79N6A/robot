package com.bossbutler.basemapper;

import java.io.Serializable;
import java.util.List;

import com.bossbutler.pojo.PagingBounds;

/**
 * @author wangqiao
 *
 * @param <T>
 *            操作数据库bean对象类
 * @param <T1>
 *            查询数据库返回列表对象类
 * @param <T2>
 *            查询数据库条件对象类
 */
public interface IBaseMapper<T, T1, T2 extends Serializable> {
	/**
	 * 插入操作
	 * 
	 * @param dbBean
	 *            任意的数据库bean对象
	 * @return
	 * @throws Exception
	 *             事务异常
	 */
	public int insert(T dbBean) throws Exception;

	/**
	 * 插入一批数据
	 * 
	 * @param tlist
	 *            数据库bean对象列表
	 * @throws Exception
	 *             事务异常
	 */
	public void insertList(List<T> list) throws Exception;

	/**
	 * 根据主键删除
	 * 
	 * @param id
	 *            数据主键
	 * @throws Exception
	 */
	public void deleteByPrimaryKey(String id) throws Exception;

	/**
	 * 删除多个
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void deleteByPrimaryKeys(String[] ids) throws Exception;

	/**
	 * 根据主键查找对象
	 * 
	 * @param id
	 *            数据主键
	 * @return 任意的数据库bean对象
	 * @throws Exception
	 *             事务异常
	 */
	public T findByPrimaryKey(String id) throws Exception;

	/**
	 * 查询全部
	 * 
	 * @return 数据库bean对象列表
	 * @throws Exception
	 *             事务异常
	 */
	public List<T1> select() throws Exception;

	/**
	 * 根据查询条件分页查询数据
	 * 
	 * @param queryBean
	 *            查询条件
	 * @param page
	 *            页数
	 * @param rows
	 *            显示行数
	 * @return
	 */
	public List<T1> selectPageList(T2 queryBean, PagingBounds pagingBounds) throws Exception;

	/**
	 * 手动分页查询
	 * 
	 * @param queryBean
	 *            查询条件
	 * @return
	 */
	public List<T1> selectPageByLimit(T2 queryBean) throws Exception;

	/**
	 * 根据属性条件查询全部
	 * 
	 * @param queryBean
	 *            查询条件
	 * @return 数据库bean对象列表
	 * @throws Exception
	 *             事务异常
	 */
	public List<T1> select(T2 queryBean) throws Exception;

}
