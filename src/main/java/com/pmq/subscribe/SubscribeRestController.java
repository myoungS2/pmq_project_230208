package com.pmq.subscribe;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pmq.subscribe.bo.SubscribeBO;

@RequestMapping("/subscribe")
@RestController
public class SubscribeRestController {
	// SubscribeBO 연결
	@Autowired
	private SubscribeBO subscribeBO;
	
	// logger
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * subscribe start (구독시작)
	 * @param editionId
	 * @param request
	 * @return
	 */
	@RequestMapping("/start")
	public Map<String, Object> subscribeStart(
			@RequestParam("editionId") int editionId,
			HttpServletRequest request){
		
		Map<String, Object> result = new HashMap<>();
		
		HttpSession session = request.getSession();
		String userLoginId = (String) session.getAttribute("userLoginId");
		Integer userId = (Integer) session.getAttribute("userId");
		String userEmail = (String) session.getAttribute("userEmail");
				
		if (userId == null) {
			result.put("result", "error");
			logger.error("[like] No userId");
			return result;
		}
		
		// insert DB
		subscribeBO.addOrDelSubscribe(userId, userLoginId, userEmail, editionId);
		result.put("result", "success");
		
		return result;
	}
}
