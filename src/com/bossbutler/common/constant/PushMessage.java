package com.bossbutler.common.constant;

/**
 * <p>
 * 推送标题信息.
 * </p>
 * @author wq
 *
 */
public class PushMessage {
	
	public static final String BILL_EVALUATION = "您的服务工单已评价";
	/**推送类型物业工单  类别 01:接单*/
	public static final String BILL_RECEIVED = "您的服务工单已接单"; //接单
	/**推送类型物业工单  类别 02:完成*/
	public static final String BILL_COMPLETE = "您的服务工单已完成"; //完成单
	/**推送类型物业工单  类别 03:派单*/
	public static final String BILL_SEND = "您有新派单，请及时查看"; //派单
	/**推送类型物业工单  类别 04:撤回*/
	public static final String BILL_REVOKE = "您有派单已被撤回"; //撤回
	/**推送类型物业工单  类别 05:建立*/
	public static final String BILL_CREATE = "有新的服务工单"; //建立
	/**推送类型物业工单  类别 06:取消*/
	public static final String BILL_CANCEL = "服务工单已取消"; //取消
	/**推送类型物业工单  类别 07:催单*/
	public static final String BILL_REMINDER = "发起人正在催单"; //取消
	
	public static final String BILL_RETURN = "您的服务工单已退回"; //退回
	
}