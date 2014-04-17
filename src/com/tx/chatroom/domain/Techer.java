package com.tx.chatroom.domain;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import net.sf.json.JSONObject;

public class Techer extends User implements Serializable{
	private ArrayList<User> userList=new ArrayList<User>();
	public boolean hasUser(User user){
		return userList.contains(user);
	}
	public void removerUser(User user){
		if(userList.contains(user)){
			userList.remove(user);
			JSONObject json=new JSONObject();
			json.put("messageType", 3);
			json.put("status", 1);
			json.put("userID", user.getUserID());
			json.put("userName", user.getUserName());
			sendMessage(this, json);
		}
	}
	public void addUser(User user){
		if(!userList.contains(user)){
			userList.add(user);
			JSONObject json=new JSONObject();
			json.put("messageType", 2);
			json.put("status", 1);
			json.put("userID", user.getUserID());
			json.put("userName", user.getUserName());
			json.put("nickName", user.getNickName());
			sendMessage(this, json);
		}
		
	}

	public void exit(){
		JSONObject json=new JSONObject();
		json.put("messageType", 3);
		json.put("status", 1);
		json.put("userID", this.getUserID());
		json.put("userName", this.getUserName());
		for(User user:userList){
			sendMessage(user, json);
		}
	}
	public void sayToAll(String message){
		for(User user:userList){
			sayTo(user,message);
		}
	}
	private int groupId;
	private String techerTitle;
	private String techerIcon;
	private String groupName;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getTecherTitle() {
		return techerTitle;
	}
	public void setTecherTitle(String techerTitle) {
		this.techerTitle = techerTitle;
	}
	public String getTecherIcon() {
		return techerIcon;
	}
	public void setTecherIcon(String techerIcon) {
		this.techerIcon = techerIcon;
	}
	
	
	
//	private String passWord;
//	public String getPassWord() {
//		return passWord;
//	}
//	public void setPassWord(String passWord) {
//		this.passWord = passWord;
//	}
//	public String getNickName() {
//		return nickName;
//	}
//	public void setNickName(String nickName) {
//		this.nickName = nickName;
//	}
//	private String nickName;

}
