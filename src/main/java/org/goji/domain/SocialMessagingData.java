package org.goji.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.goji.pojo.Message;

public final class SocialMessagingData {

	private static SocialMessagingData INSTANCE;
	private ConcurrentHashMap<String, Map<Long,Message>> userMessagesMap;
	private Set<String> usersRecommending;
	
	private SocialMessagingData() {
		this.userMessagesMap = new ConcurrentHashMap<>();
	}

	
	public static SocialMessagingData getInstance() {
		
		if (INSTANCE == null)
			return new SocialMessagingData();
		return INSTANCE;
	}
	
	public ConcurrentHashMap<String,Map<Long,Message>> getUserMessagesMap(){
		return userMessagesMap;
	}
	
	
	public void addUserMessage(Message userMessage) {
		HashMap<Long,Message> messagesList;
		if (this.userMessagesMap.containsKey(userMessage.getUserName())) {
			messagesList = (HashMap<Long, Message>) userMessagesMap.get(userMessage.getUserName());	
		}
		else {
			messagesList = new HashMap<Long,Message>();

		}
		messagesList.put(userMessage.getMessageId(),userMessage);
		this.userMessagesMap.put(userMessage.getUserName(), messagesList);
	}


	public void addMessagesRecommended(String username, Message messageRecommended) {
		if (this.usersRecommending == null) {
			this.usersRecommending = ConcurrentHashMap.newKeySet() ;
		}
		Message userRecomendingMessage = this.userMessagesMap.get(messageRecommended.getUserName()).get(messageRecommended.getMessageId());
		userRecomendingMessage.addRecomendedByUser(username);
		if (!usersRecommending.contains(username))
			this.usersRecommending.add(username);
	}
	
	public List<String> getUserMessagesList(String userName){
		if (this.userMessagesMap==null || this.userMessagesMap.isEmpty())
			return null;
	 	return this.userMessagesMap.get(userName).entrySet().stream().map(val -> val.getValue().getMessage()).collect(Collectors.toList());
		
	}
	public List<String> getListUsersRecommending() {
		if (this.usersRecommending ==null ||this.usersRecommending.isEmpty())
			return null;
		return  this.usersRecommending.stream().collect(Collectors.toList());
	}
	public String getMostRecommendedMessage() {
		if (this.userMessagesMap.isEmpty())
			return "List of messages is empty";
		List<Message> messagesList = this.userMessagesMap.values().stream().flatMap(
				map ->map.values().stream().filter(
						message ->message.getRecomendedByUserList()!=null &&
						message.getRecomendedByUserList().size() > 0)).collect(Collectors.toList());
		if (messagesList.isEmpty())
			return "There is no recommendations yet";
		int maxNumberRecommendations=messagesList.stream().mapToInt(m ->m.getRecomendedByUserList().size()).max().getAsInt();
		for (Message message: messagesList) {
			if (message.getRecomendedByUserList()!=null && message.getRecomendedByUserList().size()==maxNumberRecommendations) {
				return message.getMessage();
			}
		}
		return "No message with that value has been found";
	}
}
