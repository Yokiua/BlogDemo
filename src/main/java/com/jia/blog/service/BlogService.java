package com.jia.blog.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageInfo;
import com.jia.blog.pojo.Blog;
import java.util.List;


/**
 * 管理Blog的用户层
 */
public interface BlogService {

    /*--------------------Select-------------------*/
    /**
     * 根据id查询
     * @param id 要查询的id
     * @return 查询的实体类
     */
    Blog getBlog(Long id);

    /**
     * 获取一个博客,并把内容转换成html(只用于前端展示)
     * @param id 要查询的id
     * @return 查询的实体类(序列化为json)
     */
    Blog getBlogConvert(Long id) throws JsonProcessingException;

    /**
     * 获取所有发布博客的数量
     * @return 博客数量
     */
    Integer getBlogCount();

    /**
     * 根据用户查询的方法,只用于前台渲染
     * @param id 查询的id
     * @return 查询到的实体类
     */
    Blog getByIdBlog(Long id);

    /**
     * 分页查询(分页显示)
     * @param pageNum 页码
     * @param pageSize 每页显示的个数
     * @return 分页插件封装的集合
     */
    PageInfo<Blog> getBlogList(int pageNum,int pageSize);

    /**
     * 根据条件进行查询
     * @param pageNum 页码
     * @param pageSize 每页显示的个数
     * @param blog 实体类
     * @return 分页插件封装的集合
     */
    PageInfo<Blog> findByBlogList(int pageNum,int pageSize,Blog blog);

    /**
     * 获取发布的博客
     * @return 分页插件封装的集合
     */
    PageInfo<Blog> getBlogsByPublished(int pageNum,int pageSize);

    /**
     * 根据分类id,查询对应的博客(并实现分页功能)
     * @param pageNum 页码
     * @param pageSize 每页显示的个数
     * @param id 要查询的分类id
     * @return 分页插件封装的集合
     */
    PageInfo<Blog> findByTypeId(int pageNum,int pageSize,Long id);

    /**
     * 获取推荐的博客，并按照时间倒叙排序(最新在上)
     * @return 实体类集合
     */
    List<Blog> AllBlogSortDate();

    /**
     * 条件查询(查询标题和内容)
     * @param search 查询的内容
     * @param pageNum 页码
     * @param pageSize 每页显示的个数
     * @return 分页插件封装的集合
     */
    PageInfo<Blog> search(String search,Integer pageNum,Integer pageSize);

    /**
     * 根据创建年份查询博客(倒序)
     * @param dataYY 查询的年份
     * @return 实体类集合
     */
    List<Blog> archives(String dataYY);

    /**
     * 获取博客的所有访问量
     * @return 访问量
     */
    Integer findBlogViews();

    /*--------------------Update-------------------*/
    /**
     * 更新博客点击的数量
     * @param id 要更新的id
     * @param views 点击数
     * @return 影响的行数
     */
    int updateBlogViews(Long id,Integer views);

    /*--------------------Insert-------------------*/
    /**
     * 新增一个blog
     * @param blog 实体类
     * @return 影响的行数
     */
    int saveBlog(Blog blog);

    /*--------------------Update-------------------*/
    /**
     * 修改blog
     * @param blog 要修改的id
     * @return 影响的行数
     */
    int updateBlog(Blog blog) throws JsonProcessingException;

    /*--------------------Delete-------------------*/
    /**
     * 删除blog
     * @param id 要删除的id
     * @return 影响的行数
     */
    int removeBlog(Long id);


}
