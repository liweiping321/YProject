package com.game.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author lip.li
 * @date 2017年1月9日
 */
public class MyProperties
{
 
    private Properties properties;

    public MyProperties(String filePath)throws Exception
    {
        properties = loadProp(filePath);
    }

  
    public Properties loadProp(String filePath)throws Exception
    {
        if (StringUtil.isEmpty(filePath))
        {
            throw new RuntimeException("Properties file path can't null");
        }
        Properties prop = new Properties();
        InputStream fileinputstream = null;
        try
        {
            fileinputstream = new FileInputStream(filePath);
            prop.load(fileinputstream);
            properties = prop;
        }
        finally
        { 
                if (null != fileinputstream)
                {
                    fileinputstream.close();
                }
             
        }
        return prop;
    }

    public Properties getProperties()
    {
        return properties;
    }

    public void setProperties(Properties properties)
    {
        this.properties = properties;
    }

    public String getProperty(String key, String defaultValue)
    {
        return properties.getProperty(key, defaultValue);
    }

    public String getProperty(String key)
    {
        return properties.getProperty(key);
    }

    public int getInt(String key, int defaultValue)
    {
        String value = properties.getProperty(key);
        if (StringUtil.isEmpty(value))
        {
            return defaultValue;
        }
        return Integer.parseInt(value);

    }

    public long getLong(String key, long defaultValue)
    {
        String value = properties.getProperty(key);
        if (StringUtil.isEmpty(value))
        {
            return defaultValue;
        }
        return Long.parseLong(value);

    }

    public boolean getBoolean(String key, boolean defaultValue)
    {
        String value = properties.getProperty(key);
        if (StringUtil.isEmpty(value))
        {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);

    }

    public int getInt(String key)
    {
        return Integer.parseInt(properties.getProperty(key));
    }

    public long getLong(String key)
    {
        return Long.parseLong(properties.getProperty(key));
    }

    public boolean getBoolean(String key)
    {
        return Boolean.parseBoolean(properties.getProperty(key));
    }
    
    public double getDouble(String key, double defaultValue)
    {
        String value = properties.getProperty(key);
        if (StringUtil.isEmpty(value))
        {
            return defaultValue;
        }
        return Double.parseDouble(value);

    }

    public double getDouble(String key)
    {
        String value = properties.getProperty(key); 
        return Double.parseDouble(value);

    }

}
