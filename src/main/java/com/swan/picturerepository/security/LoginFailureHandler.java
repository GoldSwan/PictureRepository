package com.swan.picturerepository.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginFailureHandler implements AuthenticationFailureHandler {
	
	private String loginUsername;//login.jsp id input tag name
	private String loginPassword;//login.jsp password input tag name
	private String defaultFailureUrl;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

        String username = request.getParameter(this.loginUsername);
        String password = request.getParameter(this.loginPassword);
        
        request.setAttribute(this.loginUsername, username);
        request.setAttribute(this.loginPassword, password);
        
        request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
	}

}
