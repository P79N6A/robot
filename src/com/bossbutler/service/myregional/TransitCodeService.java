package com.bossbutler.service.myregional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.common.constant.AccountConstant;
import com.bossbutler.common.constant.StatusType;
import com.bossbutler.mapper.myregional.MyRegionalMapper;
import com.bossbutler.mapper.system.AccountMapper;
import com.bossbutler.mapper.system.AccountTransitMapper;
import com.bossbutler.pojo.AppHFiveBackView;
import com.bossbutler.pojo.regional.TransitModel;
import com.bossbutler.pojo.system.Account;
import com.bossbutler.pojo.system.AccountTransit;
import com.bossbutler.pojo.visitor.TransitCodePwdIn;
import com.bossbutler.service.BasicService;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.WriteToPageAppHFiveM;
import com.company.util.DateUtil;

@Service
public class TransitCodeService extends BasicService {

	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private AccountTransitMapper accountTransitMapper;

	@Autowired
	private MyRegionalMapper regionalMapper;

	/**
	 * 移动端重置密码
	 * 
	 * @param transitCodePwdIn
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public AppHFiveBackView resetTransitCodePwd(HttpServletResponse response, TransitCodePwdIn transitCodePwdIn,
			String token) throws Exception {
		Map<String, Object> retMap = new HashMap<>();
		// 参数校验
		String accountId = transitCodePwdIn.getAccountId();
		if (StringUtils.isBlank(accountId)) {
			return WriteToPageAppHFiveM.setResponseMessage(response, retMap, IfConstant.PARAM_ACCOUNT_ID_NULL, token);
		}
		String pwd = transitCodePwdIn.getPwd();
		if (StringUtils.isBlank(pwd)) {
			return WriteToPageAppHFiveM.setResponseMessage(response, retMap, IfConstant.PARAM_TRANSIT_CODE_PWD_NOT_NULL,
					token);
		}
		if (!transitCodePwdIn.getPwd().matches("^\\d{4}$")) {
			return WriteToPageAppHFiveM.setResponseMessage(response, retMap,
					IfConstant.PARAM_TRANSIT_CODE_PWD_IS_FOUR_NUM, token);
		}

		Account account = accountMapper.queryUserById(accountId);
		if (account == null) {
			return WriteToPageAppHFiveM.setResponseMessage(response, retMap, IfConstant.PARAM_ACCOUNT_ID_NULL, token);
		}

		AccountTransit pwdAccountTransit = accountTransitMapper.findByTypeAccountId(accountId,
				AccountConstant.TransitTypePwd);
		if (pwdAccountTransit == null) {// 增加密码
			AccountTransit qrAccountTransit = accountTransitMapper.findByTypeAccountId(accountId,
					AccountConstant.TransitTypeQr);
			String qr = qrAccountTransit.getTransitCode();
			// 通过二维码添加用户通行密码
			qrAccountTransit.setTransitId(getIdWorker().nextId());
			qrAccountTransit.setTransitType(AccountConstant.TransitTypePwd);
			qrAccountTransit.setTransitCode(account.getAccountCode() + pwd);
			accountTransitMapper.insert(qrAccountTransit);

			// 如果通行biz有密码删除
			List<TransitModel> tlPwd = regionalMapper.queryTransitByTyAcTcPi(AccountConstant.TransitTypePwd,
					account.getAccountCode() + "", null, null);
			if (tlPwd.size() > 0) {// 删除原来的密码通行
				deleteTransitList(tlPwd, accountId);
			}

			// 通过二维码列表添加result_biz密码
			List<TransitModel> tlQr = regionalMapper.queryTransitByTyAcTcPi(AccountConstant.TransitTypeQr,
					account.getAccountCode() + "", qr, null);
			if (tlQr.size() > 0) {// 增加的密码通行
				insertTransitListByQrTarnsitList(tlQr, accountId, pwd);
			}
			retMap.put("pwd", pwd);
			return WriteToPageAppHFiveM.setResponseMessage(response, retMap, IfConstant.SERVER_SUCCESS, token);
		} else {// 更新密码
			String pTransitCode = pwdAccountTransit.getTransitCode();
			String dbOldPwd = StringUtils.substring(pTransitCode, -4);
			if (pwd.equals(dbOldPwd)) {
				retMap.put("pwd", pwd);
				return WriteToPageAppHFiveM.setResponseMessage(response, retMap, IfConstant.SERVER_SUCCESS, token);
			} else {
				// 更新用户通行密码
				pwdAccountTransit.setTransitCode(account.getAccountCode() + pwd);
				accountTransitMapper.update(pwdAccountTransit);
				// 更新result_biz密码
				List<TransitModel> tlPwd = regionalMapper.queryTransitByTyAcTcPi(AccountConstant.TransitTypePwd,
						account.getAccountCode() + "", null, null);
				if (tlPwd.size() > 0) {// 删除原来的密码通行
					deleteTransitList(tlPwd, accountId);
				}
				AccountTransit qrAccountTransit = accountTransitMapper.findByTypeAccountId(accountId,
						AccountConstant.TransitTypeQr);
				List<TransitModel> tlQr = regionalMapper.queryTransitByTyAcTcPi(AccountConstant.TransitTypeQr,
						account.getAccountCode() + "", qrAccountTransit.getTransitCode(), null);
				if (tlQr.size() > 0) {// 增加的密码通行
					insertTransitListByQrTarnsitList(tlQr, accountId, pwd);
				}
				retMap.put("pwd", pwd);
				return WriteToPageAppHFiveM.setResponseMessage(response, retMap, IfConstant.SERVER_SUCCESS, token);
			}
		}
	}

	/**
	 * 删除名单
	 * 
	 * @param tl
	 * @param operatorId
	 * @throws Exception
	 */
	private void deleteTransitList(List<TransitModel> tl, String operatorId) throws Exception {
		for (TransitModel t : tl) {
			t.setList_id(getIdWorker().nextId());
			t.setBegin_date(DateUtil.getCurrentDate());
			t.setStatus_code(StatusType.TransitDelete);
			t.setStatus_time(DateUtil.getCurrentDate());
			t.setOperator_id(Long.parseLong(operatorId));
			t.setSynch_state("01");
		}
		if (tl.size() > 0) {
			Set<TransitModel> set = new HashSet<TransitModel>(tl);
			tl.clear();
			tl = new ArrayList<TransitModel>(set);
			regionalMapper.createTransitList(tl);
		}
	}

