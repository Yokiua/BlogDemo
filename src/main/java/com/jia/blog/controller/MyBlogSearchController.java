package com.jia.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jia.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

/**
 * 管理搜索功能处理器类
 */
@Controller
public class MyBlogSearchController {

    @Autowired
    private BlogService blogService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 跳转搜索页面
     * @return search
     */
    @GetMapping("/search")
    public String search(){
        return "search";
    }

    /**
     * 条件模糊查询,查询博客的标题和内容,并分页
     * @param query 查询的内容
     * @param pageNum 页码
     * @param pageSize 每页显示的个数
     * @return json
     */
    @GetMapping("/searchBlog")
    @ResponseBody
    public String searchBlog(String query,Integer pageNum,Integer pageSize) throws JsonProcessingException, UnsupportedEncodingException {
        query = new String(query.getBytes("ISO-8859-1"),"UTF-8"); //解决中文乱码问题
        if (pageSize == null || pageSize == 0){
            pageSize = 8; //默认显示8条
        }
        return objectMapper.writeValueAsString(blogService.search(query,pageNum,pageSize));
    }
}
