package com.bossbutler.pojo.system;

import java.io.Serializable;
import java.util.Date;

import com.common.cnnetty.gateway.util.HexUtil;
/**
 * @ClassName: AccountTransit
 * @Description: 账号通行表
 * @author hzhang
 * @date 2016年9月1日 下午2:16:22
 *
 */
public class AccountTransit implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long transitId;

    private Long accountId;
    /**
     * 通行类型（十六进制1个字节：00，qr；01，ic；02，pwd；03，IDCard；04，id卡）
     */
    private String transitType;
    /**
     * 通行码（十六进制序列或IC卡号)
     */
    private String transitCode;
    
    private String statusCode;

    private Date statusTime;

    private Date createTime;
    
    private Date updateTime;

    private Long operatorId;

    private Date dataVersion;

    
	public Long getTransitId() {
		return transitId;
	}

	public void setTransitId(Long transitId) {
		this.transitId = transitId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getTransitType() {
		return transitType;
	}

	public void setTransitType(String transitType) {
		this.transitType = transitType;
	}

	public String getTransitCode() {
		return transitCode;
	}

	public void setTransitCode(String transitCode) {
		this.transitCode = transitCode;
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

	public Date getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(Date dataVersion) {
		this.dataVersion = dataVersion;
	}

	public static void main(String[] args) {
		System.out.println(HexUtil.intToHex(1000000000, 4));;
	}
}