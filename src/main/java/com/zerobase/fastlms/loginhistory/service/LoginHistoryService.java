package com.zerobase.fastlms.loginhistory.service;

import java.time.LocalDateTime;
import java.util.List;

import com.zerobase.fastlms.loginhistory.entity.LoginHistory;
import com.zerobase.fastlms.loginhistory.model.LoginHistoryInput;

public interface LoginHistoryService {

	/**
	 * 히스토리 추가
	 */
	boolean add(LoginHistoryInput parameter);
	
	/**
	 * 로그인 내역 얻기
	 */
	List<LoginHistory> list(String userId);
}
