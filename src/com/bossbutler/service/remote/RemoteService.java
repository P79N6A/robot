package com.bossbutler.service.remote;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletResponse;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.bossbutler.common.InitConf;
import com.bossbutler.jms.MqQueueEnumAppServer;
import com.bossbutler.mapper.myregional.DeviceMapper;
import com.bossbutler.mapper.project.ProjectMapper;
import com.bossbutler.mapper.resource.ResourceMapper;
import com.bossbutler.mapper.system.AccountMapper;
import com.bossbutler.mapper.system.AccountTransitMapper;
import com.bossbutler.mapper.system.EmpMapper;
import com.bossbutler.pojo.AppHFiveBackView;
import com.bossbutler.pojo.project.Project;
import com.bossbutler.pojo.regional.TransitModel;
import com.bossbutler.pojo.remote.DeviceControllerInfo;
import com.bossbutler.pojo.resource.ResourceModel;
import com.bossbutler.pojo.system.Account;
import com.bossbutler.pojo.system.AccountTransit;
import com.bossbutler.pojo.system.EmpRelations;
import com.bossbutler.pojo.system.Employee;
import com.bossbutler.service.system.AccountService;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.RedisConstant;
import com.bossbutler.util.WriteToPage;
import com.bossbutler.util.WriteToPageAppHFiveM;
import com.common.cnnetty.gateway.pojo.CommandFactory;
import com.common.cnnetty.gateway.pojo.EOutIntType;
import com.common.cnnetty.gateway.util.ByteUtility;
import com.company.common.redis.active.CacheManager;
import com.company.util.DateUtil;

/**
 * <p>
 * 远程操作设备管理.
 * </p>
 * 
 * @author wq
 */
@Service
public class RemoteService {

	private static Logger logger = Logger.getLogger(RemoteService.class);

	@Autowired
	private JmsTemplate jmsTimeToLiveSendTemplate;
	
	@Autowired
	private DeviceMapper deviceMapper;
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Autowired
	private AccountTransitMapper accountTransitMapper;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private EmpMapper empMapper;
	
	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private ResourceMapper resourceMapper;

	/**
	 * 通过卡号远程开门
	 * 
	 * @param openSeconds
	 * @param cardCode
	 * @param deviceCode
	 * @param mainCode
	 * @param remoteOpenDoorLockKey
	 * @param oiType
	 * @param controllerType
	 * @return
	 * @throws Exception
	 */
	public String queryRemoteOpenDoor(int openSeconds, String cardCode, String deviceCode, int channel, String mainCode,
			String remoteOpenDoorLockKey, EOutIntType oiType, String controllerType, Account account, String projectId)
			throws Exception {
		logger.error("queryRemoteOpenDoor通过卡号远程开门:" + openSeconds + "|" + cardCode + "|" + deviceCode + "|" +
			channel + "|" + mainCode + "|" + remoteOpenDoorLockKey + "|" + projectId);
		logger.error("queryRemoteOpenDoor通过卡号远程开门:controllerType:" + controllerType);
		if (InitConf.getDahaoSupplierNameMult().equals(controllerType)) {// 大豪
			return remoteOpenDaHaoDevice(openSeconds, cardCode, deviceCode, channel, mainCode, remoteOpenDoorLockKey,
					oiType, account, projectId);
		} else {// 非大豪开门
			return remoteOpenUnDahaoDevice(openSeconds, cardCode, deviceCode, mainCode, remoteOpenDoorLockKey, oiType,
					account, projectId);
		}
	}
	
	/**
	 * 通过扫描二维码远程开门
	 * 
	 * @param openSeconds
	 * @param cardCode
	 * @param deviceCode
	 * @param mainCode
	 * @param remoteOpenDoorLockKey
	 * @param oiType
	 * @param controllerType
	 * @return
	 * @throws Exception
	 */
	public String queryScanQRemoteOpenDoor(int openSeconds, String cardCode, String deviceCode, int channel, String mainCode,
			String remoteOpenDoorLockKey, EOutIntType oiType, String controllerType, Account account, String projectId)
					throws Exception {
		if (InitConf.getDahaoSupplierNameMult().equals(controllerType)) {// 大豪
			return scanQRemoteOpenDaHaoDevice(openSeconds, cardCode, deviceCode, channel, mainCode, remoteOpenDoorLockKey,
					oiType, account, projectId);
		} else {// 非大豪开门
			return scanQRemoteOpenUnDahaoDevice(openSeconds, cardCode, deviceCode, mainCode, remoteOpenDoorLockKey, oiType,
					account, projectId);
		}
	}

