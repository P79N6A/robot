package com.bossbutler.pojo.system;

/**
 * @author hzhang
 *
 */
public class InvitationOut {

    private String invitationCode; //邀请码
    
    private String phone;//接收邀请码的手机号

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
