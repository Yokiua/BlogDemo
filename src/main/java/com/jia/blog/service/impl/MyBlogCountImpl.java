package com.jia.blog.service.impl;

import com.jia.blog.mapper.MyBlogCountMapper;
import com.jia.blog.service.MyBlogCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyBlogCountImpl implements MyBlogCount {

    @Autowired
    private MyBlogCountMapper myBlogCountMapper;
    /**
     * 更新网站的访问量
     * @param count 访问量
     */
    @Override
    @Transactional
    public void updateCount(Long count) {
        myBlogCountMapper.updateCount(count);
    }

    /**
     * 获取网站的访问量
     * @return 访问量
     */
    @Override
    public Long getMyBlogCount() {
        return myBlogCountMapper.getMyBlogCount();
    }
}
