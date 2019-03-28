package com.bossbutler.service.apk.notice;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.bossbutler.common.InitConf;
import com.bossbutler.mapper.Information.InformationLogMapper;
import com.bossbutler.mapper.Information.InformationMapper;
import com.bossbutler.pojo.inf.AppMainMessageDto;
import com.bossbutler.pojo.inf.AppMessageVo;
import com.bossbutler.pojo.inf.HotNoticeDto;
import com.bossbutler.pojo.inf.NormalNoticeDto;
import com.bossbutler.pojo.inf.PushAnnouncementsOpredVo;
import com.bossbutler.pojo.inf.PushAnnouncementsVo;
import com.bossbutler.pojo.inf.PushNoticeDto;
import com.bossbutler.pojo.inf.PushNoticePageDto;
import com.bossbutler.pojo.rest.ResMsg;
import com.bossbutler.pojo.rest.ResMsgCode;
import com.bossbutler.service.BasicService;

/**
 * 移动端app通知消息设置
 * 
 * @author horizon
 */
@Service
public class ApkNoticeMessageService extends BasicService {

	public static final Logger LOG = LoggerFactory.getLogger(ApkNoticeMessageService.class);

	@Autowired
	InformationMapper informationMapper;

	@Autowired
	InformationLogMapper informationLogMapper;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 获取手机主页通知公告消息
	 * 
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public AppMainMessageDto getMainMessageNotice(AppMessageVo vo) {

		LOG.debug("ApkNoticeMessageService获取手机主页通知公告消息，参数：" + vo.toString());

		ResMsg resMsg = restTemplate.getForObject(InitConf.getMsgMqpmHost() + "/rest/inf/announcements/main?accountId={accountId}&appVersion={appVersion}&code={code}&appSystem={appSystem}",
				ResMsg.class, vo.getAccountId(), vo.getAppVersion(), vo.getCode(), vo.getAppSystem());
		System.out.println("=============================llc"+resMsg);
		if(resMsg.getCode() == ResMsgCode.SUCCESS && resMsg.getObj() != null) {
			Map<String, Object> result = (Map<String, Object>) resMsg.getObj();
			AppMainMessageDto appMainMessageDto = new AppMainMessageDto();
			appMainMessageDto.setFocusNoticeNoReadCount((Integer) result.get("focusNoticeNoReadCount"));
			appMainMessageDto.setFocusNoticeTitleArray((List<NormalNoticeDto>) result.get("focusNoticeTitleObjArray"));
			if(result.get("hotNotice") != null) {
				HotNoticeDto hotNoticeDto = new HotNoticeDto();
				appMainMessageDto.setHotNotice(hotNoticeDto);
				Map<String, Object> hotNoticeDtoMap = (Map<String, Object>) result.get("hotNotice");
				if(hotNoticeDtoMap.get("infoId") != null) {
					hotNoticeDto.setInfoId((String) hotNoticeDtoMap.get("infoId"));
				}
				if(hotNoticeDtoMap.get("infoType") != null) {
					hotNoticeDto.setInfoType((String) hotNoticeDtoMap.get("infoType"));
				}
				if(hotNoticeDtoMap.get("title") != null) {
					hotNoticeDto.setTitle((String) hotNoticeDtoMap.get("title"));
				}
			}
			appMainMessageDto.setSystemInfoNoReadCount((Integer) result.get("systemInfoNoReadCount"));
			return appMainMessageDto;
		} else if(resMsg != null && resMsg.getCode() != ResMsgCode.SUCCESS) {
			LOG.warn(resMsg.getDes());
		}
		return null;
	}

	/**
	 * 通知推送消息列表
	 * 
	 * @param accountId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PushNoticePageDto getPushAnnouncements(PushAnnouncementsVo vo) {

		LOG.debug("ApkNoticeMessageService通知推送消息列表，参数：accountId=" + vo.getAccountId() + "restart=" + vo.getRestart() +
				"appCodeName=" + vo.getAppCodeName() + "page=" + vo.getPage() + "pageSize=" + vo.getPageSize());

		ResMsg resMsg = restTemplate.getForObject(InitConf.getMsgMqpmHost() + "/rest/inf/announcements/push?accountId={accountId}&restart={restart}&appCodeName={appCodeName}&page={page}&pageSize={pageSize}",
				ResMsg.class, vo.getAccountId(), vo.getRestart(), vo.getAppCodeName(), vo.getPage(), vo.getPageSize());
		if(resMsg != null && resMsg.getCode() == ResMsgCode.SUCCESS && resMsg.getObj() != null) {
			Map<String, Object> result = (Map<String, Object>) resMsg.getObj();
			PushNoticePageDto pushNoticePageDto = new PushNoticePageDto();
			pushNoticePageDto.setTotal((Integer) result.get("total"));
			pushNoticePageDto.setRows((List<PushNoticeDto>) result.get("rows"));
			return pushNoticePageDto;
		} else if(resMsg != null && resMsg.getCode() != ResMsgCode.SUCCESS) {
			LOG.warn(resMsg.getDes());
		}
		return null;
	}

	/**
	 * 通知推送消息列表已经读取
	 * 
	 * @param accountId
	 * @param infoIdsArray
	 * @throws URISyntaxException 
	 */
	@SuppressWarnings("rawtypes")
	public boolean updatePushAnnouncementsReaded(PushAnnouncementsOpredVo vo) throws URISyntaxException {
		LOG.debug("ApkNoticeMessageService通知推送消息列表已经读取，参数：accountId=" + vo.getAccountId() + "|infoIdsArray=" + vo.getInfoIds() + "|appCodeName=" + vo.getAppCodeName());

		LinkedMultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("accountId", vo.getAccountId());
        requestMap.add("appCodeName", vo.getAppCodeName());
        requestMap.add("infoIds", vo.getInfoIds());

        ResMsg resMsg = restTemplate.postForObject(InitConf.getMsgMqpmHost() + "/rest/inf/announcements/push/readed", requestMap, ResMsg.class);

		if(resMsg == null) {
			return false;
		}

		if(resMsg.getCode() == ResMsgCode.SUCCESS) {
			return true;
		} else {
			LOG.warn(resMsg.getDes());
		}
		return false;
	}

	/**
	 * 删除消息
	 * 
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean deletePushAnnouncements(PushAnnouncementsOpredVo vo) {
		LOG.debug("ApkNoticeMessageService通知推送消息列表已经读取删除，参数：accountId=" + vo.getAccountId() + "|infoIdsArray=" + vo.getInfoIds() + "|appCodeName=" + vo.getAppCodeName());

		LinkedMultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("accountId", vo.getAccountId());
        requestMap.add("appCodeName", vo.getAppCodeName());
        requestMap.add("infoIds", vo.getInfoIds());

        ResMsg resMsg = restTemplate.postForObject(InitConf.getMsgMqpmHost() + "/rest/inf/announcements/push/del", requestMap, ResMsg.class);
		if(resMsg == null) {
			LOG.warn("resMsg is null!!");
			return false;
		}
		if(resMsg.getCode() == ResMsgCode.SUCCESS) {
			LOG.info("resMsg is success!!");
			return true;
		} else {
			LOG.warn(resMsg.getDes());
		}
		return false;
	}

}
