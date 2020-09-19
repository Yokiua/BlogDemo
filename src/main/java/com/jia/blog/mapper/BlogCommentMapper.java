package com.jia.blog.mapper;

import com.jia.blog.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论功能的持久层
 */
@Repository
public interface BlogCommentMapper {

    /*--------------------Select-------------------*/
    /**
     * 获取博客对应的所有父评论
     * @return List<Comment>
     */
    @Select("SELECT * FROM t_comment WHERE parent_comment_id IS NULL and blog_id = #{blog_id}")
    List<Comment> getCommentSAndFather(Long blog_id);


    /**
     * 查询父评论 的子评论(最早回复的在前)
     * @param parent_comment_id 父评论的id
     * @return List<Comment>
     */
    @Select("SELECT * FROM t_comment WHERE parent_comment_id = #{parent_comment_id} ORDER BY create_time")
    List<Comment> getParentCommentS(Long parent_comment_id);


    /*--------------------Insert-------------------*/
    /**
     * 添加一条评论(前台会提交 parent_comment_id 的，若是父评论,就null)
     * @param comment 实体类
     * @return 影响的行数 int
     */
    @Insert("INSERT INTO " +
            "t_comment(content,create_time,email,nickname,blog_id,parent_comment_id,ReplyToUser,flag) " +
            "VALUES(#{content},#{create_time},#{email},#{nickname},#{blog_id},#{parent_comment_id},#{ReplyToUser},#{flag})")
    int saveComment(Comment comment);


    /*--------------------Delete-------------------*/
    /**
     * 删除一条评论(删父评论就包括子评论)
     * @param comment 对应的数据
     * @return 返回是否成功
     */
    int removeComment(Comment comment); //sql语句被xml配置

}
