package com.tx.chatroom.domain;

import java.util.ArrayList;
import java.util.HashMap;

import net.sf.json.JSONObject;

public class ChatRoom {

	int roomID;
	HashMap<String,User> userMap=new HashMap<String,User>();
	
	public ChatRoom(int roomID){
		this.roomID=roomID;
	}
	
	public User getUser(String userID){
		return userMap.get(userID);
	}
	
//	public void inRoom(User user){
//		if(user.getUserID()!=roomID)){//客户
//			JSONObject json=new JSONObject();
//			json.put("status", 0);
//			json.put("messageType", 2);
//			json.put("userID", user.getUserID());
//			json.put("toUserID", roomID);
//			user.sendMessage(UserSystem.getUser(roomID), json);
//		}
//		userMap.put(user.userID, user);
//		user.room=this;
//	}
	
//	public void inRoom(String userID){
//		if(!userMap.containsKey(userID)){
//			User user=new User();
//			user.userID=userID;
//			userMap.put(user.userID, user);
//		}
//	}
	
//	public void outRoom(User user){
//		if(user.userID.equals(roomID)){//客户
//			Techer techer=(Techer)user;
//			JSONObject json=new JSONObject();
//			json.put("status", 0);
//			json.put("messageType", 3);
//			json.put("userID", user.userID);
//			json.put("toUserID", roomID);
//			techer.sendtoAll(json);
//		}else{
//			JSONObject json=new JSONObject();
//			json.put("status", 0);
//			json.put("messageType", 3);
//			json.put("userID", user.userID);
//			json.put("toUserID", roomID);
//			user.sendMessage(UserSystem.getUser(roomID), json);
//		}
//		userMap.remove(user.userID);
//		user.room=null;
//	}
//	public void outRoom(String userID){
//		if(userMap.containsKey(userID)){
//			userMap.remove(userID);
//		}
//	}
	
}
