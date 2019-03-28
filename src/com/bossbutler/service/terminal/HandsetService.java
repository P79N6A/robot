package com.bossbutler.service.terminal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bossbutler.common.InitConf;
import com.bossbutler.common.constant.AccountConstant;
import com.bossbutler.common.constant.RegionalConstant;
import com.bossbutler.common.constant.ServiceConstant;
import com.bossbutler.common.constant.StatusType;
import com.bossbutler.common.constant.VisitorConstant;
import com.bossbutler.mapper.myregional.Arrange;
import com.bossbutler.mapper.myregional.ArrangeMapper;
import com.bossbutler.mapper.myregional.Building;
import com.bossbutler.mapper.myregional.BuildingMapper;
import com.bossbutler.mapper.myregional.DeviceMapper;
import com.bossbutler.mapper.myregional.MyRegionalMapper;
import com.bossbutler.mapper.org.OrgMapper;
import com.bossbutler.mapper.project.ProjectMapper;
import com.bossbutler.mapper.system.AccountMapper;
import com.bossbutler.mapper.system.AccountTransitMapper;
import com.bossbutler.mapper.system.EmpMapper;
import com.bossbutler.mapper.visitor.InviteVisitorMapper;
import com.bossbutler.mapper.visitor.TerminalMapper;
import com.bossbutler.mapper.visitor.VisitorApplyMapper;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.FileResultOut;
import com.bossbutler.pojo.ImageFilePojo;
import com.bossbutler.pojo.ImageFileView;
import com.bossbutler.pojo.ImageInfoPojo;
import com.bossbutler.pojo.MyArrangePojo;
import com.bossbutler.pojo.MyvisitorPojo;
import com.bossbutler.pojo.OrgModel;
import com.bossbutler.pojo.PagingBounds;
import com.bossbutler.pojo.RegionalPojo;
import com.bossbutler.pojo.VisitorInfoPojo;
import com.bossbutler.pojo.org.Org;
import com.bossbutler.pojo.project.Project;
import com.bossbutler.pojo.regional.Device;
import com.bossbutler.pojo.regional.TransitModel;
import com.bossbutler.pojo.regional.VisitorMedia;
import com.bossbutler.pojo.regional.VisitorModel;
import com.bossbutler.pojo.system.Account;
import com.bossbutler.pojo.system.AccountTransit;
import com.bossbutler.pojo.system.EmpRelations;
import com.bossbutler.pojo.visitor.LongtermVisitor;
import com.bossbutler.pojo.visitor.Terminal;
import com.bossbutler.pojo.visitor.TerminalIn;
import com.bossbutler.pojo.visitor.TerminalPojo;
import com.bossbutler.pojo.visitor.VisitorApply;
import com.bossbutler.pojo.visitor.VisitorApplyContents;
import com.bossbutler.pojo.visitor.VisitorApplyIn;
import com.bossbutler.pojo.visitor.VisitorApplyType;
import com.bossbutler.pojo.visitor.VisitorIn;
import com.bossbutler.pojo.visitor.VisitorRecord;
import com.bossbutler.pojo.wx.MiniAppletIn;
import com.bossbutler.pojo.wx.MiniVisitorApplyInfo;
import com.bossbutler.service.BasicService;
import com.bossbutler.service.passthrough.PassFilterThroughService;
import com.bossbutler.service.visitor.VisitorService;
import com.bossbutler.util.GenerateCreater;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.RedisConstant;
import com.bossbutler.util.ReturnPageBean;
import com.bossbutler.util.WriteToPage;
import com.common.cnnetty.gateway.util.HexUtil;
import com.company.common.redis.active.CacheManager;
import com.company.exception.AppException;
import com.company.util.BeanUtility;
import com.company.util.CommonUtil;
import com.company.util.DateUtil;

@Service
public class HandsetService extends BasicService {
	
	/**
	 * 时间格式模板
	 */
	private static final SimpleDateFormat simpleFormat = new SimpleDateFormat(DateUtil.PATTERN_STANDARD);
	@Autowired
	private  TerminalMapper terminalMapper;
	@Autowired
	private  ProjectMapper projectMapper;
	@Autowired
	private  InviteVisitorMapper inviteVisitorMapper;
	@Autowired
	private  VisitorApplyMapper visitorApplyMapper;
	@Autowired
	private  EmpMapper empMapper;
	@Autowired
	private  AccountMapper accountMapper;
	@Autowired
	private  DeviceMapper deviceMapper;
	@Autowired
	private  ArrangeMapper arrangeMapper;
	@Autowired
	private  MyRegionalMapper regionalMapper;
	@Autowired
	private  AccountTransitMapper accountTransitMapper;
	@Autowired
	private  BuildingMapper buildingMapper;
	@Autowired
	private PassFilterThroughService passFilterThroughService;
	
	@Autowired
	private VisitorService visitorService;
	
	@Autowired
	private OrgMapper orgMapper;

	public String init(ControllerView view) throws AppException {
		String dataJson = view.getData();
		// 实体内容转为对象
		TerminalIn in = BeanUtility.jsonStrToObject(dataJson, TerminalIn.class, true);
		Terminal t = terminalMapper.findTerminalByParams(in);
		return WriteToPage.setResponseMessage(t, IfConstant.SERVER_SUCCESS);
	}

