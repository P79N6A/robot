package com.bossbutler.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.bossbutler.util.MailProcessType;
import com.company.mail.MailManager;
import com.company.mail.MailSenderType;
import com.company.mail.exception.MailException;

@Service
public class EmailService {

	@Autowired
	private VelocityEngine velocityEngine;

	private String getTemplateText(final Map<String, Object> model, final String vmfile) {
		return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, vmfile, "UTF-8", model);
	}

	/**
	 * 重置密码
	 * 
	 * @param userMail
	 * @param validationUrl
	 * @return
	 * @throws MailException
	 */
	public boolean sendFindpwdEmail(String userMail, String validationUrl) throws MailException {
		String subject = MailProcessType.FINDPWD.getSubject();
		String vmfile = MailProcessType.FINDPWD.getTemplate();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("company", MailProcessType.FINDPWD.getCompany());
		model.put("validationUrl", validationUrl);
		model.put("userMail", userMail);
		String content = getTemplateText(model, vmfile);
		return MailManager.sendMailHtml(MailSenderType.AUTH_SENDER, subject, content, null, null, userMail);
	}

	/**
	 * 验证码
	 * 
	 * @param userMail
	 * @param validcode
	 * @param validcodeMin
	 *            有效时间分钟
	 * @return
	 * @throws MailException
	 */
	public boolean sendValidcodeEmail(String userMail, String validcode, int validcodeMin) throws MailException {
		String subject = MailProcessType.VALIDCODE.getSubject();
		String vmfile = MailProcessType.VALIDCODE.getTemplate();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("company", MailProcessType.VALIDCODE.getCompany());
		model.put("validcode", validcode);
		model.put("validcodeTime", validcodeMin);
		String content = getTemplateText(model, vmfile);
		return MailManager.sendMailHtml(MailSenderType.AUTH_SENDER, subject, content, null, null, userMail);
	}

	/**
	 * 邮箱验证
	 * 
	 * @param userMail
	 * @param validcode
	 * @param validcodeMin
	 *            有效时间分钟
	 * @return
	 * @throws MailException
	 */
	public boolean sendValidationEmail(String userMail, String validationUrl, int validcodeMin) throws MailException {
		String subject = MailProcessType.VALIDATION.getSubject();
		String vmfile = MailProcessType.VALIDATION.getTemplate();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("company", MailProcessType.VALIDATION.getCompany());
		model.put("validationUrl", validationUrl);
		model.put("userMail", userMail);
		String content = getTemplateText(model, vmfile);
		return MailManager.sendMailHtml(MailSenderType.AUTH_SENDER, subject, content, null, null, userMail);
	}

}