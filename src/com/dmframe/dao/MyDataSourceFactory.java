package com.dmframe.dao;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;

public class MyDataSourceFactory implements DataSourceFactory {

	private BasicDataSource datasource = null;
	public MyDataSourceFactory(){
		datasource=new BasicDataSource();
	}
	@Override
	public DataSource getDataSource() {
		// TODO Auto-generated method stub
		return datasource;
	}

	@Override
	public void setProperties(Properties ps) {
		// TODO Auto-generated method stub
		datasource.setDriverClassName( ps.getProperty("driverclassname"));
        datasource.setUsername( ps.getProperty("username"));
        datasource.setUrl( ps.getProperty("url"));
        datasource.setPassword( ps.getProperty("password"));
        datasource.setDefaultAutoCommit( ps.getProperty("defaultautocommit","0").equals("1") );
        datasource.setInitialSize( Integer.parseInt(ps.getProperty("initialsize","2")) );
        datasource.setMaxActive( Integer.parseInt(ps.getProperty("maxactive","20")));
        datasource.setMaxIdle( Integer.parseInt(ps.getProperty("maxidle","0")));
        datasource.setMaxWait(Long.parseLong(ps.getProperty("maxwait","0")));    
        datasource.setTestOnBorrow(true);      
        datasource.setTestOnReturn(true);
        datasource.setTestWhileIdle(true);
        datasource.setValidationQuery("select 1"); 
        datasource.setLogAbandoned(true);
        
	}

}
