package com.pmq.test;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pmq.test.bo.TestBO;

@Controller
public class TestController {
	// TestBO 연결
	@Autowired
	private TestBO testBO;
	
	@ResponseBody
	@RequestMapping("/test1")
	public String test1() {
		return "Hello World!";
	}
	
	// DB 연동 테스트 -> PmqApplication에서 @EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class}) 어노테이션 없애주기
	@RequestMapping("/test2")
	@ResponseBody
	public List<Map<String, Object>> test2(){
		return testBO.getUserList();
	}
	
	// JSP 연동 테스트
	@RequestMapping("/test3")
	public String test3() {
		return "test/test"; // jsp
	}
}
