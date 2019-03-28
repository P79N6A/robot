package com.bossbutler.service.news;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bossbutler.mapper.news.NewsLogMapper;
import com.bossbutler.mapper.news.NewsMapper;
import com.bossbutler.mapper.project.ProjectMapper;
import com.bossbutler.pojo.news.NewsLogModel;
import com.bossbutler.pojo.news.NewsModel;
import com.bossbutler.pojo.third.ComboProjectIdName;
import com.bossbutler.service.BasicService;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.WriteToPage;
import com.company.exception.AppException;
import com.company.exception.FileConst;
import com.company.util.CommonUtil;

@Service
public class NewsService extends BasicService {

	@Autowired
	private NewsMapper newsMapper;
	
	@Autowired
	private NewsLogMapper newsLogMapper;
	
	@Autowired
	private ProjectMapper projectMapper;


	/**
	 * 方法描述:获取新闻
	 * 创建人: llc
	 * 创建时间: 2018年11月8日 上午10:44:53
	 * @param accountId
	 * @return List<NewsModel>
	 * @throws AppException 
	*/
	public List<NewsModel> getNewsList(NewsModel  model) throws AppException{
		
		List<ComboProjectIdName> comboProjectIdNameList =projectMapper.selectProjectIdNameByAccountId(model.getAccountId() == null ? 0 : Long.parseLong(model.getAccountId()));
		List<String> projectIds=new ArrayList<>();
		
		for(ComboProjectIdName proModel:comboProjectIdNameList){
			projectIds.add(proModel.getProjectID()+"");
		}
		
		model.setProjectIds(projectIds);
		List<NewsModel> nmList=newsMapper.queryDynamic(model);
		for(NewsModel nm:nmList){
			if(StringUtils.isNotBlank(nm.getMediaId()) && StringUtils.isNotBlank(nm.getMediaCreateTime())){
				String path = this.getFilePath(nm.getMediaId(), nm.getMediaCreateTime().substring(0,19), FileConst.ORIGINAL);
				if(StringUtils.isNotBlank(path)){
					String dateTime=String.valueOf(CommonUtil.strToDate(nm.getDataVersion(),"yyyy-MM-dd HH:mm:ss").getTime());
					nm.setMediaPath(path+"?dateTime="+dateTime);
				}
			}
			nm.setMediaCreateTime(null);
			nm.setMediaId(null);
		}
		return nmList;
		
	}

	/**
	 * 方法描述:存日志
	 * 创建人: llc
	 * 创建时间: 2018年11月11日 下午1:27:19
	 * @param accountId
	 * @param newsId
	 * @return
	 * @throws Exception String
	*/
	public String readNews(String accountId,String newsId) throws Exception{
		String dateTime = String.valueOf(new Date().getTime());
		NewsModel nm = newsMapper.findById(newsId);
		if (nm == null) {
			return WriteToPage.setResponseMessage("该新闻不存在", IfConstant.CUSTOM, dateTime);
		}
		NewsLogModel queryModel=new NewsLogModel();
		queryModel.setAccountId(accountId);
		queryModel.setNewsId(newsId);
		List<NewsLogModel> log= newsLogMapper.queryDynamic(queryModel);
		if (log != null && log.size()>0) {
			return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
		}
		
		NewsLogModel model = new NewsLogModel();
		model.setLogId(this.getIdWorker().nextId()+"");
		model.setAccountId(accountId);
		model.setNewsId(newsId);
		model.setStatusCode("02");
		model.setOperatorId(new Long(accountId));
		int i =newsLogMapper.insert(model );
		return WriteToPage.setResponseMessage("{}", IfConstant.SERVER_SUCCESS, dateTime);
	}
}
