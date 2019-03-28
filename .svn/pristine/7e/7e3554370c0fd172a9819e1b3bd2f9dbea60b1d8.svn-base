package com.bossbutler.controller.remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bossbutler.common.InitConf;
import com.bossbutler.controller.BaseDeviceCacheController;
import com.bossbutler.mapper.myregional.MyRegionalMapper;
import com.bossbutler.mapper.project.ProjectMapper;
import com.bossbutler.mapper.system.AccountMapper;
import com.bossbutler.mapper.system.AccountTransitMapper;
import com.bossbutler.mapper.system.EmpMapper;
import com.bossbutler.pojo.RemoteOpenDoor;
import com.bossbutler.pojo.ScanQRData;
import com.bossbutler.pojo.ScanQRRemoteOpenDoor;
import com.bossbutler.pojo.project.Project;
import com.bossbutler.pojo.regional.Device;
import com.bossbutler.pojo.regional.TransitModel;
import com.bossbutler.pojo.remote.DeviceControllerInfo;
import com.bossbutler.pojo.system.Account;
import com.bossbutler.pojo.system.AccountTransit;
import com.bossbutler.service.myregional.ProjectService;
import com.bossbutler.service.remote.DeviceService;
import com.bossbutler.service.remote.RemoteService;
import com.bossbutler.service.system.AccountService;
import com.bossbutler.service.system.TransitResultBizService;
import com.bossbutler.service.system.VisitorApplyService;
import com.bossbutler.util.AESUtils;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.MapDistance;
import com.bossbutler.util.RedisConstant;
import com.bossbutler.util.WriteToPage;
import com.common.cnnetty.gateway.pojo.EOutIntType;
import com.company.common.redis.active.CacheManager;
import com.company.exception.AppException;
import com.company.util.BeanUtility;

/**
 * 远程操作设备信息
 * 
 * @author wangqiao
 */
@RestController
@RequestMapping("remote")
public class RemoteController extends BaseDeviceCacheController {

	private static Logger logger = Logger.getLogger(RemoteController.class);

	/**
	 * 扫描二维码进门key
	 */
	private static final String scanQRKey = "BAOBANwang@123bbw";

	@Autowired
	RemoteService remoteService;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransitResultBizService transitResultBizService;
	
	@Autowired
	private VisitorApplyService visitorApplyService;

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MyRegionalMapper myRegionalMapper;
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Autowired
	private AccountTransitMapper accountTransitMapper;
	
	@Autowired
	private EmpMapper empMapper;
	
	@Autowired
	private ProjectMapper  projectMapper;

