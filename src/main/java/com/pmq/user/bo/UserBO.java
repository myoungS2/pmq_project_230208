package com.pmq.user.bo;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pmq.common.FileManagerService;
import com.pmq.edition.model.Edition;
import com.pmq.subscribe.bo.SubscribeBO;
import com.pmq.user.dao.UserDAO;
import com.pmq.user.model.User;

@Service
public class UserBO {
	// logger
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// FileManagerService 연결
	@Autowired
	private FileManagerService fileManagerService;
	
	// subscribeBO 연결
	@Autowired
	private SubscribeBO subscribeBO;
	
	// UserDAO 연결
	@Autowired
	private UserDAO userDAO;
	
	/**
	 *  duplicate check (아이디 중복확인)
	 * @param loginId
	 * @return
	 */
	public boolean existLoginId(String loginId) {
		return userDAO.existLoginId(loginId); 
	}
	
	/**
	 *  duplicate check (닉네임 중복확인)
	 * @param nickname
	 * @return
	 */
	public boolean existNickname(String nickname) {
		return userDAO.existNickname(nickname);
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
	public int addUser(String loginId, String password, MultipartFile file, String name, 
			String nickname, String email ,String address, String website, String introduce, String role) {
		 String profileImgPath = null; 
		 if (file != null) { try {
				profileImgPath = fileManagerService.saveFile(loginId, file);
			} catch (IOException e) {
				logger.error("[파일업로드 에러] " + e.getMessage());
			}
		}
		 
		return userDAO.insertUser(loginId, password, profileImgPath, name, nickname, email, address, website, introduce, role); 
	}
	
	/**
	 * sign in
	 * @param loginId
	 * @param password
	 * @return
	 */
	public User getUserByLoginIdAndPassword(String loginId, String password) {
		return userDAO.selectUserByLoginIdAndPassword(loginId, password);
	}
	
	/**
	 * select user (userRole)
	 * @param loginUserId
	 * @return
	 */
	public User getUser(int loginUserId) {
		return userDAO.selectUser(loginUserId);
	}
	
	/** 
	 * select user (publisher info)
	 * @param userId
	 * @return
	 */
	public User getPublisher(int userId) {
		return userDAO.selectPublisher(userId);
	}
	
	
	/**
	 * update user
	 * @param userId
	 * @param userLoginId
	 * @param file
	 * @param nickname
	 * @param email
	 * @param address
	 * @param website
	 * @param introduce
	 */
	public void updateUser(int userId, String userLoginId, MultipartFile file, String nickname, String email, 
			String address, String website, String introduce) {
			
			// user 유무 검증
			User user = getUser(userId);
			if (user == null) {
				logger.error("[유저프로필 수정] user is null. userId:{}", userId); 
				return;
			}
		
			// file이 있으면 업로드 후 thumbnailPath를 얻어와야한다.
			String profileImgPath = user.getProfileImgPath();
				if(file != null) {
					try {
						profileImgPath = fileManagerService.saveFile(userLoginId, file);
						
					// 기존에 있던 파일 제거 -> thumbnailPath가 존재 && 기존에 파일이 있을 시
					if(profileImgPath != null && user.getProfileImgPath() != null) {
						// 업로드가 실패할 수 있으므로, 업로드 성공 후 제거
					fileManagerService.deleteFile(user.getProfileImgPath());
					}
					} catch (IOException e) {
						
					}
				}
				// update DB
				userDAO.updateUser(userId, profileImgPath, nickname, email, address, website, introduce);
	}
	
	
}
