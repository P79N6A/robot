package com.bossbutler.mapper.complaint;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.PagingBounds;
import com.bossbutler.pojo.complaint.ComplaintIn;
import com.bossbutler.pojo.complaint.ComplaintModel;
import com.bossbutler.pojo.complaint.ComplaintOut;


public interface ComplaintMapper {
	
	List<ComplaintModel>getPageList(@Param("ComplaintModel") ComplaintModel ComplaintModel, PagingBounds page);

	ComplaintModel getEntity (String complaintId);
	
	List<ComplaintModel> getListByIdList (List<Long> idList);
	
	int insert (ComplaintModel complaintModel);
	
	int update(ComplaintModel complaintModel);

	List<ComplaintModel> getPageList(@Param("projectId")String projectId, @Param("page") int page,
			@Param("npage") int npage);
	/**
	 * 列表数据
	 * @param in
	 * @param page 
	 * @return
	 */
	List<ComplaintOut> queryPageList(ComplaintIn in, PagingBounds page);
	/**
	 * 获取物管组织
	 * @param accountId
	 * @return
	 */
	List<Map<String, String>> queryManageOrg(String accountId);

	ComplaintOut findIbInfo(String complaintId);
	
	ComplaintOut findHmInfo(String complaintId);

	List<ComplaintOut> queryHmPageList(ComplaintIn param, PagingBounds page);

}
