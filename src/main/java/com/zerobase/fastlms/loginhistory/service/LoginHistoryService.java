package com.zerobase.fastlms.loginhistory.service;

import com.zerobase.fastlms.loginhistory.model.LoginHistoryInput;

public interface LoginHistoryService {

	/**
	 * 히스토리 추가
	 */
	boolean add(LoginHistoryInput parameter);
}
