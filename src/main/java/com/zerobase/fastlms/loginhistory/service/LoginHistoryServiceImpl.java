package com.zerobase.fastlms.loginhistory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	@Override
	public List<LoginHistory> list(String userId) {
		Optional<List<LoginHistory>> list = loginHistoryRepository.findByUsername(userId);
		if(!list.isPresent()) {
			return new ArrayList<LoginHistory>();
		}
		return list.get();
	}
	
	
}
