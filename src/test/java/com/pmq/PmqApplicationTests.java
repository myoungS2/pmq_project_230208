package com.pmq;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.pmq.subscribe.bo.SubscribeBO;
import com.pmq.subscribe.dao.SubscribeDAO;
import com.pmq.user.bo.UserBO;
import com.pmq.user.model.User;

@SpringBootTest
class PmqApplicationTests {

	// logger
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// DB연동
	@Autowired
	private UserBO userBO;
	
	// @Test
	void contextLoads() {
		logger.debug("### Hello world!!! ###");
	}
	
	@Test
	// 메인함수
	void 더하기테스트() {
		logger.debug("$$$ 더하기 테스트 시작!!!");
		
		int a = 10;
		int b = 20;
		assertEquals(30, sum(a, b)); // 둘이 일치하는지..!
	}
	
	// 메소드
	int sum(int x, int y) {
		return x + y;
	}
	
	@Transactional // -> DB에 CRUD를 시도하다가 오류가 나면, rollback
	@Test
	void 디비테스트() {
		// User user = userBO.getUser;
		// 유저를 가지고 오는 와서 null인지 검사
		// assertNotNull(user);
	}
	


}

