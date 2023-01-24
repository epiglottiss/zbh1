package com.zerobase.fastlms.loginhistory.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
@Builder
public class LoginHistoryInput {
	private String username;
	private LocalDateTime loginDt;
	private String ip;
	private String userAgent; 
}