	public String createVisitor(ImageFileView view) throws Exception {
		Map<String,String> retMap =  new  HashMap<String,String>();
		String dataJson = view.getData();
		MiniAppletIn in = BeanUtility.jsonStrToObject(dataJson, MiniAppletIn.class, true);
		/*String visitorType=StringUtils.isNotBlank(in.getVisitorType())?in.getVisitorType():"03";*/
		String visitorType="03";
		Project project = this.projectMapper.findByPrimaryKey(in.getProjectId()+"");

		//查询改项目下是否已存在该访客
		VisitorIn vin = new VisitorIn();
		vin.setIdCardNo(in.getVisitorIdNo());
		vin.setProjectCode(project.getProjectCode());
		vin.setVisitorType(visitorType);
		List<VisitorModel> vlist = inviteVisitorMapper.queryVistorByParams(vin );
		if(vlist !=null && vlist.size()>0 && vlist.get(0) !=null){
			if(!vlist.get(0).getStatus_code().equals("01")){
				return WriteToPage.setResponseMessageForError(vlist.get(0).getStatus_remark(), "999999999");
			}
		}
		//验证手机号
		
		if(StringUtils.isNotBlank(in.getMobilePhone())){
			VisitorIn vin1 = new VisitorIn();
			vin1.setPhone(in.getMobilePhone());
			List<VisitorModel> mlist = inviteVisitorMapper.queryVistorByParams(vin1);
			if(mlist !=null && mlist.size()>0){
				VisitorModel vm=mlist.get(0);
				if(!vm.getIdcard().equals(in.getVisitorIdNo())){
					return WriteToPage.setResponseMessageForError("该手机号已经被其他访客"+encryptName(vm.getVisitor_name().trim())+"绑定", "999999999");
				}
			}
		}
		//获取项目配置信息
		String projectProfile = "0";
		Map<String, String> params = new HashMap<>();
		params.put("projectId", in.getProjectId()+"");
		params.put("type", "70402");//安防级别配置
		String profile = this.projectMapper.getProjectProfileByParam(params);
		if(StringUtils.isNotBlank(profile)){//如果没有查到安防级别
			Map<String,Object> pm = BeanUtility.jsonStrToObject(profile, Map.class, true);
			projectProfile = (String) pm.get("protectLevel");
		}
		if (vlist.size() >0 && vlist != null) {
			VisitorMedia vm = visitorApplyMapper.hasOtherIc(vlist.get(0).getVisitor_id()+"");
			ImageInfoPojo info = BeanUtility.jsonStrToObject(dataJson, ImageInfoPojo.class, true);
			info.setBussinessID(vlist.get(0).getVisitor_id()+"");
			view.setData(BeanUtility.objectToJsonStr(info, true));
			if(null == vm){
				this.updateServerImage(view,null,null);
			}else{
				this.updateServerImage(view,vm.getCreate_time(),vm.getMedia_id());
			}
			
			VisitorIn vi=new  VisitorIn();
			vi.setStatusCodes("01");
			vi.setVisitorType(visitorType);
			vi.setCheckCnt("0");
			vi.setVisitorId(vlist.get(0).getVisitor_id()+"");
			//更新手机号
			if(StringUtils.isNotBlank(in.getMobilePhone()) && !in.getMobilePhone().equals(vlist.get(0).getMobilephone())){
				vi.setPhone(in.getMobilePhone());
			}
			inviteVisitorMapper.updateDynamic(vi);
			retMap.put("visitorId", vlist.get(0).getVisitor_id()+"");
			retMap.put("passProfile", projectProfile);
			retMap.put("mobilePhone", vlist.get(0).getMobilephone());
			return WriteToPage.setResponseMessage(retMap, IfConstant.SERVER_SUCCESS);
		}
		VisitorModel visitor = new VisitorModel();
		visitor.setVisitor_type(visitorType);
		visitor.setVisitor_id(getIdWorker().nextId());
		visitor.setTransit_code(HexUtil.intToHex(Integer.parseInt(GenerateCreater.getTransitCode()), 4));
		visitor.setVisitor_name(in.getVisitorName());
		visitor.setMobilephone(in.getMobilePhone());
		visitor.setProject_code(project.getProjectCode());
		visitor.setIdcard(in.getVisitorIdNo());
		visitor.setStatus_code("01");
		visitor.setCheckCnt("0");
		visitorApplyMapper.createVisitor(visitor);
		//访客相片
		ImageInfoPojo info = BeanUtility.jsonStrToObject(dataJson, ImageInfoPojo.class, true);
		info.setBussinessID(visitor.getVisitor_id()+"");
		view.setData(BeanUtility.objectToJsonStr(info, true));
		Map<String,String> map = this.updateServerImage(view,null,null);
		if(null ==map){
			return WriteToPage.setResponseMessageForError("图片上传失败！", IfConstant.UNKNOWN_ERROR.getCode());
		}
		VisitorMedia media = new VisitorMedia();
		media.setMedia_id(map.get("mediaID"));
		media.setVisitor_id(visitor.getVisitor_id()+"");
		media.setMedia_type("61107");//媒体类别（61107,访客身份证；61108，访客其他证件）
		media.setFile_type("png");
		media.setMedia_no(in.getVisitorIdNo());
		media.setMain_classify("60002");
		media.setUpdate_time(DateUtil.getCurrentDate());
		media.setOrdinal(1);
		media.setData_version(DateUtil.getCurrentDate());
		media.setRemark(in.getIdcardInfo());
		this.visitorApplyMapper.createVisitorMedia(media);
		retMap.put("visitorId", visitor.getVisitor_id()+"");
		retMap.put("passProfile", projectProfile);
		return WriteToPage.setResponseMessage(retMap, IfConstant.SERVER_SUCCESS);
	}
	public String orgList(ControllerView view, Logger logger) throws Exception {
		String dataJson = view.getData();
		MiniAppletIn in = BeanUtility.jsonStrToObject(dataJson, MiniAppletIn.class, true);
		VisitorIn vin = new VisitorIn();
		//查询个人下是那些项目的访客
//		if(StringUtils.isBlank(in.getOrgName())){
//			return WriteToPage.setResponseMessage("[]", IfConstant.SERVER_SUCCESS);
//		}
		if (StringUtils.isNotBlank(in.getAccountId())) {
			Map params = new HashMap<>();
			params.put("accountId", in.getAccountId());
			params.put("projectId", in.getProjectId());
			List<EmpRelations> empl = this.empMapper.queryEmpByAcc(params);
			for (EmpRelations ee : empl) {
				String des ="";
				String resourcName = this.projectMapper.getResourceNameByOrgIds(ee.getOrgId()+"");
				des = ee.getEmpName()+"("+ee.getMobilephone()+")--"+ee.getSuperName();
				if (StringUtils.isNotBlank(resourcName)) {
					des += "("+resourcName+")";
				}
				ee.setSuperName(des);
			}
			return WriteToPage.setResponseMessage(empl, IfConstant.SERVER_SUCCESS);
		}
		List<Org> plist = projectMapper.queryOrgByProjectId(in.getProjectId()+"",in.getOrgName());
		if(plist != null && plist.size()>0){
			for (Org o : plist) {
				if(StringUtils.isBlank(o.getPassProfile())){
					o.setPassProfile("0"); 
				}else{
					Map profile	= BeanUtility.jsonStrToObject(o.getPassProfile(), Map.class, false);
					o.setPassProfile(profile.get("protectLevel").toString());
				}
			}
		}
		return WriteToPage.setResponseMessage(plist, IfConstant.SERVER_SUCCESS);
	}
	public String getAccountInfo(ControllerView view, Logger logger) throws AppException {
		Map<String,Object> retMap = new HashMap<>();
		String dataJson = view.getData();
		MiniAppletIn in = BeanUtility.jsonStrToObject(dataJson, MiniAppletIn.class, true);
		Account account = this.accountMapper.queryUserByPhone(in.getMobilePhone());
		if (account != null) {
			List<EmpRelations> emp = this.queryEmpByAccProPer(account.getAccountId(), in.getProjectId()+"",null,in.getOrgId());
			if (emp.size()>0 && emp!=null) {
				if (StringUtils.isNotBlank(in.getOrgId())  ) {
					if(!(in.getOrgId().equals(emp.get(0).getOrgId()+""))){
						return WriteToPage.setResponseMessageForError("该手机用户不在本企业", "99999999");
					}
				}
				Map params = new HashMap<>();
				params.put("accountId", account.getAccountId());
				params.put("projectId", in.getProjectId());
				params.put("orgId", in.getOrgId());
				List<EmpRelations> empl = this.empMapper.queryEmpByAcc(params);
				retMap.put("accountId", account.getAccountId());
				retMap.put("empList", empl);
				return WriteToPage.setResponseMessage(retMap, IfConstant.SERVER_SUCCESS);
			}
			return WriteToPage.setResponseMessageForError("该手机用户不在本大厦内", "99999999");
		}
		return WriteToPage.setResponseMessageForError("该手机用户不是本平台用户", "99999999");
	}
	public String createVisitorApply(ControllerView view, Logger logger) throws Exception {
		String dateTime = String.valueOf(new Date().getTime());
		String dataJson = view.getData();
		MiniAppletIn in = BeanUtility.jsonStrToObject(dataJson, MiniAppletIn.class, true);
		String beginDate="";
		String endDate="";
		String beginTime="";
		String endTime="";
		
		if(StringUtils.isNotBlank(in.getBeginTime())){
			beginTime=in.getBeginTime();
		}else{
			beginTime="00:00:00";
		}
		if(StringUtils.isNotBlank(in.getEndTime())){
			endTime=in.getEndTime();
		}else{
			endTime="23:59:59";
		}
		if(StringUtils.isNotBlank(in.getBeginDate())){
			beginDate=in.getBeginDate();
		}else{
			beginDate=DateUtil.format(new Date(), DateUtil.PATTERN_DATE);
		}
		if(StringUtils.isNotBlank(in.getEndDate())){
			endDate=in.getEndDate();
		}else{
			endDate=DateUtil.format(new Date(), DateUtil.PATTERN_DATE);
		}
		if(StringUtils.isBlank(in.getVisitorId()) || in.getProjectId() ==null){
			return WriteToPage.setResponseMessage("参数有误", IfConstant.CUSTOM);
		}
		
		MyvisitorPojo visitor = inviteVisitorMapper.findById(in.getVisitorId());
		visitor.setProjectId(in.getProjectId());
		if(visitor==null){
			return WriteToPage.setResponseMessage("访客不存在", IfConstant.CUSTOM);
		}
		
		
		VisitorApply va=new VisitorApply();
		va.setStatusCode("02");
		VisitorApply vaQuery=new VisitorApply();
		vaQuery.setVisitorId(new Long(in.getVisitorId()));
		vaQuery.setStatusCode("01");
		List<VisitorApply> vaList=visitorApplyMapper.queryDynamic(vaQuery);
		if(vaList !=null && vaList.size()>0){
			vaQuery.setVisitorId(null);
			StringBuffer sb=new StringBuffer();
			for(VisitorApply va2:vaList){
				sb.append(va2.getRelationId()+",");
			}
			vaQuery.setRelationIds(sb.substring(0, sb.length()-1));
			visitorApplyMapper.updateDynamicQuery(va,vaQuery);
		}
		
		
		
		Project project = this.projectMapper.findByPrimaryKey(in.getProjectId()+"");
		VisitorInfoPojo apply = new VisitorInfoPojo();
		
		
		
		String arrangeIds="";
		if(StringUtils.isNotBlank(in.getArrangeId())){//房源id前台实际传的是房源ID
			String resourceId=in.getArrangeId();
			OrgModel orgModel=orgMapper.getOrgByRId(resourceId);
			if(orgModel !=null){
				in.setOrgId(orgModel.getOrgId()+"");
				//apply.setOrgId(orgModel.getOrgId()+"");
			}
			Arrange arrange=arrangeMapper.getArrangeByReId(resourceId);//查询房源所在的节点
			if(arrange !=null){
				arrangeIds=arrange.getArrangeId()+"";
				in.setArrangeId(arrange.getArrangeId()+"");
				apply.setArrangeId(arrange.getArrangeId()+"");
			}
		}else{// 访问个人，访问组织 ，都会带有组织id
			// 查询组织房源关联的节点
			apply.setOrgId(in.getOrgId()+"");
			apply.setAccountId(CommonUtil.string2Long(in.getAccountId()));
			arrangeIds = this.deviceMapper.queryArrangeByOrgId(in.getOrgId());
			if(StringUtils.isBlank(arrangeIds)){
				arrangeIds="null";
			}
			
		}
		
		//查找节点的父节点
		Arrange arrange =new Arrange();
		arrange.setArrangeIds(arrangeIds);
		arrange.setEntrance_type(RegionalConstant.ArrangeType01);
		List<Arrange> arrPubList=arrangeMapper.queryParentDynamic(arrange);
		
		// 查找内部访客区域（员工）节点
		List<Arrange> arrRegList=new ArrayList<Arrange>();
		if(StringUtils.isNotBlank(in.getOrgId()) ){
			Arrange arrange02 =new Arrange();
			arrange02.setProjectId(project.getProjectId());
			arrange02.setRegionalType("03");
			arrange02.setOrgId(CommonUtil.string2Long(in.getOrgId()));
			arrange02.setScopeType("01");
			arrRegList =arrangeMapper.getArrRegDynamic(arrange02);
		}
		
				
		StringBuffer sb=new StringBuffer();
		
		for(Arrange arr:arrPubList){
			sb.append(arr.getArrangeId()+",");
		}
		for(Arrange arr:arrRegList){
			sb.append(arr.getArrangeId()+",");
		}
		if (sb.length()==0) {
			// 查找业主内部访客区域（员工）节点
			Arrange arrange02 =new Arrange();
			arrange02.setProjectId(project.getProjectId());
			arrange02.setRegionalType("03");
			arrange02.setOrgId(project.getAdminOrg());
			arrange02.setScopeType("01");
			arrRegList =arrangeMapper.getArrRegDynamic(arrange02);
			
			for(Arrange arr:arrRegList){
				sb.append(arr.getArrangeId()+",");
			}
			
			if (sb.length()==0) {
				return WriteToPage.setResponseMessage("{}", IfConstant.NO_PERMISSIONS, dateTime);
			
			}
		}
		
		//查询节点下设备
		Device d=new Device();
		d.setArrangeIds(sb.substring(0, sb.length()-1));
		List<Device> deviceList=deviceMapper.queryDynamic(d);
		List<String> transitDevices = new ArrayList<>();
		apply.setBeginTime(beginTime);
		apply.setEndTime(endTime);
		String openAccess = this.createOpenAccess(deviceList, transitDevices,apply);
		VisitorApplyContents vac = new VisitorApplyContents();
		vac.setIdCode(visitor.getTransitCode());
		vac.setDevicePre(project.getProjectCode());
		vac.setOpenAccess(openAccess);
		vac.setSynchCode("00000000");//同步码
		String address = project.getAddress();
		vac.setAddress(address);
		
		
		//通行策略
		VisitorApply va2=new VisitorApply();
		va2.setProjectId(new Long(in.getProjectId()));
		va2.setSourceType("03");
		String trafficStrategy=visitorService.updateTrafficStrategy(va2);
		
		
		if(StringUtils.isNotBlank(trafficStrategy)){
			apply.setTrafficStrategy(trafficStrategy);
		}
		apply.setRelationid(getIdWorker().nextId());
		apply.setProjectId(in.getProjectId()+"");
		apply.setFollows(1);
		apply.setVisitorid(Long.parseLong(in.getVisitorId()));
		apply.setTransitCode(visitor.getTransitCode());
		apply.setTime(DateUtil.format(new Date(), DateUtil.PATTERN_DATE));
		apply.setBegindate(beginDate);
		apply.setEnddate(endDate);
		
		apply.setRemark(in.getVisitorReason());
		apply.setOperatorId(in.getOperatorId());
		apply.setContents(BeanUtility.objectToJsonStr(vac, false));
		apply.setVisitorCode(CommonUtil.generateShortUuid());
		apply.setContents(BeanUtility.objectToJsonStr(vac, false));
		apply.setVisitorCode(CommonUtil.generateShortUuid());
		apply.setSourceType("03");
		apply.setApplyType(in.getApplyType());
		
		//end
		
		apply.setTransitDevices(StringUtils.join(transitDevices, ","));
		inviteVisitorMapper.insertVisitorApply(apply);
		
		passFilterThroughService.visitorApplyCache(apply.getRelationid()+"");
		in.setVisitorCode(apply.getVisitorCode());
		return WriteToPage.setResponseMessage(getVisitorApply(in), IfConstant.SERVER_SUCCESS);
	}
	
