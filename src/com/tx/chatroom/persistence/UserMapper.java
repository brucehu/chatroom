package com.tx.chatroom.persistence;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.tx.chatroom.domain.User;

public interface UserMapper {

	public User getUserByID(int id);
	public ArrayList<User> getUserList();
	public ArrayList<User> getUserListPage(@Param(value="begin")int begin,@Param(value="pageNum")int pageNum);
	public void addUser(User user);
	public void updateUser(User user);
	public void deleteUser(@Param(value="userId") String userId);
	public int getCount();
	
	public void updateAdminPwd(@Param(value="userName") String userName,@Param(value="passWord") String passWord);
	
	public User getUserByNameAndPwd(@Param(value="userName") String userName,@Param(value="passWord") String passWord);
	
	public User getAdminByNameAndPwd(@Param(value="userName") String userName,@Param(value="passWord") String passWord);
}
