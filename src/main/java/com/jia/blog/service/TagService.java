package com.jia.blog.service;

import com.github.pagehelper.PageInfo;
import com.jia.blog.pojo.Blog;
import com.jia.blog.pojo.Tag;
import java.util.List;

/**
 * 标签相关的用户接口
 */
public interface TagService {

    /*--------------------Insert-------------------*/
    /**
     * 新增
     * @param tag 实体类
     * @return 影响的行数
     */
    int saveTag(Tag tag);

    /*--------------------Select-------------------*/
    /**
     * 查询
     * @param id 查询的id
     * @return 查询到的实体类
     */
    Tag getTag(Long id);

    /**
     * 分页查询
     * @return 实体类集合
     */
    List<Tag> listAllTag();

    /**
     * 根据name查询数据库
     * @param name 要查询的name
     * @return 实体类
     */
    Tag getTagByName(String name);

    /**
     * 根据前台提交的value值(1,2,3) 查询对应的表
     * @param ids 标签的id列表 "1,2,3..."
     * @return 查询到的实体类集合
     */
    List<Tag> listByIdsTag(String ids);

    /**
     * 查询标签 对应的博客个数
     * @return 实体类集合
     */
    List<Tag> findByBlog_Tags_Count();

    /**
     * 查询中间表,获得数据的实体类(分别为blog的id和tag的id，多对一)
     * @param id 要查询的id
     * @return 分页插件封装的集合
     */
    PageInfo<Blog> findByBlog_Tags_List(Long id, Integer pageNum, Integer pageSize);

    /*--------------------Update-------------------*/
    /**
     * 修改
     * @param id 要修改的id
     * @param tag 实体类
     * @return 影响的行数
     */
    int updateTag(Long id,Tag tag);

    /*--------------------Delete-------------------*/
    /**
     * 根据id删除
     * @param id 要删除的id
     */
    int reomveTag(Long id);
}