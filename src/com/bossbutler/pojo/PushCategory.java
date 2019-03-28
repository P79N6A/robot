package com.bossbutler.pojo;

/**
 * 推送类别
 */
public class PushCategory{
	/**推送类型物业工单  类别 01:接单*/
	public static final String BILL_RECEIVED = "01"; //接单
	/**推送类型物业工单  类别 02:完成*/
	public static final String BILL_COMPLETE = "02"; //完成单
	/**推送类型物业工单  类别 03:派单*/
	public static final String BILL_SEND = "03"; //派单
	/**推送类型物业工单  类别 04:撤回*/
	public static final String BILL_REVOKE = "04"; //撤回
	/**推送类型物业工单  类别 05:建立*/
	public static final String BILL_CREATE = "05"; //建立
	/**推送类型物业工单  类别 06:取消*/
	public static final String BILL_CANCEL = "06"; //取消
	/**推送类型物业工单  类别 07:催单*/
	public static final String BILL_REMINDER = "07"; //取消
	/**推送类型物业工单  类别 08:催单*/
	public static final String BILL_EVALUATE = "08"; //取消
	
	/**推送类型 访客记录*/
	public static final String VISITOR_RECORD = "09"; //推送访客记录
	
	/**推送类型 账单缴费 */
	public static final String BILL_RECORD = "10"; //推送账单类型
	
	/**推送类型运维通知详情 */
	public static final String MAINTAIN_INF_DETAIL = "11"; //运维通知详情类型
}
