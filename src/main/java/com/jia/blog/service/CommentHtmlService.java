package com.jia.blog.service;
import com.jia.blog.pojo.Comment;
import java.util.List;

/**
 * 留言区对应的用户层接口
 */
public interface CommentHtmlService {


    /*--------------------Select-------------------*/
    /** [留言区]
     * 获取博客对应的所有的父评论
     * @return 实体类集合
     */
    List<Comment> getCommentHtmlSAndFather();

    /** [留言区]
     * 获取留言区的评论(完整)
     * @return 实体类集合
     */
    List<Comment> getCommentHtml();

    /** [留言区]
     * 查询父评论 的子评论(最早回复的在前)
     * @param parent_comment_id 父评论的id
     * @return 实体类集合
     */
    List<Comment> getParentCommentHtmlS(Long parent_comment_id);

    /*--------------------Insert-------------------*/
    /** [留言区]
     * 添加一条评论
     * @param comment 评论实体类
     * @return 影响的行数
     */
    int saveCommentHtml(Comment comment);

    /*--------------------Delete-------------------*/
    /** [留言区]
     * 删除一条评论(若删除父评论，这也删除子评论)
     * @param comment 实体类(里面包含父、子评论的id和子评论对应的父评论id)
     * @return 影响的行数
     */
    int removeCommentHtml(Comment comment);

}
