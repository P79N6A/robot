package com.bossbutler.service.customer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bossbutler.common.constant.PermissionConstant;
import com.bossbutler.common.constant.PushMessage;
import com.bossbutler.common.constant.RolesConstant;
import com.bossbutler.common.constant.ServiceConstant;
import com.bossbutler.jms.PushMessageService;
import com.bossbutler.mapper.bill.ServiceBillMapper;
import com.bossbutler.mapper.customerservice.CusBillOperationMapper;
import com.bossbutler.mapper.customerservice.CustomerBillMapper;
import com.bossbutler.mapper.customerservice.CustomerServerMapper;
import com.bossbutler.mapper.customerservice.MyBillProMapper;
import com.bossbutler.mapper.customerservice.PropertyBillMapper;
import com.bossbutler.mapper.customerservice.ServiceProgressMapper;
import com.bossbutler.mapper.system.AccountMapper;
import com.bossbutler.pojo.AddServerPojo;
import com.bossbutler.pojo.CommentsPojo;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.CusServerDetailPojo;
import com.bossbutler.pojo.CusServerPojo;
import com.bossbutler.pojo.FileResultOut;
import com.bossbutler.pojo.ImageFilePojo;
import com.bossbutler.pojo.ImageFileView;
import com.bossbutler.pojo.ImageInfoPojo;
import com.bossbutler.pojo.ImagePojo;
import com.bossbutler.pojo.MyEmpIdPojo;
import com.bossbutler.pojo.MyempPojo;
import com.bossbutler.pojo.PagingBounds;
import com.bossbutler.pojo.PagingBoundsView;
import com.bossbutler.pojo.ProjectPojo;
import com.bossbutler.pojo.ProjectProcessPojo;
import com.bossbutler.pojo.PropertyRequestPojo;
import com.bossbutler.pojo.PropertyServerPojo;
import com.bossbutler.pojo.PropertyService;
import com.bossbutler.pojo.PushCategory;
import com.bossbutler.pojo.PushMessageDto;
import com.bossbutler.pojo.ServerImgPojo;
import com.bossbutler.pojo.ServiceTypePojo;
import com.bossbutler.pojo.ServicerPojo;
import com.bossbutler.pojo.bill.BillHome;
import com.bossbutler.pojo.bill.ServiceBill;
import com.bossbutler.pojo.bill.ServiceProgress;
import com.bossbutler.pojo.system.EmpRelations;
import com.bossbutler.service.BasicService;
import com.bossbutler.service.image.ImageService;
import com.bossbutler.util.GenerateCreater;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.MacConfirm;
import com.bossbutler.util.WriteToPage;
import com.company.exception.AppException;
import com.company.util.BeanUtility;
import com.company.util.DateUtil;

@Service
public class CustomeServerService extends BasicService {
	@Autowired
	CustomerServerMapper mapper;
	@Autowired
	CustomerBillMapper cumapper;
	@Autowired
	CusBillOperationMapper opmapper;
	@Autowired
	PropertyBillMapper promapper;
	@Autowired
	MyBillProMapper mypromapper;
	@Autowired
	AccountMapper accountMapper;
	@Autowired
	private PushMessageService pushMessageService;
	@Autowired
	private ServiceProgressMapper serviceProgressMapper;
	@Autowired
	private ServiceBillMapper serviceBillMapper;
	@Autowired
	private ImageService imageService;
	
	private long bill_id = 0;

