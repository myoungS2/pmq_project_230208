package com.pmq.subscribe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.pmq.subscribe.model.Subscribe;

@Repository
public interface SubscribeDAO {
	
	/**
	 * select subscribe
	 * @param userId
	 * @param editionId
	 * @return
	 */
	public int selectSubscribeByEditionIdOrUserId(
			@Param("userId") int userId,
			@Param("editionId") int editionId);
	
	/**
	 * insert subscribe
	 * @param userId
	 * @param userLoginId
	 * @param userEmail
	 * @param editionId
	 */
	public void insertSubscribe(
			@Param("userId") int userId,
			@Param("userLoginId") String userLoginId,
			@Param("userEmail") String userEmail,
			@Param("editionId") int editionId);
	
	/**
	 * delete subscribe (구독취소)
	 * @param userId
	 * @param editionId
	 */
	public void deleteSubscribe(
			@Param("userId") int userId,
			@Param("editionId") int editionId);
	
	/**
	 * select subscribe like
	 * @param editionId
	 * @return
	 */
	public List<Subscribe> selectSubscribeList(int editionId);
	
	/**
	 * select subscribe like
	 * @param userId
	 * @return
	 */
	public List<Subscribe> selectSubscribeListByUserId(int userId);
	
	/** 
	 * delete subscribe (edition 삭제)
	 * @param editionId
	 */
	public void deleteSubscribeByEditionId(int editionId);
	
}
