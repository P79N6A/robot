package com.bossbutler.pojo;

import java.io.Serializable;
import java.util.List;

public class PushMessageView implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> receivers;

	private String message;

	public List<String> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<String> receivers) {
		this.receivers = receivers;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
