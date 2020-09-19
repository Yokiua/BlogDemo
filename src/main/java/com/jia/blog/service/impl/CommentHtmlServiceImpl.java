package com.jia.blog.service.impl;

import com.jia.blog.mapper.CommentHtmlMapper;
import com.jia.blog.pojo.Comment;
import com.jia.blog.service.CommentHtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 留言区的用户接口实现类
 */
@Service
public class CommentHtmlServiceImpl implements CommentHtmlService {

    @Autowired
    private CommentHtmlMapper commentHtmlMapper;

    /*--------------------Select-------------------*/
    /**
     * 获取对应博客的评论(完整)
     * @return 实体类集合
     */
    @Override
    public List<Comment> getCommentHtml() {
        //1.首先查询所有的父评论
        List<Comment> commentsFather = this.getCommentHtmlSAndFather();
        for (Comment comment : commentsFather) {
            //2.通过父评论的id,查询对应的子评论
            List<Comment> parent = this.getParentCommentHtmlS(comment.getId());
            comment.setParentCommentS(parent);
        }
        return commentsFather;
    }

    /**
     * 查询所有的父评论
     * @return 实体类集合
     */
    @Override
    public List<Comment> getCommentHtmlSAndFather() {
        return commentHtmlMapper.getComment_htmlSAndFather();
    }

    /**
     * 查询父评论 对应的 子评论
     * @param parent_comment_id 父评论的id
     * @return 实体类集合
     */
    @Override
    public List<Comment> getParentCommentHtmlS(Long parent_comment_id) {
        return commentHtmlMapper.getParentCommentS_html(parent_comment_id);
    }

    /*--------------------Insert-------------------*/
    /**
     * 添加一条评论(回复)
     * @param comment 实体类
     * @return 影响的行数
     */
    @Override
    @Transactional
    public int saveCommentHtml(Comment comment) {
        comment.setCreate_time(new Date());
        return commentHtmlMapper.saveComment_html(comment);
    }

    /*--------------------Delete-------------------*/
    /**
     * 删除一条评论(若删除父评论，这也删除子评论)
     * @param comment 实体类
     * @return 影响的行数
     */
    @Override
    @Transactional
    public int removeCommentHtml(Comment comment) {
        return commentHtmlMapper.removeComment_html(comment);
    }
}
