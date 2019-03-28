package com.bossbutler.service.visitor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.common.InitConf;
import com.bossbutler.common.SpringContextHolder;
import com.bossbutler.common.constant.RegionalConstant;
import com.bossbutler.common.constant.VisitorConstant;
import com.bossbutler.mapper.RAppProjectMapper;
import com.bossbutler.mapper.myregional.Arrange;
import com.bossbutler.mapper.myregional.ArrangeMapper;
import com.bossbutler.mapper.myregional.DeviceMapper;
import com.bossbutler.mapper.myregional.MyRegionalMapper;
import com.bossbutler.mapper.project.ProjectMapper;
import com.bossbutler.mapper.visitor.InviteVisitorMapper;
import com.bossbutler.mapper.visitor.UniqueCacheMapper;
import com.bossbutler.mapper.visitor.VisitorApplyMapper;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.MobileMsgTemplate;
import com.bossbutler.pojo.MyvisitorPojo;
import com.bossbutler.pojo.PagingBounds;
import com.bossbutler.pojo.PropertyRequestPojo;
import com.bossbutler.pojo.RegionalPojo;
import com.bossbutler.pojo.VisitorInfoPojo;
import com.bossbutler.pojo.VisitorPojo;
import com.bossbutler.pojo.org.OrgProFileModel;
import com.bossbutler.pojo.project.Project;
import com.bossbutler.pojo.regional.Device;
import com.bossbutler.pojo.regional.VisitorModel;
import com.bossbutler.pojo.resource.ResourceModel;
import com.bossbutler.pojo.system.EmpRelations;
import com.bossbutler.pojo.visitor.UniqueCache;
import com.bossbutler.pojo.visitor.VisitorApply;
import com.bossbutler.pojo.visitor.VisitorApplyContents;
import com.bossbutler.pojo.visitor.VisitorApplyIn;
import com.bossbutler.pojo.visitor.VisitorApplyOut;
import com.bossbutler.pojo.visitor.VisitorIn;
import com.bossbutler.service.BasicService;
import com.bossbutler.service.ConfigureService;
import com.bossbutler.service.passthrough.PassFilterThroughService;
import com.bossbutler.util.GenerateCreater;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.MacConfirm;
import com.bossbutler.util.ReturnPageBean;
import com.bossbutler.util.WriteToPage;
import com.common.cnnetty.gateway.util.HexUtil;
import com.company.exception.AppException;
import com.company.mobile.model.AppCodeNameEnum;
import com.company.util.BeanUtility;
import com.company.util.CommonUtil;
import com.company.util.DateUtil;
import com.bossbutler.mapper.org.OrgProFileMapper;

@Service
public class InviteVisitorService extends BasicService {

	@Autowired
	private UniqueCacheMapper uniqueCacheMapper;

	@Autowired
	private InviteVisitorMapper inmapper;

	@Autowired
	private DeviceMapper deviceMapper;

	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private MyRegionalMapper myRegionalMapper;
	
	@Autowired
	private RAppProjectMapper rAppProjectMapper;
	
	@Autowired
	private PassFilterThroughService passFilterThroughService;
	
	@Autowired
	private VisitorService visitorService;
	
	@Autowired
	private VisitorApplyMapper visitorApplyMapper;
	
	@Autowired
	private ArrangeMapper arrangeMapper;
	
