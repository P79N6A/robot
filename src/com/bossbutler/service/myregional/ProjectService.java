package com.bossbutler.service.myregional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.project.ProjectMapper;
import com.bossbutler.mapper.project.ProjectMediaMapper;
import com.bossbutler.pojo.project.Project;
import com.bossbutler.pojo.project.ProjectMedia;
import com.bossbutler.pojo.third.ComboProjectIdName;
import com.bossbutler.service.BasicService;
import com.company.exception.FileConst;
import com.company.util.DateUtil;

@Service
public class ProjectService extends BasicService {

	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private ProjectMediaMapper projectMediaMapper;

	public List<ComboProjectIdName> queryComboProjectNameId(String accountId) {
		return projectMapper.selectProjectIdNameByAccountId(accountId == null ? 0 : Long.parseLong(accountId));
	}

	public long queryCountByProjectIdAId(String accountId, String projectId) {
		return projectMapper.selectCountByAccountIdProjectId(accountId == null ? 0 : Long.parseLong(accountId), projectId == null ? 0 : Long.parseLong(projectId));
	}

	public Project queryProjectByProjectCode(String projectCode) throws Exception {
		return projectMapper.findByProjectCode(projectCode);
	}
	
	public List<String> getImagePath(String accountId){
		
		List<ComboProjectIdName> comboProjectIdNameList = this.queryComboProjectNameId(accountId);
		List<String> pathList=new ArrayList<>();
		for(ComboProjectIdName proModel:comboProjectIdNameList){
			List<ProjectMedia> list=projectMediaMapper.getMediaByProId(proModel.getProjectID()+"","61002","60001");
			for(ProjectMedia pm:list){
				String path = this.getFilePath(pm.getMediaId(), DateUtil.format(pm.getCreateTime(),"yyyy-MM-dd HH:mm:ss"), FileConst.ORIGINAL);
				if(StringUtils.isNotBlank(path)){
					pathList.add(path);
				}
				
			}
		}
		
		return pathList;
		
	}

	
	/**
	 * 方法描述:获取配置信息
	 * 创建人: llc
	 * 创建时间: 2018年11月8日 下午1:48:29
	 * @param projectId
	 * @param type
	 * @return String
	*/
	public String getConfigure(String projectId,String type){
		String result="";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("projectId",projectId);
		params.put("type",type);
		List<Map<String,String>> profileList=projectMapper.getProfileByParam(params);
		if(profileList !=null && profileList.size()>0){
			result=profileList.get(0).get("profile");
		}
		return result;
		
	}
	
	

}
