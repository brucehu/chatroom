package com.tx.chatroom.web.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dmframe.annotation.Controller;
import com.dmframe.annotation.UrlPattern;
import com.dmframe.dao.BaseDao;
import com.dmframe.dao.CacheBase;
import com.tx.chatroom.domain.Techer;
import com.tx.chatroom.domain.TecherGroup;
import com.tx.chatroom.domain.User;
import com.tx.chatroom.domain.UserSystem;
import com.tx.chatroom.persistence.TecherMapper;
import com.tx.chatroom.persistence.UserMapper;

@Controller
public class TecherServer {
	@UrlPattern(urlPath="/reloadTecherListCache.do")
	public void reloadTecherListCache(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
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
			out.println(array.toString());
		}finally{
			session.close();
		}
	}
	@UrlPattern(urlPath="/getTecherListJSON.do")
	public void getTecherListJSON(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		Cache cache=CacheBase.cacheManager.getCache("newsCache");
		Element element=cache.get("techerListJson");
		if(element!=null){
			String json=(String)element.getObjectValue();
			out.println(json);
			return;
		}
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
			element=new Element("techerListJson",array.toString());
			cache.put(element);
			out.println(array.toString());
		}finally{
			session.close();
		}
	}
//	static int userID=100;
	/*
	 * 用户登录，验证用户名密码
	 */
	@UrlPattern(urlPath="/techer_login.do")
	public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		String userName=req.getParameter("userName");
		String passWord=req.getParameter("passWord");
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		Techer techer=null;
		try{
			TecherMapper mapper=session.getMapper(TecherMapper.class);
			techer=mapper.getTecherByNameAndPwd(userName, passWord);
		}finally{
			session.close();
		}
		JSONObject json=new JSONObject();
		if(techer==null){
			json.put("status", 0);
			json.put("message", "用户名密码错误，登陆失败");
		}else{
			json.put("status", 1);
			json.put("techerID", techer.getUserID());
			json.put("nickName", techer.getNickName());
			UserSystem.newUser(techer.getUserID(), techer);
		}
		
		out.println(json.toString());
	}
	
	@UrlPattern(urlPath="/techer_logout.do")
	public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		String userID=req.getParameter("userID");
		String flag=req.getParameter("flag");
		System.out.println("logout "+userID+",flag="+flag);
		
		JSONObject json=new JSONObject();
		json.put("status", 1);
		if(userID==null||userID.length()<=0){
			out.println(json.toString());
			return;
		}
		int iuserID=Integer.parseInt(userID);
		
		
		Techer techer=(Techer)UserSystem.getUser(iuserID);
		if(techer!=null){
			UserSystem.userMap.remove(iuserID);
			techer.exit();
			if(techer.ac!=null){
				techer.ac.complete();
				techer.ac=null;
			}
		}
		

		out.println(json.toString());
		
	}
	
	@UrlPattern(urlPath="/techer_send.do")
	public void sendMassage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out= resp.getWriter();
		
		String userID=req.getParameter("userID");
		String techerID=req.getParameter("techerID");
		String message=req.getParameter("message");
//		if(message!=null){
//			message=new String(message.getBytes("ISO8859-1"),"UTF-8");
//		}
		
		int itecherID=Integer.parseInt(techerID);
		Techer techer=(Techer)UserSystem.getUser(itecherID);
		if("ALL".equals(userID)){
			techer.sayToAll(message);
		}else{
			int iuserID=Integer.parseInt(userID);
			User user=UserSystem.getUser(iuserID);
			if(techer.hasUser(user)){
				techer.sayTo(user, message);
			}
		}
		
		
		JSONObject json=new JSONObject();
		json.put("status", 1);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sendTime=dateFormat.format(new Date());
		json.put("sendTime", sendTime);
		out.println(json.toString());
		
	}
	
	@UrlPattern(urlPath="/techer_poll.do")
	public void poll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		final String techerID=req.getParameter("userID");

		int itecherID=Integer.parseInt(techerID);
		Techer user=(Techer)UserSystem.getUser(itecherID);
		if(user==null){
			JSONObject json=new JSONObject();
			json.put("messageType", 1);
       		json.put("status", 0);
       		json.put("message", "user is null");
			out.println(json.toString());
			return;
		}
		AsyncContext ac = req.startAsync(); 
		user.ac=ac;
		ac.setTimeout(300*1000); 
        ac.addListener(new AsyncListener() { 
            public void onComplete(AsyncEvent event) throws IOException { 
//               System.out.println("onComplete"+techerID);
            } 

            public void onTimeout(AsyncEvent event) throws IOException { 
//            	System.out.println("onTimeout"+techerID);
            	//user.ac=null;
            	event.getAsyncContext().getResponse().setContentType("text/html; charset=UTF-8");
           		PrintWriter out = event.getAsyncContext().getResponse().getWriter();
           		JSONObject json=new JSONObject();
           		json.put("messageType", 1);
           		json.put("status", 2);
           		json.put("message", "timeout");
				out.println(json.toString());
				event.getAsyncContext().complete();
            } 

            public void onError(AsyncEvent event) throws IOException { 
            	System.out.println("onError"+techerID);
            	//user.ac=null;
            } 

            public void onStartAsync(AsyncEvent event) throws IOException { 
            	System.out.println("onStartAsync"+techerID);
            } 
        }); 
	}

}
