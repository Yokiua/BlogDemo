package com.jia.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.jia.blog.pojo.Type;
import com.jia.blog.service.BlogService;
import com.jia.blog.service.TypeService;
import com.jia.blog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 管理Type页面 前台数据展示的处理器类
 */
@Controller  //将所有返回的数据，初始化为json
@RequestMapping("/MyBlog")
public class MyBlogTypeController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RedisUtil redisUtil;


    /*------------------页面跳转-------------------*/
    /**
     * 跳转分类详情
     * @return html
     */
    @GetMapping("/types")
    public String types(){
        return "types";
    }

    /*--------------------Select-------------------*/
    /**
     * 获取所有分类(使用分页插件统计分页的数量)  [Redis优化]
     * @return json
     * @throws JsonProcessingException json异常
     */
    @GetMapping("/type/typeListPage")
    @ResponseBody
    public String findAllTypePage() throws JsonProcessingException{
        Object allType = redisUtil.get("allTypes");
        if (allType == null){ typeService.listAllType();
            PageInfo<Type> info = new PageInfo<Type>(typeService.listAllType());
            String json = objectMapper.writeValueAsString(info);
            redisUtil.set("allTypes",json);
            return json;
        }
        return (String) allType;
    }

    /**
     * 通过分类的id，获取对应的博客集合(并实现分页功能)  [Redis优化]
     * @param id 分类(type)的id
     * @param pageNum 页码
     * @param pageSize 每页显示的个数
     * @return json
     * @throws JsonProcessingException json异常
     */
    @GetMapping("/type/getTypeAndBlog")
    @ResponseBody
    public String getTypeAndBlog(Long id,Integer pageNum,Integer pageSize) throws JsonProcessingException {
        if (pageNum == null || pageNum == 0) {
            pageNum = 1; //默认显示第一页
        }
        if (pageNum > 0) {
            if (id != null) {
                Object o = redisUtil.get("type_blog_" + id+"_"+pageNum);
                if (o == null) {
                    if (pageSize == null || pageSize == 0) {
                        pageSize = 8; //默认每页显示数量
                    }

                    String json = objectMapper.writeValueAsString(blogService.findByTypeId(pageNum, pageSize, id));
                    redisUtil.set("type_blog_" + id+"_"+pageNum,json);
                    return json;
                }
                return (String) o;
            }
        }
        return null;
    }
}
