package com.bossbutler.service.wx;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.common.InitConf;
import com.bossbutler.common.constant.RegionalConstant;
import com.bossbutler.mapper.myregional.DeviceMapper;
import com.bossbutler.mapper.myregional.MyRegionalMapper;
import com.bossbutler.mapper.project.ProjectMapper;
import com.bossbutler.mapper.system.AccountMapper;
import com.bossbutler.mapper.system.EmpMapper;
import com.bossbutler.mapper.visitor.InviteVisitorMapper;
import com.bossbutler.mapper.visitor.VisitorApplyMapper;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.MyArrangePojo;
import com.bossbutler.pojo.MyvisitorPojo;
import com.bossbutler.pojo.RegionalPojo;
import com.bossbutler.pojo.VisitorInfoPojo;
import com.bossbutler.pojo.org.Org;
import com.bossbutler.pojo.project.Project;
import com.bossbutler.pojo.regional.Device;
import com.bossbutler.pojo.regional.VisitorModel;
import com.bossbutler.pojo.system.Account;
import com.bossbutler.pojo.system.EmpRelations;
import com.bossbutler.pojo.visitor.VisitorApply;
import com.bossbutler.pojo.visitor.VisitorApplyContents;
import com.bossbutler.pojo.visitor.VisitorApplyIn;
import com.bossbutler.pojo.visitor.VisitorIn;
import com.bossbutler.pojo.wx.MiniAppletIn;
import com.bossbutler.pojo.wx.MiniVisitorApplyInfo;
import com.bossbutler.service.BasicService;
import com.bossbutler.service.passthrough.PassFilterThroughService;
import com.bossbutler.service.visitor.VisitorService;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.WriteToPage;
import com.common.cnnetty.gateway.util.HexUtil;
import com.company.exception.AppException;
import com.company.util.BeanUtility;
import com.company.util.CommonUtil;
import com.company.util.DateUtil;

@Service
public class MiniAppletService extends BasicService {

	@Autowired
	private VisitorApplyMapper visitorApplyMapper;

	@Autowired
	private InviteVisitorMapper inviteVisitorMapper;

	@Autowired
	private DeviceMapper deviceMapper;

	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Autowired
	private EmpMapper empMapper;
	
	@Autowired
	private VisitorService visitorService;
	
	@Autowired
	private PassFilterThroughService passFilterThroughService;
	
	@Autowired
	private  MyRegionalMapper regionalMapper;
	
	public String saveVisitor(ControllerView view, Logger logger) throws Exception{
		String dataJson = view.getData();
		MiniAppletIn in = BeanUtility.jsonStrToObject(dataJson, MiniAppletIn.class, true);
		String dateTime = String.valueOf(new Date().getTime());
	
		//查询改项目下是否已存在该访客
//		VisitorIn vin = new VisitorIn();
//		vin.setPhone(in.getMobilePhone());
//		vin.setIdCardNo(in.getVisitorIdNo());
//		vin.setVisitorType("03");//项目访客 
//		vin.setProjectCode(project.getProjectCode());
//		List<VisitorModel> vlist = inviteVisitorMapper.queryVistorByParams(vin );
		if(StringUtils.isNotBlank(in.getProjectId()) && !"809760378929418240".equals(in.getProjectId())){//如果访客扫码过来
			MyvisitorPojo visitor = inviteVisitorMapper.findById(in.getProjectId()+"");//projectId 暂时用做visitorId
			if (visitor != null) {
				inviteVisitorMapper.updateMobilephone(in.getMobilePhone(),Long.parseLong(visitor.getVisitorId()));
				return WriteToPage.setResponseMessage(visitor.getPhone(), IfConstant.SERVER_SUCCESS, dateTime);
			}else{
				return WriteToPage.setResponseMessageForError("该手机号没有访问信息", "99999999");
			}
		}
		VisitorIn vin = new VisitorIn();
		vin.setPhone(in.getMobilePhone());
		//vin.setVisitorType("03");//项目访客 
		List<VisitorModel> vlist = inviteVisitorMapper.queryVistorByParams(vin );
		if (vlist != null && vlist.size()>0) {
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
		}else{
			return WriteToPage.setResponseMessageForError("该手机号没有访问信息", "99999999");
		}
/**
		VisitorModel visitor = new VisitorModel();
		visitor.setAccount_id(null);
		visitor.setVisitor_type("03");
		visitor.setVisitor_id(getIdWorker().nextId());
		visitor.setTransit_code(HexUtil.intToHex(Integer.parseInt(GenerateCreater.getTransitCode()), 4));
		visitor.setVisitor_name(in.getVisitorName());
		visitor.setMobilephone(in.getMobilePhone());
		visitor.setProject_code(project.getProjectCode());
		visitor.setIdcard(in.getVisitorIdNo());
		visitor.setStatus_code("01");;
		visitorApplyMapper.createVisitor(visitor);
*/		
		
	}

