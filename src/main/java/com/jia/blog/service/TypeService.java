package com.jia.blog.service;

import com.jia.blog.pojo.Type;
import java.util.List;

/**
 * 分类相关的用户接口
 */
public interface TypeService {
    /*--------------------Insert-------------------*/
    /**
     * 新增
     * @param type 实体类
     * @return 影响的行数
     */
    int saveType(Type type);

    /*--------------------Select-------------------*/
    /**
     * 查询一个分类
     * @param id 查询的id
     * @return 实体类
     */
    Type getType(Long id);

    /**
     * 分页查询 分类
     * @return 实体类集合
     */
    List<Type> listAllType();

    /**
     * 根据name查询分类表
     * @param name 查询的name
     * @return 实体类
     */
    Type getTypeByName(String name);

    /*--------------------Update-------------------*/
    /**
     * 修改
     * @param id 修改的id
     * @param type 实体类
     * @return 影响的行数
     */
    int updateType(Long id,Type type);

    /*--------------------Delete-------------------*/
    /**
     * 根据id删除
     * @param id 删除的id
     */
    int reomveType(Long id);

}
