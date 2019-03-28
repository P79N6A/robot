package com.bossbutler.service.complaint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.complaint.ComplaintMapper;
import com.bossbutler.mapper.complaintdispose.ComplaintdisposeMapper;
import com.bossbutler.mapper.system.AccountMapper;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.OrgModel;
import com.bossbutler.pojo.PagingBounds;
import com.bossbutler.pojo.PagingBoundsView;
import com.bossbutler.pojo.complaint.ComplaintIn;
import com.bossbutler.pojo.complaint.ComplaintModel;
import com.bossbutler.pojo.complaint.ComplaintOut;
import com.bossbutler.pojo.complaintdispose.ComplaintdisposeModel;
import com.bossbutler.pojo.system.Account;
import com.bossbutler.pojo.system.EmpRelations;
import com.bossbutler.service.BasicService;
import com.bossbutler.service.system.AccountService;
import com.bossbutler.util.GenerateCreater;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.MacConfirm;
import com.bossbutler.util.ReturnPageBean;
import com.bossbutler.util.WriteToPage;
import com.bossbutler.util.enums.ComplaintDisponseStatusCodeEnum;
import com.bossbutler.util.enums.ComplaintInfoTypeEnum;
import com.bossbutler.util.enums.ComplaintStatusCodeEnum;
import com.bossbutler.util.enums.InfoTypeEnum;
import com.bossbutler.util.enums.StatusCodeEnum;
import com.bossbutler.util.funs.ComplaintFuns;
import com.company.exception.AppException;
import com.company.util.BeanUtility;
import com.company.util.CommonUtil;
import com.company.util.DateUtil;
import com.google.common.collect.Lists;

@Service
public class ComplaintService extends BasicService {
	@Autowired
	private ComplaintMapper complaintMapper;
	@Autowired
	private ComplaintdisposeMapper complaintdisposeMapper;
	@Autowired
	AccountMapper accountMapper;
	@Autowired
	private AccountService accountService;
	//
	// @Autowired
	// private OrgService orgService;

