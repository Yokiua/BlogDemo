package com.jia.blog.mapper;

import com.jia.blog.pojo.Tag;
import com.jia.blog.pojo.t_blog_tags;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

/**
 * 管理标签的dao持久层
 */
@Repository
public interface TagMapper extends Mapper<Tag> {

    /*--------------------Insert-------------------*/
    /**
     * 新增一个标签
     * @param tag 实体类
     * @return 影响行数
     */
    @Insert("insert into t_tag values(#{id},#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")/*增加这个注解插入记录后会返回自增长的id*/
    int saveTag(Tag tag);

    /*--------------------Select-------------------*/
    /**
     * 查询 一个标签
     * @param id 查询标签的id
     * @return Tag实体类
     */
    @Select("select * from t_tag where id=#{id}")
    Tag getTag(Long id);

    /**
     * 分页查询 (所有)标签
     * @return List<Tag>
     */
    @Select("select * from t_tag")
    List<Tag> listAllTag();

    /**
     * 根据name查询标签表
     * @param name 查询的name
     * @return 返回实体类
     */
    @Select("select * from t_tag where name=#{name}")
    Tag getTagByName(String name);

    /**
     * 查询标签对应的博客有多少个
     * @param id 标签id
     * @return 个数
     */
    //@Select("SELECT COUNT(NAME) FROM t_blog b,t_tag t WHERE t.id = #{id} AND b.type_id = t.id")
    //int getTagCountBlog(Long id);

    /**
     * 查询中间表,获取每个标签对应的博客个数
     * @param id 标签的id
     * @return 个数
     */
    @Select("SELECT COUNT(*) FROM t_blog_tags WHERE tagsId=#{id}")
    Integer findByBlog_Tags_Count(Long id);

    /**
     * 查询中间表,获得数据的实体类(分别为blog的id和tag的id，多对一)
     * @param id 标签id
     * @return 返回泛型为中间表的集合
     */
    @Select("SELECT * FROM t_blog_tags WHERE tagsId=#{id}")
    List<t_blog_tags> findByBlog_Tags_List(Long id);

    /*--------------------Update-------------------*/
    /**
     * 修改一个标签
     * @param tid 修改的标签id
     * @param tag 实体类
     * @return 影响行数 int
     */
    @Update("update t_tag set name=#{tag.name} where id=#{tid}")
    int updateTag(Long tid,Tag tag);

    /*--------------------Delete-------------------*/
    /**
     * 删除一个标签
     * @param id 要删除的标签id
     */
    @Delete("delete from t_tag where id=#{id}")
    int reomveTag(Long id);
}
