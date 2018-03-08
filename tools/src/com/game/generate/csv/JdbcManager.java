package com.game.generate.csv;

import com.game.generate.dao.MyProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author jianpeng.zhang
 * @since 2017/3/2.
 */
public class JdbcManager
{
	private static final Logger LOG =LogManager.getLogger(CSVReader.class);
    private MyProperties prop = null;

    private volatile static JdbcManager jdbcManager;
    private final static Object lock = new Object();

    private JdbcManager()
    {
        init();

    }

    public static JdbcManager getInstance()
    {
        if (jdbcManager == null)
        {
            synchronized (lock)
            {
                if (jdbcManager == null)
                {
                    jdbcManager = new JdbcManager();
                }
            }
        }
        return jdbcManager;
    }

    private void init()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            prop = new MyProperties("config/csv_reader_config.properties");
        }
        catch (Exception e)
        {
        	LOG.error(e.getMessage(), e);
        }
    }

    public Connection getConnect()
    {
        try{
            return DriverManager
                    .getConnection(prop.getProperty("db.url"), prop.getProperty("db.user"), prop.getProperty("db.pwd"));
        }catch(SQLException se){
            System.out.println("数据库连接失败！");
            LOG.error(se.getMessage(), se);
        }
        return null;
    }
}
