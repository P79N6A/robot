package com.bossbutler.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 实体对象创建工具类
 * 
 * @author XN
 *
 * @param <E>
 */
public class EntityBuiler {

	/**
	 * 自定义实体映射 注意：sql查询结果集必须和返回实体字段名一模一样
	 * 
	 * @param resultMap
	 * @param clazz
	 * @return
	 */
	private static <E> E buildCustomEntity(Map<String, Object> resultMap, Class<E> clazz) {
		// 如果查询结果为空，直接返回null
		if (resultMap == null) {
			return null;
		}
		// 否则组装实体对象
		Field[] fields = clazz.getDeclaredFields();
		E entity = null;
		try {
			entity = clazz.newInstance();
			for (Field field : fields) {
				String fieldName = field.getName();
				field.setAccessible(true);
				Object fieldValue = resultMap.get(fieldName);

				if (fieldValue != null)
					field.set(entity, fieldValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return entity;
	}

	/**
	 * 自定义实体映射 注意：sql查询结果集必须和返回实体字段名一模一样
	 * 
	 * @param resultMap
	 * @param clazz
	 * @return
	 */
	public static <E> List<E> buildCustomEntity(List<Map<String, Object>> resultList, Class<E> clazz) {
		List<E> result = new ArrayList<E>();
		if (resultList != null && !resultList.isEmpty()) {
			for (Map<String, Object> resultMap : resultList) {
				result.add(buildCustomEntity(resultMap, clazz));
			}
		}
		return result;
	}

}