	public String createApplyByArrId(ControllerView view, Logger logger) throws Exception {
		String dataJson = view.getData();
		MiniAppletIn in = BeanUtility.jsonStrToObject(dataJson, MiniAppletIn.class, true);
		//如果有邀请记录直接返回邀请码
		
		String beginDate="";
		String endDate="";
		String beginTime="";
		String endTime="";
		
		
		if(StringUtils.isNotBlank(in.getBeginDate())){
			long lt = new Long(in.getBeginDate());
		    Date date = new Date(lt);
		    beginDate=DateUtil.format(date, DateUtil.PATTERN_DATE);
		    beginTime=DateUtil.format(date, DateUtil.PATTERN_TIME);
		}else{
			beginDate=DateUtil.format(new Date(), DateUtil.PATTERN_DATE);
			beginTime="00:00:00";
		}
		if(StringUtils.isNotBlank(in.getEndDate())){
			long lt = new Long(in.getEndDate());
		    Date date = new Date(lt);
		    endDate=DateUtil.format(date, DateUtil.PATTERN_DATE);
		    endTime=DateUtil.format(date, DateUtil.PATTERN_TIME);
		}else{
			endDate=DateUtil.format(new Date(), DateUtil.PATTERN_DATE);
			endTime="23:59:59";
		}
		
		if(in.getProjectId() ==null || StringUtils.isBlank(in.getVisitorId())){
			return WriteToPage.setResponseMessage("参数有误", IfConstant.CUSTOM);
		}  
		
		MyvisitorPojo visitor = inviteVisitorMapper.findById(in.getVisitorId());
		if(visitor==null){
			return WriteToPage.setResponseMessage("访客不存在", IfConstant.CUSTOM);
		}
		visitor.setProjectId(in.getProjectId());
		VisitorApply va=new VisitorApply();
		va.setStatusCode("02");
		VisitorApply vaQuery=new VisitorApply();
		vaQuery.setVisitorId(new Long(in.getVisitorId()));
		vaQuery.setStatusCode("01");
		List<VisitorApply> vaList=visitorApplyMapper.queryDynamic(vaQuery);
		if(vaList !=null && vaList.size()>0){
			vaQuery.setVisitorId(null);
			StringBuffer sb=new StringBuffer();
			for(VisitorApply va2:vaList){
				sb.append(va2.getRelationId()+",");
			}
			vaQuery.setRelationIds(sb.substring(0, sb.length()-1));
			visitorApplyMapper.updateDynamicQuery(va,vaQuery);
		}
		
		Project project = this.projectMapper.findByPrimaryKey(in.getProjectId()+"");
		VisitorInfoPojo apply = new VisitorInfoPojo();
		List<Device> deviceList = new ArrayList<>();
		if(StringUtils.isNotBlank(in.getArrangeId())){//		
			String arrangeIds=in.getArrangeId();
			apply.setArrangeId(arrangeIds);
			Device d=new Device();
			d.setArrangeIds(arrangeIds);
			deviceList = this.deviceMapper.queryDynamic(d);
			
			if (deviceList.size() == 0) {
				deviceList = deviceMapper.queryByEntranceTypeProjectIdNotAccountId(in.getProjectId()+"", RegionalConstant.ArrangeType01,null,null);
			}
		}else{
			deviceList = this.deviceMapper.queryByProjectIdOrOrgId(in.getProjectId(), in.getOrgId());
			if (deviceList.size() == 0) {
				deviceList = deviceMapper.queryByEntranceTypeProjectIdNotAccountId(in.getProjectId()+"", RegionalConstant.ArrangeType01,null,null);
			}
		}

		List<String> transitDevices = new ArrayList<>();
		apply.setBeginTime(beginTime);
		apply.setEndTime(endTime);
		String openAccess = this.createOpenAccess(deviceList, transitDevices,apply);
		VisitorApplyContents vac = new VisitorApplyContents();
		vac.setIdCode(visitor.getTransitCode());
		vac.setDevicePre(project.getProjectCode());
		vac.setOpenAccess(openAccess);
		vac.setSynchCode("00000000");//同步码
		String address = project.getAddress();
		vac.setAddress(address);
		
		
		//通行策略
		VisitorApply va2=new VisitorApply();
		va2.setProjectId(new Long(in.getProjectId()));
		va2.setSourceType("01");
		String trafficStrategy=visitorService.updateTrafficStrategy(va);
		
		
		
		
		if(StringUtils.isNotBlank(trafficStrategy)){
			apply.setTrafficStrategy(trafficStrategy);
		}
		apply.setOrgId(null);
		apply.setRelationid(getIdWorker().nextId());
		apply.setProjectId(in.getProjectId()+"");
		apply.setFollows(1);
		apply.setVisitorid(Long.parseLong(in.getVisitorId()));
		apply.setTransitCode(visitor.getTransitCode());
		apply.setTime(DateUtil.format(new Date(), DateUtil.PATTERN_DATE));
		apply.setBegindate(beginDate);
		apply.setEnddate(endDate);
		
		apply.setRemark(in.getVisitorReason());
		apply.setOperatorId(in.getOperatorId());
		apply.setContents(BeanUtility.objectToJsonStr(vac, false));
		apply.setVisitorCode(CommonUtil.generateShortUuid());
		apply.setContents(BeanUtility.objectToJsonStr(vac, false));
		apply.setVisitorCode(CommonUtil.generateShortUuid());
		apply.setSourceType("01");
		apply.setApplyType(in.getApplyType());
		
		//end
		
		apply.setTransitDevices(StringUtils.join(transitDevices, ","));
		inviteVisitorMapper.insertVisitorApply(apply);
		in.setVisitorCode(apply.getVisitorCode());
		passFilterThroughService.visitorApplyCache(apply.getRelationid()+"");

		return WriteToPage.setResponseMessage(getVisitorApply(in), IfConstant.SERVER_SUCCESS);
		//return WriteToPage.setResponseMessage(in.getVisitorCode(), IfConstant.SERVER_SUCCESS);
	}
	public String building(ControllerView view, Logger logger) throws AppException {
		String dataJson = view.getData();
		Map in = BeanUtility.jsonStrToObject(dataJson, Map.class, true);
		String parentId = in.get("parentId").toString() == null ? "" :in.get("parentId").toString();
		if (StringUtils.isBlank(parentId)) {
			in.put("resourceType", "01");
		}
		List<Building> list = this.buildingMapper.queryByProjectIdOrType(in);
		return WriteToPage.setResponseMessage(list, IfConstant.SERVER_SUCCESS);
	}
	
