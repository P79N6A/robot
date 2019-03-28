package com.bossbutler.mapper.Information;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.basemapper.IBaseMapper;
import com.bossbutler.pojo.PagingBounds;
import com.bossbutler.pojo.Information.Information;
import com.bossbutler.pojo.Information.InformationGroup;
import com.bossbutler.pojo.Information.InformationIn;

public interface InformationMapper extends IBaseMapper<Information, Information, Information> {

	public List<Information> queryPageList(InformationIn in, PagingBounds page);

	/**
	 * 方法描述 :分组列表数据 创建人 : hzhang 创建时间: 2016年10月29日 下午1:12:49
	 * @param in
	 * @return
	 */
	public List<InformationGroup> queryGroupList(InformationIn in);

	/**
	 * 
	 * 方法描述 : 创建人 : hzhang 创建时间: 2016年11月19日 下午4:57:41
	 * @param id
	 * @return
	 */
	public Information findById(String id);

	/**
	 * 查询未读消息数量
	 * @param infoType 消息类型
	 * @param accountId 账号ID
	 * @return
	 */
	public String countUnReadByTypeAi(@Param("infoType")String infoType, @Param("accountId")String accountId, @Param("appCodeName") String appCodeName);


	/**
	 * 方法描述:动态查询
	 * 创建人: llc
	 * 创建时间: 2018年5月16日 下午12:01:48
	 * @param information
	 * @return List<Information>
	*/
	public List<Information> queryInfor(Information information);

}
