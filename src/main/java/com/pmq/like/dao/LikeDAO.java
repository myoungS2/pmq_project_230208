package com.pmq.like.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.pmq.like.model.Like;

@Repository
public interface LikeDAO {
	/**
	 * select like count 
	 * @param userId
	 * @param editionId
	 * @return
	 */
		public int selectLikeCountByEditionIdOrUserId(
				@Param("userId") int userId,
				@Param("editionId") int editionId);
	/**
	 * select like (for publisher)
	 * @param editionId
	 * @return
	 */
		public int selectLikeByEditionId(int editionId);
		
	/**
	 * insert like
	 * @param userId
	 * @param userNickname
	 * @param editionId
	 */
	public void insertLike(
			@Param("userId") int userId,
			@Param("userNickname") String userNickname,
			@Param("editionId") int editionId);
		
	/**
	 * delete like (좋아요 취소)
	 * @param userId
	 * @param editionId
	 */
	public void deleteLikeByEditionIdUserId(
			@Param("userId") int userId,
			@Param("editionId") int editionId);
		
	/**
	 * select likeList
	 * @param userId
	 * @return
	 */
	public List<Like> selectLikeListByUserId(int userId);
	
	/**
	 * delete like (edition 삭제)
	 * @param editionId
	 */
	public void deleteLikeByEditionId(int editionId);
}