	/**
	 * 方法描述:获取通行区域
	 * 创建人: llc
	 * 创建时间: 2018年12月27日 下午7:43:48
	 * @param view
	 * @param logger
	 * @return
	 * @throws AppException String 
	*/
	public String trafficNode(ControllerView view, Logger logger) throws AppException {
		String dataJson = view.getData();
		Map in = BeanUtility.jsonStrToObject(dataJson, Map.class, true);
		String projectId = in.get("projectId")==null ? "" :in.get("projectId").toString();
		if (StringUtils.isBlank(projectId)) {
			return WriteToPage.setResponseMessageForError("缺失参数", "99999999");
		}
		RegionalPojo rp=new RegionalPojo();
		rp.setProjectId(projectId);
		rp.setStatusCode("01");
		rp.setRegionalTypes("04");
		List<RegionalPojo> rpList=regionalMapper.queryRegional(rp);
		if(rpList==null || rpList.size()==0){
			return WriteToPage.setResponseMessageForError("未设置访客通行区域，请选择其他方式","99999999");
		}
		Map<String,Object> map=new HashMap<String,Object>();
		String regionalId="";
		for(RegionalPojo rpModel:rpList){
			if(StringUtils.isNotBlank(rpModel.getRegionalId())){
				regionalId=rpModel.getRegionalId()+",";
			}
			
		}
		if(regionalId.length()>0){
			MyArrangePojo model=new MyArrangePojo();
			model.setRegionalId(regionalId.substring(0,regionalId.length()-1 ));
			model.setArrangeType("02");
			List<MyArrangePojo> arrangeList=regionalMapper.querymyarrange(model);
			return WriteToPage.setResponseMessage(arrangeList, IfConstant.SERVER_SUCCESS);
			
		}
		return WriteToPage.setResponseMessage("", IfConstant.SERVER_SUCCESS);
		
	
	}
	public String floorRoom(ControllerView view, Logger logger) throws AppException {
		String dataJson = view.getData();
		Map in = BeanUtility.jsonStrToObject(dataJson, Map.class, true);
		String parentId = in.get("parentId").toString() == null ? "" :in.get("parentId").toString();
		if (StringUtils.isBlank(parentId)) {
			in.put("resourceType", "02");
		}else{
			in.put("arrangeId", parentId);
			in.put("parentId", "");
		}
		List<Building> list = this.buildingMapper.queryByProjectIdOrType(in);
		if (list != null && list.size()>0) {
			for (Building building : list) {
				List<Arrange> resourceList = this.buildingMapper.queryResourceByFloor(building.getArrangeId()+"");
				building.setRooms(resourceList);
			}
		}
		return WriteToPage.setResponseMessage(list, IfConstant.SERVER_SUCCESS);
	}
	private Map getVisitorApply(MiniAppletIn in) throws AppException{
		System.out.println("weirongbin===========================================================");
		Map<String,String> retMap =  new HashMap<String, String>();
		MiniVisitorApplyInfo info = this.visitorApplyMapper.getVisitorApply(in.getVisitorCode());
		if (info != null) {
			retMap.put("qrCodeURL", InitConf.getSelfvisitorapplypath()+"create/"+in.getVisitorCode());
			if(StringUtils.isNotBlank(info.getProjectName())){
				retMap.put("projectDesc", info.getProjectName());
				retMap.put("curDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
			}
			if(StringUtils.isNotBlank(info.getOrgName())){
				String orgInfo = info.getOrgName();
				String resurceName = this.projectMapper.getResourceNameByOrgIds(info.getOrgId());
				if(StringUtils.isNotBlank(resurceName)){
					retMap.put("orgRemark", resurceName);
					orgInfo += "("+resurceName+")";
				}
				retMap.put("orgDesc", orgInfo);
			}else if(StringUtils.isNotBlank(info.getAccountId()) && StringUtils.isNotBlank(info.getProjectId()) &&  !"0".equals(info.getAccountId())){
				List<OrgModel> omList=orgMapper.getOrgByAccIdAndPId(info.getAccountId(),info.getProjectId());
				StringBuffer sb=new StringBuffer();
				for(OrgModel om:omList){
					sb.append(om.getOrgId()+",");
				}
				if(sb.length()>0){
					String resurceName = this.projectMapper.getResourceNameByOrgIds(sb.substring(0,sb.length()-1));
					if(StringUtils.isNotBlank(resurceName)){
						retMap.put("orgRemark", resurceName);
					}
				}
				
			}
			if(StringUtils.isNotBlank(info.getAccountId()) && !"0".equals(info.getAccountId())){
				List<EmpRelations> el = this.queryEmpByAccProPer(info.getAccountId(), info.getProjectId(), null,info.getOrgId());
				if (el != null && el.size() > 0) {
					retMap.put("personDesc", el.get(0).getEmpName()+"("+el.get(0).getMobilephone()+")");
				}
			}
			retMap.put("effectiveTime","00:00~23:59");
			retMap.put("effectiveCount","不限");
			if(StringUtils.isNotBlank(info.getProjectProfile())){
				Map<String,String> proMap = BeanUtility.jsonStrToObject(info.getProjectProfile(), Map.class, false);
				String projectRemark = proMap.get("remark") == null ? "" :proMap.get("remark").toString();
				if (StringUtils.isNotBlank(projectRemark)) {
					retMap.put("projectRemark", projectRemark);
				}
			}
			
		}
		return retMap;
	}

	private Map updateServerImage(ImageFileView view,String createTime,String mediaID) throws Exception {
		String dataJson = view.getData();
		List<MultipartFile> multipartFileList = view.getFileBytes();
		long bill_id = 0;
		Map<String,Object> map = new HashMap<String,Object>();
		if (multipartFileList != null  && multipartFileList.size()>0) {
			for (MultipartFile multipartFile : multipartFileList) {
				ImageInfoPojo info = BeanUtility.jsonStrToObject(dataJson, ImageInfoPojo.class, true);
				String fileType = info.getFileType() == null ? "png": info.getFileType();
				if (StringUtils.isBlank(info.getBussinessID())) {
					return null;
				}
				if (bill_id == 0) {
					bill_id = Long.parseLong(info.getBussinessID());
				}
				System.out.println("classify->>"+ServiceConstant.main_classify+"bussinessID->>"+info.getBussinessID()+"fileType->>"+fileType+"bytes->>"+multipartFile);
				FileResultOut fileResultOut = uploadFile(ServiceConstant.main_classify, info.getBussinessID(), false, false,
						multipartFile.getBytes(), "png", createTime, mediaID);
				System.out.println("fileResultOut:>>>"+BeanUtility.objectToJsonStr(fileResultOut, false));
				if (fileResultOut.isSuccess()) {
					String dataJson1 = fileResultOut.getData();
					ImageFilePojo pojo = BeanUtility.jsonStrToObject(dataJson1, ImageFilePojo.class, true);
					map.put("createTime",pojo.getCreateTime());
					map.put("filepath",pojo.getFilepath());
					map.put("mediaID", pojo.getMediaID());
					return map;
				}else{
					return null;
				}
			}
		}
		return null;
	}
	/**
	 * 方法描述 :邀请访客生成二维设备权限
	 * 创建人 :  hzhang
	 * 创建时间: 2016年9月2日 下午11:15:28
	 * @param deviceList
	 * @return 权限值
	 * @throws Exception 
	 */
	private String createOpenAccess(List<Device> deviceList, List<String> transitDevices,VisitorInfoPojo apply) throws Exception {
		SimpleDateFormat dfs = new SimpleDateFormat("HH:mm:ss");
		long between=(dfs.parse(apply.getEndTime()).getTime()-dfs.parse(apply.getBeginTime()).getTime())/1000/60;// 换成分钟
		int index=(int) between;
		StringBuffer sb = new StringBuffer();
		for (Device d : deviceList) {
			if(d.getControllerType().equals("01")){
				String validTime = HexUtil.intToHex(index/10, 1);
				String dcode = d.getDeviceCode();
				dcode = dcode.substring(dcode.length()-4, dcode.length());
				sb.append(dcode+validTime);
			}
			
			String afterStr = d.getDeviceCode();
			if(InitConf.getDahaoSupplierNameMult().equals(d.getControllerType())) {
				afterStr = d.getSerialNumber();
			}
			if(!transitDevices.contains(d.getControllerType()+afterStr)){
				transitDevices.add(d.getControllerType()+afterStr);
			}
		}
		
		
		
		//不够60位后面补0
		if (sb.length() < 60) {
			for (int i = 0, lg = 60 - sb.length(); i < lg; i++) {
				sb.append("F");
			}
		}else if(sb.length() > 60){
			return sb.substring(0, 60);
		}
		return sb.toString();
	}

	/**
	 * 检测访客是否为常住访客
	 * @param view
	 * @return
	 * @throws AppException 
	 */
	public String checkLongtermVisitor(ControllerView view) throws AppException {
		Map<String,Object> retMap = new HashMap<>();
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		VisitorIn in = BeanUtility.jsonStrToObject(dataJson, VisitorIn.class, true);
		//参数校验开始
		if (StringUtils.isBlank(in.getMackAddress())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_MACADDRESS_IS_NOT_NULL);
		}
		if (StringUtils.isBlank(in.getIdCardNo())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_IDCARDNO_IS_NOT_NULL);
		}
		//查询设备对应的项目
		Project project = this.getProjectByMacAdress(in.getMackAddress());
		if (project == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_NOT_EXIST_PROJECT);
		}
		//参数校验结束
		VisitorModel visitor = findVisitorByIdCard(project.getProjectCode(),in.getIdCardNo());
		if (visitor == null) {//已存在项目里的访客
			retMap.put("resultCode", "01");
			retMap.put("message", "访客不存在");
			return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStrLongToStr(retMap, false), IfConstant.SERVER_SUCCESS, dateTime);
		}
		VisitorApply va = this.visitorApplyMapper.findLongtermByVisitorId(visitor.getVisitor_id()+"");
		if (va == null) {
			retMap.put("resultCode", "02");
			retMap.put("message", "已存在访客，但是不是长期访客");
			return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStrLongToStr(retMap, false), IfConstant.SERVER_SUCCESS, dateTime);
		}
		//返回内容
		LongtermVisitor lv = new LongtermVisitor();
		if (StringUtils.isNotBlank(va.getRemark())) {
			@SuppressWarnings("unchecked")
			Map<String,String> remarkMap = BeanUtility.jsonStrToObject(va.getRemark(), Map.class, false);
			if (StringUtils.isNotBlank(remarkMap.get("phone"))) {
				lv.setPhone(remarkMap.get("phone"));
			}
			if (StringUtils.isNotBlank(remarkMap.get("remark"))) {
				lv.setRemarks(remarkMap.get("remark"));
			}
		}
		lv.setName(visitor.getVisitor_name());
		//赋值icCardNo
		if (StringUtils.isNotBlank(visitor.getIccard_code())) {
			lv.setIcCardNo(visitor.getIccard_code());
		}
		//长期访客类型
		List<VisitorApplyType> val = this.visitorApplyMapper.getApplyTypes(VisitorConstant.applyType);
		for (VisitorApplyType vat : val) {
			if (vat.getData_id().equals(va.getApplyType())) {
				lv.setApplyType(vat.getData_name());
			}
		}
		retMap.put("resultCode", "00");
		retMap.put("resultObj", lv);
		retMap.put("message", "是长期访客");
		return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStrLongToStr(retMap, false), IfConstant.SERVER_SUCCESS, dateTime);
	}
	/**
	 * 创建长期访客
	 * @param view
	 * @return
	 * @throws Exception 
	 */
	public String createLongtermVisitor(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		VisitorIn in = BeanUtility.jsonStrToObject(dataJson, VisitorIn.class, true);
		//参数校验开始
		if (StringUtils.isBlank(in.getMackAddress())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_MACADDRESS_IS_NOT_NULL);
		}
		if (StringUtils.isBlank(in.getIdCardNo())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_IDCARDNO_IS_NOT_NULL);
		}
		//查询设备对应的项目
		Project project = this.getProjectByMacAdress(in.getMackAddress());
		if (project == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_NOT_EXIST_PROJECT);
		}
		VisitorModel visitor = findVisitorByIdCard(project.getProjectCode(),in.getIdCardNo());
		if (visitor == null) {//已存在项目里的访客
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_VISITOR_NOT_EXIST, dateTime);
		}
		
		List<Long> regList = new ArrayList<>();
		//建立新名单
		if (StringUtils.isNotBlank(in.getRegionals())) {
			String contents = in.getRegionals();
			List<Map<String,String>> rl = BeanUtility.jsonStrToObject(contents, List.class, false);
			
			if (rl.size()>0 && rl !=null) {
				for (Map<String,String> map : rl) {
					regList.add(Long.parseLong(map.get("regionalId")));
				}
				
			}
		}
		
		
		
		//参数校验结束
		//建立长期访客配置信息开始
		
	 	VisitorApply va = this.visitorApplyMapper.findLongtermByVisitorId(visitor.getVisitor_id()+"");
	 	if (va != null) {
	 		this.visitorApplyMapper.deleteLongtermByVisitorId(visitor.getVisitor_id()+"");
	 		if (StringUtils.isNotBlank(visitor.getIccard_code())) {
	 			//删除卡上的名单
	 			int trannsitCode = HexUtil.hexToInt(visitor.getTransit_code());
				String bn = "10"+this.format8bitInt(trannsitCode, 8, "0");
				List<TransitModel>  tl = regionalMapper.queryTransitByTyAcTcPi(AccountConstant.TransitTypeIc,bn,visitor.getIccard_code(), project.getProjectId());
				if (tl.size()>0) {
					this.deleteTransitList(tl,in.getOperatorId());
				}
				//建立新名单
				if(regList !=null && regList.size()>0){
					List<Device> dl = this.visitorApplyMapper.getDeviceByRegionalId(regList);
					visitor.setBegin_date(in.getBeginDate());
					visitor.setEnd_date(in.getEndDate());
					visitor.setBegin_time(in.getBeginTime());
					visitor.setEnd_time(in.getEndTime());
					this.insertTransitList(dl,project,visitor,va,in.getOperatorId());
				}
						
			}
				
		}
	 	
	 	if(regList !=null && regList.size()>0){
	 		List<Device> deviceList =this.visitorApplyMapper.getAllDeviceByRegionalId(regList);
	 		List<String>  transitDevices= new ArrayList<String>();
	 		for (Device d : deviceList) {
	 			
	 			String td=d.getDeviceCode();
	 			if(d.getControllerType().equals("03")){
	 				td=d.getSerialNumber();
	 			}
	 			if(!transitDevices.contains(d.getControllerType()+td)){
	 				transitDevices.add(d.getControllerType()+td);
	             }
	 			
	 			
	 		}
	 	
	 		if(transitDevices.size()>0){
		 		visitor.setTransit_devices(StringUtils.join(transitDevices, ","));
	 		}
	 		
	 	}
	 	
	 	visitor.setSource_type("01");
	 	visitor.setVisitor_id(visitor.getVisitor_id());
	 	visitor.setVisitor_code(HexUtil.intToHex(Integer.parseInt(GenerateCreater.getTransitCode()), 4));
	 	visitor.setProject_id(project.getProjectId());
		visitor.setRelation_id(this.getIdWorker().nextId());
		visitor.setBegin_date(in.getBeginDate());
		visitor.setEnd_date(in.getEndDate());
		visitor.setBegin_time(in.getBeginTime());
		visitor.setEnd_time(in.getEndTime());
		visitor.setApply_type(in.getApplyType());
		visitor.setContents(in.getRegionals());
		visitor.setItem_num(0);
		visitor.setStatus_code(StatusType.INIT);
		visitor.setOperator_id(Long.parseLong(in.getOperatorId()));
		Map<String, String> remark = new HashMap<>();
		if (StringUtils.isNotBlank(in.getPhone())) {//
			remark.put("phone", in.getPhone());
		}
		if (StringUtils.isNotBlank(in.getRemarks())) {//
			remark.put("remark", in.getRemarks());
		}
		visitor.setRemark(BeanUtility.objectToJsonStr(remark,false));
		this.visitorApplyMapper.createVisitorApply(visitor);
		//建立长期访客配置信息结束
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}
	/**
	 * 绑定常驻人员IcCode
	 * @param view
	 * @return
	 * @throws Exception 
	 */
	public String bindIc(ControllerView view) throws Exception {
		Map<String,Object> retMap = new HashMap<>();
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		VisitorIn in = BeanUtility.jsonStrToObject(dataJson, VisitorIn.class, true);
		//参数校验开始
		if (StringUtils.isBlank(in.getMackAddress())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_MACADDRESS_IS_NOT_NULL);
		}
		if (StringUtils.isBlank(in.getIc())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_ICNO_IS_NOT_NULL);
		}
		if (StringUtils.isBlank(in.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL);
		}
		//查询设备对应的项目
		Project project = this.getProjectByMacAdress(in.getMackAddress());
		if (project == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_NOT_EXIST_PROJECT);
		}
		Account accountT = accountTransitMapper.hasIcCode(in.getIc());
		if (accountT != null ) {
			if (in.getAccountId().equals(accountT.getAccountId()) && in.getIc().equals(accountT.getTransit_code())) {
				retMap.put("resultCode", "01");
				retMap.put("message", "该卡已被此用户绑定，无需重复绑定");
				return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStrLongToStr(retMap, false), IfConstant.SERVER_SUCCESS, dateTime);
			}
			retMap.put("resultCode", "02");
			retMap.put("message", "IC卡号已被其他常住人员绑定，不能进行绑定");
			retMap.put("accountCode", accountT.getAccountCode());
			retMap.put("userName", accountT.getLoginName());
			return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStrLongToStr(retMap, false), IfConstant.SERVER_SUCCESS, dateTime);
		}
		Account account = this.accountMapper.queryUserById(in.getAccountId());
		AccountTransit aTransit = this.accountTransitMapper.findByTypeAccountId(in.getAccountId(), AccountConstant.TransitTypeIc);
		/*List<TransitModel> reTlist = this.regionalMapper.queryTransitListByTyAcTcPi(account.getAccountCode(),AccountConstant.TransitTypeIc,project.getProjectId(),in.getIc());
		if(reTlist != null && reTlist.size()>0){
			retMap.put("resultCode", "03");
			retMap.put("message", "您的账号有没完成绑定，请稍后再试");
			return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStrLongToStr(retMap, false), IfConstant.SERVER_SUCCESS, dateTime);
		}*/
		/*List<TransitModel> decTlist = this.regionalMapper.queryTransitListByTyAcTcPi(account.getAccountCode(),null,project.getProjectId(),in.getIc());
		if (decTlist != null  && decTlist.size()>0) {
			retMap.put("resultCode", "03");
			retMap.put("message", "您的账号有没完成绑定，请稍后再试");
			return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStrLongToStr(retMap, false), IfConstant.SERVER_SUCCESS, dateTime);
		}*/
		//参数校验结束
		//检查ic卡号是否被其他用户绑定
		if (accountT != null ){
		/*		//删除之前别绑定用户
				this.accountTransitMapper.deleteByPrimaryKey(accountT.getTransitId());
				//修改新绑定用户transitCode
				//TODO:以后需要调整accountCode规则 11+accountCode
				List<TransitModel>  tl = regionalMapper.queryTransitByTyAcTcPi(AccountConstant.TransitTypeIc, accountT.getAccountCode()+"",in.getIc(), project.getProjectId());
				if (tl.size()>0) {
					this.deleteTransitList(tl,in.getOperatorId());
				}
				if (aTransit != null) {//当前用户已绑定ic卡
					List<TransitModel>  dtl = regionalMapper.queryTransitByTyAcTcPi(AccountConstant.TransitTypeIc, account.getAccountCode()+"",aTransit.getTransitCode(), project.getProjectId());
					if (dtl.size()>0) {
						this.deleteTransitList(dtl,in.getOperatorId());
					}
					//更新transitCode
					AccountTransit transit = new AccountTransit();
					transit.setTransitCode(in.getIc());
					transit.setTransitId(aTransit.getTransitId());
					accountTransitMapper.update(transit);
				}else{
					accountT = new Account();
					accountT.setAccountCode(account.getAccountCode());
					AccountTransit transit = new AccountTransit();
					transit.setTransitId(this.getIdWorker().nextId());
					transit.setAccountId(Long.parseLong(in.getAccountId()));
					transit.setTransitType(AccountConstant.TransitTypeIc);
					transit.setTransitCode(in.getIc());
					transit.setStatusCode(AccountConstant.TransitStatus01);
					transit.setStatusTime(new Date());
					transit.setCreateTime(new Date());
					transit.setUpdateTime(new Date());
					transit.setDataVersion(new Date());
					transit.setOperatorId(Long.parseLong(in.getOperatorId()));
					accountTransitMapper.insert(transit);
				}*/
		}else{//ic卡没被绑定
			account = new Account();
			account.setAccountCode(this.accountMapper.queryUserById(in.getAccountId()).getAccountCode());
			if (aTransit != null) {//当前用户已绑定ic卡
				List<TransitModel>  dtl = regionalMapper.queryTransitByTyAcTcPi(AccountConstant.TransitTypeIc, account.getAccountCode()+"",aTransit.getTransitCode(), project.getProjectId());
				if (dtl.size()>0) {
					this.deleteTransitList(dtl,in.getOperatorId());
				}
				//更新transitCode
				AccountTransit transit = new AccountTransit();
				transit.setTransitCode(in.getIc());
				transit.setTransitId(aTransit.getTransitId());
				accountTransitMapper.update(transit);
			}else{//当前用户已绑定ic卡
				AccountTransit transit = new AccountTransit();
				transit.setTransitId(this.getIdWorker().nextId());
				transit.setAccountId(Long.parseLong(in.getAccountId()));
				transit.setTransitType(AccountConstant.TransitTypeIc);
				transit.setTransitCode(in.getIc());
				transit.setStatusCode(AccountConstant.TransitStatus01);
				transit.setStatusTime(new Date());
				transit.setCreateTime(new Date());
				transit.setUpdateTime(new Date());
				transit.setDataVersion(new Date());
				transit.setOperatorId(Long.parseLong(in.getOperatorId()));
				accountTransitMapper.insert(transit);
			}
		}
		//查询二维码设备名单
		List<TransitModel>  tl = regionalMapper.queryTransitByTyAcTcPi(AccountConstant.TransitTypeQr, account.getAccountCode()+"",null, project.getProjectId());
		if (tl.size()>0) {
			this.insertTransitListByQrTarnsitList(tl,in.getOperatorId(),in.getIc(),project.getProjectName(),account.getLoginName());
		}
		retMap.put("resultCode", "00");
		retMap.put("message", "IC卡绑定成功");
		return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStrLongToStr(retMap, false), IfConstant.SERVER_SUCCESS, dateTime);
	}
	/**
	 * 返回访客邀请记录里的类型字典
	 *   typeId 为 12
	 */
	public List<VisitorApplyType> getApplyTypes(String typeId) {
		return visitorApplyMapper.getApplyTypes(typeId);
	}
	public Project getProjectByMacAdress(String mackAddress) {
		TerminalPojo transit= visitorApplyMapper.findTransitById(mackAddress);
		if(null == transit){
			return null;
		}
		Project project = visitorApplyMapper.getProjectById(transit.getProject_id()+"");
		return project;
	}
	//查找临时访客是否已经有记录
	public VisitorModel findVisitorByIdCard(String proiject_code,String idNo) {
		return visitorApplyMapper.findVisitorByIdCard(proiject_code,idNo);
	}
	/**
	 * 长期访客绑定IC卡
	 * @param view
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@SuppressWarnings("unchecked")
	public String bindingLongtermVisitor(ControllerView view) throws NumberFormatException, Exception {
		Map<String,Object> retMap = new HashMap<>();
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		VisitorIn in = BeanUtility.jsonStrToObject(dataJson, VisitorIn.class, true);
		//参数校验开始
		if (StringUtils.isBlank(in.getMackAddress())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_MACADDRESS_IS_NOT_NULL);
		}
		if (StringUtils.isBlank(in.getIdCardNo())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_IDCARDNO_IS_NOT_NULL);
		}
		if (StringUtils.isBlank(in.getIcNo())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_ICNO_IS_NOT_NULL);
		}
		//查询设备对应的项目
		Project project = this.getProjectByMacAdress(in.getMackAddress());
		if (project == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_NOT_EXIST_PROJECT);
		}
		VisitorModel visitor = findVisitorByIdCard(project.getProjectCode(),in.getIdCardNo());
		if (visitor == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_VISITOR_NOT_EXIST, dateTime);
		}
		String oldIcNo = visitor.getIccard_code();//记录旧ic卡号，用于删除
		if (in.getIcNo().equals(oldIcNo)) {
			retMap.put("resultCode", "01");
			retMap.put("message", "该卡已被此用户绑定，无需重复绑定");
			return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStrLongToStr(retMap, false), IfConstant.SERVER_SUCCESS, dateTime);
		}
		VisitorApply va = this.visitorApplyMapper.findLongtermByVisitorId(visitor.getVisitor_id()+"");
		if (va == null) {
			retMap.put("resultCode", "02");
			retMap.put("message", "该访客不是长期访客");
			return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStrLongToStr(retMap, false), IfConstant.SERVER_SUCCESS, dateTime);
		}
		Account account = this.accountTransitMapper.hasIcCode(in.getIcNo());
		if (account != null) {
			retMap.put("resultCode", "03");
			retMap.put("message", "IC卡号已被其他常驻人员绑定，不能进行绑定");
			retMap.put("userName",account.getLoginName());
			retMap.put("accountCode", account.getAccountCode());
			return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStrLongToStr(retMap, false), IfConstant.SERVER_SUCCESS, dateTime);
		}
		//参数校验结束
		//判断ic卡是否以在
		VisitorModel vis = this.visitorApplyMapper.queryVisitorByIcProject(project.getProjectCode(), in.getIcNo());
		if (vis != null ) {
			retMap.put("resultCode", "03");
			retMap.put("message", "IC卡号已被其他长期访客绑定，不能进行绑定");
			retMap.put("userName",vis.getVisitor_name());
			retMap.put("accountCode", vis.getVisitor_code());
			return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStrLongToStr(retMap, false), IfConstant.SERVER_SUCCESS, dateTime);
//		 	int trannsitCode = HexUtil.hexToInt(vis.getTransit_code());
//			String bn = "10"+this.format8bitInt(trannsitCode, 8, "0");
//			List<TransitModel>  tl = regionalMapper.queryTransitByTyAcTcPi(AccountConstant.TransitTypeIc, bn,in.getIcNo(), project.getProjectId());
//			if (tl.size()>0) {
//				this.deleteTransitList(tl,in.getOperatorId());
//			}
//			this.visitorApplyMapper.updateVisitorIcNo("",in.getOperatorId(),vis.getVisitor_id());
		}
		List<TransitModel> decTlist = this.regionalMapper.queryTransitListByTyAcTcPi(null,AccountConstant.TransitTypeIc,null,in.getIcNo());
		if (decTlist != null  && decTlist.size()>0) {
			retMap.put("resultCode", "04");
			retMap.put("message", "您的账号有没完成绑定，请稍后再试");
			return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStrLongToStr(retMap, false), IfConstant.SERVER_SUCCESS, dateTime);
		}
		//修改ICNO
		int ret = this.visitorApplyMapper.updateVisitorIcNo(in.getIcNo(),in.getOperatorId(),visitor.getVisitor_id());
		visitor.setIccard_code(in.getIcNo());
		if (ret < 1 ) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_VISITOR_NOT_EXIST, dateTime);
		}
		//同步名单
		if (StringUtils.isNotBlank(va.getContents())) {
			String contents = va.getContents();
			List<Map<String,String>> rl = BeanUtility.jsonStrToObject(contents, List.class, false);
			List<Long> regList = new ArrayList<>();
			if (rl.size()>0 && rl !=null) {
				if (StringUtils.isNotBlank(oldIcNo)) {
				 	int trannsitCode = HexUtil.hexToInt(visitor.getTransit_code());
					String bn = "10"+this.format8bitInt(trannsitCode, 8, "0");
					List<TransitModel>  tl = regionalMapper.queryTransitByTyAcTcPi(AccountConstant.TransitTypeIc,bn,oldIcNo, project.getProjectId());
					if (tl.size()>0) {
						this.deleteTransitList(tl,in.getOperatorId());
					}
				}
				for (Map<String,String> map : rl) {
					regList.add(Long.parseLong(map.get("regionalId")));
				}
				List<Device> dl = this.visitorApplyMapper.getDeviceByRegionalId(regList);
				this.insertTransitList(dl,project,visitor,va,in.getOperatorId());
			}
		}
		retMap.put("resultCode", "00");
		retMap.put("message", "绑定成功");
		return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStrLongToStr(retMap, false), IfConstant.SERVER_SUCCESS, dateTime);

	}
	public String applyList(ControllerView view) throws AppException {
		Map<String,String> retMap =  new  HashMap<String,String>();
		String dataJson = view.getData();
		MiniAppletIn in = BeanUtility.jsonStrToObject(dataJson, MiniAppletIn.class, true);
		PagingBounds page = this.getPagingBounds(in.getPageNo()+"",in.getPageSize()+"");// 分页
		VisitorApplyIn pin = new VisitorApplyIn();
		pin.setProjectId(in.getProjectId());
		pin.setOperatorId(in.getOperatorId());
		List<VisitorRecord> applyList = this.inviteVisitorMapper.queryVaPageListByProjectId(pin, page);
		ReturnPageBean<VisitorRecord> ret = this.getPageResultBean(applyList, page);
		return WriteToPage.setResponseMessage(ret, IfConstant.SERVER_SUCCESS);
	}
	/**
	 * 整形不够位补数
	 * @param num 要补位数
	 * @param numLength 位数
	 * @param format补位字符
	 * @return
	 */
	public String format8bitInt(Integer num,Integer numLength,String format){
		StringBuffer n = new StringBuffer();
		int length = (num+"").length();
		for (long i = 1; i <= numLength - length; i++) {
			n.append(format);
		}
		n.append(num);
		return n.toString();
	}
	private void insertTransitList(List<Device> dl,Project project,VisitorModel v, VisitorApply va,String operatorId ) throws Exception {
		List<TransitModel> tl = new ArrayList<>();
		for (Device d : dl) {
			TransitModel t = new TransitModel();
		 	int trannsitCode = HexUtil.hexToInt(v.getTransit_code());
			String bn = this.format8bitInt(trannsitCode, 8, "0");
			t.setList_id(this.getIdWorker().nextId());
			t.setAccount_code(Integer.parseInt("10"+bn));//长期访客accountCode设置为10+transitCode
//			t.setEmp_id(acc.getEmp_id());
			t.setOrg_name(project.getProjectName());
			t.setProject_id(project.getProjectId());
			t.setDevice_code(d.getDeviceCode());
			t.setTransit_code(v.getIccard_code());//四种通行类型
			t.setTransit_type(AccountConstant.TransitTypeIc);
			if (va.getBeginDate() != null ) {
				t.setBegin_date(DateUtil.format(va.getBeginDate(), "yyyy-MM-dd"));
			}
			if (va.getBeginTime() != null ) {
				t.setBegin_time(DateUtil.format(va.getBeginTime(), "HH:mm:ss"));
			}
			if (va.getEndDate() != null ) {
				t.setEnd_date(DateUtil.format(va.getEndDate(), "yyyy-MM-dd"));
			}
			if (va.getEndTime() != null ) {
				t.setEnd_time(DateUtil.format(va.getEndTime(), "HH:mm:ss"));
			}
			t.setLimit_count(0);
			t.setProjectName(project.getProjectName());
			t.setEmp_name(v.getVisitor_name());
			t.setStatus_code(StatusType.TransitAdd);
			t.setStatus_time(DateUtil.getCurrentDate());
			t.setOperator_id(Long.parseLong(operatorId));
			t.setSynch_state("01");
			tl.add(t);
		}
		if(tl.size()>0){
			if(tl.size()>0){
				Set set = new HashSet(tl);
				tl.clear(); 
				tl = new ArrayList(set);    
				regionalMapper.createTransitList(tl);
			}
		}
		
	}
	/**
	 * 删除名单
	 * @param tl
	 * @param operatorId
	 * @throws Exception
	 */
	private void deleteTransitList(List<TransitModel> tl,String operatorId) throws Exception {
		for (TransitModel t : tl) {
			t.setList_id(getIdWorker().nextId());
			t.setBegin_date(DateUtil.getCurrentDate());
			t.setStatus_code(StatusType.TransitDelete);
			t.setStatus_time(DateUtil.getCurrentDate());
			t.setOperator_id(Long.parseLong(operatorId));
			t.setSynch_state("01");
		}
		if(tl.size()>0) {
			Set set = new HashSet(tl);
			tl.clear();
			tl = new ArrayList(set);
			this.regionalMapper.createTransitList(tl);
		}
	}
	/**
	 * 通过二维码设备名单同步名单
	 * @param tl
	 * @param operatorId
	 * @throws Exception 
	 */
	private void insertTransitListByQrTarnsitList(List<TransitModel> tl,
			String operatorId,String icNo,String projectName,String accountName) throws Exception {
//		List<TransitModel> synclist = this.regionalMapper.querySyncTransitByTyAcTcPi(tl.get(0).getAccount_code(),AccountConstant.TransitTypeQr, tl.get(0).getProject_id());
		for (TransitModel t : tl) {
			t.setList_id(getIdWorker().nextId());
			t.setTransit_code(icNo);
			t.setTransit_type(AccountConstant.TransitTypeIc);
//			t.setEmp_name(accountName);
			t.setProjectName(projectName);
			t.setOrg_name(projectName);
//			t.setBegin_date(DateUtil.getCurrentDate());
			t.setStatus_code(StatusType.TransitAdd);
			t.setStatus_time(DateUtil.getCurrentDate());
			t.setOperator_id(Long.parseLong(operatorId));
			t.setSynch_state("01");
		}
		if(tl.size()>0){
			if(tl.size()>0){
				Set set = new HashSet(tl);
				tl.clear(); 
				tl = new ArrayList(set);    
				regionalMapper.createTransitList(tl);
			}
		}
	}

	private String encryptName(String name){
		if(StringUtils.isBlank(name)){
			return "**";
		}
		String retName = name.substring(0,1);
		String stars = "";
		for (int i = 1; i < name.length(); i++) {
			stars +="*";
		}
		return retName+stars;
	}

	
	/**
	 * 获得当前时间到当天晚上的秒差
	 * 
	 * @return
	 * @throws ParseException
	 */
	private int getCurrentDayCompSeconds(String toDateTime) throws ParseException {
		if(StringUtils.isBlank(toDateTime)) {
			toDateTime = DateUtil.getCurrentDate() + " 23:59:59";
		}
		long from = new Date().getTime();
		long to = simpleFormat.parse(toDateTime).getTime();
		return (int) ((to - from) / 1000);
	}
	
}




