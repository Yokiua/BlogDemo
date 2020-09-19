package com.jia.blog.service;

import com.jia.blog.pojo.User;
import java.io.IOException;

/**
 * 管理用户的接口
 */
public interface UserService {
    /*--------------------Select-------------------*/
    /**
     * 校验用户名密码
     * @param username 前台输入的用户名
     * @param password 前台输入的密码
     * @return 实体类
     * @throws IOException 异常
     */
    User checkUser(String username,String password) throws IOException;

    /**
     * 通过id查询对应的用户
     * @param id 查询的id
     * @return 实体类
     */
    User findById(Long id);
}
