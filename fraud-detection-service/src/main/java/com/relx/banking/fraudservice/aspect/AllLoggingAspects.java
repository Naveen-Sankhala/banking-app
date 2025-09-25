package com.relx.banking.fraudservice.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
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
public class AllLoggingAspects {
	
	@Pointcut("execution(* com.relx.banking.*.controller.*.*(..))")
	private void forControllerPackage() {}

	@Pointcut("execution(* com.relx.banking.*.service.*.*(..))")
	private void forServicePackage() {}

	@Pointcut("forServicePackage()")
	private void forAppFlow() {}

	@AfterThrowing(pointcut="forAppFlow()",throwing = "ex")
	public void doRecoveryActions(JoinPoint joinPoint, Throwable ex) {

		Signature signature = joinPoint.getSignature();
		String methodName = signature.getName();
		String stuff = signature.toString();
		String arguments = Arrays.toString(joinPoint.getArgs());

		if(ex.getMessage().equalsIgnoreCase("Invalid Token...!!!") || ex.getMessage().equalsIgnoreCase("No Record Found...!!!")) {
			log.warn(ex.getMessage());
		}
		else {

			log.error("We have caught exception in method: " + methodName + " with arguments " + arguments + 
					"\n                         and the full toString: " + stuff +
					"\n                         the exception is: " + ex.getMessage());
			//"\n                         Error Class is :: "+ ex.getStackTrace()[0].getClassName()+" Method :: "+ex.getStackTrace()[0].getMethodName()+" Line No. :: "+ex.getStackTrace()[0].getLineNumber());
			log.error("error trace is",ex);
		}
	}
}
