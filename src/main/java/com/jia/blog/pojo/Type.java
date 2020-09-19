package com.jia.blog.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 分类的实体类
 */
@Data
@Table(name = "t_type")
public class Type {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String name;

    @Transient
    private Integer count; //对应博客的数量

}