	/**
	 * 通过二维码设备名单同步名单
	 * 
	 * @param tl
	 * @param operatorId
	 * @param pwd
	 * @throws Exception
	 */
	private void insertTransitListByQrTarnsitList(List<TransitModel> tl, String operatorId, String pwd)
			throws Exception {
		for (TransitModel t : tl) {
			t.setList_id(getIdWorker().nextId());
			t.setTransit_code(pwd);
			t.setTransit_type(AccountConstant.TransitTypePwd);
			t.setStatus_code(StatusType.TransitAdd);
			t.setStatus_time(DateUtil.getCurrentDate());
			t.setOperator_id(Long.parseLong(operatorId));
			t.setSynch_state("01");
		}
		if (tl.size() > 0) {
			if (tl.size() > 0) {
				Set<TransitModel> set = new HashSet<TransitModel>(tl);
				tl.clear();
				tl = new ArrayList<TransitModel>(set);
				regionalMapper.createTransitList(tl);
			}
		}
	}

	/**
	 * 获得通行密码
	 * 
	 * @param accountId
	 * @return
	 */
	public void queryTransitCodePwd(String accountId, HttpServletRequest request) {
		AccountTransit pwdAccountTransit = accountTransitMapper.findByTypeAccountId(accountId,
				AccountConstant.TransitTypePwd);
		Account account = accountMapper.queryUserById(accountId);
		request.setAttribute("acode", account.getAccountCode());
		request.setAttribute("phone", account.getMobilephone());

		String pwd = null;
		if (pwdAccountTransit != null) {
			pwd = StringUtils.substring(pwdAccountTransit.getTransitCode(), -4);
		}
		request.setAttribute("pwd", pwd);
		request.setAttribute("xpwd", pwd == null ? "xxxx" : pwd);
	}

}
