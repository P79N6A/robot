package com.bossbutler.mapper.bill;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.basemapper.IBaseMapper;
import com.bossbutler.pojo.PagingBounds;
import com.bossbutler.pojo.bill.BillHome;
import com.bossbutler.pojo.bill.HmServerBillOut;
import com.bossbutler.pojo.bill.IbServerBillOut;
import com.bossbutler.pojo.bill.ServiceBill;

public interface ServiceBillMapper extends IBaseMapper<ServiceBill, ServiceBill, ServiceBill> {

	int update(ServiceBill bill);

	/**
	 * 方法描述 : 根据帐号获取管理首页数据 创建人 : hzhang 创建时间: 2016年10月11日 上午11:11:50
	 * 
	 * @param accountId
	 * @return
	 */
	List<BillHome> queryBillhomeByAccountId(@Param("accountId") String accountId);

	List<HmServerBillOut> queryHmBillPageList(@Param("accountId") String accountId, @Param("type") String type,
			@Param("billtype") String billtype, @Param("projectId") String projectId,@Param("isAdmin") String isAdmin, PagingBounds page);

	List<IbServerBillOut> queryIbBillPageList(@Param("accountId") String accountId, @Param("type") String type,
			PagingBounds page);

	String findProjectIsAdminByAccountId(@Param("accountId")String accountId, @Param("projectId")String projectId);

}