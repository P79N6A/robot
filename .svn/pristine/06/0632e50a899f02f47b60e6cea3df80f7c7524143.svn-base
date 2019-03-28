package com.bossbutler.service.visitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.project.ProjectMapper;
import com.bossbutler.mapper.visitor.VisitorApplyMapper;
import com.bossbutler.pojo.visitor.VisitorApply;
import com.company.exception.AppException;
import com.company.util.BeanUtility;

@Service
public class VisitorService{
	
	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private VisitorApplyMapper visitorApplyMapper;
	
	public String updateTrafficStrategy(VisitorApply va) throws AppException{
		Map<String,String> params =new HashMap<String,String>();
		params.put("type","70402");
		params.put("projectId",va.getProjectId()+"");
		String projectProfile=projectMapper.getProjectProfileByParam(params);
		
		String trafficStrategy="";
		
		if(StringUtils.isNotBlank(projectProfile)){
			Map<String,Object> map=BeanUtility.jsonStrToObject(projectProfile,new HashMap<String,Object>().getClass() ,true);
			if(map.get("trafficStrategy") !=null && map.get("trafficStrategy")!=""){
				List<Object> obList=(List<Object>) map.get("trafficStrategy");
				for(Object obj:obList){
					Map modelMap=(Map) obj;
					if(modelMap.get("sourceType").equals(va.getSourceType())){
						modelMap.remove("sourceType");
						trafficStrategy=BeanUtility.objectToJsonStr(modelMap, true);
					}
				}
			}
			
		}
		return trafficStrategy;
	}
	
	
	

}
