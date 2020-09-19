package com.jia.blog.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签类
 */
@Data
@Table(name = "t_tag")
public class Tag {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;

    @Transient
    private Integer count;

}
