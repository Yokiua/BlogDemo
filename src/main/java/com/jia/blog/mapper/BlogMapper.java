package com.jia.blog.mapper;
import com.jia.blog.pojo.Blog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 操作blog表的dao层
 */
@Repository
public interface BlogMapper extends Mapper<Blog> {

    /*--------------------Select-------------------*/
    /**
     * 根据id查询
     * @param id (在xml中配置)
     * @return Blog实体类
     */
    Blog getBlog(Long id);

    /**
     * 查询所有发布博客的数量
     * @return 博客输入
     */
    @Select("SELECT COUNT(id) FROM t_blog WHERE published = 1")
    Integer getBlogCount();

    /**
     * 查询数据库中的全部数据(包括对应的分类表数据)
     * @return 该方法被mybatis的xml所配置
     */
    List<Blog> getAllBlog();

    /**
     * 复杂条件查询(这个方法有xml配置文件)
     * @param blog 标题(title) 分类(type_id) 推荐(recommend)
     * @return 集合<Blog实体类>
     */
    List<Blog> getBlogAndQuery(Blog blog);

    /**
     * 通过分类的id,查询对应的博客
     * @param id 分类id
     * @return 集合<Blog实体类>
     */
    @Select("select * from t_blog where type_id=#{id}")
    List<Blog> findByTypeId(Long id);

    /**
     * 获取推荐的博客，并按照时间倒叙排序(最新在上)
     * @return 集合<Blog实体类>
     */
    @Select("SELECT * FROM t_blog WHERE recommend=1 AND published=1 ORDER BY updateTime DESC ")
    List<Blog> AllBlogSortDate();

    /**
     * 条件模糊查询(查询标题和内容)
     * @param query 条件
     * @return 集合<Blog实体类>
     */
    @Select("SELECT * FROM t_blog WHERE title LIKE '%'#{query}'%' OR content LIKE concat('%',#{query},'%')")
    List<Blog> search(String query);

    /**
     * 根据创建年份查询(倒序)
     * @param dataYY 年份
     * @return 集合<Blog实体类>
     */
    @Select("SELECT * FROM t_blog WHERE YEAR(createTime)=#{dataYY} ORDER BY createTime DESC")
    List<Blog> archives(String dataYY);

    /**
     * 获取所有发布的博客
     * @return  已在xml中配置
     */
    List<Blog> getBlogByPublished();

    /**
     * 获取博客的所有访问量
     * @return 访问量
     */
    @Select("SELECT SUM(views) FROM t_blog")
    Integer findBlogViews();

    /*--------------------Update-------------------*/
    /**
     * 更新博客点击数
     * @return 影响的行数 int
     */
    @Update("update t_blog set views=#{views} where id = #{id}")
    int updateBlogViews(Long id,Integer views);

    /*--------------------Insert-------------------*/
    /**
     * 新增一个blog
     * @param blog 实体类数据
     * @return 影响行数 int
     */
    @Insert("INSERT INTO t_blog " +
    " VALUES(#{id},#{commentabled},#{content},#{createTime},#{firstPicture},#{flag},#{published},#{recommend}," +
            "#{title},#{updateTime},#{views},#{type_id},#{user_id},#{tagIds},#{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id") //id回填
    int saveBlog(Blog blog);

    /**
     * 新增博客后，把博客的id和对应的标签id添加到中间表
     * @return 影响行数 int
     */
    @Insert("insert into t_blog_tags values(#{id},#{tagId})")
    int saveBlog_Tag(Long id , Long tagId);

    /*--------------------Update-------------------*/
    /**
     * 修改blog
     * @param blog 实体类数据
     * @return 影响行数int
     */
    @Update("UPDATE t_blog  SET " +
    "title = #{title},content = #{content},firstPicture = #{firstPicture},flag = #{flag},updateTime = #{updateTime},type_id = #{type_id},tagIds = #{tagIds},published=#{published}, " +
    "recommend=#{recommend},commentabled=#{commentabled},description=#{description}" +
    " WHERE id = #{id}")
    int updateBlog(Blog blog);

    /*--------------------Delete-------------------*/
    /**
     * 删除blog
     * @param id 要删除博客的id
     * @return 影响行数 int
     */
    @Delete("delete from t_blog where id=#{id}")
    int removeBlog(Long id);
}
