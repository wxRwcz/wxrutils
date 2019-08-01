package cn.wxrwcz.utils;

public class BitOperationUtil
{
    public static boolean hasState(long states,long value)
    {
        return (states & value) !=  0;
    }

    public static long addState(long states,long value)
    {
        if(hasState(states,value))
        {
            return states;
        }
        return (states | value) ;
    }
    public static long removeState(long states,long value)
    {
        if(!hasState(states,value))
        {
            return states;
        }
        return (states ^ value) ;
    }
}