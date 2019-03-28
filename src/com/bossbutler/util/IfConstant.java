package com.bossbutler.util;

/**
 * <p>
 * </p>
 * 
 * @author wq
 *
 */
public enum IfConstant {
	/** 操作成功 */
	SERVER_SUCCESS("000000000", "操作成功"),
	CUSTOM("000000001","自定义错误"),
	NET_FAIL("100100001", "网络连接失败"),
	PARA_FAIL("100100002","请求参数有误"),
	PLATFORM_FAIL("100100003", "平台报文校验错误"),
	UNKNOWN_ERROR("100100004","未知错误"),
	LOGIN_ERROR("100100005", "用户密码或账号错误"), 
	MOBILE_CODE_UNUSED("100100006","短信验证码已经失效请重新发送"), 
	MOBILE_CODE_UNRIGHT("100100007", "短信验证码匹配错误"),
	LOGINNAME_NOT_EXIST("100100008", "登录名不存在"),
	LOGINNAME_EXISTED("100100088", "登录名已存在"),
	LOGINNAME_FORMAT_ERROR("100100088", "登录名只能是字母和数字"),
	LOGINNAME_FORMAT_GREATER20("100100088", "登录名不能大于20位"),
	PASSWORD_FORMAT_GREATER20("100100088", "密码不能小于8位"),
	PHONE_NOT_REGISTER("100100009", "该手机未注册"),
	PHONE_REGISTERED("100100009", "该手机已注册"),
	MAINCONTROLLER_NOT_ONLINE("100100010", "该主控板不在线"),
	INVITATION_NOT_EXIST("100100013","邀请码不存在"),
	INVITATION_NOT_EMP("100100014","该邀请码需要到网站上登录"),
	INVITATION_USERED("100100015","邀请码已验证"),
	INVITATION_TIME_OUT("100100016","邀请码已过期"),
	NO_PERMISSIONS("100100017","没有权限"),
	FILE_IMAGE_TOOMAX("100100011", "文件大小限制3M"),
	OTHER_USER_OPENING_DOOR("100100013", "其他用户正在开启该设备"),
	OTHER_USER_OPERO_DEVICE("100100019", "其他用户正在操作该设备"),
	IS_CUNZAI("100100022", "用户已存在"),
	FILE_IMAGE_UPLOAD_FAIL("100100012", "文件上传失败"),
	FILE_BYTES_NOT_EXIST("100100018", "文件上传失败"),
	
	
	//自定义返回-张欢
	BILL_REMINDER_ONLY_RECEIVED ("100100100", "只有已接单状态才能催单"),
	BILL_NOT_EXIST ("100100101", "该服务单不存在"),
	BILL_REVOKE_ONLY_SEND ("100100102", "只有已派单状态才能取消派单"),
	BILL_SEND_ONLY_WAIT ("100100103", "只有待接单状态才能派单"),
	BILL_RECEIVE_ONLY_WAIT_SEND ("100100104", "只有待接单和已派单状态才能接单"),
	BILL_SEND_ACCOUNT_NOT_EXIST ("100100105", "被指派人不存在"),
	BILL_CANCEL_ONLY_WAITED_SEND ("100100106", "只有待接单才能取消"),
	BILL_COMPLETE_ONLY_RECEIVE ("100100107", "只有已接单才能完成"),
	//设置返回值
	SETTING_PHONE_BINDING_MYSELF("100100200", "该手机号已经绑定当前用户"),
	SETTING_PHONE_BINDINGED("100100201", "该手机号已经绑定其他用户"),
	SETTING_PHONE_FORMAT_ERROR ("100100202", "手机格式错误"),
	SETTING_PASSWORD_ERROR("100100202", "原密码错误"),
	
	NO_RESPONSE("1001000","请求无响应"),
	NO_REMOTE_DOOR_RESPONSE("1001000","开门请求通信异常，请稍后再试。"),
	
	
	
