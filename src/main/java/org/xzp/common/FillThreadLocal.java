package org.xzp.common;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/10 16:46
 * @Version 1.0
 */
public class FillThreadLocal {

    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();

    public static void setvalue(Long id){
        threadLocal.set(id);
    }

    public static Long getvalue(){
        return threadLocal.get();
    }

}
