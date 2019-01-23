package org.goji.domain;



import java.util.ArrayList;

import org.goji.pojo.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class TestSocialMessagingData {
	
	
	public SocialMessagingData data = SocialMessagingData.getInstance();
	public Message m;
	public Message m1;
	public Message m2;
	
	@Before
	public void setup() {
	m = new Message();
	m.setMessage("Hello, just testing the application");
	m.setUserName("john81");
	m.setMessageId(1L);
	data.addUserMessage(m);
	m1 = new Message();
	m1.setMessage("Hello, john81 testing");
	m1.setUserName("john81");
	m1.setMessageId(2L);
	data.addUserMessage(m1);
	m2 = new Message();
	m2.setMessage("Hello, leeFlower testing");
	m2.setUserName("leeFlower");
	m2.setMessageId(3L);
	data.addUserMessage(m2);
	}
	
	@Test
	public void testAddUserMessage() {

		Assert.assertNotNull(data.getUserMessagesMap());
		Assert.assertEquals(2, data.getUserMessagesMap().size());
		
	}
	
	@Test
	public void testAddMessageRecommended() {

		data.addMessagesRecommended("leeFlower",m);
		Assert.assertNotNull(data.getListUsersRecommending());
		Assert.assertEquals(1,data.getListUsersRecommending().size());
		Assert.assertEquals(1,m.getRecomendedByUserList().size());
	}
	
	@Test
	public void testGetUserMessagesList() {
		ArrayList<String> userMessages = new ArrayList<>();
		userMessages.add("Hello, just testing the application");
		userMessages.add("Hello, john81 testing");
		Assert.assertEquals(userMessages,data.getUserMessagesList("john81"));		
	}
	
	@Test
	public void testGetListUsersRecomending() {
		Assert.assertNull(data.getListUsersRecommending());
		data.addMessagesRecommended("leeFlower", m);
		ArrayList<String> usersRecommending = new ArrayList<>();
		usersRecommending.add("leeFlower");
		Assert.assertEquals(usersRecommending, data.getListUsersRecommending());
		
	}
	
	@Test
	public void testGetMostRecommendedMessage() {
		data.addMessagesRecommended("leeFlower",m);
		Assert.assertEquals("Hello, just testing the application",data.getMostRecommendedMessage());
	}	

}
