package com.itheima.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class TimeRecordAspect {

    @Around("execution(* com.itheima.Controller.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();

        log.info("Class: {}, Method: {} took {} ms, Args: {}", className, methodName, endTime - startTime, args);

        return result;
    }
}
