package com.jia.blog.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jia.blog.pojo.Blog;
import com.jia.blog.pojo.Tag;
import com.jia.blog.pojo.Type;
import com.jia.blog.service.BlogService;
import com.jia.blog.service.TagService;
import com.jia.blog.service.TypeService;
import com.jia.blog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * 管理博客主页的处理器类,为前端提供数据和跳转页面 【Redis优化】
 */
@Controller
@RequestMapping("/MyBlog")
public class MyBlogIndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private RedisUtil redisUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();



    /*--------------------Select-------------------*/
    /**
     * 获取所有的博客,并分页  [redis优化]
     * @return json
     */
    @GetMapping("/index/blogList")
    @ResponseBody
    public String findAllBlog(Integer pageNum , Integer pageSize) throws JsonProcessingException {
        if (pageNum != null && pageNum > 0){
            Object allBlogs = redisUtil.get("allBlogs_"+pageNum);
            if (allBlogs == null) {
                if (pageSize == null || pageSize == 0) {
                    pageSize = 8; //默认一页显示8张博客
                }
                String json = objectMapper.writeValueAsString(blogService.getBlogsByPublished(pageNum, pageSize));
                redisUtil.set("allBlogs_"+pageNum,json);
                return json;
            }
            return (String) allBlogs;
        }
        return null;
    }

    /**
     * 获取所有的分类(附带对应博客的数量) [Redis优化]
     * @return json
     * @throws JsonProcessingException json异常
     */
    @GetMapping("/index/typeList")
    @ResponseBody
    public String findAllType() throws JsonProcessingException {
        Object allTypes = redisUtil.get("allTypes");
        if (allTypes == null){
            String json = objectMapper.writeValueAsString(new PageInfo<Type>(typeService.listAllType()));
            redisUtil.set("allTypes",json);
            return json;
        }
        return (String) allTypes;
    }

    /**
     * 获取所有的标签(附带对应博客的数量) [Redis优化]
     * @return json
     * @throws JsonProcessingException json异常
     */
    @GetMapping("/index/tagList")
    @ResponseBody
    public String findAllTag() throws JsonProcessingException {
        Object allTags = redisUtil.get("allTags");
        if (allTags == null){
            String json = objectMapper.writeValueAsString(new PageInfo<Tag>(tagService.findByBlog_Tags_Count()));
            redisUtil.set("allTags",json);
            return json;
        }
        return (String) allTags;
    }

    /**
     * 获取推荐的博客，并按照时间倒叙排序(最新在上) [Redis优化]
     * @return json
     * @throws JsonProcessingException json异常
     */
    @GetMapping("/index/sortDateBlog")
    @ResponseBody
    public String allBlogSortDate(Integer pageSize) throws JsonProcessingException {
        /*网页底部的最新推荐(显示3条)*/
        if (pageSize !=null && pageSize == 3){
            Object newBlogs_3 = redisUtil.get("newBlogs_3");
            if (newBlogs_3 == null){
                PageHelper.startPage(1,pageSize); //默认分页显示5条最新博客
                List<Blog> blogs = blogService.AllBlogSortDate();
                String json = objectMapper.writeValueAsString(new PageInfo<Blog>(blogs));
                redisUtil.set("newBlogs_3",json);
                return json;
            }
            return (String) newBlogs_3;
        }

        /* 网页右边的最新推荐*/
        Object newBlogs = redisUtil.get("newBlogs");
        if (newBlogs == null){
            if (pageSize == null){
                pageSize = 5;
            }
            PageHelper.startPage(1,pageSize); //默认分页显示5条最新博客
            List<Blog> blogs = blogService.AllBlogSortDate();
            String json = objectMapper.writeValueAsString(new PageInfo<Blog>(blogs));
            redisUtil.set("newBlogs",json);
            return json;
        }
        return (String) newBlogs;
    }

}
