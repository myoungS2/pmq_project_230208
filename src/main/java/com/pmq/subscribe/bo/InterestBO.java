package com.pmq.subscribe.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmq.edition.bo.EditionBO;
import com.pmq.edition.model.Edition;
//import com.pmq.like.bo.LikeBO;
//import com.pmq.like.model.Like;
import com.pmq.subscribe.model.InterestView;
import com.pmq.subscribe.model.Subscribe;

@Service
public class InterestBO {
	// EditionBO 연결
	@Autowired
	private EditionBO editionBO;
	
	// SubscribeBO 연결
	@Autowired
	private SubscribeBO subscribeBO;
	
	// edition - subscribe 가져오기
	public List<InterestView> generateInterestViewList(int loginUserId){
		List<InterestView> interestViewList = new ArrayList<>();
		
			List<Subscribe> subscribeList = subscribeBO.getSubscribeListByUserId(loginUserId);
			// subscribeList -> 반복문 돌려서 subscribe 1개를 꺼냄
			for (Subscribe subscribe : subscribeList) {
				// 구독중인 1개의 edition과 맵핑 될 insertest 만들기
				InterestView interest = new InterestView();
				
				// 하나의 subscribe와 맵핑 되는 하나의 edition
				Edition edition = editionBO.getEdition(subscribe.getEditionId());
				
				// 매핑된 eiditon-subscribe 한행을 interest에 담음 
				interest.setSubscribe(subscribe);
				interest.setEdition(edition);
				
				interestViewList.add(interest);
			}
			
			return interestViewList;
	}
	
	
}