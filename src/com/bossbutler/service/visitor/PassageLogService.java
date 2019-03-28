package com.bossbutler.service.visitor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.visitor.TransitLogMapper;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.PagingBounds;
import com.bossbutler.pojo.visitor.TransitLog;
import com.bossbutler.pojo.visitor.TransitLogIn;
import com.bossbutler.pojo.visitor.TransitLogMappingDto;
import com.bossbutler.pojo.visitor.TransitLogMyDto;
import com.bossbutler.service.BasicService;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.ReturnPageBean;
import com.bossbutler.util.WriteToPage;
import com.company.exception.AppException;
import com.company.util.BeanUtility;
import com.company.util.DateUtil;

@Service
public class PassageLogService extends BasicService {
	@Autowired
	private TransitLogMapper transitLogMapper;

	public String querylist(ControllerView view, Logger logger) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 实体内容转为对象
		TransitLogIn in = BeanUtility.jsonStrToObject(dataJson, TransitLogIn.class, true);
		PagingBounds page = this.getPagingBounds(in.getPageNo()+"",in.getPageSize()+"");// 分页
		TransitLog params = new TransitLog();
		if (StringUtils.isNotBlank(in.getVisitorId())) {
			params.setVisitorId(in.getVisitorId());
		}
		if (StringUtils.isNotBlank(in.getAccountId()+"")) {
			params.setAccountId(in.getAccountId());
		}
		List<TransitLog> list = transitLogMapper.queryPassageByPageList(params, page);
		ReturnPageBean<TransitLog> ret = this.getPageResultBean(list, page);
		// 生成返回实体json
		String resultData = BeanUtility.objectToJsonStr(ret, false);
		// 发送成功
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	/**
	 * 时间2017-04-13 访客通行记录
	 * @param view
	 * @param logger
	 * @return
	 * @throws AppException
	 */
	public String queryVisitor(ControllerView view, Logger logger) throws AppException {
		List<TransitLogMappingDto> retList = new ArrayList<>();
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 实体内容转为对象
		TransitLogIn in = BeanUtility.jsonStrToObject(dataJson, TransitLogIn.class, true);
		if (in.getVisitorId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_VISITOR_ID_IS_NOT_NULL, dateTime);
		}
		TransitLog params = new TransitLog();
		if (StringUtils.isNotBlank(in.getVisitorId())) {
			params.setVisitorId(in.getVisitorId());
		}
		List<TransitLogMyDto> list = transitLogMapper.queryByVisitorId(in.getVisitorId());
		if (list != null && list.size() >0) {
			retList =  this.createMappingLog(list);
		}
		// 生成返回实体json
		String resultData = BeanUtility.objectToJsonStr(retList, false);
		// 发送成功
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	public String queryMyList(ControllerView view, Logger logger) throws AppException {
		List<TransitLogMappingDto> retList = new ArrayList<>();
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 实体内容转为对象
		TransitLogIn in = BeanUtility.jsonStrToObject(dataJson, TransitLogIn.class, true);
		if (in.getAccountId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		if (StringUtils.isBlank(in.getDate())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		TransitLog params = new TransitLog();
		if (in.getAccountId() != null) {
			params.setAccountId(in.getAccountId());
		}
		if (StringUtils.isNotBlank(in.getDate())) {
			params.setDate(in.getDate());;
		}
		List<TransitLogMyDto> list = transitLogMapper.queryMyByAccountDate(params);
		if (list != null && list.size() >0) {
			retList =  this.createMappingLog(list);
		}
		// 生成返回实体json
		String resultData = BeanUtility.objectToJsonStr(retList, false);
		// 发送成功
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	public String queryMyInfoList(ControllerView view, Logger logger) throws AppException {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		// 实体内容转为对象
		TransitLogIn in = BeanUtility.jsonStrToObject(dataJson, TransitLogIn.class, true);
		if (in.getAccountId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		if (StringUtils.isBlank(in.getDate())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		TransitLog params = new TransitLog();
		if (in.getAccountId() != null) {
			params.setAccountId(in.getAccountId());
		}
		if (StringUtils.isNotBlank(in.getDate())) {
			params.setDate(in.getDate());;
		}
		List<TransitLogMyDto> list = transitLogMapper.queryMyInfoByAccountDate(params);
		// 生成返回实体json
		String resultData = BeanUtility.objectToJsonStr(list, false);
		// 发送成功
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	public static List<TransitLogMappingDto> createMappingLog(List<TransitLogMyDto> list) {
		List<TransitLogMappingDto> retList = new ArrayList<>();
		Map<String, List<TransitLogMyDto>> map = new HashMap<>();
		for (TransitLogMyDto mydto : list) {
			 String key = mydto.groupKey();
			 List<TransitLogMyDto> sub = map.get(key);
			 // 若子集合不存在，则重新创建一个新集合，并把当前Project加入，然后put到map中
             if (sub == null){
            	 sub = new ArrayList<>();
            	 sub.add(mydto);
                 map.put(key, sub);
             }else {
                // 若子集合存在，则直接把当前Project加入即可
            	 sub.add(mydto);
             }
		}
		for(String key : map.keySet()) {
			TransitLogMappingDto dto = new TransitLogMappingDto();
			dto.setDate(key);
			dto.setWeek(getWeekOfDate(DateUtil.string2Date(key, "yyyy-MM-dd")));
			dto.setTransitLog(map.get(key));
			retList.add(dto);
		} 
		//降序排列
		Collections.sort(retList, new Comparator<TransitLogMappingDto>() {  
            @Override  
            public int compare(TransitLogMappingDto o1, TransitLogMappingDto o2) {  
                int flag = o2.getDate().compareTo(o1.getDate());  
                return flag;   
            }  
        });  
		return retList;
	}
	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date dt) {
	    String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(dt);
	
	    int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
	    if (w < 0)
	        w = 0;
	
	    return weekDays[w];
	}
	public static void main(String[] args) throws AppException {
		List<TransitLogMyDto> list = new ArrayList<>();
		TransitLogMyDto d1 = new TransitLogMyDto();
		d1.setCurDate("2017-03-26");
		d1.setProjectName("p1");
		list.add(d1);
		TransitLogMyDto d2 = new TransitLogMyDto();
		d2.setCurDate("2017-03-26");
		d2.setProjectName("p1");
		list.add(d2);
		TransitLogMyDto d3 = new TransitLogMyDto();
		d3.setCurDate("2017-03-27");
		d3.setProjectName("p2");
		list.add(d3);
		TransitLogMyDto d4 = new TransitLogMyDto();
		d4.setCurDate("2017-03-27");
		d4.setProjectName("p2");
		list.add(d4);
		TransitLogMyDto d5 = new TransitLogMyDto();
		d5.setCurDate("2017-03-21");
		d5.setProjectName("p3");
		list.add(d5);
		TransitLogMyDto d6 = new TransitLogMyDto();
		d6.setCurDate("2017-03-21");
		d6.setProjectName("p3");
		list.add(d6);
		List<TransitLogMappingDto> retList = createMappingLog(list);
		Collections.sort(retList, new Comparator<TransitLogMappingDto>() {  
            @Override  
            public int compare(TransitLogMappingDto o1, TransitLogMappingDto o2) {  
                int flag = o2.getDate().compareTo(o1.getDate());  
                return flag;   
            }  
        });  
		System.out.println(BeanUtility.objectToJsonStr(retList, true) );
	}

}