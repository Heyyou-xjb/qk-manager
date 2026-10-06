package com.itheima.aop;


import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

// 代理模式
@Slf4j
@Aspect // 标识当前类是一个切面类
@Component
public class MyAspect {

    @Pointcut("@annotation(com.itheima.aop.Log)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        String className = joinPoint.getSignature().getDeclaringTypeName();
        log.info("拦截类： {}", className);

        String methodName = joinPoint.getSignature().getName();
        log.info("拦截方法： {}", methodName);

        Object[] args = joinPoint.getArgs();
        log.info("拦截参数： {}", JSONUtil.toJsonStr(args));

        log.info("around before"); // 1
        // 方法前
        long startTime = System.currentTimeMillis();

        // 目标方法执行
        Object proceed = joinPoint.proceed();

        // 方法后
        long endTime = System.currentTimeMillis();

        log.info("方法执行时间：{}ms", endTime - startTime);
        log.info("around after");

        return proceed;
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint){
        log.info("before"); // 2
    }


    @After("pointcut()")
    public void after(JoinPoint joinPoint){
        // 目标方法执行后执行（不管有没有异常）
        log.info("after"); // 4
    }

    @AfterReturning("pointcut()")
    public void afterReturning(JoinPoint joinPoint){
        // 目标方法正常返回才执行
        log.info("afterReturning"); // 3
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing(JoinPoint joinPoint){
        log.info("afterThrowing");
    }

}
