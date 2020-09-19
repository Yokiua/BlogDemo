package com.jia.blog.pojo;

import lombok.Data;

import javax.persistence.Table;

/**
 * 博客和标签的中间表(多对一)
 */
@Data
@Table(name = "t_blog_tags")
public class t_blog_tags {
    private Long blogsId;
    private Long tagsId;
}
