package com.tx.chatroom.persistence;

import org.apache.ibatis.annotations.Param;

public interface LogMapper {

	public void addLog(
			@Param(value="userID") int userID,
			@Param(value="toUserID") int toUserID,
			@Param(value="content") String content,
			@Param(value="logTime") String logTime);
}
