package com.bossbutler.service.apk.notice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bossbutler.common.InitConf;
import com.bossbutler.mapper.apk.notice.ApkNoticeUpgradeMapper;
import com.bossbutler.mapper.project.ProjectMapper;
import com.bossbutler.pojo.apk.upgrade.ApkNoticeDto;
import com.bossbutler.pojo.apk.upgrade.ApkNoticeVo;
import com.bossbutler.pojo.apk.upgrade.ApkUpgradeResultBean;
import com.bossbutler.pojo.apk.upgrade.AppUpgradeLogPoJo;
import com.bossbutler.pojo.apk.upgrade.TbApkNoticePo;
import com.bossbutler.pojo.third.ComboProjectIdName;
import com.bossbutler.service.BasicService;
import com.bossbutler.service.myregional.ProjectService;
import com.company.exception.AppException;
import com.company.util.BeanUtility;
import com.company.util.CommonUtil;

@Service
public class ApkNoticeUpgradeService extends BasicService {

	private static final String AppsystemUpperIOS = "IOS";

	private static final String AppsystemUpperIOSCODE = "02";

	private static final String AppsystemUpperAndroid = "ANDROID";

	private static final String AppsystemUpperAndroidCODE = "01";

	@Autowired
	private ApkNoticeUpgradeMapper apkNoticeUpgradeMapper;
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private ProjectService projectService;

	/**
	 * 获取级别最高升级
	 * 
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	public ApkNoticeDto getHighestNoticeTypeTbApkNotice02(ApkNoticeVo vo) throws Exception {
		if(AppsystemUpperIOS.equals(vo.getAppSystem().toUpperCase())) {
			vo.setAppSystem(AppsystemUpperIOSCODE);
		} else if(AppsystemUpperAndroid.equals(vo.getAppSystem().toUpperCase())) {
			vo.setAppSystem(AppsystemUpperAndroidCODE);
		}
		//hu===
		String accountId = vo.getAccountId();
		List<ComboProjectIdName> pList =projectMapper.selectProjectIdNameByAccountId(StringUtils.isBlank(accountId) ? 0 : new Long(accountId));
		List<String> projectIds=new ArrayList<String>();
		for(ComboProjectIdName cpn:pList){
			projectIds.add(cpn.getProjectID()+"");
		}
		
		boolean boo1=false;
		boolean boo2=false;
		if(projectIds !=null && projectIds.size()>0 && projectIds.get(0) !=null){
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("type","70403");
			params.put("projectIds",projectIds);
			List<Map<String,String>> profileList2=projectMapper.getProfileByParam(params);
			for(Map profile:profileList2){
				Map<String,Object> map = BeanUtility.jsonStrToObject(profile.get("profile")+"",new HashMap<String,Object>().getClass(), true);
				if(map != null && map.get("version") != null){
					String version=map.get("version").toString();
					if(version.compareTo(vo.getAppVersion()) >0){
						System.out.println("比较结果："+version+"和"+vo.getAppVersion()+"="+version.compareTo(vo.getAppVersion()));
						boo1=true;
						long count=apkNoticeUpgradeMapper.getCountAccountUpdateByPId(profile.get("projectId")+"");
						if(count==0){
							boo2=true;
						}
					}
				}
				
			}
			
			
		}
		TbApkNoticePo apkNoticePo = null;
		if(boo1){
			if(boo2){
				apkNoticePo = apkNoticeUpgradeMapper.selectHighestNoticeTypeTbApkNotice(vo.getCode(), vo.getAppVersion(),
						vo.getAppSystem(), null);
			}else{
				// 首先获取该用户级别最高升级
				apkNoticePo = apkNoticeUpgradeMapper.selectHighestNoticeTypeTbApkNotice(vo.getCode(), vo.getAppVersion(),
						vo.getAppSystem(), vo.getAccountId());
				
			}	
		}
		ApkNoticeDto result= apkNoticePo2ApkNoticeDto(apkNoticePo, vo.getAppSystem());
		if(InitConf.getAppUpgradeLog().equals("01")){
			//保存请求日志
			AppUpgradeLogPoJo aul=new AppUpgradeLogPoJo();
			aul.setAccountId(vo.getAccountId());
			aul.setAppSystem(vo.getAppSystem());
			aul.setAppType(vo.getCode());
			RequestAttributes ra = RequestContextHolder.getRequestAttributes();
	        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
	        HttpServletRequest request = sra.getRequest();  //获取request 可以从中获取参数或cookie
	        Map<String, String[]> map = request.getParameterMap();  
			aul.setRequestParam(BeanUtility.objectToJsonStr(map,true));
			System.out.println(aul.getRequestParam());
			aul.setOldVersion(vo.getAppVersion());
			aul.setCreateTime(CommonUtil.dateToStr(new Date(),"yyyy-MM-dd"));
			if (apkNoticePo != null && StringUtils.isNotBlank(vo.getAppSystem())
					&& StringUtils.isNotBlank(apkNoticePo.getNoticeType())) {
				aul.setNoticeType(apkNoticePo.getNoticeType());
				aul.setNewVersion(apkNoticePo.getVersionName());
			}
			
			ApkUpgradeResultBean jv = new ApkUpgradeResultBean();
			ApkUpgradeResultBean aurpStr=jv.setMessageCodeDataSign("操作成功！", "000000000", result == null ? "{}" : result);
			aul.setResultParam(BeanUtility.objectToJsonStr(aurpStr, true));
			apkNoticeUpgradeMapper.insertAppUpgradeLog(aul);
			//end
		}
		
		
		return result;
	}

	/**
	 * 获取级别最高升级
	 * 
	 * @param vo
	 * @return
	 * @throws AppException 
	 */
	public ApkNoticeDto getHighestNoticeTypeTbApkNotice(ApkNoticeVo vo) throws AppException {
		if(AppsystemUpperIOS.equals(vo.getAppSystem().toUpperCase())) {
			vo.setAppSystem(AppsystemUpperIOSCODE);
		} else if(AppsystemUpperAndroid.equals(vo.getAppSystem().toUpperCase())) {
			vo.setAppSystem(AppsystemUpperAndroidCODE);
		}
		TbApkNoticePo apkNoticePo = null;
		long count = apkNoticeUpgradeMapper.selectCountAccountUpdate();
		if(count == 0) {// 如果没有针对用户的升级则获取级别最高升级
			apkNoticePo = apkNoticeUpgradeMapper.selectHighestNoticeTypeTbApkNotice(vo.getCode(), vo.getAppVersion(),
					vo.getAppSystem(), null);
		} else {// 首先获取该用户级别最高升级
			apkNoticePo = apkNoticeUpgradeMapper.selectHighestNoticeTypeTbApkNotice(vo.getCode(), vo.getAppVersion(),
					vo.getAppSystem(), vo.getAccountId());
		}
		ApkNoticeDto result=apkNoticePo2ApkNoticeDto(apkNoticePo, vo.getAppSystem());
		
		if(InitConf.getAppUpgradeLog().equals("01")){
			//保存请求日志
			AppUpgradeLogPoJo aul=new AppUpgradeLogPoJo();
			aul.setAccountId(vo.getAccountId());
			aul.setAppSystem(vo.getAppSystem());
			aul.setAppType(vo.getCode());
			RequestAttributes ra = RequestContextHolder.getRequestAttributes();
	        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
	        HttpServletRequest request = sra.getRequest();  //获取request 可以从中获取参数或cookie
	        Map<String, String[]> map = request.getParameterMap();  
			aul.setRequestParam(BeanUtility.objectToJsonStr(map,true));
			System.out.println(aul.getRequestParam());
			aul.setOldVersion(vo.getAppVersion());
			aul.setCreateTime(CommonUtil.dateToStr(new Date(),"yyyy-MM-dd"));
			if (apkNoticePo != null && StringUtils.isNotBlank(vo.getAppSystem())
					&& StringUtils.isNotBlank(apkNoticePo.getNoticeType())) {
				aul.setNoticeType(apkNoticePo.getNoticeType());
				aul.setNewVersion(apkNoticePo.getVersionName());
			}
			
			ApkUpgradeResultBean jv = new ApkUpgradeResultBean();
			ApkUpgradeResultBean aurpStr=jv.setMessageCodeDataSign("操作成功！", "000000000", result == null ? "{}" : result);
			aul.setResultParam(BeanUtility.objectToJsonStr(aurpStr, true));
			apkNoticeUpgradeMapper.insertAppUpgradeLog(aul);
			//end
		}
		
		return result;
	}
	
