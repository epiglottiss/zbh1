package com.zerobase.fastlms.banner.model;

import lombok.Data;

@Data
public class BannerInput {
	String name;
	String imageFile;
	String alterText;
	String clickLink;
	String isNewBrowser;
	
	int showOrder;
	boolean showFront;
	
    String filename;
}
