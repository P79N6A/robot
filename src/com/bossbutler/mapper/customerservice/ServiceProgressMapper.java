package com.bossbutler.mapper.customerservice;

import java.util.Map;

import com.bossbutler.pojo.bill.ServiceProgress;
/**
 * 服务单进程表
 * @author hzhang
 */
public interface ServiceProgressMapper {
	/**
	 * 方法描述 : 获取服务进度根据状态
	 * 创建人 :  hzhang
	 * 创建时间: 2016年10月8日 下午10:27:34
	 * @return
	 */
	ServiceProgress findByBillIdAndStatus(Map<String,Object> params);
	
	int insert(ServiceProgress sp);
	
}
