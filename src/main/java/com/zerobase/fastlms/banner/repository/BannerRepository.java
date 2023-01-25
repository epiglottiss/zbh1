package com.zerobase.fastlms.banner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zerobase.fastlms.banner.entity.Banner;

public interface BannerRepository extends JpaRepository<Banner, String> {

}
