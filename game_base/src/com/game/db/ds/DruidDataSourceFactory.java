package com.game.db.ds;

import java.lang.management.ManagementFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.console.DruidStat;
import com.alibaba.druid.support.console.Option;
import com.alibaba.druid.util.JdbcUtils;
import com.game.utils.MyProperties;

/**
 * 
 * @功能 :数据库连接池类
 * 
 */
public class DruidDataSourceFactory {
	private static final Logger logger =LogManager.getLogger(DruidDataSourceFactory.class);
	
	private static final DruidDataSourceFactory instance=new DruidDataSourceFactory();
	private  DruidDataSource dataSource;

	public DruidDataSourceFactory(){
	}
 

	public void initDruidDataSource(String jdbcPath) throws Exception {
			MyProperties prop=new MyProperties(jdbcPath);
		 	
			DruidDataSource druidDataSource = new DruidDataSource();
			druidDataSource.setConnectProperties(prop.getProperties());
			druidDataSource.setUrl(prop.getProperty("db.url"));
			druidDataSource.setUsername(prop.getProperty("db.username"));
			druidDataSource.setPassword(prop.getProperty("db.password"));
		 
			druidDataSource.init();
			dataSource=druidDataSource;
	}

	 

	public void printStatInfo() {
		try {
			String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
			String[] cmdArray = { "-sql", pid };
			Option opt = Option.parseOptions(cmdArray);
			DruidStat.printDruidStat(opt);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}

	}

	public void shutdown() {
		JdbcUtils.close(dataSource);
	}

	public DruidDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DruidDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public static DruidDataSourceFactory getInstance() {
		return instance;
	}

}
