package com.bossbutler.service.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.mapper.account.AccountProfileMapper;
import com.bossbutler.mapper.dictionary.DictionaryDataMapper;
import com.bossbutler.mapper.project.ProjectMapper;
import com.bossbutler.mapper.system.AccountMapper;
import com.bossbutler.pojo.account.AccountProfileModel;
import com.bossbutler.pojo.dictionary.DictionaryDataModel;
import com.bossbutler.pojo.system.Account;
import com.bossbutler.pojo.third.ComboProjectIdName;
import com.bossbutler.service.BasicService;
import com.bossbutler.service.myregional.ProjectService;
import com.company.exception.AppException;
import com.company.util.BeanUtility;

@Service
public class AccountProfileService extends BasicService{
	@Autowired
	private AccountProfileMapper accountProfileMapper;
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private DictionaryDataMapper dictionaryDataMapper;
	@Autowired
	private AccountMapper accountMapper;
	
	
	
	public List<DictionaryDataModel> getLanguageList(String accountId) throws AppException{
		List<DictionaryDataModel> list=null;
		List<ComboProjectIdName> pList =projectMapper.selectProjectIdNameByAccountId(new Long(accountId));
		if(pList !=null && pList.size()>0){
			boolean boo =true;
			String profile1=projectService.getConfigure(pList.get(0).getProjectID()+"","70403");
			if(StringUtils.isNotBlank(profile1)){
				Map<String,Object> map1=BeanUtility.jsonStrToObject(profile1,new HashMap<String,Object>().getClass(), true);
				List<String> lageList1=(List<String>) map1.get("lageList");
				for(int i=1;i<pList.size();i++){
					String profile2=projectService.getConfigure(pList.get(i).getProjectID()+"","70403");
					if(StringUtils.isNotBlank(profile2)){
						Map<String,Object> map2=BeanUtility.jsonStrToObject(profile2,new HashMap<String,Object>().getClass(), true);
						List<String> lageList2=(List<String>) map2.get("lageList");
						lageList2.removeAll(lageList1);
						if(lageList2.size()>0){
							boo=false;
							break;
						}
					}else{
						boo=false;
					}
					
				}
				if(boo){
					if(lageList1 !=null && lageList1.size()>0){
						DictionaryDataModel ddm=new DictionaryDataModel();
						ddm.setDataIdList(lageList1);
						list=dictionaryDataMapper.queryDynamic(ddm);
					}
				}
			}
				
		}
		if(list==null || list.size()==0){
			list=new ArrayList<DictionaryDataModel>();
			DictionaryDataModel ddm=new DictionaryDataModel();
			ddm.setId("15001");
			ddm.setName("简体中文");
			list.add(ddm);
		}
		
		return list;
	}
	/**
	 * 方法描述:保存语言
	 * 创建人: llc
	 * 创建时间: 2018年11月8日 下午6:34:56
	 * @param accountId
	 * @param lageId
	 * @return
	 * @throws Exception int
	*/
	public int saveLanguage(String accountId,String lageId) throws Exception{
		AccountProfileModel model=new AccountProfileModel();
		model.setAccountId(accountId);
		model.setType("70201");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("language",lageId);
		model.setProfile(BeanUtility.objectToJsonStr(map, true));
		List<AccountProfileModel> list=accountProfileMapper.queryDynamic(model);
		if(list !=null && list.size()>0){
			model.setProfileId(list.get(0).getProfileId());
			return accountProfileMapper.update(model);
		}
		Account account=accountMapper.queryUserById(accountId);

		model.setProfileId(this.getIdWorker().nextId()+"");
		model.setName("项目语言配置");
		model.setAccountCode(account.getAccountCode()+"");
		model.setStatusCode("01");
		model.setOperatorId(account.getAccountCode()+"");
		return accountProfileMapper.insertDynamic(model);
		
		
	}

}
