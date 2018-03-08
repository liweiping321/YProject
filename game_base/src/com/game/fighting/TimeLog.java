package com.game.fighting;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jianpeng.zhang
 * @since 2017/6/6.
 */
public class TimeLog
{
    private StringBuilder sb = new StringBuilder();
    private File file = new File("timeLog.txt");
    private Map<String, Long> markMap = new HashMap<>();
    private boolean onlyLogDelay = true;
    private boolean debug = true;

    public void mark(String name)
    {
        if (debug)
        {
            markMap.put(name, System.currentTimeMillis());
            sb.append(name).append("--------------start,         ");
        }
    }

    public void log(String name)
    {
        if (debug)
        {
            sb.append(name).append("--------------end, cost time = ").append(System.currentTimeMillis() - markMap.get(name)).append("\n");
        }
    }

    public void push()
    {
        if (debug)
        {
            try
            {
                if (!onlyLogDelay || System.currentTimeMillis() - markMap.get("game update") >= 30)
                {
                    FileUtils.writeStringToFile(file, sb.toString(), true);
                }
                sb = new StringBuilder();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public StringBuilder stringBuilder = new StringBuilder();
    public long time = 0;
    public void markSingle(String tab)
    {
        if (debug)
        {
            time = System.currentTimeMillis();
            stringBuilder.append(tab).append("  ----------- start").append("\n");
        }
    }

    public void addLog(String tab)
    {
        if (debug)
        {
            stringBuilder.append(tab).append(", current cost time = ").append(System.currentTimeMillis() - time).append("\n");
        }
    }

    public void logSingle()
    {
        // if (debug)
        // {
            // if (System.currentTimeMillis() - time >= 8)
            // {
            //     try
            //     {
            //         FileUtils.writeStringToFile(file, stringBuilder.toString(), true);
            //     }
            //     catch (IOException e)
            //     {
            //         e.printStackTrace();
            //     }
            // }
            // stringBuilder = new StringBuilder();
        // }
    }


}
