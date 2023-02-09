package com.pmq.edition;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pmq.edition.bo.EditionBO;

@RequestMapping("edition")
@RestController
public class EditionRestController {
	
	// EditionBO 연결
	@Autowired
	private EditionBO editionBO;
	
	/**
	 * create edition
	 * @param file
	 * @param subject
	 * @param category
	 * @param publishingDate
	 * @param content
	 * @param request
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> editionCreate(
		@RequestParam("file") MultipartFile file,
		@RequestParam("subject") String subject,
		@RequestParam("category") String category,
		@RequestParam("publishingDate") int publishingDate,
		@RequestParam("content") String content,
		HttpServletRequest request){
		
		// session에서 유저 id를 가져온다.
		HttpSession session = request.getSession(); // edition > create에서 ajax가 잘 작동되면 여기로 들어오게 됨..!(breakPoint)
		Integer userId = (Integer) session.getAttribute("userId"); // 어디에 session을 넣었는지 잘 확인하기(UserRestController)
		String userLoginId = (String) session.getAttribute("userLoginId");
				
		Map<String, Object> result = new HashMap<>();
		result.put("result", "error");
				
		// insert DB
		int row = editionBO.createEdition(userId, userLoginId ,file, subject, category, publishingDate, content);
		if (row > 0) {
			// 성공
			result.put("result", "success");
		} else {
			result.put("result", "error");
		}
		return result;
	}
	
	/**
	 * update edition
	 * @param editionId
	 * @param file
	 * @param subject
	 * @param category
	 * @param publishingDate
	 * @param content
	 * @param request
	 * @return
	 */
	@PostMapping("/update")
	public Map<String, Object> editionUpdate(
			@RequestParam("editionId") int editionId,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam("subject") String subject,
			@RequestParam("category") String category,
			@RequestParam("publishingDate") int publishingDate,
			@RequestParam("content") String content,
			HttpServletRequest request) {
		
		// session에서 유저 id를 가져온다.
		HttpSession session = request.getSession(); // edition > create에서 ajax가 잘 작동되면 여기로 들어오게 됨..!(breakPoint)
		Integer userId = (Integer) session.getAttribute("userId"); // 어디에 session을 넣었는지 잘 확인하기(UserRestController)
		String userLoginId = (String) session.getAttribute("userLoginId");
		
		// file이 null이라면, 기존 파일 그대로 업데이트...
		if (file == null) {
			
		}
		
		
		// update DB
		editionBO.updateEdition(editionId ,userId, userLoginId ,file, subject, category, publishingDate, content);
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		
		return result;
	}
	
	/**
	 * delete edtion
	 * @param editionId
	 * @return
	 */
	@DeleteMapping("/delete")
	public Map<String, Object> delete(
			@RequestParam("editionId") int editionId){
		
		// delete DB
		editionBO.deleteEdition(editionId);
		
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		
		
		return result;
	}
	
}
