package com.bossbutler.mapper.customerservice;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.PagingBounds;
import com.bossbutler.pojo.PropertyServerPojo;
import com.bossbutler.pojo.ServiceTypePojo;

/**
 * 名称 : 
 * 描述 : 物业的工单的列表
 * 创建人 :  xbj
 * 创建时间: 2016年9月5日 下午3:32:31
 * 
 */
public interface PropertyBillMapper {
	List<ServiceTypePojo>  querypropertytype(@Param("accountId") String accountId,@Param("type") String type,@Param("projectId") String projectId);
	List<PropertyServerPojo> queryBillPageListlist(@Param("accountId") String accountId,@Param("type") String type,@Param("billtype") String billtype,@Param("projectId") String projectId, PagingBounds page);

}
