package com.tx.chatroom.persistence;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.tx.chatroom.domain.Techer;
import com.tx.chatroom.domain.TecherGroup;

public interface TecherMapper {
	
	public void addGroup(@Param(value="group_name") String groupName);
	public void updateGroup(TecherGroup group);
	public void deleteGroup(@Param(value="id") String groupId);
	public ArrayList<TecherGroup> getTecherGroupList();
	public TecherGroup getTecherGroupById(@Param(value="id") String id);

	
	public void addTecher(Techer techer);
	public void updateTecher(Techer techer);
	public void deleteTecher(@Param(value="userId") String userId);
	public ArrayList<Techer> getTecherList();
	public Techer getTecherById(@Param(value="userId") String userId);
	
	public ArrayList<Techer> getTecherListByGroup(int group_id);
	public Techer getTecherByNameAndPwd(@Param(value="userName") String userName,@Param(value="passWord") String passWord);
}
