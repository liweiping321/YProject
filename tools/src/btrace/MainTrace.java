package btrace;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author jianpeng.zhang
 * @since 2017/4/11.
 */
public class MainTrace
{
    private static boolean flag = true;
    private int i = 0;
    private static int j = 0;
    public static void main(String[] args) throws Exception
    {
        MyThread thread = new MyThread();
        thread.start();
        while (flag)
        {
            int[] longs = new int[3];
            new MainTrace().bar();
            foo("param" + j);
        }
    }



    private int bar() throws InterruptedException
    {
        Thread.sleep(5000);
        add();
        int[] longs = new int[5];
        longs[1] = 234;
        getI();
        System.out.println(longs[1]);
        System.getProperty("aaa");
        if (!flag)
        {
            cut();
        }
        return i;
    }

    public int getI()
    {
        return i;
    }

    public void setI(int i)
    {
        this.i = i;
    }

    private void add()
    {
        int[] longs = new int[1];
        longs[0] = 234;
        System.out.println(longs[0]);
        setI(i + 1);
        j = j + 1;
    }

    private void cut()
    {
        i--;
    }

    private static void foo(String param) throws InterruptedException
    {
        Thread.sleep(1000);
        System.out.println(param);
        int[] longs = new int[6];
        new ArrayList<Integer>();
    }
}

class MyThread extends Thread
{
    @Override
    public void run()
    {
        try
        {
            sleep(3000);
            int[][] longs = new int[7][2];
            longs[1][1] = 234;
            new LinkedList<String>();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("MyThread running");
        new MyThread().start();
    }
}
