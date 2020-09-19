package com.jia.blog.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类(注册拦截器拦截器)
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    //注册拦截器
    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    //添加拦截器到spring mvc拦截器链

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/admin/**")
        .excludePathPatterns("/admin")
        .excludePathPatterns("/admin/login"); //排除路径
    }
}
