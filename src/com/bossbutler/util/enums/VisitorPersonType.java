package com.bossbutler.util.enums;


public enum VisitorPersonType {

	COMPANY_VISITOR("01", "01"), APP_APPLY("02", "02"), BEFORE_DONE("03", "03"), SELF_DONE("04", "04"), WINXIN_APP("05",
			"05");

	private String sourceTypeCode;
	private String personTypeCode;

	private VisitorPersonType(String sourceTypeCode, String personTypeCode) {
		this.sourceTypeCode = sourceTypeCode;
		this.personTypeCode = personTypeCode;
	}

	public static String getTransitCodeByDbCode(String sourceTypeCode) {
		for (VisitorPersonType item : VisitorPersonType.values()) {
			if (item.getSourceTypeCode().equals(sourceTypeCode)) {
				return item.getPersonTypeCode();
			}
		}
		return null;
	}

	public String getSourceTypeCode() {
		return sourceTypeCode;
	}

	public void setSourceTypeCode(String sourceTypeCode) {
		this.sourceTypeCode = sourceTypeCode;
	}

	public String getPersonTypeCode() {
		return personTypeCode;
	}

	public void setPersonTypeCode(String personTypeCode) {
		this.personTypeCode = personTypeCode;
	}
}
