package cn.wxrwcz.utils;

/**
 * @Description: java类作用描述
 * @Author: wangcz
 * @CreateDate: 2019/8/1$ 12:15$
 * @Version: 1.0
 */
public class BitOperationUtil
{    /**
 * 是否存在一个状态值
 */
    public static boolean hasState(long states,long value)
    {
        return (states & value) !=  0;
    }
    /**
     * 添加一个状态值
     */
    public static long addState(long states,long value)
    {
        if(hasState(states,value))
        {
            return states;
        }
        return (states | value) ;
    }
    /**
     * 删除一个状态值
     */
    public static long removeState(long states,long value)
    {
        if(!hasState(states,value))
        {
            return states;
        }
        return (states ^ value) ;
    }
}