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
import com.tx.chatroom.domain.Util;
import com.tx.chatroom.persistence.TecherMapper;

@Controller
public class TecherController {

	@UrlPattern(urlPath="/admin/techer_add.do")
	public String techerAdd(HttpServletRequest req, HttpServletResponse resp){
		req.setAttribute("buttonValue", "添加");
		req.setAttribute("action", "techer_add_db.do");
		
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			TecherMapper mapper=session.getMapper(TecherMapper.class);
			ArrayList<TecherGroup> groupList=mapper.getTecherGroupList();
			req.setAttribute("groupList", groupList);
		}finally{
			session.close();
		}
		return "admin/techer_add.jsp";
	}
	@UrlPattern(urlPath="/admin/techer_add_db.do")
	public void techerAddDB(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String groupId=req.getParameter("groupId");
		String userName=req.getParameter("userName");
		String passWord=req.getParameter("passWord");
		String nickName=req.getParameter("nickName");
		String techerTitle=req.getParameter("techerTitle");
		String techerIcon=req.getParameter("techerIcon");
		
		if(nickName!=null){
			nickName=new String(nickName.getBytes("ISO8859-1"),"UTF-8");
		}
		if(techerTitle!=null){
			techerTitle=new String(techerTitle.getBytes("ISO8859-1"),"UTF-8");
		}
		
		
		Techer techer=new Techer();
		techer.setGroupId(Integer.parseInt(groupId));
		techer.setUserName(userName);
		techer.setPassWord(passWord);
		techer.setNickName(nickName);
		techer.setTecherTitle(techerTitle);
		techer.setTecherIcon(techerIcon);
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			TecherMapper mapper=session.getMapper(TecherMapper.class);
			mapper.addTecher(techer);
			session.commit();
		}finally{
			session.close();
		}
		Util.refreshTecherListCache();
		try {
			req.getRequestDispatcher("/admin/techer_list.do").forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@UrlPattern(urlPath="/admin/techer_update.do")
	public String techerUpdate(HttpServletRequest req, HttpServletResponse resp){
		String id=req.getParameter("id");
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			TecherMapper mapper=session.getMapper(TecherMapper.class);
			Techer techer=mapper.getTecherById(id);
			req.setAttribute("techer", techer);
			ArrayList<TecherGroup> groupList=mapper.getTecherGroupList();
			req.setAttribute("groupList", groupList);
		}finally{
			session.close();
		}
		req.setAttribute("buttonValue", "修改");
		req.setAttribute("action", "techer_update_db.do");
		
		return "admin/techer_add.jsp";
	}
	
	@UrlPattern(urlPath="/admin/techer_update_db.do")
	public void techerUpdateDB(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String id=req.getParameter("id");
		String groupId=req.getParameter("groupId");
		String userName=req.getParameter("userName");
		String passWord=req.getParameter("passWord");
		String nickName=req.getParameter("nickName");
		String techerTitle=req.getParameter("techerTitle");
		String techerIcon=req.getParameter("techerIcon");
		
		if(nickName!=null){
			nickName=new String(nickName.getBytes("ISO8859-1"),"UTF-8");
		}
		if(techerTitle!=null){
			techerTitle=new String(techerTitle.getBytes("ISO8859-1"),"UTF-8");
		}
		
		
		Techer techer=new Techer();
		techer.setUserID(Integer.parseInt(id));
		techer.setGroupId(Integer.parseInt(groupId));
		techer.setUserName(userName);
		techer.setPassWord(passWord);
		techer.setNickName(nickName);
		techer.setTecherTitle(techerTitle);
		techer.setTecherIcon(techerIcon);
		
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			TecherMapper mapper=session.getMapper(TecherMapper.class);
			mapper.updateTecher(techer);
			session.commit();
		}finally{
			session.close();
		}
		Util.refreshTecherListCache();
		try {
			req.getRequestDispatcher("/admin/techer_list.do").forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@UrlPattern(urlPath="/admin/techer_list.do")
	public String techerList(HttpServletRequest req, HttpServletResponse resp){
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			TecherMapper mapper=session.getMapper(TecherMapper.class);
			ArrayList<Techer> groupList=mapper.getTecherList();
			req.setAttribute("techerList", groupList);
		}finally{
			session.close();
		}
		return "admin/techer_list.jsp";
	}
	
	@UrlPattern(urlPath="/admin/techer_delete.do")
	public void techerDel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		String id=req.getParameter("id");
		System.out.println("id="+id);
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			TecherMapper mapper=session.getMapper(TecherMapper.class);
			mapper.deleteTecher(id);
			session.commit();
		}finally{
			session.close();
		}
		Util.refreshTecherListCache();
//		out.println("delete id="+id);
		try {
			req.getRequestDispatcher("/admin/techer_list.do").forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
