package com.bossbutler.service.serverbill;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.common.constant.ServiceConstant;
import com.bossbutler.jms.PushMessageService;
import com.bossbutler.mapper.bill.ServiceBillMapper;
import com.bossbutler.mapper.bill.ServiceEvaluationMapper;
import com.bossbutler.mapper.customerservice.ServiceProgressMapper;
import com.bossbutler.mapper.myinfo.MyinfoMenuMapper;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.MyinfoMorePojo;
import com.bossbutler.pojo.PagingBounds;
import com.bossbutler.pojo.PushCategory;
import com.bossbutler.pojo.PushMessageDto;
import com.bossbutler.pojo.bill.HmServerBillOut;
import com.bossbutler.pojo.bill.IbServerBillOut;
import com.bossbutler.pojo.bill.ServerBillIn;
import com.bossbutler.pojo.bill.ServiceBill;
import com.bossbutler.pojo.bill.ServiceEvaluation;
import com.bossbutler.pojo.bill.ServiceEvaluationOut;
import com.bossbutler.pojo.bill.ServiceProgress;
import com.bossbutler.pojo.system.EmpRelations;
import com.bossbutler.service.BasicService;
import com.bossbutler.service.image.ImageService;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.ReturnPageBean;
import com.bossbutler.util.WriteToPage;
import com.company.exception.AppException;
import com.company.util.BeanUtility;

@Service
public class ServerBillService extends BasicService {

