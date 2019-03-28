package com.bossbutler.service.image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bossbutler.common.constant.MediaMainClassify;
import com.bossbutler.common.constant.MediaType;
import com.bossbutler.mapper.image.ImageMapper;
import com.bossbutler.pojo.FileResultOut;
import com.bossbutler.pojo.ImageFilePojo;
import com.bossbutler.pojo.ImageFileView;
import com.bossbutler.pojo.ImageInfoPojo;
import com.bossbutler.pojo.ImagePojo;
import com.bossbutler.pojo.bill.BillMedia;
import com.bossbutler.pojo.system.AccountMedia;
import com.bossbutler.service.BasicService;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.WriteToPage;
import com.company.exception.FileConst;
import com.company.util.BeanUtility;
import com.company.util.DateUtil;

@Service
public class ImageService extends BasicService {
	@Autowired
	ImageMapper mapper;

	public String uploadAccountHead(ImageFileView view) throws Exception {
		String dataJson = view.getData();
		String dateTime = String.valueOf(new Date().getTime());
		List<MultipartFile> multipartFileList = view.getFileBytes();
		byte[] bytes  = null;
		//如果不存在  在流中查找
//		if (multipartFileList == null) {
//			ServletInputStream in = super.getRequest().getInputStream();
//			bytes = this.input2byte(in);
//		}
		if (bytes == null && multipartFileList != null ) {
			MultipartFile multipartFile = multipartFileList.get(0);
			bytes = multipartFile.getBytes();
		}
		if (bytes == null) {
			return WriteToPage.setResponseMessage("{}", IfConstant.FILE_BYTES_NOT_EXIST);
		}
		ImageInfoPojo info = BeanUtility.jsonStrToObject(dataJson, ImageInfoPojo.class, true);
		String fileType = info.getFileType();
		if (StringUtils.isBlank(info.getBussinessID())) {
			return WriteToPage.setResponseMessage("{}", IfConstant.PARA_FAIL);
		}

		FileResultOut fileResultOut = uploadFile(MediaMainClassify.MediaMainClassify10, info.getBussinessID(), false,
				false, bytes, fileType, null, null);
		if (fileResultOut.isSuccess()) {
			ImagePojo pojo1 = mapper.queryordinal(MediaMainClassify.MediaMainClassify10, MediaType.MediaType01);
			String dataJson1 = fileResultOut.getData();
			ImageFilePojo pojo = BeanUtility.jsonStrToObject(dataJson1, ImageFilePojo.class, true);
			ImagePojo impojo = new ImagePojo();
			impojo.setAccountId(info.getBussinessID());
			// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd
			// HH:mm:ss");//设置日期格式
			impojo.setCreatetime(new Date());
			impojo.setFileType(fileType);
			impojo.setMainClassify(MediaMainClassify.MediaMainClassify10);
			impojo.setMediaId(pojo.getMediaID());
			impojo.setMediaType(MediaType.MediaType01);
			impojo.setOrdinal(pojo1.getOrdinal());
			mapper.insertmedia(impojo);
			return WriteToPage.setResponseMessage(pojo.getFilepath(), IfConstant.SERVER_SUCCESS, dateTime);
		} else {
			return WriteToPage.setResponseMessage("{}", IfConstant.FILE_IMAGE_UPLOAD_FAIL);
		}
	}
	public String getAccountHeadPath(String accountId) throws Exception {
		AccountMedia media = mapper.findMediaByAccountId(accountId,
				MediaMainClassify.MediaMainClassify10,MediaType.MediaType01);
		String path = "";
		if (media == null) {
			path = this.getFilePath(null,null,null);
		}else{
			path = this.getFilePath(media.getMediaId(), DateUtil.format(media.getCreateTime(),"yyyy-MM-dd HH:mm:ss"), FileConst.ORIGINAL);
		}
		return path;
	}
	public List<String> getServerBillPicPath(String serverId) throws Exception {
		List<BillMedia> medias = mapper.findMediaByBillId(serverId,
				MediaMainClassify.MediaMainClassify09,MediaType.MediaType301);
		List<String> paths = new ArrayList<String>();
		if (medias != null && medias.size()>0) {
			for (BillMedia media : medias) {
				String path = this.getFilePath(media.getMediaId(), 
						DateUtil.format(media.getCreateTime(),"yyyy-MM-dd HH:mm:ss"), FileConst.ORIGINAL);
				paths.add(path);
			}
		}
		return paths;
	}
    @SuppressWarnings("unused")
	private byte[] input2byte(InputStream inStream) throws IOException {  
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
        byte[] buff = new byte[100];  
        int rc = 0;  
        while ((rc = inStream.read(buff, 0, 100)) > 0) {  
            swapStream.write(buff, 0, rc);  
        }  
        byte[] in2b = swapStream.toByteArray();  
        return in2b;  
    }  
}
