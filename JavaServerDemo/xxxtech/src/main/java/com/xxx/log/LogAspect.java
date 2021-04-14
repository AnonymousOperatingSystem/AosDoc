package com.xxx.log;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Aspect
@Component
@Slf4j
public class LogAspect {
    @Pointcut("execution(public * com.xxx..controller.*.*(..))")
    public void all(){}

    @Before("all()")
    public void aroundWeb(JoinPoint pjp) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Enumeration<String> headerNames = request.getHeaderNames();
        //获取获取的消息头名称，获取对应的值，并输出
        while(headerNames.hasMoreElements()){
            String nextElement = headerNames.nextElement();
            log.info(nextElement+":"+request.getHeader(nextElement));
        }

        log.info("url:{}",request.getRequestURL()+"?"+request.getQueryString());
        log.info("IP :{}",request.getRemoteAddr());
        log.info("method:{}",request.getMethod());
        log.info("CLASS_METHOD:{}",pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
        log.info("args:{}", new Gson().toJson(pjp.getArgs()));
    }

    @AfterReturning(returning = "o",pointcut = "all()")
    public void afterReturning(Object o){
        log.info("method ：{}",new Gson().toJson(o));
    }
    @AfterThrowing(pointcut = "all()",throwing = "e")
    public void afterThrowing(Exception e){
        log.info("logaspect方法执行发生异常：{}",e.getMessage());
    }
    @Before("all()")
    public void aroundRedis() {
        log.info("logaspect 开始：");
    }
}