package com.pmq.subscribe.model;

import com.pmq.edition.model.Edition;
import com.pmq.like.model.Like;

public class InterestView {
	private Edition edition;
	private Subscribe subscribe;
	
	public Edition getEdition() {
		return edition;
	}
	public void setEdition(Edition edition) {
		this.edition = edition;
	}
	
	public Subscribe getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(Subscribe subscribe) {
		this.subscribe = subscribe;
	}
	
}
