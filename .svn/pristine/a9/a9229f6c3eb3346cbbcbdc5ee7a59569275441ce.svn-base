package com.bossbutler.mapper.visitor;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.MyvisitorPojo;
import com.bossbutler.pojo.PagingBounds;
import com.bossbutler.pojo.RegionalPojo;
import com.bossbutler.pojo.VisitorInfoPojo;
import com.bossbutler.pojo.VisitorPojo;
import com.bossbutler.pojo.regional.VisitorModel;
import com.bossbutler.pojo.visitor.VisitorApply;
import com.bossbutler.pojo.visitor.VisitorApplyIn;
import com.bossbutler.pojo.visitor.VisitorApplyOut;
import com.bossbutler.pojo.visitor.VisitorIn;
import com.bossbutler.pojo.visitor.VisitorRecord;

public interface InviteVisitorMapper {
	List<VisitorPojo> queryvisitorlist(@Param("accountId") String accountId);
	/**
	 * 访客记录
	 * 
	 * @param pojo
	 * @return
	 */
	int insertVisitorApply(VisitorInfoPojo pojo);
	/**
	 * 增加访客
	 * 
	 * @param pojo
	 * @return
	 */
	int insertvisitor(VisitorInfoPojo pojo);
	/**
	 * 我的访客
	 * 
	 * @param accountId
	 * @return
	 */
	List<MyvisitorPojo> queryMyVisitorPageList(@Param("accountId") String accountId, PagingBounds page);

	List<RegionalPojo> queryRegional(@Param("accountId") String accountId);
	
	List<RegionalPojo> getProOrgByAccId(@Param("accountId") String accountId);
	
	

	String querymyvisitorcount(@Param("accountId") String accountId);

	MyvisitorPojo myvisitor(@Param("name") String name, @Param("phone") String phone, @Param("accountId")long accountId);
	
	MyvisitorPojo findById(@Param("visitorId") String visitorId);
	/**
	 * 方法描述 :邀请访客记录
	 * 创建人 :  hzhang
	 * 创建时间: 2016年10月26日 下午8:46:01
	 * @param visitorId
	 * @param page 
	 * @param string 
	 */
	List<VisitorApplyOut> queryApplyRecordPageList(@Param("visitorId")String visitorId,
			@Param("accountId")String accountId, PagingBounds page);
	/**
	 * 删除邀请记录
	 * 方法描述 :
	 * 创建人 :  hzhang
	 * 创建时间: 2016年11月27日 下午1:13:42
	 * @param applyId
	 * @return
	 */
	int deleteApplyRecord(String applyId);
	/**
	 * 删除访客（逻辑删除）
	 * @param visitorId
	 * @return
	 */
	int updateVisitorDelete(@Param("visitorId") String visitorId,@Param("accountId")String accountId);
	
	List<VisitorApply> queryVistorApplyByParams(VisitorApplyIn in);
	
	List<VisitorModel> queryVistorByParams(VisitorIn in);
	
	int updateMobilephone(@Param("mobilePhone") String mobilePhone,@Param("visitorId") Long visitor_id);
	
	int updateDynamic(@Param("model")VisitorIn  model);
	
	
	List<VisitorRecord> queryVaPageListByProjectId(VisitorApplyIn pin, PagingBounds page);
	/**
	 * 
	 * @param visitorId
	 * @return
	 */
	MyvisitorPojo findByPrimary(String visitorId);
}
