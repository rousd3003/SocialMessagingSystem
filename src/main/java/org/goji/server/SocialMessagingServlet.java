package org.goji.server;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.goji.domain.SocialMessagingData;
import org.goji.pojo.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SocialMessagingServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SocialMessagingData data = SocialMessagingData.getInstance();
	ObjectMapper mapper = new ObjectMapper();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String action =request.getParameter("ACTION");
		JsonResponse jsonResponse;
		StringBuilder bodyRequest = new StringBuilder();
		try {

			String lineRequest ;
			while ((lineRequest = request.getReader().readLine()) != null) {
				bodyRequest.append(lineRequest);
			}
			
			
			response.setContentType("application/json");
			if ("publish".equals(action)){
					
				Message userMessage = mapper.readValue(bodyRequest.toString(), Message.class);
				data.addUserMessage(userMessage);
				jsonResponse=new JsonResponse();
				jsonResponse.setMessage("Message added by user "+userMessage.getUserName());
				jsonResponse.setStatus(HttpServletResponse.SC_CREATED);
				response.getWriter().println(mapper.writeValueAsString(jsonResponse));
				response.setStatus(HttpServletResponse.SC_CREATED);
				return;
	
			}

			if ("recommend".equals(action)) {
				
				String userRecommending=request.getParameter("userName");
				
				if (userRecommending ==null ||userRecommending.isEmpty()) {
					response.getWriter().println(mapper.writeValueAsString(
							new JsonResponse("Username must not be empty to recommend a message",HttpServletResponse.SC_BAD_REQUEST)));
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
				Message messageRecommended = mapper.readValue(bodyRequest.toString(), Message.class);
				data.addMessagesRecommended(userRecommending, messageRecommended);
				jsonResponse = new JsonResponse();
				jsonResponse.setStatus(HttpServletResponse.SC_OK);
				jsonResponse.setMessage("Message recommeded by user "+userRecommending);
				jsonResponse.setMessageRecommended(messageRecommended);
				response.getWriter().println(mapper.writeValueAsString(jsonResponse));
				response.setStatus(HttpServletResponse.SC_OK);
				return;
				
			}
		}
		catch(IOException e) {
			jsonResponse=new JsonResponse();
			jsonResponse.setMessage("There was an Exception when reading the body");
			response.getWriter().println(mapper.writeValueAsString(jsonResponse));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
		}
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	

		String action =request.getParameter("ACTION");
		response.setContentType("application/json");
		if("list".equals(action)) {
	
			String userName=request.getParameter("userName");	
			if (userName ==null ||userName.isEmpty()) {
				response.getWriter().println(mapper.writeValueAsString(
						new JsonResponse("Username must not be empty to list the user messages list",HttpServletResponse.SC_BAD_REQUEST)));
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			response.getWriter().print(data.getUserMessagesList(userName)!=null?
					mapper.writeValueAsString(data.getUserMessagesList(userName)):
					mapper.writeValueAsString(new JsonResponse("No data found",HttpServletResponse.SC_OK)));
						
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}
		
		if ("listUsersRecomending".equals(action)){
			response.getWriter().print(data.getListUsersRecommending()!=null?
					mapper.writeValueAsString(data.getListUsersRecommending()):
					mapper.writeValueAsString(new JsonResponse("No data found",HttpServletResponse.SC_OK)));
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}
		
		if ("showMostRecommendedMessage".equals(action)) {
			response.getWriter().print(mapper.writeValueAsString(data.getMostRecommendedMessage()));
			response.setStatus(HttpServletResponse.SC_OK);
			return;
			
		}
	}

}
