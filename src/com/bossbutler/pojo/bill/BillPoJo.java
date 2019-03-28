package com.bossbutler.pojo.bill;
import java.util.Date;
import java.util.List;
import com.bossbutler.pojo.BaseModel;
public class BillPoJo extends BaseModel implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long billId;// 账单id
	private String billCode;
	private String billName;// 账单名称（标题）
	private Long contractId;// 合同id
	private String contractCode;
	private String contractDesc;// 合同描述
	private Long orgId;//组织id
	private Long empId;// 员工id
	private String orgName;// 组织名称
	private Long customerId;//客户id
	private String customerName;// 客户名称
	private String billType;// 账单类型（01，租赁账单；02，物业账单；）
	private String billDesc;// 账单描述
	private Date beginDate;// 账单开始时间
	private Date endDate;// 账单结束时间
	private Double initialAmount;// 初始账单金额(合同金额) 
	private Double actualAmount;// 实际账单金额(合同金额+费用明细金额+欠费金额)
	private Double chargeAmount;// 费用明细金额
	private Double arrearsAmount;// 欠费金额
	private Double deductAmount;// 抵扣金额
	private Double payAmount;// 本期应缴金额
	private String address;// 账单地址
	private String bankId;// 收款账户
	private String bankName;// 开户行
	private String bankDesc;// 账户名称
	private String bankNum;// 银行账号
	private Date billDate;// 账单生成日
	private String billDateBegin;// 账单生成日开始时间
	private String billDateEnd;// 账单生成日结束时间
	private String billDateDesc;// 账单生成日
	private Date payDate;// 缴费截止日
	private String payDateStr;// 缴费截止日
	private Date settleDate;// 账单结算日
	private String settleDateStr;// 账单结算日
	private String remark;// 备注信息
	private Long orderId;// 订单id
	private String statusCode;//账单状态
	private String statusCodeOld;// 状态变化之前的状态
	private String statusCodeDesc;//
	private Date statusTime;// 状态时间
	private String statusTimeStr;// 状态时间
	private Date createTime;// 创建时间
	private Date updateTime;// 修改时间 
	private Long operatorId;// 操作人
	private String operatorName;// 操作人
	private String keyword;//
	private String jspType;// save :保存 update：更新
	private String billHeader;// 账单页眉
	private String billInscribe;// 账单落款
    private String jsonData;
	private String beginDateFormat;// 账单开始时间 格式化
	private String endDateFormat;//账单结束时间   格式化

	private String autoFlag;// 0:手动,1:自动
	private java.lang.Integer ordinal;
	private List<BillDebtorModel> billDebtorModelList;  //账单应付列表

	private List<String> orgIds;// 组织列表
	private String accountId;
	
	
	public List<String> getOrgIds() {
		return orgIds;
	}
	public void setOrgIds(List<String> orgIds) {
		this.orgIds = orgIds;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getStatusCodeOld() {
		return statusCodeOld;
	}
	public void setStatusCodeOld(String statusCodeOld) {
		this.statusCodeOld = statusCodeOld;
	}
	public String getBillDateBegin() {
		return billDateBegin;
	}
	public void setBillDateBegin(String billDateBegin) {
		this.billDateBegin = billDateBegin;
	}
	public String getBillDateEnd() {
		return billDateEnd;
	}
	public void setBillDateEnd(String billDateEnd) {
		this.billDateEnd = billDateEnd;
	}

	private List<BillDebtorModel>  currentBillDebtorModelList;  //本期费用列表
	private List<BillDebtorModel> historyDebtorModelList;  //历史费用列表

	private List<BillDeductModel> billDeductModelList;//余额抵扣列表

	
	public String getBankDesc() {
		return bankDesc;
	}
	public void setBankDesc(String bankDesc) {
		this.bankDesc = bankDesc;
	}
	public String getBankNum() {
		return bankNum;
	}
	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}
	public String getStatusTimeStr() {
		return statusTimeStr;
	}
	public void setStatusTimeStr(String statusTimeStr) {
		this.statusTimeStr = statusTimeStr;
	}
	
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getBillName() {
		return billName;
	}
	public void setBillName(String billName) {
		this.billName = billName;
	}
	public String getBillHeader() {
		return billHeader;
	}
	public void setBillHeader(String billHeader) {
		this.billHeader = billHeader;
	}
	public String getBillInscribe() {
		return billInscribe;
	}
	public void setBillInscribe(String billInscribe) {
		this.billInscribe = billInscribe;
	}
	public String getJspType() {
		return jspType;
	}
	public void setJspType(String jspType) {
		this.jspType = jspType;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getAutoFlag() {
		return autoFlag;
	}
	public void setAutoFlag(String autoFlag) {
		this.autoFlag = autoFlag;
	}
	public List<BillDeductModel> getBillDeductModelList() {
		return billDeductModelList;
	}
	public void setBillDeductModelList(List<BillDeductModel> billDeductModelList) {
		this.billDeductModelList = billDeductModelList;
	}
	public Double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public Double getArrearsAmount() {
		return arrearsAmount;
	}
	public void setArrearsAmount(Double arrearsAmount) {
		this.arrearsAmount = arrearsAmount;
	}
	public Double getDeductAmount() {
		return deductAmount;
	}
	public void setDeductAmount(Double deductAmount) {
		this.deductAmount = deductAmount;
	}
	
	public String getPayDateStr() {
		return payDateStr;
	}
	public void setPayDateStr(String payDateStr) {
		this.payDateStr = payDateStr;
	}
	public String getSettleDateStr() {
		return settleDateStr;
	}
	public void setSettleDateStr(String settleDateStr) {
		this.settleDateStr = settleDateStr;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public String getContractDesc() {
		return contractDesc;
	}
	public void setContractDesc(String contractDesc) {
		this.contractDesc = contractDesc;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public String getStatusCodeDesc() {
		return statusCodeDesc;
	}
	public void setStatusCodeDesc(String statusCodeDesc) {
		this.statusCodeDesc = statusCodeDesc;
	}
	public String getBillDateDesc() {
		return billDateDesc;
	}
	public void setBillDateDesc(String billDateDesc) {
		this.billDateDesc = billDateDesc;
	}
	public Long getBillId() {
		return billId;
	}
	public void setBillId(Long billId) {
		this.billId = billId;
	}
	public String getBillCode() {
		return billCode;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	public Long getContractId() {
		return contractId;
	}
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getBillDesc() {
		return billDesc;
	}
	public void setBillDesc(String billDesc) {
		this.billDesc = billDesc;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Double getInitialAmount() {
		return initialAmount;
	}
	public void setInitialAmount(Double initialAmount) {
		this.initialAmount = initialAmount;
	}
	public Double getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}
	
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public Date getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public Date getStatusTime() {
		return statusTime;
	}
	public void setStatusTime(Date statusTime) {
		this.statusTime = statusTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public List<BillDebtorModel> getBillDebtorModelList() {
		return billDebtorModelList;
	}

	public void setBillDebtorModelList(List<BillDebtorModel> billDebtorModelList) {
		this.billDebtorModelList = billDebtorModelList;
	}


	public List<BillDebtorModel> getCurrentBillDebtorModelList() {
		return currentBillDebtorModelList;
	}

	public void setCurrentBillDebtorModelList(List<BillDebtorModel> currentBillDebtorModelList) {
		this.currentBillDebtorModelList = currentBillDebtorModelList;
	}

	public List<BillDebtorModel> getHistoryDebtorModelList() {
		return historyDebtorModelList;
	}

	public void setHistoryDebtorModelList(List<BillDebtorModel> historyDebtorModelList) {
		this.historyDebtorModelList = historyDebtorModelList;
	}


	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}


	public String getBeginDateFormat() {
		return beginDateFormat;
	}

	public void setBeginDateFormat(String beginDateFormat) {
		this.beginDateFormat = beginDateFormat;
	}

	public String getEndDateFormat() {
		return endDateFormat;
	}

	public void setEndDateFormat(String endDateFormat) {
		this.endDateFormat = endDateFormat;
	}

}
