package com.bossbutler.mapper.dictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.basemapper.IBaseMapper;
import com.bossbutler.pojo.dictionary.DictionaryDataModel;



public interface DictionaryDataMapper extends IBaseMapper<DictionaryDataModel, DictionaryDataModel, DictionaryDataModel>{
	
	/**
	 * 字典列表
	 * @param model
	 * @return
	 */
	List<DictionaryDataModel> queryDynamic(@Param("model") DictionaryDataModel model);
}
