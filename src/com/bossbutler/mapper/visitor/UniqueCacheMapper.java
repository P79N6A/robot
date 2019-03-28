package com.bossbutler.mapper.visitor;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.visitor.UniqueCache;

public interface UniqueCacheMapper {

	UniqueCache queryUniqueValueByKey(@Param("relationid") long relationid);

}
