package com.bossbutler.mapper.chart;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.chart.ChargingOverview;
import com.bossbutler.pojo.chart.RentModel;
import com.bossbutler.pojo.chart.RptFeeModel;
public interface ChartMapperM {
	
	
	/**
	 * 方法描述 : 查询出租面积
	 * 创建人 :  llc
	 * 创建时间: 2018年3月19日
	 * @param projectId 项目id
	 * @param date 时间格式为：yyyy-MM-dd
	 * @param contractType 01,租赁;02,物业;
	 * @return
	 */
	String queryLeaseholdArea(@Param("projectId") String projectId,@Param("date") String date,@Param("contractType")String contractType);
	

	/**
	 * 方法描述 :查看某项目所有房源总面价
	 * 创建人 :  llc
	 * 创建时间: 2018年3月19日
	 * @param projectId
	 * @param date
	 * @return
	 */
	String queryAllResourceArea(@Param("projectId") String projectId);
	
	/**
	 * 方法描述 : 查询到期合同数
	 * 创建人 :  llc
	 * 创建时间: 2018年3月19日
	 * @param type 01,本年度;02,本月;03,本天;
	 * @param projectId 项目id
	 * @param date 日期
	 * @Param contractType 01,租赁;02,物业;
	 * @return
	 */
	String queryExpiryContract(@Param("type")String type,@Param("projectId") String projectId,@Param("date") String date,@Param("contractType")String contractType);
	
	/**
	 * 方法描述 : 查询签约合同数
	 * 创建人 :  llc
	 * 创建时间: 2018年3月19日
	 * @param type 01,本年度;02,本月;03,本天;
	 * @param projectId 项目id
	 * @param date 日期
	 * @return
	 */
	String querySignContract(@Param("type")String type,@Param("projectId") String projectId,@Param("date") String date,@Param("contractType")String contractType);
	
	/**
	 * 方法描述 : 统计租户总数
	 * 创建人 :  llc
	 * 创建时间: 2018年3月19日
	 * @param projectId 项目id
	 * @param date 日期
	 * @return
	 */
	String queryTenantTotal(@Param("projectId") String projectId,@Param("date") String date,@Param("contractType")String contractType);
	
	/**
	 * 方法描述 : 统计入户数(截止到目前)
	 * 创建人 :  llc
	 * 创建时间: 2018年3月19日
	 * @param projectId 项目id
	 * @param date 日期
	 * @return
	 */
	String queryInTenantTotal(@Param("projectId") String projectId,@Param("date") String date,@Param("contractType")String contractType);
	
	/**
	 * 方法描述 : 统计入户
	 * 创建人 :  llc
	 * 创建时间: 2018年3月19日
	 * @param type type 01,本年度;02,本月;03,本天;
	 * @param projectId 项目id
	 * @param date 日期
	 * @return
	 */
	String queryInTenantTotalByType(@Param("type")String type,@Param("projectId") String projectId,@Param("date") String date,@Param("nowDate") String nowDate,@Param("contractType")String contractType);
	
	
	/**
	 * 方法描述 : 统计退租
	 * 创建人 :  llc
	 * 创建时间: 2018年3月20日
	 * @param type 01,本年度;02,本月;03,本天;
	 * @param projectId 项目id
	 * @param date 日期
	 * @param nowDate 截止到日期
	 * @return
	 */
	String queryOutTenantTotalByType(@Param("type")String type,@Param("projectId") String projectId,@Param("date") String date,@Param("nowDate") String nowDate,@Param("contractType")String contractType);
	
	/**
	 * 方法描述 : 查询租金情况
	 * 创建人 :  llc
	 * 创建时间: 2018年3月20日
	 * @param projectId
	 * @param date 年
	 * @return
	 */
	List<RentModel> queryRentList(@Param("projectId") String projectId,@Param("date") String date,@Param("contractType")String contractType);
	
	
	/**
	 * 方法描述 : 查询实收金额
	 * 创建人 :  llc
	 * 创建时间: 2018年3月21日
	 * @param projectId 项目id
	 * @param beginDate 
	 * @param endDate
	 * @param chargeType 费目类型：01,租金;02,物业费,03,能耗费；其他费目（除了租金）;
	 * @return
	 */
	List<ChargingOverview> netReceiptsCharAtList(@Param("projectId") String projectId,@Param("beginDate") String beginDate,
			@Param("endDate") String endDate,
			@Param("billType")String billType,
			@Param("chargeType")String chargeType);
	
