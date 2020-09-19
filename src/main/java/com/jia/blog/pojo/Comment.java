package com.jia.blog.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 评论实体类
 */
@Data
@Table(name = "t_comment")
public class Comment {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String nickname; //昵称
    private String email; //邮箱
    private String content; //评论内容
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date create_time; //评论时间
    private Long blog_id; //管理博客表的外键
    private Long parent_comment_id; //自关联关系表(1个comment表下可以有多个comment)
    private String ReplyToUser; //回复的用户名
    private Boolean flag; //是否是博主
    @Transient //不是数据库的字段
    private Blog blog;
    @Transient
    private List<Comment> parentCommentS = new ArrayList<Comment>(); //自关联的实体
}
