package com.jia.blog.mapper;

import com.jia.blog.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentHtmlMapper {
    /**
     * 获取所有父评论 [留言区]
     * @return List<Comment>
     */
    @Select("SELECT * FROM t_comment_html WHERE parent_comment_id IS NULL")
    List<Comment> getComment_htmlSAndFather();

    /**
     * 查询父评论 的子评论(最早回复的在前) [留言区]
     * @param parent_comment_id 父评论的id
     * @return List<Comment>
     */
    @Select("SELECT * FROM t_comment_html WHERE parent_comment_id = #{parent_comment_id} ORDER BY create_time")
    List<Comment> getParentCommentS_html(Long parent_comment_id);

    /**
     * 添加一条评论(前台会提交 parent_comment_id 的，若是父评论,就null) [操作留言区的表]
     * @param comment 实体类
     * @return 影响的行数 int
     */
    @Insert("INSERT INTO " +
            "t_comment_html(content,create_time,email,nickname,parent_comment_id,ReplyToUser,flag) " +
            "VALUES(#{content},#{create_time},#{email},#{nickname},#{parent_comment_id},#{ReplyToUser},#{flag})")
    int saveComment_html(Comment comment);

    /**
     * 删除一条评论(删父评论就包括子评论) [留言区]
     * @param comment 对应的数据
     * @return 返回是否成功
     */
    int removeComment_html(Comment comment);
}
