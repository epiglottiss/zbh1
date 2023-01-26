package com.zerobase.fastlms.banner.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.banner.entity.Banner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BannerDto {
	String name;
	String imageFile;
	LocalDateTime regDt;
	
	long totalCount;
	long seq;
	
	public static BannerDto of(Banner banner) {
		return BannerDto.builder()
				.name(banner.getName())
				.imageFile(banner.getImageFile())
				.regDt(banner.getRegDt())
				.build();
	}
	
}
