package com.zerobase.fastlms.configuration;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.zerobase.fastlms.loginhistory.entity.LoginHistory;
import com.zerobase.fastlms.loginhistory.model.LoginHistoryInput;
import com.zerobase.fastlms.loginhistory.service.LoginHistoryService;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final LoginHistoryService loginHistoryService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
	
		UserDetails details = (UserDetails)authentication.getPrincipal();
		WebAuthenticationDetails webDetails = 
				(WebAuthenticationDetails)authentication.getDetails();
		LoginHistoryInput history = LoginHistoryInput.builder()
				.username(details.getUsername())
				.ip(webDetails.getRemoteAddress())
				.loginDt(LocalDateTime.now())
				.userAgent(request.getHeader("user-agent"))
				.build();
		
    	loginHistoryService.add(history);
		System.out.println("Login History saved.");
		super.onAuthenticationSuccess(request, response, authentication);
	}

	
}