	public String queryServerList(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		PropertyRequestPojo pojo = BeanUtility.jsonStrToObject(dataJson, PropertyRequestPojo.class, true);
		if (pojo.getAccountId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		List<CusServerPojo> pojo1 = new ArrayList<>();
		pojo1 = cumapper.queryserverlistcuss(pojo.getAccountId(), pojo.getType());
		String resultData = BeanUtility.objectToJsonStr(pojo1, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String queryTypeServerList(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String mac = view.getMac();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		PropertyRequestPojo pojo = BeanUtility.jsonStrToObject(dataJson, PropertyRequestPojo.class, true);
		if (pojo.getAccountId() == null || pojo.getType() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		// 验证地址
		boolean checkMac = MacConfirm.checkMac(mac, dataJson, view.getDateTime());
		if (!checkMac) {
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
		List<CusServerPojo> pojo1 = new ArrayList<>();
		pojo1 = cumapper.queryserverlistcuss(pojo.getAccountId(), pojo.getType());
		if (pojo1 == null)
			return WriteToPage.setResponseMessage("[]", IfConstant.SERVER_SUCCESS, dateTime);
		String resultData = BeanUtility.objectToJsonStr(pojo1, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	/**
	 * 方法描述 : 进度列表有问题 创建人 : xbj 创建时间: 2016年9月9日 下午5:48:58
	 * 
	 * @param view
	 * @return
	 * @throws Exception
	 */
	public String queryDetail(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		PropertyRequestPojo pojo = BeanUtility.jsonStrToObject(dataJson, PropertyRequestPojo.class, false);
		//验证参数开始
		if (StringUtils.isBlank(pojo.getServerId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_SERVICE_BILL_ID_NULL, dateTime);
		}
		ServiceBill b = (ServiceBill) serviceBillMapper.findByPrimaryKey(pojo.getServerId());
		if (b == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_NOT_EXIST, dateTime);
		}
		//验证参数结束
		List<ProjectProcessPojo> pList = new ArrayList<>();
		pList = cumapper.queryserverprogress(pojo.getServerId(), b.getStatusCode());
		CusServerDetailPojo pojo1 = new CusServerDetailPojo();
		pojo1 = cumapper.querydetail(pojo.getServerId(), pojo.getAccountId());
		pojo1.setpList(pList);
		pojo1.setPicPath(imageService.getServerBillPicPath(pojo.getServerId()));
		String resultData = BeanUtility.objectToJsonStr(pojo1, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String updateCancel(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		PropertyRequestPojo pojo = BeanUtility.jsonStrToObject(dataJson, PropertyRequestPojo.class, true);
		//验证参数开始
		if (StringUtils.isBlank(pojo.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		if (StringUtils.isBlank(pojo.getServerId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_SERVICE_BILL_ID_NULL, dateTime);
		}
		ServiceBill b = (ServiceBill) serviceBillMapper.findByPrimaryKey(pojo.getServerId());
		if (b == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_NOT_EXIST, dateTime);
		}
		if (!(ServiceConstant.BILL_STATUS_02.equals(b.getStatusCode())
				|| ServiceConstant.BILL_STATUS_04.equals(b.getStatusCode())
				)){
			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_CANCEL_ONLY_WAITED_SEND, dateTime);
		}
		//验证参数结束
		int row = opmapper.updatecancel(pojo.getAccountId(), pojo.getServerId());
		pojo.setProgressId(String.valueOf(getIdWorker().nextId()));
		//添加过程数据
		List<EmpRelations> el = this.queryEmpByAccProPer(pojo.getAccountId(), b.getProjectId()+"", null);
		ServiceProgress sp = new ServiceProgress();
		sp.setProgressId(this.getIdWorker().nextId());
		sp.setBillId(Long.parseLong(pojo.getServerId()));
		sp.setStatusCode(ServiceConstant.BILL_STATUS_09);
		sp.setStatusTime(new Date());
		sp.setOperatorId(Long.parseLong(pojo.getAccountId()));
		if (el != null && el.size()>0) {
			sp.setStatusRemark(el.get(0).getEmpName()+"取消了该服务单");
		}
		sp.setCreateTime(new Date());
		int row1 = serviceProgressMapper.insert(sp);
		if (row <= 0 || row1 <= 0)
			return WriteToPage.setResponseMessage("{}", IfConstant.NET_FAIL, dateTime);
		//TODO:推送
		ServiceBill billM = (ServiceBill) serviceBillMapper.findByPrimaryKey(pojo.getServerId());
		//手机推送消息开始
		List<String> receivers = new ArrayList<>();
		receivers.add(b.getAccountId()+"");
		PushMessageDto dto = new PushMessageDto("0101",PushCategory.BILL_CANCEL,pojo.getServerId()+"", "服务工单已取消",getPushInfo(billM.getBillTitle(), el.get(0).getEmpName()));
		pushMessageService.iBaobanPushSendMessage(BeanUtility.objectToJsonStr(dto,false),PushMessage.BILL_CANCEL, receivers);
		//手机推送消息结束
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}

	/**
	 * 没有做 数据库没设计好
	 * 
	 * @param view
	 * @return
	 * @throws Exception
	 */
	public String updateReminder(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		CusServerPojo pojo = BeanUtility.jsonStrToObject(dataJson, CusServerPojo.class, true);
		if (pojo.getServerId() == null || pojo.getAccountId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		//手机推送消息开始
		//TODO:催单
		Map<String, Object> param = new HashMap<>();
		param.put("billId", pojo.getServerId());
		param.put("statusCode", "06");//接单
		ServiceProgress sp = serviceProgressMapper.findByBillIdAndStatus(param);
		if (sp == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_REMINDER_ONLY_RECEIVED, dateTime);
		}
		List<String> receivers = new ArrayList<>();
		receivers.add(sp.getOperatorId()+"");
		PushMessageDto dto = new PushMessageDto("0101",PushCategory.BILL_REMINDER, pojo.getServerId()+"", "发起人正在催单",getPushInfo("", ""));
		pushMessageService.iBaobanPushSendMessage(BeanUtility.objectToJsonStr(dto,false),PushMessage.BILL_REMINDER, receivers);
		//手机推送消息结束
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}

	/**
	 * 评价时接单人的 信息 头像没有
	 * 
	 * @param view
	 * @return
	 * @thro ws Exception
	 */
	public String queryServicer(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String mac = view.getMac();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		CusServerPojo pojo = BeanUtility.jsonStrToObject(dataJson, CusServerPojo.class, true);
		if (pojo.getServerId() == null || pojo.getAccountId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		// 验证地址
		boolean checkMac = MacConfirm.checkMac(mac, dataJson, view.getDateTime());
		if (!checkMac) {
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
		ServicerPojo pojo1 = new ServicerPojo();
		pojo1 = opmapper.queryservicer(pojo.getServerId());
		if (pojo1 == null)
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
		String resultData = BeanUtility.objectToJsonStr(pojo1, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	/**
	 * 方法描述 : 增加评论
	 * 创建人 :  xbj
	 * 创建时间: 2016年9月17日 下午9:55:09
	 * @param view
	 * @return
	 * @throws Exception
	 */
	public String addComments(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String mac = view.getMac();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		CommentsPojo pojo = BeanUtility.jsonStrToObject(dataJson, CommentsPojo.class, true);
		if (pojo.getServerId() == null || pojo.getAccountId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		// 验证地址
		boolean checkMac = MacConfirm.checkMac(mac, dataJson, view.getDateTime());
		if (!checkMac){
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
		pojo.setEvaluationId(String.valueOf(getIdWorker().nextId()));
		int row=opmapper.insertservice_evaluation(pojo);
		if(row<=0)
			return WriteToPage.setResponseMessage("{}", IfConstant.NET_FAIL, dateTime);
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}

	/**
	 * 方法描述 : 新增工单的查询项目 创建人 : xbj 创建时间: 2016年9月5日 下午3:13:51
	 * 
	 * @param view
	 * @return
	 * @throws Exception
	 */
	public String queryAddress(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		CusServerPojo pojo = BeanUtility.jsonStrToObject(dataJson, CusServerPojo.class, true);
		if (pojo.getAccountId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		List<ProjectPojo> list = new ArrayList<>();
		list = opmapper.queryaddress(pojo.getAccountId());
		if (list == null)
			return WriteToPage.setResponseMessage("[]", IfConstant.SERVER_SUCCESS, dateTime);
		String resultData = BeanUtility.objectToJsonStr(list, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String addServer(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		AddServerPojo pojo = BeanUtility.jsonStrToObject(dataJson, AddServerPojo.class, true);
		if (pojo.getAccountId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		if (pojo.getServertime() != null) {
			String serverTime = pojo.getServertime().replace(" ", "");
//			String date = serverTime.substring(0, 10);
//			String begintime = serverTime.substring(10, 16);
//			String endtime = serverTime.substring(16, 21);
			String date ="";
			String begintime ="";
			String endtime ="";
			try {
				date = serverTime.substring(0, 10);
				begintime = serverTime.substring(10, 16);
				endtime = serverTime.substring(16, 21);
			} catch (Exception e) {
				date = serverTime.substring(0, 9);
				date = DateUtil.format(DateUtil.string2Date(date, "yyyy-MM-dd"), "yyyy-MM-dd");
				begintime = serverTime.substring(9, 14);
				endtime = serverTime.substring(15, 20);
			}
			pojo.setBegintime(date + " " + begintime);
			pojo.setEndtime(date + " " + endtime);
		}
		switch (pojo.getServerType()) {
		case "工程类":
			pojo.setServerType("01");
			break;
		case "保安类":
			pojo.setServerType("02");
			break;
		case "保洁类":
			pojo.setServerType("03");
			break;
		case "客服类":
			pojo.setServerType("04");
			break;
		default:
			pojo.setServerType("05");
			break;
		}
		bill_id = getIdWorker().nextId();
		if (StringUtils.isNotBlank(pojo.getDetailedAddress())) {
			pojo.setAddress(pojo.getAddress()+pojo.getDetailedAddress());
		}
		pojo.setServerId(bill_id);
		pojo.setServercode(GenerateCreater.getBillCode());
		pojo.setProgressId(String.valueOf(getIdWorker().nextId()));
		int row = opmapper.insertserver(pojo);
		//添加过程数据
		List<EmpRelations> el = this.queryEmpByAccProPer(pojo.getAccountId(), pojo.getProjectId()+"", null);
		ServiceProgress sp = new ServiceProgress();
		sp.setProgressId(this.getIdWorker().nextId());
		sp.setBillId(pojo.getServerId());
		sp.setStatusCode(ServiceConstant.BILL_STATUS_02);
		sp.setStatusTime(new Date());
		sp.setOperatorId(Long.parseLong(pojo.getAccountId()));
		if (el != null && el.size()>0) {
			sp.setStatusRemark(el.get(0).getSuperName()+"创建了该服务单");
		}
		sp.setCreateTime(new Date());
		int row1 = serviceProgressMapper.insert(sp);
		if (row <= 0 || row1 <= 0)
			return WriteToPage.setResponseMessage("{}", IfConstant.NET_FAIL, dateTime);
		//
		//手机推送消息开始
		Map<String, Object> param = new HashMap<>();
		param.put("projectId", pojo.getProjectId());
		param.put("dutyType", RolesConstant.RoleType0301);
		param.put("permissions", PermissionConstant.PERMISSION_RECEIVED_ORDER);
		List<String> receivers = this.accountMapper.queryAccountIdByParams(param );
		param.put("permissions", PermissionConstant.PERMISSION_BILL_ADMIN);
		List<String> pdrece = this.accountMapper.queryAccountIdByParams(param );
		receivers.removeAll(pdrece);
		PushMessageDto dto = new PushMessageDto("0101",PushCategory.BILL_CREATE, bill_id+"", "有新的服务工单",getPushInfo(pojo.getTittle(), el.get(0).getEmpName()));
		dto.setJd("1");
		pushMessageService.iBaobanPushSendMessage(BeanUtility.objectToJsonStr(dto,false),PushMessage.BILL_CREATE, receivers);
		dto.setPd("1");
		pushMessageService.iBaobanPushSendMessage(BeanUtility.objectToJsonStr(dto,false),PushMessage.BILL_CREATE, pdrece);
		//手机推送消息结束
		return WriteToPage.setResponseMessage(""+bill_id, IfConstant.SERVER_SUCCESS, dateTime);
	}
	@SuppressWarnings("unchecked")
	public String queryProperty(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		PropertyRequestPojo pojo = BeanUtility.jsonStrToObject(dataJson, PropertyRequestPojo.class, true);
		if (pojo.getAccountId() == null || pojo.getType() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		//分页
		PagingBoundsView pageView = new PagingBoundsView();
		pageView.setPage(pojo.getPage() == null ? 1 :Integer.parseInt(pojo.getPage()) );
		pageView.setRows(pojo.getPagesize() == null ? 10 :Integer.parseInt(pojo.getPageSize()));
		PagingBounds page = pageView.buiderPagingBounds();
		//
		List<PropertyServerPojo> pList = new ArrayList<>();// 列表数据的list
		String typeStr = "[{\"typeName\":\"工程\",\"typeId\":\"01\"},"
		+ " {\"typeName\":\"保安\",\"typeId\":\"02\"}, "
		+ "{\"typeName\":\"保洁\",\"typeId\":\"03\"}, "
		+ "{\"typeName\":\"客服\",\"typeId\":\"04\"},"
		+ "{\"typeName\":\"其他\",\"typeId\":\"05\"}]";
		List<ServiceTypePojo> tList = BeanUtility.jsonStrToObject(typeStr, List.class, false);
//		tList = promapper.querypropertytype(pojo.getAccountId(), pojo.getType(),pojo.getProjectId());
		if (tList == null)
			return WriteToPage.setResponseMessage("[]", IfConstant.SERVER_SUCCESS, dateTime);
		if (tList.size() > 0)
			pList = promapper.queryBillPageListlist(pojo.getAccountId(), pojo.getType(), 
					pojo.getServerType(),pojo.getProjectId(),page);
		PropertyService pojo1 = new PropertyService();
		pojo1.setpList(pList);
		pojo1.settList(tList);
		String resultData = BeanUtility.objectToJsonStr(pojo1, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String queryProserver(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		PropertyRequestPojo pojo = BeanUtility.jsonStrToObject(dataJson, PropertyRequestPojo.class, true);
		if (pojo.getAccountId() == null || pojo.getType() == null || pojo.getServerType() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		PagingBoundsView pageView = new PagingBoundsView();
		pageView.setPage(pojo.getPage() == null ? 1 :Integer.parseInt(pojo.getPage()) );
		pageView.setRows(pojo.getPagesize() == null ? 10 :Integer.parseInt(pojo.getPageSize()));
		PagingBounds page = pageView.buiderPagingBounds();
		List<PropertyServerPojo> pojo1 = new ArrayList<>();// 列表数据的list
		pojo1 = promapper.queryBillPageListlist(pojo.getAccountId(), pojo.getType(), pojo.getServerType(),pojo.getProjectId()
				,page);
		if (pojo1 == null)
			return WriteToPage.setResponseMessage("[]", IfConstant.SERVER_SUCCESS, dateTime);
		String resultData = BeanUtility.objectToJsonStr(pojo1, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String myProServer(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String mac = view.getMac();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		PropertyRequestPojo pojo = BeanUtility.jsonStrToObject(dataJson, PropertyRequestPojo.class, true);
		if (pojo.getAccountId() == null || pojo.getType() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		// 验证地址
		boolean checkMac = MacConfirm.checkMac(mac, dataJson, view.getDateTime());
		if (!checkMac) {
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
		List<PropertyServerPojo> pojo1 = new ArrayList<>();// 列表数据的list
		pojo1 = mypromapper.myproserver(pojo.getAccountId(), pojo.getType());
		for (int i = 0; i < pojo1.size(); i++) {
			if (pojo1.get(i).getServerId() == null)
				pojo1.remove(i);
		}
		String resultData = BeanUtility.objectToJsonStr(pojo1, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String updateReceiveServer(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		PropertyRequestPojo pojo = BeanUtility.jsonStrToObject(dataJson, PropertyRequestPojo.class, true);
		if (pojo.getServerId() == null || pojo.getPretime() == null || pojo.getAccountId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		ServiceBill b = (ServiceBill) serviceBillMapper.findByPrimaryKey(pojo.getServerId());
		if (b == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_NOT_EXIST, dateTime);
		}
		if (!ServiceConstant.BILL_STATUS_02.equals(b.getStatusCode()) 
				&& !ServiceConstant.BILL_STATUS_04.equals(b.getStatusCode())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_RECEIVE_ONLY_WAIT_SEND, dateTime);
		}
		Calendar calendar = Calendar.getInstance();
		switch (pojo.getPretime()) {
		case "1":// 一小时内完成
			calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);
			pojo.setPretime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
			break;
		case "2":// 3小时内完成
			calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 3);
			pojo.setPretime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
			break;
		case "3":// 一天内完成
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date date = calendar.getTime();
			pojo.setPretime(sdf.format(date));
			break;
		}
		pojo.setProgressId(String.valueOf(getIdWorker().nextId()));
		int row = mypromapper.updatereceiveserver(pojo.getAccountId(), pojo.getServerId(), ServiceConstant.BILL_STATUS_06);
		//添加过程数据
//		Map<String, Object> param = new HashMap<>();
//		param.put("billId", pojo.getServerId());
//		param.put("statusCode", "04");//
//		ServiceProgress sp04 = this.serviceProgressMapper.findByBillIdAndStatus(param);
//		if (sp04 != null) {
//			if (pojo.getAccountId().equals(sp04.getOperatorId())) {
//				
//			}
//		}

		List<EmpRelations> el = this.queryEmpByAccProPer(pojo.getAccountId(), b.getProjectId()+"", null);
		ServiceProgress sp = new ServiceProgress();
		sp.setProgressId(this.getIdWorker().nextId());
		sp.setBillId(Long.parseLong(pojo.getServerId()));
		sp.setStatusCode(ServiceConstant.BILL_STATUS_06);
		sp.setStatusTime(new Date());
		sp.setUpdateTime(DateUtil.string2Date(pojo.getPretime(), "yyyy-MM-dd HH:mm:ss"));
		sp.setOperatorId(Long.parseLong(pojo.getAccountId()));
		if (el != null && el.size()>0) {
			sp.setStatusRemark(el.get(0).getEmpName()+"已接单");
		}
		sp.setCreateTime(new Date());
		int row1 = serviceProgressMapper.insert(sp);
		if (row <= 0 || row1 < 0)
			return WriteToPage.setResponseMessage("{}", IfConstant.NET_FAIL, dateTime);
		ServiceBill bill = (ServiceBill) serviceBillMapper.findByPrimaryKey(pojo.getServerId());		
		//TODO:接单推送
		//手机推送消息开始
		List<String> receivers = new ArrayList<>();
		receivers.add(bill.getAccountId()+"");
		PushMessageDto dto = new PushMessageDto("01",PushCategory.BILL_RECEIVED, bill.getBillId()+"", "您的服务工单已接单",getPushInfo(bill.getBillTitle(), el.get(0).getEmpName()));
		pushMessageService.iBaobanPushSendMessage(BeanUtility.objectToJsonStr(dto,false),PushMessage.BILL_RECEIVED, receivers);
		//手机推送消息结束
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String queryMyEmp(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String mac = view.getMac();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		PropertyRequestPojo pojo = BeanUtility.jsonStrToObject(dataJson, PropertyRequestPojo.class, true);
		if (pojo.getAccountId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		if (pojo.getServerId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_SERVICE_BILL_ID_NULL, dateTime);
		}
		// 验证地址
		boolean checkMac = MacConfirm.checkMac(mac, dataJson, view.getDateTime());
		if (!checkMac) {
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
		List<MyempPojo> eList = new ArrayList<>();
		eList = mypromapper.querymyemp(pojo.getAccountId(),pojo.getServerId());
		String resultData = BeanUtility.objectToJsonStr(eList, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	/**
	 * 方法描述 : 创建人 : xbj 创建时间: 2016年9月7日 下午1:42:37
	 * @param view
	 * @return
	 * @throws Exception
	 */
	public String updateSendServer(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		PropertyRequestPojo pojo = BeanUtility.jsonStrToObject(dataJson, PropertyRequestPojo.class, true);
		if (StringUtils.isBlank(pojo.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		if (StringUtils.isBlank(pojo.getServerId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_SERVICE_BILL_ID_NULL, dateTime);
		}
		if (pojo.getBpaccountId() == null || pojo.getAccountId() == null || pojo.getServerId() == null
				) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		ServiceBill b = (ServiceBill) serviceBillMapper.findByPrimaryKey(pojo.getServerId());
		if (b == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_NOT_EXIST, dateTime);
		}
		if (!ServiceConstant.BILL_STATUS_02.equals(b.getStatusCode())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_SEND_ONLY_WAIT, dateTime);
		}
		//添加过程数据 两条数据   派单 被派单
		List<EmpRelations> el = this.queryEmpByAccProPer(pojo.getAccountId(), b.getProjectId()+"", null);
		List<EmpRelations> bpel = this.queryEmpByAccProPer(pojo.getBpaccountId(), b.getProjectId()+"", null);
		ServiceProgress sp = new ServiceProgress();
		//保存03状态
		sp.setProgressId(this.getIdWorker().nextId());
		sp.setBillId(Long.parseLong(pojo.getServerId()));
		sp.setStatusCode(ServiceConstant.BILL_STATUS_03);
		sp.setStatusTime(new Date());
		sp.setOperatorId(Long.parseLong(pojo.getAccountId()));
		if (el != null && el.size()>0 && bpel != null && bpel.size() >0) {
			sp.setStatusRemark(el.get(0).getEmpName()+"指派服务单给"+bpel.get(0).getEmpName());
		}
		sp.setCreateTime(new Date());
		serviceProgressMapper.insert(sp);
		//保存04状态
		sp.setProgressId(this.getIdWorker().nextId());
		sp.setBillId(Long.parseLong(pojo.getServerId()));
		sp.setStatusCode(ServiceConstant.BILL_STATUS_04);
		sp.setStatusTime(new Date());
		sp.setOperatorId(Long.parseLong(pojo.getBpaccountId()));
		if (bpel == null || bpel.size() ==0) {
			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_SEND_ACCOUNT_NOT_EXIST, dateTime);
		}
		if (el != null && el.size()>0 && bpel != null && bpel.size() >0) {
			sp.setStatusRemark(bpel.get(0).getEmpName()+"被指派服务单");
		}
		sp.setCreateTime(new Date());
		serviceProgressMapper.insert(sp);
		//更新服务单状态
		b.setStatusCode(ServiceConstant.BILL_STATUS_04);
		b.setStatusTime(new Date());
		b.setUpdateTime(new Date());
		b.setOperatorId(Long.parseLong(pojo.getAccountId()));
		serviceBillMapper.update(b);
		//TODO:派单
		List<String> receivers = new ArrayList<>();
		receivers.add(pojo.getBpaccountId());
		PushMessageDto dto = new PushMessageDto("0101",PushCategory.BILL_SEND, b.getBillId()+"", "您有新派单，请及时查看",getPushInfo("", el.get(0).getEmpName()));
		dto.setJd("1");
		pushMessageService.iBaobanPushSendMessage(BeanUtility.objectToJsonStr(dto,false),PushMessage.BILL_SEND, receivers);
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String updateServerImage(ImageFileView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		List<MultipartFile> multipartFileList = view.getFileBytes();
		List<String> filePaths = new ArrayList<String>();
		if (multipartFileList.size()>0 && multipartFileList != null) {
			for (MultipartFile multipartFile : multipartFileList) {
				ImageInfoPojo info = BeanUtility.jsonStrToObject(dataJson, ImageInfoPojo.class, true);
				String fileType = info.getFileType();
				if (StringUtils.isBlank(info.getBussinessID())) {
					return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL);
				}
				if (bill_id == 0) {
					bill_id = Long.parseLong(info.getBussinessID());
				}
				FileResultOut fileResultOut = uploadFile(ServiceConstant.main_classify, info.getBussinessID(), false, false,
						multipartFile.getBytes(), fileType, null, null);
				if (fileResultOut.isSuccess()) {
					ImagePojo pojo1 = mapper.queryordinal(ServiceConstant.main_classify, ServiceConstant.media_type
							,String.valueOf(bill_id));
					String dataJson1 = fileResultOut.getData();
					ImageFilePojo pojo = BeanUtility.jsonStrToObject(dataJson1, ImageFilePojo.class, true);
					ServerImgPojo impojo = new ServerImgPojo();
					impojo.setCreatetime(pojo.getCreateTime());
					impojo.setFileType(fileType);
					impojo.setMain_classify(ServiceConstant.main_classify);
					impojo.setMediaId(pojo.getMediaID());
					impojo.setMediaType(ServiceConstant.media_type);
					impojo.setOrdinal(pojo1.getOrdinal());
					impojo.setBillId(String.valueOf(bill_id));
					filePaths.add(pojo.getFilepath());
					int row = mapper.insertsermidia(impojo);
					if (row < 0)
						return WriteToPage.setResponseMessage("{}", IfConstant.NET_FAIL, dateTime);
				}else{
					return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStr(filePaths, false), IfConstant.FILE_IMAGE_UPLOAD_FAIL);
				}
			}
		}
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String queryMyempId(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		MyEmpIdPojo pojo = BeanUtility.jsonStrToObject(dataJson, MyEmpIdPojo.class, true);
		String resultData = BeanUtility.objectToJsonStr(
				queryEmpByAccProPer(pojo.getAccountId(), pojo.getProjectId(), pojo.getPermissions()), false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String UpdateServerRevoke(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		PropertyRequestPojo pojo = BeanUtility.jsonStrToObject(dataJson, PropertyRequestPojo.class, true);
		//验证参数开始
		if (StringUtils.isBlank(pojo.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		if (StringUtils.isBlank(pojo.getServerId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_SERVICE_BILL_ID_NULL, dateTime);
		}
		ServiceBill b = (ServiceBill) serviceBillMapper.findByPrimaryKey(pojo.getServerId());
		if (b == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_NOT_EXIST, dateTime);
		}
		//只有被指派才能取消派单
		if (!ServiceConstant.BILL_STATUS_04.equals(b.getStatusCode())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_REVOKE_ONLY_SEND, dateTime);
		}
		//验证参数结束
		
		//修改单据状态
		ServiceBill bill = new ServiceBill();
		bill.setBillId(Long.parseLong(pojo.getServerId()));
		bill.setStatusCode(ServiceConstant.BILL_STATUS_02);
		bill.setStatusTime(new Date());
		bill.setOperatorId(Long.parseLong(pojo.getAccountId()));
		serviceBillMapper.update(bill);
		//添加过程数据
		List<EmpRelations> el = this.queryEmpByAccProPer(pojo.getAccountId(), b.getProjectId()+"", null);
		ServiceProgress sp = new ServiceProgress();
		sp.setProgressId(this.getIdWorker().nextId());
		sp.setBillId(Long.parseLong(pojo.getServerId()));
		sp.setStatusCode(ServiceConstant.BILL_STATUS_05);
		sp.setStatusTime(new Date());
		sp.setOperatorId(Long.parseLong(pojo.getAccountId()));
		if (el != null && el.size()>0) {
			sp.setStatusRemark(el.get(0).getEmpName()+"撤回该服务单");
		}
		sp.setCreateTime(new Date());
		serviceProgressMapper.insert(sp);
		//TODO:撤回
		Map<String, Object> param = new HashMap<>();
		param.put("billId", pojo.getServerId());
		param.put("statusCode", "04");//派单
		ServiceProgress sp04 = serviceProgressMapper.findByBillIdAndStatus(param);
		List<String> receivers = new ArrayList<>();
		receivers.add(sp04.getOperatorId()+"");
		PushMessageDto dto = new PushMessageDto("0101", PushCategory.BILL_REVOKE,b.getBillId()+"", "您有派单已被撤回",getPushInfo("", el.get(0).getEmpName()));
		dto.setJd("1");
		pushMessageService.iBaobanPushSendMessage(BeanUtility.objectToJsonStr(dto,false),PushMessage.BILL_REVOKE, receivers);
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}
	/**
	 * 方法描述 :完成服务单
	 * 创建人 :  hzhang
	 * 创建时间: 2016年10月10日 上午10:51:52
	 * @param view
	 * @throws Exception 
	 */
	public String completeBill(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		//获取解析参数
		PropertyRequestPojo pojo = BeanUtility.jsonStrToObject(dataJson, PropertyRequestPojo.class, true);
		//验证参数开始
		if (StringUtils.isBlank(pojo.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		if (StringUtils.isBlank(pojo.getServerId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_SERVICE_BILL_ID_NULL, dateTime);
		}
		ServiceBill b = (ServiceBill) serviceBillMapper.findByPrimaryKey(pojo.getServerId());
		if (b == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_NOT_EXIST, dateTime);
		}
		if (!ServiceConstant.BILL_STATUS_06.equals(b.getStatusCode()) ) {
			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_COMPLETE_ONLY_RECEIVE, dateTime);
		}
		//验证参数结束
		//修改单据状态
		ServiceBill bill = new ServiceBill();
		bill.setBillId(Long.parseLong(pojo.getServerId()));
		bill.setStatusCode(ServiceConstant.BILL_STATUS_07);
		bill.setStatusTime(new Date());
		bill.setOperatorId(Long.parseLong(pojo.getAccountId()));
		serviceBillMapper.update(bill);
		//添加过程数据
		List<EmpRelations> el = this.queryEmpByAccProPer(pojo.getAccountId(), b.getProjectId()+"", null);
		ServiceProgress sp = new ServiceProgress();
		sp.setProgressId(this.getIdWorker().nextId());
		sp.setBillId(Long.parseLong(pojo.getServerId()));
		sp.setStatusCode(ServiceConstant.BILL_STATUS_07);
		sp.setStatusTime(new Date());
		sp.setOperatorId(Long.parseLong(pojo.getAccountId()));
		if (el != null && el.size()>0) {
			sp.setStatusRemark(el.get(0).getEmpName()+"完成了该服务单");
		}
		sp.setCreateTime(new Date());
		serviceProgressMapper.insert(sp);
		ServiceBill billM = (ServiceBill) serviceBillMapper.findByPrimaryKey(pojo.getServerId());
		//TODO:完成推送
		//手机推送消息开始
		List<String> receivers = new ArrayList<>();
		receivers.add(b.getAccountId()+"");
		PushMessageDto dto = new PushMessageDto("01", PushCategory.BILL_COMPLETE,pojo.getServerId()+"", "您的服务工单已完成",getPushInfo(billM.getBillTitle(), el.get(0).getEmpName()));
		pushMessageService.iBaobanPushSendMessage(BeanUtility.objectToJsonStr(dto,false),PushMessage.BILL_COMPLETE, receivers);
		//手机推送消息结束
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}
	/**
	 * 方法描述 :管理首页数据
	 * 创建人 :  hzhang
	 * 创建时间: 2016年10月11日 上午11:04:35
	 * @param view
	 * @return
	 * @throws AppException 
	 *
	 */
	@SuppressWarnings("unchecked")
	public String queryBillhome(ControllerView view) throws AppException {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		//获取解析参数
		PropertyRequestPojo pojo = BeanUtility.jsonStrToObject(dataJson, PropertyRequestPojo.class, true);
		//验证参数开始
		if (StringUtils.isBlank(pojo.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		//验证参数结束
		List<BillHome> list = serviceBillMapper.queryBillhomeByAccountId(pojo.getAccountId());
		String typeStr = "[{\"typeName\":\"工程\",\"typeId\":\"01\"},"
		+ " {\"typeName\":\"保安\",\"typeId\":\"02\"}, "
		+ "{\"typeName\":\"保洁\",\"typeId\":\"03\"}, "
		+ "{\"typeName\":\"客服\",\"typeId\":\"04\"},"
		+ "{\"typeName\":\"其他\",\"typeId\":\"05\"}]";
		List<ServiceTypePojo> tList = BeanUtility.jsonStrToObject(typeStr, List.class, false);
		for (BillHome h : list) {
//			先禁用
//			if ("1".equals(h.getPd())) {
//				h.setWaitCount(h.getPwaitCount());
//				h.setReceivedCount(h.getPreceivedCount());
//				h.setCompleteCount(h.getPcompleteCount());
//			}
			h.setBillTypeList(tList);
		}
		return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStr(list, false), IfConstant.SERVER_SUCCESS, dateTime);
	}
	
	
	public static void main(String[] args) throws AppException {
		PushMessageDto dto = new PushMessageDto("02","02","02","01",null);
		System.err.println(BeanUtility.objectToJsonStr(dto, true));
		String serverTime = "2017-01-07 13:00-18:00".replace(" ", "");
		String date ="";
		String begintime ="";
		String endtime ="";
		try {
			date = serverTime.substring(0, 10);
			begintime = serverTime.substring(10, 16);
			endtime = serverTime.substring(16, 21);
		} catch (Exception e) {
			date = serverTime.substring(0, 9);
			date = DateUtil.format(DateUtil.string2Date(date, "yyyy-MM-dd"), "yyyy-MM-dd");
			begintime = serverTime.substring(9, 14);
			endtime = serverTime.substring(15, 20);
		}
		System.out.println(date +" "+begintime +"-"+endtime);
	}

	public String rejectedServerBill(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		//获取解析参数
		PropertyRequestPojo pojo = BeanUtility.jsonStrToObject(dataJson, PropertyRequestPojo.class, true);
		//验证参数开始
		if (StringUtils.isBlank(pojo.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		if (StringUtils.isBlank(pojo.getServerId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_SERVICE_BILL_ID_NULL, dateTime);
		}
		ServiceBill b = (ServiceBill) serviceBillMapper.findByPrimaryKey(pojo.getServerId());
		if (b == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_NOT_EXIST, dateTime);
		}
//		if (!ServiceConstant.BILL_STATUS_10.equals(b.getStatusCode()) ) {
//			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_COMPLETE_ONLY_RECEIVE, dateTime);
//		}
		//验证参数结束
		//修改单据状态
		ServiceBill bill = new ServiceBill();
		bill.setBillId(Long.parseLong(pojo.getServerId()));
		bill.setStatusCode(ServiceConstant.BILL_STATUS_10);
		bill.setStatusTime(new Date());
		bill.setOperatorId(Long.parseLong(pojo.getAccountId()));
		serviceBillMapper.update(bill);
		//添加过程数据
		List<EmpRelations> el = this.queryEmpByAccProPer(pojo.getAccountId(), b.getProjectId()+"", null);
		ServiceProgress sp = new ServiceProgress();
		sp.setProgressId(this.getIdWorker().nextId());
		sp.setBillId(Long.parseLong(pojo.getServerId()));
		sp.setStatusCode(ServiceConstant.BILL_STATUS_10);
		sp.setStatusTime(new Date());
		sp.setOperatorId(Long.parseLong(pojo.getAccountId()));
		if (el != null && el.size()>0) {
			StringBuffer sb = new StringBuffer();
			sb.append(el.get(0).getEmpName()+"退回了该服务单。");
			if (StringUtils.isNotBlank(pojo.getRejectedContent())) {
				sb.append("\n退回原因："+pojo.getRejectedContent());
			}
			if (StringUtils.isNotBlank(pojo.getRejectedContent())) {
				sb.append("\n解决方案："+pojo.getSolution());
			}
			sp.setStatusRemark(sb.toString());
		}
		sp.setCreateTime(new Date());
		serviceProgressMapper.insert(sp);
		//TODO:完成推送
		//手机推送消息开始
		ServiceBill billM = (ServiceBill) serviceBillMapper.findByPrimaryKey(pojo.getServerId());
		List<String> receivers = new ArrayList<>();
		receivers.add(b.getAccountId()+"");
		PushMessageDto dto = new PushMessageDto("01", PushCategory.BILL_COMPLETE,pojo.getServerId()+"", "您的服务工单被退回",getPushInfo(billM.getBillTitle(), el.get(0).getEmpName()));
		pushMessageService.iBaobanPushSendMessage(BeanUtility.objectToJsonStr(dto,false),PushMessage.BILL_RETURN, receivers);
		//手机推送消息结束
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}
	
	
	public String cancelServerBill(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		//获取解析参数
		PropertyRequestPojo pojo = BeanUtility.jsonStrToObject(dataJson, PropertyRequestPojo.class, true);
		//验证参数开始
		if (StringUtils.isBlank(pojo.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		if (StringUtils.isBlank(pojo.getServerId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_SERVICE_BILL_ID_NULL, dateTime);
		}
		ServiceBill b = (ServiceBill) serviceBillMapper.findByPrimaryKey(pojo.getServerId());
		if (b == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_NOT_EXIST, dateTime);
		}
//		if (!ServiceConstant.BILL_STATUS_10.equals(b.getStatusCode()) ) {
//			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_COMPLETE_ONLY_RECEIVE, dateTime);
//		}
		//验证参数结束
		//修改单据状态
		ServiceBill bill = new ServiceBill();
		bill.setBillId(Long.parseLong(pojo.getServerId()));
		bill.setStatusCode(ServiceConstant.BILL_STATUS_09);
		bill.setStatusTime(new Date());
		bill.setOperatorId(Long.parseLong(pojo.getAccountId()));
		serviceBillMapper.update(bill);
		//添加过程数据
		List<EmpRelations> el = this.queryEmpByAccProPer(pojo.getAccountId(), b.getProjectId()+"", null);
		ServiceProgress sp = new ServiceProgress();
		sp.setProgressId(this.getIdWorker().nextId());
		sp.setBillId(Long.parseLong(pojo.getServerId()));
		sp.setStatusCode(ServiceConstant.BILL_STATUS_09);
		sp.setStatusTime(new Date());
		sp.setOperatorId(Long.parseLong(pojo.getAccountId()));
		if (el != null && el.size()>0) {
			StringBuffer sb = new StringBuffer();
			sb.append(el.get(0).getEmpName()+"取消了该服务单。");
			if (StringUtils.isNotBlank(pojo.getRejectedContent())) {
				sb.append("\n取消原因："+pojo.getRejectedContent());
			}
			if (StringUtils.isNotBlank(pojo.getRejectedContent())) {
				sb.append("\n解决方案："+pojo.getSolution());
			}
			sp.setStatusRemark(sb.toString());
		}
		sp.setCreateTime(new Date());
		serviceProgressMapper.insert(sp);
		//TODO:完成推送
		//手机推送消息开始
		ServiceBill billM = (ServiceBill) serviceBillMapper.findByPrimaryKey(pojo.getServerId());
		List<String> receivers = new ArrayList<>();
		receivers.add(b.getAccountId()+"");
		PushMessageDto dto = new PushMessageDto("01", PushCategory.BILL_COMPLETE,pojo.getServerId()+"", "您的服务工单被取消",getPushInfo(billM.getBillTitle(), el.get(0).getEmpName()));
		pushMessageService.iBaobanPushSendMessage(BeanUtility.objectToJsonStr(dto,false),PushMessage.BILL_RETURN, receivers);
		//手机推送消息结束
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}
	
	public Map<String, String> getPushInfo(String billName,String operatorName) {
		Map<String,String> info = new HashMap<String,String>();
		info.put("billName",StringUtils.isNotBlank(billName)?billName:"");
		info.put("operatorName",StringUtils.isNotBlank(operatorName)?operatorName:"");
		info.put("systemDate",(new Date()).getTime()+"");
		return info;
	}
}
