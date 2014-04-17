package com.tx.chatroom.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class UserController {

	@UrlPattern(urlPath="/admin/user_add.do")
	public String userAdd(HttpServletRequest req, HttpServletResponse resp){
		req.setAttribute("buttonValue", "添加");
		req.setAttribute("action", "user_add_db.do");
		return "admin/user_add.jsp";
	}
	@UrlPattern(urlPath="/admin/user_add_db.do")
	public void userAddDB(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String userName=req.getParameter("userName");
		String passWord=req.getParameter("passWord");
		String nickName=req.getParameter("nickName");
		
		if(nickName!=null){
			nickName=new String(nickName.getBytes("ISO8859-1"),"UTF-8");
		}
			
		User user=new User();
		user.setUserName(userName);
		user.setPassWord(passWord);
		user.setNickName(nickName);

		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			UserMapper mapper=session.getMapper(UserMapper.class);
			mapper.addUser(user);
			session.commit();
		}finally{
			session.close();
		}
		try {
			req.getRequestDispatcher("/admin/user_list.do").forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@UrlPattern(urlPath="/admin/user_update.do")
	public String userUpdate(HttpServletRequest req, HttpServletResponse resp){
		String id=req.getParameter("id");
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			UserMapper mapper=session.getMapper(UserMapper.class);
			User user=mapper.getUserByID(Integer.parseInt(id));
			req.setAttribute("user", user);
		}finally{
			session.close();
		}
		req.setAttribute("buttonValue", "修改");
		req.setAttribute("action", "user_update_db.do");
		
		return "admin/user_add.jsp";
	}
	
	@UrlPattern(urlPath="/admin/user_update_db.do")
	public void userUpdateDB(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String id=req.getParameter("id");
		String userName=req.getParameter("userName");
		String passWord=req.getParameter("passWord");
		String nickName=req.getParameter("nickName");
		
		if(nickName!=null){
			nickName=new String(nickName.getBytes("ISO8859-1"),"UTF-8");
		}
		
		User user=new User();
		user.setUserID(Integer.parseInt(id));
		user.setUserName(userName);
		user.setPassWord(passWord);
		user.setNickName(nickName);
		
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			UserMapper mapper=session.getMapper(UserMapper.class);
			mapper.updateUser(user);
			session.commit();
		}finally{
			session.close();
		}
		
		try {
			req.getRequestDispatcher("/admin/user_list.do").forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@UrlPattern(urlPath="/admin/user_list.do")
	public String userList(HttpServletRequest req, HttpServletResponse resp){
		String curPage=req.getParameter("curPage");
		if(curPage==null||curPage.length()<=0){
			curPage="1";
		}
		int icurPage=Integer.parseInt(curPage);
		int pageSize=20;
		
		
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			UserMapper mapper=session.getMapper(UserMapper.class);
			int total=mapper.getCount();
			int pageNum=total/pageSize==0?total/pageSize:total/pageSize+1;
			if(pageNum==0){
				pageNum=1;
			}
			if(icurPage<1){
				icurPage=1;
			}
			if(icurPage>pageNum){
				icurPage=(int)pageNum;
			}
			int begin=(icurPage-1)*pageSize;
			ArrayList<User> userList=mapper.getUserListPage(begin, pageSize);
			req.setAttribute("userList", userList);
			req.setAttribute("icurPage", icurPage);
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("total", total);
			req.setAttribute("iprevPage",icurPage-1);
			req.setAttribute("inextPage",icurPage+1);
		}finally{
			session.close();
		}
		return "admin/user_list.jsp";
	}
	
	@UrlPattern(urlPath="/admin/user_delete.do")
	public void userDel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		String id=req.getParameter("id");
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			UserMapper mapper=session.getMapper(UserMapper.class);
			mapper.deleteUser(id);
			session.commit();
		}finally{
			session.close();
		}
//		out.println("delete id="+id);
		try {
			req.getRequestDispatcher("/admin/user_list.do").forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
