package com.bossbutler.service.chart;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.chart.ChartMapper;
import com.bossbutler.pojo.BackView;
import com.bossbutler.pojo.chart.CardData;
import com.bossbutler.pojo.chart.ChartModel;
import com.bossbutler.pojo.chart.LeasingOverview;
import com.bossbutler.pojo.chart.PeopleData;
import com.bossbutler.pojo.chart.RptModel;
import com.bossbutler.pojo.chart.TrafficModel;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.WriteToPage;
import com.company.exception.AppException;
import com.company.util.BeanUtility;
import com.company.util.DateUtil;

@Service
public class ChartService {
	@Autowired
	private ChartMapper chartMapper;
	/**
	 *  租用概况
	 *  	-- 房源空置率  未租面积   总面积 
	 *  合计统计
	 *  	--本年到期合同   本年签约合同
	 *  租户统计
	 *  	--总租户数    户客户数 退租客户数
	 *  租金均价
	 * @param project_id
	 * @param emp_id-
	 * @param index
	 * @param createStartTime
	 * @param createEndTime
	 * @return
	 */
	public String transferInfo(String project_id, String emp_id, String index, String createStartTime,
			String createEndTime) {
		LeasingOverview leasingOverview = chartMapper.getTransferInfo();
		double notRentArea =  Integer.parseInt(leasingOverview.getFloorage_area())-Integer.parseInt(leasingOverview.getAvailable_area());
		double bernotRentArea =  Integer.parseInt(leasingOverview.getFloorage_area())-Integer.parseInt(leasingOverview.getBefore_available_area());
		double vacancyRate = Integer.parseInt(leasingOverview.getFloorage_area())/notRentArea;
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("piedata:{vacancyRate:'"+vacancyRate+"%',data:["+leasingOverview.getFloorage_area()+","+leasingOverview.getAvailable_area()+"]},");
		double lastvacancyRate = Integer.parseInt(leasingOverview.getFloorage_area())/bernotRentArea;
		sb.append("lastvacancyRate:'"+lastvacancyRate+"%',"); // 去年同期的房源空置率
		sb.append("notRentArea:"+notRentArea+",");// 未租面积 和上面data里的一样
		sb.append("RentArea:"+Integer.parseInt(leasingOverview.getFloorage_area())+",");// 总面积  和上面data里面的一样
		sb.append("expiratContract:{year:"+leasingOverview.getEndContranct()+",month:"+leasingOverview.getEndMContranct()+"},");// 到期合同，year为本年的，month为本月
		sb.append("signingContract :{year:"+leasingOverview.getBookContranct()+",month:"+leasingOverview.getBookMContranct()+"},");// 签约合同数，year为本年的，month为本月的
		sb.append("rentNumber:"+leasingOverview.getCustomerNum()+",");// 租户总数
		sb.append("innerConstomer:{year:"+leasingOverview.getAddCustomerNum()+",month:"+leasingOverview.getAddMCustomerNum()+"},");// 入住客户数，year为一共的，month为本月的
		sb.append("outerComstomer:{year:"+leasingOverview.getSubCustomerNum()+",month:"+leasingOverview.getSubMCustomerNum()+"},");// 退租客户数,year为一共的，month为本月的
		sb.append("bardata:{monthNumber:12,data:["+leasingOverview.getRentQuote()+"]}");
		sb.append("}");
		return WriteToPage.setResponseMessage(sb.toString(), IfConstant.SERVER_SUCCESS);
	}
	/**
	 * 租金分部 
	 * 		-- 实收租金	去年同期实收租金
	 * 	本月实收（元）
	 * 		--实收金额（元)	应收金额（元）
	 * 	其它费用概况
	 * 		--费用总金额		去年同期费用总金额      
	 * 			赔偿金       罚没金	滞纳金		违约金		补偿金	
	 * 		
	 * @param project_id
	 * @param emp_id
	 * @param index
	 * @param createStartTime
	 * @param createEndTime
	 * @return
	 */
	public String chargingOverview(String project_id, String emp_id, String index, String createStartTime,
			String createEndTime) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		//这个是渲染饼图name的数据，需要将金额和名字拼接成这样的格式，因为插件原因
		sb.append("pieName:['赔偿金:￥1000','罚没金:￥1000','滞纳金:￥2000','违约金:￥2000','补偿金:￥3000','其他:￥2000'],");
		