	/**
	 * 非大豪设备远程开门
	 * 
	 * @param openSeconds
	 * @param cardCode
	 * @param deviceCode
	 * @param mainCode
	 * @param remoteOpenDoorLockKey
	 * @param oiType
	 * @return
	 * @throws Exception
	 */
	private String remoteOpenUnDahaoDevice(int openSeconds, String cardCode, String deviceCode, String mainCode,
			String remoteOpenDoorLockKey, EOutIntType oiType, Account account, String projectId) throws Exception {
		byte[] command = CommandFactory.remoteOpening(deviceCode, openSeconds, cardCode, oiType);
		String queueContent = mainCode + ":" + ByteUtility.bytesToHexString(command);
		// queueContent = queueContent + "_" + deviceCode + "_0_" + openSeconds + "_" + cardCode + "_"
		//		+ oiType.getCode() + "_6_" + account.getAccountId() + "_" + account.getLoginName() + "_" + projectId;
		// 失败重发
		Object responseObj = null;
		int recount = InitConf.getRodoorResendlimit();// 重发次数
		for (int i = 0; i <= recount && responseObj == null; i++) {
			queryQueueMessage(MqQueueEnumAppServer.SYNCH_REMOTE_OPEN_DOOR, queueContent);
			long start = System.currentTimeMillis();
			while ((System.currentTimeMillis() - InitConf.getRodoorTimeoutseconds() * 1000 <= start) && responseObj == null) {
				String commandKey = deviceCode + "@" + CommandFactory.remoteOpening;
				String remoteOpenDoorKey = String.format(RedisConstant.REDIS_TABLE_REMOTE_CONTROL_KEY, commandKey);
				responseObj = CacheManager.getExpire(remoteOpenDoorKey);
			}
		}
		CacheManager.delExpire(remoteOpenDoorLockKey);
		logger.error("remoteOpenUnDahaoDevice非大豪设备远程开门:queueContent:" + queueContent);
		logger.error("remoteOpenUnDahaoDevice非大豪设备远程开门:responseObj" + responseObj);
		if (responseObj != null) {
			return String.valueOf(responseObj);
		}
		return StringUtils.EMPTY;
	}
	
