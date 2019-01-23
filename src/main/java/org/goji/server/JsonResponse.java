package org.goji.server;

import org.goji.pojo.Message;

public class JsonResponse {
	
	private String message;
	private int status;
	private Message messageRecommended;
	
	public JsonResponse() {}
	
	public JsonResponse(String message, int status) {
		super();
		this.message = message;
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Message getMessageRecommended() {
		return messageRecommended;
	}
	public void setMessageRecommended(Message messageRecommended) {
		this.messageRecommended = messageRecommended;
	}

}
