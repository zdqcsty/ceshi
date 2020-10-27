package com.example.ceshi.aaa;

public class ThreadDomain19
{
    public void serviceMethodA()
    {
        synchronized (this)
        {
            try
            {
                System.out.println("A begin time = " + System.currentTimeMillis());
                Thread.sleep(2000);
                System.out.println("A end time = " + System.currentTimeMillis());
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

        }
    }

    public void serviceMethodB()
    {
        synchronized (this)
        {
            System.out.println("B begin time = " + System.currentTimeMillis());
            System.out.println("B end time = " + System.currentTimeMillis());
        }
    }
}