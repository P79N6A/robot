package com.bossbutler.service.serverbill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.Information.InformationMapper;
import com.bossbutler.mapper.bill.MyBillMapper;
import com.bossbutler.mapper.resource.ResourceMapper;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.PushMessageDto;
import com.bossbutler.pojo.Information.Information;
import com.bossbutler.pojo.bill.BillDebtorModel;
import com.bossbutler.pojo.bill.BillPoJo;
import com.bossbutler.pojo.bill.MyBillDetailModel;
import com.bossbutler.pojo.bill.MyBillListModel;
import com.bossbutler.pojo.resource.ResourceModel;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.WriteToPage;
import com.company.exception.AppException;
import com.company.util.BeanUtility;
import com.company.util.DateUtil;
@Service
public class MyBillService {
	
	@Autowired
	private MyBillMapper billMapper;
	
	@Autowired
	private InformationMapper informationMapper;
	
	
	@Autowired
	private ResourceMapper resourceMapper;
	
	
	public String queryMyBillList(ControllerView view) throws AppException{
		
		String jsonStr = view.getData();
		Map<String,String> params = BeanUtility.jsonStrToObject(jsonStr, Map.class, false);
		String orgIds = params.get("value");
		String billType = params.get("billType");
		String accountId=params.get("accountId");
		if(StringUtils.isBlank(orgIds) && StringUtils.isBlank(billType)){
			return WriteToPage.setResponseMessage("参数不可以为空", IfConstant.PARAM_PROJECT_ID_NULL);
		}
		String[] types = {"01","02","03"};
		if(Arrays.asList(types).contains(billType) == false){
		billType = "02";	
		}
		String [] orgIdsO=orgIds.split(",");
		List<String> orgIdList=new ArrayList<String>();
		for(int i=0;i<orgIdsO.length;i++){
			orgIdList.add(orgIdsO[i]);
		}
		BillPoJo model = new BillPoJo();
		model.setAccountId(accountId);
		model.setOrgIds(orgIdList);
		List<MyBillListModel> list = billMapper.queryList2(model, billType);
		String backView = BeanUtility.objectToJsonStr(list, true);
		return WriteToPage.setResponseMessage(backView, IfConstant.SERVER_SUCCESS);
		
	}
	
	public MyBillDetailModel queryMyBillDetail(String billId,String accountId, String appCodeName) throws AppException{
		//billId ="981091685264396288";
		MyBillDetailModel backModel = billMapper.queryBillDetail(billId);
		List<BillDebtorModel> currentCostList = billMapper.queryDebtorListByBillId(billId, "01");
		List<BillDebtorModel> pastCostList = billMapper.queryDebtorListByBillId(billId, "02");
		Information information=new Information();
		information.setContents(billId);
		information.setAccountId(accountId);
		information.setAppCodeName(appCodeName);
		// 通知消息
		List<Information> infoList=informationMapper.queryInfor(information);
		
		
		if(backModel!=null){
			//合同下的房源
			List <ResourceModel> rmList=resourceMapper.queryByConId(backModel.getContractId());
			String billType="";
			if(backModel.getBillType().equals("0101")){
				billType="租赁账单";
			}else if(backModel.getBillType().equals("0201")){
				billType="物业账单";
			}
			if(backModel.getStatusCode().equals("06")){
				backModel.setStatusCodeDesc("待签收");
			}else if(backModel.getStatusCode().equals("07")){
				backModel.setStatusCodeDesc("已签收");
			}else if(backModel.getStatusCode().equals("08")){
				backModel.setStatusCodeDesc("已拒签");
			}
			
			if(rmList !=null && rmList.size()>0){
				backModel.setRmList(rmList);
			}
			if(currentCostList!=null && currentCostList.size()>0){
				backModel.setCurrentFees(currentCostList);
			}
			if(pastCostList!=null && pastCostList.size()>0){
				backModel.setPassFees(pastCostList);
			}
			if(infoList !=null && infoList.size()>0){
				for(Information info:infoList){
					Calendar calendar=Calendar.getInstance();
					calendar.setTime(info.getCreateTime());
					String monthStr=DateUtil.calendarToMonth(calendar)<10?"0"+DateUtil.calendarToMonth(calendar):DateUtil.calendarToMonth(calendar)+"";
					String dayStr=DateUtil.calendarToDay(calendar)<10?"0"+DateUtil.calendarToDay(calendar):DateUtil.calendarToDay(calendar)+"";
					String time=monthStr+"月"+dayStr+"日"+" "+DateUtil.calendarToHour24(calendar)+":"+DateUtil.calendarToMinute(calendar);
					info.setTime(time);
					PushMessageDto pushMessageDto=BeanUtility.jsonStrToObject(info.getContents(),PushMessageDto.class,true);
					String content="收到来自"+backModel.getProjectName();
					if("10".equals(pushMessageDto.getCategory())){
						content=content+"物业中心的"+billType;
					}else{
						content=content+"物业中心的缴费提醒";
					}
					info.setContent(content);
				}
				backModel.setInfoList(infoList);
			}
		}
		
		return backModel;
	}
	
	
	
	/*public String signInBill(ControllerView view) throws AppException{
		String jsonStr = view.getData();
		Map<String,String> params = BeanUtility.jsonStrToObject(jsonStr, Map.class, false);
		String billId = params.get("billId");
		String operatorId = params.get("accountId");
		if (StringUtils.isBlank(billId)&&StringUtils.isBlank(operatorId)) {
			return WriteToPage.setResponseMessage("参数不可以为空", IfConstant.UNKNOWN_ERROR);
		}
		int i = billMapper.updateBillstaCode(billId, operatorId);
		if(i==1){
			return WriteToPage.setResponseMessage("", IfConstant.SERVER_SUCCESS);
		}
		 return WriteToPage.setResponseMessage("账单不存在", IfConstant.UNKNOWN_ERROR);
	}
	*/
	
	/**
	 * 方法描述 : 改变账单状态
	 * 创建人 :  llc
	 * 创建时间: 2018年4月24日
	 * @param billId
	 * @param accountId
	 * @param statusCode
	 * @return
	 */
	public int updateBillStatus(BillPoJo model){
		/*billId ="981082594307608576";*/
		int i = billMapper.updateBillstaCode(model);
		return i;
	}
	
	
	/**
	 * 方法描述 : 查看项目列表
	 * 创建人 :  llc
	 * 创建时间: 2018年4月24日
	 * @return
	 * @throws AppException 
	 */
	public String queryProjectList(ControllerView view) throws AppException{
		String jsonStr = view.getData();
		Map<String,String> params = BeanUtility.jsonStrToObject(jsonStr, Map.class, false);
		String accountId=params.get("accountId");
		if(StringUtils.isBlank(accountId)){
			return WriteToPage.setResponseMessage("参数不可以为空", IfConstant.PARAM_PROJECT_ID_NULL);
		}
		List<MyBillListModel> list=billMapper.queryProjectList(accountId);
		String backView = BeanUtility.objectToJsonStr(list, true);
		return WriteToPage.setResponseMessage(backView, IfConstant.SERVER_SUCCESS);
		 
	}
	
}
