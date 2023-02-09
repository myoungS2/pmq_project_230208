package com.pmq.publication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pmq.publication.bo.MailBO;
import com.pmq.publication.bo.PublicationBO;
import com.pmq.publication.model.Mail;
import com.pmq.publication.model.Publication;
import com.pmq.subscribe.bo.SubscribeBO;
import com.pmq.subscribe.model.Subscribe;

@RequestMapping("/publication")
@RestController
public class PublicationRestController {
	// SubscriberBO 연결
	@Autowired
	private SubscribeBO subscribeBO;
	

	// PublicationBO 연결
	@Autowired
	private PublicationBO publicationBO;
	
	// 발행 (발행)
	@PostMapping("/email/send")
	public Map<String, Object> publicationSend(
			@RequestParam("editionId") int editionId,
			@RequestParam("userId") int userId,
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam("state") String state,
			HttpServletRequest request,
			Model model){
		
		// session에서 유저정보 가져오기
		HttpSession session = request.getSession();
		String userNickname = (String) session.getAttribute("userNickname");
		Integer loginUserId = (Integer)session.getAttribute("userId");	
		
		Map<String, Object> result = new HashMap<>();
		
		// insert DB
		int row = publicationBO.createPublication(editionId, userId, userNickname, subject, content, state);
		if (row > 0) {
			result.put("result", "success");
			
		} else {
			result.put("result", "error");
		}
		
		// 발행글
		Publication publication = publicationBO.getPublicationById(userId);
		model.addAttribute("publication", publication);
		
		// 구독자 이메일 주소
		List<Subscribe> subscriberInfo = subscribeBO.getSubscribeList(editionId);
		model.addAttribute("subscriberInfo", subscriberInfo);
		for (Subscribe subscriber : subscriberInfo) {
			String subscriberEmail = subscriber.getuserEmail();
			model.addAttribute("subscriberEmail", subscriberEmail);
		}
		
		return result;
		
	}
	
	// 업데이트
	
	
	
}

