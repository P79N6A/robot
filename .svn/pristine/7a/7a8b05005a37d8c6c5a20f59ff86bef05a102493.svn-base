package com.bossbutler.service.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.common.constant.AccountConstant;
import com.bossbutler.common.constant.InvitationType;
import com.bossbutler.common.constant.StatusType;
import com.bossbutler.exception.TransitListException;
import com.bossbutler.mapper.InvitationMapper;
import com.bossbutler.mapper.RAppAccountPushMapper;
import com.bossbutler.mapper.account.AccountProfileMapper;
import com.bossbutler.mapper.menu.MenuMapper;
import com.bossbutler.mapper.myinfo.MyinfoMenuMapper;
import com.bossbutler.mapper.myregional.Arrange;
import com.bossbutler.mapper.myregional.MyRegionalMapper;
import com.bossbutler.mapper.org.OrgMapper;
import com.bossbutler.mapper.project.ProjectMapper;
import com.bossbutler.mapper.system.AccountMapper;
import com.bossbutler.mapper.system.AccountTransitMapper;
import com.bossbutler.mapper.system.EmpMapper;
import com.bossbutler.mapper.visitor.VisitorApplyMapper;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.MyCompany;
import com.bossbutler.pojo.MyselfinfoPojo;
import com.bossbutler.pojo.OrgModel;
import com.bossbutler.pojo.account.RAppAccountPushModel;
import com.bossbutler.pojo.menu.MenuitemModel;
import com.bossbutler.pojo.org.OrgProjectRelation;
import com.bossbutler.pojo.org.RoleModel;
import com.bossbutler.pojo.project.Project;
import com.bossbutler.pojo.regional.ArrangeModel;
import com.bossbutler.pojo.regional.Device;
import com.bossbutler.pojo.regional.RegionalArrangeRelationModel;
import com.bossbutler.pojo.regional.RegionalEmpRelationModel;
import com.bossbutler.pojo.regional.TransitModel;
import com.bossbutler.pojo.resource.ResourceModel;
import com.bossbutler.pojo.system.Account;
import com.bossbutler.pojo.system.AccountIn;
import com.bossbutler.pojo.system.AccountOut;
import com.bossbutler.pojo.system.AccountTransit;
import com.bossbutler.pojo.system.Employee;
import com.bossbutler.pojo.system.Invitation;
import com.bossbutler.pojo.system.InvitationContent;
import com.bossbutler.pojo.third.ComboProjectIdName;
import com.bossbutler.pojo.visitor.TerminalPojo;
import com.bossbutler.service.BasicService;
import com.bossbutler.service.image.ImageService;
import com.bossbutler.service.myregional.ProjectService;
import com.bossbutler.util.CommonMobileMsgSend;
import com.bossbutler.util.GenerateCreater;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.RedisConstant;
import com.bossbutler.util.WriteToPage;
import com.common.cnnetty.gateway.util.HexUtil;
import com.company.common.redis.active.CacheManager;
import com.company.exception.AppException;
import com.company.util.BeanUtility;
import com.company.util.CommonUtil;
import com.company.util.DateUtil;
import com.company.util.IdWorker;

/**
 * <p>
 * 用户业务管理.
 * </p>
 * 
 * @author wq
 *
 */
@Service
public class AccountService extends BasicService {
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private AccountTransitMapper accountTransitMapper;
	@Autowired
	private InvitationMapper invitationMapper;
	@Autowired
	private EmpMapper empMapper;
	@Autowired
	private ImageService imageService;
	@Autowired
	private MyinfoMenuMapper mymapper;
	@Autowired
	private VisitorApplyMapper visitorApplyMapper;
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private MyRegionalMapper regionalMapper;
	@Autowired
	private OrgMapper orgMapper;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private RAppAccountPushMapper rAppAccountPushMapper;
	@Autowired
	private AccountProfileMapper accountProfileMapper;
	@Autowired
	private MenuMapper menuMapper;
	private final static String APP_CUSTOMER_ID ="002";
	/**
	 * 登录
	 * @param view
	 * @param nocacheStr
	 * @param logger
	 * @return
	 * @throws Exception
	 */
	public String queryUserForLogin(ControllerView view, Logger logger, String appCodeName) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 实体内容转为对象
		AccountIn in = BeanUtility.jsonStrToObject(dataJson, AccountIn.class, true);
		// 参数时间戳
		String dateTime = String.valueOf(new Date().getTime());
		// 设置登录令牌信息
		String oldToken = in.getOldToken();
		String token = oldToken;
		// 登录用户验证
		Account tmpAccount = accountMapper.queryUser(in.getLoginName(), null);
		if (tmpAccount == null) {
			logger.warn("login该用户没有注册：" + in.getLoginName());
			return WriteToPage.setResponseMessage("{}", IfConstant.LOGINNAME_NOT_EXIST, dateTime);
		}
		Account account = accountMapper.queryUser(in.getLoginName(), in.getPwd());
		AccountOut out = new AccountOut();
		if (account == null) {//
			logger.warn("用户密码或账号错误：" + in.getLoginName());
			return WriteToPage.setResponseMessage("{}", IfConstant.LOGIN_ERROR, dateTime);
		} else if (StringUtils.isNotBlank(oldToken)
				&& CacheManager.hexists(RedisConstant.REDIS_TABLE_TOKEN_KEY, oldToken)) {// 如果用户存在且客户端token存在继续使用
			out.setToken(oldToken);
		} else {// 如果用户存在没有oldToke，创建新的token并返回
			token = CommonUtil.generateUuid() + dateTime;
			out.setToken(token);
			//保存到redis
		}
		BeanUtility.bindObject(out, account);
		//修改登录时间
		accountMapper.updateLoginTime(account.getAccountId()+"",in.getLogInfo());
		AccountTransit at = accountTransitMapper.findByTypeAccountId(account.getAccountId(),
				AccountConstant.TransitTypeQr);
//		out.setQrId(at.getTransitType()+at.getTransitCode());
		out.setQrId(at.getTransitCode());
		out.setHeadPath(imageService.getAccountHeadPath(account.getAccountId()));
		//获取评价得分
		MyselfinfoPojo info = mymapper.queryinfo(account.getAccountId());
		//获取公司信息
		out.setCompanies(mymapper.queryCompanies(account.getAccountId()));
		out.setBillCount(info.getBillCount());
		out.setSex(info.getSex());
		out.setScore(info.getScore());
		//增加当前时间戳
		out.setCurrentTime(String.valueOf(new Date().getTime()));
		// TODO:添加菜单列表
		out.setMenus(getMenuList(account.getAccountId(), appCodeName));
		// 生成返回实体json
		String resultData = BeanUtility.objectToJsonStr(out, true);
		// 更缓存中的token
		CacheManager.setHTableKeyVal(RedisConstant.REDIS_TABLE_TOKEN_KEY, token, view.getDateTime());
	
		
		// 登录成功
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	
	
