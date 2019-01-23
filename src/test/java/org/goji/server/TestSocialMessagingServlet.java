package org.goji.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.goji.pojo.Message;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;



public class TestSocialMessagingServlet {
	
    HttpServletRequest testRequest = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse testResponse = Mockito.mock(HttpServletResponse.class);
    SocialMessagingServlet servlet = new SocialMessagingServlet();

    BufferedReader br = Mockito.mock(BufferedReader.class);
    
    
    @Test
    public void testDoPostRecommendAMessageShouldAddUsernameAsAParameter() throws ServletException, IOException {

    	Mockito.when(testRequest.getParameter("ACTION")).thenReturn("recommend");
    	Mockito.when(testRequest.getReader()).thenReturn(br);
    	


        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        
        Mockito.when(testResponse.getWriter()).thenReturn(writer);
        
    	servlet.doPost(testRequest, testResponse);
    	Mockito.verify(testRequest, Mockito.atLeast(1)).getParameter("userName");
    	writer.flush();
    	Assert.assertTrue(stringWriter.toString().contains("Username must not be empty to recommend a message"));
    	
    	
        
    }
    
    @Test
    public void testDoPostRecommendAMessageShouldThrowExceptionIfBodyIsNotSent() throws ServletException, IOException {

    	Mockito.when(testRequest.getParameter("ACTION")).thenReturn("recommend");
    	Mockito.when(testRequest.getParameter("userName")).thenReturn("userTest");
    	Mockito.when(testRequest.getReader()).thenReturn(br);
    	


        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        
        Mockito.when(testResponse.getWriter()).thenReturn(writer);
        
    	servlet.doPost(testRequest, testResponse);
    	Mockito.verify(testRequest, Mockito.atLeast(1)).getParameter("userName");
    	writer.flush();
    	Assert.assertTrue(stringWriter.toString().contains("There was an Exception when reading the body"));
    }  	
    @Test
    public void testDoGetUserMessagesListShouldGetMessages() throws ServletException, IOException {

		Message message = new Message();
		message.setMessageId(1L);
		message.setMessage("Testing the servlet post request");
		message.setUserName("username");
		servlet.data.addUserMessage(message);
    	Mockito.when(testRequest.getParameter("ACTION")).thenReturn("list");
    	Mockito.when(testRequest.getParameter("userName")).thenReturn("username");
    	Mockito.when(testRequest.getReader()).thenReturn(br);
    	


        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        
        Mockito.when(testResponse.getWriter()).thenReturn(writer);
        
    	servlet.doGet(testRequest, testResponse);
    	Mockito.verify(testRequest, Mockito.atLeast(1)).getParameter("userName");
    	writer.flush();
    	Assert.assertTrue(stringWriter.toString().contains("Testing the servlet post request"));
    }
    
    
    @Test
    public void testDoGetListUsersRecommendingShouldReturnListOfNames() throws IOException, ServletException {

		Message m = new Message();
		m.setMessage("Hello, just testing the application");
		m.setUserName("john81");
		m.setMessageId(1L);
		servlet.data.addUserMessage(m);
		Message m1 = new Message();
		m1.setMessage("Hello, john81 testing");
		m1.setUserName("john81");
		m1.setMessageId(2L);
		servlet.data.addUserMessage(m1);
		Message m2 = new Message();
		m2.setMessage("Hello, leeFlower testing");
		m2.setUserName("leeFlower");
		m2.setMessageId(3L);
		servlet.data.addUserMessage(m2);
		
		servlet.data.addMessagesRecommended("leeFlower", m1);
		servlet.data.addMessagesRecommended("leeFlower2", m1);
		
	   	Mockito.when(testRequest.getParameter("ACTION")).thenReturn("listUsersRecomending");
    	Mockito.when(testRequest.getReader()).thenReturn(br);
   

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        
        Mockito.when(testResponse.getWriter()).thenReturn(writer);
        
    	servlet.doGet(testRequest, testResponse);
    	writer.flush();
    	Assert.assertTrue(stringWriter.toString().contains("leeFlower"));

    	
    }
    
   @Test
    public void testDoGetMostRecommededMessageShouldReturnListOfMessagesEmptyIfThereIsNoMessages() throws IOException, ServletException {

    	
	   	Mockito.when(testRequest.getParameter("ACTION")).thenReturn("showMostRecommendedMessage");
    	Mockito.when(testRequest.getReader()).thenReturn(br);
   

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        
        Mockito.when(testResponse.getWriter()).thenReturn(writer);
        
    	servlet.doGet(testRequest, testResponse);
    	writer.flush();
    	Assert.assertTrue(stringWriter.toString().contains("List of messages is empty"));
    }
    
    @Test
    public void testDoGetMostRecommededMessageShouldReturnNoRecommendationMessageIfNoneOfMessageHasBeenRecommended() throws IOException, ServletException {
		Message m = new Message();
		m.setMessage("Hello, just testing the application");
		m.setUserName("john81");
		m.setMessageId(1L);
		servlet.data.addUserMessage(m);
		
	   	Mockito.when(testRequest.getParameter("ACTION")).thenReturn("showMostRecommendedMessage");
    	Mockito.when(testRequest.getReader()).thenReturn(br);
   

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        
        Mockito.when(testResponse.getWriter()).thenReturn(writer);
        
    	servlet.doGet(testRequest, testResponse);
    	writer.flush();
    	Assert.assertTrue(stringWriter.toString().contains("There is no recommendations yet"));
    }

    @Test
    public void testDoGetMostRecommededMessageShouldReturnMostRecommendedMessage() throws IOException, ServletException {

		Message m = new Message();
		m.setMessage("Hello, just testing the application");
		m.setUserName("john81");
		m.setMessageId(1L);
		servlet.data.addUserMessage(m);
		Message m1 = new Message();
		m1.setMessage("Hello, john81 testing");
		m1.setUserName("john81");
		m1.setMessageId(2L);
		servlet.data.addUserMessage(m1);
		Message m2 = new Message();
		m2.setMessage("Hello, leeFlower testing");
		m2.setUserName("leeFlower");
		m2.setMessageId(3L);
		servlet.data.addUserMessage(m2);
		
		servlet.data.addMessagesRecommended("leeFlower", m1);
		servlet.data.addMessagesRecommended("leeFlower2", m1);
	   	Mockito.when(testRequest.getParameter("ACTION")).thenReturn("showMostRecommendedMessage");
    	Mockito.when(testRequest.getReader()).thenReturn(br);
   

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        
        Mockito.when(testResponse.getWriter()).thenReturn(writer);
        
    	servlet.doGet(testRequest, testResponse);
    	writer.flush();
    	Assert.assertTrue(stringWriter.toString().contains("Hello, john81 testing"));
    }
    
}