package com.relx.banking.notificationservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Naveen.Sankhala
 * Sep 22, 2025
 */
@Aspect
@Component
@Slf4j
public class NotificationServiceLoggingAspect {

	@Pointcut("execution(* com.relx.banking.*.controller.*.*(..))")
	private void forControllerPackage() {}

	@Pointcut("execution(* com.relx.banking.*.service.*.*(..))")
	private void forServicePackage() {}

	@Pointcut("forControllerPackage() || forServicePackage()")
	private void forPccRetrievalFlow() {}

	
	@Around("forServicePackage()")
	public Object profileStrategyMethods(ProceedingJoinPoint pjp) throws Throwable {

		long start = System.currentTimeMillis();
		Object output = null;
		log.info("=====>> Class:"+pjp.getTarget().getClass()+" entry -> method ->"+pjp.getSignature().getName());
		try{
			output = pjp.proceed();
			long elapsedTime = System.currentTimeMillis() - start;
			log.info("=====>> Method execution time: " + elapsedTime + " milliseconds.");
			log.info("=====>> Class:"+pjp.getTarget().getClass()+" exit -> method ->"+pjp.getSignature().getName());
		}finally {
            //Do Something useful, If you have
        }

		return output;
	}
}
