package com.jia.blog.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客实体类
 */
@Data
@Table(name = "t_blog")
public class Blog {

    @Id
    @KeySql(useGeneratedKeys = true)//主键回填
    private Long id; //对应的主键

    private String title; //标题

    /*//大字段类型(默认string映射数据库的是255长度)
    @Basic(fetch = FetchType.LAZY)
    @Lob*/
    private String content; //内容
    private String firstPicture; //首图
    private String flag; //标记
    private Integer views; //浏览次数
    private boolean commentabled; //是否开启评论
    private boolean published; //是否发布
    private boolean recommend; //是否推荐
    /**更新时间  用户可以点击更新，保存最新更新的时间。**/
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createTime; //创建时间
    /**更新时间  用户可以点击更新，保存最新更新的时间。**/
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date updateTime; //更新时间
    private Long type_id; //type(分类表的外键)
    private Long user_id; //user(用户表的外键)
    private String tagIds; //对应标签的id(1,2,3这种)
    //博客描述
    private String description;

    //分类
    @Transient  //标记不是数据库的字段
    private Type type;

    //所属用户
    @Transient
    private User user;

    //标签
    @Transient
    private List<Tag> tags = new ArrayList<>();

    //评论
    @Transient
    private List<Comment> comments = new ArrayList<Comment>();

    //分类名(只用于前台展示)
    @Transient
    private String typeName;
    //用户昵称(只用于前台展示)
    @Transient
    private String userNickname;
    //用户头像地址(只用于前台展示)
    @Transient
    private String userAvatar;
    //博客对应的标签名(只用于前台展示)
    @Transient
    private List<String> tagName;
}