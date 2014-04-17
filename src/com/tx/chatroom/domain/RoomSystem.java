package com.tx.chatroom.domain;

import java.util.HashMap;

public class RoomSystem {

	public static HashMap<String,ChatRoom> roomMap=new HashMap<String,ChatRoom>();
	
//	static{
//		for(int i=0;i<5;i++){
//			System.out.println("init room"+i);
//			getRoom("room"+i);
//		}
//	}
	
	public static ChatRoom getRoom(String roomID){
//		if(!roomMap.containsKey(roomID)){
//			ChatRoom room=new ChatRoom(roomID);
//			roomMap.put(roomID, room);
//		}
		return roomMap.get(roomID);
	}

	public static void removeRoom(String roomID){
		roomMap.remove(roomID);
	}
}
