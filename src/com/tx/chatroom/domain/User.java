package com.tx.chatroom.domain;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;

import com.dmframe.dao.BaseDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class User implements Serializable{

	private int userID;
	private String userName;
	private String passWord;
	private String nickName;
	
	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getPassWord() {
		return passWord;
	}


	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}


	public int getUserID() {
		return userID;
	}


	public void setUserID(int userID) {
		this.userID = userID;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public Techer techer;
	
	public AsyncContext ac;
	
	public Vector<String> messageCache=new Vector<String>();
	
//	public ChatRoom room;//weak;
	
	public void sendMessage(User toUser,JSONObject json){
		if(toUser!=null){
			if(toUser.ac!=null){
				PrintWriter out;
				try {
					ServletResponse response=toUser.ac.getResponse();
					toUser.ac.getResponse().setContentType("text/html; charset=UTF-8");
					out = toUser.ac.getResponse().getWriter();
					out.println(json.toString());
					toUser.ac.complete();
					int messageType=json.getInt("messageType");
					if(messageType==1){
						System.out.println("write log");
						LogTask task=new LogTask(this.getUserID(),toUser.getUserID(),json.getString("message"),json.getString("sendTime"));
						BaseDao.postSqlTast(task);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				toUser.messageCache.add(json.toString());
			}
		}
	}
	
	public void sayTo(User toUser,String message){
		JSONObject json=new JSONObject();
		json.put("messageType", 1);
		json.put("status", 1);
		json.put("message", message);
		json.put("userID", this.getUserID());
		json.put("userName", this.getUserName());
		json.put("nickName", this.getNickName());
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sendTime=dateFormat.format(new Date());
		json.put("sendTime", sendTime);
		sendMessage(toUser,json);
	}
	
}
