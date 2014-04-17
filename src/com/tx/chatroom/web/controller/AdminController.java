package com.tx.chatroom.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.apache.ibatis.session.SqlSession;

import com.dmframe.annotation.Controller;
import com.dmframe.annotation.UrlPattern;
import com.dmframe.dao.BaseDao;
import com.tx.chatroom.domain.Techer;
import com.tx.chatroom.domain.TecherGroup;
import com.tx.chatroom.domain.User;
import com.tx.chatroom.persistence.TecherMapper;
import com.tx.chatroom.persistence.UserMapper;

@Controller
public class AdminController {
	
	@UrlPattern(urlPath="/admin/changePwd.do")
	public String changPwd(HttpServletRequest req, HttpServletResponse resp){
		HttpSession session=req.getSession();
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "admin/login.jsp";
		}
		return "admin/change_pwd.jsp";
	}
	@UrlPattern(urlPath="/admin/changePwdDB.do")
	public String changePwdDB(HttpServletRequest req, HttpServletResponse resp){
		HttpSession session=req.getSession();
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "admin/login.jsp";
		}
		String oldPwd=req.getParameter("oldPwd");
		String newPwd=req.getParameter("newPwd");
		if(user.getPassWord().equals(oldPwd)){
			SqlSession sqlSession=BaseDao.sqlSessionFactory.openSession();
			try{
				UserMapper mapper=sqlSession.getMapper(UserMapper.class);
				mapper.updateAdminPwd(user.getUserName(), newPwd);
				sqlSession.commit();
			}finally{
				sqlSession.close();
			}
			req.setAttribute("message", "修改成功，请<a href='logout.do'>注销重新登录</a>");
		}else{
			req.setAttribute("message", "旧密码不对！修改失败！");
		}
		return "admin/change_pwd.jsp";
	}

	@UrlPattern(urlPath="/admin/logout.do")
	public String logout(HttpServletRequest req, HttpServletResponse resp){
		HttpSession session=req.getSession();
		session.invalidate();
		return "admin/login.jsp";
	}
	
	@UrlPattern(urlPath="/admin/login.do")
	public String login(HttpServletRequest req, HttpServletResponse resp){
		return "admin/login.jsp";
	}
	@UrlPattern(urlPath="/admin/login_check.do")
	public String loginCheck(HttpServletRequest req, HttpServletResponse resp){
		HttpSession session=req.getSession();
		User user=(User) session.getAttribute("user");
		if(user!=null){
			return "admin/index.jsp";
		}
		String username=req.getParameter("name");
		String password=req.getParameter("pass");
		SqlSession sqlSession=BaseDao.sqlSessionFactory.openSession();
		User admin=null;
		try{
			UserMapper mapper=sqlSession.getMapper(UserMapper.class);
			admin=mapper.getAdminByNameAndPwd(username, password);
		}finally{
			sqlSession.close();
		}
		
		if(admin!=null){
			session.setAttribute("user", admin);
			return "admin/index.jsp";
		}else{
			return "admin/login.jsp";
		}
	}
	@UrlPattern(urlPath="/admin/index.do")
	public String index(HttpServletRequest req, HttpServletResponse resp){
		HttpSession session=req.getSession();
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "admin/login.jsp";
		}
		return "admin/index.jsp";
	}
	@UrlPattern(urlPath="/admin/header.do")
	public String header(HttpServletRequest req, HttpServletResponse resp){
		HttpSession session=req.getSession();
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "admin/login.jsp";
		}
		req.setAttribute("user", user);
		return "admin/header.jsp";
	}
	@UrlPattern(urlPath="/admin/middel.do")
	public String middel(HttpServletRequest req, HttpServletResponse resp){
		HttpSession session=req.getSession();
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "admin/login.jsp";
		}
		return "admin/middel.jsp";
	}
	@UrlPattern(urlPath="/admin/menu.do")
	public String menu(HttpServletRequest req, HttpServletResponse resp){
		HttpSession session=req.getSession();
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "admin/login.jsp";
		}
		return "admin/menu.jsp";
	}

}