	/**
	 * 远程开门
	 * 
	 * @param carcode(卡号)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/open/door", method = RequestMethod.POST)
	public String remoteOpenDoor(@RequestParam("data") String data, HttpServletRequest request) {
		try {
			RemoteOpenDoor remoteOpenDoor = BeanUtility.jsonStrToObject(data, RemoteOpenDoor.class, true);
			logger.error("remoteOpenDoor远程开门:data:" + data + "(remoteOpenDoor == null)" + (remoteOpenDoor == null));
			if (remoteOpenDoor == null) {
				return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, null);
			}
			// 检测卡号
			String cardCode = remoteOpenDoor.getCardCode();
			String deviceCode = remoteOpenDoor.getDeviceCode();
			EOutIntType eOiType = remoteOpenDoor.getOiType();
			logger.error("remoteOpenDoor远程开门:cardCode:" + cardCode + "|deviceCode:" + deviceCode + "|eOiType:" + (eOiType == null));
			if (StringUtils.isBlank(cardCode) || StringUtils.isBlank(deviceCode) || eOiType == null) {
				return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, null);
			}
			// 限制设备远程开锁并发
			String remoteOpenDoorLockKey = String.format(RedisConstant.REDIS_TABLE_REMOTE_OPEN_DOOR_LOCK_KEY, deviceCode);
			if (CacheManager.exists(remoteOpenDoorLockKey)) {
				return WriteToPage.setResponseMessage("{}", IfConstant.OTHER_USER_OPENING_DOOR, null);
			}
			
			// 通过设备编号获得主控板编号
			DeviceControllerInfo info = getDeviceControllerInfo(deviceCode, deviceService);
			logger.error("remoteOpenDoor远程开门:info:" + (info == null));
			if (info == null) {
				return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, null);
			}
			// 设置重开间隔
			logger.error("remoteOpenDoor远程开门: + info.getControllerType():" + info.getControllerType());
			CacheManager.setExpire(remoteOpenDoorLockKey, InitConf.getRodooropenlockseconds(), System.currentTimeMillis());

			Account account = accountService.getAccountByQrTransitCode(cardCode);
			cardCode = "00" + cardCode;// 二维码类型00
			String responseStr = remoteService.queryRemoteOpenDoor(InitConf.getRodoorOpenseconds(), cardCode,
					deviceCode, info.getChannel(), info.getControllerCode(), remoteOpenDoorLockKey, remoteOpenDoor.getOiType(),
					info.getControllerType(), account, info.getProjectId());
			if (StringUtils.isBlank(responseStr)) {
				deleteDeviceControllerInfoCache(deviceCode);
				return WriteToPage.setResponseMessage(responseStr, IfConstant.NO_REMOTE_DOOR_RESPONSE,
						String.valueOf(System.currentTimeMillis()));
			}

			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS,
					String.valueOf(System.currentTimeMillis()));
		} catch (Exception e) {
			logger.error("remoteOpenDoor参数obj转换json报错:" + e.getMessage(), e);
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, null);
		}

	}

	
	
	
	
	/**
	 * 机器人开门
	 * 
	 * @param carcode(卡号)
	 * @param request
	 * @return
	 * @throws AppException 
	 */
	@RemoteAnnotation
	@RequestMapping(value = "/robot/open", method = {RequestMethod.POST})
	public String robotOpenDoor(HttpServletRequest request) {
		Map<String,String> map=new HashMap<String,String>();
		try {
			String data=request.getParameter("data");
			if(StringUtils.isBlank(data) ){
				return WriteToPage.setResponseMessage(null,"9","缺少data参数");
			}
			Map<String,String> mapDate=null;
			try{
				mapDate=BeanUtility.jsonStrToObject(data, Map.class, true);
			}catch(Exception e){
				return WriteToPage.setResponseMessage(null,"9","remoteOpen参数obj转换json报错");
			}
			String loginId=mapDate.get("loginId");
			String deviceCode=mapDate.get("deviceCode");
			String openType=mapDate.get("openType");
			String openTime=mapDate.get("openTime");
			String receivePhone=mapDate.get("userId");
			
			if(StringUtils.isBlank(loginId)){
				return WriteToPage.setResponseMessage(null,"9","loginId不能为空");
			}
			
			Pattern pattern = Pattern.compile("^[\\+]?[\\d]*$");
			if(! pattern.matcher(mapDate.get("openTime")).matches() ){
				return WriteToPage.setResponseMessage(null,"9","openTime只能是整数");
			}
			
			String timeKey="robot_time"+loginId;
			String countKey="robot_count"+loginId;
			if(CacheManager.exists(countKey+"01")){
				return WriteToPage.setResponseMessage(null,"7","请求过于频繁,请于1分钟后重试");
			}
			if(CacheManager.exists(countKey)){
				long count1=CacheManager.get(countKey+"02");
				if(count1>30){
					CacheManager.delKey(countKey+"02");
					CacheManager.setExpire(countKey+"01", InitConf.getRodooropenlockseconds(),1);
					return WriteToPage.setResponseMessage(null,"6","请求过于频繁,请于1分钟后重试");
				}else{
					CacheManager.inDecrBy(countKey+"02",1);
				}
			}else{
				CacheManager.setExpire(countKey,InitConf.getRodooropenlockseconds(),1);
				CacheManager.delKey(countKey+"02");
				CacheManager.set(countKey+"02",1+"");
			}
			int time=new Integer(mapDate.get("openTime"))+1;
			if(CacheManager.exists(timeKey)){
				CacheManager.setExpire(timeKey,time,loginId);
				return WriteToPage.setResponseMessage(null,"5","请求过于频繁");
			}
			CacheManager.setExpire(timeKey,time,loginId);
			if(StringUtils.isBlank(deviceCode)){
				return WriteToPage.setResponseMessage(null,"9","deviceCode不能为空");
			}
			if(StringUtils.isBlank(openType)){
				return WriteToPage.setResponseMessage(null,"9","openType不能为空");
			}
			if(!(openType.equals("00") ||openType.equals("01"))){
				return WriteToPage.setResponseMessage(null,"9","openType格式不正确");
			}
			if(StringUtils.isBlank(openTime)){
				return WriteToPage.setResponseMessage(null,"9","openTime不能为空");
			}
			if(StringUtils.isBlank(receivePhone)){
				return WriteToPage.setResponseMessage(null,"9","receivePhone不能为空");
			}
			EOutIntType oiType;
			if (openType.equals("00")) {
				oiType=EOutIntType.INT;
			} else if (openType.equals("01")) {
				oiType=EOutIntType.OUT;
			} else {
				return null;
			}
			Account acc = accountMapper.queryUser(loginId, null);
			if(acc==null){
				return WriteToPage.setResponseMessage(null,"2","该机器人不存在");
			}
			AccountTransit at=accountTransitMapper.findByTypeAccountId(acc.getAccountId(),"00");
			if(at==null){
				return WriteToPage.setResponseMessage(null,"2","该机器人没有TransitCode");
			}
			Account account = accountService.getAccountByQrTransitCode(at.getTransitCode());
			// 通过设备编号获得主控板编号
			DeviceControllerInfo info = getDeviceControllerInfo(deviceCode, deviceService);
			if (info == null) {
				return WriteToPage.setResponseMessage(null,"2","设备不存在");
			}
			List<Device> dlist=new ArrayList<Device>();
			Device d=new Device();
			d.setDeviceCode(deviceCode);
			dlist.add(d);
			List<Account> alist=new ArrayList<Account>();
			alist.add(acc);
			List<TransitModel> transtList=myRegionalMapper.getTransitByDeviceAccId(dlist,alist,"00");
			if(transtList==null || transtList.size()==0){
				return WriteToPage.setResponseMessage(null,"2","机器人无开门权限");
			}
			/*//验证被访问人位置
			Account accReceive=accountMapper.queryUserByPhone(receivePhone);
			if(accReceive==null){
				return WriteToPage.setResponseMessage(null,"2","此手机号未注册");
			}
			alist.clear();
			alist.add(accReceive);
			List<TransitModel> receiveList=myRegionalMapper.getTransitByDeviceAccId(dlist,alist,"00");
			if(receiveList==null || receiveList.size()==0){
				return WriteToPage.setResponseMessage(null,"2","访问人不在此位置");
			}*/
			// 判断是否有用户正在开启该设备
			String remoteOpenDoorLockKey = String.format(RedisConstant.REDIS_TABLE_REMOTE_OPEN_DOOR_LOCK_KEY, deviceCode);		
			if (CacheManager.exists(remoteOpenDoorLockKey)) {
				Object accountId=CacheManager.getExpire(remoteOpenDoorLockKey);
				if(accountId.toString().equals(acc.getAccountId())){
					return WriteToPage.setResponseMessage(null,"3","正在开启该设备中...");			
				}
				return WriteToPage.setResponseMessage(null,"4","其他用户正在开启该设备");
			}
			// 设置设备开启锁定时间
			CacheManager.setExpire(remoteOpenDoorLockKey, InitConf.getRodooropenlockseconds(), acc.getAccountId());
		
			String transitCode= "00" + at.getTransitCode();// 二维码类型00
			String responseStr = remoteService.queryRemoteOpenDoor(InitConf.getRodoorOpenseconds(), transitCode,
					deviceCode, info.getChannel(), info.getControllerCode(), remoteOpenDoorLockKey, oiType,
					info.getControllerType(), account, info.getProjectId());
			
			if(StringUtils.isBlank(responseStr)) {
				deleteDeviceControllerInfoCache(deviceCode);
				return WriteToPage.setResponseMessage(null,"1","远程未响应");
			}
    		return WriteToPage.setResponseMessage(null,"0","开门成功");
		} catch (Exception e) {
			logger.error("remoteOpen参数obj转换json报错:" + e.getMessage(), e);
			map.put("code","9");
			map.put("message", "程序异常");
			return WriteToPage.setResponseMessage(null,"9","程序异常");
			
		}

	}
	@RemoteAnnotation
	@RequestMapping(value = "/robot/room", method = RequestMethod.POST)
	public String room(HttpServletRequest request)  {
		String data=request.getParameter("data");
		try{
			
			if(StringUtils.isBlank(data) ){
				return WriteToPage.setResponseMessage(null,"9","缺少data参数");
			}
			Map<String,String> mapDate=null;
			try{
				mapDate=BeanUtility.jsonStrToObject(data, Map.class, true);
			}catch(Exception e){
				return WriteToPage.setResponseMessage(null,"9","remoteOpen参数obj转换json报错");
			}
			String loginId=mapDate.get("loginId");
			String userId=mapDate.get("userId");
			return remoteService.room(loginId, userId);
		}catch(Exception e){
			return WriteToPage.setResponseMessage(null,"9","程序异常");
		}
		
	}
	
