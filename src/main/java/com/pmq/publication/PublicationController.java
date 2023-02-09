package com.pmq.publication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pmq.publication.bo.PublicationBO;
import com.pmq.publication.model.Publication;

@RequestMapping("/publication")
@Controller
public class PublicationController {
	// MailBO 연결
	
	// publicationBO 연결
	@Autowired
	private PublicationBO publicationBO;
	
	// 발행글 작성 뷰(임시저장/발행)
	@RequestMapping("/create_view")
	public String publicationCreateView(
			@RequestParam("editionId") int editionId,
			@RequestParam("userId") int userId,
			// @RequestParam("publicationId") int publicationId,
			Model model) {
		
		model.addAttribute("editionId", editionId);
		model.addAttribute("userId", userId);
		// model.addAttribute("publicationId", publicationId);
		model.addAttribute("viewName", "publication/create");
		
		return "/template/layout_publication";
	}
	
	// 발행글 업데이트 뷰(임시저장->발행)
	@RequestMapping("/update_view")
	public String publicationUpdateView(
			@RequestParam("editionId") int editionId,
			@RequestParam("userId")int userId,
			@RequestParam("publicationId") int publicationId,
			Model model){
		
		Publication publication = publicationBO.getPublicationById(publicationId);
		model.addAttribute("publication" , publication);
		model.addAttribute("editionId", editionId);
		model.addAttribute("userId", userId);
		
		if (publication.getState() != "send") {
			model.addAttribute("viewName", "publication/update");
		}
		
		return "/template/layout_publication";
	}
}