	public String getComplaintList(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String mac = view.getMac();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		ComplaintModel queryModel = BeanUtility.jsonStrToObject(dataJson, ComplaintModel.class, true);

		// 设置页码
		PagingBoundsView pageView = new PagingBoundsView();
		pageView.setPage(CommonUtil.string2Int(queryModel.getPageNo()));
		pageView.setRows(CommonUtil.string2Int(queryModel.getPageSize()));

		// 验证地址
		boolean checkMac = MacConfirm.checkMac(mac, dataJson, view.getDateTime());
		if (!checkMac) {
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
		List<ComplaintModel> list = new ArrayList<>();

		PagingBounds page = pageView.buiderPagingBounds();
		list = complaintMapper.getPageList(queryModel, page);

		// 取出所有AccountID
		List<Long> accountIdList = Lists.transform(list, ComplaintFuns.getAccountIdList());
		List<Account> accountList = Lists.newArrayList();
		if (accountIdList.size() > 0) {
			accountList = accountService.getAccountListByList(accountIdList);
		}

//		List<Long> orgIdList = Lists.transform(list, ComplaintFuns.getOrgIdList());
		List<OrgModel> orgList = Lists.newArrayList();
		// if(orgIdList.size()>0){
		// orgList=orgService.getListByIdList(orgIdList);
		// }
		//
		int index = 1;
		for (ComplaintModel dmodel : list) {
			dmodel.setNumId(index);
			if (dmodel.getStatusTime() != null)
				dmodel.setStatusTimeStr(CommonUtil.getYYYYMMddHHmmss(dmodel.getStatusTime()));
			if (dmodel.getCreateTime() != null) {
				dmodel.setCreateTimeStr(CommonUtil.getYYYYMMddHHmmss(dmodel.getCreateTime()));
			}
			dmodel.setInfoTypeStr(ComplaintInfoTypeEnum.getName(dmodel.getInfoType()));
			// if(dmodel.getReplayTime()!=null)
			// dmodel.setReplayTimeStr(CommonUtil.getYYYYMMddHHmmss(dmodel.getReplayTime()));

			for (Account account : accountList) {
				if (String.valueOf(dmodel.getAccountId()).equals(account.getAccountId())) {
					dmodel.setLoginName(account.getLoginName());
					continue;
				}
			}

			for (OrgModel orgModel : orgList) {
				if (dmodel.getOrgId() == orgModel.getOrgId()) {
					dmodel.setOrgName(orgModel.getOrgName());
					continue;
				}
			}
			index++;
		}

		String resultData = BeanUtility.objectToJsonStr(list, false);
		// 发送成功
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);

	}
	//
	public String ComplaintDetail(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		ComplaintModel queryModel = BeanUtility.jsonStrToObject(dataJson, ComplaintModel.class, false);
		if (queryModel.getComplaintId() == null || queryModel.getAccountId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		String mac = view.getMac();
		// 验证地址
		boolean checkMac = MacConfirm.checkMac(mac, dataJson, view.getDateTime());
		if (!checkMac) {
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
		// 单条记录查询
		ComplaintModel resultmodel = complaintMapper.getEntity(String.valueOf(queryModel.getComplaintId()));

		// 枚举 设置StatusCode字段

		String setStatusName = StatusCodeEnum.getName(resultmodel.getStatusCode());

		resultmodel.setStatusName(setStatusName);

		String resultData = BeanUtility.objectToJsonStr(resultmodel, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	/**
	 * 方法描述 : 增加评论
	 * 
	 * @param view
	 * @return
	 * @throws Exception
	 */
	public String ComplaintaComments(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String mac = view.getMac();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		ComplaintdisposeModel insertModel = BeanUtility.jsonStrToObject(dataJson, ComplaintdisposeModel.class, true);
		if (insertModel.getComplaintId() == null || insertModel.getOperatorId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		// 验证地址
		boolean checkMac = MacConfirm.checkMac(mac, dataJson, view.getDateTime());
		if (!checkMac) {
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}

		insertModel.setDisposeId(getIdWorker().nextId());
		insertModel.setStatusCode(ComplaintDisponseStatusCodeEnum.Read.getIndex());
		Date currentDate = new Date();
		insertModel.setUpdateTime(currentDate);
		insertModel.setStatusTime(currentDate);
		insertModel.setCreateTime(currentDate);
		int row = complaintdisposeMapper.insert(insertModel);

		if (row <= 0)
			return WriteToPage.setResponseMessage("{}", IfConstant.NET_FAIL, dateTime);
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);

	}

	/**
	 * 方法描述 : 设置增加
	 * 
	 * @param view
	 * @return
	 * @throws Exception
	 */
	public String insertCompaint(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		ComplaintModel model = BeanUtility.jsonStrToObject(dataJson, ComplaintModel.class, true);
		if (model.getAccountId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		// 添加数据
		model.setComplaintId(getIdWorker().nextId());
		model.setComplaintCode(GenerateCreater.getCompaintCode());
		model.setInfoTitle(DateUtil.curDate()+"-"+model.getAccountId()+"-建议");
		model.setInfoType(InfoTypeEnum.proposal.getIndex());
		model.setStatusCode(StatusCodeEnum.Pending.getIndex());// already
		model.setAccountId(model.getAccountId());
		model.setOperatorId(model.getAccountId());

		Date currentDate = new Date();
		model.setCreateTime(currentDate);
		model.setStatusTime(currentDate);

		int row = complaintMapper.insert(model);
		if (row <= 0)
			return WriteToPage.setResponseMessage("{}", IfConstant.NET_FAIL, dateTime);
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}
	/**
	 * 方法描述 :  投诉建议增加
	 * 
	 * @param view
	 * @return
	 * @throws Exception
	 */
	public String insertIbCompaint(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		ComplaintModel model = BeanUtility.jsonStrToObject(dataJson, ComplaintModel.class, true);
		if (model.getAccountId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		if (model.getOrgId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_COMPLAINT_ORG_NOT_NULL, dateTime);
		}
		if (StringUtils.isBlank(model.getInfoType())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_COMPLAINT_TYPE_NOT_NULL, dateTime);
		}
		// 添加数据
		model.setComplaintId(getIdWorker().nextId());
		model.setComplaintCode(GenerateCreater.getCompaintCode());
		String infoTypeName = model.getInfoType() == "01" ? "-投诉": "-建议";
		model.setInfoTitle(DateUtil.curDate()+"-"+model.getAccountId()+infoTypeName);
//		model.setInfoType(InfoTypeEnum.proposal.getIndex());
		model.setStatusCode(StatusCodeEnum.Pending.getIndex());// already
		model.setAccountId(model.getAccountId());
		model.setOperatorId(model.getAccountId());
		
		Date currentDate = new Date();
		model.setCreateTime(currentDate);
		model.setStatusTime(currentDate);
		
		int row = complaintMapper.insert(model);
		if (row <= 0)
			return WriteToPage.setResponseMessage("{}", IfConstant.NET_FAIL, dateTime);
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}
	public String queryIbComplaintList(ControllerView view) throws AppException {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		ComplaintIn in = BeanUtility.jsonStrToObject(dataJson, ComplaintIn.class, true);
		// 验证参数开始
		if (StringUtils.isBlank(in.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		// 验证参数结束
		PagingBounds page = this.getPagingBounds(in.getPageNo(),in.getPageSize());// 分页
		List<ComplaintOut> pList = complaintMapper.queryPageList(in,page);
		ReturnPageBean<ComplaintOut> ret =  this.getPageResultBean(pList,page);
		String resultData = BeanUtility.objectToJsonStr(ret, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	public String queryIbOrg(ControllerView view) throws AppException {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		ComplaintIn in = BeanUtility.jsonStrToObject(dataJson, ComplaintIn.class, true);
		// 验证参数开始
		if (StringUtils.isBlank(in.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		List<Map<String,String>> pList = complaintMapper.queryManageOrg(in.getAccountId());
		String resultData = BeanUtility.objectToJsonStr(pList, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	public String findIbInfo(ControllerView view) throws AppException {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		ComplaintIn in = BeanUtility.jsonStrToObject(dataJson, ComplaintIn.class, true);
		// 验证参数开始
		if (StringUtils.isBlank(in.getComplaintId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_COMPLAINT_ID_NOT_NULL, dateTime);
		}
		ComplaintOut c = complaintMapper.findIbInfo(in.getComplaintId());
		String resultData = BeanUtility.objectToJsonStr(c, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	public String queryHmComplaintList(ControllerView view) throws AppException {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		ComplaintIn in = BeanUtility.jsonStrToObject(dataJson, ComplaintIn.class, true);
		// 验证参数开始
		if (StringUtils.isBlank(in.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		List<EmpRelations> el = this.queryEmpByAccProPer(in.getAccountId(), null, null);
		String orgId = "0";
		if (el.size()>0 && el != null) {
			for (EmpRelations e : el) {
				orgId += ","+e.getOrgId();
			}
		}
		ComplaintIn param = new ComplaintIn();
		param.setOrgId(orgId);
		param.setInfoType(in.getInfoType());
		// 验证参数结束
		PagingBounds page = this.getPagingBounds(in.getPageNo(),in.getPageSize());// 分页
		List<ComplaintOut> pList = complaintMapper.queryHmPageList(param,page);
		ReturnPageBean<ComplaintOut> ret =  this.getPageResultBean(pList,page);
		String resultData = BeanUtility.objectToJsonStr(ret, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	public String findHmInfo(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		ComplaintIn in = BeanUtility.jsonStrToObject(dataJson, ComplaintIn.class, true);
		// 验证参数开始
		if (StringUtils.isBlank(in.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		if (StringUtils.isBlank(in.getComplaintId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_COMPLAINT_ID_NOT_NULL, dateTime);
		}
		ComplaintOut c = complaintMapper.findHmInfo(in.getComplaintId());
		if (ComplaintStatusCodeEnum.Default.getIndex().equals(c.getStatusCode()) ||
				ComplaintStatusCodeEnum.Published.getIndex().equals(c.getStatusCode())	) {
			ComplaintModel model = new ComplaintModel();
			model.setComplaintId(c.getComplaintId());
			model.setStatusCode(ComplaintStatusCodeEnum.Read.getIndex());
			model.setStartTime(DateUtil.curDate());
			model.setOperatorId(Long.parseLong(in.getAccountId()));
			model.setUpdateTime(new Date());
			complaintMapper.update(model);
			ComplaintdisposeModel insertModel = new ComplaintdisposeModel();
			insertModel.setComplaintId(c.getComplaintId()+"");
			insertModel.setDisposeId(getIdWorker().nextId());
			insertModel.setStatusCode(ComplaintDisponseStatusCodeEnum.Read.getIndex());
			Date currentDate = new Date();
			insertModel.setUpdateTime(currentDate);
			insertModel.setStatusTime(currentDate);
			insertModel.setCreateTime(currentDate);
			insertModel.setOperatorId(Long.parseLong(in.getAccountId()));
			int row = complaintdisposeMapper.insert(insertModel);
			if (row <= 0)
			return WriteToPage.setResponseMessage("{}", IfConstant.NET_FAIL, dateTime);
		}
		String resultData = BeanUtility.objectToJsonStr(c, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	public String insertComplaintReply(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 解析获取到的参数
		ComplaintIn in = BeanUtility.jsonStrToObject(dataJson, ComplaintIn.class, true);
		// 验证参数开始
		if (StringUtils.isBlank(in.getAccountId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		if (StringUtils.isBlank(in.getComplaintId())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_COMPLAINT_ID_NOT_NULL, dateTime);
		}
		if (StringUtils.isBlank(in.getReplyContents())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_COMPLAINT_REPLY_CONTENTS_NOT_NULL, dateTime);
		}
		if (in.getReplyContents().length()>200) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_COMPLAINT_REPLY_CONTENTS_LENGTH_200, dateTime);
		}
		ComplaintOut c = complaintMapper.findHmInfo(in.getComplaintId());
		if (ComplaintStatusCodeEnum.Replay.getIndex().equals(c.getStatusCode()) ||
				ComplaintStatusCodeEnum.Solve.getIndex().equals(c.getStatusCode())	) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_COMPLAINT_NOT_REPLY, dateTime);
		}		
		//
		ComplaintModel model = new ComplaintModel();
		model.setComplaintId(Long.parseLong(in.getComplaintId()));
		model.setStatusCode(ComplaintStatusCodeEnum.Replay.getIndex());
		model.setStartTime(DateUtil.curDate());
		model.setOperatorId(Long.parseLong(in.getAccountId()));
		model.setUpdateTime(new Date());
		complaintMapper.update(model);
		ComplaintdisposeModel insertModel = new ComplaintdisposeModel();
		insertModel.setDisposeId(getIdWorker().nextId());
		insertModel.setComplaintId(in.getComplaintId()+"");
		insertModel.setStatusCode(ComplaintDisponseStatusCodeEnum.Replay.getIndex());
		in.setComplaintId(in.getComplaintId());
		Date currentDate = new Date();
		insertModel.setUpdateTime(currentDate);
		insertModel.setStatusTime(currentDate);
		insertModel.setCreateTime(currentDate);
		insertModel.setOperatorId(Long.parseLong(in.getAccountId()));
		insertModel.setStatusRemark(in.getReplyContents());
		int row = complaintdisposeMapper.insert(insertModel);

		if (row <= 0)
			return WriteToPage.setResponseMessage("{}", IfConstant.NET_FAIL, dateTime);
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);

	}
}
