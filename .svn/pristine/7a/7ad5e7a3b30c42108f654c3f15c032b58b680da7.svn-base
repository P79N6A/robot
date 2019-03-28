package com.bossbutler.service.Information;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.common.constant.InformationType;
import com.bossbutler.mapper.Information.InformationLogMapper;
import com.bossbutler.mapper.Information.InformationMapper;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.PagingBounds;
import com.bossbutler.pojo.Information.Information;
import com.bossbutler.pojo.Information.InformationGroup;
import com.bossbutler.pojo.Information.InformationIn;
import com.bossbutler.pojo.Information.InformationLog;
import com.bossbutler.pojo.Information.InformationOut;
import com.bossbutler.service.BasicService;
import com.bossbutler.service.redisCache.AccountRedisCache;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.RedisConstant;
import com.bossbutler.util.RelativeDateFormat;
import com.bossbutler.util.ReturnPageBean;
import com.bossbutler.util.WriteToPage;
import com.company.common.redis.active.CacheManager;
import com.company.exception.AppException;
import com.company.util.BeanUtility;
import com.company.util.DateUtil;

@Service
public class InformationService extends BasicService {
	@Autowired
	InformationMapper informationMapper;
	@Autowired
	InformationLogMapper informationLogMapper;
	@Autowired
	private AccountRedisCache accountRedisCache;

	public String queryPageList(ControllerView view, String appCodeName) throws AppException {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		InformationIn in = BeanUtility.jsonStrToObject(dataJson, InformationIn.class, true);
		PagingBounds page = this.getPagingBounds(in.getPageNo()+"",in.getPageSize()+"");// 分页
		in.setAppCodeName(appCodeName);
		List<Information> list = informationMapper.queryPageList(in,page);
		List<InformationOut> outl = new ArrayList<>();
		for (Information i : list) {
			InformationOut o = new InformationOut();
			o.setInfoId(i.getInfoId());
			o.setInfoType(i.getInfoType());
			o.setInfoTitle(i.getInfoTitle());
			o.setContents(i.getContents());
			o.setInscribe(i.getInscribe());
			o.setIsRead(i.getIsRead());
			o.setInfoIdJs(i.getInfoId()+"");
			o.setDiplayTime(RelativeDateFormat.format(i.getCreateTime()));
			o.setCreateTime(DateUtil.date2String(i.getCreateTime(), DateUtil.PATTERN_STANDARD));
			outl.add(o);
		}
		ReturnPageBean<InformationOut> ret =  this.getPageResultBean(outl,page);
		String resultData = BeanUtility.objectToJsonStr(ret, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);

	}
	public String queryGroupList(ControllerView view, String appCodeName) throws AppException {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		InformationIn in = BeanUtility.jsonStrToObject(dataJson, InformationIn.class, true);
		in.setAppCodeName(appCodeName);
		List<InformationGroup> list = informationMapper.queryGroupList(in);
		if (list.size()>0 && list != null) {
			Map<String, Object> ret = accountRedisCache.getInformationCountByAccount(in.getAccountId(), appCodeName);
			for (InformationGroup i : list) {
				if (InformationType.InformationType01.equals(i.getInfoType()) ) {
					i.setUnreadCount(ret.get("sysInfoCount").toString());
				}
				if (InformationType.InformationType02.equals(i.getInfoType()) ) {
					i.setUnreadCount(ret.get("proInfoCount").toString());
				}
				if (InformationType.InformationType03.equals(i.getInfoType()) ) {
					i.setUnreadCount(ret.get("billInfoCount").toString());
				}
				if (InformationType.InformationType04.equals(i.getInfoType()) ) {
					i.setUnreadCount(ret.get("leaseInfoCount").toString());
				}
			}
		}
		String resultData = BeanUtility.objectToJsonStr(list, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}
	public String findById(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		InformationIn in = BeanUtility.jsonStrToObject(dataJson, InformationIn.class, true);
		Information info = informationMapper.findById(in.getId());
		if (info == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_INFO_NOT_EXIST, dateTime);
		}
		if (in.getAccountId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		InformationOut o = new InformationOut();
		o.setInfoId(info.getInfoId());
		o.setInfoIdJs(info.getInfoId()+"");
		o.setInfoType(info.getInfoType());
		o.setInfoTitle(info.getInfoTitle());
		o.setContents(info.getContents());
		o.setInscribe(info.getInscribe());
		o.setDiplayTime(RelativeDateFormat.format(info.getCreateTime()));
		o.setCreateTime(DateUtil.date2String(info.getCreateTime(), DateUtil.PATTERN_STANDARD));
		String resultData = BeanUtility.objectToJsonStr(o, false);
		//标记为已读
		InformationLog log = informationLogMapper.queryByInfoIdAndAccountId(in.getId(),in.getAccountId());
		if (log != null) {
			return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
		}
		InformationLog model = new InformationLog();
		model.setLogId(this.getIdWorker().nextId());
		model.setAccountId(Long.parseLong(in.getAccountId()));
		model.setInfoId(Long.parseLong(in.getId()));
		model.setStatusCode("01");
		model.setOperatorId(Long.parseLong(in.getAccountId()));
		int i = informationLogMapper.insert(model );
		if (i > 0) {
	    	String redisKey=String.format(RedisConstant.REDIS_MESSAGE_KEY,in.getAccountId(),info.getInfoType());
	    	System.out.println(redisKey);
	    	CacheManager.inDecrBy(redisKey, -1);
		}
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String queryCountByAccountId(ControllerView view, String appCodeName) throws AppException {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		InformationIn in = BeanUtility.jsonStrToObject(dataJson, InformationIn.class, true);
		Map<String, Object> ret = accountRedisCache.getInformationCountByAccount(in.getAccountId(), appCodeName);
		String resultData = BeanUtility.objectToJsonStr(ret, false);
		return WriteToPage.setResponseMessage(resultData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	public String read(ControllerView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		InformationIn in = BeanUtility.jsonStrToObject(dataJson, InformationIn.class, true);
		if (in.getAccountId() == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_ACCOUNT_ID_NULL, dateTime);
		}
		Information info = informationMapper.findById(in.getId());
		if (info == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARAM_INFO_NOT_EXIST, dateTime);
		}
		InformationLog log = informationLogMapper.queryByInfoIdAndAccountId(in.getId(),in.getAccountId());
		if (log != null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
		}
		InformationLog model = new InformationLog();
		model.setLogId(this.getIdWorker().nextId());
		model.setAccountId(Long.parseLong(in.getAccountId()));
		model.setInfoId(Long.parseLong(in.getId()));
		model.setStatusCode("01");
		model.setOperatorId(Long.parseLong(in.getAccountId()));
		int i = informationLogMapper.insert(model );
		if (i > 0) {
	    	String redisKey=String.format(RedisConstant.REDIS_MESSAGE_KEY,in.getAccountId(),info.getInfoType());
	    	System.out.println(redisKey);
	    	CacheManager.inDecrBy(redisKey, -1);
		}
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}

}
