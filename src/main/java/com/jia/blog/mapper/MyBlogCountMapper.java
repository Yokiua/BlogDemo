package com.jia.blog.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 记录网站访问量(记录首页)
 */
@Repository
public interface MyBlogCountMapper {

    /**
     * 更新网站访问量
     * @param count 数量
     */
    @Update("update t_count set count=#{count} where id = 1")
    void updateCount(Long count);

    /**
     * 获取网站的访问量
     * @return 访问量
     */
    @Select("SELECT COUNT FROM t_count WHERE id = 1")
    Long getMyBlogCount();
}
