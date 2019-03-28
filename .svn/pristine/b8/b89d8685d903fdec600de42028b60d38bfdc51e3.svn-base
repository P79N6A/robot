package com.bossbutler.service.myregional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bossbutler.common.constant.AccountConstant;
import com.bossbutler.common.constant.MediaMainClassify;
import com.bossbutler.common.constant.MediaType;
import com.bossbutler.common.constant.QrcodeConstant;
import com.bossbutler.common.constant.ServiceConstant;
import com.bossbutler.common.constant.StatusType;
import com.bossbutler.common.constant.VisitorConstant;
import com.bossbutler.mapper.myinfo.MyinfoMenuMapper;
import com.bossbutler.mapper.myregional.Arrange;
import com.bossbutler.mapper.myregional.MyRegionalMapper;
import com.bossbutler.mapper.project.ProjectMapper;
import com.bossbutler.mapper.system.AccountMapper;
import com.bossbutler.mapper.system.AccountTransitMapper;
import com.bossbutler.mapper.system.EmpMapper;
import com.bossbutler.mapper.visitor.VisitorApplyMapper;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.DevicePojo;
import com.bossbutler.pojo.FileResultOut;
import com.bossbutler.pojo.ImageFilePojo;
import com.bossbutler.pojo.ImageFileView;
import com.bossbutler.pojo.ImageInfoPojo;
import com.bossbutler.pojo.ImagePojo;
import com.bossbutler.pojo.MobileCodeView;
import com.bossbutler.pojo.MyArrangePojo;
import com.bossbutler.pojo.TreeNode;
import com.bossbutler.pojo.org.OrgProjectRelation;
import com.bossbutler.pojo.project.Project;
import com.bossbutler.pojo.regional.ArrangeModel;
import com.bossbutler.pojo.regional.Device;
import com.bossbutler.pojo.regional.RegionalArrangeRelationModel;
import com.bossbutler.pojo.regional.RegionalEmpRelationModel;
import com.bossbutler.pojo.regional.RegionalModel;
import com.bossbutler.pojo.regional.TransitModel;
import com.bossbutler.pojo.regional.VisitorApplyContents;
import com.bossbutler.pojo.regional.VisitorMedia;
import com.bossbutler.pojo.regional.VisitorModel;
import com.bossbutler.pojo.resource.ResourceModel;
import com.bossbutler.pojo.system.Account;
import com.bossbutler.pojo.system.AccountOut;
import com.bossbutler.pojo.system.AccountTransit;
import com.bossbutler.pojo.system.EmpRelations;
import com.bossbutler.pojo.visitor.LongtermVisitor;
import com.bossbutler.pojo.visitor.TerminalPojo;
import com.bossbutler.pojo.visitor.VisitorApply;
import com.bossbutler.pojo.visitor.VisitorApplyType;
import com.bossbutler.pojo.visitor.VisitorIn;
import com.bossbutler.pojo.visitor.VisitorMachineIn;
import com.bossbutler.service.BasicService;
import com.bossbutler.service.image.ImageService;
import com.bossbutler.util.CommonMobileMsgSend;
import com.bossbutler.util.GenerateCreater;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.WriteToPage;
import com.common.cnnetty.gateway.util.BarCodeUtil;
import com.common.cnnetty.gateway.util.EidentityType;
import com.common.cnnetty.gateway.util.HexUtil;
import com.company.exception.AppException;
import com.company.util.BeanUtility;
import com.company.util.DateUtil;
import com.company.util.IdWorker;

