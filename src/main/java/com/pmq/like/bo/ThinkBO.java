package com.pmq.like.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmq.edition.bo.EditionBO;
import com.pmq.edition.model.Edition;
import com.pmq.like.model.Like;
import com.pmq.like.model.ThinkView;

@Service
public class ThinkBO {
	// EditionBO 연결
	@Autowired
	private EditionBO editionBO;
	
	// LikeBO 연결
	@Autowired
	private LikeBO likeBO;
	
	// edition - like 가져오기
	public List<ThinkView> generateThinkViewList(int loginUserId){
		List<ThinkView> thinkViewList = new ArrayList<>();
		
		List<Like> likeList = likeBO.getLikeListByUserId(loginUserId);
		 // likeList -> 반복문 돌려서 하나의 like를 꺼냄
		for (Like like : likeList) {
			// 좋아요한 하나의 edition과 맵핑 될 think 만들기
			ThinkView think = new ThinkView();
			
			// 하나의 like와 맵핑 되는 하나의 edition
			Edition edition = editionBO.getEdition(like.getEditionId());
			
			// 맵핑 된 edition - like 한행을 think에 담기
			think.setLike(like);
			think.setEdition(edition);
			
			thinkViewList.add(think);
		}
		
		return thinkViewList;
	}
}
