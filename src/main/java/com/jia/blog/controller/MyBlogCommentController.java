package com.jia.blog.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.jia.blog.pojo.Comment;
import com.jia.blog.pojo.User;
import com.jia.blog.service.BlogCommentService;
import com.jia.blog.service.CommentHtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 管理留言区的处理器类
 */
@Controller
@RequestMapping("/MyBlog")
public class MyBlogCommentController {

    @Autowired
    private CommentHtmlService commentHtmlService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 跳转到留言区
     * @return html
     */
    @GetMapping("/comment")
    public String comment(){
        return "comment";
    }

    /**
     * 验证是否管理员登录(登录后可对留言进行删除操作，而且评论不用发输入个人信息)
     * @return 返回管理员信息
     */
    @PostMapping("/comment/checkAdmin")
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
     * 获取所有的评论
     * @return 实体类
     */
    @GetMapping("/comment/findComment")
    @ResponseBody
    public String getComment() throws JsonProcessingException {
        PageInfo<Comment> pageInfo = new PageInfo<>(commentHtmlService.getCommentHtml());
        return objectMapper.writeValueAsString(pageInfo);
    }

    /**
     * 添加一条评论(或回复)
     * @return 布尔
     */
    @PostMapping("/comment/saveComment")
    @ResponseBody
    public Boolean saveComment(Comment comment,String replyToUser){
        if (comment != null){
            if (replyToUser != null && !"".equals(replyToUser)){
                comment.setReplyToUser(replyToUser);
            }
            int i = commentHtmlService.saveCommentHtml(comment);
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
    @GetMapping("/comment/removeComment")
    @ResponseBody
    public Boolean removeComment(Comment comment){
        //判断传入的值,然后动态的拼接sql语句
        int i = commentHtmlService.removeCommentHtml(comment);
        return i!=0;
    }
}
