package com.bossbutler.mapper.customerservice;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.MyempPojo;
import com.bossbutler.pojo.PropertyServerPojo;

/**
 * 名称 : 
 * 描述 : 我的工单（物业端）
 * 创建人 :  xbj
 * 创建时间: 2016年9月5日 下午3:44:43
 * 
 */
public interface MyBillProMapper {
	List<PropertyServerPojo>  myproserver(@Param("accountId") String accountId,@Param("type") String type);
	int selectserviceprogressch(@Param("serverId")String serverId,@Param("accountId") String accountId);
	int updateserviceprogressch(@Param("serverId")String serverId,@Param("accountId") String accountId,@Param("status") String status);
	int updatereceiveserver(@Param("accountId") String accountId,@Param("serverId") String serverId,@Param("status") String status);
	int insertservprogress(@Param("accountId") String accountId,@Param("serverId") String serverId,@Param("progressId") String progressId,@Param("pretime") String pretime);
	List<MyempPojo> querymyemp(@Param("accountId") String accountId,@Param("serverId") String serverId);
	int insertbpservprogress(@Param("serverId") String serverId,@Param("progressId") String progressId,@Param("bpaccountId") String bpaccountId);
    int deleteService_progress(@Param("serverId") String serverId,@Param("status") String status);
}
