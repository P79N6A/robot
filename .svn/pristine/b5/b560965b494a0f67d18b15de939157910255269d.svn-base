package com.bossbutler.mapper.chart;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.chart.LeasingOverview;
import com.bossbutler.pojo.chart.RptModel;
import com.bossbutler.pojo.chart.TrafficModel;

public interface ChartMapper {
	//租赁概况
	LeasingOverview getTransferInfo();
	
	
	//以前的报表
	//今日通行
	//String getTrafficeCurrent(@Param("project_id")String project_id);
	//昨天通行
	//String getTrafficeYesterdayCurrent(@Param("project_id")String project_id,@Param("startTime")String startTime);
	//List<RptModel> getTrafficeType(@Param("project_id")String project_id,@Param("startTime")String startTime, @Param("type")String type);
	//List<RptModel> getTrafficeCurrentList(@Param("project_id")String project_id, @Param("currentDate")String currentDate);
	
	
	//今日通行
	String getTrafficeCurrent(@Param("projectId")String projectId,@Param("date") String date);
	//昨天通行
	String getTrafficeYesterdayCurrent(@Param("project_id")String project_id,@Param("startTime")String startTime);
	
	/**
	 * 方法描述 : 查询各种通行人员和通行方式人次统计
	 * 创建人 :  llc
	 * 创建时间: 2018年4月8日
	 * @param projectId
	 * @param date
	 * @return
	 */
	Map<String,String> getTrafficeTypePT(@Param("projectId")String projectId,@Param("date") String date);
	
	/**
	 * 方法描述 : 查询常驻人员通行人数
	 * 创建人 :  llc
	 * 创建时间: 2018年4月8日
	 * @param projectId
	 * @param date
	 * @return
	 */
	TrafficModel getResidentTrafficePN(@Param("projectId")String projectId,@Param("date") String date);
	
	/**
	 * 方法描述 : 查询临时访客通行人数
	 * 创建人 :  llc
	 * 创建时间: 2018年4月10日
	 * @param projectId
	 * @param date
	 * @return
	 */
	TrafficModel getShVisiTrafficePN(@Param("projectId")String projectId,@Param("date") String date);
	
	/**
	 * 方法描述 : 查询长期访客通行人数
	 * 创建人 :  llc
	 * 创建时间: 2018年4月10日
	 * @param projectId
	 * @param date
	 * @return
	 */
	TrafficModel getLongVisiTrafficePN(@Param("projectId")String projectId,@Param("date") String date);
	
	List<RptModel> getTrafficeCurrentList(@Param("projectId")String project_id, @Param("date")String currentDate);
	
	
	/**
	 * 方法描述 : 查询楼宇在线人员情况
	 * 创建人 :  llc
	 * 创建时间: 2018年4月8日
	 * @param projectId
	 * @return
	 */
	TrafficModel getBuildTotal(@Param("projectId") String projectId,@Param("date") String date);
	
	/**
	 * 方法描述 :查询楼宇在线长期访客人数
	 * 创建人 :  llc
	 * 创建时间: 2018年4月9日
	 * @param projectId
	 * @param date
	 * @return
	 */
	String getBLongVisiTotal(@Param("projectId") String projectId,@Param("date") String date);
	
	/**
	 * 方法描述 : 租户未活动人数
	 * 创建人 :  llc
	 * 创建时间: 2018年4月9日
	 * @param projectId
	 * @param date
	 * @param days 
	 * @return
	 */
	String getNoActiveTenantTotal(@Param("projectId") String projectId,@Param("date") String date,@Param("days")int days);
	
	/**
	 * 方法描述 : 长期访客未活动人数
	 * 创建人 :  llc
	 * 创建时间: 2018年4月9日
	 * @param projectId
	 * @param date
	 * @param days
	 * @return
	 */
	String getNoActiveVisitorTotal(@Param("projectId") String projectId,@Param("date") String date,@Param("days")int days);
}
