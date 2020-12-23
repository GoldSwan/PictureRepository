package com.swan.picturerepository.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginSucessHandler implements AuthenticationSuccessHandler{

	private String loginUsername;//login.jsp id input tag name
	private String loginPassword;//login.jsp password input tag name
	private String defaultSucessUrl;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String username = request.getParameter(this.loginUsername);
		
        request.setAttribute(this.loginUsername, username);      
        request.getRequestDispatcher(defaultSucessUrl).forward(request, response);
	}

}
