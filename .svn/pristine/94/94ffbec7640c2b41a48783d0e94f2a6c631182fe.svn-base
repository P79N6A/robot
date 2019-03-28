package com.bossbutler.mapper.bill;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.basemapper.IBaseMapper;
//import com.bossbutler.pojo.PagingBounds;
import com.bossbutler.pojo.bill.*;
public interface MyBillMapper  extends IBaseMapper<BillPoJo,BillPoJo,BillPoJo>{


//	List<BillPoJo> getPageList(@Param("model") BillPoJo  model, PagingBounds pagingBounds); 
//	List<MyBillListModel> queryPageList(@Param("model") BillPoJo model,PagingBounds pagingBounds);
	List<MyBillListModel> queryList(@Param("model") BillPoJo model, @Param("billType") String billType);
	
	List<MyBillListModel> queryList2(@Param("model") BillPoJo model, @Param("billType") String billType);
	
	// 本期 往期01 欠费02
	List<BillDebtorModel> queryDebtorListByBillId(@Param("billId") String billId,@Param("type") String type);
	
	// 查询 租户单元 房源信息 银行
	 MyBillDetailModel queryBillDetail (@Param("billId") String billId);
	 
	 // 账单
	 int updateBillstaCode (@Param("model") BillPoJo model);
	 
	 
	 List<MyBillListModel> queryProjectList(@Param("accountId") String accountId);
}
