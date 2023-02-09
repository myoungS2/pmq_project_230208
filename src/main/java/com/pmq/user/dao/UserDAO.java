package com.pmq.user.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.pmq.user.model.User;

@Repository
public interface UserDAO {
	
	/**
	 * duplicate check (아이디 중복확인)
	 * @param loginId
	 * @return
	 */
	public boolean existLoginId(String loginId);
	
	/**
	 * duplicate check (닉네임 중복확인)
	 * @param nickname
	 * @return
	 */
	public boolean existNickname(String nickname);
	
	/** 
	 * insert user
	 * @param loginId
	 * @param password
	 * @param profileImgPath
	 * @param name
	 * @param nickname
	 * @param address
	 * @param website
	 * @param introduce
	 * @param role
	 * @return
	 */
	public int insertUser(
			@Param("loginId")String loginId, 
			@Param("password")String password, 
			@Param("profileImgPath")String profileImgPath, 
			@Param("name")String name, 
			@Param("nickname")String nickname, 
			@Param("email")String email,
			@Param("address")String address, 
			@Param("website")String website,
			@Param("introduce")String introduce, 
			@Param("role")String role);
	
	/**
	 * select user
	 * @param loginId
	 * @param password
	 * @return
	 */
	public User selectUserByLoginIdAndPassword(
			@Param("loginId")String loginId, 
			@Param("password")String password);
	
	/**
	 * select user (userRole)
	 * @param loginUserId
	 * @return
	 */
	public User selectUser(int id);
	
	/**
	 * select user (publisher info)
	 * @param userId
	 * @return
	 */
	public User selectPublisher(int id);
	
	/**
	 * update user
	 * @param id
	 * @param profileImgPath
	 * @param nickname
	 * @param email
	 * @param address
	 * @param website
	 * @param introduce
	 */
	public void updateUser(
			@Param("id")int id,
			@Param("profileImgPath")String profileImgPath, 
			@Param("nickname")String nickname, 
			@Param("email")String email,
			@Param("address")String address, 
			@Param("website")String website,
			@Param("introduce")String introduce);
}
