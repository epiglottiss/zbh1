package com.zerobase.fastlms.banner.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.zerobase.fastlms.banner.dto.BannerDetailDto;
import com.zerobase.fastlms.banner.dto.BannerDto;
import com.zerobase.fastlms.banner.model.BannerInput;
import com.zerobase.fastlms.banner.model.BannerParam;
import com.zerobase.fastlms.banner.service.BannerService;
import com.zerobase.fastlms.course.controller.AdminCourseController;
import com.zerobase.fastlms.course.controller.BaseController;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BannerController extends BaseController{
	private final BannerService bannerService;
	
	@GetMapping("/admin/banner/list.do")
	public String list(Model model, BannerParam parameter) {
		
		parameter.init();
		List<BannerDto> banners = bannerService.list(parameter);
		
		long totalCount = 0;
        if (banners != null && banners.size() > 0) {
            totalCount = banners.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = getPaperHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);
        
        model.addAttribute("list", banners);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);
		
		return "admin/banner/list";
	}
	
	@GetMapping(value ={"/admin/banner/add.do", "/admin/banner/edit.do"})
	public String add(Model model, HttpServletRequest request, BannerInput parameter) {
		
		 boolean editMode = request.getRequestURI().contains("/edit.do");
	        BannerDetailDto detail = new BannerDetailDto();
	        
	        if (editMode) {
	            String bannerName = parameter.getName();
	            BannerDetailDto existBanner = bannerService.getByName(bannerName);
	            if (existBanner == null) {
	                // error ??????
	                model.addAttribute("message", "??????????????? ???????????? ????????????.");
	                return "common/error";
	            }
	            detail = existBanner;
	        }
	        
	        model.addAttribute("editMode", editMode);
	        model.addAttribute("detail", detail);
		return "admin/banner/add";
	}
	
	private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String originalFilename) {
	    
        LocalDate now = LocalDate.now();
    
        String[] dirs = {
                String.format("%s/%d/", baseLocalPath,now.getYear()),
                String.format("%s/%d/%02d/", baseLocalPath, now.getYear(),now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())};
        
        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        
        for(String dir : dirs) {
            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }
        
        String fileExtension = "";
        if (originalFilename != null) {
            int dotPos = originalFilename.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }
        
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newFilename = String.format("%s%s", dirs[2], uuid);
        String newUrlFilename = String.format("%s%s", urlDir, uuid);
        if (fileExtension.length() > 0) {
            newFilename += "." + fileExtension;
            newUrlFilename += "." + fileExtension;
        }
    
        return new String[]{newFilename, newUrlFilename};
    }
	
	@PostMapping(value= {"/admin/banner/add.do", "/admin/banner/edit.do"})
	public String addSubmit(Model model, HttpServletRequest request
                            , MultipartFile file
            , BannerInput parameter) {
		String saveFilename = "";
        String urlFilename = "";
        
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            
            String baseLocalPath = "C:/Users/user/Desktop/std/zerobase-backend/web/work1/fastlms3/Files";
            String baseUrlPath = "/files";
            
            String[] arrFilename = getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);
    
            saveFilename = arrFilename[0];
            urlFilename = arrFilename[1];
            
            try {
                File newFile = new File(saveFilename);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e) {
                log.info("############################ - 1");
                log.info(e.getMessage());
            }
        }
        
        parameter.setFilename(saveFilename);
        parameter.setImageFile(urlFilename);
        
        boolean editMode = request.getRequestURI().contains("/edit.do");
        
        if (editMode) {
            String bannerName = parameter.getName();
            String[] names = bannerName.split(",");
            String newBannerName = names[0];
            String oldBannerName = names[1];
            parameter.setName(oldBannerName);
            BannerDetailDto existBanner = bannerService.getByName(oldBannerName);          
            
            if (existBanner == null) {
                // error ??????
                model.addAttribute("message", "??????????????? ???????????? ????????????.");
                return "common/error";
            }
            
            if(oldBannerName.equals(newBannerName)) {
            	bannerService.set(parameter);
            }
            else {
            	bannerService.set(parameter, newBannerName);
            }
   
        } else {
        	BannerDetailDto existBanner = bannerService.getByName(parameter.getName());
        	 if (existBanner != null) {
                 // error ??????
                 model.addAttribute("message", "????????? ????????? ????????? ???????????????.");
                 return "common/error";
             }
        	
        	bannerService.add(parameter);
        }
        
        return "redirect:/admin/banner/list.do";
	}
}
