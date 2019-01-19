package org.goji.domain;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.goji.pojo.Message;

public final class SocialMessagingData {

	private static SocialMessagingData INSTANCE;
	private ConcurrentHashMap<String, List<Message>> userMessagesMap;
	
	private SocialMessagingData() {}

	
	public static SocialMessagingData getInstance() {
		
		if (INSTANCE == null)
			return new SocialMessagingData();
		return INSTANCE;
	}
}