	/**
	 * 非大豪设备远程开门
	 * 
	 * @param openSeconds
	 * @param cardCode
	 * @param deviceCode
	 * @param mainCode
	 * @param remoteOpenDoorLockKey
	 * @param oiType
	 * @return
	 * @throws Exception
	 */
	private String scanQRemoteOpenUnDahaoDevice(int openSeconds, String cardCode, String deviceCode, String mainCode,
			String remoteOpenDoorLockKey, EOutIntType oiType, Account account, String projectId) throws Exception {
		byte[] command = CommandFactory.remoteOpening(deviceCode, openSeconds, cardCode, oiType);
		String queueContent = mainCode + ":" + ByteUtility.bytesToHexString(command);
		// queueContent = queueContent + "_" + deviceCode + "_0_" + openSeconds + "_" + cardCode + "_"
		//		+ oiType.getCode() + "_6_" + account.getAccountId() + "_" + account.getLoginName() + "_" + projectId;
		// 失败重发
		Object responseObj = null;
		int recount = InitConf.getRodoorResendlimit();// 重发次数
		for (int i = 0; i <= recount && responseObj == null; i++) {
			queryQueueMessage(MqQueueEnumAppServer.SYNCH_DERXI_SCAN_QR_REMOTE_CHANGE_OPEN, queueContent);
			long start = System.currentTimeMillis();
			while ((System.currentTimeMillis() - InitConf.getRodoorTimeoutseconds() * 1000 <= start) && responseObj == null) {
				String commandKey = deviceCode + "@" + CommandFactory.remoteOpening;
				String remoteOpenDoorKey = String.format(RedisConstant.REDIS_TABLE_REMOTE_CONTROL_KEY, commandKey);
				responseObj = CacheManager.getExpire(remoteOpenDoorKey);
			}
		}
		CacheManager.delExpire(remoteOpenDoorLockKey);
		if (responseObj != null) {
			return String.valueOf(responseObj);
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 大豪远程开门
	 * 
	 * @param openSeconds
	 * @param cardCode
	 * @param deviceCode
	 * @param channel
	 * @param mainCode
	 * @param remoteOpenDoorLockKey
	 * @param oiType
	 * @param account
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	private String remoteOpenDaHaoDevice(int openSeconds, String cardCode, String deviceCode, int channel,
			String mainCode, String remoteOpenDoorLockKey, EOutIntType oiType, Account account, String projectId)
			throws Exception {
		String queueContent = mainCode + "_" + deviceCode + "_" + channel + "_" + openSeconds + "_" + cardCode + "_"
				+ oiType.getCode() + "_6_" + account.getAccountId() + "_" + account.getLoginName() + "_" + projectId + "_";
		// 失败重发
		Object responseObj = null;
		int recount = InitConf.getRodoorResendlimit();// 重发次数
		for (int i = 0; i <= recount && responseObj == null; i++) {
			
			queryQueueMessage(MqQueueEnumAppServer.SYNCH_DAHAO_REMOTE_OPEN_DOOR, queueContent);
			long start = System.currentTimeMillis();
			System.out.println(i+":"+start);
			int j=0;
			while ((System.currentTimeMillis() - InitConf.getRodoorTimeoutseconds() * 1000 <= start) && responseObj == null) {
				
				//System.out.println(i+":"+j);
				j=j+1;
				String commandKey = deviceCode + "@" + CommandFactory.remoteOpening;
				String remoteOpenDoorKey = String.format(RedisConstant.REDIS_TABLE_REMOTE_CONTROL_KEY, commandKey);
				responseObj = CacheManager.getExpire(remoteOpenDoorKey);
				
			}
		}
		CacheManager.delExpire(remoteOpenDoorLockKey);
		logger.error("remoteOpenDaHaoDevice大豪远程开门:queueContent:" + queueContent);
		logger.error("remoteOpenDaHaoDevice大豪远程开门:responseObj" + responseObj);
		if (responseObj != null && (Integer) responseObj == 1) {
			return String.valueOf(responseObj);
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 大豪扫描二维码远程开门
	 * 
	 * @param openSeconds
	 * @param cardCode
	 * @param deviceCode
	 * @param channel
	 * @param mainCode
	 * @param remoteOpenDoorLockKey
	 * @param oiType
	 * @param account
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	private String scanQRemoteOpenDaHaoDevice(int openSeconds, String cardCode, String deviceCode, int channel,
			String mainCode, String remoteOpenDoorLockKey, EOutIntType oiType, Account account, String projectId)
					throws Exception {
		String queueContent = mainCode + "_" + deviceCode + "_" + channel + "_" + openSeconds + "_" + cardCode + "_"
				+ oiType.getCode() + "_6_" + account.getAccountId() + "_" + account.getLoginName() + "_" + projectId + "_";
		// 失败重发
		Object responseObj = null;
		int recount = InitConf.getRodoorResendlimit();// 重发次数
		for (int i = 0; i <= recount && responseObj == null; i++) {
			queryQueueMessage(MqQueueEnumAppServer.SYNCH_DAHAO_SCAN_QR_REMOTE_CHANGE_OPEN, queueContent);
			long start = System.currentTimeMillis();
			while ((System.currentTimeMillis() - InitConf.getRodoorTimeoutseconds() * 1000 <= start) && responseObj == null) {
				String commandKey = deviceCode + "@" + CommandFactory.remoteOpening;
				String remoteOpenDoorKey = String.format(RedisConstant.REDIS_TABLE_SCAN_QR_REMOTE_CONTROL_KEY, commandKey);
				responseObj = CacheManager.getExpire(remoteOpenDoorKey);
			}
		}
		CacheManager.delExpire(remoteOpenDoorLockKey);
		if (responseObj != null && (Integer) responseObj == 1) {
			return String.valueOf(responseObj);
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 大豪远程常开或取消常开门禁或闸机
	 * 
	 * @param openSeconds
	 * @param cardCode
	 * @param deviceCode
	 * @param channel
	 * @param mainCode
	 * @param remoteOpenChangeLockKey
	 * @param oiType 进为常开出为取消常开
	 * @param account
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	private boolean remoteDaHaoChangeNormalopenDevice(int openSeconds, String cardCode, String deviceCode, int channel,
			String mainCode, String remoteOpenChangeLockKey, EOutIntType oiType, Account account, String projectId)
			throws Exception {
		String queueContent = mainCode + "_" + deviceCode + "_" + channel + "_" + openSeconds + "_" + cardCode + "_"
				+ oiType.getCode() + "_6_" + account.getAccountId() + "_" + account.getLoginName() + "_" + projectId + "_";
		// 失败重发
		Object responseObj = null;
		int recount = InitConf.getRodoorResendlimit();// 重发次数
		for (int i = 0; i <= recount && responseObj == null; i++) {
			queryQueueMessage(MqQueueEnumAppServer.SYNCH_DAHAO_REMOTE_CHANGE_OPEN, queueContent);
			long start = System.currentTimeMillis();
			while ((System.currentTimeMillis() - InitConf.getRodoorTimeoutseconds() * 1000 <= start) && responseObj == null) {
				String commandKey = deviceCode + "@" + CommandFactory.writeGateOpenDirStrategy;
				String remoteOpenDoorKey = String.format(RedisConstant.REDIS_TABLE_REMOTE_CONTROL_KEY, commandKey);
				responseObj = CacheManager.getExpire(remoteOpenDoorKey);
			}
		}
		CacheManager.delExpire(remoteOpenChangeLockKey);
		if (responseObj != null && (Integer) responseObj == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 发送队列日志
	 * 
	 * @param mqueueNameEnum
	 * @param message
	 */
	public void queryQueueMessage(final MqQueueEnumAppServer mqueueNameEnum, final String message) {
		Destination destination = new ActiveMQQueue(mqueueNameEnum.getName());
		jmsTimeToLiveSendTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session s) throws JMSException {
				TextMessage msg = s.createTextMessage(message);
				return msg;
			}
		});
	}

	/**
	 * 大豪门禁闸机（迪尔西忽略）常开和取消常开
	 * 
	 * @param response
	 * @param deviceCode
	 * @param isOpen 是否常开1为常开0为取消常开
	 * @param openMinute
	 * @param token
	 * @param account
	 * @param info
	 * @param remoteOpenChangeLockKey
	 * @return
	 * @throws Exception
	 */
	public AppHFiveBackView dahaoChangeNormalopen(HttpServletResponse response, String deviceCode,
			int isOpen, int openMinute, String token, Account account, DeviceControllerInfo info, String remoteOpenChangeLockKey) throws Exception {
		int openSeconds = 1;
		if(isOpen == 1) {
			openSeconds = openMinute * 60;
		}
		boolean flag = remoteDaHaoChangeNormalopenDevice(openSeconds, "00" + account.getTransit_code(), deviceCode, info.getChannel(), info.getControllerCode(), remoteOpenChangeLockKey,
				isOpen == 1 ? EOutIntType.INT : EOutIntType.OUT, account, info.getProjectId());
		if(flag) {
			Date keepEndDate = DateUtil.addDate(new Date(), openSeconds, TimeUnit.SECONDS);
			deviceMapper.updateKeepTime(deviceCode, keepEndDate);
			return WriteToPageAppHFiveM.setResponseMessage(response, "{}", IfConstant.SERVER_SUCCESS, token);
		}
		return WriteToPageAppHFiveM.setResponseMessage(response, "{}", IfConstant.NO_REMOTE_CHANGE_OPEN_RESPONSE, token);
		
	}

	public String room(String loginId,String userId){
		
		if(StringUtils.isBlank(loginId)){
			return WriteToPage.setResponseMessage(null,"9","loginId不能为空");
		}
		if(StringUtils.isBlank(userId)){
			return WriteToPage.setResponseMessage(null,"9","userId不能为空");
		}
		Account acc = accountMapper.queryUser(loginId, null);
		if(acc==null){
			return WriteToPage.setResponseMessage(null,"1","该机器人不存在");
		}
		
		AccountTransit at=accountTransitMapper.findByTypeAccountId(acc.getAccountId(),"00");
		if(at==null){
			return WriteToPage.setResponseMessage(null,"1","该机器人没有TransitCode");
		}
		//Account account = accountService.getAccountByQrTransitCode(at.getTransitCode());
		Employee emp=new Employee();
		emp.setAccountId(acc.getAccountId());
		
		List<Employee> empList=empMapper.queryDynamic(emp);
		if(empList ==null || empList.size()==0){
			return WriteToPage.setResponseMessage(null,"1","该机器人不是一个emp");
		}
		Employee emplo=empList.get(0);
		if(emplo.getStatusCode().equals("02")){
			return WriteToPage.setResponseMessage(null,"1","该机器人已被禁用");
		}
		Project project=projectMapper.getProjectByEmpId(emplo.getEmpId()+"");
		
		//验证被访问人位置
		Account accReceive=accountMapper.queryUserQuery(userId);
		if(accReceive==null){
			return WriteToPage.setResponseMessage(null,"1","此人未注册");
		}
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("accountId",accReceive.getAccountId());
		params.put("projectId",project.getProjectId());
		params.put("statusCode","01");
		List<EmpRelations> userEmpList=empMapper.queryEmpByAccProPer(params);
		if(userEmpList==null || userEmpList.size()==0){
			return WriteToPage.setResponseMessage(null,"1","该人不是本大厦员工");
		}
		ResourceModel rm=new ResourceModel();
		List<String> orgIds=new ArrayList<String>();
		for(EmpRelations er:userEmpList){
			orgIds.add(er.getOrgId());
		}
		rm.setProjectId(project.getProjectId());
		rm.setOrgList(orgIds);
		List<ResourceModel> rList=resourceMapper.getResourceByOrgIds(rm);
		return WriteToPage.setResponseMessage(rList,"0","读取成功");
		
	}
}
