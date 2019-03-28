package com.bossbutler.db.page;

import java.sql.Connection;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;

@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }))
public class MySqlPagingInterceptor extends AbstractPagingInterceptor {

	/**
	 * 改造sql变成查询总数的sql
	 * 
	 * @param targetSql
	 *            正常查询数据的sql: select id, name from user where name = ?
	 * @return select count(1) from user where name = ?
	 */
	@Override
	protected String getSelectTotalSql(String targetSql) {
		String sql = targetSql.toLowerCase();
		StringBuilder sqlBuilder = new StringBuilder(sql);
		sqlBuilder.insert(0, "select count(1) as _count from ( ").append(" ) t");
		return sqlBuilder.toString();
	}

}
