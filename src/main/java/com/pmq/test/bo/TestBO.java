package com.pmq.test.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmq.test.dao.TestDAO;

@Service
public class TestBO {
	// TestDAO 연결
	@Autowired
	private TestDAO testDAO;
	
	public List<Map<String, Object>> getUserList() {
		return testDAO.selectUserList();
	}
	
}
