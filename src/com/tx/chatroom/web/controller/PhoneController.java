package com.tx.chatroom.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.SqlSession;

import com.dmframe.annotation.Controller;
import com.dmframe.annotation.UrlPattern;
import com.dmframe.dao.BaseDao;
import com.dmframe.dao.MyDataSourceFactory;
import com.tx.chatroom.domain.PhoneQhpz;
import com.tx.chatroom.domain.QQ;
import com.tx.chatroom.domain.User;
import com.tx.chatroom.persistence.PhoneQhpzMapper;
import com.tx.chatroom.persistence.QQMapper;
import com.tx.chatroom.persistence.TecherMapper;
import com.tx.chatroom.persistence.UserMapper;

@Controller
public class PhoneController {
	
	@UrlPattern(urlPath="/admin/qq_list.do")
	public String listQQ(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String curPage=req.getParameter("curPage");
		if(curPage==null||curPage.length()<=0){
			curPage="1";
		}
		int icurPage=Integer.parseInt(curPage);
		int pageSize=20;
		
		
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			QQMapper mapper=session.getMapper(QQMapper.class);
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
			ArrayList<QQ> qqList=mapper.getQQListPage(begin, pageSize);
			req.setAttribute("qqList", qqList);
			req.setAttribute("icurPage", icurPage);
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("total", total);
			req.setAttribute("iprevPage",icurPage-1);
			req.setAttribute("inextPage",icurPage+1);
		}finally{
			session.close();
		}
		return "admin/qq_list.jsp";
	}
	
	@UrlPattern(urlPath="/listPhone.do")
	public String listPhone(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			PhoneQhpzMapper mapper=session.getMapper(PhoneQhpzMapper.class);
			ArrayList<PhoneQhpz> list= mapper.getPhoneList();
			req.setAttribute("list", list);
		}finally{
			session.close();
		}
		
		return "listPhone.jsp";
	}
	@UrlPattern(urlPath="/submitPhone.do")
	public void submitPhone(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		String phone=req.getParameter("phone");
		String qhpz=req.getParameter("qhpz");
		System.out.println("before "+qhpz);
//		if(qhpz!=null){
//			qhpz=new String(qhpz.getBytes("ISO8859-1"),"UTF-8");
//		}
		System.out.println(qhpz);
		PhoneQhpz phoneQhpz=new PhoneQhpz();
		phoneQhpz.setPhone(phone);
		phoneQhpz.setQhpz(qhpz);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ctime=dateFormat.format(new Date());
		phoneQhpz.setCtime(ctime);
		SqlSession session=BaseDao.sqlSessionFactory.openSession();
		try{
			PhoneQhpzMapper mapper=session.getMapper(PhoneQhpzMapper.class);
			mapper.addPhoneQhpzMapper(phoneQhpz);
			session.commit();
		}finally{
			session.close();
		}
		JSONObject json=new JSONObject();
		json.put("status", 1);
		out.println(json.toString());
		
	}
}
