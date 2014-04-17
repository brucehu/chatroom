package com.tx.chatroom.persistence;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.tx.chatroom.domain.QQ;

public interface QQMapper {

	public int getCount();
	public void addQQ(QQ qq);
	public ArrayList<QQ> getQQList();
	public ArrayList<QQ> getQQListPage(@Param(value="begin")int begin,@Param(value="pageNum")int pageNum);
}
