package com.zerobase.fastlms.loginhistory.service;

import org.springframework.stereotype.Service;

import com.zerobase.fastlms.loginhistory.entity.LoginHistory;
import com.zerobase.fastlms.loginhistory.model.LoginHistoryInput;
import com.zerobase.fastlms.loginhistory.repository.LoginHistoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginHistoryServiceImpl implements LoginHistoryService{
	private final LoginHistoryRepository loginHistoryRepository;

	@Override
	public boolean add(LoginHistoryInput parameter) {
		LoginHistory history = LoginHistory.builder()
				.username(parameter.getUsername())
				.loginDt(parameter.getLoginDt())
				.ip(parameter.getIp())
				.userAgent(parameter.getUserAgent())
				.build();
		loginHistoryRepository.save(history);
		
		return false;
	}
	
	
}
