package com.jia.blog.controller;
import com.jia.blog.service.MyBlogCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 只用于首页跳转
 */
@Controller
public class index {

    @Autowired
    private MyBlogCount myBlogCount;

    /*--------------------页面跳转-------------------*/
    /**
     * 跳转首页
     * @return index
     */
    @GetMapping(path = {"/",""})
    public String index_(){
        //每访问一次网页,增加一次网站访问量(只访问首页)
        Long count = myBlogCount.getMyBlogCount();
        Long c = count + 1L;
        myBlogCount.updateCount(c);

        return "index";
    }
}
