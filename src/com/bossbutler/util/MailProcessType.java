package com.bossbutler.util;

public enum MailProcessType {

	VALIDATION("验证亲的电子邮件地址", "mail/validation_mail.vm"), FINDPWD("找回亲的密码", "mail/findpwd_mail.vm"), VALIDCODE("验证码发送",
			"mail/validcode_mail.vm");

	private String subject;

	private String template;

	private MailProcessType(String subj, String temp) {
		subject = subj;
		template = temp;
	}

	public String getCompany() {
		return "包办网";
	}
	
	public String getSubject() {
		return subject;
	}

	public String getTemplate() {
		return template;
	}

}
