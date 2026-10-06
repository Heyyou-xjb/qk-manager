package com.itheima.aop;

import cn.hutool.json.JSONUtil;
import com.itheima.model.dataobject.OperateLogDO;
import com.itheima.service.OperateLogService;
import com.itheima.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class LogReportAspect {

    @Autowired
    private OperateLogService operateLogService;

    @Around("@annotation(com.itheima.aop.Log)")
    public Object logReport(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        Integer userId = UserHolder.getUserId();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        Object proceed = joinPoint.proceed();

        long endTime = System.currentTimeMillis();

        try {
            OperateLogDO operateLogDO = new OperateLogDO();
            operateLogDO.setOperateUserId(userId);
            operateLogDO.setClassName(className);
            operateLogDO.setMethodName(methodName);
            operateLogDO.setMethodParams(JSONUtil.toJsonStr(args));
            operateLogDO.setReturnValue(JSONUtil.toJsonStr(proceed));
            operateLogDO.setCostTime(endTime - startTime);
            operateLogDO.setOperateTime(LocalDateTime.now());

            operateLogService.save(operateLogDO);
        } catch (Exception e) {
            log.warn("日志保存失败", e);
        }

        return proceed;
    }
}
