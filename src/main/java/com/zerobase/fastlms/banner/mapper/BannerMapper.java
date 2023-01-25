package com.zerobase.fastlms.banner.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zerobase.fastlms.banner.dto.BannerDto;
import com.zerobase.fastlms.banner.model.BannerParam;

@Mapper
public interface BannerMapper {
	long selectListCount(BannerParam parameter);
	List<BannerDto> selectList(BannerParam parameter);
}
