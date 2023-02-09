package com.pmq.edition.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.pmq.edition.model.Edition;
import com.pmq.subscribe.model.Subscribe;

@Repository
public interface EditionDAO {
	
	/**
	 * insert edition
	 * @param userId
	 * @param userNickname
	 * @param thumbnailPath
	 * @param subject
	 * @param category
	 * @param publishingDate
	 * @param content
	 * @return
	 */
	public int insertEdition(
		@Param("userId") int userId,
		@Param("userLoginId") String userLoginId,
		@Param("thumbnailPath")String thumbnailPath,
		@Param("subject")String subject,
		@Param("category")String category,
		@Param("publishingDate")int publishingDate,
		@Param("content")String content);
	

	/**
	 * select edition List
	 * @return
	 */
	public List<Edition> selectEditionList();
	
	/**
	 * select edition by editionId
	 * @param id
	 * @return
	 */
	public Edition selectEdition(int id);
	
	/**
	 * update edition
	 * @param editionId
	 * @param userId
	 * @param userLoginId
	 * @param thumbnailPath
	 * @param subject
	 * @param category
	 * @param publishingDate
	 * @param content
	 */
	public void updateEdition(
			@Param("id") int editionId,
			@Param("userId") int userId,
			@Param("userLoginId") String userLoginId,
			@Param("thumbnailPath")String thumbnailPath,
			@Param("subject")String subject,
			@Param("category")String category,
			@Param("publishingDate")int publishingDate,
			@Param("content")String content);
	
	/**
	 * delete edition
	 * @param id
	 */
	public void deleteEdition(int id);
	
	/**
	 * select edition (발행인의 발행글 리스트 가져오기)
	 * @param loginUserId
	 * @return
	 */
	public List<Edition> selectEditionListByUserId(int userId);
	
	/**
	 * select edition (구독자의 구독중인 발행글 리스트 가져오기)
	 * @param id
	 * @return
	 */
	public List<Edition> selectEditionListByEditionId(int id);
	
	/**
	 * select edition (타임라인 검색기능)
	 * @param keyword
	 * @return
	 */
	public List<Edition> selectEditionListByKeyword(String keyword);
}
