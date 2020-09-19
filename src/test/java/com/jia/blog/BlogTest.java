package com.jia.blog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.jia.blog.mapper.BlogMapper;
import com.jia.blog.pojo.Blog;
import com.jia.blog.pojo.Tag;
import com.jia.blog.pojo.Type;
import com.jia.blog.service.BlogService;
import com.jia.blog.service.TagService;
import com.jia.blog.service.TypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApplication.class)
public class BlogTest {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private TypeService typeService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test //测试分页查询
    public void b1() throws JsonProcessingException {
        PageInfo<Blog> blogList = blogService.getBlogList(1, 6);
        String json = mapper.writeValueAsString(blogList);
        System.out.println(json);
        //System.out.println(blogList);


    }

    @Test
    public void t8() throws JsonProcessingException {
        Blog blog = new Blog();
        blog.setType_id(5L);
        PageInfo<Blog> info = blogService.findByBlogList(1, 6, blog);

        String json = mapper.writeValueAsString(info);
        System.out.println(json);
    }

    @Test
    public void t9(){
        List<Tag> tags = tagService.listByIdsTag("1,2,3");
        System.out.println(tags);
    }

    @Test
    public void t10(){
        Blog blog = new Blog();
        blog.setCommentabled(true);
        blog.setContent("爱你摸摸大");
        blog.setCreateTime(new Date());
        blog.setFirstPicture("首图地址");
        blog.setFlag("原创");
        blog.setPublished(true);
        blog.setRecommend(true);
        blog.setTitle("java学习");
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blog.setType_id(1L);
        blog.setUser_id(1L);

        blogMapper.saveBlog(blog);

    }

    @Test
    public void t11(){
        Blog blog = blogService.getBlog(15L);
        System.out.println(blog);
    }

    @Test
    public void t12(){
        List<Blog> allBlog = blogMapper.getAllBlog();
        for (Blog blog : allBlog) {
            System.out.println(blog);
        }
    }

    @Test
    public void t13() throws JsonProcessingException {
        List<Type> blog = typeService.listAllType();
        String s = mapper.writeValueAsString(blog);
        System.out.println(s);
    }

    @Test
    public void t14() throws JsonProcessingException{
        List<Type> types = typeService.listAllType();
        PageInfo<Type> info = new PageInfo<>(types);
        System.out.println(mapper.writeValueAsString(info));
    }

    @Test
    public void t15() throws JsonProcessingException {
        PageInfo<Blog> info = tagService.findByBlog_Tags_List(2L, 1, 8);
        System.out.println(mapper.writeValueAsString(info));
    }

    @Test
    public void t16(){
        List<Blog> java = blogMapper.search("java");
        for (Blog blog : java) {
            System.out.println(blog);
        }
    }

    @Test
    public void t17(){
        List<Blog> blogs = blogMapper.getAllBlog();
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
    }

    @Test
    public void t18(){
        blogService.updateBlogViews(26L,5);
    }
}
