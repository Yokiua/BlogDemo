package com.jia.blog.excetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 博客不存在的异常
 */
@ResponseStatus(HttpStatus.NOT_FOUND) //作为一个资源找不到的状态返回(404)
public class NotFondExcetion extends RuntimeException{

    public NotFondExcetion() {
    }

    public NotFondExcetion(String message) {
        super(message);
    }

    public NotFondExcetion(String message, Throwable cause) {
        super(message, cause);
    }
}
