package com.bossbutler.service.system;

 

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.common.constant.InvitationType;
import com.bossbutler.common.constant.StatusType;
import com.bossbutler.mapper.InvitationMapper;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.system.Invitation;
import com.bossbutler.pojo.system.InvitationContent;
import com.bossbutler.pojo.system.InvitationIn;
import com.bossbutler.pojo.system.InvitationOut;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.WriteToPage;
import com.company.util.BeanUtility;

/**
 * <p>
 * 邀请码管理.
 * </p>
 * 
 * @author zst
 *
 */
@Service
public class InvitationService{

	@Autowired
	private InvitationMapper invitationMapper;
	/**
	 * 邀请码
	 * @param view
	 * @return
	 * @throws Exception
	 */
	public String queryInvitationByCode(ControllerView view) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 实体内容转为对象
		InvitationIn in = BeanUtility.jsonStrToObject(dataJson, InvitationIn.class, true);
		// 参数时间戳
		String dateTime = String.valueOf(new Date().getTime());
		// 邀请码
		Invitation invitation = invitationMapper.queryInvitationByCode(in.getInvitationCode());
		if(invitation==null){
			return WriteToPage.setResponseMessage("{}", IfConstant.INVITATION_NOT_EXIST);
		}else if(!StatusType.InvitationStatus01.equals(invitation.getStatusCode())) {
			if (StatusType.InvitationStatus02.equals(invitation.getStatusCode())) {
				return WriteToPage.setResponseMessage("{}", IfConstant.INVITATION_USERED);
			}else{
				return WriteToPage.setResponseMessage("{}", IfConstant.INVITATION_TIME_OUT);
			}
		}else{
			//邀请码信息内容
			String itype = invitation.getInvitationType();
			if(InvitationType.InvitationType01.equals(itype))//邀请组织管理员
				return WriteToPage.setResponseMessage("{}", IfConstant.INVITATION_NOT_EMP, dateTime);
		}
		InvitationOut out = new InvitationOut();
		out.setInvitationCode(in.getInvitationCode());
		out.setPhone(invitation.getMobilephone());
		// 查询成功
		return WriteToPage.setResponseMessage(BeanUtility.objectToJsonStr(out, false), IfConstant.SERVER_SUCCESS, dateTime);
	}

}