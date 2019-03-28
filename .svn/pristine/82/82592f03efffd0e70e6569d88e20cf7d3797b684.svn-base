package com.bossbutler.mapper.visitor;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.ImagePojo;
import com.bossbutler.pojo.org.OrgProjectRelation;
import com.bossbutler.pojo.project.Project;
import com.bossbutler.pojo.regional.ArrangeModel;
import com.bossbutler.pojo.regional.Device;
import com.bossbutler.pojo.regional.RegionalEmpRelationModel;
import com.bossbutler.pojo.regional.RegionalModel;
import com.bossbutler.pojo.regional.TransitModel;
import com.bossbutler.pojo.regional.VisitorMedia;
import com.bossbutler.pojo.regional.VisitorModel;
import com.bossbutler.pojo.visitor.TerminalPojo;
import com.bossbutler.pojo.visitor.VisitorApply;
import com.bossbutler.pojo.visitor.VisitorApplyType;
import com.bossbutler.pojo.wx.MiniVisitorApplyInfo;
public interface VisitorApplyMapper {
	/**
	 * 查找某个访客是否已经在数据库里
	 * @param project_code
	 * @param idNo
	 * @return
	 */
	VisitorModel findVisitorByIdCard(@Param("project_code")String proiject_id,@Param("idNo")String idNo);

	void createVisitor(VisitorModel model);
	
	/**
	 * 方法描述:动态更新值
	 * 创建人: llc
	 * 创建时间: 2018年10月20日 上午11:02:41
	 * @param mdoel
	 * @return int
	*/
	int updateDynamic(@Param("model")VisitorApply mdoel);
	
	/**
	 * 方法描述:动态查询
	 * 创建人: llc
	 * 创建时间: 2018年10月20日 上午11:02:41
	 * @param mdoel
	 * @return int
	*/
	List<VisitorApply> queryDynamic(@Param("model")VisitorApply mdoel);
	
	/**
	 * 方法描述:动态条件更新值
	 * 创建人: llc
	 * 创建时间: 2019年1月4日 上午12:24:31
	 * @param mdoel
	 * @param queryModel
	 * @return int 
	*/
	int updateDynamicQuery(@Param("model")VisitorApply mdoel,@Param("queryModel")VisitorApply queryModel);

	List<RegionalModel> getPublicRegionalByMachId(@Param("machindeId")String machindeId);

	VisitorModel findVisitorById(@Param("id")long id);
	VisitorModel queryVisitorByIcProject(@Param("projectCode")String projectCode,@Param("icNo")String icNo);

	List<RegionalModel> getRegionalListByEmpId(@Param("list")List<RegionalEmpRelationModel> list);

	List<Device> getDeviceByRegionalId(@Param("list")List<Long> regList);
	
	List<Device> getAllDeviceByRegionalId(@Param("list")List<Long> regList);

	Project getProjectByRegionalId(@Param("id")Long regional_id);

	void createTransitList(List<TransitModel> transitList);

	List<Device> getDeviceByContents(@Param("contents")List<Long> list);

	void createVisitorApply(VisitorModel model);

	VisitorMedia hasOtherIc(@Param("visitor_id")String visitor_id);

	void createVisitorMedia(VisitorMedia media);

	VisitorApply findByVisitorCode(@Param("code")String code);

	List<ArrangeModel> getPublicAllArrange(@Param("id")String projectId);

	long addArrangeToRegional(List<ArrangeModel> list);

	void deleteArrange(@Param("regionalId")String regionalId);

	Project getProjectById(@Param("projectId")String projectId);

	void updatePhoneNumber(@Param("visitorId")String visitorId,@Param("phone") String phone,@Param("operatorId")String operatorId);

	RegionalModel getRegionalByOrgId(@Param("orgId")long orgId);

	void createRegional(RegionalModel regional);

	void setTerminalTransitTime(@Param("mackAddress")String mackAddress,@Param("transitTime") String transitTime,@Param("operatorId")String operatorId);

	TerminalPojo findTransitById(@Param("mackAddress")String mackAddress);

	void setTerminalDesc(@Param("mackAddress")String mackAddress, @Param("terminalDesc")String terminalDesc,@Param("operatorId") String operatorId);

	void setBarcodeRemark(@Param("mackAddress")String mackAddress, @Param("barcodeRemark")String barcodeRemark,@Param("operatorId") String operatorId);

	OrgProjectRelation getOrgyProId(@Param("projectId")Long projectId);

	List<OrgProjectRelation> findOrgListByPId(@Param("projectId")long project_id);
	/**
	 * 获得启用的公共节点
	 * @param string
	 * @return
	 */
	List<RegionalModel> getPublicRegionalEnableByMachId(@Param("machindeId")String machindeId);
	/**
	 * 查询上传媒体当前序列号
	 * @param mediamainclassify11
	 * @param mediatypevisitor09
	 * @param valueOf
	 * @return
	 */
	ImagePojo queryVisitorOrdinal(@Param("mainClassify") String main_classify, 
			@Param("mediaType") String mediaType,@Param("visitorId") String visitorId);
	
	/**
	 * 查询访客身份类型字典
	 */
	List<VisitorApplyType> getApplyTypes(@Param("typeId") String typeId);
	
	int deleteVisitorMedia(@Param("mediaId")String mediaId);
	/**
	 * 查找是否为长期访客
	 * @param visitorId
	 * @return
	 */
	VisitorApply findLongtermByVisitorId(@Param("visitorId")String visitorId);
	
	int deleteLongtermByVisitorId(@Param("visitorId")String visitorId);

	int updateVisitorIcNo(@Param("icNo")String icNo, @Param("operatorId")String operatorId, @Param("visitorId")Long visitorId);
	
	/**
	 * 查询邀请访客信息
	 * @param visitorCode
	 * @return
	 */
	MiniVisitorApplyInfo getVisitorApply(String visitorCode);

	int selectVisitorApplyResultCount(@Param("projectId") long projectId, @Param("applyId") String applyId, @Param("deviceTransit") String deviceTransit, @Param("transitCode") String transitCode);


}