@Service
public class MyVisitorService extends BasicService {
	@Autowired
	private VisitorApplyMapper visitorApplyMapper;
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private AccountTransitMapper accountTransitMapper;
	@Autowired
	private EmpMapper empMapper;
	@Autowired
	private MyRegionalMapper regionalMapper;
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private ImageService imageService;
	@Autowired
	private MyinfoMenuMapper mymapper;
	//查找临时访客是否已经有记录
	public VisitorModel findVisitorByIdCard(String proiject_code,String idNo) {
		return visitorApplyMapper.findVisitorByIdCard(proiject_code,idNo);
	}
	/**
	 * 创建一个访客
	 * @param model
	 */
	public void createVisitor(VisitorModel model) {
		visitorApplyMapper.createVisitor( model);
	}
	public VisitorApplyContents createMachineVisitorApply(VisitorModel model,String regional_id,VisitorModel visitor,String transitTime ,String mackAddress) throws Exception {
		TerminalPojo terminal = visitorApplyMapper.findTransitById(mackAddress);
		if(terminal.getTerminal_type().equals("1")){
			model.setSource_type(VisitorConstant.APPLY_SOURCE_TYPE_RECE);
		}else if(terminal.getTerminal_type().equals("2")){
			model.setSource_type(VisitorConstant.APPLY_SOURCE_TYPE_SELFHELP);
		}
		List<Long> list = new ArrayList<Long>();
		MyArrangePojo mapModel=new MyArrangePojo();
		mapModel.setRegionalId(regional_id);
		List<MyArrangePojo> querymyarrange = regionalMapper.querymyarrange(mapModel);
		//StringBuffer arrangeNames= new StringBuffer();
		for(MyArrangePojo marr : querymyarrange){
			list.add(Long.parseLong(marr.getArrangeId()));
			//arrangeNames.append(marr.getArrangeName()+",");
		}
		List<Device> devices = visitorApplyMapper.getDeviceByContents(list);
	
		if(null == transitTime){
			if(null !=terminal && 0 != terminal.getBarcode_time()){
				transitTime = terminal.getBarcode_time()+"";
				model.setEnd_time(DateUtil.add(model.getBegin_time(), DateUtil.PATTERN_TIME, Long.parseLong(transitTime)*10, TimeUnit.MINUTES));
				model.setEnd_date(DateUtil.add(model.getBegin_date(), DateUtil.PATTERN_STANDARD, Long.parseLong(transitTime)*10, TimeUnit.MINUTES));
			}else{
				model.setEnd_time(DateUtil.add(model.getBegin_time(),  DateUtil.PATTERN_TIME, QrcodeConstant.QRCODE_VALID_TIME, TimeUnit.HOURS));
				model.setEnd_date(DateUtil.add(model.getBegin_date(),  DateUtil.PATTERN_STANDARD, QrcodeConstant.QRCODE_VALID_TIME, TimeUnit.HOURS));
			}
			
		}else{
			model.setEnd_time(DateUtil.add(model.getBegin_date().substring(11),  DateUtil.PATTERN_TIME, Long.parseLong(transitTime)*10, TimeUnit.MINUTES));
			model.setEnd_date(DateUtil.add(model.getBegin_date(),  DateUtil.PATTERN_STANDARD, Long.parseLong(transitTime)*10, TimeUnit.MINUTES));
		}
		
		String benginDay=DateUtil.stringToDay(model.getBegin_date());
		String endDay=DateUtil.stringToDay(model.getEnd_date());
		if(!benginDay.equals(endDay)){
			model.setEnd_time("23:59:59");
			model.setEnd_date(model.getBegin_date().substring(0, 10));
		}
		
		VisitorApplyContents visitorApplyContents = new VisitorApplyContents();
        String projectCode = "";
        String projectName="";
        Long project_id = null;
        StringBuffer sb = new StringBuffer();
        List<String> transitDevices = new ArrayList<>();
       
        if(StringUtils.isBlank(model.getEnd_time())){
        	model.setEnd_time("23:59:59");
        }
        SimpleDateFormat dfs = new SimpleDateFormat("HH:mm:ss");
		long between=(dfs.parse(model.getEnd_time()).getTime()-dfs.parse(model.getBegin_time()).getTime())/1000/60;// 换成分钟
		int index=(int) between;
		for (Device d : devices) {
			
			if("device".equals(d.getDeviceType())){
				
				String td=d.getDeviceCode();
				if(d.getControllerType().equals("03")){
					td=d.getSerialNumber();
				}
				if(!transitDevices.contains(d.getControllerType()+td)){
					transitDevices.add(d.getControllerType()+td);
				}
				if(d.getControllerType().equals("01")){
					String validTime =HexUtil.intToHex(index/10, 1);;
					
					String dcode = d.getDeviceCode();
					dcode = dcode.substring(dcode.length()-4, dcode.length());
					sb.append(dcode+validTime);
				}
				
			}else{
				 projectCode=d.getDeviceCode();
				 projectName=d.getDeviceType();
				 project_id = d.getDeviceId();
			 }
		}
		
		
		//不够60位后面补0
		if (sb.length() < 60) {
			for (int i = 0, lg = 60 - sb.length(); i < lg; i++) {
				sb.append("F");
			}
		}
		
		model.setTransit_devices(StringUtils.join(transitDevices, ","));
        visitorApplyContents.setIdCode(visitor.getTransit_code());
        model.setAccount_id(0L);
        visitorApplyContents.setDevicePre(projectCode);
        visitorApplyContents.setOpenAccess(sb.toString());
        visitorApplyContents.setSynchCode("00000000");
        visitorApplyContents.setAddress(projectName);
        //visitorApplyContents.setArrangeName(arrangeNames.toString());
        visitorApplyContents.setVisitorCode(model.getVisitor_code());
        model.setContents(BeanUtility.objectToJsonStr(visitorApplyContents, false));
        model.setProject_id(project_id);
        
        visitorApplyMapper.createVisitorApply(model);
		return visitorApplyContents;
	}
	/**
	 * 得到项目下的所有公共通行区域节点
	 * @param project_code
	 * @return
	 */
	public List<RegionalModel> getPublicRegionalByMachId(String mackAddress) {
		TerminalPojo transit= visitorApplyMapper.findTransitById(mackAddress);
		if(null ==transit){
			return null;
		}
		List<RegionalModel> list_all = visitorApplyMapper.getPublicRegionalByMachId(transit.getTerminal_id()+"");
		
		return list_all;
	}
	/**
	 * 得到项目下的所有启用公共通行区域节点
	 * @param project_code
	 * @return
	 */
	public List<RegionalModel> getPublicRegionalEnableByMachId(String mackAddress) {
		TerminalPojo transit= visitorApplyMapper.findTransitById(mackAddress);
		if(null ==transit){
			return null;
		}
		List<RegionalModel> list_all = visitorApplyMapper.getPublicRegionalEnableByMachId(transit.getTerminal_id()+"");
		for (RegionalModel r : list_all) {
			Long regionalId = r.getRegional_id();
			List<Long> regList = new ArrayList<>();
			regList.add(regionalId);
			List<Device> deviceList = this.visitorApplyMapper.getDeviceByRegionalId(regList);
			r.setDeviceList(deviceList);
		}
		return list_all;
	}
	/**
	 * 通过ID查找访客
	 * @param parseLong
	 * @return
	 */
	public VisitorModel findVisitorById(long id) {
		return visitorApplyMapper.findVisitorById(id);
	}
	/**
	 * 通过手机号查找account
	 * @param mobilephone
	 * @return
	 */
	public Account findAccountByMobile(String mobilephone) {
		 return accountMapper.queryUserByPhone(mobilephone);
	}
	/**
	 * 查看是否已经被邦定
	 * @param accountId
	 * @param icCode
	 * @return
	 */
	public Account hasIcCode(String accountId, String icCode) {
		return accountTransitMapper.hasIcCode(icCode);
	}
	public Map bindIc(String accountId, String icCode,String operatorId,IdWorker iw) {
		Map<String,String> map = new HashMap<String,String>();
		try {
			//此卡是否被别个邦定
			Account account = accountTransitMapper.hasIcCode(icCode);
			AccountTransit transit = new AccountTransit();
			if(null != account && !accountId.equals(account.getAccountId()+"")){
				map.put("accountId", account.getAccountId()+"");
				transit.setTransitCode("temp");
				transit.setTransitId(Long.parseLong(account.getTransitId()));
				accountTransitMapper.update(transit);
				map.put("transitId", account.getTransitId());
			}
			//此用户是否已经邦定卡
			AccountTransit accountTransit = accountTransitMapper.findByTypeAccountId(accountId,"01");
			if(null != accountTransit){//如果已经邦定 则更新IC卡
				transit.setTransitCode(icCode);
				transit.setTransitId(accountTransit.getTransitId());
				accountTransitMapper.update(transit);
				map.put("icCode", accountTransit.getTransitCode());
				return map;
			}
			transit = new AccountTransit();
			transit.setTransitId(iw.nextId());
			transit.setAccountId(Long.parseLong(accountId));
			transit.setTransitType("01");
			transit.setTransitCode(icCode);
			transit.setStatusCode("01");
			transit.setStatusTime(new Date());
			transit.setCreateTime(new Date());
			transit.setUpdateTime(new Date());
			transit.setDataVersion(new Date());
			transit.setOperatorId(Long.parseLong(operatorId));
			accountTransitMapper.insert(transit);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public void deleteByPrimaryKey(String id) throws Exception {
		accountTransitMapper.deleteByPrimaryKey(id);	
	}

	/**
	 * 绑定IC卡名单维护
	 * @param empList
	 * @param icCode
	 * @param string
	 * @param iw
	 * @throws Exception
	 */
	public void bindTransit(List<EmpRelations> empList, String icCode, String temp,IdWorker iw,String operatorId,String mackAddress) throws Exception {
		/**
		 * 1.通过相关人员找到Account
		 * 2.查找人员所有的通行区域
		 * 3.通过通行区域查询所有设备
		 */
		List<RegionalEmpRelationModel> list = new ArrayList<RegionalEmpRelationModel>();
		for(EmpRelations emp : empList){
			RegionalEmpRelationModel remp = new RegionalEmpRelationModel();
			remp.setEmp_id(emp.getEmpId());
			list.add(remp);
		}
		List<Account> accounts= accountTransitMapper.getAccontByEmpId2(list);
		List<TransitModel> oldTransits = new ArrayList<TransitModel>();
		List<TransitModel> newTransits = new ArrayList<TransitModel>();
		for(EmpRelations emp : empList){
			/**
			 * 首先找到该房源的通行日期
			 * 		如果没有：跳过
			 * 		把相关设备找到产生名单
			 * 		找到下一个房源 比较时间 
			 */
			String maxDate = "";
			String minDate = "";
			List<Device> divices = new ArrayList<Device>();
			List<ResourceModel> resourceList = regionalMapper.getOrgResourceByOrgId(Long.parseLong(emp.getOrgId()));
			Project p =projectMapper.getProjectByEmpId(emp.getEmpId()+"");
			List<RegionalArrangeRelationModel> rar = new ArrayList<>();
			for(ResourceModel res : resourceList){
				if("".equals(maxDate) && "".equals(minDate)){
					maxDate =  DateUtil.date2String(res.getEndDate(), DateUtil.PATTERN_DATE);
					minDate =  DateUtil.date2String(res.getBeginDate(), DateUtil.PATTERN_DATE);
				}
				int minInt = DateUtil.compareDateStr(minDate, DateUtil.date2String(res.getBeginDate(), DateUtil.PATTERN_DATE), DateUtil.PATTERN_DATE);
				int maxInt = DateUtil.compareDateStr(maxDate, DateUtil.date2String(res.getEndDate(), DateUtil.PATTERN_DATE), DateUtil.PATTERN_DATE);
				if(maxInt<0){
					maxDate =  DateUtil.date2String(res.getEndDate(), DateUtil.PATTERN_DATE);
				}
				if(minInt>0){
					minDate=DateUtil.date2String(res.getBeginDate(), DateUtil.PATTERN_DATE);
				}
				ArrangeModel arrangeModel= regionalMapper.getParentArrange(res.getArrangeId());
				String status_code = arrangeModel.getStatus_code();
				if("$".equals(status_code)){
					continue;
				}
				status_code = status_code.substring(2);
				List<Arrange> parentArrange = regionalMapper.getArrangeByArrIdPrId(status_code,p.getProjectId());
				for(Arrange arr : parentArrange){
					RegionalArrangeRelationModel regArr = new RegionalArrangeRelationModel();
					regArr.setArrange_id(arr.getArrangeId());
					rar.add(regArr);
				}
				if(rar.size()>0){
					divices.addAll(regionalMapper.getDeviceByArrangeIds(rar));
					List<TransitModel> tempNewTransits = getTempNewTransits(divices,accounts,p,iw,DateUtil.date2String(res.getBeginDate(), DateUtil.PATTERN_DATE),DateUtil.date2String(res.getEndDate(), DateUtil.PATTERN_DATE),temp);
					newTransits = getNewTransitsDate(newTransits,tempNewTransits);
				}
				List<Device> pubDivices = regionalMapper.getPublicDevicebyProjectId(p);
				if(pubDivices.size()>0){
					newTransits.addAll(getTempNewTransits(pubDivices,accounts,p,iw,minDate,maxDate,temp));
				}
				if(accounts.size()>0 && divices.size()>0){
					oldTransits.addAll(regionalMapper.getTransitByDeviceAccId2(divices,accounts.get(0),p.getProjectId()));
				}
			}
			
		}
		List<RegionalModel> regionalList= visitorApplyMapper.getRegionalListByEmpId(list);
		TerminalPojo transit= visitorApplyMapper.findTransitById(mackAddress);
		//查找项目下所有的机构
		List<OrgProjectRelation> orgList = visitorApplyMapper.findOrgListByPId(transit.getProject_id());
		List<Long> regList = new ArrayList<Long>();
		for(RegionalModel reg : regionalList){
			for(OrgProjectRelation org : orgList){
				if(org.getOrgId().equals(reg.getOrg_id())){
					regList.add(reg.getRegional_id());
				}
			}
		}
		List<Device> tempDivices = new ArrayList<Device>();
		if(regList.size()>0){
			tempDivices = visitorApplyMapper.getDeviceByRegionalId(regList);
		}
		if(tempDivices.size()>0){
			oldTransits.addAll(regionalMapper.getTransitByDeviceAccId(tempDivices,accounts,"01"));
		} 
		
		for(Device d : tempDivices){
			Project p = visitorApplyMapper.getProjectByRegionalId(d.getRegional_id());
			for(Account acc : accounts){
				if("01".equals(acc.getTransit_type())){
				TransitModel t = new TransitModel();
				t.setList_id(iw.nextId());
				t.setAccount_code(acc.getAccountCode());
				t.setProject_id(p.getProjectId());
				t.setDevice_code(d.getDeviceCode());
				t.setTransit_code(icCode);
				t.setTransit_type(acc.getTransit_type());
				t.setBegin_date(DateUtil.getCurrentDate());
				t.setEnd_date("2050-12-31");
				t.setBegin_time("0");
				t.setEnd_time("23:59:59");
				t.setLimit_count(0);
				t.setProjectName(p.getProjectName());
				t.setOrg_name(p.getOrg_name());
				t.setEmp_name(acc.getEmp_name());
				t.setStatus_code(temp);
				t.setStatus_time(DateUtil.getCurrentDate());
				t.setOperator_id(0L);
				t.setSynch_state("01");
				t.setData_version(DateUtil.timestamp2String(DateUtil.currentTimestamp(), DateUtil.PATTERN_STANDARD));
				t.setOperator_id(Long.parseLong(operatorId));
				newTransits.add(t);
				}
			}
		}
		if(newTransits.size()>0){
			Set set = new HashSet(newTransits);
			newTransits.clear(); 
			newTransits = new ArrayList(set);    
			createTransitList(oldTransits,newTransits);
		}
	}

	/**
	 * 产生的名单 和 结果表中的数据做对比
	 * @param oldList
	 * @param newList
	 * @return
	 * @throws Exception
	 */
	public void createTransitList(List<TransitModel> oldList,List<TransitModel> newList)throws Exception{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~createTransitList-begin~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		//仅存在于新列表中的数据
		List<TransitModel> transitList = new ArrayList<TransitModel>();
		//获取仅存在于新表中的数据
		if(null !=newList){
			for(TransitModel nmodel : newList){
				boolean isuniquer = true;
				if(null != oldList){
					for(TransitModel omodel : oldList){
						/*	String a = nmodel.getAccount_code()+"--"+nmodel.getDevice_code()+"--"+nmodel.getTransit_code();
						String b = omodel.getAccount_code()+"--"+omodel.getDevice_code()+"--"+omodel.getTransit_code();
						System.out.println(a);
						System.out.println(b);
						int compareDateStr1 = DateUtil.compareDateStr(nmodel.getEnd_date(), omodel.getEnd_date(), DateUtil.PATTERN_DATE);
						System.out.println(compareDateStr1);
						System.out.println("--------------------------------");*/
						if(nmodel.getAccount_code().equals(omodel.getAccount_code()) && nmodel.getDevice_code().equals(omodel.getDevice_code())&& nmodel.getTransit_code().equals(omodel.getTransit_code())){
							int beingDateStr = DateUtil.compareDateStr(nmodel.getBegin_date(), omodel.getBegin_date(), DateUtil.PATTERN_DATE);
							int endDateStr = DateUtil.compareDateStr(nmodel.getEnd_date(), omodel.getEnd_date(), DateUtil.PATTERN_DATE);
							if(null != nmodel.getBegin_date() && null != nmodel.getEnd_date() && (nmodel.getBegin_date().equals(omodel.getBegin_date()) && nmodel.getEnd_date().equals(omodel.getEnd_date())) || (beingDateStr>=0 && endDateStr<=0)){
								if(!"03".equals(nmodel.getStatus_code())){
									isuniquer = false;
								}
								break;
							}       
							if(beingDateStr>0){
								nmodel.setBegin_date(omodel.getBegin_date());
							}
							if(endDateStr<0){
								nmodel.setEnd_date(omodel.getEnd_date());
							}
							nmodel.setStatus_code("02");
						}
					}
					if(isuniquer){
						transitList.add(nmodel);
					}
				}
			}
		}
		if(null != transitList){
			System.out.println("uniqueNewList: "+transitList.size()); 
		}
		if(null != oldList){
			System.out.println("uniqueOldList: " + oldList.size()); 
		}
		if(transitList.size()>0){
			regionalMapper.createTransitList(transitList);
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~createTransitList-end~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	private List<TransitModel> getTempNewTransits(List<Device> divices,List<Account> accounts,Project p,IdWorker iw,String beginDate,String endDate,String temp){
		List<TransitModel> newTransits = new ArrayList<TransitModel>();
		for(Device d : divices){
			for(Account acc : accounts){
				TransitModel t = new TransitModel();
				t.setList_id(iw.nextId());
				t.setAccount_code(Integer.parseInt(acc.getAccountCode()+""));
				t.setProject_id(p.getProjectId());
				t.setDevice_code(d.getDeviceCode());
				t.setTransit_code(acc.getTransit_code());//四种通行类型
				t.setTransit_type(acc.getTransit_type());
				t.setBegin_date(beginDate);
				t.setEnd_date(endDate);
				t.setBegin_time("0");
				t.setEnd_time("23:59:59");
				t.setLimit_count(0);
				t.setProjectName(p.getProjectName());
				t.setOrg_name(p.getOrg_name());
				t.setEmp_name(acc.getEmp_name());
				t.setStatus_code(temp);
				t.setStatus_time(DateUtil.getCurrentDate());
				t.setOperator_id(Long.parseLong(acc.getAccountId()));
				t.setSynch_state("01");
				//t.setSynch_time();
				//t.setSynch_remark();
				t.setData_version(DateUtil.timestamp2String(DateUtil.currentTimestamp(), DateUtil.PATTERN_STANDARD));
				newTransits.add(t);
			}
		}
		return newTransits;
	}

	private List<TransitModel> getNewTransitsDate(List<TransitModel> newTransits, List<TransitModel> tempNewTransits) throws Exception {
		List<TransitModel> transitList  = new ArrayList<TransitModel>();
		for(TransitModel  tempTransit: tempNewTransits  ){
			boolean temp = false;
			for(TransitModel transit  : newTransits){
				if(transit.getAccount_code().equals(tempTransit.getAccount_code()) 
						&& transit.getTransit_type().equals(tempTransit.getTransit_type())
						&& transit.getDevice_code().equals(tempTransit.getDevice_code())){
					temp  = true;
					int beingDateStr = DateUtil.compareDateStr(tempTransit.getBegin_date(), tempTransit.getBegin_date(), DateUtil.PATTERN_DATE);
					int endDateStr = DateUtil.compareDateStr(tempTransit.getEnd_date(), tempTransit.getEnd_date(), DateUtil.PATTERN_DATE);
					if(beingDateStr>0){
						tempTransit.setBegin_date(tempTransit.getBegin_date());
					}
					if(endDateStr<0){
						tempTransit.setEnd_date(tempTransit.getEnd_date());
					}
				}
			}
			if(!temp){
				transitList.add(tempTransit);
			}
		}
		newTransits.addAll(transitList);
		return newTransits;
	}
	/**
	 * 通过accountId查找所有用户
	 * @param accountId
	 * @return
	 */
	public List<EmpRelations> queryEmpByAccProPer(String accountId) {
		Map<String,Object> params = new HashMap<>();
		params.put("accountId", accountId);
		return empMapper.queryEmpByAccProPer(params);
		
	}
	public Map updateServerImage(ImageFileView view,String createTime,String mediaID) throws Exception {
		String dataJson = view.getData();
		List<MultipartFile> multipartFileList = view.getFileBytes();
		long bill_id = 0;
		Map<String,Object> map = new HashMap<String,Object>();
		if (multipartFileList != null  && multipartFileList.size()>0) {
			for (MultipartFile multipartFile : multipartFileList) {
				ImageInfoPojo info = BeanUtility.jsonStrToObject(dataJson, ImageInfoPojo.class, true);
				String fileType = info.getFileType();
				if (StringUtils.isBlank(info.getBussinessID())) {
					return null;
				}
				if (bill_id == 0) {
					bill_id = Long.parseLong(info.getBussinessID());
				}
				FileResultOut fileResultOut = uploadFile(ServiceConstant.main_classify, info.getBussinessID(), false, false,
						multipartFile.getBytes(), fileType, createTime, mediaID);
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
	public VisitorMedia hasOtherIc(String visitor_id) {
		VisitorMedia acctr = visitorApplyMapper.hasOtherIc(visitor_id);
		if(null==acctr){
			return null;
		}
		return acctr;
	}
	public void createVisitorMedia(VisitorMedia media) {
		visitorApplyMapper.createVisitorMedia(media);
	}

	public void delVisitorFile(String mediaID, String createTime) {
		delServerFile(mediaID, createTime);
	}
	/**
	 * 获得生成二维码信息
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	public String getContents(String code) throws Exception {
		String dateTime = String.valueOf(new Date().getTime());
		Map<String,Object> map = new HashMap<String,Object>();
		VisitorApply va = visitorApplyMapper.findByVisitorCode(code);
		if(null == va){
			return WriteToPage.setResponseMessage("{}",IfConstant.PARAM_MACHINE_VISITOR_APPLY_NOT_EXIST,dateTime);
		}
		if (StringUtils.isNotBlank(va.getApplyType())) {
			return WriteToPage.setResponseMessage("{}",IfConstant.PARAM_MACHINE_VISITOR_APPLY_INVALID,dateTime);
		}
		if (va.getEndDate().before(new Date()) && !DateUtil.compareDate(va.getEndDate(), new Date())) {
			return WriteToPage.setResponseMessage("{}",IfConstant.PARAM_MACHINE_VISITOR_APPLY_TIMEOUT,dateTime);
		}
		if(null != va.getEndTime() && DateUtil.compareDate(va.getEndDate(), new Date()) &&
				DateUtil.string2Date(
						DateUtil.format(va.getEndDate(),"yyyy-MM-dd" )+" "+
								DateUtil.format(va.getEndTime(),"HH:mm:ss" )
						, "yyyy-MM-dd HH:mm:ss").before(new Date())
				){
			return WriteToPage.setResponseMessage("{}",IfConstant.PARAM_MACHINE_VISITOR_APPLY_TIMEOUT,dateTime);
		}
		String contents = va.getContents();
		VisitorApplyContents vac = BeanUtility.jsonStrToObject(contents, VisitorApplyContents.class, false);
		String arrangeName = vac.getArrangeName();
		String projectName = vac.getAddress();
		String qrDate = getQrDate(new Date());
		if (va.getBeginDate().after(new Date()) && !DateUtil.compareDate(va.getBeginDate(), new Date())) {
			qrDate = getQrDate(DateUtil.string2Date(DateUtil.format(va.getBeginDate(),"yyyy-MM-dd" )+" 00:00:00","yyyy-MM-dd HH:mm:ss" ));
		}
		if(null != va.getBeginTime() && DateUtil.compareDate(va.getBeginDate(), new Date()) &&
				DateUtil.string2Date(
						DateUtil.format(va.getBeginDate(),"yyyy-MM-dd" )+" "+
								DateUtil.format(va.getBeginTime(),"HH:mm:ss" )
						, "yyyy-MM-dd HH:mm:ss").after(new Date())
				){
			qrDate = getQrDate(DateUtil.string2Date(DateUtil.format(va.getBeginDate(),"yyyy-MM-dd" )+" "+
					DateUtil.format(va.getBeginTime(),"HH:mm:ss" )
			, "yyyy-MM-dd HH:mm:ss"));
		}
		String contens = BarCodeUtil.createBarCode(EidentityType.VISITOR, 
				vac.getIdCode(), qrDate, vac.getDevicePre(), vac.getOpenAccess());
		map.put("arrangeName",arrangeName);
		map.put("contens",contens);
		map.put("projectName",projectName);
		map.put("beginDate",va.getBeginDate());
		map.put("endDate",va.getEndDate());
		if (va.getBeginTime() != null ) {
			map.put("beginTime",DateUtil.format(va.getBeginTime(),"HH:mm:ss" ));
			map.put("endTime",DateUtil.format(va.getEndTime(),"HH:mm:ss" ));
		}else{
			map.put("beginTime", "00:00:00");
			map.put("endTime", "23:59:59");
		}
		return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStr(map, false),IfConstant.SERVER_SUCCESS,dateTime);
	}
	/**
	 * 生成160808080808的日期格式
	 * @param date 2016-08-08 08:08:08
	 * @return 160808080808
	 */
	private String getQrDate(Date date){
		String ret = DateUtil.date2String(date, null);
		ret = ret.replace("-", "").replace(":", "").replace(" ", "").trim();
		ret = ret.substring(2, ret.length());
		return ret;
	}
	//通过通行区域ID 获得所有的节点
	public List<TreeNode> getArrangeByRegionalId(String regional_id, String mackAddress) {
		MyArrangePojo mapModel=new MyArrangePojo();
		mapModel.setRegionalId(regional_id);
		List<MyArrangePojo> selectArrange = regionalMapper.querymyarrange(mapModel);
		//得到项目下的所有节点\
		TerminalPojo transit= visitorApplyMapper.findTransitById(mackAddress);
		if(null == transit){
			return null;
		}
		List<ArrangeModel> allArrange = visitorApplyMapper.getPublicAllArrange(transit.getProject_id()+"");
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		for (ArrangeModel m : allArrange) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(m.getArrange_id().toString());
			treeNode.setName(m.getArrange_name());
			treeNode.setIsParent(false);
			treeNode.setpId(m.getSupper_id()+"");
			treeNode.setOpen(true);
			for (MyArrangePojo am : selectArrange) {
				if (am.getArrangeId().equals(m.getArrange_id()+"")) {
					treeNode.setChecked(true);
				}
			}
			List<DevicePojo> deviceList = this.regionalMapper.querymydeviceDEX(m.getArrange_id().toString());
			treeNode.setDeviceList(deviceList);
			treeNodeList.add(treeNode);
		}
		return treeNodeList;
	}
	/**
	 * 修改 通行区域下的节点
	 * @param regional_id
	 * @param ids
	 * @param iw
	 */
	public void saveSelectArrange(String regional_id,String ids,String regional_name,String operatorId,IdWorker iw) {
		
		List<ArrangeModel> list = new ArrayList<ArrangeModel>();
		String[] id = ids.split(",");
		for(String str : id){
			ArrangeModel model = new ArrangeModel();
			model.setRelation_id(iw.nextId());
			model.setRegional_id(Long.parseLong(regional_id));
			model.setArrange_id(Long.parseLong(str));
			list.add(model);
		}
		if(!"".equals(regional_name)){
			regionalMapper.setRegionalNameById(regional_id, regional_name,operatorId);;
		}
		visitorApplyMapper.deleteArrange(regional_id);
		visitorApplyMapper.addArrangeToRegional(list);
	}
	public String getPhoneNumber(String visitorId) {
		VisitorModel model = visitorApplyMapper.findVisitorById(Long.parseLong(visitorId));
		return model.getMobilephone();
	}
	public void updatePhoneNumber(String visitorId, String phone,String operatorId) {
		visitorApplyMapper.updatePhoneNumber(visitorId,phone,operatorId);		
	}
	/**
	 * 查询访客机下的通行区域
	 * @param terminalId 访客机ID
	 * @param statusCode  状态代码（01，初始化；02，已禁用；）
	 * @return
	 */
	public List<RegionalModel> getTerminalRegionalByTerId(String mackAddress,String statusCode)throws Exception {
		return regionalMapper.getTerminalRegionalByTerId(mackAddress,statusCode);
	}
	/**
	 * 创建访客机通行区域
	 * @param terminalId
	 * @param regionalName
	 * @param arrangeId
	 */
	public void createTerminalRegional(String mackAddress, String regionalName, String arrangeId,String operatorId,IdWorker iw)throws Exception {
		/**
		 * 创建通行区域
		 * 创建节点和通行区域的关系
		 */
		TerminalPojo transit= visitorApplyMapper.findTransitById(mackAddress);
		if(null == transit){
			throw new Exception();
		}
		RegionalModel regional = new RegionalModel();
		regional.setRegional_id(iw.nextId());
		//regional.setOrg_id(org_id);
		//regional.setSupper_id(supper_id);
		regional.setRegional_type("03");
		regional.setRegional_name(regionalName);
		regional.setTerminal_id(transit.getTerminal_id());
		regional.setStatus_code("01");
		regional.setOperator_id(Long.parseLong(operatorId));
		visitorApplyMapper.createRegional(regional);
		if(null != arrangeId && !"".equals(arrangeId)){
			List<ArrangeModel> arrangeList = new ArrayList<ArrangeModel>();
			String[] arrangeIds = arrangeId.split(",");
			for(String arrId : arrangeIds){
				ArrangeModel regionalArrange = new ArrangeModel();
				regionalArrange.setRelation_id(iw.nextId());
				regionalArrange.setRegional_id(regional.getRegional_id());
				regionalArrange.setArrange_id(Long.parseLong(arrId));
				regionalArrange.setStatus_code("01");
				arrangeList.add(regionalArrange);
			}
			if(arrangeList.size()>0){
				visitorApplyMapper.addArrangeToRegional(arrangeList);
			}
		}
		
	}
	/**
	 * 启用或禁用通行区域
	 * @param regionalid
	 * @param statusCode
	 */
	public void setRegionalStatusCode(String regionalid, String statusCode,String operatorId) {
		regionalMapper.setRegionalStatusCode(regionalid,statusCode,operatorId);
	}
	/**
	 * 设置通行时间
	 * @param terminalId
	 * @param transitTime
	 */
	public String setTerminalTransitTime(String mackAddress, String transitTime,String operatorId) {
		TerminalPojo terminal = visitorApplyMapper.findTransitById(mackAddress);
		if(null == terminal){
			return WriteToPage.setResponseMessageForError("未找到此访客机！", IfConstant.UNKNOWN_ERROR.getCode());
		}
		visitorApplyMapper.setTerminalTransitTime(mackAddress,transitTime,operatorId);
		return WriteToPage.setResponseMessage(null,IfConstant.SERVER_SUCCESS);
	}
	/**
	 * 获得通行访客机通行时间 
	 * @param terminalId
	 * @return
	 */
	public String getTerminalById(String mackAddress) {
		TerminalPojo terminal = visitorApplyMapper.findTransitById(mackAddress);
		if(null == terminal){
			return WriteToPage.setResponseMessageForError("未找到此访客机！", IfConstant.UNKNOWN_ERROR.getCode());
		}
		return WriteToPage.setResponseMessage(terminal,IfConstant.SERVER_SUCCESS);
	}
	public Project getProjectByMacAdress(String mackAddress) {
		TerminalPojo transit= visitorApplyMapper.findTransitById(mackAddress);
		if(null == transit){
			return null;
		}
		Project project = visitorApplyMapper.getProjectById(transit.getProject_id()+"");
		return project;
	}
	public String setTerminalDesc(String mackAddress, String terminalDesc, String operatorId) {
		TerminalPojo terminal = visitorApplyMapper.findTransitById(mackAddress);
		if(null == terminal){
			return WriteToPage.setResponseMessageForError("未找到此访客机！", IfConstant.UNKNOWN_ERROR.getCode());
		}
		visitorApplyMapper.setTerminalDesc(mackAddress,terminalDesc,operatorId);
		return WriteToPage.setResponseMessage(null,IfConstant.SERVER_SUCCESS);
	}
	public String setBarcodeRemark(String mackAddress, String barcodeRemark,
			String operatorId) {
		TerminalPojo terminal = visitorApplyMapper.findTransitById(mackAddress);
		if(null == terminal){
			return WriteToPage.setResponseMessageForError("未找到此访客机！", IfConstant.UNKNOWN_ERROR.getCode());
		}
		visitorApplyMapper.setBarcodeRemark(mackAddress,barcodeRemark,operatorId);
		return WriteToPage.setResponseMessage(null,IfConstant.SERVER_SUCCESS);
	}
	/**
	 * 
	 * @param name
	 * @param password
	 * @throws Exception 
	 */
	public String getAccountfroBindIc(String name, String password) throws Exception {
		String dateTime = String.valueOf(new Date().getTime());
		// 登录用户验证
		Account tmpAccount = accountMapper.queryUser(name, null);
		if (null == tmpAccount) {
			return WriteToPage.setResponseMessage("login该用户没有注册", IfConstant.LOGINNAME_NOT_EXIST);
		}
		if(null==password || "".equals(password)){
			return WriteToPage.setResponseMessage("密码不能为空！", IfConstant.LOGIN_ERROR);
		}
	//	String md5 = CommonUtil.MD5(password);
		Account account = accountMapper.queryUser(name, password);
		if (account == null) {
			return WriteToPage.setResponseMessage("用户名或密码不正确！", IfConstant.LOGIN_ERROR);
		}
		AccountOut out = new AccountOut();
		BeanUtility.bindObject(out, account);
		AccountTransit at = accountTransitMapper.findByTypeAccountId(account.getAccountId(),
				AccountConstant.TransitTypeIc);
//		out.setQrId(at.getTransitType()+at.getTransitCode());
		if (at != null) {
			out.setIcId(at.getTransitCode());
		}
		out.setHeadPath(imageService.getAccountHeadPath(account.getAccountId()));
		//获取公司信息
		out.setCompanies(mymapper.queryCompanies(account.getAccountId()));
		String resultData = BeanUtility.objectToJsonStr(out, true);
		// 验证成功
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
		
	}
	public VisitorApply findByVisitorCode(String code) {
		return visitorApplyMapper.findByVisitorCode(code);
	}
	/**
	 * 获得项目下的协作
	 * @param projectId
	 * @return
	 */
	public OrgProjectRelation getOrgyProId(Long projectId) {
		return visitorApplyMapper.getOrgyProId(projectId);
	}
	/**
	 * 抓拍访客图片
	 * @param view
	 * @return
	 * @throws AppException
	 * @throws IOException
	 */
	public String uploadSnapVisitorPic(ImageFileView view) throws AppException, IOException {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		List<MultipartFile> multipartFileList = view.getFileBytes();
		List<String> filePaths = new ArrayList<String>();
		if (multipartFileList.size()>0 && multipartFileList != null) {
			for (MultipartFile multipartFile : multipartFileList) {
				ImageInfoPojo info = BeanUtility.jsonStrToObject(dataJson, ImageInfoPojo.class, true);
//				String fileType = info.getFileType();
				if (StringUtils.isBlank(info.getBussinessID())) {
					return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_VISITOR_ID_IS_NOT_NULL);
				}
				FileResultOut fileResultOut = uploadFile(MediaMainClassify.MediaMainClassify11, info.getBussinessID(), false, false,
						multipartFile.getBytes(), "png", null, null);
				if (fileResultOut.isSuccess()) {
					ImagePojo pojo1 = visitorApplyMapper.queryVisitorOrdinal(MediaMainClassify.MediaMainClassify11, MediaType.MediaTypeVisitor09
							,String.valueOf(info.getBussinessID()));
					String dataJson1 = fileResultOut.getData();
					ImageFilePojo pojo = BeanUtility.jsonStrToObject(dataJson1, ImageFilePojo.class, true);
					VisitorMedia media = new VisitorMedia();
					media.setMedia_id(pojo.getMediaID());
					media.setVisitor_id(info.getBussinessID());
					media.setMedia_type(MediaType.MediaTypeVisitor09);
					media.setFile_type("jpg");
					media.setMedia_no(info.getMediaNo());
					media.setMain_classify(MediaMainClassify.MediaMainClassify11);
					media.setUpdate_time(DateUtil.getCurrentDate());
					media.setOperator_id(info.getOperatorId());
					media.setOrdinal(Long.parseLong(pojo1.getOrdinal()));
					this.createVisitorMedia(media);
					filePaths.add(pojo.getFilepath());
				}else{
					return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStr(filePaths, false), IfConstant.FILE_IMAGE_UPLOAD_FAIL);
				}
			}
		}
		return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStr(filePaths, false), IfConstant.SERVER_SUCCESS, dateTime);
	}
	public String deleteTerminalRegional(ControllerView view) throws AppException {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		VisitorMachineIn in = BeanUtility.jsonStrToObject(dataJson, VisitorMachineIn.class, true);
		if (StringUtils.isBlank(in.getRegionalId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_REGIONAL_ID_NULL, dateTime);
		}
		//删除通行区域
		regionalMapper.deleteRegionalArrangeRelationById(in.getRegionalId());
		//删除通行区域与节点的关系
		regionalMapper.deleteById(in.getRegionalId());
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}
	
	/**
	 * 返回访客邀请记录里的类型字典
	 *   typeId 为 12
	 */
	public List<VisitorApplyType> getApplyTypes(String typeId) {
		return visitorApplyMapper.getApplyTypes(typeId);
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
	 		 List<String> transitDevices = new ArrayList<>();
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
		 		visitor.setTransit_devices(transitDevices.toString().substring(0,transitDevices.toString().length()-1));

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
	/**
	 * 解除长期访客IC卡绑定
	 * @param view
	 * @return
	 * @throws Exception 
	 */
	public String unbundlingLongtermVisitor(ControllerView view) throws Exception {
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
		if (visitor == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_VISITOR_NOT_EXIST, dateTime);
		}
		//当前ic卡与当前访客绑定ic卡样
		if (StringUtils.isBlank(visitor.getIccard_code())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_VISITOR_NOT_BINDING_ICCARD, dateTime);
		}
		String oldIcNo = visitor.getIccard_code();
		//解绑icCard
		int ret = this.visitorApplyMapper.updateVisitorIcNo("",in.getOperatorId(),visitor.getVisitor_id());
		if (ret < 1 ) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_VISITOR_NOT_EXIST, dateTime);
		}
		int trannsitCode = HexUtil.hexToInt(visitor.getTransit_code());
		String bn = "10"+this.format8bitInt(trannsitCode, 8, "0");
		List<TransitModel>  tl = regionalMapper.queryTransitByTyAcTcPi(AccountConstant.TransitTypeIc,bn,oldIcNo, project.getProjectId());
		if (tl.size()>0) {
			this.deleteTransitList(tl,in.getOperatorId());
		}
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
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
	/**
	 * 创建长期访客
	 * @param view
	 * @return
	 * @throws Exception 
	 */
	public String createLongtermVisitorTemp(ImageFileView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		List<MultipartFile> multipartFileList = view.getFileBytes();
		List<String> filePaths = new ArrayList<String>();
		VisitorIn in = BeanUtility.jsonStrToObject(dataJson, VisitorIn.class, true);
		//参数校验开始
		if (StringUtils.isBlank(in.getMackAddress())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_MACADDRESS_IS_NOT_NULL);
		}
		if (StringUtils.isBlank(in.getIdCardNo())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_IDCARDNO_IS_NOT_NULL);
		}
		if (StringUtils.isBlank(in.getIdCardInfo())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_IDCARDINFO_IS_NOT_NULL);
		}
		//查询设备对应的项目
		Project project = this.getProjectByMacAdress(in.getMackAddress());
		if (project == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_NOT_EXIST_PROJECT);
		}
		//参数校验结束
		//建立访客开始
		VisitorModel visitor = findVisitorByIdCard(project.getProjectCode(),in.getIdCardNo());
		if (visitor != null) {//已存在项目里的访客
			VisitorMedia vm = visitorApplyMapper.hasOtherIc(visitor.getVisitor_id()+"");
			in.setMediaID(vm.getMedia_id());
			this.uploadVisitorMedia(multipartFileList,in,true);
		}else{
			OrgProjectRelation orgP = this.getOrgyProId(project.getProjectId());
			visitor = new VisitorModel();
			visitor.setVisitor_id(this.getIdWorker().nextId());
			visitor.setVisitor_type(VisitorConstant.visitorType03);
			visitor.setTransit_code(HexUtil.intToHex(Integer.parseInt(GenerateCreater.getTransitCode()), 4));
			visitor.setVisitor_name(in.getName());
			visitor.setIdcard(in.getIdCardNo());
			visitor.setRemark(in.getIdCardInfo());
			if(null != orgP){
				visitor.setOrg_id(orgP.getOrgId());
			}
			visitor.setRemark(null);
			visitor.setStatus_code(StatusType.INIT);
			visitor.setProject_code(project.getProjectCode());
			visitor.setOperator_id(Long.parseLong(in.getOperatorId()));
			visitorApplyMapper.createVisitor(visitor);
			this.uploadVisitorMedia(multipartFileList,in,false);
		}
		//建立访客结束
		//建立长期访客配置信息开始
		visitor.setRegional_id(this.getIdWorker().nextId());
		visitor.setBegin_date(in.getBeginDate());
		visitor.setEnd_date(in.getEndDate());
		visitor.setBegin_time(in.getBeginTime());
		visitor.setEnd_time(in.getEndTime());
		visitor.setSource_type("01");
		this.visitorApplyMapper.createVisitorApply(visitor);
		//建立长期访客配置信息结束
		return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStr(filePaths, false), IfConstant.SERVER_SUCCESS, dateTime);
	}
	/**
	 * 上传访问附件
	 * @param fileList
	 * @param in
	 * @param isUpdate
	 * @throws AppException 
	 * @throws IOException 
	 */
	private void uploadVisitorMedia(List<MultipartFile> fileList , VisitorIn in, boolean isUpdate) throws AppException, IOException {
		if (fileList.size()>0 && fileList != null) {
			for (MultipartFile multipartFile : fileList) {
				if (isUpdate) {
					this.visitorApplyMapper.deleteVisitorMedia(in.getMediaID());
				}
				FileResultOut fileResultOut = uploadFile(MediaMainClassify.MediaMainClassify11, in.getVisitorId(), false, false,
						multipartFile.getBytes(), "jpg", null, null);
				if (fileResultOut.isSuccess()) {
					ImagePojo pojo1 = visitorApplyMapper.queryVisitorOrdinal(MediaMainClassify.MediaMainClassify11, in.getMediaType()
							,String.valueOf(in.getVisitorId()));
					String dataJson1 = fileResultOut.getData();
					ImageFilePojo pojo = BeanUtility.jsonStrToObject(dataJson1, ImageFilePojo.class, true);
					VisitorMedia media = new VisitorMedia();
					media.setMedia_id(pojo.getMediaID());
					media.setVisitor_id(in.getVisitorId());
					media.setMedia_type(in.getMediaType());
					media.setFile_type("jpg");
					media.setMedia_no(in.getIdCardNo());
					media.setMain_classify(MediaMainClassify.MediaMainClassify11);
					media.setUpdate_time(DateUtil.getCurrentDate());
					media.setOperator_id(in.getOperatorId());
					media.setOrdinal(Long.parseLong(pojo1.getOrdinal()));
					this.visitorApplyMapper.createVisitorMedia(media);
				}else{
					new Exception();
				}
			}
		}
		
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
	public String verifyPhoneRegularStaff(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		MobileCodeView mobileCodeView = BeanUtility.jsonStrToObject(dataJson, MobileCodeView.class, true);
		// 获得手机识别码
		String sn = mobileCodeView.getSn();
		String mobilenu = mobileCodeView.getPhone();
		String codeTemp = mobileCodeView.getContent();
		// 返回验证码
		Object codeObj = CommonMobileMsgSend.getSendMobileCode(sn + mobilenu);
		if (codeObj == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.MOBILE_CODE_UNUSED, dateTime);
		}
		String[] codeStrArray = codeObj.toString().split(",");
		String code = codeStrArray[0];
		if (!codeTemp.equals(code)) {
			return WriteToPage.setResponseMessage("{}", IfConstant.MOBILE_CODE_UNRIGHT, dateTime);
		}
		AccountOut out = new AccountOut();
		Account account = this.accountMapper.queryUserById(mobileCodeView.getAccountId());
		BeanUtility.bindObject(out, account);
		AccountTransit at = accountTransitMapper.findByTypeAccountId(account.getAccountId(),
				AccountConstant.TransitTypeIc);
//		out.setQrId(at.getTransitType()+at.getTransitCode());
		if (at != null) {
			out.setIcId(at.getTransitCode());
		}
		out.setHeadPath(imageService.getAccountHeadPath(account.getAccountId()));
		//获取公司信息
		out.setCompanies(mymapper.queryCompanies(account.getAccountId()));
		String resultData = BeanUtility.objectToJsonStr(out, true);
		// 验证成功
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String unbundingIcRegularStaff(ControllerView view) throws Exception {
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
		Account account = this.accountMapper.queryUserById(in.getAccountId());

		/*List<TransitModel> decTlist = this.regionalMapper.queryTransitListByTyAcTcPi(account.getAccountCode(),null,project.getProjectId(),in.getIc());
		if (decTlist != null && decTlist.size()>0) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_MACHINE_ICNO_IS_BINDING, dateTime);
		}*/
		Account accountT = accountTransitMapper.hasIcCode(in.getIc());
		if (accountT != null ) {
			//删除用户
			this.accountTransitMapper.deleteByPrimaryKey(accountT.getTransitId());
			//TODO:以后需要调整accountCode规则 11+accountCode
			List<TransitModel>  tl = regionalMapper.queryTransitByTyAcTcPi(AccountConstant.TransitTypeIc, accountT.getAccountCode()+"",in.getIc(), project.getProjectId());
			if (tl.size()>0) {
				this.deleteTransitList(tl,in.getOperatorId());
			}
		}
		return WriteToPage.setResponseMessage("", IfConstant.SERVER_SUCCESS, dateTime);
	}

}
