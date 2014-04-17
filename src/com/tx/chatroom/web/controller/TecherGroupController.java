package com.tx.chatroom.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.session.SqlSession;

import com.dmframe.annotation.Controller;
import com.dmframe.annotation.UrlPattern;
import com.dmframe.dao.BaseDao;
import com.dmframe.dao.CacheBase;
import com.tx.chatroom.domain.*;
import com.tx.chatroom.persistence.TecherMapper;

@Controller
public class TecherGroupController {

	@UrlPattern(urlPath="/admin/techer_group_add.do")
	public String techerGroupAdd(HttpServletRequest req, HttpServletResponse resp){
		req.setAttribute("buttonValue", "添加");
		req.setAttribute("action", "techer_group_add_db.do");
		return "admin/group_add.jsp";
	}
	@UrlPattern(urlPath="/admin/techer_group_add_db.do")
	public void techerGroupAddDB(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String groupName=req.getParameter("groupName");
		if(groupName!=null){
			groupName=new String(groupName.getBytes("ISO8859-1"),"UTF-8");
		}
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			TecherMapper mapper=session.getMapper(TecherMapper.class);
			mapper.addGroup(groupName);
			session.commit();
		}finally{
			session.close();
		}
		Util.refreshTecherListCache();
		try {
			req.getRequestDispatcher("/admin/techer_group.do").forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@UrlPattern(urlPath="/admin/techer_group_update.do")
	public String techerGroupUpdate(HttpServletRequest req, HttpServletResponse resp){
		String id=req.getParameter("id");
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			TecherMapper mapper=session.getMapper(TecherMapper.class);
			TecherGroup group=mapper.getTecherGroupById(id);
			req.setAttribute("group", group);
		}finally{
			session.close();
		}
		req.setAttribute("buttonValue", "修改");
		req.setAttribute("action", "techer_group_update_db.do");
		
		return "admin/group_add.jsp";
	}
	
	@UrlPattern(urlPath="/admin/techer_group_update_db.do")
	public void techerGroupDB(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String id=req.getParameter("id");
		String groupName=req.getParameter("groupName");
		if(groupName!=null){
			groupName=new String(groupName.getBytes("ISO8859-1"),"UTF-8");
		}
		TecherGroup group=new TecherGroup();
		group.setGroupId(Integer.parseInt(id));
		group.setGroupName(groupName);
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			TecherMapper mapper=session.getMapper(TecherMapper.class);
			mapper.updateGroup(group);
			session.commit();
		}finally{
			session.close();
		}
		Util.refreshTecherListCache();
		try {
			req.getRequestDispatcher("/admin/techer_group.do").forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@UrlPattern(urlPath="/admin/techer_group.do")
	public String techerGroup(HttpServletRequest req, HttpServletResponse resp){
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			TecherMapper mapper=session.getMapper(TecherMapper.class);
			ArrayList<TecherGroup> groupList=mapper.getTecherGroupList();
			req.setAttribute("groupList", groupList);
		}finally{
			session.close();
		}
		return "admin/group_list.jsp";
	}
	
	@UrlPattern(urlPath="/admin/techer_group_delete.do")
	public void techerGroupDel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		String id=req.getParameter("id");
		System.out.println("id="+id);
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			TecherMapper mapper=session.getMapper(TecherMapper.class);
			mapper.deleteGroup(id);
			session.commit();
		}finally{
			session.close();
		}
		Util.refreshTecherListCache();
//		out.println("delete id="+id);
		try {
			req.getRequestDispatcher("/admin/techer_group.do").forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
