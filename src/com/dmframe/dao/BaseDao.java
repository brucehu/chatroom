package com.dmframe.dao;

import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class BaseDao {

	public static ExecutorService threadPool;
	public static SqlSessionFactory sqlSessionFactory;
	static{
		threadPool=Executors.newFixedThreadPool(50);
		Reader reader;
		try {
			reader = Resources.getResourceAsReader("/mybatis.xml");
			sqlSessionFactory= new SqlSessionFactoryBuilder().build(reader);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		 
	}
	
	public static void postSqlTast(Runnable task){
		threadPool.execute(task);
	}
}
