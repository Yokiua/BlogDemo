package com.jia.blog.service;

/**
 * 统计网站的访问量
 */
public interface MyBlogCount {

    /**
     * 更新网站访问量
     * @param count 访问量
     */
    void updateCount(Long count);

    /**
     * 获取网站访问量
     * @return 访问量
     */
    Long getMyBlogCount();
}