	/**
	 * 方法描述 : 查询应收金额
	 * 创建人 :  llc
	 * 创建时间: 2018年3月21日
	 * @param projectId 项目id
	 * @param beginDate 
	 * @param endDate
	 * @param billType 账单类型
	 * @param chargeType 费目类型：01,租金;02,物业费,03,能耗费；其他费目（除了租金）;
	 * @return
	 */
	List<ChargingOverview> payableCharAtList(@Param("projectId") String projectId,@Param("beginDate") String beginDate,
			@Param("endDate") String endDate,
			@Param("billType")String billType,
			@Param("chargeType")String chargeType);
	
	
	
	
	// 组织列表
	List<MyChartOrg> queryOrgs(@Param("accountId") String accountId,@Param("type")String type);

	// rpt_service_report
	String queryserviceCurrent(@Param("code") String code, @Param("project_id") String project_id,
			@Param("org_id") String org_id, @Param("period_begin") String period_begin,
			@Param("period_end") String period_end);

	// rpt_project_current
	String queryProjectCurrent(@Param("code") String code, @Param("project_id") String project_id,
			@Param("org_id") String org_id, @Param("period_begin") String period_begin,
			@Param("period_end") String period_end);

	// rpt_contract_report
	String queryContractReportSum(@Param("code") String code, @Param("project_id") String project_id,
			@Param("org_id") String org_id, @Param("period_begin") String period_begin,
			@Param("period_end") String period_end, @Param("type") String type);
	String queryContractCurrent(@Param("code") String code, @Param("project_id") String project_id,
			@Param("org_id") String org_id, @Param("period_begin") String period_begin,
			@Param("period_end") String period_end, @Param("type") String type);
	// 日租金
	 String queryContractCurrentRentFee(@Param("date") String date, @Param("project_id") String project_id,
			 @Param("code_fee")String code_fee,@Param("code_num")String code_num);
	 String queryContractReportRentFee(@Param("date") String date, @Param("project_id") String project_id,
			 @Param("code_fee")String code_fee,@Param("code_num")String code_num);
	// rpt_coustomer_report
	String queryCustomerReportSum(@Param("code") String code, @Param("project_id") String project_id,
			@Param("org_id") String org_id, @Param("period_begin") String period_begin,
			@Param("period_end") String period_end, @Param("type") String type);
	String queryCustomerCurrentSum(@Param("code") String code, @Param("project_id") String project_id,
			@Param("org_id") String org_id, @Param("period_begin") String period_begin,
			@Param("period_end") String period_end, @Param("type") String type);

	// rpt_charge_report
	String queryChargeReportSum(@Param("code") String code, @Param("project_id") String project_id,
			@Param("org_id") String org_id, @Param("period_begin") String period_begin,
			@Param("period_end") String period_end, @Param("type") String type);

	// rpt_charge_process (charge_code/罚没金/滞纳金等)
	String queryChargeReportProcess(@Param("param_code") String param_code, @Param("project_id") String project_id,
			@Param("org_id") String org_id, @Param("charge_type") String charge_type,
			@Param("charge_id") String charge_id, @Param("period_begin") String period_begin,
			@Param("period_end") String period_end, @Param("type") String type, @Param("isOther") String isOther);
	// 其他费用 总额
	List<RptFeeModel> queryOtherSumFees(@Param("param_code") String param_code, @Param("project_id") String project_id,
			@Param("org_id") String org_id, @Param("charge_type") String charge_type,
			@Param("charge_id") String charge_id, @Param("period_begin") String period_begin,
			@Param("period_end") String period_end, @Param("type") String type, @Param("isOther") String isOther);
	
	// charge 查询 其他费名字
	List<RptFeeModel> queryOtherFees(@Param("org_id") String org_id, @Param("charge_id") String charge_id,
			@Param("charge_type") String charge_type);

	String queryOtherChargeSum(@Param("param_code") String param_code, @Param("charge_id") String charge_id,
			@Param("org_id") String org_id, @Param("project_id") String project_id,
			@Param("period_begin") String period_begin, @Param("period_end") String period_end,
			@Param("type") String type);
	
}