	/**
	 * 登录
	 * @param view
	 * @param nocacheStr
	 * @param logger
	 * @return
	 * @throws Exception
	 */
	public String queryUserForLogin02(ControllerView view, Logger logger, String appCodeName) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 实体内容转为对象
		AccountIn in = BeanUtility.jsonStrToObject(dataJson, AccountIn.class, true);
		// 参数时间戳
		String dateTime = String.valueOf(new Date().getTime());
		// 设置登录令牌信息
		String oldToken = in.getOldToken();
		String token = oldToken;
		// 登录用户验证
		Account tmpAccount = accountMapper.queryUser(in.getLoginName(), null);
		if (tmpAccount == null) {
			logger.warn("login该用户没有注册：" + in.getLoginName());
			return WriteToPage.setResponseMessage("{}", IfConstant.LOGINNAME_NOT_EXIST, dateTime);
		}
		Account account = accountMapper.queryUser(in.getLoginName(), in.getPwd());
		AccountOut out = new AccountOut();
		if (account == null) {//
			logger.warn("用户密码或账号错误：" + in.getLoginName());
			return WriteToPage.setResponseMessage("{}", IfConstant.LOGIN_ERROR, dateTime);
		} else if (StringUtils.isNotBlank(oldToken)
				&& CacheManager.hexists(RedisConstant.REDIS_TABLE_TOKEN_KEY, oldToken)) {// 如果用户存在且客户端token存在继续使用
			out.setToken(oldToken);
		} else {// 如果用户存在没有oldToke，创建新的token并返回
			token = CommonUtil.generateUuid() + dateTime;
			out.setToken(token);
			//保存到redis
		}
		BeanUtility.bindObject(out, account);
		//修改登录时间
		accountMapper.updateLoginTime(account.getAccountId()+"",in.getLogInfo());
		AccountTransit at = accountTransitMapper.findByTypeAccountId(account.getAccountId(),
				AccountConstant.TransitTypeQr);
//		out.setQrId(at.getTransitType()+at.getTransitCode());
		out.setQrId(at.getTransitCode());
		out.setHeadPath(imageService.getAccountHeadPath(account.getAccountId()));
		
		
		//获取评价得分 带优化
		//MyselfinfoPojo info = mymapper.queryinfo(account.getAccountId());
		
		
		//获取公司信息
		List<OrgModel> orgList=orgMapper.getOrgByAccId(account.getAccountId());
		List<MyCompany> comList=new ArrayList<MyCompany>();
		for(OrgModel om:orgList){
			MyCompany mc=new MyCompany();
			mc.setCompanyId(om.getOrgId()+"");
			mc.setCompanyName(om.getOrgName()+"");
			comList.add(mc);
		}
		out.setCompanies(comList);
		out.setBillCount("0");
		out.setScore("5.0");
		if(StringUtils.isNotBlank(account.getGender()) && account.getGender().equals("1")){
			out.setSex("男");
		}else  if(StringUtils.isNotBlank(account.getGender()) && account.getGender().equals("0")){
			out.setSex("女");
		}else{
			out.setSex("未设置");
		}
		
		
		//增加当前时间戳
		out.setCurrentTime(String.valueOf(new Date().getTime()));
		
		// TODO:添加菜单列表
		Map<String,String> menuMap=getMenuList02(account.getAccountId(), appCodeName);
		out.setMenus(menuMap.get("menuStr"));
		out.setQrMenus(menuMap.get("qrMenuStr"));
		
		//获取项目个性设置
		//获取账号所在的项目
		List<ComboProjectIdName> pList =projectMapper.selectProjectIdNameByAccountId(new Long(account.getAccountId()));
		//显示位置
		String openShow="";
		//语言
		String language="";
		
		//二维码有效期（s）
		String addSecond="";
		//二维码刷新间隔时间(s)
		String refSecond="";
		
		boolean openShowBoo=false;
		boolean languageBoo=false;
		if(pList!=null && pList.size()>0 ){
			for(int i=0;i<pList.size();i++){
				String profile=projectService.getConfigure(pList.get(i).getProjectID()+"","70403");
				if(StringUtils.isNotBlank(profile)){
					Map<String,Object> map=BeanUtility.jsonStrToObject(profile,new HashMap<String,Object>().getClass(), true);
					if(map.get("openShow") !=null){
						if(StringUtils.isBlank(openShow) ){
							openShow=map.get("openShow").toString();
						}else{
							if(!openShow.equals(map.get("openShow").toString())){
								openShowBoo=true;
							}
						}	
					}
					if(map.get("language") !=null){
						if(StringUtils.isBlank(language)){
							language=map.get("language").toString();
						}else{
							if(!language.equals(map.get("language").toString())){
								languageBoo=true;
							}
						}
					}
					
					//取最小值
					if(map.get("addSecond") !=null){
						if(StringUtils.isBlank(addSecond) || !StringUtils.isNumeric(addSecond)){
							addSecond=map.get("addSecond").toString();
						}else{
							String addSecond2=map.get("addSecond").toString();
							if(StringUtils.isNotBlank(addSecond2) && StringUtils.isNumeric(addSecond2)){
								if(new Integer(addSecond2)<new Integer(addSecond)){
									addSecond=addSecond2;
								}
							}
						}
					}
					if(map.get("refSecond") !=null){
						//取最小值
						if(StringUtils.isBlank(refSecond) || !StringUtils.isNumeric(refSecond)){
							refSecond=map.get("refSecond").toString();
						}else{
							String refSecond2=map.get("refSecond").toString();
							if(StringUtils.isNotBlank(refSecond2) && StringUtils.isNumeric(refSecond2)){
								if(new Integer(refSecond2)<new Integer(refSecond)){
									refSecond=refSecond2;
								}
							}
						}
					}
				}
			}	
		}
		if(openShowBoo ||StringUtils.isBlank(openShow)){
			openShow="02";
		}
		if(languageBoo || StringUtils.isBlank(language)){
			language="15001";
		}
		if(StringUtils.isBlank(addSecond) || !StringUtils.isNumeric(addSecond) ||StringUtils.isBlank(refSecond) 
				|| !StringUtils.isNumeric(refSecond)
				|| new Integer(addSecond) <=new Integer(refSecond)){
			addSecond="60";
			refSecond="30";
		}
		out.setOpenShow(openShow);// 登录跳到二维码页面
		out.setLanguage(language);//设置成中文
		out.setAddSecond(addSecond);//二维码有效期（s）
		out.setRefSecond(refSecond);//二维码刷新间隔时间(s)
		
		
		//获取用户个性配置
		String accountProfile=accountProfileMapper.getAccountProfileByParam(account.getAccountId(),"70201");
		if(StringUtils.isNotBlank(accountProfile)){
			Map<String,Object> map=BeanUtility.jsonStrToObject(accountProfile,new HashMap<String,Object>().getClass(), true);
			out.setLanguage(map.get("language").toString());
		}
		// 生成返回实体json
		String resultData = BeanUtility.objectToJsonStr(out, true);
		// 更缓存中的token
		CacheManager.setHTableKeyVal(RedisConstant.REDIS_TABLE_TOKEN_KEY, token, view.getDateTime());
	
		
		// 登录成功
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	public String inviLogin(ControllerView view, Logger logger, String appCodeName) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 实体内容转为对象
		AccountIn in = BeanUtility.jsonStrToObject(dataJson, AccountIn.class, true);
		String dateTime = String.valueOf(new Date().getTime());
		Account account = accountMapper.queryUser(in.getLoginName(), null);
		AccountOut out = new AccountOut();
		if (account == null) {//
			logger.warn("用户密码或账号错误：" + in.getLoginName());
			return WriteToPage.setResponseMessage("{}", IfConstant.LOGIN_ERROR, dateTime);
		}
		BeanUtility.bindObject(out, account);
		Invitation invitation =invitationMapper.queryInvitationByCode(in.getInvitationCode());
		if(invitation==null){
			return WriteToPage.setResponseMessage("{}", IfConstant.INVITATION_NOT_EXIST);
		}else if(!StatusType.InvitationStatus01.equals(invitation.getStatusCode())) {
			if (StatusType.InvitationStatus02.equals(invitation.getStatusCode())) {
				return WriteToPage.setResponseMessage("{}", IfConstant.INVITATION_USERED);
			}else{
				return WriteToPage.setResponseMessage("{}", IfConstant.INVITATION_TIME_OUT);
			}
		}else{
			//邀请码信息内容
			String contents = invitation.getContents();
			InvitationContent ic = BeanUtility.jsonStrToObject(contents, InvitationContent.class, false);
			String itype = invitation.getInvitationType();
			switch (itype) {
			case InvitationType.InvitationType01://邀请组织管理员
				return WriteToPage.setResponseMessage("{}", IfConstant.INVITATION_NOT_EMP, dateTime);
			case InvitationType.InvitationType02://邀请员工
				this.initEmp(ic, account);
				break;
			case InvitationType.InvitationType03:
	
				break;
			default:
				break;
			}
			Map<String, Object> params = new HashMap<>();
			params.put("status_code", StatusType.InvitationStatus02);
			params.put("status_time", new Date());
			params.put("update_time", new Date());
			params.put("invitation_code", in.getInvitationCode());
			this.invitationMapper.updateInvitationForReg(params );
			// 验证成功
			String token = CommonUtil.generateUuid() + dateTime;
			out.setToken(token);
			AccountTransit at = accountTransitMapper.findByTypeAccountId(account.getAccountId(),
					AccountConstant.TransitTypeQr);
//			out.setQrId(at.getTransitType()+at.getTransitCode());
			out.setQrId(at.getTransitCode());
			out.setHeadPath(imageService.getAccountHeadPath(account.getAccountId()));
			//获取评价得分
			MyselfinfoPojo info = mymapper.queryinfo(account.getAccountId());
			//获取公司信息
			out.setCompanies(mymapper.queryCompanies(account.getAccountId()));
			//TODO: 添加 菜单
			out.setMenus(getMenuList(account.getAccountId(), appCodeName));
			out.setBillCount(info.getBillCount());
			out.setSex(info.getSex());
			out.setScore(info.getScore());
			//增加当前时间戳
			out.setCurrentTime(String.valueOf(new Date().getTime()));
			//保存token到redis
			CacheManager.setHTableKeyVal(RedisConstant.REDIS_TABLE_TOKEN_KEY, token, view.getDateTime());
			String resultData = BeanUtility.objectToJsonStr(out, true);
			synTransit(view);
			return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
		}
	}
	private void initEmp(InvitationContent ic, Account account) {
		String accountId = account.getAccountId();
		String empId = ic.getEmpId();
		Employee emp = new Employee();
		emp.setEmpId(Long.parseLong(empId));
		emp.setAccountId(accountId+"");
		emp.setUpdateTime(new Date());
		this.empMapper.update(emp);
	}
	public String checkAccountExist(ControllerView view, Logger logger) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 实体内容转为对象
		AccountIn in = BeanUtility.jsonStrToObject(dataJson, AccountIn.class, true);
		// 参数时间戳
		String dateTime = String.valueOf(new Date().getTime());
		// 设置登录令牌信息
