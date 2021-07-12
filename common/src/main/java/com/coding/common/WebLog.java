package com.coding.common;

import com.coding.utils.HttpKit;
import com.coding.utils.JsonUtil;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author mac
 */
@Aspect
@Slf4j
public class WebLog {

    @Pointcut("execution(* com.coding.controller..*.*(..))")
    private void pointCut() {
    }

    @Around("pointCut()")
    public Object timing(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed;
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.warn("request:error: uri:{},request:{}", HttpKit.getRequest().getRequestURI(), JsonUtil.toJson(joinPoint.getArgs()), throwable);
            throw throwable;
        }
        long times = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        try {
            log.info("request:success: useTime:{}ms,uri:{},request:{},response:{},header:{}", times,
                    HttpKit.getRequest().getRequestURI(),
                    JsonUtil.toJson(joinPoint.getArgs()),
                    JsonUtil.toJson(proceed),
                    JsonUtil.toJson(HttpKit.getRequestHeader())
            );
        } catch (Exception ignored) {
            // 无需打印错误日志
        }

        return proceed;
    }
}
