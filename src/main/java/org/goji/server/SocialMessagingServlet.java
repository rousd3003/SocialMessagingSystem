package org.goji.server;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.goji.pojo.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SocialMessagingServlet extends HttpServlet{
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		String method = request.getMethod();
		String action =request.getParameter("ACTION");
		
		response.setContentType("application/json");
		
		if ("publishMessage".equals(action)){
			
			String userName = request.getParameter("userName");

			try {
				StringBuilder bodyRequest = new StringBuilder();
				String lineRequest ;
				while ((lineRequest = request.getReader().readLine()) != null) {
					bodyRequest.append(lineRequest);
				}
				ObjectMapper mapper = new ObjectMapper();
				Message userMessage = mapper.readValue(bodyRequest.toString(), Message.class);
				
			}
			catch(Exception e) {
				
			}
		}
	}

}
