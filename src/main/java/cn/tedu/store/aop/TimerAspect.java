package cn.tedu.store.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimerAspect {
    @Around("execution(* cn.tedu.store.service.impl.*.*(..))")
    public Object timeAspect(ProceedingJoinPoint pjp) throws Throwable {
        //切入前的操作
        long start = System.currentTimeMillis();
        //切入点方法
        Object result = pjp.proceed();
        //切入后的方法
        long end = System.currentTimeMillis();
        System.out.println("执行的耗时:" + (end - start) + "ms");
        return result;
    }
}
