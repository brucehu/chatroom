package com.tx.chatroom.web.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.json.JSONObject;

import com.dmframe.annotation.Controller;
import com.dmframe.annotation.UrlPattern;
import com.dmframe.dao.BaseDao;
import com.dmframe.dao.CacheBase;
import com.tx.chatroom.domain.Techer;
import com.tx.chatroom.domain.TecherGroup;
import com.tx.chatroom.domain.User;
import com.tx.chatroom.domain.UserSystem;
import com.tx.chatroom.persistence.LogMapper;
import com.tx.chatroom.persistence.TecherMapper;
import com.tx.chatroom.persistence.UserMapper;

@Controller
public class UserServer {

	/*
	 * 用户登录，验证用户名密码
	 */
	@UrlPattern(urlPath="/login.do")
	public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		String userName=req.getParameter("userName");
		String passWord=req.getParameter("passWord");
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		User user=null;
		try{
			UserMapper mapper=session.getMapper(UserMapper.class);
			user=mapper.getUserByNameAndPwd(userName, passWord);
		}finally{
			session.close();
		}
		JSONObject json=new JSONObject();
		if(user==null){
			json.put("status", 0);
			json.put("message", "用户名密码错误，登陆失败");
		}else{
			json.put("status", 1);
			json.put("userID", user.getUserID());
			UserSystem.newUser(user.getUserID(), user);
		}

		out.println(json.toString());
	}
	
	/*
	 * 开始长连接
	 */
	@UrlPattern(urlPath="/connect.do")
	public void connect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		String userID=req.getParameter("userID");
		int iuserID=Integer.parseInt(userID);
		User user=UserSystem.getUser(iuserID);
		if(user==null){
			JSONObject json=new JSONObject();
       		json.put("status", 0);
       		json.put("message", "user is null");
			out.println(json.toString());
			return;
		}
		JSONObject json=new JSONObject();
		json.put("status", 1);
		out.println(json.toString());
		
	}
	@UrlPattern(urlPath="/disconnect.do")
	public void disconnect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		String userID=req.getParameter("userID");
		int iuserID=Integer.parseInt(userID);
		User user=UserSystem.getUser(iuserID);
		if(user==null){
			JSONObject json=new JSONObject();
       		json.put("status", 0);
       		json.put("message", "user is null");
			out.println(json.toString());
			return;
		}
		Techer techer=user.techer;
		if(techer!=null){
			techer.removerUser(user);	
		}
		if(user.ac!=null){
			user.ac.complete();
			user.ac=null;
		}
		
		JSONObject json=new JSONObject();
		json.put("status", 1);
		out.println(json.toString());
		
	}
	
	
	/*
	 * 用户退出，清除用户对象，退出聊天室
	 */
	@UrlPattern(urlPath="/logout.do")
	public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		String userID=req.getParameter("userID");
		
		System.out.println("logout "+userID);
		
		JSONObject json=new JSONObject();
		json.put("status", 1);
		if(userID==null||userID.length()<=0){
			json.put("status", 1);
			out.println(json.toString());
			return;
		}
		
		int iuserID=Integer.parseInt(userID);
		User user=UserSystem.getUser(iuserID);
		if(user!=null){
			UserSystem.userMap.remove(iuserID);
			Techer techer=user.techer;
			if(techer!=null){
				techer.removerUser(user);	
			}
			if(user.ac!=null){
				user.ac.complete();
				user.ac=null;
			}
		}
		
		out.println(json.toString());
		
	}
	
	/*
	 * 连接老师（聊天室），切换老师（更换聊天室）
	 */
	@UrlPattern(urlPath="/chatToTecher.do")
	public void chatToTecher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out= resp.getWriter();;
		String userID=req.getParameter("userID");
		String techerID=req.getParameter("techerID");
		
		int iuserID=Integer.parseInt(userID);
		int itecherID=Integer.parseInt(techerID);
		User user=UserSystem.getUser(iuserID);
		JSONObject json=new JSONObject();
		if(user==null){
			json.put("status", 0);
			json.put("message", "user is null");
			out.println(json.toString());
			return;
		}
		Techer techer=(Techer)UserSystem.getUser(itecherID);
		Techer oldTecher=user.techer;
		if(techer==null){
			json.put("status", 0);
			json.put("message", "不在线");
			if(oldTecher!=null){
				oldTecher.removerUser(user);
			}
		}else{
			if(oldTecher!=null&&oldTecher!=techer){
				oldTecher.removerUser(user);
			}
			techer.addUser(user);
			user.techer=techer;
			json.put("status", 1);
		}
		
		
		out.println(json.toString());
	}
	
	@UrlPattern(urlPath="/sendChat.do")
	public void sendMassage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out= resp.getWriter();
		
		String userID=req.getParameter("userID");
		String techerID=req.getParameter("techerID");
		String message=req.getParameter("message");
//		if(message!=null){
//			message=new String(message.getBytes("ISO8859-1"),"UTF-8");
//		}
		
		int iuserID=Integer.parseInt(userID);
		int itecherID=Integer.parseInt(techerID);
		User user=UserSystem.getUser(iuserID);
		Techer techer=(Techer)UserSystem.getUser(itecherID);
		user.sayTo(techer, message);
		
		JSONObject json=new JSONObject();
		json.put("status", 1);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sendTime=dateFormat.format(new Date());
		json.put("sendTime", sendTime);
		out.println(json.toString());
		
	}
	
	@UrlPattern(urlPath="/poll.do")
	public void poll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out= resp.getWriter();
		
		final String userID=req.getParameter("userID");
		int iuserID=Integer.parseInt(userID);
		User user=UserSystem.getUser(iuserID);
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
//               System.out.println("onComplete"+userID);
            } 

            public void onTimeout(AsyncEvent event) throws IOException { 
//            	System.out.println("onTimeout"+userID);
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
            	System.out.println("onError"+userID);
            	//user.ac=null;
            } 

            public void onStartAsync(AsyncEvent event) throws IOException { 
            	System.out.println("onStartAsync"+userID);
            } 
        }); 
	}
	
	
	
	@UrlPattern(urlPath="/test.do")
	public void test(HttpServletRequest req, HttpServletResponse resp){
		
	}
}
