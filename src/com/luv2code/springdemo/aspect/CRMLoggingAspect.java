package com.luv2code.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	// setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	// setup pointcut declarations
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	// add @Before advice
	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	// add @AfterReturning advice
	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	private void forDAOPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	private void forAppFlow() {}
	
	// add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		
		
		// display method we are calling
		String method = joinPoint.getSignature().toShortString();
		myLogger.info("============> in @Before: calling method: " + method);
		
		// display the arguments to the method
		
		// get the arguments
		Object[] args = joinPoint.getArgs();
		
		// loop through and display args
		
		for (Object arg: args) {
			
			myLogger.info("=============> arguments: " + arg);			
			
		}
		
	}
	
	// add @AfterReturning advice
	@AfterReturning(pointcut="forAppFlow()", returning="result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		
		// display method we are calling
		String method = joinPoint.getSignature().toShortString();
		myLogger.info("============> in @AfterReturning: from method: " + method);
		
		// display data returned
		myLogger.info("============> result: " + result	);
		
	}
}
