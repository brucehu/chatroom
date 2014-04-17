package com.tx.chatroom.domain;

import java.util.ArrayList;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.session.SqlSession;

import com.dmframe.dao.BaseDao;
import com.dmframe.dao.CacheBase;
import com.tx.chatroom.persistence.TecherMapper;

public class Util {

	public static void refreshTecherListCache(){
		Cache cache=CacheBase.cacheManager.getCache("newsCache");
		cache.remove("techerListJson");
		System.out.println("get techerListJson from db");
		JSONArray array=new JSONArray();
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			TecherMapper mapper=session.getMapper(TecherMapper.class);
			ArrayList<TecherGroup> groupList=mapper.getTecherGroupList();
			
			for(TecherGroup group:groupList){
				JSONObject json=new JSONObject();
				json.put("groupName", group.getGroupName());
				ArrayList<Techer> techerList=mapper.getTecherListByGroup(group.getGroupId());
				JSONArray techerListArray=new JSONArray();
				for(Techer techer:techerList){
					JSONObject jsonTecher=new JSONObject();
					jsonTecher.put("techerID", techer.getUserID());
					jsonTecher.put("techerName", techer.getNickName());
					jsonTecher.put("icon", techer.getTecherIcon());
					jsonTecher.put("title", techer.getTecherTitle());
					techerListArray.add(jsonTecher);
				}
				json.put("techerArray", techerListArray);
				array.add(json);
			}
			Element element=new Element("techerListJson",array.toString());
			cache.put(element);
		}finally{
			session.close();
		}
	}
//	public static boolean isUTF8=false;
}
