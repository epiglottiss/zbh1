package com.zerobase.fastlms.banner.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Banner {
	@Id
	String name;
	String imageFile;
	String alterText;
	String clickLink;
	boolean isNewBrowser;
	
	@Column(nullable = false,unique = true)
	int showOrder;
	boolean showFront;
	LocalDateTime regDt;
	
}
