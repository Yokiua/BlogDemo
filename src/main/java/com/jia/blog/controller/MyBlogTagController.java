package com.jia.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.jia.blog.pojo.Tag;
import com.jia.blog.service.TagService;
import com.jia.blog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 管理Tag页面 前台数据展示的处理器类
 */
@Controller
@RequestMapping("/MyBlog")
public class MyBlogTagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private RedisUtil redisUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /*--------------------跳转页面-------------------*/
    /**
     * 跳转标签详情
     * @return html
     */
    @GetMapping("/tags")
    public String tags(){
        return "tags";
    }

    /*--------------------Select-------------------*/
    /**
     * 获取全部的标签(并附带对应博客的数量) [Redis优化]
     * @return json
     */
    @GetMapping("/tags/getTags")
    @ResponseBody
    public String getTags() throws JsonProcessingException {
        Object allTags = redisUtil.get("allTags");
        if (allTags == null){
            PageInfo<Tag> info = new PageInfo<Tag>(tagService.findByBlog_Tags_Count());
            String json = objectMapper.writeValueAsString(info);
            redisUtil.set("allTags",json);
            return json;
        }
        return (String) allTags;
    }

    /**
     * 根据标签的id,查询对应的博客,并实现分页功能(集合) [Redis优化]
     * @return json
     */
    @GetMapping("/tags/getTagAndBlog")
    @ResponseBody
    public String getTagAndBlog(Long id,Integer pageNum,Integer pageSize) throws JsonProcessingException {
        if (pageNum == null || pageNum == 0) {
            pageNum = 1; //默认显示第一页
        }
        if (pageNum > 0) {
            if (id != null) {
                Object o = redisUtil.get("tag_blog_" + id+"_"+pageNum);
                if (o == null) {
                    if (pageSize == null || pageSize == 0) {
                        pageSize = 8;
                    }
                    String json = objectMapper.writeValueAsString(tagService.findByBlog_Tags_List(id, pageNum, pageSize));
                    redisUtil.set("tag_blog_" +id+"_"+pageNum, json);
                    return json;
                }
                return (String) o;
            }
        }
        return null;
    }

}
