package com.zerobase.fastlms.banner.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zerobase.fastlms.banner.entity.Banner;

public interface BannerRepository extends JpaRepository<Banner, String> {
	Optional<List<Banner>> findByShowFrontOrderByShowOrderAsc(boolean showFront);
}
