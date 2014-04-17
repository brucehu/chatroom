package com.tx.chatroom.domain;

import org.apache.ibatis.session.SqlSession;

import com.dmframe.dao.BaseDao;
import com.tx.chatroom.persistence.LogMapper;
import com.tx.chatroom.persistence.TecherMapper;

public class LogTask implements Runnable{

	private int userID, toUserID;
	private String content, logTime;
	public LogTask(int userID,int toUserID,String content,String logTime){
//		if(userID>1000){
//			this.userID=userID%1000;
//		}
//		if(toUserID>1000){
//			this.toUserID=toUserID%1000;
//		}
		this.userID=userID;
		this.toUserID=toUserID;
		this.content=content;
		this.logTime=logTime;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			LogMapper mapper=session.getMapper(LogMapper.class);
			mapper.addLog(userID, toUserID, content, logTime);
			session.commit();
		}finally{
			session.close();
		}
//		System.out.println(mapper);
	}

}
