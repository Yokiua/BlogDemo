package com.jia.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日记处理的aop切面类
 */
@Aspect //说明这个类是aop类
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //拦截路径
    @Pointcut("execution(* com.jia.blog.controller.*.*(..))")
    public void log() { //切面方法

    }

    @Before("log()") //在log方法执行之前执行
    public void doBefore(JoinPoint joinPoint) {
        //获取并设置相关参数
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURI();
        String ip = request.getRemoteAddr();
        String classMthod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMthod, args);
        logger.info("Request : {}", requestLog);
    }

    @After("log()") //在log方法执行之后执行
    public void doAfter() {
        /* logger.info("--------doafter---------");*/
    }

    //方法返回  result : 返回的参数, pointcut : 切面的方法
    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterRutrun(Object result) {
        logger.info("Result : {}", result);
    }

    //自定义内部类,为了获取请求的url、ip、method、args
    private class RequestLog {
        private String url;
        private String ip;
        private String classsMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classsMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classsMethod = classsMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classsMethod='" + classsMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
