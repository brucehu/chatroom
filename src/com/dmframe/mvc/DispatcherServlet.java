package com.dmframe.mvc;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dmframe.annotation.Controller;
import com.dmframe.annotation.UrlPattern;
import com.dmframe.util.PackageUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;



public class DispatcherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HashMap<String,Object> cache=new HashMap<String,Object>();
	HashMap<String,Class<?>> classCache=new HashMap<String,Class<?>>();
	HashMap<String,Method> methodCache=new HashMap<String,Method>();
	String controlPath="";
	String viewPath="";
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		System.out.println("init()");
		
	}



	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		controlPath=config.getInitParameter("path");
		viewPath=config.getInitParameter("view");
		
		String contextPath=this.getServletContext().getContextPath();
		System.out.println("init() config, controlPath="+controlPath+",contextPath="+contextPath+",viewPath="+viewPath);
		List<String> controllers=PackageUtil.getClassName(controlPath);
		for(String controller:controllers){
			
			//String[] pathArray=controller.split("/");
			String className=controller;//pathArray[pathArray.length-1];
//			System.out.println(className);
			
			try {
				Class<?> cls = Class.forName(className);
//				Annotation[] annotations = cls.getAnnotations();
				if(cls.isAnnotationPresent(Controller.class)){
//					Controller controllerA = cls.getAnnotation(Controller.class);
					
					Method[] methods=cls.getMethods();
					for(Method method:methods){
						 boolean hasAnnotation = method.isAnnotationPresent(UrlPattern.class);
						 if (hasAnnotation) {
							 /*
							 * 根据注解类型返回方法的指定类型注解
							 */
							 UrlPattern annotation = method.getAnnotation(UrlPattern.class);
							 String urlPath=contextPath+annotation.urlPath();
							 classCache.put(urlPath, cls);
							 methodCache.put(urlPath, method);
							 System.out.println("method="+cls+" " +method.getName()+",urlPath="+urlPath);
						 }
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}



	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
		
	}
	
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doPost(req, resp);
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.service(request, response);
		String uri=((HttpServletRequest)request).getRequestURI();
//		System.out.println(uri);
		
		try {
			
			Class<?> cls=classCache.get(uri);
			if(cls==null){
				request.setAttribute("message", "class 404");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}
		
			Method method=methodCache.get(uri);
			if(method==null){
				request.setAttribute("message", "method 404");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}
			
			
			
			Object obj=cache.get(cls.getName());
			if(obj==null){
				obj=cls.newInstance();
//				System.out.println("newInstance "+obj);
				cache.put(cls.getName(), obj);
			}
//			System.out.println(obj);
//			Method method = cls.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			Object result=method.invoke(obj, request,response);
			if(result!=null&&result.getClass()==String.class){
				request.getRequestDispatcher(viewPath+"/"+(String)result).forward(request, response);
			}
//			System.out.println("invoke result:"+result);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			PrintWriter out=resp.getWriter();
//			e.printStackTrace(out);
			request.getRequestDispatcher("/error.jsp").forward(request,response);
		} 
		
	}

	
}
