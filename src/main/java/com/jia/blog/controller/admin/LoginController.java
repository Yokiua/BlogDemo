package com.jia.blog.controller.admin;

import com.jia.blog.pojo.User;
import com.jia.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 后台管理登录的处理器类
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 返回登录页面的
     * @return 登录页面
     */
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    /*------------------Select------------------*/
    /**
     * 【使用模板引擎渲染前台页面】
     * 登录方法
     * @param username 前台输入用户名
     * @param password 后台输入
     * @param session session 用于存储管理员登录后的信息(方便校验)
     * @param attributes 存储提示信息(引擎模板渲染数据的)
     * @return 根据是否登录成功，返回相应的页面
     * @throws IOException 异常
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) throws IOException {
        //密码已被md5加密,这里查询数据库需要把密码md5化
        User user = userService.checkUser(username, DigestUtils.md5DigestAsHex(password.getBytes()));
        if (user != null){
            user.setPassword(null);//把密码为空,为了安全性
            session.setAttribute("user",user);
            return "admin/index";
        }
        //设置参数,并重定向到登录页面
        attributes.addFlashAttribute("message","用户名或密码错误");
        return "redirect:/admin";
    }

    /**
     * 登出方法
     * @return 登出后返回登录页面
     */
    @GetMapping("/logout")
    public String logOut(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
