package com.jia.blog.controller;

import com.jia.blog.service.MyBlogCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/MyBlog")
public class MyBlogAboutController {

    @Autowired
    private MyBlogCount myBlogCount;

    /**
     * 跳转关于我页面
     * @return html
     */
    @GetMapping("/about")
    public String about(){
        return "about";
    }

    /**
     * 获取网站的访问量
     * @return 访问量
     */
    @GetMapping("/about/myBlogCount")
    @ResponseBody
    public Long getMyBlogCount(){
        return myBlogCount.getMyBlogCount();
    }
}
