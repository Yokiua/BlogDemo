package com.jia.blog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jia.blog.mapper.BlogMapper;
import com.jia.blog.mapper.TypeMapper;
import com.jia.blog.pojo.Blog;
import com.jia.blog.pojo.Tag;
import com.jia.blog.pojo.Type;
import com.jia.blog.pojo.User;
import com.jia.blog.service.TagService;
import com.jia.blog.service.TypeService;
import com.jia.blog.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApplication.class)
class BlogApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogMapper blogMapper;

    @Test
    void contextLoads() throws IOException {
        User user = userService.checkUser("yokiua", "123456");
        System.out.println(user);
    }

    @Test //md5加密
    void md5(){
        String md5 = DigestUtils.md5DigestAsHex("617513".getBytes());
        System.out.println(md5);
    }

    //测试分页插件
    @Test
    void page(){
        PageHelper.startPage(1,3);
        List<Type> types = typeMapper.listAllType();

        for (Type type : types) {
            System.out.println(type);
        }
    }

    //测试id返回
    @Test
    public void ttt(){
        Type type = new Type();
        type.setName("哦哦哦");
        typeService.updateType(2L,type);
    }

    @Test
    public void t2(){
        Type type = new Type();
        type.setName("游戏");
        typeMapper.saveType(type);
        System.out.println(type.getId());
    }

    @Test
    public void t3() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        PageHelper.startPage(1,6);
        List<Type> typeList = typeService.listAllType();
        PageInfo<Type> info = new PageInfo<Type>(typeList);


        String s = mapper.writeValueAsString(info);
        System.out.println(s);

    }

    @Test
    public void ttk(){
        Type type = new Type();
        type.setName("资源");
        typeMapper.updateType(11L,type);
    }

    @Test
    public void ttttt(){
        Tag tag = new Tag();
        tag.setName("迪亚波罗");
        tagService.updateTag(9L,tag);
    }

    /**
     * 测试复杂条件组合查询
     */
    @Test
    public void t66(){
        Blog blog = new Blog();
        //blog.setTitle("java学习");

        //blog.setType_id(5L);

        //blog.setRecommend(true);

        List<Blog> blogs = blogMapper.getBlogAndQuery(blog);

        System.out.println(blogs);
    }

    @Test
    public void t7() throws JsonProcessingException {
        List<Type> types = typeService.listAllType();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(types);
        System.out.println(json);
    }

    @Test
    public void t8(){
        List<Tag> tags = tagService.listByIdsTag("1,2,3");

        for (Tag tag : tags) {
            System.out.println(tag);
        }
    }


    @Test
    public void t9(){
        int i = typeService.reomveType(11L);
        System.out.println(i);
    }


}
