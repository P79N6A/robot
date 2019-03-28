package com.bossbutler.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bossbutler.common.InitConf;
import com.bossbutler.common.SpringContextHolder;
import com.bossbutler.mapper.system.EmpMapper;
import com.bossbutler.pojo.FileResultOut;
import com.bossbutler.pojo.FileUploadParam;
import com.bossbutler.pojo.PagingBounds;
import com.bossbutler.pojo.PagingBoundsView;
import com.bossbutler.pojo.system.EmpRelations;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.MacConfirmFm;
import com.bossbutler.util.ReturnPage;
import com.bossbutler.util.ReturnPageBean;
import com.bossbutler.util.enums.IdWorkerBuider;
import com.company.exception.FileConst;
import com.company.util.BeanUtility;
import com.company.util.CommonUtil;
import com.company.util.IdWorker;

@Service
public class BasicService {

	@Autowired
	private RestTemplate restTemplate;

	public static HttpServletRequest getRequest() {
		if (RequestContextHolder.getRequestAttributes() != null) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			return request;
		}
		return null;
	}

	/**
	 * 获得id生成对象
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public IdWorker getIdWorker() throws Exception {
		return IdWorkerBuider.IDWORKER.getIdWorker();
	}

	/**
	 * 验证时间字符串格式输入是否正确 YYYY-MM-dd hh:mm:ss
	 * 
	 * @param timeStr
	 * @return
	 */
	public static boolean valiDateTimeWithLongFormat(String timeStr) {
		if (StringUtils.isBlank(timeStr)) {
			return false;
		}
		String format = "^([1-9][0-9]{3})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) "
				+ "([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(timeStr);
		return matcher.matches();
	}

	/**
	 * <p>
	 * 上传文件.
	 * </p>
	 * <p>
	 * 参数（mainClassify、bussinessID、fileType、medium（目前没有用到直接写false）、little（
	 * 目前没有用到直接写false）、fileBytes[、createTime、mediaID ]）.
	 * </p>
	 * <p>
	 * 注意如果需要覆盖原来文件需要creatTime, mediaID.
	 * </p>
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected FileResultOut uploadFile(String mainClassify, String bussinessID, boolean medium, boolean little,
			byte[] fileBytes, String fileType, String createTime, String mediaID) {

		FileResultOut fileResultOut = new FileResultOut();
		if (StringUtils.isBlank(mainClassify) || StringUtils.isBlank(bussinessID) || StringUtils.isBlank(fileType)
				|| fileBytes == null) {
			fileResultOut.setMessage("参数不完整");
			return fileResultOut;
		}

		// 生成临时文件
		File ftmp = new File(InitConf.getSelftmpsavepath());
		if (!ftmp.exists()) {
			ftmp.mkdir();
		}

		String tempFileName = InitConf.getSelftmpsavepath() + File.separator + CommonUtil.generateShortUuid() + "."
				+ fileType;
		try {
			FileUploadParam fileUploadParam = new FileUploadParam();
			fileUploadParam.setMainClassify(mainClassify);
			fileUploadParam.setBussinessID(bussinessID);
			fileUploadParam.setFileType(fileType);
			fileUploadParam.setCreateTime(createTime);
			fileUploadParam.setMediaID(mediaID);
			if (medium) {
				fileUploadParam.setMedium(FileConst.MEDIUM);
			}
			if (little) {
				fileUploadParam.setMedium(FileConst.LITTLE);
			}
			String data = BeanUtility.objectToJsonStr(fileUploadParam, true);
			String dateTime = String.valueOf(new Date().getTime());

			MultiValueMap<String, Object> fileUploadOut = new LinkedMultiValueMap<String, Object>();
			fileUploadOut.add("dateTime", dateTime);
			fileUploadOut.add("data", data);
			fileUploadOut.add("mac", MacConfirmFm.getMacForMobile(data, dateTime));

			FileOutputStream fo = new FileOutputStream(tempFileName);
			fo.write(fileBytes);
			fo.close();
			fileUploadOut.add("fileBytes", new FileSystemResource(tempFileName));

			String responseJson = restTemplate.postForObject(InitConf.getFilesystemhost() + InitConf.getFmuploadpath(),
					fileUploadOut, String.class);
			Map<String, Object> responseMap = BeanUtility.jsonStrToObject(responseJson, Map.class, true);
			Object codeObj = responseMap.get("code");
			Object dataObj = responseMap.get("data");
			if (codeObj != null && (String.valueOf(codeObj).equals(IfConstant.SERVER_SUCCESS.getCode()))) {
				fileResultOut.setSuccess(true);
				fileResultOut.setData(dataObj == null ? StringUtils.EMPTY : (String) dataObj);
			} else {
				Object messageObj = responseMap.get("message");
				fileResultOut.setMessage(messageObj == null ? "文件存储失败" : (String) messageObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			fileResultOut.setMessage("文件存储失败");
		} finally {// clean-up
			File f = new File(tempFileName);
			if (f != null && f.exists()) {
				f.delete();
			}
		}

		return fileResultOut;
	}

	/**
	 * 获得图片路径接口
	 * 
	 * @param mediaID
	 *            媒体ID
	 * @param createTime
	 *            创建时间
	 * @param sizeType
	 *            FileConst常量图片类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected String getFilePath(String mediaID, String createTime, String sizeType) {

		if (StringUtils.isBlank(mediaID) || StringUtils.isBlank(createTime) || StringUtils.isBlank(sizeType)) {
			return InitConf.getSelfhost() + InitConf.getSelfimgpath();
		}
		if (!valiDateTimeWithLongFormat(createTime)) {
			return InitConf.getSelfhost() + InitConf.getSelfimgpath();
		}
		if (!StringUtils.equals(FileConst.ORIGINAL, sizeType) && !StringUtils.equals(FileConst.MEDIUM, sizeType)
				&& !StringUtils.equals(FileConst.LITTLE, sizeType)) {
			return InitConf.getSelfhost() + InitConf.getSelfimgpath();
		}
		try {

			Map<String, Object> fileloadParam = new HashMap<String, Object>();
			fileloadParam.put("mediaID", mediaID);
			fileloadParam.put("sizeType", sizeType);
			fileloadParam.put("createTime", createTime);

			String data = BeanUtility.objectToJsonStr(fileloadParam, true);
			String dateTime = String.valueOf(new Date().getTime());

			MultiValueMap<String, Object> fileUploadOut = new LinkedMultiValueMap<String, Object>();
			fileUploadOut.add("dateTime", dateTime);
			fileUploadOut.add("data", data);
			fileUploadOut.add("mac", MacConfirmFm.getMacForMobile(data, dateTime));
			String responseJson = restTemplate.postForObject(InitConf.getFilesystemhost() + InitConf.getFmloadpath(),
					fileUploadOut, String.class);

			Map<String, Object> responseMap = BeanUtility.jsonStrToObject(responseJson, Map.class, true);
			Object codeObj = responseMap.get("code");
			Object dataObj = responseMap.get("data");
			if (codeObj != null && (String.valueOf(codeObj).equals(IfConstant.SERVER_SUCCESS.getCode()))) {
				return (String) dataObj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return InitConf.getSelfhost() + InitConf.getSelfimgpath();
	}

	/**
	 * 删除图片接口
	 * 
	 * @param mediaID
	 *            媒体ID
	 * @param createTime
	 *            创建时间
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected boolean delServerFile(String mediaID, String createTime) {

		if (StringUtils.isBlank(mediaID) || StringUtils.isBlank(createTime)) {
			return false;
		}
		if (!valiDateTimeWithLongFormat(createTime)) {
			return false;
		}
		try {

			Map<String, Object> fileloadParam = new HashMap<String, Object>();
			fileloadParam.put("mediaID", mediaID);
			fileloadParam.put("createTime", createTime);

			String data = BeanUtility.objectToJsonStr(fileloadParam, true);
			String dateTime = String.valueOf(new Date().getTime());

			MultiValueMap<String, Object> fileUploadOut = new LinkedMultiValueMap<String, Object>();
			fileUploadOut.add("dateTime", dateTime);
			fileUploadOut.add("data", data);
			fileUploadOut.add("mac", MacConfirmFm.getMacForMobile(data, dateTime));
			String responseJson = restTemplate.postForObject(InitConf.getFilesystemhost() + InitConf.getFmloadpath(),
					fileUploadOut, String.class);

			Map<String, Object> responseMap = BeanUtility.jsonStrToObject(responseJson, Map.class, true);
			Object codeObj = responseMap.get("code");
			if (codeObj != null && (String.valueOf(codeObj).equals(IfConstant.SERVER_SUCCESS.getCode()))) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 方法描述 :根据帐号项目权限获取对应该的员工信息 创建人 : hzhang 创建时间: 2016年9月3日 下午12:19:13
	 * 
	 * @param accountId
	 * @param projectId
	 * @param permission
	 * @return
	 */
	protected List<EmpRelations> queryEmpByAccProPer(String accountId, String projectId, String permission,String orgId) {
		EmpMapper empMapper = SpringContextHolder.getBean("empMapper");
		Map<String, Object> params = new HashMap<>();
		params.put("accountId", accountId);
		params.put("projectId", projectId);
		params.put("permission", permission);
		params.put("orgId", orgId);
		List<EmpRelations> list = empMapper.queryEmpByAccProPer(params);
		if (list != null && list.size() > 0) {
			for (EmpRelations er : list) {
				if (StringUtils.isNotBlank(er.getSuperName())) {
//					er.setEmpName(er.getEmpName() + "(" + er.getSuperName() + ")");
//					er.setEmpName(er.getEmpName() + "(" + er.getMobilephone() + ")");
					er.setSuperName(er.getEmpName() + "(" + er.getSuperName() + ")");//租户名称
				}
			}
		}
		return list;
	}
	/**
	 * 方法描述 :根据帐号项目权限获取对应该的员工信息 创建人 : hzhang 创建时间: 2016年9月3日 下午12:19:13
	 * 
	 * @param accountId
	 * @param projectId
	 * @param permission
	 * @return
	 */
	protected List<EmpRelations> queryEmpByAccProPer(String accountId, String projectId, String permission) {
		EmpMapper empMapper = SpringContextHolder.getBean("empMapper");
		Map<String, Object> params = new HashMap<>();
		params.put("accountId", accountId);
		params.put("projectId", projectId);
		params.put("permission", permission);
		List<EmpRelations> list = empMapper.queryEmpByAccProPer(params);
		if (list != null && list.size() > 0) {
			for (EmpRelations er : list) {
				if (StringUtils.isNotBlank(er.getSuperName())) {
//					er.setEmpName(er.getEmpName() + "(" + er.getSuperName() + ")");
					er.setEmpName(er.getEmpName() + "(" + er.getMobilephone() + ")");
					er.setSuperName(er.getEmpName() + "(" + er.getSuperName() + ")");//租户名称
				}
			}
		}
		return list;
	}
	protected <T> ReturnPageBean<T> getPageResultBean(List<T> pList, PagingBounds page) {
		ReturnPageBean<T> ret = new ReturnPageBean<T>();
		ReturnPage p = new ReturnPage();
		p.setPageNo(page.getPage());
		p.setPageSize(page.getRows());
		p.setPageTotal(page.getTotal());
		if (page.getPage() * page.getRows() < page.getTotal()) {
			p.setHasMore(true);
		} else {
			p.setHasMore(false);
		}
		ret.setPage(p);
		ret.setData(pList);
		return ret;
	}

	protected PagingBounds getPagingBounds(String pageNo, String pageSize) {
		PagingBoundsView pageView = new PagingBoundsView();
		pageView.setPage(pageNo == null ? 1 : Integer.parseInt(pageNo));
		pageView.setRows(pageSize == null ? 10 : Integer.parseInt(pageSize));
		PagingBounds page = pageView.buiderPagingBounds();
		return page;
	}
}