	/**
	 * 扫二维码开门
	 * 
	 * @param qrdata(二维码通行码)
	 * @param userType(用户类型【常驻（00）或者访客（05）】)
	 * @param aId(常驻accountId或者访客applyId)
	 * @param transitCode(二维码开门通行码)
	 * @param longitude(手机端定位经度)
	 * @param latitude(手机端定位维度)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "scan/qr/open/door", method = RequestMethod.POST)
	public String scanQRRemoteOpenDoor(@RequestParam("data") String data, HttpServletRequest request) {
		try {
			ScanQRRemoteOpenDoor scanQRRemoteOpenDoor = BeanUtility.jsonStrToObject(data, ScanQRRemoteOpenDoor.class,
					true);
			if (scanQRRemoteOpenDoor == null) {
				return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_BUSY_RESPONSE, null);
			}
			String qrData = scanQRRemoteOpenDoor.getQrdata();
			String userType = scanQRRemoteOpenDoor.getUserType();
			String aId = scanQRRemoteOpenDoor.getaId();
			String transitCode = scanQRRemoteOpenDoor.getTransitCode();
			if (StringUtils.isBlank(qrData) || StringUtils.isBlank(userType) || StringUtils.isBlank(aId)
					|| StringUtils.isBlank(transitCode)) {
				return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_BUSY_RESPONSE, null);
			}

			// 经纬度验证
			String longitude = scanQRRemoteOpenDoor.getLongitude();
			String latitude = scanQRRemoteOpenDoor.getLatitude();
			if ("1".equals(InitConf.getAppLocationFlag())
					&& (StringUtils.isBlank(longitude) || StringUtils.isBlank(latitude))) {
				return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_BUSY_RESPONSE, null);
			}

			String qrDataDec = AESUtils.aesDecrypt(qrData, scanQRKey);
			if (qrDataDec == null) {
				return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_BUSY_RESPONSE, null);
			}

			// 距离判断
			String projectCode = StringUtils.substring(qrDataDec, 0, 4);
			Project project = projectService.queryProjectByProjectCode(projectCode);
			if(InitConf.getAppLocationFlag() == 1) {
				String distance = MapDistance.getDistance(latitude, longitude, String.valueOf(project.getLatitude()),
						String.valueOf(project.getLongitude()));
				
				if(InitConf.getAppLocationOffset() < Long.valueOf(distance)) {
					return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_NO_DISTANCE_ACCESS_RESPONSE, null);
				}
			}

			// 构建扫码对象
			ScanQRData scanQRData = new ScanQRData(String.valueOf(project.getProjectId()),
					StringUtils.substring(qrDataDec, 4, 12), StringUtils.substring(qrDataDec, 12, 14),
					StringUtils.substring(qrDataDec, 14, 16));

			// 检测卡号
			String deviceCode = scanQRData.getDeviceCode();
			EOutIntType eOiType = scanQRData.getIoType();
			if (eOiType == null || StringUtils.isBlank(deviceCode)) {
				return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_BUSY_RESPONSE, null);
			}

			// 通过设备编号获得主控板编号
			DeviceControllerInfo info = getDeviceControllerInfo(deviceCode, deviceService);
			if (info == null) {
				return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_BUSY_RESPONSE, null);
			}

			// 判断通行权限0001000112010200
			if("00".equals(userType)) {// 常驻
				Account account = accountService.getAccountByQrTransitCode(transitCode);
				if(account == null || !aId.equals(account.getAccountId())) {
					return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_NO_ACCESS_RESPONSE, null);
				}
				// projectId, accountCode, deviceCode, transitType, transitCode
				int count =  transitResultBizService.queryTransitListResultBizCount(project.getProjectId(), account.getAccountCode(), deviceCode, "00", transitCode);
				if(count == 0) {
					return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_NO_ACCESS_RESPONSE, null);
				}
			} else if("05".equals(userType)) {// 访客
				String deviceTransit = deviceCode;
				if (InitConf.getDahaoSupplierNameMult().equals(info.getControllerType())) {// 大豪
					deviceTransit = info.getControllerCode() + "0" + info.getChannel();
				}
				// projectId, applyId, deviceTransit（controller_code + channel_dh 或者 deviceCode_derx）, transitCode
				int count =  visitorApplyService.queryVisitorApplyResultCount(project.getProjectId(), aId, deviceTransit, transitCode);
				if(count == 0) {
					return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_NO_ACCESS_RESPONSE, null);
				}
			} else {
				return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_BUSY_RESPONSE, null);
			}
	
			String remoteOpenDoorLockKey = String.format(RedisConstant.REDIS_TABLE_REMOTE_OPEN_DOOR_LOCK_KEY,
					deviceCode);

			Account account = accountService.getAccountByQrTransitCode(transitCode);
			transitCode = "00" + transitCode;// 二维码类型00
			String responseStr = remoteService.queryScanQRemoteOpenDoor(InitConf.getRodoorOpenseconds(), transitCode,
					deviceCode, info.getChannel(), info.getControllerCode(), remoteOpenDoorLockKey, eOiType, info.getControllerType(),
					account, info.getProjectId());
			if (StringUtils.isBlank(responseStr)) {
				deleteDeviceControllerInfoCache(deviceCode);
				return WriteToPage.setResponseMessage(responseStr, IfConstant.NO_REMOTE_DOOR_RESPONSE,
						String.valueOf(System.currentTimeMillis()));
			}

			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS,
					String.valueOf(System.currentTimeMillis()));
		} catch (Exception e) {
			logger.error("scanQRRemoteOpenDoor参数obj转换json报错:" + e.getMessage(), e);
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_BUSY_RESPONSE, null);
		}

	}

}
