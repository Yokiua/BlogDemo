package com.jia.blog.service.impl;

import com.jia.blog.mapper.BlogCommentMapper;
import com.jia.blog.pojo.Comment;
import com.jia.blog.service.BlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

/**
 * 博客留言功能 的 用户接口实现类
 */
@Service
public class BlogCommentServiceImpl implements BlogCommentService {

    @Autowired
    private BlogCommentMapper blogCommentMapper;

    /*--------------------Select-------------------*/
    /**
     * 获取对应博客的评论(完整)
     * @param blogId 博客id
     * @return 实体类集合
     */
    @Override
    public List<Comment> getComment(Long blogId) {
        //1.首先查询所有的父评论
        List<Comment> commentsFather = this.getCommentSAndFather(blogId);
        for (Comment comment : commentsFather) {
            //2.通过父评论的id,查询对应的子评论
            List<Comment> parent = this.getParentCommentS(comment.getId());
            comment.setParentCommentS(parent);
        }
        return commentsFather;
    }

    /**
     * 查询所有的父评论
     * @return 实体类集合
     */
    @Override
    public List<Comment> getCommentSAndFather(Long blog_id) {
        return blogCommentMapper.getCommentSAndFather(blog_id);
    }

    /**
     * 查询父评论 对应的 子评论
     * @param parent_comment_id 父评论的id
     * @return 实体类集合
     */
    @Override
    public List<Comment> getParentCommentS(Long parent_comment_id) {
        return blogCommentMapper.getParentCommentS(parent_comment_id);
    }


    /*--------------------Insert-------------------*/
    /**
     * 添加一条评论(回复)
     * @param comment 实体类
     * @return 影响的行数
     */
    @Override
    @Transactional
    public int saveComment(Comment comment) {
        comment.setCreate_time(new Date());
        return blogCommentMapper.saveComment(comment);
    }

    /*--------------------Delete-------------------*/
    /**
     * 删除一条评论(若删除父评论，这也删除子评论)
     * @param comment 实体类
     * @return 影响的行数
     */
    @Override
    @Transactional
    public int removeComment(Comment comment) {
        return blogCommentMapper.removeComment(comment);
    }

}
