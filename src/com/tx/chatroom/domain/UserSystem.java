package com.tx.chatroom.domain;

import java.util.HashMap;

public class UserSystem {

	public static HashMap<Integer,User> userMap=new HashMap<Integer,User>();
	
	
	public static User getUser(int userID){
		System.out.println("getUser "+ userID+" from map,map.size="+userMap.size());
		return userMap.get(userID);
	}
	
//	public static boolean login(String userID){
//		if(!userMap.containsKey(userID)){
//			User user=new User();
//			user.userID=userID;
//			userMap.put(userID, user);
//			return true;
//		}
//		return false;
//	}
	public static void newUser(int userID,User user){
		if(!userMap.containsKey(userID)){
//			User user=new User();
//			user.setUserID(userID);
//			user.setUserName(usreName);
			System.out.println("新增用户:id="+user.getUserID()+","+user.getNickName());
			userMap.put(userID, user);
		}
	}
	
	public static void techer_connect(int techerID){
		if(!userMap.containsKey(techerID)){
			Techer user=new Techer();
			user.setUserID(techerID);
			userMap.put(techerID, user);
		}
	}
//	
//	public static void logout(User user){	
//		if(userMap.containsKey(user.userID)){
//			userMap.remove(user.userID);
//		}
//		if(user.ac!=null){
//			user.ac.complete();
//		}
//		System.out.println(user.userID+" logout from map,map.size="+userMap.size());
//	}
//	
//	public static void changeTecher(User user,User newTecher){
//		user.room.outRoom(user);
//		if(user.ac!=null){
//			user.ac.complete();
//		}
//		
//		newTecher.room.inRoom(user);
//	}
}
