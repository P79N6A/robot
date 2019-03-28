package com.bossbutler.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bossbutler.common.InitConf;
import com.bossbutler.common.ZkCuratorService;
import com.bossbutler.jms.PushMessageService;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.PushMessageDto;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.WriteToPage;
import com.company.util.BeanUtility;

@RestController
public class PushMessageController {

	private static Logger logger = Logger.getLogger(MobileMsgController.class);

	@Autowired
	private PushMessageService pushMessageService;
	
	/*@Autowired*/
	private ZkCuratorService zkCuratorService;

	/**
	 * 推送消息
	 * 
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pushMessage/send", method = RequestMethod.POST)
	public String pushSendMessage(@ModelAttribute ControllerView view) {
		InterProcessLock lock = zkCuratorService.newLock("pushMessage");
		try {
			// 获得锁
			System.out.println("准备获得锁");
			lock.acquire();
			System.out.println("获得锁");

			List<String> receivers = new ArrayList<String>();
			receivers.add("809569246198763520");// yanbing
			receivers.add("809318711679913984");// senhao
			receivers.add("809315124421726208");// wangqiao
			Map<String, String> map = new HashMap<>();
			//map.put("requestUrl", InitConf.getSelfhost() + "/app/hpage/redit/frame/device/maintain/notify/detail/page/809315124421726208");
			//map.put("title", "运维通知详情页");
			//map.put("systemDate", System.currentTimeMillis() + "");
//			PushMessageDto dto = new PushMessageDto("0202", PushCategory.MAINTAIN_INF_DETAIL, "运维通知详情测试推送", "运维通知详情页", null);
			PushMessageDto dto = new PushMessageDto();
			dto.setMap(map);
			dto.setType("0202");
			dto.setTitle("运维通知详情页");
			dto.setWebHtml(InitConf.getSelfhost() + "/app/hpage/redit/frame/device/maintain/notify/detail/page/809315124421726208");
			// 推送到i包办应用的消息
//			boolean result = pushMessageService.iBaobanPushSendMessage(BeanUtility.objectToJsonStr(dto,false),PushMessage.BILL_RETURN, receivers);
			boolean result = pushMessageService.iBaobanPushSendMessage(BeanUtility.objectToJsonStr(dto,false), "运维通知详情页", receivers);
			if (result) {
				return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, "" + new Date().getTime());
			}
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, "" + new Date().getTime());
		} catch (Exception e) {
			logger.error("pushSendMessage参数obj转换json报错:" + e.getMessage(), e);
			return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR, null);
		} finally {
			try {
				System.out.println("准备释放锁");
				lock.release();
				System.out.println("释放锁");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
