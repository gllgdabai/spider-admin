package com.dabai.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author
 * @create 2022-03-24 8:52
 */
// 不再打印，注释掉Configuration
//@Configuration
@Aspect
public class AnnotationPointcut {

    private static final Logger Log = LoggerFactory.getLogger(AnnotationPointcut.class);

    public static Object basicAspect(ProceedingJoinPoint jp) throws Throwable {
//        System.out.println("环绕前");
        long time = System.currentTimeMillis();
        // 执行目标方法proceed
        Object proceed = jp.proceed();
//        System.out.println("环绕后");
        long costTime = System.currentTimeMillis() - time;
        System.out.println("1.调用方法：" + jp.getSignature());
        System.out.println("2.执行时间:" + costTime + "ms");
//        System.out.println("3.返回结果：" + proceed);
        String a = (proceed == null) ? null : String.valueOf(proceed.getClass());
        System.out.println("4. 返回类型：" + a + "\n");
        return proceed;
    }

    // 所有module下都是可以监测到的，因为包名和前缀都一样
    @Around("execution(* com.dabai.*.*.*(..))")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        return basicAspect(jp);
    }

    @Around("execution(* com.dabai.*.*.*.*(..))")
    public Object around1(ProceedingJoinPoint jp) throws Throwable {
        return basicAspect(jp);
    }

    @Around("execution(* com.dabai.*.*.*.*.*(..))")
    public Object around2(ProceedingJoinPoint jp) throws Throwable {
        return basicAspect(jp);
    }

    @Around("execution(* com.dabai.*.*.*.*.*.*(..))")
    public Object around3(ProceedingJoinPoint jp) throws Throwable {
        return basicAspect(jp);
    }
}
