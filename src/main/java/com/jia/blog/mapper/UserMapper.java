package com.jia.blog.mapper;

import com.jia.blog.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 * 操作user数据的dao层
 */
@Repository
public interface UserMapper {

    /*--------------------Select-------------------*/
    /**
     * 校验用户名和验证码
     * @param username 前台输入的用户名
     * @param password 前台输入的密码
     * @return 查询的实体类
     * @throws IOException 异常
     */
    @Select("select * from t_user where username=#{username} and password=#{password}")
    User findByUsernameByPassword(String username,String password) throws IOException;

    /**
     * 通过id查询对应的用户名
     * @param id 要查询的id
     * @return 查询的实体类
     */
    @Select("select * from t_user where id=#{id}")
    User findById(Long id);

}
