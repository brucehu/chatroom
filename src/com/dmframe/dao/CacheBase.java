package com.dmframe.dao;

import net.sf.ehcache.CacheManager;

public class CacheBase {

	public static CacheManager cacheManager =null;
	static{
		java.net.URL url=CacheBase.class.getResource("/ehcache.xml");
		System.out.println(url.toString());
		cacheManager = CacheManager.newInstance(url);
	}
}
