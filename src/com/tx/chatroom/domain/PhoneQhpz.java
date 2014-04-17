package com.tx.chatroom.domain;

import java.io.Serializable;

public class PhoneQhpz implements Serializable{

	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQhpz() {
		return qhpz;
	}
	public void setQhpz(String qhpz) {
		this.qhpz = qhpz;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	private String phone;
	private String qhpz;
	private String ctime;
}
