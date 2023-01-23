package com.zerobase.fastlms.loginhistory.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class LoginHistory {

	@Id
	private String username;
	private LocalDateTime loginDt;
	private String ip;
	private String userAgent; 
}
