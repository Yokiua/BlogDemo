package com.jia.blog.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jia.blog.service.BlogService;
import com.jia.blog.service.MyBlogCount;
import com.jia.blog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 管理归档相关的前端数据 【Redis优化】
 */
@Controller
@RequestMapping("/MyBlog")
public class MyBlogArchivesController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private RedisUtil redisUtil;

    private final ObjectMapper jsonMapper = new ObjectMapper();
    /*--------------------跳转html页面-------------------*/
    /**
     * 跳转归档页面
     * @return html
     */
    @GetMapping("/archives")
    public String archives(){
        return "archives";
    }

    /*--------------------Select-------------------*/
    /**
     * 根据创建年份，查询博客，并倒序(最新的在前)
     * @param dataYY 创建的年
     * @return json
     */
    @GetMapping("/archives/findDataYY")
    @ResponseBody
    public String archivesData(String dataYY) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(blogService.archives(dataYY));
    }

    /**
     * 获取所有发布博客的数量 [Redis优化]
     * @return json
     */
    @GetMapping("/archives/BlogCount")
    @ResponseBody
    public Integer AllBlogCount(){
        Object blogCount = redisUtil.get("blogCount");
        if (blogCount==null){
            Integer bc = blogService.getBlogCount();
            redisUtil.set("blogCount",bc);
            return bc;
        }
        return (Integer) blogCount;
    }



}