		sb.append("pieData:[{");
		sb.append("name:'赔偿金:￥1000',");
		sb.append(" value : 1000");
		sb.append("},{");
		sb.append("name :'罚没金:￥1000',");
		sb.append("value : 1000");
		sb.append("},{");
		sb.append(" name : '滞纳金:￥2000',");
		sb.append(" value : 2000");
		sb.append("}, {");
		sb.append("name:'违约金:￥2000',");
		sb.append("value:2000");
		sb.append("},{");
		sb.append("name:'补偿金:￥3000',");
		sb.append("value:3000");
		sb.append("},{");
		sb.append("name:'其他:￥1000',");
		sb.append("value:1000");
		sb.append("}],");
		
		sb.append("moneyReceived:'3,6000.000',");//实收租金
		sb.append("lastMoneyReceived:'410,000.000',");// 去年同期实收租金
		sb.append("realityMoney:'360,000.000',");// 实收金额
		sb.append("receivableMoney : '410,000.000',");// 去年实收金额
		sb.append("monthMoney:'7,800.00',");// 本月实收
		sb.append("realityPercent:'72',");// 实收占应该收的百分比
		sb.append("monthPercent:'20',");// 这个月实收的占应该收的百分比
		sb.append(" totalAmount:'10,000.000',");// 费用总金额
		sb.append("lastTotalAmount:'410,000.000',");// 去年同期费用总金额
		sb.append("}");
		return WriteToPage.setResponseMessage(sb.toString(), IfConstant.SERVER_SUCCESS);
	}
	/**
	 * 添加日子
	 * @param key
	 * @param i
	 * @return
	 */
	private String addDate(String key, int i){
		String[] date = key.split("-");
		int d = Integer.parseInt(date[2])+i;
		if("01".equals(date[1]) || "03".equals(date[1]) || "05".equals(date[1]) || "07".equals(date[1]) || "08".equals(date[1]) || "10".equals(date[1]) || "12".equals(date[1]) ){
			if(d<1){
				d=31;
				date[1]=Integer.parseInt(date[1])-1+"";
				if(Integer.parseInt(date[1])<1){
					date[0]=Integer.parseInt(date[0])-1+"";
				}
			}
			if(d>31){
				d=31;
				date[1]=date[1]+1+"";
				if(Integer.parseInt(date[1])>12){
					date[1]="12";
					date[0]=Integer.parseInt(date[0])+1+"";
				}
			}
			
		}
		if(d<10){
			return date[0]+"-"+date[1]+"-0"+d;
		}
		return date[0]+"-"+date[1]+"-"+d;
	}
	
	
	/**
	 * 方法描述 : 通行概况
	 *  楼宇概况
	 *  通行概况
	 *  未活动人员概况
	 * 创建人 :  llc
	 * 创建时间: 2018年4月9日
	 * @param projectId
	 * @return
	 * @throws AppException 
	 */
	public String trafficSurvey(String projectId,int days) throws AppException{
		String dateDay=DateUtil.format(new Date(), "yyyy-MM-dd");
		TrafficModel traModel=new TrafficModel();
		TrafficModel tm=chartMapper.getBuildTotal(projectId,dateDay);
		String blongVisiTotal=chartMapper.getBLongVisiTotal(projectId,dateDay);
		
		//楼宇概况 start
		String bTenPerTotal="0";//楼宇租户人数
		String bManaPerTotal="0";//楼宇管理人数
		int bPerTotal=0;//楼宇总人数
		String bLongVisiTotal=(blongVisiTotal==null || blongVisiTotal=="")?"0":blongVisiTotal;//楼宇长期访客人数
		
		
		if(tm !=null){
			bTenPerTotal=StringUtils.isBlank(tm.getbTenPerTotal())?"0":tm.getbTenPerTotal();
			bManaPerTotal=StringUtils.isBlank(tm.getbManaPerTotal())?"0":tm.getbManaPerTotal();
			bPerTotal=(new Integer(bTenPerTotal))+(new Integer(bManaPerTotal));
		}
		traModel.setbTenPerTotal(bTenPerTotal);
		traModel.setbManaPerTotal(bManaPerTotal);
		traModel.setbLongVisiTotal(StringUtils.isBlank(blongVisiTotal)?"0":blongVisiTotal);
		bPerTotal=bPerTotal+new Integer(bLongVisiTotal);
		traModel.setbPerTotal(bPerTotal+"");
		//楼宇概况 end
		
		
		//通行概况 start
		TrafficModel tmpn=chartMapper.getResidentTrafficePN(projectId,dateDay);
		//通行租户数
		String tTenPerTotal="0";
		// 通行管理数
		String tManaPerTotal="0";
		if(tmpn !=null){
			tTenPerTotal=StringUtils.isBlank(tmpn.gettTenPerTotal())?"0":tmpn.gettTenPerTotal();
			tManaPerTotal=StringUtils.isBlank(tmpn.gettManaPerTotal())?"0":tmpn.gettManaPerTotal();
		}
		traModel.settTenPerTotal(tTenPerTotal);
		traModel.settManaPerTotal(tManaPerTotal);
		
		
		// app邀请
		String appApply="0";
		// 前台办理
		String reception="0";
		// 自助办理
		String selfHelp="0";
		// 小程序办理
		String smallProgram="0";
		//通行临时访客人数
		int tShortVisiTotal=0;
		
		// 工程人员
		String engineer="0";
		// 快递人员
		String express="0";
		// 外卖人员
		String takeout="0";
		// 其他人员
		String otherProple="0";
		//通行长期访客人数
		int tLongVisiTotal=0;
		// 通行总人数
		int tPerTotal=0;
		
		TrafficModel tmsvt=chartMapper.getShVisiTrafficePN(projectId,dateDay);
		if(tmsvt!=null){
			// app邀请
			appApply=StringUtils.isBlank(tmsvt.getAppApply())?"0":tmsvt.getAppApply();
			// 前台办理
			reception=StringUtils.isBlank(tmsvt.getReception())?"0":tmsvt.getReception();
			// 自助办理
			selfHelp=StringUtils.isBlank(tmsvt.getSelfHelp())?"0":tmsvt.getSelfHelp();
			// 小程序办理
			smallProgram=StringUtils.isBlank(tmsvt.getSmallProgram())?"0":tmsvt.getSmallProgram();
		}
		tShortVisiTotal=Integer.parseInt(appApply)+Integer.parseInt(reception)+Integer.parseInt(selfHelp)+Integer.parseInt(smallProgram);
		
		TrafficModel tmlvt=chartMapper.getLongVisiTrafficePN(projectId,dateDay);
		if(tmlvt !=null){
			// 工程人员
			engineer=StringUtils.isBlank(tmlvt.getEngineer())?"0":tmlvt.getEngineer();
			// 快递人员
			express=StringUtils.isBlank(tmlvt.getExpress())?"0":tmlvt.getExpress();
			// 外卖人员
			takeout=StringUtils.isBlank(tmlvt.getTakeout())?"0":tmlvt.getTakeout();
			// 其他人员
			otherProple=StringUtils.isBlank(tmlvt.getOtherProple())?"0":tmlvt.getOtherProple();
		}
		
		tLongVisiTotal=Integer.parseInt(engineer)+Integer.parseInt(express)+Integer.parseInt(takeout)+Integer.parseInt(otherProple);
		// 通行总人数
		tPerTotal=Integer.parseInt(tTenPerTotal)+Integer.parseInt(tManaPerTotal)+tShortVisiTotal+tLongVisiTotal;
		List<ChartModel> sourceTypeList=new ArrayList<ChartModel>();
		
		ChartModel stcm=new ChartModel();
		stcm.setName("APP邀请");
		stcm.setValue(appApply);
		sourceTypeList.add(stcm);
		
		stcm=new ChartModel();
		stcm.setName("前台办理");
		stcm.setValue(reception);
		sourceTypeList.add(stcm);
		
		stcm=new ChartModel();
		stcm.setName("自助办理");
		stcm.setValue(selfHelp);
		sourceTypeList.add(stcm);
		
		stcm=new ChartModel();
		stcm.setName("小程序");
		stcm.setValue(smallProgram);
		sourceTypeList.add(stcm);
		
		List<ChartModel> visitorTypeList=new ArrayList<ChartModel>();
		ChartModel vtcm=new ChartModel();
		vtcm.setName("工程人员");
		vtcm.setValue(engineer);
		visitorTypeList.add(vtcm);
		
		vtcm=new ChartModel();
		vtcm.setName("快递人员");
		vtcm.setValue(express);
		visitorTypeList.add(vtcm);
		
		vtcm=new ChartModel();
		vtcm.setName("外卖人员");
		vtcm.setValue(takeout);
		visitorTypeList.add(vtcm);
		
		vtcm=new ChartModel();
		vtcm.setName("其他人员");
		vtcm.setValue(otherProple);
		visitorTypeList.add(vtcm);
		//通行临时访客人数
		traModel.settShortVisiTotal(tShortVisiTotal+"");
		//通行长期访客人数
		traModel.settLongVisiTotal(tLongVisiTotal+"");
		//通行总人数
		traModel.settPerTotal(tPerTotal+"");
		traModel.setSourceTypeList(sourceTypeList);
		traModel.setVisitorTypeList(visitorTypeList);
		//通行概况 start
		
		//未活动人数概况
		String jsonStr= getNoActivityTotal(projectId,dateDay,days);
		BackView backView=BeanUtility.jsonStrToObject(jsonStr,BackView.class,false);
		TrafficModel tmNoActive=BeanUtility.jsonStrToObject(backView.getData()+"",TrafficModel.class,false);
		if(tmNoActive !=null){
			traModel.setNoActiveTenTotal(tmNoActive.getNoActiveTenTotal());
			traModel.setNoActivelVisiTotal(tmNoActive.getNoActivelVisiTotal());
			traModel.setNoActiveTotal(tmNoActive.getNoActiveTotal());
		}else{
			traModel.setNoActiveTenTotal("0");
			traModel.setNoActivelVisiTotal("0");
			traModel.setNoActiveTotal("0");
		}
		return WriteToPage.setResponseMessage(traModel, IfConstant.SERVER_SUCCESS);
	}
	
	
	/**
	 * 方法描述 : 
	 * 创建人 :  llc
	 * 创建时间: 2018年4月9日
	 * @param projectId
	 * @param todays 未活动天数
	 * @return
	 */
	public String getNoActivityTotal(String projectId,String date,int days){
		//租户未活动天数
		String tenNoActiveTotal=chartMapper.getNoActiveTenantTotal(projectId,date,days);
		tenNoActiveTotal=StringUtils.isBlank(tenNoActiveTotal)?"0":tenNoActiveTotal;
		//长期访客未活动天数
		String noActiveVisitorTotal=chartMapper.getNoActiveVisitorTotal(projectId,date,days);
		noActiveVisitorTotal=StringUtils.isBlank(noActiveVisitorTotal)?"0":noActiveVisitorTotal;
		//未活动总数
		int noActiveTotal=Integer.parseInt(tenNoActiveTotal)+Integer.parseInt(noActiveVisitorTotal);
		TrafficModel traModel=new TrafficModel();
		traModel.setNoActiveTenTotal(tenNoActiveTotal);
		traModel.setNoActivelVisiTotal(noActiveVisitorTotal);
		traModel.setNoActiveTotal(noActiveTotal+"");
		return WriteToPage.setResponseMessage(traModel, IfConstant.SERVER_SUCCESS);
	}
	
	
	/**
	 * 通行概况
	 * 		今日通行人数   昨日通行人
	 * 通行人员统计
	 * 		内部人员	临时访客	长椅访客	其它
	 * 通行方式统计
	 * 		二维码通行	ic卡通行	id通行 	其它
	 * @param project_id
	 * @param emp_id
	 * @param index
	 * @param createStartTime
	 * @param createEndTime
	 * @return
	 * @throws Exception 
	 */
	public String trafficProfile(String project_id, String emp_id) throws Exception {
		
		//今日通行
		String todayPass = chartMapper.getTrafficeCurrent(project_id,DateUtil.format(new Date(), "yyyy-MM-dd"));
		String currentDate = DateUtil.getCurrentDate();
		String startTime = addDate(currentDate, -1);
		//昨日通行
		String lastPass = chartMapper.getTrafficeCurrent(project_id,startTime);
		
		Map<String,String> peapleType = chartMapper.getTrafficeTypePT(project_id,DateUtil.format(new Date(), "yyyy-MM-dd"));
		int inside = 0;//常驻人员
		int longside = 0;//长期访客
		int shortside=0;//临时访客
		int otherside = 0;//其他
		
		int chatCode = 0;//二维码
		int icCode = 0;// ic卡
		int pwCode=0;//密码
		int otherCode = 0;//其他
		
		if(peapleType !=null){
			inside =new Integer(String.valueOf(peapleType.get("inside")));//常驻人员
			longside =new Integer(String.valueOf(peapleType.get("longside")));//长期访客
			shortside =new Integer(String.valueOf(peapleType.get("shortside")));//临时访客
			otherside =new Integer(String.valueOf(peapleType.get("otherside")));//其他
			
			chatCode = new Integer(String.valueOf(peapleType.get("chatCode")));//二维码
			icCode =new Integer(String.valueOf(peapleType.get("icCode")));// ic卡
			pwCode=new Integer(String.valueOf(peapleType.get("pwCode")));//密码
			otherCode =new Integer(String.valueOf(peapleType.get("otherCode")));//其他
		}
	
		
		List<RptModel> cruentList  = chartMapper.getTrafficeCurrentList(project_id,currentDate);
		
		List<String> barData = new ArrayList<String>();
		int typeNum01 = 0;
		int typeNum02 = 0;
		int typeNum03 = 0;
		int typeNum04 = 0;
		int typeNum05 = 0;
		int typeNum06 = 0;
		int typeNum07 = 0;
		int typeNum08 = 0;
		for(RptModel r : cruentList){
			if(new Integer(r.getName())<8){
				typeNum01+=r.getValue();
				continue;
			}
			if(new Integer(r.getName())<10){
				typeNum02 +=r.getValue();
				continue;
			}
			
			if(new Integer(r.getName())<12){
				typeNum03 +=r.getValue();
				continue;
			}
		
			if(new Integer(r.getName())<14){
				typeNum04 +=r.getValue();
				continue;
			}
			
			if(new Integer(r.getName())<16){
				typeNum05 +=r.getValue();
				continue;
			}
			if(new Integer(r.getName())<18){
				typeNum06 +=r.getValue();
				continue;
			}
			
			if(new Integer(r.getName())<20){
				typeNum07 +=r.getValue();
				continue;
			}
			if(new Integer(r.getName())<24){
				typeNum08 +=r.getValue();
				continue;
			}
			
		}
		barData.add(0, typeNum01+"");
		barData.add(1, typeNum02+"");
		barData.add(2, typeNum03+"");
		barData.add(3, typeNum04+"");
		barData.add(4, typeNum05+"");
		barData.add(5, typeNum06+"");
		barData.add(6, typeNum07+"");
		barData.add(7, typeNum08+"");
		
		TrafficModel trafficModel = new TrafficModel();
		trafficModel.setTodayPass(todayPass);
		trafficModel.setLastPass(lastPass);
		trafficModel.setBarData(barData);
		
		List<String> peoNameList = new ArrayList<>();
		peoNameList.add("常驻人员:"+inside+"人次"); 
		peoNameList.add("长期访客:"+longside+"人次");
		peoNameList.add("临时访客:"+shortside+"人次");
		peoNameList.add("其他:"+otherside+"人次");
		trafficModel.setPerpleName(peoNameList);
		
		List<PeopleData> peoDataList = new ArrayList<>();
		PeopleData peoData = new PeopleData();
		peoData.setName("常驻人员:"+inside+"人次");
		peoData.setValue(inside+"");
		peoDataList.add(peoData);
		
		peoData = new PeopleData();
		peoData.setName("长期访客:"+longside+"人次");
		peoData.setValue(longside+"");
		peoDataList.add(peoData);
		
		peoData = new PeopleData();
		peoData.setName("临时访客:"+shortside+"人次");
		peoData.setValue(shortside+"");
		peoDataList.add(peoData);
		
		peoData = new PeopleData();
		peoData.setName("其他:"+otherside+"人次");
		peoData.setValue(otherside+"");
		peoDataList.add(peoData);
		
		trafficModel.setPeopleData(peoDataList);
		
		List<String> cardNameList = new ArrayList<>();
		cardNameList.add("二维码通行:"+chatCode+"人次");
		cardNameList.add("IC卡通行:"+icCode+"人次");
		cardNameList.add("密码通行:"+pwCode+"人次");
		cardNameList.add("其他:"+otherCode+"人次");
		trafficModel.setCardName(cardNameList);
		
		List<CardData> cardDateList = new ArrayList<>();
		CardData cardDate= new CardData();
		cardDate.setName("二维码通行:"+chatCode+"人次");
		cardDate.setValue(chatCode+"");
		cardDateList.add(cardDate);
		
		cardDate = new CardData();
		cardDate.setName("IC卡通行:"+icCode+"人次");
		cardDate.setValue(icCode+"");
		cardDateList.add(cardDate);
		
		cardDate = new CardData();
		cardDate.setName("密码通行:"+pwCode+"人次");
		cardDate.setValue(pwCode+"");
		cardDateList.add(cardDate);
		
		cardDate = new CardData();
		cardDate.setName("其他:"+otherCode+"人次");
		cardDate.setValue(otherCode+"");
		cardDateList.add(cardDate);
		trafficModel.setCardData(cardDateList);
		
		
		/*sb.append("{");
		sb.append("todayPass:"+todayPass+",");  // 今日通行人数
		sb.append("lastPass:"+lastPass+",");// 昨日通行人数
		sb.append("barData:"+barData.toString()+",");// 柱状图的数据需要两个小时一个间隔的数据
		sb.append("pieName1:['内部人员:"+inside+"人','访客:"+outside+"人','其他:"+otherside+"人'],");// 第一个饼状图的name 通行人员统计的
		sb.append("piedata1:[{");
		sb.append("name : '内部人员:"+inside+"人',");
		sb.append("value : "+inside+",");
		sb.append("},{");
		sb.append("name:'访客:"+outside+"人',");
		sb.append("value:"+outside+",");
		sb.append("},{");
		sb.append("name : '其他:"+otherside+"人',");
		sb.append("value : "+otherside+",");
		sb.append("}],"); // 第一个饼状图的data 通行人员统计的
		sb.append(" pieName2 : ['二维码通行:"+chatCode+"人','IC卡通行:"+icCode+"人','其他:"+otherCode+"人'],");// 第二个饼状图的name 通行方式统计
		sb.append("piedata2:[{");
		sb.append("name : '二维码通行:"+chatCode+"人',");
		sb.append("value : "+chatCode+","); 
		sb.append("},{");
		sb.append("name:'IC卡通行:"+icCode+"人',");
		sb.append("value:"+icCode+",");
		sb.append("},{");
		sb.append("name : '其他:"+otherCode+"人',");
		sb.append("value : "+otherCode+",");
		sb.append("}]");// 第二个饼状图的data 通行方式统计
		sb.append("}");*/
		return WriteToPage.setResponseMessage(trafficModel, IfConstant.SERVER_SUCCESS);
	}
	
	
	
	/**
	 * 通行概况（以前的报表）
	 * 		今日通行人数   昨日通行人
	 * 通行人员统计
	 * 		内部人员	临时访客	长椅访客	其它
	 * 通行方式统计
	 * 		二维码通行	ic卡通行	id通行 	其它
	 * @param project_id
	 * @param emp_id
	 * @param index
	 * @param createStartTime
	 * @param createEndTime
	 * @return
	 * @throws Exception 
	 */
	/*public String trafficProfile(String project_id, String emp_id) throws Exception {
		String todayPass = chartMapper.getTrafficeCurrent(project_id);
		String currentDate = DateUtil.getCurrentDate();
		String startTime = addDate(currentDate, -1);
		String lastPass = chartMapper.getTrafficeYesterdayCurrent(project_id,startTime);
		
		List<RptModel> peapleType = chartMapper.getTrafficeType(project_id,startTime,"01");
		int inside = 0;
		int outside = 0;
		int otherside = 0;
		for(RptModel rpt : peapleType){
			//and (report_code='05301' or report_code='05302' or report_code='05305' or report_code='05306') 
			if("05201".equals(rpt.getGroup()) || "05202".equals(rpt.getGroup()) ){
				inside+=rpt.getValue();
			}else if("05203".equals(rpt.getGroup()) || "05204".equals(rpt.getGroup()) ){
				outside+=rpt.getValue();
			}else {
				otherside+=rpt.getValue();
			}
		}
		int chatCode = 0;
		int icCode = 0;
		int otherCode = 0;
		List<RptModel> trafficType = chartMapper.getTrafficeType(project_id,startTime,"02");
		for(RptModel rp : trafficType){
			if("05205".equals(rp.getGroup()) || "05207".equals(rp.getValue())||"05205".equals(rp.getGroup()) || "05206".equals(rp.getValue())){
				chatCode+=rp.getValue();
			}else if("05206".equals(rp.getGroup()) || "05208".equals(rp.getValue()) ||"05206".equals(rp.getGroup()) || "05208".equals(rp.getValue())){
				icCode+=rp.getValue();
			}else{
				otherCode+=rp.getValue();
			}
		}

		List<RptModel> cruentList  = chartMapper.getTrafficeCurrentList(project_id,currentDate);
		String currentDate02 = currentDate+" 02:00:00";
		String currentDate04 = currentDate+" 04:00:00";
		String currentDate06 = currentDate+" 06:00:00";
		String currentDate08 = currentDate+" 08:00:00";
		String currentDate10 = currentDate+" 10:00:00";
		String currentDate12 = currentDate+" 12:00:00";
		String currentDate14 = currentDate+" 14:00:00";
		String currentDate16 = currentDate+" 16:00:00";
		String currentDate18 = currentDate+" 18:00:00";
		String currentDate20 = currentDate+" 20:00:00";
		String currentDate22 = currentDate+" 22:00:00";
		String currentDate24 = currentDate+" 24:00:00";
		List<String> barData = new ArrayList<String>();
		int typeNum01 = 0;
		int typeNum02 = 0;
		int typeNum03 = 0;
		int typeNum04 = 0;
		int typeNum05 = 0;
		int typeNum06 = 0;
		int typeNum07 = 0;
		int typeNum08 = 0;
		for(RptModel r : cruentList){
			 curr  =  DateUtil.compareDateStr(r.getName(), currentDate02, DateUtil.PATTERN_STANDARD);
			if(curr<0){
				typeNum +=r.getValue();
				if(typeNum!=0){
					barData.add(typeNum+"");
				}
				continue;
			}
			curr  =  DateUtil.compareDateStr(r.getName(), currentDate04, DateUtil.PATTERN_STANDARD);
			if(curr<0){
				typeNum +=r.getValue();
				if(typeNum!=0){
					barData.add(typeNum+"");
				}
				continue;
			}
			curr  =  DateUtil.compareDateStr(r.getName(), currentDate06, DateUtil.PATTERN_STANDARD);
			if(curr<0){
				typeNum +=r.getValue();
				if(typeNum!=0){
					barData.add(typeNum+"");
				}
				continue;
			}
			int curr  =  DateUtil.compareDateStr(r.getName(), currentDate08, DateUtil.PATTERN_STANDARD);
			if(curr<0){
				typeNum01 +=r.getValue();
				continue;
			}
			curr  =  DateUtil.compareDateStr(r.getName(), currentDate10, DateUtil.PATTERN_STANDARD);
			if(curr<0){
				typeNum02 +=r.getValue();
				continue;
			}
			curr  =  DateUtil.compareDateStr(r.getName(), currentDate12, DateUtil.PATTERN_STANDARD);
			if(curr<0){
				typeNum03 +=r.getValue();
				continue;
			}
			curr  =  DateUtil.compareDateStr(r.getName(), currentDate14, DateUtil.PATTERN_STANDARD);
			if(curr<0){
				typeNum04 +=r.getValue();
				continue;
			}
			curr  =  DateUtil.compareDateStr(r.getName(), currentDate16, DateUtil.PATTERN_STANDARD);
			if(curr<0){
				typeNum05 +=r.getValue();
				continue;
			}
			curr  =  DateUtil.compareDateStr(r.getName(), currentDate18, DateUtil.PATTERN_STANDARD);
			if(curr<0){
				typeNum06 +=r.getValue();
				continue;
			}
			curr  =  DateUtil.compareDateStr(r.getName(), currentDate20, DateUtil.PATTERN_STANDARD);
			if(curr<0){
				typeNum07 +=r.getValue();
				continue;
			}
			curr  =  DateUtil.compareDateStr(r.getName(), currentDate22, DateUtil.PATTERN_STANDARD);
			if(curr<0){
				typeNum08 +=r.getValue();
				continue;
			}
			curr  =  DateUtil.compareDateStr(r.getName(), currentDate24, DateUtil.PATTERN_STANDARD);
			if(curr<0){
				typeNum08 +=r.getValue();
				continue;
			}
			
		}
		barData.add(0, typeNum01+"");
		barData.add(1, typeNum02+"");
		barData.add(2, typeNum03+"");
		barData.add(3, typeNum04+"");
		barData.add(4, typeNum05+"");
		barData.add(5, typeNum06+"");
		barData.add(6, typeNum07+"");
		barData.add(7, typeNum08+"");
		if(!"".equals(todayPass) && null!=todayPass){
			int indexOf = todayPass.indexOf(".");
			if(indexOf>0){
				todayPass = todayPass.substring(0, indexOf);
			}
			
		}
		if(!"".equals(lastPass) && null!=lastPass){
			int indexOf = lastPass.indexOf(".");
			if(indexOf>0){
				lastPass =lastPass.substring(0, indexOf);
			}
		}
		TrafficModel trafficModel = new TrafficModel();
		trafficModel.setTodayPass(todayPass);
		trafficModel.setLastPass(lastPass);
		trafficModel.setBarData(barData);
		
		List<String> peoNameList = new ArrayList<>();
		peoNameList.add("内部人员:"+inside+"人"); 
		peoNameList.add("访客:"+outside+"人");
		peoNameList.add("其他:"+otherside+"人");
		trafficModel.setPerpleName(peoNameList);
		
		List<PeopleData> peoDataList = new ArrayList<>();
		PeopleData peoData = new PeopleData();
		peoData.setName("内部人员:"+inside+"人");
		peoData.setValue(inside+"");
		peoDataList.add(peoData);
		
		peoData = new PeopleData();
		peoData.setName("访客:"+outside+"人");
		peoData.setValue(outside+"");
		peoDataList.add(peoData);
		
		peoData = new PeopleData();
		peoData.setName("其他:"+otherside+"人");
		peoData.setValue(otherside+"");
		peoDataList.add(peoData);
		
		trafficModel.setPeopleData(peoDataList);
		
		List<String> cardNameList = new ArrayList<>();
		cardNameList.add("二维码通行:"+chatCode+"人");
		cardNameList.add("IC卡通行:"+icCode+"人");
		cardNameList.add("其他:"+otherCode+"人");
		trafficModel.setCardName(cardNameList);
		
		List<CardData> cardDateList = new ArrayList<>();
		CardData cardDate= new CardData();
		cardDate.setName("二维码通行:"+chatCode+"人");
		cardDate.setValue(chatCode+"");
		cardDateList.add(cardDate);
		
		cardDate = new CardData();
		cardDate.setName("IC卡通行:"+icCode+"人");
		cardDate.setValue(icCode+"");
		cardDateList.add(cardDate);
		
		cardDate = new CardData();
		cardDate.setName("其他:"+otherCode+"人");
		cardDate.setValue(otherCode+"");
		cardDateList.add(cardDate);
		trafficModel.setCardData(cardDateList);
		
		
		sb.append("{");
		sb.append("todayPass:"+todayPass+",");  // 今日通行人数
		sb.append("lastPass:"+lastPass+",");// 昨日通行人数
		sb.append("barData:"+barData.toString()+",");// 柱状图的数据需要两个小时一个间隔的数据
		sb.append("pieName1:['内部人员:"+inside+"人','访客:"+outside+"人','其他:"+otherside+"人'],");// 第一个饼状图的name 通行人员统计的
		sb.append("piedata1:[{");
		sb.append("name : '内部人员:"+inside+"人',");
		sb.append("value : "+inside+",");
		sb.append("},{");
		sb.append("name:'访客:"+outside+"人',");
		sb.append("value:"+outside+",");
		sb.append("},{");
		sb.append("name : '其他:"+otherside+"人',");
		sb.append("value : "+otherside+",");
		sb.append("}],"); // 第一个饼状图的data 通行人员统计的
		sb.append(" pieName2 : ['二维码通行:"+chatCode+"人','IC卡通行:"+icCode+"人','其他:"+otherCode+"人'],");// 第二个饼状图的name 通行方式统计
		sb.append("piedata2:[{");
		sb.append("name : '二维码通行:"+chatCode+"人',");
		sb.append("value : "+chatCode+","); 
		sb.append("},{");
		sb.append("name:'IC卡通行:"+icCode+"人',");
		sb.append("value:"+icCode+",");
		sb.append("},{");
		sb.append("name : '其他:"+otherCode+"人',");
		sb.append("value : "+otherCode+",");
		sb.append("}]");// 第二个饼状图的data 通行方式统计
		sb.append("}");
		return WriteToPage.setResponseMessage(trafficModel, IfConstant.SERVER_SUCCESS);
	}
*/
}