	//参数自定义-返回以1001002**开头
	PARAM_ACCOUNT_NOT_EXIST_EMP ("100100200", "该账户没有绑定员工信息，请联系企业管理员"),
	PARAM_ACCOUNT_ID_NULL ("100100200", "帐号ID不能为空"),
	PARAM_PASSWORD_NOT_LESS_BIT8 ("100100200", "密码长度不能少于8位"),
	PARAM_PHONE_NULL ("100100200", "手机号不能为空"),
	PARAM_LOGIN_NAME_NULL ("100100201", "用户昵称不能为空"),
	PARAM_LOGIN_ID_NULL ("100100201", "登录名不能为空"),
	PARAM_SEX_NULL ("100100201", "性别不能为空"),
	PARAM_PROJECT_ID_NULL("100100202", "项目ID不能为空"),
	PARAM_BILL_TYPE_NULL("100100203", "服务单类型不能为空"),
	PARAM_BILL_STATUS_NULL("100100204", "服务单状态不能为空"),
	PARAM_BILL_EVALUATION_SCORE_NULL("100100205", "评价分数不能为空"),
	//邀请访客
	PARAM_VISITOR_NAME_NULL("100100220", "访客姓名不能为空"),
	PARAM_VISITOR_PHONE_NULL("100100221", "访客联系方式不能为空"),
	PARAM_VISITOR_TIME_NULL("100100223", "访问时间不能为空"),
	PARAM_VISITOR_APPLY_ID_NULL("100100224", "邀请ID不能为空"),
	PARAM_VISITOR_APPLY_IS_DELETE("100100225", "记录已删除"),
	PARAM_VISITOR_ID_IS_NOT_NULL("100100226", "访客ID不能为空"),
	//访客机
	PARAM_REGIONAL_ID_NULL("100100240", "通行区域ID不能为空"),
	PARAM_MACHINE_MACADDRESS_IS_NOT_NULL("100100241", "访客机MAC地址不能为空"),
	PARAM_MACHINE_IDCARDNO_IS_NOT_NULL("100100242", "访客身份证号不能为空"),
	PARAM_MACHINE_IDCARDINFO_IS_NOT_NULL("100100243", "访客身份证信息不能为空"),
	PARAM_MACHINE_NOT_EXIST_PROJECT("100100244", "设备没有在平台内使用"),
	PARAM_MACHINE_VISITOR_NOT_EXIST("100100245", "访客不存在"),
	PARAM_MACHINE_VISITOR_NOT_LONGTERM("100100246", "该访客不是长期访客"),
	PARAM_MACHINE_ICNO_IS_NOT_NULL("100100247", "IC卡号不能为空"),
	PARAM_MACHINE_ICNO_IS_ACCOUNT("100100248", "IC卡号以被常住人员绑定，不能进行绑定"),
	PARAM_MACHINE_ICNO_BELONG_TO_ACCOUNT("100100249","该卡已被此用户绑定，无需重复绑定"),
	PARAM_MACHINE_ICNO_IS_BINDING("100100250","您的账号有没完成绑定，请稍后再试"),
	PARAM_MACHINE_ICNO_IS_OTHER_ACCOUNT("100100248", "IC卡号已被其他常住人员绑定，不能进行绑定"),
	PARAM_MACHINE_ICNO_IS_OTHER_PERSION("100100252", "IC卡号已被其他人员绑定，不能进行绑定"),
	PARAM_MACHINE_VISITOR_APPLY_NOT_EXIST("100100253", "邀请码不存在"),
	PARAM_MACHINE_VISITOR_APPLY_TIMEOUT("100100254", "邀请时间已过期"),
	PARAM_MACHINE_VISITOR_NOT_BINDING_ICCARD("100100255", "此长期访客没有绑定IC卡"),
	PARAM_MACHINE_VISITOR_APPLY_INVALID("100100256", "无效邀请码"),

	//消息公告
	PARAM_INFO_NOT_EXIST("100100230", "通知消息不存在"),
	//投诉建议
	PARAM_COMPLAINT_ORG_NOT_NULL("100100240", "提交对象不能为空"),
	PARAM_COMPLAINT_TYPE_NOT_NULL("100100241", "投诉建议类型不能为空"),
	PARAM_COMPLAINT_ID_NOT_NULL("100100241", "投诉建议ID不能为空"),
	PARAM_COMPLAINT_REPLY_CONTENTS_NOT_NULL("100100241", "投诉建议回复内容不能为空"),
	PARAM_COMPLAINT_NOT_REPLY("100100241", "该投诉建议已回复，无需重复回复"),
	PARAM_COMPLAINT_REPLY_CONTENTS_LENGTH_200("100100241", "回复内容在200字以内"),

	//重置通行密码
	PARAM_TRANSIT_CODE_PWD_NOT_NULL("100100260", "访客密码不能为空"),
	PARAM_TRANSIT_CODE_PWD_IS_FOUR_NUM("100100261", "访客密码只能为4位数字"),
	PARAM_TRANSIT_CODE_PWD_FAIL("100100262", "更改通行密码失败，请稍后再试！"),
	PARAM_TRANSIT_ANALYZE_FAIL("100100263", "通行分析失败，请稍后再试！"),
	PARAM_TRANSIT_BILL_FIND_FAIL("100100264", "查看账单失败，请稍后再试！"),
	PARAM_TRANSIT_NOBILL_FIND_FAIL("100100265", "未找到相关账单！"),
	PARAM_TRANSIT_DEVICEINSP_FAIL("100100263", "设备巡检失败，请稍后再试！"),
	PARAM_TRANSIT_DEVICEORDERMAINTAIN_FAIL("100100266", "名单运维失败，请稍后再试！"),
	PARAM_TRANSIT_THIRD_SERVE_FAIL("100100263", "请求失败，请稍后再试！"),
	PARAM_TRANSIT_MINGDAN_FIND_FAIL("100100267", "查看名单失败，请稍后再试！"),
	PARAM_SERVICE_BILL_ID_NULL("100100299", "服务单ID不能为空"),
	NO_REMOTE_CHANGE_OPEN_RESPONSE("100100300","请求通信异常，请稍后再试。"),
	SERVER_BUSY_RESPONSE("100100301","后台繁忙，请稍后再试!"),
	SERVER_NO_DISTANCE_ACCESS_RESPONSE("100100303","距离目的地太远，请稍后再试!"),
	SERVER_NO_ACCESS_RESPONSE("100100302","没有权限，请联系管理人员!");
	
	private String code;
	private String message;

	private IfConstant(String cd, String msg) {
		this.code = cd;
		this.message = msg;
	}

	
	
	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}