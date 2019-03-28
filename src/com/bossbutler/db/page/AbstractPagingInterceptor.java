package com.bossbutler.db.page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.PropertyException;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import com.bossbutler.pojo.PagingBounds;
import com.bossbutler.util.ReflectHelper;

public abstract class AbstractPagingInterceptor implements Interceptor {

	private static String pageSqlId = ""; // mapper.xml中需要拦截的ID(正则匹配)

	private static final Pattern PATTERN_SQL_BLANK = Pattern.compile("\\s+");

	private static final String FIELD_DELEGATE = "delegate";

	private static final String FIELD_ROWBOUNDS = "rowBounds";

	private static final String FIELD_MAPPEDSTATEMENT = "mappedStatement";

	private static final String BLANK = " ";

	/**
	 * replace all blank
	 * 
	 * @param originalSql
	 * @return
	 */
	private String replaceSqlBlank(String originalSql) {
		Matcher matcher = PATTERN_SQL_BLANK.matcher(originalSql);
		return matcher.replaceAll(BLANK);
	}

	/**
	 * init get xml param to instance
	 * 
	 * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
	 */
	@Override
	public void setProperties(Properties properties) {
		pageSqlId = properties.getProperty("pageSqlId");
		if (StringUtils.isBlank(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 拦截方法
	 * 
	 * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.
	 *      Invocation)
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		if (invocation.getTarget() instanceof RoutingStatementHandler) {
			Connection connection = (Connection) invocation.getArgs()[0];
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();

			StatementHandler handler = (StatementHandler) ReflectHelper.getValueByFieldName(statementHandler,
					FIELD_DELEGATE);
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(handler,
					FIELD_MAPPEDSTATEMENT);
			String sqltmp = MybatisInterceptor.showSql(mappedStatement.getConfiguration(), handler.getBoundSql());
			System.out.println(sqltmp);
			// 拦截需要分页的SQL
			if (mappedStatement.getId().matches(pageSqlId)) {

				BoundSql boundSql = handler.getBoundSql();
				// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
				Object parameterObject = boundSql.getParameterObject();
				if (parameterObject == null) {
					throw new NullPointerException("parameterObject尚未实例化！");
				}

				// replace all blank
				String targetSql = replaceSqlBlank(boundSql.getSql());
				PagingBounds pagingBounds = (PagingBounds) ReflectHelper.getValueByFieldName(handler, FIELD_ROWBOUNDS);
				// paging
				getTotalAndSetInPagingBounds(targetSql, boundSql, pagingBounds, mappedStatement, connection);
			}

		}
		return invocation.proceed();
	}

	/**
	 * 获得数据总数
	 * 
	 * @param targetSql
	 * @param boundSql
	 * @param bounds
	 * @param mappedStatement
	 * @param connection
	 * @throws SQLException
	 */
	private void getTotalAndSetInPagingBounds(String targetSql, BoundSql boundSql, PagingBounds bounds,
			MappedStatement mappedStatement, Connection connection) throws SQLException {
		String totalSql = getSelectTotalSql(targetSql);
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		Object parameterObject = boundSql.getParameterObject();
		BoundSql totalBoundSql = new BoundSql(mappedStatement.getConfiguration(), totalSql, parameterMappings,
				parameterObject);
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject,
				totalBoundSql);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(totalSql);
			parameterHandler.setParameters(pstmt);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int totalRecord = rs.getInt(1);
				bounds.setTotal(totalRecord);
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}

	/**
	 * 返回结果
	 * 
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	protected abstract String getSelectTotalSql(String targetSql);

}
