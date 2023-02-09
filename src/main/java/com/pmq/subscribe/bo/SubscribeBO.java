package com.pmq.subscribe.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmq.edition.bo.EditionBO;
import com.pmq.subscribe.dao.SubscribeDAO;
import com.pmq.subscribe.model.Subscribe;

@Service
public class SubscribeBO {
	// EditionBO 연결
	@Autowired
	private EditionBO editionBO;
	
	// SubscribeDAO 연결
	@Autowired
	private SubscribeDAO subscribeDAO;
	
	/**
	 * insert subscribe
	 * @param userId
	 * @param userLoginId
	 * @param editionId
	 */
	public void addOrDelSubscribe(int userId, String userLoginId, String userEmail, int editionId) {
		
		boolean existSubscribe = existSubscribe(userId, editionId);
		
		if (existSubscribe != true) {
			// 구독 중이지 않을 때 -> insert DB
			subscribeDAO.insertSubscribe(userId, userLoginId , userEmail , editionId);
		} else {
			subscribeDAO.deleteSubscribe(userId, editionId);
		}
	}
	
	/**
	 * exist subscribe -> 구독 여부
	 * @param userId
	 * @param editionId
	 * @return
	 */
	public boolean existSubscribe(int userId, int editionId) {
		int count = subscribeDAO.selectSubscribeByEditionIdOrUserId(userId, editionId);
		return count > 0? true: false;
	}
	
	/**
	 * select subscribe list 
	 * @param editionId
	 * @return
	 */
	public List<Subscribe> getSubscribeList(int editionId){
		return subscribeDAO.selectSubscribeList(editionId);
	}
	
	/**
	 * select subscribe list
	 * @param loginUserId
	 * @return
	 */
	public List<Subscribe> getSubscribeListByUserId (int loginUserId){
		return subscribeDAO.selectSubscribeListByUserId(loginUserId);
	}
	
	
	
}
