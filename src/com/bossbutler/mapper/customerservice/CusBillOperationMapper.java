package com.bossbutler.mapper.customerservice;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.AddServerPojo;
import com.bossbutler.pojo.CommentsPojo;
import com.bossbutler.pojo.ProjectPojo;
import com.bossbutler.pojo.ServicerPojo;

/**
 * 名称 : 
 * 描述 : 租户对工单的操作
 * 创建人 :  xbj
 * 创建时间: 2016年9月5日 下午3:24:47
 * 
 */
public interface CusBillOperationMapper {
	int  updatecancel(@Param("accountId") String accountId,@Param("serverId") String serverId);
	int insertserviceprogresscancel(@Param("progressId") String progressId,@Param("accountId") String accountId,@Param("serverId") String serverId);
	ServicerPojo queryservicer(@Param("serverId") String serverId);
	int  insertservice_evaluation(CommentsPojo pojo);
	List<ProjectPojo> queryaddress(@Param("accountId") String accountId);
	int  insertserver(AddServerPojo addpojo);
	int  insertserverprogress(AddServerPojo addpojo);
}
