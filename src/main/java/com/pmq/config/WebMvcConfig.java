package com.pmq.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	// 이미지 저장
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 // http://localhost//images/qwer_2389283923/alien.png -> 를 넣어줌
		registry.addResourceHandler("/images/**") 
		// 실제 파일 저장 위치  (=file:///(윈도우일때..!)FileManagerService.FILE_UPLOAD_PATH/) 
		// 1) 집 컴퓨터
		// .addResourceLocations("file:///D:\\웹개발(21-05-20)\\pmq\\pmq_workspace\\images/");
		.addResourceLocations("file:///D:\\web\\workspace4pmq\\pmq\\src\\main\\resources\\static\\images\\user_images/");
		// 2) 학원 컴퓨터
		// .addResourceLocations("file:///D:\\심미영_웹개발_210520\\7_personal_project\\pmq_workspace\\images/");
	}
}
