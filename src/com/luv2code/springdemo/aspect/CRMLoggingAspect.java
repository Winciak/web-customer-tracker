package com.luv2code.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {

    //setup logger
    private Logger myLogger = Logger.getLogger(getClass().getName());

    //setup pointcut declarations
    @Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))" )
    private void forControllerPackage() {}

    @Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))" )
    private void forServicePackage() {}

    @Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))" )
    private void forDaoPackage() {}

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow(){}

    //add Before advice
    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint){
        String method = joinPoint.getSignature().toShortString();
        myLogger.info("=====>> in @Before method: " + method);

        Object[] args = joinPoint.getArgs();

        for (Object tempArg : args){
            myLogger.info("========>> argument: " + tempArg);
        }
    }

    //add After Returning Advice
    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "theResult")
    public void afterReturning(JoinPoint joinPoint, Object theResult){

        String method = joinPoint.getSignature().toShortString();
        myLogger.info("=====>> in @AfterReturning method: " + method);

        myLogger.info("=====>> result: "+theResult);
    }
}
