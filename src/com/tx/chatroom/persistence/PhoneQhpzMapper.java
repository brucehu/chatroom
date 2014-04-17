package com.tx.chatroom.persistence;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.tx.chatroom.domain.PhoneQhpz;

public interface PhoneQhpzMapper {

	public void addPhoneQhpzMapper(PhoneQhpz phoneQhpz);
	
	public ArrayList<PhoneQhpz> getPhoneList();
}
