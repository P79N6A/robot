package com.bossbutler.pojo;

import java.io.Serializable;

public class PushMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String pushTableKey;

	private String pushTableUserKey;

	private boolean greater100;

	public PushMessage(String pushTableKey, String pushTableUserKey, boolean greater100) {

		this.pushTableKey = pushTableKey;
		this.pushTableUserKey = pushTableUserKey;
		this.greater100 = greater100;

	}

	public String getPushTableKey() {
		return pushTableKey;
	}

	public void setPushTableKey(String pushTableKey) {
		this.pushTableKey = pushTableKey;
	}

	public String getPushTableUserKey() {
		return pushTableUserKey;
	}

	public void setPushTableUserKey(String pushTableUserKey) {
		this.pushTableUserKey = pushTableUserKey;
	}

	public boolean isGreater100() {
		return greater100;
	}

	public void setGreater100(boolean greater100) {
		this.greater100 = greater100;
	}

}
