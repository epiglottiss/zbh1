package com.zerobase.fastlms.banner.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            //????????? ???????????? ??????
            return false;
        }
        replaceDuplicatedShowOrder(parameter);
        
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
            //????????? ???????????? ??????
            return false;
        }
        replaceDuplicatedShowOrder(parameter);
        
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
		replaceDuplicatedShowOrder(parameter);
		
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

	@Override
	public List<BannerDetailDto> list() {
		Optional<List<Banner>> optionalBanner = bannerRepository.findByShowFrontOrderByShowOrderAsc(true);
		List<BannerDetailDto> list = new ArrayList<BannerDetailDto>();
		if (!optionalBanner.isPresent()) {
            return list;
        }
		
		List<Banner> banners = optionalBanner.get();
		for(Banner banner : banners) {
			list.add(BannerDetailDto.of(banner));
		}
		return list;
	}

	private void replaceDuplicatedShowOrder(BannerInput parameter) {
		Optional<Banner> optionalBanner = bannerRepository.findByShowOrder(parameter.getShowOrder());
		List<Banner> changeList = new ArrayList<Banner>();
		while(optionalBanner.isPresent()) {
			Banner banner = optionalBanner.get();
			bannerRepository.delete(banner);
			banner.setShowOrder(banner.getShowOrder()+1);
			changeList.add(banner);
			
			optionalBanner = bannerRepository.findByShowOrder(banner.getShowOrder());
		}
		
		bannerRepository.flush();
		bannerRepository.saveAll(changeList);
	}

	
}
