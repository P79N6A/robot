package com.bossbutler.service.visitor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bossbutler.common.constant.ServiceConstant;
import com.bossbutler.mapper.project.ProjectMapper;
import com.bossbutler.mapper.visitor.InviteVisitorMapper;
import com.bossbutler.mapper.visitor.TerminalMapper;
import com.bossbutler.mapper.visitor.VisitorApplyMapper;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.FileResultOut;
import com.bossbutler.pojo.ImageFilePojo;
import com.bossbutler.pojo.ImageFileView;
import com.bossbutler.pojo.ImageInfoPojo;
import com.bossbutler.pojo.MobileMsgLog;
import com.bossbutler.pojo.MyvisitorPojo;
import com.bossbutler.pojo.project.Project;
import com.bossbutler.pojo.regional.VisitorMedia;
import com.bossbutler.pojo.regional.VisitorModel;
import com.bossbutler.pojo.visitor.Terminal;
import com.bossbutler.pojo.visitor.TerminalIn;
import com.bossbutler.pojo.visitor.VisitorIn;
import com.bossbutler.pojo.wx.MiniAppletIn;
import com.bossbutler.service.BasicService;
import com.bossbutler.util.CommonMobileMsgSend;
import com.bossbutler.util.GenerateCreater;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.MobileConstant;
import com.bossbutler.util.WriteToPage;
import com.common.cnnetty.gateway.util.HexUtil;
import com.company.exception.AppException;
import com.company.util.BeanUtility;
import com.company.util.CommonUtil;
import com.company.util.DateUtil;

@Service
public class VisitorMachineService extends BasicService {
	
	@Autowired
	private  TerminalMapper terminalMapper;
	@Autowired
	private  ProjectMapper projectMapper;
	@Autowired
	private  InviteVisitorMapper inviteVisitorMapper;
	@Autowired
	private  VisitorApplyMapper visitorApplyMapper;
	
	
	
	

	public String init(ControllerView view) throws AppException {
		String dataJson = view.getData();
		// 实体内容转为对象
		TerminalIn in = BeanUtility.jsonStrToObject(dataJson, TerminalIn.class, true);
		Terminal t = terminalMapper.findTerminalByParams(in);
		return WriteToPage.setResponseMessage(t, IfConstant.SERVER_SUCCESS);
	}

