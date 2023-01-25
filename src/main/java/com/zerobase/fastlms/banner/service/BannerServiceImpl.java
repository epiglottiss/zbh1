package com.zerobase.fastlms.banner.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.banner.dto.BannerDetailDto;
import com.zerobase.fastlms.banner.dto.BannerDto;
import com.zerobase.fastlms.banner.entity.Banner;
import com.zerobase.fastlms.banner.mapper.BannerMapper;
import com.zerobase.fastlms.banner.model.BannerInput;
import com.zerobase.fastlms.banner.model.BannerParam;
import com.zerobase.fastlms.banner.repository.BannerRepository;
import com.zerobase.fastlms.course.entity.Course;
import com.zerobase.fastlms.loginhistory.entity.LoginHistory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BannerServiceImpl implements BannerService {

	private final BannerRepository bannerRepository;
	private final BannerMapper bannerMapper;
	
	@Override
	public List<BannerDto> list(BannerParam parameter) {
		
		long totalCount = bannerMapper.selectListCount(parameter);
		
		List<BannerDto> list = bannerMapper.selectList(parameter);
		
		if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for(BannerDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);           
                i++;
            }
        }
        
        return list;
	}

	@Override
	public BannerDetailDto getByName(String name) {
		Optional<Banner> banner = bannerRepository.findById(name);
		if(!banner.isPresent()) {
			return null;
		}
		
		return BannerDetailDto.of(banner.get());
	}

	@Override
	public boolean set(BannerInput parameter) {
		Optional<Banner> optionalBanner = bannerRepository.findById(parameter.getName());
        if (!optionalBanner.isPresent()) {
            //수정할 데이터가 없음
            return false;
        }
        
        Banner banner = optionalBanner.get();
        banner.setAlterText(parameter.getAlterText());
        banner.setClickLink(parameter.getClickLink());
        banner.setImageFile(parameter.getImageFile());
        banner.setName(parameter.getName());
        banner.setNewBrowser(parameter.getIsNewBrowser().equals("true"));
        banner.setRegDt(LocalDateTime.now());
        banner.setShowFront(parameter.isShowFront());
        banner.setShowOrder(parameter.getShowOrder());

        bannerRepository.save(banner);
        
        return true;
	}

	@Override
	public boolean set(BannerInput parameter, String newBannerName) {
		Optional<Banner> optionalBanner = bannerRepository.findById(parameter.getName());
        if (!optionalBanner.isPresent()) {
            //수정할 데이터가 없음
            return false;
        }
        
        Banner banner = Banner.builder()
        		.name(newBannerName)
				.alterText(parameter.getAlterText())
				.clickLink(parameter.getClickLink())
				.imageFile(parameter.getImageFile())
				.isNewBrowser(parameter.getIsNewBrowser().equals("true"))
				.regDt(LocalDateTime.now())
				.showFront(parameter.isShowFront())
				.showOrder(parameter.getShowOrder())
				.build();

        bannerRepository.save(banner);
        bannerRepository.delete(optionalBanner.get());
        return true;
	}
	
	@Override
	public boolean add(BannerInput parameter) {
		
		Banner banner = Banner.builder()
				.alterText(parameter.getAlterText())
				.clickLink(parameter.getClickLink())
				.imageFile(parameter.getImageFile())
				.isNewBrowser(parameter.getIsNewBrowser().equals("true"))
				.name(parameter.getName())
				.regDt(LocalDateTime.now())
				.showFront(parameter.isShowFront())
				.showOrder(parameter.getShowOrder())
				.build();
		bannerRepository.save(banner);
		
		return true;
	}



	
}
