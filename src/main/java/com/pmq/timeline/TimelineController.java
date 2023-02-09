package com.pmq.timeline;

import java.io.Console;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pmq.edition.bo.EditionBO;
import com.pmq.edition.model.Edition;
import com.pmq.user.bo.UserBO;
import com.pmq.user.model.User;

@RequestMapping("/timeline")
@Controller
public class TimelineController {
	
	// UserBO 연결
	@Autowired
	private UserBO userBO;
	
	// EditionBO 연결
	@Autowired
	private EditionBO editionBO;
	
	/**
	 * timeline view
	 * @param model
	 * @param request
	 * @param keyword
	 * @return
	 */
	@RequestMapping("/view")
	public String timelineView(
			Model model, 
			HttpServletRequest request,
			@RequestParam(value = "keyword", required = false) String keyword) {
		
		// 로그인 여부 확인-> userId정보 내려주기
		HttpSession session = request.getSession();
		 if (session.getAttribute("userId") == null) {
			 
		 } else {
			Integer userId = (Integer) session.getAttribute("userId");
			// userId로 userRole정보 내려주기 -> 발행시작 버튼 (publisher일때만 보이게)
			User userInfo = userBO.getUser(userId);
			if (userInfo != null) {
				model.addAttribute("userInfo", userInfo);
				model.addAttribute("viewName" ,"timeline");
			} 
		 }
		 
		 
		// edition(전체) 가져와서 thumbnailPath 뿌려주기
		List<Edition> editionList = editionBO.getEditionList();
		model.addAttribute("editionList", editionList);
		
		// 검색어로 editionList가져오기
		List<Edition> searchEditionList = editionBO.getEditionListByKeyword(keyword);
		model.addAttribute("searchEditionList", searchEditionList);
		if(searchEditionList != null) {
			model.addAttribute("keyword", keyword);
		}
		model.addAttribute("viewName", "timeline");
		
		return "template/layout_timeline";
	}
	
}
