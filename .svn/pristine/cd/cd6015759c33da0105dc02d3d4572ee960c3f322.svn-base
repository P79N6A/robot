package com.bossbutler.service.mingdan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.common.constant.RolesConstant;
import com.bossbutler.mapper.mingdan.NameListMapper;
import com.bossbutler.mapper.myregional.Arrange;
import com.bossbutler.mapper.myregional.ArrangeMapper;
import com.bossbutler.mapper.myregional.MyRegionalMapper;
import com.bossbutler.mapper.org.OrgMapper;
import com.bossbutler.pojo.OrgModel;
import com.bossbutler.pojo.TransitList;
import com.company.util.DateUtil;
import com.bossbutler.util.enums.TransitTypeEnum;




@Service
public class NameListService {
	
	@Autowired
	private NameListMapper  nameListMapper;
	
	@Autowired 
	private ArrangeMapper arrangeMapper;
	
	@Autowired
	private OrgMapper orgMapper;
	
	@Autowired
	private MyRegionalMapper myRegionalMapper;
	
	public List<Map<String,String>> deviceTree(String orgId,String projectId) throws Exception{
		 
		List<Map<String,Object>> orgModel=nameListMapper.getOrgListByProId(projectId);
		
		List<String> orgIds=new ArrayList<String>();
		orgIds.add(orgId);
		List<Map<String,Object>> arrList=nameListMapper.getArrangeListByOrgIds(orgIds);
		List<Map<String,String>> deviceList=new ArrayList<Map<String,String>>();
		   
		  
		  //公共节点
		   for(Map<String,Object> arr:arrList ){
			   String status_code =nameListMapper.getParentArrange(arr.get("arrangeId")+"");
			  
			   if("$".equals(status_code)){
					continue;
				}
			   status_code = status_code.substring(2);
			   List<Map<String,Object>> parentArrange = nameListMapper.getArrangeByArrIdPrId(status_code,new Long(projectId));//arrangeMapper.getParentArrange(str.getArrangeId());
			   List<String> ArrIds=new ArrayList<String>();
			   if("01".equals(arr.get("entrance_type")) || "04".equals(arr.get("entrance_type"))){
					ArrIds.add(arr.get("arrangeId")+"");
				}
			   for(Map<String,Object> parr:parentArrange){
				   if("01".equals(parr.get("entrance_type")) || "04".equals(parr.get("entrance_type"))){
					   ArrIds.add(parr.get("arrange_id")+"");
				   }
			   }
			   
			   List<Map<String,Object>> deList=nameListMapper.getDeviceByArrIds(ArrIds);
			   for(Map<String,Object> d:deList){
				   boolean dex=true;
				   Map<String,String> dNew=new HashMap<String,String>();
				   for(Map<String,String> device:deviceList){
					   if(device.get("orgId").toString().equals(arr.get("orgId")) 
							   && device.get("deviceId").toString().equals(d.get("deviceId"))){
						  dex=false;
						  break;
					   }
				   }
				   if(dex){
					   dNew.put("orgId",arr.get("orgId")+"");
					   dNew.put("deviceId",d.get("deviceId")+"");
					   dNew.put("deviceName",d.get("deviceName")+"");
					   deviceList.add(dNew); 
				   }
			   }
			  
		   }
		   List<Map<String,Object>> pubArrange= nameListMapper.getArrangeByPId(projectId);
		   
		   
		   // 内部通行区域
		  
			   List<Map<String,Object>> regionalList =null;
			   Map<String,String> rModel=new HashMap<String,String>();
			   rModel.put("org_id",orgId);
			   regionalList = nameListMapper.getArrangeInsideTreeAll(orgId);
			   List<String> ArrIds=new ArrayList<String>();
			   for(Map<String,Object> am :regionalList){
				   ArrIds.add(am.get("arrange_id")+"");
			   }
			   for(Map<String,Object> pua:pubArrange){
				   ArrIds.add(pua.get("arrangeId")+"");
			   }
			   List<Map<String,Object>> deList=null;
			   if(ArrIds!=null && ArrIds.size()>0){
				   deList=nameListMapper.getDeviceByArrIds(ArrIds);
			   }
			   if(deList!=null){
			       for(Map<String,Object> d:deList){
			    	   boolean dex=true;
			    	   for(Map<String,String> device:deviceList){
			    		   if(device.get("orgId").equals(orgId) 
			    				   && device.get("deviceId").equals(d.get("deviceId"))){
			    			   dex=false;
			    			   break;
			    		   }
			    	   }
			    	   if(dex){
			    		   Map<String,String> dnew=new HashMap<String,String>();
			    		   d.put("orgId",orgId);
			    		   dnew.put("deviceId",d.get("deviceId")+"");
			    		   dnew.put("deviceName",d.get("deviceName")+"");
			    		   dnew.put("orgId",orgId);
			    		   deviceList.add(dnew); 
			    	   }
			       }
			     
			   }
		  
	  		return deviceList;
	  }
	