	/**
	 * ApkNotice包装成ApkNoticeDto
	 * 
	 * @param apkNoticePo
	 * @param appSystem
	 * @return
	 */
	private ApkNoticeDto apkNoticePo2ApkNoticeDto(TbApkNoticePo apkNoticePo, String appSystem) {
		if (apkNoticePo == null || StringUtils.isBlank(appSystem)
				|| StringUtils.isBlank(apkNoticePo.getNoticeType())) {
			return null;
		}
		ApkNoticeDto dto = new ApkNoticeDto();
		dto.setDisplayTime(apkNoticePo.getDisplayTime());
		if (AppsystemUpperIOS.equals(appSystem.toUpperCase()) || AppsystemUpperIOSCODE.equals(appSystem.toUpperCase())) {
			dto.setDownloadPath(apkNoticePo.getDownloadPathIos());
		} else {
			dto.setDownloadPath(apkNoticePo.getDownloadPathAndroid());
		}
		dto.setNoticeType(apkNoticePo.getNoticeType());
		dto.setNoticeWay(apkNoticePo.getNoticeWay());
		dto.setNoticeContent(apkNoticePo.getNoticeContent());
		dto.setFontColor(apkNoticePo.getFontColor());
		dto.setSkipPath(apkNoticePo.getSkipPath());
		dto.setHtmlTitle(apkNoticePo.getHtmlTitle());
		dto.setVersionName(apkNoticePo.getVersionName());
		dto.setTitle("发现新版本");
		return dto;
	}
	
}
