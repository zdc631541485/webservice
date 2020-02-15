package com.qdtz.SpringWebService.aop;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.qdtz.SpringWebService.annotation.PointAnnotation;
import com.qdtz.SpringWebService.annotation.TestEnum;
import com.qdtz.SpringWebService.model.Car;
import com.qdtz.SpringWebService.service.TestOracleService;

@Component
@Aspect
public class MyAop {
	
	final static Logger logger = Logger.getLogger(MyAop.class);
	
	@Autowired
	private TestOracleService  testOracleService;

	//@Pointcut("execution(* com.qdtz.SpringWebService.controller.HelloController.*(..))")//表示HelloController下所有的方法
	@Pointcut("@annotation(com.qdtz.SpringWebService.annotation.PointAnnotation)")  //表示带有AssertOK的注解
    public void point() {

    }
	
	@Before("point()")
    public void before(JoinPoint joinPoint) {
        logger.info("before success");
    }
	
	@After("point()")
    public void after(JoinPoint joinPoint) {
		//int i = 1/0;
        logger.info("after success");
    }
	
	//@AfterReturning("point()")
    public void before01(JoinPoint joinPoint) {
        logger.info("before success");
    }
	
	@SuppressWarnings("rawtypes")
	@AfterReturning(value = "point()", returning = "result")
    public void after(JoinPoint joinPoint, Object result) {
		try {
			String methodName = joinPoint.getSignature().getName(); //先获取目标方法的签名，再获取目标方法的名
	        Object[] args = joinPoint.getArgs();  //获取目标方法的入参
	        Car car = null;
	        for (Object object : args) {
	        	if(object instanceof Car)
	            	car  = (Car) object;
			}
	        logger.info("方法名: " + methodName + " ,参数列表:  " + Arrays.asList(args));
	        logger.info("参数对象Car: "+car.toString());
        
        	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        	HttpSession session = request.getSession();
        	Object sessionMsg = session.getAttribute("msg");
        	Class<?> clazz = joinPoint.getTarget().getClass(); //先获取被织入增强处理的目标对象，再获取目标类的clazz
			Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes(); //获取目标方法参数类型
            Method method = clazz.getMethod(methodName, parameterTypes);  //获取目标方法
            PointAnnotation annotation = method.getAnnotation(PointAnnotation.class);  //获取方法上的注解
            TestEnum testEnum = annotation.description();  //获取注解值
            logger.info("用户的ip: " + request.getRemoteAddr() + " ,获取的sesison消息：" + sessionMsg);
            logger.info("注解信息：" + testEnum.value());
            logger.info("注入的testOracleService：" + testOracleService);
            logger.info(Thread.currentThread().getName());
            testOracleService.asynJob();
        } catch (Throwable throwable) {
            logger.error("MyAop @AfterReturning error:	" + throwable.getMessage());
        }
    }
	
	@SuppressWarnings("rawtypes")
	//@Around(value = "point()")
	public Object assertAround(ProceedingJoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName(); //先获取目标方法的签名，再获取目标方法的名
        Object[] args = joinPoint.getArgs();  //获取目标方法的入参
        logger.info("方法名: " + methodName + " ,参数列表:  " + Arrays.asList(args));
        try {
        	Class<?> clazz = joinPoint.getTarget().getClass(); //先获取被织入增强处理的目标对象，再获取目标类的clazz
        	Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes(); //获取目标方法参数类型
            Method method = clazz.getMethod(methodName, parameterTypes);  //获取目标方法
            PointAnnotation annotation = method.getAnnotation(PointAnnotation.class);//获取方法上的注解
            String desc = annotation.str();  //获取注解函数值
            long starttime = System.currentTimeMillis();
            Object proceed = joinPoint.proceed();  //执行目标方法
            long exctime = System.currentTimeMillis() - starttime;
            logger.info("执行时间："+ exctime + "毫秒");
            logger.info("注解信息："+ desc + "......");
            logger.info("proceed: "+ proceed);  //打印目标方法的return结果
        } catch (Throwable e) {
            logger.error("MyAop Around Error: " + e.getMessage());
        }
        return "MyAop类@Around通知的返回值";
    }
	
	//JoinPoint这个参数一定要出现在参数列表的第一位
    @AfterThrowing(value = "point()",throwing = "exception")
    public void logException(JoinPoint joinPoint,Exception exception) {
    	logger.error("全类名为：" + joinPoint.getSignature().getDeclaringTypeName()+"的方法："+joinPoint.getSignature().getName() +"()发生异常...异常信息为：" + exception.getMessage());
    }

}
