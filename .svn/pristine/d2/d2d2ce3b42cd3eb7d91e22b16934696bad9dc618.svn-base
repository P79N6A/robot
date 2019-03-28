package com.bossbutler.service.qrcode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bossbutler.common.constant.AccountConstant;
import com.bossbutler.common.constant.QrcodeConstant;
import com.bossbutler.mapper.system.AccountMapper;
import com.bossbutler.mapper.system.AccountTransitMapper;
import com.bossbutler.pojo.ControllerView;
import com.bossbutler.pojo.qrcode.QrcodeIn;
import com.bossbutler.pojo.system.Account;
import com.bossbutler.pojo.system.AccountTransit;
import com.bossbutler.util.IfConstant;
import com.bossbutler.util.WriteToPage;
import com.common.cnnetty.gateway.util.BarCodeUtil;
import com.common.cnnetty.gateway.util.EidentityType;
import com.company.util.BeanUtility;
import com.company.util.CommonUtil;
import com.company.util.DateUtil;

/**
 * <p>
 * 二维码管理.
 * </p>
 * 
 * @author zst
 *
 */
@Service
public class QrcodeService {

	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private AccountTransitMapper accountTransitMapper;

	/**
	 * 二维码生成
	 * 
	 * @param view
	 * @return
	 * @throws Exception
	 */
	public String createLong(ControllerView view) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 实体内容转为对象
		QrcodeIn in = BeanUtility.jsonStrToObject(dataJson, QrcodeIn.class, true);
		// 参数时间戳
		String dateTime = String.valueOf(new Date().getTime());
		// 用户信息
		Account account = accountMapper.queryUserById(in.getAccountId());
		String retData = this.createQrcode(account, "long");
		// 查询成功
		return WriteToPage.setResponseMessage(retData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	/**
	 * 二维码生成
	 * 
	 * @param view
	 * @return
	 * @throws Exception
	 */
	public String createTemp(ControllerView view) throws Exception {
		// 请求的json格式实体内容
		String dataJson = view.getData();
		// 实体内容转为对象
		QrcodeIn in = BeanUtility.jsonStrToObject(dataJson, QrcodeIn.class, true);
		// 参数时间戳
		String dateTime = String.valueOf(new Date().getTime());
		// 用户信息
		Account account = accountMapper.queryUserById(in.getAccountId());
		String retData = this.createQrcode(account, "temp");
		// 查询成功
		return WriteToPage.setResponseMessage(retData, IfConstant.SERVER_SUCCESS, dateTime);
	}

	/**
	 * 生成QRCODE
	 * 
	 * @param account用户信息
	 * @param type
	 *            生成类型
	 * @return
	 * @throws Exception
	 */
	private String createQrcode(Account account, String type) throws Exception {
		StringBuffer ret = new StringBuffer(QrcodeConstant.QRCODE_START_VERSION);// 版本号
		String idCode = QrcodeConstant.QRCODE_TYPE_LONG;
		AccountTransit at = accountTransitMapper.findByTypeAccountId(account.getAccountId(),
				AccountConstant.TransitTypeQr);
		String qrId = at.getTransitCode();
		String qrDate = this.getQrDate(this.addMinutes(2));
		String defaultDevice = QrcodeConstant.QRCODE_DEFAULT_DEVICE;
		String permission = this.createStr("F", 60);
		String sysSyncCode = QrcodeConstant.QRCODE_SYS_SYNC_CODE;
		String verifyCode = CommonUtil.generateShortUuid();
		switch (type) {
		case "long":// 长期使用
			break;
		case "temp":// 临时使用

			break;
		default:
			break;
		}
		ret.append(idCode);// 身份
		ret.append(qrId);// ID号
		ret.append(qrDate);// 二维码有效截止时间
		ret.append(defaultDevice);// 设备编号的前2字节
		ret.append(permission);// 开门权限（编号及时间剩余）
		ret.append(sysSyncCode);// 系统同步码
		ret.append(verifyCode);// 校验
		return BarCodeUtil.createBarCode(EidentityType.RESIDENT, qrId, qrDate, defaultDevice, permission);
		// return ret.toString();
	}

	/**
	 * 生成160808080808的日期格式
	 * 
	 * @param date
	 *            2016-08-08 08:08:08
	 * @return 160808080808
	 */
	private String getQrDate(Date date) {
		String ret = DateUtil.date2String(date, null);
		ret = ret.replace("-", "").replace(":", "").replace(" ", "").trim();
		ret = ret.substring(2, ret.length());
		return ret;
	}

	/**
	 * 生成count的相同字符串
	 * 
	 * @param c
	 * @param count
	 * @return
	 */
	private String createStr(String c, int count) {
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < count; i++) {
			ret.append(c);
		}
		return ret.toString();
	}
	
	private Date addDay(){
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) + 1);
		Date endDate = new Date();
		try {
			endDate = dft.parse(dft.format(date.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return endDate;
	}
	private Date addMinutes(int minutes){
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.MINUTE, date.get(Calendar.MINUTE) + minutes);
		Date endDate = new Date();
		try {
			endDate = dft.parse(dft.format(date.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return endDate;
	}

}
