package com.bossbutler.mapper.Information;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.Information.InformationLog;

public interface InformationLogMapper {
	
	int insert(@Param("model") InformationLog model);
	/**
	 * 方法描述 :获取消息读日志
	 * 创建人 :  hzhang
	 * 创建时间: 2016年12月11日 下午3:44:34
	 * @param id
	 */
	InformationLog queryByInfoIdAndAccountId(@Param("infoId") String infoId,@Param("accountId") String accountId);

}