	@Autowired
	private OrgProFileMapper orgProFileMapper;
	
	
	public String querylist(ControllerView view, Logger logger) throws Exception {
		List<VisitorPojo> list = new ArrayList<VisitorPojo>();
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 请求的签名
		String mac = view.getMac();
		// 实体内容转为对象
		PropertyRequestPojo pojo = BeanUtility.jsonStrToObject(dataJson, PropertyRequestPojo.class, true);
		// 验证签名
		boolean checkMac = MacConfirm.checkMac(mac, dataJson, view.getDateTime());
		String dateTime = String.valueOf(new Date().getTime());
		if (!checkMac) {
			logger.warn("querylist参数mac验证不正确");
			return WriteToPage.setResponseMessage("[]", IfConstant.UNKNOWN_ERROR, dateTime);
		}
		list = inmapper.queryvisitorlist(pojo.getAccountId());
		// 生成返回实体json
		if (list == null)
			return WriteToPage.setResponseMessage("[]", IfConstant.SERVER_SUCCESS, dateTime);
		String resultData = BeanUtility.objectToJsonStr(list, false);
		// 发送成功
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	/**
	 * 邀请访客的添加
	 * @param view
	 * @param logger
	 * @return
	 * @throws Exception
	 */
	public String visitoradd(ControllerView view, Logger logger) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		String date=CommonUtil.dateToStr(new Date(), DateUtil.PATTERN_DATE);
		// 实体内容转为对象
		VisitorInfoPojo pojo = BeanUtility.jsonStrToObject(dataJson, VisitorInfoPojo.class, false);
		
        
        
        
		if (StringUtils.isBlank(pojo.getRegionalId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		if (StringUtils.isBlank(pojo.getName())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_VISITOR_NAME_NULL, dateTime);
		}
		if (StringUtils.isBlank(pojo.getPhone())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_VISITOR_PHONE_NULL, dateTime);
		}
		if (StringUtils.isBlank(pojo.getTime())) {
			return WriteToPage.setResponseMessage("日期不能为空", IfConstant.CUSTOM, dateTime);
		}
		int index=DateUtil.compareDateStr(pojo.getTime(), date, DateUtil.PATTERN_DATE);
		
		if (index<0) {
			return WriteToPage.setResponseMessage("日期小于当前时间", IfConstant.CUSTOM, dateTime);
		}
		long days=(CommonUtil.strToDate(pojo.getTime(), DateUtil.PATTERN_DATE).getTime()- CommonUtil.strToDate(date,DateUtil.PATTERN_DATE).getTime())/1000/3600/24+1;
		
		String orgId=pojo.getOrgId();
		String EndDate="0";
		String DateSpan="7";
		
		
		Map<String,String> params =new HashMap<String,String>();
		params.put("type","70405");
		params.put("projectId",pojo.getRegionalId()+"");
		String projectProfile=projectMapper.getProjectProfileByParam(params);
		
		if(StringUtils.isNotBlank(projectProfile)){
	    	Map<String,String> map=BeanUtility.jsonStrToObject(projectProfile,new HashMap().getClass(),true);
	    	if(StringUtils.isNotBlank(map.get("EndDate")) && StringUtils.isNumeric(map.get("EndDate"))
	    			&& (map.get("EndDate").equals("0") || map.get("EndDate").equals("1"))){
	    		EndDate=map.get("EndDate");
	    	}
	    	if(StringUtils.isNotBlank(map.get("DateSpan")) && StringUtils.isNumeric(map.get("DateSpan"))){
	    		DateSpan=map.get("DateSpan");
	    	}
	    }	
		
		
		OrgProFileModel opm=new OrgProFileModel();
		opm.setType("70110");
		opm.setOrgId(CommonUtil.string2Long(orgId));
		List<OrgProFileModel> opmList=orgProFileMapper.getOrgProfile(opm);
		if(opmList !=null && opmList.size()>0){
		    OrgProFileModel opModel=opmList.get(0);
		    if(StringUtils.isNotBlank(opModel.getProfile())){
		    	Map<String,String> map=BeanUtility.jsonStrToObject(opModel.getProfile(),new HashMap().getClass(),true);
		    	if(StringUtils.isNotBlank(map.get("EndDate")) && StringUtils.isNumeric(map.get("EndDate"))
		    			&& (map.get("EndDate").equals("0") || map.get("EndDate").equals("1"))){
		    		EndDate=map.get("EndDate");
		    	}
		    	if(StringUtils.isNotBlank(map.get("DateSpan")) && StringUtils.isNumeric(map.get("DateSpan"))){
		    		DateSpan=map.get("DateSpan");
		    	}
		    }	
		}
		
		if(days>new Integer(DateSpan) && !DateSpan.equals("0")){
			String message="";
			if(EndDate.equals("0")){
				message="请选择"+DateSpan+"日内的日期，到访日期当天有效";
				
			}else {
				pojo.setBegindate(CommonUtil.dateToStr(new Date(),DateUtil.PATTERN_DATE) + " 00:00:00");
				message="请选择"+DateSpan+"日内的日期，截止选择日期有效";
			}
			return WriteToPage.setResponseMessage(message, IfConstant.CUSTOM, dateTime);
		}
		
		if(EndDate.equals("0")){
			pojo.setBegindate(pojo.getTime() + " 00:00:00");
		}else {
			pojo.setBegindate(CommonUtil.dateToStr(new Date(),DateUtil.PATTERN_DATE) + " 00:00:00");
		}
		
		pojo.setEnddate(pojo.getTime() + " 23:59:59");
		pojo.setPhone(pojo.getPhone().replace(" ", "").replace("-", ""));

		String visitorTransitCode = "";
		Project project = projectMapper.findByPrimaryKey(pojo.getRegionalId());
		//MyvisitorPojo visitor = inmapper.myvisitor(null, pojo.getPhone(),pojo.getAccountId());
		VisitorIn vin = new VisitorIn();
		
		vin.setVisitorType("02");
		vin.setAccountId(pojo.getAccountId()+"");
		vin.setPhone(pojo.getPhone());
		List<VisitorModel> vlist = inmapper.queryVistorByParams(vin);
		
		if (vlist != null && vlist.size()>0 && vlist.get(0) !=null) {
			VisitorModel visitor=vlist.get(0);
			visitorTransitCode = visitor.getTransit_code();
			pojo.setVisitorid(Long.parseLong(visitor.getVisitor_id()+""));
			VisitorIn vi=new VisitorIn();
			vi.setVisitorId(visitor.getVisitor_id()+"");
			vi.setName(pojo.getName());
			vi.setCompany(pojo.getCompany());
			inmapper.updateDynamic(vi);
		}
		if (StringUtils.isBlank(pojo.getEnddate())) {
			pojo.setEnddate(pojo.getBegindate());
		}
		
		if(StringUtils.isBlank(pojo.getBeginTime())){
			pojo.setBeginTime("00:00:00");
		}
		if(StringUtils.isBlank(pojo.getEndTime())){
			pojo.setEndTime("23:59:59");
		}
		if (pojo.getVisitorid() > 0) {// 这个是访客表中有该访客
			pojo.setTransitCode(visitorTransitCode);
			
		} else {
			visitorTransitCode = HexUtil.intToHex(Integer.parseInt(GenerateCreater.getTransitCode()), 4);
			pojo.setVisitorid(getIdWorker().nextId());
			pojo.setTransitCode(visitorTransitCode);
			pojo.setPhone(pojo.getPhone().replace(" ", "").replace("-", ""));
			pojo.setProjectCode(null);
			pojo.setOrgId(null);
			
			inmapper.insertvisitor(pojo);
		}
		
		//保存邀请记录 TODO:
		UniqueCache uniqueCache=null;
		VisitorApply va=new VisitorApply();
		va.setStatusCode("02");
		VisitorApply vaQuery=new VisitorApply();
		vaQuery.setVisitorId(pojo.getVisitorid());
		vaQuery.setStatusCode("01");
		vaQuery.setSourceType("02");
		
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
		
		pojo.setRelationid(getIdWorker().nextId());
		// 查询组织房源关联的节点
		String arrangeIds = this.deviceMapper.queryArrangeByOrgId(orgId);
		if(StringUtils.isBlank(arrangeIds)){
			arrangeIds="null";
		}
		//查找节点的父节点
		Arrange arrange =new Arrange();
		arrange.setArrangeIds(arrangeIds);
		arrange.setEntrance_type(RegionalConstant.ArrangeType01);
		List<Arrange> arrPubList=arrangeMapper.queryParentDynamic(arrange);
		
		// 查找内部访客区域（员工）节点
		List<Arrange> arrRegList=new ArrayList<Arrange>();
		if(StringUtils.isNotBlank(orgId) ){
			Arrange arrange02 =new Arrange();
			arrange02.setProjectId(project.getProjectId());
			arrange02.setRegionalType("03");
			arrange02.setOrgId(CommonUtil.string2Long(orgId));
			arrange02.setScopeType("01");
			arrRegList=arrangeMapper.getArrRegDynamic(arrange02);
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
		
		VisitorApplyContents vac = new VisitorApplyContents();
		vac.setIdCode(visitorTransitCode);
		vac.setDevicePre(project.getProjectCode());
		List<String> transitDevices = new ArrayList<>();
			
		String openAccess = this.createOpenAccess(deviceList, transitDevices,pojo);
		vac.setOpenAccess(openAccess);
		vac.setSynchCode("00000000");//同步码
		String address = project.getAddress();
		vac.setAddress(address);
		pojo.setContents(BeanUtility.objectToJsonStr(vac, false));
		pojo.setVisitorCode(CommonUtil.generateShortUuid());
		pojo.setSourceType(VisitorConstant.APPLY_SOURCE_TYPE_APP);
		pojo.setTransitDevices(StringUtils.join(transitDevices, ","));
			
		VisitorApply va2=new VisitorApply();
		va2.setProjectId(new Long(pojo.getRegionalId()));
		va2.setSourceType("02");
		String trafficStrategy=visitorService.updateTrafficStrategy(va2);
		if(StringUtils.isNotBlank(trafficStrategy)){
			pojo.setTrafficStrategy(trafficStrategy);
		}
		pojo.setOrgId(orgId);
		pojo.setProjectId(pojo.getRegionalId());
		pojo.setOperatorId(pojo.getAccountId()+"");
		int row = inmapper.insertVisitorApply(pojo);
		if (row <= 0) {				
			return WriteToPage.setResponseMessage("{}", IfConstant.NET_FAIL, dateTime);
		}
		
		// 添加缓存
		passFilterThroughService.visitorApplyCache(pojo.getRelationid()+"");
			
		// 获取发送短信内容
		String projectName = "我公司";
//		if (elist.size()>0 && elist != null) {
//		orgName = elist.get(0).getSuperName();
//	}
		projectName = project.getProjectName();
		String appCodeName = rAppProjectMapper.selectAppCodeNameByProjectId(project.getProjectId());
		ConfigureService configureService = SpringContextHolder.getBean("configureService");
		if(StringUtils.isBlank(appCodeName)) {
			appCodeName = "IBB";
		}
		
		
		MobileMsgTemplate result = configureService.getMobileTemplateConfs("15", AppCodeNameEnum.getAdapteDescrib(appCodeName));
		String contents = result.getContents();
		String[] msgArray = new String[] {projectName, InitConf.getSelfvisitorapplypath() + pojo.getVisitorCode() + " ",
				project.getAddress(),randomNumbe4(), pojo.getBegindate(),
				pojo.getEnddate()};
//		String[] msgArray = new String[] { orgName,InitConf.getSelfvisitorapplypath()+pojo.getVisitorCode()+" ",
//				project.getAddress(),pojo.getVisitorCode(),pojo.getBegindate(),
//				pojo.getEnddate()};
		for (int i = 0, lg = msgArray.length; i < lg; i++) {
			String key = "{" + (i + 1) + "}";
			String val = msgArray[i];
			contents = contents.replace(key, val);
		}
		if (StringUtils.isNotBlank(pojo.getIsMessage())) {
			String title = "【包办网】";
			if(StringUtils.isNotBlank(appCodeName)) {
				switch (appCodeName) {
				case "ARPM":
					title = "【安荣物业】";
					break;
				case "SHZX":
					title = "【中星城】";
					break;
				case "SJSM":
					title = "【世纪商贸】";
					break;

				default:
					title = "【包办网】";
					break;
				}
			}
			String resultMessage="访客二维码，";
			String endDateStr=CommonUtil.dateToStr(CommonUtil.strToDate(pojo.getEnddate(),CommonUtil.DATE_FMT_YMD),CommonUtil.DATE_FMT_YMD_CN);
			if(EndDate.equals("0")){
				resultMessage=resultMessage+"到访日期"+endDateStr+"。";
			}else {
				resultMessage=resultMessage+"有效期截止"+endDateStr+"。";
			}
			
			Map<String,Object> ret = new HashMap<String,Object>();
			ret.put("messageTitle", title  +resultMessage);
			ret.put("message", contents);
			ret.put("url", InitConf.getSelfvisitorapplypath() + pojo.getVisitorCode());
			return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStr(ret, true), IfConstant.SERVER_SUCCESS, dateTime);
		}
		return WriteToPage.setResponseMessage(InitConf.getSelfvisitorapplypath()+pojo.getVisitorCode()+" ", IfConstant.SERVER_SUCCESS, dateTime);
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
	public String querymyvisitor(ControllerView view, Logger logger) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 实体内容转为对象
		PropertyRequestPojo pojo = BeanUtility.jsonStrToObject(dataJson, PropertyRequestPojo.class, true);
		String dateTime = String.valueOf(new Date().getTime());
		// 这里是想当数据加载完毕时候走的。。可根据data数据 判断是否加载完毕
//		String count = inmapper.querymyvisitorcount(pojo.getAccountId());
		// if(Integer.parseInt(pojo.getPage())*10>Integer.parseInt(count))
		// return WriteToPage.setResponseMessage("{数据加载完毕}",
		// IfConstant.SERVER_SUCCESS, dateTime);
		//填写别人挖的坑
		if (StringUtils.isNotBlank(pojo.getPage())){
			pojo.setPageNo(pojo.getPage());
		}
		if (StringUtils.isNotBlank(pojo.getPagesize())){
			pojo.setPageSize(pojo.getPage());
		}
		
		PagingBounds page = this.getPagingBounds(pojo.getPageNo(),pojo.getPageSize());// 分页
		List<MyvisitorPojo> pojo1 = inmapper.queryMyVisitorPageList(pojo.getAccountId(),page);
		ReturnPageBean<MyvisitorPojo> ret =  this.getPageResultBean(pojo1,page );
		String resultData = BeanUtility.objectToJsonStr(ret, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	
	public String getProOrgByAccId(ControllerView view, Logger logger) throws Exception{
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 请求的签名
		String mac = view.getMac();
		// 实体内容转为对象
		RegionalPojo pojo = BeanUtility.jsonStrToObject(dataJson, RegionalPojo.class, false);
		String dateTime = String.valueOf(new Date().getTime());
		List<RegionalPojo> list = inmapper.getProOrgByAccId(pojo.getAccountId());
		if (list == null)
		return WriteToPage.setResponseMessage("[]", IfConstant.SERVER_SUCCESS, dateTime);
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		Map<String,Map<String,Object>> listMap2 =new HashMap<String,Map<String,Object>>();
		
		for(RegionalPojo rp:list){
			if(listMap2.get(rp.getRegionalId()) ==null ){
				Map<String,Object> map=new HashMap<String,Object>();
				Map<String,String> orgMap=new HashMap<String,String>();
				List<Map<String,String>> orgList=new ArrayList<Map<String,String>>();
				map.put("projectId",rp.getRegionalId());
				map.put("projectName",rp.getRegionalName());
				orgMap.put("orgId",rp.getOrgId());
				orgMap.put("orgName",rp.getOrgName());
				orgList.add(orgMap);
				map.put("orgList", orgList);
				listMap2.put(rp.getRegionalId(),map);
			}else{
				Map<String,Object> map=listMap2.get(rp.getRegionalId());
				List<Map<String,String>> orgList=(List<Map<String, String>>) map.get("orgList");
				Map<String,String> orgMap=new HashMap<String,String>();
				orgMap.put("orgId",rp.getOrgId());
				orgMap.put("orgName",rp.getOrgName());
				orgList.add(orgMap);
				map.put("orgList", orgList);
				listMap2.put(rp.getRegionalId(),map);
			}
			
						
		}
		for(String key:listMap2.keySet()){
			listMap.add(listMap2.get(key));
		}
		// 生成返回实体json
		String resultData = BeanUtility.objectToJsonStr(listMap, false);
		// 发送成功
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	
	
	public String getResourceByOrgId(ControllerView view, Logger logger) throws Exception{
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 请求的签名
		String mac = view.getMac();
		// 实体内容转为对象
		RegionalPojo pojo = BeanUtility.jsonStrToObject(dataJson, RegionalPojo.class, false);
		String dateTime = String.valueOf(new Date().getTime());
		List<ResourceModel> list =myRegionalMapper.getResourceByOrgId(pojo.getOrgId());
		if (list == null)
		return WriteToPage.setResponseMessage("[]", IfConstant.SERVER_SUCCESS, dateTime);
		// 生成返回实体json
		String resultData = BeanUtility.objectToJsonStr(list, false);
		// 发送成功
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	
	
	public String querytxqy(ControllerView view, Logger logger) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 请求的签名
		String mac = view.getMac();
		// 实体内容转为对象
		RegionalPojo pojo = BeanUtility.jsonStrToObject(dataJson, RegionalPojo.class, false);
		String dateTime = String.valueOf(new Date().getTime());
		List<RegionalPojo> list = inmapper.queryRegional(pojo.getAccountId());
		if (list == null)
			return WriteToPage.setResponseMessage("[]", IfConstant.SERVER_SUCCESS, dateTime);
		// 生成返回实体json
		String resultData = BeanUtility.objectToJsonStr(list, false);
		// 发送成功
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String myvisitoradd(ControllerView view, Logger logger) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 实体内容转为对象
		VisitorInfoPojo pojo = BeanUtility.jsonStrToObject(dataJson, VisitorInfoPojo.class, true);
		pojo.setPhone(pojo.getPhone().replace(" ", ""));
		String dateTime = String.valueOf(new Date().getTime());
		
		if (StringUtils.isBlank(pojo.getName()) || StringUtils.isBlank(pojo.getPhone()) ) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		MyvisitorPojo isco = inmapper.myvisitor(null, pojo.getPhone(),pojo.getAccountId());
		if (isco != null){
			VisitorIn vi=new VisitorIn();
			vi.setVisitorId(isco.getVisitorId());
			vi.setPhone(pojo.getPhone());
			vi.setName(pojo.getName());
			vi.setAccountId(pojo.getAccountId()+"");
			vi.setCompany(pojo.getCompany());
			inmapper.updateDynamic(vi);
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
		}
			
		pojo.setVisitorid(getIdWorker().nextId());
		String visitorTransitCode = HexUtil.intToHex(Integer.parseInt(GenerateCreater.getTransitCode()), 4);
		pojo.setTransitCode(visitorTransitCode);
		int row1 = inmapper.insertvisitor(pojo);
		if (row1 <= 0)
			return WriteToPage.setResponseMessage("{}", IfConstant.NET_FAIL, dateTime);
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}
	/**
	 * 方法描述 :访客记录
	 * 创建人 :  hzhang
	 * 创建时间: 2016年10月26日 下午8:32:36
	 * @return
	 * @throws AppException 
	 */
	public String applyRecord(ControllerView view, Logger logger) throws AppException {
		//请求的json格式实体内容
		String dataJson = view.getData();
		//实体内容转为对象
		VisitorApplyIn in = BeanUtility.jsonStrToObject(dataJson, VisitorApplyIn.class, true);
		String dateTime = String.valueOf(new Date().getTime());
		if (StringUtils.isBlank(in.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		PagingBounds page = this.getPagingBounds(in.getPageNo()+"",in.getPageSize()+"");// 分页
		List<VisitorApplyOut> out = inmapper.queryApplyRecordPageList(in.getVisitorId(),in.getAccountId(),page);
		ReturnPageBean<VisitorApplyOut> ret =  this.getPageResultBean(out,page);
		// 生成返回实体json
		String resultData = BeanUtility.objectToJsonStr(ret, false);
		System.out.println(resultData);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	/**
	 * 方法描述 :访客记录
	 * 创建人 :  hzhang
	 * 创建时间: 2016年10月26日 下午8:32:36
	 * @return
	 * @throws AppException 
	 */
	public String deleteApplyRecord(ControllerView view, Logger logger) throws AppException {
		//请求的json格式实体内容
		String dataJson = view.getData();
		//实体内容转为对象
		VisitorApplyIn in = BeanUtility.jsonStrToObject(dataJson, VisitorApplyIn.class, true);
		String dateTime = String.valueOf(new Date().getTime());
		if (StringUtils.isBlank(in.getApplyId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_VISITOR_APPLY_ID_NULL, dateTime);
		}
		int out = inmapper.deleteApplyRecord(in.getApplyId());
		if (out < 1) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_VISITOR_APPLY_IS_DELETE, dateTime);
		}
		// 生成返回实体json
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}
	/**
	 * 删除访客
	 * @param view
	 * @param logger
	 * @return
	 * @throws AppException 
	 */
	public String deleteVisitor(ControllerView view, Logger logger) throws AppException {
		//请求的json格式实体内容
		String dataJson = view.getData();
		//实体内容转为对象
		VisitorApplyIn in = BeanUtility.jsonStrToObject(dataJson, VisitorApplyIn.class, true);
		String dateTime = String.valueOf(new Date().getTime());
		if (StringUtils.isBlank(in.getVisitorId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_VISITOR_ID_IS_NOT_NULL, dateTime);
		}
		int out = inmapper.updateVisitorDelete(in.getVisitorId(),in.getAccountId());
		if (out < 1) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_VISITOR_APPLY_IS_DELETE, dateTime);
		}
		// 生成返回实体json
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}
	/**
	 * 重发邀请短信内容
	 * @param view
	 * @param logger
	 * @return
	 * @throws Exception 
	 */
	public String visitorApplyRepeat(ControllerView view, Logger logger) throws Exception {
		//请求的json格式实体内容
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		//实体内容转为对象
		VisitorApplyIn in = BeanUtility.jsonStrToObject(dataJson, VisitorApplyIn.class, true);
		if (StringUtils.isBlank(in.getApplyId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_VISITOR_APPLY_ID_NULL, dateTime);
		}
		List<VisitorApply> applyList = this.inmapper.queryVistorApplyByParams(in);
		/*UniqueCache uniqueCache = uniqueCacheMapper.queryUniqueValueByKey(Long.parseLong(in.getApplyId()));*/
		if (applyList == null || applyList.size() ==0) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_VISITOR_APPLY_IS_DELETE, dateTime);
		}
		VisitorApply apply = applyList.get(0);
		Project project = projectMapper.findByPrimaryKey(apply.getProjectId()+"");
		
		List<EmpRelations> elist = this.queryEmpByAccProPer(apply.getAccountId()+"", project.getProjectId()+"", null);
		String orgName = "我公司";
//		if (elist.size()>0 && elist != null) {
//			orgName = elist.get(0).getSuperName();
//		}
		orgName = project.getProjectName();
		String appCodeName = rAppProjectMapper.selectAppCodeNameByProjectId(project.getProjectId());
		ConfigureService configureService = SpringContextHolder.getBean("configureService");
		if(StringUtils.isBlank(appCodeName)) {
			appCodeName = "IBB";
		}
		MobileMsgTemplate result = configureService.getMobileTemplateConfs("15", AppCodeNameEnum.getAdapteDescrib(appCodeName));
		String contents = result.getContents();
		String[] msgArray = new String[] { orgName, InitConf.getSelfvisitorapplypath() + apply.getVisitorCode() + " ",
				project.getAddress(), randomNumbe4(), DateUtil.format(apply.getBeginDate(), "yyyy-MM-dd"),
				DateUtil.format(apply.getEndDate(), "yyyy-MM-dd")};
//		String[] msgArray = new String[] { orgName,InitConf.getSelfvisitorapplypath()+apply.getVisitorCode()+" ",
//				project.getAddress(),apply.getVisitorCode(),DateUtil.format(apply.getBeginDate(), "yyyy-MM-dd"),
//				DateUtil.format(apply.getEndDate(), "yyyy-MM-dd")};
		for (int i = 0, lg = msgArray.length; i < lg; i++) {
			String key = "{" + (i + 1) + "}";
			String val = msgArray[i];
			contents = contents.replace(key, val);
		}
		MyvisitorPojo visitor = this.inmapper.findById(apply.getVisitorId()+"");
		Map<String,Object> ret = new HashMap<String,Object>();
		ret.put("mobilephone", visitor.getPhone());
		ret.put("messageTitle", contents);
		ret.put("message", contents);
		ret.put("url", InitConf.getSelfvisitorapplypath()+apply.getVisitorCode());
		return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStr(ret, true), IfConstant.SERVER_SUCCESS, dateTime);
	}
	
	
	
	private String randomNumbe4(){
		Random random = new Random();
		String fourRandom = random.nextInt(10000) +"";
		int randLength = fourRandom.length();
		if(randLength<4){
		for(int i=1; i<=4-randLength; i++)
			fourRandom = "0" + fourRandom ;
		}
		return fourRandom;
	}
	
}