	public String projectList(ControllerView view, Logger logger) throws Exception {
		String dataJson = view.getData();
		MiniAppletIn in = BeanUtility.jsonStrToObject(dataJson, MiniAppletIn.class, true);
		//Project project = this.projectMapper.findByPrimaryKey(in.getProjectId()+"");
		//查询个人下是那些项目的访客
		VisitorIn vin = new VisitorIn();
		vin.setPhone(in.getMobilePhone());
		vin.setIdCardNo(in.getVisitorIdNo());
		//vin.setProjectCode(project.getProjectCode());
		List<Project> plist = projectMapper.queryProjectByVisitor(vin);
		if(plist != null && plist.size()>0){
			for (Project p : plist) {
				/*//判断是否是企业访客
				VisitorApplyIn vai = new VisitorApplyIn();
				//vai.setProjectId(in.getProjectId()+"");
				
				vai.setVisitorId(p.getVisitorId());
				vai.setSourceType("01");
				//vai.setAccountId(in.getAccountId());
				//vai.setOrgId(in.getOrgId());
				List<VisitorApply> applyList = this.inviteVisitorMapper.queryVistorApplyByParams(vai);
				if(applyList != null && applyList.size()>0){
					String dateformatType=applyList.get(0).getDateformatType();
					vai.setVisitorTime(DateUtil.format(new Date(), DateUtil.PATTERN_DATE));
					if(dateformatType.equals("02")){
						vai.setVisitorTime(DateUtil.format(new Date(), DateUtil.PATTERN_STANDARD));
					}
					vai.setvTime(DateUtil.format(new Date(), "HH:mm:ss"));
					vai.setDateformatType(applyList.get(0).getDateformatType());
					// 有效的长期访客
					List<VisitorApply> applyLongList = this.inviteVisitorMapper.queryVistorApplyByParams(vai);
					if(applyLongList != null && applyLongList.size()>0){
							p.setPassProfile("0");
							continue;
					}
					
				}*/
				if(StringUtils.isBlank(p.getPassProfile())){
					p.setPassProfile("0");
				}else{
					Map profile	= BeanUtility.jsonStrToObject(p.getPassProfile(), Map.class, false);
					p.setPassProfile(profile.get("protectLevel").toString());
				}
			}
		}
		return WriteToPage.setResponseMessage(plist, IfConstant.SERVER_SUCCESS);
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
				String des = "";
				String resourcName = this.projectMapper.getResourceNameByOrgIds(ee.getOrgId());
				if (StringUtils.isNotBlank(resourcName)) {
					des += resourcName;
				}
				ee.setProjectName(des);
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
	public String createVisitorApply(ControllerView view, Logger logger) throws Exception {
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
		MyvisitorPojo visitor = inviteVisitorMapper.findById(in.getVisitorId());
		if(visitor==null ){
			return WriteToPage.setResponseMessage("访客不存在", IfConstant.CUSTOM);
		}
		
		if(visitor.getStatusCode().equals("02")){
			return WriteToPage.setResponseMessage("通行权限已过期，请重新认证", IfConstant.CUSTOM);
		}
		
		//如果有邀请记录直接返回邀请码
		VisitorApplyIn vin = new VisitorApplyIn();
		vin.setProjectId(in.getProjectId()+"");
		vin.setBeginDate(beginDate);
		vin.setEndDate(endDate);
		vin.setBeginTime(beginTime);
		vin.setEndTime(endTime);
		vin.setVisitorId(in.getVisitorId());
		vin.setAccountId(in.getAccountId());
		vin.setOrgId(in.getOrgId());
		List<VisitorApply> applyList = this.inviteVisitorMapper.queryVistorApplyByParams(vin);
		if (applyList.size()>0 && applyList!= null) {
			VisitorApply va=applyList.get(0);
			VisitorApply vaModel=new VisitorApply();
			//设置通行策略
			String trafficStrategy=visitorService.updateTrafficStrategy(va);
			if(StringUtils.isNotBlank(trafficStrategy)){
				vaModel.setRelationId(va.getRelationId());
				vaModel.setTrafficStrategy(trafficStrategy);
				
			}
			vaModel.setCreateTime(new Date());
			visitorApplyMapper.updateDynamic(vaModel);
			//判断是否超出顶级节点并计数，清理计数
			if(StringUtils.isNotBlank(va.getTransitDevices())){
				String transitDevices[]=va.getTransitDevices().split(",");
				Device d=new Device();
				List<String> deviceCodeList=new ArrayList<String>();
				List<String> serialNumbers=new ArrayList<String>();
				for(int i=0;i<transitDevices.length;i++){
					if(StringUtils.isNotBlank(transitDevices[i])){
						String serialNumber=transitDevices[i].substring(2,transitDevices[i].length());
						if(serialNumber.length()==10){
							serialNumbers.add(serialNumber);
						}
						
					}
				}
				if(serialNumbers.size()>0){
					d.setSerialNumbers(serialNumbers);
					List<Device> dList=deviceMapper.queryDynamic(d);
					for(Device dModel:dList){
						deviceCodeList.add(dModel.getDeviceCode());
					}
				}
				if(deviceCodeList.size()>0){
					passFilterThroughService.judgeFilterVisitorPowerClearCache(in.getProjectId()+"","00",visitor.getTransitCode(),deviceCodeList.toArray(new String[0]));
				}
			}
			//end
			
			return WriteToPage.setResponseMessage(applyList.get(0).getVisitorCode(), IfConstant.SERVER_SUCCESS);
		}
		
		Project project = this.projectMapper.findByPrimaryKey(in.getProjectId()+"");
		VisitorInfoPojo apply = new VisitorInfoPojo();
		List<Device> deviceList = new ArrayList<>();
		if(StringUtils.isNotBlank(in.getAccountId())){
			//保存邀请记录 TODO:
			String arrangeIds = this.deviceMapper.queryArrangeByAccountId(Long.parseLong(in.getAccountId()));
			deviceList = deviceMapper.queryByEntranceTypeProjectId(in.getProjectId()+"", RegionalConstant.ArrangeType01,
					arrangeIds,in.getAccountId() + "");
			apply.setAccountId(Long.parseLong(in.getAccountId()));
		}else if(StringUtils.isNotBlank(in.getOrgId())){
			String arrangeIds = this.deviceMapper.queryArrangeByOrgId(in.getOrgId());
			deviceList = deviceMapper.queryByEntranceTypeProjectIdNotAccountId(in.getProjectId()+"", RegionalConstant.ArrangeType01,arrangeIds,in.getOrgId());
		}else{
			deviceList = this.deviceMapper.queryByProjectIdOrOrgId(in.getProjectId(), in.getOrgId());
			if (deviceList.size() == 0) {
				deviceList = deviceMapper.queryByEntranceTypeProjectIdNotAccountId(in.getProjectId()+"", RegionalConstant.ArrangeType01,null,null);
			}
		}
		apply.setOrgId(in.getOrgId());
		apply.setRelationid(getIdWorker().nextId());
		apply.setRegionalId(in.getProjectId()+"");
		apply.setFollows(1);
		apply.setVisitorid(Long.parseLong(in.getVisitorId()));
		apply.setTransitCode(visitor.getTransitCode());
		apply.setTime(DateUtil.format(new Date(), DateUtil.PATTERN_DATE));
		apply.setBegindate(beginDate);
		apply.setEnddate(endDate);
		apply.setBeginTime(beginTime);
		apply.setEndTime(endTime);
		
		VisitorApplyContents vac = new VisitorApplyContents();
		vac.setIdCode(visitor.getTransitCode());
		vac.setDevicePre(project.getProjectCode());
		List<String> transitDevices = new ArrayList<>();
		String openAccess = this.createOpenAccess(deviceList, transitDevices,apply);
		vac.setOpenAccess(openAccess);
		vac.setSynchCode("00000000");//同步码
		String address = project.getAddress();
		vac.setAddress(address);
		apply.setContents(BeanUtility.objectToJsonStr(vac, false));
		apply.setVisitorCode(CommonUtil.generateShortUuid());
		apply.setContents(BeanUtility.objectToJsonStr(vac, false));
		apply.setVisitorCode(CommonUtil.generateShortUuid());
		apply.setSourceType("05");
		apply.setTransitDevices(StringUtils.join(transitDevices, ","));
		
		//通行策略
		VisitorApply va=new VisitorApply();
		va.setProjectId(new Long(in.getProjectId()));
		va.setSourceType("05");
		String trafficStrategy=visitorService.updateTrafficStrategy(va);
		if(StringUtils.isNotBlank(trafficStrategy)){
			apply.setTrafficStrategy(trafficStrategy);
		}
		//end
		
		inviteVisitorMapper.insertVisitorApply(apply);
		
		//判断是否超出顶级节点并计数，清理计数
		if(transitDevices.size()>0){
			Device d=new Device();
			List<String> deviceCodeList=new ArrayList<String>();
			List<String> serialNumbers=new ArrayList<String>();
			for(String td:transitDevices){
				if(StringUtils.isNotBlank(td)){
					String serialNumber=td.substring(2,td.length());
					if(serialNumber.length()==10){
						serialNumbers.add(serialNumber);
					}
				}
			}
			if(serialNumbers.size()>0){
				d.setSerialNumbers(serialNumbers);
				List<Device> dList=deviceMapper.queryDynamic(d);
				for(Device dModel:dList){
					deviceCodeList.add(dModel.getDeviceCode());
				}
			}
			if(deviceCodeList.size()>0){
				passFilterThroughService.judgeFilterVisitorPowerClearCache(in.getProjectId()+"","00",visitor.getTransitCode(),deviceCodeList.toArray(new String[0]));
			}
		}//end
		return WriteToPage.setResponseMessage(apply.getVisitorCode(), IfConstant.SERVER_SUCCESS);
	}

	public String getAccountInfo(ControllerView view, Logger logger) throws AppException {
		String dataJson = view.getData();
		MiniAppletIn in = BeanUtility.jsonStrToObject(dataJson, MiniAppletIn.class, true);
		Account account = this.accountMapper.queryUserByPhone(in.getMobilePhone());
		if (account != null) {
			List<EmpRelations> emp = this.queryEmpByAccProPer(account.getAccountId(), in.getProjectId()+"",null,in.getOrgId());
			if (emp.size()>0 && emp!=null) {
				if (StringUtils.isNotBlank(in.getOrgId())  ) {
					if(!(in.getOrgId().equals(emp.get(0).getOrgId()+""))){
						return WriteToPage.setResponseMessageForError("被访人不存在（组织）", "99999999");
					}
				}
				return WriteToPage.setResponseMessage(account.getAccountId(), IfConstant.SERVER_SUCCESS);
			}
			return WriteToPage.setResponseMessageForError("被访人不存在（项目）", "99999999");
		}
		return WriteToPage.setResponseMessageForError("被访人不存在（平台）", "99999999");
	}
	public String getVisitorInfo(ControllerView view, Logger logger) throws AppException {
		String dataJson = view.getData();
		MiniAppletIn in = BeanUtility.jsonStrToObject(dataJson, MiniAppletIn.class, true);
		MyvisitorPojo visitor = this.inviteVisitorMapper.findById(in.getVisitorId());
		if("03".equals(visitor.getVisitorType())){
			//如果 visitorType = 01 直接返回邀请码
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
			//如果有邀请记录直接返回邀请码
			VisitorApplyIn vin = new VisitorApplyIn();
//			vin.setProjectId(visitor.getProjectId()+"");
			vin.setBeginDate(beginDate);
			vin.setEndDate(endDate);
			vin.setBeginTime(beginTime);
			vin.setEndTime(endTime);
			vin.setSourceType("03");
			vin.setStatusCode("01");
			/*vin.setVisitorTime(DateUtil.format(new Date(), DateUtil.PATTERN_DATE));*/
			vin.setVisitorId(in.getVisitorId());
			List<VisitorApply> applyList = this.inviteVisitorMapper.queryVistorApplyByParams(vin);
			if (applyList.size()>0 && applyList!= null) {
				VisitorApply va=applyList.get(0);
				visitor.setVisitorCode(va.getVisitorCode());
			}else{
				return WriteToPage.setResponseMessage("无通行权限", IfConstant.CUSTOM);
			}
		}
		return WriteToPage.setResponseMessage(visitor, IfConstant.SERVER_SUCCESS);
	}
	public String getVisitorApply(ControllerView view, Logger logger) throws AppException {
		String dataJson = view.getData();
		MiniAppletIn in = BeanUtility.jsonStrToObject(dataJson, MiniAppletIn.class, true);
		Map<String,String> retMap =  new HashMap<String, String>();
		MiniVisitorApplyInfo info = this.visitorApplyMapper.getVisitorApply(in.getVisitorCode());
		String top = "";
		if (info != null) {
			retMap.put("qrCodeURL", InitConf.getSelfvisitorapplypath()+"create/");
			if(StringUtils.isNotBlank(info.getProjectName())){
				retMap.put("projectDesc", info.getProjectName());
				retMap.put("curDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
			}
			if(StringUtils.isNotBlank(info.getOrgName())){
				String orgInfo = info.getOrgName();
				String resurceName = this.projectMapper.getResourceNameByOrgIds(info.getOrgId());
				if(StringUtils.isNotBlank(resurceName)){
					retMap.put("resurceName", resurceName);
				}
				retMap.put("orgDesc", orgInfo);
			}
			if(StringUtils.isNotBlank(info.getAccountId()) && !"0".equals(info.getAccountId())){
				List<EmpRelations> el = this.queryEmpByAccProPer(info.getAccountId(), info.getProjectId(), null,info.getOrgId());
				if (el != null && el.size() > 0) {
					retMap.put("personDesc", el.get(0).getEmpName());
					retMap.put("mobilephone", el.get(0).getMobilephone());
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
			if(StringUtils.isNotBlank(info.getOrgProfile())){
				Map<String,String> orgMap = BeanUtility.jsonStrToObject(info.getOrgProfile(), Map.class, false);
				String orgRemark = orgMap.get("remark") == null ? "" :orgMap.get("remark").toString();
				if (StringUtils.isNotBlank(orgRemark)) {
					retMap.put("orgRemark", orgRemark);
				}
			}
		}
		return WriteToPage.setResponseMessage(retMap, IfConstant.SERVER_SUCCESS);
	}
	/**
	 * 方法描述 :邀请访客生成二维设备权限
	 * 创建人 :  hzhang
	 * 创建时间: 2016年9月2日 下午11:15:28
	 * @param deviceList
	 * @return 权限值
	 * @throws ParseException 
	 */
	private String createOpenAccess(List<Device> deviceList, List<String> transitDevices,VisitorInfoPojo pojo) throws ParseException {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat dfs = new SimpleDateFormat("HH:mm:ss");
		long between=(dfs.parse(pojo.getEndTime()).getTime()-dfs.parse(pojo.getBeginTime()).getTime())/1000/60;//
		int index=(int) between;
		
		for (Device d : deviceList) {
			if(d.getControllerType().equals("01")){
				String validTime = HexUtil.intToHex(index/10, 1) ;
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
	 * 方法描述:验证小程序
	 * 创建人: llc
	 * 创建时间: 2018年12月29日 下午2:28:52
	 * @param view
	 * @param logger
	 * @return
	 * @throws AppException String 
	*/
	public String checkVisitor(ControllerView view, Logger logger) throws AppException {
		String dataJson = view.getData();
		MiniAppletIn in = BeanUtility.jsonStrToObject(dataJson, MiniAppletIn.class, true);
		if(StringUtils.isBlank(in.getVisitorId()) || StringUtils.isBlank(in.getCheckCode())){
			return WriteToPage.setResponseMessage("未认证用户", IfConstant.CUSTOM);
		}
		MyvisitorPojo visitor = this.inviteVisitorMapper.findByPrimary(in.getVisitorId());
		if(visitor ==null){
			return WriteToPage.setResponseMessage("访客不存在", IfConstant.CUSTOM);
		}
		if(!"01".equals(visitor.getStatusCode())){
			return WriteToPage.setResponseMessage("访客状态异常", IfConstant.CUSTOM);
		}
		String idcard=visitor.getIdcard();
		if(visitor.getProjectCode() != null){
			Project project = this.projectMapper.findByProjectCode(visitor.getProjectCode());
			visitor.setProjectId(project.getProjectId()+"");
		}
		if(StringUtils.isNotBlank(idcard)){
			if(idcard.length()>=4){
				idcard=idcard.substring(idcard.length()-4,idcard.length());
				if(idcard.equals(in.getCheckCode())){
					int checkCnt;
					if(StringUtils.isBlank(visitor.getCheckCnt())){
						checkCnt=1;
					}else{
						checkCnt=new Integer(visitor.getCheckCnt())+1;
					}
					VisitorIn vi=new VisitorIn();
					vi.setVisitorId(in.getVisitorId());
					vi.setCheckCnt(checkCnt+"");
					inviteVisitorMapper.updateDynamic(vi);
					if("03".equals(visitor.getVisitorType())){
						//如果 visitorType = 01 直接返回邀请码
						String beginDate="";
						String endDate="";
						String beginTime="";
						String endTime="";
						
//						if(StringUtils.isNotBlank(in.getBeginTime())){
//							beginTime=in.getBeginTime();
//						}else{
//							beginTime="00:00:00";
//						}
//						if(StringUtils.isNotBlank(in.getEndTime())){
//							endTime=in.getEndTime();
//						}else{
//							endTime="23:59:59";
//						}
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
						//如果有邀请记录直接返回邀请码
						VisitorApplyIn vin = new VisitorApplyIn();
						vin.setProjectId(visitor.getProjectId()+"");
						vin.setBeginDate(beginDate);
						vin.setEndDate(endDate);
						vin.setBeginTime(beginTime);
						vin.setEndTime(endTime);
						vin.setSourceType("03");
						vin.setStatusCode("01");
						/*vin.setVisitorTime(DateUtil.format(new Date(), DateUtil.PATTERN_DATE));*/
						vin.setVisitorId(in.getVisitorId());
						List<VisitorApply> applyList = this.inviteVisitorMapper.queryVistorApplyByParams(vin);
						if (applyList.size()>0 && applyList!= null) {
							VisitorApply va=applyList.get(0);
							visitor.setVisitorCode(va.getVisitorCode());
						}else{
							return WriteToPage.setResponseMessage("无通行权限", IfConstant.CUSTOM);
						}
					}
					return WriteToPage.setResponseMessage(visitor, IfConstant.SERVER_SUCCESS);
				}else{
					return WriteToPage.setResponseMessage("验证不匹配", IfConstant.CUSTOM);
				}
			}
		}
		
		String phone=visitor.getPhone();
		if(StringUtils.isBlank(phone) || phone.length()<4){
			return WriteToPage.setResponseMessage("验证失败", IfConstant.CUSTOM);
		}
		phone=phone.substring(phone.length()-4,phone.length());
		if(phone.equals(in.getCheckCode())){
			int checkCnt;
			if(StringUtils.isBlank(visitor.getCheckCnt())){
				checkCnt=1;
			}else{
				checkCnt=new Integer(visitor.getCheckCnt())+1;
			}
			VisitorIn vi=new VisitorIn();
			vi.setVisitorId(in.getVisitorId());
			vi.setCheckCnt(checkCnt+"");
			inviteVisitorMapper.updateDynamic(vi);
			return WriteToPage.setResponseMessage(null, IfConstant.SERVER_SUCCESS);
		}else{
			return WriteToPage.setResponseMessage("验证失败", IfConstant.CUSTOM);
		}
	}
	
	
	

	/**
	 * 方法描述:验证小程序
	 * 创建人: llc
	 * 创建时间: 2018年12月29日 下午2:28:52
	 * @param view
	 * @param logger
	 * @return
	 * @throws AppException String 
	*/
	public String getVisitorCheck(ControllerView view, Logger logger) throws AppException {
		String dataJson = view.getData();
		MiniAppletIn in = BeanUtility.jsonStrToObject(dataJson, MiniAppletIn.class, true);
		if(StringUtils.isBlank(in.getVisitorId()) ){
			return WriteToPage.setResponseMessage("参数缺失", IfConstant.CUSTOM);
		}
		MyvisitorPojo visitor = this.inviteVisitorMapper.findById(in.getVisitorId());
		if(visitor ==null){
			return WriteToPage.setResponseMessage("访客不存在，或者被禁用",IfConstant.CUSTOM);
		}
		String checkCnt =visitor.getCheckCnt();
		if(StringUtils.isNotBlank(checkCnt) && new Integer(checkCnt)>0 ){
			return WriteToPage.setResponseMessage("1", IfConstant.SERVER_SUCCESS);
		}
		return WriteToPage.setResponseMessage("0", IfConstant.CUSTOM);
	}
	/**
	 * 获取同行区域接口
	 * @param view
	 * @param logger
	 * @return
	 * @throws AppException 
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

	public String createApplyByArrId(ControllerView view, Logger logger) throws Exception {
		String sourceType = "01";
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
		
		if(in.getProjectId() ==null){
			return WriteToPage.setResponseMessage("参数有误", IfConstant.CUSTOM);
		}  
		
		MyvisitorPojo visitor = inviteVisitorMapper.findById(in.getVisitorId());
		if(visitor==null){
			return WriteToPage.setResponseMessage("访客不存在", IfConstant.CUSTOM);
		}
		String arrangeIds=in.getArrangeId();
		if(arrangeIds.split(",").length >5){
            return WriteToPage.setResponseMessage("选择通行区域不能超过5个", IfConstant.CUSTOM);
        }
		
		VisitorApply va=new VisitorApply();
		va.setStatusCode("02");
		VisitorApply vaQuery=new VisitorApply();
		vaQuery.setVisitorId(new Long(in.getVisitorId()));
		vaQuery.setStatusCode("01");
		vaQuery.setSourceType("01");
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
		if(StringUtils.isNotBlank(arrangeIds)){//
			RegionalPojo rp=new RegionalPojo();
			rp.setProjectId(in.getProjectId());
			rp.setStatusCode("01");
			rp.setRegionalTypes("04");
			List<RegionalPojo> rpList=regionalMapper.queryRegional(rp);
			
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
				for(MyArrangePojo ap:arrangeList){
					if(ap.getEntryRoot().equals("1")){
						arrangeIds=arrangeIds+","+ap.getArrangeId();
					}
				}
			}
			
			
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
		VisitorApply va1=new VisitorApply();
		va1.setProjectId(new Long(in.getProjectId()));
		va1.setSourceType(sourceType);
		String trafficStrategy=visitorService.updateTrafficStrategy(va1);
				
		
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
		apply.setSourceType(sourceType);
		apply.setApplyType(in.getApplyType());
		
		//end

		apply.setTransitDevices(StringUtils.join(transitDevices, ","));
		inviteVisitorMapper.insertVisitorApply(apply);
		in.setVisitorCode(apply.getVisitorCode());
		passFilterThroughService.visitorApplyCache(apply.getRelationid()+"");
		
		in.setVisitorCode(apply.getVisitorCode());
//		return WriteToPage.setResponseMessage(getVisitorApply(in), IfConstant.SERVER_SUCCESS);
		return WriteToPage.setResponseMessage(in.getVisitorCode(), IfConstant.SERVER_SUCCESS);
	}
	


}
