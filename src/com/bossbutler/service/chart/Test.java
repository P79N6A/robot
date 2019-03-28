package com.bossbutler.service.chart;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.web.client.RestTemplate;

import com.bossbutler.common.InitConf;
import com.company.exception.AppException;
import com.company.util.BeanUtility;
import com.company.util.CommonUtil;
import com.company.util.DateUtil;
public class Test {

	

	
	public static void main(String[] args) throws Exception {
		
		System.out.println(InitConf.getRodoorResendlimit());
		
		System.out.println(System.currentTimeMillis());
		
		System.out.println(InitConf.getRodoorResendlimit());
		
		
		System.out.println(CommonUtil.strToDate("2019-03-25", "yyyy-MM-dd").before(new Date()));
		
		System.out.println(DateUtil.compareDate(CommonUtil.strToDate("2019-03-24", "yyyy-MM-dd"), new Date()));
		
		
		
		
	
		
		Long a=new Long(2l);
		Long b=2l;
		
		System.out.println(a==b);
		
		
		RestTemplate restTemplate=new RestTemplate();
        Map<String,Object> map=new HashMap<String,Object>();

      // map = restTemplate.getForObject("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxec6ce54018ee7c99&secret=7c1ad7a7b2d8dbd5bf17c77b3ef02662", new HashMap<String,Object>().getClass());
       
       // String access_token=(String) map.get("access_token");
        String access_token="19_EF9UMF0S9qDxtScAsM1xnQQFWVeZLFvL2xTnNUMmwwYYslWD5Y5w99j0lF3uCd635dm8SHelPKvl-ZJQc3Hhb5aYGwRp99c1AHAgazwuG8Ne3dY6xB1WcMSd7ISvxlF_sLzhwB5N6d8iCvckZUIcAHALGU";
        String errcode=(String) map.get("errcode");
        Integer expires_in=(Integer) map.get("expires_in");
        System.out.println(expires_in);
        System.out.println(errcode);
        
       Map<String,Object> map2=new HashMap<String,Object>();
        map2.put("scene","1099909634958954496");
        byte[]  result= restTemplate.postForObject("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+access_token,map2,byte[].class);
       
       
        InputStream inputStream = null;
        OutputStream outputStream = null;
        inputStream = new ByteArrayInputStream(result);

        File file = new File("D:/image/1.png");
        if (!file.exists()){
            file.createNewFile();
        }
        outputStream = new FileOutputStream(file);
        outputStream.write(result);
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = inputStream.read(buf,0,1024)) != -1) {
            outputStream.write(buf, 0, len);
        }
        outputStream.flush();

	}
	
	private int versionComparison(String version1,String version2){
	
		return version1.compareTo(version2);
		
	}


}
