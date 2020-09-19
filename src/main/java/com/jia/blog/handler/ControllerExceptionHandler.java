package com.jia.blog.handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

/**
 * 拦截器,统一异常类(异常处理类)
 */
@ControllerAdvice //可以拦截所有被Controller注解的类
public class ControllerExceptionHandler {

    //获取日志信息(this.getClass : 获取是哪个类Class发生了异常)
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Exception.class : 只要是这个异常类型的,都可以被拦截
    @ExceptionHandler(Exception.class) //标识这个方法可以做异常处理
    public ModelAndView exceptionHander(HttpServletRequest request,Exception e) throws Exception {

        //1.获取发生异常的URL 2.在控制台上打印异常信息
        logger.error("Request URL : {}, Exception : {}",request.getRequestURL(),e);

        /*当异常有自定义状态码时,就不要拦截,交给boot自动处理*/
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) !=null){
            throw e; //说明自定义状态码存在,就抛出异常
        }

        ModelAndView mav = new ModelAndView();
        //1.获取发生异常的url,并存储model  2. 获取异常的信息
        mav.addObject("url",request.getRequestURL());
        mav.addObject("exception",e);
        mav.setViewName("error/error"); //返回的页面
        return mav;
    }
}
