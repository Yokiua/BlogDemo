package com.jia.blog.mapper;

import com.jia.blog.pojo.Type;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 管理分类的dao持久层
 */
@Repository
public interface TypeMapper extends Mapper<Type> {

    /*--------------------Insert-------------------*/
    /**
     * 新增一个分类
     * @param type 实体类
     * @return 影响的行数
     */
    @Insert("insert into t_type values(#{id},#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")/*增加这个注解插入记录后会返回自增长的id*/
    int saveType(Type type);

    /*--------------------Select-------------------*/
    /**
     * 查询单个分类
     * @param id 查询的id
     * @return 实体类
     */
    @Select("select * from t_type where id=#{id}")
    Type getType(Long id);

    /**
     * 查询所有分类
     * @return 存有实体类的集合
     */
    @Select("select * from t_type")
    List<Type> listAllType();

    /**
     * 根据name查询分类表
     * @param name 对应数据库的name字段
     * @return 实体类
     */
    @Select("select * from t_type where name=#{name}")
    Type getTypeByName(String name);

    /**
     * 查询分类对应的博客有多少个
     * @param id 通过ud
     * @return 影响的行数
     */
    @Select("SELECT COUNT(NAME) FROM t_blog b,t_type t WHERE t.id = #{id} AND b.type_id = t.id")
    int getTypeCountBlog(Long id);

    /*--------------------Update-------------------*/
    /**
     * 修改一个分类
     * @param tid 修改的分类id
     * @param type 实体类
     * @return 影响的行数
     */
    @Update("update t_type set name=#{type.name} where id=#{tid}")
    int updateType(Long tid,Type type);

    /*--------------------Delete-------------------*/
    /**
     * 删除一个分类
     * @param id 要删除的分类id
     */
    @Delete("delete from t_type where id=#{id}")
    int reomveType(Long id);

}
