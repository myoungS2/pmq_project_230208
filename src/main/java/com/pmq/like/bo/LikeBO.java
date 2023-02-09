package com.pmq.like.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmq.like.dao.LikeDAO;
import com.pmq.like.model.Like;

@Service
public class LikeBO {
	
	// LikeDAO 연결
	@Autowired
	private LikeDAO likeDAO;
	
		/** 
		 * like yes or no (좋아요 여부)
		 * @param userId
		 * @param userNickname
		 * @param editionId
		 */
		public void LikeYn(int userId, String userNickname, int editionId) {
			boolean existLike = existLike(userId, editionId);
			if (existLike) {
				likeDAO.deleteLikeByEditionIdUserId(userId, editionId);
			} else {
				likeDAO.insertLike(userId, userNickname, editionId);
			}
		}
		
		/**
		 * exist like (좋아요 존재->t/f)
		 * @param userId
		 * @param editionId
		 * @return
		 */
		public boolean existLike(int userId, int editionId) {
			int count = likeDAO.selectLikeCountByEditionIdOrUserId(userId, editionId);
			return count > 0? true : false; 
		}
		
		public int existLikeForPublisher(int editionId) {
			int count = likeDAO.selectLikeByEditionId(editionId);
			return count;
		}
	
		/** 
		 * select like count (좋아요개수)
		 * @param userId
		 * @param editionId
		 * @return
		 */
		public int getLikeCountByEditionId(int userId,int editionId) {
			return likeDAO.selectLikeCountByEditionIdOrUserId(userId ,editionId); 
		}
		
		/**
		 * select like (좋아요리스트)
		 * @param loginUserId
		 * @return
		 */
		public List<Like> getLikeListByUserId (int loginUserId){
			return likeDAO.selectLikeListByUserId(loginUserId);
		}
	
}
