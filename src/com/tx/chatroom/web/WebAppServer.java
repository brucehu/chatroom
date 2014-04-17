package com.tx.chatroom.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;

import com.dmframe.annotation.Controller;
import com.dmframe.annotation.UrlPattern;
import com.dmframe.dao.BaseDao;
import com.tx.chatroom.domain.PhoneQhpz;
import com.tx.chatroom.domain.User;
import com.tx.chatroom.domain.WebApp;
import com.tx.chatroom.persistence.PhoneQhpzMapper;
import com.tx.chatroom.persistence.WebAppMapper;

@Controller
public class WebAppServer {

	@UrlPattern(urlPath="/admin/webAppIndex.do")
	public String webAppIndex(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String appid=req.getParameter("appid");
		req.setAttribute("appid", appid);
		return "webApp/index.jsp";
	}
	@UrlPattern(urlPath="/admin/webAppmenu.do")
	public String menu(HttpServletRequest req, HttpServletResponse resp){
		return "webApp/menu.jsp";
	}
	@UrlPattern(urlPath="/webApp.do")
	public String webApp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String appid=req.getParameter("appid");
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			WebAppMapper mapper=session.getMapper(WebAppMapper.class);
			WebApp webApp=mapper.getWebAppByAppid(appid);
			req.setAttribute("content", webApp.getContent());
		}finally{
			session.close();
		}

		
		return "webApp.jsp";
	}
	@UrlPattern(urlPath="/editWebApp.do")
	public String editWebApp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String appid=req.getParameter("appid");
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			WebAppMapper mapper=session.getMapper(WebAppMapper.class);
			WebApp webApp=mapper.getWebAppByAppid(appid);
			req.setAttribute("webApp",webApp);
		}finally{
			session.close();
		}
		
		return "editWebApp.jsp";
	}
	@UrlPattern(urlPath="/updateWebApp.do")
	public String updateWebApp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String appid=req.getParameter("appid");
		String content=req.getParameter("content");
		System.out.println(content);
//		String tempcontent=new String(content.getBytes("ISO8859-1"),"UTF-8");
//		System.out.println(tempcontent);
		if(content!=null){
//			if(!com.dmframe.util.Util.isUTF8){
				content=new String(content.getBytes("ISO8859-1"),"UTF-8");
//			}
		}
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			WebAppMapper mapper=session.getMapper(WebAppMapper.class);
			WebApp webApp=new WebApp();
			webApp.setAppid(appid);
			webApp.setContent(content);
			mapper.updateWebApp(webApp);
			session.commit();
			req.setAttribute("webApp",webApp);
			req.setAttribute("message","修改成功！");
		}finally{
			session.close();
		}
		
		return "editWebApp.jsp";
	}
}
