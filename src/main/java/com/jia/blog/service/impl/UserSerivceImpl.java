package com.jia.blog.service.impl;

import com.jia.blog.mapper.UserMapper;
import com.jia.blog.pojo.User;
import com.jia.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 管理用户账号的实现类
 */
@Service
public class UserSerivceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /*--------------------Select-------------------*/
    /**
     * 校验用户输入的用户名和密码是否正确
     * @param username 前台输入的用户名
     * @param password 前台输入的密码
     * @return 实体类
     */
    @Override
    public User checkUser(String username, String password) throws IOException {
        return userMapper.findByUsernameByPassword(username,password);
    }

    /**
     * 通过id查询对应的用户
     * @param id 查询的id
     * @return 实体类
     */
    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }
}
