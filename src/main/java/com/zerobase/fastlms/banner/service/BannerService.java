package com.zerobase.fastlms.banner.service;

import java.util.List;

import com.zerobase.fastlms.banner.dto.BannerDetailDto;
import com.zerobase.fastlms.banner.dto.BannerDto;
import com.zerobase.fastlms.banner.model.BannerInput;
import com.zerobase.fastlms.banner.model.BannerParam;

public interface BannerService {

	public List<BannerDto> list(BannerParam bannerParam);
	
	public BannerDetailDto getByName(String name);
	
	/**
	 * 배너 정보 수정
	 */
	boolean set(BannerInput parameter);
	
	/**
	 * 배너 정보 수정, pk 변경
	 */
	boolean set(BannerInput parameter, String newBannerName);
	
	/**
	 * 배너 추가
	 */
	boolean add(BannerInput parameter);
}
