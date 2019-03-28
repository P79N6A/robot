package com.bossbutler.controller.remote;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.bossbutler.controller.BaseController;
import com.bossbutler.mapper.robot.RobotRequestMapper;
import com.bossbutler.pojo.robot.RobotRequestModel;
import com.bossbutler.util.IpAdrressUtil;
import com.bossbutler.util.WriteToPage;
import com.company.util.BeanUtility;

@Aspect
@Component
public class RemoteAspect extends BaseController {
	
	@Pointcut(value = "@annotation(com.bossbutler.controller.remote.RemoteAnnotation)")
    public void pointCut(){}
	
	private static final ThreadLocal<Long> thread = new ThreadLocal<>(); 
	
	private String aa;
	
	@Autowired
	private RobotRequestMapper  robotRequestMapper;
	@AfterReturning(value="pointCut()",argNames = "retVal",returning = "retVal")
	public void After(JoinPoint joinPoint,Object retVal){
		
		RobotRequestModel rrm=new RobotRequestModel();
		rrm.setRequestTime(thread.get()+"");
		rrm.setReponseTime(System.currentTimeMillis()+"");
	    RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();  //获取request 可以从中获取参数或cookie
		String data=request.getParameter("data");
		/** 获取uri */
		String ip=IpAdrressUtil.getIpAdrress(request);
		/** 获取contextPath */
		String ctxPath = request.getContextPath();
		/** 获取uri */
		String uri = request.getRequestURI();
		/** 获取请求动作标识 */
		String actionName = uri.replaceFirst(ctxPath + "/", "");
		String resultStr=retVal+"";
		rrm.setRequestParam(data);
		rrm.setRequestAddress(ip+uri);
		rrm.setResultParam(retVal+"");
		if(actionName.equals("remote/robot/room")){
			rrm.setRequestType("00");
		}else if(actionName.equals("remote/robot/open")){
			rrm.setRequestType("01");
		}
		try {
			Map<String,String> map=BeanUtility.jsonStrToObject(resultStr,new HashMap<String,String>().getClass(),true);
			String code=map.get("code")+"";
			if(code.equals("0")){
				rrm.setResult("00");
			}else{
				rrm.setResult("01");
			}
			rrm.setId(getIdWorker(request).nextId()+"");
			if(StringUtils.isNotBlank(data) ){
				Map<String,String> mapDate=null;
				try{
					mapDate=BeanUtility.jsonStrToObject(data,Map.class, true);
					String loginId=mapDate.get("loginId");
					rrm.setLoginId(loginId);
					
				}catch(Exception e){
					WriteToPage.setResponseMessage(null,"9","remoteOpen参数obj转换json报错");
				}
			}
			if(!code.equals("7") && !code.equals("5") ){
				robotRequestMapper.insert(rrm);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
			
	}
	@Before(value="pointCut()")
	public void before(){ 
	
	     
	     long time=System.currentTimeMillis();
	     System.out.println("方法执行之前："+Thread.currentThread().getName()+":"+time);
	     thread.set(time);
	     aa=time+"";
    } 

}
