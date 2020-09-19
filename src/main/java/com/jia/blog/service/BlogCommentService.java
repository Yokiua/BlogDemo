package com.jia.blog.service;

import com.jia.blog.pojo.Comment;
import java.util.List;

/**
 * 管理评论功能的用户接口
 */
public interface BlogCommentService {

    /*--------------------Select-------------------*/
    /**
     * 获取博客对应的所有的父评论
     * @return 实体类集合
     */
    List<Comment> getCommentSAndFather(Long blog_id);

    /**
     * 获取对应博客的评论(完整)
     * @param blogId 对应博客的id
     * @return 实体类集合
     */
    List<Comment> getComment(Long blogId);

    /**
     * 查询父评论 的评论
     * @param parent_comment_id 父评论的id
     * @return 实体类集合
     */
    List<Comment> getParentCommentS(Long parent_comment_id);

    /*--------------------Insert-------------------*/
    /**
     * 添加一条评论
     * @param comment 评论实体类
     * @return 影响的行数
     */
    int saveComment(Comment comment);

    /*--------------------Delete-------------------*/
    /**
     * 删除一条评论(若删除父评论，这也删除子评论)
     * @param comment 实体类(里面包含父、子评论的id和子评论对应的父评论id)
     * @return 影响的行数
     */
    int removeComment(Comment comment);
}
