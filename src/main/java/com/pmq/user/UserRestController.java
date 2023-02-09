package com.pmq.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pmq.common.EncryptUtils;
import com.pmq.user.bo.UserBO;
import com.pmq.user.model.User;

@RestController
@RequestMapping("/user")
public class UserRestController {
	// logger
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// UserBO 연결
	@Autowired
	private UserBO userBO;
	
	/**
	 * duplicate check (아이디 중복확인)
	 * @param loginId
	 * @return
	 */
	@RequestMapping("/is_duplicated_id")
	public Map<String, Boolean> isDuplicatedId (
			@RequestParam("loginId") String loginId){
		// 아이디 중복 여부 -> DB조회
		// 중복여부에 대한 결과 -> Map생성
		Map<String, Boolean> result = new HashMap<>();
		result.put("result", userBO.existLoginId(loginId) );
		
		// return result
		return result;
	}
	
	/**
	 * duplicate check (닉네임 중복확인)
	 * @param nickname
	 * @return
	 */
	@RequestMapping("/is_duplicated_nickname")
	public Map<String, Boolean> isDuplicatedNickname (
			@RequestParam("nickname") String nickname) {
		// 닉네임 중복 여부 -> DB조회
		
		// 중복여부에 대한 결과 -> Map생성
		Map<String, Boolean> result = new HashMap<>();
		result.put("result", userBO.existNickname(nickname));
		
		return result;
	}
	
	/**
	 * sign up
	 * @param loginId
	 * @param password
	 * @param file
	 * @param name
	 * @param nickname
	 * @param address
	 * @param website
	 * @param introduce
	 * @param role
	 * @return
	 */
	@PostMapping("/sign_up")
	public Map<String, Object> singUp (
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam("name") String name,
			@RequestParam("nickname") String nickname,
			@RequestParam("email") String email,
			@RequestParam("address") String address,
			@RequestParam(value = "website", required = false) String website,
			@RequestParam(value = "introduce", required = false) String introduce,
			@RequestParam("role") String role){
		
		// 비밀번호 -> 해싱
		String encryptPassword = EncryptUtils.md5(password);
		
		Map<String, Object> result = new HashMap<>();
		int row = userBO.addUser(loginId, encryptPassword, file, name, nickname, email, address, website, introduce, role);
		if (row > 0) {
			result.put("result", "success");
		} else {
			result.put("result", "error");
		}
		
		return result;
	}
	
	
	/**
	 * sign in
	 * @param loginId
	 * @param password
	 * @param request
	 * @return
	 */
	@PostMapping("/sign_in")
	public Map<String, Object> SignIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request){

		// 파라미터로 받은 비밀번호를 해싱한다.
		String encryptPassword = EncryptUtils.md5(password);
		
		// select DB (id와 해싱 된 pw로)
		User user = userBO.getUserByLoginIdAndPassword(loginId, encryptPassword);
		
		Map<String, Object> result = new HashMap<>();
		// 일치하는 값 있으면 -> sign in success
		if (user != null) {
			result.put("result", "success");
		// 세션 (로그인 상태 유지)
			HttpSession session = request.getSession();
		// 담고싶은 정보 담기
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userNickname", user.getNickname());
			session.setAttribute("userId", user.getId());
			session.setAttribute("userEmail", user.getEmail());
			
		} else {
			// 일치하는 값 없으면 -> sign in fail
			result.put("result", "error");
		}
		
		return result;
	}
	
	/**
	 * update user profile
	 * @param file
	 * @param nickname
	 * @param email
	 * @param address
	 * @param website
	 * @param introduce
	 * @param request
	 * @param model
	 * @return
	 */
	@PostMapping("/profile_update")
	public Map<String, Object> profileUpdate(
			@RequestParam(value="file", required = false) MultipartFile file,
			@RequestParam(value="nickname", required = false) String nickname,
			@RequestParam("email") String email,
			@RequestParam("address") String address,
			@RequestParam(value = "website", required = false) String website,
			@RequestParam(value = "introduce", required = false) String introduce, 
			HttpServletRequest request,
			Model model){

		// session에서 유저 id를 가져온다.
		HttpSession session = request.getSession(); // edition > create에서 ajax가 잘 작동되면 여기로 들어오게 됨..!(breakPoint)
		Integer userId = (Integer) session.getAttribute("userId"); // 어디에 session을 넣었는지 잘 확인하기(UserRestController)
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		// update DB
		userBO.updateUser(userId, userLoginId, file, nickname, email, address, website, introduce);
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		
		return result;
	}
	
	/**
	 * duplicate check (프로파일 업데이트 닉네임 중복확인)
	 * @param nickname
	 * @param userId
	 * @return
	 */
	@RequestMapping("/is_duplicated_update_nickname")
	public Map<String, Boolean> isDuplicatedUpdateNickname (
			@RequestParam("nickname") String nickname,
			@RequestParam("userId") int userId) {
		
		
		Map<String, Boolean> result = new HashMap<>();
		
		// 닉네임 중복 여부 -> DB조회
		User user = userBO.getUser(userId);
		
		if (userBO.existNickname(nickname) && user != null) {
			result.put("result", true);
		} else {
			}
		
		// 중복여부에 대한 결과 -> Map생성
		
		result.put("result", userBO.existNickname(nickname));
		
		return result;
	}
	
}
