package com.zerobase.fastlms.loginhistory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zerobase.fastlms.loginhistory.entity.LoginHistory;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, String>{
	Optional<LoginHistory> findByUsername(String username);
}
