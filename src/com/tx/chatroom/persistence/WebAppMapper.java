package com.tx.chatroom.persistence;

import org.apache.ibatis.annotations.Param;

import com.tx.chatroom.domain.WebApp;

public interface WebAppMapper {

	public WebApp getWebAppByAppid(String appid);
	public void updateWebApp(WebApp webApp);
}
