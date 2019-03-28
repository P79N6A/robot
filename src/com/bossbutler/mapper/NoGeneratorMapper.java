package com.bossbutler.mapper;

import java.util.Map;

public interface NoGeneratorMapper {

	Map getNoGeneratorByUsedCat(String UsedCat);

	int updateNoGeneratorByUsedCat(String UsedCat);

}
