package com.bossbutler.controller.third;

public enum EMerchants {

	DIAN_RONG("6ebd9acf58e84517896ba64b308a90f3", "点融商户号", "http://tapp.baobanwang.com/drTest.jsp", true), //
	YUE_MANAGER("7bfce1e071804159b3901ab476bad41f", "悦管家商户号", "http://wechat.yueguanjia.com/jielin-web/#/tab/wechat/service-introduce/91", false), //
	KAIYELA("6ebd9acf58e800000000000000000000", "开业啦商户号", "https://kdt.im/2afqvr", false);

	private String code;
	private String website;
	private String des;
	private boolean isSq;

	private EMerchants(String code, String des, String website, boolean isSq) {
		this.code = code;
		this.des = des;
		this.website = website;
		this.isSq = isSq;
	}

	public static String getDecByCode(String code) {
		for (EMerchants c : EMerchants.values()) {
			if (c.getCode().equals(code)) {
				return c.getDes();
			}
		}
		return null;
	}

	public static String getWebsiteByCode(String code) {
		for (EMerchants c : EMerchants.values()) {
			if (c.getCode().equals(code)) {
				return c.getWebsite();
			}
		}
		return null;
	}

	public static boolean getIsSqByCode(String code) {
		for (EMerchants c : EMerchants.values()) {
			if (c.getCode().equals(code)) {
				return c.isSq();
			}
		}
		return false;
	}

	public String getCode() {
		return code;
	}

	public String getDes() {
		return des;
	}

	public String getWebsite() {
		return website;
	}

	public boolean isSq() {
		return isSq;
	}

}
