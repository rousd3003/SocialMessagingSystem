package org.goji.pojo;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Message {
	private String userName;
	private Long messageId;
	private String message;
	private Set<String> recomendedByUserList= ConcurrentHashMap.newKeySet();
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Set<String> getRecomendedByUserList() {
		return recomendedByUserList;
	}
	public void addRecomendedByUser(String recomendedByUser) {
		this.recomendedByUserList.add(recomendedByUser);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (messageId == null) {
			if (other.messageId != null)
				return false;
		} else if (!messageId.equals(other.messageId))
			return false;
		return true;
	}
	
}
