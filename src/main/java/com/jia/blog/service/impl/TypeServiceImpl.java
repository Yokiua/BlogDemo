package com.jia.blog.service.impl;

import com.jia.blog.excetion.NotFondExcetion;
import com.jia.blog.mapper.TypeMapper;
import com.jia.blog.pojo.Type;
import com.jia.blog.service.TypeService;
import com.jia.blog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 管理分类的用户实现类
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;


    /*--------------------Select-------------------*/
    /**
     * 通过id查询
     * @param id 查询的id
     * @return 实体类
     */
    @Override
    public Type getType(Long id) {
        return typeMapper.getType(id);
    }

    /**
     * 查询所有的分类(包括分类对应的博客数量)
     * @return 实体类集合
     */
    @Override
    public List<Type> listAllType() {
        List<Type> types = typeMapper.listAllType();
        for (Type type : types) {
            int countBlog = typeMapper.getTypeCountBlog(type.getId());
            type.setCount(countBlog);
        }
        return types;
    }

    /**
     * 根据name查询数据库
     * @param name 查询的name
     * @return 实体类
     */
    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }

    /*--------------------Insert-------------------*/
    /**
     * 添加一条分类
     * @param type 实体类
     * @return 影响的行数
     */
    @Transactional
    @Override
    public int saveType(Type type) {
        int i = typeMapper.saveType(type);
        if (i == 0){
            return 0;
        }
        return Math.toIntExact(type.getId()); //添加后的id
    }

    /*--------------------Update-------------------*/
    /**
     * 修改分类
     * @param id 修改的id
     * @param type 实体类
     * @return 影响的行数
     */
    @Transactional
    @Override
    public int updateType(Long id, Type type){
        if (typeMapper.getType(id) == null){
            throw new NotFondExcetion("不存在该类型");

        }
        return typeMapper.updateType(id, type);
    }

    /*--------------------Delete-------------------*/
    /**
     * 删除分类
     * @param id 删除的id
     * @return 影响的行数
     */
    @Transactional
    @Override
    public int reomveType(Long id) {
        return typeMapper.reomveType(id);
    }












}
