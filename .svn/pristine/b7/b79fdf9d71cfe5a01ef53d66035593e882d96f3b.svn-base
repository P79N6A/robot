package com.bossbutler.mapper.customerservice;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.CusServerDetailPojo;
import com.bossbutler.pojo.CusServerPojo;
import com.bossbutler.pojo.ProjectProcessPojo;

/**
 * 名称 : 描述 : 租户工单列表和详情 创建人 : xbj 创建时间: 2016年9月5日 下午3:25:16
 * 
 */
public interface CustomerBillMapper {
	List<CusServerPojo> queryserverlistcuss(@Param("accountId") String accountId, @Param("type") String type);

	CusServerDetailPojo querydetail(@Param("serverId") String serverId, @Param("accountId") String accountId);

	List<ProjectProcessPojo> queryserverprogress(@Param("serverId") String serverId,
			@Param("statusCode") String statusCode);

	String selectstatus(@Param("serverId") String serverId);
}
