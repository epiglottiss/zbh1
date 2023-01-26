package com.zerobase.fastlms.banner.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;

import com.zerobase.fastlms.banner.entity.Banner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BannerDetailDto {
	String name;
	String imageFile;
	String alterText;
	String clickLink;
	boolean isNewBrowser;
	
	int showOrder;
	boolean showFront;
	LocalDateTime regDt;
	
	public static BannerDetailDto of(Banner banner) {
		return BannerDetailDto.builder()
				.name(banner.getName())
				.imageFile(banner.getImageFile())
				.alterText(banner.getAlterText())
				.clickLink(banner.getClickLink())
				.isNewBrowser(banner.isNewBrowser())
				.showOrder(banner.getShowOrder())
				.showFront(banner.isShowFront())
				.regDt(banner.getRegDt())
				.build();
	}
}
