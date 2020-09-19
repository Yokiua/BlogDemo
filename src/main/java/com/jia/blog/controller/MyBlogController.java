package com.jia.blog.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.jia.blog.pojo.Blog;
import com.jia.blog.pojo.Comment;
import com.jia.blog.pojo.User;
import com.jia.blog.service.BlogService;
import com.jia.blog.service.BlogCommentService;
import com.jia.blog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 管理博客详情页面的处理器类 【Redis优化】
 */
@Controller
@RequestMapping("/MyBlog")
public class MyBlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogCommentService blogCommentService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /* ---------Redis -------------*/
    @Autowired
    private RedisUtil redisUtil;

    /*--------------------跳转页面-------------------*/
    /**
     * 跳转博客详情
     * @return html
     */
    @GetMapping("/blog")
    public String blog(){
        return "blog";
    }

    /*--------------------Select-------------------*/
    /**
     * 查询单个博客
     * @param id 查询博客的id
     * @return json
     * @throws JsonProcessingException json异常
     */
    @GetMapping("/blog/getBlog")
    @ResponseBody
    public String getBlog(Long id) throws JsonProcessingException {
        Blog blog = blogService.getBlogConvert(id);
        Integer views = blog.getViews();
        views += 1;
        blogService.updateBlogViews(id, views);
        blog.setViews(views);
        return objectMapper.writeValueAsString(blog);
    }

    /**
     * 获取博客对应的评论
     * @param blog_id 对应博客的id
     * @return json
     */
    @GetMapping("/blog/getComment")
    @ResponseBody
    public String getComment(Long blog_id) throws JsonProcessingException {
        List<Comment> comment = blogCommentService.getComment(blog_id);
        return objectMapper.writeValueAsString(new PageInfo<Comment>(comment));
    }

    /**
     * 验证是否管理员登录(登录后可对留言进行删除操作，而且评论不用发输入个人信息)
     * @return 返回管理员信息
     */
    @PostMapping("/blog/checkAdmin")
    @ResponseBody
    public String checkAdmin(HttpSession session) throws JsonProcessingException {
        User user = (User) session.getAttribute("user");
        if (user != null){
            //说明管理员登录了
            user.setPassword("");
            user.setUsername("");
            return objectMapper.writeValueAsString(user);
        }
        return null;
    }

    /**
     * 获取博客的所有访问量
     * @return 访问量
     */
    @GetMapping("/blog/viewsCount")
    @ResponseBody
    public Integer findBlogViews(){
        return blogService.findBlogViews();
    }

    /**
     * 获取博客的数量   [Redis改进]
     * @return 数量
     */
    @GetMapping("/blog/blogCount")
    @ResponseBody
    public Integer findBlogCount(){
        Object bc = redisUtil.get("blogCount");
        if (bc == null){
            Integer blogCount = blogService.getBlogCount();
            redisUtil.set("blogCount",blogCount);
            return blogCount;
        }
        return (Integer) bc;
    }

    /*--------------------Insert-------------------*/
    /**
     * 添加一条评论(或回复)
     * @return 布尔
     */
    @PostMapping("/blog/saveComment")
    @ResponseBody
    public Boolean saveComment(Comment comment,String replyToUser){
        if (comment != null){
            if (replyToUser != null && !"".equals(replyToUser)){
                comment.setReplyToUser(replyToUser);
            }
            int i = blogCommentService.saveComment(comment);
            return i != 0;
        }
        return false;
    }

    /*--------------------Delete-------------------*/
    /**
     * 删除一条评论(若删除父评论，这也删除子评论)
     * @param comment 对应评论的数据(包括 评论id 父评论的id)
     * @return 判断是否成功
     */
    @GetMapping("/blog/removeComment")
    @ResponseBody
    public Boolean removeComment(Comment comment){
        //判断传入的值,然后动态的拼接sql语句

        int i = blogCommentService.removeComment(comment);
        return i!=0;
    }
}