	/**
	 * 方法描述:访客机
	 * 创建人: llc
	 * 创建时间: 2019年3月19日 下午5:12:56
	 * @param view
	 * @return
	 * @throws Exception String 
	*/
	public String createVisitor(ImageFileView view) throws Exception {
		Map<String,String> retMap =  new  HashMap<String,String>();
		String dataJson = view.getData();
		MiniAppletIn in = BeanUtility.jsonStrToObject(dataJson, MiniAppletIn.class, true);
		//String visitorType=StringUtils.isNotBlank(in.getVisitorType())?in.getVisitorType():"01";
		String visitorType="01";
		Project project = this.projectMapper.findByPrimaryKey(in.getProjectId()+"");
		//查询改项目下是否已存在该访客
		VisitorIn vin = new VisitorIn();
		vin.setIdCardNo(in.getVisitorIdNo());
		vin.setProjectCode(project.getProjectCode());
		vin.setVisitorType(visitorType);
		List<VisitorModel> vlist = inviteVisitorMapper.queryVistorByParams(vin);
		if (vlist.size() >0 && vlist != null) {
			if(vlist.get(0).getStatus_code().equals("02")){
				return WriteToPage.setResponseMessage("访客状态异常", IfConstant.CUSTOM);
			}
			VisitorMedia vm = visitorApplyMapper.hasOtherIc(vlist.get(0).getVisitor_id()+"");
			ImageInfoPojo info = BeanUtility.jsonStrToObject(dataJson, ImageInfoPojo.class, true);
			info.setBussinessID(vlist.get(0).getVisitor_id()+"");
			view.setData(BeanUtility.objectToJsonStr(info, true));
			if(null == vm){
				this.updateServerImage(view,null,null);
			}else{
				this.updateServerImage(view,vm.getCreate_time(),vm.getMedia_id());
			}
			retMap.put("visitorId", vlist.get(0).getVisitor_id()+"");
			retMap.put("mobilePhone", vlist.get(0).getMobilephone());
			
			VisitorIn visitor = new VisitorIn();
			visitor.setVisitorId(vlist.get(0).getVisitor_id()+"");
			visitor.setCheckCnt("0");
			visitor.setName(in.getVisitorName());
			inviteVisitorMapper.updateDynamic(visitor);
			return WriteToPage.setResponseMessage(retMap, IfConstant.SERVER_SUCCESS);
		}
		VisitorModel visitor = new VisitorModel();
		visitor.setVisitor_type(visitorType);
		visitor.setVisitor_id(getIdWorker().nextId());
		visitor.setTransit_code(HexUtil.intToHex(Integer.parseInt(GenerateCreater.getTransitCode()), 4));
		visitor.setVisitor_name(in.getVisitorName());
		//visitor.setMobilephone(in.getMobilePhone());
		visitor.setProject_code(project.getProjectCode());
		visitor.setIdcard(in.getVisitorIdNo());
		visitor.setStatus_code("01");
		visitorApplyMapper.createVisitor(visitor);
		//访客相片
		ImageInfoPojo info = BeanUtility.jsonStrToObject(dataJson, ImageInfoPojo.class, true);
		info.setBussinessID(visitor.getVisitor_id()+"");
		view.setData(BeanUtility.objectToJsonStr(info, true));
		Map<String,String> map = this.updateServerImage(view,null,null);
		if(null ==map){
			return WriteToPage.setResponseMessageForError("图片上传失败！", IfConstant.UNKNOWN_ERROR.getCode());
		}
		VisitorMedia media = new VisitorMedia();
		media.setMedia_id(map.get("mediaID"));
		media.setVisitor_id(visitor.getVisitor_id()+"");
		media.setMedia_type("61107");//媒体类别（61107,访客身份证；61108，访客其他证件）
		media.setFile_type("png");
		media.setMedia_no(in.getVisitorIdNo());
		media.setMain_classify("60002");
		media.setUpdate_time(DateUtil.getCurrentDate());
		media.setOrdinal(1);
		media.setData_version(DateUtil.getCurrentDate());
		media.setRemark(in.getIdcardInfo());
		this.visitorApplyMapper.createVisitorMedia(media);
		retMap.put("visitorId", visitor.getVisitor_id()+"");
		retMap.put("mobilePhone", visitor.getMobilephone());
		return WriteToPage.setResponseMessage(retMap, IfConstant.SERVER_SUCCESS);
	}
	public String checkVisitorPhone(ImageFileView view, String appCodeName) throws Exception {
		Map<String,String> retMap =  new  HashMap<String,String>();
		String dataJson = view.getData();
		MiniAppletIn in = BeanUtility.jsonStrToObject(dataJson, MiniAppletIn.class, true);
		if (StringUtils.isBlank(in.getVisitorId())) {
			return WriteToPage.setResponseMessageForError("访客ID不能为空", "999999999");
		}
		/*if (StringUtils.isBlank(in.getMobilePhone())) {
			return WriteToPage.setResponseMessageForError("访客手机号码不能为空", "999999999");
		}*/
		retMap.put("visitorId", in.getVisitorId());
		VisitorIn vin = new VisitorIn();
		vin.setVisitorId(in.getVisitorId());
		List<VisitorModel> visitorList = inviteVisitorMapper.queryVistorByParams(vin);
		VisitorModel visitor = visitorList.get(0);
		if(visitor==null){
			return WriteToPage.setResponseMessageForError("访客不存在", "999999999");
		}
		if(visitor.getStatus_code().equals("02")){
			return WriteToPage.setResponseMessageForError("访客已禁用", "999999999");
		}
		//查询此项目下该手机是否已绑定其他访客
		if(StringUtils.isNotBlank(in.getMobilePhone())){
			vin.setVisitorId(null);
			vin.setPhone(in.getMobilePhone());
			vin.setProjectCode(visitor.getProject_code());
			vin.setVisitorType("01");
			List<VisitorModel> pvlist = inviteVisitorMapper.queryVistorByParams(vin );
			if (pvlist != null && pvlist.size() >0) {
				if(!(in.getMobilePhone().equals(visitor.getMobilephone()))){
				return WriteToPage.setResponseMessageForError("该手机号已经被其他访客<"+encryptName(pvlist.get(0).getVisitor_name().trim())+">绑定", "999999999");
				}
			}
			inviteVisitorMapper.updateMobilephone(in.getMobilePhone(),Long.parseLong(in.getVisitorId()));
		}
		
		
		/*//是否变更了手机号，是否是新办理，发送验证码
		if (StringUtils.isBlank(visitor.getMobilephone())
				||(StringUtils.isNotBlank(in.getMobilePhone()) && !in.getMobilePhone().equals(visitor.getMobilephone()))) {
			retMap.put("sendFlag", "1");//发过验证码
			// 获得手机识别码
			String sn = in.getMobilePhone();
			String mobilenu = in.getMobilePhone();
			if (StringUtils.isBlank(sn) || StringUtils.isBlank(mobilenu)) {
				return WriteToPage.setResponseMessage("{}", IfConstant.UNKNOWN_ERROR);
			}
			// 发送并返回验证码
			String rand = CommonUtil.getForRand();
			long now = new Date().getTime();
			String nu = rand + "," + now;

			MobileMsgLog mobilelog = new MobileMsgLog();
			mobilelog.setLogId(getIdWorker().nextId());
			mobilelog.setReceiverMobile(mobilenu);
			mobilelog.setSendTime(new Date());
			try {
				CommonMobileMsgSend.sendMobileCode(sn + mobilenu, mobilenu, new String[] { MobileConstant.VISITOR, rand, "5" }, "01",
						mobilelog, nu, 5, appCodeName);
			} catch (Exception e) {
				retMap.put("sendFlag", "0");//没有发验证码
			}
		}else{
			retMap.put("sendFlag", "0");//没有发验证码
		}*/
		retMap.put("qrCodeStr", retMap.get("visitorId")+"-"+"0");
		return WriteToPage.setResponseMessage(retMap, IfConstant.SERVER_SUCCESS);
	}
	/**     
	 * 姓名加密
	 * @param name
	 * @return
	 */
	private String encryptName(String name){
		if(StringUtils.isBlank(name)){
			return "**";
		}
		String retName = name.substring(0,1);
		String stars = "";
		for (int i = 1; i < name.length(); i++) {
			stars +="*";
		}
		return retName+stars;
	}
	private Map updateServerImage(ImageFileView view,String createTime,String mediaID) throws Exception {
		String dataJson = view.getData();
		List<MultipartFile> multipartFileList = view.getFileBytes();
		long bill_id = 0;
		Map<String,Object> map = new HashMap<String,Object>();
		System.out.println("fileData:>>>"+multipartFileList.size());
		if (multipartFileList != null  && multipartFileList.size()>0) {
			for (MultipartFile multipartFile : multipartFileList) {
				ImageInfoPojo info = BeanUtility.jsonStrToObject(dataJson, ImageInfoPojo.class, true);
				String fileType = info.getFileType() == null ? "png": info.getFileType();
				if (StringUtils.isBlank(info.getBussinessID())) {
					return null;
				}
				if (bill_id == 0) {
					bill_id = Long.parseLong(info.getBussinessID());
				}
				System.out.println("classify->>"+ServiceConstant.main_classify+"bussinessID->>"+info.getBussinessID()+"fileType->>"+fileType+"bytes->>"+multipartFile);
				FileResultOut fileResultOut = uploadFile(ServiceConstant.main_classify, info.getBussinessID(), false, false,
						multipartFile.getBytes(), "png", createTime, mediaID);
				System.out.println("fileResultOut:>>>"+BeanUtility.objectToJsonStr(fileResultOut, false));
				if (fileResultOut.isSuccess()) {
					String dataJson1 = fileResultOut.getData();
					ImageFilePojo pojo = BeanUtility.jsonStrToObject(dataJson1, ImageFilePojo.class, true);
					map.put("createTime",pojo.getCreateTime());
					map.put("filepath",pojo.getFilepath());
					map.put("mediaID", pojo.getMediaID());
					return map;
				}else{
					return null;
				}
			}
		}
		return null;
	}
   
}