	public List<TransitList> synchCount(String projectId){
		
		List<TransitList> list=nameListMapper.synchCountGrDevice(projectId);
		List<TransitList> list2=nameListMapper.synchCountGrArrange(projectId);
		Map<String,List<TransitList>> map=new HashMap<String,List<TransitList>>();
		for(TransitList t:list){
			if(map.get(t.getArrangeId()+"")==null){
				List<TransitList> l=new ArrayList<TransitList>();
				l.add(t);
				map.put(t.getArrangeId()+"", l);
			}else{
				map.get(t.getArrangeId()+"").add(t);
			}
		}
		for(TransitList t2:list2){
			t2.setList(map.get(t2.getArrangeId()+""));
		}
		
		return list2;
	}
	
	public List<TransitList> getList(int status, String projectId, 
			String supperId,String treeId)
			throws Exception {
		List<TransitList> list = null;
		String orgId=supperId;
		String deviceId="";
		if(supperId==""||supperId==null){
			orgId=treeId;		
		}else{
			deviceId=treeId;
		}
		
		switch (status) {
		case 1: // 查看已同步 
			list=getResultPageList(supperId,treeId,projectId);
			break;
		case 2: // 获得已同步的名单
			list = nameListMapper.getSyncedList("",projectId,orgId,deviceId);
			break;
		case 3:// 获得同步失败的名单
			list = nameListMapper.getInvalidList("",projectId,orgId,deviceId);
			break;
		case 4: // 获得未同步的名单
			list = nameListMapper.getNoList("",projectId,orgId,deviceId);
			break;
		default:
			list = new ArrayList<TransitList>();
			
			break;
		}
		for(TransitList transitList:list){
			transitList.setTransitTypeDesc(TransitTypeEnum.getName(transitList.getTransitType()));
			if(StringUtils.isNotBlank(transitList.getBeginDate()) ){
				transitList.setBeginDate(transitList.getBeginDate().substring(0,10));
			}
			if(StringUtils.isNotBlank(transitList.getEndDate()) ){
				transitList.setEndDate(transitList.getEndDate().substring(0,10));
			}
		}
		return list;
	}
	
	
	 public List<TransitList> getResultPageList(String supperId,String treeId,String projectId) throws Exception{
		  String orgId=supperId;
		  String deviceId="";
		  List<TransitList> transitList=null;
		  
		  if(supperId==""||supperId==null){
			  orgId=treeId;	
			  List<String> orgIds=new ArrayList<String> ();
			  orgIds.add(orgId);
			  List<Arrange> arrList=arrangeMapper.getArrangeListByOrgIds(orgIds);
			  List<String> arrIds=new ArrayList<String>();
			  String arrIdStr="'";
			  List<Arrange> pubArrange= arrangeMapper.getArrangeByPId(projectId);
			  for(Arrange a: pubArrange){
				  arrIds.add(a.getArrangeId().toString());
				  arrIdStr=arrIdStr+a.getArrangeId().toString()+"','";
			  }
			  for(Arrange arr:arrList ){
				  Arrange arrangeModel= arrangeMapper.getParentArrange(arr.getArrangeId()+"");
				  String arrangeIds = arrangeModel.getArrangeIds();
				  if("$".equals(arrangeIds)){
						continue;
					}
				  arrangeIds = arrangeIds.substring(2);
				  List<Arrange> parentArrange = arrangeMapper.getArrangeByArrIdPrId(arrangeIds,new Long(projectId));//arrangeMapper.getParentArrange(str.getArrangeId());
				  if("01".equals(arr.getEntrance_type()) || "04".equals(arr.getEntrance_type())){
						arrIds.add(arr.getArrangeId().toString());
						arrIdStr=arrIdStr+arr.getArrangeId().toString()+"','";
				  }
				  for(Arrange parr:parentArrange){
					   if("01".equals(parr.getEntrance_type()) || "04".equals(parr.getEntrance_type())){
						   arrIds.add(parr.getArrangeId().toString());
						   arrIdStr=arrIdStr+parr.getArrangeId().toString()+"','";
					   }
				   }
			  }
			  
			  
			  if(arrIds.size()>0){
				  arrIdStr=arrIdStr.substring(0, arrIdStr.length()-2);
				  transitList=nameListMapper.getRegionalByOrgId(orgId,arrIdStr,null,projectId);  
			  }else{
				  transitList=nameListMapper.getRegionalPriByDeviceId(orgId,null,projectId);
			  }
			  
		  }else{
			 deviceId=treeId;
				
			 OrgModel orgModel=orgMapper.getOrgById(orgId);
			 if(orgModel==null){
				return new ArrayList<TransitList>();
			 }
			 if(orgModel.getDutyType().equals(RolesConstant.RoleType0301)){
				transitList=nameListMapper.getRegionalPriByDeviceId(orgId,deviceId,projectId);
			 }else{
					
				List<Arrange> list=myRegionalMapper.getArrangebyDeviceId(deviceId);
				if(list !=null && list.get(0) !=null){
					if(list.get(0).getEntrance_type().equals("01") || 
						list.get(0).getEntrance_type().equals("04")){
						transitList=nameListMapper.getRegionalPubByDeviceId(orgId,deviceId,projectId);
					}else if(list.get(0).getEntrance_type().equals("02") || 
									list.get(0).getEntrance_type().equals("03")){
						transitList=nameListMapper.getRegionalPriByDeviceId(orgId,deviceId,projectId);
					}
				}
					
				/*	List<Arrange> list=arrangeDeviceMapper.getArrangebyDeviceId(deviceId);
					if(list.get(0).getEntrance_type().equals("02") || 
							list.get(0).getEntrance_type().equals("03")){
						transitList=synchTransitMapper.getRegionalPriByDeviceIdPageList(orgId,deviceId,projectId,pagingBounds);	
					}else{
						transitList=synchTransitMapper.getRegionalByOrgIdPageList(orgId,null,deviceId,projectId,pagingBounds);
					}*/
			}
		}
		  
		  
		  
		  if(null !=transitList && transitList.size()>0){
			  for(TransitList tl: transitList ){
				  if(tl.getEmpStatusCode().equals("02")&& tl.getIsDeviceSynch().equals("1")){
					  tl.setStatusCode("03");
					  tl.setIsSynch("2");
					  tl.setBeginDate("");
					  tl.setEndDate("");
					 
					 
					  continue;
				  }
				  if(tl.getEmpStatusCode().equals("01")){
					  tl.setNewBeginDate(tl.getOldBeginDate());
					  tl.setNewEndDate(tl.getOldEndDate());
					
					  if(tl.getIsDeviceSynch().equals("0")){
						  tl.setStatusCode("01");
						  tl.setIsSynch("1");
						  tl.setNewBeginDate(tl.getBeginDate());
						  tl.setNewEndDate(tl.getEndDate());
						  continue;
					  }
					  int beingDateStr = DateUtil.compareDateStr(tl.getBeginDate(),tl.getOldBeginDate(), DateUtil.PATTERN_DATE);
					  int endDateStr = DateUtil.compareDateStr(tl.getEndDate(),tl.getOldEndDate(), DateUtil.PATTERN_DATE);
					  if((tl.getBeginDate().equals(tl.getOldBeginDate()) && tl.getEndDate().equals(tl.getOldEndDate())) || (beingDateStr>=0 && endDateStr<=0)){
						  tl.setIsSynch("0");
						
						  continue;
					  }
					  
					  if(beingDateStr<0){
						  tl.setNewBeginDate(tl.getBeginDate()); 
					  }
					  if(endDateStr>0){
						  tl.setNewEndDate(tl.getEndDate());
					  }
					  tl.setStatusCode("02");
					  tl.setIsSynch("1");
					
				  }
				}
			}		  
		  return transitList;
		  
	  }
}
