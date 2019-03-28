package com.bossbutler.service.chart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.chart.ChartMapperM;
import com.bossbutler.mapper.chart.MyChartOrg;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.chart.*;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.WriteToPage;
import com.company.exception.AppException;
import com.company.util.BeanUtility;
import com.company.util.DateUtil;
import com.google.common.collect.Lists;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class ChartServiceM {
	@Autowired
	private ChartMapperM chartMapper;

	public String queryOrgList(ControllerView view) throws AppException {
		String dataJson = view.getData();
		Map<String, String> params = BeanUtility.jsonStrToObject(dataJson, Map.class, false);
		String accountId = params.get("accountId");
		String chartType = params.get("chartType");
		if (StringUtils.isEmpty(accountId)) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL);
		}
		List<MyChartOrg> orgs  = new ArrayList<>();
		if(chartType.equals("tjfx")){ // 统计分析  
			orgs = chartMapper.queryOrgs(accountId,"02");
		}
		if(chartType.equals("zd")){ //账单
			 orgs = chartMapper.queryOrgs(accountId,"01");
		}

		if ((orgs == null) || (orgs.size() == 0)) {
			WriteToPage.setResponseMessage("[]", IfConstant.SERVER_SUCCESS);
		}
		String backView = BeanUtility.objectToJsonStr(orgs, false);
		return WriteToPage.setResponseMessage(backView, IfConstant.SERVER_SUCCESS);
	}

	// 租赁概况
	public String queryLeasingInfo2(Map<String, String> view, String orgId, String project_id,String contractType) throws AppException {
		
		String dateDay=DateUtil.format(new Date(), "yyyy-MM-dd");
		String dateMonth=DateUtil.format(new Date(), "yyyy-MM");
		String dateYear=DateUtil.format(new Date(), "yyyy");
		LeasigOverviewM model = new LeasigOverviewM();
		// 出租面积
		String rentAreaS = chartMapper.queryLeaseholdArea( project_id,dateDay,contractType);
		// 总面积
		String allAreaS = chartMapper.queryAllResourceArea(project_id);
		
		double rentAreaD = Double.valueOf(rentAreaS != null ? rentAreaS : "0.0");
		double allAreaD = Double.valueOf(allAreaS != null ? allAreaS : "0.0");
		double unRentAreaD = allAreaD - rentAreaD;
		double rate = unRentAreaD / allAreaD;
		rate = (Double.isNaN(rate) || Double.isInfinite(rate)) ? 0.0 : rate;
		model.setAllRentArea(doublePattern(allAreaD,2)+"");
		model.setUnRentArea(doublePattern(unRentAreaD,2) + "");
		model.setRoomRate(doubleToString(rate));
		// 去年同期
		Calendar calendar =Calendar.getInstance(); 
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR,-1);
		rentAreaS= chartMapper.queryLeaseholdArea(project_id,DateUtil.format(calendar.getTime(), "yyyy-MM-dd"),contractType);
		rentAreaD = Double.valueOf(rentAreaS != null ? rentAreaS : "0.0");
		unRentAreaD=allAreaD - rentAreaD;
		rate = unRentAreaD / allAreaD;
		rate = (Double.isNaN(rate) || Double.isInfinite(rate)) ? 0.0 : rate;
		model.setpRoomRate(doubleToString(rate));
		//本年到期 合同
		model.setDueContract(chartMapper.queryExpiryContract("01",project_id,dateYear,contractType));
		//本月到期合同数
		model.setmDueContract(chartMapper.queryExpiryContract("02",project_id,dateMonth,contractType));
		//本年签约合同数
		model.setSignContract(chartMapper.querySignContract("01",project_id,dateYear,contractType));
		//本月签约合同数
		model.setmSignContract(chartMapper.querySignContract("02",project_id,dateMonth,contractType));
		//总租户数
		model.setAllTenant(chartMapper.queryTenantTotal(project_id,dateDay,contractType));
		//入户客户数
		model.setInTenant(chartMapper.queryInTenantTotal(project_id,dateDay,contractType));
		//本月入户客户数
		model.setmInTenant(chartMapper.queryInTenantTotalByType("02", project_id,dateMonth,dateDay,contractType));
		//退租客户数
		model.setOutTenant(chartMapper.queryOutTenantTotalByType("01",project_id,dateYear,null,contractType));
		//本月退租客户数
		model.setmOutTenant(chartMapper.queryOutTenantTotalByType("02",project_id,dateMonth,dateDay,contractType));
		
		Map<String,Double[]> rentMap=new HashMap<String,Double[]>();
		Map<String,String>areaMap=new HashMap<String,String>();
		List<RentModel> rentList =chartMapper.queryRentList(project_id,dateYear,contractType);
		for(RentModel rm:rentList){
			String beginDate=rm.getBeginDate();
			String endDate=rm.getEndDate();
			int beginYear=new Integer(beginDate.substring(0,4));
			int endYear=new Integer(endDate.substring(0,4));
			
			if(beginYear<new Integer(dateYear)){
				beginDate=dateYear+"-01-01";
			}
			if(endYear >new Integer(dateYear)){
				endDate=dateYear+"-12-31";
			}
			int beginMonth=new Integer(beginDate.substring(5,7));
			int endMonth=new Integer(endDate.substring(5,7));
			int beginDay=new Integer(beginDate.substring(8,10));
			int endDay=new Integer(endDate.substring(8,10));
			if(beginMonth==endMonth){
				int days=endDay-beginDay+1;
				if(rentMap.get(beginMonth+"") ==null){
					Double[] dou=new Double[2];
					dou[0]=days*(new Double(rm.getRentDaysAmount()));
					/*if(contractType.equals("02")){
						dou[1]=new Double(rm.getFloorageArea());
						areaMap.put(beginMonth+","+rm.getContractId(),rm.getFloorageArea());
					}else{*/
						dou[1]=days*(new Double(rm.getFloorageArea()));
					/*}
					*/
					rentMap.put(beginMonth+"",dou);
				}else{
					Double[] dou=rentMap.get(beginMonth+"");
					/*if(contractType.equals("02")){
						if(areaMap.get(beginMonth+","+rm.getContractId()) ==null){
							dou[1]=dou[1]+(new Double(rm.getFloorageArea()));
							areaMap.put(beginMonth+","+rm.getContractId()+"",rm.getFloorageArea());
						}
					}else{*/
						dou[1]=dou[1]+days*(new Double(rm.getFloorageArea()));
					/*}*/
					dou[0]=dou[0]+days*(new Double(rm.getRentDaysAmount()));
				}
				continue;
			}
			for(int i=beginMonth;i<=endMonth;i++){
				int beginD=0;
				int engD=0;
				if(i==beginMonth){
					beginD=beginDay;
					String j=(i+1)<10?"0"+(i+1):(i+1)+"";
					String dateNext=dateYear+"-"+j+"-01";
					Calendar cal =Calendar.getInstance(); 
					cal.setTime(DateUtil.string2Date(dateNext,"yyyy-MM-dd"));
					cal.add(calendar.DATE,-1);
					engD=new Integer(DateUtil.format(cal.getTime(),"yyyy-MM-dd").substring(8,10));
				}else if(i==endMonth){
					beginD=1;
					engD=endDay;
				}else{
					beginD=1;
					String j=(i+1)<10?"0"+(i+1):(i+1)+"";
					String dateNext=dateYear+"-"+j+"-01";
					Calendar cal =Calendar.getInstance(); 
					cal.setTime(DateUtil.string2Date(dateNext,"yyyy-MM-dd"));
					cal.add(calendar.DATE,-1);
					engD=new Integer(DateUtil.format(cal.getTime(),"yyyy-MM-dd").substring(8,10));
				}
				int days=engD-beginD+1;
				
				if(rentMap.get(i+"") ==null){
					Double[] dou=new Double[2];
					/*if(contractType.equals("02")){
						dou[1]=new Double(rm.getFloorageArea());
						areaMap.put(i+","+rm.getContractId(),rm.getFloorageArea());
					}else{*/
						dou[1]=days*(new Double(rm.getFloorageArea()));
					/*}*/
					dou[0]=days*(new Double(rm.getRentDaysAmount()));
					rentMap.put(i+"",dou);
				}else{
					Double[] dou=rentMap.get(i+"");
					/*if(contractType.equals("02")){
						if(areaMap.get(i+","+rm.getContractId()) ==null){
							dou[1]=dou[1]+(new Double(rm.getFloorageArea()));
							areaMap.put(i+","+rm.getContractId()+"",rm.getFloorageArea());
						}
					}else{*/
						dou[1]=dou[1]+days*(new Double(rm.getFloorageArea()));
					/*}*/
					dou[0]=dou[0]+days*(new Double(rm.getRentDaysAmount()));
				}
				
			}
			
		}
		
		List<String> list=new ArrayList<String>();
		for(int i=1;i<=12;i++){
			Double[] d=rentMap.get(i+"");
			if(d !=null){
				if(d[1]==0.0){
					list.add("0.0");
					continue;
				}
				
				double monthFee=0d;
				monthFee=d[0]/d[1];
				BigDecimal bg = new BigDecimal(monthFee);
				monthFee= bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				list.add(monthFee+""); 
			}else{
				list.add("0.0"); 
			}
		}
		model.setRentFeeNum(list.size() + "");
		model.setRentFees(list);
		String backView = BeanUtility.objectToJsonStr(model, true);
		return WriteToPage.setResponseMessage(backView, IfConstant.SERVER_SUCCESS);
	}
	
	// 租赁概况
	public String queryLeasingInfo(Map<String, String> view, String orgId, String project_id) throws AppException {
		LeasigOverviewM model = new LeasigOverviewM();
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		// String endDate= year+"-"+month+"-"+day +"";
		String endDateY = year + "-12-31";
		String startDateY = year + "-01-01";
		String nowDate = year + "-" + month + "-" + day;
		String endDateM = year + "-" + month + "-31";
		String startDateM = year + "-" + month + "-01";
		String rentAreaS = chartMapper.queryContractCurrent("02201", project_id, null, null, null, null);
//		String rentAreaS = chartMapper.queryContractReportSum("02301", project_id, null, startDateY, endDateM, "4");
		String allAreaS = chartMapper.queryProjectCurrent("01201", project_id, null, null, null);
		double rentAreaD = Double.valueOf(rentAreaS != null ? rentAreaS : "0.0");
		double allAreaD = Double.valueOf(allAreaS != null ? allAreaS : "0.0");
		double unRentAreaD = allAreaD - rentAreaD;
		double rate = unRentAreaD / allAreaD;
		rate = (Double.isNaN(rate) || Double.isInfinite(rate)) ? 0.0 : rate;
		model.setAllRentArea(allAreaD+"");
		model.setUnRentArea(allAreaD - rentAreaD + "");
		model.setRoomRate(doubleToString(rate));
		// 去年同期
		rentAreaD = Double.valueOf(chartMapper.queryContractReportSum("02301", project_id, null, year-1+"-01-01", year-1+"-"+month+"-"+day, "4"));
		rate = (allAreaD -rentAreaD )/allAreaD;
		rate = (Double.isNaN(rate) || Double.isInfinite(rate)) ? 0.0 : rate;
		model.setpRoomRate(doubleToString(rate));
		// 本年到期 合同
		model.setDueContract(chartMapper.queryContractReportSum("02306", project_id, null, startDateY, endDateY, "4"));
		model.setmDueContract(chartMapper.queryContractReportSum("02306", project_id, null, startDateM, endDateM, "4"));
		// 本年签约 合同
		model.setSignContract(chartMapper.queryContractReportSum("02304", project_id, null, startDateY, endDateY, "3"));
		model.setmSignContract(
				chartMapper.queryContractReportSum("02304", project_id, null, startDateM, endDateM, "3"));
		// 总租户
		// model.setAllTenant(chartMapper.queryCustomerReportSum("04301",
		// project_id, null, null, endDateY, "2"));
		model.setAllTenant(chartMapper.queryCustomerCurrentSum("04201", project_id, null, null, null, null));
		// 入户
		model.setInTenant(chartMapper.queryCustomerReportSum("04302", project_id, null, startDateY, endDateY, "3"));
		model.setmInTenant(chartMapper.queryCustomerReportSum("04302", project_id, null, startDateM, endDateM, "3"));
		// 退租
		model.setOutTenant(chartMapper.queryCustomerReportSum("04303", project_id, null, startDateY, endDateY, "4"));
		model.setmOutTenant(chartMapper.queryCustomerReportSum("04303", project_id, null, startDateM, endDateM, "4"));
		// 租金单价
		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= month; i++) {
			String tm = (i < 10) ? ("0" + i) : (i + "");
			String monthFee = chartMapper.queryContractReportRentFee(year + tm, project_id, "02303", "02317");
			monthFee = StringUtils.isBlank(monthFee) ? "0.0" : monthFee;
			list.add(monthFee);
		}
		model.setRentFeeNum(list.size() + "");
		model.setRentFees(list);
		String backView = BeanUtility.objectToJsonStr(model, true);
		return WriteToPage.setResponseMessage(backView, IfConstant.SERVER_SUCCESS);

		// LeasigOverviewM model = new LeasigOverviewM();
		// model.setAllRentArea("10000");
		// model.setUnRentArea("111");
		// model.setRoomRate("9%");
		// model.setpRoomRate("10%");
		// model.setDueContract("33");
		// model.setmDueContract("3");
		// model.setSignContract("909");
		// model.setmSignContract("98");
		// model.setAllTenant("99");
		// model.setInTenant("11");
		// model.setmInTenant("44");
		// model.setOutTenant("9");
		// model.setmOutTenant("7");
		// List<String> list = new ArrayList<String>();
		// for (int i = 1; i < 9; i++) {
		// list.add((i + 2) + "");
		// }
		// model.setRentFeeNum(list.size() + "");
		// model.setRentFees(list);
		// String backView = BeanUtility.objectToJsonStr(model, false);
		// return WriteToPage.setResponseMessage(backView,
		// IfConstant.SERVER_SUCCESS);

	}

	//租赁收费概况
	
	public String queryChargingInfo2(Map<String, String> view, String orgId, String project_id,String billType) throws AppException{
		ChargingOverview model = new ChargingOverview();
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String monthStr=month<10?"0"+month:month+"";
		String dayStr=day<10?"0"+day:day+"";
		//本年度租金实收金额
		List<ChargingOverview> list=chartMapper.netReceiptsCharAtList(project_id,year+"-01-01",year+"-"+monthStr+"-"+dayStr,billType,"01");
		if(list !=null && list.size()>0){
			ChargingOverview co=list.get(0);
			model.setPayRentCurrent(co.getAmount());
		}else{
			model.setPayRentCurrent(0.0+"");
		}
		//去年同期固定费目实收金额
		int lastYear=year-1;
		List<ChargingOverview> lastList=chartMapper.netReceiptsCharAtList(project_id,lastYear+"-01-01",lastYear+"-"+monthStr+"-"+dayStr,billType,"01");
		if(lastList !=null && lastList.size()>0){
			ChargingOverview co=lastList.get(0);
			model.setPayRentPast(co.getAmount());
		}else{
			model.setPayRentPast(0.0+"");
		}
		//本月固定费目实收
		List<ChargingOverview> monthList=chartMapper.netReceiptsCharAtList(project_id,year+"-"+monthStr+"-01",year+"-"+monthStr+"-"+dayStr,billType,"01");
		if(monthList !=null && monthList.size()>0){
			ChargingOverview co=monthList.get(0);
			model.setPayRentMonth(co.getAmount());
		}else{
			model.setPayRentMonth(0.0+"");
		}
		//本年度应收
		List<ChargingOverview> payList=chartMapper.payableCharAtList(project_id,year+"-01-01",year+"-12-31",billType,"01");
		if(payList !=null && payList.size()>0){
			ChargingOverview co=payList.get(0);
			model.setPayRentExpect(co.getAmount());
		}else{
			model.setPayRentExpect(0.0+"");
		}
		
		double rate = new Double(model.getPayRentCurrent()) / new Double(model.getPayRentExpect());
		rate = (Double.isNaN(rate) || Double.isInfinite(rate)) ? 0.0 : rate;
		model.setPayRate(doubleToString(rate));
		
		rate = new Double(model.getPayRentMonth()) / new Double(model.getPayRentExpect());
		rate = (Double.isNaN(rate) || Double.isInfinite(rate)) ? 0.0 : rate;
		model.setMonthPercent(doubleToString(rate));
		
		//本年度其他费用
		List<String> chartNames = new ArrayList<>();
		List<RptModel> chartDatas = new ArrayList<>();
		List<ChargingOverview> orderList=chartMapper.netReceiptsCharAtList(project_id,year+"-01-01",year+"-"+monthStr+"-"+dayStr,billType,"04");
		if(orderList !=null && orderList.size()>0){
			double orderAount=0.0d;
			for(int i=0;i<orderList.size();i++){
				orderAount=orderAount+(new Double(orderList.get(i).getAmount()));
				RptModel rpt = new RptModel();
				rpt.setName(orderList.get(i).getChargeName());
				rpt.setValue(Double.parseDouble(orderList.get(i).getAmount()));
				chartDatas.add(rpt);
				chartNames.add(orderList.get(i).getChargeName() + ":¥ " + orderList.get(i).getAmount());
			}
			//其他费用总金额
			model.setOtherFeesCurrent(orderAount+"");
		}else{
			model.setOtherFeesCurrent(0.0+"");
		}
		
		model.setChartNames(chartNames);
		model.setChartData(chartDatas);
		
		//去年同期其他费用
		List<ChargingOverview> lastOrderList=chartMapper.netReceiptsCharAtList(project_id,lastYear+"-01-01",lastYear+"-"+monthStr+"-"+dayStr,billType,"04");
		if(lastOrderList !=null && lastOrderList.size()>0){
			double orderAount=0.0d;
			for(int i=0;i<lastOrderList.size();i++){
				orderAount=orderAount+(new Double(lastOrderList.get(i).getAmount()));
				
			}
			model.setOtherFeesPast(orderAount+"");
		}else{
			model.setOtherFeesPast(0.0+"");
		}
	
		String backView = BeanUtility.objectToJsonStr(model, false);
		return WriteToPage.setResponseMessage(backView, IfConstant.SERVER_SUCCESS);
	}
	// 收费概况
	public String queryChargingInfo(Map<String, String> view, String orgId, String project_id) throws AppException {
		ChargingOverview model = new ChargingOverview();
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		// String endDate= year+"-"+month+"-"+day +"";
		String endDateY = year + "-12-31";
		String startDateY = year + "-01-01";
		String nowDate = year + "-" + month + "-" + day;
		String endDateM = year + "-" + month + "-31";
		String startDateM = year + "-" + month + "-01";
		// 实收
		double payRentD = Double
				.valueOf(chartMapper.queryChargeReportSum("03302", project_id, null, startDateY, endDateM, "3"));
		// 应收
		double payExpectD = Double
				.valueOf(chartMapper.queryChargeReportSum("03301", project_id, null, startDateY, endDateM, "3"));
		double monthPercent = Double
				.valueOf(chartMapper.queryChargeReportSum("03302", project_id, null, startDateM, endDateM, "3"));
		// 实收金额
		model.setPayRentCurrent(payRentD + "");
		model.setPayRentPast(chartMapper.queryChargeReportSum("03302", project_id, null, year - 1 + "-01-01",
				year - 1 + "-" + month + "-" + day, "3"));
		model.setPayRentMonth(monthPercent + "");
		double mRate = monthPercent / payExpectD ;
		mRate = (Double.isNaN(mRate) || Double.isInfinite(mRate)) ? 0.0 : mRate;
		model.setMonthPercent(doubleToString(mRate));
		// 应收
		payRentD = (payRentD != 0) ? payRentD : 0;

		double rate = payRentD / payExpectD;
		rate = (Double.isNaN(rate) || Double.isInfinite(rate)) ? 0.0 : rate;
		model.setPayRate(doubleToString(rate));
		model.setPayRentExpect(payExpectD + "");
//	model.setOtherFeesPast(chartMapper.queryChargeReportSum("03308", project_id, null, startDateY, endDateM, "3"));
//		model.setOtherFeesCurrent(chartMapper.queryChargeReportProcess("03102", project_id, null, "0101", "1102",
//				startDateY, endDateY, "", "1"));
//		
//		model.setOtherFeesPast(chartMapper.queryChargeReportProcess("03102", project_id, null, "0101", "1102",
//				year - 1 + "-01-01", year - 1 + "-" + month + "-" + day, "", "1"));
		List<String> chartNames = new ArrayList<>();
		List<RptModel> chartDatas = new ArrayList<>();
		double otherFee = 0.0;
		List<RptFeeModel> feeArr = chartMapper.queryOtherSumFees("03102", project_id, null, "0101", "1102",
				startDateY, endDateY, "", "1");
		List<RptFeeModel> feeArrP = chartMapper.queryOtherSumFees("03102", project_id, null, "0101", "1102",
				year - 1 + "-01-01", year - 1 + "-" + month + "-" + day, "", "1");
			if(feeArrP.isEmpty()){
				model.setOtherFeesPast("0.0");
			}else{
					for(RptFeeModel rptFeeModel : feeArrP){
						String feeValue = rptFeeModel.getCharge_code();
						feeValue = !StringUtils.isBlank(feeValue) ? feeValue : "0.0";
						otherFee += Double.valueOf(feeValue);
					}
			}
		
		otherFee = 0.0;
		
		if (!feeArr.isEmpty()) {
			for (RptFeeModel rptFeeModel : feeArr) {
				String feeValue = rptFeeModel.getCharge_code();
				feeValue = !StringUtils.isBlank(feeValue) ? feeValue : "0.0";
				chartNames.add(rptFeeModel.getCharge_name() + ":¥ " + feeValue);
				otherFee += Double.valueOf(feeValue);
			}
			model.setOtherFeesCurrent(otherFee+"");
		} else {
			chartNames.add("其他费用:¥ " + "0.0");
			model.setOtherFeesCurrent("0.0"+"");
		}
		model.setChartNames(chartNames);
		for (String key : chartNames) {
			String value[] = key.split(":¥ ");
			RptModel rpt = new RptModel();
			rpt.setName(key);
			rpt.setValue(Double.parseDouble(value[1]));
			chartDatas.add(rpt);
		}
		model.setChartData(chartDatas);

		String backView = BeanUtility.objectToJsonStr(model, false);
		return WriteToPage.setResponseMessage(backView, IfConstant.SERVER_SUCCESS);
		// ChargingOverview model = new ChargingOverview();
		// model.setPayRentCurrent("370");
		// model.setPayRentPast("444");
		// model.setPayRentMonth("33");
		// model.setOtherFeesCurrent("999");
		// model.setOtherFeesPast("123");
		// model.setPeicFee("11");
		// model.setFamFee("22");
		// model.setZhinNFee("33");
		// model.setWeiyFee("44");
		// model.setBucFee("55");
		// List<String> chartNames = new ArrayList<>();
		// chartNames.add("赔偿金:¥ " + model.getPeicFee());
		// chartNames.add("罚没金:¥ " + model.getFamFee());
		// chartNames.add("滞纳金:¥ " + model.getZhinNFee());
		// chartNames.add("违约金:¥ " + model.getWeiyFee());
		// chartNames.add("补偿金:¥ " + model.getBucFee());
		// model.setChartNames(chartNames);
		// List<RptModel> chartDatas = new ArrayList<>();
		// for (String key : chartNames) {
		// String value[] = key.split(":¥ ");
		// RptModel rpt = new RptModel();
		// rpt.setName(key);
		// rpt.setValue(Double.parseDouble(value[1]));
		// chartDatas.add(rpt);
		// }
		// model.setChartData(chartDatas);
		// String backView = BeanUtility.objectToJsonStr(model, false);
		// return WriteToPage.setResponseMessage(backView,
		// IfConstant.SERVER_SUCCESS);
	}

	// 物业管理
	public String queryPropertyFeeInfo(Map<String, String> view, String orgId, String project_id) throws AppException {
		PropertyMgrOverview model = new PropertyMgrOverview();
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		// String endDate= year+"-"+month+"-"+day +"";
		String endDateY = year + "-12-31";
		String startDateY = year + "-01-01";
		String nowDate = year + "-" + month + "-" + day;
		String endDateM = year + "-" + month + "-31";
		String startDateM = year + "-" + month + "-01";
//		String rentAreaS = chartMapper.queryProjectCurrent("02309", project_id, null, null, null);
		String rentAreaS = chartMapper.queryContractCurrent("02204", project_id, null, null, null, null);
//		String rentAreaS = chartMapper.queryContractReportSum("02309", project_id, null, startDateY, endDateM, "4");
		String allAreaS = chartMapper.queryProjectCurrent("01201", project_id, null, null, null);
		double rentAreaD = Double.valueOf(rentAreaS != null ? rentAreaS : "0.0");
		double allAreaD = Double.valueOf(allAreaS != null ? allAreaS : "0.0");
		double unRentAreaD = allAreaD - rentAreaD;
		model.setAllRentArea(allAreaD+"");
		model.setUnRentArea(allAreaD - rentAreaD + "");
		double rate = unRentAreaD / allAreaD;
		rate = (Double.isNaN(rate) || Double.isInfinite(rate)) ? 0.0 : rate;
		model.setRoomRate(doubleToString(rate));
		// 去年同期
		rentAreaD = Double.valueOf(chartMapper.queryContractReportSum("02309", project_id, null, year-1+"-01-01", year-1+"-"+month+"-"+day, "4"));
		rate = (allAreaD -rentAreaD )/allAreaD;
		rate = (Double.isNaN(rate) || Double.isInfinite(rate)) ? 0.0 : rate;
		model.setpRoomRate(doubleToString(rate));
		// 本年到期 合同
		model.setDueContract(chartMapper.queryContractReportSum("02314", project_id, null, startDateY, endDateY, "4"));
		model.setmDueContract(chartMapper.queryContractReportSum("02314", project_id, null, startDateM, endDateM, "4"));
		// 本年签约 合同
		model.setSignContract(chartMapper.queryContractReportSum("02312", project_id, null, startDateY, endDateY, "3"));
		model.setmSignContract(
				chartMapper.queryContractReportSum("02312", project_id, null, startDateM, endDateM, "3"));
		// 总租户
		// model.setAllTenant(chartMapper.queryCustomerReportSum("04304",
		// project_id, null, null, endDateY, "2"));
		model.setAllTenant(chartMapper.queryCustomerCurrentSum("04202", project_id, null, null, null, null));
		// 入户
		model.setInTenant(chartMapper.queryCustomerReportSum("04305", project_id, null, startDateY, endDateY, "3"));
		model.setmInTenant(chartMapper.queryCustomerReportSum("04305", project_id, null, startDateM, endDateM, "3"));
		// 退租
		model.setOutTenant(chartMapper.queryCustomerReportSum("04306", project_id, null, startDateY, endDateY, "4"));
		model.setmOutTenant(chartMapper.queryCustomerReportSum("04306", project_id, null, startDateM, endDateM, "4"));
		// 租金单价
		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= month; i++) {
			String tm = (i < 10) ? ("0" + i) : (i + "");
			String monthFee = chartMapper.queryContractReportRentFee(year + tm, project_id, "02311", "02318");
			monthFee = StringUtils.isBlank(monthFee) ? "0.0" : monthFee;
			list.add(monthFee);
		}
		model.setRentFeeNum(list.size() + "");
		model.setRentFees(list);
		String backView = BeanUtility.objectToJsonStr(model, true);
		return WriteToPage.setResponseMessage(backView, IfConstant.SERVER_SUCCESS);

		// PropertyMgrOverview model = new PropertyMgrOverview();
		// model.setAllRentArea("10000");
		// model.setUnRentArea("111");
		// model.setRoomRate("9%");
		// model.setpRoomRate("10%");
		// model.setDueContract("33");
		// model.setmDueContract("3");
		// model.setSignContract("909");
		// model.setmSignContract("98");
		// model.setAllTenant("99");
		// model.setInTenant("11");
		// model.setmInTenant("44");
		// model.setOutTenant("9");
		// model.setmOutTenant("7");
		// List<String> list = new ArrayList<String>();
		// for (int i = 1; i < 9; i++) {
		// list.add((i + 2) + "");
		// }
		// model.setRentFeeNum(list.size() + "");
		// model.setRentFees(list);
		// String backView = BeanUtility.objectToJsonStr(model, false);
		// return WriteToPage.setResponseMessage(backView,
		// IfConstant.SERVER_SUCCESS);

	}

	// 物业收费
	
	public String queryPropertyMgrInfo2(Map<String, String> view, String orgId, String project_id,String billType) throws AppException {
		
		PropertyFeeOverview model = new PropertyFeeOverview();
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String monthStr=month<10?"0"+month:month+"";
		String dayStr=day<10?"0"+day:day+"";
		//本年度物业费实收金额
		List<ChargingOverview> proList=chartMapper.netReceiptsCharAtList(project_id,year+"-01-01",year+"-"+monthStr+"-"+dayStr,billType,"02");
		if(proList !=null && proList.size()>0){
			ChargingOverview co=proList.get(0);
			model.setPropertyFee(co.getAmount());
		}else{
			model.setPropertyFee(0.0+"");
		}
		
		//去年同期物业费实收金额
		int lastYear=year-1;
		List<ChargingOverview> lastProList=chartMapper.netReceiptsCharAtList(project_id,lastYear+"-01-01",lastYear+"-"+monthStr+"-"+dayStr,billType,"02");
		if(lastProList !=null && lastProList.size()>0){
			ChargingOverview co=lastProList.get(0);
			model.setpPropertyFee(co.getAmount());
		}else{
			model.setpPropertyFee(0.0+"");
		}
		
		//本月物业费目实收
		List<ChargingOverview> monthProList=chartMapper.netReceiptsCharAtList(project_id,year+"-"+monthStr+"-01",year+"-"+monthStr+"-"+dayStr,billType,"02");
		if(monthProList !=null && monthProList.size()>0){
			ChargingOverview co=monthProList.get(0);
			model.setmPropertyFee(co.getAmount());
		}else{
			model.setmPropertyFee(0.0+"");
		}
		//本年度应收物业费
		List<ChargingOverview> payProList=chartMapper.payableCharAtList(project_id,year+"-01-01",year+"-12-31",billType,"02");
		if(payProList !=null && payProList.size()>0){
			ChargingOverview co=payProList.get(0);
			model.setPropertyExpFee(co.getAmount());
		}else{
			model.setPropertyExpFee(0.0+"");
		}
		
		double rate = new Double(model.getPropertyFee()) / new Double(model.getPropertyExpFee());
		rate = (Double.isNaN(rate) || Double.isInfinite(rate)) ? 0.0 : rate;
		model.setPropertyRate(doubleToString(rate));
		
		
		rate = new Double(model.getmPropertyFee()) / new Double(model.getPropertyExpFee());
		rate = (Double.isNaN(rate) || Double.isInfinite(rate)) ? 0.0 : rate;
		model.setMonthPropertyRate(doubleToString(rate));
		
		
		//本年度能耗费实收金额
		List<ChargingOverview> proEcList=chartMapper.netReceiptsCharAtList(project_id,year+"-01-01",year+"-"+monthStr+"-"+dayStr,billType,"03");
		
		if(proEcList !=null && proEcList.size()>0){
			double energyFee=0.0d;
			for(ChargingOverview co:proEcList){
				energyFee=energyFee+(new Double(co.getAmount()));
			}
			model.setEnergyFee(energyFee+"");
		}else{
			model.setEnergyFee(0.0+"");
		}
				
		//去年同期能耗实收金额
		List<ChargingOverview> lastEcList=chartMapper.netReceiptsCharAtList(project_id,lastYear+"-01-01",lastYear+"-"+monthStr+"-"+dayStr,billType,"03");
		if(lastEcList !=null && lastEcList.size()>0){
			double pEnergyFee=0.0d;
			for(ChargingOverview co:lastEcList){
				pEnergyFee=pEnergyFee+(new Double(co.getAmount()));
			}
			model.setpEnergyFee(pEnergyFee+"");
		}else{
			model.setpEnergyFee(0.0+"");
		}
				
		//本月能耗实收
		List<ChargingOverview> monthEcList=chartMapper.netReceiptsCharAtList(project_id,year+"-"+monthStr+"-01",year+"-"+monthStr+"-"+dayStr,billType,"03");
		if(monthEcList !=null && monthEcList.size()>0){
			double mEnergyFee=0.0d;
			for(ChargingOverview co:monthEcList){
				mEnergyFee=mEnergyFee+(new Double(co.getAmount()));
			}
			model.setmEnergyFee(mEnergyFee+"");		
		}else{
			model.setmEnergyFee(0.0+"");
		}
				
		
		//本年度应收能耗费
		List<ChargingOverview> payEcList=chartMapper.payableCharAtList(project_id,year+"-01-01",year+"-12-31",billType,"03");
		if(payEcList !=null && payEcList.size()>0){
			double energyExpFee=0.0d;
			for(ChargingOverview co:payEcList){
				energyExpFee=energyExpFee+(new Double(co.getAmount()));
			}
			model.setEnergyExpFee(energyExpFee+"");	
		}else{
			model.setEnergyExpFee(0.0+"");
		}
				
		
		rate = new Double(model.getEnergyFee()) / new Double(model.getEnergyExpFee());
		rate = (Double.isNaN(rate) || Double.isInfinite(rate)) ? 0.0 : rate;
		model.setEnergyRate(doubleToString(rate));
		
		
		rate = new Double(model.getmEnergyFee()) / new Double(model.getEnergyExpFee());
		rate = (Double.isNaN(rate) || Double.isInfinite(rate)) ? 0.0 : rate;
		model.setMonthEnergyRate(doubleToString(rate));
		
		
		
		//本年度其他费用
		List<String> chartNames = new ArrayList<>();
		List<RptModel> chartDatas = new ArrayList<>();
		List<ChargingOverview> orderList=chartMapper.netReceiptsCharAtList(project_id,year+"-01-01",year+"-"+monthStr+"-"+dayStr,billType,"04");
		if(orderList !=null && orderList.size()>0){
			double orderAount=0.0d;
			for(int i=0;i<orderList.size();i++){
				orderAount=orderAount+(new Double(orderList.get(i).getAmount()));
				RptModel rpt = new RptModel();
				rpt.setName(orderList.get(i).getChargeName());
				rpt.setValue(Double.parseDouble(orderList.get(i).getAmount()));
				chartDatas.add(rpt);
				chartNames.add(orderList.get(i).getChargeName() + ":¥ " + orderList.get(i).getAmount());
			}
			//其他费用总金额
			model.setOtherFee(orderAount+"");
		}else{
			model.setOtherFee(0.0+"");
		}
				
		model.setChartNames(chartNames);
		model.setChartData(chartDatas);
				
		//去年同期其他费用
		List<ChargingOverview> lastOrderList=chartMapper.netReceiptsCharAtList(project_id,lastYear+"-01-01",lastYear+"-"+monthStr+"-"+day,billType,"04");
		if(lastOrderList !=null && lastOrderList.size()>0){
			double orderAount=0.0d;
			for(int i=0;i<lastOrderList.size();i++){
				orderAount=orderAount+(new Double(lastOrderList.get(i).getAmount()));		
			}
			model.setpOtherFee(orderAount+"");
		}else{
			model.setpOtherFee(0.0+"");
		}
		
		
		String backView = BeanUtility.objectToJsonStr(model, false);
		return WriteToPage.setResponseMessage(backView, IfConstant.SERVER_SUCCESS);
		
	}
	// 物业收费
	public String queryPropertyMgrInfo(Map<String, String> view, String orgId, String project_id) throws AppException {
		PropertyFeeOverview model = new PropertyFeeOverview();
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		// String endDate= year+"-"+month+"-"+day +"";
		String endDateY = year + "-12-31";
		String startDateY = year + "-01-01";
		String nowDate = year + "-" + month + "-" + day;
		String endDateM = year + "-" + month + "-31";
		String startDateM = year + "-" + month + "-01";
		// 实收
		double payRentD = Double
				.valueOf(chartMapper.queryChargeReportSum("03304", project_id, null, startDateY, endDateM, "3"));
		// 应收
		double payExpectD = Double
				.valueOf(chartMapper.queryChargeReportSum("03303", project_id, null, startDateY, endDateM, "3"));
		double monthPercent = Double
				.valueOf(chartMapper.queryChargeReportSum("03304", project_id, null, startDateM, endDateM, "3"));
		// 实收金额
		model.setPropertyFee(payRentD + "");
		model.setpPropertyFee(chartMapper.queryChargeReportSum("03304", project_id, null, year - 1 + "-01-01",
				year - 1 + "-" + month + "-" + day, "3"));
		model.setmPropertyFee(monthPercent + "");
		double mRate = monthPercent / payExpectD;
		mRate = (Double.isNaN(mRate) || Double.isInfinite(mRate)) ? 0.0 : mRate;
		model.setMonthPropertyRate(doubleToString(mRate));
		// 应收
		payRentD = (payRentD != 0) ? payRentD : 0;

		double rate = payRentD / payExpectD;
		rate = (Double.isNaN(rate) || Double.isInfinite(rate)) ? 0.0 : rate;
		model.setPropertyRate(doubleToString(rate));
		model.setPropertyExpFee(payExpectD + "");
		// 能耗 实收03306 本月 应收03305
		double energyFeeD = Double
				.valueOf(chartMapper.queryChargeReportSum("03306", project_id, null, startDateY, endDateM, "3"));
		double energyExpectD = Double
				.valueOf(chartMapper.queryChargeReportSum("03305", project_id, null, startDateY, endDateM, "3"));
		monthPercent = Double
				.valueOf(chartMapper.queryChargeReportSum("03306", project_id, null, startDateM, endDateM, "3"));

		energyFeeD = (energyFeeD != 0) ? energyFeeD : 0;
		energyExpectD = (energyExpectD != 0) ? energyExpectD : 0;
		rate = energyFeeD / energyExpectD;
		rate = ((Double.isNaN(rate) || Double.isInfinite(rate)) || Double.isInfinite(rate)) ? 0.0 : rate;
		model.setEnergyRate(doubleToString(rate));
		model.setEnergyFee(energyFeeD + "");
		model.setEnergyExpFee(energyExpectD + "");
		model.setpEnergyFee(chartMapper.queryChargeReportSum("03306", project_id, null, year - 1 + "-01-01",
				year - 1 + "-" + month + "-" + day, "3"));
		model.setmEnergyFee(monthPercent + "");
		mRate = monthPercent / energyExpectD;
		mRate = (Double.isNaN(mRate) || Double.isInfinite(mRate)) ? 0.0 : mRate;
		model.setMonthEnergyRate(mRate*100 + "%");
		// 主类code 2001 type 0102
		List<String> chartNames = new ArrayList<>();
		List<RptModel> chartDatas = new ArrayList<>();
		double otherFee = 0.0;
		List<RptFeeModel> feeArr = chartMapper.queryOtherSumFees("03102", project_id, null, "0102", "1202",
				year + "-01-01", year + "-" + month + "-" + day, "", "1");
		List<RptFeeModel> feeArrP = chartMapper.queryOtherSumFees("03102", project_id, null, "0102", "1202",
				year - 1 + "-01-01", year - 1 + "-" + month + "-" + day, "", "1");
			if(feeArrP.isEmpty()){
				model.setpOtherFee("0.0");
			}else{
					for(RptFeeModel rptFeeModel : feeArrP){
						String feeValue = rptFeeModel.getCharge_code();
						feeValue = !StringUtils.isBlank(feeValue) ? feeValue : "0.0";
						otherFee += Double.valueOf(feeValue);
					}
			}
		
		otherFee = 0.0;
		
		if (!feeArr.isEmpty()) {
			for (RptFeeModel rptFeeModel : feeArr) {
				String feeValue = rptFeeModel.getCharge_code();
				feeValue = !StringUtils.isBlank(feeValue) ? feeValue : "0.0";
				chartNames.add(rptFeeModel.getCharge_name() + ":¥ " + feeValue);
				otherFee += Double.valueOf(feeValue);
			}
			model.setOtherFee(otherFee+"");
		} else {
			chartNames.add("其他费用:¥ " + "0.0");
			model.setOtherFee("0.0"+"");
		}
//		model.setOtherFee(chartMapper.queryChargeReportProcess("03102", project_id, null, "0102", "1202",
//				year + "-01-01", year + "-" + month + "-" + day, "", "1"));
//		model.setpOtherFee(chartMapper.queryChargeReportProcess("03102", project_id, null, "0102", "1202",
//				year - 1 + "-01-01", year - 1 + "-" + month + "-" + day, "", "1"));
////		model.setpOtherFee(chartMapper.queryChargeReportSum("03310", project_id, null, startDateY, endDateM, "3"));
//		List<String> chartNames = new ArrayList<>();
//		List<RptModel> chartDatas = new ArrayList<>();
//		List<RptFeeModel> feeArr = chartMapper.queryOtherFees(null, "1202", "0102");
//		if (!feeArr.isEmpty()) {
//			for (RptFeeModel rptFeeModel : feeArr) {
//				String feeValue = chartMapper.queryOtherChargeSum("03102", rptFeeModel.getCharge_id(), null, project_id,
//						startDateY, endDateM, "3");
//				feeValue = !StringUtils.isBlank(feeValue) ? feeValue : "0.0";
//				chartNames.add(rptFeeModel.getCharge_name() + ":¥ " + feeValue);
//			}
//
//		} else {
//			chartNames.add("其他费用:¥ " + "0.0");
//		}
		model.setChartNames(chartNames);
		for (String key : chartNames) {
			String value[] = key.split(":¥ ");
			RptModel rpt = new RptModel();
			rpt.setName(key);
			rpt.setValue(Double.parseDouble(value[1]));
			chartDatas.add(rpt);
		}
		model.setChartData(chartDatas);
		String backView = BeanUtility.objectToJsonStr(model, false);
		return WriteToPage.setResponseMessage(backView, IfConstant.SERVER_SUCCESS);
		// PropertyFeeOverview model = new PropertyFeeOverview();
		// model.setPropertyFee("6568");
		// model.setpPropertyFee("5876");
		// model.setmPropertyFee("333");
		// model.setEnergyFee("789");
		// model.setpEnergyFee("341");
		// model.setmEnergyFee("123");
		// model.setOtherFee("1111");
		// model.setpOtherFee("982");
		// model.setPeicFee("22");
		// model.setFamFee("44");
		// model.setZhinNFee("88");
		// model.setWeiyFee("999");
		// model.setBucFee("100");
		//
		// List<String> chartNames = new ArrayList<>();
		// chartNames.add("赔偿金:¥ " + model.getPeicFee());
		// chartNames.add("罚没金:¥ " + model.getFamFee());
		// chartNames.add("滞纳金:¥ " + model.getZhinNFee());
		// chartNames.add("违约金:¥ " + model.getWeiyFee());
		// chartNames.add("补偿金:¥ " + model.getBucFee());
		// model.setChartNames(chartNames);
		// List<RptModel> chartDatas = new ArrayList<>();
		// for (String key : chartNames) {
		// String value[] = key.split(":¥ ");
		// RptModel rpt = new RptModel();
		// rpt.setName(key);
		// rpt.setValue(Double.parseDouble(value[1]));
		// chartDatas.add(rpt);
		// }
		// model.setChartData(chartDatas);
		//
		// String backView = BeanUtility.objectToJsonStr(model, false);
		// return WriteToPage.setResponseMessage(backView,
		// IfConstant.SERVER_SUCCESS);
	}

	// 通行概况
	public String queryTrafficInfo(Map<String, String> view, String orgId, String project_id) throws AppException {
		return "";
		// LeasigOverviewM model = new LeasigOverviewM();
		// Calendar cal = Calendar.getInstance();
		// int day = cal.get(Calendar.DATE);
		// int month = cal.get(Calendar.MONTH) + 1;
		// int year = cal.get(Calendar.YEAR);
		// // String endDate= year+"-"+month+"-"+day +"";
		// String endDateY = year + "-12-31";
		// String startDateY = year + "-01-01";
		// String nowDate = year + "-" + month + "-" + day;
		// String endDateM = year + "-" + month + "-31";
		// String startDateM = year + "-" + month + "-01";
		// String rentAreaS = chartMapper.queryProjectCurrent("02201",
		// project_id, null, null, null);
		// String allAreaS = chartMapper.queryserviceReport("01201", project_id,
		// null, null, null);
		// double rentAreaD = Double.valueOf(rentAreaS != null ? rentAreaS :
		// "0");
		// double allAreaD = Double.valueOf(allAreaS != null ? allAreaS : "0");
		// double unRentAreaD = allAreaD - rentAreaD;
		// double rate = unRentAreaD / allAreaD;
		// rate = (Double.isNaN(rate)||Double.isInfinite(rate))?0.0:rate;
		//
		// model.setAllRentArea(allAreaS);
		// model.setUnRentArea(allAreaD - rentAreaD + "");
		// model.setRoomRate(rate*100 + "%");
		// // 去年同期
		// model.setpRoomRate(rate*100 + "%");
		// // 本年到期 合同
		// model.setSignContract(chartMapper.queryContractReportSum("02306",
		// project_id, null, startDateY, endDateY, "4"));
		// model.setmSignContract(
		// chartMapper.queryContractReportSum("02306", project_id, null,
		// startDateM, endDateM, "4"));
		// // 本年签约 合同
		// model.setSignContract(chartMapper.queryContractReportSum("02304",
		// project_id, null, startDateY, endDateY, "3"));
		// model.setmSignContract(
		// chartMapper.queryContractReportSum("02304", project_id, null,
		// startDateM, endDateM, "3"));
		// // 总租户
		// model.setAllTenant(chartMapper.queryCustomerReportSum("04301",
		// project_id, null, null, endDateY, "2"));
		// // 入户
		// model.setInTenant(chartMapper.queryCustomerReportSum("04302",
		// project_id, null, startDateY, endDateY, "3"));
		// model.setmInTenant(chartMapper.queryCustomerReportSum("04302",
		// project_id, null, startDateM, endDateM, "3"));
		// // 退租
		// model.setOutTenant(chartMapper.queryCustomerReportSum("04303",
		// project_id, null, startDateY, endDateY, "4"));
		// model.setmOutTenant(chartMapper.queryCustomerReportSum("04303",
		// project_id, null, startDateM, endDateM, "4"));
		// // 租金单价
		// List<String> list = new ArrayList<String>();
		// for (int i = 1; i <= month; i++) {
		// String tm = (i<10)?("0"+i):(i+"");
		// String monthFee = chartMapper.queryContractCurrentRentFee(year+tm,
		// project_id, "02203", "02207");
		// monthFee = StringUtils.isBlank(monthFee)?"0.0":monthFee;
		// list.add(monthFee);
		// }
		// model.setRentFeeNum(list.size() + "");
		// model.setRentFees(list);
		// String backView = BeanUtility.objectToJsonStr(model, true);
		// return WriteToPage.setResponseMessage(backView,
		// IfConstant.SERVER_SUCCESS);
	}
	
	public String doubleToString(Double sender){
//		new DecimalFormat("0.00").format(sender*100) +"%";
		BigDecimal bd = new BigDecimal(sender*100).setScale(2, BigDecimal.ROUND_HALF_UP);
		return  bd+"%";
	}
	
	/**
	 * 方法描述 : 四舍五入保留2位小数
	 * 创建人 :  llc
	 * 创建时间: 2018年3月23日
	 * @param dou
	 * @param i
	 * @return
	 */
	public double doublePattern(double dou,int i){
		BigDecimal bg = new BigDecimal(dou);
		dou= bg.setScale(i, BigDecimal.ROUND_HALF_UP).doubleValue();
		return dou;
	}

}
