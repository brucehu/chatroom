package com.tx.chatroom.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class TecherGroup implements Serializable{

	private int groupId;
	private String groupName;
	private ArrayList<Techer> techerList;
	
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public ArrayList<Techer> getTecherList() {
		return techerList;
	}
	public void setTecherList(ArrayList<Techer> techerList) {
		this.techerList = techerList;
	}
	
}