//		String oldToken = in.getOldToken();
//		String token = oldToken;
		// 登录用户验证
		Account account = accountMapper.queryUser(in.getLoginName(), null);
		if (account == null) {//
			logger.warn("用户名不存在:" + in.getLoginName());
			return WriteToPage.setResponseMessage("{}", IfConstant.LOGINNAME_NOT_EXIST, dateTime);
		} 
		AccountOut out = new AccountOut();
		BeanUtility.bindObject(out, account);
		String resultData = BeanUtility.objectToJsonStr(out, true);
		// 登录成功
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	/**
	 * 邀请码登录
	 * @param view
	 * @param logger
	 * @return
	 * @throws Exception 
	 */
	public String registerUser(ControllerView view, Logger logger, String appCodeName) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 实体内容转为对象
		AccountIn in = BeanUtility.jsonStrToObject(dataJson, AccountIn.class, true);
		String dateTime = String.valueOf(new Date().getTime());
//		String regEx="^[A-Z,a-z,0-9]*$";
//		boolean result=Pattern.compile(regEx).matcher(in.getLoginName()).find();
//		if (!result) {
//			return WriteToPage.setResponseMessage("{}", IfConstant.LOGINNAME_FORMAT_ERROR);
//		}
		if (StringUtils.isNotBlank(in.getPwd()) && in.getPwd().length()<8) {
			return WriteToPage.setResponseMessage("{}", IfConstant.LOGINNAME_EXISTED);
		}
		Account a = accountMapper.queryUser(in.getLoginName(), null);
		if (a != null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.LOGINNAME_EXISTED);
		}
		Invitation invitation =invitationMapper.queryInvitationByCode(in.getInvitationCode());
		if(invitation==null){
			return WriteToPage.setResponseMessage("{}", IfConstant.INVITATION_NOT_EXIST);
		}else if(!StatusType.InvitationStatus01.equals(invitation.getStatusCode())) {
			if (StatusType.InvitationStatus02.equals(invitation.getStatusCode())) {
				return WriteToPage.setResponseMessage("{}", IfConstant.INVITATION_USERED);
			}else{
				return WriteToPage.setResponseMessage("{}", IfConstant.INVITATION_TIME_OUT);
			}
		}else{
			AccountOut out = new AccountOut();
			Account account = new Account();
			long accountId = this.getIdWorker().nextId();
			account.setAccountId(accountId+"");
			account.setAccountCode(Integer.parseInt(GenerateCreater.getAccountId()));
			account.setAppId(APP_CUSTOMER_ID);
			account.setAuthenum(AccountConstant.AuthEnum1);
			account.setRoles(AccountConstant.Role2+"");
			account.setCreateTime(new Date());
			account.setStatusCode(StatusType.AccountStatus02);
			account.setStatusTime(new Date());
			account.setMobilephone(invitation.getMobilephone());
			account.setPassword(in.getPwd());
			//account.setLoginId(in.getLoginName());
			account.setLoginName(in.getLoginName());
			account.setStatusTime(new Date());
			account.setCreateTime(new Date());
			int i = this.accountMapper.saveAccount(account);
			if (i<=0) {
				return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
			}
			AccountTransit at = new AccountTransit();
			long qrId = this.getIdWorker().nextId();
			at.setTransitId(qrId);
			at.setTransitType(AccountConstant.TransitTypeQr);
			at.setTransitCode(HexUtil.intToHex(Integer.parseInt(GenerateCreater.getTransitCode()), 4));
			at.setAccountId(accountId);
			at.setStatusCode(AccountConstant.TransitStatus01);
			at.setStatusTime(new Date());
			at.setCreateTime(new Date());
			accountTransitMapper.insert(at);
			//邀请码信息内容
			String contents = invitation.getContents();
			InvitationContent ic = BeanUtility.jsonStrToObject(contents, InvitationContent.class, false);
			String itype = invitation.getInvitationType();
			switch (itype) {
			case InvitationType.InvitationType01://邀请组织管理员
				return WriteToPage.setResponseMessage("{}", IfConstant.INVITATION_NOT_EMP, dateTime);
			case InvitationType.InvitationType02://邀请员工
				this.initEmp(ic, account);
				break;
			case InvitationType.InvitationType03:
	
				break;
			default:
				break;
			}
			Map<String, Object> params = new HashMap<>();
			params.put("status_code", StatusType.InvitationStatus02);
			params.put("status_time", new Date());
			params.put("update_time", new Date());
			params.put("invitation_code", in.getInvitationCode());
			this.invitationMapper.updateInvitationForReg(params );
			// 验证成功
			String token = CommonUtil.generateUuid() + dateTime;
			BeanUtility.bindObject(out, account);
			out.setToken(token);
//			out.setQrId("00"+qrId);//00为二维码
			out.setQrId(at.getTransitCode());
			out.setHeadPath(imageService.getAccountHeadPath(account.getAccountId()));
			//获取评价得分
			MyselfinfoPojo info = mymapper.queryinfo(accountId+"");
			//获取公司信息
			out.setCompanies(mymapper.queryCompanies(accountId+""));
			out.setMenus(getMenuList(accountId + "", appCodeName));
			out.setBillCount(info.getBillCount());
			out.setSex(info.getSex());
			out.setScore(info.getScore());
			//增加当前时间戳
			out.setCurrentTime(String.valueOf(new Date().getTime()));
			//修改登录时间
			accountMapper.updateLoginTime(account.getAccountId()+"",in.getLogInfo());
			//保存token到redis
			CacheManager.setHTableKeyVal(RedisConstant.REDIS_TABLE_TOKEN_KEY, token, view.getDateTime());
			String resultData = BeanUtility.objectToJsonStr(out, true);
			synTransit(view);
			return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
		}
	}

	public String inviLoginNext(ControllerView view, Logger logger, String appCodeName) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 实体内容转为对象
		AccountIn in = BeanUtility.jsonStrToObject(dataJson, AccountIn.class, true);
		String dateTime = String.valueOf(new Date().getTime());
		Invitation invitation = invitationMapper.queryInvitationByCode(in.getInvitationCode());
		if(invitation==null){
			return WriteToPage.setResponseMessage("{}", IfConstant.INVITATION_NOT_EXIST);
		}else if(!StatusType.InvitationStatus01.equals(invitation.getStatusCode())) {
			if (StatusType.InvitationStatus02.equals(invitation.getStatusCode())) {
				return WriteToPage.setResponseMessage("{}", IfConstant.INVITATION_USERED);
			}else{
				return WriteToPage.setResponseMessage("{}", IfConstant.INVITATION_TIME_OUT);
			}
		}else{
			//邀请码信息内容
			String itype = invitation.getInvitationType();
			if(InvitationType.InvitationType01.equals(itype))//邀请组织管理员
				return WriteToPage.setResponseMessage("{}", IfConstant.INVITATION_NOT_EMP, dateTime);
		}
		// 获得手机识别码
		String sn = in.getSn();
		String mobilenu = in.getPhone();
		String codeTemp = in.getContent();
		if (StringUtils.isBlank(sn) || StringUtils.isBlank(mobilenu) || StringUtils.isBlank(codeTemp)
				) {
			logger.warn("queryMsgForSendMsg参数sn|num|code为空");
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		// 返回验证码
		Object codeObj = CommonMobileMsgSend.getSendMobileCode(sn + mobilenu);
		if (codeObj == null) {
			logger.warn("queryMsgForSendMsg缓存验证码过期");
			return WriteToPage.setResponseMessage("{}", IfConstant.MOBILE_CODE_UNUSED, dateTime);
		}
		String[] codeStrArray = codeObj.toString().split(",");
		String code = codeStrArray[0];
		if (!codeTemp.equals(code)) {
			logger.warn("queryMsgForSendMsg缓存验证码匹配错误");
			return WriteToPage.setResponseMessage("{}", IfConstant.MOBILE_CODE_UNRIGHT, dateTime);
		}
		//判断用户是否存在
		Account account = accountMapper.queryUser(in.getPhone(), null);
		if (account == null) {//
			logger.warn("用户名不存在:" + in.getLoginName());
			String empId = invitation.getSourceId()+"";
			Employee emp = this.empMapper.findByPrimaryKey(empId);
			return WriteToPage.setResponseMessage(emp.getEmpName(), IfConstant.LOGINNAME_NOT_EXIST, dateTime);
		} 
		AccountOut out = new AccountOut();
		BeanUtility.bindObject(out, account);
		AccountTransit at = accountTransitMapper.findByTypeAccountId(account.getAccountId(),
				AccountConstant.TransitTypeQr);
		out.setQrId(at.getTransitCode());
//		out.setQrId(at.getTransitType()+at.getTransitCode());
		out.setHeadPath(imageService.getAccountHeadPath(account.getAccountId()));
		//获取评价得分
		MyselfinfoPojo info = mymapper.queryinfo(account.getAccountId());
		//获取公司信息
		out.setCompanies(mymapper.queryCompanies(account.getAccountId()));
		out.setMenus(getMenuList(account.getAccountId(), appCodeName));
		out.setBillCount(info.getBillCount());
		out.setSex(info.getSex());
		out.setScore(info.getScore());
		//增加当前时间戳
		out.setCurrentTime(String.valueOf(new Date().getTime()));
		String resultData = BeanUtility.objectToJsonStr(out, true);
		// 登录成功
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	/**
	 * 邀请码登录  该用户没有在其它机构下
	 * @param regView
	 * @throws Exception
	 */
	public void synTransit(ControllerView view) throws Exception {
		String dataJson = view.getData();
		AccountIn in = BeanUtility.jsonStrToObject(dataJson, AccountIn.class, true);
		Invitation invitation = invitationMapper.queryInvitationByCode(in.getInvitationCode());
		String contents = invitation.getContents();
		InvitationContent ic = BeanUtility.jsonStrToObject(contents, InvitationContent.class, false);
		insertTransitList(ic);
	}
	private void insertTransitList(InvitationContent ic) throws Exception {
		/**
		 * 帐号下的通行方式
		 * 机构对应项目下的租赁的公共节点
		 */
		Employee employee = new Employee();
		employee.setEmpId(Long.parseLong(ic.getEmpId()));
		List<Employee> empList = empMapper.queryDynamic(employee);
		List<RegionalEmpRelationModel> regEmpList = new ArrayList<RegionalEmpRelationModel>();
		RegionalEmpRelationModel regEmp = null;

		for(Employee emp : empList){
			regEmp = new RegionalEmpRelationModel();
			regEmp.setEmp_id(emp.getEmpId());
			regEmpList.add(regEmp);
		}
		Project p =projectMapper.getProjectByEmpId(ic.getEmpId());
		//Employee emp = empMapper.findByPrimaryKey(ic.getEmpId());
		List<ResourceModel> resourceList = regionalMapper.getOrgResourceByOrgId(empList.get(0).getOrgId());
		List<RegionalArrangeRelationModel> rar = new ArrayList<>();
		for(ResourceModel res : resourceList){
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
		} 
		List<Device> divices = new ArrayList<>();
		List<Account> accounts = new ArrayList<>();
		if(rar.size()>0){
			divices = regionalMapper.getPublicDevicebyProjectId(p);
			divices.addAll(regionalMapper.getDeviceByArrangeIds(rar));
		}/*else{
			OrgModel org = orgMapper.getOrgById(employee.getOrgId()+"");
			if("0301".equals(org.getDutyType())){
				//查找所有的租住公共区节点下的设备
				divices = regionalMapper.getDevicebyProjectId(p);
				accounts = regionalMapper.getAccontByEmpId3(regEmpList);
			}else{
				accounts = regionalMapper.getAccontByEmpId2(regEmpList);
			}
		}*/
		accounts = regionalMapper.getAccontByEmpId2(regEmpList);
		if(accounts.size()==0){
			return ;
		}
		List<TransitModel> oldTransits = new ArrayList<TransitModel>();
		if(accounts.size()>0 && divices.size()>0){
			List<TransitModel> transitsList = regionalMapper.getTransitListByDeviceAccId2(divices,accounts.get(0),p.getProjectId());
			if(transitsList.size()>0){
				throw new TransitListException();
			}
			oldTransits = regionalMapper.getTransitByDeviceAccId2(divices,accounts.get(0),p.getProjectId());
		}
		IdWorker iw = this.getIdWorker();
		List<TransitModel> newTransits = new ArrayList<TransitModel>();
		for(Device d : divices){
			String temp = "01";//标志是否循环
			for(Account acc : accounts){
				for(TransitModel transits : oldTransits){
					if(transits.getDevice_code().equals(d.getDeviceCode()) && transits.getTransit_code().equals(acc.getTransit_code())){
						int compareDateStr = DateUtil.compareDateStr(transits.getEnd_date(), acc.getEnd_date(), "yyyy-MM-dd");
						if(compareDateStr>0){//如果结果表里的结束日期大
							temp = "03";
						}else{
							temp = "02";//更新
						}
						break;
					}
				}
				if("03".equals(temp)){
					temp = "01";
					continue;
				}
				TransitModel t = new TransitModel();
				t.setList_id(iw.nextId());
				t.setAccount_code(Integer.parseInt(acc.getAccountCode()+""));
				t.setProject_id(p.getProjectId());
				t.setDevice_code(d.getDeviceCode());
				t.setTransit_code(acc.getTransit_code());//四种通行类型
				t.setTransit_type(acc.getTransit_type());
				t.setBegin_date(acc.getBegin_date());
				t.setEnd_date(acc.getEnd_date());
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
		if(newTransits.size()>0){
				Set set = new HashSet(newTransits);
				newTransits.clear(); 
				newTransits = new ArrayList(set);    
			regionalMapper.createTransitList(newTransits);
		}

	}

	public List<Account> getAccountListByList(List<Long> accountIdList) {
		return accountMapper.getAccountListByList(accountIdList);
	}

	/*public String quertyProject(String code) {
		Project p = accountMapper.quertyProject(code);
		return WriteToPage.setResponseMessage(p, IfConstant.SERVER_SUCCESS);
	}*/
	/**
	 * 检查访客机登录
	 * @param name
	 * @param password
	 * @return
	 * @throws Exception 
	 */
	public String checMechinekUser(String name, String password,String mackAddress) throws Exception {
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
		TerminalPojo transit= visitorApplyMapper.findTransitById(mackAddress);
		if(null == transit){
			return WriteToPage.setResponseMessageForError("没有此访客机对应的项目！", IfConstant.UNKNOWN_ERROR.getCode());
		}
		//查询是否有物管
		//查询权限
		List<Employee> empList = accountMapper.checkPower(account.getAccountId());
		if(empList.size()==0){
			return WriteToPage.setResponseMessage("此用户没有权限！", IfConstant.NO_PERMISSIONS);
		}
		List<Long> orgList = new ArrayList<Long>();
		boolean power = false;
		List<Employee> empRoles = new ArrayList<Employee>();//查询自定义职务
		List<OrgProjectRelation> orgListByPro= visitorApplyMapper.findOrgListByPId(transit.getProject_id());
		for(Iterator<OrgProjectRelation> iterator = orgListByPro.iterator();iterator.hasNext();){
			OrgProjectRelation next = iterator.next();
			for(Employee emp : empList) {
				if(next.getOrgId().equals(emp.getOrgId())){
					orgList.add(emp.getOrgId());
					empRoles.add(emp);
					if((emp.getRoles()&32)>0 || (emp.getRoles()&64)>0){
						power = true;
					}
				}
			}
			
		}
		if(orgList.size()==0){
			return WriteToPage.setResponseMessageForError("此用户不是物业人员！", IfConstant.UNKNOWN_ERROR.getCode());
		}
		List<Project> p = accountMapper.getProjects(orgList);
		for(Iterator<Project> iterator = p.iterator() ;iterator.hasNext();){
			Project next = iterator.next();
			if((next.getRoles()&1024)==0 || (next.getRoles()&1024)<0 || next.getProjectId()!=transit.getProject_id()){
				iterator.remove();
			}
		}
		if(p.size()==0){
			return WriteToPage.setResponseMessageForError("此用户不是物业人员！", IfConstant.UNKNOWN_ERROR.getCode());
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("project", p);
		map.put("projectId",transit.getProject_id());
		map.put("transit", transit);
		map.put("accountId", account.getAccountId());
		map.put("bindIcRole", "0");//TODO  用户数据权限选写死
		map.put("visitorRole", "0");
		if(power){
			map.put("managerRole", "1");
		}else{
			map.put("managerRole", "0");
		}
		if(empRoles.size()>0){//说明有权限  不是普通用户
			//查询自定义职务
			Project project = p.get(0);
			if(null != empRoles.get(0).getEprRoles()){
				List<RoleModel> roleList = accountMapper.getRolePermissions(Long.parseLong(empRoles.get(0).getEprRoles()),project.getOrg_id());
				for(RoleModel roleM : roleList){
					if((roleM.getPermissions()&65536)>0){
						map.put("bindIcRole", "1");
					}
					if((roleM.getPermissions()&131072)>0){
						map.put("visitorRole", "1");
					}
				}
			}
			
			
		}
		return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStr(map, true), IfConstant.SERVER_SUCCESS,null);
		}

	public String refreshAccount(ControllerView view, String appCodeName) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 实体内容转为对象
		AccountIn in = BeanUtility.jsonStrToObject(dataJson, AccountIn.class, true);
		String dateTime = String.valueOf(new Date().getTime());
		if (StringUtils.isBlank(in.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		Account account = accountMapper.queryUserById(in.getAccountId());
		AccountOut out = new AccountOut();
		BeanUtility.bindObject(out, account);
		AccountTransit at = accountTransitMapper.findByTypeAccountId(account.getAccountId(),
				AccountConstant.TransitTypeQr);
//		out.setQrId(at.getTransitType()+at.getTransitCode());
		out.setQrId(at.getTransitCode());
		out.setHeadPath(imageService.getAccountHeadPath(account.getAccountId()));
		//获取评价得分
		MyselfinfoPojo info = mymapper.queryinfo(account.getAccountId());
		//获取公司信息
		out.setCompanies(mymapper.queryCompanies(account.getAccountId()));
		out.setBillCount(info.getBillCount());
		out.setSex(info.getSex());
		out.setScore(info.getScore());
		//增加当前时间戳
		out.setCurrentTime(String.valueOf(new Date().getTime()));
		// 更缓存中的token
		String token = CommonUtil.generateUuid() + dateTime;
		
		out.setToken(token);
		//修改登录时间
		accountMapper.updateLoginTime(in.getAccountId()+"",in.getLogInfo());
		CacheManager.setHTableKeyVal(RedisConstant.REDIS_TABLE_TOKEN_KEY, token, view.getDateTime());
		// TODO: 添加 菜单字段
		out.setMenus(getMenuList(account.getAccountId(), appCodeName));
		
		
		// 生成返回实体json
		String resultData = BeanUtility.objectToJsonStr(out, true);
		
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	
	
	
	public String refreshAccount02(ControllerView view, String appCodeName) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 实体内容转为对象
		AccountIn in = BeanUtility.jsonStrToObject(dataJson, AccountIn.class, true);
		String dateTime = String.valueOf(new Date().getTime());
		if (StringUtils.isBlank(in.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		Account account = accountMapper.queryUserById(in.getAccountId());
		if (account==null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.LOGINNAME_NOT_EXIST, dateTime);
		}
		
		AccountOut out = new AccountOut();
		BeanUtility.bindObject(out, account);
		AccountTransit at = accountTransitMapper.findByTypeAccountId(account.getAccountId(),
				AccountConstant.TransitTypeQr);
//		out.setQrId(at.getTransitType()+at.getTransitCode());
		out.setQrId(at.getTransitCode());
		out.setHeadPath(imageService.getAccountHeadPath(account.getAccountId()));
		//获取评价得分
		MyselfinfoPojo info = mymapper.queryinfo(account.getAccountId());
		
		//获取公司信息
		//获取公司信息
		List<OrgModel> orgList=orgMapper.getOrgByAccId(account.getAccountId());
		List<MyCompany> comList=new ArrayList<MyCompany>();
		for(OrgModel om:orgList){
			MyCompany mc=new MyCompany();
			mc.setCompanyId(om.getOrgId()+"");
			mc.setCompanyName(om.getOrgName()+"");
			comList.add(mc);
		}
		out.setCompanies(comList);
		
		
		out.setBillCount(info.getBillCount());
		out.setSex(info.getSex());
		out.setScore(info.getScore());
		//增加当前时间戳
		out.setCurrentTime(String.valueOf(new Date().getTime()));
		// 更缓存中的token
		String token = CommonUtil.generateUuid() + dateTime;
		
		out.setToken(token);
		//修改登录时间
		accountMapper.updateLoginTime(in.getAccountId()+"",in.getLogInfo());
		CacheManager.setHTableKeyVal(RedisConstant.REDIS_TABLE_TOKEN_KEY, token, view.getDateTime());
		
		
		// TODO:添加菜单列表
		Map<String,String> menuMap=getMenuList02(account.getAccountId(), appCodeName);
		out.setMenus(menuMap.get("menuStr"));
		out.setQrMenus(menuMap.get("qrMenuStr"));
		
		
		
		//获取项目个性设置
		//获取账号所在的项目
		List<ComboProjectIdName> pList =projectMapper.selectProjectIdNameByAccountId(new Long(account.getAccountId()));
		//显示位置
		String openShow="";
				//语言
				String language="";
				
				//二维码有效期（s）
				String addSecond="";
				//二维码刷新间隔时间(s)
				String refSecond="";
				
				boolean openShowBoo=false;
				boolean languageBoo=false;
				if(pList!=null && pList.size()>0 ){
					for(int i=0;i<pList.size();i++){
						String profile=projectService.getConfigure(pList.get(i).getProjectID()+"","70403");
						if(StringUtils.isNotBlank(profile)){
							Map<String,Object> map=BeanUtility.jsonStrToObject(profile,new HashMap<String,Object>().getClass(), true);
							if(map.get("openShow") !=null){
								if(StringUtils.isBlank(openShow) ){
									openShow=map.get("openShow").toString();
								}else{
									if(!openShow.equals(map.get("openShow").toString())){
										openShowBoo=true;
									}
								}	
							}
							if(map.get("language") !=null){
								if(StringUtils.isBlank(language)){
									language=map.get("language").toString();
								}else{
									if(!language.equals(map.get("language").toString())){
										languageBoo=true;
									}
								}
							}
							
							//取最小值
							if(map.get("addSecond") !=null){
								if(StringUtils.isBlank(addSecond) || !StringUtils.isNumeric(addSecond)){
									addSecond=map.get("addSecond").toString();
								}else{
									String addSecond2=map.get("addSecond").toString();
									if(StringUtils.isNotBlank(addSecond2) && StringUtils.isNumeric(addSecond2)){
										if(new Integer(addSecond2)<new Integer(addSecond)){
											addSecond=addSecond2;
										}
									}
								}
							}
							if(map.get("refSecond") !=null){
								//取最小值
								if(StringUtils.isBlank(refSecond) || !StringUtils.isNumeric(refSecond)){
									refSecond=map.get("refSecond").toString();
								}else{
									String refSecond2=map.get("refSecond").toString();
									if(StringUtils.isNotBlank(refSecond2) && StringUtils.isNumeric(refSecond2)){
										if(new Integer(refSecond2)<new Integer(refSecond)){
											refSecond=refSecond2;
										}
									}
								}
							}
						}
					}	
				}
				if(openShowBoo ||StringUtils.isBlank(openShow)){
					openShow="02";
				}
				if(languageBoo || StringUtils.isBlank(language)){
					language="15001";
				}
				if(StringUtils.isBlank(addSecond) || !StringUtils.isNumeric(addSecond) ||StringUtils.isBlank(refSecond) 
						|| !StringUtils.isNumeric(refSecond)
						|| new Integer(addSecond) <=new Integer(refSecond)){
					addSecond="60";
					refSecond="30";
				}
				out.setOpenShow(openShow);// 登录跳到二维码页面
				out.setLanguage(language);//设置成中文
				out.setAddSecond(addSecond);//二维码有效期（s）
				out.setRefSecond(refSecond);//二维码刷新间隔时间(s)
				
				
				//获取用户个性配置
				String accountProfile=accountProfileMapper.getAccountProfileByParam(account.getAccountId(),"70201");
				if(StringUtils.isNotBlank(accountProfile)){
					Map<String,Object> map=BeanUtility.jsonStrToObject(accountProfile,new HashMap<String,Object>().getClass(), true);
					out.setLanguage(map.get("language").toString());
				}
		
		// 生成返回实体json
		String resultData = BeanUtility.objectToJsonStr(out, true);
		
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	
	public String getMenuList(String accountId, String appCodeName){
		if(accountMapper.queryByAccountId(accountId) == 0){
			return "[{\"title\":\"智能门禁\",\"code\":\"10008\",\"menus\":[{\"title\":\"智能门禁\",\"type\":\"0\",\"code\":\"10009\",\"url\":\"\"},{\"title\":\"访客管理\",\"type\":\"0\",\"code\":\"10010\",\"url\":\"\"},{\"title\":\"通行区域\",\"type\":\"0\",\"code\":\"10011\",\"url\":\"\"},{\"title\":\"通行日志\",\"type\":\"0\",\"code\":\"10012\",\"url\":\"\"}]},{\"title\":\"物业服务\",\"code\":\"10013\",\"menus\":[{\"title\":\"物业服务\",\"type\":\"0\",\"code\":\"10014\",\"url\":\"\"},{\"title\":\"物业公告\",\"type\":\"0\",\"code\":\"10015\",\"url\":\"\"},{\"title\":\"服务投诉\",\"type\":\"0\",\"code\":\"10016\",\"url\":\"\"},{\"title\":\"智能停车\",\"type\":\"0\",\"code\":\"com.txrz\",\"url\":\"\"}]},{\"title\":\"经营服务\",\"code\":\"10017\",\"menus\":[{\"title\":\"账单管理\",\"type\":\"0\",\"code\":\"10018\",\"url\":\"\"},{\"title\":\"会议室预定\",\"type\":\"0\",\"code\":\"10019\",\"url\":\"\"},{\"title\":\"工位预定\",\"type\":\"0\",\"code\":\"10020\",\"url\":\"\"},{\"title\":\"节能管理\",\"type\":\"0\",\"code\":\"10021\",\"url\":\"\"}]},{\"title\":\"企业服务\",\"code\":\"10022\",\"menus\":[{\"title\":\"开业啦\",\"type\":\"0\",\"code\":\"10023\",\"url\":\"\"},{\"title\":\"悦管家\",\"type\":\"0\",\"code\":\"10025\",\"url\":\"\"}]}]";
		}
		int count = rAppAccountPushMapper.selectCountByAppCodeNameAndAccountId(accountId, appCodeName);
		if(count == 0) {
			return "[{\"title\":\"包办管家\",\"code\":\"10001\",\"menus\":[{\"title\":\"工单管理\",\"type\":\"0\",\"code\":\"10024\",\"url\":\"\"},{\"title\":\"安防分析\",\"type\":\"0\",\"code\":\"10101\",\"url\":\"\"},{\"title\":\"运营分析\",\"type\":\"0\",\"code\":\"10002\",\"url\":\"\"},{\"title\":\"设备巡检\",\"type\":\"0\",\"code\":\"10100\",\"url\":\"\"},{\"title\":\"投诉建议\",\"type\":\"0\",\"code\":\"10003\",\"url\":\"\"},{\"title\":\"招商营销\",\"type\":\"0\",\"code\":\"10004\",\"url\":\"\"},{\"title\":\"营运管理\",\"type\":\"0\",\"code\":\"10005\",\"url\":\"\"},{\"title\":\"商动联办\",\"type\":\"0\",\"code\":\"10006\",\"url\":\"\"},{\"title\":\"能源管理\",\"type\":\"0\",\"code\":\"10007\",\"url\":\"\"}]},{\"title\":\"智能门禁\",\"code\":\"10008\",\"menus\":[{\"title\":\"智能门禁\",\"type\":\"0\",\"code\":\"10009\",\"url\":\"\"},{\"title\":\"访客管理\",\"type\":\"0\",\"code\":\"10010\",\"url\":\"\"},{\"title\":\"通行区域\",\"type\":\"0\",\"code\":\"10011\",\"url\":\"\"},{\"title\":\"通行日志\",\"type\":\"0\",\"code\":\"10012\",\"url\":\"\"}]},{\"title\":\"物业服务\",\"code\":\"10013\",\"menus\":[{\"title\":\"物业服务\",\"type\":\"0\",\"code\":\"10014\",\"url\":\"\"},{\"title\":\"物业公告\",\"type\":\"0\",\"code\":\"10015\",\"url\":\"\"},{\"title\":\"服务投诉\",\"type\":\"0\",\"code\":\"10016\",\"url\":\"\"},{\"title\":\"智能停车\",\"type\":\"0\",\"code\":\"com.txrz\",\"url\":\"\"}]},{\"title\":\"经营服务\",\"code\":\"10017\",\"menus\":[{\"title\":\"账单管理\",\"type\":\"0\",\"code\":\"10018\",\"url\":\"\"},{\"title\":\"会议室预定\",\"type\":\"0\",\"code\":\"10019\",\"url\":\"\"},{\"title\":\"工位预定\",\"type\":\"0\",\"code\":\"10020\",\"url\":\"\"},{\"title\":\"节能管理\",\"type\":\"0\",\"code\":\"10021\",\"url\":\"\"}]},{\"title\":\"企业服务\",\"code\":\"10022\",\"menus\":[{\"title\":\"开业啦\",\"type\":\"0\",\"code\":\"10023\",\"url\":\"\"},{\"title\":\"悦管家\",\"type\":\"0\",\"code\":\"10025\",\"url\":\"\"}]}]";
		}
		return "[{\"title\":\"系统运维\",\"code\":\"10026\",\"menus\":[{\"title\":\"设备巡检\",\"type\":\"0\",\"code\":\"10100\",\"url\":\"\"},{\"title\":\"名单运维\",\"type\":\"0\",\"code\":\"10127\",\"url\":\"\"},{\"title\":\"运维通知\",\"type\":\"0\",\"code\":\"10128\",\"url\":\"\"}]},{\"title\":\"包办管家\",\"code\":\"10001\",\"menus\":[{\"title\":\"工单管理\",\"type\":\"0\",\"code\":\"10024\",\"url\":\"\"},{\"title\":\"安防分析\",\"type\":\"0\",\"code\":\"10101\",\"url\":\"\"},{\"title\":\"运营分析\",\"type\":\"0\",\"code\":\"10002\",\"url\":\"\"},{\"title\":\"投诉建议\",\"type\":\"0\",\"code\":\"10003\",\"url\":\"\"},{\"title\":\"招商营销\",\"type\":\"0\",\"code\":\"10004\",\"url\":\"\"},{\"title\":\"营运管理\",\"type\":\"0\",\"code\":\"10005\",\"url\":\"\"},{\"title\":\"商动联办\",\"type\":\"0\",\"code\":\"10006\",\"url\":\"\"},{\"title\":\"能源管理\",\"type\":\"0\",\"code\":\"10007\",\"url\":\"\"}]},{\"title\":\"智能门禁\",\"code\":\"10008\",\"menus\":[{\"title\":\"智能门禁\",\"type\":\"0\",\"code\":\"10009\",\"url\":\"\"},{\"title\":\"访客管理\",\"type\":\"0\",\"code\":\"10010\",\"url\":\"\"},{\"title\":\"通行区域\",\"type\":\"0\",\"code\":\"10011\",\"url\":\"\"},{\"title\":\"通行日志\",\"type\":\"0\",\"code\":\"10012\",\"url\":\"\"}]},{\"title\":\"物业服务\",\"code\":\"10013\",\"menus\":[{\"title\":\"物业服务\",\"type\":\"0\",\"code\":\"10014\",\"url\":\"\"},{\"title\":\"物业公告\",\"type\":\"0\",\"code\":\"10015\",\"url\":\"\"},{\"title\":\"服务投诉\",\"type\":\"0\",\"code\":\"10016\",\"url\":\"\"},{\"title\":\"智能停车\",\"type\":\"0\",\"code\":\"com.txrz\",\"url\":\"\"}]},{\"title\":\"经营服务\",\"code\":\"10017\",\"menus\":[{\"title\":\"账单管理\",\"type\":\"0\",\"code\":\"10018\",\"url\":\"\"},{\"title\":\"会议室预定\",\"type\":\"0\",\"code\":\"10019\",\"url\":\"\"},{\"title\":\"工位预定\",\"type\":\"0\",\"code\":\"10020\",\"url\":\"\"},{\"title\":\"节能管理\",\"type\":\"0\",\"code\":\"10021\",\"url\":\"\"}]},{\"title\":\"企业服务\",\"code\":\"10022\",\"menus\":[{\"title\":\"开业啦\",\"type\":\"0\",\"code\":\"10023\",\"url\":\"\"},{\"title\":\"悦管家\",\"type\":\"0\",\"code\":\"10025\",\"url\":\"\"}]}]";		
	
	
		
		
	}

	public Map<String,String> getMenuList02(String accountId, String appCodeName) throws AppException {
		String menuStr="";
		String qrMenuStr="";
		Map<String,String> resultMap=new HashMap<String,String>();
		//查询账号所在的项目
		List<ComboProjectIdName> pList =projectMapper.selectProjectIdNameByAccountId(new Long(accountId));
		List<String> projectIds=new ArrayList<String>();
		for(ComboProjectIdName pro:pList){
			projectIds.add(pro.getProjectID()+"");
		}
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("projectIds",projectIds);
		params.put("type","70404");
		//查询项目配置信息
		List<Map<String,String>> profileList=projectMapper.getProfileByParam(params);
		//项目配置的菜单Id 集合
		List<String> menuIds=new ArrayList<>();
		
		RAppAccountPushModel raap=new RAppAccountPushModel();
		raap.setAppCodeName(appCodeName);
		raap.setAccountId(accountId);
		//查找账号配置菜单
		List<RAppAccountPushModel> raapList=rAppAccountPushMapper.getRAppByAccountId(raap);
		if(raapList!=null && raapList.size()>0 && raapList.get(0)!=null && StringUtils.isNotBlank(raapList.get(0).getMenus())){
			String [] menus=raapList.get(0).getMenus().split(",");
			for(int i=0;i<menus.length;i++){
				if(!menuIds.contains(menus[i])){
					menuIds.add(menus[i]);
				}
			}
		}
		//首页展示的菜单Map
		Map<String,String> homePage=new HashMap<String,String>();
		//首页展示的菜单Map
		List<String> qrMenuIds=new ArrayList<>();
		//遍历项目配置信息
		for(Map profile:profileList){
			if(StringUtils.isNotBlank(profile.get("profile")+"")){
				Map<String,String> map=BeanUtility.jsonStrToObject(profile.get("profile")+"",new HashMap<String,Object>().getClass(), true);
				if(StringUtils.isNotBlank(map.get("menu"))){
					//配置的菜单数组
					String [] menu=map.get("menu").split(",");
					//菜单去重
					for(int i=0;i<menu.length;i++){
						if(!menuIds.contains(menu[i])){
							menuIds.add(menu[i]);
						}
					}
				}
				if(StringUtils.isNotBlank(map.get("homePage"))){
					//配置的首页展示的菜单数组
					String [] home=map.get("homePage").split(",");
					//首页菜单去重
					for(int i=0;i<home.length;i++){
						if(StringUtils.isBlank(homePage.get(home[i]))){
							homePage.put(home[i], home[i]);
						}
					}
				}
				if(StringUtils.isNotBlank(map.get("qrPage"))){
					//配置的二维码展示的菜单数组
					String [] qr=map.get("qrPage").split(",");
					//二维码菜单去重
					for(int i=0;i<qr.length;i++){
						if(!qrMenuIds.contains(qr[i])){
							qrMenuIds.add(qr[i]);
						}
					}
				}
				
			}
		}
		
		if(menuIds.size()>0){
			// 获取菜单
			List<MenuitemModel> menuList=menuMapper.getListByIdList(menuIds);
			//返回结果
			List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
			// 判断是否是管理组织
			int isAdmin=accountMapper.queryByAccountId(accountId);
			// 判断是不是运维人员
			int count = rAppAccountPushMapper.selectCountByAppCodeNameAndAccountId(accountId, appCodeName);
			// 父菜单集合
			List<MenuitemModel> supperList=new ArrayList<MenuitemModel>();
			// 子菜单集合
			List<MenuitemModel> sonList=new ArrayList<MenuitemModel>();
			for(MenuitemModel mm:menuList){
				if(isAdmin== 0){//普通组织
					if(mm.getMenuId().equals("201") || mm.getSuperId().equals("201")
							|| mm.getMenuId().equals("202") || mm.getSuperId().equals("202")){
						continue;
					}
				}else{
					if(count == 0) {//管理组织
						if(mm.getMenuId().equals("201") || mm.getSuperId().equals("201")){
							continue;
						}
					}
				}
			
				if(StringUtils.isBlank(mm.getSuperId())){
					supperList.add(mm);
				}else{
					sonList.add(mm);
				}
			}
			for(MenuitemModel supMM:supperList){
				// 父菜单map
				Map<String,Object> supMap=new HashMap<String,Object>();
				supMap.put("title",supMM.getMenuText());
				supMap.put("code",supMM.getMenuId());
				List<Map<String,String>> menus=new ArrayList<Map<String,String>>();
				for(MenuitemModel sonMM:sonList){
					if(sonMM.getSuperId().equals(supMM.getMenuId())){
						Map<String,String> sonMap=new HashMap<String,String>();
						sonMap.put("title",sonMM.getMenuText());
						sonMap.put("code",sonMM.getMenuId());
						sonMap.put("type","0");
						sonMap.put("url",sonMM.getMenuPath());
						if(StringUtils.isNotBlank(homePage.get(sonMM.getMenuId()))){
							sonMap.put("isHome","0");
						}else{
							sonMap.put("isHome","1");
						}
						menus.add(sonMap);
					}
				
				}
				supMap.put("menus", menus);
				resultList.add(supMap);
			}
		
			if(resultList.size()>0){
				menuStr=BeanUtility.objectToJsonStr(resultList, true);
			}
		}
		
		if(qrMenuIds.size()>0){
			// 获取菜单
			List<MenuitemModel> qrMenuList=menuMapper.getListByIdList(qrMenuIds);
			List<Map<String,Object>> qrList=new ArrayList<Map<String,Object>>();
			if(qrMenuList.size()>0){
				for(int i=0;i<qrMenuList.size();i++){
					if(i>2){// 限制菜单为三个
						break;
					}
					Map<String,Object> qrMap=new HashMap<String,Object>();
					qrMap.put("title",qrMenuList.get(i).getMenuText());
					qrMap.put("code",qrMenuList.get(i).getMenuId());
					qrMap.put("url",qrMenuList.get(i).getMenuPath());
					qrMap.put("ordinal",qrMenuList.get(i).getOrdinal());
					qrList.add(qrMap);
				}
			}
			
			if(qrList.size()>0){
				qrMenuStr=BeanUtility.objectToJsonStr(qrList, true);
			}
		}
		resultMap.put("menuStr", menuStr);
		resultMap.put("qrMenuStr", qrMenuStr);
		return resultMap;
	}

	public Account getAccountByQrTransitCode(String cardCode) {
		return accountMapper.selectAccountByQrTransitCode(cardCode);
	}

	/**
	 * 通过accountId 获得account对象
	 * 
	 * @param accountId
	 * @return
	 */
	public Account getAccountByAccountId(String accountId) {
		return accountMapper.selectAccountByAccountId(accountId);
	}

}