	@Autowired
	private ServiceBillMapper serviceBillMapper;
	@Autowired
	private ServiceProgressMapper serviceProgressMapper;
	@Autowired
	private ServiceEvaluationMapper serviceEvaluationMapper;
	@Autowired
	private ImageService imageService;
	@Autowired
	private MyinfoMenuMapper mymapper;
	@Autowired
	private PushMessageService pushMessageService;
	/**
	 * 
	 * 方法描述 :管家服务单列表
	 * 创建人 :  hzhang
	 * 创建时间: 2016年10月20日 上午12:35:32
	 * @param view
	 * @throws Exception
	 */
	public String queryHmServerBillList(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		ServerBillIn in = BeanUtility.jsonStrToObject(dataJson, ServerBillIn.class, true);
		// 验证参数开始
		if (StringUtils.isBlank(in.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		if (StringUtils.isBlank(in.getProjectId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_PROJECT_ID_NULL, dateTime);
		}
		if (StringUtils.isBlank(in.getType())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_BILL_STATUS_NULL, dateTime);
		}
		// 验证参数结束
		PagingBounds page = this.getPagingBounds(in.getPageNo(),in.getPageSize());// 分页
//		String isAdmin = serviceBillMapper.findProjectIsAdminByAccountId(in.getAccountId(),in.getProjectId());
		List<HmServerBillOut> pList = serviceBillMapper.queryHmBillPageList(in.getAccountId(), in.getType(), in.getServerType(),
				in.getProjectId(), in.getIsAdmin(),page);
		ReturnPageBean<HmServerBillOut> ret =  this.getPageResultBean(pList,page);
		String resultData = BeanUtility.objectToJsonStr(ret, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String queryIbServerBillList(ControllerView view) throws AppException {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		ServerBillIn in = BeanUtility.jsonStrToObject(dataJson, ServerBillIn.class, true);
		// 验证参数开始
		if (StringUtils.isBlank(in.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		if (StringUtils.isBlank(in.getType())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_BILL_STATUS_NULL, dateTime);
		}
		// 验证参数结束
		PagingBounds page = this.getPagingBounds(in.getPageNo(),in.getPageSize());// 分页
		List<IbServerBillOut> pList = serviceBillMapper.queryIbBillPageList(in.getAccountId(),in.getType(),page);
		ReturnPageBean<IbServerBillOut> ret =  this.getPageResultBean(pList,page);
		String resultData = BeanUtility.objectToJsonStr(ret, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	/**
	 * 评价
	 * 创建人 :  hzhang
	 * 创建时间: 2016年10月26日 上午9:33:02
	 * @param view
	 * @return
	 * @throws Exception 
	 */
	public String evaluation(ControllerView view, String appCodeName) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		ServiceEvaluation in = BeanUtility.jsonStrToObject(dataJson, ServiceEvaluation.class, true);
		// 验证参数开始
		ServiceBill b = (ServiceBill) serviceBillMapper.findByPrimaryKey(in.getBillId()+"");
		if (b == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_NOT_EXIST, dateTime);
		}
		if (in.getAccountId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		if ( in.getSkills() ==0 || in.getAttitude() ==0 || in.getEfficient() ==0 || in.getImage() ==0) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_BILL_EVALUATION_SCORE_NULL, dateTime);
		}
		in.setEvaluationId(this.getIdWorker().nextId());
		in.setBillId(in.getBillId());
		in.setStatusCode("01");
		in.setStatusTime(new Date());
		in.setOperatorId(b.getAccountId());
		int ret = serviceEvaluationMapper.insert(in);
		if (ret < 1) {
			return WriteToPage.setResponseMessage("{}", IfConstant.NET_FAIL, dateTime);
		}
		//修改单据状态
		ServiceBill bill = new ServiceBill();
		bill.setBillId(in.getBillId());
		bill.setStatusCode(ServiceConstant.BILL_STATUS_08);
		bill.setStatusTime(new Date());
		bill.setOperatorId(b.getAccountId());
		serviceBillMapper.update(bill);
		//添加过程数据
		List<EmpRelations> el = this.queryEmpByAccProPer(b.getAccountId()+"", b.getProjectId()+"", null);
		ServiceProgress sp = new ServiceProgress();
		sp.setProgressId(this.getIdWorker().nextId());
		sp.setBillId(b.getBillId());
		sp.setStatusCode(ServiceConstant.BILL_STATUS_08);
		sp.setStatusTime(new Date());
		sp.setOperatorId(b.getAccountId());
		if (el != null && el.size()>0) {
			String remark = el.get(0).getEmpName()+"评价该服务单 ";
			if (StringUtils.isNotBlank(in.getRemark())) {
				remark += in.getRemark();
			}
			sp.setStatusRemark(remark);
		}
		sp.setCreateTime(new Date());
		serviceProgressMapper.insert(sp);
		//TODO:完成推送
		//手机推送消息开始
		List<String> receivers = new ArrayList<>();
		receivers.add(in.getAccountId()+"");
		PushMessageDto dto = new PushMessageDto("01",PushCategory.BILL_EVALUATE, in.getBillId()+"", "您的服务工单已评价",getPushInfo("", el.get(0).getEmpName()));

		switch (appCodeName) {
		case "ARPM":
			pushMessageService.aRPMPushSendMessage(BeanUtility.objectToJsonStr(dto,false),"", receivers);
			break;
		case "SHZX":
			pushMessageService.sHZXPushSendMessage(BeanUtility.objectToJsonStr(dto,false),"", receivers);
			break;
		case "SJSM":
			pushMessageService.sJSMPushSendMessage(BeanUtility.objectToJsonStr(dto,false),"", receivers);
			break;

		default:
			pushMessageService.iBaobanPushSendMessage(BeanUtility.objectToJsonStr(dto,false),"", receivers);
			break;
		}
		
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String toEvaluation(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		ServiceEvaluation in = BeanUtility.jsonStrToObject(dataJson, ServiceEvaluation.class, true);
		// 验证参数开始
		ServiceBill b = (ServiceBill) serviceBillMapper.findByPrimaryKey(in.getBillId()+"");
		if (b == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.BILL_NOT_EXIST, dateTime);
		}
		Map<String, Object> param = new HashMap<>();
		param.put("billId", in.getBillId());
		param.put("statusCode", "06");//接单
		ServiceProgress sp = serviceProgressMapper.findByBillIdAndStatus(param);
		
		MyinfoMorePojo pojo1 = mymapper.querymore(sp.getOperatorId()+"");
		ServiceEvaluationOut out = new ServiceEvaluationOut();
		out.setAccountId(sp.getOperatorId()+"");
		out.setHeadpath(imageService.getAccountHeadPath(sp.getOperatorId()+""));
		out.setName(pojo1.getRealname());
		out.setServerCode(b.getBillCode()+"");
		out.setServerTitle(b.getBillTitle());
		out.setPhone(pojo1.getPhone());
		out.setBillCount(pojo1.getBillCount());
		out.setScore(pojo1.getScore());
		ServiceEvaluation se = serviceEvaluationMapper.findByPrimaryKey(b.getBillId()+"");
		if (se != null) {
			out.setAttitude(se.getAttitude());
			out.setEfficient(se.getEfficient());
			out.setImage(se.getImage());
			out.setSkills(se.getSkills());
			out.setRemark(se.getRemark());
		}
		String resultData = BeanUtility.objectToJsonStr(out, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	//
	public Map<String, String> getPushInfo(String billName,String operatorName) {
		Map<String,String> info = new HashMap<String,String>();
		info.put("billName",StringUtils.isNotBlank(billName)?billName:"");
		info.put("operatorName",StringUtils.isNotBlank(operatorName)?operatorName:"");
		info.put("systemDate",(new Date()).getTime()+"");
		return info;
	}
}
