
package com.bossbutler.service.myregional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.myregional.Arrange;
import com.bossbutler.mapper.myregional.ArrangeMapper;
import com.bossbutler.mapper.myregional.MyRegionalMapper;
import com.bossbutler.mapper.system.EmpMapper;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.DevicePojo;
import com.bossbutler.pojo.MyArrangePojo;
import com.bossbutler.pojo.RegionalPojo;
import com.bossbutler.pojo.regional.ArrangeModel;
import com.bossbutler.pojo.system.EmpRelations;
import com.bossbutler.pojo.visitor.TransitLogMappingDto;
import com.bossbutler.pojo.visitor.TransitLogMyDto;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.MacConfirm;
import com.bossbutler.util.WriteToPage;
import com.company.exception.AppException;
import com.company.util.BeanUtility;
import com.company.util.DateUtil;
@Service
public class MyRegionalService {
	@Autowired
	private MyRegionalMapper remapper;
	
	@Autowired
	private ArrangeMapper arrangeMapper;
	
	@Autowired
	private EmpMapper empMapper;
	
	public String querymyregional(ControllerView view) throws Exception{
		String dataJson = view.getData();
		String mac = view.getMac();
		String dateTime = String.valueOf(new Date().getTime());
		RegionalPojo pojo = BeanUtility.jsonStrToObject(dataJson, RegionalPojo.class, true);
		if(pojo.getAccountId()==null){
			return	 WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		boolean checkMac = MacConfirm.checkMac(mac, dataJson, view.getDateTime());
		if (!checkMac) {
			return	 WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
		List<RegionalPojo> pojo1=remapper.querymyregional(pojo.getAccountId());
		if(pojo1==null)
			return WriteToPage.setResponseMessage("[]", IfConstant.SERVER_SUCCESS, dateTime);
		String resultData = BeanUtility.objectToJsonStr(pojo1, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	public String querymyarrange(ControllerView view) throws Exception{
		String dataJson = view.getData();
		String mac = view.getMac();
		String dateTime = String.valueOf(new Date().getTime());
		MyArrangePojo pojo = BeanUtility.jsonStrToObject(dataJson, MyArrangePojo.class, true);
		if(pojo.getRegionalId()==null){
			return	 WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		boolean checkMac = MacConfirm.checkMac(mac, dataJson, view.getDateTime());
		if (!checkMac) {
			return	 WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, dateTime);
		}
		MyArrangePojo mapModel=new MyArrangePojo();
		mapModel.setRegionalId(pojo.getRegionalId());
		List<MyArrangePojo> pojo1=remapper.querymyarrange(mapModel);
		if(pojo1==null)
			return WriteToPage.setResponseMessage("[]", IfConstant.SERVER_SUCCESS, dateTime);
		String resultData = BeanUtility.objectToJsonStr(pojo1, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	public String querymydevice(ControllerView view) throws Exception{
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		DevicePojo pojo = BeanUtility.jsonStrToObject(dataJson, DevicePojo.class, true);
		if(pojo.getArrangeId()==null){
			return	 WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL, dateTime);
		}
		List<DevicePojo> list=remapper.querymydevice(pojo.getArrangeId());
		if(list==null)
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
		String resultData = BeanUtility.objectToJsonStr(list, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	
	public String myRemotedevice(ControllerView view) throws Exception{
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		DevicePojo pojo = BeanUtility.jsonStrToObject(dataJson, DevicePojo.class, true);
		if(pojo.getArrangeId()==null){
			return WriteToPage.setResponseMessage("节点id缺失", IfConstant.CUSTOM, dateTime);
		}
		if(pojo.getAccountId()==null){
			return WriteToPage.setResponseMessage("用户id缺失", IfConstant.CUSTOM, dateTime);
		}
		
		// 内部通行区域节点
		List<Arrange> inlist = arrangeMapper.getInArrByAccId(pojo.getAccountId());
		// 该节点是否在通行区域内
		int isRemote=0;
		for(Arrange arr:inlist){
			if(pojo.getArrangeId().equals(arr.getArrangeId()+"")){
				isRemote=1;
				break;
			}
		}
		List<DevicePojo> list=remapper.querymydevice(pojo.getArrangeId());
		for(DevicePojo dp:list){
			dp.setIsRemote(isRemote);
		}
		if(list==null)
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
		String resultData = BeanUtility.objectToJsonStr(list, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	/**
	 * 方法描述 :(新我的通行区域1极)
	 * 创建人 :  hzhang
	 * 创建时间: 2016年11月2日 下午2:59:34
	 * @return
	 * @throws AppException 
	 *
	 */
	public String arrangeFirst(ControllerView view) throws AppException {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		DevicePojo pojo = BeanUtility.jsonStrToObject(dataJson, DevicePojo.class, true);
		if(StringUtils.isBlank(pojo.getAccountId())){
			return	 WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		List<MyArrangePojo> list=remapper.queryArrangeFirst(pojo.getAccountId());
		if(list==null)
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
		String resultData = BeanUtility.objectToJsonStr(list, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	/**
	 * 方法描述 :(新我的通行区域1极)
	 * 创建人 :  hzhang
	 * 创建时间: 2017年04月02日 下午2:59:34
	 * @return
	 * @throws AppException 
	 */
	public String arrangeFirstNew(ControllerView view) throws AppException {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		DevicePojo pojo = BeanUtility.jsonStrToObject(dataJson, DevicePojo.class, true);
		if(StringUtils.isBlank(pojo.getAccountId())){
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		
		List<MyArrangePojo> list = remapper.queryArrangeFirstNew(pojo.getAccountId());
		if(list==null)
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
		Map<String, List<MyArrangePojo>> retList = createMappingLog(list);
		String resultData = BeanUtility.objectToJsonStr(retList, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	
	/**
	 * 方法描述 :(新我的通行区域1极)
	 * 创建人 :  hzhang
	 * 创建时间: 2017年04月02日 下午2:59:34
	 * @return
	 * @throws AppException 
	 */
	public String arrangeFirstNew02(ControllerView view) throws AppException {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		DevicePojo pojo = BeanUtility.jsonStrToObject(dataJson, DevicePojo.class, true);
		if(StringUtils.isBlank(pojo.getAccountId())){
			return WriteToPage.setResponseMessage("用户id缺失", IfConstant.CUSTOM, dateTime);
		}
		//查询个人关联房源所在的节点
		List<Arrange> arrList=arrangeMapper.getResArrByAccId(pojo.getAccountId());
		Map<String,String> arrMap=new HashMap<String,String>(); //节点数组(节点去重)
		Map<String,List<Arrange>> resultMap= new HashMap<>();// 返回结果
		Map<String,String> proMap=new HashMap<String,String>();
		//租赁公共服务区
		List<String> projectIdList=new ArrayList<String>();
		
		
		if(arrList!=null && arrList.size()>0 ){
			for(Arrange arr:arrList){
				if(!proMap.containsKey(arr.getProjectId()+"")){//查找节点所在项目并去重
					proMap.put(arr.getProjectId()+"","");
					projectIdList.add(arr.getProjectId()+"");
					
				}
				//查询当前节点所有的父节点
				Arrange arrModel= arrangeMapper.getParentArrange(arr.getArrangeId()+"");
				String arrangeIds = arrModel.getArrangeIds();
				if("$".equals(arrangeIds)){
					continue;
				}
				
				arrangeIds = arrangeIds.substring(2);
				
				Arrange query=new Arrange();
				query.setEntrance_type("01");
				query.setArrange_type("02");
				query.setArrangeIds(arrangeIds);
				// 查找arrangeIds 的节点详情
				List<Arrange> parentArrange = arrangeMapper.queryDynamic(query);
				for(Arrange arrange:parentArrange){
					if(!arrMap.containsKey(arrange.getArrangeId()+"")){// 按照节点id 去重
						arrange.setProjectName(arr.getProjectName());
						arrMap.put(arrange.getArrangeId()+"","");
						// 按照项目id分组
						List<Arrange> arrProList=resultMap.get(arr.getProjectName()+"");
						arrange.setProjectId(null);
						arrange.setArrangeCode(null);
						arrange.setSupperId(null);
						if(arrProList==null){
							arrProList=new ArrayList<Arrange>();
							arrProList.add(arrange);
							resultMap.put(arr.getProjectName(),arrProList);
						}else{
							arrProList.add(arrange);
						}
					}
					
				}
			}
		}
		
		
		
		// 公共服务区节点
		List<Arrange> arrPublicList=new ArrayList<Arrange>();
		
		if(projectIdList.size()>0){
			Arrange query=new Arrange();
			query.setEntrance_type("04");
			query.setArrange_type("02");
			query.setProjectIds(projectIdList);
			arrPublicList=arrangeMapper.queryDynamic(query);
		}
		for(Arrange arrange:arrPublicList){
			if(!arrMap.containsKey(arrange.getArrangeId()+"")){// 按照节点id 去重
				arrMap.put(arrange.getArrangeId()+"","");
				// 按照项目id分组
				String projectName=arrange.getProjectName()+"";
				arrange.setProjectId(null);
				arrange.setArrangeCode(null);
				arrange.setSupperId(null);
				List<Arrange> arrProList=resultMap.get(projectName);
				if(arrProList==null){
					arrProList=new ArrayList<Arrange>();
					arrProList.add(arrange);
					resultMap.put(projectName,arrProList);
				}else{
					arrProList.add(arrange);
				}
			}
			
		}
		
		// 内部通行区域节点
		
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("accountId",pojo.getAccountId());
		params.put("dutyType","0301");
		List<EmpRelations> empList=empMapper.queryEmpByAccProPer(params);
		for(EmpRelations emp:empList){
			if(!proMap.containsKey(emp.getProjectId()+"")){//项目并去重
				proMap.put(emp.getProjectId()+"","");
				projectIdList.add(emp.getProjectId()+"");
			}
		}
		if(projectIdList.size()>0){
			Arrange query=new Arrange();
			query.setAccountId(pojo.getAccountId());
			query.setArrange_type("02");
			query.setProjectIds(projectIdList);
			List<Arrange> list =arrangeMapper.getInArrDynamic(query);
			if(list!=null && list.size()>0){
				for(Arrange arrange:list){
					if(!arrMap.containsKey(arrange.getArrangeId()+"")){// 按照节点id 去重
						arrMap.put(arrange.getArrangeId()+"","");
						// 按照项目id分组
						String projectName=arrange.getProjectName()+"";
						arrange.setProjectId(null);
						arrange.setArrangeCode(null);
						arrange.setSupperId(null);
						List<Arrange> arrProList=resultMap.get(projectName);
						if(arrProList==null){
							arrProList=new ArrayList<Arrange>();
							arrProList.add(arrange);
							resultMap.put(projectName,arrProList);
						}else{
							arrProList.add(arrange);
						}
					}
					
				}
			}
		}
		
		if(resultMap==null){
			return WriteToPage.setResponseMessage("[]", IfConstant.SERVER_SUCCESS, dateTime);
		}
		String resultData = BeanUtility.objectToJsonStr(resultMap, true);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	
	public static Map<String, List<MyArrangePojo>> createMappingLog(List<MyArrangePojo> list) {
		Map<String, List<MyArrangePojo>> map = new HashMap<>();
		for (MyArrangePojo mydto : list) {
			 String key = mydto.groupKey();
			 List<MyArrangePojo> sub = map.get(key);
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
		return map;
	}
}
