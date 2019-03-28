package com.bossbutler.mapper.apk.notice;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.apk.upgrade.AppUpgradeLogPoJo;
import com.bossbutler.pojo.apk.upgrade.TbApkNoticePo;

public interface ApkNoticeUpgradeMapper {

	/**
	 * 获得最新升级版本
	 * 
	 * @param code
	 *            应用代码 IBB/BBGJ
	 * @param appVersion
	 *            版本号
	 * @param appSystem
	 *            手机系统 IOS/ANDROID
	 * @param accountId
	 *            帐号ID
	 * @return
	 */
	TbApkNoticePo selectHighestNoticeTypeTbApkNotice(@Param("code") String code, @Param("appVersion") String appVersion,
			@Param("appSystem") String appSystem, @Param("accountId") String accountId);

	int selectCountAccountUpdate();
	
	/**
	 * 方法描述:查询项目内账号有没有设置
	 * 创建人: llc
	 * 创建时间: 2018年11月13日 下午6:23:00
	 * @param projectId
	 * @return int
	*/
	int getCountAccountUpdateByPId(@Param("projectId")String projectId);
	
	/**
	 * 方法描述:保存请求日志
	 * 创建人: llc
	 * 创建时间: 2018年11月16日 下午3:30:04
	 * @param model
	 * @return int
	*/
	int insertAppUpgradeLog(AppUpgradeLogPoJo model);

}
