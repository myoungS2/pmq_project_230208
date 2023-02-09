package com.pmq.like.model;

import java.util.Date;

public class Like {
private int	id;
private int	userId;
private String userNickname;
private int	editionId;
private Date createdAt;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public String getUserNickname() {
	return userNickname;
}
public void setUserNickname(String userNickname) {
	this.userNickname = userNickname;
}
public int getEditionId() {
	return editionId;
}
public void setEditionId(int editionId) {
	this.editionId = editionId;
}
public Date getCreatedAt() {
	return createdAt;
}
public void setCreatedAt(Date createdAt) {
	this.createdAt = createdAt;
}


}
